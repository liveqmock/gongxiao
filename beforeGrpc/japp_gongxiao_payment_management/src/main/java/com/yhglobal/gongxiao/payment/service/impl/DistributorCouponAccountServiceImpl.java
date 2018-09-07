package com.yhglobal.gongxiao.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.payment.dao.DistributorCouponAccountDao;
import com.yhglobal.gongxiao.payment.model.DistributorCouponAccount;
import com.yhglobal.gongxiao.payment.service.DistributorCouponAccountService;
import com.yhglobal.gongxiao.payment.service.DistributorCouponFlowService;
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
public class DistributorCouponAccountServiceImpl implements DistributorCouponAccountService {

    private static Logger logger = LoggerFactory.getLogger(DistributorCouponAccountServiceImpl.class);

    @Autowired
    DistributorCouponAccountDao distributorCouponAccountDao;

    @Autowired
    DistributorCouponFlowService distributorCouponFlowService;

    @Override
    public String updateDistributorCouponAccount(RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, long couponAmount, String orderNo, Date transactionTime, String remark, String sourceFlowNo) {
        try {
            logger.info("#traceId={}# [IN][updateDistributorCouponAccount] params: distributorId={}, projectId={}, couponAmount={}, transactionTime={}, remark={}, sourceFlowNo={}",
                    rpcHeader.traceId, distributorId, projectId, couponAmount, transactionTime, remark, sourceFlowNo);
            int maxRetryTimes = 6;
            String flowNo = null;
            while (maxRetryTimes-- > 0) {
                //查找账户
                DistributorCouponAccount account = distributorCouponAccountDao.getAccount(projectId, distributorId);
                //改变余额
                long originalAmount = account.getTotalAmount();
                account.setTotalAmount(originalAmount + couponAmount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), couponAmount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户（基于乐观锁，失败自动重试）
                int update = distributorCouponAccountDao.update(account);
                if (update == 1) {
                    //更新成功，插入流水,跳出循环
                    flowNo = distributorCouponFlowService.insertFlow(rpcHeader,
                            projectId, account.getProjectName(),
                            distributorId, account.getDistributorName(),
                            currencyCode, originalAmount,
                            couponAmount, transactionTime,
                            orderNo, sourceFlowNo, remark);
                    break;
                }
            }
            //如果更新失败，抛出位置系统异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update distributor coupon account");
                throw new RuntimeException("FAILED to update distributor coupon account");
            }
            //返回流水号
            logger.info("#traceId={}# [OUT]: update distributor coupon account success. amount={} flowNo={}", rpcHeader.traceId, couponAmount, flowNo);
            return flowNo;

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


}
