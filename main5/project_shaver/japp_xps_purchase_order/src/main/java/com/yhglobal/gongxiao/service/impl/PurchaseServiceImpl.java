package com.yhglobal.gongxiao.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.PurchaseStatus;
import com.yhglobal.gongxiao.dao.PurchaseOrderDao;
import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.dao.PurchaseScheduleDeliveryDao;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierServiceGrpc;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure;
import com.yhglobal.gongxiao.purchase.microservice.PurchaseServiceGrpc;
import com.yhglobal.gongxiao.purchase.microservice.PurchaseStructure;
import com.yhglobal.gongxiao.status.EasSynStatus;
import com.yhglobal.gongxiao.status.PurchaseEasStatus;
import com.yhglobal.gongxiao.status.PurchaseStatusEnum;
import com.yhglobal.gongxiao.status.ShippingModeStatus;
import com.yhglobal.gongxiao.task.SyncPlanInboundToWmsTask;
import com.yhglobal.gongxiao.task.SyncPurchaseOrderToEasTask;
import com.yhglobal.gongxiao.util.*;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Service
public class PurchaseServiceImpl extends PurchaseServiceGrpc.PurchaseServiceImplBase {

    private Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    @Autowired
    PurchaseOrderDao purchaseOrderDao; //采购单

    @Autowired
    PurchaseOrderItemDao purchaseOrderItemDao; //采购明细

    @Autowired
    PurchaseScheduleDeliveryDao purchaseScheduleDeliveryDao;//采购方记录推送的预约入库的数据

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 通过采购单号获取采购详情
     * <p>
     * rpcHeader rpc调用的header
     * purchaseOrderNo 采购单号
     *
     * @return
     */
    public void getPurchaseDetail(PurchaseStructure.GetPurchaseDetailReq request,
                                  StreamObserver<PurchaseStructure.GetPurchaseDetailResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String purchaseOrderNo = request.getPurchaseOrderNo();
        long projectId = request.getProjectId();

        Map<Integer, String> map = new HashMap();
        map.put(1, "普通采购");
        map.put(2, "货补采购");
        map.put(3, "赠品采购");

        PurchaseStructure.GetPurchaseDetailResp response = null; //保存返回的值
        PurchaseStructure.GetPurchaseDetailResp.Builder respBuilder = PurchaseStructure.GetPurchaseDetailResp.newBuilder(); //每个proto对象都需要从builder构建出来

        logger.info("#traceId={}# [IN][getCurrencyByCode] params: {purchaseOrderNo}={}  ", rpcHeader.getTraceId(), purchaseOrderNo);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(tablePrefix,purchaseOrderNo);
            List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemDao.selectByOrderNo(tablePrefix,purchaseOrderNo);

            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setWarehouseId(purchaseOrder.getWarehouseId() + "")
                    .build();
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseServiceBlockingStub = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.GetWarehouseByIdResp getWarehouseByIdResp = warehouseServiceBlockingStub.getWarehouseById(getWarehouseByIdReq);
            WarehouseStructure.Warehouse warehouseInfo = getWarehouseByIdResp.getWarehouse();

            //设置订单信息
            PurchaseStructure.PurchaseOrderDetailVo.Builder purchaseOrderDetail = PurchaseStructure.PurchaseOrderDetailVo.newBuilder();
            purchaseOrderDetail.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
            purchaseOrderDetail.setOrderStatus(purchaseOrder.getOrderStatus());
            purchaseOrderDetail.setProjectId(purchaseOrder.getProjectId());
            purchaseOrderDetail.setProjectName(purchaseOrder.getProjectName());
//            purchaseOrderDetail.setBrandId(purchaseOrder.getBrandId());
//            purchaseOrderDetail.setBrandName(purchaseOrder.getBrandName());
            purchaseOrderDetail.setSupplierId(GrpcCommonUtil.getProtoParam(purchaseOrder.getSupplierId()));
            purchaseOrderDetail.setSupplierName(GrpcCommonUtil.getProtoParam(purchaseOrder.getSupplierName()));
            purchaseOrderDetail.setPaymentMode(GrpcCommonUtil.getProtoParam(purchaseOrder.getPaymentMode()));
            purchaseOrderDetail.setShippingMode(GrpcCommonUtil.getProtoParam(purchaseOrder.getShippingMode()));
            if (purchaseOrder.getExpectedInboundDate() != null) {
                purchaseOrderDetail.setExpectedInboundDate(purchaseOrder.getExpectedInboundDate().getTime());
            }
            if (purchaseOrder.getOrderDeadlineDate() != null) {
                purchaseOrderDetail.setOrderDeadlineDate(purchaseOrder.getOrderDeadlineDate().getTime());
            }
            if (purchaseOrder.getCreateTime() != null) {
                purchaseOrderDetail.setBusinessDate(purchaseOrder.getBusinessDate().getTime());
            }
            purchaseOrderDetail.setWarehouseId(GrpcCommonUtil.getProtoParam(purchaseOrder.getWarehouseId()));
            purchaseOrderDetail.setWarehouseName(GrpcCommonUtil.getProtoParam(purchaseOrder.getWarehouseName()));
            if (warehouseInfo != null) {
                purchaseOrderDetail.setAddress(warehouseInfo.getStreetAddress());
            }
            purchaseOrderDetail.setBrandOrderNo(GrpcCommonUtil.getProtoParam(purchaseOrder.getBrandOrderNo()));
            purchaseOrderDetail.setContractReferenceOrderNo(GrpcCommonUtil.getProtoParam(purchaseOrder.getContractReferenceOrderNo()));
            purchaseOrderDetail.setRemark(GrpcCommonUtil.getProtoParam(purchaseOrder.getRemark()));
            purchaseOrderDetail.setPurchaseCategory(GrpcCommonUtil.getProtoParam(purchaseOrder.getPurchaseCategory()));
            purchaseOrderDetail.setTotalQuantity(CommonUtil.intFormatComma(purchaseOrder.getTotalQuantity()));
            //设置各金额
            purchaseOrderDetail.setCouponAmountUse(DoubleScale.keepTwoBits(purchaseOrder.getCouponAmountUse()));
            purchaseOrderDetail.setPrepaidAmountUse(DoubleScale.keepTwoBits(purchaseOrder.getPrepaidAmountUse()));
            purchaseOrderDetail.setPurchaseGuideAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseGuideAmount()));
            purchaseOrderDetail.setPurchasePrivilegeAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchasePrivilegeAmount()));
            purchaseOrderDetail.setReturnCash(DoubleScale.keepTwoBits(purchaseOrder.getReturnCash()));
            purchaseOrderDetail.setPurchaseShouldPayAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseShouldPayAmount()));
            purchaseOrderDetail.setPurchaseActualPayAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseActualPayAmount()));
            purchaseOrderDetail.setPurchaseType(map.get(purchaseOrder.getPurchaseType()));

            //设置操作日记
            String tracelog = purchaseOrder.getTracelog();
            List<OperateHistoryRecord> traceLogList;
            if (tracelog != null && !"".equals(tracelog)) {
                traceLogList = JSON.parseObject(tracelog, new TypeReference<List<OperateHistoryRecord>>() {});
            } else {
                traceLogList = new ArrayList<>();
            }
            for (OperateHistoryRecord operateHistoryRecord : traceLogList) {
                operateHistoryRecord.setOperateTimeString(DateUtil.longDateToFXString(operateHistoryRecord.getOperateTime()));
            }
            for (OperateHistoryRecord operateHistoryRecord : traceLogList) {
                PurchaseStructure.OperateHistoryRecord record = PurchaseStructure.OperateHistoryRecord.newBuilder()
                        .setOperateStatus(operateHistoryRecord.getOperateStatus())
                        .setOperateTime(operateHistoryRecord.getOperateTime().getTime())
                        .setOperateFunction(operateHistoryRecord.getOperateFunction())
                        .setOperateId(operateHistoryRecord.getOperateId())
                        .setOperateName(operateHistoryRecord.getOperateName())
                        .setOperateTimestring(operateHistoryRecord.getOperateTimeString())
                        .build();
                purchaseOrderDetail.addTraceLogList(record);
            }

            //设置货品明细
            for (PurchaseOrderItem item : purchaseOrderItems) {
                String factoryPrice = "";
                if (item.getFactoryPrice() == null) {
                    factoryPrice = "0.000000";
                } else {
                    factoryPrice = DoubleScale.keepSixBits(item.getFactoryPrice());
                }
                PurchaseStructure.PurchaseItemVo purchaseItemVo = PurchaseStructure.PurchaseItemVo.newBuilder()
                        .setPurchaseItemId(item.getPurchaseItemId())
                        .setOrderStatus(item.getOrderStatus())
                        .setPurchaseOrderNo(item.getPurchaseOrderNo())
                        .setProductId(GrpcCommonUtil.getProtoParam(item.getProductId()))
                        .setProductCode(GrpcCommonUtil.getProtoParam(item.getProductCode()))
                        .setProductName(GrpcCommonUtil.getProtoParam(item.getProductName()))
                        .setProductUnit(GrpcCommonUtil.getProtoParam(item.getProductUnit()))
                        .setWarehouseId(GrpcCommonUtil.getProtoParam(item.getWarehouseId()))
                        .setWarehouseName(GrpcCommonUtil.getProtoParam(item.getWarehouseName()))
                        .setShippingMode(GrpcCommonUtil.getProtoParam(item.getShippingMode()))
                        .setDiscountPercent("0.000000")
                        .setCouponAmount(DoubleScale.keepTwoBits(item.getCouponAmount()))
                        .setPrepaidAmount(DoubleScale.keepTwoBits(item.getPrepaidAmount()))
                        .setCashAmount(DoubleScale.keepTwoBits(GrpcCommonUtil.getProtoParam(item.getCashAmount())))
                        .setGuidePrice(DoubleScale.keepSixBits(item.getGuidePrice()))
                        .setPurchasePrice(DoubleScale.keepSixBits(item.getPurchasePrice()))
                        .setCostPrice(DoubleScale.keepSixBits(item.getCostPrice()))
                        .setPurchaseNumber(item.getPurchaseNumber())
                        .setFactoryPrice(factoryPrice).build();
                purchaseOrderDetail.addItemList(purchaseItemVo);
            }
            logger.info("#traceId={}# [OUT] getPurchaseDetail success", rpcHeader.getTraceId());
            response = respBuilder.setPurchaseOrderDetailVo(purchaseOrderDetail).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 获取采购单的货品明细信息 预约入库时会调用 //没用到
     * <p>
     * rpcHeader rpc调用的header
     * purchaseOrderNo
     *
     * @return
     */
    @Override
    public void getItemList(PurchaseStructure.GetItemListReq request,
                            StreamObserver<PurchaseStructure.GetItemListResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String purchaseOrderNo = request.getPurchaseOrderNo();
        long projectId = request.getProjectId();

        PurchaseStructure.GetItemListResp resp;
        PurchaseStructure.GetItemListResp.Builder getItemListBuilder = PurchaseStructure.GetItemListResp.newBuilder();

        logger.info("#traceId={}# [IN][getCurrencyByCode] params: purchaseOrderNo={}  ", rpcHeader.getTraceId(), purchaseOrderNo);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemDao.selectByOrderNo(tablePrefix,purchaseOrderNo);
            if (purchaseOrderItems.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to getItemList: purchaseOrderItems=null ", rpcHeader.getTraceId());
            } else {
                logger.info("#traceId={}# [OUT] getItemList success: purchaseOrderItems.size={} ", rpcHeader.getTraceId(), purchaseOrderItems.size());
            }
            for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
                PurchaseStructure.PurchaseOrderItem purchaseOrderItem1 = PurchaseStructure.PurchaseOrderItem.newBuilder()
                        .setPurchaseItemId(purchaseOrderItem.getPurchaseItemId())
                        .setOrderStatus(purchaseOrderItem.getOrderStatus())
                        .setPurchaseNumber(purchaseOrderItem.getPurchaseNumber())
                        .setProductId(purchaseOrderItem.getProductId())
                        .setProductCode(purchaseOrderItem.getProductCode())
                        .setProductName(purchaseOrderItem.getProductName())
                        .setProductUnit(purchaseOrderItem.getProductUnit())
                        .setWarehouseId(purchaseOrderItem.getWarehouseId())
                        .setWarehouseName(purchaseOrderItem.getWarehouseName())
                        .setShippingMode(purchaseOrderItem.getShippingMode())
                        .setCouponAmount(purchaseOrderItem.getCouponAmount())
                        .setPrepaidAmount(purchaseOrderItem.getCouponAmount())
                        .setGuidePrice(purchaseOrderItem.getGuidePrice())
                        .setPurchasePrice(purchaseOrderItem.getPurchasePrice())
                        .setCostPrice(purchaseOrderItem.getCostPrice())
                        .setCouponBasePrice(purchaseOrderItem.getCouponBasePrice())
                        .setFactoryPrice(purchaseOrderItem.getFactoryPrice())
                        .setPurchaseNumber(purchaseOrderItem.getPurchaseNumber())
                        .setInTransitQuantity(purchaseOrderItem.getInTransitQuantity())
                        .setInStockQuantity(purchaseOrderItem.getInStockQuantity())
                        .setImperfectQuantity(purchaseOrderItem.getImperfectQuantity())
                        .setCanceledQuantity(purchaseOrderItem.getCanceledQuantity())
                        .setReturnedQuantity(purchaseOrderItem.getReturnedQuantity())
                        .setOngoingInboundOrderInfo(purchaseOrderItem.getOngoingInboundOrderInfo())
                        .setFinishedInboundOrderInfo(purchaseOrderItem.getFinishedInboundOrderInfo())
                        .setDataVersion(purchaseOrderItem.getDataVersion())
                        .setCreateTime(DateUtil.getTime(purchaseOrderItem.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(purchaseOrderItem.getLastUpdateTime()))
                        .setTracelog(purchaseOrderItem.getTracelog())
                        .setEasEntryId(purchaseOrderItem.getEasEntryId())
                        .setEasMateriaCode(purchaseOrderItem.getEasMateriaCode())
                        .build();
                getItemListBuilder.addPurchaseOrderItemList(purchaseOrderItem1);
            }
            responseObserver.onNext(getItemListBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 获取采购订单信息  采购单管理页的列表
     * <p>
     * rpcHeader rpc调用的header
     * purchaseOrderNo 采购单号
     * brandId 品牌ID
     * warehouseId 仓库ID
     *
     * @return
     */
    @Override
    public void getPurchaseOrderList(PurchaseStructure.GetPurchaseOrderListReq request,
                                     StreamObserver<PurchaseStructure.GetPurchaseOrderListResp> responseObserver) {

        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String projectId = request.getProjectId();
        String purchaseOrderNo = request.getPurchaseOrderNo();
        String supplierId = request.getBrandId();
        String warehouseId = request.getWarehouseId();
        String productCode = request.getProductCode();
        String requireArrivalDate = request.getRequireArrivalDate();
        String arrivalDeadline = request.getArrivalDeadline();
        String businessDate = request.getBusinessDate();
        int pageNumber = request.getPageNumber();
        int pageSize = request.getPageSize();

        PurchaseStructure.GetPurchaseOrderListResp.Builder getPurchaseOrderListResp = PurchaseStructure.GetPurchaseOrderListResp.newBuilder();
        PurchaseStructure.GetPurchaseOrderListResp response = null;

        logger.info("#traceId={}# [IN][getPurchaseOrderList] params: projectId={}, purchaseOrderNo={}, brandId={}, " +
                        "warehouseId={}, productCode={}, requireArrivalDate={}, arrivalDeadline={}, businessDate={}  ",
                rpcHeader.getTraceId(), projectId, purchaseOrderNo, warehouseId, productCode, requireArrivalDate, arrivalDeadline, businessDate);

        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, Long.parseLong(projectId));
            PageHelper.startPage(pageNumber, pageSize);
            List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.getPurchaseOrderList(tablePrefix,
                    purchaseOrderNo,
                    supplierId,
                    warehouseId,
                    productCode,
                    requireArrivalDate,
                    arrivalDeadline,
                    businessDate);
            PageInfo<PurchaseOrder> purchaseOrderPageInfo = new PageInfo<>(purchaseOrderList);

            PurchaseStructure.PageInfoPurchaseOrderList.Builder pageInfoPurchaseBuilder = PurchaseStructure.PageInfoPurchaseOrderList.newBuilder();

            for (PurchaseOrder purchaseOrder : purchaseOrderList) {
                int paymentMode = 0;
                if (purchaseOrder.getPaymentMode() != null) {
                    paymentMode = purchaseOrder.getPaymentMode();
                }
                Byte orderStatus = purchaseOrder.getOrderStatus();
                String purchaseOrderStatus = "";
                if (orderStatus == PurchaseStatusEnum.DRAFT.getCode()) { //web端展示的值 和 枚举定义的值 不一样，这里硬编码
                    purchaseOrderStatus = "草稿";
                } else if (orderStatus == PurchaseStatusEnum.COMMIT.getCode()) {
                    purchaseOrderStatus = "待发货";
                } else if (orderStatus == PurchaseStatusEnum.ALREADY_PAY.getCode()) {
                    purchaseOrderStatus = "待发货";
                } else if (orderStatus == PurchaseStatusEnum.ALREADY_SHIPPING.getCode()) {
                    purchaseOrderStatus = "待收货";
                } else if (orderStatus == PurchaseStatusEnum.PART_INBOUND.getCode()) {
                    purchaseOrderStatus = "部分收货";
                } else if (orderStatus >= PurchaseStatusEnum.NORMAL_COMPLETE.getCode()
                        && orderStatus < PurchaseStatusEnum.CANCEL.getCode()) {
                    purchaseOrderStatus = "交易成功";
                } else if (orderStatus == PurchaseStatusEnum.CANCEL.getCode()) {
                    purchaseOrderStatus = "已取消";
                }
                boolean enableEdit = false; //根据订单状态来设定是否可编辑
                // 显示编辑
                if (orderStatus == PurchaseStatusEnum.DRAFT.getCode()) {
                    enableEdit = true;
                }
                boolean enableCancle = false;
                //显示作废
                if (orderStatus == PurchaseStatusEnum.ALREADY_PAY.getCode()
                        || orderStatus == PurchaseStatusEnum.DRAFT.getCode()
                        || orderStatus == PurchaseStatusEnum.COMMIT.getCode()
                        ) {
                    enableCancle = true;
                }
                boolean enablePlanInstrock = false;
                //显示预约入库
                if (orderStatus == PurchaseStatusEnum.ALREADY_PAY.getCode()
                        || orderStatus == PurchaseStatusEnum.ALREADY_SHIPPING.getCode()
                        || orderStatus == PurchaseStatusEnum.PART_INBOUND.getCode()) {
                    int purchaseNumber = purchaseOrder.getTotalQuantity();
                    int inTransitQuantity = purchaseOrder.getInTransitQuantity();
                    int inStockQuantity = purchaseOrder.getInStockQuantity();
                    if ((inTransitQuantity + inStockQuantity) < purchaseNumber) {
                        enablePlanInstrock = true;
                    }
                }
                //显示入库详情
                boolean enableDetail = false;
                if (orderStatus == PurchaseStatusEnum.ALREADY_SHIPPING.getCode()
                        || orderStatus == PurchaseStatusEnum.PART_INBOUND.getCode()
                        || orderStatus == PurchaseStatusEnum.NORMAL_COMPLETE.getCode()
                        || orderStatus == PurchaseStatusEnum.EXCEPTION_COMPLETE_HANDLED.getCode()
                        || orderStatus == PurchaseStatusEnum.EXCEPTION_COMPLETE_NOT_HANDLE.getCode()
                        || orderStatus == PurchaseStatusEnum.GENERATE_COUPON.getCode()) {
                    enableDetail = true;
                }

                PurchaseStructure.PurchaseOrderInfo purchaseOrderInfoBuild = PurchaseStructure.PurchaseOrderInfo.newBuilder()
                        .setPurchaseOrderNumber(purchaseOrder.getPurchaseOrderNo())
                        .setSupplierId(purchaseOrder.getSupplierId() != null ? purchaseOrder.getSupplierId() : "")
                        .setSupplierName(purchaseOrder.getSupplierName() != null ? purchaseOrder.getSupplierName() : "")
                        .setOrderAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseActualPayAmount()))
                        .setPurchaseCategory(purchaseOrder.getPurchaseCategory())
                        .setPurchaseNumber(purchaseOrder.getTotalQuantity())
                        .setPaymentMode(paymentMode)
                        .setWarehouseName(purchaseOrder.getWarehouseName() != null ? purchaseOrder.getWarehouseName() : "")
                        .setWarehouseId(purchaseOrder.getWarehouseId())
                        .setRequirArrivalDate(purchaseOrder.getOrderDeadlineDate() != null ? DateUtil.getTime(purchaseOrder.getOrderDeadlineDate()) : 0)
                        .setBusinessDate(DateUtil.dateToString(purchaseOrder.getCreateTime(), "yyyy年MM月dd日 HH:mm"))
                        .setCreatePerson(purchaseOrder.getCreatedByName())
                        .setSupplierOrderNo(purchaseOrder.getBrandOrderNo())
                        .setPurchaseStatus(purchaseOrderStatus)
                        .setPurchaseStatusInt(orderStatus)
                        .setContractReferenceOrderNo(purchaseOrder.getContractReferenceOrderNo())
                        .setEnableCancel(enableCancle)
                        .setEnableDetail(enableDetail)
                        .setEnableEdit(enableEdit)
                        .setEnablePlanInStock(enablePlanInstrock)
                        .build();
                pageInfoPurchaseBuilder.addPurchaseOrderlist(purchaseOrderInfoBuild);
            }
            pageInfoPurchaseBuilder.setTotal(purchaseOrderPageInfo.getTotal());
            getPurchaseOrderListResp.setPageInfoPurchaseOrderList(pageInfoPurchaseBuilder);

            response = getPurchaseOrderListResp.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] getPurchaseOrderList success: purchaseOrderInfoList.size={} ", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 获取入库货品列表
     * <p>
     * rpcHeader rpc调用的header
     * purchaseNo 采购单号
     *
     * @return
     */
    @Override
    public void getInboundItemList(PurchaseStructure.GetInboundItemListReq request,
                                   StreamObserver<PurchaseStructure.GetInboundItemListResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String purchaseNo = request.getPurchaseNo();
        String productCode = request.getProductCode();
        long projectId = request.getProjectId();

        logger.info("#traceId={}# [IN][getInboundItemList] params: purchaseNo={}, productCode={}", rpcHeader.getTraceId(), purchaseNo, productCode);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(tablePrefix,purchaseNo);

            List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemDao.selectByNoAndProduct(tablePrefix,purchaseNo, productCode);
            PurchaseStructure.GetInboundItemListResp.Builder getInboundListBuilder
                    = PurchaseStructure.GetInboundItemListResp.newBuilder();

            List<PurchaseStructure.PlanInboundItem> planInboundItemList = new ArrayList<>();
            for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
                int purchaseNumber = purchaseOrderItem.getPurchaseNumber();//采购数量
                int inTransitQuantity = purchaseOrderItem.getInTransitQuantity();//在途数量
                int inStockQuantity = purchaseOrderItem.getInStockQuantity();//已入库数量purchaseOrderItem
                Date expectedInboundDate = purchaseOrder.getExpectedInboundDate();

                PurchaseStructure.PlanInboundItem planInboundItemBuild = PurchaseStructure.PlanInboundItem.newBuilder()
                        .setPurchaseItemId(purchaseOrderItem.getPurchaseItemId())
                        .setProductId(purchaseOrderItem.getProductId())
                        .setProductCode(purchaseOrderItem.getProductCode())
                        .setProductName(purchaseOrderItem.getProductName())
                        .setProductUnit(purchaseOrderItem.getProductUnit())
//                .setBrandId(supplier.getSupplierId() + "")
                        .setBrandName(purchaseOrder.getSupplierName())
//                planInboundItem.setCurrencyCode(currency.getCurrencyCode());
//                planInboundItem.setCurrencyName(currency.getCurrencyName());
                        .setPurchaseNumber(purchaseNumber)
                        .setNeedInstockNumber(purchaseNumber - inTransitQuantity - inStockQuantity)
                        .setGuidePrice(DoubleScale.keepSixBits(purchaseOrderItem.getGuidePrice()))
                        .setCostValue(DoubleScale.keepSixBits(purchaseOrderItem.getCostPrice()))
                        .setRequireInboundDate(expectedInboundDate != null ? expectedInboundDate.getTime() : null)
                        .build();
                getInboundListBuilder.addPlanInboundList(planInboundItemBuild);
            }
            PurchaseStructure.GetInboundItemListResp resp = getInboundListBuilder.build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] getInboundItemList success: planInboundItemList.size={} ", rpcHeader.getTraceId(), planInboundItemList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 采购管理 > 采购订单管理 > 预约入库 页 ，点提交时 进入该接口
     * 生成预约入库单
     * <p>
     * rpcHeader rpc调用的header
     * planInboundBasicInfo 入库单订单信息
     * planInboundItemList 入库单货品信息
     */
    public void planInbound(PurchaseStructure.PlanInboundReq request,
                            StreamObserver<PurchaseStructure.PlanInboundResp> responseObserver) {
        PurchaseStructure.PlanInboundResp.Builder resp = PurchaseStructure.PlanInboundResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        PurchaseStructure.PlanInboundBasicInfo planInboundBasicInfo = request.getPlanInboundBasicInfo();
        List<PurchaseStructure.PlanInboundItem> planInboundItemList = request.getPlanInboundItemListList();
        logger.info("#traceId={}# [IN][planInbound] params: ", rpcHeader.getTraceId());
        logger.info("#traceId={}# [IN][PurchaseServiceTransactionProcessor::planInbound] params: ", rpcHeader.getTraceId());
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            //1.1 获取采购单以及采购明细信息
            String purchaseOrderNo = planInboundBasicInfo.getPurchaseOrderNo();
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(tablePrefix,purchaseOrderNo);
            List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemDao.selectByOrderNo(tablePrefix,planInboundBasicInfo.getPurchaseOrderNo());

            //1.1.1 校验预约入库数量
            for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {
                String productCode = purchaseOrderItem.getProductCode();
                Integer purchaseNumber = purchaseOrderItem.getPurchaseNumber();
                Integer inTransitQuantity = purchaseOrderItem.getInTransitQuantity();
                int canImportQuantity = purchaseNumber - inTransitQuantity;
                for (PurchaseStructure.PlanInboundItem planInboundItem : planInboundItemList) {
                    int planInstockNumber = planInboundItem.getPlanInstockNumber();
                    String productCode1 = planInboundItem.getProductCode();
                    if (productCode.equals(productCode1)) {
                        if (planInstockNumber > canImportQuantity) { //若预约入库数量 大于 可预约入库数量 则报错
                            GongxiaoRpc.RpcResult rpcResult = GongxiaoRpc.RpcResult.newBuilder()
                                    .setReturnCode(ErrorCode.INBOUND_EXCEPTION.getErrorCode())
                                    .setMsg(ErrorCode.INBOUND_EXCEPTION.getMessage())
                                    .build();
                            resp.setRpcResult(rpcResult);
                            responseObserver.onNext(resp.build());
                            responseObserver.onCompleted();
                            return;
                        }
                    }
                }
            }
            //获取仓库信息
            WarehouseServiceGrpc.WarehouseServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setWarehouseId(planInboundBasicInfo.getWarehouseId())
                    .build();
            WarehouseStructure.GetWarehouseByIdResp warehouseByIdResp = rpcStub.getWarehouseById(getWarehouseByIdReq);
            WarehouseStructure.Warehouse warehouse = warehouseByIdResp.getWarehouse();
            String streetAddress = warehouse.getStreetAddress();
            //1.2 更新采购单汇总单在途商品数量
            //1.2.1 采购单的数量
            int inTransitQuantity1 = purchaseOrder.getInTransitQuantity();
            int unhandledQuantity = purchaseOrder.getUnhandledQuantity();
            //1.2.2 需变更的数量
            int planInstockNumberTotal = planInboundBasicInfo.getPlanInstockNumberTotal();
            PurchaseOrderBackWrite purchaseOrderBackWrite = new PurchaseOrderBackWrite();
            purchaseOrderBackWrite.setOrderStatus(PurchaseStatus.ALREADY_PLAN.getStatus());
            purchaseOrderBackWrite.setInTransitQuantity(inTransitQuantity1 + planInstockNumberTotal);
            purchaseOrderBackWrite.setUnhandledQuantity(unhandledQuantity - planInstockNumberTotal);
            purchaseOrderBackWrite.setPurchaseOrderNo(purchaseOrder.getPurchaseOrderNo());
            purchaseOrderBackWrite.setTablePrefix(tablePrefix);
            purchaseOrderDao.updateBack(purchaseOrderBackWrite);
            logger.info("更新采购单成功 purchaseNo={}", purchaseOrder.getPurchaseOrderNo());

            //1.3 更新采购单明细信息
            for (PurchaseStructure.PlanInboundItem planInboundItem : planInboundItemList) {
                int planInstockNumber = planInboundItem.getPlanInstockNumber();
                String productCode = planInboundItem.getProductCode();
                if (planInstockNumber > 0) {
                    for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {
                        String productCode1 = purchaseOrderItem.getProductCode();
                        if (productCode.equals(productCode1)) {
                            PurchaseOrderItemBackWrite purchaseOrderItemBackWrite = new PurchaseOrderItemBackWrite();
                            purchaseOrderItemBackWrite.setPurchaseItemId(purchaseOrderItem.getPurchaseItemId());
                            purchaseOrderItemBackWrite.setInTransitQuantity(purchaseOrderItem.getInTransitQuantity() + planInboundItem.getPlanInstockNumber());//在途商品数量=原先在途的商品数量+本次玉如入库的数量
                            purchaseOrderItemBackWrite.setOrderStatus(PurchaseStatus.ALREADY_PLAN.getStatus());
                            purchaseOrderItemBackWrite.setTablePrefix(tablePrefix);
                            purchaseOrderItemDao.updateBackWriteItem(purchaseOrderItemBackWrite);
                        }
                    }
                }
            }
            logger.info("更新采购单明细成功成功 purchaseNo={}", purchaseOrder.getPurchaseOrderNo());

            // 2 日志
            String tracelog = purchaseOrder.getTracelog();
            ArrayList<OperateHistoryRecord> recordList
                    = JSON.parseObject(tracelog, new TypeReference<ArrayList<OperateHistoryRecord>>() {});
            if (recordList == null) {
                recordList = new ArrayList<>();
            }
            OperateHistoryRecord operateRecord = new OperateHistoryRecord();
            operateRecord.setOperateFunction("预约入库");
            operateRecord.setOperateTime(new Date());
            operateRecord.setOperateId(rpcHeader.getUid());
            operateRecord.setOperateName(rpcHeader.getUsername());
            recordList.add(operateRecord);
            String traceJson = JSON.toJSONString(recordList);
            purchaseOrderDao.updateOperateTraceLog(tablePrefix,purchaseOrderNo, traceJson);
            logger.info("记录预约入库流水成功");

            logger.info("开始同步采购入库数据给WMS,采购单号={}", purchaseOrderNo);
            //3. 同步到WMS
            SyncPlanInboundToWmsTask syncPlanInboundToWmsTask
                    = new SyncPlanInboundToWmsTask(rpcHeader,
                    ApplicationContextProvider.getApplicationContext(),
                    planInboundBasicInfo,
                    planInboundItemList,
                    streetAddress);
            threadPoolTaskExecutor.submit(syncPlanInboundToWmsTask);
            logger.info("同步采购入库数据给WMS完成,采购单号={}", purchaseOrderNo);
//
            byte orderStatus = purchaseOrder.getOrderStatus();
//
            //4.同步到EAS
            if (orderStatus <= PurchaseStatusEnum.ALREADY_SHIPPING.getCode()) {
                logger.info("采购单状态 ={}", orderStatus);
                SyncPurchaseOrderToEasTask task = new SyncPurchaseOrderToEasTask(ApplicationContextProvider.getApplicationContext(),
                        rpcHeader,
                        purchaseOrder,
                        purchaseOrderItemList);
                threadPoolTaskExecutor.submit(task);
            }

            logger.info("#traceId={}# [OUT] generatePurchaseOrder success: ", rpcHeader.getTraceId());
            GongxiaoRpc.RpcResult rpcResult = GongxiaoRpc.RpcResult.newBuilder()
                    .setReturnCode(ErrorCode.SUCCESS.getErrorCode())
                    .setMsg(ErrorCode.SUCCESS.getMessage())
                    .build();
            resp.setRpcResult(rpcResult);
            responseObserver.onNext(resp.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 采购管理 > 采购单管理 页 点击"编辑" 进入该接口
     *
     * 注：此页面没有日记
     *
     * rpcHeader
     * purchaseNo
     *
     * @return
     */
    @Override
    public void getPurchaseEditDetail(PurchaseStructure.GetPurchaseEditDetailReq request,
                                      StreamObserver<PurchaseStructure.GetPurchaseEditDetailResp> responseObserver) {
        String purchaseOrderNumber = request.getPurchaseNo();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        logger.info("#traceId={}# [IN][getPurchaseEditDetail] params: ", rpcHeader.getTraceId());
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(tablePrefix,purchaseOrderNumber);
            List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemDao.selectByOrderNo(tablePrefix,purchaseOrderNumber);
            SupplierAccountServiceStructure.GetSupplierAccountAmountRequest getSupplierAccountAmountRequest = SupplierAccountServiceStructure.GetSupplierAccountAmountRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode("CNY")
                    .setProjectId(purchaseOrder.getProjectId())
                    .build();
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub supplierAccountServiceBlockingStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            logger.info("call supplier account param:  purchaseOrder.tostring = {}",purchaseOrder.toString());
            PaymentCommonGrpc.AccountAmountResponse accountAmount = supplierAccountServiceBlockingStub.getSupplierAccountAmount(getSupplierAccountAmountRequest);
            PurchaseStructure.PurchaseEditDetail.Builder purchaseEditBuilder = PurchaseStructure.PurchaseEditDetail.newBuilder();
            //设置订单信息
            purchaseEditBuilder.setAccountCouponAmount(DoubleScale.keepTwoBits(accountAmount.getCouponAmount()));
            purchaseEditBuilder.setAccountPrepaidAmount(DoubleScale.keepTwoBits(accountAmount.getPrepaidAmount()));

            purchaseEditBuilder.setProjectId(purchaseOrder.getProjectId() + "");
            purchaseEditBuilder.setProjectName(purchaseOrder.getProjectName());
            purchaseEditBuilder.setBusinessDate(purchaseOrder.getBusinessDate().getTime());
            purchaseEditBuilder.setPurchaseType(purchaseOrder.getPurchaseType() + "");
            purchaseEditBuilder.setSupplierOrderNo(purchaseOrder.getBrandOrderNo());
            purchaseEditBuilder.setContractReferenceOrderNo(purchaseOrder.getContractReferenceOrderNo());
            purchaseEditBuilder.setPurchaseCategory(purchaseOrderItemList.size());
            purchaseEditBuilder.setPurchaseTotalNumber(purchaseOrder.getTotalQuantity());
            purchaseEditBuilder.setCouponAmountUse(DoubleScale.keepTwoBits(purchaseOrder.getCouponAmountUse()));
            purchaseEditBuilder.setPrepaidAmountUse(DoubleScale.keepTwoBits(purchaseOrder.getPrepaidAmountUse()));
            purchaseEditBuilder.setAdCouponAmountUse("0.00");
            purchaseEditBuilder.setAdPrepaidAmountUse("0.00");
            purchaseEditBuilder.setPurchaseGuideAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseGuideAmount()));
            purchaseEditBuilder.setPurchaseShouldPayAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseShouldPayAmount()));
            purchaseEditBuilder.setPurchasePrivilegeAmount("0.00");
            purchaseEditBuilder.setReturnCash(DoubleScale.keepTwoBits(purchaseOrder.getReturnCash()));
            purchaseEditBuilder.setPurchaseActualPayAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseActualPayAmount()));
            purchaseEditBuilder.setCashAmountUse(DoubleScale.keepTwoBits(purchaseOrder.getCashAmountUse()));
            //设置货品信息
            List<PurchaseItemVo> purchaseItemVos = new ArrayList<>();
            for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {
                PurchaseStructure.PurchaseItemVo purchaseItemVo = PurchaseStructure.PurchaseItemVo.newBuilder()
                        .setPurchaseItemId(purchaseOrderItem.getPurchaseItemId())
                        .setPurchaseOrderNo(purchaseOrderItem.getPurchaseOrderNo())
                        .setProductId(purchaseOrderItem.getProductId())
                        .setProductCode(purchaseOrderItem.getProductCode())
                        .setProductName(purchaseOrderItem.getProductName())
                        .setDiscountPercent("0.000000")
                        .setCouponAmount(DoubleScale.keepTwoBits(purchaseOrderItem.getCouponAmount()))
                        .setPrepaidAmount(DoubleScale.keepTwoBits(purchaseOrderItem.getPrepaidAmount()))
                        .setCashAmount(DoubleScale.keepTwoBits(GrpcCommonUtil.getProtoParam(purchaseOrderItem.getCashAmount())))
                        .setGuidePrice(DoubleScale.keepSixBits(purchaseOrderItem.getGuidePrice()))
                        .setPurchasePrice(DoubleScale.keepSixBits(purchaseOrderItem.getPurchasePrice()))
                        .setCostPrice(DoubleScale.keepSixBits(purchaseOrderItem.getCostPrice()))
                        .setFactoryPrice(DoubleScale.keepSixBits(purchaseOrderItem.getFactoryPrice()))
                        .setPurchaseNumber(purchaseOrderItem.getPurchaseNumber()).build();
                purchaseEditBuilder.addItemList(purchaseItemVo);
            }
            PurchaseStructure.PurchaseEditDetail purchaseEditDetail = purchaseEditBuilder.build();
            PurchaseStructure.GetPurchaseEditDetailResp.Builder GetPurchaseEditDetailResp = PurchaseStructure.GetPurchaseEditDetailResp.newBuilder().setPurchaseEditDetail(purchaseEditDetail);
            responseObserver.onNext(GetPurchaseEditDetailResp.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] getPurchaseEditDetail success: ", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 通过新增的方式创建 进入该接口
     */
    public void createPurchaseOrder(PurchaseStructure.CreatePurchaseOrderReq request,
                                    StreamObserver<PurchaseStructure.CreatePurchaseOrderResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        PurchaseStructure.CreatePurchaseBasicInfo purchaseBasicInfo = request.getPurchaseBasicInfo();
        List<PurchaseStructure.CreatePurchaseItemInfo> purchaseItemInfoList = request.getPurchaseItemInfoListList();
        long projectId = request.getProjectId();
        logger.info("#traceId={}# [IN][updatePurchaseOrder] params: projectId={} ,purchaseBasicInfo={}", rpcHeader.getTraceId(),purchaseBasicInfo.toString());
        try {
            PurchaseOrder purchaseOrder = new PurchaseOrder();

            ProjectServiceGrpc.ProjectServiceBlockingStub projectStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq.Builder getProjectByIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setProjectId(projectId + "");
            ProjectStructure.GetByProjectIdResp getByProjectIdResp = projectStub.getByProjectId(getProjectByIdReq.build());
            ProjectStructure.Project project = getByProjectIdResp.getProject();
            String tablePrefix = project.getProjectTablePrefix();

            SupplierServiceGrpc.SupplierServiceBlockingStub supplierServiceBlockingStub = RpcStubStore.getRpcStub(SupplierServiceGrpc.SupplierServiceBlockingStub.class);
            SupplierStructure.GetSupplierByCodeReq getSupplierByCodeReq = SupplierStructure.GetSupplierByCodeReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setSupplierCode(purchaseBasicInfo.getSupplierCode())
                    .build();
            SupplierStructure.GetSupplierByCodeResp getSupplierByCodeResp = supplierServiceBlockingStub.getSupplierByCode(getSupplierByCodeReq);
            SupplierStructure.Supplier supplier = getSupplierByCodeResp.getSupplier();

            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseServiceBlockingStub = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder()
                    .setWarehouseId(purchaseBasicInfo.getWarehouseId())
                    .setRpcHeader(rpcHeader)
                    .build();
            WarehouseStructure.GetWarehouseByIdResp warehouseByIdResp = warehouseServiceBlockingStub.getWarehouseById(getWarehouseByIdReq);
            WarehouseStructure.Warehouse warehouse = warehouseByIdResp.getWarehouse();

            //拼采购单数据
            purchaseOrder.setSupplierReceipt((byte) purchaseBasicInfo.getSupplierReceipt());
            purchaseOrder.setTablePrefix(tablePrefix);
            purchaseOrder.setOrderStatus(PurchaseStatus.ORDER_IMPORT.getStatus());
            purchaseOrder.setEasStatus(EasSynStatus.INIT.getStatus());
            String oderNumber = DateTimeIdGenerator.nextId(tablePrefix, BizNumberType.PURCHASE_ORDER_NO);
            purchaseOrder.setPurchaseOrderNo(oderNumber);
            purchaseOrder.setProjectId(project.getProjectId());
            purchaseOrder.setProjectName(project.getProjectName());
            purchaseOrder.setSupplierId(supplier.getSupplierId()+"");
            purchaseOrder.setSupplierName(supplier.getSupplierName());
            purchaseOrder.setWarehouseId((int)warehouse.getWarehouseId());
            purchaseOrder.setWarehouseName(warehouse.getWarehouseName());
            purchaseOrder.setPurchaseType(purchaseBasicInfo.getPurchaseType());
            purchaseOrder.setPaymentMode((byte)purchaseBasicInfo.getPaymentMode());
            purchaseOrder.setShippingMode((byte)purchaseBasicInfo.getLogisticType());
            purchaseOrder.setExpectedInboundDate(DateUtil.long2Date(purchaseBasicInfo.getRequirArrivalDate()));
            purchaseOrder.setOrderDeadlineDate(DateUtil.long2Date(purchaseBasicInfo.getArrivalDeadline()));
            purchaseOrder.setBrandOrderNo(purchaseBasicInfo.getBrandOrderNo());
            purchaseOrder.setContractReferenceOrderNo(purchaseBasicInfo.getContractReferenceOrderNo());
            purchaseOrder.setRemark(purchaseBasicInfo.getRemark());
            purchaseOrder.setPurchaseCategory(purchaseBasicInfo.getPurchaseCategory());
            purchaseOrder.setTotalQuantity(purchaseBasicInfo.getPurchaseNumber());
            purchaseOrder.setCouponAmountUse(DoubleScale.multipleHundred(purchaseBasicInfo.getCouponAmountUse()));
            purchaseOrder.setPrepaidAmountUse(DoubleScale.multipleHundred(purchaseBasicInfo.getPrepaidAmountUse()));
            purchaseOrder.setAdCouponAmountUse(DoubleScale.multipleHundred(purchaseBasicInfo.getAdCouponAmountUse()));
            purchaseOrder.setAdPrepaidAmountUse(DoubleScale.multipleHundred(purchaseBasicInfo.getAdPrepaidAmountUse()));
            purchaseOrder.setCashAmount(DoubleScale.multipleHundred(purchaseBasicInfo.getCashAmount()));
            purchaseOrder.setPurchaseGuideAmount(DoubleScale.multipleHundred(purchaseBasicInfo.getPurchaseGuideAmount()));
            purchaseOrder.setPurchasePrivilegeAmount(DoubleScale.multipleHundred(purchaseBasicInfo.getPurchasePrivilegeAmount()));
            purchaseOrder.setPurchaseShouldPayAmount(DoubleScale.multipleHundred(purchaseBasicInfo.getPurchaseShouldPayAmount()));
            purchaseOrder.setPurchaseActualPayAmount(DoubleScale.multipleHundred(purchaseBasicInfo.getPurchaseActualPayAmount()));
            purchaseOrder.setInTransitQuantity(0);
            purchaseOrder.setInStockQuantity(0);
            purchaseOrder.setCanceledQuantity(0);
            purchaseOrder.setReturnedQuantity(0);
            purchaseOrder.setUnhandledQuantity(purchaseBasicInfo.getPurchaseNumber());
            purchaseOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            purchaseOrder.setCreatedByName(rpcHeader.getUsername());
            purchaseOrder.setCreateTime(new Date());
            purchaseOrder.setLastUpdateTime(new Date());
            //设置日志
            List<OperateHistoryRecord> recordList = new ArrayList<>();
            OperateHistoryRecord operateRecord = new OperateHistoryRecord();
            operateRecord.setOperateFunction("导入");
            operateRecord.setOperateTime(new Date());
            operateRecord.setOperateId(rpcHeader.getUid());
            operateRecord.setOperateName(rpcHeader.getUsername());
            recordList.add(operateRecord);
            String traceJson = JSON.toJSONString(recordList);
            purchaseOrder.setTracelog(traceJson);

            //拼采购单明细数据
            ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            for (PurchaseStructure.CreatePurchaseItemInfo createPurchaseItemInfo : purchaseItemInfoList){
                ProductStructure.GetByProductIdReq getByProductIdReq = ProductStructure.GetByProductIdReq.newBuilder()
                        .setProjectId(projectId)
                        .setRpcHeader(rpcHeader)
                        .build();
                ProductStructure.GetByProductIdResp getByProductIdResp = productServiceBlockingStub.getByProductId(getByProductIdReq);
                ProductStructure.ProductBusiness productBusiness = getByProductIdResp.getProductBusiness();

                PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
                purchaseOrderItem.setPriceDiscount(DoubleScale.multipleMillion(createPurchaseItemInfo.getPriceDiscount()));
                purchaseOrderItem.setTablePrefix(project.getProjectTablePrefix());
                purchaseOrderItem.setOrderStatus(PurchaseStatus.ORDER_IMPORT.getStatus());
                purchaseOrderItem.setPurchaseOrderNo(oderNumber);
                purchaseOrderItem.setProductId(createPurchaseItemInfo.getProductID());
                purchaseOrderItem.setProductCode(createPurchaseItemInfo.getProductCode());
                purchaseOrderItem.setProductName(createPurchaseItemInfo.getProductName());
                purchaseOrderItem.setProductUnit(productBusiness.getEasUnitCode());
                purchaseOrderItem.setWarehouseId((int)warehouse.getWarehouseId());
                purchaseOrderItem.setWarehouseName(warehouse.getWarehouseName());
                purchaseOrderItem.setShippingMode((byte)purchaseBasicInfo.getLogisticType());
                purchaseOrderItem.setCouponAmount(DoubleScale.multipleHundred(createPurchaseItemInfo.getCouponAmount()));
                purchaseOrderItem.setPrepaidAmount(DoubleScale.multipleHundred(createPurchaseItemInfo.getPrepaidAmount()));
                purchaseOrderItem.setGuidePrice(DoubleScale.multipleMillion(createPurchaseItemInfo.getGuidePrice()));
                purchaseOrderItem.setPurchasePrice(DoubleScale.multipleMillion(createPurchaseItemInfo.getPurchasePrice()));
                purchaseOrderItem.setCostPrice(DoubleScale.multipleMillion(createPurchaseItemInfo.getCostPrice()));
                purchaseOrderItem.setCouponBasePrice(DoubleScale.multipleMillion(createPurchaseItemInfo.getCouponBasePrice()));
                purchaseOrderItem.setFactoryPrice(DoubleScale.multipleMillion(createPurchaseItemInfo.getFactoryPrice()));
                purchaseOrderItem.setPurchaseNumber(createPurchaseItemInfo.getPurchaseNumber());
                purchaseOrderItem.setInTransitQuantity(0);
                purchaseOrderItem.setInStockQuantity(0);
                purchaseOrderItem.setImperfectQuantity(0);
                purchaseOrderItem.setCanceledQuantity(0);
                purchaseOrderItem.setReturnedQuantity(0);
                purchaseOrderItem.setCreateTime(new Date());
                purchaseOrderItem.setLastUpdateTime(new Date());
                purchaseOrderItem.setEasMateriaCode(productBusiness.getEasCode());
                purchaseOrderItemDao.insert(purchaseOrderItem);
            }
            purchaseOrderDao.insert(purchaseOrder);
            logger.info("#traceId={}# [IN][createPurchaseOrder] .: ",rpcHeader.getTraceId());
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 采购管理 > 采购单管理 页 点击"编辑"进行更新后，点击提交 则进入该接口
     * <p>
     * rpcHeader rpc调用的header
     * purchaseBasicInfo 采购单基础信息
     * purchaseItemInfoList 采购单的货品列表
     *
     * @return 采购单号
     */
    @Override
    public void updatePurchaseOrder(PurchaseStructure.UpdatePurchaseOrderReq request,
                                    StreamObserver<PurchaseStructure.UpdatePurchaseOrderResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        PurchaseStructure.CreatePurchaseBasicInfo purchaseBasicInfo = request.getPurchaseBasicInfo();
        List<PurchaseStructure.CreatePurchaseItemInfo> purchaseItemInfoList = request.getPurchaseItemInfoListList();
        logger.info("#traceId={}# [IN][updatePurchaseOrder] params:", rpcHeader.getTraceId());
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            SupplierStructure.GetSupplierByCodeReq supplierByCodeReq = SupplierStructure.GetSupplierByCodeReq.newBuilder().setRpcHeader(rpcHeader)
                    .setSupplierCode(purchaseBasicInfo.getSupplierCode())
                    .build();
            SupplierServiceGrpc.SupplierServiceBlockingStub supplierServiceBlockingStub = RpcStubStore.getRpcStub(SupplierServiceGrpc.SupplierServiceBlockingStub.class);
            SupplierStructure.GetSupplierByCodeResp supplierRsp = supplierServiceBlockingStub.getSupplierByCode(supplierByCodeReq);
            SupplierStructure.Supplier supplier = supplierRsp.getSupplier();

            WarehouseStructure.GetWarehouseByIdReq warehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader)
                    .setWarehouseId(purchaseBasicInfo.getWarehouseId())
                    .build();
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseServiceBlockingStub = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.GetWarehouseByIdResp warehouseById = warehouseServiceBlockingStub.getWarehouseById(warehouseByIdReq);
            WarehouseStructure.Warehouse warehouse = warehouseById.getWarehouse();

            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(tablePrefix,purchaseBasicInfo.getPurchaseOrderNo());
            String tracelog = purchaseOrder.getTracelog();
            PurchaseOrder purchaseOrderInfo = new PurchaseOrder();
            List<PurchaseOrderItem> purchaseOrderItemList = new ArrayList<>();
            //1)插入订单信息
            //1.1 采购单
            Date date = new Date();
            purchaseOrderInfo.setOrderStatus(PurchaseStatus.ORDER_SUBMIT.getStatus());
            purchaseOrderInfo.setShippingMode(ShippingModeStatus.OHTER_PARTY_LOGISTIC.getStatus());
            purchaseOrderInfo.setPurchaseOrderNo(purchaseBasicInfo.getPurchaseOrderNo());
            purchaseOrderInfo.setPaymentMode((byte) purchaseBasicInfo.getPaymentMode());
            purchaseOrderInfo.setBrandOrderNo(purchaseBasicInfo.getBrandOrderNo());
            purchaseOrderInfo.setContractReferenceOrderNo(purchaseBasicInfo.getContractReferenceOrderNo());
            purchaseOrderInfo.setRemark(purchaseBasicInfo.getRemark());
            purchaseOrderInfo.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            purchaseOrderInfo.setCreatedByName(purchaseBasicInfo.getUserName());
            purchaseOrderInfo.setPurchaseType(purchaseBasicInfo.getPurchaseType());
            //设置时间
            purchaseOrderInfo.setExpectedInboundDate(DateUtil.long2Date(purchaseBasicInfo.getRequirArrivalDate()));
            purchaseOrderInfo.setOrderDeadlineDate(DateUtil.long2Date(purchaseBasicInfo.getArrivalDeadline()));
            purchaseOrderInfo.setBusinessDate(DateUtil.long2Date(purchaseBasicInfo.getBusinessDate()));
            //设置项目信息
            purchaseOrderInfo.setSupplierId(supplier.getSupplierId() + "");
            purchaseOrderInfo.setSupplierName(supplier.getSupplierName());
            purchaseOrderInfo.setWarehouseId(Integer.parseInt(warehouse.getWarehouseId() + ""));
            purchaseOrderInfo.setWarehouseName(warehouse.getWarehouseName());
            //        purchaseOrderInfo.setLogisticsType((byte)purchaseBasicInfo.getLogisticType());
            //设置金额信息
            purchaseOrderInfo.setCouponAmountUse(DoubleScale.multipleHundred(purchaseBasicInfo.getCouponAmountUse())); //采购总金额
            purchaseOrderInfo.setPrepaidAmountUse(DoubleScale.multipleHundred(purchaseBasicInfo.getPrepaidAmountUse()));
            purchaseOrderInfo.setAdCouponAmountUse(DoubleScale.multipleHundred(purchaseBasicInfo.getAdCouponAmountUse()));
            purchaseOrderInfo.setAdPrepaidAmountUse(DoubleScale.multipleHundred(purchaseBasicInfo.getAdPrepaidAmountUse()));

            purchaseOrderInfo.setPurchaseGuideAmount(DoubleScale.multipleHundred(purchaseBasicInfo.getPurchaseGuideAmount()));
            purchaseOrderInfo.setPurchasePrivilegeAmount(DoubleScale.multipleHundred(purchaseBasicInfo.getPurchasePrivilegeAmount()));
            //设置操作日记
            //将货品列表json数组的字符串转换为List
            ArrayList<OperateHistoryRecord> recordList
                    = JSON.parseObject(tracelog, new TypeReference<ArrayList<OperateHistoryRecord>>() {});
            if (recordList == null) {
                recordList = new ArrayList<>();
            }
            OperateHistoryRecord operateRecord = new OperateHistoryRecord();
            operateRecord.setOperateFunction("更新");
            operateRecord.setOperateTime(date);
            operateRecord.setOperateId(purchaseBasicInfo.getUserId());
            operateRecord.setOperateName(purchaseBasicInfo.getUserName());
            recordList.add(operateRecord);
            String traceJson = JSON.toJSONString(recordList);
            //1.2 采购订单明细
            double totalAmount = 0;
            JSONArray productList = new JSONArray();
            for (PurchaseStructure.CreatePurchaseItemInfo createPurchaseItemInfo : purchaseItemInfoList) {
                PurchaseOrderItem purchaseOrderItem1 =
                        purchaseOrderItemDao.selectByOrderNoAndProduct(tablePrefix,
                                purchaseOrder.getPurchaseOrderNo(),
                                createPurchaseItemInfo.getProductCode());

                ProductStructure.GetByProductModelReq getByProductModelReq = ProductStructure.GetByProductModelReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProductModel(createPurchaseItemInfo.getProductCode())
                        .setProjectId(projectId)
                        .build();
                ProductServiceGrpc.ProductServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                ProductStructure.GetByProductModelResp getByProductModelResp = rpcStub.getByProductModel(getByProductModelReq);
                ProductStructure.ProductBusiness productBusiness = getByProductModelResp.getProductBusiness();
                ProductItem productItem = new ProductItem();
                productItem.setProductCode(createPurchaseItemInfo.getProductCode());
                productItem.setProductName(createPurchaseItemInfo.getProductName());
                productItem.setPurchaseNumber(createPurchaseItemInfo.getPurchaseNumber());
                productList.add(productItem);
                PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
                purchaseOrderItem.setOrderStatus(PurchaseStatus.ORDER_SUBMIT.getStatus());
                purchaseOrderItem.setShippingMode((byte) 2);
                purchaseOrderItem.setPurchaseItemId(purchaseOrderItem1.getPurchaseItemId());
//            purchaseOrderItem.setProductCode(byProductCode.getEasCode());
                purchaseOrderItem.setEasMateriaCode(productBusiness.getEasCode());
                //设置仓库信息
                purchaseOrderItem.setWarehouseId(Integer.parseInt(warehouse.getWarehouseId() + ""));
                purchaseOrderItem.setWarehouseName(warehouse.getWarehouseName());
                //设置各金额
                purchaseOrderItem.setGuidePrice(DoubleScale.multipleMillion(createPurchaseItemInfo.getGuidePrice()));
                int disCount = (int) (DoubleScale.multipleMillion(createPurchaseItemInfo.getPurchaseDiscount()));
                purchaseOrderItem.setPrepaidAmount(DoubleScale.multipleHundred(createPurchaseItemInfo.getPrepaidAmount()));
                purchaseOrderItem.setCouponAmount(DoubleScale.multipleHundred(createPurchaseItemInfo.getCouponAmount()));
//            purchaseOrderItem.setNeedPlanQuantity(createPurchaseItemInfo.getPurchaseNumber()); //尚未计划入库的数量
                purchaseOrderItem.setLastUpdateTime(date);
                purchaseOrderItem.setTracelog(traceJson);
                purchaseOrderItem.setTablePrefix(tablePrefix);
                purchaseOrderItemList.add(purchaseOrderItem);
                int row = purchaseOrderItemDao.updatePurchaseItem(purchaseOrderItem); //更新采购单明细表
            }
            logger.info("采购单号:{} ,插入货品信息完成", purchaseOrderInfo.getPurchaseOrderNo());
            purchaseOrderInfo.setProductInfo(productList.toJSONString());
            purchaseOrderInfo.setTracelog(traceJson);
            purchaseOrderInfo.setLastUpdateTime(date);
            purchaseOrderInfo.setEasStatus(PurchaseEasStatus.SYN_EAS_FAILURE.getStatus());
            purchaseOrderInfo.setTablePrefix(tablePrefix);
            purchaseOrderDao.updatePurchaseOrder( purchaseOrderInfo); //更新采购单汇总表
            logger.info("采购单号:{} ,插入订单信息完成", purchaseOrderInfo.getPurchaseOrderNo());
            //4) 真实扣件订单的返利与代垫
            SupplierAccountServiceStructure.PayForPurchaseRequest pay = SupplierAccountServiceStructure.PayForPurchaseRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode("CNY")
                    .setProjectId(projectId)
                    .setCouponToYh(purchaseOrderInfo.getCouponAmountUse())
                    .setCouponToAd(purchaseOrderInfo.getAdCouponAmountUse())
                    .setPrepaidToYh(purchaseOrderInfo.getPrepaidAmountUse())
                    .setPrepaidToAd(purchaseOrderInfo.getAdPrepaidAmountUse())
                    .setTransactionTime(DateUtil.getTime(date))
                    .build();
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            SupplierAccountServiceStructure.PayForPurchaseResponse payForPurchaseResponse = rpcStub.payForPurchase(pay);
            logger.info("采购单号:{} ,扣款完成", purchaseOrderInfo.getPurchaseOrderNo());
            //5)扣款成功更新订单为付款成功
            purchaseOrderDao.updateAlreadyPay(tablePrefix,purchaseOrderInfo.getPurchaseOrderNo());
            logger.info("采购单号:{} ,更新扣款状态完成", purchaseOrderInfo.getPurchaseOrderNo());
            logger.info("#traceId={}# [OUT] updatePurchaseOrder success: ", rpcHeader.getTraceId());
            PurchaseStructure.UpdatePurchaseOrderResp resp = PurchaseStructure.UpdatePurchaseOrderResp.newBuilder().setNumber(1).build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 导入飞利浦订单后，进行作废时 进入该接口
     *
     * TODO：检查当前订单的状态 是否可作废
     */
    public void cancelPurchaseOrder(PurchaseStructure.CancelPurchaseOrderReq request,
                                    StreamObserver<PurchaseStructure.CancelPurchaseOrderResp> responseObserver) {
        PurchaseStructure.CancelPurchaseOrderResp.Builder resp = PurchaseStructure.CancelPurchaseOrderResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String purchaseOrderNumber = request.getPurchaseOrderNumber();
        long projectId = request.getProjectId();
        logger.info("#traceId={}# [IN][cancelPurchaseOrder] params: purchaseOrderNumber={}", rpcHeader.getTraceId(), purchaseOrderNumber);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            int i = purchaseOrderDao.cancelPurchaseOrder(tablePrefix,purchaseOrderNumber);
            resp.setNumber(i);
            responseObserver.onNext(resp.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
