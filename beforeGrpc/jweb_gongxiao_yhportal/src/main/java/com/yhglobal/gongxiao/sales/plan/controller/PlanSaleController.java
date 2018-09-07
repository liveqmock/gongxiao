package com.yhglobal.gongxiao.sales.plan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.product.price.service.ProductPriceService;
import com.yhglobal.gongxiao.inventory.service.ProductInventoryService;
import com.yhglobal.gongxiao.sales.model.plan.dto.SaleEditInfo;
import com.yhglobal.gongxiao.sales.plan.model.ProductInfo;
import com.yhglobal.gongxiao.sales.model.plan.dto.SalePlanInfoIn;
import com.yhglobal.gongxiao.sales.model.plan.dto.SalePlanInfoOut;
import com.yhglobal.gongxiao.sales.service.PlanSaleItemService;
import com.yhglobal.gongxiao.sales.service.PlanSaleService;

import com.yhglobal.gongxiao.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 销售计划
 *
 * @author: 陈浩
 */
@Controller
@RequestMapping("/planSale")
public class PlanSaleController {

    private static Logger logger = LoggerFactory.getLogger(PlanSaleController.class);

    @Reference
    PlanSaleService planSaleService; //销售计划服务类

    @Reference
    ProductInventoryService inventoryService; //库存查询服务类

    @Reference
    ProductPriceService productPriceService; //商品价格表

    @Reference
    PlanSaleItemService planSaleItemService;

    @Reference
    ProductService productService;//商品信息表

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;


    /**
     * 获取当前项目的货品信息以及实际库存
     *
     * @param projectId   品牌商ID
     * @param productCode 产品CODE
     * @param productName 产品名称
     * @param request
     * @return
     */
    @RequestMapping(value = "/getProductList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getProductList(String projectId,
                                         String productCode,
                                         String productName,
                                         int pageNumber,
                                         int pageSize,
                                         HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getProductList] params: projectId:{}, productCode:{}, productName:{} ",
                    projectId, productCode, productName);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PageInfo<ProductBasic> productPriceList = productService.selectProductByProjectId(rpcHeader, Long.parseLong(projectId),productCode,productName,pageNumber,pageSize);
            List<ProductInfo> productInfoList = new ArrayList<>();
            for (ProductBasic productBasic : productPriceList.getList()) {
                int instockQuantity =
                        inventoryService.selectQuantityByProjectIdAndProductCode(rpcHeader, projectId, productBasic.getProductCode());
                ProductInfo productInfo = new ProductInfo();
                productInfo.setProductCode(productBasic.getProductCode());
                productInfo.setProductName(productBasic.getProductName());
                productInfo.setGuidePrice(DoubleScale.keepSixBits(productBasic.getGuidePrice()));
                productInfo.setInStockQuantity(instockQuantity);
                productInfo.setProductId(productBasic.getId()+"");
                productInfo.setSaleGuidePrice(DoubleScale.keepSixBits(productBasic.getSaleGuidePrice()));
                productInfoList.add(productInfo);
            }
            PageInfo<ProductInfo> productInfoPageInfo = new PageInfo<ProductInfo>();
            productInfoPageInfo.setList(productInfoList);
            productInfoPageInfo.setTotal(productPriceList.getTotal());
            logger.info("#traceId={}# [OUT] getProductList success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), productInfoPageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 插入销售计划信息
     *
     * @param salePlanJson 销售计划json
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertSalePlanInfo", method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult insertSalePlanInfo(String salePlanJson, HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][insertSalePlanInfo] params: salePlanJson:{} ", salePlanJson);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            //将货品列表json数组的字符串转换为List
            ArrayList<SalePlanInfoIn> salePlanInfoInArrayList
                    = JSON.parseObject(salePlanJson, new TypeReference<ArrayList<SalePlanInfoIn>>() {
            });
            for (SalePlanInfoIn salePlanInfoIn:salePlanInfoInArrayList){
                String startTime = salePlanInfoIn.getStartTime();
                String endTime = salePlanInfoIn.getEndTime();

                boolean notEmpty = ValidationUtil.isNotEmpty(startTime, endTime);
                if (!notEmpty){
                    return new GongxiaoResult(ErrorCode.DATE_NOT_NULL.getErrorCode(), ErrorCode.DATE_NOT_NULL.getMessage());
                }
                Date startDate = DateUtil.stringToDate(startTime,"yyyy-MM-dd");
                Date endDate = DateUtil.stringToDate(endTime,"yyyy-MM-dd");
                if (startDate.after(endDate)){
                    return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.NOT_REPEAT_START_END_DATE);
                }
                Long productId = salePlanInfoIn.getProductId();
                //判断该货品是否重复
                boolean productRepeat = planSaleService.isProductRepeat(rpcHeader,
                        salePlanInfoIn.getProjectId() + "",
                        productId + "",
                        startDate,
                        endDate);
                if(productRepeat){
                    return new GongxiaoResult(ErrorCode.PRODUCT_REPEAT.getErrorCode(), "货品编码为"+salePlanInfoIn.getProductCode()+ErrorCode.PRODUCT_REPEAT.getMessage());
                }
            }

            int insertNumber = planSaleService.createSalePlan(rpcHeader, salePlanInfoInArrayList);
            logger.info("#traceId={}# [OUT] insertSalePlanInfo success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), insertNumber);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    /**
     * 获取当前项目的销售计划列表
     *
     * @param projectId   项目ID
     * @param productCode 产品编码
     * @param productName 产品名称
     * @param createTime  创建时间
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSalePlanList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getSalePlanList(String projectId,
                                          String productCode,
                                          String productName,
                                          String createTime,
                                          int pageNumber,
                                          int pageSize,
                                          HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getSalePlanList] params: projectId:{}, productCode:{}, productName:{}, productName:{createTime} ",
                    traceId, projectId, productCode, productName, createTime);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PageInfo<SalePlanInfoOut> salePlanInfoOuts
                    = planSaleService.selectSalePlanListByProject(rpcHeader,
                    projectId,
                    productCode,
                    productName,
                    createTime,
                    pageNumber,
                    pageSize);
            logger.info("#traceId={}# [OUT] getSalePlanList success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), salePlanInfoOuts);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    @RequestMapping(value = "/getSalePlanDetail", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getSalePlanDetail(String salesPlanNo,
                                            HttpServletRequest request) throws Exception {
        String traceId = null;
        traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        logger.info("#traceId={}# [IN][getPurchaseReturnDetail] params: salesPlanNo:{} ", salesPlanNo);
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        if (!ValidationUtil.isNotEmpty(salesPlanNo)) {
            logger.error("#traceId={}# [OUT] getSalePlanDetail success: ", traceId);
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        try {
            SalePlanInfoOut salePlanDetail = planSaleService.getSalePlanDetail(rpcHeader, salesPlanNo);
            logger.info("#traceId={}# [OUT] getSalePlanDetail success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), salePlanDetail);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 通过销售计划单号拿到该条销售计划信息
     *
     * @param salesPlanNo
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPlanSaleInfo", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getPlanSaleInfo(String salesPlanNo, HttpServletRequest request) throws Exception {
        String traceId = null;
        traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        logger.info("#traceId={}# [IN][getPlanSaleInfo] params: salesPlanNo:{} ", salesPlanNo);
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        if ("".equals(salesPlanNo)) {
            logger.error("#traceId={}# [OUT] getPlanSaleInfo success: ", traceId);
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        try {
            SaleEditInfo saleEditInfo = planSaleService.getSalePlanInfo(rpcHeader, salesPlanNo);
            logger.info("#traceId={}# [OUT] getPlanSaleInfo success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), saleEditInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 修改销售计划
     *
     * @param salesPlanNo    销售计划单号
     * @param onSaleQuantity 可售数量
     * @param startDate      开始日期
     * @param endDate        结束日期
     * @param request
     * @return
     */
    @RequestMapping(value = "/editPlanQuantity", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult editPlanQuantity(String salesPlanNo,
                                           int onSaleQuantity,
                                           String startDate,
                                           String endDate,
                                           HttpServletRequest request) throws Exception {
        String traceId = null;
        try {

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][editPlanQuantity] params: salesPlanNo:{}, onSaleQuantity:{}, startDate:{}, endDate:{} ",
                    traceId, salesPlanNo, onSaleQuantity, startDate, endDate);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            //1)数据校验
            SalePlanInfoOut salePlanDetail = planSaleService.getSalePlanDetail(rpcHeader, salesPlanNo);
            int allocatedQuantity = salePlanDetail.getAllocatedQuantity();
            if (onSaleQuantity==0){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.AT_LEAST_ONE);
            }
            if (onSaleQuantity<allocatedQuantity){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.QUANTITY_VALIDATE);
            }
            boolean startDateNotNull = ValidationUtil.isNotEmpty(startDate,endDate);
            if (!startDateNotNull){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.DATE_NOT_NULL);
            }
            Date startTime = DateUtil.stringToDate(startDate,"yyyy-MM-dd");
            Date endTime = DateUtil.stringToDate(endDate,"yyyy-MM-dd");
            if (startTime.after(endTime)){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.NOT_REPEAT_START_END_DATE);
            }
            return planSaleService.editSalePlan(rpcHeader, salesPlanNo, onSaleQuantity, startDate, endDate);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 更新销售计划数据
     *
     * @param salesPlanNo         销售计划单号
     * @param onSaleQuantity      可售数量
     * @param allocatedQuantity   已分配数量
     * @param unallocatedQuantity 未分配数量
     * @param request
     * @return
     */
    @RequestMapping(value = "/updatePlanQuantity", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult updatePlanQuantity(String salesPlanNo,
                                             int onSaleQuantity,
                                             int allocatedQuantity,
                                             int unallocatedQuantity,
                                             HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][updatePlanQuantity] params: salesPlanNo:{}, onSaleQuantity:{}, allocatedQuantity:{}, unallocatedQuantity:{} ",
                    traceId, salesPlanNo, onSaleQuantity, allocatedQuantity, unallocatedQuantity);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            int updateQuantity = planSaleService.updatePlanQuantity(rpcHeader, salesPlanNo, onSaleQuantity, allocatedQuantity, unallocatedQuantity);
            logger.info("#traceId={}# [OUT] updatePlanQuantity success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), updateQuantity);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 作废预售单
     * @param planSaleOrderListString 须作废的预售单列表
     * @param request
     * @return
     */
    @RequestMapping("/cancelPlanOrder")
    @ResponseBody
    public GongxiaoResult cancelPlanOrder(String planSaleOrderListString,HttpServletRequest request){
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            boolean notEmpty = ValidationUtil.isNotEmpty(planSaleOrderListString);
            logger.info("#traceId={}# [IN][cancelPlanOrder] params: planSaleOrderListString={}",
                    traceId, planSaleOrderListString);
            if (!notEmpty){
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.NOT_SELECT_DATA);
                return gongxiaoResult;
            }
            String[] planOrderArray = planSaleOrderListString.split(",");
            for(String  planOrderNo:planOrderArray){
                //取消预售明细
                int i = planSaleItemService.cancelPlan(rpcHeader, planOrderNo);
                //取消预售单
                planSaleService.cancelPlanSaleOrder(rpcHeader,planOrderNo);
            }
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.SUCCESS);

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;

    }

}
