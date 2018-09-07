package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.SyncWmsEnum;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.model.InboundOrder;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.ReceiverInfo;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.Result;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.SenderInfo;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.cancel.WmsCanselOrderResp;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.instock.StockInOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.instock.WmsInStockNotifyRequest;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationInboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 采购预约入库后 通过该任务 通知WMS收货
 */
public class NotificationWmsInboundTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(NotificationWmsInboundTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private InBoundOrderDao inBoundOrderDao;

    private String orderSourceNo;

    private InboundOrder inboundOrder;

    private List<InboundOrderItem> inboundOrderItemList;

    private WmsNotificationInboundService wmsNotificationInboundService;

    private WarehouseConfig warehouseConfig;

    public NotificationWmsInboundTask(GongxiaoRpc.RpcHeader rpcHeader, String orderSourceNo, InboundOrder inboundOrder, List<InboundOrderItem> inboundOrderItemList, InBoundOrderDao inBoundOrderDao,WmsNotificationInboundService wmsNotificationInboundService,WarehouseConfig warehouseConfig) {
        this.rpcHeader = rpcHeader;
        this.orderSourceNo = orderSourceNo;
        this.inboundOrder = inboundOrder;
        this.inboundOrderItemList = inboundOrderItemList;
        this.inBoundOrderDao = inBoundOrderDao;
        this.wmsNotificationInboundService = wmsNotificationInboundService;
        this.warehouseConfig = warehouseConfig;
    }

    @Override
    public void run() {
        logger.info("入库单信息已经记录完毕，开始通知wms系统准备收货");
        logger.info("[IN][run] start NotificationWmsInboundTask.run() params: inboundOrder={},inboundOrderItemList={}", JSON.toJSONString(inboundOrder), JSON.toJSONString(inboundOrderItemList));
        try {
            WmsInStockNotifyRequest wmsInStockNotifyRequest = new WmsInStockNotifyRequest();

            //调用基础模拟块的项目的grpc差项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(String.valueOf(inboundOrder.getProjectId())).build();
            ProjectStructure.GetByProjectIdResp rpcResponse = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = rpcResponse.getProject();
            String easProjectCode = project.getEasProjectCode();
            wmsInStockNotifyRequest.setProjectCode(easProjectCode);  //项目编码
            String projectPrrefix = project.getProjectTablePrefix();

            //调用基础模块的仓库grpc服务查仓库编码
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(inboundOrder.getWarehouseId()).build();
            WarehouseStructure.GetWarehouseByIdResp warehouseRpcResp = warehouseService.getWarehouseById(getWarehouseByIdReq);
            WarehouseStructure.Warehouse warehouse = warehouseRpcResp.getWarehouse();
            wmsInStockNotifyRequest.setCkid(warehouse.getWmsWarehouseCode());                //对应wms仓库的ckid,我们系统DB里面的仓库编码  必选
            wmsInStockNotifyRequest.setWarehouseCode("EAS_"+project.getEasProjectCode());    //对应的是wms固定前缀 "EAS_项目编码"  必选
            wmsInStockNotifyRequest.setOrderNo(inboundOrder.getGongxiaoInboundOrderNo());    //订单号    必选

            //调用基础模块的SourceChannel服务
            String channelId = null;
            if (inboundOrder.getGongxiaoInboundOrderNo().contains("shaver")){
                channelId = WmsSourceChannel.CHANNEL_SHAVER.getChannelId();
            }else if (inboundOrder.getGongxiaoInboundOrderNo().contains("PHTM")){
                channelId = WmsSourceChannel.CHANNEL_TMALL.getChannelId();
            }else if(inboundOrder.getGongxiaoInboundOrderNo().contains("JMGO")){
                channelId = WmsSourceChannel.CHANNEL_JMGO.getChannelId();
            }else {
                channelId = WmsSourceChannel.CHANNEL_TUANGOU.getChannelId();
            }
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder().setRpcHeader(rpcHeader).setXpsChannelId(channelId).build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();

            wmsInStockNotifyRequest.setCustCode(sourceChannel.getWmsCustCode());          //客户代码  必选
            wmsInStockNotifyRequest.setOrderType(inboundOrder.getInboundType()); //操作类型/订单类型 必选
            wmsInStockNotifyRequest.setOrderSource(sourceChannel.getWmsOrderSource());   //订单来源  必选
            wmsInStockNotifyRequest.setSourceOrderNo(orderSourceNo);            //来源单号  可选
            ReceiverInfo receiverInfo = new ReceiverInfo();
            wmsInStockNotifyRequest.setReceiverInfo(receiverInfo);              //收货方信息  必选
            SenderInfo senderInfo = new SenderInfo();
            senderInfo.setSenderAddress(inboundOrder.getDeliverAddress());      //发货方地址   必选
            senderInfo.setSenderName(inboundOrder.getContactsPeople());
            wmsInStockNotifyRequest.setSenderInfo(senderInfo);                  //发货方信息   必选
            List<StockInOrderItem> orderItemList = new ArrayList<>();

            //调用基础模块的商品的grpc查询项目信息
            ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            for (InboundOrderItem record : inboundOrderItemList) {
                ProductStructure.GetProductDetailByModelReq getProductDetailByModelReq = ProductStructure.GetProductDetailByModelReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(Long.parseLong(record.getProjectId())).setProductModel(record.getProductCode()).build();
                ProductStructure.GetProductDetailByModelResp response = productService.getProductDetailByModel(getProductDetailByModelReq);
                ProductStructure.ProductBasicDetail productBasicDetail = response.getProductBasicDetail();
                StockInOrderItem stockInOrderItem = new StockInOrderItem();
                stockInOrderItem.setItemNo(record.getInboundOrderItemNo());         //行号
                stockInOrderItem.setItemCode(productBasicDetail.getWmsCode());      //商品编码  必选
                stockInOrderItem.setItemName(record.getProductName());              //商品名称
                stockInOrderItem.setInventoryType(WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType());       //库存类型  必选
                stockInOrderItem.setItemQuantity(record.getTotalQuantity());        //商品数量  必选
                stockInOrderItem.setBatchCode(inboundOrder.getBatchNo());           //批次号
                stockInOrderItem.seteRPBatchCode(inboundOrder.getBatchNo());   //wms系统实际通过这个接收批次
                orderItemList.add(stockInOrderItem);
            }
            wmsInStockNotifyRequest.setOrderItemList(orderItemList);                      //订单商品信息  必选
            wmsInStockNotifyRequest.setRemark(inboundOrder.getNote());                    //备注   可选


            try {
                String result = wmsNotificationInboundService.notificationWmsInboundInfo(wmsInStockNotifyRequest,warehouseConfig.getWmsUrl());
                WmsCanselOrderResp cancelResult = JSON.parseObject(result, new TypeReference<WmsCanselOrderResp>() {
                });
                Date dateTime = new Date();

                if (cancelResult == null) {
                    TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms系统网络超时,重新同步到wms");
                    String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), failTraceLog);
                    inBoundOrderDao.notifyFail(wmsInStockNotifyRequest.getOrderNo(), newTraceLog, projectPrrefix);
                } else {
                    if (cancelResult.isSuccess()) {    //先判断是否成功接收到wms反馈信息
                        Result handleResult = cancelResult.getResult();
                        if (handleResult.isSuccess()) {      //再判断wms是否预约入库成功

                            TraceLog successTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "预约入库成功");
                            String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), successTraceLog);
                            inBoundOrderDao.notifySuccess(wmsInStockNotifyRequest.getOrderNo(), SyncWmsEnum.SYNC_WMS_SUCCESS.getStatus(), newTraceLog, projectPrrefix);

                        } else {    //wms入库失败
                            if (handleResult.getErrorMsg().contains("系统繁忙")) {           //如果是系统繁忙导致的失败，重试
                                TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms入库失败:" + handleResult.getErrorMsg());
                                String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), failTraceLog);
                                inBoundOrderDao.notifyFail(wmsInStockNotifyRequest.getOrderNo(), newTraceLog, projectPrrefix);
                            } else {   //否则设置订单状态为“已同步到wms”,并打印tracelog日志
                                TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms入库失败:" + handleResult.getErrorMsg());
                                String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), failTraceLog);
                                inBoundOrderDao.notifySuccess(handleResult.getOrderNo(), SyncWmsEnum.SYNC_WMS_SUCCESS.getStatus(), newTraceLog, projectPrrefix);
                            }

                        }
                    } else {     //wms无法按照订单入库，设置订单状态为“已同步到wms”,并打印tracelog日志

                        TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms无法按照订单入库" + cancelResult.getError());
                        String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), failTraceLog);
                        inBoundOrderDao.notifySuccess(wmsInStockNotifyRequest.getOrderNo(), SyncWmsEnum.SYNC_WMS_SUCCESS.getStatus(), newTraceLog, projectPrrefix);
                    }
                }

                logger.info("[OUT] get NotificationWmsInboundTask.run() success");
            } catch (Exception e) {
                e.printStackTrace();
            }

            logger.info("#traceId={}# [IN][run] get NotificationWmsInboundTask.run() success");
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
        }
    }
}
