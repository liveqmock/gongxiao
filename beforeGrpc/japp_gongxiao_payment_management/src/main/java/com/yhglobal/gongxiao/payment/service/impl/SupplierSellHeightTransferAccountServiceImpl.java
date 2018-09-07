package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.payment.dao.SupplierSellHeightTransferAccountDao;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidBufferToYhglobal;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightAccount;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightTransferRecord;
import com.yhglobal.gongxiao.payment.service.SupplierSellHeightTransferAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierSellHeightTransferRecordService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Override
    public SupplierSellHeightAccount getSupplierPrepaidBufferToYhglobal(RpcHeader rpcHeader, String currencyCode, long projectId) {
        return supplierSellHeightTransferAccountDao.getAccount(projectId);
    }

    @Override
    public String updateSupplierSellHeightTransferAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long amount, String salesOrderNo, Date transactionTime) {
        try {
            logger.info("#traceId={}# [IN][updateSupplierSellHeightTransferAccount] params: projectId={}, amount={}",
                    rpcHeader.traceId, projectId, amount);
            int maxRetryTimes = 6;
            String flowNo = null;
            while (maxRetryTimes-- > 0) {
                //查找账户
                SupplierSellHeightAccount account = supplierSellHeightTransferAccountDao.getAccount(projectId);
                //改变余额
                Long originalAmount = account.getTotalAmount();
                account.setTotalAmount(originalAmount + amount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), amount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户
                int update = supplierSellHeightTransferAccountDao.update(account);
                //插入记录
                if (update == 1) {
                    // 更新成功，插入流水,跳出循环
                    flowNo = supplierSellHeightTransferRecordService.insertRecord(rpcHeader, currencyCode, originalAmount, amount,
                            originalAmount + amount, transactionTime,
                            account.getSupplierId(), account.getSupplierName(),
                            account.getProjectId(), account.getProjectName());
                    break;
                }
            }
            //如果更新失败，抛出异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update sell high account");
                throw new RuntimeException("FAILED to update sell high account");
            }
            //返回流水号
            logger.info("#traceId={}# [OUT]: update sell high account success. amount={} flowNo={}", rpcHeader.traceId, amount, flowNo);
            return flowNo;

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public String writeoffSellHeightAmount(RpcHeader rpcHeader, String currencyCode, long projectId, long amount, Date transactionTime) {
        return this.updateSupplierSellHeightTransferAccount(rpcHeader, currencyCode, projectId, -amount, null, transactionTime);
    }
}
