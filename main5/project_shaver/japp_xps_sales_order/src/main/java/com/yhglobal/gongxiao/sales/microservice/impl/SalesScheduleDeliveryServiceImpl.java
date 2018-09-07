package com.yhglobal.gongxiao.sales.microservice.impl;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.XpsGrossProfitManager;
import com.yhglobal.gongxiao.XpsPrepaidManager;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.sales.SalesOrderStatusEnum;
import com.yhglobal.gongxiao.eas.model.SaleOutOrder;
import com.yhglobal.gongxiao.eas.model.SaleOutOrderItem;
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
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure;
import com.yhglobal.gongxiao.payment.microservice.CashConfirmServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure;
import com.yhglobal.gongxiao.plansales.dao.SalePlanItemDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderItemDao;
import com.yhglobal.gongxiao.sales.dao.SalesOutboundOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesOutboundOrderItemDao;
import com.yhglobal.gongxiao.sales.dao.SalesScheduleDeliveryDao;
import com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure;
import com.yhglobal.gongxiao.sales.microservice.SalesScheduleDeliveryServiceGrpc;
import com.yhglobal.gongxiao.sales.model.SalesConfig;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import com.yhglobal.gongxiao.sales.model.SalesOutboundOrder;
import com.yhglobal.gongxiao.sales.model.SalesOutboundOrderItem;
import com.yhglobal.gongxiao.sales.model.SalesScheduleDelivery;
import com.yhglobal.gongxiao.sales.model.dto.EasOutboundOrderRequest;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.transportataion.sendtotransportation.XpsTransportationManager;
import com.yhglobal.gongxiao.type.WmsOrderType;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehouseapi.model.PlanOutboundOrderItem;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yhglobal.gongxiao.constant.ErrorCode.EAS_ID_EMPTY;
import static com.yhglobal.gongxiao.constant.ErrorCode.ORDER_STATUS_CAN_NOT_MODIFY;
import static com.yhglobal.gongxiao.constant.ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER;
import static com.yhglobal.gongxiao.constant.FXConstant.MILLION;
import static com.yhglobal.gongxiao.constant.FXConstant.MILLION_DOUBLE;
import static com.yhglobal.gongxiao.constant.FXConstant.TAX_RATE;
import static com.yhglobal.gongxiao.constant.sales.SalesOrderStatusEnum.INFORMED;
import static com.yhglobal.gongxiao.constant.sales.SalesOrderStatusEnum.OUTBOUND;
import static com.yhglobal.gongxiao.constant.sales.SalesOrderStatusEnum.SIGNED;
import static com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum.UNHANDLED;
import static com.yhglobal.gongxiao.constant.sales.SalesOutboundOrderStatusEnum.IN_OUTBOUND;
import static com.yhglobal.gongxiao.constant.sales.SalesOutboundOrderStatusEnum.OUTBOUND_FINISHED;
import static com.yhglobal.gongxiao.constant.sales.SalesOutboundOrderStatusEnum.OUTBOUND_SIGNED;
import static com.yhglobal.gongxiao.id.BizNumberType.SALES_SCHEDULE_ORDER_NO;

/**
 * @author 葛灿
 */
@Service
public class SalesScheduleDeliveryServiceImpl extends SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(SalesScheduleDeliveryServiceImpl.class);

    @Autowired
    private SalesScheduleDeliveryDao salesScheduleDeliveryDao;
    @Autowired
    private SalesOrderItemDao salesOrderItemDao;
    @Autowired
    private SalesOrderDao salesOrderDao;
    @Autowired
    private SalesOutboundOrderDao salesOutboundOrderDao;
    @Autowired
    private SalesConfig salesConfig;
    @Autowired
    private SalePlanItemDao salePlanItemDao;
    @Autowired
    private SalesOutboundOrderItemDao salesOutboundOrderItemDao;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolExecutor;


    @Override
    public void createScheduleOrder(DeliverScheduleStructure.CreateScheduleOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        long arrivalDate = request.getArrivalDate();
        long projectId = request.getProjectId();
        List<DeliverScheduleStructure.ScheduleItems> productInfo = request.getProductInfoList();
        try {
            logger.info("#getTraceId()={}# [IN][createScheduleOrder] params: salesOrderNo={},warehouseId={},warehouseName={}," +
                    " productInfo={}", rpcHeader.getTraceId(), salesOrderNo, productInfo);
            // 0.查询表前缀 
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            HashMap<String, Integer> countMap = new HashMap<>();
            for (DeliverScheduleStructure.ScheduleItems inventoryBatch : productInfo) {
                String productCode = inventoryBatch.getProductCode();
                int scheduleQuantity = inventoryBatch.getScheduleQuantity();
                if (countMap.get(productCode) == null) {
                    countMap.put(productCode, scheduleQuantity);
                } else {
                    countMap.put(productCode, countMap.get(productCode) + scheduleQuantity);
                }
            }
            //数据校验
            for (String productCode : countMap.keySet()) {
                SalesOrderItem item = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, salesOrderNo, productCode);
                int unhandledQuantity = item.getUnhandledQuantity();
                Integer scheduleQuantity = countMap.get(productCode);
                if (scheduleQuantity > unhandledQuantity) {
                    //如果数据版本不一致
                    logger.info("#getTraceId()={}# [OUT]: schedule FAILED !", rpcHeader.getTraceId());
                    response = GrpcCommonUtil.fail(OVERTAKE_MAX_ALLOCATE_NUMBER);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }
            }

            //一、通知仓库
            int totalQuantity = 0;
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
            ArrayList<PlanOutboundOrderItem> planOutboundOrderItems = new ArrayList<>();
            //转换模型(数据校验)
            for (DeliverScheduleStructure.ScheduleItems record : productInfo) {
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
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, salesOrderNo, productCode);
                planOutboundOrderItem.setWholesalePrice(salesOrderItem.getWholesalePrice());
                planOutboundOrderItem.setSalesGuidePrice(salesOrderItem.getSalesGuidePrice());
                planOutboundOrderItems.add(planOutboundOrderItem);
                if (record.getScheduleQuantity() > record.getOrderUnhandledQuantity() || record.getScheduleQuantity() > record.getInventoryBatchAmount()) {
                    //如果 预约数量>未发货数量 || 预约数量>库存数量 ,预约失败
                    logger.info("#getTraceId()={}# [OUT]: schedule FAILED !", rpcHeader.getTraceId());
                    response = GrpcCommonUtil.fail(OVERTAKE_MAX_ALLOCATE_NUMBER.getErrorCode(), ORDER_STATUS_CAN_NOT_MODIFY.getMessage());
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }
            }
            // 根据channelId查询channelToken
            String channelId = salesConfig.getChannelId();
            //调用基础模块的SourceChannel服务
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(channelId + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            // 唯一号
            String saleUniqueNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.SALE_UNIQUE_NO);
            // 返回<出库单号,出库单商品明细>
            Map<String, List<OutboundOrderItem>> outboundMap = XpsWarehouseManager.SalesCreateOutboundOrder2(salesConfig.getWarehouseUrl(),
                    salesConfig.getChannelId(), xpsChannelSecret,
                    salesOrder.getMarketingChannel() + "", WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode(),
                    salesOrder.getProjectId() + "",
                    salesOrder.getDistributorId() + "",
                    salesOrderNo,
                    salesOrder.getRecipientName(),
                    salesOrder.getRecipientMobile(),
                    salesOrder.getShippingAddress(),
                    salesOrder.getProvinceName(),
                    salesOrder.getCityName(),
                    salesOrder.getShippingMode(), "logisticsCompanyName", "logisticsNo", "note", totalQuantity,
                    planOutboundOrderItems, "sales", saleUniqueNo, new Date(arrivalDate),
                    Long.parseLong(rpcHeader.getUid()), rpcHeader.getUsername()
            );
            // 如果仓库模块返回结果异常,停止操作
            if (outboundMap == null) {
                throw new RuntimeException("Warehouse Unknown Exception");
            }

            //二、新建预约出库记录
            String schduleOrderNo = DateTimeIdGenerator.nextId(prefix, SALES_SCHEDULE_ORDER_NO);
            SalesScheduleDelivery salesScheduleDelivery = new SalesScheduleDelivery();
            //创建预约发货单号
            salesScheduleDelivery.setScheduleOrderNo(schduleOrderNo);
            //填充字段
            salesScheduleDelivery.setUniqueNo(saleUniqueNo);
            salesScheduleDelivery.setScheduleStatus(0);
            salesScheduleDelivery.setSyncToGongxiaoWarehouseFlag(false);
            salesScheduleDelivery.setSalesOrderId(salesOrderNo);
            salesScheduleDelivery.setShippingMode(0);
            salesScheduleDelivery.setDataVersion(0L);
            salesScheduleDelivery.setTotalQuantity(totalQuantity);
            salesScheduleDelivery.setOnGoingOutboundOrderInfo(JSON.toJSONString(outboundMap.keySet()));
            salesScheduleDelivery.setOutboundedOrderInfo("[]");
            salesScheduleDelivery.setSignedOrderInfo("[]");
            TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "新建");
            ArrayList<TraceLog> traceLogs = new ArrayList<>();
            traceLogs.add(traceLog);
            salesScheduleDelivery.setTracelog(JSON.toJSONString(traceLogs));
            salesScheduleDelivery.setCreateTime(new Date());
            //插入数据库
//            String jsonString = JSON.toJSONString(productInfo);
            salesScheduleDelivery.setProductInfo(productInfo.toString());
            int insert = salesScheduleDeliveryDao.insert(prefix, salesScheduleDelivery);

            //插入出库单记录
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
                salesOutboundOrder.setOrderStatus(IN_OUTBOUND.getStatus());
                salesOutboundOrder.setExpectedArrivalTime(new Date(arrivalDate));
                salesOutboundOrder.setItems(JSON.toJSONString(outboundItems));
                salesOutboundOrder.setCreatedById(rpcHeader.getUid());
                salesOutboundOrder.setCreatedByName(rpcHeader.getUsername());
                salesOutboundOrderDao.insert(prefix, salesOutboundOrder);

                Long distributorId = salesOrder.getDistributorId();
                for (OutboundOrderItem item : outboundItems) {
                    int itemTotalQuantity = item.getTotalQuantity();
                    String productCode = item.getProductCode();
                    long purchasePrice = item.getPurchasePrice();
                    // 从预售获取商品信息
                    SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, salesOrderNo, productCode);
                    Date createTime = salesOrderItem.getCreateTime();
                    SalesPlanItem salesPlanItem = salePlanItemDao.getItemByProductModel(prefix, distributorId + "", productCode, createTime);
                    long salesGuidePrice = salesPlanItem.getSaleGuidePrice();
                    //出货价 毛利点位 应收差价 应收代垫 应收毛利 实收毛利
                    long wholesalePrice = salesPlanItem.getWholesalePrice();
                    long grossProfitValue = salesPlanItem.getGrossProfitValue();
                    long sellHighAmount = 0L;
                    long shouldReceivedPrepaid = 0L;
                    long shouldReceivedGrossProfit = 0L;
                    long receivedGrossProfit = 0L;
                    // 毛利点位 = 0
                    if (grossProfitValue == 0) {
                        //销售成本价 = 采购价*(1-YH毛利支持折扣)
                        double percent = 1.0 * salesPlanItem.getYhPrepaidDiscount() / MILLION;
                        long salesCostPrice = Math.round(purchasePrice * (1 - percent));
                        //当【出货价-销售成本价>0】时，就是高卖，生成销售差价，乘以数量后的金额到销售差价预留账户中。
                        //当【出货价-销售成本价<0】时，就是低卖，生成应收代垫，乘以数量后的金额到代垫确认页面生成应收代垫。
                        if (wholesalePrice - salesCostPrice > 0) {
                            //产生差价
                            sellHighAmount = wholesalePrice - salesCostPrice;
                        } else if (wholesalePrice - salesCostPrice < 0) {
                            //产生代垫
                            shouldReceivedPrepaid = Math.abs(wholesalePrice - salesCostPrice);
                        }
                    } else {
                        // 应收毛利 = 采购价*毛利点位
                        shouldReceivedGrossProfit = Math.round(purchasePrice * grossProfitValue / MILLION_DOUBLE);
                        // 出货价-采购价*(1+毛利点位) >0 ,差值为销售差价, 实收毛利=采购价*毛利点位
                        if (wholesalePrice - salesGuidePrice - shouldReceivedGrossProfit > 0) {
                            sellHighAmount = wholesalePrice - salesGuidePrice;
                            receivedGrossProfit = Math.round(purchasePrice * grossProfitValue / MILLION_DOUBLE);
                        }
                        // 采购价 - 出货价 > 0, 差值为应收代垫, 实收毛利 = 0
                        else if (purchasePrice - wholesalePrice > 0) {
                            shouldReceivedPrepaid = purchasePrice - wholesalePrice;
                            receivedGrossProfit = 0L;
                        }
                        // 采购指导价 < 出货价 < 销售指导价, 实收毛利 = 出货价 - 采购指导价
                        else if (purchasePrice < wholesalePrice && wholesalePrice < salesGuidePrice + shouldReceivedGrossProfit) {
                            receivedGrossProfit = wholesalePrice - purchasePrice;
                        }
                    }
                    salesOutboundOrderItemDao.insert(prefix, productCode, outboundOrderNo, salesOrderNo,
                            itemTotalQuantity, grossProfitValue, wholesalePrice, salesGuidePrice, purchasePrice,
                            salesPlanItem.getYhPrepaidDiscount(), sellHighAmount, shouldReceivedPrepaid, shouldReceivedGrossProfit, receivedGrossProfit);
                }
            }


            //三、更新销售单商品明细
            for (DeliverScheduleStructure.ScheduleItems record : productInfo) {
                //每条明细的出库单信息，添加预约发货单号
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, salesOrderNo, record.getProductCode());
                int unhandledQuantity = salesOrderItem.getUnhandledQuantity() - record.getScheduleQuantity();
                salesOrderItem.setUnhandledQuantity(unhandledQuantity);
//                String ongoingOutboundOrderInfo = salesOrderItem.getOngoingOutboundOrderInfo();
//                List<String> itemOutboundOrderIno = JSON.parseArray(ongoingOutboundOrderInfo, String.class);
//                itemOutboundOrderIno.addAll(outboundMap.keySet());
//                salesOrderItem.setOngoingOutboundOrderInfo(JSON.toJSONString(itemOutboundOrderIno));
                int update = salesOrderItemDao.updateUnhandledQuantity(prefix, salesOrderItem.getSalesOrderItemId(), salesOrderItem.getDataVersion(), unhandledQuantity);
                if (update != 1) {
                    logger.error("FAILED to update salesOrder item quantity. salesOrderNo={}, productCode={}, unhandledQuantity={}", salesOrderNo, salesOrderItem.getProductCode(), unhandledQuantity);
                }
            }

            //四、更新销售单信息
            // 如果是销售单第一次发货，更改状态为待同步eas
            int syncEasStatus = salesOrder.getSalesOrderStatus();
            if (salesOrder.getUnhandledQuantity() == salesOrder.getTotalQuantity()) {
                syncEasStatus = UNHANDLED.getStatus();
            }
            //如果销售单商品全部发货，修改状态为“已通知仓库”
            int totalUnhandledQuantity = salesOrder.getUnhandledQuantity() - totalQuantity;
            int salesOrderStatus = salesOrder.getSalesOrderStatus();
            Date informWarehouseTime = null;
            if (totalUnhandledQuantity == 0) {
                salesOrderStatus = INFORMED.getStatus();
                informWarehouseTime = new Date();
            }
            String ongoingOutboundOrderInfo = salesOrder.getOngoingOutboundOrderInfo();
            //出库单json中，添加预约发货单号
            List<String> originalScheduleOrderNoList = JSON.parseArray(ongoingOutboundOrderInfo, String.class);
            originalScheduleOrderNoList.addAll(outboundMap.keySet());
            String newOngoingOutboundOrderInfo = JSON.toJSONString(originalScheduleOrderNoList);
            //操作
            TraceLog traceLog2 = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "新建发货单");
            String tracelog = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog2);

            int update = salesOrderDao.updateWhenScheduleDelivery(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), salesOrderStatus, informWarehouseTime, syncEasStatus, totalUnhandledQuantity, newOngoingOutboundOrderInfo, tracelog);

            logger.info("#getTraceId()={}# [OUT]: create schedule order success", rpcHeader.getTraceId());
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#getTraceId()=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void cancelSalesOutboundOrder(DeliverScheduleStructure.OutboundNoRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        int update;
        String outboundOrderNo = request.getOutboundOrderNo();
        long projectId = request.getProjectId();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        try {
            logger.info("#traceId={}# [IN][cancelSalesOutboundOrder] outboundOrderNo={},  projectId={} ", rpcHeader.getTraceId(), outboundOrderNo, projectId);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            // 找到出库单,出库单明细
            SalesOutboundOrder salesOutboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(prefix, outboundOrderNo);
            int totalQuantity = salesOutboundOrder.getTotalQuantity();
            String salesOrderNo = salesOutboundOrder.getSalesOrderNo();
            String items = salesOutboundOrder.getItems();
            List<OutboundOrderItem> outboundOrderItems = JSON.parseArray(items, OutboundOrderItem.class);

            // 修改对应销售单明细中的信息
            for (OutboundOrderItem outboundOrderItem : outboundOrderItems) {
                String productCode = outboundOrderItem.getProductCode();
                SalesOrderItem salesOrderItem =
                        salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, salesOrderNo, productCode);
                int unhandledQuantity = salesOrderItem.getUnhandledQuantity() + outboundOrderItem.getTotalQuantity();
                update = salesOrderItemDao.updateUnhandledQuantity(prefix, salesOrderItem.getSalesOrderItemId(), salesOrderItem.getDataVersion(), unhandledQuantity);
            }

            // 修改销售单的信息(基于乐观锁最多重试6次)
            int maxRetryTimes = 6;
            boolean success = false;
            SalesOrder salesOrder = null;
            while (maxRetryTimes-- > 0) {
                salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
                int totalUnhandledQuantity = salesOrder.getUnhandledQuantity() + totalQuantity;
                // 如果是销售单出库被全部退回
                //, 修改状态为“已通知仓库”, 通知仓库时间为null
                int salesOrderStatus = salesOrder.getSalesOrderStatus();
                Date informWarehouseTime = salesOrder.getInformWarehouseTime();
                String ongoingOutboundOrderInfo = salesOrder.getOngoingOutboundOrderInfo();
                List<String> originalScheduleOrderNoList = JSON.parseArray(ongoingOutboundOrderInfo, String.class);
                originalScheduleOrderNoList.remove(outboundOrderNo);
                ongoingOutboundOrderInfo = JSON.toJSONString(originalScheduleOrderNoList);
                if (totalUnhandledQuantity == salesOrder.getTotalQuantity()) {
                    salesOrderStatus = SalesOrderStatusEnum.PAID.getStatus();
                    informWarehouseTime = null;
                }
                //操作
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消出库单");
                String tracelog = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                update = salesOrderDao.updateWhenScheduleDelivery(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), salesOrderStatus, informWarehouseTime, salesOrder.getSalesOrderStatus(), totalUnhandledQuantity, ongoingOutboundOrderInfo, tracelog);
                if (update == 1) {
                    success = true;
                    break;
                }
            }
            if (!success) {
                logger.error("FAILED to update sales order. id={}, dataVersion={}, salesOrderStatus={}, totalQuantity={}"
                        , salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), totalQuantity);
            }

            // 新起线程,同步TMS
            GongxiaoResult gongxiaoResult = XpsTransportationManager.cancelOutboundOrder(salesConfig.getTransportationUrl(), projectId, outboundOrderNo);
            GongxiaoRpc.RpcResult rpcResult = GrpcCommonUtil.fail(gongxiaoResult.getReturnCode(), gongxiaoResult.getMessage());
            responseObserver.onNext(rpcResult);
            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.error("#getTraceId()=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void selectSaleScheduleProductList(DeliverScheduleStructure.SelectSaleScheduleProductListRequest request, StreamObserver<DeliverScheduleStructure.SelectSaleScheduleProductListResponse> responseObserver) {
        DeliverScheduleStructure.SelectSaleScheduleProductListResponse rpcResponse;
        DeliverScheduleStructure.SelectSaleScheduleProductListResponse.Builder builder = DeliverScheduleStructure.SelectSaleScheduleProductListResponse.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcheader();
        String salesOrderNo = request.getSalesOrderNo();
        long projectId = request.getProjectId();
        try {
            logger.info("#getTraceId()={}# [IN][selectSaleScheduleProductList] params: salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            //查询销售商品信息
            List<SalesOrderItem> list = salesOrderItemDao.selectListBySalesOrderNo(prefix, salesOrderNo);
            for (SalesOrderItem item : list) {
                if (item.getUnhandledQuantity() == 0) {
                    continue;
                }
                DeliverScheduleStructure.ScheduleItems.Builder protoObject = DeliverScheduleStructure.ScheduleItems.newBuilder();
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

    @Override
    public void syncSalesOutboundOrderToEas(DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest request, StreamObserver<DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse> responseObserver) {
        DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse response;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        long projectId = request.getProjectId();
        List<DeliverScheduleStructure.EasOutboundItem> easOutboundItems = request.getEasOutboundItemsList();
        logger.info("#traceId={}# [IN][syncSalesOutboundOrderToEas] salesOrderNo={}, easOutboundItems.size={} ", rpcHeader.getTraceId(), salesOrderNo, easOutboundItems.size());
        EasOutboundOrderRequest easOutboundOrderRequest = new EasOutboundOrderRequest();
        try {
            // 0.查询表前缀 
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
            if (salesOrder.getEasOrderId() == null) {
                logger.info("#traceId={}# [OUT]: no EAS salesOrder id", rpcHeader.getTraceId());
                easOutboundOrderRequest.setErrorCode(EAS_ID_EMPTY.getErrorCode());
                response = DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse.newBuilder().setJsonStr(JSON.toJSONString(easOutboundOrderRequest)).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            //项目rpc
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq projectRequest = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId + "").build();
            ProjectStructure.GetByProjectIdResp project = projectService.getByProjectId(projectRequest);
            // rpc distributor
            DistributorStructure.GetDistributorBusinessByIdReq getDistributorBasicByIdReq = DistributorStructure.GetDistributorBusinessByIdReq.newBuilder().setRpcHeader(rpcHeader).setDistributorBusinessId(salesOrder.getDistributorId()).build();
            DistributorServiceGrpc.DistributorServiceBlockingStub distributorService = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.GetDistributorBusinessByIdResp distributor = distributorService.getDistributorBusinessById(getDistributorBasicByIdReq);

            ArrayList<SaleOutOrderItem> easSaleOutItemList = new ArrayList<>();
            double totalAmount = 0;
            int totalQuantity = 0;
            for (DeliverScheduleStructure.EasOutboundItem item : easOutboundItems) {

                SaleOutOrderItem easItem = new SaleOutOrderItem();
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, salesOrder.getSalesOrderNo(), item.getProductCode());
                String productCode = salesOrderItem.getProductCode();
                easItem.setTaxRate(TAX_RATE);
                easItem.setDiscount((salesOrderItem.getYhDiscountPercent() + salesOrderItem.getSupplierDiscountPercent()) / 10000.0);
                int quantity = item.getQuantity();
                totalQuantity += quantity;
                easItem.setNumber(quantity);
                double wholesalePrice = 1.0 * salesOrderItem.getSalesGuidePrice() / MILLION;
                easItem.setTaxPrice(wholesalePrice);
                totalAmount += wholesalePrice * quantity;
                String warehouseId = item.getWarehouseId();
                //仓库rpc
                WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
                WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(warehouseId).build();
                WarehouseStructure.GetWarehouseByIdResp warehouse = warehouseService.getWarehouseById(getWarehouseByIdReq);
                easItem.setWarehouseId(warehouse.getWarehouse().getEasWarehouseCode() + "");
                if (item.getGoodProduct()) {
                    easItem.setLocationId("01"); // 良品
                } else {
                    easItem.setLocationId("02"); // 不良品
                }
                if (item.getGift()) {
                    easItem.setLocationId("05"); // 赠品
                }
                easItem.setLot(item.getBatchNo());
                easItem.setSourceBillId(salesOrder.getEasOrderId());
                easItem.setSourceOrderNumber(salesOrder.getEasOrderNo());
                easItem.setSourceItemBillId(salesOrderItem.getEntryId());
                //货品rpc
                ProductServiceGrpc.ProductServiceBlockingStub productService = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                ProductStructure.GetByProductModelReq getByProductCodeReq = ProductStructure.GetByProductModelReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).setProductModel(productCode).build();
                ProductStructure.GetByProductModelResp productBasic = productService.getByProductModel(getByProductCodeReq);
                easItem.setUnit(productBasic.getProductBusiness().getEasUnitCode());
                easItem.setMaterialId(productBasic.getProductBusiness().getEasCode());
                easItem.setCustomerId(distributor.getDistributorBusiness().getDistributorEasCode());
                easSaleOutItemList.add(easItem);
            }
            SaleOutOrder easSaleOutOrder = new SaleOutOrder();
            easSaleOutOrder.setProjectId(project.getProject().getEasProjectCode());
            easSaleOutOrder.setCustomerId(distributor.getDistributorBusiness().getDistributorEasCode());
            easSaleOutOrder.setCurrencyId("BB01");
            easSaleOutOrder.setTotalTaxAmount(totalAmount);
            //税额
            double percent = TAX_RATE / 100;
            double totalTax = totalAmount * (percent / (1 + percent));
            easSaleOutOrder.setTaxAmount(totalTax);
            easSaleOutOrder.setTotalQuantity(totalQuantity);
            easSaleOutOrder.setNumber(totalQuantity);
            easSaleOutOrder.setId(salesOrder.getEasOrderId());
            easSaleOutOrder.setEasSalesOrderNumber(salesOrder.getEasOrderNo());
            easSaleOutOrder.setBusinessDate(salesOrder.getCreateTime());
            //回调
            easOutboundOrderRequest.setEasSalesOutboundOrder(easSaleOutOrder);
            easOutboundOrderRequest.setEasSalesOutboundItems(easSaleOutItemList);


            logger.info("#traceId={}# [OUT]: sync finished.", rpcHeader.getTraceId());
            response = DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse.newBuilder().setJsonStr(JSON.toJSONString(easOutboundOrderRequest)).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void submitOutboundOrderToTms(DeliverScheduleStructure.OutboundNoRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String outboundOrderNo = request.getOutboundOrderNo();
        long projectId = request.getProjectId();
        try {
            logger.info("#getTraceId()={}# [IN][submitOutboundOrderToTms]: outboundOrderNo={}", rpcHeader.getTraceId(), outboundOrderNo);
            // 0.查询表前缀 
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            //修改出库单的状态为 待同步tms
            int maxRetryTimes = 6;
            while (maxRetryTimes-- > 0) {
                SalesOutboundOrder salesOutboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(prefix, outboundOrderNo);
                int syncTms = UNHANDLED.getStatus();
                int update = salesOutboundOrderDao.updateSyncTmsStatus(prefix, salesOutboundOrder.getOid(), salesOutboundOrder.getDataVersion(), syncTms);
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update outboundOrder. outboundOrderNo={}", outboundOrderNo);
                throw new RuntimeException();
            }
            logger.info("#getTraceId()={}# [OUT]: update order info success", rpcHeader.getTraceId());
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#getTraceId()=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void itemsOutboundFinished(DeliverScheduleStructure.OutboundNoRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String outboundOrderNo = request.getOutboundOrderNo();
        long projectId = request.getProjectId();
        try {
            logger.info("#getTraceId()={}# [IN][outboundOrderFinished]: outboundOrderNo={}", rpcHeader.getTraceId(), outboundOrderNo);
            // 0.查询表前缀 
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            int update;
            int maxRetryTimes = 6;
            SalesOrder salesOrder = null;
            TraceLog traceLog;
            //一、查到出库单，找到对应的销售单明细、销售单
            //更新出库单
            SalesOutboundOrder outboundOrder = null;
            while (maxRetryTimes-- > 0) {
                outboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(prefix, outboundOrderNo);
                int outboundOrderStatus = OUTBOUND_FINISHED.getStatus();
                Date outboundTime = new Date();
                update = salesOutboundOrderDao.updateOutboundTime(prefix, outboundOrder.getOid(), outboundOrder.getDataVersion(), outboundOrderStatus, outboundTime);
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update outboundOrder. outboundOrderNo={}", outboundOrderNo);
                throw new RuntimeException("FAILED to update outboundOrder");
            }
            maxRetryTimes = 6;
            //更新销售单
            String salesOrderNo = outboundOrder.getSalesOrderNo();
            int totalQuantity = outboundOrder.getTotalQuantity();
            while (maxRetryTimes-- > 0) {
                salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
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
                    salesOrderStatus = OUTBOUND.getStatus();
                    outboundTime = new Date();
                    //增加操作日志
                    traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "出库完成");
                    tracelog = TraceLogUtil.appendTraceLog(tracelog, traceLog);
                }
                update = salesOrderDao.updateWhenOutbound(prefix, salesOrder.getSalesOrderId(),
                        salesOrder.getDataVersion(), salesOrderStatus, outboundTime
                        , onGoingOutboundOrderInfo, finishedOutboundOrderInfo, transitQuantity, tracelog);
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update salesOrder. salesOrderNo={}", salesOrderNo);
            }

            //更新销售单明细
            String items = outboundOrder.getItems();
            List<OutboundOrderItem> outboundOrderItems = JSON.parseArray(items, OutboundOrderItem.class);
            for (OutboundOrderItem outboundOrderItem : outboundOrderItems) {
                int itemQuantity = outboundOrderItem.getTotalQuantity();
                String productCode = outboundOrderItem.getProductCode();
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, salesOrderNo, productCode);
                itemQuantity += salesOrderItem.getInTransitQuantity();
                update = salesOrderItemDao.updateDeliveryAndInTransitQuantity(prefix,
                        salesOrderItem.getSalesOrderItemId(), salesOrderItem.getDataVersion(),
                        salesOrderItem.getDeliveredQuantity(), itemQuantity);
            }
            logger.info("#getTraceId()={}# [OUT]: update order info success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#getTraceId()=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void outboundSigned(DeliverScheduleStructure.OutboundSignedRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
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
                outboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(prefix, outboundOrderNo);
                int orderStatus = OUTBOUND_SIGNED.getStatus();
                firstTime = outboundOrder.getTmsSignedTime() == null;
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
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, salesOrderNo, productCode);
                //如果货品编码一致
                int outboundQuantity = outboundOrderItem.getTotalQuantity();
                int deliveredQuantity = salesOrderItem.getDeliveredQuantity() + outboundQuantity;
                int inTransitQuantity = salesOrderItem.getInTransitQuantity() - outboundQuantity;
                update = salesOrderItemDao.updateDeliveryAndInTransitQuantity(prefix, salesOrderItem.getSalesOrderItemId(), salesOrderItem.getDataVersion(), deliveredQuantity, inTransitQuantity);
            }

            //三、修改销售单内的“出库单状态”、“送达数量”
            SalesOrder salesOrder = null;
            maxRetryTimes = 6;
            while (maxRetryTimes-- > 0) {
                salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
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
                if (salesOrder.getTotalQuantity() == salesOrder.getDeliveredQuantity()) {
                    //如果订单总数量==送达总数量，修改订单状态为“已签收”,产生应收代垫，产生低买高卖账户金额
                    salesOrderStatus = SIGNED.getStatus();
                    signTime = new Date();
                    //增加操作日志
                    TraceLog traceLog2 = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "签收完成");
                    tracelog = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog2);
                }
                update = salesOrderDao.updateWhenOutboundSigned(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), deliveredQuantity, inTransitQuantity, salesOrderStatus, signTime, tracelog);
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("update salesOrder FAILED! salesOrderNo={}", salesOrderNo);
            }
            long sellHighAmount = 0L;
            long shouldReceiveGrossProfit = 0L;
            long receivedGrossProfit = 0L;
            Long generatedPrepaid = 0L;
            List<SalesOutboundOrderItem> salesOutboundOrderItems = salesOutboundOrderItemDao.selectListByOutboundOrderNo(prefix, outboundOrderNo);
            for (SalesOutboundOrderItem salesOutboundOrderItem : salesOutboundOrderItems) {
                int quantity = salesOutboundOrderItem.getQuantity();
                sellHighAmount += Math.round(1.0 * salesOutboundOrderItem.getSellHighAmount() * quantity / 10000);
                shouldReceiveGrossProfit += Math.round(1.0 * salesOutboundOrderItem.getShouldReceiveGrossProfit() * quantity / 10000);
                receivedGrossProfit += Math.round(1.0 * salesOutboundOrderItem.getReceivedGrossProfit() * quantity / 10000);
                generatedPrepaid += Math.round(1.0 * salesOutboundOrderItem.getGeneratedPrepaid() * quantity / 10000);
            }

            // 根据channelId查询channelToken
            String channelId = salesConfig.getChannelId();
            //调用基础模块的SourceChannel服务
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(channelId + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            //如果供应商代垫金额》0，产生应收代垫
            if (generatedPrepaid > 0) {
                GongxiaoResult gongxiaoResult = XpsPrepaidManager.generateYhglobalPrepaidLedger(salesConfig.getPrepaidUrl(),
                        channelId, xpsChannelSecret, rpcHeader.getUid(), rpcHeader.getUsername(),
                        projectId, salesOrder.getCurrencyCode(), outboundOrderNo, salesOrder.getProjectName(),
                        salesOrder.getSupplierId().intValue(), salesOrder.getSupplierName(), salesOrder.getSalesContactNo(),
                        generatedPrepaid, outboundOrder.getOutboundFinishedTime());
                if (gongxiaoResult.getReturnCode() != 0) {
                    logger.error("add prepaid FAILED! salesOrderNo={}, amount(long)={}", salesOrderNo, generatedPrepaid);
                }
            }

            //产生低买高卖金额,去账户转入金额
            if (sellHighAmount > 0) {
                SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest rpcRequest = SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setCurrencyCode(salesOrder.getCurrencyCode())
                        .setProjectId(projectId)
                        .setAmount(sellHighAmount)
                        .setSalesOrderNo(outboundOrderNo)
                        .setTransactionTime(outboundOrder.getOutboundFinishedTime().getTime())
                        .build();
                SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
                GongxiaoRpc.RpcResult rpcResponse = rpcStub.salesUpdateSellHighAccount(rpcRequest);
                if (rpcResponse.getReturnCode() != ErrorCode.SUCCESS.getErrorCode()) {
                    logger.error("update sellHighAmount FAILED! salesOrderNo={}, amount(long)={}", salesOrderNo, sellHighAmount);
                }
            }

            // 如果产生了应收毛利
            if (shouldReceiveGrossProfit > 0) {
                GongxiaoResult gongxiaoResult = XpsGrossProfitManager.generateYhglobalGrossProfit(salesConfig.getGrossProfitUrl(),
                        channelId,
                        xpsChannelSecret,
                        rpcHeader.getUid(),
                        rpcHeader.getUsername(),
                        projectId, salesOrder.getProjectName(), salesOrder.getCurrencyCode(),
                        outboundOrderNo,
                        shouldReceiveGrossProfit,
                        salesOrder.getSalesOrderNo(),
                        outboundOrder.getOutboundFinishedTime(),
                        receivedGrossProfit,
                        prefix
                );
                if (gongxiaoResult.getReturnCode() != ErrorCode.SUCCESS.getErrorCode()) {
                    logger.error("update should received gross profit FAILED! salesOrderNo={}, shouldReceiveGrossProfit={}, receivedGrossProfit={}"
                            , salesOrderNo, shouldReceiveGrossProfit, receivedGrossProfit);
                }

            }

            //如果销售单全部签收, 前往支付模块修改金额相关
            if (salesOrder.getTotalQuantity() == salesOrder.getDeliveredQuantity()) {
                //如果存在 待确认金额为-值  ，将金额自动转入ad现金账户
                CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest rpcRequest = CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest.newBuilder()
                        .setProjectId(projectId)
                        .setRpcHeader(rpcHeader).setSalesOrderNo(salesOrderNo).build();
                CashConfirmServiceGrpc.CashConfirmServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(CashConfirmServiceGrpc.CashConfirmServiceBlockingStub.class);
                GongxiaoRpc.RpcResult rpcResponse = rpcStub.confirmNegativeAmountAutomatically(rpcRequest);
                if (rpcResponse.getReturnCode() != ErrorCode.SUCCESS.getErrorCode()) {
                    logger.error("update cashConfirm FAILED! salesOrderNo={}", salesOrderNo);
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
