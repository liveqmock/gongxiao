package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.payment.dao.SupplierPrepaidBufferToYhglobalDao;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidBufferToYhglobal;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidTransferToYhglobalFlowService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 供应商代垫过账YH账户 service
 *
 * @author: 葛灿
 */
@Service
public class SupplierPrepaidBufferToYhglobalServiceImpl implements SupplierPrepaidBufferToYhglobalService {

    private static Logger logger = LoggerFactory.getLogger(SupplierPrepaidBufferToYhglobalServiceImpl.class);

    @Autowired
    SupplierPrepaidBufferToYhglobalDao supplierPrepaidBufferToYhglobalDao;

    @Autowired
    SupplierPrepaidTransferToYhglobalFlowService supplierPrepaidTransferToYhglobalFlowService;


    @Override
    public String updatePrepaidBufferToYhglobal(RpcHeader rpcHeader, String currencyCode, long projectId, long prepaidAmount, String purchaseOrderNo, Date transactionTime, String remark, String sourceFlowNo) {
        try {
            logger.info("#traceId={}# [IN][updatePrepaidBufferToYhglobal] params: supplierId={}, projectId={}, prepaidAmount={}, purchaseOrderNo={}, transactionTime={}, remark={}, sourceFlowNo={}",
                    rpcHeader.traceId, projectId, prepaidAmount, purchaseOrderNo, transactionTime, remark, sourceFlowNo);
            int maxRetryTimes = 6;
            String flowNo = null;
            while (maxRetryTimes-- > 0) {
                //查找账户
                SupplierPrepaidBufferToYhglobal account = supplierPrepaidBufferToYhglobalDao.getAccount(projectId);
                //改变余额
                Long orginalAmount = account.getTotalAmount();
                account.setTotalAmount(orginalAmount + prepaidAmount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), prepaidAmount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户
                int update = supplierPrepaidBufferToYhglobalDao.update(account);
                if (update == 1) {
                    // 更新成功，插入流水,跳出循环
                    flowNo = supplierPrepaidTransferToYhglobalFlowService.insertFlow(rpcHeader,
                            account.getSupplierId(), account.getSupplierName(),
                            projectId, account.getProjectName(),
                            currencyCode, orginalAmount, prepaidAmount,
                            transactionTime, purchaseOrderNo, sourceFlowNo);
                    break;
                }
            }
            //如果更新失败，抛出异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update prepaid buffer to yhglobal account");
                throw new RuntimeException("FAILED to update prepaid buffer to yhglobal account");
            }
            //返回流水号
            logger.info("#traceId={}# [OUT]: update supplier prepaid buffer to yhglobal success. amount={} flowNo={}", rpcHeader.traceId, prepaidAmount, flowNo);
            return flowNo;

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
