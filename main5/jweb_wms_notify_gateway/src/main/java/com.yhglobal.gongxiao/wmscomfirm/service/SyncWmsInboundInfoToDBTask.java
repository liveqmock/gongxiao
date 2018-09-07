package com.yhglobal.gongxiao.wmscomfirm.service;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.SyncEasEnum;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehouseapi.model.InboundOrder;
import com.yhglobal.gongxiao.wmscomfirm.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.wmscomfirm.dao.WmsInboundDao;
import com.yhglobal.gongxiao.wmscomfirm.dao.WmsInboundItemDao;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.StockInDetailDto;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.StocksQtyDto;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.WmsIntboundRecord;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.WmsIntboundRecordItem;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.instockconfirm.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  收到WMS入库信息回告后，记录流水到DB
 */
public class SyncWmsInboundInfoToDBTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToDBTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private Data inStockConfirmRequest;

    private WmsInboundDao wmsInboundDao;

    private WmsInboundItemDao wmsInboundItemDao;

    private InBoundOrderDao inBoundOrderDao;

    public SyncWmsInboundInfoToDBTask(GongxiaoRpc.RpcHeader rpcHeader, Data inStockConfirmRequest, WmsInboundDao wmsInboundDao, WmsInboundItemDao wmsInboundItemDao,InBoundOrderDao inBoundOrderDao) {
        this.rpcHeader = rpcHeader;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.wmsInboundDao = wmsInboundDao;
        this.wmsInboundItemDao = wmsInboundItemDao;
        this.inBoundOrderDao = inBoundOrderDao;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start to SyncWmsInboundInfoToDBTask params: wmsInStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(inStockConfirmRequest));
        String[] orderNoArgs = inStockConfirmRequest.getOrderNo().split("_");
        String projectPrefix = orderNoArgs[1];
        InboundOrder inboundOrder = inBoundOrderDao.getInboundRecordByOrderNo(inStockConfirmRequest.getOrderNo(),projectPrefix);
        int totalQuantity = 0; //入库确认的总数量
        List<String> productCodeList = new ArrayList<>(); //入库确认记录中所有的productCode

        //1. 入库记录明细表写流水
        List<WmsIntboundRecordItem> wmsIntboundRecordItemList = new ArrayList<>(); //入库确认的明细列表
        //2、调用基础模块的商品的grpc查询项目信息
        ProductServiceGrpc.ProductServiceBlockingStub productService = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
        for (StockInDetailDto record : inStockConfirmRequest.getStockInDetails()) {
           //修改成根据wmscode查到分销的code
            ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(inboundOrder.getProjectId()).setProductWmsCode(record.getCargoCode()).build();
            ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
            ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();
            productCodeList.add(productBusiness.getProductModel());
            for (StocksQtyDto stocksQtyDto : record.getStocksQty()) {
                totalQuantity += stocksQtyDto.getQuantity();
                WmsInventoryType wmsInventoryType = WmsInventoryType.getInventoryTypeByNumValue(stocksQtyDto.getInventoryType()); //获取wms的出库类型
                WmsIntboundRecordItem wmsIntboundRecordItem = createInboundRecordItem(productBusiness, wmsInventoryType, stocksQtyDto.getQuantity(), inStockConfirmRequest.getInboundBatchNo(),inboundOrder);
                wmsIntboundRecordItemList.add(wmsIntboundRecordItem);
            }
        }

        try {
            wmsInboundItemDao.insertWmsInboundInfo(wmsIntboundRecordItemList);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
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
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }

        logger.info("#traceId={}# [OUT] get SyncWmsInboundInfoToDBTask.run() to recordWmsSystemInfo success", rpcHeader.getTraceId());
    }

    private WmsIntboundRecordItem createInboundRecordItem( ProductStructure.ProductBusiness productBusiness, WmsInventoryType wmsInventoryType, int quantity, String wmsInboundBatchNo,InboundOrder inboundOrder){
        WmsIntboundRecordItem wmsIntboundRecordItem = new WmsIntboundRecordItem();
        wmsIntboundRecordItem.setProjectId(String.valueOf(inboundOrder.getProjectId()));
        wmsIntboundRecordItem.setPurchaseType(inboundOrder.getPurchaseType());
        wmsIntboundRecordItem.setInventoryType(wmsInventoryType.getInventoryType());
        wmsIntboundRecordItem.setPurchaseOrderNo(inboundOrder.getPurchaseOrderNo());
        wmsIntboundRecordItem.setGongxiaoInboundOrderNo(inboundOrder.getGongxiaoInboundOrderNo());
        wmsIntboundRecordItem.setWmsInboundOrderNo(wmsInboundBatchNo);
        wmsIntboundRecordItem.setWarehouseId(inboundOrder.getWarehouseId());
        wmsIntboundRecordItem.setWarehouseName(inboundOrder.getWarehouseName());
        wmsIntboundRecordItem.setProductId(String.valueOf(productBusiness.getProductBasicId()));
        wmsIntboundRecordItem.setProductCode(productBusiness.getProductModel());
        wmsIntboundRecordItem.setProductName(productBusiness.getProductName());
        wmsIntboundRecordItem.setInStockQuantity(quantity);
        wmsIntboundRecordItem.setCreateTime(new Date());
        return wmsIntboundRecordItem;
    }
}
