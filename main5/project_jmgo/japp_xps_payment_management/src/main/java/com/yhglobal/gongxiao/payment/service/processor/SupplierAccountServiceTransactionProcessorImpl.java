package com.yhglobal.gongxiao.payment.service.processor;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.payment.dto.PurchaseFlowCollections;
import com.yhglobal.gongxiao.payment.service.SupplierCouponAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToYhglobalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author: 葛灿
 */
@Service
public class SupplierAccountServiceTransactionProcessorImpl implements SupplierAccountServiceTransactionProcessor {

    private static Logger logger = LoggerFactory.getLogger(SupplierAccountServiceTransactionProcessorImpl.class);
    @Autowired
    SupplierCouponAccountService supplierCouponAccountService;

    @Autowired
    SupplierPrepaidAccountService supplierPrepaidAccountService;

    @Autowired
    SupplierCouponBufferToYhglobalService supplierCouponBufferToYhglobalService;

    @Autowired
    SupplierPrepaidBufferToYhglobalService supplierPrepaidBufferToYhglobalService;

    @Autowired
    SupplierCouponBufferToDistributorService supplierCouponBufferToDistributorService;

    @Autowired
    SupplierPrepaidBufferToDistributorService supplierPrepaidBufferToDistributorService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpcResult<PurchaseFlowCollections> payForPurchase(String prefix,
                                                             GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, long couponToYh, long couponToAd, long prepaidToYh, long prepaidToAd, String purchaseOrderNo, Date transactionTime) {
        logger.info("#traceId={}# [IN][payForPurchase] params: currencyCode={}, projectId={}, couponToYh={}, couponToAd={}, prepaidToYh={}, prepaidToAd={}, purchaseOrderNo={}, transactionTime={}",
                rpcHeader.getTraceId(), currencyCode, projectId, couponToYh, couponToAd, prepaidToYh, prepaidToAd, purchaseOrderNo, transactionTime);
        try {
            PurchaseFlowCollections purchaseFlowCollections = new PurchaseFlowCollections();
            if (couponToAd > 0) {
                try {
                    //上账账户扣款 插入记录
                    String postFlowNo = supplierCouponAccountService.updateSupplierCouponAccount(prefix, rpcHeader, currencyCode, projectId, -couponToAd, transactionTime, null, purchaseOrderNo, "Yhglobal");
                    //转账到AD过账账户
                    String transferFlowNo = supplierCouponBufferToDistributorService.updateCouponBufferToDistributorAccount(prefix, rpcHeader, currencyCode, projectId, null, null, couponToAd, purchaseOrderNo, transactionTime, null, postFlowNo);
                    purchaseFlowCollections.setCouponFlowNoToDistributor(transferFlowNo);
                } catch (DataIntegrityViolationException e) {
                    //返利上账账户余额不足
                    logger.error("#traceId={}# [OUT]: pay for order FAILED.", rpcHeader.getTraceId());
                    return RpcResult.newFailureResult(ErrorCode.SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
                }
            }
            if (couponToYh > 0) {
                try {
                    //上账账户冻结金额
                    String flowNo = supplierCouponAccountService.updateSupplierCouponAccount(prefix, rpcHeader, currencyCode, projectId, -couponToYh, transactionTime, null, purchaseOrderNo, "Yhglobal");
                    //转账到越海缓冲账户
                    String bufferFlowNo = supplierCouponBufferToYhglobalService.updateCouponBufferToYhglobal(prefix, rpcHeader, currencyCode, projectId, couponToYh, purchaseOrderNo, transactionTime, null, flowNo);
                    purchaseFlowCollections.setCouponFlowNoToYhglobal(bufferFlowNo);
                } catch (DataIntegrityViolationException e) {
                    //返利上账账户余额不足
                    logger.error("#traceId={}# [OUT]: pay for order FAILED.", rpcHeader.getTraceId());
                    return RpcResult.newFailureResult(ErrorCode.SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
                }
            }
            if (prepaidToAd > 0) {
                try {
                    //上账账户扣款
                    String postFlowNo = supplierPrepaidAccountService.updateSupplierPrepaidAccount(prefix, rpcHeader, currencyCode, projectId, -prepaidToAd, transactionTime, null, purchaseOrderNo, "Distributor");
                    //转账到越海缓冲账户
                    String flowNo = supplierPrepaidBufferToDistributorService.updatePrepaidBufferToDistributorAccount(prefix, rpcHeader, currencyCode, projectId, null, null, prepaidToAd, purchaseOrderNo, transactionTime, null, postFlowNo);
                    purchaseFlowCollections.setPrepaidFlowNoToDistributor(flowNo);
                } catch (DataIntegrityViolationException e) {
                    //代垫上账账户余额不足
                    logger.error("#traceId={}# [OUT]: pay for order FAILED.", rpcHeader.getTraceId());
                    return RpcResult.newFailureResult(ErrorCode.SUPPLIER_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.SUPPLIER_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
                }
            }
            if (prepaidToYh > 0) {
                try {
                    //上账账户冻结金额
                    String flowNo = supplierPrepaidAccountService.updateSupplierPrepaidAccount(prefix, rpcHeader, currencyCode, projectId, -prepaidToYh, transactionTime, null, purchaseOrderNo, "Distributor");
                    //转账到越海缓冲账户
                    String bufferFlowNo = supplierPrepaidBufferToYhglobalService.updatePrepaidBufferToYhglobal(prefix, rpcHeader, currencyCode, projectId, prepaidToYh, purchaseOrderNo, transactionTime, null, flowNo);
                    purchaseFlowCollections.setPrepaidFlowNoToYhglobal(bufferFlowNo);
                } catch (DataIntegrityViolationException e) {
                    //代垫上账账户余额不足
                    logger.error("#traceId={}# [OUT]: pay for order FAILED.", rpcHeader.getTraceId());
                    return RpcResult.newFailureResult(ErrorCode.SUPPLIER_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.SUPPLIER_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
                }
            }
            logger.info("#traceId={}# [OUT]: pay for purchase success.", rpcHeader.getTraceId());
            return RpcResult.newSuccessResult(purchaseFlowCollections);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
