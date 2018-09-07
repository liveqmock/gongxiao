package com.yhglobal.gongxiao.wmscomfirm.service;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.SyncEasEnum;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrder;
import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrderItem;
import com.yhglobal.gongxiao.wmscomfirm.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.wmscomfirm.dao.OutBoundOrderItemDao;
import com.yhglobal.gongxiao.wmscomfirm.dao.WmsOutboundDao;
import com.yhglobal.gongxiao.wmscomfirm.dao.WmsOutboundItemDao;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.StockOutOrderConfirmItemDto;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.StockOutOrderConfirmOrderItemDto;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.WmsOutboundRecord;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.WmsOutboundRecordItem;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.outstockconfirm.Data;
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

    private GongxiaoRpc.RpcHeader rpcHeader;

    private Data outStockConfirmRequest; //wms反馈的出库信息

    private OutBoundOrderItemDao outBoundOrderItemDao;

    private WmsOutboundDao wmsOutboundDao;

    private WmsOutboundItemDao wmsOutboundItemDao;

    private OutBoundOrderDao outBoundOrderDao;

    public SyncWmsOutboundInfoToDBTask(GongxiaoRpc.RpcHeader rpcHeader, Data outStockConfirmRequest, OutBoundOrderItemDao outBoundOrderItemDao, WmsOutboundDao wmsOutboundDao, WmsOutboundItemDao wmsOutboundItemDao,OutBoundOrderDao outBoundOrderDao) {
        this.rpcHeader = rpcHeader;
        this.outStockConfirmRequest = outStockConfirmRequest;
        this.outBoundOrderItemDao = outBoundOrderItemDao;
        this.wmsOutboundDao = wmsOutboundDao;
        this.wmsOutboundItemDao = wmsOutboundItemDao;
        this.outBoundOrderDao = outBoundOrderDao;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start SyncWmsOutboundInfoToDBTask: wmsOutStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(outStockConfirmRequest));

        try{
            String projectPrefix;
            if (outStockConfirmRequest.getOrderNo().startsWith("SOOUT")){     //为了兼容之前做的单，所以做了此判断（待删除）
                projectPrefix = "shaver";
            }else {
                String[] orderNoArgs = outStockConfirmRequest.getOrderNo().split("_");
                projectPrefix = orderNoArgs[1];
            }


            OutboundOrder outboundOrder = outBoundOrderDao.getOutboundRecordByGoxiaoOutNo(outStockConfirmRequest.getOrderNo(), projectPrefix);

            List<WmsOutboundRecordItem> wmsOutboundRecordItemList = new ArrayList<>(); //出库明细

            List<String> productCodeList = new ArrayList<>();
            int outStockQuantity = 0;
            //调用基础模块的商品的grpc查询项目信息
            ProductServiceGrpc.ProductServiceBlockingStub productService = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            for (StockOutOrderConfirmOrderItemDto record : outStockConfirmRequest.getOrderItems()) { //遍历wms出库确认信息
                String outboundOrderItemNo = record.getItemNo(); //获得WMS反馈的ItemNo, 当前传的是OutboundOrderItem.outboundOrderItemNo字段
                //根据wmscode查询
                ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(Long.parseLong(outboundOrder.getProjectId())).setProductWmsCode(record.getItemCode()).build();
                ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
                ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();
                for (StockOutOrderConfirmItemDto item : record.getItems()) {
                    outStockQuantity += item.getQuantity();
                    productCodeList.add(item.getProduceCode());
                    WmsInventoryType wmsInventoryType = WmsInventoryType.getInventoryTypeByNumValue(item.getInventoryType()); //获取wms的出库类型
                    WmsOutboundRecordItem wmsOutboundRecordItem = createWmsOutboundItem(outboundOrderItemNo, productBusiness, wmsInventoryType, item.getQuantity(), outStockConfirmRequest.getOutBizCode(),outboundOrder,projectPrefix);
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
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
            logger.info("#traceId={}# [OUT] syncWmsOutboundInfoToDBTask.run() success", rpcHeader.getTraceId());
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    private WmsOutboundRecordItem createWmsOutboundItem(String outboundOrderItemNo, ProductStructure.ProductBusiness productBusiness, WmsInventoryType wmsInventoryType, int quantity, String wmsOutBizCode,OutboundOrder outboundOrder,String projectPrefix) {
        OutboundOrderItem outboundOrderItem = outBoundOrderItemDao.getOutboundOrderItemByItemNo(outboundOrderItemNo,projectPrefix); //根据outboundOrderItemNo 查出对应的出库明细记录

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
        wmsOutboundRecordItem.setProductId(String.valueOf(productBusiness.getProductBasicId()));
        wmsOutboundRecordItem.setProductCode(productBusiness.getProductModel());
        wmsOutboundRecordItem.setProductName(productBusiness.getProductName());
        wmsOutboundRecordItem.setProductUnit("台"); //TODO: 商品单位暂时硬编码
        wmsOutboundRecordItem.setCreateTime(new Date());
        return wmsOutboundRecordItem;
    }

}
