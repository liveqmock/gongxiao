package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.foundation.channel.Channel;
import com.yhglobal.gongxiao.foundation.channel.ChannelDao;
import com.yhglobal.gongxiao.foundation.distributor.dao.DistributorDao;
import com.yhglobal.gongxiao.foundation.distributor.model.Distributor;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.inventory.service.ProductInventoryService;
import com.yhglobal.gongxiao.sales.service.SalesScheduleDeliveryService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.customer.service.OutstockService;
import com.yhglobal.gongxiao.warehouse.service.OutboundService;
import com.yhglobal.gongxiao.warehouse.service.WmsCancelOrderService;
import com.yhglobal.gongxiao.warehouse.service.WmsCloseOrderService;
import com.yhglobal.gongxiao.warehouse.task.*;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehouse.type.WmsSourceChannel;
import com.yhglobal.gongxiao.warehousemanagement.dao.*;
import com.yhglobal.gongxiao.warehousemanagement.dto.PlanOutboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.cancel.WmsCancelOrderRequest;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.close.OrderItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.close.WmsCloseOrderRequest;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.OutboundOrderStatusEnum;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.SyncEasEnum;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.SyncWmsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 预约发货实现类
 *
 * @author : liukai
 */
@Service(timeout = 5000)
public class OutboundServiceImpl implements OutboundService {

    private Logger logger = LoggerFactory.getLogger(OutboundServiceImpl.class);

    @Autowired
    OutBoundOrderDao outBoundOrderDao;

    @Autowired
    OutBoundOrderItemDao outBoundOrderItemDao;

    @Autowired
    WmsCancelOrderService wmsCancelOrderService;

    @Autowired
    WmsCloseOrderService wmsCloseOrderService;

    @Autowired
    DistributorDao distributorDao;

    @Autowired
    InBoundOrderDao inBoundOrderDao;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    OutstockService outstockService;

    @Autowired
    ChannelDao channelDao;

    @Reference(check = false)
    ProductInventoryService productInventoryService;

    @Reference(check = false)
    ProjectService projectService;

    @Reference(check = false)
    ProductService productService;

    @Reference(check = false)
    WarehouseService warehouseService;

    @Reference(check = false)
    SalesScheduleDeliveryService salesScheduleDeliveryService;

    @Autowired
    WarehouseConfig warehouseConfig;

    @Override
    public String createOutboundOrder(RpcHeader rpcHeader, String sourceChannelId, int outboundType, String projectId, String customerId, String traceNo, String recipientName, String recipientPhoneNumber, String recipientAddress, String warehouseId, String warehouseName, int shippingMode, String logisticsCompanyName, String logisticsNo, String note, int totalQuantity, List<PlanOutboundOrderItem> itemList, String signature, String uniqueNo) {
        logger.info("#traceId={}# [IN][createOutboundOrder]: sourceChannelId={},outboundType={},projectId={},customerId={},traceNo={},recipientName={},recipientPhoneNumber={},recipientAddress={},warehouseId={},warehouseName={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={},uniqueNo={}", rpcHeader.traceId, sourceChannelId, outboundType, projectId, customerId, traceNo, recipientName, recipientPhoneNumber, recipientAddress, warehouseId, warehouseName, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, itemList, signature, uniqueNo);
        try {
            OutboundOrder outboundOrder = new OutboundOrder();
            String gongxiaoOutboundOrderNo = DateTimeIdGenerator.nextId(BizNumberType.STOCK_POOUT_ORDER_NO);
            outboundOrder.setSourceChannel(Integer.parseInt(sourceChannelId));          //发起出库通知单的渠道
            outboundOrder.setProjectId(projectId);
            outboundOrder.setOutboundType(outboundType);                                //订单类型
            outboundOrder.setWarehouseId(warehouseId);                                  //仓库id
            outboundOrder.setWarehouseName(warehouseName);                              //仓库名称
            Distributor distributor = distributorDao.selectRecordById(customerId);
            outboundOrder.setCustomer(distributor.getDistributorName());                //客户
            outboundOrder.setContactsPeople(recipientName);                             //联系人
            outboundOrder.setPhoneNum(recipientPhoneNumber);                            //联系电话
            outboundOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));           //创建人id
            outboundOrder.setCreatedByName(rpcHeader.getUsername());                    //创建人
            Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, String.valueOf(warehouseId));
            outboundOrder.setShippingAddress(warehouse.getStreetAddress());             //发货地址

            outboundOrder.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);          //出库单号
            outboundOrder.setShippingMode(shippingMode);                                //物流方式
            outboundOrder.setTotalQuantity(totalQuantity);                              //数量
            outboundOrder.setNote(note);                                                //备注
            List<String> connectedProduct = new ArrayList<>();
            for (PlanOutboundOrderItem record : itemList) {
                connectedProduct.add(record.getProductCode());
            }
            outboundOrder.setConnectedProduct(JSON.toJSONString(connectedProduct));
            TraceLog traceLog = new TraceLog();
            Date date = new Date();
            traceLog.setOpTime(date.getTime());
            traceLog.setContent("创建出库单");
            traceLog.setOpName(rpcHeader.getUsername());
            traceLog.setOpUid(String.valueOf(rpcHeader.getTraceId()));
            outboundOrder.setTracelog(TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), traceLog));
            outboundOrder.setCreateTime(new Date());
            outboundOrder.setOrderStatus(OutboundOrderStatusEnum.OUTBOUND_ORDER_WAIT.getStatus()); //出库单状态
            outboundOrder.setSyncToWmsFlag(SyncWmsEnum.SYNC_WMS_FAIL.getStatus()); //默认为未同步
            outboundOrder.setDataVersion(0);

            List<OutboundOrderItem> outboundOrderItemList = new ArrayList<>();
            StringBuilder outboundBatchNo = new StringBuilder();
            if (itemList.size() > 0) {
                for (PlanOutboundOrderItem record : itemList) {
                    OutboundOrderItem outboundOrderItem = new OutboundOrderItem();
                    String outboundOrderItemNo = DateTimeIdGenerator.nextId(BizNumberType.STOCK_POOUT_ORDER_NO);
                    outboundBatchNo.append(record.getBatchNo());
                    outboundOrderItem.setBatchNo(record.getBatchNo());                                                 //批次号
                    outboundOrderItem.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);          //出库单号
                    outboundOrderItem.setInventoryType(record.getInventoryType());                  //入库类型
                    outboundOrderItem.setPurchaseType(record.getPurchaseType());
                    outboundOrderItem.setOutboundOrderItemNo(outboundOrderItemNo);                  //出库单明细单号
                    outboundOrderItem.setSalesOrderNo(record.getSalesOrderNo());                    //销售单号
                    outboundOrderItem.setProductId(record.getProductId());                          //商品id
                    outboundOrderItem.setProductCode(record.getProductCode());                      //商品编码
                    outboundOrderItem.setProductName(record.getProductName());                      //商品名称
                    outboundOrderItem.setWarehouseId(record.getWarehouseId());                      //仓库id
                    outboundOrderItem.setWarehouseName(record.getWarehouseName());                  //仓库名称
                    outboundOrderItem.setTotalQuantity(record.getTotalQuantity());                  //数量
                    outboundOrderItem.setCreateTime(new Date());                                    //创建时间
                    outboundOrderItemList.add(outboundOrderItem);
                }
                outboundOrder.setBatchNo(outboundBatchNo.toString());
                outBoundOrderDao.insertOutStorageInfo(outboundOrder);
                outBoundOrderItemDao.insertOutboundOrderItem(outboundOrderItemList);
            }

            //通知wms
            NotificationWmsOutboundTask notificationWmsOutboundTask = new NotificationWmsOutboundTask(rpcHeader, ApplicationContextProvider.getApplicationContext(), outboundOrder, outboundOrderItemList, outBoundOrderDao, warehouseService, projectService, productService, warehouseConfig,salesScheduleDeliveryService);
            threadPoolTaskExecutor.submit(notificationWmsOutboundTask);

            outstockService.createInstockRecor(outboundOrder, outboundOrderItemList);

            logger.info("#traceId={}# [OUT] get createOutboundOrder success: result={}", rpcHeader.traceId, "success");
            return gongxiaoOutboundOrderNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public Map<String, List<OutboundOrderItem>> createOutboundOrder2(RpcHeader rpcHeader, String sourceChannelId, int outboundType, String projectId, String customerId, String traceNo, String recipientName, String recipientPhoneNumber, String recipientAddress, String provinceName, String cityName, int shippingMode, String logisticsCompanyName, String logisticsNo, String note, int totalQuantity, List<PlanOutboundOrderItem> itemList, String signature, String uniqueNo, Date arrivalDate) {
        logger.info("#traceId={}# [IN][createOutboundOrder2]: sourceChannelId={},outboundType={},projectId={},customerId={},traceNo={},recipientName={},recipientPhoneNumber={},recipientAddress={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={},uniqueNo={},arrivalDate={}", rpcHeader.traceId, sourceChannelId, outboundType, projectId, customerId, traceNo, recipientName, recipientPhoneNumber, recipientAddress, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature, uniqueNo, arrivalDate);
        try {
            Map<String, List<OutboundOrderItem>> resultMap = new HashMap<>();   //定义结果Map

            //将预约出库记录进行按照仓库划分
            Map<String, List<PlanOutboundOrderItem>> warehouseOrderMap = new HashMap<>();   //key=warehouseId,value=入库明细单
            for (PlanOutboundOrderItem record : itemList) {
                if (warehouseOrderMap.containsKey(record.getWarehouseId())) {        //如果Map里面已经存在相同仓库的,放到同一个list
                    warehouseOrderMap.get(record.getWarehouseId()).add(record);
                } else {                            //如果Map里面不存在相同仓库的,放到一个新的list里面
                    List<PlanOutboundOrderItem> newStockOrder = new ArrayList<>();
                    newStockOrder.add(record);
                    warehouseOrderMap.put(record.getWarehouseId(), newStockOrder);
                }
            }

            for (Map.Entry<String, List<PlanOutboundOrderItem>> entry : warehouseOrderMap.entrySet()) {
                //封装出库记录
                String gongxiaoOutboundOrderNo = DateTimeIdGenerator.nextId(BizNumberType.STOCK_SOOUT_ORDER_NO);  //出库单号

                OutboundOrder outboundOrder = new OutboundOrder();
                outboundOrder.setProjectId(projectId);
                outboundOrder.setSourceChannel(Integer.parseInt(sourceChannelId));   //发起出库通知单的渠道
                outboundOrder.setOutboundType(outboundType);                         //订单类型
                outboundOrder.setWarehouseId(entry.getKey());
                Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, entry.getKey());
                outboundOrder.setWarehouseName(warehouse.getWarehouseName());
                outboundOrder.setDeliverAddress(warehouse.getStreetAddress());      //发货地址
                Distributor distributor = distributorDao.selectRecordById(customerId);
                outboundOrder.setCustomer(distributor.getDistributorName());     //客户
                outboundOrder.setContactsPeople(recipientName);
                outboundOrder.setPhoneNum(recipientPhoneNumber);
                outboundOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                outboundOrder.setCreatedByName(rpcHeader.getUsername());
                outboundOrder.setShippingAddress(recipientAddress);     //收货地址
                outboundOrder.setProvinceName(provinceName);
                outboundOrder.setCityName(cityName);
                outboundOrder.setExpectedArrivalTime(arrivalDate);
                outboundOrder.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);
                outboundOrder.setSalesOrderNo(traceNo);
                outboundOrder.setOrderStatus(OutboundOrderStatusEnum.OUTBOUND_ORDER_WAIT.getStatus());     //出库单状态
               outboundOrder.setSyncToWmsFlag(SyncEasEnum.SYNC_EAS_FAIL.getStatus());       //同步wms状态
                outboundOrder.setShippingMode(shippingMode);
                outboundOrder.setSourceChannel(Integer.parseInt(sourceChannelId));
                outboundOrder.setNote(note);
                outboundOrder.setDataVersion(0);
                TraceLog traceLog = new TraceLog();
                Date date = new Date();
                traceLog.setOpTime(date.getTime());
                traceLog.setContent("创建出库单");
                traceLog.setOpName(rpcHeader.getUsername());
                traceLog.setOpUid(String.valueOf(rpcHeader.getTraceId()));
                outboundOrder.setTracelog(TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), traceLog));
                outboundOrder.setCreateTime(new Date());

                StringBuilder conectedBatchNo = new StringBuilder();
                StringBuilder connectedProduct = new StringBuilder();
                StringBuilder connectedOutboundOrderItemNo = new StringBuilder();
                int recordTotalQuantity = 0;

                List<OutboundOrderItem> outboundOrderItemList = new ArrayList<>();
                for (PlanOutboundOrderItem record : entry.getValue()) {
                    //封装出库明细记录
                    String outboundOrderItemNo = DateTimeIdGenerator.nextId(BizNumberType.STOCK_SOOUT_ORDER_ITEM_NO);   //出库明细单号
                    conectedBatchNo.append(record.getBatchNo());
                    connectedProduct.append(record.getProductCode());
                    recordTotalQuantity += record.getTotalQuantity();
                    connectedOutboundOrderItemNo.append(outboundOrderItemNo);

                    OutboundOrderItem outboundOrderItem = new OutboundOrderItem();
                    outboundOrderItem.setProjectId(projectId);
                    outboundOrderItem.setBatchNo(record.getBatchNo());                              //批次号
                    outboundOrderItem.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);          //出库单号
                    outboundOrderItem.setPurchaseType(record.getPurchaseType());                    //货补，普通采购，赠品
                    outboundOrderItem.setInventoryType(record.getInventoryType());                  //入库类型
                    outboundOrderItem.setOutboundOrderItemNo(outboundOrderItemNo);                  //出库单明细单号
                    outboundOrderItem.setSalesOrderNo(record.getSalesOrderNo());                    //销售单号
                    outboundOrderItem.setProductCode(record.getProductCode());                      //商品编码
                    outboundOrderItem.setProductName(record.getProductName());                      //商品名称
                    outboundOrderItem.setProductId(record.getProductId());                          //商品id
                    outboundOrderItem.setWarehouseId(record.getWarehouseId());                      //仓库id
                    outboundOrderItem.setWarehouseName(record.getWarehouseName());                  //仓库名称
                    outboundOrderItem.setTotalQuantity(record.getTotalQuantity());                  //数量
                    outboundOrderItem.setCreateTime(new Date());                                    //创建时间
                    outboundOrderItemList.add(outboundOrderItem);

                }

                outboundOrder.setBatchNo(conectedBatchNo.toString());
//                outboundOrder.setInventoryType(record.getInventoryType());                  //库存类型,因为有多种类型，不填
                outboundOrder.setConnectedProduct(connectedProduct.toString());                 //出库商品
                outboundOrder.setOutboundOrderItemNo(connectedOutboundOrderItemNo.toString());   //出库明细单号
                outboundOrder.setTotalQuantity(recordTotalQuantity);                  //数量

                outBoundOrderItemDao.insertOutboundOrderItem(outboundOrderItemList);
                outBoundOrderDao.insertOutStorageInfo(outboundOrder);
                resultMap.put(gongxiaoOutboundOrderNo,outboundOrderItemList);

                try {
                    //修改库存占用数量
                    NotificationInventoryTask notificationInventoryTask = new NotificationInventoryTask(rpcHeader, productInventoryService, projectId, itemList);
                    threadPoolTaskExecutor.submit(notificationInventoryTask);

                    //通知WMS出库
                    NotificationWmsOutboundTask task = new NotificationWmsOutboundTask(rpcHeader, ApplicationContextProvider.getApplicationContext(), outboundOrder, outboundOrderItemList, outBoundOrderDao, warehouseService, projectService, productService, warehouseConfig,salesScheduleDeliveryService);
                    threadPoolTaskExecutor.submit(task);

                } catch (Exception e) {
                    logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                    return resultMap;
                }
            }
            logger.info("#traceId={}# [OUT] get createOutboundOrder2 success,", rpcHeader.traceId, "success");
            return resultMap;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public int cancelOrder(RpcHeader rpcHeader, String projectId, String warehouseId, String gongxiaoOutboundOrderNo, String productCode, String signature) {
        logger.info("#traceId={}# [IN][cancelOutboundOrder] params: projectId={},warehouseId={},gongxiaoInboundOrderNo={},orderType={},ownerCode={},signature={},productCode ={}", rpcHeader.traceId, projectId, warehouseId, gongxiaoOutboundOrderNo, signature, productCode);
        WmsCancelOrderRequest wmsCancelOrderRequest = new WmsCancelOrderRequest();
        wmsCancelOrderRequest.setCkid(Integer.parseInt(warehouseId));
        wmsCancelOrderRequest.setOrderNo(gongxiaoOutboundOrderNo);
        wmsCancelOrderRequest.setOrderType(String.valueOf(WmsOrderType.OUTBOUND_ORDER.getInboundOrderCode()));   //订单类型
        Channel channel = channelDao.selectByChannelId(Integer.parseInt(WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId()));
        wmsCancelOrderRequest.setOwnerCode(channel.getCustCode());                    //货主
        Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, warehouseId);
        wmsCancelOrderRequest.setWarehouseCode(warehouse.getWmsWarehouseCode());                              //仓库编码

        NotificationWmsCancelOutTask task = new NotificationWmsCancelOutTask(rpcHeader, projectId, productCode, wmsCancelOrderService, wmsCancelOrderRequest, outBoundOrderDao, outBoundOrderItemDao, warehouseConfig);  //通知WMS系统取消出库单
        threadPoolTaskExecutor.submit(task);
        logger.info("#traceId={}# [OUT] get cancelOutboundOrder success", rpcHeader.traceId);
        return 0;
    }

    @Override
    public int closeOutboundOrder(RpcHeader rpcHeader, String projectId, String warehouseId, String gongxiaoOutboundOrderNo, String productCode, String signature) {
        logger.info("#traceId={}# [IN][closeOutboundOrder] params: projectId={},warehouseId={},gongxiaoInboundOrderNo={},orderType={},ownerCode={},signature={}", rpcHeader.traceId, projectId, warehouseId, gongxiaoOutboundOrderNo, signature);
        WmsCloseOrderRequest wmsCloseOrderRequest = new WmsCloseOrderRequest();
        wmsCloseOrderRequest.setCkid(Integer.parseInt(warehouseId));
        wmsCloseOrderRequest.setOrderNo(gongxiaoOutboundOrderNo);
        wmsCloseOrderRequest.setOrderType(String.valueOf(WmsOrderType.OUTBOUND_ORDER.getInboundOrderCode()));   //订单类型
        Channel channel = channelDao.selectByChannelId(Integer.parseInt(WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId()));
        wmsCloseOrderRequest.setOwnerCode(channel.getCustCode());                    //货主
        Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, warehouseId);
        wmsCloseOrderRequest.setWarehouseCode(warehouse.getWmsWarehouseCode());                              //仓库编码
        List<OutboundOrderItem> outboundOrderItemList = outBoundOrderItemDao.selectOutboundOrderItemByNo(gongxiaoOutboundOrderNo);
        List<OrderItemDto> itemDtoList = new ArrayList<>();
        for (OutboundOrderItem record : outboundOrderItemList) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setItemCode(record.getProductCode());
            orderItemDto.setItemQuantity(record.getTotalQuantity());
        }
        wmsCloseOrderRequest.setItems(itemDtoList);

        NotificationWmsCloseOutTask task = new NotificationWmsCloseOutTask(rpcHeader, projectId, productCode, wmsCloseOrderService, wmsCloseOrderRequest, warehouseConfig, outBoundOrderDao, outBoundOrderItemDao);  //通知WMS系统取消出库单
        threadPoolTaskExecutor.submit(task);
        logger.info("#traceId={}# [OUT] get closeOutboundOrder success", rpcHeader.traceId);
        return 0;
    }

    @Transactional
    @Override
    public int adjustOutboundQuantity(RpcHeader rpcHeader, String projectId, String gongxiaoOutboundOrderNo, String productCode, int originalQuantity, int adjustmentQuantity, int targetQuantity, String signature) {
        logger.info("#traceId={}# [IN][adjustOutboundQuantity] params: projectId={},gongxiaoOutboundOrderNo={},productCode={},originalQuantity={},adjustmentQuantity={},targetQuantity={},signature={}", rpcHeader.traceId, projectId, gongxiaoOutboundOrderNo, productCode, originalQuantity, adjustmentQuantity, targetQuantity, signature);
        try {
            outBoundOrderDao.updateByProjectAndOutboundNo(projectId, gongxiaoOutboundOrderNo, adjustmentQuantity);
            outBoundOrderItemDao.updateByCondition(projectId, gongxiaoOutboundOrderNo, productCode, adjustmentQuantity);
            logger.info("#traceId={}# [OUT] get adjustOutboundQuantity success: result={}", rpcHeader.traceId, "success");
            return 0;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public List<OutboundOrder> getOutboundRecord(RpcHeader rpcHeader, String projectId, String gongxiaoOutboundOrder, String signature) {
        logger.info("#traceId={}# [IN][getOutboundRecord] params: projectId={},gongxiaoOutboundOrderNo={},signature={}", rpcHeader.traceId, projectId, gongxiaoOutboundOrder, signature);
        try {
            List<OutboundOrder> resultList = outBoundOrderDao.getOutboundRecord(projectId, gongxiaoOutboundOrder, signature);
            logger.info("#traceId={}# [OUT] get getOutboundRecord success: resultList.size={}", rpcHeader.traceId, resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

//    @Override
//    public int modifyAccordingWms(RpcHeader rpcHeader, OutboundOrderItem outboundOrderItem) {
//        logger.info("#traceId={}# [IN][modifyAccordingWms] params: outboundOrderItem={}", rpcHeader.traceId, JSON.toJSONString(outboundOrderItem));
//        try {
//            List<OutboundOrderItem> orderItem = outBoundOrderItemDao.selectItemByNoAndProductCode(outboundOrderItem.getGongxiaoOutboundOrderNo(), outboundOrderItem.getProductCode(), outboundOrderItem.getInventoryType());
//            outboundOrderItem.setRealOutStockQuantity(orderItem.getRealOutStockQuantity() + outboundOrderItem.getRealOutStockQuantity());
//            int i = outBoundOrderItemDao.updateOutboundOrderItemInfo(outboundOrderItem);
//            logger.info("#traceId={}# [OUT] get modifyAccordingWms success: resultList.size={}", rpcHeader.traceId, i);
//            return i;
//        } catch (Exception e) {
//            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    @Override
//    public int modifyAOutboundInfoAccordingWms(RpcHeader rpcHeader, OutboundOrder outboundOrder) {
//        logger.info("#traceId={}# [IN][modifyAOutboundInfoAccordingWms] params: outboundOrder={}", rpcHeader.traceId, JSON.toJSONString(outboundOrder));
//        try {
//            OutboundOrder order = outBoundOrderDao.getOutboundRecordByOrderNo(outboundOrder.getGongxiaoOutboundOrderNo(), outboundOrder.getConnectedProduct());
//            String olderTraceLog = order.getTracelog();
//            TraceLog traceLog = new TraceLog();
//            Date dateTime = new Date();
//            traceLog.setOpTime(dateTime.getTime());
//            traceLog.setOpUid(rpcHeader.getUid());
//            traceLog.setOpName(rpcHeader.getUsername());
//            traceLog.setContent("WMS已经发货");
//            outboundOrder.setOutStockQuantity(order.getOutStockQuantity() + outboundOrder.getOutStockQuantity());
//            outboundOrder.setImperfectQuantity(order.getImperfectQuantity() + outboundOrder.getImperfectQuantity());
//            outboundOrder.setTransferQuantity(order.getTransferQuantity() + outboundOrder.getTransferQuantity());
//            outboundOrder.setCanceledQuantity(order.getCanceledQuantity() + outboundOrder.getCanceledQuantity());
//            outboundOrder.setRealOutStockQuantity(order.getRealOutStockQuantity() + outboundOrder.getRealOutStockQuantity());
//            outboundOrder.setTracelog(TraceLogUtil.appendTraceLog(olderTraceLog, traceLog));
//            int i = outBoundOrderDao.updateOutboundOrderByNo(outboundOrder);
//            logger.info("#traceId={}# [OUT] get modifyAOutboundInfoAccordingWms success: resultList.size={}", rpcHeader.traceId, i);
//            return i;
//        } catch (Exception e) {
//            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
//            throw e;
//        }
//    }

    @Override
    public void sureSighIn(RpcHeader rpcHeader, String transporter, String custOrdNo, String tmsOrdNo, String remark, String signedBy, String postedBy, String signedPhone, Date signedTime) {
        logger.info("#traceId={}# [IN][sureSighIn]: transporter={},custOrdNo={},tmsOrdNo={},remark={},signedBy={},postedBy={},signedPhone={},signedTime={}", rpcHeader.getTraceId(), transporter, custOrdNo, tmsOrdNo, remark, signedBy, postedBy, signedPhone, signedTime);
        try {
            OutboundOrder outboundOrder = outBoundOrderDao.getOutStorageInfoByNo(custOrdNo);    //根据单号查到对应的出库单
            if (outboundOrder != null) {         //出库单存在
                outboundOrder.setTransporter(transporter);
                outboundOrder.setTmsOrdNo(tmsOrdNo);
                outboundOrder.setNote(remark);
                outboundOrder.setTmsSignedBy(signedBy);
                outboundOrder.setTmsSignedPhone(signedPhone);
                outboundOrder.setTmsSingedTime(signedTime);
                if (outboundOrder.getOutboundType() == WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode()) {  //如果是销售出库

                    outBoundOrderDao.sureSighIn(outboundOrder);
                    outBoundOrderItemDao.sureSighIn(outboundOrder.getGongxiaoOutboundOrderNo());
                    logger.info("#traceId={}# [OUT] get sureSighIn success,签收成功", rpcHeader.getTraceId());
                } else {       //如果是其他出库单
                    outBoundOrderDao.sureSighIn(outboundOrder);
                    outBoundOrderItemDao.sureSighIn(outboundOrder.getGongxiaoOutboundOrderNo());
                    logger.info("#traceId={}# [OUT] get sureSighIn success,签收成功", rpcHeader.getTraceId());
                }

            } else {   //出库单不存在
                logger.info("#traceId={}# [OUT] get sureSighIn success,签收失败,订单不存在", rpcHeader.getTraceId());
            }

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }
    }

}
