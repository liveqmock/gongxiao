package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponAccountDao;
import com.yhglobal.gongxiao.payment.model.SupplierCouponAccount;
import com.yhglobal.gongxiao.payment.service.SupplierCouponAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponTransferRecordService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.utils.BigDecimalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应商返利上账账户 service
 *
 * @author: 葛灿
 */
@Service
public class SupplierCouponAccountServiceImpl implements SupplierCouponAccountService {

    private static Logger logger = LoggerFactory.getLogger(SupplierCouponAccountServiceImpl.class);

    @Autowired
    SupplierCouponAccountDao supplierCouponAccountDao;


    @Autowired
    SupplierCouponTransferRecordService supplierCouponTransferRecordService;


    @Override
    public String updateSupplierCouponAccount(String prefix, GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, long amount,
                                              Date transactionTime, String remark, String purchaseOrderNo, String transferType) {
        try {
            logger.info("#traceId={}# [IN][updateSupplierCouponAccount] params: currencyCode={}, projectId={}, couponAmount={}, transactionTime={}, remark={}",
                    rpcHeader.getTraceId(), currencyCode, projectId, amount, transactionTime, remark);
            int maxRetryTimes = 6;
            String flowNo = null;
            while (maxRetryTimes-- > 0) {
                // 查找账户
                SupplierCouponAccount account = supplierCouponAccountDao.getAccount(prefix, projectId);
                // 改变余额
                BigDecimal originalAmount = account.getTotalAmount();
                //account.setTotalAmount(originalAmount + amount);
                BigDecimal amountNew = BigDecimalUtil.divideHundred(amount);
                account.setTotalAmount(originalAmount.add(amountNew));
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), amount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户（基于乐观锁，失败自动重试）
                int update = supplierCouponAccountDao.update(prefix, account);
                if (update == 1) {
                    //更新成功，插入流水,跳出循环
                    flowNo = supplierCouponTransferRecordService.insertRecord(prefix, rpcHeader,
                            account.getSupplierId(), account.getSupplierName(), projectId, account.getProjectName(),
                            currencyCode, originalAmount, amountNew, transactionTime, purchaseOrderNo, transferType, remark);
                    break;
                }
            }
            //如果更新失败，抛出异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update supplier coupon account");
                throw new RuntimeException("FAILED to update supplier coupon account");
            }
            //返回流水号
            logger.info("#traceId={}# [OUT]: update supplier coupon account success. amount={} flowNo={}", rpcHeader.getTraceId(), amount, flowNo);
            return flowNo;

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

}
