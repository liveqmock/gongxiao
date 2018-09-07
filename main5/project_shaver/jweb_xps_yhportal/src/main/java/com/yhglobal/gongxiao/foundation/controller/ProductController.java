package com.yhglobal.gongxiao.foundation.controller;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.status.DataStatus;
import com.yhglobal.gongxiao.foundation.status.OperateType;
import com.yhglobal.gongxiao.foundation.viewobject.ProductPriceVo;
import com.yhglobal.gongxiao.foundation.viewobject.ProductVo;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.ValidationUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yhglobal.gongxiao.status.EasSynStatus;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Controller
@RequestMapping("/product")
public class ProductController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

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
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            logger.info("#traceId={}# [IN][handlePurchaseFlow] params: ", rpcHeader.getTraceId());
            ProductStructure.SelectProductByProjectIdReq selectProductByProjectIdReq = ProductStructure.SelectProductByProjectIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(Long.parseLong(projectId))
                    .build();
            ProductServiceGrpc.ProductServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.SelectProductByProjectIdResp resp = null;
            try {
                resp = rpcStub.selectProductByProjectId(selectProductByProjectIdReq);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ProductStructure.ProductBusinessPage pageInfoProduct = resp.getProductBusinessPage();
            List<ProductPriceVo> productPriceVoList = new ArrayList<>();
            for (ProductStructure.ProductBusiness productBusiness : pageInfoProduct.getProductBusinessList()) {
                ProductPriceVo productPriceVo = new ProductPriceVo();
                productPriceVo.setProductId(productBusiness.getProductBusinessId());
                productPriceVo.setProductCode(productBusiness.getCustomerProductCode());
                productPriceVo.setProductName(productBusiness.getProductName());
                productPriceVo.setEasCode(productBusiness.getEasCode());
                productPriceVo.setGuidePrice(DoubleScale.keepSixBits(productBusiness.getPurchaseGuidePrice()));
                productPriceVoList.add(productPriceVo);
            }
            PageInfo<ProductPriceVo> ProductPriceVoPage = new PageInfo<ProductPriceVo>();
            ProductPriceVoPage.setList(productPriceVoList);
            ProductPriceVoPage.setTotal(pageInfoProduct.getTotal());
            logger.info("#traceId={}# [OUT] getProductInfoList success: productInfoList.size()={}", rpcHeader.getTraceId(), productPriceVoList.size());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), ProductPriceVoPage);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @RequestMapping("/selectProductListWithCondition")
    public GongxiaoResult selectProductListWithCondition(HttpServletRequest request,
                                                         long projectId,
                                                         int status,
                                                         int pageNumber,
                                                         int pageSize) {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        logger.info("#traceId={}# [IN][handlePurchaseFlow] params: projectId = {}, status={}, pageNumber = {}, pageSize = {}",
                rpcHeader.getTraceId(), projectId, status, pageNumber, pageSize);
        try {
            boolean projectNotNull = ValidationUtil.isNotEmpty(projectId);
            if (!projectNotNull) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PROJECT_NOT_NULL);
            }
            boolean statusNotNull = ValidationUtil.isNotEmpty(status);
            if (!statusNotNull) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.SEARCH_STATUS_NOT_NULL);
            }
            boolean pageParamNotNull = ValidationUtil.isNotEmpty(pageNumber, pageSize);
            if (!pageParamNotNull) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PAGE_PARAM_NOT_NULL);
            }
            String productCode = request.getParameter("productCode");
            String productName = request.getParameter("productName");
            String easCode = request.getParameter("easCode");
            logger.info("#traceId={}# 开始获取货品信息", rpcHeader.getTraceId());
            ProductServiceGrpc.ProductServiceBlockingStub productRpcStub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.SelectProductByConditionReq selectProductByConditionReq = ProductStructure.SelectProductByConditionReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setProductCode(productCode)
                    .setProductName(productName)
                    .setProductEasCode(easCode)
                    .setStatus(status)
                    .build();
            ProductStructure.SelectProductByConditionResp selectProductByConditionResp = productRpcStub.selectProductByCondition(selectProductByConditionReq);
            logger.info("#traceId={}# 获取货品信息完成", rpcHeader.getTraceId());
            List<ProductStructure.ProductBusiness> productBusinessList = selectProductByConditionResp.getProductBusinessList();
            List<ProductVo> productVoList = new ArrayList<>();
            for (ProductStructure.ProductBusiness productBusiness : productBusinessList) {
                ProductVo productVo = new ProductVo();
                productVo.setProductBusinessId(productBusiness.getProductBusinessId());
                productVo.setProductCode(productBusiness.getProductModel());
                productVo.setProductName(productBusiness.getProductName());
                productVo.setEasCode(productBusiness.getEasCode());
                productVo.setWmsCode(productBusiness.getWmsCode());
                productVo.setDataStatus(DataStatus.getErrorCodeByCode(productBusiness.getEasSynStatus()).getMessage());
                productVo.setPackageNumber(productBusiness.getPackageNumber());
                productVoList.add(productVo);
            }
            PageInfo<ProductVo> productVoPageInfo = new PageInfo<>();
            productVoPageInfo.setList(productVoList);
            productVoPageInfo.setTotal(selectProductByConditionResp.getTotal());
            logger.info("#traceId={}# [OUT][handlePurchaseFlow] success", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), productVoPageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @RequestMapping("/addProduct")
    public GongxiaoResult addProduct(HttpServletRequest request,
                                     int operateType,//操作类型,1 保存 2.提交
                                     String easCode,
                                     String wmsCode,
                                     String productLine,
                                     int productLength,
                                     int productWidth,
                                     int productHeight,
                                     int productWeight,
                                     int boxLength,
                                     int boxWidth,
                                     int boxHeight,
                                     int boxWeight,
                                     int packageNumber,
                                     long projectId,
                                     String projectName,
                                     String productName,
                                     String productCode,
                                     String customerSKUCode,
                                     String customerProductCode,
                                     long purchaseGuidePrice,
                                     long taxRate,
                                     String taxCode,
                                     long saleGuidePrice,
                                     long actualSaleReturn,
                                     long shouldSaleReturn,
                                     long factorySendReturn,
                                     long costPrice,
                                     long outPrice,
                                     int generateCoupon,
                                     String easUnitCode) {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        logger.info("#traceId={}# [IN][handlePurchaseFlow] params:  operateType = {},  easCode = {}, wmsCode = {}, productLine = {}, productLength = {}, productWidth = {}, productHeight = {}, productWeight = {}, boxLength = {}, boxWidth = {}, boxHeight = {}, boxWeight = {}, packageNumber = {}, projectId = {}, projectName = {}, productName = {}, productCode = {}, customerSKUCode = {}, customerProductCode = {}, purchaseGuidePrice = {}, taxRate = {}, taxCode = {}, saleGuidePrice = {}, actualSaleReturn = {}, shouldSaleReturn = {}, factorySendReturn = {}, costPrice = {}, outPrice = {}, generateCoupon = {}, easUnitCode = {}",
                rpcHeader.getTraceId(), operateType, easCode, wmsCode, productLine, productLength, productWidth, productHeight, productWeight, boxLength, boxWidth, boxHeight, boxWeight, packageNumber, projectId, projectName, productName, productCode, customerSKUCode, customerProductCode, purchaseGuidePrice, taxRate, taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, factorySendReturn, costPrice, outPrice, generateCoupon, easUnitCode);
        try {
            boolean productInfoNotEmpty = ValidationUtil.isNotEmpty(productName,productCode,easCode,wmsCode);
            if (!productInfoNotEmpty){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PRODUCT_INFO_NOT_NULL);
            }
            boolean rateNotEmpty = ValidationUtil.isNotEmpty(purchaseGuidePrice, saleGuidePrice, taxRate);
            if (!rateNotEmpty){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PRODUCT_PRICE_NOT_NULL);
            }
//            ValidationUtil.isNotEmpty(boxLength)
            ProductServiceGrpc.ProductServiceBlockingStub productRpcStub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.InsertProductInfoReq.Builder insertProductReq = ProductStructure.InsertProductInfoReq.newBuilder();

            ProductStructure.ProductBasicDetail.Builder productBasicDetail = ProductStructure.ProductBasicDetail.newBuilder();
            productBasicDetail.setProductBasicId(0);
            productBasicDetail.setProductBusinessId(0);
            productBasicDetail.setOperateType(OperateType.SAVE.getStatus());
            productBasicDetail.setEasCode(easCode);
            productBasicDetail.setWmsCode(wmsCode);
            productBasicDetail.setCategoryId(0);
            productBasicDetail.setCategoryName("");
            productBasicDetail.setProductLine(productLine);
            productBasicDetail.setProductLength(productLength);
            productBasicDetail.setProductWidth(productWidth);
            productBasicDetail.setProductHeight(productHeight);
            productBasicDetail.setProductWeight(productWeight);
            productBasicDetail.setProductGrossWeight(0);
            productBasicDetail.setProductNetWeight(0);
            productBasicDetail.setBoxLength(boxLength);
            productBasicDetail.setBoxWidth(boxWidth);
            productBasicDetail.setBoxHeight(boxHeight);
            productBasicDetail.setBoxWeight(boxWeight);
            productBasicDetail.setGuaranteePeriod(0);
            productBasicDetail.setPackageNumber(packageNumber);
            productBasicDetail.setProjectId(projectId+"");
            productBasicDetail.setProjectName(projectName);
            productBasicDetail.setRecordStatus(0);
            productBasicDetail.setEasSynStatus(1);
            productBasicDetail.setProductName(productName);
            productBasicDetail.setProductModel(productCode);
            productBasicDetail.setCustomerSKUCode(customerSKUCode);
            productBasicDetail.setCustomerProductCode(customerProductCode);
            productBasicDetail.setPurchaseGuidePrice(purchaseGuidePrice);
            productBasicDetail.setTaxRate(taxRate);
            productBasicDetail.setTaxCode(taxCode);
            productBasicDetail.setSaleGuidePrice(saleGuidePrice);
            productBasicDetail.setActualSaleReturn(actualSaleReturn);
            productBasicDetail.setShouldSaleReturn(shouldSaleReturn);
            productBasicDetail.setFactorySendReturn(factorySendReturn);
            productBasicDetail.setCostPrice(costPrice);
            productBasicDetail.setOutPrice(outPrice);
            productBasicDetail.setGenerateCoupon(generateCoupon);
            productBasicDetail.setEasUnitCode(easUnitCode);
            ProductStructure.OperateHistoryRecord.Builder operateRecord = ProductStructure.OperateHistoryRecord.newBuilder();
            productBasicDetail.addOperateHistoryRecord(operateRecord);

            insertProductReq.setProductBasicDetail(productBasicDetail);
            insertProductReq.setRpcHeader(rpcHeader);

            ProductStructure.InsertProductInfoResp insertProductInfoResp = productRpcStub.insertProductInfo(insertProductReq.build());
            GongxiaoRpc.RpcResult rpcResult = insertProductInfoResp.getRpcResult();

            logger.info("#traceId={}# [OUT][addProduct] success", rpcHeader.getTraceId());

            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.getErrorCodeByCode(rpcResult.getReturnCode()));
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    public GongxiaoResult editProduct(HttpServletRequest request,
                                      long productBusinessId,
                                      String easCode,
                                      String wmsCode,
                                      String productLine,
                                      int productLength,
                                      int productWidth,
                                      int productHeight,
                                      int productWeight,
                                      int boxLength,
                                      int boxWidth,
                                      int boxHeight,
                                      int boxWeight,
                                      int packageNumber,
                                      long projectId,
                                      String projectName,
                                      String productName,
                                      String productCode,
                                      String customerSKUCode,
                                      String customerProductCode,
                                      long purchaseGuidePrice,
                                      long taxRate,
                                      String taxCode,
                                      long saleGuidePrice,
                                      long actualSaleReturn,
                                      long shouldSaleReturn,
                                      long factorySendReturn,
                                      long costPrice,
                                      long outPrice,
                                      int generateCoupon,
                                      String easUnitCode){
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        logger.info("#traceId={}# [IN][handlePurchaseFlow] params: easCode = {}, wmsCode = {}, productLine = {}, productLength = {}, productWidth = {}, productHeight = {}, productWeight = {}, boxLength = {}, boxWidth = {}, boxHeight = {}, boxWeight = {}, packageNumber = {}, projectId = {}, projectName = {}, productName = {}, productCode = {}, customerSKUCode = {}, customerProductCode = {}, purchaseGuidePrice = {}, taxRate = {}, taxCode = {}, saleGuidePrice = {}, actualSaleReturn = {}, shouldSaleReturn = {}, factorySendReturn = {}, costPrice = {}, outPrice = {}, generateCoupon = {}, easUnitCode = {}",
                rpcHeader.getTraceId(),  easCode, wmsCode, productLine, productLength, productWidth, productHeight, productWeight, boxLength, boxWidth, boxHeight, boxWeight, packageNumber, projectId, projectName, productName, productCode, customerSKUCode, customerProductCode, purchaseGuidePrice, taxRate, taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, factorySendReturn, costPrice, outPrice, generateCoupon, easUnitCode);
        try {
            ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.GetByProductIdReq.Builder getProductById = ProductStructure.GetByProductIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).setProductId(productBusinessId);
            ProductStructure.GetByProductIdResp getByProductIdResp = productServiceBlockingStub.getByProductId(getProductById.build());
            ProductStructure.ProductBusiness productBusiness = getByProductIdResp.getProductBusiness();
            int easSynStatus = productBusiness.getEasSynStatus();
            //未同步EAS
            if (EasSynStatus.getStatusByCode((byte) easSynStatus) != EasSynStatus.SYN_COMPLETE){
                boolean productInfoNotEmpty = ValidationUtil.isNotEmpty(productName,productCode,easCode,wmsCode);
                if (!productInfoNotEmpty){
                    return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PRODUCT_INFO_NOT_NULL);
                }
            }

        return null;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

}
