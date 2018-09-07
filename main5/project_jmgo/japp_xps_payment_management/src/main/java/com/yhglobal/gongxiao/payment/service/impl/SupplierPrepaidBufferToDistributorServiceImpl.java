package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.dao.SupplierPrepaidBufferToDistributorDao;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidBufferToDistributor;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidTransferToDistributorFlowService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 供应商代垫过账AD账户 service
 *
 * @author: 葛灿
 */
@Service
public class SupplierPrepaidBufferToDistributorServiceImpl implements SupplierPrepaidBufferToDistributorService {

    private static Logger logger = LoggerFactory.getLogger(SupplierPrepaidBufferToDistributorServiceImpl.class);

    @Autowired
    SupplierPrepaidBufferToDistributorDao supplierPrepaidBufferToDistributorDao;

    @Autowired
    SupplierPrepaidTransferToDistributorFlowService supplierPrepaidTransferToDistributorFlowService;


    @Override
    public String updatePrepaidBufferToDistributorAccount(String prefix, GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, Long distributorId, String distributorName, long prepaidAmount, String purchaseOrderNo, Date transactionTime, String remark, String sourceFlowNo) {
        try {
            logger.info("#traceId={}# [IN][updatePrepaidBufferToDistributorAccount] params: supplierId={}, projectId={}, prepaidAmount={}, purchaseOrderNo={}, transactionTime={}, remark={}, sourceFlowNo={}",
                    rpcHeader.getTraceId(), projectId, prepaidAmount, purchaseOrderNo, transactionTime, remark, sourceFlowNo);
            int maxRetryTimes = 6;
            String flowNo = null;
            while (maxRetryTimes-- > 0) {
                //查找账户
                SupplierPrepaidBufferToDistributor account = supplierPrepaidBufferToDistributorDao.getAccount(prefix, projectId);
                //改变余额
                Long originalAmount = account.getTotalAmount();
                account.setTotalAmount(originalAmount + prepaidAmount);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), prepaidAmount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户（基于乐观锁，失败自动重试）
                int update = supplierPrepaidBufferToDistributorDao.update(prefix, account);
                if (update == 1) {
                    //更新成功，插入流水,跳出循环
                    flowNo = supplierPrepaidTransferToDistributorFlowService.insertFlow(prefix, rpcHeader,
                            account.getSupplierId(), account.getSupplierName(),
                            projectId, account.getProjectName(),
                            distributorId, distributorName,
                            currencyCode, originalAmount, prepaidAmount,
                            transactionTime, purchaseOrderNo, sourceFlowNo);
                    break;
                }
            }
            //如果更新失败，抛出异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update prepaid buffer to distributor account");
                throw new RuntimeException("FAILED to update prepaid buffer to distributor account");
            }
            //返回流水号
            logger.info("#traceId={}# [OUT]: update supplier prepaid buffer to distributor success. amount={} flowNo={}", rpcHeader.getTraceId(), prepaidAmount, flowNo);
            return flowNo;

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
