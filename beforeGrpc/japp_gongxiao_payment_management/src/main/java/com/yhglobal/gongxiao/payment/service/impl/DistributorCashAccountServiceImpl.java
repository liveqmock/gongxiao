package com.yhglobal.gongxiao.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.payment.dao.DistributorCashAccountDao;
import com.yhglobal.gongxiao.payment.model.DistributorCashAccount;
import com.yhglobal.gongxiao.payment.service.DistributorCashAccountService;
import com.yhglobal.gongxiao.payment.service.DistributorCashFlowService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * ad返利账户 service
 *
 * @author: 葛灿
 */
@Service
public class DistributorCashAccountServiceImpl implements DistributorCashAccountService {

    private static Logger logger = LoggerFactory.getLogger(DistributorCashAccountServiceImpl.class);

    @Autowired
    DistributorCashAccountDao distributorCashAccountDao;

    @Autowired
    DistributorCashFlowService distributorCashFlowService;

    @Override
    public DistributorCashAccount getDistributorCashAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long distributorId) {
        return distributorCashAccountDao.getAccount(projectId, distributorId);
    }


    @Override
    public String updateDistributorCashAccount(RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, long couponAmount, String orderNo, Date transactionTime, String remark) {
        try {
            logger.info("#traceId={}# [IN][updateDistributorCashAccount] params: distributorId={}, projectId={}, couponAmount={}, salesOrderNo={}, transactionTime={}, remark={}",
                    rpcHeader.traceId, distributorId, projectId, couponAmount, orderNo, transactionTime, remark);

            int maxRetryTimes = 6;
            String flowNo = null;
            while (maxRetryTimes-- > 0) {
                //查找账户
                DistributorCashAccount account = distributorCashAccountDao.getAccount(projectId, distributorId);
                //改变余额
                long originalAmount = account.getTotalAmount();
                account.setTotalAmount(originalAmount + couponAmount);
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), couponAmount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户（基于乐观锁，失败自动重试）
                int update = distributorCashAccountDao.update(account);
                if (update == 1) {
                    //更新成功，插入流水,跳出循环
                    flowNo = distributorCashFlowService.insertFlow(rpcHeader,
                            projectId, account.getProjectName(),
                            distributorId, account.getDistributorName(),
                            currencyCode, originalAmount,
                            couponAmount, transactionTime,
                            orderNo, null);
                    break;
                }
            }
            //如果更新失败，抛出位置系统异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update distributor cash account");
                throw new RuntimeException("FAILED to update distributor cash account");
            }
            //返回流水号
            logger.info("#traceId={}# [OUT]: update distributor cash account success. amount={} flowNo={}", rpcHeader.traceId, couponAmount, flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


}
