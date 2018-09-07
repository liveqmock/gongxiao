package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehousemanagement.dao.OutBoundOrderItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.WmsOutboundDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.WmsOutboundItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmOrderItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecord;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecordItem;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.SyncEasEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 收到WMS出库信息回告后 记录流水到DB
 */
public class SyncWmsOutboundInfoToDBTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsOutboundInfoToDBTask.class);

    private RpcHeader rpcHeader;

    private Data outStockConfirmRequest; //wms反馈的出库信息

    private OutboundOrder outboundOrder; //db中当前出库单

    private OutBoundOrderItemDao outBoundOrderItemDao;

    WmsOutboundDao wmsOutboundDao;

    WmsOutboundItemDao wmsOutboundItemDao;

    private ProductService productService;

    public SyncWmsOutboundInfoToDBTask(RpcHeader rpcHeader, Data outStockConfirmRequest, OutboundOrder outboundOrder, OutBoundOrderItemDao outBoundOrderItemDao, WmsOutboundDao wmsOutboundDao, WmsOutboundItemDao wmsOutboundItemDao, ProductService productService) {
        this.rpcHeader = rpcHeader;
        this.outStockConfirmRequest = outStockConfirmRequest;
        this.outboundOrder = outboundOrder;
        this.outBoundOrderItemDao = outBoundOrderItemDao;
        this.wmsOutboundDao = wmsOutboundDao;
        this.wmsOutboundItemDao = wmsOutboundItemDao;
        this.productService = productService;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start SyncWmsOutboundInfoToDBTask: wmsOutStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(outStockConfirmRequest));
        List<WmsOutboundRecordItem> wmsOutboundRecordItemList = new ArrayList<>(); //出库明细

        List<String> productCodeList = new ArrayList<>();
        int outStockQuantity = 0;
        for (StockOutOrderConfirmOrderItemDto record : outStockConfirmRequest.getOrderItems()) { //遍历wms出库确认信息
            String outboundOrderItemNo = record.getItemNo(); //获得WMS反馈的ItemNo, 当前传的是OutboundOrderItem.outboundOrderItemNo字段
            ProductBasic productBasic = productService.getByWmsProductCode(rpcHeader, record.getItemCode()); //wmscode转成分销商品编码
            for (StockOutOrderConfirmItemDto item : record.getItems()) {
                outStockQuantity += item.getQuantity();
                productCodeList.add(item.getProduceCode());
                WmsInventoryType wmsInventoryType = WmsInventoryType.getInventoryTypeByNumValue(item.getInventoryType()); //获取wms的出库类型
                WmsOutboundRecordItem wmsOutboundRecordItem = createWmsOutboundItem(outboundOrderItemNo, productBasic, wmsInventoryType, item.getQuantity(), outStockConfirmRequest.getOutBizCode());
                wmsOutboundRecordItemList.add(wmsOutboundRecordItem);
            }
        }

        WmsOutboundRecord wmsOutboundRecord = new WmsOutboundRecord();
        wmsOutboundRecord.setProjectId(outboundOrder.getProjectId());
        wmsOutboundRecord.setWmsOutboundOrderNo(outStockConfirmRequest.getOutBizCode()); //WMS那边记录的出库单号
        wmsOutboundRecord.setGongxiaoOutboundOrderNo(outboundOrder.getGongxiaoOutboundOrderNo());
        wmsOutboundRecord.setPurchaseOrderNo(outboundOrder.getPurchaseOrderNo());
        wmsOutboundRecord.setTmsOutboundOrderNo(outboundOrder.getTmsOrdNo());
        wmsOutboundRecord.setSalesOrderNo(outboundOrder.getSalesOrderNo());
        wmsOutboundRecord.setOutboundType(Integer.parseInt(outStockConfirmRequest.getOrderType())); //出库类型 0:不详 1:销售发货  6:采购退货
        wmsOutboundRecord.setWarehouseId(outboundOrder.getWarehouseId());
        wmsOutboundRecord.setWarehouseName(outboundOrder.getWarehouseName());
        wmsOutboundRecord.setCreateTime(new Date());
        wmsOutboundRecord.setOutStockQuantity(outStockQuantity); //已出库的商品数量(含残次品)
        wmsOutboundRecord.setEasFlag(SyncEasEnum.SYNC_EAS_FAIL.getStatus());
        wmsOutboundRecord.setProductCode(JSON.toJSONString(productCodeList)); //汇总表商品编码为所有编码数组的JSON串

        try {
            wmsOutboundDao.insertWmsOutboundInfo(wmsOutboundRecord);
            wmsOutboundItemDao.insertWmsOutboundInfo(wmsOutboundRecordItemList);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

        logger.info("#traceId={}# [OUT] syncWmsOutboundInfoToDBTask success", rpcHeader.getTraceId());
    }

    private WmsOutboundRecordItem createWmsOutboundItem(String outboundOrderItemNo, ProductBasic productBasic, WmsInventoryType wmsInventoryType, int quantity, String wmsOutBizCode) {
        OutboundOrderItem outboundOrderItem = outBoundOrderItemDao.getOutboundOrderItemByItemNo(outboundOrderItemNo); //根据outboundOrderItemNo 查出对应的出库明细记录

        WmsOutboundRecordItem wmsOutboundRecordItem = new WmsOutboundRecordItem(); //待组装的明细记录
        wmsOutboundRecordItem.setProjectId(outboundOrder.getProjectId());
        wmsOutboundRecordItem.setGongxiaoOutboundOrderNo(outboundOrder.getGongxiaoOutboundOrderNo());
        wmsOutboundRecordItem.setWmsOutboundOrderNo(wmsOutBizCode);
        wmsOutboundRecordItem.setOutboundOrderItemNo(outboundOrder.getOutboundOrderItemNo());
        wmsOutboundRecordItem.setBatchNo(outboundOrderItem.getBatchNo()); //设定批次
        wmsOutboundRecordItem.setSalesOrderNo(outboundOrder.getSalesOrderNo());
        wmsOutboundRecordItem.setWarehouseId(outboundOrder.getWarehouseId());
        wmsOutboundRecordItem.setWarehouseName(outboundOrder.getWarehouseName());
        wmsOutboundRecordItem.setPurchaseType(outboundOrderItem.getPurchaseType()); //设置采购类型
        wmsOutboundRecordItem.setInventoryType(wmsInventoryType.getInventoryType());
        wmsOutboundRecordItem.setOutStockQuantity(quantity);
        wmsOutboundRecordItem.setProductId(String.valueOf(productBasic.getId()));
        wmsOutboundRecordItem.setProductCode(productBasic.getProductCode());
        wmsOutboundRecordItem.setProductName(productBasic.getProductName());
        wmsOutboundRecordItem.setProductUnit("台"); //TODO: 商品单位暂时硬编码
        wmsOutboundRecordItem.setCreateTime(new Date());
        return wmsOutboundRecordItem;
    }

}
