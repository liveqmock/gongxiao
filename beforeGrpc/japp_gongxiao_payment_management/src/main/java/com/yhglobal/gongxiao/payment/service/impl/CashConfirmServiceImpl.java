package com.yhglobal.gongxiao.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.payment.dao.CashLedgerDao;
import com.yhglobal.gongxiao.payment.model.CashConfirm;
import com.yhglobal.gongxiao.payment.model.CashConfirmStatusEnum;
import com.yhglobal.gongxiao.payment.model.CashLedger;
import com.yhglobal.gongxiao.payment.service.CashConfirmService;
import com.yhglobal.gongxiao.payment.service.DistributorCashAccountService;
import com.yhglobal.gongxiao.payment.service.processor.CashConfirmTransactionProcessor;
import com.yhglobal.gongxiao.sales.dao.SalesCashConfirmDao;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yhglobal.gongxiao.constant.ErrorCode.ARGUMENTS_INVALID;
import static com.yhglobal.gongxiao.constant.ErrorCode.BANK_FLOW_NUMBER_REPEATED;
import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;

/**
 * 销售收款确认表Service
 *
 * @author: 葛灿
 */
@Service(timeout = 5000)
public class CashConfirmServiceImpl implements CashConfirmService {
    private static Logger logger = LoggerFactory.getLogger(CashConfirmServiceImpl.class);
    @Autowired
    SalesCashConfirmDao salesCashConfirmDao;

    @Autowired
    CashLedgerDao cashLedgerDao;

    @Autowired
    DistributorCashAccountService distributorCashAccountService;

    @Autowired
    CashConfirmTransactionProcessor cashConfirmTransactionProcessor;


    @Override
    public PageInfo<CashConfirm> selectListSelective(RpcHeader rpcHeader,
                                                     String salesOrderNo,
                                                     Long projectId,
                                                     String distributorName,
                                                     String bankName,
                                                     Date orderBegin,
                                                     Date orderEnd,
                                                     Date confirmBegin,
                                                     Date confirmEnd,
                                                     Integer[] orderStatus,
                                                     int pageNum,
                                                     int pageSize) {

        logger.info("#traceId={}# [IN][selectListSelective] params: salesOrderNo={}, projectId={}, distributorName={}, bankName={}, orderBegin={}, orderEnd={}, confirmBegin={}, confirmEnd={}, orderStatus={}",
                rpcHeader.traceId, salesOrderNo, projectId, distributorName, bankName, orderBegin, orderEnd, confirmBegin, confirmEnd, orderStatus);
        PageInfo<CashConfirm> pageInfo;
        try {
            String floatString;
            //查询用户信息
            PageHelper.startPage(pageNum, pageSize);
            List<CashConfirm> confirmList = salesCashConfirmDao.selectListSelectively(salesOrderNo, projectId, distributorName, bankName, orderBegin, orderEnd, confirmBegin, confirmEnd, orderStatus);
            for (CashConfirm record : confirmList) {
                floatString = DoubleScale.keepTwoBits(record.getConfirmAmount());
                record.setConfirmAmountDouble(floatString);
                floatString = DoubleScale.keepTwoBits(record.getUnreceiveAmount());
                record.setUnreceiveAmountDouble(floatString);
                floatString = DoubleScale.keepTwoBits(record.getShouldReceiveAmount());
                record.setShouldReceiveAmountDouble(floatString);
                floatString = DoubleScale.keepTwoBits(record.getRecipientAmount());
                record.setRecipientAmountDouble(floatString);
            }
            pageInfo = new PageInfo<CashConfirm>(confirmList);
            logger.info("#traceId={}# [OUT]: get confirm list success", rpcHeader.traceId);
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("search orderList error!", e);
        }
    }

    @Override
    public RpcResult confirmCash(RpcHeader rpcHeader, String bankName, Date recipientDate, List<CashConfirm> items, String bankFlowNo) throws CloneNotSupportedException {

        logger.info("#traceId={}# [IN][confirmCash] params: bankName={}, recipientDate={}, items.size={}",
                rpcHeader.traceId, bankName, recipientDate, items.size());
        try {
            if (items.size() < 1) {
                return RpcResult.newFailureResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
            }
            //查询银行流水号是否存在,存在则return
            int count = salesCashConfirmDao.selectCountByBankFlowNo(bankFlowNo);
            if (count != 0) {
                return RpcResult.newFailureResult(BANK_FLOW_NUMBER_REPEATED.getErrorCode(), BANK_FLOW_NUMBER_REPEATED.getMessage());
            }
            //执行事务
            cashConfirmTransactionProcessor.cashConfirmTransaction(rpcHeader, bankName, bankFlowNo, recipientDate, items);

            logger.info("#traceId={}# [OUT]: confirm success", rpcHeader.traceId);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult cancelCashConfirm(RpcHeader rpcHeader, String salesOrderNo, long prestoredAmount, Long couponAmount, long prepaidAmount) {
        try {
            logger.info("#traceId={}# [IN][cancelCashConfirm] params: salesOrderNo={}, prestoredAmount={}, couponAmount={}, prepaidAmount={}",
                    rpcHeader.traceId, salesOrderNo, prestoredAmount, couponAmount, prepaidAmount);
            int maxRetryTimes = 6;
            CashConfirm confirmOrder = null;
            while (maxRetryTimes-- > 0) {
                //根据订单号，查找 未收金额=0 && 未作废 的确认单
                confirmOrder = salesCashConfirmDao.getConfirmOrder(salesOrderNo);
                if (confirmOrder == null || confirmOrder.getConfirmStatus() != CashConfirmStatusEnum.UNCONFIRM.getStatus()) {
                    //如果未找到 || 订单状态不为“未确认”，则说明无法取消
                    logger.info("#traceId={}# [OUT]: cancel cash confirm fail. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
                    return RpcResult.newFailureResult();
                }
                //操作日志添加
                TraceLog newTracelog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "作废");
                String traceLogJson = TraceLogUtil.appendTraceLog(confirmOrder.getTracelog(), newTracelog);
                confirmOrder.setTracelog(traceLogJson);
                //作废确认单
                int update = salesCashConfirmDao.cancelComfirmOrder(confirmOrder);
                if (update == 1) {
                    break;
                }
            }
            // 如果更新失败，抛出位置系统异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to cancel cashConfirm. salesOrderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to cancel cashConfirm");
            }
            logger.info("#traceId={}# [OUT]: cancel cash confirm success. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult confirmNegativeAmountAutomatically(RpcHeader rpcHeader, String salesOrderNo) {
        logger.info("#traceId={}# [IN][confirmNegativeAmountAutomatically] params: salesOrderNo={}",
                rpcHeader.traceId, salesOrderNo);
        try {
            CashConfirm cashConfirm = salesCashConfirmDao.getConfirmOrder(salesOrderNo);
            //如果存在 未收金额>0 无需自动转入
            if (cashConfirm == null || cashConfirm.getUnreceiveAmount() >= 0) {
                logger.info("#traceId={}# [OUT]: cancel cash confirm success. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
                return RpcResult.newSuccessResult();
            }
            String automaticFlow = distributorCashAccountService.updateDistributorCashAccount(rpcHeader, cashConfirm.getUnreceiveCurrency(), cashConfirm.getDistributorId(), cashConfirm.getProjectId(), -cashConfirm.getUnreceiveAmount(), salesOrderNo, new Date(), null);
            logger.info("#traceId={}# [OUT]: cancel cash confirm success. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<CashConfirm> selectConfirmList(RpcHeader rpcHeader, String[] salesOrderNoList) {
        logger.info("#traceId={}# [IN][selectConfirmList] params: salesOrderNoList={}",
                rpcHeader.traceId, String.valueOf(salesOrderNoList));
        try {
            String floatString;
            List<CashConfirm> list = new ArrayList<>();
            for (String salesOrderNo : salesOrderNoList) {
                CashConfirm cashConfirm = null;
                cashConfirm = salesCashConfirmDao.getConfirmOrder(salesOrderNo);
                floatString = DoubleScale.keepTwoBits(cashConfirm.getUnreceiveAmount());
                cashConfirm.setUnreceiveAmountDouble(floatString);
                floatString = DoubleScale.keepTwoBits(cashConfirm.getShouldReceiveAmount());
                cashConfirm.setShouldReceiveAmountDouble(floatString);
                floatString = DoubleScale.keepTwoBits(cashConfirm.getUnreceiveAmount());
                cashConfirm.setConfirmAmountDouble(floatString);
                floatString = DoubleScale.keepTwoBits(cashConfirm.getUnreceiveAmount());
                cashConfirm.setRecipientAmountDouble(floatString);
                list.add(cashConfirm);
            }
            logger.info("#traceId={}# [OUT]: select list success", rpcHeader.traceId);
            return list;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult createSalesOrderCashConfirmOrder(RpcHeader rpcHeader, String salesOrderNo, Long distributorId, String distributorName, Long projectId, String projectName, String currencyCode, Long cashAmount, Date createTime) {
        logger.info("#traceId={}# [IN][createSalesOrderCashConfirmOrder] params: salesOrderNo={}, distributorId={}, distributorName={}, projectId={}, projectName={}, currencyCode={}, cashAmount={}, createTime={}",
                rpcHeader.traceId, salesOrderNo, distributorId, distributorName, projectId, projectName, currencyCode, cashAmount, createTime);
        try {
            CashConfirm cashConfirm = new CashConfirm();
            cashConfirm.setSalesOrderNo(salesOrderNo);
            cashConfirm.setFlowNo(null);
            cashConfirm.setRecipientStatus(false);
            cashConfirm.setConfirmStatus(CashConfirmStatusEnum.UNCONFIRM.getStatus());
            cashConfirm.setDistributorId(distributorId);
            cashConfirm.setDistributorName(distributorName);
            cashConfirm.setProjectId(projectId);
            cashConfirm.setProjectName(projectName);
            cashConfirm.setShouldReceiveCurrency(currencyCode);
            cashConfirm.setShouldReceiveAmount(cashAmount);
            cashConfirm.setUnreceiveCurrency(currencyCode);
            cashConfirm.setUnreceiveAmount(cashAmount);
            cashConfirm.setConfirmCurrency(currencyCode);
            cashConfirm.setConfirmAmount(0);
            cashConfirm.setRecipientAmount(0);
            cashConfirm.setRecipientCurrency(currencyCode);
            cashConfirm.setConfirmTime(null);
            cashConfirm.setReceiveTime(null);
            cashConfirm.setPayer(null);
            cashConfirm.setCreateTime(new Date());
            cashConfirm.setOrderTime(createTime);
            TraceLog newTraceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "新建");
            String traceLogJson = TraceLogUtil.appendTraceLog(null, newTraceLog);
            cashConfirm.setTracelog(traceLogJson);
            if (cashConfirm.getShouldReceiveAmount() == 0) {
                //如果为0,自动确认,插入审核后的台账数据
                String flowNo = DateTimeIdGenerator.nextId(BizNumberType.SALES_CASH_CONFIRM);
                cashConfirm.setFlowNo(flowNo);
                cashConfirm.setConfirmStatus(CashConfirmStatusEnum.CONFIRM.getStatus());
                cashConfirm.setRecipientStatus(true);
                cashConfirm.setConfirmTime(new Date());

                CashLedger cashLedger = new CashLedger();
                cashLedger.setFlowNo(flowNo);
                cashLedger.setApprovalStatus(true);
                cashLedger.setDistributorId(cashConfirm.getDistributorId());
                cashLedger.setDistributorName(cashConfirm.getDistributorName());
                cashLedger.setProjectId(cashConfirm.getProjectId());
                cashLedger.setProjectName(cashConfirm.getProjectName());
                cashLedger.setConfirmAmount(0L);
                cashLedger.setRecipientAmount(0L);
                cashLedger.setConfirmCurrency(cashConfirm.getConfirmCurrency());
                cashLedger.setRecipientCurrency(cashLedger.getRecipientCurrency());
                cashLedger.setConfirmTime(new Date());
                cashLedger.setApproveTime(new Date());
                cashLedger.setApprovalUserName("系统");
                cashLedger.setCreateTime(new Date());
                cashLedger.setDataVersion(0L);
                cashLedger.setTracelog(traceLogJson);
                cashLedger.setBankFlowNo("");
                int insert = cashLedgerDao.insert(cashLedger);
            }
            int insert = salesCashConfirmDao.insert(cashConfirm);
            logger.info("#traceId={}# [OUT]: confirm success", rpcHeader.traceId);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }


}
