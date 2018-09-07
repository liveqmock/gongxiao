package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponBufferToDistributorDao;
import com.yhglobal.gongxiao.payment.model.SupplierCouponBufferToDistributor;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponTransferRecordService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponTransferToDistributorFlowService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 供应商返利过账ad账户 service
 *
 * @author: 葛灿
 */
@Service
public class SupplierCouponBufferToDistributorServiceImpl implements SupplierCouponBufferToDistributorService {

    private static Logger logger = LoggerFactory.getLogger(SupplierCouponBufferToDistributorServiceImpl.class);

    @Autowired
    SupplierCouponBufferToDistributorDao supplierCouponBufferToDistributorDao;

    @Autowired
    SupplierCouponTransferToDistributorFlowService supplierCouponTransferToDistributorFlowService;

    @Autowired
    SupplierCouponTransferRecordService supplierCouponTransferRecordService;

    @Override
    public String updateCouponBufferToDistributorAccount(String prefix, GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, Long distributorId, String distributorName, long couponAmount, String purchaseOrderNo, Date transactionTime, String remark, String sourceFlowNo) {
        try {
            logger.info("#traceId={}# [IN][updateCouponBufferToDistributorAccount] params: projectId={}, couponAmount={}, purchaseOrderNo={}, transactionTime={}, remark={}, sourceFlowNo={}",
                    rpcHeader.getTraceId(), projectId, couponAmount, purchaseOrderNo, transactionTime, remark, sourceFlowNo);
            int maxRetryTimes = 6;
            String flowNo = null;
            while (maxRetryTimes-- > 0) {
                //查找账户
                SupplierCouponBufferToDistributor account = supplierCouponBufferToDistributorDao.getAccount(prefix, projectId);
                //改变余额
                Long originalAmount = account.getTotalAmount();
                account.setTotalAmount(originalAmount + couponAmount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), couponAmount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户（基于乐观锁，失败自动重试）
                int update = supplierCouponBufferToDistributorDao.update(prefix, account);
                if (update == 1) {
                    //更新成功，插入流水,跳出循环
                    flowNo = supplierCouponTransferToDistributorFlowService.insertFlow(prefix, rpcHeader,
                            1L, account.getSupplierName(),
                            projectId, account.getProjectName(),
                            distributorId, distributorName,
                            currencyCode, originalAmount, couponAmount,
                            transactionTime, purchaseOrderNo, sourceFlowNo);
                    break;
                }
            }
            //如果更新失败，抛出异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update coupon buffer to distributor account");
                throw new RuntimeException("FAILED to update coupon buffer to distributor account");
            }
            //返回流水号
            logger.info("#traceId={}# [OUT]: update supplier coupon buffer to distributor success. amount={} flowNo={}", rpcHeader.getTraceId(), couponAmount, flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
