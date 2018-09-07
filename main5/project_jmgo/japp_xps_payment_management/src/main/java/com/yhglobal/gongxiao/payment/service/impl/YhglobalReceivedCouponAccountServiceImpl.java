package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.bo.WriteOffReturnObject;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedCouponAccountDao;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponAccount;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponRecordService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.yhglobal.gongxiao.constant.ErrorCode.YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH;

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

    public WriteOffReturnObject updateYhglobalReceivedCouponAccount(String prefix, GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, long couponAmount, Date transactionTime, String orderNo, String sourceFlowNo) {
        try {
            logger.info("#traceId={}# [IN][updateYhglobalReceivedCouponAccount] params: projectId={}, couponAmount={}",
                    rpcHeader.getTraceId(), projectId, couponAmount);
            WriteOffReturnObject writeOffReturnObject = new WriteOffReturnObject();
            int maxRetryTimes = 6;
            String flowNo = null;
            boolean updateSuccess = false;
            long orginalAmount = 0;
            while (maxRetryTimes-- > 0) {
                //查找账户
                YhglobalReceivedCouponAccount account = yhglobalReceivedCouponAccountDao.getAccount(prefix, projectId);
                orginalAmount = account.getTotalAmount();
                //账户余额
                long totalAmount = orginalAmount + couponAmount;
                if (totalAmount < 0) {
                    continue;
                }
                account.setTotalAmount(totalAmount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), couponAmount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户（基于乐观锁，失败自动重试）
                int update = yhglobalReceivedCouponAccountDao.update(prefix, account);
                if (update == 1) {
                    //更新成功，插入流水,跳出循环
                    flowNo = yhglobalReceivedCouponRecordService.insertRecord(prefix, rpcHeader,
                            account.getSupplierId(), account.getSupplierName(),
                            projectId, account.getProjectName(),
                            currencyCode, orginalAmount, couponAmount,
                            transactionTime, orderNo, sourceFlowNo
                    );
                    updateSuccess = true;
                    break;
                }
            }
            if (!updateSuccess) {
                //更新失败 账户余额不足
                logger.info("prepaid received account balance NOT enough");
                writeOffReturnObject.setReturnCode(YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode());
            } else {
                //返回流水号
                logger.info("#traceId={}# [OUT]: update yhglobal coupon account success. amount={} flowNo={}", rpcHeader.getTraceId(), couponAmount, flowNo);
                writeOffReturnObject.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                writeOffReturnObject.setFlowNo(flowNo);
                writeOffReturnObject.setAmountBeforeTrans(orginalAmount);
                writeOffReturnObject.setAmountAfterTrans(orginalAmount + couponAmount);
            }
            return writeOffReturnObject;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

}
