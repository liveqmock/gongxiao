package com.yhglobal.gongxiao.purchase.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure;
import com.yhglobal.gongxiao.purchase.controller.vo.*;
import com.yhglobal.gongxiao.purchase.microservice.PurchaseServiceGrpc;
import com.yhglobal.gongxiao.purchase.microservice.PurchaseStructure;
import com.yhglobal.gongxiao.util.*;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.yhglobal.gongxiao.grpc.client.RpcStubStore.*;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Controller
@RequestMapping("/purchase/order")
public class PurchaseOrderController {

    private static Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    /**
     * 预约入库
     *
     * @param purchaseOrderNo         采购单号
     * @param warehouseId             仓库ID
     * @param warehouseName           仓库名
     * @param requireInboundDate      要求到货日期
     * @param totalQuantity           采购总量
     * @param planInstockNumberTotal  计划入库数量总量
     * @param planInboundItemListJson 货品信息列表的json
     * @return
     */
    @RequestMapping(value = "/planInbound", method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult planInboundOrder(
            String purchaseOrderNo,
            String warehouseId,
            String warehouseName,
            String requireInboundDate,
            int totalQuantity,
            int planInstockNumberTotal,
            String planInboundItemListJson,
            HttpServletRequest request) throws Exception {

        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

        try {
            long projectId = portalUserInfo.getProjectId();
            String format = "yyyy-MM-dd";
            logger.info("[#traceId={}# [IN][planInbound] params: projectId:{}, purchaseOrderNo:{}, warehouseId:{}, warehouseName:{}, requireInboundDate:{}, " +
                            "totalQuantity:{}, planInstockNumberTotal:{}, planInboundItemListJson:{} ", rpcHeader.getTraceId(), projectId, purchaseOrderNo, warehouseId, warehouseName, requireInboundDate, totalQuantity,
                    planInstockNumberTotal);
            if (planInstockNumberTotal == 0) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PLAN_NUMMBER_NOT_NULL);
            }
            long requireInbDate = 0;
            if (requireInboundDate != null && !"".equals(requireInboundDate)) {
                requireInbDate = CommonUtil.StringToDateWithFormat(requireInboundDate, format).getTime();
            }
            PurchaseStructure.PlanInboundBasicInfo planInboundBasicInfo = PurchaseStructure.PlanInboundBasicInfo.newBuilder()
                    .setProjectId(projectId + "")
                    .setPurchaseOrderNo(purchaseOrderNo)
                    .setWarehouseId(warehouseId)
                    .setWarehouseName(warehouseName)
                    .setTotalQuantity(totalQuantity)
                    .setPlanInstockNumberTotal(planInstockNumberTotal)
                    .setRequireInboundDate(requireInbDate).build();

            ArrayList<PlanInboundItem> purchaseItemInfoList
                    = JSON.parseObject(planInboundItemListJson, new TypeReference<ArrayList<PlanInboundItem>>() {
            });

            PurchaseStructure.PlanInboundReq.Builder planInboundReq = PurchaseStructure.PlanInboundReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setPlanInboundBasicInfo(planInboundBasicInfo);
            for (PlanInboundItem planInboundItem : purchaseItemInfoList) {
                int planInstockNumber = planInboundItem.getPlanInstockNumber();
                int needInstockNumber = planInboundItem.getNeedInstockNumber();
                if (planInstockNumber > needInstockNumber) {
                    return new GongxiaoResult(ErrorCode.INBOUND_EXCEPTION.getErrorCode(), ErrorCode.INBOUND_EXCEPTION.getMessage());
                }
                PurchaseStructure.PlanInboundItem planInboundItem1 = PurchaseStructure.PlanInboundItem.newBuilder()
                        .setPurchaseItemId(planInboundItem.getPurchaseItemId())
                        .setBrandId(planInboundItem.getBrandId())
                        .setBrandName(planInboundItem.getBrandName())
                        .setProductId(planInboundItem.getProductId())
                        .setProductCode(planInboundItem.getProductCode())
                        .setProductName(planInboundItem.getProductName())
                        .setProductUnit(planInboundItem.getProductUnit())
                        .setCurrencyCode(planInboundItem.getCurrencyCode())
                        .setCurrencyName(planInboundItem.getCurrencyName())
                        .setPurchaseNumber(planInboundItem.getPurchaseNumber())
                        .setNeedInstockNumber(planInboundItem.getNeedInstockNumber())
                        .setPlanInstockNumber(planInboundItem.getPlanInstockNumber())
                        .setGuidePrice(planInboundItem.getGuidePrice())
                        .setCostValue(planInboundItem.getCostValue())
                        .setRemark(planInboundItem.getRemark())
                        .setRequireInboundDate(planInboundItem.getRequireInboundDate())
                        .build();
                planInboundReq.addPlanInboundItemList(planInboundItem1);
            }
            PurchaseServiceGrpc.PurchaseServiceBlockingStub rpcStub = getRpcStub(PurchaseServiceGrpc.PurchaseServiceBlockingStub.class);
            PurchaseStructure.PlanInboundResp resp = null;
            try {
                resp = rpcStub.planInbound(planInboundReq.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
            GongxiaoRpc.RpcResult rpcResult = resp.getRpcResult();
            if (rpcResult.getReturnCode() != 0) {
                return GongxiaoResultUtil.createGongxiaoErrorResultByCode(rpcResult.getReturnCode());
            }
            logger.info("#traceId={}# [OUT] planInboundOrder success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), 1);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/calculatePurchaseType")
    public GongxiaoResult calculatePurchaseType(byte purchaseType,
                                                String productJson,
                                             HttpServletRequest request){
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        CalculateOrderInfo calculateOrderInfo = new CalculateOrderInfo();
        try {
            logger.info("#traceId={}# [IN][calculatePurchaseType] params: projectId:{}, productJson:{} ",
                    rpcHeader.getTraceId(),productJson );
            List<ProductItem> productItems = JSON.parseObject(productJson, new TypeReference<List<ProductItem>>() {
            });
            List<CalculateProductInfo> calculateProductInfoList = new ArrayList<>();

            ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);

            double purchaseGuideAmount = 0;
            double prepaidAmountTotal = 0;
            double couponAmountTotal = 0;
            for (ProductItem productItem : productItems){
                CalculateProductInfo calculateProductInfo = new CalculateProductInfo();
                calculateProductInfo.setProductId(productItem.getProductId());
                calculateProductInfo.setProductCode(productItem.getProductCode());
                calculateProductInfo.setProductName(productItem.getProductName());
                calculateProductInfo.setGuidePrice(CommonUtil.doubleToString(productItem.getGuidePrice(),"0.000000"));
                calculateProductInfo.setPurchaseNumber(productItem.getPurchaseNumber());
                calculateProductInfo.setDiscountPercent(CommonUtil.doubleToString(productItem.getDiscountPercent(),"0.000000"));
                calculateProductInfo.setCouponAmount(CommonUtil.doubleToString(productItem.getCouponAmount(),"0.00"));
                calculateProductInfo.setPrepaidAmount(CommonUtil.doubleToString(productItem.getPrepaidAmount(),"0.00"));
                calculateProductInfo.setPurchasePrice(CommonUtil.doubleToString(productItem.getGuidePrice(),"0.000000"));
                calculateProductInfo.setCostPrice(CommonUtil.doubleToString(productItem.getGuidePrice(),"0.000000"));

                double guidePrice = productItem.getGuidePrice();
                int purchaseNumber = productItem.getPurchaseNumber();
                double couponAmount = productItem.getCouponAmount();
                double prepaidAmount = productItem.getPrepaidAmount();
                if (purchaseType == 1){
                    double factoryPrice = guidePrice - couponAmount - prepaidAmount;
                    calculateProductInfo.setFactoryPrice(CommonUtil.doubleToString(factoryPrice,"0.000000"));
                }else {
                    calculateProductInfo.setFactoryPrice(CommonUtil.doubleToString(guidePrice,"0.000000"));
                }
                calculateProductInfoList.add(calculateProductInfo);

                purchaseGuideAmount += (guidePrice * purchaseNumber);
                prepaidAmountTotal += prepaidAmount;
                couponAmountTotal += couponAmount;

            }

            calculateOrderInfo.setPurchaseGuideAmount(CommonUtil.doubleToString(purchaseGuideAmount, "0.00"));
            //采购实付金额
            double purchaseActualPayAmount = purchaseGuideAmount-couponAmountTotal-prepaidAmountTotal;
            calculateOrderInfo.setPurchaseActualPayAmount(CommonUtil.doubleToString(purchaseActualPayAmount, "0.00"));

            calculateOrderInfo.setCalculateProductInfoList(calculateProductInfoList);

            logger.info("#traceId={}# [OUT][calculatePurchaseType] params: projectId:{}, productJson:{} ",
                    rpcHeader.getTraceId(),productJson );
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), calculateOrderInfo);

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    /**
     * 计算货品的各价格
     *
     * @param couponAmountUse     订单返利使用额
     * @param prepaidAmountUse    订单代垫使用额
     * @param productJson         货品信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/calculateProductInfo", method = RequestMethod.POST)
    public GongxiaoResult calculateProduct(
            double couponAmountUse,
            double prepaidAmountUse,
            String productJson,
            HttpServletRequest request) throws Exception {
        long projectId = portalUserInfo.getProjectId();

        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            logger.info("#traceId={}# [IN][getBrandList] params: projectId:{}, brandId:{}, brandName:{},  " +
                            "  couponAmountUse:{}, prepaidAmountUse:{} ",
                    rpcHeader.getTraceId(), projectId,  couponAmountUse, prepaidAmountUse);
            CalculateOrderInfo calculateOrderInfo = new CalculateOrderInfo();
            List<CalculateProductInfo> calculateProductInfoList = new ArrayList<>();
            calculateOrderInfo.setCalculateProductInfoList(calculateProductInfoList);

            List<ProductItem> productItems = JSON.parseObject(productJson, new TypeReference<List<ProductItem>>() {
            });
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            SupplierAccountServiceStructure.GetSupplierAccountAmountRequest getSupplierAccountAmountRequest = SupplierAccountServiceStructure.GetSupplierAccountAmountRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode("CNY")
                    .build();
            PaymentCommonGrpc.AccountAmountResponse accountAmount = rpcStub.getSupplierAccountAmount(getSupplierAccountAmountRequest);

            double accountCouponAmount = accountAmount.getCouponAmount() / FXConstant.HUNDRED;
            double accountPrepaidAmount = accountAmount.getPrepaidAmount() / FXConstant.HUNDRED;
//            double accountCouponAmount = 5000;
//            double accountPrepaidAmount = 2000;

            double totalAmount = 0;
            for (ProductItem item : productItems) {
                double guidePrice = item.getGuidePrice();
                int purchaseNumber = item.getPurchaseNumber();
                totalAmount += (guidePrice * purchaseNumber );
            }
            //确定实际使用返利总额与实际使用代垫总额
            double actualCouponAmount;
            double actualPrepaidAmount;
            actualCouponAmount = couponAmountUse > accountCouponAmount ? accountCouponAmount : couponAmountUse;
            actualPrepaidAmount = prepaidAmountUse > accountPrepaidAmount ? accountPrepaidAmount : prepaidAmountUse;
            //设置剩余返利与代垫
            accountCouponAmount = accountCouponAmount - actualCouponAmount;
            accountPrepaidAmount = accountPrepaidAmount - actualPrepaidAmount;
            //确定yh使用返利,代垫的总额/AD使用返利,代垫的总额
            double yhCouponAmountTotal = 0;
            double yhPrepaidAmountTotal = 0;

            //2)拼装其他数据
            double purchaseGuideAmount = 0;//采购指导金额
            double purchasePrivilegeAmount = 0;//采购优惠金额
            double returnCash = 0;              //现金返点金额
            double purchaseShouldPayAmount = 0; //采购应付金额
            double purchaseActualPayAmount = 0; //采购实付金额

            //设置单位返利
            for (ProductItem item : productItems) {
                double guidePrice = item.getGuidePrice();
                double purchaseDiscount = item.getDiscountPercent();
                int purchaseNumber = item.getPurchaseNumber();
                //加权平均算返利代垫
                double couponAmountItem = guidePrice * purchaseNumber / totalAmount * actualCouponAmount;
                double prepaidAmountItem = guidePrice * purchaseNumber / totalAmount * actualPrepaidAmount;
                CalculateProductInfo calculateProductInfo = new CalculateProductInfo();
                calculateProductInfo.setProductId(item.getProductId());
                calculateProductInfo.setProductCode(item.getProductCode());
                calculateProductInfo.setProductName(item.getProductName());
                calculateProductInfo.setGuidePrice(CommonUtil.doubleToString(item.getGuidePrice(), "0.000000"));
                calculateProductInfo.setPurchaseNumber(item.getPurchaseNumber());
                calculateProductInfo.setCouponAmount(CommonUtil.doubleToString(couponAmountItem, "0.00"));
                calculateProductInfo.setPrepaidAmount(CommonUtil.doubleToString(prepaidAmountItem, "0.00"));

                purchaseGuideAmount += guidePrice * purchaseNumber; //采购指导金额
                purchasePrivilegeAmount += guidePrice * purchaseDiscount; //采购优惠金额
                purchaseShouldPayAmount += guidePrice * (1 - purchaseDiscount) * purchaseNumber;//采购应付金额

                //设置采购价格
                double purchasePrice = guidePrice  ;
                calculateProductInfo.setPurchasePrice(CommonUtil.doubleToString(purchasePrice, "0.000000"));
                //设置成本价
                double costValue = guidePrice ;
                calculateProductInfo.setCostPrice(CommonUtil.doubleToString(costValue, "0.000000"));
                //设置进货价
                double factoryPrice = guidePrice - couponAmountItem / purchaseNumber - prepaidAmountItem / purchaseNumber;
                calculateProductInfo.setFactoryPrice(CommonUtil.doubleToString(factoryPrice, "0.000000"));
                System.out.println(calculateProductInfo.toString());
                calculateProductInfoList.add(calculateProductInfo);
            }
            //设置返利余额
            calculateOrderInfo.setCouponRemainMoney(CommonUtil.doubleToString(accountCouponAmount, "0.00"));
            //设置代垫余额
            calculateOrderInfo.setPrepaidRemainMoney(CommonUtil.doubleToString(accountPrepaidAmount, "0.00"));
            //设置YH返利使用金额
            calculateOrderInfo.setCouponAmount(CommonUtil.doubleToString(yhCouponAmountTotal, "0.00"));
            //设置YH代垫使用金额
            calculateOrderInfo.setPrepaidAmount(CommonUtil.doubleToString(yhPrepaidAmountTotal, "0.00"));
            //设置采购指导金额
            calculateOrderInfo.setPurchaseGuideAmount(CommonUtil.doubleToString(purchaseGuideAmount, "0.00"));
            //采购实付金额
            purchaseActualPayAmount = purchaseGuideAmount-couponAmountUse-prepaidAmountUse;
            calculateOrderInfo.setPurchaseActualPayAmount(CommonUtil.doubleToString(purchaseActualPayAmount, "0.00"));
            calculateOrderInfo.setCalculateProductInfoList(calculateProductInfoList);
            logger.info("#traceId={}# [OUT] calculateProductInfo success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), calculateOrderInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/calculateEditProductInfo", method = RequestMethod.POST)
    public GongxiaoResult calculateEditProductInfo(
            double couponAmountUse, //YH返利使用金额
            double prepaidAmountUse,//YH代垫使用金额
            double adCouponAmountUse,//AD返利使用金额
            double adPrepaidAmountUse,//AD代垫使用金额
            double purchaseShouldPayAmount,//采购应付金额
            int status, //status=1 以YH为主  status=2 表示以AD为主
            String productJson,
            HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();
            logger.info("#traceId={}# [IN][getBrandList] params: projectId:{}, brandId:{}, brandName:{}, cashRate:{}, couponUseReferRate:{}, prepaidUseReferRate:{}, monthlyCouponGenerateRate:{}, " +
                            "quarterlyCouponGenerateRate:{}, annualCouponGenerateRate:{}, couponAmountUse:{}, prepaidAmountUse:{} ",
                    rpcHeader.getTraceId(), projectId, couponAmountUse, prepaidAmountUse);
            CalculateOrderInfo calculateOrderInfo = new CalculateOrderInfo();
            List<CalculateProductInfo> calculateProductInfoList = new ArrayList<>();
            calculateOrderInfo.setCalculateProductInfoList(calculateProductInfoList);

            List<ProductItem> productItems = JSON.parseObject(productJson, new TypeReference<List<ProductItem>>() {
            });
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            SupplierAccountServiceStructure.GetSupplierAccountAmountRequest getSupplierAccountAmountRequest = SupplierAccountServiceStructure.GetSupplierAccountAmountRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode("CNY")
                    .build();
            PaymentCommonGrpc.AccountAmountResponse accountAmount = rpcStub.getSupplierAccountAmount(getSupplierAccountAmountRequest);
            double accountCouponAmount = accountAmount.getCouponAmount() / FXConstant.HUNDRED;
            double accountPrepaidAmount = accountAmount.getPrepaidAmount() / FXConstant.HUNDRED;

            double couponTotal = 0;//订单使用返利
            double prepaidTotal = 0;//订单使用代垫
            //获取订单使用的返利,代垫总额
            for (ProductItem item : productItems) {
                couponTotal += item.getCouponAmount();
                prepaidTotal += item.getPrepaidAmount();
            }
            //校验账户剩余的返利代垫余额是否充足
            if (couponTotal >= accountCouponAmount) {
                return new GongxiaoResult(ErrorCode.SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
            }
            if (prepaidTotal >= accountPrepaidAmount) {
                return new GongxiaoResult(ErrorCode.SUPPLIER_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.SUPPLIER_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
            }
            accountCouponAmount = accountCouponAmount - couponTotal;
            accountPrepaidAmount = accountPrepaidAmount - prepaidTotal;
            //设置YH/AD 返利/代垫使用情况
            if (status == 1) {
                adCouponAmountUse = couponTotal - couponAmountUse;
                adPrepaidAmountUse = prepaidTotal - prepaidAmountUse;
            }
            if (status == 2) {
                couponAmountUse = couponTotal - adCouponAmountUse;
                prepaidAmountUse = prepaidTotal - adPrepaidAmountUse;
            }
            double totalGuidePrice = 0;
            double totalPrivilegeAmount = 0;
            for (ProductItem item : productItems) {

                double purchaseDiscount = item.getDiscountPercent();
                double guidePrice = item.getPurchasePrice() * (1 + purchaseDiscount);
                totalGuidePrice += (guidePrice * item.getPurchaseNumber());
                totalPrivilegeAmount += (guidePrice * purchaseDiscount * item.getPurchaseNumber());
                CalculateProductInfo calculateProductInfo = new CalculateProductInfo();
                calculateProductInfo.setProductId(item.getProductId());
                calculateProductInfo.setProductCode(item.getProductCode());
                calculateProductInfo.setProductName(item.getProductName());
                calculateProductInfo.setGuidePrice(CommonUtil.doubleToString(guidePrice, "0.000000"));
                calculateProductInfo.setPurchaseNumber(item.getPurchaseNumber());
                calculateProductInfo.setDiscountPercent(CommonUtil.doubleToString(item.getDiscountPercent(), "0.000000"));
                calculateProductInfo.setCouponAmount(CommonUtil.doubleToString(item.getCouponAmount(), "0.00"));
                calculateProductInfo.setPrepaidAmount(CommonUtil.doubleToString(item.getPrepaidAmount(), "0.00"));
                calculateProductInfo.setPurchasePrice(CommonUtil.doubleToString(item.getPurchasePrice(), "0.000000"));
                calculateProductInfo.setCostPrice(CommonUtil.doubleToString(item.getCostPrice(), "0.000000"));
                calculateProductInfo.setFactoryPrice(CommonUtil.doubleToString(item.getFactoryPrice(), "0.000000"));
                calculateProductInfoList.add(calculateProductInfo);
            }
            calculateOrderInfo.setCalculateProductInfoList(calculateProductInfoList);
            //设置返利余额
            calculateOrderInfo.setCouponRemainMoney(CommonUtil.doubleToString(accountCouponAmount, "0.00"));
            //设置代垫余额
            calculateOrderInfo.setPrepaidRemainMoney(CommonUtil.doubleToString(accountPrepaidAmount, "0.00"));
            //设置YH返利使用金额
            calculateOrderInfo.setCouponAmount(CommonUtil.doubleToString(couponAmountUse, "0.00"));
            //设置YH代垫使用金额
            calculateOrderInfo.setPrepaidAmount(CommonUtil.doubleToString(prepaidAmountUse, "0.00"));
            //设置AD返利使用金额
            calculateOrderInfo.setAdCouponAmount(CommonUtil.doubleToString(adCouponAmountUse, "0.00"));
            //设置AD代垫使用金额
            calculateOrderInfo.setAdPrepaidAmount(CommonUtil.doubleToString(adPrepaidAmountUse, "0.00"));
            //设置采购指导金额
            calculateOrderInfo.setPurchaseGuideAmount(CommonUtil.doubleToString(totalGuidePrice, "0.00"));
            //采购应付金额
            calculateOrderInfo.setPurchaseShouldPayAmount(CommonUtil.doubleToString(purchaseShouldPayAmount, "0.00"));
            //采购优惠金额
            calculateOrderInfo.setPurchasePrivilegeAmount(CommonUtil.doubleToString(totalPrivilegeAmount, "0.00"));
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), calculateOrderInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }


    /**
     * 通过采购单号获取计划收货单列表
     *
     * @param purchaseNumber 采购单号
     * @return
     */
    @RequestMapping(value = "/getInboundList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getInboundList(String purchaseNumber,
                                         String productCode,
                                         HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        if ("".equals(purchaseNumber)) {
            return new GongxiaoResult(205, "采购单号为空");
        }
        try {
            long projectId = portalUserInfo.getProjectId();
            logger.info("#traceId={}# [IN][getInboundList] params: purchaseNumber:{}, productCode ", purchaseNumber, productCode);
            PurchaseServiceGrpc.PurchaseServiceBlockingStub rpcStub = getRpcStub(PurchaseServiceGrpc.PurchaseServiceBlockingStub.class);
            PurchaseStructure.GetInboundItemListReq getInboundItemListReq = PurchaseStructure.GetInboundItemListReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setPurchaseNo(purchaseNumber)
                    .setProductCode(productCode)
                    .build();
            PurchaseStructure.GetInboundItemListResp resp = rpcStub.getInboundItemList(getInboundItemListReq);
            List<PurchaseStructure.PlanInboundItem> planInboundListList = resp.getPlanInboundListList();
            List<PlanInboundItem> planInboundItemList = new ArrayList<>();
            for (PurchaseStructure.PlanInboundItem planInboundItem : planInboundListList) {
                PlanInboundItem planInboundItem1 = new PlanInboundItem();
                planInboundItem1.setPurchaseItemId(planInboundItem.getPurchaseItemId());
                planInboundItem1.setBrandId(planInboundItem.getBrandId());
                planInboundItem1.setBrandName(planInboundItem.getBrandName());
                planInboundItem1.setProductId(planInboundItem.getProductId());
                planInboundItem1.setProductCode(planInboundItem.getProductCode());
                planInboundItem1.setProductName(planInboundItem.getProductName());
                planInboundItem1.setProductUnit(planInboundItem.getProductUnit());
                planInboundItem1.setCurrencyCode(planInboundItem.getCurrencyCode());
                planInboundItem1.setCurrencyName(planInboundItem.getCurrencyName());
                planInboundItem1.setPurchaseNumber(planInboundItem.getPurchaseNumber());
                planInboundItem1.setNeedInstockNumber(planInboundItem.getNeedInstockNumber());
                planInboundItem1.setPlanInstockNumber(planInboundItem.getPlanInstockNumber());
                planInboundItem1.setGuidePrice(planInboundItem.getGuidePrice());
                planInboundItem1.setCostValue(planInboundItem.getCostValue());
                planInboundItem1.setRemark(planInboundItem.getRemark());
                planInboundItem1.setRequireInboundDate(planInboundItem.getRequireInboundDate());
                planInboundItemList.add(planInboundItem1);
            }
            logger.info("#planInboundListList={}# [OUT] getInboundList success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), planInboundItemList);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 获取采购单列表
     *
     * @param purchaseOrderNo    采购单号
     * @param brandId            品牌商ID
     * @param warehouseId        仓库ID
     * @param productCode        产品编码
     * @param requireArrivalDate 要求到货时间
     * @param arrivalDeadline    到货截止时间
     * @param businessDate       业务发生时间
     * @return
     */
    @RequestMapping(value = "/getPurchaseList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getPurchaseList(String purchaseOrderNo,
                                          String brandId,
                                          String warehouseId,
                                          String productCode,
                                          String requireArrivalDate,
                                          String arrivalDeadline,
                                          String businessDate,
                                          int pageNumber,
                                          int pageSize,
                                          HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();

            logger.info("#traceId={}# [IN][getBrandList] params: proje ctId:{}, purchaseOrderNo{}, brandId{}, warehouseId{}, productCode{}, requireArrivalDate{}, arrivalDeadline{}, businessDate{} ",
                    rpcHeader.getTraceId(), projectId, purchaseOrderNo, brandId, warehouseId, productCode, requireArrivalDate, arrivalDeadline, businessDate);

            PurchaseServiceGrpc.PurchaseServiceBlockingStub rpcStub = getRpcStub(PurchaseServiceGrpc.PurchaseServiceBlockingStub.class);
            PurchaseStructure.GetPurchaseOrderListReq getPurchaseOrderListReq = PurchaseStructure.GetPurchaseOrderListReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId + "")
                    .setPurchaseOrderNo(purchaseOrderNo)
                    .setWarehouseId(warehouseId == null || "".equals(warehouseId) ? "" : warehouseId)
                    .setProductCode(productCode == null || "".equals(productCode) ? "" : productCode)
                    .setRequireArrivalDate(requireArrivalDate == null || "".equals(requireArrivalDate) ? "" : requireArrivalDate)
                    .setArrivalDeadline(arrivalDeadline == null || "".equals(arrivalDeadline) ? "" : arrivalDeadline)
                    .setBusinessDate(businessDate == null || "".equals(businessDate) ? "" : businessDate)
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            PurchaseStructure.GetPurchaseOrderListResp resp = rpcStub.getPurchaseOrderList(getPurchaseOrderListReq);
            PurchaseStructure.PageInfoPurchaseOrderList pageInfoPurchaseOrderList = resp.getPageInfoPurchaseOrderList();

            PageInfo<PurchaseOrderInfo> purchaseOrderInfoPageInfo = new PageInfo<>();
            List<PurchaseOrderInfo> purchaseOrderInfoList = new ArrayList<>();

            for (PurchaseStructure.PurchaseOrderInfo purchaseOrderInfo : pageInfoPurchaseOrderList.getPurchaseOrderlistList()) {
                PurchaseOrderInfo purchaseOrderInfo1 = new PurchaseOrderInfo();
                purchaseOrderInfo1.setPurchaseOrderNumber(purchaseOrderInfo.getPurchaseOrderNumber());
                purchaseOrderInfo1.setCreatePerson(purchaseOrderInfo.getCreatePerson());
                purchaseOrderInfo1.setBrandId(purchaseOrderInfo.getBrandId());
                purchaseOrderInfo1.setBrandName(purchaseOrderInfo.getBrandName());
                purchaseOrderInfo1.setSupplierId(purchaseOrderInfo.getSupplierId());
                purchaseOrderInfo1.setSupplierName(purchaseOrderInfo.getSupplierName());
                purchaseOrderInfo1.setOrderAmount(purchaseOrderInfo.getOrderAmount());
                purchaseOrderInfo1.setPurchaseCategory(purchaseOrderInfo.getPurchaseCategory());
                purchaseOrderInfo1.setPurchaseNumber(purchaseOrderInfo.getPurchaseNumber());
                purchaseOrderInfo1.setPaymentMode(purchaseOrderInfo.getPaymentMode());
                purchaseOrderInfo1.setWarehouseId(purchaseOrderInfo.getWarehouseId());
                purchaseOrderInfo1.setWarehouseName(purchaseOrderInfo.getWarehouseName());
                purchaseOrderInfo1.setPurchaseStatus(purchaseOrderInfo.getPurchaseStatus());
                purchaseOrderInfo1.setPurchaseStatusInt(purchaseOrderInfo.getPurchaseStatusInt());
                purchaseOrderInfo1.setRequirArrivalDate(DateUtil.long2Date(purchaseOrderInfo.getRequirArrivalDate()));
                purchaseOrderInfo1.setBusinessDate(purchaseOrderInfo.getBusinessDate());
                purchaseOrderInfo1.setSupplierOrderNo(purchaseOrderInfo.getSupplierOrderNo());
                purchaseOrderInfo1.setContractReferenceOrderNo(purchaseOrderInfo.getContractReferenceOrderNo());
                purchaseOrderInfo1.setEnableEdit(purchaseOrderInfo.getEnableEdit());
                purchaseOrderInfo1.setEnableCancel(purchaseOrderInfo.getEnableCancel());
                purchaseOrderInfo1.setEnableDetail(purchaseOrderInfo.getEnableDetail());
                purchaseOrderInfo1.setEnablePlanInStock(purchaseOrderInfo.getEnablePlanInStock());
                purchaseOrderInfoList.add(purchaseOrderInfo1);
            }
            purchaseOrderInfoPageInfo.setTotal(pageInfoPurchaseOrderList.getTotal());
            purchaseOrderInfoPageInfo.setList(purchaseOrderInfoList);

            logger.info("#traceId={}# [OUT] getPurchaseList success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), purchaseOrderInfoPageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 通过采购单号获取采购详情
     *
     * @param purchaseOrderNo 采购单号
     * @return
     */
    @RequestMapping(value = "/getPurchaseDetail", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getPurchaseDetail(String purchaseOrderNo,
                                            HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();
            logger.info("#traceId={}# [IN][getPurchaseDetail] params: purchaseOrderNo={}", rpcHeader.getTraceId(), purchaseOrderNo);
            PurchaseServiceGrpc.PurchaseServiceBlockingStub rpcStub = getRpcStub(PurchaseServiceGrpc.PurchaseServiceBlockingStub.class);
            PurchaseStructure.GetPurchaseDetailReq getPurchaseDetailReq = PurchaseStructure.GetPurchaseDetailReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setPurchaseOrderNo(purchaseOrderNo)
                    .build();
            PurchaseStructure.GetPurchaseDetailResp resp = rpcStub.getPurchaseDetail(getPurchaseDetailReq);
            PurchaseStructure.PurchaseOrderDetailVo purchaseDetail = resp.getPurchaseOrderDetailVo();

            PurchaseOrderDetailVo purchaseOrderDetailVo = new PurchaseOrderDetailVo();
            purchaseOrderDetailVo.setPurchaseOrderId(purchaseDetail.getPurchaseOrderId());
            purchaseOrderDetailVo.setOrderStatus((byte) purchaseDetail.getOrderStatus());
            purchaseOrderDetailVo.setPurchaseOrderNo(purchaseDetail.getPurchaseOrderNo());
            purchaseOrderDetailVo.setProjectId(purchaseDetail.getProjectId());
            purchaseOrderDetailVo.setProjectName(purchaseDetail.getProjectName());
            purchaseOrderDetailVo.setBrandId(purchaseDetail.getBrandId());
            purchaseOrderDetailVo.setBrandName(purchaseDetail.getBrandName());
            purchaseOrderDetailVo.setSupplierId(purchaseDetail.getSupplierId());
            purchaseOrderDetailVo.setSupplierName(purchaseDetail.getSupplierName());
            purchaseOrderDetailVo.setPaymentMode((byte) purchaseDetail.getPaymentMode());
            purchaseOrderDetailVo.setShippingMode((byte) purchaseDetail.getShippingMode());
            purchaseOrderDetailVo.setBusinessDate(DateUtil.long2Date(purchaseDetail.getBusinessDate()));
            purchaseOrderDetailVo.setExpectedInboundDate(DateUtil.long2Date(purchaseDetail.getExpectedInboundDate()));
            purchaseOrderDetailVo.setOrderDeadlineDate(DateUtil.long2Date(purchaseDetail.getOrderDeadlineDate()));
            purchaseOrderDetailVo.setWarehouseId(purchaseDetail.getWarehouseId());
            purchaseOrderDetailVo.setWarehouseName(purchaseDetail.getWarehouseName());
            purchaseOrderDetailVo.setAddress(purchaseDetail.getAddress());
            purchaseOrderDetailVo.setBrandOrderNo(purchaseDetail.getBrandOrderNo());
            purchaseOrderDetailVo.setContractReferenceOrderNo(purchaseDetail.getContractReferenceOrderNo());
            purchaseOrderDetailVo.setRemark(purchaseDetail.getRemark());
            purchaseOrderDetailVo.setPurchaseCategory(purchaseDetail.getPurchaseCategory());
            purchaseOrderDetailVo.setTotalQuantity(purchaseDetail.getTotalQuantity());
            purchaseOrderDetailVo.setCouponAmountUse(purchaseDetail.getCouponAmountUse());
            purchaseOrderDetailVo.setPrepaidAmountUse(purchaseDetail.getPrepaidAmountUse());
            purchaseOrderDetailVo.setPurchaseGuideAmount(purchaseDetail.getPurchaseGuideAmount());
            purchaseOrderDetailVo.setPurchasePrivilegeAmount(purchaseDetail.getPurchasePrivilegeAmount());
            purchaseOrderDetailVo.setReturnCash(purchaseDetail.getReturnCash());
            purchaseOrderDetailVo.setPurchaseShouldPayAmount(purchaseDetail.getPurchaseShouldPayAmount());
            purchaseOrderDetailVo.setPurchaseActualPayAmount(purchaseDetail.getPurchaseActualPayAmount());
            purchaseOrderDetailVo.setPurchaseType(purchaseDetail.getPurchaseType());
            int supplierReceipt = purchaseDetail.getSupplierReceipt();
            if (1 == supplierReceipt ){
                purchaseOrderDetailVo.setSupplierReceipt("不开票");
            }else if (2 == supplierReceipt){
                purchaseOrderDetailVo.setSupplierReceipt("开票");
            }
            List<OperateHistoryRecord> traceLogList = new ArrayList<>();
            List<PurchaseStructure.OperateHistoryRecord> traceLogListList = purchaseDetail.getTraceLogListList();
            for (PurchaseStructure.OperateHistoryRecord operateHistoryRecord : traceLogListList) {
                OperateHistoryRecord operateHistoryRecord1 = new OperateHistoryRecord();
                operateHistoryRecord1.setOperateStatus(operateHistoryRecord.getOperateStatus());
                operateHistoryRecord1.setOperateFunction(operateHistoryRecord.getOperateFunction());
                operateHistoryRecord1.setOperateTime(DateUtil.long2Date(operateHistoryRecord.getOperateTime()));
                operateHistoryRecord1.setOperateTimeString(operateHistoryRecord.getOperateTimestring());
                operateHistoryRecord1.setOperateId(operateHistoryRecord.getOperateId());
                operateHistoryRecord1.setOperateName(operateHistoryRecord.getOperateName());
                traceLogList.add(operateHistoryRecord1);
            }
            purchaseOrderDetailVo.setTraceLogList(traceLogList);

            List<PurchaseItemVo> itemList = new ArrayList<>();
            List<PurchaseStructure.PurchaseItemVo> itemListList = purchaseDetail.getItemListList();
            for (PurchaseStructure.PurchaseItemVo purchaseItemVo : itemListList) {
                PurchaseItemVo purchaseItemVo1 = new PurchaseItemVo();
                purchaseItemVo1.setPurchaseItemId(purchaseItemVo.getPurchaseItemId());
                purchaseItemVo1.setOrderStatus((byte) purchaseItemVo.getOrderStatus());
                purchaseItemVo1.setPurchaseOrderNo(purchaseItemVo.getPurchaseOrderNo());
                purchaseItemVo1.setProductId(purchaseItemVo.getProductId());
                purchaseItemVo1.setProductCode(purchaseItemVo.getProductCode());
                purchaseItemVo1.setProductName(purchaseItemVo.getProductName());
                purchaseItemVo1.setProductUnit(purchaseItemVo.getProductUnit());
                purchaseItemVo1.setWarehouseId(purchaseItemVo.getWarehouseId());
                purchaseItemVo1.setWarehouseName(purchaseItemVo.getWarehouseName());
                purchaseItemVo1.setShippingMode((byte) purchaseItemVo.getShippingMode());
                purchaseItemVo1.setDiscountPercent(purchaseItemVo.getDiscountPercent());
                purchaseItemVo1.setCouponAmount(purchaseItemVo.getCouponAmount());
                purchaseItemVo1.setPurchaseNumber(purchaseItemVo.getPurchaseNumber());
                purchaseItemVo1.setPrepaidAmount(purchaseItemVo.getPrepaidAmount());
                purchaseItemVo1.setGuidePrice(purchaseItemVo.getGuidePrice());
                purchaseItemVo1.setPurchasePrice(purchaseItemVo.getPurchasePrice());
                purchaseItemVo1.setFactoryPrice(purchaseItemVo.getFactoryPrice());
                purchaseItemVo1.setCostPrice(purchaseItemVo.getCostPrice());
                purchaseItemVo1.setCouponBasePrice(purchaseItemVo.getCouponBasePrice());
                purchaseItemVo1.setPurchaseNumber(purchaseItemVo1.getPurchaseNumber());
                itemList.add(purchaseItemVo1);
            }
            purchaseOrderDetailVo.setItemList(itemList);
            purchaseOrderDetailVo.setPurchaseCategory(itemList.size());

            System.out.println(JSON.toJSONString(purchaseOrderDetailVo));
            logger.info("#traceId={}# [OUT] getPurchaseDetail success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), purchaseOrderDetailVo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }


    @RequestMapping(value = "/getPurchaseEditDetail", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getPurchaseEditDetail(String purchaseOrderNumber,
                                                HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();
            logger.info("#traceId={}# [IN][getPurchaseEditDetail] params: purchaseOrderNo={}", rpcHeader.getTraceId(), purchaseOrderNumber);
            PurchaseServiceGrpc.PurchaseServiceBlockingStub rpcStub = getRpcStub(PurchaseServiceGrpc.PurchaseServiceBlockingStub.class);
            PurchaseStructure.GetPurchaseEditDetailReq getPurchaseEditDetailReq = PurchaseStructure.GetPurchaseEditDetailReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setPurchaseNo(purchaseOrderNumber)
                    .build();
            PurchaseStructure.GetPurchaseEditDetailResp getPurchaseEditDetailResp = rpcStub.getPurchaseEditDetail(getPurchaseEditDetailReq);
            PurchaseStructure.PurchaseEditDetail purchaseEditDetail = getPurchaseEditDetailResp.getPurchaseEditDetail();
            PurchaseEditDetail purchaseEditDetail1 = new PurchaseEditDetail();
            purchaseEditDetail1.setProjectId(purchaseEditDetail.getProjectId());
            purchaseEditDetail1.setProjectName(purchaseEditDetail.getProjectName());
            purchaseEditDetail1.setSupplierId(purchaseEditDetail.getSupplierId());
            purchaseEditDetail1.setSupplierName(purchaseEditDetail.getSupplierName());
            purchaseEditDetail1.setWarehouseId(purchaseEditDetail.getWarehouseId());
            purchaseEditDetail1.setWarehouseName(purchaseEditDetail.getWarehouseName());
            purchaseEditDetail1.setWarehouseAddress(purchaseEditDetail.getWarehouseAddress());
            purchaseEditDetail1.setBusinessDate(DateUtil.long2Date(purchaseEditDetail.getBusinessDate()));
            purchaseEditDetail1.setPurchaseType(purchaseEditDetail.getPurchaseType());
            purchaseEditDetail1.setExpectedInboundDate(DateUtil.long2Date(purchaseEditDetail.getExpectedInboundDate()));
            purchaseEditDetail1.setOrderDeadlineDate(DateUtil.long2Date(purchaseEditDetail.getOrderDeadlineDate()));
            purchaseEditDetail1.setSupplierOrderNo(purchaseEditDetail.getSupplierOrderNo());
            purchaseEditDetail1.setContractReferenceOrderNo(purchaseEditDetail.getContractReferenceOrderNo());
            purchaseEditDetail1.setAccountCouponAmount(purchaseEditDetail.getAccountCouponAmount());
            purchaseEditDetail1.setAccountPrepaidAmount(purchaseEditDetail.getAccountPrepaidAmount());
            purchaseEditDetail1.setPurchaseCategory(purchaseEditDetail.getPurchaseCategory());
            purchaseEditDetail1.setPurchaseTotalNumber(purchaseEditDetail.getPurchaseTotalNumber());
            purchaseEditDetail1.setCouponAmountUse(purchaseEditDetail.getCouponAmountUse());
            purchaseEditDetail1.setPrepaidAmountUse(purchaseEditDetail.getPrepaidAmountUse());
            purchaseEditDetail1.setAdCouponAmountUse(purchaseEditDetail.getAdCouponAmountUse());
            purchaseEditDetail1.setAdPrepaidAmountUse(purchaseEditDetail.getAdPrepaidAmountUse());
            purchaseEditDetail1.setPurchaseGuideAmount(purchaseEditDetail.getPurchaseGuideAmount());
            purchaseEditDetail1.setPurchaseShouldPayAmount(purchaseEditDetail.getPurchaseShouldPayAmount());
            purchaseEditDetail1.setPurchasePrivilegeAmount(purchaseEditDetail.getPurchasePrivilegeAmount());
            purchaseEditDetail1.setPurchaseActualPayAmount(purchaseEditDetail.getPurchaseActualPayAmount());
            purchaseEditDetail1.setReturnCash(purchaseEditDetail.getReturnCash());
            purchaseEditDetail1.setCashAmountUse(purchaseEditDetail.getCashAmountUse());

            List<PurchaseStructure.PurchaseItemVo> itemListList = purchaseEditDetail.getItemListList();
            List<PurchaseItemVo> purchaseItemVos = new ArrayList<>();
            for (PurchaseStructure.PurchaseItemVo purchaseItemVo : itemListList) {
                PurchaseItemVo purchaseItemVo1 = new PurchaseItemVo();
                purchaseItemVo1.setPurchaseItemId(purchaseItemVo.getPurchaseItemId());
                purchaseItemVo1.setOrderStatus((byte) purchaseItemVo.getOrderStatus());
                purchaseItemVo1.setPurchaseOrderNo(purchaseItemVo.getPurchaseOrderNo());
                purchaseItemVo1.setProductId(purchaseItemVo.getProductId());
                purchaseItemVo1.setProductCode(purchaseItemVo.getProductCode());
                purchaseItemVo1.setProductName(purchaseItemVo.getProductName());
                purchaseItemVo1.setProductUnit(purchaseItemVo.getProductUnit());
                purchaseItemVo1.setWarehouseId(purchaseItemVo.getWarehouseId());
                purchaseItemVo1.setWarehouseName(purchaseItemVo.getWarehouseName());
                purchaseItemVo1.setShippingMode((byte) purchaseItemVo.getShippingMode());
                purchaseItemVo1.setDiscountPercent(purchaseItemVo.getDiscountPercent());
                purchaseItemVo1.setCouponAmount(purchaseItemVo.getCouponAmount());
                purchaseItemVo1.setPrepaidAmount(purchaseItemVo.getPrepaidAmount());
                purchaseItemVo1.setGuidePrice(purchaseItemVo.getGuidePrice());
                purchaseItemVo1.setPurchasePrice(purchaseItemVo.getPurchasePrice());
                purchaseItemVo1.setFactoryPrice(purchaseItemVo.getFactoryPrice());
                purchaseItemVo1.setCostPrice(purchaseItemVo.getCostPrice());
                purchaseItemVo1.setCouponBasePrice(purchaseItemVo.getCouponBasePrice());
                purchaseItemVo1.setPurchaseNumber(purchaseItemVo.getPurchaseNumber());
                purchaseItemVos.add(purchaseItemVo1);
            }

            purchaseEditDetail1.setItemList(purchaseItemVos);

            logger.info("#traceId={}# [OUT] getPurchaseEditDetail success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), purchaseEditDetail1);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @RequestMapping(value = "/createPurchaseOrder", method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult createPurchaseOrder(String warehouseId,//仓库ID
                                              String supplierCode,//供应商编码
                                              int logisticType,//物流方式
                                              String purchaseItemInfoJson,//货品信息
                                              byte supplierReceipt,//是否开具发票
                                              HttpServletRequest request) {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,
                portalUserInfo.getUserId(),
                portalUserInfo.getUserName());
        String arrivalDeadline = request.getParameter("arrivalDeadline");
        String requireArrivalDate = request.getParameter("requireArrivalDate");
        String purchaseType = request.getParameter("purchaseType");
        String businessDate = request.getParameter("businessDate");
        String adCouponAmountUse = request.getParameter("adCouponAmountUse");
        String adPrepaidAmountUse = request.getParameter("adPrepaidAmountUse");
        String purchaseGuideAmount = request.getParameter("purchaseGuideAmount");
        String purchaseNumber = request.getParameter("purchaseNumber");
        String cashAmount = request.getParameter("cashAmount");
        String brandOrderNo = request.getParameter("brandOrderNo");
        String contractReferenceOrderNo = request.getParameter("contractReferenceOrderNo");
        String purchaseCategory = request.getParameter("purchaseCategory");
        String remark = request.getParameter("remark");
        double couponAmountUse = Double.parseDouble(request.getParameter("couponAmountUse") == "" ? "0" : request.getParameter("couponAmountUse"));
        double prepaidAmountUse = Double.parseDouble(request.getParameter("prepaidAmountUse") == "" ? "0" : request.getParameter("prepaidAmountUse"));

        String format = "yyyy-MM-dd";
        if (requireArrivalDate == null || "".equals(requireArrivalDate)) {
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.REQUIRE_ARRIVAL_DATE_NOT_NULL);
        }
        boolean purchaseTypeNotNull = ValidationUtil.isNotEmpty(purchaseType);
        if (!purchaseTypeNotNull) {
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PURCHASE_TYPE_NOT_NULL);
        }
        boolean businessDateNotNull = ValidationUtil.isNotEmpty(businessDate);
        if (!businessDateNotNull) {
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.BUSINESS_DATE_NOT_NULL);
        }
        try {
            long projectId = portalUserInfo.getProjectId();
            logger.info("#traceId={}# [IN][putPurchaseEditDetail] params: warehouseId={}, supplierCode={}, " +
                            "logisticType={}, remark={}, couponAmountUse={}, adPrepaidAmountUse={}, " +
                            "purchaseGuideAmount={}, purchasePrivilegeAmount={}, purchaseItemInfoJson={}", rpcHeader.getTraceId(), warehouseId, supplierCode,
                    logisticType, remark, couponAmountUse, prepaidAmountUse, adCouponAmountUse, 0, purchaseItemInfoJson);
            //将货品列表json数组的字符串转换为List
            ArrayList<CreatePurchaseItemInfo> purchaseItemInfoList
                    = JSON.parseObject(purchaseItemInfoJson, new TypeReference<ArrayList<CreatePurchaseItemInfo>>() {
            });
            PurchaseStructure.CreatePurchaseOrderReq.Builder createPurchaseOrderReq = PurchaseStructure.CreatePurchaseOrderReq.newBuilder();
            PurchaseServiceGrpc.PurchaseServiceBlockingStub purchaseServiceBlockingStub = RpcStubStore.getRpcStub(PurchaseServiceGrpc.PurchaseServiceBlockingStub.class);
            createPurchaseOrderReq.setRpcHeader(rpcHeader);
            PurchaseStructure.CreatePurchaseBasicInfo createPurchaseBasicInfo = PurchaseStructure.CreatePurchaseBasicInfo.newBuilder()
                    .setPurchaseOrderNo("")
                    .setUserId(rpcHeader.getUid())
                    .setUserName(rpcHeader.getUsername())
                    .setProjectId(projectId + "")
                    .setWarehouseId(warehouseId)
                    .setBrandId("")
                    .setSupplierCode(supplierCode)
                    .setPaymentMode(0)
                    .setLogisticType(logisticType)
                    .setPurchaseType(Integer.parseInt(purchaseType))
                    .setBusinessDate(DateUtil.getTime(businessDate, "yyyy-MM-dd"))
                    .setRequirArrivalDate(DateUtil.getTime(requireArrivalDate, "yyyy-MM-dd"))
                    .setArrivalDeadline(DateUtil.getTime(arrivalDeadline, "yyyy-MM-dd"))
                    .setBrandOrderNo(GrpcCommonUtil.getProtoParam(brandOrderNo))
                    .setContractReferenceOrderNo(GrpcCommonUtil.getProtoParam(contractReferenceOrderNo))
                    .setRemark(GrpcCommonUtil.getProtoParam(remark))
                    .setPurchaseCategory(Integer.parseInt(purchaseCategory))
                    .setPurchaseNumber(Integer.parseInt(purchaseNumber))
                    .setCouponAmountUse(GrpcCommonUtil.getProtoParam(couponAmountUse))
                    .setPrepaidAmountUse(GrpcCommonUtil.getProtoParam(prepaidAmountUse))
                    .setPurchaseGuideAmount(Double.parseDouble(purchaseGuideAmount))
                    .setReturnCash(0)
                    .setAdCouponAmountUse(0)
                    .setAdPrepaidAmountUse(0)
                    .setSupplierReceipt(supplierReceipt)
                    .setCashAmount(0)
                    .setProjectId(projectId+"")
                    .build();
            createPurchaseOrderReq.setPurchaseBasicInfo(createPurchaseBasicInfo);
            int totalQuantity = 0;
            for (CreatePurchaseItemInfo createPurchaseItemInfo : purchaseItemInfoList) {
                totalQuantity += createPurchaseItemInfo.getPurchaseNumber();
                PurchaseStructure.CreatePurchaseItemInfo createPurchaseItemInfo1 = PurchaseStructure.CreatePurchaseItemInfo.newBuilder()
                        .setProductID(createPurchaseItemInfo.getProductId())
                        .setProductCode(createPurchaseItemInfo.getProductCode())
                        .setProductName(createPurchaseItemInfo.getProductName())
                        .setGuidePrice(GrpcCommonUtil.getProtoParam(createPurchaseItemInfo.getGuidePrice()))
                        .setPurchaseNumber(createPurchaseItemInfo.getPurchaseNumber())
                        .setPurchaseDiscount(GrpcCommonUtil.getProtoParam(createPurchaseItemInfo.getPurchaseDiscount()))
                        .setCostPrice(GrpcCommonUtil.getProtoParam(createPurchaseItemInfo.getCostPrice()))
                        .setCouponBasePrice(GrpcCommonUtil.getProtoParam(createPurchaseItemInfo.getCouponBasePrice()))
                        .setPriceDiscount(GrpcCommonUtil.getProtoParam(createPurchaseItemInfo.getPriceDiscount()))
                        .setPurchasePrice(GrpcCommonUtil.getProtoParam(createPurchaseItemInfo.getPurchasePrice()))
                        .setFactoryPrice(GrpcCommonUtil.getProtoParam(createPurchaseItemInfo.getFactoryPrice()))
                        .setCouponAmount(GrpcCommonUtil.getProtoParam(createPurchaseItemInfo.getCouponAmount()))
                        .setPrepaidAmount(GrpcCommonUtil.getProtoParam(createPurchaseItemInfo.getPrepaidAmount()))
                        .build();
                createPurchaseOrderReq.addPurchaseItemInfoList(createPurchaseItemInfo1);
            }
            if (totalQuantity <= 0){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PURCHASE_NUMBER_NOT_BE_ZERO);
            }
            PurchaseStructure.CreatePurchaseOrderResp createPurchaseOrderResp = purchaseServiceBlockingStub.createPurchaseOrder(createPurchaseOrderReq.build());
            GongxiaoRpc.RpcResult rpcResult = createPurchaseOrderResp.getRpcResult();

            return new GongxiaoResult(rpcResult.getReturnCode(), 1);

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }


    //更新采购单数据
    @RequestMapping(value = "/putPurchaseEditDetail", method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult putPurchaseEditDetail(String purchaseOrderNo,
                                                String warehouseId,//仓库ID
                                                String supplierCode,//供应商编码
//                                                int paymentMode,//支付方式
                                                int logisticType,//物流方式
                                                String remark,//备注
                                                double couponAmountUse,//YH使用返利
                                                double prepaidAmountUse,//YH使用代垫
                                                double adCouponAmountUse,//AD使用返利
                                                double adPrepaidAmountUse,//AD使用代垫
                                                double purchaseGuideAmount,//指导金额
                                                double purchasePrivilegeAmount,//优惠金额
                                                String purchaseItemInfoJson,//货品信息
                                                HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        String arrivalDeadline = request.getParameter("arrivalDeadline");
        String requireArrivalDate = request.getParameter("requireArrivalDate");
        String purchaseType = request.getParameter("purchaseType");
        String businessDate = request.getParameter("businessDate");
        String format = "yyyy-MM-dd";
        if (requireArrivalDate == null || "".equals(requireArrivalDate)) {
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.REQUIRE_ARRIVAL_DATE_NOT_NULL);
        }
        try {
            long projectId = portalUserInfo.getProjectId();
            logger.info("#traceId={}# [IN][putPurchaseEditDetail] params: purchaseOrderNo={}, warehouseId={}, supplierCode={}, " +
                            "logisticType={}, remark={}, couponAmountUse={}, prepaidAmountUse={}, adCouponAmountUse={}, adPrepaidAmountUse={}, " +
                            "purchaseGuideAmount={}, purchasePrivilegeAmount={}, purchaseItemInfoJson={}", rpcHeader.getTraceId(), purchaseOrderNo, warehouseId, supplierCode,
                    logisticType, remark, couponAmountUse, prepaidAmountUse, adCouponAmountUse, adPrepaidAmountUse, purchaseGuideAmount, purchasePrivilegeAmount, purchaseItemInfoJson);

            PurchaseServiceGrpc.PurchaseServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PurchaseServiceGrpc.PurchaseServiceBlockingStub.class);
            //定义请求参数
            PurchaseStructure.UpdatePurchaseOrderReq.Builder updatePurchaseReq
                    = PurchaseStructure.UpdatePurchaseOrderReq.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader);
            //将货品列表json数组的字符串转换为List
            ArrayList<CreatePurchaseItemInfo> purchaseItemInfoList
                    = JSON.parseObject(purchaseItemInfoJson, new TypeReference<ArrayList<CreatePurchaseItemInfo>>() {
            });
            for (CreatePurchaseItemInfo createPurchaseItemInfo : purchaseItemInfoList) {
                PurchaseStructure.CreatePurchaseItemInfo purchaseItemInfo = PurchaseStructure.CreatePurchaseItemInfo.newBuilder()
                        .setProductID(createPurchaseItemInfo.getProductId())
                        .setProductCode(createPurchaseItemInfo.getProductCode())
                        .setProductName(createPurchaseItemInfo.getProductName())
                        .setGuidePrice(createPurchaseItemInfo.getGuidePrice())
                        .setPurchaseNumber(createPurchaseItemInfo.getPurchaseNumber())
                        .setPurchaseDiscount(createPurchaseItemInfo.getPurchaseDiscount())
                        .setCouponAmount(createPurchaseItemInfo.getCouponAmount())
                        .setPrepaidAmount(createPurchaseItemInfo.getPrepaidAmount())
                        .setPurchasePrice(createPurchaseItemInfo.getPurchasePrice())
                        .setCostPrice(createPurchaseItemInfo.getCostPrice())
                        .setCouponBasePrice(createPurchaseItemInfo.getCouponBasePrice())
                        .build();
                updatePurchaseReq.addPurchaseItemInfoList(purchaseItemInfo);
            }

            int purchaseTypes = 0;
            if (purchaseType != null && !"".equals(purchaseType)) {
                purchaseTypes = Integer.parseInt(purchaseType);
            }
            PurchaseStructure.CreatePurchaseBasicInfo.Builder createPurchaseBasicInfo = PurchaseStructure.CreatePurchaseBasicInfo.newBuilder()
                    .setPurchaseOrderNo(purchaseOrderNo);
            createPurchaseBasicInfo.setUserId(portalUserInfo.getUserId())
                    .setUserName(portalUserInfo.getUserName())
                    .setWarehouseId(warehouseId)
                    .setSupplierCode(supplierCode)
//            createPurchaseBasicInfo.setPaymentMode(paymentMode);
                    .setPurchaseType(purchaseTypes)
                    .setLogisticType(logisticType)
                    .setRequirArrivalDate(DateUtil.getTime(CommonUtil.StringToDateWithFormat(requireArrivalDate, format)))
                    .setArrivalDeadline(DateUtil.getTime(CommonUtil.StringToDateWithFormat(arrivalDeadline, format)))
                    .setRemark(remark)
                    .setCouponAmountUse(couponAmountUse)
                    .setPrepaidAmountUse(prepaidAmountUse)
                    .setAdPrepaidAmountUse(adPrepaidAmountUse)
                    .setAdCouponAmountUse(adCouponAmountUse)
/*            采购单表新增现金使用金额字段
            createPurchaseBasicInfo.setCashAmountUse(cashAmountUse);*/
                    .setPurchaseGuideAmount(purchaseGuideAmount)
                    .setProjectId(projectId + "")
                    .setBusinessDate(DateUtil.getTime(CommonUtil.StringToDateWithFormat(businessDate, format)));

            updatePurchaseReq.setPurchaseBasicInfo(createPurchaseBasicInfo);

            PurchaseStructure.UpdatePurchaseOrderResp resp = rpcStub.updatePurchaseOrder(updatePurchaseReq.build());
            int number = resp.getNumber();
            logger.info("#traceId={}# [OUT] putPurchaseEditDetail success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), number);

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @RequestMapping(value = "/cancelPurchaseOrder", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult cancelPurchaseOrder(String purchaseOrderNumber,
                                              HttpServletRequest request) {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        long projectId = portalUserInfo.getProjectId();
        try {

            logger.info("#traceId={}# [IN][cancelPurchaseOrder] params: purchaseOrderNo={}", rpcHeader.getTraceId(), purchaseOrderNumber);
            PurchaseServiceGrpc.PurchaseServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PurchaseServiceGrpc.PurchaseServiceBlockingStub.class);
            PurchaseStructure.CancelPurchaseOrderReq cancelPurchaseOrderReq = PurchaseStructure.CancelPurchaseOrderReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setPurchaseOrderNumber(purchaseOrderNumber)
                    .build();
            PurchaseStructure.CancelPurchaseOrderResp cancelPurchaseOrderResp = rpcStub.cancelPurchaseOrder(cancelPurchaseOrderReq);
            int number = cancelPurchaseOrderResp.getNumber();
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), number);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

}
