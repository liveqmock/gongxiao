package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.dao.DistributorCashFlowDao;
import com.yhglobal.gongxiao.payment.model.DistributorCashFlow;
import com.yhglobal.gongxiao.payment.model.FlowTypeEnum;
import com.yhglobal.gongxiao.payment.service.DistributorCashFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: 葛灿
 */
@Service
public class DistributorCashFlowServiceImpl implements DistributorCashFlowService {
    private static Logger logger = LoggerFactory.getLogger(DistributorCashFlowServiceImpl.class);

    @Autowired
    DistributorCashFlowDao distributorCashFlowDao;

    @Override
    public String insertFlow(String prefix, GongxiaoRpc.RpcHeader rpcHeader, long projectId, String projectName, long distributorId, String distributorName, String currencyCode, long amountBeforeTransaction, long transactionAmount, Date transactionTime, String salesOrderNo, String extroInfo) {
        try {
            logger.info("#traceId={}# [IN][insertFlow] params: projectId={} projectName={} distributorId={} distributorName={} currencyCode={} amountBeforeTransaction={} transactionAmount={} transactionTime={} salesOrderNo={} extroInfo={}",
                    rpcHeader.getTraceId(), projectId, projectName, distributorId, distributorName, currencyCode, amountBeforeTransaction, transactionAmount, transactionTime, salesOrderNo, extroInfo);
            DistributorCashFlow flow = new DistributorCashFlow();
            String flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.PAYMENT_DISTRIBUTOR_COUPON_FLOW);
            flow.setFlowNo(flowNo);
            flow.setFlowType(transactionAmount > 0 ? FlowTypeEnum.IN.getType() : FlowTypeEnum.OUT.getType());
            flow.setDistributorId(distributorId);
            flow.setDistributorName(distributorName);
            flow.setProjectId(projectId);
            flow.setProjectName(projectName);
            flow.setCurrencyCode(currencyCode);
            flow.setAmountBeforeTransaction(amountBeforeTransaction);
            flow.setTransactionAmount(transactionAmount);
            flow.setAmountAfterTransaction(amountBeforeTransaction + transactionAmount);
            flow.setTransactionTime(transactionTime);
            flow.setOrderNo(salesOrderNo);
            flow.setCreateTime(new Date());
            flow.setCreateById(Long.parseLong(rpcHeader.getUid()));
            flow.setCreateByName(rpcHeader.getUsername());
            int insert = distributorCashFlowDao.insert(prefix, flow);
            logger.info("#traceId={}# [OUT]: insert cash success. flowNo={}", rpcHeader.getTraceId(), flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
