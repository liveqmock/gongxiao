package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.payment.dao.SupplierSellHeightTransferRecordDao;
import com.yhglobal.gongxiao.payment.flow.FlowTypeEnum;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightTransferRecord;
import com.yhglobal.gongxiao.payment.service.SupplierSellHeightTransferRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: 葛灿
 */
@Service
public class SupplierSellHeightTransferRecordServiceImpl implements SupplierSellHeightTransferRecordService {

    private static Logger logger = LoggerFactory.getLogger(SupplierSellHeightTransferRecordServiceImpl.class);
    @Autowired
    SupplierSellHeightTransferRecordDao supplierSellHeightTransferRecordDao;

    @Override
    public String insertRecord(RpcHeader rpcHeader, String currencyCode, long amountBeforeTransfer, long postingAmount, long amountAfterTransfer, Date transferTime, long supplierId, String supplierName, long projectId, String projectName) {
        try {
            logger.info("#traceId={}# [IN][insertRecord] params: currencyCode={}, amountBeforeTransfer={}, postingAmount={}, amountAfterTransfer={}, transferTime={}, supplierId={}, supplierName={}",
                    rpcHeader.traceId, currencyCode, amountBeforeTransfer, postingAmount, amountAfterTransfer, transferTime, supplierId, supplierName);
            SupplierSellHeightTransferRecord record = new SupplierSellHeightTransferRecord();
            String flowNo = DateTimeIdGenerator.nextId(BizNumberType.PAYMENT_SUPPLIER_SELL_HIGH_FLOW);
            record.setFlowNo(flowNo);
            record.setCurrencyCode(currencyCode);
            record.setRecordType(postingAmount > 0 ? FlowTypeEnum.IN.getType() : FlowTypeEnum.OUT.getType());
            record.setAmountBeforeTransaction(amountBeforeTransfer);
            record.setTransactionAmount(postingAmount);
            record.setAmountAfterTransaction(amountAfterTransfer);
            record.setTransferTime(transferTime);
            record.setSupplierId(supplierId);
            record.setSupplierName(supplierName);
            record.setCreateTime(new Date());
            record.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            record.setCreatedByName(rpcHeader.getUsername());
            record.setProjectId(projectId);
            record.setProjectName(projectName);
            int insert = supplierSellHeightTransferRecordDao.insert(record);
            logger.info("#traceId={}# [OUT]: insert sell high record success. flowNo={}", rpcHeader.traceId, flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
