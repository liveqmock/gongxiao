package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedPrepaidRecordDao;
import com.yhglobal.gongxiao.payment.model.FlowTypeEnum;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedPrepaidRecord;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedPrepaidRecordService;
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
public class YhglobalReceivedPrepaidRecordServiceImpl implements YhglobalReceivedPrepaidRecordService {
    private static Logger logger = LoggerFactory.getLogger(YhglobalReceivedPrepaidRecordServiceImpl.class);

    @Autowired
    YhglobalReceivedPrepaidRecordDao yhglobalReceivedPrepaidRecordDao;

    @Override
    public String insertRecord(String prefix, GongxiaoRpc.RpcHeader rpcHeader, long supplierId, String supplierName,
                               long projectId, String projectName, String currencyCode, BigDecimal amountBeforeTrans,
                               BigDecimal receivedAmount, Date receivedTime, String purchaseOrderNo, String sourceFlowNo) {
        try {
            logger.info("#traceId={}# [IN][insertRecord] params: supplierId={}, supplierName={}, projectId={}, projectName={}, currencyCode={}, amountBeforeTrans={}, receivedAmount={}, receivedTime={}, purchaseOrderNo={}, sourceFlowNo={}",
                    rpcHeader.getTraceId(), supplierId, supplierName, projectId, projectName, currencyCode, amountBeforeTrans, receivedAmount, receivedTime, purchaseOrderNo, sourceFlowNo);
            YhglobalReceivedPrepaidRecord record = new YhglobalReceivedPrepaidRecord();
            String flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.PAYMENT_YHGLOBAL_PREPAID_RECEIVED_FLOW);
            record.setFlowNo(flowNo);
            record.setCurrencyCode(currencyCode);
            record.setRecordType(receivedAmount.compareTo(new BigDecimal("0"))==1 ? FlowTypeEnum.IN.getType() : FlowTypeEnum.OUT.getType());
            record.setAmountBeforeTransaction(amountBeforeTrans);
            record.setTransactionAmount(receivedAmount);
            record.setAmountAfterTransaction(amountBeforeTrans.add(receivedAmount));
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
            yhglobalReceivedPrepaidRecordDao.insert(prefix, record);
            logger.info("#traceId={}# [OUT]: insert record success. flowNo={}", rpcHeader.getTraceId(), flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
