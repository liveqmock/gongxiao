package com.yhglobal.gongxiao.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.dao.PurchaseReturnedOrderDao;
import com.yhglobal.gongxiao.dao.PurchaseReturnedOrderItemDao;
import com.yhglobal.gongxiao.dao.PurchaseReverseDeliveryDao;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;

import com.yhglobal.gongxiao.purchase.bo.OutboundNotificationBackItem;

import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.purchase.temp.PurchaseOutboundItemSummary;
import com.yhglobal.gongxiao.status.PurchaseStatusEnum;
import com.yhglobal.gongxiao.warehouse.service.OutboundNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购退货回写
 *
 * @author: 陈浩
 **/
@Service
public class PurchaseOutboundNotificationServiceImpl implements OutboundNotificationService {
    private static Logger logger = LoggerFactory.getLogger(PurchaseOutboundNotificationServiceImpl.class);

    @Reference
    ProjectService projectService;

    @Autowired
    PurchaseReturnedOrderDao purchaseReturnedOrderDao;

    @Autowired
    PurchaseReturnedOrderItemDao purchaseReturnedOrderItemDao;

    @Autowired
    PurchaseReverseDeliveryDao purchaseReverseDeliveryDao;

    @Autowired
    PurchaseOrderItemDao purchaseOrderItemDao;//采购单明细

    @Override
    public int transferDepartureNotification(RpcHeader rpcHeader,
                                             String projectId,
                                             String traceNo,
                                             String gongxiaoOutboundOrderNo,
                                             List<OutboundNotificationBackItem> planOutboundItemList) {
        logger.info("#traceId={}# [IN][getCurrencyByCode] params: #projectId={} #traceNo={} #gongxiaoOutboundOrderNo={}",
                rpcHeader.traceId, projectId, traceNo, gongxiaoOutboundOrderNo);
        try {
            //1)获取各采购退货信息
            PurchaseReturnedOrder purchaseReturnedOrder = purchaseReturnedOrderDao.getByReturnedOrderNo(traceNo);
            List<PurchaseReturnedOrderItem> purchaseReturnedOrderItemList = purchaseReturnedOrderItemDao.selectByReturnedOrderNo(traceNo);
            PurchaseReverseDelivery purchaseReverseDelivery = purchaseReverseDeliveryDao.getDeliveryByOutboundOrderNo(gongxiaoOutboundOrderNo);
            //2)获取基本数据
            String originalPurchaseOrderNo = purchaseReturnedOrder.getOriginalPurchaseOrderNo();
            String purchaseReturnedOrderNo = purchaseReturnedOrder.getPurchaseReturnedOrderNo();
            String currencyCode = purchaseReturnedOrder.getCurrencyCode();
            int totalReturnedQuantity = purchaseReturnedOrder.getTotalReturnedQuantity();
            int outStockQuantity = purchaseReturnedOrder.getOutStockQuantity();
            int totalImperfectQuantity = purchaseReturnedOrder.getTotalImperfectQuantity();
            int deliveredQuantity = purchaseReturnedOrder.getDeliveredQuantity();
            //3)拼接数据
            for (OutboundNotificationBackItem outboundNotificationBackItem : planOutboundItemList) {
                //拿到wms过来的相关数据
                String businessItemId = outboundNotificationBackItem.getBusinessItemId();
                int outboundQuantity = outboundNotificationBackItem.getOutboundQuantity();
                int signedReceiptQuantity = outboundNotificationBackItem.getSignedReceiptQuantity();
                int imperfectQuantity = outboundNotificationBackItem.getImperfectQuantity();
                //采购订单变化
                outStockQuantity += outboundQuantity;
                deliveredQuantity += signedReceiptQuantity;
                totalImperfectQuantity += imperfectQuantity;
                //采购明细变化
                for (PurchaseReturnedOrderItem purchaseReturnedOrderItem : purchaseReturnedOrderItemList) {
                    Long rowId = purchaseReturnedOrderItem.getRowId();
                    if (businessItemId.equals(rowId + "")) {
                        int totalImperfectQuantityItem = purchaseReturnedOrderItem.getTotalImperfectQuantity();
                        int totalOutStockQuantityItem = purchaseReturnedOrderItem.getTotalOutStockQuantity();
                        int deliveredQuantityItem = purchaseReturnedOrderItem.getDeliveredQuantity();
                        totalOutStockQuantityItem += outboundQuantity;
                        totalImperfectQuantityItem += imperfectQuantity;
                        deliveredQuantityItem += signedReceiptQuantity;
                        int i = purchaseReturnedOrderItemDao.updateByReturnOrderNo(rowId, (byte) 1, totalOutStockQuantityItem, totalImperfectQuantityItem, deliveredQuantityItem);
                    }

                }
            }
            //变更采购单数据
            int i = purchaseReturnedOrderDao.updateByReturnOrderNo(traceNo, (byte) 1, outStockQuantity, deliveredQuantity);

            //4)写采购退货-同步wms的流水
            String productInfo = purchaseReverseDelivery.getProductInfo();

            List<PurchaseOutboundItemSummary> purchaseOutboundItemSummaryList
                    = JSON.parseObject(productInfo, new TypeReference<List<PurchaseOutboundItemSummary>>() {
            });
            for (PurchaseOutboundItemSummary purchaseOutboundItemSummary : purchaseOutboundItemSummaryList) { //外层循环是采购-记录的预约出库
                String productCode = purchaseOutboundItemSummary.getProductCode();
                int actualOutboundQuantity = purchaseOutboundItemSummary.getActualOutboundQuantity();
                int signedReceiptQuantitySummery = purchaseOutboundItemSummary.getSignedReceiptQuantity();
                for (OutboundNotificationBackItem outboundNotificationBackItem : planOutboundItemList) { //
                    String productCodeWMS = outboundNotificationBackItem.getProductCode();
                    if (productCode.equals(productCodeWMS)) {
                        int outboundQuantity = outboundNotificationBackItem.getOutboundQuantity();
                        int signedReceiptQuantity = outboundNotificationBackItem.getSignedReceiptQuantity();
                        actualOutboundQuantity += outboundQuantity;
                        signedReceiptQuantitySummery += signedReceiptQuantity;
                    }
                    break;
                }
                purchaseOutboundItemSummary.setActualOutboundQuantity(actualOutboundQuantity);
                purchaseOutboundItemSummary.setSignedReceiptQuantity(signedReceiptQuantitySummery);
                purchaseOutboundItemSummary.setOrderStatus((byte) 1);

            }
            String productInfoJson = JSON.toJSONString(purchaseOutboundItemSummaryList);
            purchaseReverseDeliveryDao.updateByOutboundOrderNo(gongxiaoOutboundOrderNo, (byte) 1, outStockQuantity, productInfoJson);
            logger.info("#traceId={}# [OUT] transferDepartureNotification success: ", rpcHeader.traceId);
            return 1;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }


    }

    @Override
    public int transferClosedNotification(RpcHeader rpcHeader,
                                          String projectId,
                                          String traceNo,
                                          String gongxiaoOutboundOrderNo) {
        logger.info("#traceId={}# [IN][getCurrencyByCode] params: projectId={}, traceNo={}, gongxiaoOutboundOrderNo={}",
                rpcHeader.traceId,projectId,traceNo,gongxiaoOutboundOrderNo);
        try {
            //1)回写采退WMS-流水
            PurchaseReverseDelivery purchaseReverseDelivery = purchaseReverseDeliveryDao.getDeliveryByOutboundOrderNo(gongxiaoOutboundOrderNo);
            String productInfo = purchaseReverseDelivery.getProductInfo();
            List<PurchaseOutboundItemSummary> purchaseOutboundItemSummaryList = JSON.parseObject(productInfo, new TypeReference<List<PurchaseOutboundItemSummary>>() {
            });
            boolean isBeingException = false;

            Map<String, Integer> productMap = new HashMap();

            for (PurchaseOutboundItemSummary purchaseOutboundItemSummary : purchaseOutboundItemSummaryList) {
                productMap.put(purchaseOutboundItemSummary.getProductCode(), purchaseOutboundItemSummary.getPurchaseOutboundQuantity());
                int purchaseOutboundQuantity = purchaseOutboundItemSummary.getPurchaseOutboundQuantity();
                int actualOutboundQuantity = purchaseOutboundItemSummary.getActualOutboundQuantity();
                if (purchaseOutboundQuantity != actualOutboundQuantity) {
                    isBeingException = true;
                    break;
                }
            }
            if (isBeingException) {
                purchaseReverseDeliveryDao.updateStatus(gongxiaoOutboundOrderNo, (byte) PurchaseStatusEnum.EXCEPTION_COMPLETE_NOT_HANDLE.getCode());
            }
            //2)判定是否完成采退
            boolean isAllComplete = true;
            List<PurchaseReverseDelivery> purchaseReverseDeliveries = purchaseReverseDeliveryDao.selectByReturnOrder(traceNo);
            for (PurchaseReverseDelivery reverseDelivery : purchaseReverseDeliveries) {
                Byte reverseStatus = reverseDelivery.getReverseStatus();
                if (PurchaseStatusEnum.NORMAL_COMPLETE.getCode() != reverseStatus
                        && PurchaseStatusEnum.EXCEPTION_COMPLETE_NOT_HANDLE.getCode() != reverseStatus
                        && PurchaseStatusEnum.EXCEPTION_COMPLETE_HANDLED.getCode() != reverseStatus) {
                    isAllComplete = false;
                    break;
                }
            }
            //3)完成则退还已使用返利,退还已使用代垫
            if (isAllComplete) {
                PurchaseReturnedOrder purchaseReturnedOrder = purchaseReturnedOrderDao.getByReturnedOrderNo(traceNo);
                long returnedCouponAmount = purchaseReturnedOrder.getReturnedCouponAmount();
                long returnedPrepaidAmount = purchaseReturnedOrder.getReturnedPrepaidAmount();
                //:todo 退还返利金额与代垫金额 等葛灿接口拆分

                //4)如果退货单已完成,还要退还已生成返利
                Byte returnedOrderStatus = purchaseReturnedOrder.getReturnedOrderStatus();
                if (PurchaseStatusEnum.GENERATE_COUPON.getCode() == returnedOrderStatus) {
                    double returnMonthlyCouponAmount = 0;
                    double returnQuarterlyCouponAmount = 0;
                    double returnAnnualCouponAmount = 0;

                    Project project = projectService.getByProjectId(rpcHeader, projectId);
                    double monthlyCouponGenerateRate = project.getMonthlyCouponGenerateRate() / 10000.0;
                    double quarterlyCouponGenerateRate = project.getQuarterlyCouponGenerateRate() / 1000.0;
                    double annualCouponGenerateRate = project.getAnnualCouponGenerateRate() / 10000.0;
                    //获取采购单货品信息的返利基数
                    List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemDao.selectByOrderNo(purchaseReturnedOrder.getOriginalPurchaseOrderNo());
                    //获得各返利金额
                    for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {
                        String productCode = purchaseOrderItem.getProductCode();
                        int number = productMap.get(productCode);
                        if (number > 0) {
                            Long couponBasePrice = purchaseOrderItem.getCouponBasePrice();
                            returnMonthlyCouponAmount += couponBasePrice * monthlyCouponGenerateRate;
                            returnQuarterlyCouponAmount += couponBasePrice * quarterlyCouponGenerateRate;
                            returnAnnualCouponAmount += couponBasePrice * annualCouponGenerateRate;
                        }
                    }
                    //退还已生成返利
//                    if (monthlyCouponGenerateRate > 0) {
//                        PurchaseCouponLedger purchaseCouponLedger = generateCoupon(purchaseReturnedOrder, project, (byte) 1, monthlyCouponGenerateRate, returnMonthlyCouponAmount);
//                        purchaseCouponLedgerDao.insert(purchaseCouponLedger);
//                    }
//                    if (returnQuarterlyCouponAmount > 0) {
//                        PurchaseCouponLedger purchaseCouponLedger = generateCoupon(purchaseReturnedOrder, project, (byte) 1, quarterlyCouponGenerateRate, returnQuarterlyCouponAmount);
//                        purchaseCouponLedgerDao.insert(purchaseCouponLedger);
//                    }
//                    if (returnAnnualCouponAmount > 0) {
//                        PurchaseCouponLedger purchaseCouponLedger = generateCoupon(purchaseReturnedOrder, project, (byte) 1, annualCouponGenerateRate, returnAnnualCouponAmount);
//                        purchaseCouponLedgerDao.insert(purchaseCouponLedger);
//                    }
                }
            }
            logger.info("#traceId={}# [OUT] transferClosedNotification success: ", rpcHeader.traceId);
            return 1;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }


    }

//    /**
//     * 组装生成返利模型
//     *
//     * @param purchaseReturnedOrder   采购退货单信息
//     * @param project                 项目信息
//     * @param couponType              生成返利类型
//     * @param couponGenerateRate      生成返利比例
//     * @param purchaseShouldPayAmount 采购应退金额(生成返利的基数)
//     */
//    private PurchaseCouponLedger generateCoupon(PurchaseReturnedOrder purchaseReturnedOrder,
//                                                Project project,
//                                                byte couponType,
//                                                double couponGenerateRate,
//                                                double purchaseShouldPayAmount) {
//
//        PurchaseCouponLedger purchaseCouponLedger = new PurchaseCouponLedger();
//        purchaseCouponLedger.setCouponRatio((int) couponGenerateRate * 10000);
//        purchaseCouponLedger.setCouponStatus((byte) 0);
//        purchaseCouponLedger.setCouponType(couponType);
//        purchaseCouponLedger.setProjectId((long) project.getProjectId());
//        purchaseCouponLedger.setProjectName(project.getProjectName());
//        purchaseCouponLedger.setCurrencyCode("CNY");
//        purchaseCouponLedger.setPurchaseOrderNo(purchaseReturnedOrder.getPurchaseReturnedOrderNo());
//        purchaseCouponLedger.setPurchaseTime(purchaseReturnedOrder.getCreateTime());
//        //应退返利金额
//        purchaseCouponLedger.setEstimatedCouponAmount(-(long) (purchaseShouldPayAmount * couponGenerateRate * 1000));
//        logger.info("out generateCoupon service");
//
//        return purchaseCouponLedger;
//    }
}
