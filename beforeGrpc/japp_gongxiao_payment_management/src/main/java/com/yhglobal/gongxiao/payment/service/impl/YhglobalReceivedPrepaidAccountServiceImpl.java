package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
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
    public String updateYhglobalReceivedPrepaidAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long prepaidAmount, Date transactionTime, String orderNo, String sourceFlowNo, String remark) {
        try {
            logger.info("#traceId={}# [IN][updateYhglobalReceivedPrepaidAccount] params: projectId={}, prepaidAmount={}",
                    rpcHeader.traceId, projectId, prepaidAmount);
            int maxRetryTimes = 6;
            String flowNo = null;
            while (maxRetryTimes-- > 0) {
                //查找账户
                YhglobalReceivedPrepaidAccount account = yhglobalReceivedPrepaidAccountDao.getAccount(projectId);
                //改变余额
                Long orginalAmount = account.getTotalAmount();
                account.setTotalAmount(orginalAmount + prepaidAmount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), prepaidAmount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户（基于乐观锁，失败自动重试）
                int update = yhglobalReceivedPrepaidAccountDao.update(account);
                if (update == 1) {
                    //更新成功，插入流水,跳出循环
                    flowNo = yhglobalReceivedPrepaidRecordService.insertRecord(rpcHeader,
                            account.getSupplierId(), account.getSupplierName(),
                            projectId, account.getProjectName(),
                            currencyCode, orginalAmount, prepaidAmount,
                            transactionTime, orderNo, sourceFlowNo,remark
                    );
                    break;
                }
            }
            //返回流水号
            logger.info("#traceId={}# [OUT]: update yhglobal prepaid account success. amount={} flowNo={}", rpcHeader.traceId, prepaidAmount, flowNo);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void writeoffYhglobalPrepaidReceivedAccount(RpcHeader rpcHeader, String currencyCode, long supplierId, long projectId, long amount, Date transactionTime, String remark) {
        this.updateYhglobalReceivedPrepaidAccount(rpcHeader, currencyCode, projectId, -amount, transactionTime, null, null, remark);
    }
}
