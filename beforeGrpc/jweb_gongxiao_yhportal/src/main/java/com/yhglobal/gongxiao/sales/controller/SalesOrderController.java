package com.yhglobal.gongxiao.sales.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorCouponSetting;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorCouponSettingService;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.AvailableVirtualAmount;
import com.yhglobal.gongxiao.payment.service.DistributorAccountService;
import com.yhglobal.gongxiao.sales.bo.SalesOrderDetail;
import com.yhglobal.gongxiao.sales.bo.SalesOrderItemInfo;
import com.yhglobal.gongxiao.sales.bo.SalesOrderList;
import com.yhglobal.gongxiao.sales.model.SalesMarketingChannelEnum;
import com.yhglobal.gongxiao.sales.model.SalesPaymentChannelEnum;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.service.PlanSaleItemService;
import com.yhglobal.gongxiao.sales.service.PlanSaleService;
import com.yhglobal.gongxiao.sales.service.SalesOrderService;
import com.yhglobal.gongxiao.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 销售单表现层
 *
 * @Author: 葛灿
 */
@Controller
@RequestMapping("/sales")
public class SalesOrderController {
    private static Logger logger = LoggerFactory.getLogger(SalesOrderController.class);
    @Reference
    private SalesOrderService salesOrderService;

    @Reference
    PlanSaleItemService planSaleItemService;

    @Reference
    PlanSaleService planSaleService;

    @Reference
    DistributorAccountService distributorAccountService;

    @Reference
    DistributorCouponSettingService distributorCouponSettingService;

    @Autowired
    private PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象

    /**
     * 获取销售单列表
     *
     * @param orderNo     销售单号
     * @param projectId   项目id
     * @param outDate     出库时间
     * @param createTime  创建时间
     * @param orderStatus 订单状态
     * @param pageSize    条数
     * @param pageNumber  页码
     * @return
     */
    @RequestMapping("/getOrderList")
    @ResponseBody
    public GongxiaoResult<SalesOrderList> selectSalesOrderList(HttpServletRequest request, HttpServletResponse response,
                                                               String orderNo, Long projectId,
                                                               String outDate, String createTime,
                                                               Integer orderStatus, int pageSize,
                                                               int pageNumber) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            Date outDateTime = null;
            if (outDate != null && !"".equals(outDate)) {
                outDateTime = DateUtil.stringToDate(outDate, "yyyy-MM-dd");
            }
            Date createTimeDate = null;
            if (createTime != null && !"".equals(createTime)) {
                createTimeDate = DateUtil.stringToDate(createTime, "yyyy-MM-dd");
            }
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectSalesOrderList] params:  salesOrderNo={}, projectId={}, outDate={}, createTime={}, " +
                    "orderStatus={}, pageSize={}, pageRow={}", traceId, orderNo, projectId, outDateTime, createTimeDate, orderStatus, pageSize, pageNumber);
            SalesOrderList listSelectively = salesOrderService.getListSelectively(rpcHeader, projectId, orderNo, outDateTime, createTimeDate, orderStatus, pageSize, pageNumber);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), listSelectively);
            logger.info("#traceId={}# [OUT] get list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 审核时
     * 获取销售单详细信息
     *
     * @param salesOrderNo 销售单号
     * @return
     */
    @RequestMapping("/getOrderInfo")
    @ResponseBody
    public GongxiaoResult<SalesOrderDetail> getSalesOrderWhenCheck(HttpServletRequest request, HttpServletResponse response, String salesOrderNo) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderWhenCheck] params:  salesOrderNo={}", traceId, salesOrderNo);
            SalesOrderDetail orderByOrderNo = salesOrderService.getOrderByOrderNo(rpcHeader, salesOrderNo);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), orderByOrderNo);
            logger.info("#traceId={}# [OUT] get sales order success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 正常情况获取销售单详细信息
     *
     * @param salesOrderNo 销售单号
     * @return
     */
    @RequestMapping("/orderInfo")
    @ResponseBody
    public GongxiaoResult<SalesOrderDetail> getSalesOrderWhenView(HttpServletRequest request, HttpServletResponse response, String salesOrderNo) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderWhenCheck] params:  salesOrderNo={}", traceId, salesOrderNo);
            SalesOrderDetail orderByOrderNo = salesOrderService.getOrderDetailBySalesOrderNo(rpcHeader, salesOrderNo);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), orderByOrderNo);
            logger.info("#traceId={}# [OUT] get sales order success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 更改销售单结算方式
     *
     * @param days 结算模式 1、单结 2、账结 （对应枚举类）
     * @return
     */
    @RequestMapping("/settlementMode")
    @ResponseBody
    public GongxiaoResult<SalesOrderDetail> changeSettlementMode(HttpServletRequest request, HttpServletResponse response, String[] salesOrderNoList, int days, String remark) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][changeSettlementMode] params: salesOrderNo={}, days={}, remark={}", traceId, String.valueOf(salesOrderNoList), days, remark);
            RpcResult rpcResult = salesOrderService.changeSettlementMode(rpcHeader, salesOrderNoList, days, remark);
            if (rpcResult.getSuccess()) {
                //修改成功
                gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] change mode success. salesOrderNoList={}, days={}", traceId, String.valueOf(salesOrderNoList), days);
            } else {
                //修改失败
                gongxiaoResult = new GongxiaoResult<>(rpcResult.getCode(), rpcResult.getMessage());
                logger.info("#traceId={}# [OUT] change mode fail. salesOrderNo={}, days={}", traceId, salesOrderNoList, days);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 改变销售单收件人信息
     *
     * @param salesOrderNo     销售单号
     * @param recipientName    收件人名称
     * @param recipientMobile  收件人电话
     * @param provinceId       省id
     * @param provinceName     省
     * @param cityId           市id
     * @param cityName         市
     * @param districtId       区id
     * @param districtName     区
     * @param shippingAddress  收件地址
     * @param recipientCompany 收件公司
     * @return int 更新条数
     */
    @RequestMapping("/update")
    @ResponseBody
    public GongxiaoResult<SalesOrderDetail> updateRecipientInfo(HttpServletRequest request, HttpServletResponse response,
                                                                String salesOrderNo, String recipientName,
                                                                String recipientMobile, String provinceId,
                                                                String provinceName,
                                                                String cityId, String cityName,
                                                                String districtId, String districtName,
                                                                String shippingAddress, String recipientCompany) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][updateRecipientInfo] params:  salesOrderNo={}", traceId, salesOrderNo);
            RpcResult rpcResult = salesOrderService.updateRecipientInfo(rpcHeader, salesOrderNo, recipientName, recipientMobile, provinceId, provinceName, cityId, cityName, districtId, districtName, shippingAddress, recipientCompany);
            if (rpcResult.getSuccess()) {
                //修改成功
                gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] change mode success. salesOrderNo={}", traceId, salesOrderNo);
            } else {
                //修改失败
                gongxiaoResult = new GongxiaoResult<>(rpcResult.getCode(), rpcResult.getMessage());
                logger.info("#traceId={}# [OUT] change mode fail. salesOrderNo={}", traceId, salesOrderNo);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 订单审核通过
     *
     * @param response
     * @param salesOrderNo        销售单号
     * @param couponAmountDouble  使用返利金额
     * @param prepaidAmountDouble 使用代垫金额
     * @return
     */
    @RequestMapping("/check")
    @ResponseBody
    public GongxiaoResult approveSalesOrder(HttpServletRequest request, HttpServletResponse response,
                                            String salesOrderNo, double couponAmountDouble,
                                            double prepaidAmountDouble) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][approveSalesOrder] params:  salesOrderNo={}, couponAmountDouble={}, prepaidAmountDouble={}, cashAmountDouble={}", traceId, salesOrderNo, couponAmountDouble, prepaidAmountDouble, 0);
            RpcResult rpcResult = salesOrderService.approveSalesOrder(rpcHeader, salesOrderNo, couponAmountDouble, prepaidAmountDouble);
            if (rpcResult.getSuccess()) {
                //修改成功
                gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] approve success. salesOrderNo={}", traceId, salesOrderNo);
            } else {
                //修改失败
                gongxiaoResult = new GongxiaoResult<>(rpcResult.getCode(), rpcResult.getMessage());
                logger.info("#traceId={}# [OUT] approve fail. salesOrderNo={}", traceId, salesOrderNo);
            }
            logger.info("#traceId={}# [OUT] approved.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 订单驳回
     *
     * @param response
     * @param salesOrderNo 销售单号
     * @return
     */
    @RequestMapping("/reject")
    @ResponseBody
    public GongxiaoResult rejectSalesOrder(HttpServletRequest request, HttpServletResponse response,
                                           String salesOrderNo) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][rejectSalesOrder] params:  salesOrderNo={}", traceId, salesOrderNo);
            RpcResult rpcResult = salesOrderService.rejectSalesOrder(rpcHeader, salesOrderNo);
            if (rpcResult.getSuccess()) {
                //修改成功
                gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] reject success. salesOrderNo={}", traceId, salesOrderNo);
            } else {
                //修改失败
                gongxiaoResult = new GongxiaoResult<>(rpcResult.getCode(), rpcResult.getMessage());
                logger.info("#traceId={}# [OUT] reject fail. salesOrderNo={}", traceId, salesOrderNo);
            }
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
            logger.info("#traceId={}# [rejectSalesOrder][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 订单取消审核
     *
     * @param response
     * @param salesOrderNo 销售单号
     * @return
     */
    @RequestMapping("/cancelApprove")
    @ResponseBody
    public GongxiaoResult cancelApproveSalesOrder(HttpServletRequest request, HttpServletResponse response,
                                                  String salesOrderNo) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][cancelApproveSalesOrder] params:  salesOrderNo={}", traceId, salesOrderNo);
            RpcResult rpcResult = salesOrderService.cancelSalesOrderApprove(rpcHeader, salesOrderNo);
            if (rpcResult.getSuccess()) {
                //修改成功
                gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] cancel approve success. salesOrderNo={}", traceId, salesOrderNo);
            } else {
                //修改失败
                gongxiaoResult = new GongxiaoResult<>(rpcResult.getCode(), rpcResult.getMessage());
                logger.info("#traceId={}# [OUT] cancel approve fail. salesOrderNo={}", traceId, salesOrderNo);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 新建销售单
     *
     * @param salesContactNo   销售合同号
     * @param projectId        项目id
     * @param projectName      项目名称
     * @param distributorId    经销商id
     * @param distributorName  经销商名称
     * @param shippingAddress  经销商的收货地址
     * @param provinceId       省id
     * @param provinceName     省
     * @param cityId           市id
     * @param cityName         市
     * @param districtId       区id
     * @param districtName     区
     * @param recipientName    收件人名字
     * @param recipientMobile  收件人手机号
     * @param currencyId       结算货币ID
     * @param currencyCode     结算货币码
     * @param prestoredAmount  预付金额
     * @param couponAmount     返利金额
     * @param prepaidAmount    代垫金额
     * @param totalOrderAmount 总金额
     * @param totalCash        现金金额
     * @param createTimeString 交易时间
     * @param itemsInfo        货品信息(Json 页面只用传 productCode、totalQuantity、totalOrderAmountDouble)
     * @return
     */
    @RequestMapping("/createOrder")
    @ResponseBody
    public GongxiaoResult createSalesOrder(HttpServletRequest request, HttpServletResponse response,
                                           String salesContactNo,
                                           Long projectId, String projectName,
                                           Long distributorId, String distributorName,
                                           String shippingAddress,
                                           String provinceId, String provinceName,
                                           String cityId, String cityName,
                                           String districtId, String districtName, String recipientName,
                                           String recipientMobile, @RequestParam(defaultValue = "0") String currencyId,
                                           @RequestParam(defaultValue = "CNY") String currencyCode,
                                           @RequestParam(defaultValue = "0") double prestoredAmount,
                                           double couponAmount,
                                           double prepaidAmount, double totalOrderAmount, double totalCash,
                                           String createTimeString,
                                           String itemsInfo
    ) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String salesOrderNo = DateTimeIdGenerator.nextId(BizNumberType.SALES_ORDER_NO);
            logger.info("#traceId={}# [IN][createSalesOrder] params:  salesOrderNo={}", traceId, salesOrderNo);
            List<SalesOrderItemInfo> salesOrderItemInfos = JSON.parseArray(itemsInfo, SalesOrderItemInfo.class);
            //插入销售单
            Date createTime = DateUtil.stringToDate(createTimeString);
            RpcResult rpcResult = salesOrderService.createSalesOrder(rpcHeader,
                    salesOrderNo, salesContactNo, SalesMarketingChannelEnum.WEB.getStatus(), SalesPaymentChannelEnum.OFFLINE_BANK.getStatus(), projectId,
                    projectName, distributorId, distributorName,
                    shippingAddress, provinceId, provinceName, cityId, cityName, districtId, districtName,
                    recipientName, recipientMobile, currencyId, currencyCode, prestoredAmount, couponAmount, prepaidAmount,
                    totalOrderAmount, totalCash, createTime, salesOrderItemInfos
            );
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
            logger.info("#traceId={}# [OUT] create success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 预售商品列表
     *
     * @param projectId          项目ID
     * @param distributorId      经销商ID
     * @param productCode        货品ID
     * @param businessDateString 业务发生时间
     * @return
     */
    @RequestMapping("/planItems")
    @ResponseBody
    public GongxiaoResult selectPlanItems(HttpServletRequest request, HttpServletResponse response,
                                          String projectId, String distributorId, String productCode, String businessDateString, @RequestParam(defaultValue = "1") int pageNumber, int pageSize) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectPlanItems] params:  salesOrderNo={}, projectId={},  distributorId={},  productCode={},  businessDate={}",
                    traceId, projectId, distributorId, productCode, businessDateString);
            Date bussinessDate = DateUtil.stringToDate(businessDateString);
            PageInfo<SalesPlanItem> salesPlanItemPageInfo = planSaleItemService.selectSalePlanItemListConditions(rpcHeader, projectId, distributorId, productCode, bussinessDate, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), salesPlanItemPageInfo);
            logger.info("#traceId={}# [OUT] select items success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 计算新增销售单
     *
     * @param couponAmountDouble  返利余额
     * @param prepaidAmountDouble 代垫余额
     * @param couponRateDouble    返利可使用比例
     * @param prepaidRateDouble   代垫可使用比例
     * @param itemsJson           预售商品信息
     * @return
     */
    @RequestMapping("/addCalculate")
    @ResponseBody
    public GongxiaoResult addCalculate(HttpServletRequest request, HttpServletResponse response,
                                       double couponAmountDouble, double prepaidAmountDouble,
                                       double couponRateDouble, double prepaidRateDouble,
                                       String itemsJson) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<SalesPlanItem> salesPlanItems = JSON.parseArray(itemsJson, SalesPlanItem.class);
            logger.info("#traceId={}# [IN][addCalculate] params: salesPlanItems.size()={}",
                    traceId, salesPlanItems.size());
            //销售指导金额    销售金额    销售应收金额
            long salesGuideSubtotal = 0;
            long salesSubtotal = 0;
            long salesShouldReceiveSubtotal = 0;
            for (SalesPlanItem item : salesPlanItems) {
                long wholesalePrice = (long) (item.getWholesalePriceDouble() * 1000);
                int onSaleQuantity = item.getOnSaleQuantity();
                long customerDiscountAmount = (long) (item.getCustomerDiscountAmountDouble() * 1000);
                //销售指导金额 = 出货价 * 销售数量
                long salesGuideAmount = wholesalePrice * onSaleQuantity;
                salesGuideSubtotal += salesGuideAmount;
                //销售金额 = 销售指导金额 - 客户优惠金额
                long salesAmount = salesGuideAmount - customerDiscountAmount;
                salesSubtotal += salesAmount;
                //销售应收金额 = 销售金额 - 使用折扣金额（供应商支持金+越海支持金）
                long salesShouldReceiveAmount = (long) (salesAmount * (10000 - item.getTotalDiscountDouble() * 100) / 10000);
                salesShouldReceiveSubtotal += salesShouldReceiveAmount;
                item.setSubtotalDouble(salesAmount / 1000.0);
            }
            AvailableVirtualAmount availableVirtualAmount = new AvailableVirtualAmount();
            //返利金额 = Math.min( 返利余额，销售应收金额*返利可使用比例)
            long couponAmount = (long) (couponAmountDouble * 1000);
            long couponMostUsedAmount = (long) ((salesShouldReceiveSubtotal * couponRateDouble) / 100);
            //代垫金额 = Math.min( 代垫余额，销售应收金额*代垫可使用比例)
            long prepaidAmount = (long) (prepaidAmountDouble * 1000);
            long prepaidMostUsedAmount = (long) ((salesShouldReceiveSubtotal * prepaidRateDouble) / 100);
            long usedCoupon = Math.min(couponAmount, couponMostUsedAmount);
            long usedPrepaid = Math.min(prepaidAmount, prepaidMostUsedAmount);
            //实际出货金额 = 销售应收金额 - 使用返利 - 使用代垫
            long salesExportSubtotal = salesShouldReceiveSubtotal - usedCoupon - usedPrepaid;
            //组装模型
            availableVirtualAmount.setUsedCoupon(usedCoupon / 1000.0);
            availableVirtualAmount.setUsedPrepaid(usedPrepaid / 1000.0);
            availableVirtualAmount.setSalesGuideSubtotal(salesGuideSubtotal / 1000.0);
            availableVirtualAmount.setSalesSubtotal(salesSubtotal / 1000.0);
            availableVirtualAmount.setSalesShouldReceiveSubtotal(salesShouldReceiveSubtotal / 1000.0);
            availableVirtualAmount.setSalesExportSubtotal(salesExportSubtotal / 1000.0);
            availableVirtualAmount.setSalesPlanItems(salesPlanItems);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), availableVirtualAmount);
            logger.info("#traceId={}# calculating finished.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;

    }

}
