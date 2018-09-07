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
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.EasResult;
import com.yhglobal.gongxiao.type.SyncEasEnum;
import com.yhglobal.gongxiao.type.WmsAllocateWay;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.warehouse.dao.AllocateOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.AllocateOrderItemDao;
import com.yhglobal.gongxiao.warehouse.dao.WmsOutboundDao;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrder;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrderItem;
import com.yhglobal.gongxiao.warehouse.model.WmsOutboundRecord;
import com.yhglobal.gongxiao.warehouse.model.WmsOutboundRecordItem;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.XpsWarehouseNotifyManager;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.sales.model.EasOutboundItem;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.sales.model.EasOutboundOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.el.ELContext;
import java.util.ArrayList;
import java.util.List;

/**
 * 当收到wms出库回告信息后 通过该任务 将出库单信息推送给EAS
 */
public class NotificationSalesEasTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(NotificationSalesEasTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private WmsOutboundRecord wmsOutboundRecord;

    private List<WmsOutboundRecordItem> wmsOutboundRecordItemList;

    private WmsOutboundDao wmsOutboundDao;

    private AllocateOrderDao allocateOrderDao;

    private AllocateOrderItemDao allocateOrderItemDao;

    public NotificationSalesEasTask(GongxiaoRpc.RpcHeader rpcHeader,
                                    WmsOutboundRecord wmsOutboundRecord,
                                    List<WmsOutboundRecordItem> wmsOutboundRecordItemList,
                                    WmsOutboundDao wmsOutboundDao,
                                    AllocateOrderDao allocateOrderDao,
                                    AllocateOrderItemDao allocateOrderItemDao) {
        this.rpcHeader = rpcHeader;
        this.wmsOutboundRecord = wmsOutboundRecord;
        this.wmsOutboundRecordItemList = wmsOutboundRecordItemList;
        this.wmsOutboundDao = wmsOutboundDao;
        this.allocateOrderDao = allocateOrderDao;
        this.allocateOrderItemDao = allocateOrderItemDao;
    }

    @Override
    public void run() {

        logger.info("#traceId={}# [IN][run] start NotificationSalesEasTask.run(),params: wmsOutboundRecord={}，wmsOutboundRecordItemList={}", rpcHeader.getTraceId(), JSON.toJSONString(wmsOutboundRecord), JSON.toJSONString(wmsOutboundRecordItemList));
        try {
            int i = wmsOutboundDao.updateEasFlagToIng(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_ING.getStatus(), wmsOutboundRecord.getDataVersion());
            if (i == 1) {     //向EAS同步之前，先将订单状态改为“同步中”，如果跟新成功，说明只有这个线程拿到这条数据,否则该数据已经被其他线程拿到

                List<EasOutboundItem> easOutboundItems = new ArrayList<>(); //保存待同步到eas的出库单明细
                for (WmsOutboundRecordItem recordItem : wmsOutboundRecordItemList) { //遍历出库明细信息
                    EasOutboundItem easOutboundItem = new EasOutboundItem();
                    easOutboundItem.setBatchNo(recordItem.getBatchNo());
                    if (recordItem.getInventoryType() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()) {   //根据出库单的库存类型分为良品和不良品
                        easOutboundItem.setGoodProduct(true);
                    } else {
                        easOutboundItem.setGoodProduct(false);
                    }

                    if (recordItem.getPurchaseType() == 2) {  //判断是否是赠品 (0:普通采购 1:货补 2:赠品)
                        easOutboundItem.setGift(true);
                    } else {
                        easOutboundItem.setGift(false);
                    }
                    easOutboundItem.setProductCode(recordItem.getProductCode());
                    easOutboundItem.setQuantity(recordItem.getOutStockQuantity());
                    easOutboundItem.setWarehouseId(recordItem.getWarehouseId());

                    easOutboundItems.add(easOutboundItem);
                }

                String channelId = null;
                if (wmsOutboundRecord.getGongxiaoOutboundOrderNo().contains("shaver")){
                    channelId = WmsSourceChannel.CHANNEL_SHAVER.getChannelId();
                }else if (wmsOutboundRecord.getGongxiaoOutboundOrderNo().contains("PHTM")){
                    channelId = WmsSourceChannel.CHANNEL_TMALL.getChannelId();
                }else if(wmsOutboundRecord.getGongxiaoOutboundOrderNo().contains("JMGO")){
                    channelId = WmsSourceChannel.CHANNEL_JMGO.getChannelId();
                }else if(wmsOutboundRecord.getGongxiaoOutboundOrderNo().contains("PHTG")){
                    channelId = WmsSourceChannel.CHANNEL_TUANGOU.getChannelId();
                }else {   //为了适配老单
                    channelId = WmsSourceChannel.CHANNEL_SHAVER.getChannelId();
                }
                ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
                ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder().setRpcHeader(rpcHeader).setXpsChannelId(channelId).build();
                ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
                ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();

                try{
                    EasOutboundOrderRequest easOutboundOrderRequest = XpsWarehouseNotifyManager.syncSalesOutboundOrderToEas(sourceChannel.getXpsWarehouseNotifyUrl(), wmsOutboundRecord.getSalesOrderNo(), easOutboundItems, Long.parseLong(wmsOutboundRecord.getProjectId()));
                    logger.info("gongxiaoOutboundOrderNo={},easOutboundOrderRequest={}", wmsOutboundRecord.getGongxiaoOutboundOrderNo(), JSON.toJSONString(easOutboundOrderRequest));

                    if (easOutboundOrderRequest.getErrorCode() != ErrorCode.SUCCESS.getErrorCode()) { //调用salesScheduleDeliveryService返回非0 erroCode
                        logger.error("got errorCode after invoking salesScheduleDeliveryService: salesOrderNo={} gongxiaoOutboundOrderNo={} errorCode={}", wmsOutboundRecord.getSalesOrderNo(), wmsOutboundRecord.getGongxiaoOutboundOrderNo(), easOutboundOrderRequest.getErrorCode());
                        wmsOutboundDao.notifyEasFail(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                        return;
                    }

                    String easResponse = null;
                    try {
                        logger.info("[begin] sync to eas: salesOrderNo={} gongxiaoOutboundOrderNo={} json={}", wmsOutboundRecord.getSalesOrderNo(), wmsOutboundRecord.getGongxiaoOutboundOrderNo(), JSON.toJSONString(easOutboundOrderRequest));
//                        easOutboundOrderRequest.getEasSalesOutboundOrder().setBusinessDate(wmsOutboundRecord.getCreateTime());   //将wms出库日期推送给eas
                        easResponse = EasUtil.sendSaleOutOrder2Eas(easOutboundOrderRequest.getEasSalesOutboundOrder(), easOutboundOrderRequest.getEasSalesOutboundItems());
                        logger.info("[end] sync to eas: salesOrderNo={} gongxiaoOutboundOrderNo={} easResponse={}", wmsOutboundRecord.getSalesOrderNo(), wmsOutboundRecord.getGongxiaoOutboundOrderNo(), easResponse);
                        EasResult easResult = JSON.parseObject(easResponse, EasResult.class);
                        if (easResult.isSuccess()) { //同步Eas成功
                            wmsOutboundDao.notifyEasSuccess(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_SUCCESS.getStatus(), easResult.getOrderNumber());
                        } else { //同步Eas失败 则累计失败次数
                            wmsOutboundDao.notifyEasNeedHandle(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_EXCEPTION.getStatus());
                            logger.info("#traceId={}# NOTIFY EAS OUT BOUND FAIL,ErrorMessage={}", rpcHeader.getTraceId(), easResult.getError());
                        }
                    } catch (Exception e) { //同步EAS遇到异常

                        String errorMsg = e.getMessage();
                        if (errorMsg != null && errorMsg.toLowerCase().contains("connect time out".toLowerCase())) {  //连接超时 则累计失败次数
                            wmsOutboundDao.notifyEasNeedHandle(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                        } else { //其他错误 需要人工处理
                            wmsOutboundDao.notifyEasFail(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_EXCEPTION.getStatus());
                            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + errorMsg, e);
                        }
                    }
                }catch (Exception e){
                    wmsOutboundDao.notifyEasNeedHandle(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                }


                logger.info("#traceId={}# [OUT] get NotificationSalesEasTask.run() success", rpcHeader.getTraceId());

            } else {
                logger.info("#traceId={}# [OUT] get NotificationSalesEasTask success,gongxiaoOutboundOrderNo={},线程没有抢到数据，线程结束", rpcHeader.getTraceId(), wmsOutboundRecord.getGongxiaoOutboundOrderNo());
            }
        } catch (Exception e) {
            wmsOutboundDao.notifyEasFail(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }

    }

    /**
     * 调拨入库单推送EAS
     */
    private void transforInbound(){
        try {
            AllocationOrder allocationOrder = allocateOrderDao.selectRecordByAllocateNo(wmsOutboundRecord.getSalesOrderNo());   //根据调拨单查询
            List<AllocationOrderItem> allocationOrderItemList = allocateOrderItemDao.selectRecorByAllocateNo(wmsOutboundRecord.getSalesOrderNo());
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
            allocateStockEasOrder.setTransactionTypeID("027");
            allocateStockEasOrder.setIssueStorageOrgUnitID("02029901");
            allocateStockEasOrder.setReceiptStorageOrgUnitID("02029901");
            allocateStockEasOrder.setIssueCompanyOrgUnitID("0202");
            allocateStockEasOrder.setReceiptCompanyOrgUnitID("0202");

            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseRpcStub =  GlobalRpcStubStore.getRpcStub((WarehouseServiceGrpc.WarehouseServiceBlockingStub.class));
            List<AllocateStockBrandEasOrder> allocateStockBrandEasOrderList = new ArrayList<>();
            int seq = 1;
            for (AllocationOrderItem allocationOrderItem : allocationOrderItemList){
                AllocateStockBrandEasOrder allocateStockBrandEasOrder = new AllocateStockBrandEasOrder();

                allocateStockBrandEasOrder.setSeq(seq);
                allocateStockBrandEasOrder.setSourceBillEntrySeq(seq);
                seq++;
                allocateStockBrandEasOrder.setSourceBillEntryID(allocationOrderItem.getEntryId());
                allocateStockBrandEasOrder.setInvUpdateTypeID("002");
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

            //通知EAS调拨出库单
            String easResultJson = null;
            try {
                easResultJson = EasUtil.sendTransforOutBoundWare2Eas(allocateStockEasOrder, allocateStockBrandEasOrderList);
            }catch (Exception e){
                String errorMessage = e.getMessage();
                if (errorMessage != null && e.getMessage().contains("connect timed out")) {
                    wmsOutboundDao.notifyEasFail(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + errorMessage, e);
                } else {
                    wmsOutboundDao.notifyEasNeedHandle(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_EXCEPTION.getStatus());
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + errorMessage, e);
                }
            }

            EasResult easResult = JSON.parseObject(easResultJson, EasResult.class);
            if (easResult != null) {
                if (easResult.isSuccess()) {    //通知EAS成功
                    wmsOutboundDao.notifyEasSuccess(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_SUCCESS.getStatus(), easResult.getOrderNumber());
                } else {       //通知EAS失败
                    wmsOutboundDao.notifyEasFail(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                    logger.info("#traceId={}# NOTIFY EAS IN BOUND FAIL,ErrorMassage={}", rpcHeader.getTraceId(), easResult.getError());
                }
            } else {
                wmsOutboundDao.notifyEasFail(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                logger.info("#traceId={}# NOTIFY EAS IN BOUND FAIL,ErrorMassage={}", rpcHeader.getTraceId(), "eas连接超时");
            }

        }catch (Exception e){
            wmsOutboundDao.notifyEasFail(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }
    }


}

