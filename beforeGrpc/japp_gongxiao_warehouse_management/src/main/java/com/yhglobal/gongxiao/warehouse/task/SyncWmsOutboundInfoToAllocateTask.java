package com.yhglobal.gongxiao.warehouse.task;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * 当wms回告对应的订单是调拨单时 通过该任务 同步wms信息到调拨模块
 */
public class SyncWmsOutboundInfoToAllocateTask implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(SyncWmsOutboundInfoToAllocateTask.class);

    private RpcHeader rpcHeader;
    private ApplicationContext applicationContext;
    private Data outStockConfirmRequest;

    public SyncWmsOutboundInfoToAllocateTask(RpcHeader rpcHeader, ApplicationContext applicationContext, Data outStockConfirmRequest) {
        this.rpcHeader = rpcHeader;
        this.applicationContext = applicationContext;
        this.outStockConfirmRequest = outStockConfirmRequest;
    }

    @Override
    public void run() {
        outStockConfirmRequest.getSourceOrderNo();
        outStockConfirmRequest.getOrderNo();
        outStockConfirmRequest.getCkid();
        outStockConfirmRequest.getConfirmType();
        outStockConfirmRequest.getOrderItems();
        outStockConfirmRequest.getConfirmType();
    }
}
