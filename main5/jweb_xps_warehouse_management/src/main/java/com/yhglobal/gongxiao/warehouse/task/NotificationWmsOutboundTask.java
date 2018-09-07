package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
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
import com.yhglobal.gongxiao.type.WmsOrderType;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrder;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.ReceiverInfo;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.Result;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.SenderInfo;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.cancel.WmsCanselOrderResp;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.outstock.StockOutOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.outstock.WmsOutStockNotifyRequest;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationOutboundService;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.XpsWarehouseNotifyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 销售预约出库后 通过该任务 通知WMS发货
 */
public class NotificationWmsOutboundTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(NotificationWmsOutboundTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private OutboundOrder outboundOrder;

    private List<OutboundOrderItem> outboundOrderItemList;

    private OutBoundOrderDao outBoundOrderDao;

    private WmsNotificationOutboundService wmsNotificationOutboundService;

    private  WarehouseConfig warehouseConfig;

    public NotificationWmsOutboundTask(GongxiaoRpc.RpcHeader rpcHeader,
                                       OutboundOrder outboundOrder,
                                       List<OutboundOrderItem> outboundOrderItemList,
                                       OutBoundOrderDao outBoundOrderDao,
                                       WmsNotificationOutboundService wmsNotificationOutboundService,
                                       WarehouseConfig warehouseConfig) {
        this.rpcHeader = rpcHeader;
        this.outboundOrder = outboundOrder;
        this.outboundOrderItemList = outboundOrderItemList;
        this.outBoundOrderDao = outBoundOrderDao;
        this.wmsNotificationOutboundService = wmsNotificationOutboundService;
        this.warehouseConfig = warehouseConfig;
    }

    @Override
    public void run() {
        logger.info("traceId=#{} [IN][run] start NotificationWmsOutboundTask,params: outboundOrder={},outboundOrderItemList={}", rpcHeader.getTraceId(), JSON.toJSONString(outboundOrder), JSON.toJSONString(outboundOrderItemList));

        String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(outboundOrder.getProjectId()));
        int i = outBoundOrderDao.updateWmsFlagByOrder(outboundOrder.getGongxiaoOutboundOrderNo(),SyncWmsEnum.SYNC_WMS_ING.getStatus(),outboundOrder.getDataVersion(),projectPrefix);
        if (1==i) {        //向WMS同步之前，先将订单状态改为“同步中”，如果跟新成功，说明只有这个线程拿到这条数据,否则该数据已经被其他线程拿到
            WmsOutStockNotifyRequest wmsOutStockNotifyRequest = new WmsOutStockNotifyRequest();

            //调用基础模块的SourceChannel服务
            String channelId = null;
            if (outboundOrder.getGongxiaoOutboundOrderNo().contains("shaver")){
                channelId = WmsSourceChannel.CHANNEL_SHAVER.getChannelId();
            }else if (outboundOrder.getGongxiaoOutboundOrderNo().contains("PHTM")){
                channelId = WmsSourceChannel.CHANNEL_TMALL.getChannelId();
            }else if(outboundOrder.getGongxiaoOutboundOrderNo().contains("JMGO")){
                channelId = WmsSourceChannel.CHANNEL_JMGO.getChannelId();
            }else {
                channelId = WmsSourceChannel.CHANNEL_TUANGOU.getChannelId();
            }
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder().setRpcHeader(rpcHeader).setXpsChannelId(channelId).build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            try {

                wmsOutStockNotifyRequest.setCustCode(sourceChannel.getWmsCustCode());                       //客户代码  必选

                //调用基础模块的仓库grpc服务查仓库编码
                WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
                WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(outboundOrder.getWarehouseId()).build();
                WarehouseStructure.GetWarehouseByIdResp rpcResponse = warehouseService.getWarehouseById(getWarehouseByIdReq);
                WarehouseStructure.Warehouse warehouse = rpcResponse.getWarehouse();
                wmsOutStockNotifyRequest.setCkid(warehouse.getWmsWarehouseCode());   //对应wms仓库的ckid,我们系统DB里面的仓库编码  必选

                //调用基础模块的项目的grpc查询项目信息
                ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
                ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(outboundOrder.getProjectId()).build();
                ProjectStructure.GetByProjectIdResp projectIdResp = projectService.getByProjectId(getByProjectIdReq);
                ProjectStructure.Project project = projectIdResp.getProject();
                wmsOutStockNotifyRequest.setWarehouseCode("EAS_" + project.getEasProjectCode());     //对应的是wms固定前缀 "EAS_项目编码"  必选
                wmsOutStockNotifyRequest.setProjectCode(project.getEasProjectCode());              //项目编码
                wmsOutStockNotifyRequest.setOrderNo(outboundOrder.getGongxiaoOutboundOrderNo());   //订单号(出库单单号) 必选
                wmsOutStockNotifyRequest.setSourceOrderNo(outboundOrder.getSalesOrderNo());
                wmsOutStockNotifyRequest.setOrderSource(sourceChannel.getWmsOrderSource());
                wmsOutStockNotifyRequest.setOrderType(outboundOrder.getOutboundType());            //操作类型/订单类型 必选
                wmsOutStockNotifyRequest.setTransportMode(String.valueOf(outboundOrder.getShippingMode()));
                wmsOutStockNotifyRequest.setOrderCreateTime(outboundOrder.getCreateTime());

                //调用查询客户信息的接口，查询客户名称和客户代码调用查询客户信息的接口，查询客户名称和客户代码
                DistributorServiceGrpc.DistributorServiceBlockingStub distributorService = GlobalRpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
                DistributorStructure.GetDistributorBusinessByIdReq getDistributorBusinessByIdReq = DistributorStructure.GetDistributorBusinessByIdReq.newBuilder().setRpcHeader(rpcHeader).setDistributorBusinessId(Long.parseLong(outboundOrder.getCustomerId())).build();
                DistributorStructure.GetDistributorBusinessByIdResp getDistributorBusinessByIdResp = distributorService.getDistributorBusinessById(getDistributorBusinessByIdReq);
                DistributorStructure.DistributorBusiness distributorBusiness= getDistributorBusinessByIdResp.getDistributorBusiness();

                if (outboundOrder.getOutboundType() == WmsOrderType.OUTBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode()){
                    wmsOutStockNotifyRequest.setSupplierCode("YH13040801");  //客户代码
                    wmsOutStockNotifyRequest.setSupplierName("深圳越海全球供应链有限公司"); //客户名称
                }else {
                    wmsOutStockNotifyRequest.setSupplierCode(distributorBusiness.getDistributorEasCode());  //客户代码
                    wmsOutStockNotifyRequest.setSupplierName(distributorBusiness.getDistributorName()); //客户名称
                }


                ReceiverInfo receiverInfo = new ReceiverInfo();
                receiverInfo.setReceiverProvince(outboundOrder.getProvinceName());
                receiverInfo.setReceiverCity(outboundOrder.getCityName());
                receiverInfo.setReceiverArea(outboundOrder.getShippingAddress());
                receiverInfo.setReceiverName(outboundOrder.getContactsPeople());
                receiverInfo.setReceiverMobile(outboundOrder.getPhoneNum());
                receiverInfo.setReceiverPhone(outboundOrder.getPhoneNum());
                SenderInfo senderInfo = new SenderInfo();
                senderInfo.setSenderProvince(warehouse.getProvinceName());
                senderInfo.setSenderCity(warehouse.getCityName());
                senderInfo.setSenderArea(warehouse.getShortAddress());
                senderInfo.setSenderAddress(warehouse.getStreetAddress());
                senderInfo.setSenderName(warehouse.getGeneralContactName());
                wmsOutStockNotifyRequest.setReceiverInfo(receiverInfo);                                    //收货方信息   必选
                wmsOutStockNotifyRequest.setSenderInfo(senderInfo);                                        //发货方信息   必选

                List<StockOutOrderItem> orderItemList = new ArrayList<>();

                //调用基础模块的商品的grpc查询项目信息
                ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                for (OutboundOrderItem record : outboundOrderItemList) {
                    ProductStructure.GetProductDetailByModelReq getProductDetailByModelReq = ProductStructure.GetProductDetailByModelReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(Long.parseLong(outboundOrder.getProjectId())).setProductModel(record.getProductCode()).build();
                    ProductStructure.GetProductDetailByModelResp response = productService.getProductDetailByModel(getProductDetailByModelReq);
                    ProductStructure.ProductBasicDetail productBasicDetail = response.getProductBasicDetail();
                    StockOutOrderItem stockOutOrderItem = new StockOutOrderItem();
                    stockOutOrderItem.setItemNO(record.getOutboundOrderItemNo());         //wms出库行号(为了匹配WMS返回的出库明细)
                    stockOutOrderItem.setItemCode(productBasicDetail.getWmsCode());       //商品编码  必选
                    stockOutOrderItem.setItemName(productBasicDetail.getProductName());
                    stockOutOrderItem.setInventoryType(record.getInventoryType());  //库存类型  必选
                    stockOutOrderItem.setItemQuantity(record.getTotalQuantity());   //商品数量  必选
                    stockOutOrderItem.setBatchCode(record.getBatchNo());       //批次号
                    stockOutOrderItem.seteRPBatchCode(record.getBatchNo());     //wms系统实际通过这个接收批次
                    orderItemList.add(stockOutOrderItem);
                }
                wmsOutStockNotifyRequest.setOrderItemList(orderItemList);    //订单商品信息  必选
                wmsOutStockNotifyRequest.setRemark(outboundOrder.getNote());
            } catch (Exception e) {
                logger.error("# errorMessage:" + e.getMessage(), e);
                TraceLog failTraceLog = new TraceLog(new Date().getTime(), "分销系统", "分销系统", "分销系统内部错误" + e.getMessage().substring(1,20));
                String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                outBoundOrderDao.notifyFail(outboundOrder.getGongxiaoOutboundOrderNo(), SyncWmsEnum.SYNC_WMS_FAIL.getStatus(),newTraceLog,projectPrefix);
                throw e;
            }

            //通知wms出库
            try {
                String result = wmsNotificationOutboundService.notificationWmsOutboundInfo(wmsOutStockNotifyRequest, warehouseConfig.getWmsUrl());
                WmsCanselOrderResp cancelResult = JSON.parseObject(result, new TypeReference<WmsCanselOrderResp>() {
                });
                Date dateTime = new Date();

                if (cancelResult == null) {   //如果反序列化的结果为空，说明通知WMS的时候超时了
                    TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms系统网络超时,重新同步到wms");
                    String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                    outBoundOrderDao.notifyFail(outboundOrder.getGongxiaoOutboundOrderNo(), SyncWmsEnum.SYNC_WMS_FAIL.getStatus(), newTraceLog,projectPrefix);
                } else {  //如果反序列化的结果不为空，根据WMS返回的结果判断是否推送成功
                    if (cancelResult.isSuccess()) {    //判断是否成功接收到wms反馈信息
                        Result handleResult = cancelResult.getResult();
                        if (handleResult.isSuccess()) {      //再判断wms是否预约出库成功
                            TraceLog successTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "预约出库成功:" + handleResult.getMessage());
                            String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), successTraceLog);
                            outBoundOrderDao.notifySuccess(outboundOrder.getGongxiaoOutboundOrderNo(), outboundOrder.getConnectedProduct(), SyncWmsEnum.SYNC_WMS_SUCCESS.getStatus(), newTraceLog,projectPrefix);

                            //通知销售模块,出库单已经通知到WMS了，可以通知TMS发货了
                            logger.info("开始通知销售模块通知TMS系统");
                            XpsWarehouseNotifyManager.notifySalesSendCar(sourceChannel.getXpsWarehouseNotifyUrl(), Long.parseLong(outboundOrder.getProjectId()), outboundOrder.getGongxiaoOutboundOrderNo());
//                        salesScheduleDeliveryService.submitOutboundOrderToTms(rpcHeader, outboundOrder.getGongxiaoOutboundOrderNo());
                            logger.info("通知销售模块通知TMS系统成功");

                        } else {             //如果wms出库失败
                            if (handleResult.getErrorMsg().contains("系统繁忙")) {           //如果是系统繁忙导致的失败，重试
                                TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms出库失败:" + handleResult.getErrorMsg());
                                String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                                outBoundOrderDao.notifyFail(outboundOrder.getGongxiaoOutboundOrderNo(), SyncWmsEnum.SYNC_WMS_FAIL.getStatus(), newTraceLog,projectPrefix);
                            } else {                     //否则设置订单状态为“已同步到wms”,并打印tracelog日志
                                TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms出库失败:" + handleResult.getErrorMsg());
                                String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                                outBoundOrderDao.notifyFail(outboundOrder.getGongxiaoOutboundOrderNo(), SyncWmsEnum.SYNC_WMS_FAIL.getStatus(), newTraceLog,projectPrefix);
                            }

                        }
                    } else {     //wms无法按照订单出库，设置订单状态为“已同步到wms”,并打印tracelog日志
                        TraceLog failTraceLog = new TraceLog(dateTime.getTime(), "wms", "WMS系统", "wms无法按照订单出库" + cancelResult.getError());
                        String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                        outBoundOrderDao.notifyWMSSuccessTmsFail(outboundOrder.getGongxiaoOutboundOrderNo(), newTraceLog,projectPrefix);
                    }
                }

                logger.info("[OUT] get NotificationWmsOutboundTask.run() success");
            } catch (Exception e) {
                logger.error("# errorMessage:" + e.getMessage(), e);
                TraceLog failTraceLog = new TraceLog(new Date().getTime(), "分销系统", "分销系统", "分销系统内部错误" + e.getMessage().substring(1,20));
                String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                outBoundOrderDao.notifyFail(outboundOrder.getGongxiaoOutboundOrderNo(), SyncWmsEnum.SYNC_WMS_FAIL.getStatus(),newTraceLog,projectPrefix);
            }
        }else {
            logger.info("[OUT] get NotificationWmsOutboundTask success: gongxiaoOutboundOrderNo={},线程没有抢到数据,线程结束",outboundOrder.getGongxiaoOutboundOrderNo());
        }

    }

}
