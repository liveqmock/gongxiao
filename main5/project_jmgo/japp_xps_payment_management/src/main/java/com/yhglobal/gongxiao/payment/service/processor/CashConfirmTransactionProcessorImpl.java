package com.yhglobal.gongxiao.payment.service.processor;

import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.dao.CashConfirmDao;
import com.yhglobal.gongxiao.payment.dao.CashLedgerDao;
import com.yhglobal.gongxiao.payment.model.CashConfirm;
import com.yhglobal.gongxiao.payment.model.CashConfirmStatusEnum;
import com.yhglobal.gongxiao.payment.model.CashLedger;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;

/**
 * @author 葛灿
 */
@Service
public class CashConfirmTransactionProcessorImpl implements CashConfirmTransactionProcessor {

    @Autowired
    CashConfirmDao salesCashConfirmDao;

    @Autowired
    CashLedgerDao cashLedgerDao;

    private static final Logger logger = LoggerFactory.getLogger(CashConfirmTransactionProcessorImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cashConfirmTransaction(String prefix,
                                       GongxiaoRpc.RpcHeader rpcHeader, String bankName, String bankFlowNo, Date recipientDate, List<CashConfirm> cashConfirmList, String clientName, String remark) throws CloneNotSupportedException {
        logger.info("#traceId={}# [IN][cashConfirmTransaction] params: bankName={}, bankFlowNo={}, recipientDate={}, items.size={}",
                rpcHeader.getTraceId(), bankName, bankFlowNo, recipientDate, cashConfirmList.size());
        try {
            String flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.SALES_CASH_CONFIRM);
            //总确认金额、总收款金额，用于生成台账时使用
            long totalConfirmAmount = 0;
            long totalRecipientAmount = 0;
            CashConfirm cashConfirm = null;
            for (CashConfirm item : cashConfirmList) {
                long unReceivedAmount = 0;
                int confirmStatus = 0;
                int maxRetryTimes = 6;
                while (maxRetryTimes-- > 0) {
                    //页面传递的数据版本
                    long pageDataVersion = item.getDataVersion();
                    cashConfirm = salesCashConfirmDao.getById(prefix, item.getConfirmId());
                    //数据库中的数据版本
                    long dataVersion = cashConfirm.getDataVersion();
                    if (pageDataVersion != dataVersion) {
                        logger.error("cashConfirm dataVersion conflict !");
                        throw new RuntimeException("cashConfirm dataVersion conflict !");
                    }
                    //如果未收款金额为0，修改原始记录，如果未收修改金额!=0，修改原始记录的同时，插入一条待确认记录
                    //字段修改
                    long confirmAmount = Math.round(Double.parseDouble(item.getConfirmAmountDouble()) * HUNDRED);
                    totalConfirmAmount += confirmAmount;
                    long recipientAmount = Math.round(Double.parseDouble(item.getRecipientAmountDouble()) * HUNDRED);
                    totalRecipientAmount += recipientAmount;
                    //未收金额 = 原始未收金额 - 确认金额
                    unReceivedAmount = cashConfirm.getUnreceiveAmount() - confirmAmount;
                    if (unReceivedAmount == 0) {
                        //如果未收款金额为0，修改状态为“已收款”
                        confirmStatus = CashConfirmStatusEnum.CONFIRM.getStatus();
                    } else {
                        confirmStatus = CashConfirmStatusEnum.PART_CONFIRM.getStatus();
                    }
                    //添加操作日志
                    TraceLog newTraceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "现金确认");
                    String traceLogJson = TraceLogUtil.appendTraceLog(cashConfirm.getTracelog(), newTraceLog);
                    int update = salesCashConfirmDao.updateWhenConfirm(prefix, cashConfirm.getConfirmId(), cashConfirm.getDataVersion(), confirmStatus, confirmAmount,
                            recipientAmount, unReceivedAmount, bankName, flowNo, new Date(), bankFlowNo,
                            recipientDate, clientName, remark, traceLogJson
                    );
                    if (update == 1) {
                        break;
                    }
                }
                // 如果更新失败，抛出位置系统异常
                if (maxRetryTimes <= 0) {
                    logger.error("FAILED to confirm cash. salesOrderNo={}", cashConfirm.getSalesOrderNo());
                    throw new RuntimeException("FAILED to confirm cash");
                }
                if (unReceivedAmount != 0) {
                    //如果未收款金额>0，新插入一条待确认记录
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
                    clone.setClientName(null);
                    clone.setRemark(null);
                    //操作日志
                    TraceLog newTraceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "确认后新增");
                    String traceLogJson = TraceLogUtil.appendTraceLog(null, newTraceLog);
                    clone.setTracelog(traceLogJson);
                    salesCashConfirmDao.insert(prefix, clone);
                }
                //修改这个销售单相关的所有收款确认，修改未收金额和收款状态
                int update = salesCashConfirmDao.updateBySalesOrder(prefix, cashConfirm.getSalesOrderNo(), confirmStatus, unReceivedAmount);
            }

            //创建台账信息
            this.createCashLedger(prefix, rpcHeader, flowNo,
                    cashConfirm.getDistributorId(),
                    cashConfirm.getDistributorName(),
                    cashConfirm.getProjectId(),
                    cashConfirm.getProjectName(),
                    cashConfirm.getRecipientCurrency(),
                    totalConfirmAmount,
                    totalRecipientAmount,
                    recipientDate,
                    new Date(),
                    bankName,
                    bankFlowNo,
                    clientName,
                    remark);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 创建台账信息
     *
     * @param rpcHeader       头
     * @param flowOrderNo     流水号
     * @param distributorId   分销商id
     * @param distributorName 分销商名称
     * @param projectId       项目id
     * @param projectName     项目名称
     * @param currencyCode    货币编码
     * @param confirmAmount   确认金额
     * @param recipientAmount 实收金额
     * @param receiveTime     收款时间
     * @param recipientBank   收款银行
     * @return
     */
    private void createCashLedger(String prefix, GongxiaoRpc.RpcHeader rpcHeader,
                                  String flowOrderNo, Long distributorId,
                                  String distributorName, Long projectId,
                                  String projectName, String currencyCode,
                                  Long confirmAmount, long recipientAmount,
                                  Date receiveTime, Date confirmDate,
                                  String recipientBank, String bankFlowNo,
                                  String clientName, String remark) {
        try {
            logger.info("#traceId={}# [IN][getOrderByOrderNo] params: flowOrderNo={}, distributorId={}, distributorName={}, projectId={}, projectName={}, currencyCode={}, confirmAmount={}, recipientAmount={}, receiveTime={}, recipientBank={}",
                    rpcHeader.getTraceId(), flowOrderNo, distributorId, distributorName, projectId, projectName, currencyCode, confirmAmount, recipientAmount, receiveTime, recipientBank);
            //创建对象
            CashLedger cashLedger = new CashLedger();
            //填充字段
            cashLedger.setCanceled(false);
            cashLedger.setFlowNo(flowOrderNo);
            cashLedger.setApprovalStatus(false);
            cashLedger.setDistributorId(distributorId);
            cashLedger.setDistributorName(distributorName);
            cashLedger.setProjectId(projectId);
            cashLedger.setProjectName(projectName);
            cashLedger.setConfirmAmount(confirmAmount);
            cashLedger.setConfirmCurrency(currencyCode);
            cashLedger.setRecipientAmount(recipientAmount);
            cashLedger.setRecipientCurrency(currencyCode);
            cashLedger.setConfirmTime(confirmDate);
            cashLedger.setApproveTime(null);
            cashLedger.setApprovalUserId(null);
            cashLedger.setApprovalUserName(null);
            cashLedger.setReceiveTime(receiveTime);
            cashLedger.setBankName(recipientBank);
            cashLedger.setCreateTime(new Date());
            cashLedger.setDataVersion(0L);
            cashLedger.setBankFlowNo(bankFlowNo);
            cashLedger.setClientName(clientName);
            cashLedger.setRemark(remark);
            cashLedger.setCreatedById(rpcHeader.getUid());
            cashLedger.setCreatedByName(rpcHeader.getUsername());
            TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "新建");
            String traceLogJson = TraceLogUtil.appendTraceLog(null, traceLog);
            cashLedger.setTracelog(traceLogJson);
            //插入数据库
            int insert = cashLedgerDao.insert(prefix, cashLedger);
            logger.info("#traceId={}# [OUT]: create success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
