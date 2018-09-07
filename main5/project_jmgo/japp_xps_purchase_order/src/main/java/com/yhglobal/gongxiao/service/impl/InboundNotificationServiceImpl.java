package com.yhglobal.gongxiao.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.XpsCouponManager;
import com.yhglobal.gongxiao.constant.CouponLedgerCouponType;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.dao.PurchaseOrderDao;
import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.dao.PurchaseScheduleDeliveryDao;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierServiceGrpc;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.purchase.microservice.NotifiInboundServiceGrpc;
import com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure;
import com.yhglobal.gongxiao.status.PurchaseStatusEnum;
import com.yhglobal.gongxiao.status.WarehouseLocationStatus;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.PropertyUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model.PurchaseEasInboundModel;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * xps warehouse收到WMS的回告后，会把回告转给jweb_xps_warehouse_notify_gateway，
 * jweb_xps_warehouse_notify_gateway再根据采购单号回调到该入口
 *
 * 采购入库通知单回写
 *
 * @author: 陈浩
 **/
@Service
public class InboundNotificationServiceImpl extends NotifiInboundServiceGrpc.NotifiInboundServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(InboundNotificationServiceImpl.class);

//    @Autowired
//    YhglobalCouponLedgerService yhglobalcouponledgerService;

    @Autowired
    PurchaseOrderDao purchaseOrderDao; //采购单号

    @Autowired
    PurchaseOrderItemDao purchaseOrderItemDao; //采购明细

    @Autowired
    PurchaseScheduleDeliveryDao scheduleDeliveryDao; //预约收货DAO

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    PropertyUtil propertyUtil;



    /**
     * 构建待同步给EAS的采购入库单需要的相关参数，以供warehouse模块调用
     */
    @Override
    public void transferReceivedNotification(NotifiInboundStructure.TransferReceivedNotificationReq request,
                                             StreamObserver<NotifiInboundStructure.TransferReceivedNotificationResp> responseObserver) {
        logger.info("# [IN][parsePurchaseOrderList] params: ");
        NotifiInboundStructure.TransferReceivedNotificationResp.Builder respBuilder = NotifiInboundStructure.TransferReceivedNotificationResp.newBuilder();

        String projectId = request.getProjectId();
        String traceNo = request.getTraceNo();
        String gongxiaoInboundOrderNo = request.getGongxiaoInboundOrderNo();
        List<NotifiInboundStructure.InboundNotificationBackItem> inboundNotificationBackItemList = request.getInboundNotificationBackItemList(); //包含了WMS实际入库的信息 比如良品/次品/入库数量等

        String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));

        logger.info(" [IN][transferReceivedNotificationServiceIn] params: #projectId={} gongxiaoInboundOrderNo={} traceId={} ",
                projectId, traceNo, gongxiaoInboundOrderNo);

        PurchaseEasInboundModel purchaseEasInboundModel = new PurchaseEasInboundModel();

        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder()
                .setTraceId(new Date().getTime() + "")
                .setUid("999")
                .setUsername("test")
                .build();

        ProjectServiceGrpc.ProjectServiceBlockingStub projectRpcStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
        SupplierServiceGrpc.SupplierServiceBlockingStub supplierRpcStub = RpcStubStore.getRpcStub(SupplierServiceGrpc.SupplierServiceBlockingStub.class);
        WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseRpcStub = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
        ProductServiceGrpc.ProductServiceBlockingStub productRpcStub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);

        try {
            //1)获取采购单信息
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(tablePrefix,traceNo);
            if (purchaseOrder == null) {
                logger.warn("未找到采购单号={}的采购单", traceNo);
                respBuilder.setErrorCode(ErrorCode.NOT_FINDING_PURCHASE_ORDER.getErrorCode());
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            String easOrderNumber = purchaseOrder.getEasOrderNumber();
            if (easOrderNumber == null || "".equals(easOrderNumber)) { //先判断该采购入库对应的采购单是否已同步EAS
                logger.warn("采购单没有同步eas");
                respBuilder.setErrorCode(ErrorCode.PURCHASE_ORDER_NOT_SYN_EAS.getErrorCode());
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }

            List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemDao.selectByOrderNo(tablePrefix,traceNo);

            //2) 获取项目基础信息
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .build();
            SupplierStructure.GetSupplierByIdReq getSupplierByIdReq = SupplierStructure.GetSupplierByIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setSupplierId(purchaseOrder.getSupplierId())
                    .build();

            ProjectStructure.GetByProjectIdResp getByProjectIdResp = projectRpcStub.getByProjectId(getByProjectIdReq);
            SupplierStructure.GetSupplierByIdResp getSupplierByIdResp = supplierRpcStub.getSupplierById(getSupplierByIdReq);

            ProjectStructure.Project project = getByProjectIdResp.getProject();
            SupplierStructure.Supplier supplier = getSupplierByIdResp.getSupplier();

            //3)同步EAS
            //3.1 构造采购单数据
            NotifiInboundStructure.PurchaseBasicOrderInbound.Builder purchaseBasicOrderInbound = NotifiInboundStructure.PurchaseBasicOrderInbound.newBuilder();

            purchaseBasicOrderInbound.setProjectId(project.getEasProjectCode());
            purchaseBasicOrderInbound.setSupplierCode(supplier.getEasSupplierCode());
            purchaseBasicOrderInbound.setId(purchaseOrder.getEasPurchaseOrderId());
            purchaseBasicOrderInbound.setPurchaseOrderNum(purchaseOrder.getEasOrderNumber());
            purchaseBasicOrderInbound.setBusinessDate(DateUtil.getTime(purchaseOrder.getBusinessDate()));
            purchaseBasicOrderInbound.setRequireArrivalDate(DateUtil.getTime(purchaseOrder.getExpectedInboundDate()));
            //5.2 拼装采购单明细
            int inStockNumberTotal = 0;
            double totalTaxAmount = 0;
            int number = 0;

            for (NotifiInboundStructure.InboundNotificationBackItem inboundOrder : inboundNotificationBackItemList) {
                String connectedProduct1 = inboundOrder.getProductCode();
                //如果是赠品, 放在赠品库位 如果是非赠品,区分良品库位和次品库位
                if (inboundOrder.getIsGift()) { //赠品, 则放在赠品库位
                    for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
                        String productCode = purchaseOrderItem.getProductCode();
                        ProductStructure.GetByProductModelReq getByProductModelReq = ProductStructure.GetByProductModelReq.newBuilder()
                                .setRpcHeader(rpcHeader)
                                .setProductModel(productCode)
                                .build();
                        ProductStructure.GetByProductModelResp productModelResp = productRpcStub.getByProductModel(getByProductModelReq);
                        ProductStructure.ProductBusiness productBusiness = productModelResp.getProductBusiness();
                        if (productCode.equals(connectedProduct1)) { //已入库的商品code 是否等于 采购单中的商品code 来建立对应关系
                            logger.info("采购单={}，入库单号={}，残次品数量={}，正品数量={}", purchaseOrder.getPurchaseOrderNo(), inboundOrder.getInboundNotificationItemId(), inboundOrder.getImperfectQuantity(), inboundOrder.getInStockQuantity());
                            double discount =  (1-((double)purchaseOrderItem.getCostPrice()/purchaseOrderItem.getPurchasePrice()))*100; //折扣比例 = 1- (成本价/采购价)
                            number = inboundOrder.getInStockQuantity() + inboundOrder.getImperfectQuantity(); //明细数量 = 正品数量 + 残次品数量
                            inStockNumberTotal = inStockNumberTotal + number; //总入库数量 =  各明细入库数量之和
                            totalTaxAmount = totalTaxAmount + (purchaseOrderItem.getGuidePrice() * inStockNumberTotal); //总价税合计 = 各明细价税合计之和
                            //添加良品
                            NotifiInboundStructure.PurchaseBasicOrderItemInbound.Builder purchaseBasicOrderItemInbound = NotifiInboundStructure.PurchaseBasicOrderItemInbound.newBuilder();
                            purchaseBasicOrderItemInbound.setTaxRate(FXConstant.TAX_RATE);
                            purchaseBasicOrderItemInbound.setTaxPrice((double) purchaseOrderItem.getGuidePrice() / FXConstant.MILLION);
                            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder()
                                    .setRpcHeader(rpcHeader)
                                    .setWarehouseId(inboundOrder.getWarehouseId() + "")
                                    .build();
                            WarehouseStructure.GetWarehouseByIdResp warehouseResp = warehouseRpcStub.getWarehouseById(getWarehouseByIdReq);
                            WarehouseStructure.Warehouse warehouse = warehouseResp.getWarehouse();

                            purchaseBasicOrderItemInbound.setWarehouseCode(warehouse.getEasWarehouseCode());
                            purchaseBasicOrderItemInbound.setLocationId(WarehouseLocationStatus.GIVE_PRODUCT_LOCATION);//先假设都是良品,如果既有良品,又有次品要分为两个单号
                            purchaseBasicOrderItemInbound.setLot(inboundOrder.getBatchNo());
                            purchaseBasicOrderItemInbound.setNumber(inboundOrder.getInStockQuantity() + inboundOrder.getImperfectQuantity());
                            purchaseBasicOrderItemInbound.setMaterialId(purchaseOrderItem.getEasMateriaCode());
                            purchaseBasicOrderItemInbound.setPurchaseOrderId(purchaseOrder.getEasPurchaseOrderId());
                            purchaseBasicOrderItemInbound.setPurchaseOrderEntryId(purchaseOrderItem.getEasEntryId());
                            purchaseBasicOrderItemInbound.setUnit(productBusiness.getEasUnitCode());
                            purchaseBasicOrderItemInbound.setDiscount(discount);
                            respBuilder.addPurchaseBasicOrderItemInbound(purchaseBasicOrderItemInbound);                        }
                    }
                } else { //如果是非赠品,区分良品库位和次品库位
                    //采购入库单跟采购明细做匹配,获取采购明细的easid
                    for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
                        String productCode = purchaseOrderItem.getProductCode();
                        ProductStructure.GetByProductModelReq getByProductModelReq = ProductStructure.GetByProductModelReq.newBuilder()
                                .setRpcHeader(rpcHeader)
                                .setProjectId(Long.parseLong(projectId))
                                .setProductModel(productCode)
                                .build();
                        ProductStructure.GetByProductModelResp productModelResp = productRpcStub.getByProductModel(getByProductModelReq);
                        ProductStructure.ProductBusiness productBusiness = productModelResp.getProductBusiness();
                        if (productCode.equals(connectedProduct1)) {
                            logger.info("采购单={}，入库单号={}，残次品数量={}，正品数量={}", purchaseOrder.getPurchaseOrderNo(), inboundOrder.getInboundNotificationItemId(), inboundOrder.getImperfectQuantity(), inboundOrder.getInStockQuantity());
                            double discount =  (1-((double)purchaseOrderItem.getCostPrice()/purchaseOrderItem.getPurchasePrice()))*100;
                            number = inboundOrder.getInStockQuantity() + inboundOrder.getImperfectQuantity();
                            inStockNumberTotal = inStockNumberTotal + number;
                            totalTaxAmount = totalTaxAmount + (purchaseOrderItem.getGuidePrice() * inStockNumberTotal);
                            //添加良品
                            NotifiInboundStructure.PurchaseBasicOrderItemInbound.Builder purchaseBasicOrderItemInbound = NotifiInboundStructure.PurchaseBasicOrderItemInbound.newBuilder();
                            purchaseBasicOrderItemInbound.setTaxRate(FXConstant.TAX_RATE);
                            purchaseBasicOrderItemInbound.setTaxPrice((double) purchaseOrderItem.getGuidePrice() / FXConstant.MILLION);
                            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder()
                                    .setRpcHeader(rpcHeader)
                                    .setWarehouseId(inboundOrder.getWarehouseId() + "")
                                    .build();
                            WarehouseStructure.GetWarehouseByIdResp warehouseResp = warehouseRpcStub.getWarehouseById(getWarehouseByIdReq);
                            WarehouseStructure.Warehouse warehouse = warehouseResp.getWarehouse();
                            purchaseBasicOrderItemInbound.setWarehouseCode(warehouse.getEasWarehouseCode());
                            purchaseBasicOrderItemInbound.setLocationId(WarehouseLocationStatus.GOOD_PRODUCT_LOCATION);//设置为良品库位
                            purchaseBasicOrderItemInbound.setLot(inboundOrder.getBatchNo());
                            purchaseBasicOrderItemInbound.setNumber(inboundOrder.getInStockQuantity()); //instock的数量一定是良品
                            purchaseBasicOrderItemInbound.setMaterialId(purchaseOrderItem.getEasMateriaCode());
                            purchaseBasicOrderItemInbound.setPurchaseOrderId(purchaseOrder.getEasPurchaseOrderId());
                            purchaseBasicOrderItemInbound.setPurchaseOrderEntryId(purchaseOrderItem.getEasEntryId());
                            purchaseBasicOrderItemInbound.setUnit(productBusiness.getEasUnitCode());
                            purchaseBasicOrderItemInbound.setDiscount(discount);
                            respBuilder.addPurchaseBasicOrderItemInbound(purchaseBasicOrderItemInbound);
                            //添加残次品
                            if (inboundOrder.getImperfectQuantity() > 0) {
                                NotifiInboundStructure.PurchaseBasicOrderItemInbound.Builder purchaseBasicOrderItemInboundNotPerfect = NotifiInboundStructure.PurchaseBasicOrderItemInbound.newBuilder();
                                purchaseBasicOrderItemInboundNotPerfect.setTaxRate(FXConstant.TAX_RATE);
                                purchaseBasicOrderItemInboundNotPerfect.setTaxPrice((double) purchaseOrderItem.getGuidePrice() / FXConstant.MILLION);
                                purchaseBasicOrderItemInboundNotPerfect.setWarehouseCode(warehouse.getEasWarehouseCode());
                                purchaseBasicOrderItemInboundNotPerfect.setLocationId(WarehouseLocationStatus.DEFECTIVE_PRODUCT_LOCATION);//设为残次品库位
                                purchaseBasicOrderItemInboundNotPerfect.setLot(inboundOrder.getBatchNo());
                                purchaseBasicOrderItemInboundNotPerfect.setNumber(inboundOrder.getImperfectQuantity());
                                purchaseBasicOrderItemInboundNotPerfect.setMaterialId(purchaseOrderItem.getEasMateriaCode());
                                purchaseBasicOrderItemInboundNotPerfect.setPurchaseOrderId(purchaseOrder.getEasPurchaseOrderId());
                                purchaseBasicOrderItemInboundNotPerfect.setPurchaseOrderEntryId(purchaseOrderItem.getEasEntryId());
                                purchaseBasicOrderItemInboundNotPerfect.setUnit(productBusiness.getEasUnitCode());
                                purchaseBasicOrderItemInboundNotPerfect.setDiscount(discount);
                                respBuilder.addPurchaseBasicOrderItemInbound(purchaseBasicOrderItemInboundNotPerfect);
                            }
                        }
                    }
                }

            }

            //组装单头
            purchaseBasicOrderInbound.setNumber(number);
            double rate = FXConstant.TAX_RATE / 100.0;//税率
            double taxAmountTotal = totalTaxAmount / FXConstant.MILLION;//含税金额
            double taxAmount = taxAmountTotal * (rate / (1 + rate));//税额
            purchaseBasicOrderInbound.setTotalTax(taxAmount);
            purchaseBasicOrderInbound.setTotalTaxAmount(taxAmountTotal);

            //5.3 同步到EAS
//            logger.info("采购入库单对应的 FX单号={} EAS采购单号={}", purchaseOrder.getPurchaseOrderNo(), purchaseOrder.getEasOrderNumber());
//            String easJson = EasUtil.sendPurchaseInbound2Eas(purchaseBasicOrderInbound, purchaseBasicOrderItemInboundList);
            respBuilder.setPurchaseBasicOrderInbound(purchaseBasicOrderInbound);
            respBuilder.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            respBuilder.setErrorCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            return;
        }

    }

    /**
     * 当WMS做真正入库时 会回调该入口
     *
     * 更新订单状态 (即使入库数量和订单的数量相等，也不会关闭订单，需要等待close回告后进行订单关闭)
     * 更新订单的入库数量
     */
    @Override
    public void notifyPurchaseInbound(NotifiInboundStructure.NotifyPurchaseInboundReq request,
                                      StreamObserver<NotifiInboundStructure.NotifyPurchaseInboundResp> responseObserver) {
        NotifiInboundStructure.NotifyPurchaseInboundResp.Builder respBuilder = NotifiInboundStructure.NotifyPurchaseInboundResp.newBuilder();

        String projectId = request.getProjectId();
        String traceNo = request.getTraceNo();
        String gongxiaoInboundOrderNo = request.getGongxiaoInboundOrderNo();
        List<NotifiInboundStructure.InboundNotificationBackItem> inboundNotificationBackItemList = request.getInboundNotificationBackItemList();
        String uniqueNumber = request.getUniqueNumber();

        GongxiaoRpc.RpcResult.Builder rpcResult = GongxiaoRpc.RpcResult.newBuilder();

        logger.info("# [IN][notifyPurchaseInboundServiceIn] params: #projectId={} gongxiaoInboundOrderNo={} traceId={} ",
                projectId, traceNo, gongxiaoInboundOrderNo);                //校验唯一号

        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder()
                .setTraceId(new Date().getTime() + "")
                .setUid("999")
                .setUsername("test")
                .build();
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));

            PurchaseOrder byUniqueNumber = purchaseOrderDao.getByUniqueNumber(tablePrefix,uniqueNumber);
            if (byUniqueNumber != null) {
                logger.info("防止重复提交");
                rpcResult.setReturnCode(ErrorCode.DUPLICATED_REQUEST.getErrorCode());
                respBuilder.setRpcResult(rpcResult);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(tablePrefix,traceNo);
            //1) 校验入库数量
            int totalQuantity = purchaseOrder.getTotalQuantity(); //获取预约入库单的预约入库数量
            int inTransitQuantity = purchaseOrder.getInTransitQuantity();//获取在途商品数量
            int inStockQuantity = purchaseOrder.getInStockQuantity();//获取已入库的商品数量
            for (NotifiInboundStructure.InboundNotificationBackItem inboundNotificationBackItem : inboundNotificationBackItemList) {
                PurchaseOrderItem purchaseOrderItem = purchaseOrderItemDao.selectByOrderNoAndProduct(tablePrefix,
                                purchaseOrder.getPurchaseOrderNo(),
                                inboundNotificationBackItem.getProductCode());
                int inStockNumber = inboundNotificationBackItem.getInStockQuantity(); //当次回告的入库数量
                logger.info("仓管推送的采购数量为:{}", inStockNumber);
                int inStockQuantityItem = purchaseOrderItem.getInStockQuantity();//获取已入库的商品数量
                inStockQuantityItem += inStockNumber;//已入库数量增加流水里的数量
                if (inStockQuantityItem > totalQuantity) {
                    logger.warn("已入库完成,无需再入库");
                    rpcResult.setReturnCode(ErrorCode.INBOUND_ALREADY_COMPLETE.getErrorCode());
                    respBuilder.setRpcResult(rpcResult);
                    responseObserver.onNext(respBuilder.build());
                    responseObserver.onCompleted();
                    return;
                }
            }
            //2) 变更采购入库数量
            for (NotifiInboundStructure.InboundNotificationBackItem inboundNotificationBackItem : inboundNotificationBackItemList) {
                PurchaseOrderItem purchaseOrderItem = purchaseOrderItemDao.selectByOrderNoAndProduct(tablePrefix,
                                purchaseOrder.getPurchaseOrderNo(),
                                inboundNotificationBackItem.getProductCode());
                if (purchaseOrderItem == null) {
                    logger.error("#traceId={}# 没有找到采购单号为{} 货品编码为{} 的采购明细信息", traceNo, inboundNotificationBackItem.getProductCode());
                    continue;
                }
                int inStockNumber = inboundNotificationBackItem.getInStockQuantity();
                logger.info("仓管推送的采购数量为:{}", inStockNumber);
                int totalQuantityItem = purchaseOrderItem.getPurchaseNumber(); //获取预约入库单的预约入库数量
                int inTransitQuantityItem = purchaseOrderItem.getInTransitQuantity();//获取在途商品数量
                int inStockQuantityItem = purchaseOrderItem.getInStockQuantity();//获取已入库的商品数量
                inTransitQuantityItem -= inStockNumber; //在途数量扣减已入库的数量
                inStockQuantityItem += inStockNumber;//已入库数量增加流水里的数量
                //拼装货品数据
                PurchaseOrderItemBackWrite purchaseOrderItemBackWrite = new PurchaseOrderItemBackWrite();
                long purchaseItemId = purchaseOrderItem.getPurchaseItemId();
                purchaseOrderItemBackWrite.setPurchaseItemId(purchaseItemId);
                purchaseOrderItemBackWrite.setInTransitQuantity(inTransitQuantityItem);
                purchaseOrderItemBackWrite.setInStockQuantity(inStockQuantityItem);
                purchaseOrderItemBackWrite.setUniqueNumber(uniqueNumber);
                purchaseOrderItemBackWrite.setOrderStatus(PurchaseStatusEnum.PART_INBOUND.getCode());
                purchaseOrderItemBackWrite.setTablePrefix(tablePrefix);
                purchaseOrderItemDao.updateInstockQuantity(tablePrefix, //更新入库数量
                        purchaseItemId,
                        inTransitQuantityItem,
                        inStockQuantityItem,
                        "",
                        PurchaseStatusEnum.PART_INBOUND.getCode());

                inTransitQuantity -= inboundNotificationBackItem.getInStockQuantity(); //在途数量扣减已入库的数量
                inStockQuantity += inboundNotificationBackItem.getInStockQuantity();//已入库数量增加流水里的数量
            }
            PurchaseOrderBackWrite purchaseOrderBackWrite = new PurchaseOrderBackWrite();
            purchaseOrderBackWrite.setInTransitQuantity(inTransitQuantity);
            purchaseOrderBackWrite.setInStockQuantity(purchaseOrder.getInStockQuantity() + inStockQuantity);
            purchaseOrderBackWrite.setPurchaseOrderNo(traceNo);
            purchaseOrderBackWrite.setOrderStatus(PurchaseStatusEnum.PART_INBOUND.getCode());
            purchaseOrderBackWrite.setTablePrefix(tablePrefix);
            purchaseOrderDao.updateBackWrite(purchaseOrderBackWrite);
            logger.info("采购单本次入库{},还需入库{}", inStockQuantity, inTransitQuantity);
            //3) 更新采购模块的预约入库单信息 先把productInfo反序列化，更新数量后再JSON序列化后update
            PurchaseScheduleDelivery purchaseScheduleDelivery = scheduleDeliveryDao.getByWarehouseInboudOrderNo(tablePrefix, gongxiaoInboundOrderNo);
            String productInfo = purchaseScheduleDelivery.getProductInfo();
            List<PlanInboundItemSummary> planInboundItemSummaryList = JSON.parseObject(productInfo, new TypeReference<List<PlanInboundItemSummary>>() {});
            for (PlanInboundItemSummary planInboundItemSummary : planInboundItemSummaryList) {
                int alreadyInboundQuantity = planInboundItemSummary.getActualInboundQuantity();
                String productIdPurchase = planInboundItemSummary.getProductCode();
                for (NotifiInboundStructure.InboundNotificationBackItem inboundNotificationBackItem : inboundNotificationBackItemList) {
                    String productIdWarehouse = inboundNotificationBackItem.getProductCode();
                    if (productIdPurchase.equals(productIdWarehouse)) {
                        alreadyInboundQuantity += inboundNotificationBackItem.getInStockQuantity();
                        planInboundItemSummary.setActualInboundQuantity(alreadyInboundQuantity);
                    }
                }
            }
            String productJson = JSON.toJSONString(planInboundItemSummaryList);
            scheduleDeliveryDao.updateSchedule(tablePrefix,purchaseScheduleDelivery.getScheduleId(), productJson);
            //4)写操作日记
            String tracelog = purchaseOrder.getTracelog();
            ArrayList<OperateHistoryRecord> recordList
                    = JSON.parseObject(tracelog, new TypeReference<ArrayList<OperateHistoryRecord>>() {});
            OperateHistoryRecord operateRecord = new OperateHistoryRecord();
            operateRecord.setOperateFunction("收货");
            operateRecord.setOperateTime(new Date());
            operateRecord.setOperateId(rpcHeader.getUid());
            operateRecord.setOperateName(rpcHeader.getUsername());
            recordList.add(operateRecord);
            String traceJson = JSON.toJSONString(recordList);
            purchaseOrderDao.updateOperateTraceLog(tablePrefix,purchaseOrder.getPurchaseOrderNo(), traceJson);
            logger.info("# [OUT] notifyPurchaseInboundServiceOut success: ");
            rpcResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            respBuilder.setRpcResult(rpcResult);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            return;
        } catch (Exception e) {
            logger.error("#[OUT] errorMessage: " + e.getMessage(), e);
            rpcResult.setReturnCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
            respBuilder.setRpcResult(rpcResult);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            return;
        }

    }

    /**
     * 采购入库单关闭通知 每张入库单完成的时候会进行回告
     *
     * 入库完成 关闭采购订单
     */
    @Override
    public void transferClosedNotification(NotifiInboundStructure.TransferClosedNotificationReq request,
                                           StreamObserver<NotifiInboundStructure.TransferClosedNotificationResp> responseObserver) {
        NotifiInboundStructure.TransferClosedNotificationResp.Builder respBuilder = NotifiInboundStructure.TransferClosedNotificationResp.newBuilder();

        String projectId = request.getProjectId();
        String traceNo = request.getTraceNo();
        String gongxiaoInboundOrderNo = request.getGongxiaoInboundOrderNo();
        String batchNo = request.getBatchNo();
        String uniqueNumber = request.getUniqueNumber();

        GongxiaoRpc.RpcResult.Builder rpcResult = GongxiaoRpc.RpcResult.newBuilder();
        logger.info(" [IN][transferClosedNotificationServiceIn] params: projectId={}, traceNo={}, gongxiaoInboundOrderNo={}",
                projectId, traceNo, gongxiaoInboundOrderNo);

        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder()
                .setTraceId(new Date().getTime() + "")
                .setUid("999")
                .setUsername("test")
                .build();

        ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
        ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                .setRpcHeader(rpcHeader)
                .setXpsChannelId(propertyUtil.getChannel() + "").build();
        ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
        ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
        String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));

            //校验唯一号
            PurchaseOrder byUniqueNumber = purchaseOrderDao.getByUniqueNumber(tablePrefix,uniqueNumber);
            if (byUniqueNumber != null) {
                logger.info("防止重复提交");
                rpcResult.setReturnCode(ErrorCode.DUPLICATED_REQUEST.getErrorCode());
                respBuilder.setRpcResult(rpcResult);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            //1)获取采购单信息
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(tablePrefix,traceNo);
            List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemDao.selectByOrderNo(tablePrefix,traceNo);

            String purchaseOrderNo = purchaseOrder.getPurchaseOrderNo();

            PurchaseScheduleDelivery purchaseScheduleDelivery = scheduleDeliveryDao.getByWarehouseInboudOrderNo(tablePrefix,gongxiaoInboundOrderNo);
            //2)获取采购方预约入库数量
            Integer totalPlanQuantity = purchaseScheduleDelivery.getTotalQuantity();
            //3)获取采购方预约入库,实际入库数量
            String productInfo = purchaseScheduleDelivery.getProductInfo();
            List<PlanInboundItemSummary> planInboundItemSummaryList = JSON.parseObject(productInfo, new TypeReference<List<PlanInboundItemSummary>>() {});
            int totalQuantity = 0;
            for (PlanInboundItemSummary planInboundItemSummary : planInboundItemSummaryList) {
                int actualInboundQuantity = planInboundItemSummary.getActualInboundQuantity();
                totalQuantity += actualInboundQuantity;
            }
            //4)更新采购模块 存根状态
            logger.info("开始更新预约入库流水状态采购单={}预约入库流水id={}", purchaseOrderNo, purchaseScheduleDelivery.getScheduleId());
            if (totalPlanQuantity == totalQuantity) { //实际入库和计划入库数量一致 则正常关闭
                scheduleDeliveryDao.updateScheduleStatus(tablePrefix,
                        purchaseScheduleDelivery.getScheduleId(),
                        PurchaseStatusEnum.NORMAL_COMPLETE.getCode());
            } else {
                scheduleDeliveryDao.updateScheduleStatus(tablePrefix,
                        purchaseScheduleDelivery.getScheduleId(),
                        PurchaseStatusEnum.EXCEPTION_COMPLETE_NOT_HANDLE.getCode());
            }
            logger.info("更新预约入库流水状态完成,采购单={}预约入库流水id={}", purchaseOrderNo, purchaseScheduleDelivery.getScheduleId());

            //5) 一张采购单可有多个入库单，需要遍历所有的入库单状态后 来判定采购单的完成状态
            List<PurchaseScheduleDelivery> purchaseScheduleDeliveryList = scheduleDeliveryDao.selectByPurchaseOrderNo(tablePrefix,purchaseOrderNo);
            boolean isComplete = true; //标记入库单是否完成
            int completeStatus = PurchaseStatusEnum.NORMAL_COMPLETE.getCode(); //90:订单正常完成  91:异常完成 未处理  92:异常完成 已处理
            int planQuantityTotal = 0;
            for (PurchaseScheduleDelivery scheduleDelivery : purchaseScheduleDeliveryList) {
                planQuantityTotal += scheduleDelivery.getTotalQuantity();
                Byte scheduleStatus = scheduleDelivery.getScheduleStatus();
                //如果有未完成,直接break
                if (scheduleStatus != PurchaseStatusEnum.NORMAL_COMPLETE.getCode()
                        && scheduleStatus != PurchaseStatusEnum.EXCEPTION_COMPLETE_NOT_HANDLE.getCode()
                        && scheduleStatus != PurchaseStatusEnum.EXCEPTION_COMPLETE_HANDLED.getCode()) {
                    isComplete = false;
                    break;
                }
                //如果都完成了,判定 如果有预约入库单 a)异常未处理: 异常未处理; b) 无异常未处理,有异常已处理: 则异常已处理; c)都是正常完成: 则正常完成
                if (scheduleStatus == PurchaseStatusEnum.NORMAL_COMPLETE.getCode()) {

                }
                if (scheduleStatus == PurchaseStatusEnum.EXCEPTION_COMPLETE_NOT_HANDLE.getCode()) {
                    if (completeStatus == PurchaseStatusEnum.NORMAL_COMPLETE.getCode()) {
                        completeStatus = PurchaseStatusEnum.EXCEPTION_COMPLETE_NOT_HANDLE.getCode();
                    }
                }
                if (scheduleStatus == PurchaseStatusEnum.EXCEPTION_COMPLETE_HANDLED.getCode()) {
                    completeStatus = PurchaseStatusEnum.EXCEPTION_COMPLETE_HANDLED.getCode();
                }
            }
            if (isComplete) {
                purchaseOrderDao.updatePurchaseOrderStatus(tablePrefix,purchaseOrderNo, completeStatus);
            }

            //如果所有预约入库单已完成且计划入库单数量==采购单数量 且为普通采购类型,则生成返利
            if (isComplete && purchaseOrder.getPurchaseType() == 1) { //
                ProjectServiceGrpc.ProjectServiceBlockingStub projectStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
                SupplierServiceGrpc.SupplierServiceBlockingStub supplierStub = RpcStubStore.getRpcStub(SupplierServiceGrpc.SupplierServiceBlockingStub.class);

                ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(projectId)
                        .build();
                ProjectStructure.GetByProjectIdResp getByProjectIdResp = projectStub.getByProjectId(getByProjectIdReq);
                ProjectStructure.Project project = getByProjectIdResp.getProject();
                long supplierId = project.getSupplierId();

                SupplierStructure.GetSupplierByIdReq supplierByIdReq = SupplierStructure.GetSupplierByIdReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setSupplierId(supplierId + "")
                        .build();
                SupplierStructure.GetSupplierByIdResp supplierByIdResp = supplierStub.getSupplierById(supplierByIdReq);
                SupplierStructure.Supplier supplier = supplierByIdResp.getSupplier();

                //6)预约入库单都已完成且未处理数量为0,生成返利/变更采购单状态
                //6.1 生成返利
                logger.info("采购单号={},开始生成返利", purchaseOrder.getPurchaseOrderNo());
                try {
                    long monthCouponRate = project.getMonthCouponRate();
                    long quarterCouponRate = project.getQuarterCouponRate();
                    long annualCouponRate = project.getAnnualCouponRate();

                    if (monthCouponRate>0){
                        XpsCouponManager.generateYhglobalCouponLedger(propertyUtil.getCouponUrl(),
                                propertyUtil.getChannel(),
                                xpsChannelSecret,
                                rpcHeader.getUid(),
                                rpcHeader.getUsername(),
                                Long.parseLong(projectId),
                                "CNY",
                                purchaseOrder.getBrandOrderNo(),
                                purchaseOrder.getPurchaseOrderNo(),
                                DateUtil.dateToString(purchaseOrder.getBusinessDate()),
                                purchaseOrder.getPurchaseShouldPayAmount(),
                                CouponLedgerCouponType.COUPON_TYPE_MONTHLY.getCode(),
                                monthCouponRate,
                                project.getProjectName(),
                                Long.parseLong(purchaseOrder.getSupplierId()),
                                purchaseOrder.getSupplierName()
                                );
                        logger.info("生成月返成功");
                    }
                    if (quarterCouponRate>0){
                        XpsCouponManager.generateYhglobalCouponLedger(propertyUtil.getCouponUrl(),
                                propertyUtil.getChannel(),
                                xpsChannelSecret,
                                rpcHeader.getUid(),
                                rpcHeader.getUsername(),
                                Long.parseLong(projectId),
                                "CNY",
                                purchaseOrder.getBrandOrderNo(),
                                purchaseOrder.getPurchaseOrderNo(),
                                DateUtil.dateToString(purchaseOrder.getBusinessDate()),
                                purchaseOrder.getPurchaseShouldPayAmount(),
                                CouponLedgerCouponType.COUPON_TYPE_QUARTERLY.getCode(),
                                quarterCouponRate,
                                project.getProjectName(),
                                Long.parseLong(purchaseOrder.getSupplierId()),
                                purchaseOrder.getSupplierName()
                        );
                        logger.info("生成季返成功");
                    }
                    if (annualCouponRate>0){
                        XpsCouponManager.generateYhglobalCouponLedger(propertyUtil.getCouponUrl(),
                                propertyUtil.getChannel(),
                                xpsChannelSecret,
                                rpcHeader.getUid(),
                                rpcHeader.getUsername(),
                                Long.parseLong(projectId),
                                "CNY",
                                purchaseOrder.getBrandOrderNo(),
                                purchaseOrder.getPurchaseOrderNo(),
                                DateUtil.dateToString(purchaseOrder.getBusinessDate()),
                                purchaseOrder.getPurchaseShouldPayAmount(),
                                CouponLedgerCouponType.COUPON_TYPE_ANNUAL.getCode(),
                                annualCouponRate,
                                project.getProjectName(),
                                Long.parseLong(purchaseOrder.getSupplierId()),
                                purchaseOrder.getSupplierName()
                        );
                        logger.info("生成年返成功");
                    }
                }catch (Exception e){
                    logger.error("采购单号={},生成返利失败", purchaseOrder.getPurchaseOrderNo());
                }

                logger.info("采购单号={},生成返利成功", purchaseOrder.getPurchaseOrderNo());
                //6.2 变更采购单状态为返利生成完成
                purchaseOrderDao.updatePurchaseOrderStatus(tablePrefix,purchaseOrderNo, PurchaseStatusEnum.GENERATE_COUPON.getCode());
            }

            logger.info("#traceId={}# [OUT] transferClosedNotificationServiceOut success: ", rpcHeader.getTraceId());
            rpcResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            respBuilder.setRpcResult(rpcResult);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            return;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            rpcResult.setReturnCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
            respBuilder.setRpcResult(rpcResult);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            return;
        }

    }

}
