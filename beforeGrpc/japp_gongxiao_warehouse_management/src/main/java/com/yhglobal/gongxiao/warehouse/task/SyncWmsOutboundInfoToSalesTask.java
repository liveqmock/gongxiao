package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.purchase.bo.OutboundNotificationBackItem;
import com.yhglobal.gongxiao.sales.service.SalesScheduleDeliveryService;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehousemanagement.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmOrderItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * 当WMS回告对应的订单是销售单时 调用该任务 将wms出库确认信息同步给销售模块
 *
 * 注1: 若是销售出库 由于反馈时需要批次号 在SyncWmsOutboundInfoToStockTask任务中反馈到销售模块
 * 注2: 同步给EAS由定时任务来发起
 */
public class SyncWmsOutboundInfoToSalesTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsOutboundInfoToSalesTask.class);

    private RpcHeader rpcHeader;

    private Data outStockConfirmRequest;

    private ProductService productService;

    private OutBoundOrderDao outBoundOrderDao;

    private SalesScheduleDeliveryService salesScheduleDeliveryService;

    private OutboundOrder olderRecord;

    public SyncWmsOutboundInfoToSalesTask(RpcHeader rpcHeader, ProductService productService, SalesScheduleDeliveryService salesScheduleDeliveryService, OutBoundOrderDao outBoundOrderDao, Data outStockConfirmRequest, OutboundOrder olderRecord) {
        this.rpcHeader = rpcHeader;
        this.productService = productService;
        this.salesScheduleDeliveryService = salesScheduleDeliveryService;
        this.outBoundOrderDao = outBoundOrderDao;
        this.outStockConfirmRequest = outStockConfirmRequest;
        this.olderRecord = olderRecord;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start SyncWmsOutboundInfoToSalesTask.run() params: wmsOutStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(outStockConfirmRequest));

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
                    System.out.println("==============MalformedURLException 异常");
                    logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                } catch (RemoteException e) {
                    System.out.println("==============RemoteException 异常");
                    logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                }

            }

        }
    }
}
