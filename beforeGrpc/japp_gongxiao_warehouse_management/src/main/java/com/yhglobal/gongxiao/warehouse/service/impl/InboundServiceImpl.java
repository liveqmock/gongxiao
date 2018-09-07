package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.foundation.channel.Channel;
import com.yhglobal.gongxiao.foundation.channel.ChannelDao;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ProductInventoryFlowDao;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.customer.service.InstockService;
import com.yhglobal.gongxiao.warehouse.service.InboundService;
import com.yhglobal.gongxiao.warehouse.service.WmsCancelOrderService;
import com.yhglobal.gongxiao.warehouse.service.WmsCloseOrderService;
import com.yhglobal.gongxiao.warehouse.task.NotificationWmsCancelEntryTask;
import com.yhglobal.gongxiao.warehouse.task.NotificationWmsCloseEntryTask;
import com.yhglobal.gongxiao.warehouse.task.NotificationWmsInboundTask;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehouse.type.WmsSourceChannel;
import com.yhglobal.gongxiao.warehousemanagement.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.InboundOrderItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.PlanInboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.cancel.WmsCancelOrderRequest;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.close.OrderItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.close.WmsCloseOrderRequest;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.InboundOrderStatusEnum;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.SyncWmsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 预约出库是实现类
 *
 * @author : liukai
 */
@Service(timeout = 5000)
public class InboundServiceImpl implements InboundService {

    private Logger logger = LoggerFactory.getLogger(InboundServiceImpl.class);

    @Autowired
    InBoundOrderDao inBoundOrderDao;

    @Autowired
    InboundOrderItemDao inboundOrderItemDao;

    @Autowired
    ProductInventoryFlowDao productInventoryFlowDao;

    @Autowired
    WmsCancelOrderService wmsCancelOrderService;

    @Autowired
    WmsCloseOrderService wmsCloseOrderService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    InstockService instockService;

    @Autowired
    ChannelDao channelDao;

    @Reference
    ProjectService projectService;

    @Reference
    ProductService productService;

    @Reference
    WarehouseService warehouseService;

    @Autowired
    WarehouseConfig warehouseConfig;


    @Override
    public GongxiaoResult createInboundOrder(RpcHeader rpcHeader, Date expectArrivalTime, int purchaseType, String orderSourceNo, String sourceChannelId, int inboundType, String projectId, String traceNo, String senderName, String senderPhoneNo, String warehouseId, String warehouseName, String deliverAddress, int shippingMode, String logisticsCompanyName, String logisticsNo, String note, int totalQuantity, List<PlanInboundOrderItem> itemList, String signature, String uniqueNo) {
        logger.info("#traceId={}# [IN][createInboundOrder] params:  expectArrivalTime={},purchaseType={},orderSourceNo={},sourceChannelId={},projectId={},traceNo={},senderName={},senderPhoneNo={},warehouseId={},warehouseName={},deliverAddress={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={},uniqueNo={}", rpcHeader.traceId,
                expectArrivalTime, purchaseType, orderSourceNo, sourceChannelId, projectId, traceNo, senderName, senderPhoneNo, warehouseId, warehouseName, deliverAddress, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature,uniqueNo);
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        int count = inBoundOrderDao.judgeExit(uniqueNo);
        if (count <= 0) {
            try {
                InboundOrder newInbountOrder = new InboundOrder();                                      //入库单记录
                newInbountOrder.setUniqueNo(uniqueNo);
                Project project = projectService.getByProjectId(rpcHeader,projectId);
                newInbountOrder.setSupplierName(project.getSupplierName());                            //品牌商
                newInbountOrder.setPurchaseType(purchaseType);                                         //采购类型
                newInbountOrder.setInboundType(inboundType);                                              //入库类型 -----wms必传
                String gongxiaoInboundOrderNo = DateTimeIdGenerator.nextId(BizNumberType.STOCK_POIN_ORDER_NO);
                newInbountOrder.setProjectId(Long.valueOf(projectId));                                      //项目id
                newInbountOrder.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);                          //入库单号
                String batchNo = DateTimeIdGenerator.nextId(BizNumberType.STOCK_BATCH_NO);
                newInbountOrder.setBatchNo(batchNo);
                newInbountOrder.setSourceChannel(Integer.parseInt(sourceChannelId));                        //发起入库通知单的渠道
                if (inboundType == WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode()) {
                    newInbountOrder.setPurchaseOrderNo(orderSourceNo);                                             //采购单号
                } else if (inboundType == WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode()) {
                    newInbountOrder.setSalesOrderNo(orderSourceNo);                                                //销售单号
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
                newInbountOrder.setConnectedProduct(JSON.toJSONString(connectedProduct));                            //入库单关联的商品编码
                newInbountOrder.setConnectedGood(JSON.toJSONString(connectedGood));                                  //入库单关联的产品编码
                newInbountOrder.setWarehouseId(warehouseId);
                newInbountOrder.setWarehouseName(warehouseName);
                newInbountOrder.setShippingMode(shippingMode);
                newInbountOrder.setNote(note);
                newInbountOrder.setTotalQuantity(totalQuantity);               //预收入库总数
                newInbountOrder.setExpectedArrivalTime(expectArrivalTime);    //预计到货时间
                newInbountOrder.setCreateTime(new Date());
                newInbountOrder.setOrderStatus(InboundOrderStatusEnum.INBOUND_ORDER_WAIT.getStatus());
                newInbountOrder.setSyncToWmsFlag(SyncWmsEnum.SYNC_WMS_FAIL.getStatus());   //是否同步到wms
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
                        InboundOrderItem inboundOrderItem = new InboundOrderItem();                     //入库商品详情记录
                        inboundOrderItem.setInventoryType(planInboundOrderItem.getInventoryType());     //入库类型
                        inboundOrderItem.setProjectId(projectId);                                       //项目id
                        inboundOrderItem.setPurchaseOrderNo(planInboundOrderItem.getPurchaseOrderNo()); //采购单号
                        inboundOrderItem.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);             //入库单号
                        inboundOrderItem.setBatchNo(batchNo);                                           //批次号
                        String inboundOrderItemNo = String.valueOf(CommonUtil.getOderNumber());
                        inboundOrderItem.setInboundOrderItemNo(inboundOrderItemNo);                     //商品入库单号
                        inboundOrderItem.setWarehouseId(planInboundOrderItem.getWarehouseId());         //仓库id
                        inboundOrderItem.setWarehouseName(planInboundOrderItem.getWarehouseName());     //仓库名称
                        inboundOrderItem.setProductId(planInboundOrderItem.getProductId());             //商品id
                        inboundOrderItem.setProductCode(planInboundOrderItem.getProductCode());         //商品编码
                        inboundOrderItem.setProductName(planInboundOrderItem.getProductName());         //商品名称
                        inboundOrderItem.setProductUnit(planInboundOrderItem.getProductUnit());         //单位
                        inboundOrderItem.setTotalQuantity(planInboundOrderItem.getTotalQuantity());     //预约入库数量
                        inboundOrderItem.setCreateTime(new Date());                                     //入库时间
                        inboundOrderItemList.add(inboundOrderItem);
                    }
                    inBoundOrderDao.insertInStorageInfo(newInbountOrder);                               //将入库单信息记录到数据库
                    inboundOrderItemDao.inserInboundOrderItemInfo(inboundOrderItemList);                //将入库单对应的明细记录到明细表
                }

                //通知Wms入库
                try {
                    NotificationWmsInboundTask notificationWmsInboundTask = new NotificationWmsInboundTask(rpcHeader,ApplicationContextProvider.getApplicationContext(), orderSourceNo, newInbountOrder, inboundOrderItemList,inBoundOrderDao,warehouseService,productService,projectService,warehouseConfig);
                    threadPoolTaskExecutor.submit(notificationWmsInboundTask);
                    instockService.createInstockRecor(orderSourceNo, newInbountOrder, inboundOrderItemList);
                } catch (Exception e) {
                    gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                    gongxiaoResult.setData(gongxiaoInboundOrderNo);
                    logger.error("#traceId={}# [OUT] get createInboundOrder success: gongxiaoInboundOrderNo={}", rpcHeader.traceId, gongxiaoInboundOrderNo);
                    return gongxiaoResult;
                }
                logger.info("#traceId={}# [OUT] get createInboundOrder success: gongxiaoInboundOrderNo={}", rpcHeader.traceId, gongxiaoInboundOrderNo);
                gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                gongxiaoResult.setData(gongxiaoInboundOrderNo);

            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
        } else {
            gongxiaoResult.setReturnCode(ErrorCode.DUPLICATED_REQUEST.getErrorCode());
        }
        return gongxiaoResult;
    }

    @Override
    public GongxiaoResult createInboundOrder2(RpcHeader rpcHeader,
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
        logger.info("#traceId={}# [IN][createInboundOrder] params: orderSourceNo={},sourceChannelId={},projectId={},traceNo={},senderName={},senderPhoneNo={},warehouseId={},warehouseName={},phoneNum={},deliverAddress={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={}", rpcHeader.traceId, orderSourceNo, sourceChannelId, projectId, traceNo, senderName, senderPhoneNo, warehouseId, warehouseName, deliverAddress, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature);
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        int count = inBoundOrderDao.judgeExit(uniqueNo);
        if (count <= 0) {
            try {
                InboundOrder newInbountOrder = new InboundOrder();                                      //入库单记录
                newInbountOrder.setUniqueNo(uniqueNo);
                Project project = projectService.getByProjectId(rpcHeader,projectId);
                newInbountOrder.setSupplierName(project.getSupplierName());                              //品牌商
                newInbountOrder.setInboundType(inboundType);                                              //入库类型 -----wms必传
                String gongxiaoInboundOrderNo = DateTimeIdGenerator.nextId(BizNumberType.STOCK_SOIN_ORDER_NO);
                newInbountOrder.setProjectId(Long.valueOf(projectId));                                      //项目id
                newInbountOrder.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);                          //入库单号
                String batchNo = DateTimeIdGenerator.nextId(BizNumberType.STOCK_BATCH_NO);
                newInbountOrder.setBatchNo(batchNo);
                newInbountOrder.setSourceChannel(Integer.parseInt(sourceChannelId));                        //发起入库通知单的渠道
                if (inboundType == WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode()) {
                    newInbountOrder.setPurchaseOrderNo(orderSourceNo);                                             //采购单号
                } else if (inboundType == WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode()) {
                    newInbountOrder.setSalesOrderNo(orderSourceNo);                                                //销售单号
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
                newInbountOrder.setConnectedProduct(JSON.toJSONString(connectedProduct));    //入库单关联的商品编码
                newInbountOrder.setConnectedGood(JSON.toJSONString(connectedGood));          //入库单关联的产品编码
                newInbountOrder.setWarehouseId(warehouseId);
                newInbountOrder.setWarehouseName(warehouseName);
                newInbountOrder.setShippingMode(shippingMode);
                newInbountOrder.setNote(note);
                newInbountOrder.setTotalQuantity(totalQuantity);
                newInbountOrder.setCreateTime(new Date());
                newInbountOrder.setOrderStatus(InboundOrderStatusEnum.INBOUND_ORDER_WAIT.getStatus());
                newInbountOrder.setSyncToWmsFlag(SyncWmsEnum.SYNC_WMS_FAIL.getStatus());       //是否同步到wms 1:同步失败 2:同步中 3:同步完成
                TraceLog traceLog = new TraceLog();
                Date date = new Date();
                traceLog.setOpTime(date.getTime());
                traceLog.setContent("创建入库单");
                traceLog.setOpName(rpcHeader.getUsername());
                traceLog.setOpUid(String.valueOf(rpcHeader.getTraceId()));
                newInbountOrder.setTracelog(TraceLogUtil.appendTraceLog(newInbountOrder.getTracelog(), traceLog));
                newInbountOrder.setCreateTime(new Date());

                List<InboundOrderItem> inboundOrderItemList = new ArrayList<>();
                if (itemList.size() > 0) {    //遍历入库单里面商品详情,组装入库商品的详情记录
                    for (PlanInboundOrderItem planInboundOrderItem : itemList) {
                        InboundOrderItem inboundOrderItem = new InboundOrderItem();       //入库商品详情记录
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
                        inboundOrderItem.setCreateTime(new Date());
                        inboundOrderItemList.add(inboundOrderItem);
                    }
                    inBoundOrderDao.insertInStorageInfo(newInbountOrder);                               //将入库单信息记录到数据库
                    inboundOrderItemDao.inserInboundOrderItemInfo(inboundOrderItemList);                //将入库单对应的明细记录到明细表
                }

                try {
                    //通知wms
                    NotificationWmsInboundTask notificationWmsInboundTask = new NotificationWmsInboundTask(rpcHeader,ApplicationContextProvider.getApplicationContext(), orderSourceNo, newInbountOrder, inboundOrderItemList,inBoundOrderDao,warehouseService,productService,projectService,warehouseConfig);
                    threadPoolTaskExecutor.submit(notificationWmsInboundTask);
                    instockService.createInstockRecor(orderSourceNo, newInbountOrder, inboundOrderItemList);

                }catch (Exception e){
                    gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                    gongxiaoResult.setData(gongxiaoInboundOrderNo);
                    logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                    return gongxiaoResult;
                }

                logger.info("#traceId={}# [OUT] get createInboundOrder2 success: gongxiaoInboundOrderNo={}", rpcHeader.traceId, gongxiaoInboundOrderNo);
                gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                gongxiaoResult.setData(gongxiaoInboundOrderNo);

            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
        } else {
            gongxiaoResult.setReturnCode(ErrorCode.DUPLICATED_REQUEST.getErrorCode());
        }
        return gongxiaoResult;
    }

    @Override
    public int cancelInboundOrder(RpcHeader rpcHeader, String projectId, String warehouseId, String gongxiaoInboundOrderNo, String signature) {
        logger.info("#traceId={}# [IN][cancelInboundOrder] params: projectId={},warehouseId={},gongxiaoInboundOrderNo={},signature={}", rpcHeader.traceId, projectId, warehouseId, gongxiaoInboundOrderNo, signature);
        try {
            WmsCancelOrderRequest wmsCancelOrderRequest = new WmsCancelOrderRequest();
            wmsCancelOrderRequest.setCkid(Integer.parseInt(warehouseId));                       //仓库id
            wmsCancelOrderRequest.setOrderNo(gongxiaoInboundOrderNo);                           //订单号
            wmsCancelOrderRequest.setOrderType(String.valueOf(WmsOrderType.INBOUND_ORDER.getInboundOrderCode()));     //订单类型
            Channel channel = channelDao.selectByChannelId(Integer.parseInt(WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId()));                                     //货主
            wmsCancelOrderRequest.setOwnerCode(channel.getCustCode());
            Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader,warehouseId);
            wmsCancelOrderRequest.setWarehouseCode(warehouse.getWmsWarehouseCode());                              //仓库编码
            NotificationWmsCancelEntryTask task = new NotificationWmsCancelEntryTask(rpcHeader,projectId,wmsCancelOrderService,wmsCancelOrderRequest,inBoundOrderDao,inboundOrderItemDao,warehouseConfig);
            threadPoolTaskExecutor.submit(task);
            logger.info("#traceId={}# [OUT] get cancelInboundOrder success", rpcHeader.traceId);
            return 0;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int closeInboundOrder(RpcHeader rpcHeader, String projectId, String warehouseId, String gongxiaoInboundOrderNo, String signature) {
        logger.info("#traceId={}# [IN][closeInboundOrder] params: projectId={},warehouseId={},gongxiaoInboundOrderNo={},signature={}", rpcHeader.traceId, projectId, warehouseId, gongxiaoInboundOrderNo, signature);
        try {
            WmsCloseOrderRequest wmsCloseOrderRequest = new WmsCloseOrderRequest();
            wmsCloseOrderRequest.setCkid(Integer.parseInt(warehouseId));                       //仓库id
            wmsCloseOrderRequest.setOrderNo(gongxiaoInboundOrderNo);                           //订单号
            wmsCloseOrderRequest.setOrderType(String.valueOf(WmsOrderType.INBOUND_ORDER.getInboundOrderCode()));     //订单类型
            Channel channel = channelDao.selectByChannelId(Integer.parseInt(WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId()));                                     //货主
            wmsCloseOrderRequest.setOwnerCode(channel.getCustCode());
            Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader,warehouseId);
            wmsCloseOrderRequest.setWarehouseCode(warehouse.getWmsWarehouseCode());                              //仓库编码
            List<InboundOrderItem> inboundOrderItemList= inboundOrderItemDao.selectInboundOrderItemByNo(gongxiaoInboundOrderNo);
            List<OrderItemDto> itemDtoList = new ArrayList<>();
            for (InboundOrderItem record : inboundOrderItemList){
                OrderItemDto orderItemDto = new OrderItemDto();
                orderItemDto.setItemCode(record.getProductCode());
                orderItemDto.setItemQuantity(record.getTotalQuantity());
            }
            wmsCloseOrderRequest.setItems(itemDtoList);

            NotificationWmsCloseEntryTask task = new NotificationWmsCloseEntryTask(rpcHeader,projectId,wmsCloseOrderService,wmsCloseOrderRequest,inBoundOrderDao,inboundOrderItemDao,warehouseConfig);
            threadPoolTaskExecutor.submit(task);
            logger.info("#traceId={}# [OUT] get closeInboundOrder success", rpcHeader.traceId);
            return 0;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    //更新明细表
    @Override
    public int modifyInboundItermByWms(RpcHeader rpcHeader, String gongxiaoInBoundOrderNo,String productCode,int imperfectQuantity,int inStockQuantity,int inTransitQuantity,int realInStockQuantity) {
        logger.info("#traceId={}# [IN][modifyInboundItermByWms] params: gongxiaoInBoundOrderNo={},productCode={},imperfectQuantity={},inStockQuantity={},inTransitQuantity={},realInStockQuantity={}", rpcHeader.traceId,gongxiaoInBoundOrderNo,productCode,imperfectQuantity,inStockQuantity,inTransitQuantity,realInStockQuantity);
        try {
            InboundOrderItem order = inboundOrderItemDao.selectOrderItemByNo(gongxiaoInBoundOrderNo,productCode);
            int afterImperfectQuantity = order.getImperfectQuantity()+imperfectQuantity;
            int afterInStockQuantity = order.getInStockQuantity()+inStockQuantity;
            int afterInTransitQuantity = order.getInTransitQuantity()+inTransitQuantity;
            int afterRealInStockQuantity = order.getRealInStockQuantity()+realInStockQuantity;
            int i = inboundOrderItemDao.updateInStorageDetailInfo(order.getRowId(),afterImperfectQuantity,afterInStockQuantity,afterInTransitQuantity,afterRealInStockQuantity);
            logger.info("#traceId={}# [OUT] get modifyInboundItermByWms success: inboundOrder={}", rpcHeader.traceId, gongxiaoInBoundOrderNo);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 特别注意：本函数的返回值为 更新后入库单是否已收货完成
     */
    @Override
    public boolean modifyInboundOderByWms(RpcHeader rpcHeader, String gongxiaoInboundOrderNo,int realInStockQuantity,int imperfectQuantity,int inStockQuantity,int inTransitQuantity) {
        logger.info("#traceId={}# [IN][modifyInboundOderByWms] params: gongxiaoInboundOrderNo={},realInStockQuantity={},imperfectQuantity={},inStockQuantity={},inTransitQuantity={}", rpcHeader.traceId, gongxiaoInboundOrderNo,realInStockQuantity,imperfectQuantity,inStockQuantity,inTransitQuantity);
        int maxTryTimes = 3; //当前最大尝试的次数
        boolean updateSuccess = false; //标记是否更新DB成功
        while (!updateSuccess && maxTryTimes > 0) { //若尚未更新db成功 并且剩余重试数大于0
            try {
                InboundOrder order = inBoundOrderDao.getInboundRecordByOrderNo(gongxiaoInboundOrderNo);
                String olderTraceLog = order.getTracelog();
                TraceLog traceLog = new TraceLog();
                Date dateTime = new Date();
                traceLog.setOpTime(dateTime.getTime());
                traceLog.setOpUid(rpcHeader.getUid());
                traceLog.setOpName(rpcHeader.getUsername());
                traceLog.setContent("WMS已经收货");
                int afterRealInStockQuantity = order.getRealInStockQuantity() + realInStockQuantity;
                int afterImperfectQuantity = order.getImperfectQuantity() + imperfectQuantity;
                int afterInStockQuantity = order.getInStockQuantity() + inStockQuantity;
                int afterInTransitQuantity = order.getInTransitQuantity() + inTransitQuantity;
                String tracelog = TraceLogUtil.appendTraceLog(olderTraceLog, traceLog);

                boolean isFinished = order.getTotalQuantity() <= order.getRealInStockQuantity() + realInStockQuantity;
                int orderStatus = isFinished? InboundOrderStatusEnum.INBOUND_ORDER_RECEIVE_FINISH.getStatus() : InboundOrderStatusEnum.INBOUND_ORDER_RECEIVE_PART.getStatus();
                int row = inBoundOrderDao.updateInStorageInfo(order.getRowId(), afterRealInStockQuantity, afterImperfectQuantity, afterInStockQuantity, afterInTransitQuantity, tracelog, orderStatus, order.getDataVersion());
                if (row == 1)    return isFinished; //如更新成功则返回
                logger.info("#traceId={}# fail to modifyInboundOderByWms: gongxiaoInboundOrderNo={}", rpcHeader.traceId, gongxiaoInboundOrderNo);
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
        }
        if (!updateSuccess && maxTryTimes<=0) {
            logger.info("#traceId={}# fail to modifyInboundOderByWms after maxTryTimes: gongxiaoInboundOrderNo={}", rpcHeader.traceId, gongxiaoInboundOrderNo);
        }
        return false;
    }

    @Override
    public List<InboundOrderItem> getInboundItemRecord(RpcHeader rpcHeader, String projectId, String purchaseOrderNo) {
        logger.info("#traceId={}# [IN][getInboundItemRecord] params: projectId={},purchaseOrderNo={}", rpcHeader.traceId, projectId, purchaseOrderNo);
        try {
            List<InboundOrderItem> resultList = inboundOrderItemDao.selectByPurchaseNo(projectId, purchaseOrderNo);
            logger.info("#traceId={}# [OUT] get getInboundItemRecord success: resultList.size={}", rpcHeader.traceId, resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


}
