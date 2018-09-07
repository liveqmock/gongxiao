package com.yhglobal.gongxiao.purchase.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.supplier.model.Supplier;
import com.yhglobal.gongxiao.foundation.supplier.service.SupplierService;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.payment.account.ProjectAccountModel;
import com.yhglobal.gongxiao.purchase.bo.ProjectAccountAmount;
import com.yhglobal.gongxiao.purchase.model.ProductPriceVo;
import com.yhglobal.gongxiao.purchase.model.ProjectVo;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
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

/**
 * 获取采购相关的基础信息
 *
 * @author: 陈浩
 * @create: 2018-02-27 14:28
 **/
@Controller
@RequestMapping("/purchase/foundation")
public class FoundationInfoController {

    private static Logger logger = LoggerFactory.getLogger(FoundationInfoController.class);

    @Reference
    ProjectService projectInfoService;

    @Reference
    WarehouseService warehouseService;

    @Reference
    ProductService productService;

    @Reference
    SupplierService supplierService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 获取项目信息列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProjectList", method = RequestMethod.GET)
    public GongxiaoResult getProjectInfoList(HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getProjectList] params: ", traceId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<Project> projectInfoList = projectInfoService.selectProjectList(rpcHeader);
            logger.info("#traceId={}# [OUT] getProjectList success: ", traceId);
            List<ProjectVo> projectVoList = new ArrayList<>();
            for (Project project:projectInfoList){
                ProjectVo projectVo = new ProjectVo();
                projectVo.setProjectId(project.getProjectId());
                projectVo.setProjectName(project.getProjectName());
                projectVo.setStatus(project.getStatus());
                projectVo.setBrandId(project.getBrandId());
                projectVo.setBrandName(project.getBrandName());
                projectVo.setSupplierId(project.getSupplierId());
                projectVo.setSupplierName(project.getSupplierName());
                projectVo.setDistributionInfo(project.getDistributionInfo());
                projectVo.setPrepaidUseReferRate(DoubleScale.keepSixBits(project.getPrepaidUseReferRate()));
                projectVo.setCouponUseReferRate(DoubleScale.keepSixBits(project.getCouponUseReferRate()));
                projectVo.setMonthlyCouponGenerateRate(DoubleScale.keepSixBits(project.getMonthlyCouponGenerateRate()));
                projectVo.setQuarterlyCouponGenerateRate(DoubleScale.keepSixBits(project.getQuarterlyCouponGenerateRate()));
                projectVo.setAnnualCouponGenerateRate(DoubleScale.keepSixBits(project.getAnnualCouponGenerateRate()));
                projectVoList.add(projectVo);
            }
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), projectVoList);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 根据用户ID获取项目信息 目前是获取的第一个项目
     *
     * @param projectId 项目ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProjectInfo", method = RequestMethod.GET)
    public GongxiaoResult getProjectInfo(String projectId, HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),  request.getServletPath());
            logger.info("#traceId={}# [IN][getProjectInfo] params: projectId={} ", traceId,projectId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            Project project = projectInfoService.getByProjectId(rpcHeader, projectId);
            if (project == null){
                logger.info("#traceId={}# [OUT] fail to getProjectInfo: project=null", traceId);
            }else {
                logger.info("#traceId={}# [OUT] getProjectInfo success: project = {}", traceId,project.toString());
            }
            ProjectVo projectVo = new ProjectVo();
            projectVo.setProjectId(project.getProjectId());
            projectVo.setProjectName(project.getProjectName());
            projectVo.setStatus(project.getStatus());
            projectVo.setBrandId(project.getBrandId());
            projectVo.setBrandName(project.getBrandName());
            projectVo.setSupplierId(project.getSupplierId());
            projectVo.setSupplierName(project.getSupplierName());
            projectVo.setDistributionInfo(project.getDistributionInfo());
            projectVo.setPrepaidUseReferRate(DoubleScale.keepSixBits(project.getPrepaidUseReferRate()));
            projectVo.setCouponUseReferRate(DoubleScale.keepSixBits(project.getCouponUseReferRate()));
            projectVo.setMonthlyCouponGenerateRate(DoubleScale.keepSixBits(project.getMonthlyCouponGenerateRate()));
            projectVo.setQuarterlyCouponGenerateRate(DoubleScale.keepSixBits(project.getQuarterlyCouponGenerateRate()));
            projectVo.setAnnualCouponGenerateRate(DoubleScale.keepSixBits(project.getAnnualCouponGenerateRate()));
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), projectVo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 获取仓库信息列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/warehouseList", method = RequestMethod.GET)
    public GongxiaoResult getWarehouseInfoList(HttpServletRequest request) throws Exception {
        String traceId =null;
        try {
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),  request.getServletPath());
            logger.info("#traceId={}# [IN][warehouseList] params: ", traceId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<Warehouse> warehouseList = warehouseService.selectAll(rpcHeader);
            logger.info("#traceId={}# [OUT] get warehouseList success: warehouseList.size()={}", traceId, warehouseList.size());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), warehouseList);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 获取项目的货品信息
     *
     * @param projectId 项目id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProductInfoList", method = RequestMethod.GET)
    public GongxiaoResult getProductInfoList(String projectId,
                                             HttpServletRequest request,
                                             String productCode,
                                             String productName,
                                             int pageNumber,
                                             int pageSize) throws Exception {
        String traceId =null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),  request.getServletPath());
            logger.info("#traceId={}# [IN][handlePurchaseFlow] params: ", traceId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PageInfo<ProductBasic> productInfoList = productService.selectProductByProjectId(rpcHeader, Long.parseLong(projectId),productCode,productName,pageNumber,pageSize);
            List<ProductPriceVo> productPriceVoList = new ArrayList<>();
            for (ProductBasic productBasic:productInfoList.getList()){
                ProductPriceVo productPriceVo = new ProductPriceVo();
                productPriceVo.setProductId(productBasic.getId());
                productPriceVo.setProductCode(productBasic.getProductCode());
                productPriceVo.setProductName(productBasic.getProductName());
                productPriceVo.setEasCode(productBasic.getEasCode());
                productPriceVo.setEasModel(productBasic.getEasModel());
                productPriceVo.setEasName(productBasic.getEasName());
                productPriceVo.setBarCode(productBasic.getBarCode());
                productPriceVo.setBrandId(productBasic.getBrandId());
                productPriceVo.setBrandName(productBasic.getBrandName());
                productPriceVo.setGuidePrice(DoubleScale.keepSixBits(productBasic.getGuidePrice()));
                productPriceVo.setListedPrice(DoubleScale.keepSixBits(productBasic.getListedPrice()));
                productPriceVoList.add(productPriceVo);
            }
            PageInfo<ProductPriceVo> ProductPriceVoPage = new PageInfo<ProductPriceVo>();
            ProductPriceVoPage.setList(productPriceVoList);
            ProductPriceVoPage.setTotal(productInfoList.getTotal());

            logger.info("#traceId={}# [OUT] getProductInfoList success: productInfoList.size()={}", traceId, productPriceVoList.size());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), ProductPriceVoPage);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 获取项目的余额
     *
     * @param projectId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProjectCash", method = RequestMethod.GET)
    public GongxiaoResult getProjectCash(String projectId, HttpServletRequest request) throws Exception {
        String traceId =null;
        try {
            ProjectAccountAmount cash = new ProjectAccountAmount();
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),  request.getServletPath());
            logger.info("#traceId={}# [IN][handlePurchaseFlow] params: ", traceId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            //todo 换成新街口
//            ProjectAccountModel accountModel = yhglobalVirtualAccountService.getCash(rpcHeader, projectId);
            ProjectAccountModel accountModel = null;
            cash.setProjectId(accountModel.getProjectId());
            cash.setPrepaidAmount(DoubleScale.keepTwoBits(accountModel.getPrepaidAmount()));
            cash.setCouponMoney(DoubleScale.keepTwoBits(accountModel.getCouponAmount()));
            logger.info("#traceId={}# [OUT] getProjectCash success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), cash);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 获取品牌商列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSupplierList", method = RequestMethod.GET)
    public GongxiaoResult getSupplierList(HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),  request.getServletPath());
            logger.info("#traceId={}# [IN][getSupplierList] params: ", traceId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<Supplier> suppliers = supplierService.selectAll(rpcHeader);
            logger.info("#traceId={}# [OUT] get getSupplierList success: brandList.size()={}", traceId, suppliers.size());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), suppliers);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

}
