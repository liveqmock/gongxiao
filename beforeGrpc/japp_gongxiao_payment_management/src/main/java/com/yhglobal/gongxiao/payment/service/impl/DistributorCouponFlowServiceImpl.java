package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.payment.dao.DistributorCouponFlowDao;
import com.yhglobal.gongxiao.payment.flow.FlowTypeEnum;
import com.yhglobal.gongxiao.payment.model.DistributorCouponFlow;
import com.yhglobal.gongxiao.payment.service.DistributorCouponFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: 葛灿
 */
@Service
public class DistributorCouponFlowServiceImpl implements DistributorCouponFlowService {
    private static Logger logger = LoggerFactory.getLogger(DistributorCouponFlowServiceImpl.class);

    @Autowired
    DistributorCouponFlowDao distributorCouponFlowDao;

    @Override
    public String insertFlow(RpcHeader rpcHeader, long projectId, String projectName, long distributorId, String distributorName, String currencyCode, long amountBeforeTransaction, long transactionAmount, Date transactionTime, String salesOrderNo, String sourceFlowNo, String extroInfo) {
        try {
            logger.info("#traceId={}# [IN][insertFlow] params: projectId={} projectName={} distributorId={} distributorName={} currencyCode={} amountBeforeTransaction={} transactionAmount={} transactionTime={} salesOrderNo={} bufferAccountFlowNo={} extroInfo={}",
                    rpcHeader.traceId, projectId, projectName, distributorId, distributorName, currencyCode, amountBeforeTransaction, transactionAmount, transactionTime, salesOrderNo, sourceFlowNo, extroInfo);
            DistributorCouponFlow flow = new DistributorCouponFlow();
            String flowNo = DateTimeIdGenerator.nextId(BizNumberType.PAYMENT_DISTRIBUTOR_COUPON_FLOW);
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
            flow.setSourceFlowNo(sourceFlowNo);
            int insert = distributorCouponFlowDao.insert(flow);
            logger.info("#traceId={}# [OUT]: insert coupon flow success. flowNo={}", rpcHeader.traceId, flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
