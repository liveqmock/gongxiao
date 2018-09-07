package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.inventory.account.microservice.ProductInventoryStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.OutboundOrderStatusEnum;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.dao.AllocateOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.AllocateOrderItemDao;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrder;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrderItem;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrder;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StockOutOrderConfirmItemDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StockOutOrderConfirmOrderItemDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.outstockconfirm.Data;
import com.yhglobal.gongxiao.warehouse.service.AllocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 当wms回告对应的订单是调拨单时 通过该任务 同步wms信息到调拨模块
 */
public class SyncWmsOutboundInfoToAllocateTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(SyncWmsOutboundInfoToAllocateTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;
    private Data outStockConfirmRequest;
    private OutboundOrder outboundOrder;
    private AllocateOrderDao allocateOrderDao;
    private AllocateOrderItemDao allocateOrderItemDao;
    private AllocationService allocationService;

    public SyncWmsOutboundInfoToAllocateTask(GongxiaoRpc.RpcHeader rpcHeader, Data outStockConfirmRequest, OutboundOrder outboundOrder, AllocateOrderDao allocateOrderDao, AllocateOrderItemDao allocateOrderItemDao, AllocationService allocationService) {
        this.rpcHeader = rpcHeader;
        this.outStockConfirmRequest = outStockConfirmRequest;
        this.outboundOrder = outboundOrder;
        this.allocateOrderDao = allocateOrderDao;
        this.allocateOrderItemDao = allocateOrderItemDao;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        outStockConfirmRequest.getOrderNo();
        List<StockOutOrderConfirmOrderItemDto> stockOutOrderConfirmOrderItemDtos = outStockConfirmRequest.getOrderItems();
        List<ProductInventoryStructure.ProductInventory> inventoryList = new ArrayList<>();
        int totalOutQuantity = 0; //总数量

        for (StockOutOrderConfirmOrderItemDto record : stockOutOrderConfirmOrderItemDtos) { //遍历wms出库确认信息
            String outboundOrderItemNo = record.getItemNo(); //获得WMS反馈的ItemNo, 当前传的是OutboundOrderItem.outboundOrderItemNo字段

            int itemStockQuantity = 0;
            //调用基础模块的商品的grpc查询项目信息
            ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(Long.parseLong(outboundOrder.getProjectId())).setProductWmsCode(record.getItemCode()).build();
            ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
            ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();
            for (StockOutOrderConfirmItemDto item : record.getItems()) { //遍历同种商品的不同品质
                itemStockQuantity += item.getQuantity();
            }
            totalOutQuantity += itemStockQuantity;
            allocationService.updateAllocateOutItem(rpcHeader,outboundOrder.getSalesOrderNo(),productBusiness.getProductModel(),itemStockQuantity);

        }
        //更新调拨单汇总表
        allocationService.updateAllocateOrderOut(rpcHeader, outboundOrder.getSalesOrderNo(),totalOutQuantity);

    }

}
