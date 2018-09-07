package com.yhglobal.gongxiao.warehouse.task;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * WMS回告对应的订单是调拨单时 调用该任务将wms入库确认信息同步给调拨
 */
public class SyncWmsInboundInfoToAllocateTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToAllocateTask.class);

    private RpcHeader rpcHeader;
    private ApplicationContext applicationContext;
    private Data inStockConfirmRequest;
    private ProductService productService;

    public SyncWmsInboundInfoToAllocateTask(RpcHeader rpcHeader, ApplicationContext applicationContext, Data inStockConfirmRequest, ProductService productService) {
        this.rpcHeader = rpcHeader;
        this.applicationContext = applicationContext;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.productService = productService;
    }

    @Override
    public void run() {
        inStockConfirmRequest.getSourceOrderNo();
        inStockConfirmRequest.getOrderNo();
        inStockConfirmRequest.getCkid();
        inStockConfirmRequest.getConfirmType();
        inStockConfirmRequest.getWarehouseCode();
        inStockConfirmRequest.getReceiveDate();
        inStockConfirmRequest.getStockInDetails();

    }
}
