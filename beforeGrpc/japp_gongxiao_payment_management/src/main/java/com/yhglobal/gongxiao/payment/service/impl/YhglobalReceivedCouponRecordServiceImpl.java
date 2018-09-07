package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedCouponRecordDao;
import com.yhglobal.gongxiao.payment.flow.FlowTypeEnum;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponRecord;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: 葛灿
 */
@Service
public class YhglobalReceivedCouponRecordServiceImpl implements YhglobalReceivedCouponRecordService {
    private static Logger logger = LoggerFactory.getLogger(YhglobalReceivedCouponRecordServiceImpl.class);

    @Autowired
    YhglobalReceivedCouponRecordDao yhglobalReceivedCouponRecordDao;

    @Override
    public String insertRecord(RpcHeader rpcHeader, long supplierId, String supplierName, long projectId, String projectName, String currencyCode, long amountBeforeTrans, long receivedAmount, Date receivedTime, String purchaseOrderNo, String sourceFlowNo, String remark) {
        try {
            logger.info("#traceId={}# [IN][insertRecord] params: supplierId={}, supplierName={}, projectId={}, projectName={}, currencyCode={}, amountBeforeTrans={}, receivedAmount={}, receivedTime={}, purchaseOrderNo={}, sourceFlowNo={}",
                    rpcHeader.traceId, supplierId, supplierName, projectId, projectName, currencyCode, amountBeforeTrans, receivedAmount, receivedTime, purchaseOrderNo, sourceFlowNo);
            YhglobalReceivedCouponRecord record = new YhglobalReceivedCouponRecord();
            String flowNo = DateTimeIdGenerator.nextId(BizNumberType.PAYMENT_YHGLOBAL_COUPON_RECEIVED_FLOW);
            record.setFlowNo(flowNo);
            record.setCurrencyCode(currencyCode);
            record.setRecordType(receivedAmount > 0 ? FlowTypeEnum.IN.getType() : FlowTypeEnum.OUT.getType());
            record.setAmountBeforeTransaction(amountBeforeTrans);
            record.setTransactionAmount(receivedAmount);
            record.setAmountAfterTransaction(amountBeforeTrans + receivedAmount);
            record.setTransferTime(receivedTime);
            record.setSupplierId(supplierId);
            record.setSupplierName(supplierName);
            record.setProjectId(projectId);
            record.setProjectName(projectName);
            record.setPurchaseOrderNo(purchaseOrderNo);
            record.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            record.setCreatedByName(rpcHeader.getUsername());
            record.setCreateTime(new Date());
            record.setSourceFlowNo(sourceFlowNo);
            record.setRemark(remark);
            yhglobalReceivedCouponRecordDao.insert(record);
            logger.info("#traceId={}# [OUT]: insert record success. flowNo={}", rpcHeader.traceId, flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
