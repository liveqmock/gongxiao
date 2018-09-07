package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.PurchaseTypeStatus;
import com.yhglobal.gongxiao.eas.PurchaseEasInboundModel;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.model.EasResult;
import com.yhglobal.gongxiao.purchase.bo.InboundNotificationBackItem;
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.warehouse.service.InboundNotificationService;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehousemanagement.dao.WmsInboundDao;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecord;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecordItem;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.SyncEasEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 当收到wms入库回告信息后 通过该任务 将入库单信息推送给EAS
 */
public class NotificationPurchaseEasTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(NotificationPurchaseEasTask.class);

    private RpcHeader rpcHeader;
    private ApplicationContext applicationContext;
    private WmsIntboundRecord wmsIntboundRecord;
    private List<WmsIntboundRecordItem> wmsIntboundRecordItemList;
    private WmsInboundDao wmsInboundDao;
    private InboundNotificationService inboundNotificationService;

    public NotificationPurchaseEasTask(RpcHeader rpcHeader,
                                       ApplicationContext applicationContext,
                                       WmsIntboundRecord wmsIntboundRecord,
                                       List<WmsIntboundRecordItem> wmsIntboundRecordItemList,
                                       WmsInboundDao wmsInboundDao,
                                       InboundNotificationService inboundNotificationService) {
        this.rpcHeader = rpcHeader;
        this.applicationContext = applicationContext;
        this.wmsIntboundRecord = wmsIntboundRecord;
        this.wmsIntboundRecordItemList = wmsIntboundRecordItemList;
        this.wmsInboundDao = wmsInboundDao;
        this.inboundNotificationService = inboundNotificationService;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start NotificationPurchaseEasTask.run() params: wmsIntboundRecord={}，wmsIntboundRecordItemList={}", rpcHeader.getTraceId(), JSON.toJSONString(wmsIntboundRecord), JSON.toJSONString(wmsIntboundRecordItemList));

        String uniqueNumber = DateTimeIdGenerator.nextId(BizNumberType.STOCK_UNIQUE_NO);
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

            if (recordItem.getPurchaseType() == PurchaseTypeStatus.GIFT_PURCHASE.getStatus()){  //判断是否是赠品 (0:普通采购 1:货补 2:赠品)
                inboundNotificationBackItem.setGift(true);
            }else {
                inboundNotificationBackItem.setGift(false);
            }

            inboundNotificationBackItem.setBatchNo(wmsIntboundRecord.getBatchNo());

            inboundNotificationBackItem.setWarehouseId(recordItem.getWarehouseId());

            inboundNotificationBackItemList.add(inboundNotificationBackItem);

        }

        try {

            PurchaseEasInboundModel purchaseEasInboundModel = inboundNotificationService.transferReceivedNotification(rpcHeader, wmsIntboundRecord.getProjectId(), traceNo, wmsIntboundRecord.getGongxiaoInboundOrderNo(), inboundNotificationBackItemList, uniqueNumber);
            logger.info("gongxiaoInboundOrderNo={},purchaseEasInboundModel={}", wmsIntboundRecord.getGongxiaoInboundOrderNo(), JSON.toJSONString(purchaseEasInboundModel));
            if (purchaseEasInboundModel.getErrorCode() != ErrorCode.SUCCESS.getErrorCode()) {
                int code = purchaseEasInboundModel.getErrorCode();
                ErrorCode err = ErrorCode.getErrorCodeByCode(code);
                logger.error("got errorCode after invoking InboundNotificationService: errorCode={} errorMsg={}", code, err.getMessage());
                wmsInboundDao.notifyEasFail(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                return ;
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
                    logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + errorMessage, e);
                } else {
                    wmsInboundDao.notifyEasNeedHandle(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_EXCEPTION.getStatus());
                    logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + errorMessage, e);
                }
            }

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

            logger.info("#traceId={}# [OUT] get NotificationPurchaseEasTask.run() success", rpcHeader.getTraceId());
        } catch (Exception e) {
            wmsInboundDao.notifyEasFail(wmsIntboundRecord.getGongxiaoInboundOrderNo(), wmsIntboundRecord.getWmsInboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }

    }
}
