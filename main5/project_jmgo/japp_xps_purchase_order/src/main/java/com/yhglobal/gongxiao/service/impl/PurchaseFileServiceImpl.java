package com.yhglobal.gongxiao.service.impl;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.PurchaseEasStatus;
import com.yhglobal.gongxiao.constant.PurchaseStatus;
import com.yhglobal.gongxiao.dao.PurchaseOrderDao;
import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.excel.PurchaseOrderParse;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.BatchImportPurchaseOrder;
import com.yhglobal.gongxiao.model.OperateHistoryRecord;
import com.yhglobal.gongxiao.model.PurchaseOrder;
import com.yhglobal.gongxiao.model.PurchaseOrderItem;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure;
import com.yhglobal.gongxiao.purchase.microservice.PurchaseFileServiceGrpc;
import com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure;
import com.yhglobal.gongxiao.status.PurchaseTypeStatus;
import com.yhglobal.gongxiao.status.SupplierReceiptStatus;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import javafx.scene.control.Tab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PurchaseFileServiceImpl extends PurchaseFileServiceGrpc.PurchaseFileServiceImplBase {

    private Logger logger = LoggerFactory.getLogger(PurchaseFileServiceImpl.class);

    @Autowired
    PurchaseOrderDao purchaseOrderDao; //采购单

    @Autowired
    PurchaseOrderItemDao purchaseOrderItemDao; //采购明细

    public void parsePurchaseOrderList(PurchaseFileStructure.PurchaseFileReq request,
                                       StreamObserver<PurchaseFileStructure.PurchaseFileResp> responseObserver) {
        logger.info("# [IN][parsePurchaseOrderList] params: ");
        String filePath = request.getFilePath();
        String projectId = request.getProjectId();
        String projectName = request.getProjectName();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));

        PurchaseFileStructure.PurchaseFileResp response = null; //保存返回的值
        PurchaseFileStructure.PurchaseFileResp.Builder respBuilder = PurchaseFileStructure.PurchaseFileResp.newBuilder(); //每个proto对象都需要从builder构建出来
        PurchaseFileStructure.RpcResultPurchaseFile.Builder rpcResultBuilder = PurchaseFileStructure.RpcResultPurchaseFile.newBuilder();
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
                buildReturn(rpcResultBuilder, respBuilder, response, responseObserver,
                        ErrorCode.SUPPLIER_ORDER_NOT_NULL.getErrorCode(), ErrorCode.SUPPLIER_ORDER_NOT_NULL.getMessage());
                return;
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
                        buildReturn(rpcResultBuilder, respBuilder, response, responseObserver,
                                ErrorCode.ORDER_DATA_NOT_CONSISTENT.getErrorCode(), contractNumber+ErrorCode.ORDER_DATA_NOT_CONSISTENT.getMessage());
                        return;
                    }
                }
                Date businessDate = batchImportPurchaseOrder.getBusinessDate();
                if (busiDate == null) {
                    busiDate = businessDate;
                } else {
                    if (businessDate.getTime() != busiDate.getTime()) {
                        buildReturn(rpcResultBuilder, respBuilder, response, responseObserver,
                                ErrorCode.ORDER_DATA_NOT_CONSISTENT.getErrorCode(), ErrorCode.ORDER_DATA_NOT_CONSISTENT.getMessage());
                        return;
                    }
                }
                //1.3 校验关键数据不能为空
                if ("".equals(contractNumber) || businessDate == null) {
                    buildReturn(rpcResultBuilder, respBuilder, response, responseObserver,
                            ErrorCode.KEY_INFORMATION_IS_NULL.getErrorCode(), ErrorCode.KEY_INFORMATION_IS_NULL.getMessage());
                    return;
                }
                //1.3 行总净金额为0的,必须要有代垫
                double totalAmount = batchImportPurchaseOrder.getTotalAmount();
                if (totalAmount == 0) {
                    double prepaidAmount = batchImportPurchaseOrder.getPrepaidAmount();
                    if (prepaidAmount == 0) {
                        buildReturn(rpcResultBuilder, respBuilder, response, responseObserver,
                                ErrorCode.PRODUCT_ADDITION.getErrorCode(), ErrorCode.PRODUCT_ADDITION.getMessage());
                        return;
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
        //4) 返利/代垫余额校验  调用返利余额接口
        //***********************************
        SupplierAccountServiceStructure.GetSupplierAccountAmountRequest.Builder builder
                = SupplierAccountServiceStructure.GetSupplierAccountAmountRequest.newBuilder()
                .setRpcHeader(rpcHeader)
                .setCurrencyCode("CNY")
                .setProjectId(Long.parseLong(projectId));
        SupplierAccountServiceStructure.GetSupplierAccountAmountRequest req = builder.build();
        //4.2 接收返回值的构建
        PaymentCommonGrpc.AccountAmountResponse resp;
        //4.3 存根的构建
        SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub
                = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);

        //*****************************************
        try {
            //4.4 调用
            resp = rpcStub.getSupplierAccountAmount(req);
            double prepaidAmount = resp.getPrepaidAmount() / FXConstant.HUNDRED;
            double couponAmount = resp.getCouponAmount() / FXConstant.HUNDRED;
            if (couponAmount < couponTotalAmount) {
                buildReturn(rpcResultBuilder, respBuilder, response, responseObserver,
                        ErrorCode.COUPON_AMOUNT_NOT_ENOUGH.getErrorCode(), ErrorCode.COUPON_AMOUNT_NOT_ENOUGH.getMessage());
                return;
            }
            if (prepaidAmount < prepaidTotalAmount) {
                buildReturn(rpcResultBuilder, respBuilder, response, responseObserver,
                        ErrorCode.PREPAID_AMOUNT_NOT_ENOUGH.getErrorCode(), ErrorCode.PREPAID_AMOUNT_NOT_ENOUGH.getMessage());
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //5) 供应商单号校验
        for (String supplierNo : purchaseMap.keySet()) {
            List<PurchaseOrder> purchaseOrders = purchaseOrderDao.getByBrandOrderNo(tablePrefix,supplierNo);
            if (purchaseOrders.size() != 0) {
                buildReturn(rpcResultBuilder, respBuilder, response, responseObserver,
                        ErrorCode.ORDER_ALREADY_KEY_IN.getErrorCode(), supplierNo+ErrorCode.ORDER_ALREADY_KEY_IN.getMessage());
                return;
            }
        }

        //5) 生成采购单
        //*************************调用获取项目接口
        //5.1 请求参数的构建
        ProjectStructure.GetByProjectIdReq.Builder getByProjectIdBuilder
                = ProjectStructure.GetByProjectIdReq.newBuilder();
        getByProjectIdBuilder.setRpcHeader(rpcHeader);
        getByProjectIdBuilder.setProjectId(projectId);
        ProjectStructure.GetByProjectIdReq projectIdReq = getByProjectIdBuilder.build();
        //5.2 接收返回值的构建
        ProjectStructure.GetByProjectIdResp getByProjectIdResp = null;
        //5.3 构建存根
        ProjectServiceGrpc.ProjectServiceBlockingStub projectServiceBlockingStub
                = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
        //5.4 实际调用
        try {
            getByProjectIdResp = projectServiceBlockingStub.getByProjectId(projectIdReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //************************

        ProjectStructure.Project project = getByProjectIdResp.getProject();
        long monthlyCouponGenerateRate = project.getMonthCouponRate();
        long quarterlyCouponGenerateRate = project.getQuarterCouponRate();
        long annualCouponGenerateRate = project.getAnnualCouponRate();
        int couponGenerateRate = 0;
        if (monthlyCouponGenerateRate != 0) {
            couponGenerateRate += monthlyCouponGenerateRate;
        }
        if (quarterlyCouponGenerateRate != 0) {
            couponGenerateRate += quarterlyCouponGenerateRate;
        }
        if (annualCouponGenerateRate != 0) {
            couponGenerateRate += annualCouponGenerateRate;
        }
        for (String supplierNo : purchaseMap.keySet()) {
            List<BatchImportPurchaseOrder> batchImportPurchaseOrders = purchaseMap.get(supplierNo);
            //5.1 生成采购单号
            String oderNumber = DateTimeIdGenerator.nextId(tablePrefix,BizNumberType.PURCHASE_ORDER_NO);
            String purchaseOrderNumber = oderNumber;
            //5.2 拼采购订单信息
            PurchaseOrder purchaseOrderInfo = new PurchaseOrder();
            purchaseOrderInfo.setOrderStatus(PurchaseStatus.ORDER_IMPORT.getStatus());
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
            double cashAmount = 0;
            double prepaidAmountTotal = 0;
            double couponAmountTotal = 0;
            double totalAmount = 0;
            for (BatchImportPurchaseOrder batchImportPurchaseOrder : batchImportPurchaseOrders) {
//****************************************************************  调用货品接口
                //5.4.1 构建请求参数
                ProductStructure.GetByProductModelReq.Builder productModelBuilder
                        = ProductStructure.GetByProductModelReq.newBuilder();
                productModelBuilder.setRpcHeader(rpcHeader);
                productModelBuilder.setProductModel(batchImportPurchaseOrder.getProductCode());
                productModelBuilder.setProjectId(Long.parseLong(projectId));
                ProductStructure.GetByProductModelReq getByProductModelReq = productModelBuilder.build();
                //5.4.2 构建返回值
                ProductStructure.GetByProductModelResp getByProductModelResp = null;
                //5.4.3 构建存根
                ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub
                        = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                //5.4.4 调用
                try {
                    getByProductModelResp = productServiceBlockingStub.getByProductModel(getByProductModelReq);
                } catch (Exception e) {
                    e.printStackTrace();
                    buildReturn(rpcResultBuilder, respBuilder, response, responseObserver,
                            ErrorCode.NOT_PRODUCT_CODE.getErrorCode(), batchImportPurchaseOrder.getProductCode()+ErrorCode.NOT_PRODUCT_CODE.getMessage());
                    return;
                }
                //****************************************************************
                ProductStructure.ProductBusiness productBusiness = getByProductModelResp.getProductBusiness();
                if (productBusiness == null || "".equals(productBusiness.getProductModel())) {
                    buildReturn(rpcResultBuilder, respBuilder, response, responseObserver,
                            ErrorCode.NOT_PRODUCT_CODE.getErrorCode(), batchImportPurchaseOrder.getProductCode()+ErrorCode.NOT_PRODUCT_CODE.getMessage());
                    return;
                }
                //订单信息
                purchaseOrderInfo.setContractReferenceOrderNo(batchImportPurchaseOrder.getContractNumber());
                purchaseOrderInfo.setBrandOrderNo(batchImportPurchaseOrder.getSupplierNumber());
                purchaseOrderInfo.setBusinessDate(batchImportPurchaseOrder.getBusinessDate());

                purchaseNumber += batchImportPurchaseOrder.getPurchaseNumber();
                cashAmount += batchImportPurchaseOrder.getCashAmount();
                prepaidAmountTotal += batchImportPurchaseOrder.getPrepaidAmount();
                couponAmountTotal += batchImportPurchaseOrder.getCouponAmount();
                totalAmount += batchImportPurchaseOrder.getTotalAmount();

                if ("INTERNET".equals(batchImportPurchaseOrder.getPurchaseType())) {
                    purchaseOrderInfo.setPurchaseType(PurchaseTypeStatus.GENERAL_PURCHASE.getStatus());
                    purchaseOrderInfo.setSupplierReceipt(SupplierReceiptStatus.GIVE_RECEIPT.getStatus());
                } else {
                    purchaseOrderInfo.setPurchaseType(PurchaseTypeStatus.PRODUCT_ADDITIONAL.getStatus());
                    purchaseOrderInfo.setSupplierReceipt(SupplierReceiptStatus.NOT_RECEIPT.getStatus());
                }
                //5.3 拼装采购单货品信息
                PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
                purchaseOrderItem.setOrderStatus((byte) PurchaseStatus.ORDER_IMPORT.getStatus());
                purchaseOrderItem.setPurchaseOrderNo(purchaseOrderNumber);
                purchaseOrderItem.setProductCode(productBusiness.getProductModel());
                purchaseOrderItem.setProductId(productBusiness.getProductBusinessId() + "");
                purchaseOrderItem.setProductName(productBusiness.getProductName());
                purchaseOrderItem.setProductUnit("GE");
                purchaseOrderItem.setPrepaidAmount(DoubleScale.multipleHundred(-batchImportPurchaseOrder.getPrepaidAmount() * FXConstant.TAX_RATE_SUM));
                purchaseOrderItem.setCouponAmount(DoubleScale.multipleHundred(-batchImportPurchaseOrder.getCouponAmount() * FXConstant.TAX_RATE_SUM));
                purchaseOrderItem.setPurchaseNumber(batchImportPurchaseOrder.getPurchaseNumber());
                purchaseOrderItem.setInTransitQuantity(0);//已计划入库的数量为0
                purchaseOrderItem.setInStockQuantity(0);//已入库的数量为0
                purchaseOrderItem.setCreateTime(date);
                purchaseOrderItem.setLastUpdateTime(date);
                purchaseOrderItem.setTracelog(traceJson);
                purchaseOrderItem.setPriceDiscount(0);

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
                purchaseOrderItem.setTablePrefix(tablePrefix);
                int row = purchaseOrderItemDao.insert(purchaseOrderItem);
            }
            purchaseOrderInfo.setTotalQuantity((int) purchaseNumber);
            //设置各金额
            purchaseOrderInfo.setPrepaidAmountUse(DoubleScale.multipleHundred(-prepaidAmountTotal * FXConstant.TAX_RATE_SUM));//YH代垫
            purchaseOrderInfo.setCouponAmountUse(DoubleScale.multipleHundred(-couponAmountTotal * FXConstant.TAX_RATE_SUM));//YH返利
            purchaseOrderInfo.setCashAmount(DoubleScale.multipleHundred(-cashAmount * FXConstant.TAX_RATE_SUM));//现金返点
            purchaseOrderInfo.setPurchaseActualPayAmount(DoubleScale.multipleHundred(totalAmount * FXConstant.TAX_RATE_SUM));//实付金额
            double shouldPayAmount = (totalAmount - couponAmountTotal - prepaidAmountTotal - cashAmount) * FXConstant.TAX_RATE_SUM * 100;
            purchaseOrderInfo.setPurchaseShouldPayAmount((long) shouldPayAmount);//应付金额
            purchaseOrderInfo.setPurchaseGuideAmount(purchaseOrderInfo.getPurchaseShouldPayAmount());//折扣为0 采购指导金额=采购金额=采购应付金额
            purchaseOrderInfo.setEstimatedCouponTotalAmount(DoubleScale.multipleHundred(purchaseOrderInfo.getPurchaseShouldPayAmount() * couponGenerateRate));//预计返利应收
            purchaseOrderInfo.setTracelog(traceJson);
            purchaseOrderInfo.setCreatedByName(rpcHeader.getUsername());
            purchaseOrderInfo.setEasStatus((byte) PurchaseEasStatus.INIT.getStatus());
            purchaseOrderInfo.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            purchaseOrderInfo.setUnhandledQuantity((int) purchaseNumber);
            purchaseOrderInfo.setTablePrefix(tablePrefix);
            purchaseOrderDao.insert(purchaseOrderInfo);
        }
        logger.info("一共生成{}个采购单", purchaseMap.size());

        buildReturn(rpcResultBuilder, respBuilder, response, responseObserver,
                ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());
        return;
    }
    //共用设置返回值
    private void buildReturn(PurchaseFileStructure.RpcResultPurchaseFile.Builder rpcResultBuilder,
                             PurchaseFileStructure.PurchaseFileResp.Builder respBuilder,
                             PurchaseFileStructure.PurchaseFileResp response,
                             StreamObserver<PurchaseFileStructure.PurchaseFileResp> responseObserver,
                             int errorCode,
                             String errorMessage) {
        rpcResultBuilder.setReturnCode(errorCode);
        rpcResultBuilder.setMsg(errorMessage);
        respBuilder.setRpcResultPurchaseFile(rpcResultBuilder);
        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }
}
