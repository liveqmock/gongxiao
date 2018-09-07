package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.diaobo.AllocateStockBrandEasOrder;
import com.yhglobal.gongxiao.diaobo.AllocateStockEasOrder;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.EasResult;
import com.yhglobal.gongxiao.type.*;
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouse.dao.AllocateOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.AllocateOrderItemDao;
import com.yhglobal.gongxiao.warehouse.dao.WmsInboundDao;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrder;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrderItem;
import com.yhglobal.gongxiao.warehouse.model.WmsIntboundRecord;
import com.yhglobal.gongxiao.warehouse.model.WmsIntboundRecordItem;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.XpsWarehouseNotifyManager;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model.InboundNotificationBackItem;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model.PurchaseEasInboundModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.el.ELContext;
import javax.swing.plaf.PanelUI;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * 当收到wms入库回告信息后 通过该任务 将入库单信息推送给EAS
 */
public class NotificationPurchaseEasTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(NotificationPurchaseEasTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private WmsIntboundRecord wmsIntboundRecord;
    private List<WmsIntboundRecordItem> wmsIntboundRecordItemList;
    private WmsInboundDao wmsInboundDao;
    private AllocateOrderDao allocateOrderDao;
    private AllocateOrderItemDao allocateOrderItemDao;

    public NotificationPurchaseEasTask(GongxiaoRpc.RpcHeader rpcHeader,
                                       WmsIntboundRecord wmsIntboundRecord,
                                       List<WmsIntboundRecordItem> wmsIntboundRecordItemList,
                                       WmsInboundDao wmsInboundDao,
                                       AllocateOrderDao allocateOrderDao,
                                       AllocateOrderItemDao allocateOrderItemDao) {
        this.rpcHeader = rpcHeader;
        this.wmsIntboundRecord = wmsIntboundRecord;
        this.wmsIntboundRecordItemList = wmsIntboundRecordItemList;
        this.wmsInboundDao = wmsInboundDao;
        this.allocateOrderDao = allocateOrderDao;
        this.allocateOrderItemDao = allocateOrderItemDao;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start NotificationPurchaseEasTask.run() params: wmsIntboundRecord={}，wmsIntboundRecordItemList={}", rpcHeader.getTraceId(), JSON.toJSONString(wmsIntboundRecord), JSON.toJSONString(wmsIntboundRecordItemList));
        int i = wmsInboundDao.updateEasFlagToIng(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_ING.getStatus(), wmsIntboundRecord.getDataVersion());
        /**
         *  1、调拨入库单
         *  2、普通的入库单
         */
        if (i == 1) {                    //向EAS同步之前，先将订单状态改为“同步中”，如果跟新成功，说明只有这个线程拿到这条数据,否则该数据已经被其他线程拿到
            if (wmsIntboundRecord.getInboundType() == WmsOrderType.INBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode()){
                transforInbound();
            }else {
                ordinaryInbound();
            }

        } else {
            logger.info("#traceId={}# [OUT] get NotificationPurchaseEasTask success,gongxiaoInboundOrderNo={},线程没有抢到数据，线程结束", rpcHeader.getTraceId(), wmsIntboundRecord.getGongxiaoInboundOrderNo());
        }

    }

    /**
     * 调拨入库单推送EAS
     */
    private void transforInbound(){
        try {
            AllocationOrder allocationOrder = allocateOrderDao.selectRecordByAllocateNo(wmsIntboundRecord.getPurchaseOrderNo());   //根据调拨单查询
            List<AllocationOrderItem> allocationOrderItemList = allocateOrderItemDao.selectRecorByAllocateNo(wmsIntboundRecord.getPurchaseOrderNo());
            AllocateStockEasOrder allocateStockEasOrder = new AllocateStockEasOrder();
            allocateStockEasOrder.setWms(allocationOrder.getAllocateNo());
            allocateStockEasOrder.setCreatorID(rpcHeader.getUsername());
            allocateStockEasOrder.setBizDate(allocationOrder.getCreateTime());
            if (allocationOrder.getAlloteWay() == WmsAllocateWay.ALLOCATE_DIFFERENT_STOCK.getAllocateWay()){
                allocateStockEasOrder.setBizType("331");
            }else {
                allocateStockEasOrder.setBizType("321");
            }

            allocateStockEasOrder.setCreateTime(allocationOrder.getCreateTime());
            allocateStockEasOrder.setSourceBillID(allocationOrder.getEasId());
            allocateStockEasOrder.setTransactionTypeID("028");
            allocateStockEasOrder.setIssueStorageOrgUnitID("02029901");
            allocateStockEasOrder.setReceiptStorageOrgUnitID("02029901");
            allocateStockEasOrder.setIssueCompanyOrgUnitID("0202");
            allocateStockEasOrder.setReceiptCompanyOrgUnitID("0202");

            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseRpcStub =  GlobalRpcStubStore.getRpcStub((WarehouseServiceGrpc.WarehouseServiceBlockingStub.class));
            List<AllocateStockBrandEasOrder > allocateStockBrandEasOrderList = new ArrayList<>();
            int totalQty = 0;
            double totalAmount = 0;
            int seq = 1;
            for (AllocationOrderItem allocationOrderItem : allocationOrderItemList){
                AllocateStockBrandEasOrder allocateStockBrandEasOrder = new AllocateStockBrandEasOrder();
                totalQty += allocationOrderItem.getRealInQuantity();
                totalAmount += allocationOrderItem.getRealInQuantity()*allocationOrderItem.getPuchasePrice()/1000000.0;
                allocateStockBrandEasOrder.setSeq(seq);
                allocateStockBrandEasOrder.setSourceBillEntrySeq(seq);
                seq++;
                allocateStockBrandEasOrder.setSourceBillEntryID(allocationOrderItem.getEntryId());
                allocateStockBrandEasOrder.setInvUpdateTypeID("001");
                allocateStockBrandEasOrder.setMaterialID(allocationOrderItem.getMaterialCode());
                allocateStockBrandEasOrder.setLot(allocationOrderItem.getBatchNo());
                allocateStockBrandEasOrder.setUnitID("");
                allocateStockBrandEasOrder.setQty(allocationOrderItem.getRealInQuantity());

                //判断库位是良品区、
                WarehouseStructure.GetWarehouseByIdReq getWarehouseReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setWarehouseId(allocationOrder.getWarehouseEnterId() + "")
                        .build();
                WarehouseStructure.GetWarehouseByIdResp warehouseResp = warehouseRpcStub.getWarehouseById(getWarehouseReq);
                if (allocationOrderItem.getInventoryStatus() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()){
                    allocateStockBrandEasOrder.setLocationID(warehouseResp.getWarehouse().getEasWarehouseCode()+".01");
                }else {
                    allocateStockBrandEasOrder.setLocationID(warehouseResp.getWarehouse().getEasWarehouseCode()+".02");
                }
                allocateStockBrandEasOrder.setWarehouseID(warehouseResp.getWarehouse().getEasWarehouseCode());
                allocateStockBrandEasOrder.setStorageOrgUnitID("02029901");
                allocateStockBrandEasOrder.setCompanyOrgUnitID("0202");
                allocateStockBrandEasOrder.setPrice(allocationOrderItem.getPuchasePrice()/1000000.0);
                allocateStockBrandEasOrder.setAmount(allocationOrderItem.getRealInQuantity()*allocationOrderItem.getPuchasePrice()/1000000.0);
                allocateStockBrandEasOrderList.add(allocateStockBrandEasOrder);
            }
            allocateStockEasOrder.setTotalQty(totalQty);
            allocateStockEasOrder.setTotalAmount(totalAmount);
            //通知EAS调拨入库单
            String easResultJson = null;
            try {
                easResultJson = EasUtil.sendTransforInBoundWare2Eas(allocateStockEasOrder, allocateStockBrandEasOrderList);
            }catch (Exception e){
                String errorMessage = e.getMessage();
                if (errorMessage != null && e.getMessage().contains("connect timed out")) {
                    wmsInboundDao.notifyEasFail(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + errorMessage, e);
                } else {
                    wmsInboundDao.notifyEasNeedHandle(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_EXCEPTION.getStatus());
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + errorMessage, e);
                }
            }
            judgeSuccess(easResultJson);

        }catch (Exception e){
            wmsInboundDao.notifyEasFail(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }
    }

    /**
     * 普通入库单推送EAS
     */
    private void ordinaryInbound() {
        String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(wmsIntboundRecord.getProjectId()));
        String uniqueNumber = DateTimeIdGenerator.nextId(projectPrefix, BizNumberType.STOCK_UNIQUE_NO);
        String traceNo = wmsIntboundRecord.getPurchaseOrderNo();
        List<InboundNotificationBackItem> inboundNotificationBackItemList = new ArrayList();

        for (WmsIntboundRecordItem recordItem : wmsIntboundRecordItemList) {

            InboundNotificationBackItem inboundNotificationBackItem = new InboundNotificationBackItem();

            inboundNotificationBackItem.setProductCode(recordItem.getProductCode());

            inboundNotificationBackItem.setInboundNotificationItemId(recordItem.getGongxiaoInboundOrderNo());

            if (recordItem.getInventoryType() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()) {
                inboundNotificationBackItem.setInStockQuantity(recordItem.getInStockQuantity());
            } else {
                inboundNotificationBackItem.setImperfectQuantity(recordItem.getInStockQuantity());
            }

            if (recordItem.getPurchaseType() == 2) {  //判断是否是赠品 (0:普通采购 1:货补 2:赠品)
                inboundNotificationBackItem.setGift(true);
            } else {
                inboundNotificationBackItem.setGift(false);
            }

            inboundNotificationBackItem.setBatchNo(wmsIntboundRecord.getBatchNo());

            inboundNotificationBackItem.setWarehouseId(recordItem.getWarehouseId());

            inboundNotificationBackItemList.add(inboundNotificationBackItem);

        }

        //通知采购模块,调用基础模块的sourceChannel服务获取渠道的url
        String channelId = null;
        if (wmsIntboundRecord.getGongxiaoInboundOrderNo().contains("shaver")){
            channelId = WmsSourceChannel.CHANNEL_SHAVER.getChannelId();
        }else if (wmsIntboundRecord.getGongxiaoInboundOrderNo().contains("PHTM")){
            channelId = WmsSourceChannel.CHANNEL_TMALL.getChannelId();
        }else if(wmsIntboundRecord.getGongxiaoInboundOrderNo().contains("JMGO")){
            channelId = WmsSourceChannel.CHANNEL_JMGO.getChannelId();
        }else {
            channelId = WmsSourceChannel.CHANNEL_TUANGOU.getChannelId();
        }
        ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
        ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder().setRpcHeader(rpcHeader).setXpsChannelId(channelId).build();
        ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
        ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
        try {
            PurchaseEasInboundModel purchaseEasInboundModel = XpsWarehouseNotifyManager.transferReceivedNotification(sourceChannel.getXpsWarehouseNotifyUrl(), wmsIntboundRecord.getProjectId(), traceNo, wmsIntboundRecord.getGongxiaoInboundOrderNo(), inboundNotificationBackItemList, uniqueNumber);

            logger.info("gongxiaoInboundOrderNo={},purchaseEasInboundModel={}", wmsIntboundRecord.getGongxiaoInboundOrderNo(), JSON.toJSONString(purchaseEasInboundModel));
            if (purchaseEasInboundModel.getErrorCode() != ErrorCode.SUCCESS.getErrorCode()) {
                int code = purchaseEasInboundModel.getErrorCode();
                ErrorCode err = ErrorCode.getErrorCodeByCode(code);
                logger.error("got errorCode after invoking InboundNotificationService: errorCode={} errorMsg={}", code, err.getMessage());
                wmsInboundDao.notifyEasFail(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                return;
            }

            String easResultJson = null;
            try {
                logger.info("[begin] wmsToeas gongxiaoInboundOrderNo={}", wmsIntboundRecord.getGongxiaoInboundOrderNo());
                easResultJson = EasUtil.sendPurchaseInbound2Eas(purchaseEasInboundModel.getPurchaseBasicOrderInbound(), purchaseEasInboundModel.getPurchaseBasicOrderItemInboundList());
                logger.info("[end] wmsToeas gongxiaoInboundOrderNo={}, easResultJson={}", wmsIntboundRecord.getGongxiaoInboundOrderNo(), easResultJson);

            } catch (Exception e) {
                String errorMessage = e.getMessage();
                if (errorMessage != null && e.getMessage().contains("connect timed out")) {
                    wmsInboundDao.notifyEasFail(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + errorMessage, e);
                } else {
                    wmsInboundDao.notifyEasNeedHandle(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_EXCEPTION.getStatus());
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + errorMessage, e);
                }
            }
            judgeSuccess(easResultJson);
            logger.info("#traceId={}# [OUT] get NotificationPurchaseEasTask.run() success", rpcHeader.getTraceId());
        } catch (Exception e) {
            wmsInboundDao.notifyEasFail(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }
    }

    private void judgeSuccess(String easResultJson) {
        EasResult easResult = JSON.parseObject(easResultJson, EasResult.class);
        if (easResult != null) {
            if (easResult.isSuccess()) {    //通知EAS成功
                wmsInboundDao.notifyEasSuccess(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_SUCCESS.getStatus(), easResult.getOrderNumber());
            } else {       //通知EAS失败
                wmsInboundDao.notifyEasFail(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                logger.info("#traceId={}# NOTIFY EAS IN BOUND FAIL,ErrorMassage={}", rpcHeader.getTraceId(), easResult.getError());
            }
        } else {
            wmsInboundDao.notifyEasFail(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
            logger.info("#traceId={}# NOTIFY EAS IN BOUND FAIL,ErrorMassage={}", rpcHeader.getTraceId(), "eas连接超时");
        }
    }

}
