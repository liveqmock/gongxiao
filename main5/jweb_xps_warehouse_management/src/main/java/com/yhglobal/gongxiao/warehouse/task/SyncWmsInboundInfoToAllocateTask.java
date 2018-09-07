package com.yhglobal.gongxiao.warehouse.task;

import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.model.InboundOrder;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StockInDetailDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StocksQtyDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.instockconfirm.Data;
import com.yhglobal.gongxiao.warehouse.service.AllocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * WMS回告对应的订单是调拨单时 调用该任务将wms入库确认信息同步给调拨
 */
public class SyncWmsInboundInfoToAllocateTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToAllocateTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;
    private Data inStockConfirmRequest;
    private InboundOrder inboundOrder;
    private AllocationService allocationService;

    public SyncWmsInboundInfoToAllocateTask(GongxiaoRpc.RpcHeader rpcHeader, Data inStockConfirmRequest, InboundOrder inboundOrder, AllocationService allocationService) {
        this.rpcHeader = rpcHeader;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.inboundOrder = inboundOrder;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        inStockConfirmRequest.getSourceOrderNo();
        inStockConfirmRequest.getOrderNo();
        inStockConfirmRequest.getCkid();
        inStockConfirmRequest.getConfirmType();
        inStockConfirmRequest.getWarehouseCode();
        inStockConfirmRequest.getReceiveDate();
        List<StockInDetailDto> stockInDetailDtoList = inStockConfirmRequest.getStockInDetails();
        try {
            int totalQuantity = 0; //当次调拨入库通知的总数量
            //调用基础模块商品的grpc
            ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            for (StockInDetailDto stockInDetailDto : stockInDetailDtoList) { //遍历wms入库确认信息
                int itemQuantity = 0; //当次入库确认的商品数总和
                ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(inboundOrder.getProjectId()).setProductWmsCode(stockInDetailDto.getCargoCode()).build();
                ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
                ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();
                for (StocksQtyDto stocksQtyDto : stockInDetailDto.getStocksQty()) {
                    itemQuantity += stocksQtyDto.getQuantity();

                }
                totalQuantity += itemQuantity;
                allocationService.updateAllocateEntryItem(rpcHeader,inboundOrder.getPurchaseOrderNo(),productBusiness.getProductModel(),itemQuantity);
            }

            //更新调拨汇总表的状态,更新实际调入数量
            allocationService.updateAllocateOrderEntry(rpcHeader, inboundOrder.getPurchaseOrderNo(),totalQuantity);

            logger.info("#traceId={}# [OUT] get SyncWmsInboundInfoToAllocateTask.run() success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }


    }
}
