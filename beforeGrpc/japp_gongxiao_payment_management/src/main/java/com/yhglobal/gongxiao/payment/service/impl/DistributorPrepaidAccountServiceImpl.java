package com.yhglobal.gongxiao.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.payment.dao.DistributorPrepaidAccountDao;
import com.yhglobal.gongxiao.payment.model.DistributorPrepaidAccount;
import com.yhglobal.gongxiao.payment.model.DistributorPrepaidAccount;
import com.yhglobal.gongxiao.payment.service.DistributorPrepaidAccountService;
import com.yhglobal.gongxiao.payment.service.DistributorPrepaidFlowService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * AD代垫账户 service
 *
 * @author: 葛灿
 */
@Service
public class DistributorPrepaidAccountServiceImpl implements DistributorPrepaidAccountService {

    private static Logger logger = LoggerFactory.getLogger(DistributorPrepaidAccountServiceImpl.class);

    @Autowired
    DistributorPrepaidAccountDao distributorPrepaidAccountDao;

    @Autowired
    DistributorPrepaidFlowService distributorPrepaidFlowService;


    @Override
    public String updateDistributorPrepaidAccount(RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, long couponAmount, String orderNo, Date transactionTime, String remark, String sourceFlowNo) {
        try {
            logger.info("#traceId={}# [IN][updateDistributorPrepaidAccount] params: distributorId={}, projectId={}, couponAmount={}, transactionTime={}, remark={}, sourceFlowNo={}",
                    rpcHeader.traceId, distributorId, projectId, couponAmount, transactionTime, remark, sourceFlowNo);
            int maxRetryTimes = 6;
            String flowNo = null;
            while (maxRetryTimes-- > 0) {
                //查找账户
                DistributorPrepaidAccount account = distributorPrepaidAccountDao.getAccount(projectId, distributorId);
                //改变余额
                long originalAmount = account.getTotalAmount();
                account.setTotalAmount(originalAmount + couponAmount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), couponAmount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户（基于乐观锁，失败自动重试）
                int update = distributorPrepaidAccountDao.update(account);
                if (update == 1) {
                    //更新成功，插入流水,跳出循环
                    flowNo = distributorPrepaidFlowService.insertFlow(rpcHeader,
                            projectId, account.getProjectName(),
                            distributorId, account.getDistributorName(),
                            currencyCode, originalAmount,
                            couponAmount, transactionTime,
                            orderNo, sourceFlowNo, remark);
                    break;
                }
            }
            //如果更新失败，抛出异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update distributor prepaid account");
                throw new RuntimeException("FAILED to update distributor prepaid account");
            }
            //更新成功，返回流水号
            logger.info("#traceId={}# [OUT]: update distributor prepaid account success. amount={} flowNo={}", rpcHeader.traceId, couponAmount, flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


}
