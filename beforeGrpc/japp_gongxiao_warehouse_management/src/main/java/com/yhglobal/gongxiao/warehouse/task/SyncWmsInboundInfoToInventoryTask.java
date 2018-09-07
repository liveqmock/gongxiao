package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.service.InventorySyncService;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehousemanagement.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockInDetailDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StocksQtyDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 将wms的入库回告信息同步到库存模块
 */
public class SyncWmsInboundInfoToInventoryTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToInventoryTask.class);

    private RpcHeader rpcHeader;

    private Data inStockConfirmRequest;

    private String projectId;

    private InboundOrder inboundOrder;

    private InventorySyncService inventorySyncService;

    private String purchaseOrderNo;

    private ProductService productService;

    public SyncWmsInboundInfoToInventoryTask(RpcHeader rpcHeader, InventorySyncService inventorySyncService,InboundOrder inboundOrder, Data inStockConfirmRequest, String projectId, String purchaseOrderNo,ProductService productService) {
        this.rpcHeader = rpcHeader;
        this.inventorySyncService = inventorySyncService;
        this.inboundOrder = inboundOrder;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.projectId = projectId;
        this.purchaseOrderNo = purchaseOrderNo;
        this.productService = productService;

    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][notificationInventory] params: inStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(inStockConfirmRequest));
        try {
            int purchaseType = inboundOrder.getPurchaseType();
            String batchNo = inboundOrder.getBatchNo(); //批次
            List<ProductInventory> inventoryList = new ArrayList<>(); //保存所有的库存明细行

            List<StockInDetailDto> stockInDetails = inStockConfirmRequest.getStockInDetails();
            for (StockInDetailDto record : stockInDetails) { //遍历wms入库确认信息
                ProductBasic productBasic = productService.getByWmsProductCode(rpcHeader,record.getCargoCode());
                String productId = String.valueOf(productBasic.getId());
                String productCode = productBasic.getProductCode();
                String warehouseId = String.valueOf(inboundOrder.getWarehouseId());

                for (StocksQtyDto stocksQtyDto : record.getStocksQty()) { //遍历出各种库存类型的商品
                    ProductInventory inventoryEntry = createInventoryRow(purchaseType, batchNo, productId, productCode, warehouseId, stocksQtyDto);
                    inventoryList.add(inventoryEntry);
                }

            }
            //更新库存
            inventorySyncService.syncInboundInventory(rpcHeader, inventoryList, purchaseOrderNo, inboundOrder.getGongxiaoInboundOrderNo(), inboundOrder.getInboundType());
            logger.info("#traceId={}# [OUT] get notificationInventory success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 将wms入库确认信息按照  采购类型+批次+商品id+商品编码+仓库id+库存品质 分开
     * @param purchaseType
     * @param batchNo
     * @param productId
     * @param productCode
     * @param warehouseId
     * @param stocksQtyDto
     */
    private ProductInventory createInventoryRow(int purchaseType, String batchNo,String productId, String productCode, String warehouseId, StocksQtyDto stocksQtyDto) {
        ProductInventory productInventory = new ProductInventory();
        productInventory.setPurchaseType(purchaseType);
        productInventory.setInventoryStatus(stocksQtyDto.getInventoryType());
        productInventory.setProjectId(Long.parseLong(projectId));
        productInventory.setBatchNo(batchNo);
        productInventory.setProductId(productId);
        productInventory.setProductCode(productCode);
        productInventory.setWarehouseId(warehouseId);
        productInventory.setOnHandQuantity(stocksQtyDto.getQuantity());
        return productInventory;
    }

}
