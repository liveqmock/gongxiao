package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.inventory.account.microservice.ProductInventoryStructure;
import com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.OutboundOrderStatusEnum;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.type.WmsOrderType;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderItemDao;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrder;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StockOutOrderConfirmItemDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StockOutOrderConfirmOrderItemDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.outstockconfirm.Data;
import com.yhglobal.gongxiao.warehouse.model.sales.OutboundNotificationBackItem;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.XpsWarehouseNotifyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 将wms出库确认信息同步到仓储模块：确认批次(WMS没有携带批次信息 需自行算法分配), 和更新入库数量
 */
public class SyncWmsOutboundInfoToStockTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsOutboundInfoToStockTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private Data outStockConfirmRequest; //wms反馈的出库信息

    private OutboundOrder olderRecord; //db中当前出库单

    private OutBoundOrderDao outBoundOrderDao;

    private OutBoundOrderItemDao outBoundOrderItemDao;

    private WarehouseConfig warehouseConfig;

    public SyncWmsOutboundInfoToStockTask(GongxiaoRpc.RpcHeader rpcHeader, Data outStockConfirmRequest, OutboundOrder olderRecord, OutBoundOrderDao outBoundOrderDao, OutBoundOrderItemDao outBoundOrderItemDao, WarehouseConfig warehouseConfig) {
        this.rpcHeader = rpcHeader;
        this.outStockConfirmRequest = outStockConfirmRequest;
        this.olderRecord = olderRecord;
        this.outBoundOrderDao = outBoundOrderDao;
        this.outBoundOrderItemDao = outBoundOrderItemDao;
        this.warehouseConfig = warehouseConfig;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start SyncWmsOutboundInfoToStockTask.run() params: wmsOutStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(outStockConfirmRequest));
        try {
            String projectPrefix;
            if (olderRecord.getGongxiaoOutboundOrderNo().startsWith("SOOUT")){     //为了兼容之前做的单，所以做了此判断（待删除）
                projectPrefix = "shaver";
            }else {
                projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(olderRecord.getProjectId()));
            }

            List<StockOutOrderConfirmOrderItemDto> stockOutOrderConfirmOrderItemDtos = outStockConfirmRequest.getOrderItems();
            List<ProductInventoryStructure.ProductInventory> inventoryList = new ArrayList<>();
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

                //调用基础模块的商品的grpc查询项目信息
                ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                for (StockOutOrderConfirmItemDto item : record.getItems()) { //遍历同种商品的不同品质
                    ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(Long.parseLong(olderRecord.getProjectId())).setProductWmsCode(record.getItemCode()).build();
                    ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
                    ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();
                    itemStockQuantity += item.getQuantity();
                    WmsInventoryType wmsInventoryType = WmsInventoryType.getInventoryTypeByNumValue(item.getInventoryType()); //获取wms的出库类型
                    switch (wmsInventoryType) {
                        case COMMON_GOOD_MACHINE: //可销售库存
                            outStockQuantity += item.getQuantity();
                            createOutboundItem(outboundOrderItemNo, productBusiness.getProductModel(), wmsInventoryType, item.getQuantity(), inventoryList, projectPrefix);
                            break;
                        case DEFECTIVE: //残次
                            imperfectQuantity += item.getQuantity();
                            createOutboundItem(outboundOrderItemNo, productBusiness.getProductModel(), wmsInventoryType, item.getQuantity(), inventoryList, projectPrefix);
                            break;
                        case ENGINE_DAMAGE: //机损
                            imperfectQuantity += item.getQuantity();
                            createOutboundItem(outboundOrderItemNo, productBusiness.getProductModel(), wmsInventoryType, item.getQuantity(), inventoryList, projectPrefix);
                            break;
                        case BOX_DAMAGE: //箱损
                            imperfectQuantity += item.getQuantity();
                            createOutboundItem(outboundOrderItemNo, productBusiness.getProductModel(), wmsInventoryType, item.getQuantity(), inventoryList, projectPrefix);
                            break;
                        case FROZEN_STOCK: //冻结库存
                            imperfectQuantity += item.getQuantity();
                            createOutboundItem(outboundOrderItemNo, productBusiness.getProductModel(), wmsInventoryType, item.getQuantity(), inventoryList, projectPrefix);
                            break;
                        case TRANSPORTATION_INVENTORY: //在途库存
                            outTransitQuantity += item.getQuantity();
                            createOutboundItem(outboundOrderItemNo, productBusiness.getProductModel(), wmsInventoryType, item.getQuantity(), inventoryList, projectPrefix);
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
            if (olderRecord.getTotalQuantity() == olderRecord.getRealOutStockQuantity() + totalOutQuantity) { //预约出库数量 = 原有的实际出库数量+新的出库数量
                logger.info("#traceId={}# close outboundOrder: outboundOrderNo={} outboundOrderStatus=closed", rpcHeader.getTraceId(), olderRecord.getGongxiaoOutboundOrderNo());
                outboundOrder.setOrderStatus(OutboundOrderStatusEnum.OUTBOUND_ORDER_DELIVER_FINISH.getStatus());              //出库完成
            } else {
                outboundOrder.setOrderStatus(OutboundOrderStatusEnum.OUTBOUND_ORDER_DELIVER_PART.getStatus());              //出库未完成
            }

            //更新出库单汇总表
            modifyAOutboundInfoAccordingWms(rpcHeader, outboundOrder, projectPrefix);

            //下面代码仅为调试inventory的流水中存在有transactionAmount为0的问题 待删除
            for (ProductInventoryStructure.ProductInventory tmp : inventoryList) {
                if (tmp.getOnHandQuantity() == 0) {
                    logger.warn("======> got zero TransactionAmount: ProductInventoryRow={}", JSON.toJSONString(tmp));
                }
            }

            //修改库存的销售占用数量
            //调用库存中心的grpc服务
            InventorysyncServiceGrpc.InventorysyncServiceBlockingStub inventorysyncService = WarehouseRpcStubStore.getRpcStub(InventorysyncServiceGrpc.InventorysyncServiceBlockingStub.class);
            InventorysyncStructure.SyncOutboundRequest.Builder syncOutboundRequest = InventorysyncStructure.SyncOutboundRequest.newBuilder();
            syncOutboundRequest.setRpcHeader(rpcHeader);
            syncOutboundRequest.setSalesOrderNo(olderRecord.getSalesOrderNo());
            syncOutboundRequest.setGonxiaoOutboundOrderNo(olderRecord.getGongxiaoOutboundOrderNo());
            syncOutboundRequest.setOrderType(olderRecord.getOutboundType());
            syncOutboundRequest.addAllProductInventoryList(inventoryList);
            syncOutboundRequest.setProjectId(olderRecord.getProjectId());
            inventorysyncService.syncOutboundInventory(syncOutboundRequest.build());

            //通知销售模块wms出库
            if (olderRecord.getGongxiaoOutboundOrderNo().contains(BizNumberType.STOCK_SOOUT_ORDER_NO.getPrefix())){
                notifySales(projectPrefix);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

        logger.info("#traceId={}# [OUT] get SyncWmsOutboundInfoToStockTask.run() success", rpcHeader.getTraceId());
    }

    //1. 修改出库明细    2. 汇总库存的出库数量
    private void createOutboundItem(String outboundOrderItemNo, String productCode, WmsInventoryType wmsInventoryType, int wmsQuantity, List<ProductInventoryStructure.ProductInventory> inventoryList, String projectPrefix) {
        try {
            //1. 修改出库明细
            OutboundOrderItem itemRecord = outBoundOrderItemDao.getOutboundOrderItemByItemNo(outboundOrderItemNo, projectPrefix); //根据outboundOrderItemNo 查出对应的出库明细记录
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
            modifyItemAccordingWms(itemRecord.getRowId(), itemRecord.getGongxiaoOutboundOrderNo(), outStockQuantityIncrement, imperfectQuantityIncrement, realOutStockQuantityIncrement, projectPrefix);  //更新出库单明细信息

            //2. 汇总库存变更信息 以便更新销售占用数量
            ProductInventoryStructure.ProductInventory.Builder productInventory = ProductInventoryStructure.ProductInventory.newBuilder()
                    .setPurchaseType(itemRecord.getPurchaseType())
                    .setInventoryStatus(wmsInventoryType.getInventoryType())
                    .setProjectId(Long.parseLong(olderRecord.getProjectId()))
                    .setBatchNo(itemRecord.getBatchNo())
                    .setProductCode(productCode)
                    .setWarehouseId(olderRecord.getWarehouseId())
                    .setOnHandQuantity(wmsQuantity)
                    .setOnSalesOrderQuantity(wmsQuantity);
            inventoryList.add(productInventory.build()); //汇总到inventoryList
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    //更新出库单明细表 outboundOrderNo参数仅为打印日记用
    public void modifyItemAccordingWms(long rowId, String outboundOrderNo, int outStockQuantityIncrement, int imperfectQuantityIncrement, int realOutStockQuantityIncrement, String projectPrefix) {
        logger.info("[IN][modifyAccordingWms] params: OutboundOrderItemRowId={} outboundOrderNo={} outStockQuantityIncrement={} imperfectQuantityIncrement={} realOutStockQuantityIncrement={}", rowId, outboundOrderNo, outStockQuantityIncrement, imperfectQuantityIncrement, realOutStockQuantityIncrement);
        int maxTryTimes = 12; //当前最大尝试的次数
        boolean updateSuccess = false; //标记是否更新DB成功
        while (!updateSuccess && maxTryTimes > 0) { //若尚未更新db成功 并且剩余重试数大于0
            try {
                OutboundOrderItem itemRecord = outBoundOrderItemDao.getOutboundOrderItemById(rowId, projectPrefix);
                int targetOutStockQuantity = itemRecord.getOutStockQuantity() + outStockQuantityIncrement;
                int targetImperfectQuantity = itemRecord.getImperfectQuantity() + imperfectQuantityIncrement;
                int targetRealOutStockQuantity = itemRecord.getRealOutStockQuantity() + realOutStockQuantityIncrement;
                long dataVersion = itemRecord.getDataVersion();
                int row = outBoundOrderItemDao.updateOutboundOrderItemInfo(rowId, outboundOrderNo, targetOutStockQuantity, targetImperfectQuantity, targetRealOutStockQuantity, dataVersion, projectPrefix);
                if (row == 1) {
                    updateSuccess = true;
                    logger.info("modifyAccordingWms success: OutboundOrderNo={}", outboundOrderNo);
                }
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
            maxTryTimes--;
        }
        if (!updateSuccess && maxTryTimes <= 0) {
            logger.info("#traceId={}# fail to modifyAccordingWms after maxTryTimes: OutboundOrderNo={}", outboundOrderNo);
        }
    }

    //更新出库单汇总表
    public void modifyAOutboundInfoAccordingWms(GongxiaoRpc.RpcHeader rpcHeader, OutboundOrder outboundOrder, String projectPrefix) {
        logger.info("#traceId={}# [IN][modifyAOutboundInfoAccordingWms] params: outboundOrder={}", rpcHeader.getTraceId(), JSON.toJSONString(outboundOrder));
        try {
            OutboundOrder order = outBoundOrderDao.getOutboundRecordByOrderNo(outboundOrder.getGongxiaoOutboundOrderNo(), outboundOrder.getConnectedProduct(), projectPrefix);
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
            outBoundOrderDao.updateOutboundOrderByNo(outboundOrder, projectPrefix);
            logger.info("#traceId={}# [OUT] get modifyAOutboundInfoAccordingWms success: resultList.size={}", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    //通知销售模块wms出库
    private void notifySales(String projectPrefix) {
        logger.info("#traceId={}# [IN][run] start SyncWmsOutboundInfoToStockTask.notifySales() ", rpcHeader.getTraceId());

        try {
            //调用基础模块的SourceChannel,获取渠道信息
            String channelId = null;
            if (olderRecord.getGongxiaoOutboundOrderNo().contains("shaver")){
                channelId = WmsSourceChannel.CHANNEL_SHAVER.getChannelId();
            }else if (olderRecord.getGongxiaoOutboundOrderNo().contains("PHTM")){
                channelId = WmsSourceChannel.CHANNEL_TMALL.getChannelId();
            }else if(olderRecord.getGongxiaoOutboundOrderNo().contains("JMGO")){
                channelId = WmsSourceChannel.CHANNEL_JMGO.getChannelId();
            }else {
                channelId = WmsSourceChannel.CHANNEL_TUANGOU.getChannelId();
            }
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder().setRpcHeader(rpcHeader).setXpsChannelId(channelId).build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();

            if (outStockConfirmRequest.getOrderType().equals(String.valueOf(WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode()))) {  //普通出库需要通知销售模块
                List<OutboundNotificationBackItem> outboundNotificationBackItems = new ArrayList<>();
                int realTotalQuantity = 0;

                //调用基础模块的商品的grpc查询项目信息
                ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                for (StockOutOrderConfirmOrderItemDto record : outStockConfirmRequest.getOrderItems()) {
                    OutboundNotificationBackItem itemrcord = new OutboundNotificationBackItem();
                    //根据wmscode查询
                    ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(Long.parseLong(olderRecord.getProjectId())).setProductWmsCode(record.getItemCode()).build();
                    ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
                    ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();
                    itemrcord.setProductCode(productBusiness.getProductModel());
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

                List<OutboundOrder> outboundOrderList = outBoundOrderDao.judeOrderIfFinish(olderRecord.getGongxiaoOutboundOrderNo(), projectPrefix);
                int plantotalQuantity = 0;
                for (OutboundOrder outboundOrder : outboundOrderList) {
                    plantotalQuantity += outboundOrder.getTotalQuantity();
                }

                if (plantotalQuantity > realTotalQuantity + olderRecord.getRealOutStockQuantity()) {   //预约出库数量 > 刚出库的数量+原来实际出库数量 未完成
                    logger.info("WMS部分出库,gongxiaoOutboundOrderNo={},salesOrderNo={}", olderRecord.getGongxiaoOutboundOrderNo(), olderRecord.getSalesOrderNo());
                } else {
                    try {
                        logger.info("通知销售模块WMS出库完成,gongxiaoOutboundOrderNo={},salesOrderNo={}", olderRecord.getGongxiaoOutboundOrderNo(), olderRecord.getSalesOrderNo());
                        XpsWarehouseNotifyManager.itemsOutboundFinished(sourceChannel.getXpsWarehouseNotifyUrl(), olderRecord.getGongxiaoOutboundOrderNo(), Long.parseLong(olderRecord.getProjectId()));
//                        salesScheduleDeliveryService.outboundOrderFinished(rpcHeader, olderRecord.getGongxiaoOutboundOrderNo());
                        logger.info("通知销售模块WMS出库完成成功,gongxiaoOutboundOrderNo={},salesOrderNo={}", olderRecord.getGongxiaoOutboundOrderNo(), olderRecord.getSalesOrderNo());
                    } catch (Exception e) {
                        logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                    }

                }

            }
            logger.info("#traceId={}# [OUT] get SyncWmsOutboundInfoToStockTask.run() success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

}
