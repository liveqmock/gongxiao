package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.service.InventorySyncService;
import com.yhglobal.gongxiao.purchase.bo.OutboundNotificationBackItem;
import com.yhglobal.gongxiao.sales.service.SalesScheduleDeliveryService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.service.OutboundService;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehousemanagement.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.OutBoundOrderItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmOrderItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.OutboundOrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 将wms出库确认信息同步到仓储模块：确认批次(WMS没有携带批次信息 需自行算法分配), 和更新入库数量
 */
public class SyncWmsOutboundInfoToStockTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsOutboundInfoToStockTask.class);

    private RpcHeader rpcHeader;

    private Data outStockConfirmRequest; //wms反馈的出库信息

    private OutboundOrder olderRecord; //db中当前出库单

    private OutBoundOrderDao outBoundOrderDao;

    private OutBoundOrderItemDao outBoundOrderItemDao;

    private ProductService productService;

    private SalesScheduleDeliveryService salesScheduleDeliveryService;

    private InventorySyncService inventorySyncService;

    public SyncWmsOutboundInfoToStockTask(RpcHeader rpcHeader, ProductService productService, Data outStockConfirmRequest, OutboundOrder olderRecord, OutBoundOrderDao outBoundOrderDao, OutBoundOrderItemDao outBoundOrderItemDao, SalesScheduleDeliveryService salesScheduleDeliveryService, InventorySyncService inventorySyncService) {
        this.rpcHeader = rpcHeader;
        this.productService = productService;
        this.outStockConfirmRequest = outStockConfirmRequest;
        this.olderRecord = olderRecord;
        this.outBoundOrderDao = outBoundOrderDao;
        this.outBoundOrderItemDao = outBoundOrderItemDao;
        this.salesScheduleDeliveryService = salesScheduleDeliveryService;
        this.inventorySyncService = inventorySyncService;

    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start SyncWmsOutboundInfoToStockTask.run() params: wmsOutStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(outStockConfirmRequest));
        List<StockOutOrderConfirmOrderItemDto> stockOutOrderConfirmOrderItemDtos = outStockConfirmRequest.getOrderItems();
        List<ProductInventory> inventoryList = new ArrayList<>();
        int totalImperfectQuantity = 0; //总残次品数量
        int totaloutStockQuantity = 0; //总出库数量
        int totalOutTransitQuantity = 0;//总在途数量
        int totalOutQuantity = 0; //总可用数量(等于良品数量)
        //注: WMS的反馈中 有IsCompleted字段标记出库单是否已完成 但这个字段不准确，不能逻辑依赖它
        for (StockOutOrderConfirmOrderItemDto record : stockOutOrderConfirmOrderItemDtos) { //遍历wms出库确认信息

            String outboundOrderItemNo = record.getItemNo(); //获得WMS反馈的ItemNo, 当前传的是OutboundOrderItem.outboundOrderItemNo字段

            int imperfectQuantity = 0;
            int outStockQuantity = 0;
            int outTransitQuantity = 0;
            int itemStockQuantity = 0;

            for (StockOutOrderConfirmItemDto item : record.getItems()) { //遍历同种商品的不同品质
                ProductBasic productBasic = productService.getByWmsProductCode(rpcHeader, record.getItemCode()); //wmscode转成分销商品编码
                itemStockQuantity += item.getQuantity();
                WmsInventoryType wmsInventoryType = WmsInventoryType.getInventoryTypeByNumValue(item.getInventoryType()); //获取wms的出库类型
                switch (wmsInventoryType) {
                    case COMMON_GOOD_MACHINE: //可销售库存
                        outStockQuantity += item.getQuantity();
                        createOutboundItem(outboundOrderItemNo, productBasic.getProductCode(), wmsInventoryType, item.getQuantity(), inventoryList);
                        break;
                    case DEFECTIVE: //残次
                        imperfectQuantity += item.getQuantity();
                        createOutboundItem(outboundOrderItemNo, productBasic.getProductCode(), wmsInventoryType, item.getQuantity(), inventoryList);
                        break;
                    case ENGINE_DAMAGE: //机损
                        imperfectQuantity += item.getQuantity();
                        createOutboundItem(outboundOrderItemNo, productBasic.getProductCode(), wmsInventoryType, item.getQuantity(), inventoryList);
                        break;
                    case BOX_DAMAGE: //箱损
                        imperfectQuantity += item.getQuantity();
                        createOutboundItem(outboundOrderItemNo, productBasic.getProductCode(), wmsInventoryType, item.getQuantity(), inventoryList);
                        break;
                    case FROZEN_STOCK: //冻结库存
                        imperfectQuantity += item.getQuantity();
                        createOutboundItem(outboundOrderItemNo, productBasic.getProductCode(), wmsInventoryType, item.getQuantity(), inventoryList);
                        break;
                    case TRANSPORTATION_INVENTORY: //在途库存
                        outTransitQuantity += item.getQuantity();
                        createOutboundItem(outboundOrderItemNo, productBasic.getProductCode(), wmsInventoryType, item.getQuantity(), inventoryList);
                        break;
                    default:
                        logger.error("#traceId={}# SyncWmsOutboundInfoToStockTask,item.product={},item.quantity={},item.inventoryType={} 不符合分销系统的库存类型 ", item.getProduceCode(), item.getQuantity(), item.getInventoryType(), rpcHeader.getTraceId());
                }

            }
            totalImperfectQuantity += imperfectQuantity;
            totaloutStockQuantity += outStockQuantity;
            totalOutTransitQuantity += outTransitQuantity;
            totalOutQuantity += itemStockQuantity;

        }


        OutboundOrder outboundOrder = new OutboundOrder();
        outboundOrder.setGongxiaoOutboundOrderNo(outStockConfirmRequest.getOrderNo());
        outboundOrder.setOutboundOrderItemNo(olderRecord.getOutboundOrderItemNo());
        outboundOrder.setConnectedProduct(olderRecord.getConnectedProduct());
        outboundOrder.setImperfectQuantity(totalImperfectQuantity);
        outboundOrder.setOutStockQuantity(totaloutStockQuantity);
        outboundOrder.setTransferQuantity(totalOutTransitQuantity);
        outboundOrder.setRealOutStockQuantity(totalOutQuantity);

        //判断是否出库完成
        if (olderRecord.getTotalQuantity() == olderRecord.getRealOutStockQuantity() + totalOutQuantity) { //预约入库数量 = 原有的实际入库数量+新的出库数量
            logger.info("#traceId={}# close outboundOrder: outboundOrderNo={} outboundOrderStatus=closed", rpcHeader.getTraceId(), olderRecord.getGongxiaoOutboundOrderNo());
            outboundOrder.setOrderStatus(OutboundOrderStatusEnum.OUTBOUND_ORDER_DELIVER_FINISH.getStatus());              //出库完成
        } else {
            outboundOrder.setOrderStatus(OutboundOrderStatusEnum.OUTBOUND_ORDER_DELIVER_PART.getStatus());              //出库未完成
        }

        //更新出库单汇总表
        modifyAOutboundInfoAccordingWms(rpcHeader, outboundOrder);

        //下面代码仅为调试inventory的流水中存在有transactionAmount为0的问题 待删除
        for (ProductInventory tmp : inventoryList) {
            if (tmp.getOnHandQuantity() == 0) {
                logger.warn("======> got zero TransactionAmount: ProductInventoryRow={}", JSON.toJSONString(tmp));
            }
        }

        //修改库存的销售占用数量
        inventorySyncService.syncOutboundInventory(rpcHeader, inventoryList, olderRecord.getSalesOrderNo(), olderRecord.getGongxiaoOutboundOrderNo(), olderRecord.getOutboundType());

        //通知销售模块wms出库
        notifySales();

        logger.info("#traceId={}# [OUT] get SyncWmsOutboundInfoToStockTask.run() success", rpcHeader.getTraceId());
    }

    //1. 修改出库明细    2. 汇总库存的出库数量
    private void createOutboundItem(String outboundOrderItemNo, String productCode, WmsInventoryType wmsInventoryType, int wmsQuantity, List<ProductInventory> inventoryList) {

        //1. 修改出库明细
        OutboundOrderItem itemRecord = outBoundOrderItemDao.getOutboundOrderItemByItemNo(outboundOrderItemNo); //根据outboundOrderItemNo 查出对应的出库明细记录
        int outStockQuantityIncrement;
        int imperfectQuantityIncrement;
        if (wmsInventoryType == WmsInventoryType.COMMON_GOOD_MACHINE) { //可用库存
            outStockQuantityIncrement = wmsQuantity;
            imperfectQuantityIncrement = 0;
        } else { //不良品
            outStockQuantityIncrement = 0;
            imperfectQuantityIncrement = wmsQuantity;
        }
        int realOutStockQuantityIncrement = wmsQuantity;
        modifyItemAccordingWms(itemRecord.getRowId(), itemRecord.getGongxiaoOutboundOrderNo(), outStockQuantityIncrement, imperfectQuantityIncrement, realOutStockQuantityIncrement);  //更新出库单明细信息

        //2. 汇总库存变更信息 以便更新销售占用数量
        ProductInventory productInventory = new ProductInventory();
        productInventory.setPurchaseType(itemRecord.getPurchaseType());
        productInventory.setInventoryStatus(wmsInventoryType.getInventoryType());
        productInventory.setProjectId(Long.parseLong(olderRecord.getProjectId()));
        productInventory.setBatchNo(itemRecord.getBatchNo());
        productInventory.setProductCode(productCode);
        productInventory.setWarehouseId(olderRecord.getWarehouseId());
        productInventory.setOnHandQuantity(wmsQuantity);
        productInventory.setOnSalesOrderQuantity(wmsQuantity);
        inventoryList.add(productInventory); //汇总到inventoryList

    }

    //更新出库单明细表 outboundOrderNo参数仅为打印日记用
    public void modifyItemAccordingWms(long rowId, String outboundOrderNo, int outStockQuantityIncrement, int imperfectQuantityIncrement, int realOutStockQuantityIncrement) {
        logger.info("[IN][modifyAccordingWms] params: OutboundOrderItemRowId={} outboundOrderNo={} outStockQuantityIncrement={} imperfectQuantityIncrement={} realOutStockQuantityIncrement={}", rowId, outboundOrderNo, outStockQuantityIncrement, imperfectQuantityIncrement, realOutStockQuantityIncrement);
        int maxTryTimes = 12; //当前最大尝试的次数
        boolean updateSuccess = false; //标记是否更新DB成功
        while (!updateSuccess && maxTryTimes > 0) { //若尚未更新db成功 并且剩余重试数大于0
            try {
                OutboundOrderItem itemRecord = outBoundOrderItemDao.getOutboundOrderItemById(rowId);
                int targetOutStockQuantity = itemRecord.getOutStockQuantity() + outStockQuantityIncrement;
                int targetImperfectQuantity = itemRecord.getImperfectQuantity() + imperfectQuantityIncrement;
                int targetRealOutStockQuantity = itemRecord.getRealOutStockQuantity() + realOutStockQuantityIncrement;
                long dataVersion = itemRecord.getDataVersion();
                int row = outBoundOrderItemDao.updateOutboundOrderItemInfo(rowId, outboundOrderNo, targetOutStockQuantity, targetImperfectQuantity, targetRealOutStockQuantity, dataVersion);
                if (row == 1) {
                    updateSuccess = true;
                    logger.info("modifyAccordingWms success: OutboundOrderNo={}", outboundOrderNo);
                }
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
            maxTryTimes --;
        }
        if (!updateSuccess && maxTryTimes<=0) {
            logger.info("#traceId={}# fail to modifyAccordingWms after maxTryTimes: OutboundOrderNo={}", outboundOrderNo);
        }
    }

    //更新出库单汇总表
    public void modifyAOutboundInfoAccordingWms(RpcHeader rpcHeader, OutboundOrder outboundOrder) {
        logger.info("#traceId={}# [IN][modifyAOutboundInfoAccordingWms] params: outboundOrder={}", rpcHeader.traceId, JSON.toJSONString(outboundOrder));
        try {
            OutboundOrder order = outBoundOrderDao.getOutboundRecordByOrderNo(outboundOrder.getGongxiaoOutboundOrderNo(), outboundOrder.getConnectedProduct());
            String olderTraceLog = order.getTracelog();
            TraceLog traceLog = new TraceLog();
            Date dateTime = new Date();
            traceLog.setOpTime(dateTime.getTime());
            traceLog.setOpUid(rpcHeader.getUid());
            traceLog.setOpName(rpcHeader.getUsername());
            traceLog.setContent("WMS已经发货");
            outboundOrder.setOutStockQuantity(order.getOutStockQuantity() + outboundOrder.getOutStockQuantity());
            outboundOrder.setImperfectQuantity(order.getImperfectQuantity() + outboundOrder.getImperfectQuantity());
            outboundOrder.setTransferQuantity(order.getTransferQuantity() + outboundOrder.getTransferQuantity());
            outboundOrder.setCanceledQuantity(order.getCanceledQuantity() + outboundOrder.getCanceledQuantity());
            outboundOrder.setRealOutStockQuantity(order.getRealOutStockQuantity() + outboundOrder.getRealOutStockQuantity());
            outboundOrder.setTracelog(TraceLogUtil.appendTraceLog(olderTraceLog, traceLog));
            outBoundOrderDao.updateOutboundOrderByNo(outboundOrder);
            logger.info("#traceId={}# [OUT] get modifyAOutboundInfoAccordingWms success: resultList.size={}", rpcHeader.traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    //通知销售模块wms出库
    private void notifySales() {
        logger.info("#traceId={}# [IN][run] start SyncWmsOutboundInfoToStockTask.notifySales() ", rpcHeader.getTraceId());

        try {
            if (outStockConfirmRequest.getOrderType().equals(String.valueOf(WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode()))) {  //普通出库需要通知销售模块
                List<OutboundNotificationBackItem> outboundNotificationBackItems = new ArrayList<>();
                int realTotalQuantity = 0;
                for (StockOutOrderConfirmOrderItemDto record : outStockConfirmRequest.getOrderItems()) {
                    OutboundNotificationBackItem itemrcord = new OutboundNotificationBackItem();
                    //            itemrcord.setBusinessItemId();
                    ProductBasic productBasic = productService.getByWmsProductCode(rpcHeader, record.getItemCode());   //wmscode转成分销商品编码
                    itemrcord.setProductCode(productBasic.getProductCode());
                    int perfectQuantity = 0;
                    int imperfectQuantity = 0;
                    for (StockOutOrderConfirmItemDto newrecord : record.getItems()) {
                        realTotalQuantity += newrecord.getQuantity();
                        itemrcord.setSignedReceiptQuantity(0);
                        if (newrecord.getInventoryType() == 1) {
                            perfectQuantity += newrecord.getQuantity();
                        } else {
                            imperfectQuantity += newrecord.getQuantity();
                        }
                    }

                    itemrcord.setImperfectQuantity(imperfectQuantity);

                    itemrcord.setOutboundQuantity(perfectQuantity);

                    outboundNotificationBackItems.add(itemrcord);

                }

                List<OutboundOrder> outboundOrderList = outBoundOrderDao.judeOrderIfFinish(olderRecord.getGongxiaoOutboundOrderNo());
                int plantotalQuantity = 0;
                for (OutboundOrder outboundOrder : outboundOrderList) {
                    plantotalQuantity += outboundOrder.getTotalQuantity();
                }

                if (plantotalQuantity > realTotalQuantity + olderRecord.getRealOutStockQuantity()) {   //预约出库数量 > 刚出库的数量+原来实际出库数量 未完成
                    logger.info("WMS部分出库,gongxiaoOutboundOrderNo={},salesOrderNo={}", olderRecord.getGongxiaoOutboundOrderNo(), olderRecord.getSalesOrderNo());
                } else {
                    try {
                        logger.info("通知销售模块WMS出库完成,gongxiaoOutboundOrderNo={},salesOrderNo={}", olderRecord.getGongxiaoOutboundOrderNo(), olderRecord.getSalesOrderNo());
                        salesScheduleDeliveryService.outboundOrderFinished(rpcHeader, olderRecord.getGongxiaoOutboundOrderNo());
                        logger.info("通知销售模块WMS出库完成成功,gongxiaoOutboundOrderNo={},salesOrderNo={}", olderRecord.getGongxiaoOutboundOrderNo(), olderRecord.getSalesOrderNo());
                    } catch (MalformedURLException e) {
                        logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                    } catch (RemoteException e) {
                        logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                    }

                }

            }
            logger.info("#traceId={}# [OUT] get SyncWmsOutboundInfoToStockTask.run() success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

}
