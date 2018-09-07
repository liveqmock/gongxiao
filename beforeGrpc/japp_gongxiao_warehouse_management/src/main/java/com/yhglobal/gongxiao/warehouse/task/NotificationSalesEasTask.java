package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.PurchaseTypeStatus;
import com.yhglobal.gongxiao.model.EasResult;
import com.yhglobal.gongxiao.sales.dto.EasOutboundItem;
import com.yhglobal.gongxiao.sales.dto.EasOutboundOrderRequest;
import com.yhglobal.gongxiao.sales.service.SalesScheduleDeliveryService;
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehousemanagement.dao.WmsOutboundDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmOrderItemDto;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecord;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecordItem;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.SyncEasEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 当收到wms出库回告信息后 通过该任务 将出库单信息推送给EAS
 */
public class NotificationSalesEasTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(NotificationSalesEasTask.class);

    private RpcHeader rpcHeader;

    private WmsOutboundRecord wmsOutboundRecord;

    private List<WmsOutboundRecordItem> wmsOutboundRecordItemList;

    private WmsOutboundDao wmsOutboundDao;

    private SalesScheduleDeliveryService salesScheduleDeliveryService;

    public NotificationSalesEasTask(RpcHeader rpcHeader,
                                    WmsOutboundRecord wmsOutboundRecord,
                                    List<WmsOutboundRecordItem> wmsOutboundRecordItemList,
                                    WmsOutboundDao wmsOutboundDao,
                                    SalesScheduleDeliveryService salesScheduleDeliveryService) {
        this.rpcHeader = rpcHeader;
        this.wmsOutboundRecord = wmsOutboundRecord;
        this.wmsOutboundRecordItemList = wmsOutboundRecordItemList;
        this.wmsOutboundDao = wmsOutboundDao;
        this.salesScheduleDeliveryService = salesScheduleDeliveryService;
    }

    @Override
    public void run() {

        logger.info("#traceId={}# [IN][run] start NotificationSalesEasTask.run(),params: wmsOutboundRecord={}，wmsOutboundRecordItemList={}", rpcHeader.getTraceId(), JSON.toJSONString(wmsOutboundRecord), JSON.toJSONString(wmsOutboundRecordItemList));
        List<EasOutboundItem> easOutboundItems = new ArrayList<>(); //保存待同步到eas的出库单明细
        for (WmsOutboundRecordItem recordItem : wmsOutboundRecordItemList) { //遍历出库明细信息
            EasOutboundItem easOutboundItem = new EasOutboundItem();
            easOutboundItem.setBatchNo(recordItem.getBatchNo());
            if (recordItem.getInventoryType() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()) {   //根据出库单的库存类型分为良品和不良品
                easOutboundItem.setGoodProduct(true);
            } else {
                easOutboundItem.setGoodProduct(false);
            }

            if (recordItem.getPurchaseType() == PurchaseTypeStatus.GIFT_PURCHASE.getStatus()){  //判断是否是赠品 (0:普通采购 1:货补 2:赠品)
                easOutboundItem.setGift(true);
            }else {
                easOutboundItem.setGift(false);
            }
            easOutboundItem.setProductCode(recordItem.getProductCode());
            easOutboundItem.setQuantity(recordItem.getOutStockQuantity());
            easOutboundItem.setWarehouseId(recordItem.getWarehouseId());

            easOutboundItems.add(easOutboundItem);
        }

        try {
            EasOutboundOrderRequest easOutboundOrderRequest = salesScheduleDeliveryService.syncSalesOutboundOrderToEas(rpcHeader, wmsOutboundRecord.getSalesOrderNo(), easOutboundItems);
            logger.info("gongxiaoOutboundOrderNo={},easOutboundOrderRequest={}", wmsOutboundRecord.getGongxiaoOutboundOrderNo(),JSON.toJSONString(easOutboundOrderRequest));
            if (easOutboundOrderRequest == null) { //调用salesScheduleDeliveryService失败
                logger.error("fail to invoke salesScheduleDeliveryService: salesOrderNo={} gongxiaoOutboundOrderNo={}", wmsOutboundRecord.getSalesOrderNo(), wmsOutboundRecord.getGongxiaoOutboundOrderNo());
                wmsOutboundDao.notifyEasFail(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                return ;
            }

            if (easOutboundOrderRequest.getErrorCode() != ErrorCode.SUCCESS.getErrorCode()) { //调用salesScheduleDeliveryService返回非0 erroCode
                logger.error("got errorCode after invoking salesScheduleDeliveryService: salesOrderNo={} gongxiaoOutboundOrderNo={} errorCode={}", wmsOutboundRecord.getSalesOrderNo(), wmsOutboundRecord.getGongxiaoOutboundOrderNo(), easOutboundOrderRequest.getErrorCode());
                wmsOutboundDao.notifyEasFail(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                return ;
            }

            String easResponse= null;
            try {
                logger.info("[begin] sync to eas: salesOrderNo={} gongxiaoOutboundOrderNo={} json={}", wmsOutboundRecord.getSalesOrderNo(), wmsOutboundRecord.getGongxiaoOutboundOrderNo(), JSON.toJSONString(easOutboundOrderRequest));
                easResponse = EasUtil.sendSaleOutOrder2Eas(easOutboundOrderRequest.getEasSalesOutboundOrder(), easOutboundOrderRequest.getEasSalesOutboundItems());
                logger.info("[end] sync to eas: salesOrderNo={} gongxiaoOutboundOrderNo={} easResponse={}", wmsOutboundRecord.getSalesOrderNo(), wmsOutboundRecord.getGongxiaoOutboundOrderNo(), easResponse);
                if (easResponse == null) {
                    logger.error("got null response from EAS: salesOrderNo={} gongxiaoOutboundOrderNo={}", wmsOutboundRecord.getSalesOrderNo(), wmsOutboundRecord.getGongxiaoOutboundOrderNo());
                    wmsOutboundDao.notifyEasFail(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                    return ;
                }
                EasResult easResult = JSON.parseObject(easResponse, EasResult.class);
                if (easResult.isSuccess()) { //同步Eas成功
                    wmsOutboundDao.notifyEasSuccess(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_SUCCESS.getStatus(), easResult.getOrderNumber());
                } else { //同步Eas失败 则累计失败次数
                    wmsOutboundDao.notifyEasNeedHandle(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_EXCEPTION.getStatus());
                    logger.info("#traceId={}# NOTIFY EAS OUT BOUND FAIL,ErrorMessage={}", rpcHeader.getTraceId(), easResult.getError());
                }
            } catch (Exception e) { //同步EAS遇到异常
                String errorMsg = e.getMessage();
                if (errorMsg!=null && errorMsg.toLowerCase().contains("Read time out".toLowerCase())) {  //如果是读超时，将同步的状态设置为需要人工处理的状态
                    wmsOutboundDao.notifyEasNeedHandle(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_EXCEPTION.getStatus());
                    logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                } else { //其他错误 则累计失败次数
                    wmsOutboundDao.notifyEasFail(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                    logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + errorMsg, e);
                }
            }
        } catch (Exception e) {
            wmsOutboundDao.notifyEasFail(wmsOutboundRecord.getGongxiaoOutboundOrderNo(), wmsOutboundRecord.getWmsOutboundOrderNo(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }
    }


}

