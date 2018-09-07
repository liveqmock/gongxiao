package com.yhglobal.gongxiao.distributor.purchase.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.distributor.purchase.bean.*;
import com.yhglobal.gongxiao.distributor.util.DistributorPortalTraceIdGenerator;

import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemServiceGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure;

import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure;
import com.yhglobal.gongxiao.util.DateUtil;

import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.NumberFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Description:
 * @author: LUOYI
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);


    @Autowired
    private PortalConfig portalConfig;


    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象

    /**
     * ADPortal 商品查询页面 > 可下单产品列表
     *
     * @param request
     * @param response
     * @param queryStr
     * @return
     */
    @RequestMapping("/getsProductList")
    @ResponseBody
    public GongxiaoResult getProductList(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "") String queryStr) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            // session中获取信息
            long projectId = portalUserInfo.getProjectId();
            long distributorId = portalUserInfo.getDistributorId();
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProductList] params:  queryStr={} ", traceId, queryStr);
            PlanSaleItemStructure.SearchProductByCustomerReq rpcReq = PlanSaleItemStructure.SearchProductByCustomerReq.newBuilder()
                    .setDistributorId(distributorId)
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setQueryStr(queryStr)
                    .build();
            PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub stub = RpcStubStore.getRpcStub(PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub.class);
            PlanSaleItemStructure.SearchProductByCustomerResp rpcResp = stub.searchProductByCustomer(rpcReq);
            List<ProductInfo> productList = new ArrayList<>();
            List<PlanSaleItemStructure.SalesPlanItem> itemList = rpcResp.getSalesPlanItemListList();
            for (PlanSaleItemStructure.SalesPlanItem item : itemList) {
                if (item.getUnallocatedQuantity() == 0) {
                    continue;
                }
                ProductInfo productInfo = new ProductInfo();
                productInfo.setProductCode(item.getProductModle());
                productInfo.setProductId(item.getProductId());
                productInfo.setProductName(item.getProductName());
                productList.add(productInfo);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), productList);
            logger.info("#traceId={}# [getProductList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(), ErrorCode.UNKNOWN_ERROR.getMessage());
        }
        return gongxiaoResult;
    }

    /**
     * ADPortal 商品查询页面 > 可下单产品列表中点添加按钮 则到server获取产品信息
     *
     * @param request
     * @param response
     * @param productId
     * @return
     */
    @RequestMapping("/getProductInfo")
    @ResponseBody
    public GongxiaoResult getProductInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "0") Long productId) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProductInfo] params:  productId={} ", traceId, productId);
            // session中获取信息
            long projectId = portalUserInfo.getProjectId();
            long distributorId = portalUserInfo.getDistributorId();


            PlanSaleItemStructure.GetCustomerPlanInfoReq rpcReq = PlanSaleItemStructure.GetCustomerPlanInfoReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setDistributorId(distributorId + "")
                    .setProjectId(projectId + "")
                    .setProductId(productId + "")
                    .setBusinessDate(System.currentTimeMillis())
                    .build();
            //根据projectId productId, distributorId 查询该商品的预售明细
            PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub stub = RpcStubStore.getRpcStub(PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub.class);
            PlanSaleItemStructure.GetCustomerPlanInfoResp rpcResp = stub.getCustomerPlanInfo(rpcReq);
            PlanSaleItemStructure.SalesPlanItem salesPlanItem = rpcResp.getSalesPlanItem();

            ProductDto product = new ProductDto();
            product.setProductName(salesPlanItem.getProductName());
            product.setProductCode(salesPlanItem.getProductModle());
            product.setCanBePurchasedPieces(salesPlanItem.getOnSaleQuantity() - salesPlanItem.getSaleOccupationQuantity() - salesPlanItem.getSoldQuantity());
            product.setDiscount(salesPlanItem.getYhPrepaidDiscount() / FXConstant.MILLION_DOUBLE + salesPlanItem.getBrandPrepaidDiscount() / FXConstant.MILLION_DOUBLE);//折扣点
            double buyingPrice = DoubleScale.getRoundUpValue(6, salesPlanItem.getWholesalePrice() / FXConstant.MILLION_DOUBLE); // 出货价
            product.setBuyingPrice(buyingPrice);
            double guidePrice = DoubleScale.getRoundUpValue(6, salesPlanItem.getSaleGuidePrice() / FXConstant.MILLION_DOUBLE);
            product.setGuidePrice(guidePrice);
            product.setEndTime(DateUtil.long2Date(salesPlanItem.getEndTime()));
            product.setBuyingPriceStr(NumberFormat.format(buyingPrice, "#,##0.000000"));
            product.setGuidePriceStr(NumberFormat.format(guidePrice, "#,##0.000000"));
            product.setDiscountStr(NumberFormat.format(product.getDiscount(), "#,##0.0000%"));
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), product);
            logger.info("#traceId={}# [getProductInfo][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(), ErrorCode.UNKNOWN_ERROR.getMessage());
        }
        return gongxiaoResult;
    }

    /**
     * 点击"下单"按钮后 进入提交页面, 提交页点击"提交订单"按钮 创建AD采购订单
     *
     * @param request
     * @param response
     * @param shippingAddress
     * @param provinceId
     * @param provinceName
     * @param cityId
     * @param cityName
     * @param districtId
     * @param districtName
     * @param recipientName
     * @param recipientMobile
     * @param currencyId
     * @param currencyCode
     * @param cashAmount
     * @param totalOrderAmount
     * @param totalPaidAmount
     * @param salesOrderItem
     * @return
     */
    @RequestMapping("/saveOrder")
    @ResponseBody
    public GongxiaoResult getSaveOrder(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(defaultValue = "") String shippingAddress,
                                       @RequestParam(defaultValue = "") String provinceId,
                                       @RequestParam(defaultValue = "") String provinceName,
                                       @RequestParam(defaultValue = "") String cityId,
                                       @RequestParam(defaultValue = "") String cityName,
                                       @RequestParam(defaultValue = "") String districtId,
                                       @RequestParam(defaultValue = "") String districtName,
                                       @RequestParam(defaultValue = "") String recipientName,
                                       @RequestParam(defaultValue = "") String recipientMobile,
                                       @RequestParam(defaultValue = "") String currencyId,
                                       @RequestParam(defaultValue = "") String currencyCode,
                                       @RequestParam(defaultValue = "0") double cashAmount,
                                       @RequestParam(defaultValue = "0") double totalOrderAmount,
                                       @RequestParam(defaultValue = "0") double totalPaidAmount, String salesOrderItem) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][createSalesOrder] params: ", traceId);
            if (shippingAddress == null || shippingAddress.length() == 0) {
                return new GongxiaoResult(ErrorCode.SHIPPING_ADDRESS.getErrorCode(), ErrorCode.SHIPPING_ADDRESS.getMessage());
            }
            // session中获取信息
            long projectId = portalUserInfo.getProjectId();
            long distributorId = portalUserInfo.getDistributorId();
            DistributorStructure.GetDistributorAccountByIdReq dReq = DistributorStructure.GetDistributorAccountByIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setDistributorUserId(distributorId)
                    .build();
            DistributorServiceGrpc.DistributorServiceBlockingStub dStub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.GetDistributorAccountByIdResp dResp = dStub.getDistributorAccountById(dReq);

            SalesOrderServiceStructure.CreateSalesOrderRequest.Builder reqBuilder = SalesOrderServiceStructure.CreateSalesOrderRequest.newBuilder();
            SalesOrderServiceStructure.CreateSalesOrderRequest rpcReq;
            reqBuilder.setRpcHeader(rpcHeader);
            reqBuilder.setProjectId(projectId);
            reqBuilder.setProjectName(dResp.getDistributorUser().getProjectName());
            reqBuilder.setDistributorId(distributorId);
            reqBuilder.setDistributorName(dResp.getDistributorUser().getDistributorName());
            reqBuilder.setShippingAddress(shippingAddress);
            reqBuilder.setProvinceId(provinceId);
            reqBuilder.setProvinceName(provinceName);
            reqBuilder.setCityId(cityId);
            reqBuilder.setCityName(cityName);
            reqBuilder.setDistrictId(districtId);
            reqBuilder.setDistrictName(districtName);
            reqBuilder.setRecipientName(recipientName);
            reqBuilder.setRecipientMobile(recipientMobile);
            reqBuilder.setCurrencyCode(currencyCode);
            reqBuilder.setCashAmount(cashAmount);
            reqBuilder.setTotalOrderAmount(totalOrderAmount);
            reqBuilder.setTotalPaidAmount(totalPaidAmount);
            reqBuilder.setCreateTime(DateUtil.getTime(new Date()));
            List<SalesOrderItemInfo> itemList
                    = JSON.parseObject(salesOrderItem, new TypeReference<List<SalesOrderItemInfo>>() {
            }); //明细
            for (SalesOrderItemInfo item : itemList) {
                SalesOrderServiceStructure.SalesOrderItemInfoRequest infoItem = SalesOrderServiceStructure.SalesOrderItemInfoRequest.newBuilder()
                        .setProductCode(item.getProductCode() != null ? item.getProductCode() : "")
                        .setTotalQuantity(item.getTotalQuantity() != null ? item.getTotalQuantity() : 0)
                        .build();
                reqBuilder.addList(infoItem);
            }
            rpcReq = reqBuilder.build();

            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub stub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            //创建AD采购订单  TODO: 把单号返回来,打印到日记中
            GongxiaoRpc.RpcResult rpcResp = stub.createSalesOrder(rpcReq);
            gongxiaoResult = new GongxiaoResult(rpcResp.getReturnCode(), rpcResp.getMsg());
            logger.info("#traceId={}# [rejectSalesOrder][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(), ErrorCode.UNKNOWN_ERROR.getMessage());
        }
        return gongxiaoResult;
    }

    /**
     * ADPortal 订单管理页 获取订单列表
     *
     * @param request
     * @param response
     * @param purchaseNo
     * @param productCode
     * @param status
     * @param dateStart
     * @param dateEnd
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("/getOrderList")
    @ResponseBody
    public GongxiaoResult getSalesOrderList(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(defaultValue = "") String purchaseNo,
                                            @RequestParam(defaultValue = "") String productCode,
                                            @RequestParam(defaultValue = "0") Integer status,
                                            @RequestParam(defaultValue = "") String dateStart,
                                            @RequestParam(defaultValue = "") String dateEnd,
                                            @RequestParam(required = true, defaultValue = "1") Integer page,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderList] params:  purchaseNo={}, productCode={}, status={}, dateStart={}, dateEnd={}, page={}, pageSize={} ",
                    traceId, purchaseNo, productCode, status, dateStart, dateEnd, page, pageSize);
            // session中获取信息
            long projectId = portalUserInfo.getProjectId();
            long distributorId = portalUserInfo.getDistributorId();

            SalesOrderServiceStructure.SearchOrderListWithPageRequest.Builder reqBuilder = SalesOrderServiceStructure.SearchOrderListWithPageRequest.newBuilder();
            SalesOrderServiceStructure.SearchOrderListWithPageRequest rpcReq;
            reqBuilder.setProjectId(projectId);
            reqBuilder.setDistributorId(distributorId);
            reqBuilder.setRpcHeader(rpcHeader);
            reqBuilder.setSalesOrderNo(purchaseNo);
            reqBuilder.setProductCode(productCode);
            reqBuilder.setStatus(status);
            reqBuilder.setDateStart(dateStart);
            reqBuilder.setDateEnd(dateEnd);
            reqBuilder.setPage(page);
            reqBuilder.setPageSize(pageSize);
            rpcReq = reqBuilder.build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub stub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            SalesOrderServiceStructure.SearchOrderListWithPageResponse rpcResp = stub.searchOrderListWithPage(rpcReq);
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPageNum(rpcResp.getPageNum());
            pageInfo.setPageSize(rpcResp.getPageSize());
            pageInfo.setTotal(rpcResp.getTotal());
            List<SalesOrderServiceStructure.SalesOrderInfoResponse> infoList = rpcResp.getListList();
            List<SalesOrderInfo> orderInfoList = new ArrayList<>();
            for (SalesOrderServiceStructure.SalesOrderInfoResponse info : infoList) {
                SalesOrderInfo orderInfo = new SalesOrderInfo();
                orderInfo.setSalesOrderNo(info.getSalesOrderNo());
                orderInfo.setCouponAmountDouble(info.getCouponAmountDouble());
                orderInfo.setCreateTime(DateUtil.long2Date(info.getCreateTime()));
                orderInfo.setDiscountAmountTotal(info.getDiscountAmountTotal());
                orderInfo.setDistributorName(info.getDistributorName());
                orderInfo.setPaidTime(DateUtil.long2Date(info.getPaidTime()));
                orderInfo.setPrepaidAmountDouble(info.getPrepaidAmountDouble());
                orderInfo.setSalesOrderStatus(info.getSalesOrderStatus());
                orderInfo.setTotalOrderAmountDouble(info.getTotalOrderAmountDouble());
                orderInfo.setTotalQuantity(info.getTotalQuantity());
                orderInfo.setUnhandledQuantity(info.getUnhandledQuantity());
                orderInfoList.add(orderInfo);
            }
            pageInfo.setList(orderInfoList);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
            logger.info("#traceId={}# [getSalesOrderList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * * ADPortal 订单管理页的列表中点击某订单号, 展示订单的详细信息
     *
     * @param salesOrderNo 单号
     * @return
     */
    @RequestMapping("/getOrderInfo")
    @ResponseBody
    public GongxiaoResult getSalesOrderInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "") String salesOrderNo) {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderInfo] params:  salesOrderNo={}", traceId, salesOrderNo);
            // session中获取信息
            long projectId = portalUserInfo.getProjectId();
            long distributorId = portalUserInfo.getDistributorId();

            SalesOrderServiceStructure.CommonSalesOrderRequest rpcReq = SalesOrderServiceStructure.CommonSalesOrderRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesOrderNo(salesOrderNo)
                    .build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub stub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            SalesOrderServiceStructure.GetOrderByOrderNoResponse rpcResp = stub.getOrderDetailBySalesOrderNo(rpcReq);
            SalesOrderInfo info = new SalesOrderInfo();
            info.setTotalOrderAmountDouble(Double.valueOf(rpcResp.getTotalOrderAmountDouble()));
            info.setSalesOrderStatus(rpcResp.getSalesOrderStatus());
            info.setRecipientName(rpcResp.getRecipientName());
            info.setRecipientMobile(rpcResp.getRecipientMobile());
            Date lastUpdateTime = null;
            if (rpcResp.getLastUpdateTime() != 0) {
                try {
                    lastUpdateTime = DateUtil.long2Date(rpcResp.getLastUpdateTime());
                } catch (Exception e) {
                }
            }
            info.setLastUpdateTime(lastUpdateTime);
            info.setShippingAddress(rpcResp.getShippingAddress());
            List<SalesOrderServiceStructure.SalesOrderItemsResponse> itemList = rpcResp.getItemsList(); //订单对应的商品列表
            List<SalesOrderItemInfo> infoItemList = new ArrayList<>();
            for (SalesOrderServiceStructure.SalesOrderItemsResponse item : itemList) {
                SalesOrderItemInfo infoItem = new SalesOrderItemInfo();
                infoItem.setProductCode(item.getProductCode());
                infoItem.setProductName(item.getProductName());
                infoItem.setYhDiscountPercentDouble(item.getYhDiscountPercentDouble());
                infoItem.setSupplierDiscountPercentDouble(item.getSupplierDiscountPercentDouble());
                infoItem.setWholesalePriceDouble(item.getWholesalePriceDouble());
                infoItem.setTotalQuantity(item.getTotalQuantity());
                infoItem.setSendQuantity(item.getSendQuantity());
                infoItem.setDeliveredQuantity(item.getDeliveredQuantity());
                infoItem.setTotalOrderAmountDouble(item.getTotalOrderAmountDouble());
                infoItemList.add(infoItem);
            }
            info.setItemList(infoItemList);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), info);
            logger.info("#traceId={}# [getSalesOrderInfo][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 点击"下单"按钮后 进入提交页面 查询帐户可用金额
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getAvailableBalance")
    @ResponseBody
    public GongxiaoResult getAvailableBalance(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProductInfo] params: ", traceId);
            // session中获取信息
            long projectId = portalUserInfo.getProjectId();
            long distributorId = portalUserInfo.getDistributorId();

            DistributorAccountForOrder distributorAccountForOrder = new DistributorAccountForOrder();
            DistributorAccountServiceStructure.GetDistributorAccountAmountRequest rpcReq = DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode("CNY")
                    .setProjectId(projectId)
                    .setDistributorId(distributorId)
                    .build();
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub stub = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            PaymentCommonGrpc.AccountAmountResponse rpcResponse = stub.getDistributorAccountAmount(rpcReq);
            distributorAccountForOrder.setCashAmount(rpcResponse.getCashAmountDouble());
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), distributorAccountForOrder);
            logger.info("#traceId={}# [getProductInfo][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * AD下单之后, YH商务未审核之前, 可取消AD采购订单
     */
    @RequestMapping("/cancelOrder")
    @ResponseBody
    public GongxiaoResult cancelOrder(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "") String salesOrderNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderInfo] params:  salesOrderNo={}", traceId, salesOrderNo);
            // session中获取信息
            long projectId = portalUserInfo.getProjectId();
            long distributorId = portalUserInfo.getDistributorId();

            SalesOrderServiceStructure.CommonSalesOrderRequest rpcReq = SalesOrderServiceStructure.CommonSalesOrderRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setSalesOrderNo(salesOrderNo)
                    .build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub stub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResp = stub.cancelSalesOrderByDistributor(rpcReq);
            gongxiaoResult = new GongxiaoResult(rpcResp.getReturnCode(), rpcResp.getMsg());
            logger.info("#traceId={}# [getSalesOrderInfo][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
