package com.yhglobal.gongxiao.phjd.sales.microservice.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.UpdateStatus;
import com.yhglobal.gongxiao.constant.sales.*;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.phjd.common.service.CommonService;
import com.yhglobal.gongxiao.phjd.sales.dao.SalesContractNumberIndexDao;
import com.yhglobal.gongxiao.phjd.sales.dao.SalesOrderAddressDao;
import com.yhglobal.gongxiao.phjd.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.phjd.sales.dao.SalesOrderItemDao;
import com.yhglobal.gongxiao.phjd.sales.model.*;
import com.yhglobal.gongxiao.phjd.sales.model.bo.SalesOrderCountBO;
import com.yhglobal.gongxiao.phjd.sales.model.bo.SalesOrderListBO;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.utils.BigDecimalUtil;
import com.yhglobal.gongxiao.utils.DateUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 销售订单gRPC实现
 *
 * @author weizecheng
 * @date 2018/8/21 10:12
 */
@Service
public class SalesOrderServiceImpl extends SalesOrderServiceGrpc.SalesOrderServiceImplBase{

    private static final Logger logger = LoggerFactory.getLogger(SalesOrderServiceImpl.class);

    @Autowired
    private  SalesOrderItemDao salesOrderItemDao;
    @Autowired
    private SalesOrderDao salesOrderDao;
    @Autowired
    private SalesOrderAddressDao salesOrderAddressDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private SalesContractNumberIndexDao salesContractNumberIndexDao;
    @Autowired
    private SalesConfig salesConfig;

    /**
     * 获取订单详情
     *
     * @author weizecheng
     * @date 2018/8/27 11:29
     * @param request  rpcHeader 基本信息  salesOrderNo销售订单号
     * @param responseObserver 基本信息
     */
    @Override
    public void getOrderDetailBySalesOrderNo(SalesOrderServiceStructure.CommonSalesOrderRequest request, StreamObserver<SalesOrderServiceStructure.GetOrderByOrderNoResponse> responseObserver) {
        // 头部
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        // 销售订单编号
        String salesOrderNo = request.getSalesOrderNo();
        // 项目Id
        long projectId = request.getProjectId();
        SalesOrderServiceStructure.GetOrderByOrderNoResponse response;
        SalesOrderServiceStructure.GetOrderByOrderNoResponse.Builder  resBuilder =  SalesOrderServiceStructure.GetOrderByOrderNoResponse.newBuilder();
        try{
            logger.info("#traceId={}# [IN][getOrderDetailBySalesOrderNo] params:  salesOrderNo={}",
                    rpcHeader.getTraceId(), salesOrderNo);
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            // 销售订单
            SalesOrder salesOrder = salesOrderDao.getSalesOrderByOrderNo(prefix,salesOrderNo);
            // 设置操作日志
            List<GongxiaoRpc.TraceLog> traceLogs = GrpcCommonUtil.getProtoTraceLog(salesOrder.getTracelog());
            if(traceLogs != null){
                resBuilder.addAllTraceLog(traceLogs);
            }
            // 设置操作日志结束

            //  设置销售订单商品列表
            List<SalesOrderItem> salesOrderItem =  salesOrderItemDao.listBySalesOrderNo(prefix,salesOrderNo);
            salesOrderItem.forEach(item->{
                SalesOrderServiceStructure.SalesOrderItemsResponse itemsResponse =  SalesOrderServiceStructure.SalesOrderItemsResponse.newBuilder()
                        .setCurrencyCode(item.getCurrencyCode())
                        .setProductName(item.getProductName())
                        .setProductCode(item.getProductCode())
                        .setTotalQuantity(item.getTotalQuantity())
                        .setGuidePriceDouble(item.getJdSalesGuidePrice().toString())
//                        .setSupplierDiscountPercentDouble(DoubleScale.keepSixBits(item.getSupplierDiscountPercent()))
//                        .setSupplierDiscountAmountDouble(DoubleScale.keepSixBits(item.getSupplierDiscountAmount()))
                        .setWholesalePriceDouble(item.getJdPurchaseGuidePrice().toString())
                        .setTotalOrderAmountDouble(item.getTotalOrderAmount().toString())
                        .setDeliveredQuantity(item.getDeliveredQuantity())
                        .setSendQuantity(item.getTotalQuantity() - item.getUnhandledQuantity())
                        .build();
                resBuilder.addItems(itemsResponse);
            });

            // 销售订单地址
            SalesOrderAddressDO salesOrderAddressDO = salesOrderAddressDao.getAddressBySalesOrderId(prefix,salesOrder.getSalesOrderId());

            resBuilder.setSalesOrderNo(salesOrderNo)
                    .setSalesContactNo(salesOrder.getSalesContactNo())
                    .setSalesOrderStatus(salesOrder.getSalesOrderStatus())
                    .setTotalOrderAmountDouble(salesOrder.getTotalOrderAmount().toString())
                    .setCouponAmountDouble("0")
                    .setPrepaidAmountDouble("0")
//                    .setPrestoredAmountDouble(DoubleScale.keepTwoBits(salesOrder.getPrestoredAmount()))
                    // 未调用相关接口
                    .setCashAmountDouble(salesOrder.getCashAmount().toString())
                    .setOutBoundTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getOutBoundTime()))
                    .setCreateTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getCreateTime()))
                    .setOrderCheckTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getOrderCheckTime()))
                    .setPaidTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getPaidTime()))
                    .setInformWarehouseTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getInformWarehouseTime()))
                    .setSignTimeStr(GrpcCommonUtil.getProtoParam(salesOrder.getSignTime()))
                    .setSupplierDiscountAmountDouble(BigDecimalUtil.keepTwoBitsToString(salesOrder.getSupplierDiscountAmount()))
                    .setYhDiscountAmountDouble(BigDecimalUtil.keepTwoBitsToString(salesOrder.getYhDiscountAmount()))
                    // 未调用相关接口
                    .setDistributorCouponAmountDouble("0")
                    // 未调用相关接口
                    .setDistributorPrepaidAmountDouble("0");
            resBuilder.setRecipientAddress(GrpcCommonUtil.getProtoParam(salesOrderAddressDO.getArrived()))
                    .setRecipientName(salesOrderAddressDO.getReceiver())
                    .setRecipientMobile(salesOrderAddressDO.getReceiverTel())
                    .setRecipientArrived(salesOrderAddressDO.getReceiverTel());

            logger.info("#traceId={}# [OUT]: get sales order success", rpcHeader.getTraceId());
            response = resBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void getOrderDetailBySalesOrderNoAndProjectCode(SalesOrderServiceStructure.GetOrderItemRequest request, StreamObserver<SalesOrderServiceStructure.GetOrderItemResponse> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        // 获取销售订单编号
        String salesOrderNo = request.getSalesOrderNo();
        // 获取商品编码
        String productCode = request.getProductCode();

        //返回信息设置
        SalesOrderServiceStructure.GetOrderItemResponse response;
        SalesOrderServiceStructure.GetOrderItemResponse.Builder respBuilder = SalesOrderServiceStructure.GetOrderItemResponse.newBuilder();

        long projectId = request.getProjectId();
        logger.info("#traceId={}# [IN][getOrderDetailBySalesOrderNoAndProjectCode] params:  salesOrderNo={}, productCode={}",
                rpcHeader.getTraceId(), salesOrderNo, productCode);
        try {
            // 查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            SalesOrderItem salesOrderItem = salesOrderItemDao.getBySalesOrderNoAndProductCode(prefix,salesOrderNo,productCode);
            if(null == salesOrderItem){
                logger.error("getOrderDetailBySalesOrderNoAndProjectCode phjd no data");
                respBuilder.setReturnCode(ErrorCode.TARGET_DATA_NOT_EXIST.getErrorCode());
                respBuilder.setReturnMsg(ErrorCode.TARGET_DATA_NOT_EXIST.getMessage());
            }else {
                respBuilder.setCurrencyCode(salesOrderItem.getCurrencyCode());
                respBuilder.setSalesGuidePrice(salesOrderItem.getJdSalesGuidePrice().toString());
                respBuilder.setWholesalePrice(salesOrderItem.getWholesalePrice().toString());
            }
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 销售订单列表分页
     *
     * @author weizecheng
     * @date 2018/8/28 9:31
     */
    @Override
    public void listOrderPage(SalesOrderServiceStructure.ListOrderPageRequest request, StreamObserver<SalesOrderServiceStructure.ListOrderPageResponse> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        // 条件 每页的数量
        Integer pageSize = request.getPageSize();
        // 条件 当前页
        Integer pageNum = request.getPage();
        // 项目Id 用于查询表前缀
        Long projectId = request.getProjectId();
        // 条件 销售订单No
        String orderNo = request.getSalesOrderNo();
        // 条件 结束时间
        String endDate = request.getDateEnd();
        // 条件 开始时间
        String createTime = request.getDateStart();
        // 条件 订单状态  状态 0 位查询全部
        Integer orderStatus = request.getStatus();
        // 条件 客户采购单号
        String purchaseNo = request.getPurchaseNo();

        SalesOrderServiceStructure.ListOrderPageResponse response;
        SalesOrderServiceStructure.ListOrderPageResponse.Builder builder = SalesOrderServiceStructure.ListOrderPageResponse.newBuilder();

        try {
            logger.info("#traceId={}# [IN][listOrderPage] params:  projectId={}, orderNo={}, endDate={}, createTime={}, orderStatus={}, pageSize={}, pageNum={},purchaseNo={}",
                    rpcHeader.getTraceId(), projectId, orderNo, endDate, createTime, orderStatus, pageSize, pageNum,purchaseNo);
            // 表前缀
            /// 开发JD项目环境
            // String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            String prefix ="phjd";
            // 启动分页插件
            PageHelper.startPage(pageNum, pageSize);
            // 获取销售订单分页
            List<SalesOrderListBO> salesOrderListBO = salesOrderDao.listSaleOrderByTerm(prefix,0L,orderNo,null,null,createTime,endDate,orderStatus,purchaseNo);

            // 包装分页对象
            PageInfo<SalesOrderListBO> salesOrderList = new PageInfo<>(salesOrderListBO);
            // 各种状态的订单数量
            List<SalesOrderCountBO> salesOrderCount = salesOrderDao.countSalesOrder(prefix);
            // TODO 是否加入为数量为0的订单状态
            // model->rpc
            // 设置分页返回参数
            builder.setPageNum(salesOrderList.getPageNum())
                    .setPageSize(salesOrderList.getPageSize())
                    .setTotal(salesOrderList.getTotal());

            if(salesOrderListBO != null && salesOrderListBO.size() > 0){
                builder.setOrderSize(salesOrderListBO.size());
                salesOrderListBO.forEach(item->{
                    SalesOrderServiceStructure.OrderInfoResponse builderOrder = SalesOrderServiceStructure.OrderInfoResponse.newBuilder()
                            .setSalesOrderStatus(item.getSalesOrderStatus())
                            .setSettlementMode(item.getSettlementMode())
                            .setSalesOrderNo(item.getSalesOrderNo())
                            .setPaymentDays(item.getPaymentDays() == null ? 0 : item.getPaymentDays())
                            .setDistributorName(item.getDistributorName())
                            // 订单总金额设置为双精度
                            .setTotalOrderAmountDouble(item.getTotalOrderAmount().toString())
                            .setTotalQuantity(item.getTotalQuantity())
                            .setUnhandledQuantity(item.getUnhandledQuantity())
                            // 订单实际付款金额设为双精度
                            .setCashAmountDouble(item.getCashAmount().toString())
                            .setCreateTime(item.getCreateTime().getTime())
                            .setPaidTime(GrpcCommonUtil.getProtoParam(item.getPaidTime()))
                            .setOrderAttribute(SalesOrderAttributeEnum.getMessage(item.getSalesOrderAttribute()))
                            .setPurchaseNo(item.getPurchaseNo())
                            .build();
                    builder.addList(builderOrder);
                });
            }
            // 各种状态的订单数量
            salesOrderCount.forEach(count->{
                SalesOrderServiceStructure.SalesOrderCount countBuilder = SalesOrderServiceStructure.SalesOrderCount.newBuilder()
                        .setCount(count.getCount())
                        .setSatus(count.getSalesOrderStatus())
                        .build();
                builder.addCount(countBuilder);
            });

            logger.info("#traceId={}# [OUT]: get sales order list success", rpcHeader.getTraceId());
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 销售订单审核
     *
     * @author weizecheng
     * @date 2018/8/28 9:30
     */
    @Override
    public void reviewSalesOrder(SalesOrderServiceStructure.ReviewSalesOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        // 基本头部参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        // 销售订单号
        String salesOrderNo = request.getSalesOrderNo();
        // 项目Id
        long projectId = request.getProjectId();
        // 各个销售订单商品的信息
        List<SalesOrderServiceStructure.ReviewSalesOrderItem> reviewSalesOrderItems = request.getReviewItemList();
        // 此处转成HashMap是为了方便下面的查询 以productCode为key 剩下的对象为value
        HashMap<String,SalesOrderServiceStructure.ReviewSalesOrderItem> stringReviewSalesOrderItemHashMap = new HashMap<>(reviewSalesOrderItems.size());
        //同时需要取出商品 List<code>读取库存
        List<String> productList = new ArrayList<>(reviewSalesOrderItems.size());

        reviewSalesOrderItems.forEach(items->{
            // 用于查询库存
            productList.add(items.getProductCode().trim());
            stringReviewSalesOrderItemHashMap.put(items.getProductCode().trim(),items);
        });
        GongxiaoRpc.RpcResult response;
        try {
            logger.info("#traceId={}# [IN][reviewSalesOrder] params: salesOrderNo={}, couponAmountDouble={},reviewSalesOrderItems={}",
                    rpcHeader.getTraceId(), salesOrderNo, reviewSalesOrderItems);
            // 查询表前缀
//            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            String prefix = "phjd";
            // 更新是否成功
            SalesOrder salesOrder = salesOrderDao.getSalesOrderByOrderNo(prefix, salesOrderNo);
            // 订单不存在 或者 订单状态已改变 不未审核状态
            if( !SalesOrderStatusEnum.INIT.getStatus().equals(salesOrder.getSalesOrderStatus())){
                logger.info("#traceId={}# [OUT]: order can NOT review! salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
                response = GrpcCommonUtil.fail(ErrorCode.SALES_ORDER_STATUS_ERRO.getErrorCode(),ErrorCode.SALES_ORDER_STATUS_ERRO.getMessage());
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            // TODO 京东项目下默认为 信用付款 账期时间为45天
            Integer salesOrderStatus =SalesOrderStatusEnum.PAID.getStatus();;
            // 操作日志设置
            TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "销售订单审核");
            String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
            // 获取各个分仓的库存
            List<SalesOrderItem> salesOrderItemList = salesOrderItemDao.listBySalesOrderNo(prefix,salesOrderNo);
            // 商品实际 供应价格
            BigDecimal totalPrice = BigDecimal.ZERO;
            // 商品实际发货数量
            int totalCount = 0;
            int shortageReasonQuantity = 0;

            for (SalesOrderItem salesOrderItem : salesOrderItemList) {
                // 需要找出reviewSalesOrderItems中对应的数量
                if (stringReviewSalesOrderItemHashMap.containsKey(salesOrderItem.getProductCode())) {
                    // 开始一系列的数量判断并且设置值
                    // TODO 与仓库库存进行比较  考虑在 web端直接比较 在传入到RPC中 省去在RPC服务中调用仓库服务 (调用服务可能失败)
                    SalesOrderServiceStructure.ReviewSalesOrderItem reviewSalesOrderItem = stringReviewSalesOrderItemHashMap.get(salesOrderItem.getProductCode());
                    // 前段写入数量等于京东采购数量
                    int count = reviewSalesOrderItem.getCount();
                    // 销售订单实际数量
                    totalCount += count;
                    // 价格与数量相乘 订单商品单个总价
                    BigDecimal itemPrice = BigDecimalUtil.multiplication(salesOrderItem.getJdPurchaseGuidePrice(),count);
                    // 销售订单实际总价
                    totalPrice = totalPrice.add(itemPrice);
                    // 缺货状态
                    Integer itemStatus;
                    // 缺货原因
                    String shortageReason = null;
                    if (salesOrderItem.getJdTotalQuantity().equals(reviewSalesOrderItem.getCount())) {
                        // 正常状态
                        itemStatus = SalesOrderItemStatusEnum.NORMAL_OUT.getStatus();
                    }else {
                        itemStatus = SalesOrderItemStatusEnum.STOCK_OUT.getStatus();
                        shortageReason = reviewSalesOrderItem.getShortageReason();
                        shortageReasonQuantity +=(salesOrderItem.getJdTotalQuantity()-reviewSalesOrderItem.getCount());
                    }
                    // 更新订单商品
                   salesOrderItemDao.updateReviewSalesOrderItem(prefix,salesOrderItem.getSalesOrderItemId(),itemStatus,shortageReason,count,itemPrice,salesOrderItem.getDataVersion());
                } else {
                    logger.info(" compensation[IN][Unset quantity productCode]:productCode={}", salesOrderItem.getProductCode());
                }
            }
            // 进行主表更新
            // TODO 可以做循环 乐观锁 确保更新
            salesOrderDao.updateReviewSalesOrder(prefix,salesOrder.getDataVersion(),salesOrder.getSalesOrderId(),totalPrice,totalCount,salesOrderStatus,shortageReasonQuantity,new Date(),traceLogs);

            logger.info("#traceId={}# [OUT]: reviewSalesOrder sales order success. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            //model -> rpcModel
            // TODO 调用EDI 生产采购确认单 返回JD
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 销售订单驳回
     *
     * @author weizecheng
     * @date 2018/8/28 9:32
     */
    @Override
    public void rejectedSalesOrder(SalesOrderServiceStructure.CommonSalesOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        // 基本头部参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        // 销售订单号
        String salesOrderNo = request.getSalesOrderNo();
        // 项目Id
        long projectId = request.getProjectId();
        // RPC返回值
        GongxiaoRpc.RpcResult response;
        try {
            logger.info("#traceId={}# [IN][rejectSalesOrder] params: salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);

            //            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            String prefix = "phjd";
            // 更新是否成功
            SalesOrder salesOrder = salesOrderDao.getSalesOrderByOrderNo(prefix, salesOrderNo);
            // 只有订单状态为1的时候可以驳回
            if( salesOrder == null || !SalesOrderStatusEnum.INIT.getStatus().equals(salesOrder.getSalesOrderStatus())){
                // 订单不存在 或者 订单状态已改变 不未审核状态
                logger.info("#traceId={}# [OUT]: order can NOT reject! salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
                response = GrpcCommonUtil.fail(ErrorCode.SALES_ORDER_STATUS_ERRO.getErrorCode(),ErrorCode.SALES_ORDER_STATUS_ERRO.getMessage());
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            // 驳回操作日志
            TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "驳回");
            String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
            // 设置驳回状态 驳回时间 操作日志等等
            int update = salesOrderDao.updateRejectedSalesOrder(prefix,salesOrder.getDataVersion(),salesOrder.getSalesOrderId(),SalesOrderStatusEnum.REJECTED.getStatus(),new Date(),traceLogs);
            if (update != 1) {
                logger.error("FAILED to Rejected salesOrder. orderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to Rejected salesOrder");
            }
            logger.info("#traceId={}# [OUT]: rejectedSalesOrder sales order success. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            //model -> rpcModel
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] Rejected errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 创建销售订单
     *
     * @author weizecheng
     * @date 2018/8/31 17:38
     */
    @Override
    public void insertSalesOrder(SalesOrderServiceStructure.CreateSalesOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        /**jd主订单数据     开始*/
        // DocumentNumber TODO  purchaseNo 总采购号
        String purchaseNo = "";
        // TotalCategory  TODO jdSkuNumber SKU数量
        Integer jdSkuNumber = 0;
        // TotalCostAmount TODO totalOrderAmount 采购总价
        BigDecimal totalOrderAmount = new BigDecimal(0);
        // PurchaseDate TODO purchaseTime  采购日期
        Date purchaseTime = new Date();
        // RequiredArrivalDate TODO requiredArrivalTime 要求到货日期
        Date requiredArrivalTime = new Date();
        // orderAttribute TODO salesOrderAttribute 订单属性
        String salesOrderAttribute = "";
        // TotalQuantity TODO totalQuantity  采购总数量
        Integer totalQuantity = 0;
        /**jd主订单数据     结束*/

        /**jd销售订单地址   开始*/
        // DistributionCenter  TODO distributionCenter 配送中心名称
        String distributionCenter = "";
        // DistributionCenterID TODO distributionCenterId 配送中心编码
        String distributionCenterId ="";
        // Warehouse TODO warehouse 仓库名称
        String warehouse = "";
        // WarehouseID TODO warehouseId 仓库编码
        String warehouseId = "";
        // receiver TODO receiver 收货人
        String receiver ="";
        // receiverTel TODO receiverTel 收货人电话
        String receiverTel = "";
        // ReceivingAddress TODO receivingAddress 收货详细地址
        String receivingAddress = "";
        // Arrived TODO arrived 最终抵达的城市
        String arrived  = "";
        /**jd销售订单地址   结束*/

        /**jd订单商品详细情况   多个详细 开始*/
        // DocumentNumber TODO  jdPurchaseNo 总采购号
        String jdPurchaseNo = "";
        // CurrentRecordNumber TODO jdCurrentRecordNumber jd细目的序列号
        Integer jdCurrentRecordNumber = 0;
        // BuyerProductID TODO jdSkuCode 京东SKU编码
        String jdSkuCode = "";
        // ProductCode TODO productCode 系统商品编码获取基础数据
        String productCode ="";
        // ProductName TODO productName 商品名称设置
        String productName ="";
        // Quantity TODO jdTotalQuantity 京东单个商品数量
        Integer jdTotalQuantity = 0;
        // CostPrice TODO jdPurchaseGuidePrice 京东采购价
        BigDecimal jdPurchaseGuidePrice = new BigDecimal(0);
        /**jd订单商品详细情况   多个详细 结束*/

        GongxiaoRpc.RpcHeader rpcHeader = null;
        String prefix ="phjd";
        Long projectId = 0L;
        ProjectStructure.Project  project = commonService.getByProjectIdResp(rpcHeader,projectId.toString());
        //  SalesOrderNo 销售订单编号
        String salesOrderNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.SALES_ORDER_NO);
        // 获取当前时间的YYYYMMDD
        String date = DateUtil.getSafeIndexDate(LocalDateTime.now());

        // 乐观锁重试次数
        int optimistic = 3;
        // 销售合同号尾数 如20180830-1 20180830-2 20180830-3 这里考虑使用缓存来做更好 不用使用数据库维护
        int index = 1;
        while (optimistic-- > 0){
            SalesContractNumberIndex salesContractNumberIndex = salesContractNumberIndexDao.getByProjectId(prefix,projectId);
            if (date.equals(salesContractNumberIndex.getLastUpdateDate())){
                index = salesContractNumberIndex.getIndexNo()+1;
            }
            int update = salesContractNumberIndexDao.updateIndex(prefix,salesContractNumberIndex.getDataVersion(),salesContractNumberIndex.getId(),index,date);
            if(UpdateStatus.UPDATE_SUCCESS.getStatus().equals(update)){
                break;
            }
        }
        // salesContactNo 销售合同号
        String salesContractNo = date + "-" + index;
        // settlementMode 结算模式 Jd项目默认为信用付款
        Integer settlementMode = SalesOrderSettlementModeEnum.CREDIT.getStatus();
        // 操作日志
        TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "新建");
        String traceLogs = TraceLogUtil.appendTraceLog(null, traceLog);

        BigDecimal zero = BigDecimal.ZERO;
        // 销售订单明细 插入
        int i =0;
        while(i>0){
            ProductStructure.GetByProductModelResp getByProductModel = commonService.getByProductModel(rpcHeader,projectId,productCode);
            if( null != getByProductModel && null != getByProductModel.getProductBusiness()){
                ProductStructure.ProductBusiness product =getByProductModel.getProductBusiness();
                SalesOrderItem salesOrderItem = new SalesOrderItem();
                // 设置EAS code
                salesOrderItem.setEasCode(product.getEasCode());
                // 销售状态
                salesOrderItem.setItemStatus(0);
                // 销售订单编号
                salesOrderItem.setSalesOrderNo(salesOrderNo);

                /** 越海商品信息 */
                // 销售订单商品编码
                salesOrderItem.setProductCode(productCode);
                // 销售指导价
                salesOrderItem.setJdSalesGuidePrice(BigDecimalUtil.keepSixBits(product.getSaleGuidePrice()));
                // 商品Id
                salesOrderItem.setProductId(product.getProductBusinessId());
                // 商品名称
                salesOrderItem.setProductName(product.getProductName());
                // 出货价
                salesOrderItem.setWholesalePrice(BigDecimalUtil.keepSixBits(product.getOutPrice()));
                /** 越海商品信息 */

                /** 京东商品信息 */
                salesOrderItem.setTotalOrderAmount(BigDecimalUtil.multiplication(jdPurchaseGuidePrice,jdTotalQuantity));
                salesOrderItem.setJdTotalQuantity(jdTotalQuantity);
                salesOrderItem.setJdPurchaseGuidePrice(jdPurchaseGuidePrice);
                salesOrderItem.setJdSkuCode(jdSkuCode);
                salesOrderItem.setJdPurchaseNo(jdPurchaseNo);
                salesOrderItem.setJdCurrentRecordNumber(jdCurrentRecordNumber);
                // 京东备注 TODO 不为空就传入
                salesOrderItem.setJdComments("");
                /** 京东商品信息 */

                /** TODO 疑问信息 */
                // 各种代垫 毛利统一 设置为0
                salesOrderItem.setRetailPrice(zero);
                salesOrderItem.setGeneratedPrepaid(zero);
                salesOrderItem.setShouldReceiveGrossProfit(zero);
                salesOrderItem.setReceivedGrossProfit(zero);
                // 使用垫付金额
                salesOrderItem.setUsedPrepaidAmount(zero);
                /** TODO 疑问信息 */

                /** 基本商品信息 */
                salesOrderItem.setDeliveredQuantity(0);
                salesOrderItem.setInTransitQuantity(0);
                salesOrderItem.setCanceledQuantity(0);
                salesOrderItem.setReturnedQuantity(0);
                salesOrderItem.setUnhandledQuantity(0);
                salesOrderItem.setDataVersion(0L);
                salesOrderItem.setTotalQuantity(0);
                salesOrderItem.setCurrencyCode("CNY");
                salesOrderItem.setCreateTime(new Date());
                salesOrderItem.setTracelog(traceLogs);
                salesOrderItem.setOngoingOutboundOrderInfo("[]");
                salesOrderItem.setFinishedOutboundOrderInfo("[]");
                salesOrderItemDao.insertSalesOrderItem(prefix, salesOrderItem);
                logger.info("#traceId={}# [OUT]: create sales order item  success productCode={},salesOrderNo={}", rpcHeader.getTraceId(),productCode,salesOrderNo);
            } else {
                // 直接返回
                return;
            }
        }
        // 二、创建销售单
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setPaymentDays(salesConfig.getPaymentDays());
        salesOrder.setSettlementMode(SalesOrderSettlementModeEnum.CREDIT.getStatus());
        salesOrder.setSalesOrderNo(salesOrderNo);
        salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.INIT.getStatus());
        salesOrder.setDataVersion(0L);
        // 项目相关
        salesOrder.setProjectId(projectId);
        salesOrder.setProjectName(project.getProjectName());
        // 仓库相关
        salesOrder.setWarehouseId(0L);
        salesOrder.setWarehouse("");
        // 品牌
        salesOrder.setBrandId(0L);
        salesOrder.setBrandName("");
        //
        salesOrder.setSupplierId(project.getSupplierId());
        salesOrder.setSupplierName(project.getSupplierName());

        // jd
        salesOrder.setJdSkuNumber(jdSkuNumber);
        salesOrder.setTotalQuantity(totalQuantity);

        // 实发
        salesOrder.setTotalCostQuantity(0);
//        totalSalesAmount
        salesOrder.setTotalOrderAmount(totalOrderAmount);
        salesOrder.setCashAmount(zero);

//        shippingExpense
//        shippingExpensePaidBy

        salesOrder.setCurrencyCode("CNY");
        salesOrder.setPrepaidAmount(zero);

        salesOrder.setYhDiscountAmount(zero);
        salesOrder.setSupplierDiscountAmount(zero);
        salesOrder.setDeliveredQuantity(0);
        salesOrder.setInTransitQuantity(0);
        salesOrder.setCanceledQuantity(0);
        salesOrder.setReturnedQuantity(0);
        salesOrder.setUnhandledQuantity(0);
        salesOrder.setShortageReasonQuantity(0);
        salesOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));
        salesOrder.setCreatedByName(rpcHeader.getUsername());

//        createTime
//        outBoundTime
//        orderCheckTime
//        rejectTime
//        paidTime
//        informWarehouseTime
//        signTime
//        lastUpdateTime
        salesOrder.setSettlementMode(settlementMode);
        salesOrder.setSalesOrderNo(salesOrderNo);
        salesOrder.setComments("");
//        comments
        salesOrder.setMarketingChannel(SalesMarketingChannelEnum.WEB.getStatus());
        salesOrder.setPaymentChannel(1);
//        paymentChannel
        // TODO
        salesOrder.setPaymentDays(45);
        salesOrder.setCreditRemark("");
//        cashFlowNo
//        prepaidFlowNo
//        returnCashFlowNo
//        returnPrepaidFlowNo

//        easOrderNo
//        easOrderId

        salesOrder.setTotalGeneratedPrepaid(zero);
        salesOrder.setSyncEas(SalesOrderSyncEnum.INIT.getStatus());
        salesOrder.setShouldReceiveGrossProfit(zero);
        salesOrder.setReceivedGrossProfit(zero);
        salesOrder.setTotalQuantity(totalQuantity);

        salesOrder.setPurchaseNo(purchaseNo);
        salesOrder.setSalesOrderAttribute(salesOrderAttribute);
        salesOrder.setPurchaseTime(purchaseTime);
        salesOrder.setRequiredArrivalTime(requiredArrivalTime);
        salesOrder.setOngoingOutboundOrderInfo("[]");
        salesOrder.setFinishedOutboundOrderInfo("[]");
        salesOrder.setTracelog(traceLogs);
        salesOrder.setSalesContactNo(salesContractNo);
//        distributorId
//        distributorName
        Long salesOrderId =  salesOrderDao.insertSalesOrder(prefix,salesOrder);


        SalesOrderAddressDO salesOrderAddressDO = new SalesOrderAddressDO();
        salesOrderAddressDO.setArrived(arrived);
        salesOrderAddressDO.setDistributionCenter(distributionCenter);
        salesOrderAddressDO.setDistributionCenterId(distributionCenterId);
        salesOrderAddressDO.setWarehouse(warehouse);
        // TODO 这里要改下
        salesOrderAddressDO.setWarehouseGln(warehouseId);
        salesOrderAddressDO.setSalesOrderId(salesOrderId);
        // TODO 这里要改下
        salesOrderAddressDO.setWarehouseId(warehouseId);
        salesOrderAddressDO.setReceiver(receiver);
        salesOrderAddressDO.setReceiverTel(receiverTel);
        salesOrderAddressDO.setReceivingAddress(receivingAddress);
        salesOrderAddressDO.setDataVersion(0L);
        salesOrderAddressDO.setShippingMode(0);
        salesOrderAddressDO.setCreateTime(new Date());
        salesOrderAddressDao.insertSalesOrderAddress(prefix,salesOrderAddressDO);

    }
}
