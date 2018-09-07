package com.yhglobal.gongxiao.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.payment.dao.CashLedgerDao;
import com.yhglobal.gongxiao.payment.model.CashConfirm;
import com.yhglobal.gongxiao.payment.model.CashConfirmStatusEnum;
import com.yhglobal.gongxiao.payment.model.CashLedger;
import com.yhglobal.gongxiao.payment.service.CashLedgerService;
import com.yhglobal.gongxiao.sales.dao.SalesCashConfirmDao;
import com.yhglobal.gongxiao.sales.service.SalesOrderService;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yhglobal.gongxiao.constant.ErrorCode.DATE_VERSION_DIFFER;
import static com.yhglobal.gongxiao.constant.ErrorCode.LEDGER_CAN_NOT_CANCEL_APPROVE;
import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;

/**
 * 收款台账表Service
 *
 * @author: 葛灿
 */
@Service(timeout = 5000)
public class CashLedgerServiceImpl implements CashLedgerService {
    private static Logger logger = LoggerFactory.getLogger(CashLedgerServiceImpl.class);

    @Autowired
    CashLedgerDao cashLedgerDao;
    @Autowired
    SalesCashConfirmDao salesCashConfirmDao;
    @Reference(check = false)
    SalesOrderService salesOrderService;


    @Override
    public RpcResult approveLedger(RpcHeader rpcHeader, String flowNo) {
        logger.info("#traceId={}# [IN][approveLedger] params: flowNo={}",
                rpcHeader.traceId, flowNo);
        try {
            //遍历流水号，分别更新各个信息
            CashLedger cashLedger = cashLedgerDao.getByFlowNo(flowNo);
            boolean approvalStatus = cashLedger.isApprovalStatus();
            if (approvalStatus) {
                //数据校验，如果该单已审批，无法再次审批
                return RpcResult.newFailureResult(DATE_VERSION_DIFFER.getErrorCode(), DATE_VERSION_DIFFER.getMessage());
            }
            cashLedger.setApprovalStatus(true);
            cashLedger.setApproveTime(new Date());
            cashLedger.setReceiveTime(new Date());
            cashLedger.setApprovalUserId(Long.parseLong(rpcHeader.getUid()));
            cashLedger.setApprovalUserName(rpcHeader.getUsername());
            int update = cashLedgerDao.update(cashLedger);
            //根据流水号查找对应的收款确认订单list
            List<CashConfirm> cashConfirmList = salesCashConfirmDao.selectConfirmListByFlowNo(flowNo);
            for (CashConfirm cashConfirm : cashConfirmList) {
                //遍历时，如果未收金额<=0,并且其他确认单均已收款，改变收款状态为true
                long unreceiveAmount = cashConfirm.getUnreceiveAmount();
                if (unreceiveAmount <= 0) {
                    //前往销售模块，修改对应销售单的收款状态
                    RpcResult rpcResult = salesOrderService.confirmSalesOrderAmount(rpcHeader, cashConfirm.getSalesOrderNo());
                    cashConfirm.setRecipientStatus(true);
                }
                cashConfirm.setReceiveTime(new Date());
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "收款审批");
                String appendTraceLog = TraceLogUtil.appendTraceLog(cashConfirm.getTracelog(), traceLog);
                cashConfirm.setTracelog(appendTraceLog);
                int update1 = salesCashConfirmDao.update(cashConfirm);
            }
            logger.info("#traceId={}# [OUT]: approve success. flowNo={}", rpcHeader.traceId, flowNo);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult cancelApproveLedger(RpcHeader rpcHeader, String flowNo) {
        logger.info("#traceId={}# [IN][cancelApproveLedger] params: flowNo={}",
                rpcHeader.traceId, flowNo);
        try {
            //数据校验
            CashLedger cashLedger = cashLedgerDao.getByFlowNo(flowNo);
            boolean approvalStatus = cashLedger.isApprovalStatus();
            //如果未审批，无法取消审批
            if (!approvalStatus) {
                logger.info("#traceId={}# [OUT]:  flowNo'{}' can NOT cancel .", rpcHeader.traceId, flowNo);
                return RpcResult.newFailureResult(DATE_VERSION_DIFFER.getErrorCode(), DATE_VERSION_DIFFER.getMessage());
            }
            //如果有销售单已经进行发货，无法取消审批
            ArrayList<String> salesOrderNoList = new ArrayList<>();
            List<CashConfirm> cashConfirmList = salesCashConfirmDao.selectConfirmListByFlowNo(flowNo);
            for (CashConfirm cashConfirm : cashConfirmList) {
                String salesOrderNo = cashConfirm.getSalesOrderNo();
                boolean whetherOutbound = salesOrderService.whetherOutbound(rpcHeader, salesOrderNo);
                //如果存在已经发货的订单，无法取消
                if (whetherOutbound) {
                    logger.info("#traceId={}# [OUT]:  flowNo'{}' can NOT cancel .", rpcHeader.traceId, flowNo);
                    return RpcResult.newFailureResult(LEDGER_CAN_NOT_CANCEL_APPROVE.getErrorCode(), LEDGER_CAN_NOT_CANCEL_APPROVE.getMessage());
                }
            }

            //更改台账信息，分别更新各个信息
            cashLedger.setApprovalStatus(false);
            cashLedger.setApproveTime(null);
            cashLedger.setApprovalUserId(null);
            cashLedger.setApprovalUserName(null);
            //添加操作日志
            TraceLog traceLog2 = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消审批");
            String appendTraceLog2 = TraceLogUtil.appendTraceLog(cashLedger.getTracelog(), traceLog2);
            cashLedger.setTracelog(appendTraceLog2);
            for (CashConfirm cashConfirm : cashConfirmList) {
                //销售单修改状态为未收款
                String salesOrderNo = cashConfirm.getSalesOrderNo();
                boolean success = salesOrderService.cancelReceivedCash(rpcHeader, salesOrderNo).getSuccess();
                //修改收款状态、收款时间
                cashConfirm.setReceiveTime(null);
                cashConfirm.setRecipientStatus(false);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消审批");
                String appendTraceLog = TraceLogUtil.appendTraceLog(cashConfirm.getTracelog(), traceLog);
                cashConfirm.setTracelog(appendTraceLog);
                int update1 = salesCashConfirmDao.update(cashConfirm);
            }
            int update = cashLedgerDao.update(cashLedger);
            if (update == 1) {
                logger.info("#traceId={}# [OUT]: flowNo{} cancel success.", rpcHeader.traceId, flowNo);
                return RpcResult.newSuccessResult();
            } else {
                logger.error("#traceId={}# [OUT]: flowNo{} cancel FAILED.", rpcHeader.traceId, flowNo);
                return RpcResult.newFailureResult();
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<CashLedger> selectCashLedgerList(RpcHeader rpcHeader, Long projectId, String bankName, String flowNo, Date confirmBegin, Date confirmEnd, Date receiveBegin, Date receiveEnd, Boolean approveStatus, int pageNum, int pageSize) {
        logger.info("#traceId={}# [IN][selectCashLedgerList] params: projectId={}, bankName={}, flowNo={}, confirmBegin={}, confirmEnd={}, receiveBegin={}, receiveEnd={}, approveStatus={}, pageNum={}, pageSize={}",
                rpcHeader.traceId, projectId, bankName, flowNo, confirmBegin, confirmEnd, receiveBegin, receiveEnd, approveStatus, pageNum, pageSize);
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<CashLedger> list = cashLedgerDao.selectCashLedgerList(projectId, bankName, flowNo, confirmBegin, confirmEnd, receiveBegin, receiveEnd, approveStatus);
            for (CashLedger cashLedger : list) {
                String confirmAmount = DoubleScale.keepTwoBits(cashLedger.getConfirmAmount());
                cashLedger.setConfirmAmountDouble(confirmAmount);
                String recipientAmount = DoubleScale.keepTwoBits(cashLedger.getRecipientAmount());
                cashLedger.setRecipientAmountDouble(recipientAmount);
            }
            PageInfo<CashLedger> pageInfo = new PageInfo<>(list);
            logger.info("#traceId={}# [OUT]: select success.", rpcHeader.traceId);
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult cancelConfirm(RpcHeader rpcHeader, String flowNo) throws CloneNotSupportedException {
        logger.info("#traceId={}# [IN][cancelConfirm] params: flowNo={}",
                rpcHeader.traceId, flowNo);
        try {
            CashLedger cashLedger = cashLedgerDao.getByFlowNo(flowNo);
            boolean approvalStatus = cashLedger.isApprovalStatus();
            boolean canceled = cashLedger.isCanceled();
            if (approvalStatus || canceled) {
                logger.info("flowNo '{}' cancel fail", flowNo);
                return RpcResult.newFailureResult(DATE_VERSION_DIFFER.getErrorCode(), DATE_VERSION_DIFFER.getMessage());
            }
            cashLedger.setCanceled(true);
            int update = cashLedgerDao.update(cashLedger);
            //根据流水号，找到对应的现金确认list，作废
            List<CashConfirm> cashConfirms = salesCashConfirmDao.selectConfirmListByFlowNo(flowNo);
            for (CashConfirm cashConfirm : cashConfirms) {
                String salesOrderNo = cashConfirm.getSalesOrderNo();
                //如果只存在一条，修改原单数据
                int count = salesCashConfirmDao.getUncanceledCountBySalesOrderNo(salesOrderNo);
                if (count == 1) {
                    cashConfirm.setConfirmStatus(CashConfirmStatusEnum.UNCONFIRM.getStatus());
                    cashConfirm.setUnreceiveAmount(cashConfirm.getUnreceiveAmount() + cashConfirm.getConfirmAmount());
                    cashConfirm.setRecipientStatus(false);
                    cashConfirm.setConfirmAmount(0);
                    cashConfirm.setRecipientAmount(0);
                    cashConfirm.setFlowNo(null);
                    cashConfirm.setConfirmTime(null);
                    cashConfirm.setBankName(null);
                    cashConfirm.setReceiveTime(null);
                    cashConfirm.setBankFlowNo(null);
                    TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "作废");
                    String appendTraceLog = TraceLogUtil.appendTraceLog(cashConfirm.getTracelog(), traceLog);
                    cashConfirm.setTracelog(appendTraceLog);
                    int update1 = salesCashConfirmDao.update(cashConfirm);
                } else {
                    salesCashConfirmDao.cancelComfirmOrder(cashConfirm);
                    if (cashConfirm.getUnreceiveAmount() == 0) {
                        //如果未收款金额=0，新插入一条待确认记录
                        CashConfirm clone = cashConfirm.clone();
                        clone.setConfirmId(null);
                        clone.setConfirmAmount(0);
                        clone.setRecipientAmount(0);
                        clone.setFlowNo(null);
                        clone.setBankName(null);
                        clone.setConfirmTime(null);
                        clone.setReceiveTime(null);
                        clone.setBankFlowNo(null);
                        clone.setCreateTime(new Date());
                        //操作日志
                        TraceLog newTraceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消后新增");
                        String traceLogJson = TraceLogUtil.appendTraceLog(null, newTraceLog);
                        clone.setTracelog(traceLogJson);
                        salesCashConfirmDao.insert(clone);
                    }
                }
                //回滚其他应收单的“未收金额”，如果未收==应收，状态改为未确认，否则，状态改为部分确认
                long confirmAmount = cashConfirm.getConfirmAmount();
                long unreceivedAmount = cashConfirm.getUnreceiveAmount() + confirmAmount;
                CashConfirmStatusEnum confirmStatus;
                if (unreceivedAmount == cashConfirm.getShouldReceiveAmount()) {
                    confirmStatus = CashConfirmStatusEnum.UNCONFIRM;
                } else {
                    confirmStatus = CashConfirmStatusEnum.PART_CONFIRM;
                }
                //修改这个销售单相关的所有收款确认，修改未收金额和收款状态
                salesCashConfirmDao.updateBySalesOrder(salesOrderNo, confirmStatus.getStatus(), unreceivedAmount);
            }
            logger.info("#traceId={}# [OUT]: cancel confirm success. flowNo={}", rpcHeader.traceId, flowNo);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
