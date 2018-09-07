package com.yhglobal.gongxiao.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.dao.PurchaseOrderDao;
import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.dao.PurchaseScheduleDeliveryDao;

import com.yhglobal.gongxiao.eas.PurchaseBasicOrderInbound;
import com.yhglobal.gongxiao.eas.PurchaseBasicOrderItemInbound;
import com.yhglobal.gongxiao.eas.PurchaseEasInboundModel;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.supplier.model.Supplier;
import com.yhglobal.gongxiao.foundation.supplier.service.SupplierService;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.purchase.bo.InboundNotificationBackItem;
import com.yhglobal.gongxiao.purchase.bo.PlanInboundItem;
import com.yhglobal.gongxiao.purchase.dto.PurchaseOrderBackWrite;
import com.yhglobal.gongxiao.purchase.dto.PurchaseOrderItemBackWrite;
import com.yhglobal.gongxiao.model.PurchaseOrder;
import com.yhglobal.gongxiao.model.PurchaseOrderItem;
import com.yhglobal.gongxiao.model.PurchaseScheduleDelivery;
import com.yhglobal.gongxiao.purchase.temp.OperateHistoryRecord;
import com.yhglobal.gongxiao.purchase.temp.PlanInboundItemSummary;
import com.yhglobal.gongxiao.purchase.service.YhglobalCouponLedgerService;
import com.yhglobal.gongxiao.status.PurchaseStatusEnum;
import com.yhglobal.gongxiao.warehouse.service.InboundNotificationService;
import com.yhglobal.gongxiao.warehouse.service.InboundService;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 采购入库通知单回写
 *
 * @author: 陈浩
 **/
@Service(timeout = 6000)
public class InboundNotificationServiceImpl implements InboundNotificationService {

    private static Logger logger = LoggerFactory.getLogger(InboundNotificationServiceImpl.class);

    //基础数据
    @Reference
    ProjectService projectService; //项目信息

    @Reference
    WarehouseService warehouseService;

    @Reference
    ProductService productService;

    @Reference
    InboundService inboundService;

    @Reference
    SupplierService supplierService;

    @Autowired
    YhglobalCouponLedgerService yhglobalcouponledgerService;

    @Autowired
    PurchaseOrderDao purchaseOrderDao; //采购单号

    @Autowired
    PurchaseOrderItemDao purchaseOrderItemDao; //采购明细

    @Autowired
    PurchaseScheduleDeliveryDao scheduleDeliveryDao; //预约收货

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public PurchaseEasInboundModel transferReceivedNotification(RpcHeader rpcHeader,
                                                                String projectId,
                                                                String traceNo,
                                                                String gongxiaoInboundOrderNo,
                                                                List<InboundNotificationBackItem> inboundNotificationBackItemList,
                                                                String uniqueNumber) {
        logger.info("#traceId={}# [IN][transferReceivedNotification] params: #projectId={} gongxiaoInboundOrderNo={} traceId={} ",
                rpcHeader.traceId, projectId, traceNo, gongxiaoInboundOrderNo);
        PurchaseEasInboundModel purchaseEasInboundModel=new PurchaseEasInboundModel();

        try {
            //1)获取采购单信息
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(traceNo);
            if (purchaseOrder == null) {
                logger.warn("未找到采购单号={}的采购单", traceNo);
                purchaseEasInboundModel.setErrorCode(ErrorCode.NOT_FINDING_PURCHASE_ORDER.getErrorCode());
                return purchaseEasInboundModel;
            }
            String easOrderNumber = purchaseOrder.getEasOrderNumber();
            if (easOrderNumber==null||"".equals(easOrderNumber)){
                logger.warn("采购单没有同步eas");
                purchaseEasInboundModel.setErrorCode(ErrorCode.PURCHASE_ORDER_NOT_SYN_EAS.getErrorCode());
                return purchaseEasInboundModel;
            }
            List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemDao.selectByOrderNo(traceNo);
            Project project = projectService.getByProjectId(rpcHeader, projectId);
            Supplier supplier = supplierService.getSupplierById(rpcHeader, purchaseOrder.getSupplierId());

            //5)同步EAS
            List<InboundOrderItem> inboundOrders
                    = inboundService.getInboundItemRecord(rpcHeader, projectId, purchaseOrder.getPurchaseOrderNo());
            //5.1 构造采购单数据
            PurchaseBasicOrderInbound purchaseBasicOrderInbound = new PurchaseBasicOrderInbound();
            List<PurchaseBasicOrderItemInbound> purchaseBasicOrderItemInboundList = new ArrayList<>();

            purchaseBasicOrderInbound.setProjectId(project.getEasProjectCode());
            purchaseBasicOrderInbound.setSupplierCode(supplier.getEasSupplierCode());
            purchaseBasicOrderInbound.setId(purchaseOrder.getEasPurchaseOrderId());
            purchaseBasicOrderInbound.setPurchaseOrderNum(purchaseOrder.getEasOrderNumber());
            purchaseBasicOrderInbound.setBusinessDate(purchaseOrder.getBusinessDate());
            purchaseBasicOrderInbound.setRequireArrivalDate(purchaseOrder.getExpectedInboundDate());
            //5.2 拼装采购单明细
            int inStockNumberTotal = 0;
            double totalTaxAmount = 0;
            int number = 0;

            for (InboundNotificationBackItem inboundOrder : inboundNotificationBackItemList) {
                String connectedProduct1 = inboundOrder.getProductCode();
                //如果是赠品,放在赠品库位 如果是非赠品,区分良品库位和次品库位
                if (inboundOrder.isGift()){
                    for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
                        String productCode = purchaseOrderItem.getProductCode();
                        ProductBasic productBasic = productService.getByProductCode(rpcHeader, productCode);
                        if (productCode.equals(connectedProduct1)) {
                            double discount = (1-((double)purchaseOrderItem.getCostPrice()/purchaseOrderItem.getPurchasePrice()))*100;
                            logger.info("采购单={}，入库单号={}，残次品数量={}，正品数量={}",purchaseOrder.getPurchaseOrderNo(),inboundOrder.getInboundNotificationItemId(),inboundOrder.getImperfectQuantity(),inboundOrder.getInStockQuantity());
                            number = inboundOrder.getInStockQuantity() + inboundOrder.getImperfectQuantity();
                            inStockNumberTotal = inStockNumberTotal + number;
                            totalTaxAmount = totalTaxAmount + (purchaseOrderItem.getGuidePrice() * inStockNumberTotal);
                            //添加良品
                            PurchaseBasicOrderItemInbound purchaseBasicOrderItemInbound = new PurchaseBasicOrderItemInbound();
                            purchaseBasicOrderItemInbound.setTaxRate(FXConstant.TAX_RATE);
                            purchaseBasicOrderItemInbound.setTaxPrice((double) purchaseOrderItem.getGuidePrice() / FXConstant.MILLION);
                            Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, inboundOrder.getWarehouseId() + "");
                            purchaseBasicOrderItemInbound.setWarehouseCode(warehouse.getEasWarehouseCode());
                            purchaseBasicOrderItemInbound.setLocationId("05");//todo:先假设都是良品,如果既有良品,又有次品要分为两个单号
                            purchaseBasicOrderItemInbound.setLot(inboundOrder.getBatchNo());
                            purchaseBasicOrderItemInbound.setNumber(inboundOrder.getInStockQuantity()+inboundOrder.getImperfectQuantity());
                            purchaseBasicOrderItemInbound.setMaterialId(purchaseOrderItem.getEasMateriaCode());
                            purchaseBasicOrderItemInbound.setPurchaseOrderId(purchaseOrder.getEasPurchaseOrderId());
                            purchaseBasicOrderItemInbound.setPurchaseOrderEntryId(purchaseOrderItem.getEasEntryId());
                            purchaseBasicOrderItemInbound.setUnit(productBasic.getEasUnitCode());
                            purchaseBasicOrderItemInbound.setDiscount(discount);
                            purchaseBasicOrderItemInboundList.add(purchaseBasicOrderItemInbound);
                        }
                    }
                }else {
                    //采购入库单跟采购明细做匹配,获取采购明细的easid
                    for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
                        String productCode = purchaseOrderItem.getProductCode();
                        ProductBasic productBasic = productService.getByProductCode(rpcHeader, productCode);
                        if (productCode.equals(connectedProduct1)) {
                            double discount = (1-((double)purchaseOrderItem.getCostPrice()/purchaseOrderItem.getPurchasePrice()))*100;
                            logger.info("采购单={}，入库单号={}，残次品数量={}，正品数量={}",purchaseOrder.getPurchaseOrderNo(),inboundOrder.getInboundNotificationItemId(),inboundOrder.getImperfectQuantity(),inboundOrder.getInStockQuantity());
                            number = inboundOrder.getInStockQuantity() + inboundOrder.getImperfectQuantity();
                            inStockNumberTotal = inStockNumberTotal + number;
                            totalTaxAmount = totalTaxAmount + (purchaseOrderItem.getGuidePrice() * inStockNumberTotal);
                            //添加良品
                            PurchaseBasicOrderItemInbound purchaseBasicOrderItemInbound = new PurchaseBasicOrderItemInbound();
                            purchaseBasicOrderItemInbound.setTaxRate(FXConstant.TAX_RATE);
                            purchaseBasicOrderItemInbound.setTaxPrice((double) purchaseOrderItem.getGuidePrice() / FXConstant.MILLION);
                            Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, inboundOrder.getWarehouseId() + "");
                            purchaseBasicOrderItemInbound.setWarehouseCode(warehouse.getEasWarehouseCode());
                            purchaseBasicOrderItemInbound.setLocationId("01");//todo:先假设都是良品,如果既有良品,又有次品要分为两个单号
                            purchaseBasicOrderItemInbound.setLot(inboundOrder.getBatchNo());
                            purchaseBasicOrderItemInbound.setNumber(inboundOrder.getInStockQuantity());
                            purchaseBasicOrderItemInbound.setMaterialId(purchaseOrderItem.getEasMateriaCode());
                            purchaseBasicOrderItemInbound.setPurchaseOrderId(purchaseOrder.getEasPurchaseOrderId());
                            purchaseBasicOrderItemInbound.setPurchaseOrderEntryId(purchaseOrderItem.getEasEntryId());
                            purchaseBasicOrderItemInbound.setUnit(productBasic.getEasUnitCode());
                            purchaseBasicOrderItemInbound.setDiscount(discount);
                            purchaseBasicOrderItemInboundList.add(purchaseBasicOrderItemInbound);
                            //添加残次品
                            if (inboundOrder.getImperfectQuantity() > 0) {
                                PurchaseBasicOrderItemInbound purchaseBasicOrderItemInboundNotPerfect
                                        = (PurchaseBasicOrderItemInbound) purchaseBasicOrderItemInbound.clone();
                                purchaseBasicOrderItemInboundNotPerfect.setLocationId("02");//残次品
                                purchaseBasicOrderItemInboundNotPerfect.setNumber(inboundOrder.getImperfectQuantity());
                                purchaseBasicOrderItemInboundList.add(purchaseBasicOrderItemInboundNotPerfect);
                            }
                        }
                    }
                }

            }
            purchaseBasicOrderInbound.setNumber(number);
            double rate = FXConstant.TAX_RATE / 100.0;//税率
            double taxAmountTotal = totalTaxAmount / FXConstant.MILLION;//含税金额
            double taxAmount = taxAmountTotal * (rate / (1 + rate));//税额
            purchaseBasicOrderInbound.setTotalTax(taxAmount);
            purchaseBasicOrderInbound.setTotalTaxAmount(taxAmountTotal);

            purchaseEasInboundModel.setPurchaseBasicOrderInbound(purchaseBasicOrderInbound);
            purchaseEasInboundModel.setPurchaseBasicOrderItemInboundList(purchaseBasicOrderItemInboundList);
            //5.3 同步到EAS
//            logger.info("采购入库单对应的 FX单号={} EAS采购单号={}", purchaseOrder.getPurchaseOrderNo(), purchaseOrder.getEasOrderNumber());
//            String easJson = EasUtil.sendPurchaseInbound2Eas(purchaseBasicOrderInbound, purchaseBasicOrderItemInboundList);
            return purchaseEasInboundModel;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return null;
        }

    }

    @Override
    public RpcResult notifyPurchaseInbound(RpcHeader rpcHeader,
                                           String projectId,
                                           String traceNo,
                                           String gongxiaoInboundOrderNo,
                                           List<InboundNotificationBackItem> inboundNotificationBackItemList,
                                           String uniqueNumber) {
        logger.info("#traceId={}# [IN][notifyPurchaseInbound] params: #projectId={} gongxiaoInboundOrderNo={} traceId={} ",
                rpcHeader.traceId, projectId, traceNo, gongxiaoInboundOrderNo);                //校验唯一号
        try {
            PurchaseOrder byUniqueNumber = purchaseOrderDao.getByUniqueNumber(uniqueNumber);
            if (byUniqueNumber != null) {
                logger.info("防止重复提交");
                return new RpcResult(ErrorCode.DUPLICATED_REQUEST.getErrorCode(), ErrorCode.DUPLICATED_REQUEST.getMessage());
            }
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(traceNo);
            //1)变更采购数量校验
            int totalQuantity = purchaseOrder.getTotalQuantity(); //获取预约入库单的预约入库数量
            int inTransitQuantity = purchaseOrder.getInTransitQuantity();//获取在途商品数量
            int inStockQuantity = purchaseOrder.getInStockQuantity();//获取已入库的商品数量
            for (InboundNotificationBackItem inboundNotificationBackItem : inboundNotificationBackItemList) {
                PurchaseOrderItem purchaseOrderItem =
                        purchaseOrderItemDao.selectByOrderNoAndProduct(purchaseOrder.getPurchaseOrderNo(),
                                inboundNotificationBackItem.getProductCode());
                int inStockNumber = inboundNotificationBackItem.getInStockQuantity();
                logger.info("仓管推送的采购数量为:{}", inStockNumber);
                int inStockQuantityItem = purchaseOrderItem.getInStockQuantity();//获取已入库的商品数量
                inStockQuantityItem += inStockNumber;//已入库数量增加流水里的数量
                if (inStockQuantityItem > totalQuantity) {
                    logger.warn("已入库完成,无需再入库");
                    return new RpcResult(ErrorCode.INBOUND_ALREADY_COMPLETE.getErrorCode(), ErrorCode.INBOUND_ALREADY_COMPLETE.getMessage());
                }
            }
            //2)变更采购入库数量
            for (InboundNotificationBackItem inboundNotificationBackItem : inboundNotificationBackItemList) {
                PurchaseOrderItem purchaseOrderItem =
                        purchaseOrderItemDao.selectByOrderNoAndProduct(purchaseOrder.getPurchaseOrderNo(),
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
                purchaseOrderItemDao.updateBackWrite(purchaseOrderItemBackWrite);

                inTransitQuantity -= inboundNotificationBackItem.getInStockQuantity(); //在途数量扣减已入库的数量
                inStockQuantity += inboundNotificationBackItem.getInStockQuantity();//已入库数量增加流水里的数量
            }
            PurchaseOrderBackWrite purchaseOrderBackWrite = new PurchaseOrderBackWrite();
            purchaseOrderBackWrite.setInTransitQuantity(inTransitQuantity);
            purchaseOrderBackWrite.setInStockQuantity(purchaseOrder.getInStockQuantity() + inStockQuantity);
            purchaseOrderBackWrite.setPurchaseOrderNo(traceNo);
            purchaseOrderBackWrite.setOrderStatus(PurchaseStatusEnum.PART_INBOUND.getCode());
            purchaseOrderDao.updateBackWrite(purchaseOrderBackWrite);
            logger.info("采购单本次入库{},还需入库{}", inStockQuantity, inTransitQuantity);
            //3)回写采购方预约入库信息
            PurchaseScheduleDelivery purchaseScheduleDelivery = scheduleDeliveryDao.getByWarehouseInboudOrderNo(gongxiaoInboundOrderNo);
            String productInfo = purchaseScheduleDelivery.getProductInfo();
            List<PlanInboundItemSummary> planInboundItemSummaryList = JSON.parseObject(productInfo, new TypeReference<List<PlanInboundItemSummary>>() {
            });
            for (PlanInboundItemSummary planInboundItemSummary : planInboundItemSummaryList) {
                int alreadyInboundQuantity = planInboundItemSummary.getActualInboundQuantity();
                String productIdPurchase = planInboundItemSummary.getProductCode();
                for (InboundNotificationBackItem inboundNotificationBackItem : inboundNotificationBackItemList) {
                    String productIdWarehouse = inboundNotificationBackItem.getProductCode();
                    if (productIdPurchase.equals(productIdWarehouse)) {
                        alreadyInboundQuantity += inboundNotificationBackItem.getInStockQuantity();
                        planInboundItemSummary.setActualInboundQuantity(alreadyInboundQuantity);
                    }
                }
            }
            String productJson = JSON.toJSONString(planInboundItemSummaryList);
            scheduleDeliveryDao.updateSchedule(purchaseScheduleDelivery.getScheduleId(), productJson);
            //4)写操作日记
            String tracelog = purchaseOrder.getTracelog();
            ArrayList<OperateHistoryRecord> recordList
                    = JSON.parseObject(tracelog, new TypeReference<ArrayList<OperateHistoryRecord>>() {
            });
            OperateHistoryRecord operateRecord = new OperateHistoryRecord();
            operateRecord.setOperateFunction("收货");
            operateRecord.setOperateTime(new Date());
            operateRecord.setOperateId(rpcHeader.getUid());
            operateRecord.setOperateName(rpcHeader.getUsername());
            recordList.add(operateRecord);
            String traceJson = JSON.toJSONString(recordList);
            purchaseOrderDao.updateOperateTraceLog(purchaseOrder.getPurchaseOrderNo(), traceJson);
            logger.info("#traceId={}# [OUT] notifyPurchaseInbound success: ", rpcHeader.traceId);
            return new RpcResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return new RpcResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(), ErrorCode.UNKNOWN_ERROR.getMessage());
        }

    }

    @Override
    public int transferMisReceivedNotification(RpcHeader rpcHeader,
                                               String projectId,
                                               String traceNo,
                                               String gongxiaoInboundOrderNo,
                                               List<PlanInboundItem> planInboundItemList,
                                               String uniqueNumber) {
        return 0;
    }

    @Override
    public RpcResult transferClosedNotification(RpcHeader rpcHeader,
                                                String projectId,
                                                String traceNo,
                                                String gongxiaoInboundOrderNo,
                                                String batchNo,
                                                String uniqueNumber) {
        logger.info("#traceId={}# [IN][transferClosedNotification] params: projectId={}, traceNo={}, gongxiaoInboundOrderNo={}",
                rpcHeader.traceId, projectId, traceNo, gongxiaoInboundOrderNo);
        try {

            //校验唯一号
            PurchaseOrder byUniqueNumber = purchaseOrderDao.getByUniqueNumber(uniqueNumber);
            if (byUniqueNumber != null) {
                logger.info("防止重复提交");
                return new RpcResult(ErrorCode.DUPLICATED_REQUEST.getErrorCode(), ErrorCode.DUPLICATED_REQUEST.getMessage());
            }
            //1)获取采购单信息
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(traceNo);
            List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemDao.selectByOrderNo(traceNo);

            String purchaseOrderNo = purchaseOrder.getPurchaseOrderNo();

            PurchaseScheduleDelivery purchaseScheduleDelivery = scheduleDeliveryDao.getByWarehouseInboudOrderNo(gongxiaoInboundOrderNo);
            //2)获取采购方预约入库数量
            Integer totalPlanQuantity = purchaseScheduleDelivery.getTotalQuantity();
            //3)获取采购方预约入库,实际入库数量
            String productInfo = purchaseScheduleDelivery.getProductInfo();
            List<PlanInboundItemSummary> planInboundItemSummaryList = JSON.parseObject(productInfo, new TypeReference<List<PlanInboundItemSummary>>() {
            });
            int totalQuantity = 0;
            for (PlanInboundItemSummary planInboundItemSummary : planInboundItemSummaryList) {
                int actualInboundQuantity = planInboundItemSummary.getActualInboundQuantity();
                totalQuantity += actualInboundQuantity;
            }
            //4)更新采购预约收获单状态
            logger.info("开始更新预约入库流水状态采购单={}预约入库流水id={}", purchaseOrderNo, purchaseScheduleDelivery.getScheduleId());
            if (totalPlanQuantity == totalQuantity) {
                scheduleDeliveryDao.updateScheduleStatus(purchaseScheduleDelivery.getScheduleId(), PurchaseStatusEnum.NORMAL_COMPLETE.getCode());
            } else {
                scheduleDeliveryDao.updateScheduleStatus(purchaseScheduleDelivery.getScheduleId(), PurchaseStatusEnum.EXCEPTION_COMPLETE_NOT_HANDLE.getCode());
            }
            logger.info("更新预约入库流水状态完成,采购单={}预约入库流水id={}", purchaseOrderNo, purchaseScheduleDelivery.getScheduleId());

            //5)判定采购预约单是否完成
            List<PurchaseScheduleDelivery> purchaseScheduleDeliveryList = scheduleDeliveryDao.selectByPurchaseOrderNo(purchaseOrderNo);
            boolean isComplete = true;
            int completeStatus = PurchaseStatusEnum.NORMAL_COMPLETE.getCode(); //订单正常完成 90 ,异常完成 已处理92 异常完成 未处理91
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
                //如果都完成了,判定 如果有预约入库单异常未处理则异常未处理;无异常未处理,有异常已处理,则异常已处理;都是正常完成,则正常完成
                if (scheduleStatus == PurchaseStatusEnum.NORMAL_COMPLETE.getCode()) {

                }
                if (scheduleStatus == PurchaseStatusEnum.EXCEPTION_COMPLETE_NOT_HANDLE.getCode()) {
                    if (completeStatus == PurchaseStatusEnum.NORMAL_COMPLETE.getCode()) {
                        completeStatus = scheduleStatus = PurchaseStatusEnum.EXCEPTION_COMPLETE_NOT_HANDLE.getCode();
                    }
                }
                if (scheduleStatus == PurchaseStatusEnum.EXCEPTION_COMPLETE_HANDLED.getCode()) {
                    completeStatus = PurchaseStatusEnum.EXCEPTION_COMPLETE_HANDLED.getCode();
                }
            }
            if (isComplete){
                purchaseOrderDao.updatePurchaseOrderStatus(purchaseOrderNo, completeStatus);
            }

            //如果所有预约入库单已完成且计划入库单数量==采购单数量 更新采购单状态
            if (isComplete && planQuantityTotal == purchaseOrder.getTotalQuantity()) {
                Project project = projectService.getByProjectId(rpcHeader, projectId);
                Supplier supplier = supplierService.getSupplierByProjectId(rpcHeader, projectId);
                //6)预约入库单都已完成且未处理数量为0,生成返利/变更采购单状态
                //6.1 生成返利
                logger.info("采购单号={},开始生成返利", purchaseOrder.getPurchaseOrderNo());
                yhglobalcouponledgerService.generateYhglobalCouponLedger(purchaseOrder);
                logger.info("采购单号={},生成返利成功", purchaseOrder.getPurchaseOrderNo());
                //6.2 变更采购单状态
                purchaseOrderDao.updatePurchaseOrderStatus(purchaseOrderNo,PurchaseStatusEnum.GENERATE_COUPON.getCode());
            }

            logger.info("#traceId={}# [OUT] transferClosedNotification success: ", rpcHeader.traceId);
            return new RpcResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return null;
        }

    }

}
