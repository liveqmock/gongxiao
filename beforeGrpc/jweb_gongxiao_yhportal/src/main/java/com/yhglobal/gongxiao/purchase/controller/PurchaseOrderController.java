package com.yhglobal.gongxiao.purchase.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.model.ProductItem;
import com.yhglobal.gongxiao.model.PurchaseOrder;
import com.yhglobal.gongxiao.payment.account.ProjectAccountModel;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.service.SupplierAccountService;
import com.yhglobal.gongxiao.purchase.bo.*;
import com.yhglobal.gongxiao.purchase.service.PurchaseService;
import com.yhglobal.gongxiao.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购管理--采购单管理
 */
@Controller
@RequestMapping("/purchase/order")
public class PurchaseOrderController {

    private static Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Reference
    PurchaseService purchaseService; //采购服务类

    @Reference
    SupplierAccountService supplierAccountService; //供应商账户

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    /**
     * 生成采购单
     *
     * @param projectId                项目ID
     * @param warehouseId              仓库ID
     * @param paymentMode              付款方式
     * @param logisticType             物流方式
     * @param businessDate             业务发生时间
     * @param contractReferenceOrderNo 合同参考号
     * @param brandOrderNo             品牌商订单号
     * @param remark                   备注
     * @param purchaseCategory         采购品种
     * @param purchaseNumber           采购总数量
     * @param couponAmountUse          返利使用金额
     * @param prepaidAmountUse         代垫使用金额
     * @param purchaseGuideAmount      采购指导金额
     * @param purchasePrivilegeAmount  采购优惠金额
     * @param returnCash               现金返点金额
     * @param purchaseShouldPayAmount  采购应付金额
     * @param purchaseActualPayAmount  采购实付金额
     * @param purchaseItemInfoJson     采购货品json
     * @return
     */
    @RequestMapping(value = "/generatePurchaseOrders", method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult generatePurchaseOrder(
            String projectId,
            String warehouseId,
            String supplierCode,
            int paymentMode,
            int logisticType,
            String businessDate,
            String contractReferenceOrderNo,
            String brandOrderNo,
            String remark,
            int purchaseCategory,
            int purchaseNumber,
            double couponAmountUse,
            double prepaidAmountUse,
            double adCouponAmountUse,
            double adPrepaidAmountUse,
            /*double cashAmountUse,*/
            double purchaseGuideAmount,
            double purchasePrivilegeAmount,
            double returnCash,
            double purchaseShouldPayAmount,
            double purchaseActualPayAmount,
            String purchaseItemInfoJson,
            HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][generatePurchaseOrders] params: projectId:{}, projectId:{}, projectId:{}, projectId:{}, projectId:{}, projectId:{}, projectId:{}, projectId:{}, " +
                            "projectId:{}, projectId:{}, projectId:{}, projectId:{}, projectId:{}, projectId:{}, projectId:{}, projectId:{}, projectId:{}, projectId:{}, projectId:{}, " +
                            "projectId:{}, projectId:{}, projectId:{}",
                    traceId, warehouseId, supplierCode, paymentMode, logisticType, businessDate, contractReferenceOrderNo,
                    brandOrderNo, remark, purchaseCategory, purchaseNumber, couponAmountUse, prepaidAmountUse, purchaseGuideAmount, purchasePrivilegeAmount, returnCash, purchaseShouldPayAmount,
                    purchaseActualPayAmount);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String format = "yyyy-MM-dd";
            String arrivalDeadline = request.getParameter("arrivalDeadline");
            String requirArrivalDate = request.getParameter("requirArrivalDate");
            //1)校验
            boolean projectNotEmpty = ValidationUtil.isNotEmpty(projectId);
            if (!projectNotEmpty){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PROJECT_NOT_NULL);
            }
            boolean warehouseNotNull = ValidationUtil.isNotEmpty(warehouseId);
            if (!warehouseNotNull){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.WAREHOUSE_NOT_NULL);
            }
            boolean supplierNotNull = ValidationUtil.isNotEmpty(supplierCode);
            if (!supplierNotNull){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.SUPPLIER_NOT_NULL);
            }
            boolean adValue = ValidationUtil.valueGreaterZero(adCouponAmountUse, adPrepaidAmountUse);
            if (adValue){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.AD_VALUE_GREATER_ZERO);
            }
            boolean yhValue = ValidationUtil.valueGreaterZero(couponAmountUse, prepaidAmountUse);
            if (yhValue){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.YH_VALUE_GREATER_ZERO);
            }
//            boolean requireDateNotNull = ValidationUtil.isNotEmpty(requirArrivalDate);
//            if (!requireDateNotNull){
//                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.REQUIRE_ARRIVAL_DATE_NOT_NULL);
//            }
//            boolean arrivalDeadLineNotNull = ValidationUtil.isNotEmpty(arrivalDeadline);
//            if (!arrivalDeadLineNotNull){
//                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.ARRIVAL_DEAD_LINE);
//            }
            boolean brandNotNull = ValidationUtil.isNotEmpty(brandOrderNo);
            if (!brandNotNull){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.BRAND_ORDER_NOT_NULL);
            }
            boolean contractOrderNotNull = ValidationUtil.isNotEmpty(contractReferenceOrderNo);
            if (!contractOrderNotNull){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.CONTRACT_NOT_NULL);
            }

            //将货品列表json数组的字符串转换为List
            ArrayList<CreatePurchaseItemInfo> purchaseItemInfoList
                    = JSON.parseObject(purchaseItemInfoJson, new TypeReference<ArrayList<CreatePurchaseItemInfo>>() {
            });
            CreatePurchaseBasicInfo createPurchaseBasicInfo = new CreatePurchaseBasicInfo();
            createPurchaseBasicInfo.setUserId(portalUserInfo.getUserId());
            createPurchaseBasicInfo.setUserName(portalUserInfo.getUserName());
            createPurchaseBasicInfo.setProjectId(projectId);
            createPurchaseBasicInfo.setWarehouseId(warehouseId);
            createPurchaseBasicInfo.setSupplierCode(supplierCode);
            createPurchaseBasicInfo.setPaymentMode(paymentMode);
            createPurchaseBasicInfo.setLogisticType(logisticType);
            createPurchaseBasicInfo.setPurchaseType(1);
            createPurchaseBasicInfo.setBusinessDate(CommonUtil.StringToDateWithFormat(businessDate, format));
            createPurchaseBasicInfo.setRequirArrivalDate(CommonUtil.StringToDateWithFormat(requirArrivalDate, format));
            createPurchaseBasicInfo.setArrivalDeadline(CommonUtil.StringToDateWithFormat(arrivalDeadline, format));
            createPurchaseBasicInfo.setBrandOrderNo(brandOrderNo);
            createPurchaseBasicInfo.setContractReferenceOrderNo(contractReferenceOrderNo);
            createPurchaseBasicInfo.setRemark(remark);
            createPurchaseBasicInfo.setPurchaseCategory(purchaseCategory);
            createPurchaseBasicInfo.setPurchaseNumber(purchaseNumber);
            createPurchaseBasicInfo.setCouponAmountUse(couponAmountUse);
            createPurchaseBasicInfo.setPrepaidAmountUse(prepaidAmountUse);
            createPurchaseBasicInfo.setAdPrepaidAmountUse(adPrepaidAmountUse);
            createPurchaseBasicInfo.setAdCouponAmountUse(adCouponAmountUse);
/*            采购单表新增现金使用金额字段
            createPurchaseBasicInfo.setCashAmountUse(cashAmountUse);*/
            createPurchaseBasicInfo.setPurchaseGuideAmount(purchaseGuideAmount);
            createPurchaseBasicInfo.setPurchasePrivilegeAmount(purchasePrivilegeAmount);
            createPurchaseBasicInfo.setReturnCash(returnCash);
            createPurchaseBasicInfo.setPurchaseShouldPayAmount(purchaseShouldPayAmount);
            createPurchaseBasicInfo.setPurchaseActualPayAmount(purchaseActualPayAmount);
            purchaseService.generatePurchaseOrder(rpcHeader, createPurchaseBasicInfo, purchaseItemInfoList);
            logger.info("#traceId={}# [OUT] generatePurchaseOrders success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), 1);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

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
            String projectId,
            String purchaseOrderNo,
            String warehouseId,
            String warehouseName,
            String requireInboundDate,
            int totalQuantity,
            int planInstockNumberTotal,
            String planInboundItemListJson,
            HttpServletRequest request) throws Exception {

        String traceId = null;
        try {
            String format = "yyyy-MM-dd";
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("[#traceId={}# [IN][planInbound] params: projectId:{}, purchaseOrderNo:{}, warehouseId:{}, warehouseName:{}, requireInboundDate:{}, " +
                            "totalQuantity:{}, planInstockNumberTotal:{}, planInboundItemListJson:{} ", traceId, projectId, purchaseOrderNo, warehouseId, warehouseName, requireInboundDate, totalQuantity,
                    planInstockNumberTotal);
            boolean projectNotEmpty = ValidationUtil.isNotEmpty(projectId);
            if (!projectNotEmpty){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PROJECT_NOT_NULL);
            }
            if (planInstockNumberTotal==0){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PLAN_NUMMBER_NOT_NULL);
            }
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PlanInboundBasicInfo planInboundBasicInfo = new PlanInboundBasicInfo();
            planInboundBasicInfo.setProjectId(projectId);
            planInboundBasicInfo.setPurchaseOrderNo(purchaseOrderNo);
            planInboundBasicInfo.setWarehouseId(warehouseId);
            planInboundBasicInfo.setWarehouseName(warehouseName);
            planInboundBasicInfo.setTotalQuantity(totalQuantity);
            planInboundBasicInfo.setPlanInstockNumberTotal(planInstockNumberTotal);
            if (requireInboundDate!=null && !"".equals(requireInboundDate)){
                planInboundBasicInfo.setRequireInboundDate(CommonUtil.StringToDateWithFormat(requireInboundDate, format));
            }

            ArrayList<PlanInboundItem> purchaseItemInfoList
                    = JSON.parseObject(planInboundItemListJson, new TypeReference<ArrayList<PlanInboundItem>>() {
            });
            for (PlanInboundItem planInboundItem : purchaseItemInfoList) {
                int planInstockNumber = planInboundItem.getPlanInstockNumber();
                int needInstockNumber = planInboundItem.getNeedInstockNumber();
                if (planInstockNumber > needInstockNumber) {
                    return new GongxiaoResult(ErrorCode.INBOUND_EXCEPTION.getErrorCode(), ErrorCode.INBOUND_EXCEPTION.getMessage());
                }
            }
            RpcResult rpcResult = purchaseService.planInbound(rpcHeader, planInboundBasicInfo, purchaseItemInfoList);
            if (rpcResult.getCode()!=0){
                return  GongxiaoResultUtil.createGongxiaoErrorResultByCode(rpcResult.getCode());
            }
            logger.info("#traceId={}# [OUT] planInboundOrder success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), 1);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 计算货品的各价格
     *
     * @param projectId                   项目ID
     * @param cashRate                    现金返利折扣
     * @param couponUseReferRate          返利使用比例
     * @param prepaidUseReferRate         代垫使用比例
     * @param couponAmountUse             订单返利使用额
     * @param prepaidAmountUse            订单代垫使用额
     * @param productJson                 货品信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/calculateProductInfo", method = RequestMethod.POST)
    public GongxiaoResult calculateProduct(
            String projectId,
            double cashRate,
            double couponUseReferRate,
            double prepaidUseReferRate,
            double couponAmountUse,
            double prepaidAmountUse,
            double adCouponAmountUse,
            double adPrepaidAmountUse,
            int status, //status=1 表示从表格过来的  status=2 表示从主单过来的
            String productJson,
            HttpServletRequest request) throws Exception {
        double monthlyCouponGenerateRate=0;
        double quarterlyCouponGenerateRate=0;
        double annualCouponGenerateRate=0;

        String monthlyCouponGenerateRateParam = request.getParameter("monthlyCouponGenerateRate");
        if (monthlyCouponGenerateRateParam!=null&&!"".equals(monthlyCouponGenerateRateParam)){
            monthlyCouponGenerateRate = Double.parseDouble(monthlyCouponGenerateRateParam);
        }
        String quarterlyCouponGenerateRateParam = request.getParameter("quarterlyCouponGenerateRate");
        if (quarterlyCouponGenerateRateParam!=null&&!"".equals(quarterlyCouponGenerateRateParam)){
            quarterlyCouponGenerateRate = Double.parseDouble(monthlyCouponGenerateRateParam);
        }
        String annualCouponGenerateRateParam = request.getParameter("annualCouponGenerateRate");
        if (annualCouponGenerateRateParam!=null&&!"".equals(annualCouponGenerateRateParam)){
            annualCouponGenerateRate = Double.parseDouble(monthlyCouponGenerateRateParam);
        }
        String traceId = null;
        try {
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getBrandList] params: projectId:{}, brandId:{}, brandName:{}, cashRate:{}, couponUseReferRate:{}, prepaidUseReferRate:{}, monthlyCouponGenerateRate:{}, " +
                            "quarterlyCouponGenerateRate:{}, annualCouponGenerateRate:{}, couponAmountUse:{}, prepaidAmountUse:{} ",
                    traceId, projectId,  cashRate, couponUseReferRate, prepaidUseReferRate, monthlyCouponGenerateRate, quarterlyCouponGenerateRate,
                    annualCouponGenerateRate, couponAmountUse, prepaidAmountUse);
            CalculateOrderInfo calculateOrderInfo = new CalculateOrderInfo();
            List<CalculateProductInfo> calculateProductInfoList = new ArrayList<>();
            calculateOrderInfo.setCalculateProductInfoList(calculateProductInfoList);

            List<ProductItem> productItems = JSON.parseObject(productJson, new TypeReference<List<ProductItem>>() {
            });
            AccountAmount accountAmount = supplierAccountService.getSupplierAccountAmount(rpcHeader, "CNY", Long.parseLong(projectId));

            double accountCouponAmount = accountAmount.getCouponAmount() / FXConstant.HUNDRED;
            double accountPrepaidAmount = accountAmount.getPrepaidAmount() / FXConstant.HUNDRED;

            double totalCouponAmountUseTheory = couponAmountUse + adCouponAmountUse;
            double totalPrepaidAmountUse = prepaidAmountUse + adPrepaidAmountUse;

            double totalAmount = 0;
            for (ProductItem item : productItems) {
                double guidePrice = item.getGuidePrice();
                int purchaseNumber = item.getPurchaseNumber();
                double purchaseDiscount = item.getDiscountPercent();
                totalAmount += (guidePrice * purchaseNumber * (1 - purchaseDiscount));
            }
            double couponTheoryAmount = totalAmount * couponUseReferRate;//按比例得出理论使用返利金额
            double prepaidTheoryAmount = totalAmount * prepaidUseReferRate; //按比例得出理论使用代垫金额
            //确定实际使用返利总额与实际使用代垫总额
            double actualCouponAmount;
            double actualPrepaidAmount;
            if (status == 1) {
                actualCouponAmount = couponTheoryAmount > accountCouponAmount ? accountCouponAmount : couponTheoryAmount;
                actualPrepaidAmount = prepaidTheoryAmount > accountPrepaidAmount ? accountPrepaidAmount : prepaidTheoryAmount;
            } else {
                actualCouponAmount = totalCouponAmountUseTheory > accountCouponAmount ? accountCouponAmount : totalCouponAmountUseTheory;
                actualPrepaidAmount = totalPrepaidAmountUse > accountPrepaidAmount ? accountPrepaidAmount : totalPrepaidAmountUse;
            }
            //设置剩余返利与代垫
            accountCouponAmount = accountCouponAmount - actualCouponAmount;
            accountPrepaidAmount = accountPrepaidAmount - actualPrepaidAmount;
            //确定yh使用返利,代垫的总额/AD使用返利,代垫的总额
            double yhCouponAmountTotal = 0;
            double yhPrepaidAmountTotal = 0;
            double adCouponAmountTotal = 0;
            double adPrepaidAmountTotal = 0;
            if (status == 1) { //以输入框为主
                yhCouponAmountTotal = actualCouponAmount;
                yhPrepaidAmountTotal = actualPrepaidAmount;
            }
            if (status == 2) { //以yh返利/代垫金额为主
                if (couponAmountUse < actualCouponAmount) {//输入框的返利金额,小于该订单实际使用的返利金额
                    yhCouponAmountTotal = couponAmountUse;//yh使用返利保持跟输入框一致
                    adCouponAmountTotal = actualCouponAmount - yhCouponAmountTotal;//AD返利
                } else {//输入框的返利金额,不小于该订单实际使用的返利金额
                    yhCouponAmountTotal = actualCouponAmount;
                    adCouponAmountTotal = 0;//没有AD返利
                }
                if (prepaidAmountUse < actualPrepaidAmount) {
                    yhPrepaidAmountTotal = prepaidAmountUse;
                    adPrepaidAmountTotal = actualPrepaidAmount - prepaidAmountUse;
                } else {
                    yhPrepaidAmountTotal = actualPrepaidAmount;
                    adPrepaidAmountTotal = 0;
                }
            }
            if (status == 3) {//以AD返利/代垫金额为主
                if (adCouponAmountUse < actualCouponAmount) {//输入框的返利金额,小于该订单实际使用的返利金额
                    adCouponAmountTotal = adCouponAmountUse;//yh使用返利保持跟输入框一致
                    yhCouponAmountTotal = actualCouponAmount - adCouponAmountTotal;//AD返利
                } else {//输入框的返利金额,不小于该订单实际使用的返利金额
                    adCouponAmountTotal = actualCouponAmount;
                    yhCouponAmountTotal = 0;//没有AD返利
                }
                if (adPrepaidAmountUse < actualPrepaidAmount) {
                    adPrepaidAmountTotal = adPrepaidAmountUse;
                    yhPrepaidAmountTotal = actualPrepaidAmount - adPrepaidAmountTotal;
                } else {
                    adPrepaidAmountTotal = actualPrepaidAmount;
                    yhPrepaidAmountTotal = 0;
                }
            }
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
                calculateProductInfo.setProductID(item.getProductId());
                calculateProductInfo.setProductCode(item.getProductCode());
                calculateProductInfo.setProductName(item.getProductName());
//                calculateProductInfo.setBrandId(brandId);
//                calculateProductInfo.setBrandName(brandName);
                calculateProductInfo.setGuidePrice(CommonUtil.doubleToString(item.getGuidePrice(), "0.000000"));
                calculateProductInfo.setPurchaseNumber(item.getPurchaseNumber());
                calculateProductInfo.setDiscountPercent(CommonUtil.doubleToString(item.getDiscountPercent(), "0.000000"));
                calculateProductInfo.setCouponAmount(CommonUtil.doubleToString(couponAmountItem, "0.00"));
                calculateProductInfo.setPrepaidAmount(CommonUtil.doubleToString(prepaidAmountItem, "0.00"));

                purchaseGuideAmount += guidePrice * purchaseNumber; //采购指导金额
                purchasePrivilegeAmount += guidePrice * purchaseDiscount; //采购优惠金额
                purchaseShouldPayAmount += guidePrice * (1 - purchaseDiscount) * purchaseNumber;//采购应付金额

                //设置采购价格
                double purchasePrice = guidePrice * (1 - purchaseDiscount) - couponAmountItem / purchaseNumber - prepaidAmountItem / purchaseNumber;
                calculateProductInfo.setPurchasePrice(CommonUtil.doubleToString(purchasePrice, "0.000000"));
                //设置成本价
                double costValue = guidePrice * (1 - purchaseDiscount) * (1 - monthlyCouponGenerateRate - quarterlyCouponGenerateRate - annualCouponGenerateRate);
                calculateProductInfo.setCostPrice(CommonUtil.doubleToString(costValue, "0.000000"));
                //设置返利基数
                double couponBasePrice = guidePrice * (1 - purchaseDiscount);
                calculateProductInfo.setCouponBasePrice(CommonUtil.doubleToString(couponBasePrice, "0.000000"));

                System.out.println(calculateProductInfo.toString());
                calculateProductInfoList.add(calculateProductInfo);
            }
            //设置返利过账金额
            calculateOrderInfo.setAdCouponAmount(CommonUtil.doubleToString(adCouponAmountTotal, "0.00"));
            //设置代垫过账金额
            calculateOrderInfo.setAdPrepaidAmount(CommonUtil.doubleToString(adPrepaidAmountTotal, "0.00"));
            //设置返利余额
            calculateOrderInfo.setCouponRemainMoney(CommonUtil.doubleToString(accountCouponAmount, "0.00"));
            //设置代垫余额
            calculateOrderInfo.setPrepaidRemainMoney(CommonUtil.doubleToString(accountPrepaidAmount, "0.00"));
            //设置YH返利使用金额
            calculateOrderInfo.setCouponAmount(CommonUtil.doubleToString(yhCouponAmountTotal, "0.00"));
            //设置YH代垫使用金额
            calculateOrderInfo.setPrepaidAmount(CommonUtil.doubleToString(yhPrepaidAmountTotal, "0.00"));
            //设置AD返利使用金额
            calculateOrderInfo.setAdCouponAmount(CommonUtil.doubleToString(adCouponAmountTotal, "0.00"));
            //设置AD代垫使用金额
            calculateOrderInfo.setAdPrepaidAmount(CommonUtil.doubleToString(adPrepaidAmountTotal, "0.00"));
            //设置采购指导金额
            calculateOrderInfo.setPurchaseGuideAmount(CommonUtil.doubleToString(purchaseGuideAmount, "0.00"));
            //采购应付金额
            calculateOrderInfo.setPurchaseShouldPayAmount(CommonUtil.doubleToString(purchaseShouldPayAmount, "0.00"));
            //采购优惠金额
            calculateOrderInfo.setPurchasePrivilegeAmount(CommonUtil.doubleToString(purchasePrivilegeAmount, "0.00"));
            //现金返点金额
            returnCash = (purchaseShouldPayAmount - couponAmountUse - prepaidAmountUse) * cashRate;
            calculateOrderInfo.setReturnCash(CommonUtil.doubleToString(returnCash, "0.00"));
            //采购实付金额
            purchaseActualPayAmount = (purchaseShouldPayAmount - couponAmountUse - prepaidAmountUse) * (1 - cashRate);
            calculateOrderInfo.setPurchaseActualPayAmount(CommonUtil.doubleToString(purchaseActualPayAmount, "0.00"));
            calculateOrderInfo.setCalculateProductInfoList(calculateProductInfoList);
            logger.info("#traceId={}# [OUT] calculateProductInfo success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), calculateOrderInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/calculateEditProductInfo", method = RequestMethod.POST)
    public GongxiaoResult calculateEditProductInfo(
            String projectId,   //项目ID
            double couponAmountUse, //YH返利使用金额
            double prepaidAmountUse,//YH代垫使用金额
            double adCouponAmountUse,//AD返利使用金额
            double adPrepaidAmountUse,//AD代垫使用金额
            double purchaseShouldPayAmount,//采购应付金额
            int status, //status=1 以YH为主  status=2 表示以AD为主
            String productJson,
            HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getBrandList] params: projectId:{}, brandId:{}, brandName:{}, cashRate:{}, couponUseReferRate:{}, prepaidUseReferRate:{}, monthlyCouponGenerateRate:{}, " +
                            "quarterlyCouponGenerateRate:{}, annualCouponGenerateRate:{}, couponAmountUse:{}, prepaidAmountUse:{} ",
                    traceId, projectId, couponAmountUse, prepaidAmountUse);
            CalculateOrderInfo calculateOrderInfo = new CalculateOrderInfo();
            List<CalculateProductInfo> calculateProductInfoList = new ArrayList<>();
            calculateOrderInfo.setCalculateProductInfoList(calculateProductInfoList);

            List<ProductItem> productItems = JSON.parseObject(productJson, new TypeReference<List<ProductItem>>() {
            });
            AccountAmount accountAmount = supplierAccountService.getSupplierAccountAmount(rpcHeader, "CNY", Long.parseLong(projectId));
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
                calculateProductInfo.setProductID(item.getProductId());
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
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
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
    public GongxiaoResult getInboundList(String purchaseNumber, String productCode, HttpServletRequest request) throws Exception {
        String traceId = null;
        if ("".equals(purchaseNumber)) {
            return new GongxiaoResult(205, "采购单号为空");
        }
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getInboundList] params: purchaseNumber:{}, productCode ", purchaseNumber, productCode);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<PlanInboundItem> inboundItemList = purchaseService.getInboundItemList(rpcHeader, purchaseNumber, productCode);
            logger.info("#traceId={}# [OUT] getInboundList success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), inboundItemList);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 获取采购单列表
     *
     * @param projectId          项目ID
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
    public GongxiaoResult getPurchaseList(String projectId,
                                          String purchaseOrderNo,
                                          String brandId,
                                          String warehouseId,
                                          String productCode,
                                          String requireArrivalDate,
                                          String arrivalDeadline,
                                          String businessDate,
                                          int pageNumber,
                                          int pageSize,
                                          HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getBrandList] params: proje ctId:{}, purchaseOrderNo{}, brandId{}, warehouseId{}, productCode{}, requireArrivalDate{}, arrivalDeadline{}, businessDate{} ",
                    traceId, projectId, purchaseOrderNo, brandId, warehouseId, productCode, requireArrivalDate, arrivalDeadline, businessDate);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PageInfo<PurchaseOrderInfo> purchaseOrderList = purchaseService.getPurchaseOrderList(rpcHeader, projectId,
                    purchaseOrderNo,
                    brandId,
                    warehouseId,
                    productCode,
                    requireArrivalDate,
                    arrivalDeadline,
                    businessDate,
                    pageNumber,
                    pageSize);
            logger.info("#traceId={}# [OUT] getPurchaseList success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), purchaseOrderList);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
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
    public GongxiaoResult getPurchaseDetail(String purchaseOrderNo, HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getPurchaseDetail] params: purchaseOrderNo={}", traceId, purchaseOrderNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PurchaseOrderDetailVo purchaseDetail = purchaseService.getPurchaseDetail(rpcHeader, purchaseOrderNo);
            System.out.println(JSON.toJSONString(purchaseDetail));
            logger.info("#traceId={}# [OUT] getPurchaseDetail success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), purchaseDetail);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }


    @RequestMapping(value = "/getPurchaseEditDetail", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getPurchaseEditDetail(String purchaseOrderNumber, HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getPurchaseEditDetail] params: purchaseOrderNo={}", traceId, purchaseOrderNumber);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PurchaseEditDetail purchaseEditDetail = purchaseService.getPurchaseEditDetail(rpcHeader, purchaseOrderNumber);
            logger.info("#traceId={}# [OUT] getPurchaseEditDetail success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), purchaseEditDetail);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
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
        String traceId = null;
        String arrivalDeadline = request.getParameter("arrivalDeadline");
        String requireArrivalDate = request.getParameter("requireArrivalDate");
        String purchaseType = request.getParameter("purchaseType");
        String businessDate = request.getParameter("businessDate");
        boolean requireArrivalDateNotEmpty = ValidationUtil.isNotEmpty(requireArrivalDate);
        if (!requireArrivalDateNotEmpty){
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.REQUIRE_ARRIVAL_DATE_NOT_NULL);
        }
        String format = "yyyy-MM-dd";
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][putPurchaseEditDetail] params: purchaseOrderNo={}, warehouseId={}, supplierCode={}, " +
                    "logisticType={}, remark={}, couponAmountUse={}, prepaidAmountUse={}, adCouponAmountUse={}, adPrepaidAmountUse={}, " +
                    "purchaseGuideAmount={}, purchasePrivilegeAmount={}, purchaseItemInfoJson={}", traceId, purchaseOrderNo,warehouseId,supplierCode,
                    logisticType,remark,couponAmountUse,prepaidAmountUse,adCouponAmountUse,adPrepaidAmountUse,purchaseGuideAmount,purchasePrivilegeAmount,purchaseItemInfoJson);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            //将货品列表json数组的字符串转换为List

            ArrayList<CreatePurchaseItemInfo> purchaseItemInfoList
                    = JSON.parseObject(purchaseItemInfoJson, new TypeReference<ArrayList<CreatePurchaseItemInfo>>() {
            });
            //校验余额
            PurchaseOrder purchaseOrder = purchaseService.getPurchaseOrderByPurchaseNo(rpcHeader, purchaseOrderNo);
            AccountAmount accountAmount = supplierAccountService.getSupplierAccountAmount(rpcHeader, "CNY", purchaseOrder.getProjectId());
            double accountCouponAmount = (double) accountAmount.getCouponAmount() / FXConstant.HUNDRED;
            double accountPrepaidAmount = (double)accountAmount.getPrepaidAmount() / FXConstant.HUNDRED;
            double couponTotal = couponAmountUse+adCouponAmountUse;//订单使用返利
            double prepaidTotal = prepaidAmountUse+adPrepaidAmountUse;//订单使用代垫
            //校验账户剩余的返利代垫余额是否充足
            if (couponTotal > accountCouponAmount) {
                return new GongxiaoResult(ErrorCode.SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
            }
            if (prepaidTotal > accountPrepaidAmount) {
                return new GongxiaoResult(ErrorCode.SUPPLIER_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.SUPPLIER_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
            }

            CreatePurchaseBasicInfo createPurchaseBasicInfo = new CreatePurchaseBasicInfo();
            createPurchaseBasicInfo.setPurchaseOrderNo(purchaseOrderNo);
            createPurchaseBasicInfo.setUserId(portalUserInfo.getUserId());
            createPurchaseBasicInfo.setUserName(portalUserInfo.getUserName());
            createPurchaseBasicInfo.setWarehouseId(warehouseId);
            createPurchaseBasicInfo.setSupplierCode(supplierCode);
//            createPurchaseBasicInfo.setPaymentMode(paymentMode);
            if (purchaseType!=null && !"".equals(purchaseType)){
                createPurchaseBasicInfo.setPurchaseType(Integer.parseInt(purchaseType));
            }
            createPurchaseBasicInfo.setLogisticType(logisticType);
            createPurchaseBasicInfo.setRequirArrivalDate(CommonUtil.StringToDateWithFormat(requireArrivalDate, format));
            createPurchaseBasicInfo.setArrivalDeadline(CommonUtil.StringToDateWithFormat(arrivalDeadline, format));
            createPurchaseBasicInfo.setRemark(remark);
            createPurchaseBasicInfo.setCouponAmountUse(couponAmountUse);
            createPurchaseBasicInfo.setPrepaidAmountUse(prepaidAmountUse);
            createPurchaseBasicInfo.setAdPrepaidAmountUse(adPrepaidAmountUse);
            createPurchaseBasicInfo.setAdCouponAmountUse(adCouponAmountUse);
/*            采购单表新增现金使用金额字段
            createPurchaseBasicInfo.setCashAmountUse(cashAmountUse);*/
            createPurchaseBasicInfo.setPurchaseGuideAmount(purchaseGuideAmount);
            createPurchaseBasicInfo.setPurchasePrivilegeAmount(purchasePrivilegeAmount);
            createPurchaseBasicInfo.setBusinessDate(CommonUtil.StringToDateWithFormat(businessDate,format));
            int i = purchaseService.updatePurchaseOrder(rpcHeader, createPurchaseBasicInfo, purchaseItemInfoList);
            logger.info("#traceId={}# [OUT] putPurchaseEditDetail success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), i);

        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @RequestMapping(value = "/cancelPurchaseOrder", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult cancelPurchaseOrder(String purchaseOrderNumber, HttpServletRequest request){
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

            logger.info("#traceId={}# [IN][cancelPurchaseOrder] params: purchaseOrderNo={}", traceId, purchaseOrderNumber);
            PurchaseOrder purchaseOrder = purchaseService.getPurchaseOrderByPurchaseNo(rpcHeader, purchaseOrderNumber);
            Byte orderStatus = purchaseOrder.getOrderStatus();
            if (orderStatus>=60){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.CAN_NOT_CANCLE_STATUS);
            }
            int i = purchaseService.cancelPurchaseOrder(rpcHeader, purchaseOrderNumber);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), i);
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }
}
