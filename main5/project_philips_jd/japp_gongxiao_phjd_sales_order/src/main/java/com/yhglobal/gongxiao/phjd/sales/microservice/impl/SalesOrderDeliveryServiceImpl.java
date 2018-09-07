package com.yhglobal.gongxiao.phjd.sales.microservice.impl;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.XpsGrossProfitManager;
import com.yhglobal.gongxiao.XpsPrepaidManager;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.UpdateStatus;
import com.yhglobal.gongxiao.constant.sales.SalesOrderStatusEnum;
import com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum;
import com.yhglobal.gongxiao.constant.sales.SalesOutboundOrderStatusEnum;
import com.yhglobal.gongxiao.eas.model.SaleOutOrder;
import com.yhglobal.gongxiao.eas.model.SaleOutOrderItem;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.phjd.common.service.CommonService;
import com.yhglobal.gongxiao.phjd.sales.dao.*;
import com.yhglobal.gongxiao.phjd.sales.model.*;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure;
import com.yhglobal.gongxiao.transportataion.sendtotransportation.XpsTransportationManager;
import com.yhglobal.gongxiao.type.WmsOrderType;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.utils.BigDecimalUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.sales.model.EasOutboundOrderRequest;
import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehouseapi.model.PlanOutboundOrderItem;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 销售订单出货相关
 *
 * @author weizecheng
 * @date 2018/8/28 16:36
 */
@Service
public class SalesOrderDeliveryServiceImpl extends SalesOrderDeliveryServiceGrpc.SalesOrderDeliveryServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(SalesOrderDeliveryServiceImpl.class);

    @Autowired
    private SalesOrderItemDao salesOrderItemDao;
    @Autowired
    private SalesConfig salesConfig;
    @Autowired
    private SalesOrderDao salesOrderDao;
    @Autowired
    private SalesOrderAddressDao salesOrderAddressDao;
    @Autowired
    private SalesOutboundOrderDao salesOutboundOrderDao;
    @Autowired
    private SalesScheduleDeliveryDao salesScheduleDeliveryDao;
    @Autowired
    private CommonService commonService;
    /**
     * 预约出货GRPC调用
     *
     * @author weizecheng
     * @date 2018/8/29 11:46
     */
    @Override
    public void createDeliveryOrder(SalesOrderDeliveryServiceStructure.CreateDeliveryOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        long arrivalDate = request.getArrivalDate();
        long projectId = request.getProjectId();
        Integer shippingMode = request.getShippingMode();
        List<SalesOrderDeliveryServiceStructure.ScheduleItems> productInfo = request.getProductInfoList();
        try {
            logger.info("#getTraceId()={}# [IN][createScheduleOrder] params: salesOrderNo={},arrivalDate ={}, productInfo={},projectId={}"
                    , rpcHeader.getTraceId(), salesOrderNo, arrivalDate,productInfo,projectId);
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            // 获取预约发货各个商品的总数量 使用hashMap存取
            HashMap<String, Integer> countMap = new HashMap<>(productInfo.size());
            productInfo.forEach(inventoryBatch->{
                String productCode = inventoryBatch.getProductCode();
                int scheduleQuantity = inventoryBatch.getScheduleQuantity();
                if (countMap.containsKey(productCode)) {
                    countMap.put(productCode, scheduleQuantity);
                } else {
                    countMap.put(productCode, countMap.get(productCode) + scheduleQuantity);
                }
            });
            // 获取销售订单
            SalesOrder salesOrder = salesOrderDao.getSalesOrderByOrderNo(prefix,salesOrderNo);
            if(salesOrder == null){
                // 订单不存在
                logger.info("#getTraceId()={}# [OUT]: SalesOrder is null !", rpcHeader.getTraceId());
                response = GrpcCommonUtil.fail(ErrorCode.SALES_ORDER_IS_NULL);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            // 获取销售订单下的各个详细商品
            // 校验传过来的待发货数量
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.listBySalesOrderNo(prefix,salesOrderNo);
            // 方便查询 从List要for循环 太慢
            HashMap<String,SalesOrderItem> salesOrderItemHashMap = new HashMap<>(salesOrderItems.size());
            for(SalesOrderItem item:salesOrderItems){
                String productCode = item.getProductCode();
                salesOrderItemHashMap.put(productCode,item);
                if(countMap.containsKey(productCode)){
                    if (countMap.get(productCode) > item.getUnhandledQuantity()) {
                        //如果数据版本不一致
                        logger.info("#getTraceId()={}# [OUT]: Delivery FAILED !", rpcHeader.getTraceId());
                        response = GrpcCommonUtil.fail(ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER);
                        responseObserver.onNext(response);
                        responseObserver.onCompleted();
                        return;
                    }
                }
            }
            // 调用基础模块 获取标识
            String channelId = salesConfig.getChannelId();
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = commonService.getChannelByChannelIdResp(rpcHeader,channelId);
            ArrayList<PlanOutboundOrderItem> planOutboundOrderItems = new ArrayList<>(productInfo.size());
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            //一、通知仓库开始
            int totalQuantity = 0;
            for (SalesOrderDeliveryServiceStructure.ScheduleItems record : productInfo) {
                totalQuantity += record.getScheduleQuantity();
                String productCode = record.getProductCode();
                PlanOutboundOrderItem planOutboundOrderItem = new PlanOutboundOrderItem();
                planOutboundOrderItem.setInventoryType(record.getInventoryStatus());
                planOutboundOrderItem.setBatchNo(record.getBatchNo());
                planOutboundOrderItem.setSalesOrderNo(salesOrderNo);
                planOutboundOrderItem.setWarehouseId(record.getWarehouseId());
                planOutboundOrderItem.setWarehouseName(record.getWarehouseName());
                planOutboundOrderItem.setProductId(record.getProductId());
                planOutboundOrderItem.setProductCode(productCode);
                planOutboundOrderItem.setProductName(record.getProductName());
                planOutboundOrderItem.setTotalQuantity(record.getScheduleQuantity());
                planOutboundOrderItem.setPurchaseType(record.getPurchaseType());
                // 存入价格
                planOutboundOrderItem.setWholesalePrice(BigDecimalUtil.multiplicationSix(salesOrderItemHashMap.get(productCode).getWholesalePrice()).longValue());
                planOutboundOrderItem.setSalesGuidePrice(BigDecimalUtil.multiplicationSix(salesOrderItemHashMap.get(productCode).getJdSalesGuidePrice()).longValue());
                planOutboundOrderItems.add(planOutboundOrderItem);
                if (record.getScheduleQuantity() > record.getOrderUnhandledQuantity() ) {
                    //如果 预约数量>未发货数量 ,预约失败
                    logger.info("#getTraceId()={}# [OUT]: createDeliveryOrder FAILED !", rpcHeader.getTraceId());
                    response = GrpcCommonUtil.fail(ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }
            }
            // 获取销售订单地址
            SalesOrderAddressDO  salesOrderAddressDO = salesOrderAddressDao.getAddressBySalesOrderId(prefix,salesOrder.getSalesOrderId());
            // 生成销售唯一号
            String saleUniqueNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.SALE_UNIQUE_NO);
            Map<String, List<OutboundOrderItem>> outboundMap = XpsWarehouseManager.SalesCreateOutboundOrder2(salesConfig.getWarehouseUrl(),
                    salesConfig.getChannelId(), xpsChannelSecret,
                    salesOrder.getMarketingChannel().toString(), WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode(),
                    salesOrder.getProjectId().toString(),
                    salesOrder.getDistributorId().toString(),
                    salesOrderNo,
                    salesOrderAddressDO.getReceiver(),
                    salesOrderAddressDO.getReceiverTel(),
                    salesOrderAddressDO.getReceivingAddress(),
                    // TODO 京东项目地址是京东传送过来 Arrived 指货物最终到达的城市或省。
                    salesOrderAddressDO.getArrived(),
                    salesOrderAddressDO.getArrived(),
                    // TODO 送货方式前台传入 并进行更新
                    shippingMode,
                    "logisticsCompanyName", "logisticsNo", "note", totalQuantity,
                    planOutboundOrderItems, "sales", saleUniqueNo, new Date(arrivalDate),Long.parseLong(rpcHeader.getUid()),rpcHeader.getUsername()
            );

            String schduleOrderNo = DateTimeIdGenerator.nextId(prefix,BizNumberType.SALES_SCHEDULE_ORDER_NO);
            SalesScheduleDelivery salesScheduleDelivery = new SalesScheduleDelivery();
            //创建预约发货单号
            salesScheduleDelivery.setScheduleOrderNo(schduleOrderNo);
            //填充字段
            salesScheduleDelivery.setUniqueNo(saleUniqueNo);
            salesScheduleDelivery.setScheduleStatus(0);
            salesScheduleDelivery.setSyncToGongxiaoWarehouseFlag(false);
            salesScheduleDelivery.setSalesOrderId(salesOrderNo);
            // TODO 发货方式 前端传入
            salesScheduleDelivery.setShippingMode(shippingMode);
            salesScheduleDelivery.setDataVersion(0L);
            salesScheduleDelivery.setTotalQuantity(totalQuantity);
            salesScheduleDelivery.setOnGoingOutboundOrderInfo(JSON.toJSONString(outboundMap.keySet()));
            // 这个是为了转换为list 不为空
            salesScheduleDelivery.setOutboundedOrderInfo("[]");
            salesScheduleDelivery.setSignedOrderInfo("[]");
            TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "新建");
            ArrayList<TraceLog> traceLogs = new ArrayList<>(1);
            traceLogs.add(traceLog);
            salesScheduleDelivery.setTracelog(JSON.toJSONString(traceLogs));
            salesScheduleDelivery.setProductInfo(productInfo.toString());
            // 存入预约发货表
            salesScheduleDeliveryDao.insertSalesScheduleDelivery(prefix, salesScheduleDelivery);
            for (String outboundOrderNo : outboundMap.keySet()) {
                //出库单-数量总计
                int quantityInOutboundOrder = 0;
                String warehouseId = null;
                List<OutboundOrderItem> outboundItems = outboundMap.get(outboundOrderNo);
                for (OutboundOrderItem item : outboundItems) {
                    quantityInOutboundOrder += item.getTotalQuantity();
                    warehouseId = item.getWarehouseId();
                }
                SalesOutboundOrder salesOutboundOrder = new SalesOutboundOrder();
                salesOutboundOrder.setWarehouseId(warehouseId);
                salesOutboundOrder.setOutboundOrderNo(outboundOrderNo);
                salesOutboundOrder.setSalesOrderNo(salesOrderNo);
                salesOutboundOrder.setTotalQuantity(quantityInOutboundOrder);
                salesOutboundOrder.setOrderStatus(SalesOutboundOrderStatusEnum.IN_OUTBOUND.getStatus());
                salesOutboundOrder.setExpectedArrivalTime(new Date(arrivalDate));
                salesOutboundOrder.setItems(JSON.toJSONString(outboundItems));
                salesOutboundOrder.setCreatedById(rpcHeader.getUid());
                salesOutboundOrder.setCreatedByName(rpcHeader.getUsername());
                // 插入出库订单
                salesOutboundOrderDao.insertSalesOutboundOrder(prefix,salesOutboundOrder);
            }
            //  做完以上操作  再更新销售订单商品明细
            salesOrderItems.forEach(item->{
                if(countMap.containsKey(item.getProductCode())){
                    int unhandledQuantity = item.getUnhandledQuantity() - countMap.get(item.getProductCode());
                    int update = salesOrderItemDao.updateUnhandledQuantitySalesOrderItem(prefix, item.getSalesOrderItemId(), item.getDataVersion(), unhandledQuantity);
                    if (update != 1) {
                        logger.error("FAILED to update salesOrderItem unhandledQuantity. salesOrderNo={}, productCode={}, unhandledQuantity={}", salesOrderNo, item.getProductCode(), unhandledQuantity);
                    }
                }else {
                    logger.info("#getTraceId()={}# [OUT]: ProductCode is null!", rpcHeader.getTraceId(),item.getProductCode());
                }
            });

            //四、更新销售单信息
            // 如果是销售单第一次发货，更改状态为待同步eas
            Integer syncEasStatus = salesOrder.getSyncEas();
            if (salesOrder.getUnhandledQuantity().equals(salesOrder.getTotalCostQuantity())) {
                syncEasStatus = SalesOrderSyncEnum.UNHANDLED.getStatus();
            }
            //如果销售单商品全部发货，修改状态为“已通知仓库”
            int totalUnhandledQuantity = salesOrder.getUnhandledQuantity() - totalQuantity;
            Integer salesOrderStatus = salesOrder.getSalesOrderStatus();
            Date informWarehouseTime = null;
            if (totalUnhandledQuantity == 0) {
                salesOrderStatus = SalesOrderStatusEnum.INFORMED.getStatus();
                informWarehouseTime = new Date();
            }
            String ongoingOutboundOrderInfo = salesOrder.getOngoingOutboundOrderInfo();
            List<String> originalScheduleOrderNoList = JSON.parseArray(ongoingOutboundOrderInfo, String.class);
            // 防止空指针
            if(null == originalScheduleOrderNoList){
                originalScheduleOrderNoList = new ArrayList<>();
            }
            // 出库JSON
            originalScheduleOrderNoList.addAll(outboundMap.keySet());
            String newOngoingOutboundOrderInfo = JSON.toJSONString(originalScheduleOrderNoList);

            // 操作日志
            TraceLog traceLog2 = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "新建发货单");
            String tracelog = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog2);

            /* 更新销售订单相关信息 */
            int update = salesOrderDao.updateScheduleDelivery(prefix,salesOrder.getDataVersion(),salesOrder.getSalesOrderId(),tracelog,salesOrderStatus,informWarehouseTime,syncEasStatus,totalUnhandledQuantity,newOngoingOutboundOrderInfo);
            // 怕中间出现其他操作
            if(UpdateStatus.UPDATE_FAIL.getStatus().equals(update)){
                // 乐观锁得6次
                int maxRetryTimes = 6;
                while (maxRetryTimes-- > 0) {
                    SalesOrder order  = salesOrderDao.getSalesOrderById(prefix,salesOrder.getSalesOrderId());
                    update = salesOrderDao.updateScheduleDelivery(prefix,order.getDataVersion(),salesOrder.getSalesOrderId(),tracelog,salesOrderStatus,informWarehouseTime,syncEasStatus,totalUnhandledQuantity,newOngoingOutboundOrderInfo);
                    if (UpdateStatus.UPDATE_SUCCESS.getStatus().equals(update)) {
                        break;
                    }
                }
            }
            logger.info("#getTraceId()={}# [OUT]: create schedule order success", rpcHeader.getTraceId());
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT]  createDeliveryOrder errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 取消出库单GRPC调用
     *
     * @author weizecheng
     * @date 2018/8/29 15:02
     */
    @Override
    public void cancelSalesOutboundOrder(SalesOrderDeliveryServiceStructure.OutboundNoRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        // 获取出库单号
        String outboundOrderNo = request.getOutboundOrderNo();
        // 项目Id
        long projectId = request.getProjectId();
        try{
            logger.info("#traceId={}# [IN][cancelSalesOutboundOrder] outboundOrderNo={},  projectId={} ", rpcHeader.getTraceId(), outboundOrderNo, projectId);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            // 出库单
            SalesOutboundOrder salesOutboundOrder = salesOutboundOrderDao.getByOutboundOrderNo(prefix, outboundOrderNo);
            //  销售订单编号
            String salesOrderNo = salesOutboundOrder.getSalesOrderNo();
            // 出库单号JSON转List
            List<OutboundOrderItem> outboundOrderItems = JSON.parseArray(salesOutboundOrder.getItems(), OutboundOrderItem.class);
            if(null != outboundOrderItems){
                outboundOrderItems.forEach(item->{
                    String productCode = item.getProductCode();
                    SalesOrderItem salesOrderItem =
                            salesOrderItemDao.getBySalesOrderNoAndProductCode(prefix, salesOrderNo, productCode);
                    int unhandledQuantity = salesOrderItem.getUnhandledQuantity() + item.getTotalQuantity();
                    int updateUnhandledQuantity = salesOrderItemDao.updateUnhandledQuantitySalesOrderItem(prefix, salesOrderItem.getSalesOrderItemId(), salesOrderItem.getDataVersion(), unhandledQuantity);
                    if(UpdateStatus.UPDATE_FAIL.getStatus().equals(updateUnhandledQuantity)){
                        logger.error("FAILED to update salesOrderItem unhandledQuantity. salesOrderNo={}, productCode={}, unhandledQuantity={}", salesOrderNo, productCode, unhandledQuantity);
                    }
                });
            }
            // 预约发货总数量
            int totalQuantity = salesOutboundOrder.getTotalQuantity();
            int maxRetryTimes = 6;
            boolean success = false;
            while (maxRetryTimes-- > 0) {
                SalesOrder salesOrder= salesOrderDao.getSalesOrderByOrderNo(prefix, salesOrderNo);
                int totalUnhandledQuantity = salesOrder.getUnhandledQuantity() + totalQuantity;
                // 如果是销售单出库被全部退回
                //, 修改状态为“待下发仓库”, 通知仓库时间为null 相当于回滚
                int salesOrderStatus = salesOrder.getSalesOrderStatus();
                // eas同步状态
                int syncEasStatus  = salesOrder.getSyncEas();
                // 出库时间
                Date informWarehouseTime = salesOrder.getInformWarehouseTime();
                String ongoingOutboundOrderInfo = salesOrder.getOngoingOutboundOrderInfo();
                if (salesOrder.getTotalQuantity().equals(totalUnhandledQuantity)) {
                    // 待下发仓库
                    salesOrderStatus = SalesOrderStatusEnum.PAID.getStatus();
                    //  无需同步
                    syncEasStatus = SalesOrderSyncEnum.INIT.getStatus();
                    informWarehouseTime = null;
                    List<String> originalScheduleOrderNoList = JSON.parseArray(ongoingOutboundOrderInfo, String.class);
                    originalScheduleOrderNoList.remove(outboundOrderNo);
                    ongoingOutboundOrderInfo = JSON.toJSONString(originalScheduleOrderNoList);
                }
                // 操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消出库单");
                String tracelog = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                // 进行更新销售订单
                int update = salesOrderDao.updateScheduleDelivery(prefix,salesOrder.getDataVersion(),salesOrder.getSalesOrderId(),tracelog,salesOrderStatus,informWarehouseTime,syncEasStatus,totalUnhandledQuantity,ongoingOutboundOrderInfo);
                if (UpdateStatus.UPDATE_SUCCESS.getStatus().equals(update)) {
                    success = true;
                    break;
                }
            }
            if (!success) {
                logger.error("FAILED to  cancelSalesOutboundOrder update sales order. SalesOrder={}, salesOrderStatus={}, totalQuantity={}"
                        , salesOrderNo,  totalQuantity);
            }
            // 同步TMS
            GongxiaoResult gongxiaoResult = XpsTransportationManager.cancelOutboundOrder(salesConfig.getTransportationUrl(), projectId, outboundOrderNo);
            GongxiaoRpc.RpcResult rpcResult = GrpcCommonUtil.fail(gongxiaoResult.getReturnCode(), gongxiaoResult.getMessage());
            responseObserver.onNext(rpcResult);
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT]  cancelSalesOutboundOrder errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 通知TMS 更细状态
     *
     * @author weizecheng
     * @date 2018/8/29 16:34
     */
    @Override
    public void submitOutboundOrderToTms(SalesOrderDeliveryServiceStructure.OutboundNoRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        // 获取出库单号
        String outboundOrderNo = request.getOutboundOrderNo();
        // 项目Id
        long projectId = request.getProjectId();
        try {
            logger.info("#getTraceId()={}# [IN][submitOutboundOrderToTms]: outboundOrderNo={}", rpcHeader.getTraceId(), outboundOrderNo);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            SalesOutboundOrder salesOutboundOrder = salesOutboundOrderDao.getByOutboundOrderNo(prefix, outboundOrderNo);
            GongxiaoRpc.RpcResult response;
            if(null != salesOutboundOrder){
                int update = salesOutboundOrderDao.updateSyncTms(prefix,salesOutboundOrder.getOid(),salesOutboundOrder.getDataVersion(),SalesOrderSyncEnum.UNHANDLED.getStatus());
                if(UpdateStatus.UPDATE_SUCCESS.getStatus().equals(update)){
                    response = GrpcCommonUtil.success();
                    logger.info("#getTraceId()={}# [OUT]: update order info success", rpcHeader.getTraceId());
                } else {
                    logger.info("#FAIL outboundOrderNo updateSyncTms, outboundOrderNo={}", outboundOrderNo);
                    response = GrpcCommonUtil.fail(ErrorCode.ARGUMENTS_INVALID);
                }
            }else {
                logger.info("#FAIL outboundOrderNo IS NULL, outboundOrderNo={}", outboundOrderNo);
                response = GrpcCommonUtil.fail(ErrorCode.ARGUMENTS_INVALID);
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }catch (Exception e){
            logger.error("#getTraceId()=" + rpcHeader.getTraceId() + "# [OUT] submitOutboundOrderToTms errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 出库完成 计算代垫 毛利
     *
     * @author weizecheng
     * @date 2018/9/3 10:00
     */
    @Override
    public void itemsOutboundFinished(SalesOrderDeliveryServiceStructure.OutboundNoRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String outboundOrderNo = request.getOutboundOrderNo();
        long projectId = request.getProjectId();
        try{
            logger.info("#getTraceId()={}# [IN][outboundOrderFinished]: outboundOrderNo={}", rpcHeader.getTraceId(), outboundOrderNo);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            int update = 0;
            int maxRetryTimes = 6;
            SalesOrder salesOrder;
            TraceLog traceLog;
            //一、查到出库单，找到对应的销售单明细、销售单
            //更新出库单
            SalesOutboundOrder outboundOrder = null;
            while (maxRetryTimes-- > 0) {
                outboundOrder = salesOutboundOrderDao.getByOutboundOrderNo(prefix, outboundOrderNo);
                int outboundOrderStatus = SalesOutboundOrderStatusEnum.OUTBOUND_FINISHED.getStatus();
                Date outboundTime = new Date();
                update = salesOutboundOrderDao.updateOutboundTime(prefix, outboundOrder.getOid(), outboundOrder.getDataVersion(), outboundOrderStatus, outboundTime);
                if (update == 1) {
                    break;
                }
            }
            if (update == 0) {
                logger.error("FAILED to update outboundOrder. outboundOrderNo={}", outboundOrderNo);
                throw new RuntimeException("FAILED to update outboundOrder");
            }
            maxRetryTimes = 6;
            // 代垫金额
            BigDecimal actingMat = BigDecimal.ZERO;
            // 应收毛利
            BigDecimal grossProfit = BigDecimal.ZERO;
            // 实收毛利
            BigDecimal actualGrossProfit = BigDecimal.ZERO;
            //更新销售单
            String salesOrderNo = outboundOrder.getSalesOrderNo();
            int totalQuantity = outboundOrder.getTotalQuantity();
            //更新销售单明细
            String items = outboundOrder.getItems();
            List<OutboundOrderItem> outboundOrderItems = JSON.parseArray(items, OutboundOrderItem.class);
            for (OutboundOrderItem outboundOrderItem : outboundOrderItems) {
                int itemQuantity = outboundOrderItem.getTotalQuantity();
                String productCode = outboundOrderItem.getProductCode();
                SalesOrderItem salesOrderItem = salesOrderItemDao.getBySalesOrderNoAndProductCode(prefix, salesOrderNo, productCode);
                // 1.根据批次号获取商品采购价格 outboundOrderItem.getBatchNo()
                // 应收代垫
                BigDecimal itemActingMat = salesOrderItem.getGeneratedPrepaid();
                // 京东价格(销售价)
                BigDecimal jdPrice = salesOrderItem.getJdPurchaseGuidePrice();
                // 采购价
                BigDecimal purchasingPrice  = BigDecimalUtil.keepSixBits(outboundOrderItem.getPurchasePrice());
                // 京东价格与采购价 差价
                BigDecimal difference = jdPrice.subtract(purchasingPrice);
                // 产生代垫
                if(difference.compareTo(BigDecimal.ZERO) > 0){
                    // 单个item的代垫生产
                    itemActingMat = BigDecimalUtil.multiplication(difference,itemQuantity).add(itemActingMat);
                    // 总的order的代垫生产
                    actingMat = BigDecimalUtil.multiplication(difference,itemQuantity).add(actingMat);
                }
                // 获取商品的基本信息
                ProductStructure.GetByProductModelResp productModelResp = commonService.getByProductModel(rpcHeader,projectId,productCode);
                // 应收毛利item
                BigDecimal grossProfitItem = salesOrderItem.getShouldReceiveGrossProfit();
                // 当毛利点位大于 0 的时候 产生毛利
                int point = productModelResp.getProductBusiness().getGrossProfitValue();
                if( point > 0){
                    // 毛利点位的价格
                    BigDecimal itemProfit = purchasingPrice.multiply(new BigDecimal(point));
                    // 除以1000000 保留六位小数
                    itemProfit = BigDecimalUtil.keepSixBits(itemProfit);
                    // 毛利点位与数量相乘 单个Item毛利点位相加
                    grossProfitItem =grossProfitItem.add(BigDecimalUtil.multiplication(itemProfit,itemQuantity));
                    // 总的Order里的毛利点位相加
                    grossProfit = grossProfit.add(grossProfitItem);
                }
                // 实收毛利Item
                BigDecimal receivedGrossProfit = salesOrderItem.getReceivedGrossProfit();
                // 比较是否小于0
                if(difference.compareTo(BigDecimal.ZERO) < 0){
                    // 毛利差价
                    BigDecimal differenceGross = purchasingPrice.subtract(jdPrice);
                    // 毛利差价 与 数量的乘积
                    BigDecimal totalPrice =BigDecimalUtil.multiplication(differenceGross,itemQuantity);
                    // 实收毛利Item改变
                    receivedGrossProfit = receivedGrossProfit.add(totalPrice);
                    // 实收毛利Order
                    actualGrossProfit = actualGrossProfit.add(totalPrice);
                }
                itemQuantity += salesOrderItem.getInTransitQuantity();
                update = salesOrderItemDao.updateDeliveryAndInTransitQuantity(prefix,
                        salesOrderItem.getSalesOrderItemId(), salesOrderItem.getDataVersion(),
                        salesOrderItem.getDeliveredQuantity(), itemQuantity,itemActingMat,grossProfitItem,receivedGrossProfit);
                if(update != 1){
                    logger.error("FAILED to update salesOrderItem updateDeliveryAndInTransitQuantity. salesOrderItemId={}", salesOrderItem.getSalesOrderItemId());
                }
            }
            // 更新主单
            while (maxRetryTimes-- > 0) {
                salesOrder = salesOrderDao.getSalesOrderByOrderNo(prefix, salesOrderNo);
                List<String> onGoingOutboundOrderList = JSON.parseArray(salesOrder.getOngoingOutboundOrderInfo(), String.class);
                List<String> finishedOutboundOrderList = JSON.parseArray(salesOrder.getFinishedOutboundOrderInfo(), String.class);
                onGoingOutboundOrderList.remove(outboundOrderNo);
                finishedOutboundOrderList.add(outboundOrderNo);
                String onGoingOutboundOrderInfo = JSON.toJSONString(onGoingOutboundOrderList);
                String finishedOutboundOrderInfo = JSON.toJSONString(finishedOutboundOrderList);
                int transitQuantity = salesOrder.getInTransitQuantity() + totalQuantity;
                TraceLog traceLog2 = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "出库");
                String tracelog = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog2);
                int salesOrderStatus = salesOrder.getSalesOrderStatus();
                Date outboundTime = null;
                //if 在途数量 = 总数量 ，订单状态 为 “已出库”，设置出库时间
                if (salesOrder.getUnhandledQuantity() == 0 && onGoingOutboundOrderList.size() == 0) {
                    salesOrderStatus = SalesOrderStatusEnum.OUTBOUND.getStatus();
                    outboundTime = new Date();
                    //增加操作日志
                    traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "出库完成");
                    tracelog = TraceLogUtil.appendTraceLog(tracelog, traceLog);
                }
                update = salesOrderDao.updateWhenOutbound(prefix, salesOrder.getSalesOrderId(),
                        salesOrder.getDataVersion(), salesOrderStatus, outboundTime
                        , onGoingOutboundOrderInfo, finishedOutboundOrderInfo, transitQuantity, tracelog,
                        salesOrder.getTotalGeneratedPrepaid().add(actingMat),
                        salesOrder.getShouldReceiveGrossProfit().add(grossProfit),
                        salesOrder.getReceivedGrossProfit().add(actualGrossProfit));
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update salesOrder. salesOrderNo={}", salesOrderNo);
            }

            logger.info("#getTraceId()={}# [OUT]: update order info success", rpcHeader.getTraceId());
        }catch (Exception e){
            logger.error("#getTraceId()=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 获取商品下可以选批次
     *
     * @author weizecheng
     * @date 2018/8/29 17:12
     */
    @Override
    public void getSaleScheduleProductList(SalesOrderDeliveryServiceStructure.SaleScheduleProductListRequest request, StreamObserver<SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse> responseObserver) {
        SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse rpcResponse;
        SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse.Builder builder = SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcheader();
        String salesOrderNo = request.getSalesOrderNo();
        long projectId = request.getProjectId();
        try {
            logger.info("#getTraceId()={}# [IN][selectSaleScheduleProductList] params: salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            //查询销售商品信息
            List<SalesOrderItem> list = salesOrderItemDao.listBySalesOrderNo(prefix, salesOrderNo);
            for (SalesOrderItem item : list) {
                if (item.getUnhandledQuantity() == 0) {
                    continue;
                }
                SalesOrderDeliveryServiceStructure.ScheduleItems.Builder protoObject = SalesOrderDeliveryServiceStructure.ScheduleItems.newBuilder();
                protoObject.setProductName(item.getProductName());
                protoObject.setProductCode(item.getProductCode());
                protoObject.setOrderTotalQuantity(item.getTotalQuantity());
                protoObject.setOrderUnhandledQuantity(item.getUnhandledQuantity());
                builder.addList(protoObject);
            }
            logger.info("#getTraceId()={}# [OUT]: get schedule order list success", rpcHeader.getTraceId());
            rpcResponse = builder.build();
            responseObserver.onNext(rpcResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#getTraceId()=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 同步EAS
     *
     * @author weizecheng
     * @date 2018/8/29 17:13
     */
    @Override
    public void syncSalesOutboundOrderToEas(SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasRequest request, StreamObserver<SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse> responseObserver) {
        SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse response;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        Long projectId = request.getProjectId();
        List<SalesOrderDeliveryServiceStructure.EasOutboundItem> easOutboundItems = request.getEasOutboundItemsList();
        logger.info("#traceId={}# [IN][syncSalesOutboundOrderToEas] salesOrderNo={}, easOutboundItems.size={} ", rpcHeader.getTraceId(), salesOrderNo, easOutboundItems.size());
        EasOutboundOrderRequest easOutboundOrderRequest = new EasOutboundOrderRequest();
        try {
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            SalesOrder salesOrder = salesOrderDao.getSalesOrderByOrderNo(prefix, salesOrderNo);
            if (salesOrder.getEasOrderId() == null) {
                logger.info("#traceId={}# [OUT]: no EAS salesOrder id", rpcHeader.getTraceId());
                easOutboundOrderRequest.setErrorCode(ErrorCode.EAS_ID_EMPTY.getErrorCode());
                response = SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse.newBuilder().setJson(JSON.toJSONString(easOutboundOrderRequest)).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            //项目rpc
            ProjectStructure.Project project =commonService.getByProjectIdResp(rpcHeader,projectId.toString());
            // rpc distributor
            DistributorStructure.GetDistributorBusinessByIdReq getDistributorBasicByIdReq = DistributorStructure.GetDistributorBusinessByIdReq.newBuilder().setRpcHeader(rpcHeader).setDistributorBusinessId(salesOrder.getDistributorId()).build();
            DistributorServiceGrpc.DistributorServiceBlockingStub distributorService = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.GetDistributorBusinessByIdResp distributor = distributorService.getDistributorBusinessById(getDistributorBasicByIdReq);
            ArrayList<SaleOutOrderItem> easSaleOutItemList = new ArrayList<>(easOutboundItems.size());
            double totalAmount = 0;
            int totalQuantity = 0;
            for (SalesOrderDeliveryServiceStructure.EasOutboundItem item : easOutboundItems) {
                SaleOutOrderItem easItem = new SaleOutOrderItem();
                SalesOrderItem salesOrderItem = salesOrderItemDao.getBySalesOrderNoAndProductCode(prefix, salesOrder.getSalesOrderNo(), item.getProductCode());
                String productCode = salesOrderItem.getProductCode();
                easItem.setTaxRate(FXConstant.TAX_RATE);
                // TODO 折扣设置为 0;
                easItem.setDiscount(0);
                int quantity = item.getQuantity();
                totalQuantity += quantity;
                easItem.setNumber(quantity);
                // TODO 使用京东采购价
                double wholesalePrice = salesOrderItem.getJdPurchaseGuidePrice().doubleValue();
                easItem.setTaxPrice(wholesalePrice);
                totalAmount += wholesalePrice * quantity;
                String warehouseId = item.getWarehouseId();
                //仓库rpc
                WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
                WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(warehouseId).build();
                WarehouseStructure.GetWarehouseByIdResp warehouse = warehouseService.getWarehouseById(getWarehouseByIdReq);
                easItem.setWarehouseId(warehouse.getWarehouse().getEasWarehouseCode());
                if (item.getGoodProduct()) {
                    // 良品
                    easItem.setLocationId("01");
                } else {
                    // 不良品
                    easItem.setLocationId("02");
                }
                if (item.getGift()) {
                    // 赠品
                    easItem.setLocationId("05");
                }
                easItem.setLot(item.getBatchNo());
                easItem.setSourceBillId(salesOrder.getEasOrderId());
                easItem.setSourceOrderNumber(salesOrder.getEasOrderNo());
                easItem.setSourceItemBillId(salesOrderItem.getEntryId());
                //货品rpc
                ProductStructure.GetByProductModelResp productBasic = commonService.getByProductModel(rpcHeader,projectId,productCode);

                easItem.setUnit(productBasic.getProductBusiness().getEasUnitCode());
                easItem.setMaterialId(productBasic.getProductBusiness().getEasCode());
                easItem.setCustomerId(distributor.getDistributorBusiness().getDistributorEasCode());
                easSaleOutItemList.add(easItem);
            }
            SaleOutOrder easSaleOutOrder = new SaleOutOrder();
            easSaleOutOrder.setProjectId(project.getEasProjectCode());
            easSaleOutOrder.setCustomerId(distributor.getDistributorBusiness().getDistributorEasCode());
            easSaleOutOrder.setCurrencyId("BB01");
            easSaleOutOrder.setTotalTaxAmount(totalAmount);
            //税额
            double percent = FXConstant.TAX_RATE / 100;
            double totalTax = totalAmount * (percent / (1 + percent));
            easSaleOutOrder.setTaxAmount(totalTax);
            easSaleOutOrder.setTotalQuantity(totalQuantity);
            easSaleOutOrder.setNumber(totalQuantity);
            easSaleOutOrder.setId(salesOrder.getEasOrderId());
            easSaleOutOrder.setEasSalesOrderNumber(salesOrder.getEasOrderNo());
            //回调
            easOutboundOrderRequest.setEasSalesOutboundOrder(easSaleOutOrder);
            easOutboundOrderRequest.setEasSalesOutboundItems(easSaleOutItemList);

            logger.info("#traceId={}# [OUT]: sync finished.", rpcHeader.getTraceId());
            response = SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse.newBuilder().setJson(JSON.toJSONString(easOutboundOrderRequest)).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
    /**
     * 签收 完成 扣钱
     *
     * @author weizecheng
     * @date 2018/8/29 17:14
     */
    @Override
    public void outboundSigned(SalesOrderDeliveryServiceStructure.OutboundSignedRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeadser();
        String outboundOrderNo = request.getOutboundOrderNo();
        String tmsOrderNo = request.getTmsOrderNo();
        String tmsRemark = request.getTmsRemark();
        String signedBy = request.getSignedBy();
        String postedBy = request.getPostedBy();
        String signedPhone = request.getSignedPhone();
        long signedTime = request.getSignedTime();
        String transporter = request.getTransporter();
        long projectId = request.getProjectId();
        try {
            logger.info("#getTraceId()={}# [IN][salesOutboundSigned]: outboundOrderNo={}, tmsOrderNo={}, tmsRemark={}, signedBy={}, postedBy={}, signedPhone={}, signedTime={}, transporter={}",
                    rpcHeader.getTraceId(), outboundOrderNo, tmsOrderNo, tmsRemark, signedBy, postedBy, signedPhone, signedTime, transporter);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            int update;
            SalesOutboundOrder outboundOrder = null;
            int maxRetryTimes = 6;
            boolean firstTime = false;
            //一、查找出库单,修改状态
            while (maxRetryTimes-- > 0) {
                outboundOrder = salesOutboundOrderDao.getByOutboundOrderNo(prefix, outboundOrderNo);
                int orderStatus = SalesOutboundOrderStatusEnum.OUTBOUND_SIGNED.getStatus();
                firstTime = (outboundOrder.getTmsSignedTime() == null);
                update = salesOutboundOrderDao.updateWhenSigned(prefix, outboundOrder.getOid(), outboundOrder.getDataVersion(),
                        orderStatus, GrpcCommonUtil.getJavaParam(tmsOrderNo),
                        GrpcCommonUtil.getJavaParam(tmsRemark), GrpcCommonUtil.getJavaParam(signedBy),
                        GrpcCommonUtil.getJavaParam(postedBy), GrpcCommonUtil.getJavaParam(signedTime),
                        GrpcCommonUtil.getJavaParam(signedPhone), GrpcCommonUtil.getJavaParam(transporter));
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("update outboundOrder FAILED. outboundOrderNo={}", outboundOrderNo);
                throw new RuntimeException("update outboundOrder FAILED!");
            }

            // 如果已经签收过了,只用修改tms推回的收件人信息即可
            if (!firstTime) {
                logger.info("#getTraceId()={}# [OUT]: already signed. update TMS info success.", rpcHeader.getTraceId());
                response = GrpcCommonUtil.success();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            String salesOrderNo = outboundOrder.getSalesOrderNo();

            //更新销售单明细
            String items = outboundOrder.getItems();
            List<OutboundOrderItem> outboundOrderItems = JSON.parseArray(items, OutboundOrderItem.class);
            for (OutboundOrderItem outboundOrderItem : outboundOrderItems) {
                String productCode = outboundOrderItem.getProductCode();
                SalesOrderItem salesOrderItem = salesOrderItemDao.getBySalesOrderNoAndProductCode(prefix, salesOrderNo, productCode);
                int outboundQuantity = outboundOrderItem.getTotalQuantity();
                int deliveredQuantity = salesOrderItem.getDeliveredQuantity() + outboundQuantity;
                int inTransitQuantity = salesOrderItem.getInTransitQuantity() - outboundQuantity;
                update = salesOrderItemDao.updateDeliveryAndInTransitQuantitySign(prefix, salesOrderItem.getSalesOrderItemId(), salesOrderItem.getDataVersion(), deliveredQuantity, inTransitQuantity);
                if(update != 1){
                    logger.error("FAILED to update salesOrderItem updateDeliveryAndInTransitQuantity. salesOrderItemId={}", salesOrderItem.getSalesOrderItemId());
                }
            }

            //三、修改销售单内的“出库单状态”、“送达数量”
            SalesOrder salesOrder = null;
            maxRetryTimes = 6;
            while (maxRetryTimes-- > 0) {
                salesOrder = salesOrderDao.getSalesOrderByOrderNo(prefix, salesOrderNo);
                int deliveredQuantity = salesOrder.getDeliveredQuantity();
                Integer totalQuantity = outboundOrder.getTotalQuantity();
                int inTransitQuantity = salesOrder.getInTransitQuantity();
                deliveredQuantity += totalQuantity;
                inTransitQuantity -= totalQuantity;
                salesOrder.setDeliveredQuantity(deliveredQuantity);
                salesOrder.setInTransitQuantity(inTransitQuantity);
                int salesOrderStatus = salesOrder.getSalesOrderStatus();
                Date signTime = null;
                String tracelog = salesOrder.getTracelog();
                if (salesOrder.getTotalQuantity().equals(salesOrder.getDeliveredQuantity())) {
                    //如果订单总数量==送达总数量，修改订单状态为“已签收”,产生应收代垫，产生低买高卖账户金额
                    salesOrderStatus = SalesOrderStatusEnum.SIGNED.getStatus();
                    signTime = new Date();
                    //增加操作日志
                    TraceLog traceLog2 = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "签收完成");
                    tracelog = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog2);
                }
                update = salesOrderDao.updateWhenOutboundSign(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(),salesOrderStatus,signTime, deliveredQuantity, inTransitQuantity, tracelog);
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("update salesOrder FAILED! salesOrderNo={}", salesOrderNo);
            }

            //前往支付模块修改金额相关
            if (salesOrder.getTotalQuantity().equals(salesOrder.getDeliveredQuantity())) {
                // 根据channelId查询channelToken
                String channelId = salesConfig.getChannelId();
                //调用基础模块的SourceChannel服务
                ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel =commonService.getChannelByChannelIdResp(rpcHeader,channelId);
                String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

                //如果供应商代垫金额》0，产生应收代垫
                BigDecimal generatedPrepaid = BigDecimalUtil.multiplication(salesOrder.getTotalGeneratedPrepaid());

                if (generatedPrepaid.longValue() > 0) {
                    GongxiaoResult gongxiaoResult = XpsPrepaidManager.generateYhglobalPrepaidLedger(salesConfig.getPrepaidUrl(),
                            channelId, xpsChannelSecret, rpcHeader.getUid(), rpcHeader.getUsername(),
                            projectId, salesOrder.getCurrencyCode(), salesOrder.getSalesOrderNo(), salesOrder.getProjectName(),
                            salesOrder.getSupplierId().intValue(), salesOrder.getSupplierName(), salesOrder.getSalesContactNo(),
                            generatedPrepaid.longValue(),outboundOrder.getOutboundFinishedTime());
                    if (gongxiaoResult.getReturnCode() != 0) {
                        logger.error("add prepaid FAILED! salesOrderNo={}, amount(long)={}", salesOrderNo, generatedPrepaid);
                    }
                }

//                产生低买高卖金额,去账户转入金额
//                Long sellHighAmount = salesOrder.getSellHighAmount();
//                if (sellHighAmount > 0) {
//                    SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest rpcRequest = SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest.newBuilder()
//                            .setRpcHeader(rpcHeader)
//                            .setCurrencyCode(salesOrder.getCurrencyCode())
//                            .setProjectId(projectId)
//                            .setAmount(sellHighAmount)
//                            .setSalesOrderNo(salesOrderNo)
//                            .setTransactionTime(System.currentTimeMillis()).build();
//                    SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
//                    GongxiaoRpc.RpcResult rpcResponse = rpcStub.salesUpdateSellHighAccount(rpcRequest);
//                    if (rpcResponse.getReturnCode() != 0) {
//                        logger.error("update sellHighAmount FAILED! salesOrderNo={}, amount(long)={}", salesOrderNo, sellHighAmount);
//                    }
//                }
//
//                如果存在 待确认金额为-值  ，将金额自动转入ad现金账户
//                CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest rpcRequest = CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest.newBuilder()
//                        .setProjectId(projectId)
//                        .setRpcHeader(rpcHeader).setSalesOrderNo(salesOrderNo).build();
//                CashConfirmServiceGrpc.CashConfirmServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(CashConfirmServiceGrpc.CashConfirmServiceBlockingStub.class);
//                GongxiaoRpc.RpcResult rpcResponse = rpcStub.confirmNegativeAmountAutomatically(rpcRequest);
//                if (rpcResponse.getReturnCode() != ErrorCode.SUCCESS.getErrorCode()) {
//                    logger.error("update cashConfirm FAILED! salesOrderNo={}", salesOrderNo);
//                }

                // 如果产生了应收毛利
                long shouldReceiveGrossProfit = BigDecimalUtil.multiplication(salesOrder.getShouldReceiveGrossProfit()).longValue();
                long receivedGrossProfit =  BigDecimalUtil.multiplication(salesOrder.getReceivedGrossProfit()).longValue();
                if (shouldReceiveGrossProfit > 0) {
                    GongxiaoResult gongxiaoResult = XpsGrossProfitManager.generateYhglobalGrossProfit(salesConfig.getGrossProfitUrl(),
                            channelId,
                            xpsChannelSecret,
                            rpcHeader.getUid(),
                            rpcHeader.getUsername(),
                            projectId, salesOrder.getProjectName(), salesOrder.getCurrencyCode(),
                            salesOrder.getSalesOrderNo(),
                            shouldReceiveGrossProfit,
                            salesOrder.getSalesOrderNo(),
                            new Date(),
                            receivedGrossProfit,
                            prefix
                    );
                    if (gongxiaoResult.getReturnCode() != ErrorCode.SUCCESS.getErrorCode()) {
                        logger.error("update should received gross profit FAILED! salesOrderNo={}, shouldReceiveGrossProfit={}, receivedGrossProfit={}"
                                , salesOrderNo, shouldReceiveGrossProfit, receivedGrossProfit);
                    }

                }
            }
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#getTraceId()=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
