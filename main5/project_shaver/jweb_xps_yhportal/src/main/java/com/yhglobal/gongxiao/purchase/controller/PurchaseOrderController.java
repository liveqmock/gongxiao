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
 * purchase controller
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
     * [采购订单管理] 页，在采购单表格 点 "预约入库"操作
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
            //参数检查
            if (planInstockNumberTotal == 0) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PLAN_NUMMBER_NOT_NULL);
            }
            //要求到货日期 初始化为0
            long requireInbDate = 0;
            if (requireInboundDate != null && !"".equals(requireInboundDate)) { //若没传的话，则设定默认值
                requireInbDate = CommonUtil.StringToDateWithFormat(requireInboundDate, format).getTime();
            }

            //组装proto对象
            PurchaseStructure.PlanInboundBasicInfo planInboundBasicInfo = PurchaseStructure.PlanInboundBasicInfo.newBuilder()
                    .setProjectId(projectId + "")
                    .setPurchaseOrderNo(purchaseOrderNo)
                    .setWarehouseId(warehouseId)
                    .setWarehouseName(warehouseName)
                    .setTotalQuantity(totalQuantity)
                    .setPlanInstockNumberTotal(planInstockNumberTotal)
                    .setRequireInboundDate(requireInbDate).build();

            ArrayList<PlanInboundItem> purchaseItemInfoList
                    = JSON.parseObject(planInboundItemListJson, new TypeReference<ArrayList<PlanInboundItem>>() {});

            PurchaseStructure.PlanInboundReq.Builder planInboundReq = PurchaseStructure.PlanInboundReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setPlanInboundBasicInfo(planInboundBasicInfo);
            //组装明细
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
            //走rpc调用 以便生成入库的存根和请求到warehouse服务
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

    /**
     *
     * 通过新增的方式生成采购单时
     * "采购管理 > 采购单管理 > 新增采购单", 计算各个商品的 使用返利/使用代垫/采购价/成本价/进货价
     * 注：当前暂时废弃
     *
     * @param cashRate            现金返利折扣
     * @param couponUseReferRate  返利使用比例
     * @param prepaidUseReferRate 代垫使用比例
     * @param couponAmountUse     订单返利使用额
     * @param prepaidAmountUse    订单代垫使用额
     * @param productJson         货品信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/calculateProductInfo", method = RequestMethod.POST)
    public GongxiaoResult calculateProduct(
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
        double monthlyCouponGenerateRate = 0;
        double quarterlyCouponGenerateRate = 0;
        double annualCouponGenerateRate = 0;
        long projectId = portalUserInfo.getProjectId();


        String monthlyCouponGenerateRateParam = request.getParameter("monthlyCouponGenerateRate");
        if (monthlyCouponGenerateRateParam != null && !"".equals(monthlyCouponGenerateRateParam)) {
            monthlyCouponGenerateRate = Double.parseDouble(monthlyCouponGenerateRateParam);
        }
        String quarterlyCouponGenerateRateParam = request.getParameter("quarterlyCouponGenerateRate");
        if (quarterlyCouponGenerateRateParam != null && !"".equals(quarterlyCouponGenerateRateParam)) {
            quarterlyCouponGenerateRate = Double.parseDouble(monthlyCouponGenerateRateParam);
        }
        String annualCouponGenerateRateParam = request.getParameter("annualCouponGenerateRate");
        if (annualCouponGenerateRateParam != null && !"".equals(annualCouponGenerateRateParam)) {
            annualCouponGenerateRate = Double.parseDouble(monthlyCouponGenerateRateParam);
        }
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            logger.info("#traceId={}# [IN][getBrandList] params: projectId:{}, brandId:{}, brandName:{}, cashRate:{}, couponUseReferRate:{}, prepaidUseReferRate:{}, monthlyCouponGenerateRate:{}, " +
                            "quarterlyCouponGenerateRate:{}, annualCouponGenerateRate:{}, couponAmountUse:{}, prepaidAmountUse:{} ",
                    rpcHeader.getTraceId(), projectId, cashRate, couponUseReferRate, prepaidUseReferRate, monthlyCouponGenerateRate, quarterlyCouponGenerateRate,
                    annualCouponGenerateRate, couponAmountUse, prepaidAmountUse);
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
            logger.info("#traceId={}# [OUT] calculateProductInfo success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), calculateOrderInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    /**
     *
     * 通过导入的方式生成采购单后，进行编辑时，计算各个商品的 使用返利/使用代垫/采购价/成本价/进货价
     *
     * 注：前端上传的金额为double，单位为元
     *
     * @param couponAmountUse
     * @param prepaidAmountUse
     * @param adCouponAmountUse
     * @param adPrepaidAmountUse
     * @param purchaseShouldPayAmount
     * @param status
     *            场景：YH返利使用金额商务 填写80   AD返利过账金额 填写值为70
     *                 但 当前供应商上账账户里面的钱只有100元
     *                 此时
     *                 status=1 表示供应商上账账户余额 优先分配到YH返利使用金额 剩余的部分再分配为AD返利过账金额
     *                 status=2 则优先顺序倒过来
     * @param productJson
     * @param request
     * @return
     * @throws Exception
     */
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
            logger.info("#traceId={}# [IN][calculateEditProductInfo] params: projectId:{}, brandId:{}, brandName:{}, cashRate:{}, couponUseReferRate:{}, prepaidUseReferRate:{}, monthlyCouponGenerateRate:{}, " +
                            "quarterlyCouponGenerateRate:{}, annualCouponGenerateRate:{}, couponAmountUse:{}, prepaidAmountUse:{} ",
                    rpcHeader.getTraceId(), projectId, couponAmountUse, prepaidAmountUse);
            CalculateOrderInfo calculateOrderInfo = new CalculateOrderInfo(); //返给前端的数据结构(汇总部分)
            List<CalculateProductInfo> calculateProductInfoList = new ArrayList<>(); //返给前端数据结构(明细部分)
            calculateOrderInfo.setCalculateProductInfoList(calculateProductInfoList);

            List<ProductItem> productItems = JSON.parseObject(productJson, new TypeReference<List<ProductItem>>() {});
            //获取供应商上账账户余额
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            SupplierAccountServiceStructure.GetSupplierAccountAmountRequest getSupplierAccountAmountRequest = SupplierAccountServiceStructure.GetSupplierAccountAmountRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode("CNY")
                    .build();
            PaymentCommonGrpc.AccountAmountResponse accountAmount = rpcStub.getSupplierAccountAmount(getSupplierAccountAmountRequest);
            double accountCouponAmount =  (double)accountAmount.getCouponAmount() / FXConstant.HUNDRED; //单位转换
            double accountPrepaidAmount = (double)accountAmount.getPrepaidAmount() / FXConstant.HUNDRED; //单位转换

            double couponTotal = couponAmountUse + adCouponAmountUse;//订单使用返利
            double prepaidTotal = prepaidAmountUse + adPrepaidAmountUse;//订单使用代垫

            //校验账户剩余的返利代垫余额是否充足
            if (couponTotal > accountCouponAmount) {
                return new GongxiaoResult(ErrorCode.SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
            }
            if (prepaidTotal > accountPrepaidAmount) {
                return new GongxiaoResult(ErrorCode.SUPPLIER_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.SUPPLIER_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
            }
            accountCouponAmount = accountCouponAmount - couponTotal;
            accountPrepaidAmount = accountPrepaidAmount - prepaidTotal;
            //设置YH/AD 返利/代垫使用情况 TODO: 在上面验证余额是否充足后，待验证这里status部分的逻辑应不起作用了
            if (status == 1) {
                adCouponAmountUse = couponTotal - couponAmountUse;
                adPrepaidAmountUse = prepaidTotal - prepaidAmountUse;
            }
            if (status == 2) {
                couponAmountUse = couponTotal - adCouponAmountUse;
                prepaidAmountUse = prepaidTotal - adPrepaidAmountUse;
            }
            double totalGuidePrice = 0; //这采购应付总额
            double totalPrivilegeAmount = 0; //优惠总额
            for (ProductItem item : productItems) {

                double purchaseDiscount = item.getDiscountPercent();
                double guidePrice = item.getPurchasePrice() * (1 + purchaseDiscount); //指导价
                totalGuidePrice += (guidePrice * item.getPurchaseNumber()); //累加当前货品的采购总额
                totalPrivilegeAmount += (guidePrice * purchaseDiscount * item.getPurchaseNumber()); //优惠金额
                CalculateProductInfo calculateProductInfo = new CalculateProductInfo();
                calculateProductInfo.setProductID(item.getProductId());
                calculateProductInfo.setProductCode(item.getProductCode());
                calculateProductInfo.setProductName(item.getProductName());
                calculateProductInfo.setGuidePrice(CommonUtil.doubleToString(guidePrice, "0.000000")); //指导价
                calculateProductInfo.setPurchaseNumber(item.getPurchaseNumber()); //采购数量
                calculateProductInfo.setDiscountPercent(CommonUtil.doubleToString(item.getDiscountPercent(), "0.000000")); //折扣比率
                calculateProductInfo.setCouponAmount(CommonUtil.doubleToString(item.getCouponAmount(), "0.00"));
                calculateProductInfo.setPrepaidAmount(CommonUtil.doubleToString(item.getPrepaidAmount(), "0.00"));
                calculateProductInfo.setPurchasePrice(CommonUtil.doubleToString(item.getPurchasePrice(), "0.000000")); //采购价
                calculateProductInfo.setCostPrice(CommonUtil.doubleToString(item.getCostPrice(), "0.000000"));  //成本价
                calculateProductInfo.setFactoryPrice(CommonUtil.doubleToString(item.getFactoryPrice(), "0.000000")); //进货价
                calculateProductInfo.setCashAmount(CommonUtil.doubleToString(item.getCashAmount(), "0.00")); //现金金额 即现金折扣
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
     * [采购单管理] 在采购单表格 点击 "预约入库"，进入"收货通知"页
     * 该接口目标是：通过采购单号获取计划收货单列表 即通过采购订单号，查询对应的商品明细
     *
     * 注：这里理论上支持 页面头部的 筛选条件
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
            List<PlanInboundItem> planInboundItemList = new ArrayList<>(); //返回给前端的结构
            //proto对象转换为java对象
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
     * [采购订单管理]页 获取采购单列表
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

            logger.info("#traceId={}# [IN][getPurchaseList] params: projectId:{}, purchaseOrderNo{}, brandId{}, warehouseId{}, productCode{}, requireArrivalDate{}, arrivalDeadline{}, businessDate{}, pageNumber={}, pageSize={} ",
                    rpcHeader.getTraceId(), projectId, purchaseOrderNo, brandId, warehouseId, productCode, requireArrivalDate, arrivalDeadline, businessDate, pageNumber, pageSize);

            //通过rpc获取目标采购单列表
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

            //proto对象转换为java对象
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
     * [采购单订管理] > [采购单详情]   通过采购单号获取采购详情
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

            //组装返回给前端的数据 单头部分
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

            //组装日记
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

            //组装采购单明细
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
                purchaseItemVo1.setCashAmount(purchaseItemVo.getCashAmount());
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


    /**
     * 把飞利浦的订单导入到XPS平台后，点击 [采购单管理] > [编辑采购单] 进入编辑页面，走该接口拉取订单详情
     * 注1：注解见上面 采购单详情接口
     * 注2：和上面详情接口 主要区别是没有操作日记
     *
     * @param purchaseOrderNumber
     * @param request
     * @return
     * @throws Exception
     */
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
                purchaseItemVo1.setCashAmount(purchaseItemVo.getCashAmount());
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

    /**
     * 把飞利浦的订单导入到XPS平台后，点击 [采购单管理] > [编辑采购单] 进入编辑页面，修改信息后提交订单
     *
     * @param purchaseOrderNo
     * @param warehouseId
     * @param supplierCode
     * @param logisticType
     * @param couponAmountUse
     * @param prepaidAmountUse
     * @param adCouponAmountUse
     * @param adPrepaidAmountUse
     * @param purchaseGuideAmount
     * @param purchaseItemInfoJson
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/putPurchaseEditDetail", method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult putPurchaseEditDetail(String purchaseOrderNo,
                                                String warehouseId,//仓库ID
                                                String supplierCode,//供应商编码
//                                                int paymentMode,//支付方式
                                                int logisticType,//物流方式
                                                double couponAmountUse,//YH使用返利
                                                double prepaidAmountUse,//YH使用代垫
                                                double adCouponAmountUse,//AD使用返利
                                                double adPrepaidAmountUse,//AD使用代垫
                                                double purchaseGuideAmount,//指导金额
                                                String purchaseItemInfoJson,//货品信息
                                                HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        String remark = request.getParameter("remark");
        String arrivalDeadline = request.getParameter("arrivalDeadline");
        String requireArrivalDate = request.getParameter("requireArrivalDate");
        String purchaseType = request.getParameter("purchaseType");
        String businessDate = request.getParameter("businessDate");
        String format = "yyyy-MM-dd";
        //校验 要求到货日期不能为空
        if (requireArrivalDate == null || "".equals(requireArrivalDate)) {
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.REQUIRE_ARRIVAL_DATE_NOT_NULL);
        }
        logger.info("#traceId={}# [IN][putPurchaseEditDetail] params: purchaseOrderNo={} ,warehouseId={}, supplierCode={}," +
                " logisticType={}, couponAmountUse={}, prepaidAmountUse={}, adCouponAmountUse={}, adPrepaidAmountUse={}," +
                " purchaseGuideAmount={}, remark={}, arrivalDeadline{},  requireArrivalDate={}, purchaseType={}, businessDate={}",
                rpcHeader.getTraceId(), purchaseOrderNo, warehouseId, supplierCode, logisticType, couponAmountUse, prepaidAmountUse, adCouponAmountUse,
                adPrepaidAmountUse, purchaseGuideAmount, remark, arrivalDeadline, requireArrivalDate, purchaseType, businessDate);
        try {
            //1. rpc调用返利余额接口 校验返利/代垫余额是否足够
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub supplierAccountServiceBlockingStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            SupplierAccountServiceStructure.GetSupplierAccountAmountRequest getSupplierAccountAmountRequest = SupplierAccountServiceStructure.GetSupplierAccountAmountRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode("CNY")
                    .setProjectId(portalConfig.getTargetProjectId())
                    .build();
            PaymentCommonGrpc.AccountAmountResponse accountAmountResponse = supplierAccountServiceBlockingStub.getSupplierAccountAmount(getSupplierAccountAmountRequest);
            double prepaidAmount = (double) accountAmountResponse.getPrepaidAmount() / FXConstant.HUNDRED;
            double couponAmount = (double) accountAmountResponse.getCouponAmount() / FXConstant.HUNDRED;
            if (couponAmount < couponAmountUse+adCouponAmountUse) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.COUPON_AMOUNT_NOT_ENOUGH);
            }
            if (prepaidAmount < prepaidAmountUse + adPrepaidAmountUse) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PREPAID_AMOUNT_NOT_ENOUGH);
            }
            long projectId = portalUserInfo.getProjectId();

            //2. 组装rpc参数
            //定义更新采购订单数据的请求参数
            PurchaseStructure.UpdatePurchaseOrderReq.Builder updatePurchaseReq
                    = PurchaseStructure.UpdatePurchaseOrderReq.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader);
            //将货品列表json数组的字符串转换为List
            ArrayList<CreatePurchaseItemInfo> purchaseItemInfoList
                    = JSON.parseObject(purchaseItemInfoJson, new TypeReference<ArrayList<CreatePurchaseItemInfo>>() {});
            for (CreatePurchaseItemInfo createPurchaseItemInfo : purchaseItemInfoList) {
                PurchaseStructure.CreatePurchaseItemInfo purchaseItemInfo = PurchaseStructure.CreatePurchaseItemInfo.newBuilder()
                        .setProductID(createPurchaseItemInfo.getProductID())
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

            int purchaseTypes = 0; //采购方式 初始化为值
            if (purchaseType != null && !"".equals(purchaseType)) { //若前端有传该参数 则进行赋值
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
                    .setPurchasePrivilegeAmount(0)
                    .setProjectId(projectId + "")
                    .setBusinessDate(DateUtil.getTime(CommonUtil.StringToDateWithFormat(businessDate, format)));

            updatePurchaseReq.setPurchaseBasicInfo(createPurchaseBasicInfo);

            PurchaseServiceGrpc.PurchaseServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PurchaseServiceGrpc.PurchaseServiceBlockingStub.class);
            PurchaseStructure.UpdatePurchaseOrderResp resp = rpcStub.updatePurchaseOrder(updatePurchaseReq.build());
            int number = resp.getNumber();
            logger.info("#traceId={}# [OUT] putPurchaseEditDetail success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), number);

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 把飞利浦的订单导入到XPS平台后，在未进行"预约入库"操作前 均可作废该订单
     *
     * @param purchaseOrderNumber
     * @param request
     * @return
     */
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
            logger.info("#traceId={}# [OUT] cancelPurchaseOrder success: ", rpcHeader.getTraceId());

            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), number);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

}
