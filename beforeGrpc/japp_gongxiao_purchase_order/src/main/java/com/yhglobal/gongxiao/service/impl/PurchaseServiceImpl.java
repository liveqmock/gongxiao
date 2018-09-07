package com.yhglobal.gongxiao.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.constant.*;
import com.yhglobal.gongxiao.dao.PurchaseOrderDao;
import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.dao.PurchaseScheduleDeliveryDao;
import com.yhglobal.gongxiao.foundation.currency.dao.CurrencyDao;

import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;

import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.supplier.dao.SupplierDao;
import com.yhglobal.gongxiao.foundation.supplier.model.Supplier;
import com.yhglobal.gongxiao.foundation.supplier.service.SupplierService;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.model.*;

import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.service.SupplierAccountService;
import com.yhglobal.gongxiao.purchase.bo.*;

import com.yhglobal.gongxiao.purchase.dto.PurchaseOrderBackWrite;
import com.yhglobal.gongxiao.purchase.dto.PurchaseOrderItemBackWrite;
import com.yhglobal.gongxiao.purchase.temp.OperateHistoryRecord;
import com.yhglobal.gongxiao.purchase.service.PurchaseService;

import com.yhglobal.gongxiao.status.PurchaseEasStatus;
import com.yhglobal.gongxiao.status.PurchaseStatusEnum;
import com.yhglobal.gongxiao.task.SyncPlanInboundToWmsTask;
import com.yhglobal.gongxiao.task.SyncPurchaseOrderToEasTask;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.warehouse.service.InboundService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * 采购业务实现类
 *
 * @author: 陈浩
 * @create: 2018-02-05 9:32
 **/
@Service(timeout = 9000)
public class PurchaseServiceImpl implements PurchaseService {
    private static Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    //基础数据
    @Reference
    SupplierService supplierService; //供应商

    @Reference
    InboundService inboundService;//仓管

    @Reference
    ProjectService projectService; //项目信息

    @Reference
    WarehouseService warehouseService; //仓库信息

    @Reference
    ProductService productService;  //货品信息

    @Reference
    SupplierAccountService supplierAccountService; //供应商上账庄户

    //dao
    @Autowired
    SupplierDao supplierDao;

    @Autowired
    PurchaseOrderDao purchaseOrderDao; //采购单

    @Autowired
    PurchaseOrderItemDao purchaseOrderItemDao; //采购明细

    @Autowired
    PurchaseScheduleDeliveryDao purchaseScheduleDeliveryDao;//采购方记录推送的预约入库的数据

    @Autowired
    CurrencyDao currencyDao; //货币

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public PurchaseOrderDetailVo getPurchaseDetail(RpcHeader rpcHeader, String purchaseOrderNo) {
        logger.info("#traceId={}# [IN][getCurrencyByCode] params: {purchaseOrderNo}={}  ", rpcHeader.traceId, purchaseOrderNo);
        Map<Integer,String > map = new HashMap();
        map.put(0,"普通采购");
        map.put(1,"货补采购");
        map.put(2,"赠品采购");
        try {
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(purchaseOrderNo);
            List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemDao.selectByOrderNo(purchaseOrderNo);
            Warehouse warehouseInfo = warehouseService.getWarehouseById(rpcHeader, purchaseOrder.getWarehouseId() + "");

            List<PurchaseItemVo> purchaseItems = new ArrayList<>();
            PurchaseOrderDetailVo detail = new PurchaseOrderDetailVo();
            //设置订单信息
            detail.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
            detail.setOrderStatus(purchaseOrder.getOrderStatus());
            detail.setProjectId(purchaseOrder.getProjectId());
            detail.setProjectName(purchaseOrder.getProjectName());
            detail.setBrandId(purchaseOrder.getBrandId());
            detail.setBrandName(purchaseOrder.getBrandName());
            detail.setSupplierId(purchaseOrder.getSupplierId());
            detail.setSupplierName(purchaseOrder.getSupplierName());
            detail.setPaymentMode(purchaseOrder.getPaymentMode());
            detail.setPurchaseType(map.get(purchaseOrder.getPurchaseType()));
            detail.setShippingMode(purchaseOrder.getShippingMode());
            detail.setExpectedInboundDate(purchaseOrder.getExpectedInboundDate());
            detail.setOrderDeadlineDate(purchaseOrder.getOrderDeadlineDate());
            detail.setBusinessDate(purchaseOrder.getBusinessDate());
            detail.setWarehouseId(purchaseOrder.getWarehouseId());
            detail.setWarehouseName(purchaseOrder.getWarehouseName());
            if (warehouseInfo != null) {
                detail.setAddress(warehouseInfo.getStreetAddress());
            }
            detail.setBrandOrderNo(purchaseOrder.getBrandOrderNo());
            detail.setContractReferenceOrderNo(purchaseOrder.getContractReferenceOrderNo());
            detail.setRemark(purchaseOrder.getRemark());
            detail.setPurchaseCategory(purchaseOrderItems.size());
            detail.setTotalQuantity(CommonUtil.intFormatComma(purchaseOrder.getTotalQuantity()));
            //设置各金额
            detail.setCouponAmountUse(DoubleScale.keepTwoBits(purchaseOrder.getCouponAmountUse()));
            detail.setPrepaidAmountUse(DoubleScale.keepTwoBits(purchaseOrder.getPrepaidAmountUse()));
            detail.setPurchaseGuideAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseGuideAmount()));
            detail.setPurchasePrivilegeAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchasePrivilegeAmount()));
            detail.setReturnCash(DoubleScale.keepTwoBits(purchaseOrder.getReturnCash()));
            detail.setPurchaseShouldPayAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseShouldPayAmount()));
            detail.setPurchaseActualPayAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseActualPayAmount()));
            String tracelog = purchaseOrder.getTracelog();
            List<OperateHistoryRecord> traceLogList;
            if (tracelog != null && !"".equals(tracelog)) {
                traceLogList = JSON.parseObject(tracelog, new TypeReference<List<OperateHistoryRecord>>() {
                });
            } else {
                traceLogList = new ArrayList<>();
            }
            for (OperateHistoryRecord operateHistoryRecord : traceLogList) {
                operateHistoryRecord.setOperateTimeString(DateUtil.longDateToFXString(operateHistoryRecord.getOperateTime()));
            }
            detail.setTraceLogList(traceLogList);

            for (PurchaseOrderItem item : purchaseOrderItems) {
                PurchaseItemVo purchaseItem = new PurchaseItemVo();
                purchaseItem.setPurchaseItemId(item.getPurchaseItemId());
                purchaseItem.setOrderStatus(item.getOrderStatus());
                purchaseItem.setPurchaseOrderNo(item.getPurchaseOrderNo());
                purchaseItem.setProductId(item.getProductId());
                purchaseItem.setProductCode(item.getProductCode());
                purchaseItem.setProductName(item.getProductName());
                purchaseItem.setProductUnit(item.getProductUnit());
                purchaseItem.setWarehouseId(item.getWarehouseId());
                purchaseItem.setWarehouseName(item.getWarehouseName());
                purchaseItem.setShippingMode(item.getShippingMode());
                purchaseItem.setDiscountPercent("0.000000");
                purchaseItem.setCouponAmount(DoubleScale.keepTwoBits(item.getCouponAmount()));
                purchaseItem.setPrepaidAmount(DoubleScale.keepTwoBits(item.getPrepaidAmount()));
                purchaseItem.setGuidePrice(DoubleScale.keepSixBits(item.getGuidePrice()));
                purchaseItem.setPurchasePrice(DoubleScale.keepSixBits(item.getPurchasePrice()));
                purchaseItem.setCostPrice(DoubleScale.keepSixBits(item.getCostPrice()));
                if (item.getFactoryPrice() == null) {
                    purchaseItem.setFactoryPrice("0.000000");
                } else {
                    purchaseItem.setFactoryPrice(DoubleScale.keepSixBits(item.getFactoryPrice()));
                }
                purchaseItem.setPurchaseNumber(item.getPurchaseNumber());
                purchaseItems.add(purchaseItem);
            }
            detail.setItemList(purchaseItems);
            logger.info("#traceId={}# [OUT] getPurchaseDetail success", rpcHeader.traceId);
            return detail;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<PurchaseOrderItem> getItemList(RpcHeader rpcHeader, String purchaseOrderNo) {
        logger.info("#traceId={}# [IN][getCurrencyByCode] params: purchaseOrderNo={}  ", rpcHeader.traceId, purchaseOrderNo);
        try {
            List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemDao.selectByOrderNo(purchaseOrderNo);
            if (purchaseOrderItems.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to getItemList: purchaseOrderItems=null ", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] getItemList success: purchaseOrderItems.size={} ", rpcHeader.traceId, purchaseOrderItems.size());
            }
            return purchaseOrderItems;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<PurchaseOrderInfo> getPurchaseOrderList(RpcHeader rpcHeader,
                                                            String projectId,
                                                            String purchaseOrderNo,
                                                            String supplierId,
                                                            String warehouseId,
                                                            String productCode,
                                                            String requireArrivalDate,
                                                            String arrivalDeadline,
                                                            String businessDate,
                                                            int pageNumber,
                                                            int pageSize) {
        logger.info("#traceId={}# [IN][getPurchaseOrderList] params: projectId={}, purchaseOrderNo={}, brandId={}, " +
                        "warehouseId={}, productCode={}, requireArrivalDate={}, arrivalDeadline={}, businessDate={}  ",
                rpcHeader.traceId, projectId, purchaseOrderNo, supplierId, warehouseId, productCode, requireArrivalDate, arrivalDeadline, businessDate);
        PageInfo<PurchaseOrderInfo> pageInfo;

        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.getPurchaseOrderList(projectId,
                    purchaseOrderNo,
                    supplierId,
                    warehouseId,
                    productCode,
                    requireArrivalDate,
                    arrivalDeadline,
                    businessDate);
            PageInfo<PurchaseOrder> purchaseOrderPageInfo = new PageInfo<>(purchaseOrderList);


            List<PurchaseOrderInfo> purchaseOrderInfoList = new ArrayList<>();
            for (PurchaseOrder purchaseOrder : purchaseOrderList) {
                PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();
                purchaseOrderInfo.setPurchaseOrderNumber(purchaseOrder.getPurchaseOrderNo());
                purchaseOrderInfo.setBrandId(purchaseOrder.getBrandId() + "");
                purchaseOrderInfo.setBrandName(purchaseOrder.getBrandName());
                purchaseOrderInfo.setSupplierId(purchaseOrder.getSupplierId());
                purchaseOrderInfo.setSupplierName(purchaseOrder.getSupplierName());
                purchaseOrderInfo.setOrderAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseActualPayAmount()));
                purchaseOrderInfo.setPurchaseCategory(purchaseOrder.getPurchaseCategory());
                purchaseOrderInfo.setPurchaseNumber(purchaseOrder.getTotalQuantity());
                if (purchaseOrder.getPaymentMode() == null) {
                    purchaseOrderInfo.setPaymentMode(0);
                } else {
                    purchaseOrderInfo.setPaymentMode(purchaseOrder.getPaymentMode());
                }
                purchaseOrderInfo.setWarehouseName(purchaseOrder.getWarehouseName());
                purchaseOrderInfo.setWarehouseId(purchaseOrder.getWarehouseId());
                purchaseOrderInfo.setRequirArrivalDate(purchaseOrder.getOrderDeadlineDate());
                purchaseOrderInfo.setBusinessDate(DateUtil.dateToString(purchaseOrder.getCreateTime(), "yyyy年MM月dd日 HH:mm"));
                purchaseOrderInfo.setCreatePerson(purchaseOrder.getCreatedByName());
                purchaseOrderInfo.setSupplierOrderNo(purchaseOrder.getBrandOrderNo());
                purchaseOrderInfo.setContractReferenceOrderNo(purchaseOrder.getContractReferenceOrderNo());
                int orderStatus = purchaseOrder.getOrderStatus();
                if (orderStatus == PurchaseStatusEnum.DRAFT.getCode()) {
                    purchaseOrderInfo.setPurchaseStatus("草稿");
                } else if (orderStatus == PurchaseStatusEnum.COMMIT.getCode()) {
                    purchaseOrderInfo.setPurchaseStatus("待发货");
                }  else if (orderStatus == PurchaseStatusEnum.ALREADY_PAY.getCode()) {
                    purchaseOrderInfo.setPurchaseStatus("待发货");
                }  else if (orderStatus == PurchaseStatusEnum.ALREADY_SHIPPING.getCode()) {
                    purchaseOrderInfo.setPurchaseStatus("待收货");
                } else if (orderStatus == PurchaseStatusEnum.PART_INBOUND.getCode()) {
                    purchaseOrderInfo.setPurchaseStatus("部分收货");

                } else if (orderStatus >= PurchaseStatusEnum.NORMAL_COMPLETE.getCode()
                        &&orderStatus<PurchaseStatusEnum.CANCEL.getCode()) {
                    purchaseOrderInfo.setPurchaseStatus("交易成功");

                } else if (orderStatus == PurchaseStatusEnum.CANCEL.getCode()) {
                    purchaseOrderInfo.setPurchaseStatus("已取消");
                }
                purchaseOrderInfo.setPurchaseStatusInt(orderStatus);

                // 显示编辑
                if (orderStatus==PurchaseStatusEnum.DRAFT.getCode()){
                    purchaseOrderInfo.setEnableEdit(true);
                }
                //显示作废
                if (orderStatus==PurchaseStatusEnum.ALREADY_PAY.getCode()
                        ||orderStatus==PurchaseStatusEnum.DRAFT.getCode()
                        ||orderStatus==PurchaseStatusEnum.COMMIT.getCode()
                        ){
                    purchaseOrderInfo.setEnableCancel(true);
                }
                //显示预约入库
                if (orderStatus==PurchaseStatusEnum.ALREADY_PAY.getCode()
                        ||orderStatus==PurchaseStatusEnum.ALREADY_SHIPPING.getCode()
                        ||orderStatus==PurchaseStatusEnum.PART_INBOUND.getCode()){
                    int purchaseNumber = purchaseOrder.getTotalQuantity();
                    int inTransitQuantity = purchaseOrder.getInTransitQuantity();
                    int inStockQuantity = purchaseOrder.getInStockQuantity();
                    if ((inTransitQuantity+inStockQuantity)<purchaseNumber){
                        purchaseOrderInfo.setEnablePlanInStock(true);
                    }
                }
                //显示入库详情
                if (orderStatus==PurchaseStatusEnum.ALREADY_SHIPPING.getCode()
                        ||orderStatus==PurchaseStatusEnum.PART_INBOUND.getCode()
                        ||orderStatus==PurchaseStatusEnum.NORMAL_COMPLETE.getCode()
                        ||orderStatus==PurchaseStatusEnum.EXCEPTION_COMPLETE_HANDLED.getCode()
                        ||orderStatus==PurchaseStatusEnum.EXCEPTION_COMPLETE_NOT_HANDLE.getCode()
                        ||orderStatus==PurchaseStatusEnum.GENERATE_COUPON.getCode()){
                    purchaseOrderInfo.setEnableDetail(true);
                }

                purchaseOrderInfoList.add(purchaseOrderInfo);
            }
            logger.info("#traceId={}# [OUT] getPurchaseOrderList success: purchaseOrderInfoList.size={} ", rpcHeader.traceId, purchaseOrderInfoList.size());
            pageInfo = new PageInfo<PurchaseOrderInfo>(purchaseOrderInfoList);
            pageInfo.setTotal(purchaseOrderPageInfo.getTotal());
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<PlanInboundItem> getInboundItemList(RpcHeader rpcHeader, String purchaseNo, String productCode) {
        logger.info("#traceId={}# [IN][getInboundItemList] params: purchaseNo={}, productCode={}", rpcHeader.traceId, purchaseNo, productCode);
        try {
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(purchaseNo);
            String supplierId = purchaseOrder.getSupplierId();
            Supplier supplier = supplierDao.getBySupplierId(Long.parseLong(supplierId));
//            String currencyId = purchaseOrder.getCurrencyId();
//            Currency currency = currencyDao.getCurrencyById(currencyId);

            List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemDao.selectByNoAndProduct(purchaseNo, productCode);
            List<PlanInboundItem> planInboundItemList = new ArrayList<>();
            for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
                PlanInboundItem planInboundItem = new PlanInboundItem();
                planInboundItem.setPurchaseItemId(purchaseOrderItem.getPurchaseItemId());
                planInboundItem.setProductId(purchaseOrderItem.getProductId());
                planInboundItem.setProductCode(purchaseOrderItem.getProductCode());
                planInboundItem.setProductName(purchaseOrderItem.getProductName());
                planInboundItem.setProductUnit(purchaseOrderItem.getProductUnit());
                planInboundItem.setBrandId(supplier.getSupplierId() + "");
                planInboundItem.setBrandName(supplier.getSupplierName());
//                planInboundItem.setCurrencyCode(currency.getCurrencyCode());
//                planInboundItem.setCurrencyName(currency.getCurrencyName());

                int purchaseNumber = purchaseOrderItem.getPurchaseNumber();//采购数量
                int inTransitQuantity = purchaseOrderItem.getInTransitQuantity();//在途数量
                int inStockQuantity = purchaseOrderItem.getInStockQuantity();//已入库数量purchaseOrderItem
                planInboundItem.setPurchaseNumber(purchaseNumber);
                planInboundItem.setNeedInstockNumber(purchaseNumber - inTransitQuantity - inStockQuantity);
                planInboundItem.setGuidePrice(DoubleScale.keepSixBits(purchaseOrderItem.getGuidePrice()));

                planInboundItem.setCostValue(DoubleScale.keepSixBits(purchaseOrderItem.getCostPrice()));
                Date expectedInboundDate = purchaseOrder.getExpectedInboundDate();
                planInboundItem.setRequireInboundDate(expectedInboundDate != null ? expectedInboundDate.getTime() : null);
                planInboundItemList.add(planInboundItem);
            }
            logger.info("#traceId={}# [OUT] getInboundItemList success: planInboundItemList.size={} ", rpcHeader.traceId, planInboundItemList.size());
            return planInboundItemList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int generatePurchaseOrder(RpcHeader rpcHeader,
                                     CreatePurchaseBasicInfo purchaseBasicInfo,
                                     List<CreatePurchaseItemInfo> purchaseItemInfoList) throws MalformedURLException, RemoteException {
        logger.info("#traceId={}# [IN][generatePurchaseOrder] params: ", rpcHeader.traceId);
        try {
            Date date = new Date();
            PurchaseOrder purchaseOrderInfo = new PurchaseOrder();
            List<PurchaseOrderItem> purchaseOrderItemList = new ArrayList<>();

            Project project = projectService.getByProjectId(rpcHeader, purchaseBasicInfo.getProjectId());
            Supplier supplier = supplierService.getSupplierByCode(rpcHeader, purchaseBasicInfo.getSupplierCode());
            Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, purchaseBasicInfo.getWarehouseId());
            String oderNumber = DateTimeIdGenerator.nextId(BizNumberType.PURCHASE_ORDER_NO);
            String purchaseOrderNumber = oderNumber;
            //1)插入订单信息
            //1.1 采购单
//            purchaseOrderInfo.setOrderStatus((byte) PurchaseStatusEnum..getStatus());
            purchaseOrderInfo.setShippingMode((byte) 2);
            purchaseOrderInfo.setPurchaseOrderNo(purchaseOrderNumber);
            purchaseOrderInfo.setPaymentMode((byte) purchaseBasicInfo.getPaymentMode());
            purchaseOrderInfo.setBrandOrderNo(purchaseBasicInfo.getBrandOrderNo());
            purchaseOrderInfo.setContractReferenceOrderNo(purchaseBasicInfo.getContractReferenceOrderNo());
            purchaseOrderInfo.setInStockQuantity(0); //一开始已预约入库数量为0 后续在货品预约入库的时候回写
            purchaseOrderInfo.setUnhandledQuantity(purchaseBasicInfo.getPurchaseNumber()); //一开始尚未预约入库数量为采购数量
            purchaseOrderInfo.setRemark(purchaseBasicInfo.getRemark());
            //设置时间
            purchaseOrderInfo.setExpectedInboundDate(purchaseBasicInfo.getRequirArrivalDate());
            purchaseOrderInfo.setOrderDeadlineDate(purchaseBasicInfo.getArrivalDeadline());
            //设置项目信息
            purchaseOrderInfo.setProjectId(Long.parseLong(project.getProjectId() + ""));
            purchaseOrderInfo.setProjectName(project.getProjectName());
            purchaseOrderInfo.setSupplierId(supplier.getSupplierId() + "");
            purchaseOrderInfo.setSupplierName(supplier.getSupplierName());
            purchaseOrderInfo.setWarehouseId(Integer.parseInt(warehouse.getWarehouseId() + ""));
            purchaseOrderInfo.setWarehouseName(warehouse.getWarehouseName());
            //        purchaseOrderInfo.setLogisticsType((byte)purchaseBasicInfo.getLogisticType());
            //设置金额信息
            purchaseOrderInfo.setPurchaseCategory(purchaseBasicInfo.getPurchaseCategory()); //采购品种
            purchaseOrderInfo.setTotalQuantity(purchaseBasicInfo.getPurchaseNumber());
            purchaseOrderInfo.setCouponAmountUse(DoubleScale.multipleHundred(purchaseBasicInfo.getCouponAmountUse())); //采购总金额
            purchaseOrderInfo.setPrepaidAmountUse(DoubleScale.multipleHundred(purchaseBasicInfo.getPrepaidAmountUse()));
            purchaseOrderInfo.setPurchaseGuideAmount(DoubleScale.multipleHundred(purchaseBasicInfo.getPurchaseGuideAmount()));
            purchaseOrderInfo.setPurchasePrivilegeAmount(DoubleScale.multipleHundred(purchaseBasicInfo.getPurchaseGuideAmount()));
            purchaseOrderInfo.setReturnCash(DoubleScale.multipleHundred(purchaseBasicInfo.getReturnCash()));
            purchaseOrderInfo.setPurchaseShouldPayAmount(DoubleScale.multipleHundred(purchaseBasicInfo.getPurchaseShouldPayAmount()));
            purchaseOrderInfo.setPurchaseActualPayAmount(DoubleScale.multipleHundred(purchaseBasicInfo.getPurchaseActualPayAmount()));
            purchaseOrderInfo.setPrepaidAmountUse(DoubleScale.multipleHundred(purchaseBasicInfo.getPrepaidAmountUse()));
            purchaseOrderInfo.setCouponAmountUse(DoubleScale.multipleHundred(purchaseBasicInfo.getCouponAmountUse()));
            purchaseOrderInfo.setCreatedById(Long.parseLong(purchaseBasicInfo.getUserId()));
            purchaseOrderInfo.setCreatedByName(purchaseBasicInfo.getUserName());
            //设置操作日记
            List<OperateHistoryRecord> recordList = new ArrayList<>();
            OperateHistoryRecord operateRecord = new OperateHistoryRecord();
            operateRecord.setOperateFunction("新增");
            operateRecord.setOperateTime(date);
            operateRecord.setOperateId(purchaseBasicInfo.getUserId());
            operateRecord.setOperateName(purchaseBasicInfo.getUserName());
            recordList.add(operateRecord);
            String traceJson = JSON.toJSONString(recordList);
            //1.2 采购订单明细
            double totalAmount = 0;
            JSONArray productList = new JSONArray();
            for (CreatePurchaseItemInfo createPurchaseItemInfo : purchaseItemInfoList) {
                ProductItem productItem = new ProductItem();

                productItem.setProductCode(createPurchaseItemInfo.getProductCode());
                productItem.setProductName(createPurchaseItemInfo.getProductName());
                productItem.setPurchaseNumber(createPurchaseItemInfo.getPurchaseNumber());
                productList.add(productItem);
                PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
                purchaseOrderItem.setOrderStatus((byte) 0);
                purchaseOrderItem.setPurchaseOrderNo(purchaseOrderNumber);
                purchaseOrderItem.setShippingMode((byte) 2);
                //设置仓库信息
                purchaseOrderItem.setWarehouseId(Integer.parseInt(warehouse.getWarehouseId() + ""));
                purchaseOrderItem.setWarehouseName(warehouse.getWarehouseName());
                //设置货品信息
                purchaseOrderItem.setProductId(createPurchaseItemInfo.getProductID());
                purchaseOrderItem.setProductCode(createPurchaseItemInfo.getProductCode());
                purchaseOrderItem.setProductName(createPurchaseItemInfo.getProductName());
                purchaseOrderItem.setProductUnit("aaa");
                //设置各金额
                purchaseOrderItem.setGuidePrice(DoubleScale.multipleMillion(createPurchaseItemInfo.getGuidePrice()));
                purchaseOrderItem.setPurchasePrice(DoubleScale.multipleMillion(createPurchaseItemInfo.getPurchasePrice()));
                purchaseOrderItem.setCostPrice(DoubleScale.multipleMillion(createPurchaseItemInfo.getCostPrice()));
                purchaseOrderItem.setCouponBasePrice(DoubleScale.multipleMillion(createPurchaseItemInfo.getCouponBasePrice()));
                int disCount = (int) (DoubleScale.multipleMillion(createPurchaseItemInfo.getPurchaseDiscount()));
                purchaseOrderItem.setDiscountPercent(disCount);
                purchaseOrderItem.setPrepaidAmount(DoubleScale.multipleHundred(createPurchaseItemInfo.getPrepaidAmount()));
                purchaseOrderItem.setCouponAmount(DoubleScale.multipleHundred(createPurchaseItemInfo.getCouponAmount()));
                //设置各数量
                purchaseOrderItem.setPurchaseNumber(createPurchaseItemInfo.getPurchaseNumber());
                purchaseOrderItem.setInTransitQuantity(0);//已计划入库的数量为0
                purchaseOrderItem.setInStockQuantity(0);//已入库的数量为0
//            purchaseOrderItem.setNeedPlanQuantity(createPurchaseItemInfo.getPurchaseNumber()); //尚未计划入库的数量
                purchaseOrderItem.setCreateTime(date);
                purchaseOrderItem.setLastUpdateTime(date);
                purchaseOrderItem.setTracelog(traceJson);
                purchaseOrderItem.setOrderStatus((byte) 1);
                purchaseOrderItemList.add(purchaseOrderItem);
                int row = purchaseOrderItemDao.insert(purchaseOrderItem);
            }
            logger.info("采购单号:{} ,插入货品信息完成", purchaseOrderInfo.getPurchaseOrderNo());
            purchaseOrderInfo.setProductInfo(productList.toJSONString());
            purchaseOrderInfo.setTracelog(traceJson);
            purchaseOrderInfo.setCurrencyId("1");
            purchaseOrderInfo.setCurrencyName("RMB");
            purchaseOrderInfo.setCreateTime(date);
            purchaseOrderInfo.setLastUpdateTime(date);
            purchaseOrderInfo.setOrderStatus((byte) 1);
            purchaseOrderDao.insert(purchaseOrderInfo);
            logger.info("采购单号:{} ,插入订单信息完成", purchaseOrderInfo.getPurchaseOrderNo());
            //4)使用返利与代垫
            supplierAccountService.payForPurchase(rpcHeader,
                    "CNY",
                    project.getProjectId(),
                    purchaseOrderInfo.getCouponAmountUse(),
                    purchaseOrderInfo.getAdCouponAmountUse(),
                    purchaseOrderInfo.getPrepaidAmountUse(),
                    purchaseOrderInfo.getAdPrepaidAmountUse(),
                    purchaseOrderInfo.getPurchaseOrderNo(),
                    date);
            logger.info("采购单号:{} ,扣款完成", purchaseOrderInfo.getPurchaseOrderNo());
            //5)扣款成功更新订单为付款成功
            purchaseOrderDao.updateAlreadyPay(purchaseOrderNumber);
            return 1;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    public RpcResult planInbound(RpcHeader rpcHeader,
                                 PlanInboundBasicInfo planInboundBasicInfo,
                                 List<PlanInboundItem> planInboundItemList) throws Exception {
        logger.info("#traceId={}# [IN][planInbound] params: ", rpcHeader.traceId);
        logger.info("#traceId={}# [IN][PurchaseServiceTransactionProcessor::planInbound] params: ", rpcHeader.traceId);
        try {

            //1.1 获取采购单以及采购明细信息
            String purchaseOrderNo = planInboundBasicInfo.getPurchaseOrderNo();
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(purchaseOrderNo);
            List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemDao.selectByOrderNo(planInboundBasicInfo.getPurchaseOrderNo());

            //1.1.1 校验预约入库数量
            for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {
                String productCode = purchaseOrderItem.getProductCode();
                Integer purchaseNumber = purchaseOrderItem.getPurchaseNumber();
                Integer inTransitQuantity = purchaseOrderItem.getInTransitQuantity();
                int canImportQuantity = purchaseNumber - inTransitQuantity;
                for (PlanInboundItem planInboundItem : planInboundItemList) {
                    int planInstockNumber = planInboundItem.getPlanInstockNumber();
                    String productCode1 = planInboundItem.getProductCode();
                    if (productCode.equals(productCode1)) {
                        if (planInstockNumber > canImportQuantity) {
                            return new RpcResult(ErrorCode.INBOUND_EXCEPTION.getErrorCode());
                        }
                    }
                }
            }

            Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, planInboundBasicInfo.getWarehouseId());
            String streetAddress = warehouse.getStreetAddress();
            //1.2 更新采购单在途商品数量
            //1.2.1 采购单的数量
            int inTransitQuantity1 = purchaseOrder.getInTransitQuantity();
            int unhandledQuantity = purchaseOrder.getUnhandledQuantity();
            //1.2.2 需变更的数量
            int planInstockNumberTotal = planInboundBasicInfo.getPlanInstockNumberTotal();
            PurchaseOrderBackWrite purchaseOrderBackWrite = new PurchaseOrderBackWrite();
            purchaseOrderBackWrite.setOrderStatus(PurchaseStatusEnum.ALREADY_SHIPPING.getCode());
            purchaseOrderBackWrite.setInTransitQuantity(inTransitQuantity1+planInstockNumberTotal);
            purchaseOrderBackWrite.setUnhandledQuantity(unhandledQuantity-planInstockNumberTotal);
            purchaseOrderBackWrite.setPurchaseOrderNo(purchaseOrder.getPurchaseOrderNo());
            purchaseOrderDao.updateBack(purchaseOrderBackWrite);
            logger.info("更新采购单成功 purchaseNo={}",purchaseOrder.getPurchaseOrderNo());

            //1.3 更新采购单明细信息
            for (PlanInboundItem planInboundItem:planInboundItemList){
                int planInstockNumber = planInboundItem.getPlanInstockNumber();
                String productCode = planInboundItem.getProductCode();
                if (planInstockNumber>0){
                   for (PurchaseOrderItem purchaseOrderItem:purchaseOrderItemList){
                       String productCode1 = purchaseOrderItem.getProductCode();
                       if (productCode.equals(productCode1)){
                           PurchaseOrderItemBackWrite purchaseOrderItemBackWrite = new PurchaseOrderItemBackWrite();
                           purchaseOrderItemBackWrite.setPurchaseItemId(purchaseOrderItem.getPurchaseItemId());
                           purchaseOrderItemBackWrite.setInTransitQuantity(purchaseOrderItem.getInTransitQuantity()+planInboundItem.getPlanInstockNumber());//在途商品数量=原先在途的商品数量+本次玉如入库的数量
                           purchaseOrderItemBackWrite.setOrderStatus(PurchaseStatusEnum.ALREADY_SHIPPING.getCode());
                           purchaseOrderItemDao.updateBackWriteItem(purchaseOrderItemBackWrite);
                       }
                   }
                }
            }
            logger.info("更新采购单明细成功成功 purchaseNo={}",purchaseOrder.getPurchaseOrderNo());

            // 2 日志
            String tracelog = purchaseOrder.getTracelog();
            ArrayList<OperateHistoryRecord> recordList
                    = JSON.parseObject(tracelog, new TypeReference<ArrayList<OperateHistoryRecord>>() {
            });
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
            purchaseOrderDao.updateOperateTraceLog(purchaseOrderNo,traceJson);
            logger.info("记录预约入库流水成功");

            logger.info("开始同步采购入库数据给WMS,采购单号={}",purchaseOrderNo);
            //3. 同步到WMS
            SyncPlanInboundToWmsTask syncPlanInboundToWmsTask
                    = new SyncPlanInboundToWmsTask(rpcHeader,
                    ApplicationContextProvider.getApplicationContext(),
                    planInboundBasicInfo,
                    planInboundItemList,
                    inboundService,
                    streetAddress);
            threadPoolTaskExecutor.submit(syncPlanInboundToWmsTask);
            logger.info("同步采购入库数据给WMS完成,采购单号={}",purchaseOrderNo);

            byte orderStatus = purchaseOrder.getOrderStatus();

            //4.同步到EAS
            if (orderStatus <= 70) {
                logger.info("采购单状态 ={}", orderStatus);
                SyncPurchaseOrderToEasTask task = new SyncPurchaseOrderToEasTask(ApplicationContextProvider.getApplicationContext(),
                        rpcHeader,
                        projectService,
                        supplierService,
                        warehouseService,
                        productService,
                        supplierAccountService,
                        purchaseOrder,
                        purchaseOrderItemList);
                threadPoolTaskExecutor.submit(task);
            }
            logger.info("#traceId={}# [OUT] generatePurchaseOrder success: ", rpcHeader.traceId);
            return new RpcResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PurchaseEditDetail getPurchaseEditDetail(RpcHeader rpcHeader, String purchaseOrderNumber) {
        logger.info("#traceId={}# [IN][getPurchaseEditDetail] params: purchaseOrderNumber={} ", rpcHeader.traceId, purchaseOrderNumber);

        PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(purchaseOrderNumber);
        List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemDao.selectByOrderNo(purchaseOrderNumber);

        AccountAmount accountAmount = supplierAccountService.getSupplierAccountAmount(rpcHeader, "CNY", Long.parseLong(purchaseOrder.getProjectId() + ""));


        //设置订单信息
        PurchaseEditDetail purchaseEditDetail = new PurchaseEditDetail();
        purchaseEditDetail.setAccountCouponAmount(DoubleScale.keepTwoBits(accountAmount.getCouponAmount()));
        purchaseEditDetail.setAccountPrepaidAmount(DoubleScale.keepTwoBits(accountAmount.getPrepaidAmount()));

        purchaseEditDetail.setProjectId(purchaseOrder.getProjectId() + "");
        purchaseEditDetail.setProjectName(purchaseOrder.getProjectName());
        purchaseEditDetail.setBusinessDate(purchaseOrder.getBusinessDate());
        purchaseEditDetail.setPurchaseType(purchaseOrder.getPurchaseType() + "");
        purchaseEditDetail.setSupplierOrderNo(purchaseOrder.getBrandOrderNo());
        purchaseEditDetail.setContractReferenceOrderNo(purchaseOrder.getContractReferenceOrderNo());
        purchaseEditDetail.setPurchaseCategory(purchaseOrderItemList.size());
        purchaseEditDetail.setPurchaseTotalNumber(purchaseOrder.getTotalQuantity());


        purchaseEditDetail.setCouponAmountUse(DoubleScale.keepTwoBits(purchaseOrder.getCouponAmountUse()));
        purchaseEditDetail.setPrepaidAmountUse(DoubleScale.keepTwoBits(purchaseOrder.getPrepaidAmountUse()));
        purchaseEditDetail.setAdCouponAmountUse("0.00");
        purchaseEditDetail.setAdPrepaidAmountUse("0.00");
        purchaseEditDetail.setPurchaseGuideAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseGuideAmount()));
        purchaseEditDetail.setPurchaseShouldPayAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseShouldPayAmount()));
        purchaseEditDetail.setPurchasePrivilegeAmount("0.00");
        purchaseEditDetail.setReturnCash(DoubleScale.keepTwoBits(purchaseOrder.getReturnCash()));
        purchaseEditDetail.setPurchaseActualPayAmount(DoubleScale.keepTwoBits(purchaseOrder.getPurchaseActualPayAmount()));
        purchaseEditDetail.setCashAmountUse(DoubleScale.keepTwoBits(purchaseOrder.getCashAmountUse()));
        //设置货品信息
        List<PurchaseItemVo> purchaseItemVos = new ArrayList<>();
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {
            PurchaseItemVo purchaseItemVo = new PurchaseItemVo();
            purchaseItemVo.setPurchaseItemId(purchaseOrderItem.getPurchaseItemId());
            purchaseItemVo.setPurchaseOrderNo(purchaseOrderItem.getPurchaseOrderNo());
            purchaseItemVo.setProductId(purchaseOrderItem.getProductId());
            purchaseItemVo.setProductCode(purchaseOrderItem.getProductCode());
            purchaseItemVo.setProductName(purchaseOrderItem.getProductName());
            purchaseItemVo.setDiscountPercent("0.000000");
            purchaseItemVo.setCouponAmount(DoubleScale.keepTwoBits(purchaseOrderItem.getCouponAmount()));
            purchaseItemVo.setPrepaidAmount(DoubleScale.keepTwoBits(purchaseOrderItem.getPrepaidAmount()));
            purchaseItemVo.setGuidePrice(DoubleScale.keepSixBits(purchaseOrderItem.getGuidePrice()));
            purchaseItemVo.setPurchasePrice(DoubleScale.keepSixBits(purchaseOrderItem.getPurchasePrice()));
            purchaseItemVo.setCostPrice(DoubleScale.keepSixBits(purchaseOrderItem.getCostPrice()));
            purchaseItemVo.setFactoryPrice(DoubleScale.keepSixBits(purchaseOrderItem.getFactoryPrice()));
//            purchaseItemVo.setCouponBasePrice(purchaseOrderItem.getCouponBasePrice()/1000.0);
            purchaseItemVo.setPurchaseNumber(purchaseOrderItem.getPurchaseNumber());
            purchaseItemVos.add(purchaseItemVo);
        }
        purchaseEditDetail.setItemList(purchaseItemVos);
        logger.info("#traceId={}# [OUT] getPurchaseEditDetail success: ", rpcHeader.traceId);
        return purchaseEditDetail;
    }

    @Override
    public int updatePurchaseOrder(RpcHeader rpcHeader, CreatePurchaseBasicInfo purchaseBasicInfo, List<CreatePurchaseItemInfo> purchaseItemInfoList) {
        logger.info("#traceId={}# [IN][updatePurchaseOrder] params:", rpcHeader.traceId);

        Supplier supplier = supplierService.getSupplierByCode(rpcHeader, purchaseBasicInfo.getSupplierCode());
        Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, purchaseBasicInfo.getWarehouseId());
        PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(purchaseBasicInfo.getPurchaseOrderNo());
        String tracelog = purchaseOrder.getTracelog();

        long projectId = purchaseOrder.getProjectId();

        PurchaseOrder purchaseOrderInfo = new PurchaseOrder();
        List<PurchaseOrderItem> purchaseOrderItemList = new ArrayList<>();

        //1)插入订单信息
        //1.1 采购单
        Date date = new Date();
        purchaseOrderInfo.setOrderStatus((byte)PurchaseStatusEnum.COMMIT.getCode());
//        purchaseOrderInfo.setShippingMode((byte) 2);
        purchaseOrderInfo.setPurchaseOrderNo(purchaseBasicInfo.getPurchaseOrderNo());
        purchaseOrderInfo.setPaymentMode((byte) purchaseBasicInfo.getPaymentMode());
        purchaseOrderInfo.setBrandOrderNo(purchaseBasicInfo.getBrandOrderNo());
        purchaseOrderInfo.setContractReferenceOrderNo(purchaseBasicInfo.getContractReferenceOrderNo());
        purchaseOrderInfo.setRemark(purchaseBasicInfo.getRemark());
        purchaseOrderInfo.setCreatedById(Long.parseLong(purchaseBasicInfo.getUserId()));
        purchaseOrderInfo.setCreatedByName(purchaseBasicInfo.getUserName());
        purchaseOrderInfo.setPurchaseType(purchaseBasicInfo.getPurchaseType());
        //设置时间
        purchaseOrderInfo.setExpectedInboundDate(purchaseBasicInfo.getRequirArrivalDate());
        purchaseOrderInfo.setOrderDeadlineDate(purchaseBasicInfo.getArrivalDeadline());
        purchaseOrderInfo.setBusinessDate(purchaseBasicInfo.getBusinessDate());
        //设置项目信息
        purchaseOrderInfo.setSupplierId(supplier.getSupplierId() + "");
        purchaseOrderInfo.setSupplierName(supplier.getSupplierName());
        purchaseOrderInfo.setWarehouseId(Integer.parseInt(warehouse.getWarehouseId() + ""));
        purchaseOrderInfo.setWarehouseName(warehouse.getWarehouseName());
        purchaseOrderInfo.setShippingMode((byte)purchaseBasicInfo.getLogisticType());
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
                = JSON.parseObject(tracelog, new TypeReference<ArrayList<OperateHistoryRecord>>() {
        });
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
        for (CreatePurchaseItemInfo createPurchaseItemInfo : purchaseItemInfoList) {
            PurchaseOrderItem purchaseOrderItem1 =
                    purchaseOrderItemDao.selectByOrderNoAndProduct(purchaseOrder.getPurchaseOrderNo(), createPurchaseItemInfo.getProductCode());
            ProductBasic byProductCode = productService.getByProductCode(rpcHeader, createPurchaseItemInfo.getProductCode());
            ProductItem productItem = new ProductItem();
            productItem.setProductCode(createPurchaseItemInfo.getProductCode());
            productItem.setProductName(createPurchaseItemInfo.getProductName());
            productItem.setPurchaseNumber(createPurchaseItemInfo.getPurchaseNumber());
            productList.add(productItem);
            PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
            purchaseOrderItem.setOrderStatus((byte)PurchaseStatusEnum.COMMIT.getCode());
            purchaseOrderItem.setShippingMode((byte) 2);
            purchaseOrderItem.setPurchaseItemId(purchaseOrderItem1.getPurchaseItemId());
//            purchaseOrderItem.setProductCode(byProductCode.getEasCode());
            purchaseOrderItem.setEasMateriaCode(byProductCode.getEasCode());
            //设置仓库信息
            purchaseOrderItem.setWarehouseId(Integer.parseInt(warehouse.getWarehouseId() + ""));
            purchaseOrderItem.setWarehouseName(warehouse.getWarehouseName());
            //设置各金额
            purchaseOrderItem.setGuidePrice(DoubleScale.multipleMillion(createPurchaseItemInfo.getGuidePrice()));
            int disCount = (int) (DoubleScale.multipleMillion(createPurchaseItemInfo.getPurchaseDiscount()));
            purchaseOrderItem.setDiscountPercent(disCount);
            purchaseOrderItem.setPrepaidAmount(DoubleScale.multipleHundred(createPurchaseItemInfo.getPrepaidAmount()));
            purchaseOrderItem.setCouponAmount(DoubleScale.multipleHundred(createPurchaseItemInfo.getCouponAmount()));
//            purchaseOrderItem.setNeedPlanQuantity(createPurchaseItemInfo.getPurchaseNumber()); //尚未计划入库的数量
            purchaseOrderItem.setLastUpdateTime(date);
            purchaseOrderItem.setTracelog(traceJson);
            purchaseOrderItemList.add(purchaseOrderItem);
            int row = purchaseOrderItemDao.updatePurchaseItem(purchaseOrderItem);
        }
        logger.info("采购单号:{} ,插入货品信息完成", purchaseOrderInfo.getPurchaseOrderNo());
        purchaseOrderInfo.setProductInfo(productList.toJSONString());
        purchaseOrderInfo.setTracelog(traceJson);
        purchaseOrderInfo.setLastUpdateTime(date);
        purchaseOrderInfo.setEasStatus(PurchaseEasStatus.SYN_EAS_FAILURE.getStatus());
        purchaseOrderDao.updatePurchaseOrder(purchaseOrderInfo);
        logger.info("采购单号:{} ,插入订单信息完成", purchaseOrderInfo.getPurchaseOrderNo());
        //4)使用返利与代垫
        supplierAccountService.payForPurchase(rpcHeader,
                "CNY",
                projectId,
                purchaseOrderInfo.getCouponAmountUse(),
                purchaseOrderInfo.getAdCouponAmountUse(),
                purchaseOrderInfo.getPrepaidAmountUse(),
                purchaseOrderInfo.getAdPrepaidAmountUse(),
                purchaseOrderInfo.getPurchaseOrderNo(),
                date);
        logger.info("采购单号:{} ,扣款完成", purchaseOrderInfo.getPurchaseOrderNo());
        //5)扣款成功更新订单为付款成功
        purchaseOrderDao.updateAlreadyPay(purchaseOrderInfo.getPurchaseOrderNo());
        logger.info("采购单号:{} ,更新扣款状态完成", purchaseOrderInfo.getPurchaseOrderNo());
        logger.info("#traceId={}# [OUT] updatePurchaseOrder success: ", rpcHeader.traceId);
        return 0;
    }

    @Override
    public PurchaseOrder getPurchaseOrderByPurchaseNo(RpcHeader rpcHeader, String purchaseOrderNo) {
        logger.info("#traceId={}# [IN][updatePurchaseOrder] params: purchaseOrderNo={}", rpcHeader.traceId, purchaseOrderNo);
        try {
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(purchaseOrderNo);
            logger.info("#traceId={}# [OUT] updatePurchaseOrder success: ", rpcHeader.traceId);
            return purchaseOrder;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int cancelPurchaseOrder(RpcHeader rpcHeader, String purchaseOrderNo) {
        logger.info("#traceId={}# [IN][canclePurchaseOrder] params: purchaseOrderNo={}", rpcHeader.traceId, purchaseOrderNo);
        try {
            int i = purchaseOrderDao.cancelPurchaseOrder(purchaseOrderNo);
            logger.info("#traceId={}# [OUT] canclePurchaseOrder success: ", rpcHeader.traceId);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

}
