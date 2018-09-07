package com.yhglobal.gongxiao.distributor.purchase.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.distributor.DistributorAccountForOrder;
import com.yhglobal.gongxiao.distributor.purchase.service.OrderService;
import com.yhglobal.gongxiao.distributor.util.DistributorPortalTraceIdGenerator;
import com.yhglobal.gongxiao.excel.ExcelUtil;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorShippingAddress;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorAddressService;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorPortalUserService;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.product.dto.ProductDto;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.sales.bo.SalesOrderDetail;
import com.yhglobal.gongxiao.sales.bo.SalesOrderInfo;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.service.PlanSaleItemService;
import com.yhglobal.gongxiao.sales.service.SalesOrderService;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.ExcelDownUtil;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.ValidationUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @Description:
 * @author: LUOYI
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Reference
    private SalesOrderService salesOrderService;
    @Reference
    private PlanSaleItemService planSaleItemService;
    @Reference
    private DistributorAddressService distributorAddressService;
    @Reference
    private DistributorPortalUserService distributorPortalUserService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PortalConfig portalConfig;

    @Reference
    private ProductService productService;

    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象
    /**
     * 查询产品列表
     * @param request
     * @param response
     * @param queryStr
     * @return
     */
    @RequestMapping("/getsProductList")
    @ResponseBody
    public GongxiaoResult<List<SalesPlanItem>> getProductList(HttpServletRequest request, HttpServletResponse response, String queryStr) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProductList] params:  queryStr={} ", traceId, queryStr);
            List<SalesPlanItem> resultList = planSaleItemService.searchProductByCustomer(rpcHeader,queryStr);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), resultList);
            logger.info("#traceId={}# [getProductList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
    /**
     * 获取产品信息
     * @param request
     * @param response
     * @param productId
     * @return
     */
    @RequestMapping("/getProductInfo")
    @ResponseBody
    public GongxiaoResult<ProductDto> getProductInfo(HttpServletRequest request, HttpServletResponse response,Long productId) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProductInfo] params:  productId={} ", traceId, productId);
            ProductDto productDto = orderService.getProductByAd(rpcHeader,productId);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), productDto);
            logger.info("#traceId={}# [getProductInfo][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 查询帐户可用金额
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getAvailableBalance")
    @ResponseBody
    public GongxiaoResult<DistributorAccountForOrder> getAvailableBalance(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProductInfo] params: ", traceId);
            DistributorAccountForOrder distributorAccountForOrder = orderService.getAvailableBalance(rpcHeader);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), distributorAccountForOrder);
            logger.info("#traceId={}# [getProductInfo][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 保存订单
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
    public GongxiaoResult getSaveOrder(HttpServletRequest request, HttpServletResponse response,String shippingAddress, String provinceId, String provinceName, String cityId, String cityName,
                                         String districtId,String districtName, String recipientName, String recipientMobile, String currencyId, String currencyCode, double cashAmount,
                                          double totalOrderAmount, double totalPaidAmount,  String salesOrderItem) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String salesOrderNo = DateTimeIdGenerator.nextId(BizNumberType.SALES_ORDER_NO);
            logger.info("#traceId={}# [IN][createSalesOrder] params:  salesOrderNo={}", traceId, salesOrderNo);
            if(shippingAddress==null||shippingAddress.length()==0){
                return new GongxiaoResult<>(ErrorCode.SHIPPING_ADDRESS.getErrorCode(), ErrorCode.SHIPPING_ADDRESS.getMessage());
            }
            int result = orderService.createOrder(rpcHeader,shippingAddress,provinceId, provinceName, cityId, cityName,
                    districtId,districtName, recipientName, recipientMobile, currencyId, currencyCode, cashAmount,
                     totalOrderAmount, totalPaidAmount,  salesOrderItem);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
            logger.info("#traceId={}# [rejectSalesOrder][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 获取订单列表
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
    public GongxiaoResult<PageInfo<SalesOrderInfo>> getSalesOrderList(HttpServletRequest request, HttpServletResponse response,
                                                                  String purchaseNo, String productCode,
                                                                  Integer status, String dateStart, String dateEnd,
                                                                  @RequestParam(required=true,defaultValue="1") Integer page,
                                                                  @RequestParam(required=false,defaultValue="10") Integer pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderList] params:  purchaseNo={}, productCode={}, status={}, dateStart={}, dateEnd={}, page={}, pageSize={} ",
                    traceId, purchaseNo, productCode, status, dateStart, dateEnd, page, pageSize);

            PageInfo<SalesOrderInfo> pageInfo = salesOrderService.searchOrderListWithPage(rpcHeader,purchaseNo, productCode, status,
                    ValidationUtil.isNotEmpty(dateStart) ?DateUtil.stringToDate(dateStart,DateUtil.DATE_FORMAT):null,
                    ValidationUtil.isNotEmpty(dateEnd)?DateUtil.stringToDate(dateEnd,DateUtil.DATE_FORMAT):null,
                    page, pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
            logger.info("#traceId={}# [getSalesOrderList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
    /**
     * 获取订单详细信息
     *
     * @param salesOrderNo 单号
     * @return
     */
    @RequestMapping("/getOrderInfo")
    @ResponseBody
    public GongxiaoResult<SalesOrderDetail> getSalesOrderInfo(HttpServletRequest request, HttpServletResponse response, String salesOrderNo) {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderInfo] params:  salesOrderNo={}", traceId, salesOrderNo);
            SalesOrderDetail orderByOrderNo = salesOrderService.getOrderDetailBySalesOrderNo(rpcHeader, salesOrderNo);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), orderByOrderNo);
            logger.info("#traceId={}# [getSalesOrderInfo][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
    /**
     * 导出Excel
     *
     * @param
     * @return
     */
    @RequestMapping("/export")
    public void  export(HttpServletRequest request, HttpServletResponse response, String salesOrderNo,
                        String purchaseNo, String productCode,
                        Integer status, String dateStart, String dateEnd) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        logger.info("#traceId={}# [IN][getSalesOrderList] params:  purchaseNo={}, productCode={}, status={}, dateStart={}, dateEnd={} ",
                traceId, purchaseNo, productCode, status, dateStart, dateEnd);
        GongxiaoResult gongxiaoResult = null;

        String downFileName = new String("订单列表.xls");
        try {
            List<SalesOrderInfo> orderList = salesOrderService.searchOrderList(rpcHeader,purchaseNo, productCode, status,
                    ValidationUtil.isNotEmpty(dateStart) ?DateUtil.stringToDate(dateStart,DateUtil.DATE_FORMAT):null,
                    ValidationUtil.isNotEmpty(dateEnd)?DateUtil.stringToDate(dateEnd,DateUtil.DATE_FORMAT):null);
            ExcelUtil<SalesOrderInfo> util = new ExcelUtil<SalesOrderInfo>(SalesOrderInfo.class);
            Workbook workbook = util.getListToExcel(orderList,downFileName);
            ExcelDownUtil.resetResponse(response,workbook,downFileName);
            logger.info("#traceId={}# [getSalesOrderList][OUT]");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @RequestMapping("/cancelOrder")
    @ResponseBody
    public GongxiaoResult<Integer> cancelOrder(HttpServletRequest request, HttpServletResponse response, String salesOrderNo){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderInfo] params:  salesOrderNo={}", traceId, salesOrderNo);
            RpcResult result = salesOrderService.cancelSalesOrderByDistributor(rpcHeader,salesOrderNo);
            if(!result.getSuccess()){
                gongxiaoResult = GongxiaoResult.newResult(result.getCode(),result.getMessage());
            }else {
                gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
            }
            logger.info("#traceId={}# [getSalesOrderInfo][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
    @RequestMapping("/getAddress")
    @ResponseBody
    public GongxiaoResult<List<DistributorShippingAddress>> selectADPreferenceList(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderInfo] ", traceId);
            DistributorPortalUser distributorPortalUser = distributorPortalUserService.getPortalUserById(rpcHeader,rpcHeader.getUid());
            List<DistributorShippingAddress> addressList = distributorAddressService.selectADPreferenceList(rpcHeader,distributorPortalUser.getDistributorId().toString());
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), addressList);
            logger.info("#traceId={}# [getSalesOrderInfo][OUT] result.size={} ",addressList.size());
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
