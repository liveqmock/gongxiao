package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.purchase.bo.OutboundNotificationBackItem;
import com.yhglobal.gongxiao.warehouse.service.OutboundNotificationService;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmOrderItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 当WMS回告对应的订单是采购退货单时 调用该任务 将wms入库确认信息同步给采购模块
 */
public class SyncWmsOutboundInfoToPurchaseTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsOutboundInfoToPurchaseTask.class);

    private RpcHeader rpcHeader;

    private Data outStockConfirmRequest;

    private String projectId;

    private ApplicationContext applicationContext;

    private OutboundOrder olderRecord;

    public SyncWmsOutboundInfoToPurchaseTask(RpcHeader rpcHeader, ApplicationContext applicationContext, Data outStockConfirmRequest, String projectId,OutboundOrder olderRecord) {
        this.rpcHeader = rpcHeader;
        this.applicationContext = applicationContext;
        this.outStockConfirmRequest = outStockConfirmRequest;
        this.projectId = projectId;
        this.olderRecord = olderRecord;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start SyncWmsOutboundInfoToPurchaseTask.run() params: wmsOutStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(outStockConfirmRequest));
        String traceNo = outStockConfirmRequest.getOrderNo();
        String gongxiaoInboundOrderNo = olderRecord.getGongxiaoOutboundOrderNo();
        List<OutboundNotificationBackItem> planOutboundItemList = new ArrayList<>();
        List<StockOutOrderConfirmOrderItemDto> orderItems = outStockConfirmRequest.getOrderItems();
        for (StockOutOrderConfirmOrderItemDto record : orderItems) {
            int imperfectQuantity = 0;
            int outStockQuantity = 0;
            int outTransitQuantity = 0;

            for (StockOutOrderConfirmItemDto stockOutOrderConfirmItemDto : record.getItems()) {
                if (stockOutOrderConfirmItemDto.getInventoryType() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()) {           //1:可销售库存
                    outStockQuantity = stockOutOrderConfirmItemDto.getQuantity();
                } else if (stockOutOrderConfirmItemDto.getInventoryType() == WmsInventoryType.DEFECTIVE.getInventoryType()) {  //101:残次
                    imperfectQuantity += stockOutOrderConfirmItemDto.getQuantity();
                } else if (stockOutOrderConfirmItemDto.getInventoryType() == WmsInventoryType.ENGINE_DAMAGE.getInventoryType()) {  //102:机损
                    imperfectQuantity += stockOutOrderConfirmItemDto.getQuantity();
                } else if (stockOutOrderConfirmItemDto.getInventoryType() == WmsInventoryType.BOX_DAMAGE.getInventoryType()) {  //103:箱损
                    imperfectQuantity += stockOutOrderConfirmItemDto.getQuantity();
                } else if (stockOutOrderConfirmItemDto.getInventoryType() == WmsInventoryType.FROZEN_STOCK.getInventoryType()) {  //201:冻结库存
                    imperfectQuantity = stockOutOrderConfirmItemDto.getQuantity();
                } else if (stockOutOrderConfirmItemDto.getInventoryType() == WmsInventoryType.TRANSPORTATION_INVENTORY.getInventoryType()) {  //301:在途库存
                    outTransitQuantity = stockOutOrderConfirmItemDto.getQuantity();
                }

            }
            OutboundNotificationBackItem outboundNotificationBackItem = new OutboundNotificationBackItem();
            outboundNotificationBackItem.setBusinessItemId(record.getItemNo());
            outboundNotificationBackItem.setSignedReceiptQuantity(outStockQuantity + imperfectQuantity + outTransitQuantity);  //签收数量
            outboundNotificationBackItem.setOutboundQuantity(outStockQuantity);         //出库数量
            outboundNotificationBackItem.setImperfectQuantity(imperfectQuantity);           //残次数量
            planOutboundItemList.add(outboundNotificationBackItem);
        }

        OutboundNotificationService outboundNotificationService = applicationContext.getBean("outboundNotificationServiceImpl",OutboundNotificationService.class);
        outboundNotificationService.transferDepartureNotification(rpcHeader, projectId, traceNo, gongxiaoInboundOrderNo, planOutboundItemList);
        logger.info("#traceId={}# [OUT] get SyncWmsOutboundInfoToPurchaseTask.run() success", rpcHeader.getTraceId());
    }
}
