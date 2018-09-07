package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedCouponAccountDao;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponAccount;
import com.yhglobal.gongxiao.payment.service.YhglobalCouponLedgerWriteoffRecordService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponRecordService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 越海返利实收账户 service
 *
 * @author: 葛灿
 */
@Service
public class YhglobalReceivedCouponAccountServiceImpl implements YhglobalReceivedCouponAccountService {

    private static Logger logger = LoggerFactory.getLogger(YhglobalReceivedCouponAccountServiceImpl.class);

    @Autowired
    YhglobalReceivedCouponAccountDao yhglobalReceivedCouponAccountDao;

    @Autowired
    YhglobalReceivedCouponRecordService yhglobalReceivedCouponRecordService;


    @Override
    public String updateYhglobalReceivedCouponAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long couponAmount, Date transactionTime, String orderNo, String sourceFlowNo, String remark) {
        try {
            logger.info("#traceId={}# [IN][updateYhglobalReceivedCouponAccount] params: projectId={}, couponAmount={}",
                    rpcHeader.traceId, projectId, couponAmount);
            int maxRetryTimes = 6;
            String flowNo = null;
            while (maxRetryTimes-- > 0) {
                //查找账户
                YhglobalReceivedCouponAccount account = yhglobalReceivedCouponAccountDao.getAccount(projectId);
                //改变余额
                Long orginalAmount = account.getTotalAmount();
                account.setTotalAmount(orginalAmount + couponAmount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), couponAmount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户（基于乐观锁，失败自动重试）
                int update = yhglobalReceivedCouponAccountDao.update(account);
                if (update == 1) {
                    //更新成功，插入流水,跳出循环
                    flowNo = yhglobalReceivedCouponRecordService.insertRecord(rpcHeader,
                            account.getSupplierId(), account.getSupplierName(),
                            projectId, account.getProjectName(),
                            currencyCode, orginalAmount, couponAmount,
                            transactionTime, orderNo, sourceFlowNo,remark
                    );
                    break;
                }
            }
            //返回流水号
            logger.info("#traceId={}# [OUT]: update yhglobal coupon account success. amount={} flowNo={}", rpcHeader.traceId, couponAmount, flowNo);
            return flowNo;
            //返回流水号
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public void writeoffYhglobalCouponReceivedAccount(RpcHeader rpcHeader, String currencyCode, long supplierId, long projectId, long amount, Date transactionTime, String remark) {
        this.updateYhglobalReceivedCouponAccount(rpcHeader, currencyCode, projectId, -amount, transactionTime, null, null, remark);
    }
}
