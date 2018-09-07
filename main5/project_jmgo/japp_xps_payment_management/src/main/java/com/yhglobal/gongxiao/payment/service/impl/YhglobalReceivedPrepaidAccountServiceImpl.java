package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.bo.WriteOffReturnObject;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedPrepaidAccountDao;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedPrepaidAccount;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedPrepaidAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedPrepaidRecordService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.yhglobal.gongxiao.constant.ErrorCode.YHGLOBAL_RECEIVED_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH;

/**
 * 越海代垫实收账户 service
 *
 * @author: 葛灿
 */
@Service
public class YhglobalReceivedPrepaidAccountServiceImpl implements YhglobalReceivedPrepaidAccountService {

    private static Logger logger = LoggerFactory.getLogger(YhglobalReceivedPrepaidAccountServiceImpl.class);

    @Autowired
    YhglobalReceivedPrepaidAccountDao yhglobalReceivedPrepaidAccountDao;

    @Autowired
    YhglobalReceivedPrepaidRecordService yhglobalReceivedPrepaidRecordService;


    @Override
    public WriteOffReturnObject updateYhglobalReceivedPrepaidAccount(String prefix, GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, long prepaidAmount, Date transactionTime, String orderNo, String sourceFlowNo) {
        try {
            logger.info("#traceId={}# [IN][updateYhglobalReceivedPrepaidAccount] params: projectId={}, prepaidAmount={}",
                    rpcHeader.getTraceId(), projectId, prepaidAmount);
            WriteOffReturnObject writeOffReturnObject = new WriteOffReturnObject();
            int maxRetryTimes = 6;
            String flowNo = null;
            boolean updateSuccess = false;
            long orginalAmount = 0;
            while (maxRetryTimes-- > 0) {
                //查找账户
                YhglobalReceivedPrepaidAccount account = yhglobalReceivedPrepaidAccountDao.getAccount(prefix, projectId);
                //改变余额
                orginalAmount = account.getTotalAmount();
                long totalAmount = orginalAmount + prepaidAmount;
                if (totalAmount < 0) {
                    continue;
                }
                account.setTotalAmount(orginalAmount + prepaidAmount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), prepaidAmount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户（基于乐观锁，失败自动重试）
                int update = yhglobalReceivedPrepaidAccountDao.update(prefix, account);
                if (update == 1) {
                    //更新成功，插入流水,跳出循环
                    flowNo = yhglobalReceivedPrepaidRecordService.insertRecord(prefix, rpcHeader,
                            account.getSupplierId(), account.getSupplierName(),
                            projectId, account.getProjectName(),
                            currencyCode, orginalAmount, prepaidAmount,
                            transactionTime, orderNo, sourceFlowNo
                    );
                    updateSuccess = true;
                    break;
                }
            }
            if (!updateSuccess) {
                //更新失败 账户余额不足
                logger.info("prepaid received account balance NOT enough");
                writeOffReturnObject.setReturnCode(YHGLOBAL_RECEIVED_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode());
            } else {
                //返回流水号
                logger.info("#traceId={}# [OUT]: update yhglobal prepaid account success. amount={} flowNo={}", rpcHeader.getTraceId(), prepaidAmount, flowNo);
                writeOffReturnObject.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                writeOffReturnObject.setFlowNo(flowNo);
                writeOffReturnObject.setAmountBeforeTrans(orginalAmount);
                writeOffReturnObject.setAmountAfterTrans(orginalAmount + prepaidAmount);
            }
            return writeOffReturnObject;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

}
