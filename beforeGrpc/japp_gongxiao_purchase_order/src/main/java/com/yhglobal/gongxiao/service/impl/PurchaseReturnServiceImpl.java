//package com.yhglobal.gongxiao.service.impl;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import com.yhglobal.gongxiao.common.RpcHeader;
//import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
//import com.yhglobal.gongxiao.dao.PurchaseReturnedOrderDao;
//import com.yhglobal.gongxiao.dao.PurchaseReturnedOrderItemDao;
//
//import com.yhglobal.gongxiao.dao.PurchaseReverseDeliveryDao;
//import com.yhglobal.gongxiao.foundation.product.price.model.ProductPrice;
//import com.yhglobal.gongxiao.foundation.product.price.service.ProductPriceService;
//import com.yhglobal.gongxiao.id.BizNumberType;
//import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
//import com.yhglobal.gongxiao.purchase.bo.*;
//import com.yhglobal.gongxiao.model.PurchaseOrderItem;
//import com.yhglobal.gongxiao.model.PurchaseReturnedOrder;
//import com.yhglobal.gongxiao.model.PurchaseReturnedOrderItem;
//import com.yhglobal.gongxiao.model.PurchaseReverseDelivery;
//import com.yhglobal.gongxiao.purchase.temp.OperateHistoryRecord;
//import com.yhglobal.gongxiao.purchase.temp.ProductInfoTemp;
//import com.yhglobal.gongxiao.purchase.temp.PurchaseOutboundItemSummary;
//import com.yhglobal.gongxiao.purchase.service.PurchaseReturnService;
//import com.yhglobal.gongxiao.purchase.service.PurchaseService;
//import com.yhglobal.gongxiao.constant.PurchaseConstant;
//import com.yhglobal.gongxiao.warehouse.service.InboundService;
//import com.yhglobal.gongxiao.warehouse.service.InboundShowService;
//import com.yhglobal.gongxiao.warehouse.service.OutboundService;
//import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
//import com.yhglobal.gongxiao.warehouse.type.WmsSourceChannel;
//import com.yhglobal.gongxiao.warehousemanagement.dto.PlanOutboundOrderItem;
//import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
//import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrderItem;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * 采购退货
// *
// * @author: 陈浩
// * @create: 2018-03-16 11:48
// **/
//@Service
//public class PurchaseReturnServiceImpl implements PurchaseReturnService {
//    private static Logger logger = LoggerFactory.getLogger(PurchaseReturnServiceImpl.class);
//
//    //仓管
//    @Reference
//    InboundShowService inboundShowService;
//    @Reference
//    InboundService inboundService;
//    @Reference
//    OutboundService outboundService;
//
//    //基础数据
//    @Reference
//    ProductPriceService productPriceService;
//
//    //采购
//    @Autowired
//    PurchaseService purchaseService; //采购服务类
//
//    @Autowired
//    PurchaseReturnedOrderDao purchaseReturnedOrderDao; //采购退货单
//    @Autowired
//    PurchaseReturnedOrderItemDao purchaseReturnedOrderItemDao; //采购退货明细
//    @Autowired
//    PurchaseOrderItemDao purchaseOrderItemDao; //采购明细
//    @Autowired
//    PurchaseReverseDeliveryDao purchaseReverseDeliveryDao; //采购退货流水
//
//    @Override
//    public List<PurchaseReturnInboundInfo> getInboundOrderList(RpcHeader rpcHeader,
//                                                               String projectId,
//                                                               String purchaseNumber) {
//        logger.info("#traceId={}# [IN]  [getInboundOrderList]  projectId={}, purchaseNumber={}", rpcHeader.traceId,projectId,purchaseNumber);
//        try {
//            List<InboundOrder> inboundOrders = inboundShowService.selectInboundRecordByPurchaseNo(rpcHeader,
//                    projectId,
//                    purchaseNumber);
//            List<PurchaseReturnInboundInfo> returnInboundInfoList = new ArrayList<>();
//            for (InboundOrder inboundOrder : inboundOrders) {
//                PurchaseReturnInboundInfo purchaseReturnInboundInfo = new PurchaseReturnInboundInfo();
//                purchaseReturnInboundInfo.setBatchNo(inboundOrder.getBatchNo());
//                purchaseReturnInboundInfo.setInboundDate(inboundOrder.getCreateTime());
//                purchaseReturnInboundInfo.setInboundOrderNumber(inboundOrder.getGongxiaoInboundOrderNo());
//                purchaseReturnInboundInfo.setWarehouseName(inboundOrder.getWarehouseName());
//                returnInboundInfoList.add(purchaseReturnInboundInfo);
//            }
//            logger.info("#traceId={}# [OUT] getInboundOrderList success: inboundOrders.size ={}",inboundOrders.size());
//            return returnInboundInfoList;
//        }catch (Exception e){
//            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//            throw e;
//        }
//
//    }
//
//    @Override
//    public List<ProductReturnInfo> getInboundItemList(RpcHeader rpcHeader,
//                                                      String projectId,
//                                                      String gongxiaoInboundOrderNo) {
//        logger.info("#traceId={}# [IN][getCurrencyByCode] params: projectId={} gongxiaoInboundOrderNo={}",
//                rpcHeader.traceId,projectId,gongxiaoInboundOrderNo);
//        try {
//            List<ProductReturnInfo> productReturnInfoList = new ArrayList<>();
//            //获取预约入库的列表
//            List<InboundOrderItem> inboundOrderItems = inboundShowService.selectInStorageDetailInfoByinventoryNum(rpcHeader,
//                    projectId,
//                    gongxiaoInboundOrderNo);
//            if (inboundOrderItems == null || inboundOrderItems.size() == 0) {
//                return null;
//            }
//            InboundOrderItem inboundOrderItem1 = inboundOrderItems.get(0);
//            String purchaseOrderNo = inboundOrderItem1.getPurchaseOrderNo();
//            //获取采购单详情
//            PurchaseOrderDetailVo purchaseDetail = purchaseService.getPurchaseDetail(rpcHeader,purchaseOrderNo);
//            //获取采购单货品列表
//            List<PurchaseOrderItem> itemList = purchaseService.getItemList(rpcHeader,purchaseOrderNo);
//            String brandId = purchaseDetail.getBrandId() + "";
//            String brandName = purchaseDetail.getBrandName();
//            for (InboundOrderItem inboundOrderItem : inboundOrderItems) {
//                ProductReturnInfo productReturnInfo = new ProductReturnInfo();
//                productReturnInfo.setBrandId(brandId);
//                productReturnInfo.setBrandName(brandName);
//                productReturnInfo.setCurrencyCode("CNY");
//                productReturnInfo.setCurrencyName("RMB");
//
//                String productId = inboundOrderItem.getProductId();
//                ProductPrice productPrice = productPriceService.getByProductId(rpcHeader,Long.parseLong(productId));
//                productReturnInfo.setProductId(productPrice.getProductId() + "");
//                productReturnInfo.setGuidePrice(productPrice.getGuidePrice());
//                productReturnInfo.setProductCode(productPrice.getProductCode());
//                productReturnInfo.setProductName(productPrice.getProductName());
//                for (PurchaseOrderItem purchaseOrderItem : itemList) {
//                    String productCode = purchaseOrderItem.getProductCode();
//                    if (productCode.equals(productPrice.getProductCode())) {
//                        productReturnInfo.setPurchasePrice(purchaseOrderItem.getPurchasePrice() / 1000);
//                    }
//                }
//                productReturnInfo.setReturnReferNumber(inboundOrderItem.getInStockQuantity());
//                productReturnInfoList.add(productReturnInfo);
//            }
//            logger.info("#traceId={}# [OUT] getInboundItemList success: productReturnInfoList.size={} : ", rpcHeader.traceId,productReturnInfoList.size());
//            return productReturnInfoList;
//        }catch (Exception e){
//            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//            throw e;
//        }
//
//    }
//
//    @Override
//    public int putPurchaseReturnDetail(RpcHeader rpcHeader,PurchaseReturnOrderDetail purchaseReturnOrderDetail) throws Exception{
//        logger.info("#traceId={}# [IN][getCurrencyByCode] params: purchaseReturnOrderDetail={}",
//                rpcHeader.traceId,purchaseReturnOrderDetail.toString());
//        try {
////1)获取基础信息
//            String projectId = purchaseReturnOrderDetail.getProjectId();
//            String warehouseId = purchaseReturnOrderDetail.getWarehouseId();
//            String warehouseName = purchaseReturnOrderDetail.getWarehouseName();
//            String surpplierCode = "123456";
//            long createdById = (long) 11;
//            String createdByName = "风水大师";
//            int shippingMode = purchaseReturnOrderDetail.getShippingMode();
//            int totalQuantity = 0;
//            double totalReturnAmount = 0;
//            long returnCouponAmountTotal = 0;
//            long returnPrepaidAmountTotal = 0;
//            //获取采购单货品信息
//            List<PurchaseOrderItem> purchaseOrderItemList = purchaseService.getItemList(rpcHeader,purchaseReturnOrderDetail.getOriginalPurchaseOrderNo());
//
//
//            String oderNumber = DateTimeIdGenerator.nextId(BizNumberType.PURCHASE_ORDER_NO);
//
//            //2)为调用仓管出库接口拼装数据
//            List<ProductReturnInfo> productReturnInfoList = purchaseReturnOrderDetail.getProductReturnInfoList();
//            List<PlanOutboundOrderItem> itemList = new ArrayList<>();
//            for (ProductReturnInfo productReturnInfo : productReturnInfoList) {
//
//
//                PlanOutboundOrderItem planInboundOrderItem = new PlanOutboundOrderItem();
//
//                planInboundOrderItem.setSalesOrderNo(oderNumber);
//                planInboundOrderItem.setWarehouseId(warehouseId);
//                planInboundOrderItem.setWarehouseName(warehouseName);
//                planInboundOrderItem.setProductCode(productReturnInfo.getProductCode());
//                planInboundOrderItem.setProductName(productReturnInfo.getProductName());
//                planInboundOrderItem.setTotalQuantity(productReturnInfo.getReturnNumber());
//                itemList.add(planInboundOrderItem);
//                totalReturnAmount += productReturnInfo.getReturnAmount();
//
//                totalQuantity += productReturnInfo.getReturnNumber();
//            }
//
//            //3)调用仓管接口得到相关订单号
//            String purchaseUniqueNo = DateTimeIdGenerator.nextId(BizNumberType.PURCHASE_UNIQUE_NO);
//            String gongxiaoInboundOrderNo = outboundService.createOutboundOrder(rpcHeader,
//                    WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId(),
//                    WmsOrderType.OUTBOUND_FOR_RETURN_PRODUCT.getInboundOrderCode(),
//                    projectId,
//                    surpplierCode,
//                    oderNumber,
//                    "123456",
//                    "123123",
//                    "address",
//                    warehouseId,
//                    warehouseName,
//                    shippingMode,
//                    "飞鸟快递",
//                    "123",
//                    "123",
//                    totalQuantity,
//                    itemList,
//                    "分销采购退货",
//                    purchaseUniqueNo
//            );
//
//            //4)设置采购退货单信息
//            PurchaseReturnedOrder purchaseReturnedOrder = new PurchaseReturnedOrder();
//
//            purchaseReturnedOrder.setOutboundOrderInfo(gongxiaoInboundOrderNo); //一次采购退货,一次预约出库,所以不需要json
//            purchaseReturnedOrder.setOutboundOrderNumber(gongxiaoInboundOrderNo);
//            purchaseReturnedOrder.setTotalReturnedQuantity(totalQuantity);
//            purchaseReturnedOrder.setReturnedCashAmount((long) (totalReturnAmount * 1000));
//
//            purchaseReturnedOrder.setPurchaseReturnedOrderNo(oderNumber);
//            purchaseReturnedOrder.setReturnedOrderStatus((byte) 0);
//            purchaseReturnedOrder.setReturnedType((byte) 1);
//            purchaseReturnedOrder.setCreateTime(new Date());
//
//            purchaseReturnedOrder.setRecipientName(purchaseReturnOrderDetail.getRecipientName());
//            purchaseReturnedOrder.setRecipientCompanyName(purchaseReturnOrderDetail.getRecipientCompanyName());
//            purchaseReturnedOrder.setRecipientAddress(purchaseReturnOrderDetail.getRecipientAddress());
//            purchaseReturnedOrder.setRecipientDetailAddress(purchaseReturnOrderDetail.getRecipientDetailAddress());
//            purchaseReturnedOrder.setRecipientMobile(purchaseReturnOrderDetail.getRecipientMobile());
//
//            purchaseReturnedOrder.setShippingMode((byte) purchaseReturnOrderDetail.getShippingMode());
//            purchaseReturnedOrder.setLogisticsOrderNo(purchaseReturnOrderDetail.getLogisticsOrderNo());
//            purchaseReturnedOrder.setLogisticsCompany(purchaseReturnOrderDetail.getLogisticsCompany());
//            purchaseReturnedOrder.setWarehouseId(purchaseReturnOrderDetail.getWarehouseId());
//            purchaseReturnedOrder.setWarehouseName(purchaseReturnOrderDetail.getWarehouseName());
//            purchaseReturnedOrder.setFreight((long) purchaseReturnOrderDetail.getFreight() * 1000);
//            purchaseReturnedOrder.setFreightPaidBy((byte) purchaseReturnOrderDetail.getFreightPaidBy());
//            purchaseReturnedOrder.setProjectId(Long.parseLong(purchaseReturnOrderDetail.getProjectId()));
//            purchaseReturnedOrder.setProjectName(purchaseReturnOrderDetail.getProjectName());
//            purchaseReturnedOrder.setReturnedType((byte) purchaseReturnOrderDetail.getReturnType());
//            purchaseReturnedOrder.setCreatedById(createdById);
//            purchaseReturnedOrder.setCreatedByName(createdByName);
//            purchaseReturnedOrder.setCreateTime(new Date());
//            purchaseReturnedOrder.setLastUpdateTime(new Date());
//            purchaseReturnedOrder.setOriginalPurchaseOrderNo(purchaseReturnOrderDetail.getOriginalPurchaseOrderNo());
//            purchaseReturnedOrder.setOriginalGongxiaoInboundOrderNo(purchaseReturnOrderDetail.getOriginalGongxiaoInboundOrderNo());
//
//            List<OperateHistoryRecord> operateHistoryRecords = new ArrayList<>();
//            OperateHistoryRecord operateHistoryRecord = new OperateHistoryRecord();
//            operateHistoryRecord.setOperateStatus(0);
//            operateHistoryRecord.setOperateName(createdByName);
//            operateHistoryRecord.setOperateId(createdById + "");
//            operateHistoryRecord.setOperateTime(new Date());
//            operateHistoryRecord.setOperateFunction("新建");
//            operateHistoryRecords.add(operateHistoryRecord);
//            String tracelog = JSON.toJSONString(operateHistoryRecords);
//            purchaseReturnedOrder.setTracelog(tracelog);
//
//            List<ProductInfoTemp> productInfoList = new ArrayList<>();
//            //5)设置采购退货单货品信息
//            for (ProductReturnInfo productReturnInfo : productReturnInfoList) {
//                PurchaseReturnedOrderItem item = new PurchaseReturnedOrderItem();
//                int returnNumber = productReturnInfo.getReturnNumber();
//                //跟仓管的回执进行适配
//                String productCode1 = productReturnInfo.getProductCode();
//                //跟采购货品进行适配
//                for (PurchaseOrderItem planInboundOrderItem : purchaseOrderItemList) {
//                    if (productCode1.equals(planInboundOrderItem.getProductCode())) {
//                        Long couponAmount = planInboundOrderItem.getCouponAmount();
//                        Long prepaidAmount = planInboundOrderItem.getPrepaidAmount();
//                        Integer purchaseNumber = planInboundOrderItem.getPurchaseNumber();
//                        long couponUnit = couponAmount / purchaseNumber;
//                        long prepaidUnit = prepaidAmount / purchaseNumber;
//                        long returnCouponAmount = couponUnit * returnNumber;
//                        long returnPrepaidAmount = prepaidUnit * returnNumber;
//                        long returnAmount = returnCouponAmount + returnPrepaidAmount;
//                        item.setReturnAmount(returnAmount);
//                        returnCouponAmountTotal += returnCouponAmount;
//                        returnPrepaidAmountTotal += returnPrepaidAmountTotal;
//                    }
//                }
//                //:todo 掉返利新增的接口 代垫新增的接口
//                ProductInfoTemp productInfoTemp = new ProductInfoTemp();
//                productInfoTemp.setProductCode(productReturnInfo.getProductCode());
//                productInfoTemp.setProductName(productReturnInfo.getProductName());
//                productInfoTemp.setReturnNumber(productReturnInfo.getReturnNumber());
//                productInfoList.add(productInfoTemp);
//
//                item.setItemStatus((byte) 0);
//                item.setPurchaseReturnedOrderNo(oderNumber);
//                item.setSyncToWmsFlag((byte) 1);
//                item.setProductId(productReturnInfo.getProductId());
//                item.setProductCode(productReturnInfo.getProductCode());
//                item.setProductName(productReturnInfo.getProductName());
//                purchaseReturnedOrder.setBrandId(Integer.parseInt(productReturnInfo.getBrandId()));
//                purchaseReturnedOrder.setBrandName(productReturnInfo.getBrandName());
//                purchaseReturnedOrder.setCurrencyCode(PurchaseConstant.CURRENCY_CODE);
//                purchaseReturnedOrder.setCurrencyName(PurchaseConstant.CURRENCY_NAME);
//                item.setGuidePrice((long) (productReturnInfo.getGuidePrice() * 1000));
//                item.setPurchasePrice((long) (productReturnInfo.getPurchasePrice() * 1000));
//                item.setReturnAmount((long) (productReturnInfo.getReturnAmount() * 1000));
//                item.setTotalReturnedQuantity(productReturnInfo.getReturnNumber());
//                item.setReturnReason(productReturnInfo.getReturnReason());
//                item.setReturnReferNumber(productReturnInfo.getReturnReferNumber());
//                item.setWarehouseId(warehouseId);
//                item.setWarehouseName(warehouseName);
//                item.setCurrencyCode("CNY");
//                item.setCurrencyName("RMB");
//                item.setCreateTime(new Date());
//                item.setLastUpdateTime(new Date());
//                purchaseReturnedOrderItemDao.insert(item);
//            }
//            purchaseReturnedOrder.setReturnedCouponAmount(returnCouponAmountTotal);
//            purchaseReturnedOrder.setReturnedPrepaidAmount(returnPrepaidAmountTotal);
//            String productJson = JSON.toJSONString(productInfoList);
//            purchaseReturnedOrder.setReturnItemInfo(productJson);
//
//            purchaseReturnedOrder.setCreateTime(new Date());
//            purchaseReturnedOrder.setLastUpdateTime(new Date());
//            purchaseReturnedOrderDao.insert(purchaseReturnedOrder);
//
//            //6)设置采购逆向退货流水
//            PurchaseReverseDelivery purchaseReverseDelivery = new PurchaseReverseDelivery();
//            purchaseReverseDelivery.setReverseStatus((byte)0);
//            purchaseReverseDelivery.setReverseType((byte)1);
//            purchaseReverseDelivery.setPurchaseReturnedOrderNo(oderNumber);
//            purchaseReverseDelivery.setSyncToGongxiaoWarehouseFlag((byte)1);
//            purchaseReverseDelivery.setGongxiaoWarehouseOutboundOrderNo(gongxiaoInboundOrderNo);
//            purchaseReverseDelivery.setOriginalPurchaseOrderNo(purchaseReturnOrderDetail.getOriginalPurchaseOrderNo());
//            purchaseReverseDelivery.setWarehouseId(warehouseId);
//            purchaseReverseDelivery.setWarehouseName(warehouseName);
//            purchaseReverseDelivery.setShippingMode((byte)shippingMode);
//            purchaseReverseDelivery.setLogisticsOrderNo(purchaseReturnOrderDetail.getLogisticsOrderNo());
//            purchaseReverseDelivery.setLogisticsCompany(purchaseReturnOrderDetail.getLogisticsCompany());
//
//            List<PurchaseOutboundItemSummary> purchaseOutboundItemSummaryList = new ArrayList<>();
//            for (ProductReturnInfo productReturnInfo:productReturnInfoList){
//                PurchaseOutboundItemSummary purchaseOutboundItemSummary = new PurchaseOutboundItemSummary();
//                purchaseOutboundItemSummary.setProductCode(productReturnInfo.getProductId());
//                purchaseOutboundItemSummary.setCurrencyCode(productReturnInfo.getCurrencyCode());
//                purchaseOutboundItemSummary.setPurchaseOutboundQuantity(productReturnInfo.getReturnNumber());
//                purchaseOutboundItemSummary.setActualOutboundQuantity(0);
//                purchaseOutboundItemSummary.setSignedReceiptQuantity(0);
//                purchaseOutboundItemSummary.setOrderStatus((byte) 0);
//                purchaseOutboundItemSummaryList.add(purchaseOutboundItemSummary);
//            }
//            String purchaseOrderJson = JSON.toJSONString(purchaseOrderItemList);
//            purchaseReverseDelivery.setProductInfo(purchaseOrderJson);
//            purchaseReverseDelivery.setTotalQuantity(totalQuantity);
//            purchaseReverseDelivery.setCreateTime(new Date());
//            purchaseReverseDelivery.setLastUpdateTime(new Date());
//            int insert = purchaseReverseDeliveryDao.insert(purchaseReverseDelivery);
//            logger.info("#traceId={}# [OUT] putPurchaseDetail success: ", rpcHeader.traceId);
//            return 1;
//        }catch (Exception e){
//            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//            throw e;
//        }
//    }
//
//    @Override
//    public List<PurchaseReturnOrderInfo> getPurchaseReturnList(RpcHeader rpcHeader,
//                                                               String projectId,
//                                                               String warehouseId,
//                                                               int returnType,
//                                                               String orderNumber,
//                                                               String startDate,
//                                                               String endDate) {
//        logger.info("#traceId={}# [IN][getCurrencyByCode] params: projectId={}, warehouseId={}, returnType={},  orderNumber={}, startDate={}, endDate={}",
//                rpcHeader.traceId,projectId,warehouseId,returnType,orderNumber,startDate,endDate);
//        try{
//            List<PurchaseReturnOrderInfo> purchaseReturnOrderInfoList = new ArrayList<>();
//            List<PurchaseReturnedOrder> purchaseReturnList = purchaseReturnedOrderDao.selectPurchaseReturnList(projectId, warehouseId, orderNumber, startDate, endDate);
//            for (PurchaseReturnedOrder purchaseReturnedOrder : purchaseReturnList) {
//                PurchaseReturnOrderInfo purchaseReturnOrderInfo = new PurchaseReturnOrderInfo();
//
//                String purchaseReturnedOrderNo = purchaseReturnedOrder.getPurchaseReturnedOrderNo();
//                String originalPurchaseOrderNo = purchaseReturnedOrder.getOriginalPurchaseOrderNo();
//                String warehouseName = purchaseReturnedOrder.getWarehouseName();
//                String recipientAddress = purchaseReturnedOrder.getRecipientAddress();
////            purchaseReturnedOrder.getRecipientName()
//                purchaseReturnOrderInfo.setOrderNumber(purchaseReturnedOrderNo + "," + originalPurchaseOrderNo);
//
//                purchaseReturnOrderInfo.setOrderStatus(purchaseReturnedOrder.getReturnedOrderStatus());
//                //:todo 客户名称暂时没有跟
//                purchaseReturnOrderInfo.setCustomerInfo("黄佳林,13423755806");
//                purchaseReturnOrderInfo.setReturnOrderType(purchaseReturnedOrder.getReturnedType());
//                purchaseReturnOrderInfo.setLogisticInfo(warehouseName + "," + recipientAddress);
//                purchaseReturnOrderInfo.setUpdateTime(purchaseReturnedOrder.getLastUpdateTime());
//                purchaseReturnOrderInfo.setCreateTime(purchaseReturnedOrder.getCreateTime());
//                purchaseReturnOrderInfoList.add(purchaseReturnOrderInfo);
//            }
//            logger.info("#traceId={}# [OUT] getPurchaseReturnList success: purchaseReturnOrderInfoList.size={} : ", rpcHeader.traceId,purchaseReturnOrderInfoList.size());
//            return purchaseReturnOrderInfoList;
//        }catch (Exception e){
//            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//            throw e;
//        }
//    }
//
//    @Override
//    public PurchaseReturnOrderDetail getPurchaseDetail(RpcHeader rpcHeader,String purchaseReturnedOrderNo) {
//        logger.info("#traceId={}# [IN][getPurchaseDetail] params: purchaseReturnedOrderNo={}", rpcHeader.traceId,purchaseReturnedOrderNo);
//        try {
//            PurchaseReturnOrderDetail purchaseReturnOrderDetail = new PurchaseReturnOrderDetail();
//            List<ProductReturnInfo> productReturnInfoList = new ArrayList<>();
//
//            PurchaseReturnedOrder purchaseReturnedOrder = purchaseReturnedOrderDao.getByReturnedOrderNo(purchaseReturnedOrderNo);
//            List<PurchaseReturnedOrderItem> purchaseReturnedOrderItemList = purchaseReturnedOrderItemDao.selectByReturnedOrderNo(purchaseReturnedOrderNo);
//
//            String tracelog = purchaseReturnedOrder.getTracelog();
//            List<OperateHistoryRecord> operateHistoryRecordList = JSON.parseObject(tracelog, new TypeReference<List<OperateHistoryRecord>>() {
//            });
//
//            purchaseReturnOrderDetail.setOriginalPurchaseOrderNo(purchaseReturnedOrder.getOriginalPurchaseOrderNo());
//            purchaseReturnOrderDetail.setOriginalGongxiaoInboundOrderNo(purchaseReturnedOrder.getOriginalGongxiaoInboundOrderNo());
//            purchaseReturnOrderDetail.setPurchaseReturnedOrderNo(purchaseReturnedOrder.getPurchaseReturnedOrderNo());
//            purchaseReturnOrderDetail.setReturnedOrderStatus(purchaseReturnedOrder.getReturnedOrderStatus());
//            purchaseReturnOrderDetail.setCreateTime(purchaseReturnedOrder.getCreateTime());
//            purchaseReturnOrderDetail.setRecipientName(purchaseReturnedOrder.getRecipientName());
//            purchaseReturnOrderDetail.setRecipientCompanyName(purchaseReturnedOrder.getRecipientCompanyName());
//            purchaseReturnOrderDetail.setRecipientAddress(purchaseReturnedOrder.getRecipientAddress());
//            purchaseReturnOrderDetail.setRecipientDetailAddress(purchaseReturnedOrder.getRecipientDetailAddress());
//            purchaseReturnOrderDetail.setRecipientMobile(purchaseReturnedOrder.getRecipientMobile());
//            purchaseReturnOrderDetail.setShippingMode(purchaseReturnedOrder.getShippingMode());
//            purchaseReturnOrderDetail.setLogisticsOrderNo(purchaseReturnedOrder.getLogisticsOrderNo());
//            purchaseReturnOrderDetail.setLogisticsCompany(purchaseReturnedOrder.getLogisticsCompany());
//            purchaseReturnOrderDetail.setWarehouseId(purchaseReturnedOrder.getWarehouseId());
//            purchaseReturnOrderDetail.setWarehouseName(purchaseReturnedOrder.getWarehouseName());
//            purchaseReturnOrderDetail.setFreight(purchaseReturnedOrder.getFreight());
//            purchaseReturnOrderDetail.setFreightPaidBy(purchaseReturnedOrder.getFreightPaidBy());
//            purchaseReturnOrderDetail.setOutboundOrderNumber(purchaseReturnedOrder.getOutboundOrderNumber());
//            purchaseReturnOrderDetail.setProjectId(purchaseReturnedOrder.getProjectId() + "");
//            purchaseReturnOrderDetail.setProjectName(purchaseReturnedOrder.getProjectName());
//            purchaseReturnOrderDetail.setReturnType(purchaseReturnedOrder.getReturnedType());
//            purchaseReturnOrderDetail.setCreatedById(Integer.parseInt(purchaseReturnedOrder.getCreatedById()+""));
//            purchaseReturnOrderDetail.setCreatedByName(purchaseReturnedOrder.getCreatedByName());
//            purchaseReturnOrderDetail.setCreateTime(purchaseReturnedOrder.getCreateTime());
//
//            for (PurchaseReturnedOrderItem purchaseReturnedOrderItem : purchaseReturnedOrderItemList) {
//                ProductReturnInfo productReturnInfo = new ProductReturnInfo();
//                productReturnInfo.setProductId(purchaseReturnedOrderItem.getProductId());
//                productReturnInfo.setProductCode(purchaseReturnedOrderItem.getProductCode());
//                productReturnInfo.setProductName(purchaseReturnedOrderItem.getProductName());
//                productReturnInfo.setCurrencyCode(purchaseReturnedOrder.getCurrencyCode());
//                productReturnInfo.setCurrencyName(purchaseReturnedOrder.getCurrencyName());
//                productReturnInfo.setBrandId(purchaseReturnedOrder.getBrandId() + "");
//                productReturnInfo.setBrandName(purchaseReturnedOrder.getBrandName());
//                productReturnInfo.setGuidePrice(purchaseReturnedOrderItem.getGuidePrice() / 1000.0);
//                productReturnInfo.setPurchasePrice(purchaseReturnedOrderItem.getPurchasePrice() / 1000.0);
//                productReturnInfo.setReturnAmount(purchaseReturnedOrderItem.getReturnAmount() / 1000.0);
//                productReturnInfo.setReturnNumber(purchaseReturnedOrderItem.getTotalReturnedQuantity());
//                productReturnInfo.setReturnReason(purchaseReturnedOrderItem.getReturnReason());
//                productReturnInfo.setReturnReferNumber(purchaseReturnedOrderItem.getReturnReferNumber());
//                int imperfectQuantity = purchaseReturnedOrderItem.getTotalImperfectQuantity();
//                int goodQuantity = purchaseReturnedOrderItem.getTotalReturnedQuantity() - purchaseReturnedOrderItem.getTotalImperfectQuantity();
//                productReturnInfo.setWarehouseActualReceiptQuantity(goodQuantity+","+imperfectQuantity);
//                productReturnInfoList.add(productReturnInfo);
//            }
//            purchaseReturnOrderDetail.setProductReturnInfoList(productReturnInfoList);
//            purchaseReturnOrderDetail.setOperateHistoryRecordList(operateHistoryRecordList);
//            logger.info("#traceId={}# [OUT] getPurchaseDetail success: ", rpcHeader.traceId);
//            return purchaseReturnOrderDetail;
//        }catch (Exception e){
//            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//            throw e;
//        }
//
//    }
//}
