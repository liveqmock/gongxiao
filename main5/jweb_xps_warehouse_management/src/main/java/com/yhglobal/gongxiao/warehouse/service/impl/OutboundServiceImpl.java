package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.inventory.account.microservice.ProductInventoryStructure;
import com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.*;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderItemDao;
import com.yhglobal.gongxiao.warehouse.dao.WmsOutboundDao;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrder;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.PlanOutboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.cancel.WmsCancelOrderRequest;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.close.OrderItemDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.close.WmsCloseOrderRequest;
import com.yhglobal.gongxiao.warehouse.service.OutboundService;
import com.yhglobal.gongxiao.warehouse.service.WmsCancelOrderService;
import com.yhglobal.gongxiao.warehouse.service.WmsCloseOrderService;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationOutboundService;
import com.yhglobal.gongxiao.warehouse.task.*;
import io.netty.util.internal.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 预约发货实现类
 *
 * @author : liukai
 */
@Service
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
    InBoundOrderDao inBoundOrderDao;

    @Autowired
    WmsOutboundDao wmsOutboundDao;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    WarehouseConfig warehouseConfig;

    @Autowired
    WmsNotificationOutboundService wmsNotificationOutboundService;

    private static final int WMS_ORDERNO_LENGTH_LIMIT = 32;

    @Override
    public String createOutboundOrder(GongxiaoRpc.RpcHeader rpcHeader, String sourceChannelId, int outboundType, String projectId, String customerId, String traceNo, String recipientName, String recipientPhoneNumber, String recipientAddress, String warehouseId, String warehouseName, int shippingMode,
                                      String logisticsCompanyName, String logisticsNo, String note, int totalQuantity, List<PlanOutboundOrderItem> itemList, String signature, String uniqueNo, long userId, String userName) {
        logger.info("#traceId={}# [IN][createOutboundOrder]: sourceChannelId={},outboundType={},projectId={},customerId={},traceNo={},recipientName={},recipientPhoneNumber={},recipientAddress={},warehouseId={},warehouseName={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={},uniqueNo={}",
                rpcHeader.getTraceId(), sourceChannelId, outboundType, projectId, customerId, traceNo, recipientName, recipientPhoneNumber, recipientAddress, warehouseId, warehouseName, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, itemList, signature, uniqueNo, userId, userName);
        try {
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            String gongxiaoOutboundOrderNo = DateTimeIdGenerator.nextId(projectPrefix, BizNumberType.STOCK_POOUT_ORDER_NO);
            if (gongxiaoOutboundOrderNo.length() > WMS_ORDERNO_LENGTH_LIMIT) {  //如果生成的出库单号超过wms系统限制的32位直接返回
                logger.error("#traceId={}# [OUT] get createInboundOrder success: gongxiaoOutboundOrderNo={} , over wms system limit 32", rpcHeader.getTraceId(), gongxiaoOutboundOrderNo);
                new RuntimeException("gongxiaoOutboundOrderNo over wms system limit 32");
            }
            OutboundOrder outboundOrder = new OutboundOrder();
            outboundOrder.setSourceChannel(Integer.parseInt(sourceChannelId));  //发起出库通知单的渠道
            outboundOrder.setProjectId(projectId);
            outboundOrder.setOutboundType(outboundType); //订单类型
            outboundOrder.setWarehouseId(warehouseId);
            outboundOrder.setWarehouseName(warehouseName);


            //调用基础模块的经销商grpc服务
            DistributorServiceGrpc.DistributorServiceBlockingStub distributorService = GlobalRpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.GetDistributorBasicByIdReq getDistributorBasicByIdReq = DistributorStructure.GetDistributorBasicByIdReq.newBuilder().setRpcHeader(rpcHeader).setDistributorBasicId(Long.parseLong(customerId)).build();
            DistributorStructure.GetDistributorBasicByIdResp distributorBasicByIdResp = distributorService.getDistributorBasicById(getDistributorBasicByIdReq);
            DistributorStructure.DistributorBasic distributorBasic = distributorBasicByIdResp.getDistributorBasic();
            outboundOrder.setCustomer(distributorBasic.getDistributorName());   //客户
            outboundOrder.setContactsPeople(recipientName);
            outboundOrder.setPhoneNum(recipientPhoneNumber);
            outboundOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));  //创建人id
            outboundOrder.setCreatedByName(rpcHeader.getUsername());           //创建人

            //调用基础模块的仓库grpc服务查仓库编码
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(warehouseId).build();
            WarehouseStructure.GetWarehouseByIdResp warehouseByIdResp = warehouseService.getWarehouseById(getWarehouseByIdReq);
            WarehouseStructure.Warehouse warehouse = warehouseByIdResp.getWarehouse();
            outboundOrder.setDeliverAddress(warehouse.getStreetAddress());
            outboundOrder.setShippingAddress(recipientAddress);             //发货地址
            outboundOrder.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);
            outboundOrder.setShippingMode(shippingMode);                                //物流方式
            outboundOrder.setTotalQuantity(totalQuantity);
            outboundOrder.setNote(note);
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

            List<OutboundOrderItem> outboundOrderItemList = new ArrayList<>();
            StringBuilder outboundBatchNo = new StringBuilder();
            if (itemList.size() > 0) {
                for (PlanOutboundOrderItem record : itemList) {
                    OutboundOrderItem outboundOrderItem = new OutboundOrderItem();
                    String outboundOrderItemNo = DateTimeIdGenerator.nextId(projectPrefix, BizNumberType.STOCK_POOUT_ORDER_NO);
                    outboundBatchNo.append(record.getBatchNo());
                    outboundOrderItem.setBatchNo(record.getBatchNo());     //批次号
                    outboundOrderItem.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);
                    outboundOrderItem.setInventoryType(record.getInventoryType());
                    outboundOrderItem.setPurchaseType(record.getPurchaseType());
                    outboundOrderItem.setOutboundOrderItemNo(outboundOrderItemNo);
                    outboundOrderItem.setSalesOrderNo(record.getSalesOrderNo());
                    outboundOrderItem.setProductId(record.getProductId());
                    outboundOrderItem.setProductCode(record.getProductCode());
                    outboundOrderItem.setProductName(record.getProductName());
                    outboundOrderItem.setWarehouseId(record.getWarehouseId());
                    outboundOrderItem.setWarehouseName(record.getWarehouseName());
                    outboundOrderItem.setTotalQuantity(record.getTotalQuantity());
                    outboundOrderItem.setReturnQuantity(record.getTotalQuantity());
                    outboundOrderItem.setCreateTime(new Date());
                    outboundOrderItemList.add(outboundOrderItem);
                }
                outboundOrder.setBatchNo(outboundBatchNo.toString());
                outBoundOrderDao.insertOutStorageInfo(outboundOrder, projectPrefix);
                outBoundOrderItemDao.insertOutboundOrderItem(outboundOrderItemList, projectPrefix);
            }

            //1、通知wms
            NotificationWmsOutboundTask outboundTask = new NotificationWmsOutboundTask(rpcHeader, outboundOrder, outboundOrderItemList, outBoundOrderDao, wmsNotificationOutboundService, warehouseConfig);
            threadPoolTaskExecutor.submit(outboundTask);

            logger.info("#traceId={}# [OUT] get createOutboundOrder success: result={}", rpcHeader.getTraceId(), "success");
            return gongxiaoOutboundOrderNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public Map<String, List<OutboundOrderItem>> createOutboundOrder2(GongxiaoRpc.RpcHeader rpcHeader, String sourceChannelId, int outboundType, String projectId, String customerId, String traceNo, String recipientName, String recipientPhoneNumber,
                                                                     String recipientAddress, String provinceName, String cityName, int shippingMode, String logisticsCompanyName, String logisticsNo, String note, int totalQuantity, List<PlanOutboundOrderItem> itemList,
                                                                     String signature, String uniqueNo, Date arrivalDate, long userId, String userName) {
        logger.info("#traceId={}# [IN][createOutboundOrder2]: sourceChannelId={},outboundType={},projectId={},customerId={},traceNo={},recipientName={},recipientPhoneNumber={},recipientAddress={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={},uniqueNo={},arrivalDate={}，userId={}, userName={}",
                rpcHeader.getTraceId(), sourceChannelId, outboundType, projectId, customerId, traceNo, recipientName, recipientPhoneNumber, recipientAddress,
                shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature, uniqueNo, arrivalDate, userId, userName);
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

            //查找项目前缀
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            //调用基础模块的仓库grpc服务查仓库编码
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            for (Map.Entry<String, List<PlanOutboundOrderItem>> entry : warehouseOrderMap.entrySet()) {
                //封装出库记录
                String gongxiaoOutboundOrderNo = DateTimeIdGenerator.nextId(projectPrefix, BizNumberType.STOCK_SOOUT_ORDER_NO);  //出库单号
                if (gongxiaoOutboundOrderNo.length() > WMS_ORDERNO_LENGTH_LIMIT) {  //如果生成的出库单号超过wms系统限制的32位直接返回
                    logger.error("#traceId={}# [OUT] get createInboundOrder success: gongxiaoOutboundOrderNo={} , over wms system limit 32", rpcHeader.getTraceId(), gongxiaoOutboundOrderNo);
                    new RuntimeException("gongxiaoOutboundOrderNo lengh over wms system limit 32");
                }
                OutboundOrder outboundOrder = new OutboundOrder();
                outboundOrder.setProjectId(projectId);
                outboundOrder.setSourceChannel(Integer.parseInt(sourceChannelId));   //发起出库通知单的渠道
                outboundOrder.setOutboundType(outboundType);      //订单类型
                outboundOrder.setWarehouseId(entry.getKey());     //仓库id
                WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(entry.getKey()).build();
                WarehouseStructure.GetWarehouseByIdResp rpcResponse = warehouseService.getWarehouseById(getWarehouseByIdReq);
                WarehouseStructure.Warehouse warehouse = rpcResponse.getWarehouse();
                outboundOrder.setWarehouseName(warehouse.getWarehouseName());
                outboundOrder.setDeliverAddress(warehouse.getStreetAddress());

                //调用基础模块的经销商grpc服务
                DistributorServiceGrpc.DistributorServiceBlockingStub distributorService = GlobalRpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
                DistributorStructure.GetDistributorBusinessByIdReq getDistributorBusinessByIdReq = DistributorStructure.GetDistributorBusinessByIdReq.newBuilder().setRpcHeader(rpcHeader).setDistributorBusinessId(Long.parseLong(customerId)).build();
                DistributorStructure.GetDistributorBusinessByIdResp distributorBasicByIdResp = distributorService.getDistributorBusinessById(getDistributorBusinessByIdReq);
                DistributorStructure.DistributorBusiness distributorBusiness = distributorBasicByIdResp.getDistributorBusiness();
                outboundOrder.setCustomerId(customerId);
                outboundOrder.setCustomer(distributorBusiness.getDistributorName());    //客户
                outboundOrder.setContactsPeople(recipientName);
                outboundOrder.setPhoneNum(recipientPhoneNumber);
                outboundOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                outboundOrder.setCreatedByName(rpcHeader.getUsername());
                outboundOrder.setShippingAddress(recipientAddress);
                outboundOrder.setProvinceName(provinceName);
                outboundOrder.setCityName(cityName);
                outboundOrder.setExpectedArrivalTime(arrivalDate);
                outboundOrder.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);
                outboundOrder.setSalesOrderNo(traceNo);
                outboundOrder.setOrderStatus(OutboundOrderStatusEnum.OUTBOUND_ORDER_WAIT.getStatus());     //出库单状态
                outboundOrder.setSyncToWmsFlag(SyncWmsEnum.SYNC_WMS_FAIL.getStatus());       //同步wms状态
                outboundOrder.setShippingMode(shippingMode);      //物流方式
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

                //调用库存模块的项目的grpc查询项目信息
                InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub inventoryquerryService = WarehouseRpcStubStore.getRpcStub(InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub.class);
                List<OutboundOrderItem> outboundOrderItemList = new ArrayList<>();
                for (PlanOutboundOrderItem record : entry.getValue()) {    //封装出库明细记录
                    String outboundOrderItemNo = DateTimeIdGenerator.nextId(projectPrefix, BizNumberType.STOCK_SOOUT_ORDER_ITEM_NO);   //出库明细单号
                    conectedBatchNo.append(record.getBatchNo());
                    connectedProduct.append(record.getProductCode());
                    recordTotalQuantity += record.getTotalQuantity();
                    connectedOutboundOrderItemNo.append(outboundOrderItemNo);

                    OutboundOrderItem outboundOrderItem = new OutboundOrderItem();
                    outboundOrderItem.setProjectId(projectId);
                    outboundOrderItem.setBatchNo(record.getBatchNo());    //批次号
                    outboundOrderItem.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);
                    outboundOrderItem.setPurchaseType(record.getPurchaseType());     //货补，普通采购，赠品
                    outboundOrderItem.setInventoryType(record.getInventoryType());
                    outboundOrderItem.setOutboundOrderItemNo(outboundOrderItemNo);
                    outboundOrderItem.setSalesOrderNo(record.getSalesOrderNo());
                    outboundOrderItem.setProductCode(record.getProductCode());
                    outboundOrderItem.setProductName(record.getProductName());
                    outboundOrderItem.setProductId(record.getProductId());
                    outboundOrderItem.setWarehouseId(record.getWarehouseId());
                    outboundOrderItem.setWarehouseName(record.getWarehouseName());
                    outboundOrderItem.setTotalQuantity(record.getTotalQuantity());
                    outboundOrderItem.setReturnQuantity(record.getTotalQuantity());
                    outboundOrderItem.setSalesGuidePrice(record.getSalesGuidePrice());
                    outboundOrderItem.setWholesalePrice(record.getWholesalePrice());

                    InventoryquerryStructure.GetInventoryInfoRequest getInventoryInfoRequest = InventoryquerryStructure.GetInventoryInfoRequest.newBuilder()
                            .setPurchaseType(record.getPurchaseType())
                            .setInventoryStatus(record.getInventoryType())
                            .setProjectId(Long.parseLong(projectId))
                            .setBatchNo(record.getBatchNo())
                            .setProductCode(record.getProductCode())
                            .setWarehouseId(record.getWarehouseId())
                            .build();
                    InventoryquerryStructure.GetInventoryInfoResponse getInventoryInfoResponse = inventoryquerryService.selectInventoryInfo(getInventoryInfoRequest);
                    outboundOrderItem.setPurchasePrice(getInventoryInfoResponse.getProductInventory().getPurchasePrice());
                    outboundOrderItem.setCreateTime(new Date());
                    outboundOrderItemList.add(outboundOrderItem);

                }

                outboundOrder.setBatchNo(conectedBatchNo.toString());
//                outboundOrder.setInventoryType(record.getInventoryType());                  //库存类型,因为有多种类型，不填
                outboundOrder.setConnectedProduct(connectedProduct.toString());               //出库商品
                outboundOrder.setOutboundOrderItemNo(connectedOutboundOrderItemNo.toString()); //出库明细单号，对应wms的行号
                outboundOrder.setTotalQuantity(recordTotalQuantity);

                outBoundOrderItemDao.insertOutboundOrderItem(outboundOrderItemList, projectPrefix);
                outBoundOrderDao.insertOutStorageInfo(outboundOrder, projectPrefix);
                resultMap.put(gongxiaoOutboundOrderNo, outboundOrderItemList);

                try {
                    //1、通知WMS出库
                    NotificationWmsOutboundTask outboundTask = new NotificationWmsOutboundTask(rpcHeader, outboundOrder, outboundOrderItemList, outBoundOrderDao, wmsNotificationOutboundService, warehouseConfig);
                    threadPoolTaskExecutor.submit(outboundTask);
                } catch (Exception e) {
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                    return resultMap;
                }
            } //end-of-for

            try {
                //修改库存占用数量
                NotificationInventoryTask inventoryTask = new NotificationInventoryTask(rpcHeader, projectId, itemList);
                threadPoolTaskExecutor.submit(inventoryTask);
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                return resultMap;
            }
            logger.info("#traceId={}# [OUT] get createOutboundOrder2 success,", rpcHeader.getTraceId(), "success");
            return resultMap;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public int cancelOrder(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String warehouseId, String gongxiaoOutboundOrderNo, String productCode, String signature) {
        logger.info("#traceId={}# [IN][cancelOutboundOrder] params: projectId={},warehouseId={},gongxiaoInboundOrderNo={},orderType={},ownerCode={},signature={},productCode ={}",
                rpcHeader.getTraceId(), projectId, warehouseId, gongxiaoOutboundOrderNo, signature, productCode);
        //调用基础模块的仓库grpc服务查仓库编码
        WmsCancelOrderRequest wmsCancelOrderRequest = new WmsCancelOrderRequest();
        WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
        WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(warehouseId).build();
        WarehouseStructure.GetWarehouseByIdResp rpcResponse = warehouseService.getWarehouseById(getWarehouseByIdReq);
        WarehouseStructure.Warehouse warehouse = rpcResponse.getWarehouse();

        wmsCancelOrderRequest.setCkid(Integer.parseInt(warehouse.getWmsWarehouseCode()));   //仓库表里面字段WmsWarehouseCode保存的是wms仓库的ckid
        wmsCancelOrderRequest.setOrderNo(gongxiaoOutboundOrderNo);
        wmsCancelOrderRequest.setOrderType(String.valueOf(WmsOrderType.OUTBOUND_ORDER.getInboundOrderCode())); //订单类型
        //调用基础模块的SourceChannel服务
        ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
        ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder().setRpcHeader(rpcHeader).setXpsChannelId("1").build();
        ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
        ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();

        wmsCancelOrderRequest.setOwnerCode(sourceChannel.getWmsCustCode());   //货主
        wmsCancelOrderRequest.setWarehouseCode(warehouse.getWmsWarehouseCode());  //仓库编码

        NotificationWmsCancelOutTask task = new NotificationWmsCancelOutTask(rpcHeader, projectId, wmsCancelOrderService, wmsCancelOrderRequest, outBoundOrderDao, outBoundOrderItemDao, warehouseConfig);  //通知WMS系统取消出库单
        threadPoolTaskExecutor.submit(task);
        logger.info("#traceId={}# [OUT] get cancelOutboundOrder success", rpcHeader.getTraceId());
        return 0;
    }

    @Override
    public int closeOutboundOrder(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String warehouseId, String gongxiaoOutboundOrderNo, String productCode, String signature) {
        logger.info("#traceId={}# [IN][closeOutboundOrder] params: projectId={},warehouseId={},gongxiaoInboundOrderNo={},orderType={},ownerCode={},signature={}", rpcHeader.getTraceId(), projectId, warehouseId, gongxiaoOutboundOrderNo, signature);
        WmsCloseOrderRequest wmsCloseOrderRequest = new WmsCloseOrderRequest();

        //调用基础模块的仓库grpc服务查仓库编码
        WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
        WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(warehouseId).build();
        WarehouseStructure.GetWarehouseByIdResp rpcResponse = warehouseService.getWarehouseById(getWarehouseByIdReq);
        WarehouseStructure.Warehouse warehouse = rpcResponse.getWarehouse();

        wmsCloseOrderRequest.setCkid(Integer.parseInt(warehouse.getWmsWarehouseCode()));    //仓库表里面字段WmsWarehouseCode保存的是wms仓库的ckid
        wmsCloseOrderRequest.setOrderNo(gongxiaoOutboundOrderNo);                                             //订单号
        wmsCloseOrderRequest.setOrderType(String.valueOf(WmsOrderType.OUTBOUND_ORDER.getInboundOrderCode()));   //订单类型
        //调用基础模块的SourceChannel服务
        ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
        ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder().setRpcHeader(rpcHeader).setXpsChannelId("1").build();
        ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
        ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();

        wmsCloseOrderRequest.setOwnerCode(sourceChannel.getWmsCustCode());                    //货主
        wmsCloseOrderRequest.setWarehouseCode(warehouse.getWmsWarehouseCode());  //仓库编码

        String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
        List<OutboundOrderItem> outboundOrderItemList = outBoundOrderItemDao.selectOutboundOrderItemByNo(gongxiaoOutboundOrderNo, projectPrefix);
        List<OrderItemDto> itemDtoList = new ArrayList<>();
        for (OutboundOrderItem record : outboundOrderItemList) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setItemCode(record.getProductCode());
            orderItemDto.setItemQuantity(record.getTotalQuantity());
        }
        wmsCloseOrderRequest.setItems(itemDtoList);

        NotificationWmsCloseOutTask task = new NotificationWmsCloseOutTask(rpcHeader, projectId, wmsCloseOrderService, wmsCloseOrderRequest, warehouseConfig, outBoundOrderDao, outBoundOrderItemDao);  //通知WMS系统取消出库单
        threadPoolTaskExecutor.submit(task);
        logger.info("#traceId={}# [OUT] get closeOutboundOrder success", rpcHeader.getTraceId());
        return 0;
    }

    @Override
    public void sureSighIn(GongxiaoRpc.RpcHeader rpcHeader, String transporter, String custOrdNo, String tmsOrdNo, String remark, String signedBy, String postedBy, String signedPhone, Date signedTime) {
        logger.info("#traceId={}# [IN][sureSighIn]: transporter={},custOrdNo={},tmsOrdNo={},remark={},signedBy={},postedBy={},signedPhone={},signedTime={}", rpcHeader.getTraceId(), transporter, custOrdNo, tmsOrdNo, remark, signedBy, postedBy, signedPhone, signedTime);
        try {
            String projectPrefix;
            if (custOrdNo.startsWith("SOOUT")) {      //为了兼容之前做的单，所以做了此判断（待删除）
                projectPrefix = "shaver";
            } else {
                String[] custOrdNoArgs = custOrdNo.split("_");
                projectPrefix = custOrdNoArgs[1];
            }

            OutboundOrder outboundOrder = outBoundOrderDao.getOutStorageInfoByNo(custOrdNo, projectPrefix);    //根据单号查到对应的出库单
            if (outboundOrder != null) {         //出库单存在
                outboundOrder.setOrderStatus(OutboundOrderStatusEnum.OUTBOUND_ORDER_SIGHT.getStatus());
                outboundOrder.setTransporter(transporter);
                outboundOrder.setTmsOrdNo(tmsOrdNo);
                outboundOrder.setNote(remark);
                outboundOrder.setTmsSignedBy(signedBy);
                outboundOrder.setTmsSignedPhone(signedPhone);
                outboundOrder.setTmsSingedTime(signedTime);
                if (outboundOrder.getOutboundType() == WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode()) {  //如果是销售出库

                    outBoundOrderDao.sureSighIn(outboundOrder, projectPrefix);
                    outBoundOrderItemDao.sureSighIn(outboundOrder.getGongxiaoOutboundOrderNo(), projectPrefix);
                    wmsOutboundDao.updateTmsOrderNo(outboundOrder.getGongxiaoOutboundOrderNo(), tmsOrdNo);
                    logger.info("#traceId={}# [OUT] get sureSighIn success,签收成功", rpcHeader.getTraceId());
                } else {       //如果是其他出库单
                    outBoundOrderDao.sureSighIn(outboundOrder, projectPrefix);
                    outBoundOrderItemDao.sureSighIn(outboundOrder.getGongxiaoOutboundOrderNo(), projectPrefix);
                    wmsOutboundDao.updateTmsOrderNo(outboundOrder.getGongxiaoOutboundOrderNo(), tmsOrdNo);
                    logger.info("#traceId={}# [OUT] get sureSighIn success,签收成功", rpcHeader.getTraceId());
                }

            } else {   //出库单不存在
                logger.info("#traceId={}# [OUT] get sureSighIn success,签收失败,订单不存在", rpcHeader.getTraceId());
            }

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }
    }

}
