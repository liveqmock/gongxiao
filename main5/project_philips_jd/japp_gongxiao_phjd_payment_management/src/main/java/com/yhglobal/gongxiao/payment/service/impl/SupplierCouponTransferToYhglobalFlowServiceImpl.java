package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponTransferToYhglobalFlowDao;
import com.yhglobal.gongxiao.payment.model.FlowTypeEnum;
import com.yhglobal.gongxiao.payment.model.SupplierCouponBufferToYhglobalFlow;
import com.yhglobal.gongxiao.payment.service.SupplierCouponTransferToYhglobalFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: 葛灿
 */
@Service
public class SupplierCouponTransferToYhglobalFlowServiceImpl implements SupplierCouponTransferToYhglobalFlowService {

    private static Logger logger = LoggerFactory.getLogger(SupplierCouponTransferToYhglobalFlowServiceImpl.class);
    @Autowired
    SupplierCouponTransferToYhglobalFlowDao supplierCouponTransferToYhglobalFlowDao;

    @Override
    public String insertFlow(String prefix,GongxiaoRpc.RpcHeader rpcHeader, long supplierId, String supplierName,
                             long projectId, String projectName, String currencyCode, BigDecimal originalAmount,
                             BigDecimal transferAmount, Date transferTime, String purchaseOrderNo, String sourceFlowNo) {
        try {
            logger.info("#traceId={}# [IN][insertFlow] params: supplierId={}, supplierName={}, projectId={}, projectName={}, currencyCode={}, originalAmount={}, transferAmount={}, transferTime={}, purchaseOrderNo={}, sourceFlowNo={}",
                    rpcHeader.getTraceId(), supplierId, supplierName, projectId, projectName, currencyCode, originalAmount, transferAmount, transferTime, purchaseOrderNo, sourceFlowNo);
            SupplierCouponBufferToYhglobalFlow flow = new SupplierCouponBufferToYhglobalFlow();
            String flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.PAYMENT_YHGLOBAL_COUPON_BUFFER_FLOW);
            flow.setFlowNo(flowNo);
            //flow.setFlowType(transferAmount > 0 ? FlowTypeEnum.IN.getType() : FlowTypeEnum.OUT.getType());
            flow.setFlowType(transferAmount.compareTo(new BigDecimal("0"))==1 ? FlowTypeEnum.IN.getType() : FlowTypeEnum.OUT.getType());
            flow.setCurrencyCode(currencyCode);
            flow.setAmountBeforeTransaction(originalAmount);
            flow.setTransactionAmount(transferAmount);
            flow.setAmountAfterTransaction(transferAmount.add(originalAmount));
            flow.setTransactionTime(transferTime);
            flow.setSupplierId(supplierId);
            flow.setSupplierName(supplierName);
            flow.setProjectId(projectId);
            flow.setProjectName(projectName);
            flow.setBusinessOrderNo(purchaseOrderNo);
            flow.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            flow.setCreatedByName(rpcHeader.getUsername());
            flow.setCreateTime(new Date());
            flow.setSourceFlowNo(sourceFlowNo);
            int insert = supplierCouponTransferToYhglobalFlowDao.insert(prefix,flow);
            logger.info("#traceId={}# [OUT]: insert coupon record success. flowNo={}", rpcHeader.getTraceId(), flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
