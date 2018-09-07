package com.yhglobal.gongxiao.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.dao.PurchaseOrderDao;
import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.excel.PurchaseOrderParse;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.model.BatchImportPurchaseOrder;
import com.yhglobal.gongxiao.model.PurchaseOrder;
import com.yhglobal.gongxiao.model.PurchaseOrderItem;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.service.SupplierAccountService;
import com.yhglobal.gongxiao.purchase.service.PurchaseFileService;
import com.yhglobal.gongxiao.purchase.service.PurchaseService;
import com.yhglobal.gongxiao.purchase.temp.OperateHistoryRecord;
import com.yhglobal.gongxiao.status.PurchaseEasStatus;
import com.yhglobal.gongxiao.status.PurchaseStatusEnum;
import com.yhglobal.gongxiao.util.DoubleScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

@Service(timeout = 30000)
public class PurchaseFileServiceImpl implements PurchaseFileService {

    private Logger logger = LoggerFactory.getLogger(PurchaseFileServiceImpl.class);

    @Reference
    SupplierAccountService supplierAccountService; //供应商账户

    @Reference
    ProductService productService;//货品

    @Reference
    ProjectService projectService; //项目服务类

    @Autowired
    PurchaseService purchaseService; //采购单

    @Autowired
    PurchaseOrderDao purchaseOrderDao; //采购单

    @Autowired
    PurchaseOrderItemDao purchaseOrderItemDao; //采购明细

    @Override
    public RpcResult parsePurchaseOrderList(RpcHeader rpcHeader, String filePath, String projectId, String projectName) {
        //解析文件
        Map<String, List<BatchImportPurchaseOrder>> purchaseOrderMap = PurchaseOrderParse.parse(filePath);//解析后的map
        Map<String, List<BatchImportPurchaseOrder>> purchaseMap = new HashMap<>();//去除同一订单内货品重复的Map

        double couponTotalAmount = 0;
        double prepaidTotalAmount = 0;
        Date date = new Date();
        //数据校验以及数据转换
        for (String supplierOrderNo : purchaseOrderMap.keySet()) {
            //1)数据初步校验
            //1.1 校验供应商订单号不能为空
            if ("".equals(supplierOrderNo)) {
                return new RpcResult(ErrorCode.SUPPLIER_ORDER_NOT_NULL.getErrorCode(), ErrorCode.SUPPLIER_ORDER_NOT_NULL.getMessage());
            }
            List<BatchImportPurchaseOrder> batchImportPurchaseOrders = purchaseOrderMap.get(supplierOrderNo);
            String contractNo = "";
            Date busiDate = null;
            //<productCode,BatchImportPurchaseOrder>
            Map<String, BatchImportPurchaseOrder> productCodeMap = new HashMap<>();
            for (BatchImportPurchaseOrder batchImportPurchaseOrder : batchImportPurchaseOrders) {

                couponTotalAmount += batchImportPurchaseOrder.getCouponAmount();
                prepaidTotalAmount += batchImportPurchaseOrder.getPrepaidAmount();

                String contractNumber = batchImportPurchaseOrder.getContractNumber();
                //1.2 校验关键数据不一致
                if ("".equals(contractNo)) {
                    contractNo = contractNumber;
                } else {
                    if (!contractNo.equals(contractNumber)) {
                        return new RpcResult(ErrorCode.ORDER_DATA_NOT_CONSISTENT.getErrorCode(), supplierOrderNo + ErrorCode.ORDER_DATA_NOT_CONSISTENT.getMessage());
                    }
                }
                Date businessDate = batchImportPurchaseOrder.getBusinessDate();
                if (busiDate == null) {
                    busiDate = businessDate;
                } else {
                    if (businessDate.getTime() != busiDate.getTime()) {
                        return new RpcResult(ErrorCode.ORDER_DATA_NOT_CONSISTENT.getErrorCode(), contractNumber + ErrorCode.ORDER_DATA_NOT_CONSISTENT.getMessage());
                    }
                }
                //1.3 校验关键数据不能为空
                if ("".equals(contractNumber) || businessDate == null) {
                    return new RpcResult(ErrorCode.KEY_INFORMATION_IS_NULL.getErrorCode(), contractNumber + ErrorCode.KEY_INFORMATION_IS_NULL.getMessage());
                }
                //1.3 行总净金额为0的,必须要有代垫
                double totalAmount = batchImportPurchaseOrder.getTotalAmount();
                if (totalAmount == 0) {
                    double prepaidAmount = batchImportPurchaseOrder.getPrepaidAmount();
                    if (prepaidAmount == 0) {
                        return new RpcResult(ErrorCode.PRODUCT_ADDITION.getErrorCode(), "供应商单号为" + supplierOrderNo + ErrorCode.PRODUCT_ADDITION.getMessage());
                    }
                }
                //2)数据处理 以productCode为key,合并同一采购单内货品相同的采购单
                String productCode = batchImportPurchaseOrder.getProductCode();
                BatchImportPurchaseOrder batchImportPurchaseOrder1 = productCodeMap.get(productCode);
                if (batchImportPurchaseOrder1 == null) {
                    productCodeMap.put(productCode, batchImportPurchaseOrder);
                } else {
                    batchImportPurchaseOrder1.setCashAmount(batchImportPurchaseOrder1.getCashAmount() + batchImportPurchaseOrder.getCashAmount());
                    batchImportPurchaseOrder1.setPurchaseNumber(batchImportPurchaseOrder1.getPurchaseNumber() + batchImportPurchaseOrder.getPurchaseNumber());
                    batchImportPurchaseOrder1.setCouponAmount(batchImportPurchaseOrder1.getCouponAmount() + batchImportPurchaseOrder.getCouponAmount());
                    batchImportPurchaseOrder1.setPrepaidAmount(batchImportPurchaseOrder1.getPrepaidAmount() + batchImportPurchaseOrder.getPrepaidAmount());
                    batchImportPurchaseOrder1.setTotalAmount(batchImportPurchaseOrder1.getTotalAmount() + batchImportPurchaseOrder.getTotalAmount());
                }
            }
            //3)同一订单的数据放到 key为订单号,value为订单明细的订单内
            List<BatchImportPurchaseOrder> importPurchaseOrderList = new ArrayList<>();
            for (BatchImportPurchaseOrder batchImportPurchaseOrder : productCodeMap.values()) {
                importPurchaseOrderList.add(batchImportPurchaseOrder);
            }
            purchaseMap.put(supplierOrderNo, importPurchaseOrderList);
        }
        //4) 返利/代垫余额校验
        AccountAmount accountAmount = null;
        try {
            accountAmount = supplierAccountService.getSupplierAccountAmount(rpcHeader, "CNY", Long.parseLong(projectId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        double prepaidAmount = accountAmount.getPrepaidAmount() / FXConstant.HUNDRED;
        double couponAmount = accountAmount.getCouponAmount() / FXConstant.HUNDRED;
        if (couponAmount < -couponTotalAmount) {
            return new RpcResult(ErrorCode.COUPON_AMOUNT_NOT_ENOUGH.getErrorCode(), ErrorCode.COUPON_AMOUNT_NOT_ENOUGH.getMessage());
        }
        if (prepaidAmount < -prepaidTotalAmount) {
            return new RpcResult(ErrorCode.PREPAID_AMOUNT_NOT_ENOUGH.getErrorCode(), ErrorCode.PREPAID_AMOUNT_NOT_ENOUGH.getMessage());
        }
        //5) 供应商单号校验
        for (String supplierNo : purchaseMap.keySet()) {
            List<PurchaseOrder> purchaseOrders = purchaseOrderDao.getByBrandOrderNo(supplierNo);
            if (purchaseOrders.size() != 0) {
                return new RpcResult(ErrorCode.ORDER_ALREADY_KEY_IN.getErrorCode(), ErrorCode.ORDER_ALREADY_KEY_IN.getMessage());
            }
        }
        //5) 生成采购单
        Project project = projectService.getByProjectId(rpcHeader, projectId);
        Integer monthlyCouponGenerateRate = project.getMonthlyCouponGenerateRate();
        Integer quarterlyCouponGenerateRate = project.getQuarterlyCouponGenerateRate();
        Integer annualCouponGenerateRate = project.getAnnualCouponGenerateRate();
        int couponGenerateRate = 0;
        if (monthlyCouponGenerateRate != null) {
            couponGenerateRate += monthlyCouponGenerateRate;
        }
        if (quarterlyCouponGenerateRate != null) {
            couponGenerateRate += quarterlyCouponGenerateRate;
        }
        if (annualCouponGenerateRate != null) {
            couponGenerateRate += annualCouponGenerateRate;
        }
        for (String supplierNo : purchaseMap.keySet()) {
            List<BatchImportPurchaseOrder> batchImportPurchaseOrders = purchaseMap.get(supplierNo);
            //5.1 生成采购单号
            String oderNumber = DateTimeIdGenerator.nextId(BizNumberType.PURCHASE_ORDER_NO);
            String purchaseOrderNumber = oderNumber;
            //5.2 拼采购订单信息
            PurchaseOrder purchaseOrderInfo = new PurchaseOrder();
            purchaseOrderInfo.setOrderStatus((byte)PurchaseStatusEnum.DRAFT.getCode());
            purchaseOrderInfo.setInStockQuantity(0); //一开始已预约入库数量为0 后续在货品预约入库的时候回写
            purchaseOrderInfo.setPurchaseOrderNo(purchaseOrderNumber);
            purchaseOrderInfo.setProjectId(Long.parseLong(projectId));
            purchaseOrderInfo.setProjectName(projectName);
            purchaseOrderInfo.setCreateTime(date);
            purchaseOrderInfo.setLastUpdateTime(date);
            //5.3 设置操作日记
            List<OperateHistoryRecord> recordList = new ArrayList<>();
            OperateHistoryRecord operateRecord = new OperateHistoryRecord();
            operateRecord.setOperateFunction("导入");
            operateRecord.setOperateTime(date);
            operateRecord.setOperateId(rpcHeader.getUid());
            operateRecord.setOperateName(rpcHeader.getUsername());
            recordList.add(operateRecord);
            String traceJson = JSON.toJSONString(recordList);

            //5.4 各数量和金额
            double purchaseNumber = 0;
            double cashReturnTotal = 0;
            double prepaidAmountTotal = 0;
            double couponAmountTotal = 0;
            double totalAmount = 0;
            for (BatchImportPurchaseOrder batchImportPurchaseOrder : batchImportPurchaseOrders) {
                ProductBasic product = productService.getByProductModel(rpcHeader, batchImportPurchaseOrder.getProductCode());
                if (product == null) {
                    return new RpcResult(ErrorCode.NOT_PRODUCT_CODE.getErrorCode(), batchImportPurchaseOrder.getProductCode()+ErrorCode.NOT_PRODUCT_CODE.getMessage());
                }
                //订单信息
                purchaseOrderInfo.setContractReferenceOrderNo(batchImportPurchaseOrder.getContractNumber());
                purchaseOrderInfo.setBrandOrderNo(batchImportPurchaseOrder.getSupplierNumber());
                purchaseOrderInfo.setBusinessDate(batchImportPurchaseOrder.getBusinessDate());

                purchaseNumber += batchImportPurchaseOrder.getPurchaseNumber();
                cashReturnTotal += batchImportPurchaseOrder.getCashAmount();
                prepaidAmountTotal += batchImportPurchaseOrder.getPrepaidAmount();
                couponAmountTotal += batchImportPurchaseOrder.getCouponAmount();
                totalAmount += batchImportPurchaseOrder.getTotalAmount();

                if ("INTERNET".equals(batchImportPurchaseOrder.getPurchaseType())) {
                    purchaseOrderInfo.setPurchaseType(1);
                } else {
                    purchaseOrderInfo.setPurchaseType(2);
                }
                //5.3 拼装采购单货品信息
                PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
                purchaseOrderItem.setOrderStatus((byte)PurchaseStatusEnum.DRAFT.getCode());
                purchaseOrderItem.setPurchaseOrderNo(purchaseOrderNumber);
                purchaseOrderItem.setProductCode(product.getProductCode());
                purchaseOrderItem.setProductId(product.getId() + "");
                purchaseOrderItem.setProductName(product.getProductName());
                purchaseOrderItem.setProductUnit("GE");
                purchaseOrderItem.setPrepaidAmount(DoubleScale.multipleHundred(-batchImportPurchaseOrder.getPrepaidAmount() * FXConstant.TAX_RATE_SUM));
                purchaseOrderItem.setCouponAmount(DoubleScale.multipleHundred(-batchImportPurchaseOrder.getCouponAmount() * FXConstant.TAX_RATE_SUM));
                purchaseOrderItem.setCashAmount(DoubleScale.multipleHundred(-batchImportPurchaseOrder.getCashAmount() * FXConstant.TAX_RATE_SUM));
                purchaseOrderItem.setPurchaseNumber(batchImportPurchaseOrder.getPurchaseNumber());
                purchaseOrderItem.setInTransitQuantity(0);//已计划入库的数量为0
                purchaseOrderItem.setInStockQuantity(0);//已入库的数量为0
                purchaseOrderItem.setCreateTime(date);
                purchaseOrderItem.setLastUpdateTime(date);
                purchaseOrderItem.setTracelog(traceJson);

                double totalAmount1 = batchImportPurchaseOrder.getTotalAmount();
                double cashAmount1 = batchImportPurchaseOrder.getCashAmount();
                double prepaidAmount1 = batchImportPurchaseOrder.getPrepaidAmount();
                double couponAmount1 = batchImportPurchaseOrder.getCouponAmount();
                int purchaseNumber1 = batchImportPurchaseOrder.getPurchaseNumber();
                long purchasePrice = DoubleScale.multipleMillion((totalAmount1 - cashAmount1 - prepaidAmount1 - couponAmount1) / purchaseNumber1 * FXConstant.TAX_RATE_SUM);
                purchaseOrderItem.setPurchasePrice(purchasePrice);
                purchaseOrderItem.setGuidePrice(purchasePrice); //指导价 =
                long costPrice = Long.parseLong( (int)((purchasePrice * (1 - (double) couponGenerateRate / FXConstant.MILLION))+(cashAmount1/purchaseNumber1)) + "");    //成本价=采购价*(1-应收返利点之和)-|现金折扣|/数量
                purchaseOrderItem.setCostPrice(costPrice);
                purchaseOrderItem.setFactoryPrice(DoubleScale.multipleMillion(totalAmount1 / purchaseNumber1));

                int row = purchaseOrderItemDao.insert(purchaseOrderItem);
            }
            purchaseOrderInfo.setTotalQuantity((int) purchaseNumber);
            //设置各金额
            purchaseOrderInfo.setPrepaidAmountUse(DoubleScale.multipleHundred(-prepaidAmountTotal * FXConstant.TAX_RATE_SUM));//YH代垫
            purchaseOrderInfo.setCouponAmountUse(DoubleScale.multipleHundred(-couponAmountTotal * FXConstant.TAX_RATE_SUM));//YH返利
            purchaseOrderInfo.setReturnCash(DoubleScale.multipleHundred(-cashReturnTotal * FXConstant.TAX_RATE_SUM));//现金返点
            purchaseOrderInfo.setPurchaseActualPayAmount(DoubleScale.multipleHundred(totalAmount * FXConstant.TAX_RATE_SUM));//实付金额
            double shouldPayAmount = (totalAmount - couponAmountTotal - prepaidAmountTotal - cashReturnTotal) * FXConstant.TAX_RATE_SUM * 100;
            purchaseOrderInfo.setPurchaseShouldPayAmount((long) shouldPayAmount);//应付金额
            purchaseOrderInfo.setPurchaseGuideAmount(purchaseOrderInfo.getPurchaseShouldPayAmount());//折扣为0 采购指导金额=采购金额=采购应付金额
            purchaseOrderInfo.setEstimatedCouponTotalAmount(DoubleScale.multipleHundred(purchaseOrderInfo.getPurchaseShouldPayAmount() * couponGenerateRate));//预计返利应收
            purchaseOrderInfo.setTracelog(traceJson);
            purchaseOrderInfo.setCreatedByName(rpcHeader.getUsername());
            purchaseOrderInfo.setEasStatus((byte) PurchaseEasStatus.SYN_EAS_FAILURE.getStatus());
            purchaseOrderInfo.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            purchaseOrderInfo.setUnhandledQuantity((int) purchaseNumber);
            purchaseOrderDao.insert(purchaseOrderInfo);
        }
        logger.info("一共生成{}个采购单", purchaseMap.size());
        return new RpcResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());

    }
}
