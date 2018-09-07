package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponTransferRecordDao;
import com.yhglobal.gongxiao.payment.flow.FlowTypeEnum;
import com.yhglobal.gongxiao.payment.model.SupplierCouponTransferRecord;
import com.yhglobal.gongxiao.payment.service.SupplierCouponTransferRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String insertRecord(RpcHeader rpcHeader, Long supplierId, String supplierName, long projectId, String projectName, String currencyCode, long amountBeforeTransfer, long transferAmount, Date transferTime, String purchaseOrderNo, String transferAccountType,String remark) {
        try {
            logger.info("#traceId={}# [IN][insertRecord] params: supplierId={} supplierName={} projectId={} projectName={} currencyCode={} amountBeforeTransfer={} transferAmount={} transferTime={} purchaseOrderNo={} transferAccountType={}",
                    rpcHeader.traceId, supplierId, supplierName, projectId, projectName, currencyCode, amountBeforeTransfer, transferAmount, transferTime, purchaseOrderNo, transferAccountType);
            SupplierCouponTransferRecord record = new SupplierCouponTransferRecord();
            String flowNo = DateTimeIdGenerator.nextId(BizNumberType.PAYMENT_SUPPLIER_COUPON_FLOW);
            record.setFlowNo(flowNo);
            record.setCurrencyCode(currencyCode);
            record.setRecordType(transferAmount > 0 ? FlowTypeEnum.IN.getType() : FlowTypeEnum.OUT.getType());
            record.setAmountBeforeTransaction(amountBeforeTransfer);
            record.setTransactionAmount(transferAmount);
            record.setAmountAfterTransaction(amountBeforeTransfer + transferAmount);
            record.setTransferTime(transferTime);
            record.setSupplierId(supplierId);
            record.setSupplierName(supplierName);
            record.setProjectId(projectId);
            record.setProjectName(projectName);
            record.setPurchaseOrderNo(purchaseOrderNo);
            record.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            record.setCreatedByName(rpcHeader.getUsername());
            record.setCreateTime(new Date());
            record.setRemark(remark);
            supplierCouponTransferRecordDao.insert(record);
            logger.info("#traceId={}# [OUT]: insert coupon record success. flowNo={}", rpcHeader.traceId, flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


}
