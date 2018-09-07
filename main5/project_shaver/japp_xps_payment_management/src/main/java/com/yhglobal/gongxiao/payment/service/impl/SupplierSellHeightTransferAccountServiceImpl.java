package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.bo.WriteOffReturnObject;
import com.yhglobal.gongxiao.payment.dao.JmgoSupplierSellHighFlowDao;
import com.yhglobal.gongxiao.payment.dao.SupplierSellHeightTransferAccountDao;
import com.yhglobal.gongxiao.payment.model.FlowTypeEnum;
import com.yhglobal.gongxiao.payment.model.JmgoSupplierSellHighFlow;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightAccount;
import com.yhglobal.gongxiao.payment.service.SupplierSellHeightTransferAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierSellHeightTransferRecordService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.yhglobal.gongxiao.constant.ErrorCode.SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH;

/**
 * 低买高卖账户 service
 *
 * @author: 葛灿
 */
@Service
public class SupplierSellHeightTransferAccountServiceImpl implements SupplierSellHeightTransferAccountService {

    private static Logger logger = LoggerFactory.getLogger(SupplierSellHeightTransferAccountServiceImpl.class);

    @Autowired
    SupplierSellHeightTransferAccountDao supplierSellHeightTransferAccountDao;

    @Autowired
    SupplierSellHeightTransferRecordService supplierSellHeightTransferRecordService;

    @Autowired
    JmgoSupplierSellHighFlowDao jmgoSupplierSellHeightTransferAccountDao;


    @Override
    public WriteOffReturnObject updateSupplierSellHeightTransferAccount(String prefix, GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, long amount, String salesOrderNo, Date transactionTime) {
        try {
            logger.info("#traceId={}# [IN][updateSupplierSellHeightTransferAccount] params: projectId={}, amount={}",
                    rpcHeader.getTraceId(), projectId, amount);
            WriteOffReturnObject writeOffReturnObject = new WriteOffReturnObject();
            int maxRetryTimes = 6;
            String flowNo = null;
            boolean updateSuccess = false;
            long originalAmount = 0L;
            while (maxRetryTimes-- > 0) {
                //查找账户
                SupplierSellHeightAccount account = supplierSellHeightTransferAccountDao.getAccount(prefix, projectId);
                //改变余额
                originalAmount = account.getTotalAmount();
                account.setTotalAmount(originalAmount + amount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), amount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户
                int update = supplierSellHeightTransferAccountDao.update(prefix, account);
                //插入记录
                if (update == 1) {
                    // 更新成功，插入流水,跳出循环
                    flowNo = supplierSellHeightTransferRecordService.insertRecord(prefix, rpcHeader, currencyCode, originalAmount, amount, originalAmount + amount, transactionTime, account.getSupplierId(), account.getSupplierName(), projectId, account.getProjectName());
                    updateSuccess = true;
                    break;
                }
            }
            if (!updateSuccess) {
                //更新失败 账户余额不足
                logger.info("sell high account balance NOT enough");
                writeOffReturnObject.setReturnCode(SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode());
            } else {
                //返回流水号
                logger.info("#traceId={}# [OUT]: update sell high account success. amount={} flowNo={}", rpcHeader.getTraceId(), amount, flowNo);
                writeOffReturnObject.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                writeOffReturnObject.setFlowNo(flowNo);
                writeOffReturnObject.setAmountBeforeTrans(originalAmount);
                writeOffReturnObject.setAmountAfterTrans(originalAmount + amount);
            }
            return writeOffReturnObject;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public WriteOffReturnObject updateSupplierSellHighAccountOnJmgo(String prefix, GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, long distributorId, String distributorName, long amount, String salesOrderNo, Date transactionTime, String remark) {
        try {
            logger.info("#traceId={}# [IN][updateSupplierSellHeightTransferAccount] params: projectId={}, amount={}",
                    rpcHeader.getTraceId(), projectId, amount);
            WriteOffReturnObject writeOffReturnObject = new WriteOffReturnObject();
            int maxRetryTimes = 6;
            String flowNo = null;
            boolean updateSuccess = false;
            long originalAmount = 0L;
            while (maxRetryTimes-- > 0) {
                //查找账户
                SupplierSellHeightAccount account = supplierSellHeightTransferAccountDao.getAccount(prefix, projectId);
                //改变余额
                originalAmount = account.getTotalAmount();
                long amountAfterTransaction = originalAmount + amount;
                if (amountAfterTransaction < 0) {
                    continue;
                }
                account.setTotalAmount(amountAfterTransaction);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), amount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户
                int update = supplierSellHeightTransferAccountDao.update(prefix, account);
                //插入记录
                if (update == 1) {
                    // 更新成功，插入流水,跳出循环
                    flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.PAYMENT_SUPPLIER_SELL_HIGH_FLOW);
                    JmgoSupplierSellHighFlow jmgoSupplierSellHighFlow = new JmgoSupplierSellHighFlow();
                    jmgoSupplierSellHighFlow.setCurrencyCode(currencyCode);
                    jmgoSupplierSellHighFlow.setAmountBeforeTransaction(originalAmount);
                    jmgoSupplierSellHighFlow.setTransactionAmount(amount);
                    jmgoSupplierSellHighFlow.setAmountAfterTransaction(originalAmount + amount);
                    jmgoSupplierSellHighFlow.setTransactionTime(transactionTime);
                    jmgoSupplierSellHighFlow.setProjectId(projectId);
                    jmgoSupplierSellHighFlow.setProjectName(account.getProjectName());
                    jmgoSupplierSellHighFlow.setDistributorId(distributorId);
                    jmgoSupplierSellHighFlow.setDistributorName(distributorName);
                    jmgoSupplierSellHighFlow.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                    jmgoSupplierSellHighFlow.setCreatedByName(rpcHeader.getUsername());
                    jmgoSupplierSellHighFlow.setFlowNo(flowNo);
                    jmgoSupplierSellHighFlow.setFlowType(amount > 0 ? FlowTypeEnum.IN.getType() : FlowTypeEnum.OUT.getType());
                    jmgoSupplierSellHighFlow.setRemark(remark);
                    jmgoSupplierSellHeightTransferAccountDao.insertFlow(prefix, jmgoSupplierSellHighFlow);
                    updateSuccess = true;
                    break;
                }
            }
            if (!updateSuccess) {
                //更新失败 账户余额不足
                logger.info("sell high account balance NOT enough");
                writeOffReturnObject.setReturnCode(SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode());
            } else {
                //返回流水号
                logger.info("#traceId={}# [OUT]: update sell high account success. amount={} flowNo={}", rpcHeader.getTraceId(), amount, flowNo);
                writeOffReturnObject.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                writeOffReturnObject.setFlowNo(flowNo);
                writeOffReturnObject.setAmountBeforeTrans(originalAmount);
                writeOffReturnObject.setAmountAfterTrans(originalAmount + amount);
            }
            return writeOffReturnObject;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}