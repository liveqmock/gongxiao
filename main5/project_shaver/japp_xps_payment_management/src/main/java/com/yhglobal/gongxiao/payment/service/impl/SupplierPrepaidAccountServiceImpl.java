package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.dao.SupplierPrepaidAccountDao;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidAccount;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidTransferRecordService;
import com.yhglobal.gongxiao.utils.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 供应商代垫上账账户 service
 *
 * @author: 葛灿
 */
@Service
public class SupplierPrepaidAccountServiceImpl implements SupplierPrepaidAccountService {

    private static Logger logger = LoggerFactory.getLogger(SupplierPrepaidAccountServiceImpl.class);

    @Autowired
    SupplierPrepaidAccountDao supplierPrepaidAccountDao;

    @Autowired
    SupplierPrepaidTransferRecordService supplierPrepaidTransferRecordService;

    @Override
    public String updateSupplierPrepaidAccount(String prefix, GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, long amount,
                                               Date transactionTime, String remark, String purchaseOrderNo, String transferType) {
        try {
            logger.info("#traceId={}# [IN][updateSupplierPrepaidAccount] params: currencyCode={}, projectId={}, couponAmount={}, transactionTime={}, remark={}",
                    rpcHeader.getTraceId(), currencyCode, projectId, amount, transactionTime, remark);
            int maxRetryTimes = 6;
            String flowNo = null;
            while (maxRetryTimes-- > 0) {
                // 查找账户
                SupplierPrepaidAccount account = supplierPrepaidAccountDao.getAccount(prefix, projectId);
                // 改变余额
                Long originalAmount = account.getTotalAmount();
                account.setTotalAmount(originalAmount + amount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), amount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户（基于乐观锁，失败自动重试）
                int update = supplierPrepaidAccountDao.update(prefix, account);
                if (update == 1) {
                    //更新成功，插入流水,跳出循环
                    flowNo = supplierPrepaidTransferRecordService.insertRecord(prefix, rpcHeader,
                            account.getSupplierId(), account.getSupplierName(), projectId, account.getProjectName(),
                            currencyCode, originalAmount, amount, transactionTime, purchaseOrderNo, transferType, remark);
                    break;
                }
            }
            //如果更新失败，抛出异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update supplier prepaid account");
                throw new RuntimeException("FAILED to update supplier prepaid account");
            }
            //返回流水号
            logger.info("#traceId={}# [OUT]: update supplier prepaid account success. amount={} flowNo={}", rpcHeader.getTraceId(), amount, flowNo);
            return flowNo;

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

}
