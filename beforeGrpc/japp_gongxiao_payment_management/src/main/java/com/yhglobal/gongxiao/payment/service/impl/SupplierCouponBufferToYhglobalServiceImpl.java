package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponBufferToYhglobalDao;
import com.yhglobal.gongxiao.payment.model.SupplierCouponBufferToYhglobal;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponTransferRecordService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponTransferToYhglobalFlowService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 供应商返利过账YH账户  service
 *
 * @author: 葛灿
 */
@Service
public class SupplierCouponBufferToYhglobalServiceImpl implements SupplierCouponBufferToYhglobalService {

    private static Logger logger = LoggerFactory.getLogger(SupplierCouponBufferToYhglobalServiceImpl.class);

    @Autowired
    SupplierCouponBufferToYhglobalDao supplierCouponBufferToYhglobalDao;

    @Autowired
    SupplierCouponTransferToYhglobalFlowService supplierCouponTransferToYhglobalFlowService;

    @Autowired
    SupplierCouponTransferRecordService supplierCouponTransferRecordService;


    @Override
    public String updateCouponBufferToYhglobal(RpcHeader rpcHeader, String currencyCode, long projectId, long couponAmount, String purchaseOrderNo, Date transactionTime, String remark, String sourceFlowNo) {
        try {
            logger.info("#traceId={}# [IN][updateCouponBufferToYhglobal] params: supplierId={}, projectId={}, couponAmount={}, purchaseOrderNo={}, transactionTime={}, remark={}, sourceFlowNo={}",
                    rpcHeader.traceId, projectId, couponAmount, purchaseOrderNo, transactionTime, remark, sourceFlowNo);
            int maxRetryTimes = 6;
            String flowNo = null;
            while (maxRetryTimes-- > 0) {
                //查找账户
                SupplierCouponBufferToYhglobal account = supplierCouponBufferToYhglobalDao.getAccount(projectId);
                //改变余额
                Long orginalAmount = account.getTotalAmount();
                account.setTotalAmount(orginalAmount + couponAmount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), couponAmount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户
                int update = supplierCouponBufferToYhglobalDao.update(account);
                if (update == 1) {
                    //更新成功，插入流水,跳出循环
                    flowNo = supplierCouponTransferToYhglobalFlowService.insertFlow(rpcHeader,
                            account.getSupplierId(), account.getSupplierName(),
                            projectId, account.getProjectName(),
                            currencyCode, orginalAmount, couponAmount,
                            transactionTime, purchaseOrderNo, sourceFlowNo);
                    break;
                }
            }
            //如果更新失败，抛出异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update coupon buffer to yhglobal account");
                throw new RuntimeException("FAILED to update coupon buffer to yhglobal account");
            }
            //返回流水号
            logger.info("#traceId={}# [OUT]: update supplier coupon buffer to yhglobal success. amount={} flowNo={}", rpcHeader.traceId, couponAmount, flowNo);
            return flowNo;

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
