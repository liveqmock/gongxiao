package com.yhglobal.gongxiao.payment.task;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.eas.SaleOrder;
import com.yhglobal.gongxiao.eas.SaleOrderItem;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.YhglobalAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedPrepaidAccountService;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 把返利、代垫金额从缓冲账户同步到实收账户
 *
 * @Author: 葛灿
 */
public class SyncCouponPrepaidToYhglobalReceivedTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(SyncCouponPrepaidToYhglobalReceivedTask.class);


    private ApplicationContext applicationContext;

    private RpcHeader rpcHeader;
    private String currencyCode;
    private long projectId;
    private long couponAmount;
    private long prepaidAmount;
    private Date transactionTime;
    private String purchaseOrderNo;
    private SupplierCouponBufferToYhglobalService supplierCouponBufferToYhglobalService;
    private YhglobalReceivedCouponAccountService yhglobalReceivedCouponAccountService;
    private SupplierPrepaidBufferToYhglobalService supplierPrepaidBufferToYhglobalService;
    private YhglobalReceivedPrepaidAccountService yhglobalReceivedPrepaidAccountService;


    public SyncCouponPrepaidToYhglobalReceivedTask(ApplicationContext applicationContext, RpcHeader rpcHeader,
                                                   String currencyCode, long projectId,
                                                   long couponAmount, long prepaidAmount,
                                                   Date transactionTime, String purchaseOrderNo,
                                                   SupplierCouponBufferToYhglobalService supplierCouponBufferToYhglobalService,
                                                   YhglobalReceivedCouponAccountService yhglobalReceivedCouponAccountService,
                                                   SupplierPrepaidBufferToYhglobalService supplierPrepaidBufferToYhglobalService,
                                                   YhglobalReceivedPrepaidAccountService yhglobalReceivedPrepaidAccountService) {
        this.applicationContext = applicationContext;
        this.rpcHeader = rpcHeader;
        this.currencyCode = currencyCode;
        this.projectId = projectId;
        this.couponAmount = couponAmount;
        this.prepaidAmount = prepaidAmount;
        this.transactionTime = transactionTime;
        this.purchaseOrderNo = purchaseOrderNo;
        this.supplierCouponBufferToYhglobalService = supplierCouponBufferToYhglobalService;
        this.yhglobalReceivedCouponAccountService = yhglobalReceivedCouponAccountService;
        this.supplierPrepaidBufferToYhglobalService = supplierPrepaidBufferToYhglobalService;
        this.yhglobalReceivedPrepaidAccountService = yhglobalReceivedPrepaidAccountService;
    }

    @Override
    public void run() {
        try {
            logger.info("#traceId={}# [IN][SyncSalesOrderToEasTask] synchronize yhglobal amount start: ", rpcHeader.traceId);
            if (couponAmount != 0) {
                //缓冲账户扣款
                String couponFlowNo = supplierCouponBufferToYhglobalService.updateCouponBufferToYhglobal(rpcHeader, currencyCode, projectId, -couponAmount, purchaseOrderNo, transactionTime, null, null);
                //实收账户添加
                yhglobalReceivedCouponAccountService.updateYhglobalReceivedCouponAccount(rpcHeader, currencyCode, projectId, couponAmount, transactionTime, purchaseOrderNo, couponFlowNo, null);
            }

            if (prepaidAmount != 0) {
                //缓冲账户扣款
                String prepaidFlowNo = supplierPrepaidBufferToYhglobalService.updatePrepaidBufferToYhglobal(rpcHeader, currencyCode, projectId, -prepaidAmount, purchaseOrderNo, transactionTime, null, null);
                //实收账户添加
                yhglobalReceivedPrepaidAccountService.updateYhglobalReceivedPrepaidAccount(rpcHeader, currencyCode, projectId, prepaidAmount, transactionTime, purchaseOrderNo, prepaidFlowNo, null);
            }

            logger.info("#traceId={}# [OUT] synchronize yhglobal amount success: ", rpcHeader.traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }
    }

}
