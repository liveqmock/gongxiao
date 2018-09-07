package com.yhglobal.gongxiao.sales.microservice.impl;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.sales.SalesOrderSettlementModeEnum;
import com.yhglobal.gongxiao.constant.sales.SalesOrderStatusEnum;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure;
import com.yhglobal.gongxiao.payment.microservice.CashConfirmServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure;
import com.yhglobal.gongxiao.plansales.dao.SalePlanItemDao;
import com.yhglobal.gongxiao.sales.dao.SalesContractOrderIndexDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderItemDao;
import com.yhglobal.gongxiao.sales.dao.SalesScheduleDeliveryDao;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import com.yhglobal.gongxiao.sales.model.bo.SalesOrderInfo;
import com.yhglobal.gongxiao.sales.model.bo.SalesOrderList;
import com.yhglobal.gongxiao.sales.model.dto.SalesContractOrderIndex;
import com.yhglobal.gongxiao.sales.model.dto.SalesOrderCount;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.service.PlanSaleItemLocalService;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static com.yhglobal.gongxiao.constant.ErrorCode.ARGUMENTS_INVALID;
import static com.yhglobal.gongxiao.constant.ErrorCode.DISTRIBUTOR_CASH_ACCOUNT_BALANCE_NOT_ENOUGH;
import static com.yhglobal.gongxiao.constant.ErrorCode.ORDER_ALREADY_OUTBOUND;
import static com.yhglobal.gongxiao.constant.ErrorCode.ORDER_STATUS_CAN_NOT_MODIFY;
import static com.yhglobal.gongxiao.constant.ErrorCode.OVERTAKE_MAX_BUY_NUMBER;
import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;
import static com.yhglobal.gongxiao.constant.FXConstant.MILLION;

/**
 * 销售单商品详情Service实现类
 *
 * @author 葛灿
 */
@Service
public class SalesOrderServiceImpl extends SalesOrderServiceGrpc.SalesOrderServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(SalesOrderServiceImpl.class);

    @Autowired
    private SalesOrderDao salesOrderDao;
    @Autowired
    private SalesOrderItemDao salesOrderItemDao;
    @Autowired
    private SalePlanItemDao salePlanItemDao;
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private SalesScheduleDeliveryDao salesScheduleDeliveryDao;
    @Autowired
    private PlanSaleItemLocalService planSaleItemService;
    @Autowired
    private SalesContractOrderIndexDao salesContractOrderIndexDao;

    @Override
    public void getOrderByOrderNo(SalesOrderServiceStructure.CommonSalesOrderRequest request, StreamObserver<SalesOrderServiceStructure.GetOrderByOrderNoResponse> responseObserver) {
        SalesOrderServiceStructure.GetOrderByOrderNoResponse response;
        SalesOrderServiceStructure.GetOrderByOrderNoResponse.Builder respBuilder = SalesOrderServiceStructure.GetOrderByOrderNoResponse.newBuilder();
        long projectId = request.getProjectId();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        try {
            logger.info("#traceId={}# [IN][getOrderByOrderNo] params: salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            // 0.查询表前缀 
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            // 一、查询订单信息(转换为前台页面接受的BO)
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
            List<TraceLog> traceLogs = JSON.parseArray(salesOrder.getTracelog(), TraceLog.class);

            // 二、查询销售单货品信息
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(prefix, salesOrderNo);

            //model -> rpcModel
            respBuilder.setSalesOrderNo(salesOrderNo)
                    .setSalesContactNo(salesOrder.getSalesContactNo())
                    .setSalesOrderStatus(salesOrder.getSalesOrderStatus())
                    .setTotalOrderAmountDouble(DoubleScale.keepTwoBits(salesOrder.getTotalOrderAmount()))
                    .setCouponAmountDouble("0")
                    .setPrepaidAmountDouble("0")
                    .setPrestoredAmountDouble(DoubleScale.keepTwoBits(salesOrder.getPrestoredAmount()))
                    .setRecipientCompany(salesOrder.getRecipientCompany())
                    .setShippingAddress(salesOrder.getShippingAddress())
                    .setProvinceId(salesOrder.getProvinceId())
                    .setProvinceName(salesOrder.getProvinceName())
                    .setCityId(salesOrder.getCityId())
                    .setCityName(salesOrder.getCityName())
                    .setDistrictId(salesOrder.getDistrictId())
                    .setDistrictName(salesOrder.getDistrictName())
                    .setRecipientName(salesOrder.getRecipientName())
                    .setRecipientMobile(salesOrder.getRecipientMobile())
                    .setOutBoundTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getOutBoundTime()))
                    .setCreateTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getCreateTime()))
                    .setOrderCheckTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getOrderCheckTime()))
                    .setPaidTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getPaidTime()))
                    .setInformWarehouseTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getInformWarehouseTime()))
                    .setSignTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getSignTime()))
                    .setSupplierDiscountAmountDouble(DoubleScale.keepTwoBits(salesOrder.getSupplierDiscountAmount()))
                    .setYhDiscountAmountDouble(DoubleScale.keepTwoBits(salesOrder.getYhDiscountAmount()));
            List<GongxiaoRpc.TraceLog> protoTraceLog = GrpcCommonUtil.getProtoTraceLog(traceLogs);
            for (GongxiaoRpc.TraceLog protoObejct : protoTraceLog) {
                respBuilder.addTraceLog(protoObejct);
            }
            for (SalesOrderItem javaModel : salesOrderItems) {
                SalesOrderServiceStructure.SalesOrderItemsResponse itemsResponse = SalesOrderServiceStructure.SalesOrderItemsResponse.newBuilder()
                        .setProductName(javaModel.getProductName())
                        .setProductCode(javaModel.getProductCode())
                        .setTotalQuantity(javaModel.getTotalQuantity())
                        .setCurrencyCode(salesOrder.getCurrencyCode())
                        .setGuidePriceDouble(DoubleScale.keepSixBits(javaModel.getSalesGuidePrice()))
                        .setSupplierDiscountPercentDouble(DoubleScale.keepSixBits(javaModel.getSupplierDiscountPercent()))
                        .setSupplierDiscountAmountDouble(DoubleScale.keepSixBits(javaModel.getSupplierDiscountAmount()))
                        .setYhDiscountPercentDouble(DoubleScale.keepSixBits(javaModel.getYhDiscountPercent()))
                        .setYhDiscountAmountDouble(DoubleScale.keepSixBits(javaModel.getYhDiscountAmount()))
                        .setWholesalePriceDouble(DoubleScale.keepSixBits(javaModel.getWholesalePrice()))
                        .setTotalOrderAmountDouble(DoubleScale.keepTwoBits(javaModel.getTotalOrderAmount()))
                        .build();
                respBuilder.addItems(itemsResponse);
            }
            logger.info("#traceId={}# [OUT]: get sales order success", rpcHeader.getTraceId());
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void getOrderDetailBySalesOrderNo(SalesOrderServiceStructure.CommonSalesOrderRequest request, StreamObserver<SalesOrderServiceStructure.GetOrderByOrderNoResponse> responseObserver) {
        //获取参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        long projectId = request.getProjectId();
        //创建结果对象，创建转换对象，创建Builder
        SalesOrderServiceStructure.GetOrderByOrderNoResponse response;
        SalesOrderServiceStructure.GetOrderByOrderNoResponse.Builder respBuilder = SalesOrderServiceStructure.GetOrderByOrderNoResponse.newBuilder();
        try {
            logger.info("#traceId={}# [IN][getOrderDetailBySalesOrderNo] params: salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            // 一、查询订单信息(转换为前台页面接受的BO)
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
            List<TraceLog> traceLogs = JSON.parseArray(salesOrder.getTracelog(), TraceLog.class);
            // 二、查询销售单货品信息
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(prefix, salesOrderNo);

            //model -> rpcModel
            respBuilder.setSalesOrderNo(salesOrderNo)
                    .setSalesContactNo(salesOrder.getSalesContactNo())
                    .setSalesOrderStatus(salesOrder.getSalesOrderStatus())
                    .setTotalOrderAmountDouble(DoubleScale.keepTwoBits(salesOrder.getTotalOrderAmount()))
                    .setCouponAmountDouble(DoubleScale.keepTwoBits(salesOrder.getCouponAmount()))
                    .setPrepaidAmountDouble(DoubleScale.keepTwoBits(salesOrder.getPrepaidAmount()))
                    .setPrestoredAmountDouble(DoubleScale.keepTwoBits(salesOrder.getPrestoredAmount()))
                    .setCashAmountDouble(DoubleScale.keepTwoBits(salesOrder.getCashAmount()))
                    .setRecipientCompany(salesOrder.getRecipientCompany())
                    .setShippingAddress(salesOrder.getShippingAddress())
                    .setProvinceId(salesOrder.getProvinceId())
                    .setProvinceName(salesOrder.getProvinceName())
                    .setCityId(salesOrder.getCityId())
                    .setCityName(salesOrder.getCityName())
                    .setDistrictId(salesOrder.getDistrictId())
                    .setDistrictName(salesOrder.getDistributorName())
                    .setRecipientName(salesOrder.getRecipientName())
                    .setRecipientMobile(salesOrder.getRecipientMobile())
                    .setOutBoundTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getOutBoundTime()))
                    .setCreateTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getCreateTime()))
                    .setOrderCheckTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getOrderCheckTime()))
                    .setPaidTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getPaidTime()))
                    .setInformWarehouseTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getInformWarehouseTime()))
                    .setSignTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getSignTime()))
                    .setSupplierDiscountAmountDouble(DoubleScale.keepTwoBits(salesOrder.getSupplierDiscountAmount()))
                    .setYhDiscountAmountDouble(DoubleScale.keepTwoBits(salesOrder.getYhDiscountAmount()))
                    .setDistributorCouponAmountDouble("")
                    .setDistributorPrepaidAmountDouble("")
                    .setLastUpdateTime(GrpcCommonUtil.getProtoParam(salesOrder.getLastUpdateTime()))
            ;
            List<GongxiaoRpc.TraceLog> protoTraceLog = GrpcCommonUtil.getProtoTraceLog(traceLogs);
            for (GongxiaoRpc.TraceLog protoObejct : protoTraceLog) {
                respBuilder.addTraceLog(protoObejct);
            }
            for (SalesOrderItem javaModel : salesOrderItems) {
                SalesOrderServiceStructure.SalesOrderItemsResponse itemsResponse = SalesOrderServiceStructure.SalesOrderItemsResponse.newBuilder()
                        .setProductName(javaModel.getProductName())
                        .setProductCode(javaModel.getProductCode())
                        .setTotalQuantity(javaModel.getTotalQuantity())
                        .setCurrencyCode(salesOrder.getCurrencyCode())
                        .setGuidePriceDouble(DoubleScale.keepSixBits(javaModel.getSalesGuidePrice()))
                        .setSupplierDiscountPercentDouble(DoubleScale.keepSixBits(javaModel.getSupplierDiscountPercent()))
                        .setSupplierDiscountAmountDouble(DoubleScale.keepSixBits(javaModel.getSupplierDiscountAmount()))
                        .setYhDiscountPercentDouble(DoubleScale.keepSixBits(javaModel.getYhDiscountPercent()))
                        .setYhDiscountAmountDouble(DoubleScale.keepSixBits(javaModel.getYhDiscountAmount()))
                        .setWholesalePriceDouble(DoubleScale.keepSixBits(javaModel.getWholesalePrice()))
                        .setTotalOrderAmountDouble(DoubleScale.keepTwoBits(javaModel.getTotalOrderAmount()))
                        .setDeliveredQuantity(javaModel.getDeliveredQuantity())
                        .setSendQuantity(javaModel.getTotalQuantity() - javaModel.getUnhandledQuantity())
                        .build();
                respBuilder.addItems(itemsResponse);
            }
            logger.info("#traceId={}# [OUT]: get sales order success", rpcHeader.getTraceId());
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void getOrderDetailBySalesOrderNoAndProjectCode(SalesOrderServiceStructure.GetOrderItemRequest request, StreamObserver<SalesOrderServiceStructure.GetOrderItemResponse> responseObserver) {
        SalesOrderServiceStructure.GetOrderItemResponse.Builder respBuilder = SalesOrderServiceStructure.GetOrderItemResponse.newBuilder();
        SalesOrderServiceStructure.GetOrderItemResponse rpcResp;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        String productCode = request.getProductCode();
        long projectId = request.getProjectId();
        logger.info("#traceId={}# [IN][getOrderDetailBySalesOrderNoAndProjectCode] params:  salesOrderNo={}, productCode={}",
                rpcHeader.getTraceId(), salesOrderNo, productCode);
        try {
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, salesOrderNo, productCode);
            if (salesOrderItem == null) {
                logger.error("getOrderDetailBySalesOrderNoAndProjectCode no data");
                respBuilder.setReturnCode(ErrorCode.TARGET_DATA_NOT_EXIST.getErrorCode());
                respBuilder.setReturnMsg(ErrorCode.TARGET_DATA_NOT_EXIST.getMessage());
                rpcResp = respBuilder.build();
                responseObserver.onNext(rpcResp);
                responseObserver.onCompleted();
            }
            respBuilder.setCurrencyCode(salesOrderItem.getCurrencyCode());
            respBuilder.setCurrencyName(salesOrderItem.getCurrencyName());
            respBuilder.setSalesGuidePrice(String.valueOf(salesOrderItem.getSalesGuidePrice() / FXConstant.MILLION_DOUBLE));
            respBuilder.setWholesalePrice(String.valueOf(salesOrderItem.getWholesalePrice() / FXConstant.MILLION_DOUBLE));
            rpcResp = respBuilder.build();
            responseObserver.onNext(rpcResp);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void getListSelectively(SalesOrderServiceStructure.GetListSelectivelyRequest request, StreamObserver<SalesOrderServiceStructure.GetListSelectivelyResponse> responseObserver) {
        //获取参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        Long projectId = request.getProjectId();
        String orderNo = request.getOrderNo();
        String outDate = request.getOutDate();
        String createTime = request.getCreateTime();
        int orderStatus = request.getOrderStatus();
        int pageSize = request.getPageSize();
        int pageNum = request.getPageNum();
        //创建结果对象，创建转换对象，创建Builder
        SalesOrderServiceStructure.GetListSelectivelyResponse response;
        SalesOrderServiceStructure.GetListSelectivelyResponse.Builder respBuilder = SalesOrderServiceStructure.GetListSelectivelyResponse.newBuilder();
        try {
            logger.info("#traceId={}# [IN][getListSelectively] params:  projectId={}, orderNo={}, outDate={}, createTime={}, orderStatus={}, pageSize={}, pageNum={}",
                    rpcHeader.getTraceId(), projectId, orderNo, outDate, createTime, orderStatus, pageSize, pageNum);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            // 启动分页
            PageHelper.startPage(pageNum, pageSize);
            // 新建BO
            SalesOrderList salesOrderList = new SalesOrderList();
            // 订单信息
            new Date();
            List<SalesOrder> salesOrders = salesOrderDao.selectListSelectively(prefix, projectId, orderNo, outDate, createTime, orderStatus);
            for (SalesOrder salesOrder : salesOrders) {
                Long totalOrderAmount = salesOrder.getTotalOrderAmount();
                String floatString = DoubleScale.keepTwoBits(totalOrderAmount);
                salesOrder.setTotalOrderAmountDouble(floatString);
                salesOrder.setCashAmountDoubleStr(DoubleScale.keepTwoBits(salesOrder.getCashAmount()));
            }
            // 包装分页对象
            PageInfo<SalesOrder> pageInfo = new PageInfo<>(salesOrders);
            salesOrderList.setSalesOrders(pageInfo);

            // 数据库查询数量信息
            List<SalesOrderCount> countList = salesOrderDao.getCountMap(prefix, projectId, orderNo,
                    outDate, createTime);
            salesOrderList.setCountMap(countList);

            // 把数据库中不包含的状态也加入数组当中,返回给前端页面
            for (SalesOrderStatusEnum statusEnum : SalesOrderStatusEnum.values()) {
                int status = statusEnum.getStatus();
                boolean exists = false;
                for (SalesOrderCount resultStatus : countList) {
                    if (status == resultStatus.getStatus()) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    SalesOrderCount count = new SalesOrderCount();
                    count.setStatus(status);
                    count.setCount(0);
                    countList.add(count);
                }
            }
            salesOrderList.setTotalNumbers((int) pageInfo.getTotal());

            //model -> rpcModel
            SalesOrderServiceStructure.SalesOrderPageInfo.Builder pageInfoBuilder = SalesOrderServiceStructure.SalesOrderPageInfo.newBuilder()
                    .setPageNum(pageInfo.getPageNum())
                    .setPageSize(pageInfo.getPageSize())
                    .setTotal(pageInfo.getTotal());
            for (SalesOrder javaModel : pageInfo.getList()) {
                SalesOrderServiceStructure.SalesOrderResponse salesOrderResponse = SalesOrderServiceStructure.SalesOrderResponse.newBuilder()
                        .setSalesOrderStatus(javaModel.getSalesOrderStatus())
                        .setSettlementMode(javaModel.getSettlementMode())
                        .setSalesOrderNo(javaModel.getSalesOrderNo())
                        .setPaymentDays(javaModel.getPaymentDays() == null ? 0 : javaModel.getPaymentDays())
                        .setDistributorName(javaModel.getDistributorName())
                        .setTotalOrderAmountDouble(javaModel.getTotalOrderAmountDouble())
                        .setTotalQuantity(javaModel.getTotalQuantity())
                        .setUnhandledQuantity(javaModel.getUnhandledQuantity())
                        .setCashAmountDouble(javaModel.getCashAmountDoubleStr())
                        .setCreateTime(javaModel.getCreateTime().getTime())
                        .setPaidTime(GrpcCommonUtil.getProtoParam(javaModel.getPaidTime()))
                        .build();
                pageInfoBuilder.addList(salesOrderResponse);
            }
            for (SalesOrderCount javaObject : countList) {
                SalesOrderServiceStructure.SalesOrderCount.Builder protoObject = SalesOrderServiceStructure.SalesOrderCount.newBuilder().setCount(javaObject.getCount()).setSattus(javaObject.getStatus());
                respBuilder.addSalesOrderCounts(protoObject);
            }
            respBuilder.setSalesOrders(pageInfoBuilder);

            logger.info("#traceId={}# [OUT]: get sales order list success", rpcHeader.getTraceId());
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void changeSettlementMode(SalesOrderServiceStructure.ChangeSettlementModeRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        int days = request.getDays();
        List<String> salesOrderNoList = request.getSalesOrderNoList();
        String remark = request.getRemark();
        long projectId = request.getProjectId();
        //创建结果对象，创建转换对象，创建Builder
        GongxiaoRpc.RpcResult response;
        try {
            logger.info("#traceId={}# [IN][changeSettlementMode] params: salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNoList);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            ArrayList<SalesOrder> salesOrders = new ArrayList<>();
            for (String salesOrderNo : salesOrderNoList) {
                // 根据单号找到订单
                SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
                Integer salesOrderStatus = salesOrder.getSalesOrderStatus();
                Integer settlementMode = salesOrder.getSettlementMode();
                // 如果订单的状态不为“初始化/待审核”、“已审核”，或结算方式不为“单结”,无法更改结算模式
                // 订单状态不为"已下单"/ "已审核"
                boolean condition1 = SalesOrderStatusEnum.getEnum(salesOrderStatus) != SalesOrderStatusEnum.INIT ||
                        SalesOrderStatusEnum.getEnum(salesOrderStatus) != SalesOrderStatusEnum.CHECKED;
                // 订单状态不为"现金付款"
                boolean condition2 = SalesOrderSettlementModeEnum.getEnum(settlementMode) != SalesOrderSettlementModeEnum.NORMAL;
                if (condition1 && condition2) {
                    logger.info("#traceId={}# [OUT]: order {} can NOT change mode.", rpcHeader.getTraceId(), salesOrderNo);
                    response = GrpcCommonUtil.fail(ORDER_STATUS_CAN_NOT_MODIFY.getErrorCode(), ORDER_STATUS_CAN_NOT_MODIFY.getMessage());
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }
                salesOrders.add(salesOrder);
            }
            for (SalesOrder salesOrder : salesOrders) {
                // 确认所有订单均可以修改“结算方式后”，修改
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "修改结算方式");
                String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                // 如果订单的状态为"已审核/待收款"，则修改状态为“待发货”
                int update;
                if (SalesOrderStatusEnum.CHECKED == SalesOrderStatusEnum.getEnum(salesOrder.getSalesOrderStatus())) {
                    //修改订单状态
                    update = salesOrderDao.updateSettlementMode(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), SalesOrderStatusEnum.PAID.getStatus(), SalesOrderSettlementModeEnum.CREDIT.getStatus(), days, remark, traceLogs);
                } else {
                    //订单状态保持不变
                    update = salesOrderDao.updateSettlementMode(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), salesOrder.getSalesOrderStatus(), SalesOrderSettlementModeEnum.CREDIT.getStatus(), days, remark, traceLogs);
                }
                if (update != 1) {
                    logger.error("salesOrder {} change settlement mode FAILED!", salesOrder.getSalesOrderNo());
                }
            }
            logger.info("#traceId={}# [OUT]: change settlement mode success", rpcHeader.getTraceId());
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void createSalesOrder(SalesOrderServiceStructure.CreateSalesOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        //创建结果对象，创建转换对象，创建Builder
        GongxiaoRpc.RpcResult response;
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        int marketingChannel = request.getMarketingChannel();
        String salesContactNo = request.getSalesContactNo();
        int paymentChannel = request.getPaymentChannel();
        long projectId = request.getProjectId();
        String projectName = request.getProjectName();
        long distributorId = request.getDistributorId();
        String recipientMobile = request.getRecipientMobile();
        String distributorName = request.getDistributorName();
        String shippingAddress = request.getShippingAddress();
        String provinceId = request.getProvinceId();
        String provinceName = request.getProvinceName();
        String cityId = request.getCityId();
        String cityName = request.getCityName();
        String districtId = request.getDistrictId();
        String districtName = request.getDistrictName();
        String recipientName = request.getRecipientName();
        String currencyCode = request.getCurrencyCode();
        double cashAmount = request.getCashAmount();
        double totalOrderAmount = request.getTotalOrderAmount();
        double totalPaidAmount = request.getTotalPaidAmount();
        long createTime = request.getCreateTime();
        List<SalesOrderServiceStructure.SalesOrderItemInfoRequest> requestList = request.getListList();

        try {
            logger.info("#traceId={}# [IN][createSalesOrder] params: distributorId={}, distributorName={}, projectId={}, totalOrderAmount={}",
                    rpcHeader.getTraceId(), distributorId, distributorName, projectId, totalOrderAmount);

            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            // rpc去基础资料查数据
            ProjectServiceGrpc.ProjectServiceBlockingStub couponStub =
                    RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq.Builder projectBuilder = ProjectStructure.GetByProjectIdReq.newBuilder();
            ProjectStructure.GetByProjectIdReq projectIdReq =
                    projectBuilder
                            .setRpcHeader(rpcHeader)
                            .setProjectId(projectId + "")
                            .build();
            ProjectStructure.GetByProjectIdResp projectIdResp = couponStub.getByProjectId(projectIdReq);
            ProjectStructure.Project project = projectIdResp.getProject();

            // 生成销售合同号
            String salesOrderNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.SALES_ORDER_NO);
            String date = salesOrderNo.split("_")[2].replace(BizNumberType.SALES_ORDER_NO.getPrefix(), "").substring(0, 8);
            int maxRetryTimes = 6;
            boolean getIndexSuccess = false;
            int index = 0;
            while (maxRetryTimes-- > 0) {
                int update;
                SalesContractOrderIndex salesContractOrderIndex = salesContractOrderIndexDao.getIndexByProjectId(prefix, projectId);
                String lastUpdateDate = salesContractOrderIndex.getLastUpdateDate();
                boolean sameDay = date.equals(lastUpdateDate);
                if (sameDay) {
                    index = salesContractOrderIndex.getIndexNo() + 1;
                } else {
                    index = 1;
                }
                update = salesContractOrderIndexDao.updateIndex(prefix, salesContractOrderIndex.getRowId(),
                        salesContractOrderIndex.getDataVersion(), index,
                        date);
                if (update == 1) {
                    getIndexSuccess = true;
                    break;
                }
            }
            if (!getIndexSuccess) {
                throw new RuntimeException("error on creating salesContractOrderNo");
            }
            // 创建单号 todo 项目编码 经销商编码
            String salesContractNo = date + "-" + index;

            // 一、数据校验
            // 查询账户余额 如果使用现金>现金余额，信息返回页面
            // rpc查询账户返利、账户余额
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub accountStub =
                    RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.Builder accountBuilder = DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.newBuilder();
            DistributorAccountServiceStructure.GetDistributorAccountAmountRequest distributorAccountAmountRequest =
                    accountBuilder.setProjectId(projectId).setCurrencyCode(currencyCode).setDistributorId(distributorId).build();
            PaymentCommonGrpc.AccountAmountResponse account = accountStub.getDistributorAccountAmount(distributorAccountAmountRequest);
            if (account.getCashAmount() < cashAmount) {
                logger.info("#traceId={}# [OUT]: can NOT create salesOrderNo. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
                response = GrpcCommonUtil.fail(DISTRIBUTOR_CASH_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), DISTRIBUTOR_CASH_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            // 查询预售库存 
            if (true == false) {
                logger.info("#traceId={}# [OUT]: can NOT create salesOrderNo. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
                response = GrpcCommonUtil.fail(OVERTAKE_MAX_BUY_NUMBER.getErrorCode(), OVERTAKE_MAX_BUY_NUMBER.getMessage());
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            if (totalPaidAmount < 0 || cashAmount < 0) {
                logger.info("#traceId={}# [OUT]: inputed ILLEGAL amount. totalPaidAmount={}, cashAmount={}", rpcHeader.getTraceId(), totalPaidAmount, cashAmount);
                response = GrpcCommonUtil.fail(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }

            TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "新建");
            String traceLogs = TraceLogUtil.appendTraceLog(null, traceLog);

            // 一、创建销售单商品记录
            // 总数量、越海支持金，供应商支持金 高卖金额 应收代垫 应收毛利 实收毛利
            int totalQuantity = 0;
            long yhSupportAmount = 0;
            long supplierSupportAmount = 0;
            long totalSellHighAmount = 0;
            long totalGeneratedPrepaid = 0;
            long totalShouldReceiveGrossProfit = 0;
            long totalReceivedGrossProfit = 0;
            // 存放商品型号的set
            HashSet<String> itemsCodeSet = new HashSet<>();
            for (SalesOrderServiceStructure.SalesOrderItemInfoRequest info : requestList) {
                String productCode = info.getProductCode();
                Integer quantity = info.getTotalQuantity();
                SalesOrderItem salesOrderItem = new SalesOrderItem();

                salesOrderItem.setUsedCouponAmount(0);
                salesOrderItem.setUsedPrepaidAmount(0);
                salesOrderItem.setItemStatus(0);
                salesOrderItem.setSalesOrderNo(salesOrderNo);
                salesOrderItem.setProductCode(productCode);
                salesOrderItem.setTotalQuantity(quantity);
                // 从预售获取商品信息
                SalesPlanItem salesPlanItem = salePlanItemDao.getItemByProductModel(prefix, distributorId + "", productCode, new Date(createTime));
                salesOrderItem.setProductId(salesPlanItem.getProductId());
                salesOrderItem.setProductCode(productCode);
                salesOrderItem.setProductName(salesPlanItem.getProductName());
                salesOrderItem.setCurrencyCode(salesPlanItem.getCurrencyCode());
                salesOrderItem.setCurrencyName(salesPlanItem.getCurrencyName());
                salesOrderItem.setSupplierDiscountPercent(salesPlanItem.getBrandPrepaidDiscount());
                salesOrderItem.setYhDiscountPercent(salesPlanItem.getYhPrepaidDiscount());
                salesOrderItem.setWholesalePrice(salesPlanItem.getWholesalePrice());
                long yhDiscountAmount = Math.round(1.0 * salesPlanItem.getYhPrepaidUnit() * quantity / 10000.0);
                salesOrderItem.setYhDiscountAmount(salesPlanItem.getYhPrepaidUnit());
                salesOrderItem.setTotalQuantity(quantity);
                salesOrderItem.setDeliveredQuantity(0);
                salesOrderItem.setInTransitQuantity(0);
                salesOrderItem.setCanceledQuantity(0);
                salesOrderItem.setReturnedQuantity(0);
                salesOrderItem.setDataVersion(0L);
                salesOrderItem.setRetailPrice(0);
                salesOrderItem.setUnhandledQuantity(quantity);
                salesOrderItem.setCreateTime(new Date(createTime));
                salesOrderItem.setTracelog(traceLogs);
                salesOrderItem.setOngoingOutboundOrderInfo("[]");
                salesOrderItem.setFinishedOutboundOrderInfo("[]");
                //rpc调基础资料
                ProductServiceGrpc.ProductServiceBlockingStub productService = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                ProductStructure.GetByProductModelReq getByProductModelReq = ProductStructure.GetByProductModelReq.newBuilder().setRpcHeader(rpcHeader)
                        .setProductModel(productCode)
                        .setProjectId(projectId).build();
                ProductStructure.GetByProductModelResp productBasic = productService.getByProductModel(getByProductModelReq);
                String easCode = productBasic.getProductBusiness().getEasCode();
                salesOrderItem.setEasCode(easCode);
                long purchaseGuidePrice = salesPlanItem.getGuidePrice();
                long salesGuidePrice = salesPlanItem.getSaleGuidePrice();
                //销售成本价 = 采购指导价*(1-YH毛利支持折扣)
                double percent = 1.0 * salesPlanItem.getYhPrepaidDiscount() / MILLION;
                long salesCostPrice = Math.round(purchaseGuidePrice * (1 - percent));
                //出货价
                long wholesalePrice = salesPlanItem.getWholesalePrice();
                long difference = wholesalePrice - salesCostPrice;
                salesOrderItem.setSupplierDiscountAmount(salesPlanItem.getBrandPrepaidUnit());
                supplierSupportAmount += salesPlanItem.getBrandPrepaidUnit() / 10000.0 * quantity;
                salesOrderItem.setPurchaseGuidePrice(purchaseGuidePrice);
                salesOrderItem.setCustomerDiscountAmount(salesOrderItem.getYhDiscountAmount() + salesOrderItem.getSupplierDiscountAmount());
                salesOrderItem.setTotalOrderAmount(Math.round(salesOrderItem.getWholesalePrice() * salesOrderItem.getTotalQuantity() / 10000.0));
                salesOrderItem.setSalesGuidePrice(salesGuidePrice);
                salesOrderItemDao.insert(prefix, salesOrderItem);
                // 调用预售接口，修改预售剩余商品数量
                RpcResult rpcResult = planSaleItemService.updateSaleOccupation(prefix, rpcHeader, projectId + "", distributorId + "", salesOrderItem.getProductId() + "", new Date(createTime), quantity);
                if (!rpcResult.getSuccess()) {
                    logger.error("update plan item FAILED! productCode={}, quantity={}", productCode, quantity);
                }
                // 销售单总金额累加
                totalQuantity += quantity;
                yhSupportAmount += yhDiscountAmount;
                itemsCodeSet.add(productCode);
            }

            // 二、创建销售单
            SalesOrder salesOrder = new SalesOrder();
            salesOrder.setPaymentDays(0);
            salesOrder.setSettlementMode(SalesOrderSettlementModeEnum.NORMAL.getStatus());
            salesOrder.setSalesContactNo(UUID.randomUUID().toString());
            salesOrder.setShippingMode(1);
            salesOrder.setSalesOrderNo(salesOrderNo);
            salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.INIT.getStatus());
            salesOrder.setMarketingChannel(marketingChannel);
            salesOrder.setPaymentChannel(paymentChannel);
            salesOrder.setProjectId(projectId);
            salesOrder.setProjectName(projectName);
            salesOrder.setTotalGeneratedPrepaid(totalGeneratedPrepaid);
            salesOrder.setSellHighAmount(totalSellHighAmount);
            salesOrder.setShouldReceiveGrossProfit(totalShouldReceiveGrossProfit);
            salesOrder.setReceivedGrossProfit(totalReceivedGrossProfit);
            salesOrder.setSupplierId(project.getSupplierId());
            salesOrder.setSupplierName(project.getSupplierName());
            salesOrder.setDistributorId(distributorId);
            salesOrder.setDistributorName(distributorName);
            salesOrder.setRecipientCompany(distributorName);
            salesOrder.setShippingAddress(shippingAddress);
            salesOrder.setProvinceId(provinceId);
            salesOrder.setProvinceName(provinceName);
            salesOrder.setCityId(cityId);
            salesOrder.setCityName(cityName);
            salesOrder.setDistrictId(districtId);
            salesOrder.setDistrictName(districtName);
            salesOrder.setRecipientName(recipientName);
            salesOrder.setRecipientMobile(recipientMobile);
            salesOrder.setCurrencyCode(currencyCode);
            salesOrder.setTotalQuantity(totalQuantity);
            salesOrder.setUnhandledQuantity(totalQuantity);
            salesOrder.setTotalOrderAmount(Math.round(totalOrderAmount * HUNDRED));
            salesOrder.setCouponAmount(0L);
            salesOrder.setPrepaidAmount(0L);
            salesOrder.setCashAmount(Math.round(totalPaidAmount * HUNDRED));
            salesOrder.setPrestoredAmount(Math.round(cashAmount * HUNDRED));
            salesOrder.setYhDiscountAmount(yhSupportAmount);
            salesOrder.setSupplierDiscountAmount(supplierSupportAmount);
            salesOrder.setCreateTime(new Date(createTime));
            salesOrder.setTracelog(traceLogs);
            salesOrder.setDeliveredQuantity(0);
            salesOrder.setInTransitQuantity(0);
            salesOrder.setCanceledQuantity(0);
            salesOrder.setReturnedQuantity(0);
            salesOrder.setDataVersion(0L);
            salesOrder.setOngoingOutboundOrderInfo("[]");
            salesOrder.setFinishedOutboundOrderInfo("[]");
            String itemsCodeJson = JSON.toJSONString(itemsCodeSet);
            salesOrder.setItemsCode(itemsCodeJson);
            salesOrder.setDiscountAmount(yhSupportAmount + supplierSupportAmount);
            salesOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            salesOrder.setCreatedByName(rpcHeader.getUsername());
            salesOrder.setSalesContactNo(salesContractNo);
            // 销售单插入数据库
            // 三、账户中暂扣分销商的返利、代垫、现金 rpc
            if (cashAmount > 0) {
                accountStub = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
                DistributorAccountServiceStructure.PayForSalesOrderRequest.Builder payBuilder = DistributorAccountServiceStructure.PayForSalesOrderRequest.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setCurrencyCode(currencyCode)
                        .setDistributorId(distributorId)
                        .setProjectId(projectId)
                        .setCouponAmount(0)
                        .setPrepaidAmount(0)
                        .setCashAmount(Math.round(cashAmount * HUNDRED))
                        .setSalesOrderNo(salesOrderNo)
                        .setTransactionTime(System.currentTimeMillis());
                DistributorAccountServiceStructure.PayForSalesOrderRequest payRequest = payBuilder.build();
                DistributorAccountServiceStructure.PayForSalesOrderResponse payResponse = accountStub.payForSalesOrder(payRequest);
                if (payResponse.getReturnCode() != 0) {
                    logger.info("#traceId={}# [OUT]: update distributor account Failed", rpcHeader.getTraceId());
                }
            }
            salesOrderDao.insert(prefix, salesOrder);

            logger.info("#traceId={}# [OUT]: create sales order success", rpcHeader.getTraceId());
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void updateRecipientInfo(SalesOrderServiceStructure.UpdateRecipientInfoRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String orderNo = request.getOrderNo();
        String recipientName = request.getRecipientName();
        String recipientMobile = request.getRecipientMobile();
        String provinceId = request.getProvinceId();
        String provinceName = request.getProvinceName();
        String cityId = request.getCityId();
        String cityName = request.getCityName();
        String districtId = request.getDistrictId();
        String districtName = request.getDistrictName();
        String shippingAddress = request.getShippingAddress();
        String recipientCompany = request.getRecipientCompany();
        long projectId = request.getProjectId();
        //创建结果对象，创建转换对象，创建Builder
        GongxiaoRpc.RpcResult response;
        try {
            logger.info("#traceId={}# [IN][updateRecipientInfo] params: orderNo={}, recipientName={}, recipientMobile={}, provinceId={}, provinceName={}, cityId={}, cityName={}, districtId={}, districtName={}, shippingAddress={}, recipientCompany={}",
                    rpcHeader.getTraceId(), orderNo, recipientName, recipientMobile, provinceId, provinceName, cityId, cityName, districtId, districtName, shippingAddress, recipientCompany);
            // 0.查询表前缀 
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            int maxRetryTimes = 6;
            boolean updateSuccess = false;
            while (maxRetryTimes-- > 0) {
                // 找到销售单，修改字段信息，更新数据库
                SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, orderNo);
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "修改收件人信息");
                String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                int update = salesOrderDao.updateRecipientInfo(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(),
                        recipientName, recipientMobile, provinceId, provinceName, cityId, cityName, districtId, districtName, shippingAddress, recipientCompany, traceLogs);
                if (update == 1) {
                    //修改成功
                    updateSuccess = true;
                    break;
                }
            }
            // 如果更新失败，抛出位置系统异常
            if (!updateSuccess) {
                logger.error("FAILED to update salesOrder recipient info. salesOrder={}", orderNo);
                throw new RuntimeException("FAILED to update salesOrder recipient info");
            }
            logger.info("#traceId={}# [OUT]: update recipient info success. orderNo={}", rpcHeader.getTraceId(), orderNo);
            //model -> rpcModel
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void approveSalesOrder(SalesOrderServiceStructure.ApproveSalesOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        double discountAmountJmgoDouble = request.getDiscountAmountJmgo();
        String salesContractNo = request.getSalesContractNo();
        long projectId = request.getProjectId();
        //创建结果对象，创建转换对象，创建Builder
        GongxiaoRpc.RpcResult response;
        try {
            logger.info("#traceId={}# [IN][approveSalesOrder] params: salesOrderNo={}, discountAmountJmgo={}",
                    rpcHeader.getTraceId(), salesOrderNo, discountAmountJmgoDouble);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            SalesOrder salesOrder = null;
            int update;
            int maxRetryTimes = 6;
            boolean updateSuccess = false;
            long discountAmountJmgo = Math.round(discountAmountJmgoDouble * HUNDRED);
//            一、查找到订单
            while (maxRetryTimes-- > 0) {
                salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
                long totalAmount = salesOrder.getTotalOrderAmount() - discountAmountJmgo;
                // 如果折扣金额/订单总金额小于0,参数异常
                if (discountAmountJmgo < 0 || totalAmount < 0) {
                    logger.info("#traceId={}# [OUT]: can NOT approve sales order. salesOrderNo={}, discountAmountJmgo={}, totalPaid={}", rpcHeader.getTraceId(), salesOrderNo, discountAmountJmgo, totalAmount);
                    response = GrpcCommonUtil.fail(ErrorCode.ARGUMENTS_INVALID);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }

                // 查询账户余额 如果余额不足，返回错误信息
                // rpc查询账户返利、账户余额
                SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub supplierAccountService =
                        RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
                SupplierAccountServiceStructure.GetSellHighAccountRequest getSellHighAccountRequest = SupplierAccountServiceStructure.GetSellHighAccountRequest.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(projectId)
                        .setCurrencyCode(salesOrder.getCurrencyCode())
                        .build();
                SupplierAccountServiceStructure.GetSellHighAccountResponse sellHighAccount = supplierAccountService.getSellHighAccount(getSellHighAccountRequest);
                if (sellHighAccount.getTotalAmount() < discountAmountJmgo) {
                    logger.info("#traceId={}# [OUT]: balance is NOT enough. can NOT approve sales order. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
                    response = GrpcCommonUtil.fail(ErrorCode.SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }

                // 更改订单状态，各种金额
                Integer settlementMode = salesOrder.getSettlementMode();
                int salesOrderStatus;
                if (settlementMode == SalesOrderSettlementModeEnum.CREDIT.getStatus() || totalAmount == 0) {
                    // 如果订单结算方式为“信用（账期）”,或者应收款金额为0，修改订单状态为“已付款/待发货”
                    salesOrderStatus = SalesOrderStatusEnum.PAID.getStatus();
                } else if (settlementMode == SalesOrderSettlementModeEnum.NORMAL.getStatus()) {
                    // 如果订单结算方式为“普通（先款后货）”，修改订单状态为“已审核/待收款”
                    salesOrderStatus = SalesOrderStatusEnum.CHECKED.getStatus();
                } else {
                    salesOrderStatus = salesOrder.getSalesOrderStatus();
                }
                // 操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "审核");
                String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);

                update = salesOrderDao.updateApprovalInfo(prefix, salesOrder.getSalesOrderId(),
                        salesOrder.getDataVersion(),
                        salesOrderStatus,
                        discountAmountJmgo,
                        salesContractNo,
                        totalAmount,
                        new Date(),
                        traceLogs);
                if (update == 1) {
                    updateSuccess = true;
                    break;
                }
            }
            if (!updateSuccess) {
                logger.error("FAILED to approve salesOrder. salesOrderNo={}, discountAmountJmgo={}", salesOrderNo, discountAmountJmgo);
                throw new RuntimeException();
            }

            if (discountAmountJmgo > 0) {
                //如果 返利 或者 代垫 大于0，调用支付接口
                SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub supplierAccountService =
                        RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
                SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest updateSupplierSellHighAccountOnJmgoRequest = SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setAmount(-discountAmountJmgo)
                        .setCurrencyCode(salesOrder.getCurrencyCode())
                        .setSalesOrderNo(salesOrder.getSalesOrderNo())
                        .setProjectId(projectId)
                        .setDistributorId(Long.parseLong(salesOrder.getDistrictId()))
                        .setDistributorName(salesOrder.getDistributorName())
                        .setRemark("")
                        .setTransactionTime(System.currentTimeMillis())
                        .build();
                GongxiaoRpc.RpcResult rpcResult = supplierAccountService.updateSupplierSellHighAccountOnJmgo(updateSupplierSellHighAccountOnJmgoRequest);
                if (ErrorCode.SUCCESS != ErrorCode.getErrorCodeByCode(rpcResult.getReturnCode())) {
                    // 如果失败
                    logger.error("sales order update coupon/prepaid FAILED! salesOrderNo={}, amount={}", salesOrderNo, discountAmountJmgo);
                }
            }

            // 二、所有商品明细，添加返利、代垫，使用金额
            Long totalOrderAmount = salesOrder.getTotalOrderAmount();
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(prefix, salesOrderNo);
            for (SalesOrderItem salesOrderItem : salesOrderItems) {
                // 单品使用折扣金额 = 返利使用总金额*商品小计金额/订单总金额/数量
                long usedDiscountAmount = Math.round(1.0 * discountAmountJmgo * salesOrderItem.getTotalOrderAmount() / totalOrderAmount / salesOrderItem.getTotalQuantity());
                update = salesOrderItemDao.updateDiscountAmount(prefix, salesOrderItem.getSalesOrderItemId(), salesOrderItem.getDataVersion(), usedDiscountAmount);
                if (update != 1) {
                    logger.error("FAILED to approve salesOrder item. salesOrderNo={}, productCode={}, usedDiscountAmount={}", salesOrderNo, salesOrderItem.getProductCode(), usedDiscountAmount);
                }
            }

            // 三、插入收款确认数据
            CashConfirmServiceGrpc.CashConfirmServiceBlockingStub cashConfirmStub = RpcStubStore.getRpcStub(CashConfirmServiceGrpc.CashConfirmServiceBlockingStub.class);
            CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest.Builder cashConfirmBuilder = CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setSalesOrderNo(salesOrderNo)
                    .setDistributorId(salesOrder.getDistributorId())
                    .setDistributorName(salesOrder.getDistributorName())
                    .setProjectId(projectId)
                    .setProjectName(salesOrder.getProjectName())
                    .setCurrencyCode(salesOrder.getCurrencyCode())
                    .setCashAmount(salesOrder.getCashAmount())
                    .setCreateTime(salesOrder.getCreateTime().getTime());
            CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest cashConfirmRequest = cashConfirmBuilder.build();
            GongxiaoRpc.RpcResult confirmResponse = cashConfirmStub.createSalesOrderCashConfirmOrder(cashConfirmRequest);


            // 四、调用预售接口
            // 修改预售占用数量->已售数量
            projectId = salesOrder.getProjectId();
            Long distributorId = salesOrder.getDistributorId();
            Date createTime = salesOrder.getCreateTime();
            for (SalesOrderItem salesOrderItem : salesOrderItems) {
                String productCode = salesOrderItem.getProductCode();
                Integer quantity = salesOrderItem.getTotalQuantity();
                RpcResult rpcResult = planSaleItemService.updateSoldQuantity(prefix, rpcHeader, projectId + "", distributorId + "", productCode, createTime, quantity);
                if (!rpcResult.getSuccess()) {
                    logger.error("FAILED to update planItem quantity. productCode={}", productCode);
                }
            }


            logger.info("#traceId={}# [OUT]: approve sales order success. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            //model -> rpcModel
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public void cancelSalesOrderByDistributor(SalesOrderServiceStructure.CommonSalesOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        long projectId = request.getProjectId();
        //创建结果对象，创建转换对象，创建Builder
        GongxiaoRpc.RpcResult response;
        try {
            logger.info("#traceId={}# [IN][cancelSalesOrderByDistributor] params: salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            SalesOrder salesOrder = null;
            int maxRetryTimes = 6;
            int update;
            boolean updateSuccess = false;
            while (maxRetryTimes-- > 0) {
                // 查找到订单，如果订单状态不为“待审核”，则无法修改
                salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
                Integer salesOrderStatus = salesOrder.getSalesOrderStatus();
                if (SalesOrderStatusEnum.INIT != SalesOrderStatusEnum.getEnum(salesOrderStatus)) {
                    logger.info("#traceId={}# [OUT]: fail, can NOT cancel!", rpcHeader.getTraceId());
                    response = GrpcCommonUtil.fail(ORDER_STATUS_CAN_NOT_MODIFY.getErrorCode(), ORDER_STATUS_CAN_NOT_MODIFY.getMessage());
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }
                // 操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消");
                String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                update = salesOrderDao.updateSalesOrderStatus(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), SalesOrderStatusEnum.CANCELED.getStatus(), traceLogs);
                if (update == 1) {
                    updateSuccess = true;
                    break;
                }
            }
            // 如果更新失败，抛出位置系统异常
            if (!updateSuccess) {
                logger.error("FAILED to cancel salesOrder. salesOrderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to update salesOrder");
            }

            // 如果可以修改，修改订单状态，退换客户已使用的预存金额
            if (salesOrder.getPrestoredAmount() > 0) {
                DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub accountStub =
                        RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
                DistributorAccountServiceStructure.PayForSalesOrderRequest.Builder payBuilder = DistributorAccountServiceStructure.PayForSalesOrderRequest.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setCurrencyCode(salesOrder.getCurrencyCode())
                        .setDistributorId(salesOrder.getDistributorId())
                        .setProjectId(projectId)
                        .setCouponAmount(0)
                        .setPrepaidAmount(0)
                        .setCashAmount(salesOrder.getPrestoredAmount())
                        .setSalesOrderNo(salesOrderNo)
                        .setTransactionTime(System.currentTimeMillis());
                DistributorAccountServiceStructure.PayForSalesOrderRequest payRequest = payBuilder.build();
                DistributorAccountServiceStructure.PayForSalesOrderResponse payResponse = accountStub.returnForSalesReturnOrder(payRequest);
                if (payResponse.getReturnCode() == 0) {
                    String returnCashFlowNo = payResponse.getResult().getCashFlowNo();
                    salesOrderDao.updateReturnCashFlowNo(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), returnCashFlowNo);
                } else {
                    logger.error("salesOrder update cash account FAILED ! salesOrderNo={}, cashAmount={}", salesOrderNo, salesOrder.getPrestoredAmount());
                }
            }

            //退还预售数量
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(prefix, salesOrderNo);
            projectId = salesOrder.getProjectId();
            Long distributorId = salesOrder.getDistributorId();
            Date createTime = salesOrder.getCreateTime();
            for (SalesOrderItem salesOrderItem : salesOrderItems) {
                String productCode = salesOrderItem.getProductCode();
                Integer quantity = salesOrderItem.getTotalQuantity();
                RpcResult rpcResult = planSaleItemService.updateSaleOccupation(prefix, rpcHeader, projectId + "", distributorId + "", productCode, createTime, -quantity);
                if (!rpcResult.getSuccess()) {
                    logger.error("FAILED to update planItem quantity. productCode={}", productCode);
                }
            }

            logger.info("#traceId={}# [OUT]: cancel success. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            //model -> rpcModel
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void rejectSalesOrder(SalesOrderServiceStructure.CommonSalesOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        long projectId = request.getProjectId();
        //创建结果对象，创建转换对象，创建Builder
        GongxiaoRpc.RpcResult response;
        logger.info("#traceId={}# [IN][rejectSalesOrder] params: salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
        try {
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            SalesOrder salesOrder = null;
            int update;
            int maxRetryTimes = 6;
            boolean updateSuccess = false;
            while (maxRetryTimes-- > 0) {
                salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
                // 如果销售单状态不为“待审核”，无法驳回
                if (SalesOrderStatusEnum.INIT.getStatus() != salesOrder.getSalesOrderStatus()) {
                    logger.info("#traceId={}# [OUT]: order can NOT reject! salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
                    response = GrpcCommonUtil.fail(ORDER_STATUS_CAN_NOT_MODIFY);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }
                // 一、查找到订单，更改订单状态，各种金额
                salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.REJECTED.getStatus());
                salesOrder.setRejectTime(new Date());
                // 操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "驳回");
                String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                salesOrder.setTracelog(traceLogs);

                // 二、退回返利代垫账户中冻结的金额
                //如果更新账户成功，更新销售单
                update = salesOrderDao.updateSalesOrderStatusAndRejectTime(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), salesOrder.getSalesOrderStatus(), traceLogs, new Date());
                if (update == 1) {
                    updateSuccess = true;
                    break;
                }
            }
            //如果更新失败，抛出位置系统异常
            if (!updateSuccess) {
                logger.error("FAILED to reject SalesOrder. salesOrderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to reject SalesOrder");
            }
            // 退回使用的预存 rpc
            DistributorAccountServiceStructure.PayForSalesOrderResponse payResponse;
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub accountStub =
                    RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            DistributorAccountServiceStructure.PayForSalesOrderRequest.Builder payBuilder = DistributorAccountServiceStructure.PayForSalesOrderRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode(salesOrder.getCurrencyCode())
                    .setDistributorId(salesOrder.getDistributorId())
                    .setProjectId(projectId)
                    .setCouponAmount(0)
                    .setPrepaidAmount(0)
                    .setCashAmount(salesOrder.getPrestoredAmount())
                    .setSalesOrderNo(salesOrderNo)
                    .setTransactionTime(System.currentTimeMillis());
            DistributorAccountServiceStructure.PayForSalesOrderRequest payRequest = payBuilder.build();
            payResponse = accountStub.returnForSalesReturnOrder(payRequest);
            if (payResponse.getReturnCode() != 0) {
                logger.error("FAILED to return cash amount {}", salesOrder.getPrestoredAmount());
            }

            //退还预售数量
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(prefix, salesOrderNo);
            projectId = salesOrder.getProjectId();
            Long distributorId = salesOrder.getDistributorId();
            Date createTime = salesOrder.getCreateTime();
            for (SalesOrderItem salesOrderItem : salesOrderItems) {
                String productCode = salesOrderItem.getProductCode();
                Integer quantity = salesOrderItem.getTotalQuantity();
                RpcResult rpcResult = planSaleItemService.updateSaleOccupation(prefix, rpcHeader, projectId + "", distributorId + "", productCode, createTime, -quantity);
                if (!rpcResult.getSuccess()) {
                    logger.error("FAILED to update planItem quantity. productCode={}", productCode);
                }
            }

            logger.info("#traceId={}# [OUT]: reject sales order success. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            //model -> rpcModel
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void cancelSalesOrderApprove(SalesOrderServiceStructure.CommonSalesOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        long projectId = request.getProjectId();
        //创建结果对象，创建转换对象，创建Builder
        GongxiaoRpc.RpcResult response;
        try {
            logger.info("#traceId={}# [IN][cancelSalesOrderApprove] params: salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            SalesOrder salesOrder = null;
            int maxRetryTimes = 6;
            boolean updateSuccess = false;
            while (maxRetryTimes-- > 0) {
                // 找到销售单
                salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
                // 如果销售单状态不为“待收款” 或 结算方式为“账期付款”，无法取消审核
                boolean condition1 = SalesOrderStatusEnum.CHECKED != SalesOrderStatusEnum.getEnum(salesOrder.getSalesOrderStatus());
                boolean condition2 = SalesOrderSettlementModeEnum.CREDIT == SalesOrderSettlementModeEnum.getEnum(salesOrder.getSettlementMode());
                if (condition1 || condition2) {
                    logger.info("#traceId={}# [OUT]: order can NOT cancel! salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
                    response = GrpcCommonUtil.fail(ORDER_STATUS_CAN_NOT_MODIFY);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }
                // 如果销售模块修改成功，修改销售单状态
                TraceLog newTracelog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消审核");
                String tracelog = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), newTracelog);
                int update = salesOrderDao.updateSalesOrderStatusAndRejectTime(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), SalesOrderStatusEnum.CANCELED.getStatus(), tracelog, new Date());
                if (update == 1) {
                    updateSuccess = true;
                    break;
                }
            }
            //如果更新失败，抛出位置系统异常
            if (!updateSuccess) {
                logger.error("FAILED to cancel salesOrder. orderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to cancel salesOrder");
            }

            salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
            // 退回使用的优惠金额
            Long discountAmount = salesOrder.getDiscountAmount();
            if (discountAmount > 0) {
                //如果优惠金额大于0，调用支付接口,进行金额返还
                SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub supplierAccountService =
                        RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
                SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest updateSupplierSellHighAccountOnJmgoRequest = SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setAmount(discountAmount)
                        .setCurrencyCode(salesOrder.getCurrencyCode())
                        .setSalesOrderNo(salesOrder.getSalesOrderNo())
                        .setProjectId(projectId)
                        .setDistributorId(Long.parseLong(salesOrder.getDistrictId()))
                        .setDistributorName(salesOrder.getDistributorName())
                        .setRemark("销售订单被取消")
                        .setTransactionTime(System.currentTimeMillis())
                        .build();
                GongxiaoRpc.RpcResult rpcResult = supplierAccountService.updateSupplierSellHighAccountOnJmgo(updateSupplierSellHighAccountOnJmgoRequest);
                if (ErrorCode.SUCCESS != ErrorCode.getErrorCodeByCode(rpcResult.getReturnCode())) {
                    // 如果失败
                    logger.error("sales order update coupon/prepaid FAILED! salesOrderNo={}, amount={}", salesOrderNo, discountAmount);
                }
            }

            //退回现金账户
            // 退回使用的预存 rpc
            long prestoredAmount = salesOrder.getPrestoredAmount();
            if (prestoredAmount>0) {
                DistributorAccountServiceStructure.PayForSalesOrderResponse payResponse;
                DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub accountStub =
                        RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
                DistributorAccountServiceStructure.PayForSalesOrderRequest.Builder payBuilder = DistributorAccountServiceStructure.PayForSalesOrderRequest.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setCurrencyCode(salesOrder.getCurrencyCode())
                        .setDistributorId(salesOrder.getDistributorId())
                        .setProjectId(projectId)
                        .setCouponAmount(0)
                        .setPrepaidAmount(0)
                        .setCashAmount(prestoredAmount)
                        .setSalesOrderNo(salesOrderNo)
                        .setTransactionTime(System.currentTimeMillis());
                DistributorAccountServiceStructure.PayForSalesOrderRequest payRequest = payBuilder.build();
                payResponse = accountStub.returnForSalesReturnOrder(payRequest);
                if (payResponse.getReturnCode() != 0) {
                    logger.error("FAILED to return cash amount {}", salesOrder.getPrestoredAmount());
                }
            }

            //作废现金确认
            CashConfirmServiceGrpc.CashConfirmServiceBlockingStub confirmStub =
                    RpcStubStore.getRpcStub(CashConfirmServiceGrpc.CashConfirmServiceBlockingStub.class);
            CashConfirmSerivceStructure.CancelCashConfirmRequest.Builder confirmBuilder = CashConfirmSerivceStructure.CancelCashConfirmRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesOrderNo(salesOrderNo);
            CashConfirmSerivceStructure.CancelCashConfirmRequest confirmRequest = confirmBuilder.build();
            GongxiaoRpc.RpcResult cancelCashConfirmResponse = confirmStub.cancelCashConfirm(confirmRequest);
            if (cancelCashConfirmResponse.getReturnCode() == 0) {
                //作废失败
                logger.info("#traceId={}# [OUT]: cancel order cash confirm FAILED! salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            }

            //预售数量
            // 退回已售数量 -> 占用数量
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(prefix, salesOrderNo);
            projectId = salesOrder.getProjectId();
            Long distributorId = salesOrder.getDistributorId();
            Date createTime = salesOrder.getCreateTime();
            for (SalesOrderItem salesOrderItem : salesOrderItems) {
                String productCode = salesOrderItem.getProductCode();
                Integer quantity = salesOrderItem.getTotalQuantity();
                RpcResult rpcResult = planSaleItemService.updateSoldQuantity(prefix, rpcHeader, projectId + "", distributorId + "", productCode, createTime, -quantity);
                if (!rpcResult.getSuccess()) {
                    logger.error("FAILED to update planItem quantity. productCode={}", productCode);
                }
            }

            logger.info("#traceId={}# [OUT]: cancel order success. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            //model -> rpcModel
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void confirmSalesOrderAmount(SalesOrderServiceStructure.CommonSalesOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        long projectId = request.getProjectId();
        //创建结果对象，创建转换对象，创建Builder
        GongxiaoRpc.RpcResult response;
        try {
            logger.info("#traceId={}# [IN][confirmSalesOrderAmount] params: salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            int maxRetryTimes = 6;
            boolean updateSuccess = false;
            while (maxRetryTimes-- > 0) {
                // 一、查找到订单，更改订单状态，各种金额
                SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
                if (salesOrder.getPaidTime() != null) {
                    logger.info("#traceId={}# [OUT]: order already confirmed. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
                    response = GrpcCommonUtil.success();
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }
                Integer settlementMode = salesOrder.getSettlementMode();
                int salesOrderStatus;
                if (settlementMode == SalesOrderSettlementModeEnum.NORMAL.getStatus()) {
                    // 如果订单结算方式为“普通（先款后货）”，修改订单状态为“已付款/待发货”
                    salesOrderStatus = SalesOrderStatusEnum.PAID.getStatus();
                } else {
                    salesOrderStatus = salesOrder.getSalesOrderStatus();
                }
                salesOrder.setPaidTime(new Date());
                TraceLog newTracelog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "确认收款");
                String traceLogJson = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), newTracelog);
                salesOrder.setTracelog(traceLogJson);
                int update = salesOrderDao.updateSalesOrderStatusAndPaidTime(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), salesOrderStatus, traceLogJson, new Date());
                if (update == 1) {
                    updateSuccess = true;
                    break;
                }
            }
            //如果更新失败，抛出位置系统异常
            if (!updateSuccess) {
                logger.error("FAILED to confirm salesOrder amount. orderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to confirm salesOrder amount");
            }
            logger.info("#traceId={}# [OUT]: confirm sales order amount success. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            //model -> rpcModel
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void searchOrderListWithPage(SalesOrderServiceStructure.SearchOrderListWithPageRequest request, StreamObserver<SalesOrderServiceStructure.SearchOrderListWithPageResponse> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        String productCode = request.getProductCode();
        int status = request.getStatus();
        String dateStart = request.getDateStart();
        String dateEnd = request.getDateEnd();
        int page = request.getPage();
        int pageSize = request.getPageSize();
        long projectId = request.getProjectId();
        long distributorId = request.getDistributorId();
        SalesOrderServiceStructure.SearchOrderListWithPageResponse response;
        SalesOrderServiceStructure.SearchOrderListWithPageResponse.Builder builder = SalesOrderServiceStructure.SearchOrderListWithPageResponse.newBuilder();
        logger.info("#traceId={}# [IN][searchOrderList] params: salesOrderNo={}, productCode={}, status={}, dateStart={}, dateEnd={}, page={}, pageSize={} ",
                rpcHeader.getTraceId(), salesOrderNo, productCode, status, dateStart, page, pageSize);
        PageInfo<SalesOrderInfo> pageInfo;
        try {
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PageHelper.startPage(page, pageSize);

            List<SalesOrder> salesOrderList = salesOrderDao.selectListSelectivelyByDistributor(prefix, projectId, distributorId, salesOrderNo, productCode, dateStart, dateEnd, status);
            ArrayList<SalesOrderInfo> salesOrderInfos = new ArrayList<>();
            for (SalesOrder salesOrder : salesOrderList) {
                SalesOrderInfo salesOrderInfo = new SalesOrderInfo();
                salesOrderInfo.setSalesOrderNo(salesOrder.getSalesOrderNo());
                Integer salesOrderStatus = salesOrder.getSalesOrderStatus();
                salesOrderInfo.setSalesOrderStatus(salesOrderStatus);
                salesOrderInfo.setDistributorName(salesOrder.getDistributorName());
                salesOrderInfo.setPaidTime(salesOrder.getPaidTime());
                salesOrderInfo.setCreateTime(salesOrder.getCreateTime());
                salesOrderInfo.setPaidTime(salesOrder.getPaidTime());
                salesOrderInfo.setTotalOrderAmountDouble(salesOrder.getTotalOrderAmount() / FXConstant.HUNDRED_DOUBLE);
                salesOrderInfo.setDiscountAmountTotal(salesOrder.getDiscountAmount() / FXConstant.HUNDRED_DOUBLE);
                salesOrderInfo.setCouponAmountDouble(salesOrder.getCouponAmount() / FXConstant.HUNDRED_DOUBLE);
                salesOrderInfo.setPrepaidAmountDouble(salesOrder.getPrepaidAmount() / FXConstant.HUNDRED_DOUBLE);
                salesOrderInfo.setTotalQuantity(salesOrder.getTotalQuantity());
                salesOrderInfo.setUnhandledQuantity(salesOrder.getUnhandledQuantity());
                salesOrderInfos.add(salesOrderInfo);
            }
            pageInfo = new PageInfo<>(salesOrderInfos);

            builder.setPageNum(pageInfo.getPageNum()).setPageSize(pageInfo.getPageSize()).setTotal(pageInfo.getTotal());
            for (SalesOrderInfo javaObject : salesOrderInfos) {
                SalesOrderServiceStructure.SalesOrderInfoResponse protoObject = SalesOrderServiceStructure.SalesOrderInfoResponse.newBuilder()
                        .setSalesOrderNo(javaObject.getSalesOrderNo())
                        .setSalesOrderStatus(javaObject.getSalesOrderStatus())
                        .setTotalOrderAmountDouble(javaObject.getTotalOrderAmountDouble())
                        .setDiscountAmountTotal(javaObject.getDiscountAmountTotal())
                        .setCouponAmountDouble(javaObject.getCouponAmountDouble())
                        .setPrepaidAmountDouble(javaObject.getPrepaidAmountDouble())
                        .setDistributorName(javaObject.getDistributorName())
                        .setCreateTime(javaObject.getCreateTime().getTime())
                        .setPaidTime(GrpcCommonUtil.getProtoParam(javaObject.getPaidTime()))
                        .setTotalQuantity(javaObject.getTotalQuantity())
                        .setUnhandledQuantity(javaObject.getUnhandledQuantity())
                        .build();
                builder.addList(protoObject);
            }
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("search orderList error!", e);
        }
    }

    @Override
    public void searchOrderList(SalesOrderServiceStructure.SearchOrderListRequest request, StreamObserver<SalesOrderServiceStructure.SearchOrderListResponse> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        String productCode = request.getProductCode();
        int status = request.getStatus();
        String dateStart = request.getDateStart();
        String dateEnd = request.getDateEnd();
        long projectId = request.getProjectId();
        long distributorId = request.getDistributorId();
        SalesOrderServiceStructure.SearchOrderListResponse response;
        SalesOrderServiceStructure.SearchOrderListResponse.Builder builder = SalesOrderServiceStructure.SearchOrderListResponse.newBuilder();
        logger.info("#traceId={}# [IN][searchOrderList] params:  salesOrderNo={}, productCode={}, status={}, dateStart={}, dateEnd={} ",
                rpcHeader.getTraceId(), salesOrderNo, productCode, status, dateStart);
        ArrayList<SalesOrderInfo> salesOrderInfos = new ArrayList<>();
        try {
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            List<SalesOrder> salesOrderList = salesOrderDao.selectListSelectivelyByDistributor(prefix, projectId, distributorId, salesOrderNo, productCode, dateStart, dateEnd, status);
            for (SalesOrder salesOrder : salesOrderList) {
                SalesOrderInfo salesOrderInfo = new SalesOrderInfo();
                salesOrderInfo.setSalesOrderNo(salesOrder.getSalesOrderNo());
                Integer salesOrderStatus = salesOrder.getSalesOrderStatus();
                salesOrderInfo.setSalesOrderStatus(salesOrderStatus);
                salesOrderInfo.setDistributorName(salesOrder.getDistributorName());
                salesOrderInfo.setPaidTime(salesOrder.getPaidTime());
                salesOrderInfo.setCreateTime(salesOrder.getCreateTime());
                salesOrderInfo.setPaidTime(salesOrder.getPaidTime());
                salesOrderInfo.setTotalOrderAmountDouble(1.0 * salesOrder.getTotalOrderAmount() / HUNDRED);
                salesOrderInfo.setDiscountAmountTotal(1.0 * salesOrder.getDiscountAmount() / HUNDRED);
                salesOrderInfo.setTotalQuantity(salesOrder.getTotalQuantity());
                salesOrderInfo.setUnhandledQuantity(salesOrder.getUnhandledQuantity());
                salesOrderInfos.add(salesOrderInfo);
            }

            //model -> proto
            for (SalesOrderInfo javaObject : salesOrderInfos) {
                SalesOrderServiceStructure.SalesOrderInfoResponse protoObject = SalesOrderServiceStructure.SalesOrderInfoResponse.newBuilder()
                        .setSalesOrderNo(javaObject.getSalesOrderNo())
                        .setSalesOrderStatus(javaObject.getSalesOrderStatus())
                        .setTotalOrderAmountDouble(javaObject.getTotalOrderAmountDouble())
                        .setDiscountAmountTotal(javaObject.getDiscountAmountTotal())
                        .setCouponAmountDouble(javaObject.getCouponAmountDouble())
                        .setPrepaidAmountDouble(javaObject.getPrepaidAmountDouble())
                        .setDistributorName(javaObject.getDistributorName())
                        .setCreateTime(javaObject.getCreateTime().getTime())
                        .setPaidTime(GrpcCommonUtil.getProtoParam(javaObject.getPaidTime()))
                        .setTotalQuantity(javaObject.getTotalQuantity())
                        .setUnhandledQuantity(javaObject.getUnhandledQuantity())
                        .build();
                builder.addList(protoObject);
            }
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("search orderList error!", e);
        }
    }

    @Override
    public void whetherOutbound(SalesOrderServiceStructure.CommonSalesOrderRequest request, StreamObserver<SalesOrderServiceStructure.WhetherOutboundResponse> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        long projectId = request.getProjectId();
        //创建结果对象，创建转换对象，创建Builder
        SalesOrderServiceStructure.WhetherOutboundResponse response;
        logger.info("#traceId={}# [IN][whetherOutbound] params: salesOrderNo={}",
                rpcHeader.getTraceId(), salesOrderNo);
        try {
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
            int unhandledQuantity = salesOrder.getUnhandledQuantity();
            int totalQuantity = salesOrder.getTotalQuantity();
            // 如果总数量==未处理数量，则未发货，否则已发货
            if (totalQuantity == unhandledQuantity) {
                logger.info("#traceId={}# [OUT]:get status success. whetherOutbound=false", rpcHeader.getTraceId());
                response = SalesOrderServiceStructure.WhetherOutboundResponse.newBuilder().setOutbound(false).build();
            } else {
                logger.info("#traceId={}# [OUT]:get status success. whetherOutbound=true", rpcHeader.getTraceId());
                response = SalesOrderServiceStructure.WhetherOutboundResponse.newBuilder().setOutbound(true).build();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("search orderList error!", e);
        }
    }


    @Override
    public void cancelReceivedCash(SalesOrderServiceStructure.CommonSalesOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        long projectId = request.getProjectId();
        //创建结果对象，创建转换对象，创建Builder
        GongxiaoRpc.RpcResult response;
        logger.info("#traceId={}# [IN][cancelReceivedCash] params: salesOrderNo={}",
                rpcHeader.getTraceId(), salesOrderNo);
        try {
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            int maxRetryTimes = 6;
            boolean updateSuccess = false;
            while (maxRetryTimes-- > 0) {
                SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
                int unhandledQuantity = salesOrder.getUnhandledQuantity();
                int totalQuantity = salesOrder.getTotalQuantity();
                // 如果总数量==未处理数量，则未发货，否则已发货
                if (totalQuantity != unhandledQuantity) {
                    logger.info("#traceId={}# [OUT]:can NOT cancel", rpcHeader.getTraceId());
                    response = GrpcCommonUtil.fail(ORDER_ALREADY_OUTBOUND);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                } else {
                    int salesOrderStatus;
                    if (SalesOrderSettlementModeEnum.CREDIT != SalesOrderSettlementModeEnum.getEnum(salesOrder.getSettlementMode())) {
                        // 如果不是“账期结算”，修改订单状态为待收款
                        salesOrderStatus = SalesOrderStatusEnum.CHECKED.getStatus();
                    } else {
                        salesOrderStatus = salesOrder.getSalesOrderStatus();
                    }
                    TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消收款");
                    String appendTraceLog = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                    int update = salesOrderDao.updateSalesOrderStatusAndPaidTime(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), salesOrderStatus, appendTraceLog, null);
                    if (update == 1) {
                        updateSuccess = true;
                        break;
                    }
                }
            }
            //如果更新失败，抛出位置系统异常
            if (!updateSuccess) {
                logger.error("FAILED to cancel salesOrder. orderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to cancel salesOrder");
            }
            logger.info("#traceId={}# [OUT]:change status success", rpcHeader.getTraceId());
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("search orderList error!", e);
        }
    }
}
