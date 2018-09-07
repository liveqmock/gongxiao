package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.InboundOrderStatusEnum;
import com.yhglobal.gongxiao.type.SyncWmsEnum;
import com.yhglobal.gongxiao.type.WmsOrderType;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.InboundOrderItemDao;
import com.yhglobal.gongxiao.warehouse.model.InboundOrder;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.PlanInboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.cancel.WmsCancelOrderRequest;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.close.OrderItemDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.close.WmsCloseOrderRequest;
import com.yhglobal.gongxiao.warehouse.service.InboundService;
import com.yhglobal.gongxiao.warehouse.service.WmsCancelOrderService;
import com.yhglobal.gongxiao.warehouse.service.WmsCloseOrderService;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationInboundService;
import com.yhglobal.gongxiao.warehouse.task.NotificationWmsCancelEntryTask;
import com.yhglobal.gongxiao.warehouse.task.NotificationWmsCloseEntryTask;
import com.yhglobal.gongxiao.warehouse.task.NotificationWmsInboundTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 预约出库是实现类
 *
 * @author : liukai
 */
@Service
public class InboundServiceImpl implements InboundService {

    private Logger logger = LoggerFactory.getLogger(InboundServiceImpl.class);

    @Autowired
    InBoundOrderDao inBoundOrderDao;

    @Autowired
    InboundOrderItemDao inboundOrderItemDao;

    @Autowired
    WmsCancelOrderService wmsCancelOrderService;

    @Autowired
    WmsCloseOrderService wmsCloseOrderService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    WarehouseConfig warehouseConfig;

    @Autowired
    WmsNotificationInboundService wmsNotificationInboundService;

    private static final int WMS_ORDERNO_LENGTH_LIMIT = 32;

    @Override
    public GongxiaoResult createInboundOrder(GongxiaoRpc.RpcHeader rpcHeader, Date expectArrivalTime, int purchaseType, String orderSourceNo, String sourceChannelId,
                                             int inboundType, String projectId, String traceNo, String senderName, String senderPhoneNo, String warehouseId, String warehouseName,
                                             String deliverAddress, int shippingMode, String logisticsCompanyName, String logisticsNo, String note, int totalQuantity,
                                             List<PlanInboundOrderItem> itemList, String signature, String uniqueNo) {
        logger.info("#traceId={}# [IN][createInboundOrder] params:  expectArrivalTime={},purchaseType={},orderSourceNo={},sourceChannelId={},projectId={},traceNo={},senderName={},senderPhoneNo={},warehouseId={},warehouseName={},deliverAddress={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={},uniqueNo={}", rpcHeader.getTraceId(),
                expectArrivalTime, purchaseType, orderSourceNo, sourceChannelId, projectId, traceNo, senderName, senderPhoneNo, warehouseId, warehouseName, deliverAddress, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature, uniqueNo);
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            //调用基础模块的项目的grpc查询项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
            ProjectStructure.GetByProjectIdResp rpcResponse = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = rpcResponse.getProject();
            String prefix = project.getProjectTablePrefix();

            //先判断是否是重复请求
            int count = inBoundOrderDao.judgeExit(uniqueNo, prefix);
            if (count <= 0) {
                try {
                    String gongxiaoInboundOrderNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.STOCK_POIN_ORDER_NO);
                    if (gongxiaoInboundOrderNo.length() > WMS_ORDERNO_LENGTH_LIMIT) {     //如果生成的入库单号超过wms系统限制的32位直接返回
                        logger.error("#traceId={}# [OUT] get createInboundOrder success: gongxiaoInboundOrderNo={} , over wms system limit 32", rpcHeader.getTraceId(), gongxiaoInboundOrderNo);
                        return new GongxiaoResult(ErrorCode.INBOUND_ORDERNO_LENGTH_OVER_LIMIT.getErrorCode(), ErrorCode.INBOUND_ORDERNO_LENGTH_OVER_LIMIT.getMessage());
                    }
                    InboundOrder newInbountOrder = new InboundOrder();
                    newInbountOrder.setUniqueNo(uniqueNo);
                    newInbountOrder.setSupplierName(project.getSupplierName());
                    newInbountOrder.setPurchaseType(purchaseType);  //采购类型
                    newInbountOrder.setInboundType(inboundType);   //入库类型 -----wms必传
                    newInbountOrder.setProjectId(Long.valueOf(projectId));
                    newInbountOrder.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);   //入库单号
                    String batchNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.STOCK_BATCH_NO);
                    newInbountOrder.setBatchNo(batchNo);
                    newInbountOrder.setSourceChannel(Integer.parseInt(sourceChannelId));   //发起入库通知单的渠道
                    if (inboundType == WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode()) {
                        newInbountOrder.setPurchaseOrderNo(orderSourceNo);       //采购单号
                    } else if (inboundType == WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode()) {
                        newInbountOrder.setSalesOrderNo(orderSourceNo);   //销售单号
                    }

                    List<String> connectedProduct = new ArrayList<>();
                    List<String> connectedGood = new ArrayList<>();
                    for (PlanInboundOrderItem record : itemList) {     //遍历预约入库明细
                        connectedProduct.add(record.getProductCode());
                        connectedGood.add(record.getProductCode());
                    }

                    newInbountOrder.setDeliverAddress(deliverAddress); //发货地址
                    newInbountOrder.setContactsPeople(senderName);    //发货联系人
                    newInbountOrder.setPhoneNum(senderPhoneNo);       //联系电话
                    newInbountOrder.setConnectedProduct(JSON.toJSONString(connectedProduct));
                    newInbountOrder.setConnectedGood(JSON.toJSONString(connectedGood));
                    newInbountOrder.setWarehouseId(warehouseId);
                    newInbountOrder.setWarehouseName(warehouseName);
                    newInbountOrder.setShippingMode(shippingMode);  //物流方式
                    newInbountOrder.setNote(note);
                    newInbountOrder.setTotalQuantity(totalQuantity);  //预收入库总数
                    newInbountOrder.setExpectedArrivalTime(expectArrivalTime);   //预计到货时间
                    newInbountOrder.setOrderStatus(InboundOrderStatusEnum.INBOUND_ORDER_WAIT.getStatus());
                    newInbountOrder.setSyncToWmsFlag(SyncWmsEnum.SYNC_WMS_FAIL.getStatus());   //是否同步到wms
                    newInbountOrder.setCreateTime(new Date());
                    newInbountOrder.setDataVersion(0);
                    TraceLog traceLog = new TraceLog();                                               //操作日志
                    Date date = new Date();
                    traceLog.setOpTime(date.getTime());
                    traceLog.setContent("创建入库单");
                    traceLog.setOpName(rpcHeader.getUsername());
                    traceLog.setOpUid(String.valueOf(rpcHeader.getTraceId()));
                    newInbountOrder.setTracelog(TraceLogUtil.appendTraceLog(newInbountOrder.getTracelog(), traceLog));
                    newInbountOrder.setCreateTime(new Date());

                    List<InboundOrderItem> inboundOrderItemList = new ArrayList<>();
                    if (itemList.size() > 0) {                                         //遍历入库单里面商品详情,组装入库商品的详情记录
                        for (PlanInboundOrderItem planInboundOrderItem : itemList) {
                            InboundOrderItem inboundOrderItem = new InboundOrderItem();
                            inboundOrderItem.setInventoryType(planInboundOrderItem.getInventoryType());
                            inboundOrderItem.setProjectId(projectId);
                            inboundOrderItem.setPurchaseOrderNo(planInboundOrderItem.getPurchaseOrderNo());
                            inboundOrderItem.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);
                            inboundOrderItem.setBatchNo(batchNo);
                            String inboundOrderItemNo = String.valueOf(CommonUtil.getOderNumber());
                            inboundOrderItem.setInboundOrderItemNo(inboundOrderItemNo);
                            inboundOrderItem.setWarehouseId(planInboundOrderItem.getWarehouseId());
                            inboundOrderItem.setWarehouseName(planInboundOrderItem.getWarehouseName());
                            inboundOrderItem.setProductId(planInboundOrderItem.getProductId());
                            inboundOrderItem.setProductCode(planInboundOrderItem.getProductCode());
                            inboundOrderItem.setProductName(planInboundOrderItem.getProductName());
                            inboundOrderItem.setProductUnit(planInboundOrderItem.getProductUnit());
                            inboundOrderItem.setTotalQuantity(planInboundOrderItem.getTotalQuantity());
                            inboundOrderItem.setPurchasePrice(planInboundOrderItem.getPurchasePrice());
                            inboundOrderItem.setCostPrice(planInboundOrderItem.getCostPrice());
                            inboundOrderItem.setCreateTime(new Date());
                            inboundOrderItemList.add(inboundOrderItem);
                        }
                        inBoundOrderDao.insertInStorageInfo(newInbountOrder, prefix);                               //将入库单信息记录到数据库
                        inboundOrderItemDao.inserInboundOrderItemInfo(inboundOrderItemList, prefix);                //将入库单对应的明细记录到明细表
                    }

                    //通知Wms入库
                    try {
                        NotificationWmsInboundTask inboundTask = new NotificationWmsInboundTask(rpcHeader, orderSourceNo, newInbountOrder, inboundOrderItemList, inBoundOrderDao, wmsNotificationInboundService, warehouseConfig);
                        threadPoolTaskExecutor.submit(inboundTask);
                    } catch (Exception e) {
                        gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                        gongxiaoResult.setData(gongxiaoInboundOrderNo);
                        logger.error("#traceId={}# [OUT] get createInboundOrder success: gongxiaoInboundOrderNo={}", rpcHeader.getTraceId(), gongxiaoInboundOrderNo);
                        return gongxiaoResult;
                    }
                    logger.info("#traceId={}# [OUT] get createInboundOrder success: gongxiaoInboundOrderNo={}", rpcHeader.getTraceId(), gongxiaoInboundOrderNo);
                    gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                    gongxiaoResult.setData(gongxiaoInboundOrderNo);

                } catch (Exception e) {
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                    gongxiaoResult.setReturnCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
                }
            } else {
                gongxiaoResult.setReturnCode(ErrorCode.DUPLICATED_REQUEST.getErrorCode());
            }

        } catch (Exception e) {
            gongxiaoResult.setReturnCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }
        return gongxiaoResult;
    }

    @Override
    public GongxiaoResult createInboundOrder2(GongxiaoRpc.RpcHeader rpcHeader,
                                              String orderSourceNo,
                                              String sourceChannelId,
                                              int inboundType,
                                              String projectId,
                                              String traceNo,
                                              String senderName,
                                              String senderPhoneNo,
                                              String warehouseId,
                                              String warehouseName,
                                              String deliverAddress,
                                              int shippingMode,
                                              String logisticsCompanyName,
                                              String logisticsNo,
                                              String note,
                                              int totalQuantity,
                                              List<PlanInboundOrderItem> itemList,
                                              String signature,
                                              String uniqueNo) {
        logger.info("#traceId={}# [IN][createInboundOrder] params: orderSourceNo={},sourceChannelId={},projectId={},traceNo={},senderName={},senderPhoneNo={},warehouseId={},warehouseName={},phoneNum={},deliverAddress={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={}", rpcHeader.getTraceId(), orderSourceNo, sourceChannelId, projectId, traceNo, senderName, senderPhoneNo, warehouseId, warehouseName, deliverAddress, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature);
        GongxiaoResult gongxiaoResult = new GongxiaoResult();

        //调用基础模块的项目的grpc查询项目信息
        ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
        ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
        ProjectStructure.GetByProjectIdResp rpcResponse = projectService.getByProjectId(getByProjectIdReq);
        ProjectStructure.Project project = rpcResponse.getProject();
        String prefix = project.getProjectTablePrefix();
        int count = inBoundOrderDao.judgeExit(uniqueNo, prefix);
        if (count <= 0) {
            try {
                String gongxiaoInboundOrderNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.STOCK_SOIN_ORDER_NO);
                if (gongxiaoInboundOrderNo.length() > WMS_ORDERNO_LENGTH_LIMIT) {     //如果生成的入库单号超过wms系统限制的32位直接返回
                    logger.error("#traceId={}# [OUT] get createInboundOrder2 success: gongxiaoInboundOrderNo={} , over wms system limit 32", rpcHeader.getTraceId(), gongxiaoInboundOrderNo);
                    return new GongxiaoResult(ErrorCode.INBOUND_ORDERNO_LENGTH_OVER_LIMIT.getErrorCode(), ErrorCode.INBOUND_ORDERNO_LENGTH_OVER_LIMIT.getMessage());
                }
                InboundOrder newInbountOrder = new InboundOrder();
                newInbountOrder.setUniqueNo(uniqueNo);
                newInbountOrder.setSupplierName(project.getSupplierName());
                newInbountOrder.setInboundType(inboundType);   //入库类型 -----wms必传
                newInbountOrder.setProjectId(Long.valueOf(projectId));
                newInbountOrder.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);
                String batchNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.STOCK_BATCH_NO);
                newInbountOrder.setBatchNo(batchNo);
                newInbountOrder.setSourceChannel(Integer.parseInt(sourceChannelId));  //发起入库通知单的渠道
                if (inboundType == WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode()) {
                    newInbountOrder.setPurchaseOrderNo(orderSourceNo);    //采购单号
                } else if (inboundType == WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode()) {
                    newInbountOrder.setSalesOrderNo(orderSourceNo);    //销售单号
                }

                List<String> connectedProduct = new ArrayList<>();
                List<String> connectedGood = new ArrayList<>();
                for (PlanInboundOrderItem record : itemList) {     //遍历预约入库明细
                    connectedProduct.add(record.getProductCode());
                    connectedGood.add(record.getProductCode());
                }

                newInbountOrder.setDeliverAddress(deliverAddress);
                newInbountOrder.setContactsPeople(senderName);
                newInbountOrder.setPhoneNum(senderPhoneNo);
                newInbountOrder.setConnectedProduct(JSON.toJSONString(connectedProduct));   //入库单关联的商品编码
                newInbountOrder.setConnectedGood(JSON.toJSONString(connectedGood));   //入库单关联的产品编码
                newInbountOrder.setWarehouseId(warehouseId);
                newInbountOrder.setWarehouseName(warehouseName);
                newInbountOrder.setShippingMode(shippingMode);
                newInbountOrder.setNote(note);
                newInbountOrder.setTotalQuantity(totalQuantity);
                newInbountOrder.setCreateTime(new Date());
                newInbountOrder.setOrderStatus(InboundOrderStatusEnum.INBOUND_ORDER_WAIT.getStatus());
                newInbountOrder.setSyncToWmsFlag(SyncWmsEnum.SYNC_WMS_FAIL.getStatus());   //是否同步到wms 1:同步失败 2:同步中 3:同步完成
                TraceLog traceLog = new TraceLog();
                Date date = new Date();
                traceLog.setOpTime(date.getTime());
                traceLog.setContent("创建入库单");
                traceLog.setOpName(rpcHeader.getUsername());
                traceLog.setOpUid(String.valueOf(rpcHeader.getTraceId()));
                newInbountOrder.setTracelog(TraceLogUtil.appendTraceLog(newInbountOrder.getTracelog(), traceLog));
                newInbountOrder.setCreateTime(new Date());

                List<InboundOrderItem> inboundOrderItemList = new ArrayList<>();
                if (itemList.size() > 0) {                                         //遍历入库单里面商品详情,组装入库商品的详情记录
                    for (PlanInboundOrderItem planInboundOrderItem : itemList) {
                        InboundOrderItem inboundOrderItem = new InboundOrderItem();   //入库商品详情记录
                        inboundOrderItem.setInventoryType(planInboundOrderItem.getInventoryType());
                        inboundOrderItem.setProjectId(projectId);
                        inboundOrderItem.setPurchaseOrderNo(planInboundOrderItem.getPurchaseOrderNo());
                        inboundOrderItem.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);
                        inboundOrderItem.setBatchNo(batchNo);    //批次号
                        String inboundOrderItemNo = String.valueOf(CommonUtil.getOderNumber());
                        inboundOrderItem.setInboundOrderItemNo(inboundOrderItemNo);
                        inboundOrderItem.setWarehouseId(planInboundOrderItem.getWarehouseId());
                        inboundOrderItem.setWarehouseName(planInboundOrderItem.getWarehouseName());
                        inboundOrderItem.setProductId(planInboundOrderItem.getProductId());
                        inboundOrderItem.setProductCode(planInboundOrderItem.getProductCode());
                        inboundOrderItem.setProductName(planInboundOrderItem.getProductName());
                        inboundOrderItem.setProductUnit(planInboundOrderItem.getProductUnit());
                        inboundOrderItem.setTotalQuantity(planInboundOrderItem.getTotalQuantity());
                        inboundOrderItem.setCreateTime(new Date());
                        inboundOrderItemList.add(inboundOrderItem);
                    }
                    inBoundOrderDao.insertInStorageInfo(newInbountOrder, prefix);     //将入库单信息记录到数据库
                    inboundOrderItemDao.inserInboundOrderItemInfo(inboundOrderItemList, prefix);  //将入库单对应的明细记录到明细表
                }

                try {
                    //通知wms
                    NotificationWmsInboundTask inboundTask = new NotificationWmsInboundTask(rpcHeader, orderSourceNo, newInbountOrder, inboundOrderItemList, inBoundOrderDao, wmsNotificationInboundService, warehouseConfig);
                    threadPoolTaskExecutor.submit(inboundTask);

                } catch (Exception e) {
                    gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                    gongxiaoResult.setData(gongxiaoInboundOrderNo);
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                    return gongxiaoResult;
                }

                logger.info("#traceId={}# [OUT] get createInboundOrder2 success: gongxiaoInboundOrderNo={}", rpcHeader.getTraceId(), gongxiaoInboundOrderNo);
                gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                gongxiaoResult.setData(gongxiaoInboundOrderNo);

            } catch (Exception e) {
                gongxiaoResult.setReturnCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            }
        } else {
            gongxiaoResult.setReturnCode(ErrorCode.DUPLICATED_REQUEST.getErrorCode());
        }
        return gongxiaoResult;
    }

    @Override
    public int cancelInboundOrder(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String warehouseId, String gongxiaoInboundOrderNo, String signature) {
        logger.info("#traceId={}# [IN][cancelInboundOrder] params: projectId={},warehouseId={},gongxiaoInboundOrderNo={},signature={}", rpcHeader.getTraceId(), projectId, warehouseId, gongxiaoInboundOrderNo, signature);
        try {
            WmsCancelOrderRequest wmsCancelOrderRequest = new WmsCancelOrderRequest();
            //调用基础模块的仓库grpc服务查仓库编码
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(warehouseId).build();
            WarehouseStructure.GetWarehouseByIdResp rpcResponse = warehouseService.getWarehouseById(getWarehouseByIdReq);
            WarehouseStructure.Warehouse warehouse = rpcResponse.getWarehouse();

            wmsCancelOrderRequest.setCkid(Integer.parseInt(warehouse.getWmsWarehouseCode()));                       //仓库id
            wmsCancelOrderRequest.setOrderNo(gongxiaoInboundOrderNo);                           //订单号
            wmsCancelOrderRequest.setOrderType(String.valueOf(WmsOrderType.INBOUND_ORDER.getInboundOrderCode()));     //订单类型

            //调用基础模块的SourceChannel服务
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder().setRpcHeader(rpcHeader).setXpsChannelId("1").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();

            wmsCancelOrderRequest.setOwnerCode(sourceChannel.getWmsCustCode());
            wmsCancelOrderRequest.setWarehouseCode(warehouse.getWarehouseCode());                              //仓库编码
            NotificationWmsCancelEntryTask cancelEntryTask = new NotificationWmsCancelEntryTask(rpcHeader, projectId, wmsCancelOrderService, wmsCancelOrderRequest, inBoundOrderDao, inboundOrderItemDao, warehouseConfig);
            threadPoolTaskExecutor.submit(cancelEntryTask);
            logger.info("#traceId={}# [OUT] get cancelInboundOrder success", rpcHeader.getTraceId());
            return 0;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int closeInboundOrder(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String warehouseId, String gongxiaoInboundOrderNo, String signature) {
        logger.info("#traceId={}# [IN][closeInboundOrder] params: projectId={},warehouseId={},gongxiaoInboundOrderNo={},signature={}", rpcHeader.getTraceId(), projectId, warehouseId, gongxiaoInboundOrderNo, signature);
        try {
            WmsCloseOrderRequest wmsCloseOrderRequest = new WmsCloseOrderRequest();
            //调用基础模块的项目的grpc查询项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
            ProjectStructure.GetByProjectIdResp projectIdResp = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = projectIdResp.getProject();
            String prefix = project.getProjectTablePrefix();
            //调用基础模块的仓库grpc服务查仓库编码
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(warehouseId).build();
            WarehouseStructure.GetWarehouseByIdResp rpcResponse = warehouseService.getWarehouseById(getWarehouseByIdReq);
            WarehouseStructure.Warehouse warehouse = rpcResponse.getWarehouse();
            wmsCloseOrderRequest.setCkid(Integer.parseInt(warehouse.getWmsWarehouseCode()));
            wmsCloseOrderRequest.setOrderNo(gongxiaoInboundOrderNo);
            wmsCloseOrderRequest.setOrderType(String.valueOf(WmsOrderType.INBOUND_ORDER.getInboundOrderCode()));
            //调用基础模块的SourceChannel服务
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder().setRpcHeader(rpcHeader).setXpsChannelId("1").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();                                //货主
            wmsCloseOrderRequest.setOwnerCode(sourceChannel.getWmsCustCode());

            wmsCloseOrderRequest.setWarehouseCode(warehouse.getWmsWarehouseCode());           //仓库编码
            List<InboundOrderItem> inboundOrderItemList = inboundOrderItemDao.selectInboundOrderItemByNo(gongxiaoInboundOrderNo, prefix);
            List<OrderItemDto> itemDtoList = new ArrayList<>();
            for (InboundOrderItem record : inboundOrderItemList) {
                OrderItemDto orderItemDto = new OrderItemDto();
                orderItemDto.setItemCode(record.getProductCode());
                orderItemDto.setItemQuantity(record.getTotalQuantity());
            }
            wmsCloseOrderRequest.setItems(itemDtoList);

            NotificationWmsCloseEntryTask closeEntryTask = new NotificationWmsCloseEntryTask(rpcHeader, projectId, wmsCloseOrderService, wmsCloseOrderRequest, inBoundOrderDao, inboundOrderItemDao, warehouseConfig);
            threadPoolTaskExecutor.submit(closeEntryTask);
            logger.info("#traceId={}# [OUT] get closeInboundOrder success", rpcHeader.getTraceId());
            return 0;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<InboundOrderItem> getInboundItemRecord(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String purchaseOrderNo) {
        logger.info("#traceId={}# [IN][getInboundItemRecord] params: projectId={},purchaseOrderNo={}", rpcHeader.getTraceId(), projectId, purchaseOrderNo);
        try {
            //调用基础模块的项目的grpc查询项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
            ProjectStructure.GetByProjectIdResp projectIdResp = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = projectIdResp.getProject();
            String prefix = project.getProjectTablePrefix();
            List<InboundOrderItem> resultList = inboundOrderItemDao.selectByPurchaseNo(projectId, purchaseOrderNo, prefix);
            logger.info("#traceId={}# [OUT] get getInboundItemRecord success: resultList.size={}", rpcHeader.getTraceId(), resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


}
