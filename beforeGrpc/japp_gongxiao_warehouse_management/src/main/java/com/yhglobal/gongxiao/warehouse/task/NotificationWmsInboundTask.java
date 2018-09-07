package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.foundation.channel.Channel;
import com.yhglobal.gongxiao.foundation.channel.ChannelDao;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.dao.ProjectDao;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationInboundService;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehousemanagement.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.ReceiverInfo;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.Result;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.SenderInfo;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.cancel.WmsCanselOrderResp;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instock.StockInOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instock.WmsInStockNotifyRequest;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.SyncWmsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 采购预约入库后 通过该任务 通知WMS收货
 */
public class NotificationWmsInboundTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(NotificationWmsInboundTask.class);

    private RpcHeader rpcHeader;

    private InBoundOrderDao inBoundOrderDao;

    private WarehouseService warehouseService;

    private ProductService productService;

    private ProjectService projectService;

    private String orderSourceNo;

    private InboundOrder inboundOrder;

    private List<InboundOrderItem> inboundOrderItemList;

    private ApplicationContext applicationContext;

    private WarehouseConfig warehouseConfig;

    public NotificationWmsInboundTask(RpcHeader rpcHeader, ApplicationContext applicationContext, String orderSourceNo, InboundOrder inboundOrder, List<InboundOrderItem> inboundOrderItemList, InBoundOrderDao inBoundOrderDao, WarehouseService warehouseService, ProductService productService, ProjectService projectService, WarehouseConfig warehouseConfig) {
        this.rpcHeader = rpcHeader;
        this.applicationContext = applicationContext;
        this.orderSourceNo = orderSourceNo;
        this.inboundOrder = inboundOrder;
        this.inboundOrderItemList = inboundOrderItemList;
        this.inBoundOrderDao = inBoundOrderDao;
        this.warehouseService = warehouseService;
        this.productService = productService;
        this.projectService = projectService;
        this.warehouseConfig = warehouseConfig;
    }

    @Override
    public void run() {
        logger.info("入库单信息已经记录完毕，开始通知wms系统准备收货");
        logger.info("[IN][run] start NotificationWmsInboundTask.run() params: inboundOrder={},inboundOrderItemList={}", JSON.toJSONString(inboundOrder), JSON.toJSONString(inboundOrderItemList));
        try {
            WmsInStockNotifyRequest wmsInStockNotifyRequest = new WmsInStockNotifyRequest();
            ProjectDao projectDao = applicationContext.getBean("projectDao", ProjectDao.class);
            Project project = projectDao.getByProjectId(inboundOrder.getProjectId());
            String easProjectCode = project.getEasProjectCode();
            wmsInStockNotifyRequest.setProjectCode(easProjectCode);                                             //项目编码
            Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, inboundOrder.getWarehouseId());    //查询仓库编码
            wmsInStockNotifyRequest.setCkid(warehouse.getWmsWarehouseCode());                                     //对应wms仓库的ckid,我们系统DB里面的仓库编码  必选
            wmsInStockNotifyRequest.setWarehouseCode("EAS_" + project.getEasProjectCode());                           //对应的是wms固定前缀 "EAS_项目编码"  必选
            wmsInStockNotifyRequest.setOrderNo(inboundOrder.getGongxiaoInboundOrderNo());                                  //订单号    必选

            ChannelDao channelDao = applicationContext.getBean("channelDao", ChannelDao.class);
            Channel channel = channelDao.selectByChannelId(inboundOrder.getSourceChannel());
            wmsInStockNotifyRequest.setCustCode(channel.getCustCode());                                                     //客户代码  必选
            wmsInStockNotifyRequest.setOrderType(inboundOrder.getInboundType());                                            //操作类型/订单类型 必选
            wmsInStockNotifyRequest.setOrderSource(channel.getChannelName());                    //订单来源  必选
            wmsInStockNotifyRequest.setSourceOrderNo(orderSourceNo);    //来源单号  可选
            ReceiverInfo receiverInfo = new ReceiverInfo();
            wmsInStockNotifyRequest.setReceiverInfo(receiverInfo);              //收货方信息  必选
            SenderInfo senderInfo = new SenderInfo();
            senderInfo.setSenderAddress(inboundOrder.getDeliverAddress());      //发货方地址   必选
            senderInfo.setSenderName(inboundOrder.getContactsPeople());
            wmsInStockNotifyRequest.setSenderInfo(senderInfo);                  //发货方信息   必选
            List<StockInOrderItem> orderItemList = new ArrayList<>();
            for (InboundOrderItem record : inboundOrderItemList) {
                ProductBasic productBasic = productService.getByProductCode(rpcHeader, record.getProductCode());    //转换成wms商品编码
                StockInOrderItem stockInOrderItem = new StockInOrderItem();
                stockInOrderItem.setItemNo(record.getInboundOrderItemNo());         //行号
                stockInOrderItem.setItemCode(productBasic.getWMSCode());            //商品编码  必选
                stockInOrderItem.setItemName(record.getProductName());
                stockInOrderItem.setInventoryType(WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType());  //库存类型  必选
                stockInOrderItem.setItemQuantity(record.getTotalQuantity());        //商品数量  必选
                stockInOrderItem.setBatchCode(inboundOrder.getBatchNo());           //批次号
                stockInOrderItem.seteRPBatchCode(inboundOrder.getBatchNo());        //wms系统实际上用这个来接收分销系统的批次
                orderItemList.add(stockInOrderItem);
            }
            wmsInStockNotifyRequest.setOrderItemList(orderItemList);                      //订单商品信息  必选
            wmsInStockNotifyRequest.setRemark(inboundOrder.getNote());


            WmsNotificationInboundService wmsNotificationInboundService = applicationContext.getBean("wmsNotificationInboundServiceImpl", WmsNotificationInboundService.class);

            try {
                String result = wmsNotificationInboundService.notificationWmsInboundInfo(wmsInStockNotifyRequest, warehouseConfig.getWmsUrl());
                WmsCanselOrderResp cancelResult = JSON.parseObject(result, new TypeReference<WmsCanselOrderResp>() {
                });
                Date dateTime = new Date();

                if (cancelResult == null) {
                    TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms系统网络超时,重新同步到wms");
                    String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), failTraceLog);
                    inBoundOrderDao.notifyFail(wmsInStockNotifyRequest.getOrderNo(), newTraceLog);
                } else {
                    if (cancelResult.isSuccess()) {    //先判断是否成功接收到wms反馈信息
                        Result handleResult = cancelResult.getResult();
                        if (handleResult.isSuccess()) {      //再判断wms是否预约入库成功

                            TraceLog successTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "预约入库成功");
                            String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), successTraceLog);
                            inBoundOrderDao.notifySuccess(wmsInStockNotifyRequest.getOrderNo(), SyncWmsEnum.SYNC_WMS_SUCCESS.getStatus(), newTraceLog);

                        } else {    //wms入库失败
                            if (handleResult.getErrorMsg().contains("系统繁忙")) {           //如果是系统繁忙导致的失败，重试
                                TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms入库失败:" + handleResult.getErrorMsg());
                                String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), failTraceLog);
                                inBoundOrderDao.notifyFail(wmsInStockNotifyRequest.getOrderNo(), newTraceLog);
                            } else {   //否则设置订单状态为“已同步到wms”,并打印tracelog日志
                                TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms入库失败:" + handleResult.getErrorMsg());
                                String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), failTraceLog);
                                inBoundOrderDao.notifySuccess(handleResult.getOrderNo(), SyncWmsEnum.SYNC_WMS_SUCCESS.getStatus(), newTraceLog);
                            }

                        }
                    } else {     //wms无法按照订单入库，设置订单状态为“已同步到wms”,并打印tracelog日志

                        TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms无法按照订单入库" + cancelResult.getError());
                        String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), failTraceLog);
                        inBoundOrderDao.notifySuccess(wmsInStockNotifyRequest.getOrderNo(), SyncWmsEnum.SYNC_WMS_SUCCESS.getStatus(), newTraceLog);
                    }
                }

                logger.info("[OUT] get NotificationWmsCancelEntryTask.run() success");
            } catch (Exception e) {
                e.printStackTrace();
            }

            logger.info("#traceId={}# [IN][run] get NotificationWmsInboundTask.run() success");
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
        }
    }
}
