package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.model.FlowTypeEnum;

import com.yhglobal.gongxiao.payment.project.dao.supplier.SupplierCouponTransferRecordDao;
import com.yhglobal.gongxiao.payment.project.model.SupplierCouponFlow;
import com.yhglobal.gongxiao.payment.service.SupplierCouponTransferRecordService;
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
public class SupplierCouponTransferRecordServiceImpl implements SupplierCouponTransferRecordService {
    private static Logger logger = LoggerFactory.getLogger(SupplierCouponTransferRecordServiceImpl.class);

    @Autowired
    SupplierCouponTransferRecordDao supplierCouponTransferRecordDao;

    @Override
    public String insertRecord(String prefix, GongxiaoRpc.RpcHeader rpcHeader, Long supplierId, String supplierName,
                               long projectId, String projectName, String currencyCode, BigDecimal amountBeforeTransfer,
                               BigDecimal transferAmount, Date transferTime, String purchaseOrderNo, String transferAccountType, String remark) {
        try {
            logger.info("#traceId={}# [IN][insertRecord] params: supplierId={} supplierName={} projectId={} projectName={} currencyCode={} amountBeforeTransfer={} transferAmount={} transferTime={} purchaseOrderNo={} transferAccountType={}",
                    rpcHeader.getTraceId(), supplierId, supplierName, projectId, projectName, currencyCode, amountBeforeTransfer, transferAmount, transferTime, purchaseOrderNo, transferAccountType);
            SupplierCouponFlow record = new SupplierCouponFlow();
            String flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.PAYMENT_SUPPLIER_COUPON_FLOW);
            record.setFlowNo(flowNo);
            record.setCurrencyCode(currencyCode);
            //record.setFlowType(transferAmount > 0 ? FlowTypeEnum.IN.getType() : FlowTypeEnum.OUT.getType());
            record.setFlowType(transferAmount.compareTo(new BigDecimal("0"))==1 ? FlowTypeEnum.IN.getType() : FlowTypeEnum.OUT.getType());
            record.setAmountBeforeTransaction(amountBeforeTransfer);
            record.setTransactionAmount(transferAmount);
            record.setAmountAfterTransaction(amountBeforeTransfer.add(transferAmount));
            record.setTransactionTime(transferTime);
            record.setSupplierId(supplierId);
            record.setSupplierName(supplierName);
            record.setProjectId(projectId);
            record.setProjectName(projectName);
            record.setBusinessOrderNo(purchaseOrderNo);
            record.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            record.setCreatedByName(rpcHeader.getUsername());
            record.setCreateTime(new Date());
            record.setRemark(remark);
            supplierCouponTransferRecordDao.insert(prefix, record);
            logger.info("#traceId={}# [OUT]: insert coupon record success. flowNo={}", rpcHeader.getTraceId(), flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


}
