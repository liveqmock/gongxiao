package com.yhglobal.gongxiao.sales.controller;


import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.sales.entity.SalesOrder;
import com.yhglobal.gongxiao.sales.entity.SalesOrderCount;
import com.yhglobal.gongxiao.sales.entity.SalesOrderDetail;
import com.yhglobal.gongxiao.sales.entity.SalesOrderItemInfo;
import com.yhglobal.gongxiao.sales.entity.SalesOrderList;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import static com.yhglobal.gongxiao.constant.ErrorCode.ARGUMENTS_INVALID;

/**
 * 销售单表现层
 *
 * @Author: 葛灿
 */
@Controller
@RequestMapping("/sales")
public class SalesOrderController {
    private static Logger logger = LoggerFactory.getLogger(SalesOrderController.class);

    @Autowired
    private PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象

    /**
     * 获取销售单列表
     *
     * @param orderNo     销售单号
     * @param outDate     出库时间
     * @param createTime  创建时间
     * @param orderStatus 订单状态
     * @param pageSize    条数
     * @param pageNumber  页码
     * @return
     */
    @RequestMapping("/getOrderList")
    @ResponseBody
    public GongxiaoResult selectSalesOrderList(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam(defaultValue = "") String orderNo,
                                               @RequestParam(defaultValue = "") String outDate,
                                               @RequestParam(defaultValue = "") String createTime,
                                               @RequestParam(defaultValue = "0") Integer orderStatus,
                                               int pageSize,
                                               int pageNumber) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            //从session中获取项目id
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectSalesOrderList] params:  salesOrderNo={}, projectId={}, outDate={}, createTime={}, " +
                    "orderStatus={}, pageSize={}, pageRow={}", traceId, orderNo, projectId, outDate, createTime, orderStatus, pageSize, pageNumber);
            SalesOrderServiceStructure.GetListSelectivelyRequest rpcRequest = SalesOrderServiceStructure.GetListSelectivelyRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setOrderNo(orderNo)
                    .setOutDate(outDate)
                    .setCreateTime(createTime)
                    .setOrderStatus(orderStatus)
                    .setPageNum(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            SalesOrderServiceStructure.GetListSelectivelyResponse rpcResponse = rpcStub.getListSelectively(rpcRequest);
            // proto -> java
            SalesOrderList listSelectively = new SalesOrderList();
            PageInfo<SalesOrder> pageInfo = new PageInfo<>();
            List<SalesOrder> list = new LinkedList<>();
            for (SalesOrderServiceStructure.SalesOrderResponse protoObject : rpcResponse.getSalesOrders().getListList()) {
                SalesOrder javaObject = new SalesOrder();
                javaObject.setSalesOrderStatus(protoObject.getSalesOrderStatus());
                javaObject.setSettlementMode(protoObject.getSettlementMode());
                javaObject.setCashAmountDoubleStr(protoObject.getCashAmountDouble());
                javaObject.setSalesOrderNo(protoObject.getSalesOrderNo());
                int paymentDays = protoObject.getPaymentDays();
                javaObject.setPaymentDays(paymentDays == 0 ? null : paymentDays);
                javaObject.setDistributorName(protoObject.getDistributorName());
                javaObject.setTotalOrderAmountDouble(protoObject.getTotalOrderAmountDouble());
                javaObject.setTotalQuantity(protoObject.getTotalQuantity());
                javaObject.setUnhandledQuantity(protoObject.getUnhandledQuantity());
                javaObject.setCreateTime(GrpcCommonUtil.getJavaParam(protoObject.getCreateTime()));
                javaObject.setPaidTime(GrpcCommonUtil.getJavaParam(protoObject.getPaidTime()));
                list.add(javaObject);
            }
            LinkedList<SalesOrderCount> salesOrderCounts = new LinkedList<>();
            for (SalesOrderServiceStructure.SalesOrderCount protoObject : rpcResponse.getSalesOrderCountsList()) {
                SalesOrderCount javaObject = new SalesOrderCount();
                javaObject.setCount(protoObject.getCount());
                javaObject.setStatus(protoObject.getSattus());
                salesOrderCounts.add(javaObject);
            }
            listSelectively.setCountMap(salesOrderCounts);
            pageInfo.setList(list);
            pageInfo.setPageSize(rpcResponse.getSalesOrders().getPageSize());
            pageInfo.setPageNum(rpcResponse.getSalesOrders().getPageNum());
            pageInfo.setTotal(rpcResponse.getSalesOrders().getTotal());
            listSelectively.setSalesOrders(pageInfo);

            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), listSelectively);
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
    public GongxiaoResult getSalesOrderWhenCheck(HttpServletRequest request, HttpServletResponse response, String salesOrderNo) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderWhenCheck] params:  salesOrderNo={}", traceId, salesOrderNo);
            //从session中获取项目id
            long projectId = portalUserInfo.getProjectId();

            SalesOrderServiceStructure.CommonSalesOrderRequest rpcRequest =
                    SalesOrderServiceStructure.CommonSalesOrderRequest.newBuilder()
                            .setProjectId(projectId)
                            .setRpcHeader(rpcHeader)
                            .setSalesOrderNo(salesOrderNo).build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            SalesOrderServiceStructure.GetOrderByOrderNoResponse rpcResponse = rpcStub.getOrderByOrderNo(rpcRequest);
            // proto -> java
            SalesOrderDetail javaModel = new SalesOrderDetail();
            javaModel.setSalesOrderNo(rpcResponse.getSalesOrderNo());
            javaModel.setSalesContactNo(rpcResponse.getSalesContactNo());
            javaModel.setSalesOrderStatus(rpcResponse.getSalesOrderStatus());
            javaModel.setSalesOrderStatusString(rpcResponse.getSalesOrderStatusString());
            javaModel.setTotalOrderAmountDouble(rpcResponse.getTotalOrderAmountDouble());
            javaModel.setCouponAmountDouble(rpcResponse.getCouponAmountDouble());
            javaModel.setPrepaidAmountDouble(rpcResponse.getPrepaidAmountDouble());
            javaModel.setPrestoredAmountDouble(rpcResponse.getPrestoredAmountDouble());
            javaModel.setCashAmountDouble(rpcResponse.getCashAmountDouble());
            javaModel.setRecipientCompany(rpcResponse.getRecipientCompany());
            javaModel.setShippingAddress(rpcResponse.getShippingAddress());
            javaModel.setProvinceId(rpcResponse.getProvinceId());
            javaModel.setProvinceName(rpcResponse.getProvinceName());
            javaModel.setCityId(rpcResponse.getCityId());
            javaModel.setCityName(rpcResponse.getCityName());
            javaModel.setDistrictId(rpcResponse.getDistrictId());
            javaModel.setDistrictName(rpcResponse.getDistrictName());
            javaModel.setRecipientName(rpcResponse.getRecipientName());
            javaModel.setRecipientMobile(rpcResponse.getRecipientMobile());
            javaModel.setOutBoundTime(GrpcCommonUtil.getJavaParam(rpcResponse.getOutBoundTimeStr()));
            javaModel.setCreateTime(GrpcCommonUtil.getJavaParam(rpcResponse.getCreateTimeStr()));
            javaModel.setOrderCheckTime(GrpcCommonUtil.getJavaParam(rpcResponse.getOrderCheckTimeStr()));
            javaModel.setPaidTime(GrpcCommonUtil.getJavaParam(rpcResponse.getPaidTimeStr()));
            javaModel.setInformWarehouseTime(GrpcCommonUtil.getJavaParam(rpcResponse.getInformWarehouseTimeStr()));
            javaModel.setSighTime(GrpcCommonUtil.getJavaParam(rpcResponse.getSignTimeStr()));
            javaModel.setSupplierDiscountAmountDouble(rpcResponse.getSupplierDiscountAmountDouble());
            javaModel.setYhDiscountAmountDouble(rpcResponse.getYhDiscountAmountDouble());
            javaModel.setDistributorCouponAmountDouble(rpcResponse.getDistributorCouponAmountDouble());
            javaModel.setDistributorPrepaidAmountDouble(rpcResponse.getDistributorPrepaidAmountDouble());
            List<TraceLog> javaTraceLog = GrpcCommonUtil.getJavaTraceLog(rpcResponse.getTraceLogList());
            javaModel.setTraceLog(javaTraceLog);
            LinkedList<SalesOrderItemInfo> salesOrderItemInfos = new LinkedList<>();
            for (SalesOrderServiceStructure.SalesOrderItemsResponse protoObject : rpcResponse.getItemsList()) {
                SalesOrderItemInfo javaObject = new SalesOrderItemInfo();
                javaObject.setProductName(protoObject.getProductName());
                javaObject.setProductCode(protoObject.getProductCode());
                javaObject.setTotalQuantity(protoObject.getTotalQuantity());
                javaObject.setCurrencyCode(protoObject.getCurrencyCode());
                javaObject.setTotalQuantity(protoObject.getTotalQuantity());
                javaObject.setGuidePriceDouble(protoObject.getGuidePriceDouble());
                javaObject.setSupplierDiscountPercentDouble(protoObject.getSupplierDiscountPercentDouble());
                javaObject.setSupplierDiscountAmountDouble(protoObject.getSupplierDiscountAmountDouble());
                javaObject.setYhDiscountPercentDouble(protoObject.getYhDiscountPercentDouble());
                javaObject.setYhDiscountAmountDouble(protoObject.getYhDiscountAmountDouble());
                javaObject.setWholesalePriceDouble(protoObject.getWholesalePriceDouble());
                javaObject.setTotalOrderAmountDouble(protoObject.getTotalOrderAmountDouble());
                salesOrderItemInfos.add(javaObject);
            }
            javaModel.setSalesOrderItemInfos(salesOrderItemInfos);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), javaModel);
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
    public GongxiaoResult getSalesOrderWhenView(HttpServletRequest request, HttpServletResponse response, String salesOrderNo) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderWhenCheck] params:  salesOrderNo={}", traceId, salesOrderNo);
            //从session中获取项目id
            long projectId = portalUserInfo.getProjectId();

            SalesOrderServiceStructure.CommonSalesOrderRequest rpcRequest = SalesOrderServiceStructure.CommonSalesOrderRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setSalesOrderNo(salesOrderNo).build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            SalesOrderServiceStructure.GetOrderByOrderNoResponse rpcResponse = rpcStub.getOrderDetailBySalesOrderNo(rpcRequest);
            // proto -> java
            SalesOrderDetail javaModel = new SalesOrderDetail();
            javaModel.setSalesOrderNo(rpcResponse.getSalesOrderNo());
            javaModel.setSalesContactNo(rpcResponse.getSalesContactNo());
            javaModel.setSalesOrderStatus(rpcResponse.getSalesOrderStatus());
            javaModel.setSalesOrderStatusString(rpcResponse.getSalesOrderStatusString());
            javaModel.setTotalOrderAmountDouble(rpcResponse.getTotalOrderAmountDouble());
            javaModel.setCouponAmountDouble(rpcResponse.getCouponAmountDouble());
            javaModel.setPrepaidAmountDouble(rpcResponse.getPrepaidAmountDouble());
            javaModel.setPrestoredAmountDouble(rpcResponse.getPrestoredAmountDouble());
            javaModel.setCashAmountDouble(rpcResponse.getCashAmountDouble());
            javaModel.setRecipientCompany(rpcResponse.getRecipientCompany());
            javaModel.setShippingAddress(rpcResponse.getShippingAddress());
            javaModel.setProvinceId(rpcResponse.getProvinceId());
            javaModel.setProvinceName(rpcResponse.getProvinceName());
            javaModel.setCityId(rpcResponse.getCityId());
            javaModel.setCityName(rpcResponse.getCityName());
            javaModel.setDistrictId(rpcResponse.getDistrictId());
            javaModel.setDistrictName(rpcResponse.getDistrictName());
            javaModel.setRecipientName(rpcResponse.getRecipientName());
            javaModel.setRecipientMobile(rpcResponse.getRecipientMobile());
            javaModel.setOutBoundTime(GrpcCommonUtil.getJavaParam(rpcResponse.getOutBoundTimeStr()));
            javaModel.setCreateTime(GrpcCommonUtil.getJavaParam(rpcResponse.getCreateTimeStr()));
            javaModel.setOrderCheckTime(GrpcCommonUtil.getJavaParam(rpcResponse.getOrderCheckTimeStr()));
            javaModel.setPaidTime(GrpcCommonUtil.getJavaParam(rpcResponse.getPaidTimeStr()));
            javaModel.setInformWarehouseTime(GrpcCommonUtil.getJavaParam(rpcResponse.getInformWarehouseTimeStr()));
            javaModel.setSighTime(GrpcCommonUtil.getJavaParam(rpcResponse.getSignTimeStr()));
            javaModel.setSupplierDiscountAmountDouble(rpcResponse.getSupplierDiscountAmountDouble());
            javaModel.setYhDiscountAmountDouble(rpcResponse.getYhDiscountAmountDouble());
            javaModel.setDistributorCouponAmountDouble(rpcResponse.getDistributorCouponAmountDouble());
            javaModel.setDistributorPrepaidAmountDouble(rpcResponse.getDistributorPrepaidAmountDouble());
            List<TraceLog> javaTraceLog = GrpcCommonUtil.getJavaTraceLog(rpcResponse.getTraceLogList());
            javaModel.setTraceLog(javaTraceLog);
            LinkedList<SalesOrderItemInfo> salesOrderItemInfos = new LinkedList<>();
            for (SalesOrderServiceStructure.SalesOrderItemsResponse protoObject : rpcResponse.getItemsList()) {
                SalesOrderItemInfo javaObject = new SalesOrderItemInfo();
                javaObject.setProductName(protoObject.getProductName());
                javaObject.setProductCode(protoObject.getProductCode());
                javaObject.setTotalQuantity(protoObject.getTotalQuantity());
                javaObject.setCurrencyCode(protoObject.getCurrencyCode());
                javaObject.setTotalQuantity(protoObject.getTotalQuantity());
                javaObject.setGuidePriceDouble(protoObject.getGuidePriceDouble());
                javaObject.setSupplierDiscountPercentDouble(protoObject.getSupplierDiscountPercentDouble());
                javaObject.setSupplierDiscountAmountDouble(protoObject.getSupplierDiscountAmountDouble());
                javaObject.setYhDiscountPercentDouble(protoObject.getYhDiscountPercentDouble());
                javaObject.setYhDiscountAmountDouble(protoObject.getYhDiscountAmountDouble());
                javaObject.setWholesalePriceDouble(protoObject.getWholesalePriceDouble());
                javaObject.setTotalOrderAmountDouble(protoObject.getTotalOrderAmountDouble());
                salesOrderItemInfos.add(javaObject);
            }
            javaModel.setSalesOrderItemInfos(salesOrderItemInfos);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), javaModel);
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
    public GongxiaoResult changeSettlementMode(HttpServletRequest request, HttpServletResponse response,
                                               String[] salesOrderNoList,
                                               int days,
                                               @RequestParam(defaultValue = "") String remark) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            //从session中获取项目id
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][changeSettlementMode] params: salesOrderNo={}, days={}, remark={}", traceId, String.valueOf(salesOrderNoList), days, remark);
            if (days <= 0) {
                gongxiaoResult = new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                return gongxiaoResult;
            }
            SalesOrderServiceStructure.ChangeSettlementModeRequest.Builder builder = SalesOrderServiceStructure.ChangeSettlementModeRequest.newBuilder().setProjectId(projectId).setRpcHeader(rpcHeader).setDays(days).setRemark(remark);
            for (String salesOrderNo : salesOrderNoList) {
                builder.addSalesOrderNo(salesOrderNo);
            }
            SalesOrderServiceStructure.ChangeSettlementModeRequest rpcRequest = builder.build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.changeSettlementMode(rpcRequest);
            if (rpcResponse.getReturnCode() == 0) {
                //修改成功
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] change mode success. salesOrderNoList={}, days={}", traceId, String.valueOf(salesOrderNoList), days);
            } else {
                //修改失败
                gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
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
    public GongxiaoResult updateRecipientInfo(HttpServletRequest request, HttpServletResponse response,
                                              String salesOrderNo,
                                              String recipientName,
                                              String recipientMobile,
                                              String provinceId,
                                              String provinceName,
                                              String cityId,
                                              String cityName,
                                              String districtId,
                                              String districtName,
                                              String shippingAddress,
                                              String recipientCompany) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            //从session中获取项目id
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][updateRecipientInfo] params:  salesOrderNo={}", traceId, salesOrderNo);
            SalesOrderServiceStructure.UpdateRecipientInfoRequest rpcRequest = SalesOrderServiceStructure.UpdateRecipientInfoRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setOrderNo(salesOrderNo)
                    .setRecipientName(recipientName)
                    .setRecipientMobile(recipientMobile)
                    .setProvinceId(provinceId)
                    .setProvinceName(provinceName)
                    .setCityId(cityId)
                    .setCityName(cityName)
                    .setDistrictId(districtId)
                    .setDistrictName(districtName)
                    .setShippingAddress(shippingAddress)
                    .setRecipientCompany(recipientCompany).build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.updateRecipientInfo(rpcRequest);
            if (rpcResponse.getReturnCode() == 0) {
                //修改成功
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] change mode success. salesOrderNo={}", traceId, salesOrderNo);
            } else {
                //修改失败
                gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
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
     * @param salesContractNo     销售合同号
     * @return
     */
    @RequestMapping("/check")
    @ResponseBody
    public GongxiaoResult approveSalesOrder(HttpServletRequest request, HttpServletResponse response,
                                            String salesOrderNo,
                                            double couponAmountDouble,
                                            double prepaidAmountDouble,
                                            String salesContractNo) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            //从session中获取项目id
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][approveSalesOrder] params:  salesOrderNo={}, couponAmountDouble={}, prepaidAmountDouble={}, cashAmountDouble={}", traceId, salesOrderNo, couponAmountDouble, prepaidAmountDouble, 0);
            SalesOrderServiceStructure.ApproveSalesOrderRequest rpcRequest = SalesOrderServiceStructure.ApproveSalesOrderRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setSalesOrderNo(salesOrderNo)
                    .setCouponAmountDouble(couponAmountDouble)
                    .setSalesContractNo(salesContractNo)
                    .setPrepaidAmountDouble(prepaidAmountDouble).build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.approveSalesOrder(rpcRequest);

            if (rpcResponse.getReturnCode() == 0) {
                //修改成功
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] approve success. salesOrderNo={}", traceId, salesOrderNo);
            } else {
                //修改失败
                gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
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
            //从session中获取项目id
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][rejectSalesOrder] params:  salesOrderNo={}", traceId, salesOrderNo);
            SalesOrderServiceStructure.CommonSalesOrderRequest rpcRequest = SalesOrderServiceStructure.CommonSalesOrderRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setSalesOrderNo(salesOrderNo).build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.rejectSalesOrder(rpcRequest);
            if (rpcResponse.getReturnCode() == 0) {
                //修改成功
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] reject success. salesOrderNo={}", traceId, salesOrderNo);
            } else {
                //修改失败
                gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
                logger.info("#traceId={}# [OUT] reject fail. salesOrderNo={}", traceId, salesOrderNo);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
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
            //从session中获取项目id
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][cancelApproveSalesOrder] params:  salesOrderNo={}", traceId, salesOrderNo);
            SalesOrderServiceStructure.CommonSalesOrderRequest rpcRequest = SalesOrderServiceStructure.CommonSalesOrderRequest.newBuilder().setProjectId(projectId).setRpcHeader(rpcHeader).setSalesOrderNo(salesOrderNo).build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.cancelSalesOrderApprove(rpcRequest);
            if (rpcResponse.getReturnCode() == 0) {
                //修改成功
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] cancel approve success. salesOrderNo={}", traceId, salesOrderNo);
            } else {
                //修改失败
                gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
                logger.info("#traceId={}# [OUT] cancel approve fail. salesOrderNo={}", traceId, salesOrderNo);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


}
