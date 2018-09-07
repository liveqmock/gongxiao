package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehousemanagement.dao.WmsInboundDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.WmsInboundItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockInDetailDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StocksQtyDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecord;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecordItem;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.SyncEasEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  收到WMS入库信息回告后，记录流水到DB
 */
public class SyncWmsInboundInfoToDBTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToDBTask.class);

    private RpcHeader rpcHeader;

    private Data inStockConfirmRequest;

    private InboundOrder inboundOrder;

    private WmsInboundDao wmsInboundDao;

    private WmsInboundItemDao wmsInboundItemDao;

    private ProductService productService;

    public SyncWmsInboundInfoToDBTask(RpcHeader rpcHeader, Data inStockConfirmRequest, InboundOrder inboundOrder, WmsInboundDao wmsInboundDao, WmsInboundItemDao wmsInboundItemDao, ProductService productService) {
        this.rpcHeader = rpcHeader;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.inboundOrder = inboundOrder;
        this.wmsInboundDao = wmsInboundDao;
        this.wmsInboundItemDao = wmsInboundItemDao;
        this.productService = productService;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start to SyncWmsInboundInfoToDBTask params: wmsInStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(inStockConfirmRequest));

        int totalQuantity = 0; //入库确认的总数量
        List<String> productCodeList = new ArrayList<>(); //入库确认记录中所有的productCode

        //1. 入库记录明细表写流水
        List<WmsIntboundRecordItem> wmsIntboundRecordItemList = new ArrayList<>(); //入库确认的明细列表
        for (StockInDetailDto record : inStockConfirmRequest.getStockInDetails()) {
            ProductBasic productBasic = productService.getByWmsProductCode(rpcHeader, record.getCargoCode());
            productCodeList.add(productBasic.getProductCode());
            for (StocksQtyDto stocksQtyDto : record.getStocksQty()) {
                totalQuantity += stocksQtyDto.getQuantity();
                WmsInventoryType wmsInventoryType = WmsInventoryType.getInventoryTypeByNumValue(stocksQtyDto.getInventoryType()); //获取wms的出库类型
                WmsIntboundRecordItem wmsIntboundRecordItem = createInboundRecordItem(productBasic, wmsInventoryType, stocksQtyDto.getQuantity(), inStockConfirmRequest.getInboundBatchNo());
                wmsIntboundRecordItemList.add(wmsIntboundRecordItem);
            }
        }

        try {
            wmsInboundItemDao.insertWmsInboundInfo(wmsIntboundRecordItemList);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }

        //2. 入库记录汇总表写流水
        WmsIntboundRecord wmsIntboundRecord = new WmsIntboundRecord();
        wmsIntboundRecord.setCreateTime(new Date());
        wmsIntboundRecord.setProjectId(String.valueOf(inboundOrder.getProjectId()));
        wmsIntboundRecord.setPurchaseOrderNo(inboundOrder.getPurchaseOrderNo());
        wmsIntboundRecord.setBatchNo(inboundOrder.getBatchNo());
        wmsIntboundRecord.setPurchaseType(inboundOrder.getPurchaseType());
        wmsIntboundRecord.setInboundType(inboundOrder.getInboundType());
        wmsIntboundRecord.setGongxiaoInboundOrderNo(inboundOrder.getGongxiaoInboundOrderNo());
        wmsIntboundRecord.setWmsInboundOrderNo(inStockConfirmRequest.getInboundBatchNo());
        wmsIntboundRecord.setEasFlag(SyncEasEnum.SYNC_EAS_FAIL.getStatus());
        wmsIntboundRecord.setWarehouseId(inboundOrder.getWarehouseId());
        wmsIntboundRecord.setWarehouseName(inboundOrder.getWarehouseName());

        wmsIntboundRecord.setInStockQuantity(totalQuantity);
        wmsIntboundRecord.setProductCode(JSON.toJSONString(productCodeList));

        try {
            wmsInboundDao.insertWmsInboundInfo(wmsIntboundRecord);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }

        logger.info("#traceId={}# [OUT] get SyncWmsInboundInfoToDBTask.run() to recordWmsSystemInfo success", rpcHeader.getTraceId());
    }

    private WmsIntboundRecordItem createInboundRecordItem(ProductBasic productBasic, WmsInventoryType wmsInventoryType, int quantity, String wmsInboundBatchNo){
        WmsIntboundRecordItem wmsIntboundRecordItem = new WmsIntboundRecordItem();
        wmsIntboundRecordItem.setProjectId(String.valueOf(inboundOrder.getProjectId()));
        wmsIntboundRecordItem.setPurchaseType(inboundOrder.getPurchaseType());
        wmsIntboundRecordItem.setInventoryType(wmsInventoryType.getInventoryType());
        wmsIntboundRecordItem.setPurchaseOrderNo(inboundOrder.getPurchaseOrderNo());
        wmsIntboundRecordItem.setGongxiaoInboundOrderNo(inboundOrder.getGongxiaoInboundOrderNo());
        wmsIntboundRecordItem.setWmsInboundOrderNo(wmsInboundBatchNo);
        wmsIntboundRecordItem.setWarehouseId(inboundOrder.getWarehouseId());
        wmsIntboundRecordItem.setWarehouseName(inboundOrder.getWarehouseName());
        wmsIntboundRecordItem.setProductId(String.valueOf(productBasic.getId()));
        wmsIntboundRecordItem.setProductCode(productBasic.getProductCode());
        wmsIntboundRecordItem.setProductName(productBasic.getProductName());
        wmsIntboundRecordItem.setInStockQuantity(quantity);
        wmsIntboundRecordItem.setCreateTime(new Date());
        return wmsIntboundRecordItem;
    }
}
