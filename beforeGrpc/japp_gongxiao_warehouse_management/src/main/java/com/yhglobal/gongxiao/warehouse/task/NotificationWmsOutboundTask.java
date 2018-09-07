package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.foundation.channel.Channel;
import com.yhglobal.gongxiao.foundation.channel.ChannelDao;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.sales.service.SalesScheduleDeliveryService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationOutboundService;
import com.yhglobal.gongxiao.warehousemanagement.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.ReceiverInfo;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.Result;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.SenderInfo;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.cancel.WmsCanselOrderResp;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstock.StockOutOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstock.WmsOutStockNotifyRequest;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.SyncWmsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 销售预约出库后 通过该任务 通知WMS发货
 */
public class NotificationWmsOutboundTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(NotificationWmsOutboundTask.class);

    private RpcHeader rpcHeader;

    private OutboundOrder outboundOrder;

    private List<OutboundOrderItem> outboundOrderItemList;

    private ApplicationContext applicationContext;

    private OutBoundOrderDao outBoundOrderDao;

    private WarehouseService warehouseService;

    private ProjectService projectService;

    private ProductService productService;

    private WarehouseConfig warehouseConfig;

    private SalesScheduleDeliveryService salesScheduleDeliveryService;

    public NotificationWmsOutboundTask(RpcHeader rpcHeader,
                                       ApplicationContext applicationContext,
                                       OutboundOrder outboundOrder,
                                       List<OutboundOrderItem> outboundOrderItemList,
                                       OutBoundOrderDao outBoundOrderDao,
                                       WarehouseService warehouseService,
                                       ProjectService projectService,
                                       ProductService productService,
                                       WarehouseConfig warehouseConfig,
                                       SalesScheduleDeliveryService salesScheduleDeliveryService) {
        this.rpcHeader = rpcHeader;
        this.applicationContext = applicationContext;
        this.outboundOrder = outboundOrder;
        this.outboundOrderItemList = outboundOrderItemList;
        this.outBoundOrderDao = outBoundOrderDao;
        this.warehouseService = warehouseService;
        this.projectService = projectService;
        this.productService = productService;
        this.warehouseConfig = warehouseConfig;
        this.salesScheduleDeliveryService = salesScheduleDeliveryService;
    }

    @Override
    public void run() {
        logger.info("traceId=#{} [IN][run] start NotificationWmsOutboundTask,params: outboundOrder={},outboundOrderItemList={}", rpcHeader.getTraceId(), JSON.toJSONString(outboundOrder), JSON.toJSONString(outboundOrderItemList));
        WmsOutStockNotifyRequest wmsOutStockNotifyRequest = new WmsOutStockNotifyRequest();
        try {
            ChannelDao channelDao = applicationContext.getBean("channelDao", ChannelDao.class);
            Channel channel = channelDao.selectByChannelId(outboundOrder.getSourceChannel());
            wmsOutStockNotifyRequest.setCustCode(channel.getCustCode());   //客户代码  必选
            Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, outboundOrder.getWarehouseId());
            wmsOutStockNotifyRequest.setCkid(warehouse.getWmsWarehouseCode());    //对应wms仓库的ckid,我们系统DB里面的仓库编码  必选
            Project project = projectService.getByProjectId(rpcHeader, outboundOrder.getProjectId());
            wmsOutStockNotifyRequest.setWarehouseCode("EAS_"+project.getEasProjectCode());    //对应的是wms固定前缀 "EAS_项目编码"  必选
            wmsOutStockNotifyRequest.setProjectCode(project.getEasProjectCode());
            wmsOutStockNotifyRequest.setOrderNo(outboundOrder.getGongxiaoOutboundOrderNo());   //订单号(出库单单号)    必选
            wmsOutStockNotifyRequest.setSourceOrderNo(outboundOrder.getSalesOrderNo());
            wmsOutStockNotifyRequest.setOrderSource(channel.getChannelName());    //订单来源  必选
            wmsOutStockNotifyRequest.setOrderType(outboundOrder.getOutboundType());     //操作类型/订单类型 必选
            wmsOutStockNotifyRequest.setTransportMode(String.valueOf(outboundOrder.getShippingMode()));
            wmsOutStockNotifyRequest.setOrderCreateTime(outboundOrder.getCreateTime());

            ReceiverInfo receiverInfo = new ReceiverInfo();
            receiverInfo.getReceiverAddress();
            receiverInfo.getReceiverName();
            SenderInfo senderInfo = new SenderInfo();
            senderInfo.getSenderAddress();
            senderInfo.getSenderName();
            wmsOutStockNotifyRequest.setReceiverInfo(receiverInfo);                                    //收货方信息   必选
            wmsOutStockNotifyRequest.setSenderInfo(senderInfo);                                        //发货方信息   必选

            List<StockOutOrderItem> orderItemList = new ArrayList<>();
            for (OutboundOrderItem record : outboundOrderItemList) {
                ProductBasic productBasic = productService.getByProductCode(rpcHeader, record.getProductCode());
                StockOutOrderItem stockOutOrderItem = new StockOutOrderItem();
                stockOutOrderItem.setItemNO(record.getOutboundOrderItemNo());  //wms出库行号(为了匹配WMS返回的出库明细)
                stockOutOrderItem.setItemCode(productBasic.getWMSCode());      //商品编码  必选
                stockOutOrderItem.setItemName(productBasic.getProductName());
                stockOutOrderItem.setInventoryType(record.getInventoryType());  //库存类型  必选
                stockOutOrderItem.setItemQuantity(record.getTotalQuantity());  //商品数量  必选
                stockOutOrderItem.setBatchCode(record.getBatchNo());       //批次号
                stockOutOrderItem.seteRPBatchCode(record.getBatchNo());    //wms系统实际上通过这个字段接收分销系统的批次
                orderItemList.add(stockOutOrderItem);
            }
            wmsOutStockNotifyRequest.setOrderItemList(orderItemList);   //订单商品信息  必选
            wmsOutStockNotifyRequest.setRemark(outboundOrder.getNote());
        } catch (Exception e) {
            TraceLog failTraceLog = new TraceLog(new Date().getTime(), "fenxiao", "分销系统", "dubbo 服务超时");
            String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
            outBoundOrderDao.notifyFail(outboundOrder.getGongxiaoOutboundOrderNo(),outboundOrder.getConnectedProduct(),SyncWmsEnum.SYNC_WMS_FAIL.getStatus(), newTraceLog);
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }

        //通知wms出库
        try {
            WmsNotificationOutboundService wmsNotificationOutboundService = applicationContext.getBean("wmsNotificationOutboundServiceImpl", WmsNotificationOutboundService.class);
            String result = wmsNotificationOutboundService.notificationWmsOutboundInfo(wmsOutStockNotifyRequest, warehouseConfig.getWmsUrl());
            WmsCanselOrderResp cancelResult = JSON.parseObject(result, new TypeReference<WmsCanselOrderResp>() {
            });
            Date dateTime = new Date();

            if (cancelResult == null) {   //如果反序列化的结果为空，说明通知WMS的时候超时了
                TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms系统网络超时,重新同步到wms");
                String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                outBoundOrderDao.notifyFail(outboundOrder.getGongxiaoOutboundOrderNo(),outboundOrder.getConnectedProduct(),SyncWmsEnum.SYNC_WMS_FAIL.getStatus(), newTraceLog);
            } else {  //如果反序列化的结果不为空，根据WMS返回的结果判断是否推送成功
                if (cancelResult.isSuccess()) {    //判断是否成功接收到wms反馈信息
                    Result handleResult = cancelResult.getResult();
                    if (handleResult.isSuccess()) {      //再判断wms是否预约出库成功
                        TraceLog successTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "预约出库成功:"+handleResult.getMessage());
                        String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), successTraceLog);
                        outBoundOrderDao.notifySuccess(outboundOrder.getGongxiaoOutboundOrderNo(),outboundOrder.getConnectedProduct(),SyncWmsEnum.SYNC_WMS_SUCCESS.getStatus(),newTraceLog);

                        //通知销售模块,出库单已经通知到WMS了，可以通知TMS发货了
                        logger.info("开始通知销售模块通知TMS系统");
                        salesScheduleDeliveryService.submitOutboundOrderToTms(rpcHeader,outboundOrder.getGongxiaoOutboundOrderNo());
                        logger.info("通知销售模块通知TMS系统成功");

                    } else {             //如果wms出库失败
                        if (handleResult.getErrorMsg().contains("系统繁忙")) {           //如果是系统繁忙导致的失败，重试
                            TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms出库失败:" + handleResult.getErrorMsg());
                            String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                            outBoundOrderDao.notifyFail(outboundOrder.getGongxiaoOutboundOrderNo(),outboundOrder.getConnectedProduct(),SyncWmsEnum.SYNC_WMS_FAIL.getStatus(), newTraceLog);
                        } else {                     //否则设置订单状态为“已同步到wms”,并打印tracelog日志
                            TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms出库失败:" + handleResult.getErrorMsg());
                            String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                            outBoundOrderDao.notifyFail(outboundOrder.getGongxiaoOutboundOrderNo(),outboundOrder.getConnectedProduct(),SyncWmsEnum.SYNC_WMS_FAIL.getStatus(), newTraceLog);
                        }

                    }
                } else {     //wms无法按照订单出库，设置订单状态为“已同步到wms”,并打印tracelog日志
                    TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms无法按照订单出库" + cancelResult.getError());
                    String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                    outBoundOrderDao.notifyWMSSuccessTmsFail(outboundOrder.getGongxiaoOutboundOrderNo(),outboundOrder.getConnectedProduct(), newTraceLog);
                }
            }

            logger.info("[OUT] get NotificationWmsOutboundTask.run() success");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
