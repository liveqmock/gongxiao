package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.account.microservice.ProductInventoryStructure;
import com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouse.dao.InboundOrderItemDao;
import com.yhglobal.gongxiao.warehouse.model.InboundOrder;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StockInDetailDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StocksQtyDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.instockconfirm.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 将wms的入库回告信息同步到库存模块
 */
public class SyncWmsInboundInfoToInventoryTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToInventoryTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private Data inStockConfirmRequest;

    private String projectId;

    private InboundOrder inboundOrder;

    private InboundOrderItemDao inboundOrderItemDao;

    public SyncWmsInboundInfoToInventoryTask(GongxiaoRpc.RpcHeader rpcHeader, InboundOrder inboundOrder, Data inStockConfirmRequest, String projectId, InboundOrderItemDao inboundOrderItemDao) {
        this.rpcHeader = rpcHeader;
        this.inboundOrder = inboundOrder;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.projectId = projectId;
        this.inboundOrderItemDao = inboundOrderItemDao;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][notificationInventory] params: inStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(inStockConfirmRequest));
        try {
            int purchaseType = inboundOrder.getPurchaseType();
            String batchNo = inboundOrder.getBatchNo(); //批次
            String inboundOrderNo = inboundOrder.getGongxiaoInboundOrderNo();
            String purchaseOrderNo = inboundOrder.getPurchaseOrderNo();
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));

            List<StockInDetailDto> stockInDetails = inStockConfirmRequest.getStockInDetails();
            ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            //调用库存中心的grpc
            InventorysyncServiceGrpc.InventorysyncServiceBlockingStub inventorysyncService = WarehouseRpcStubStore.getRpcStub(InventorysyncServiceGrpc.InventorysyncServiceBlockingStub.class);
            InventorysyncStructure.SyncInboundRequest.Builder syncInboundRequest = InventorysyncStructure.SyncInboundRequest.newBuilder();
            for (StockInDetailDto record : stockInDetails) { //遍历wms入库确认信息
                ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(Long.parseLong(projectId)).setProductWmsCode(record.getCargoCode()).build();
                ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
                ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();
                String productId = String.valueOf(productBusiness.getProductBasicId());
                String productCode = productBusiness.getProductModel();
                String warehouseId = String.valueOf(inboundOrder.getWarehouseId());
                for (StocksQtyDto stocksQtyDto : record.getStocksQty()) { //遍历出各种库存类型的商品
                    InboundOrderItem item = inboundOrderItemDao.selectOrderItemByNo(inboundOrderNo,productCode,projectPrefix);    //到入库单明细表查找对应的订单，得到对应的采购价格和成本价
                    ProductInventoryStructure.ProductInventory inventoryEntry = createInventoryRow(purchaseType, batchNo, inboundOrderNo, purchaseOrderNo, productId, productCode, warehouseId, item.getPurchasePrice(),item.getCostPrice(),stocksQtyDto);
                    syncInboundRequest.addProductInventoryList(inventoryEntry);
                }

            }
            //更新库存
            syncInboundRequest.setRpcHeader(rpcHeader);
            syncInboundRequest.setGonxiaoInboundOrderNo(inboundOrder.getGongxiaoInboundOrderNo());
            syncInboundRequest.setOrderType(inboundOrder.getInboundType());
            syncInboundRequest.setPurchaseOrderNo((purchaseOrderNo==null?"":purchaseOrderNo));
            syncInboundRequest.setProjectId(projectId);
            inventorysyncService.syncInboundInventory(syncInboundRequest.build());
            logger.info("#traceId={}# [OUT] get notificationInventory success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 将wms入库确认信息按照  采购类型+批次+商品id+商品编码+仓库id+库存品质 分开
     *
     * @param purchaseType
     * @param batchNo
     * @param productId
     * @param productCode
     * @param warehouseId
     * @param stocksQtyDto
     */
    private ProductInventoryStructure.ProductInventory createInventoryRow(int purchaseType, String batchNo, String inboundOrderNo, String purchaseOrderNo, String productId, String productCode, String warehouseId, long purchasePrice, long costPrice, StocksQtyDto stocksQtyDto) {
        ProductInventoryStructure.ProductInventory productInventory = ProductInventoryStructure.ProductInventory.newBuilder()
                .setPurchaseType(purchaseType)
                .setInventoryStatus(stocksQtyDto.getInventoryType())
                .setProjectId(Long.parseLong(projectId))
                .setBatchNo(batchNo)
                .setInboundOrderNo(inboundOrderNo)
                .setPurchaseOrderNo(purchaseOrderNo)
                .setInboundQuantity(stocksQtyDto.getQuantity())
                .setProductId(productId)
                .setProductCode(productCode)
                .setWarehouseId(warehouseId)
                .setPurchasePrice(purchasePrice)
                .setCostPrice(costPrice)
                .setOnHandQuantity(stocksQtyDto.getQuantity()).build();
        return productInventory;
    }

}
