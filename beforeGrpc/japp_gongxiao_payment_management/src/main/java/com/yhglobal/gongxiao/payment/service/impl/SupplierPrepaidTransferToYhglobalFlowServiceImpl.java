package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.payment.dao.SupplierPrepaidTransferToYhglobalFlowDao;
import com.yhglobal.gongxiao.payment.flow.FlowTypeEnum;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidTransferToYhglobalFlow;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidTransferToYhglobalFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: 葛灿
 */
@Service
public class SupplierPrepaidTransferToYhglobalFlowServiceImpl implements SupplierPrepaidTransferToYhglobalFlowService {
    private static Logger logger = LoggerFactory.getLogger(SupplierPrepaidTransferToYhglobalFlowServiceImpl.class);

    @Autowired
    SupplierPrepaidTransferToYhglobalFlowDao supplierPrepaidTransferToYhglobalFlowDao;

    @Override
    public String insertFlow(RpcHeader rpcHeader, long supplierId, String supplierName, long projectId, String projectName, String currencyCode, long originalAmount, long transferAmount, Date transferTime, String purchaseOrderNo, String sourceFlowNo) {
        try {
            logger.info("#traceId={}# [IN][insertFlow] params: supplierId={}, supplierName={}, projectId={}, projectName={}, currencyCode={}, originalAmount={}, transferAmount={}, transferTime={}, purchaseOrderNo={}, sourceFlowNo={}",
                    rpcHeader.traceId, supplierId, supplierName, projectId, projectName, currencyCode, originalAmount, transferAmount, transferTime, purchaseOrderNo, sourceFlowNo);
            SupplierPrepaidTransferToYhglobalFlow flow = new SupplierPrepaidTransferToYhglobalFlow();
            String flowNo = DateTimeIdGenerator.nextId(BizNumberType.PAYMENT_YHGLOBAL_PREPAID_BUFFER_FLOW);
            flow.setFlowNo(flowNo);
            flow.setFlowType(transferAmount > 0 ? FlowTypeEnum.IN.getType() : FlowTypeEnum.OUT.getType());
            flow.setCurrencyCode(currencyCode);
            flow.setAmountBeforeTransaction(originalAmount);
            flow.setTransactionAmount(transferAmount);
            flow.setAmountAfterTransaction(originalAmount + transferAmount);
            flow.setTransferTime(transferTime);
            flow.setSupplierId(supplierId);
            flow.setSupplierName(supplierName);
            flow.setProjectId(projectId);
            flow.setProjectName(projectName);
            flow.setPurchaseOrderNo(purchaseOrderNo);
            flow.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            flow.setCreatedByName(rpcHeader.getUsername());
            flow.setCreateTime(new Date());
            flow.setSourceFlowNo(sourceFlowNo);
            int insert = supplierPrepaidTransferToYhglobalFlowDao.insert(flow);
            logger.info("#traceId={}# [OUT]: insert prepaid flow success. flowNo={}", rpcHeader.traceId, flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
