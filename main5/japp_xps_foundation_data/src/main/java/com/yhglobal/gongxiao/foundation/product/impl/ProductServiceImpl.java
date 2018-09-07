package com.yhglobal.gongxiao.foundation.product.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.foundation.model.OperateHistoryRecord;
import com.yhglobal.gongxiao.foundation.product.dao.FoundationProductBasicDao;
import com.yhglobal.gongxiao.foundation.product.dao.FoundationProductBusinessDao;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.product.model.FoundationProductBasic;
import com.yhglobal.gongxiao.foundation.product.model.FoundationProductBusiness;
import com.yhglobal.gongxiao.foundation.project.dao.FoundationProjectDao;
import com.yhglobal.gongxiao.foundation.project.model.FoundationProject;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.status.EasSynStatus;
import com.yhglobal.gongxiao.status.FoundationNormalStatus;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * product 主要为两个表：
 *    foundation_product_basic  基础表 主要是长宽高 这些信息
 *    shaver_product_business   业务表 比如指导价/采购价等 和具体 业务/项目 相关
 */
@Service
public class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private FoundationProductBasicDao productBasicDao;

    @Autowired
    private FoundationProductBusinessDao productBusinessDao;

    @Autowired
    private FoundationProjectDao projectDao;

    //通过项目ID获取 该项目下所有的货品列表
    public void selectProductByProjectId(ProductStructure.SelectProductByProjectIdReq req,
                                         StreamObserver<ProductStructure.SelectProductByProjectIdResp> responseObserver) {
        ProductStructure.SelectProductByProjectIdResp.Builder respBuilder = ProductStructure.SelectProductByProjectIdResp.newBuilder();
        ProductStructure.ProductBusinessPage.Builder productBusinessPage = ProductStructure.ProductBusinessPage.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long projectId = req.getProjectId();
        int pageNumber = req.getPageNumber();
        int pageSize = req.getPageSize();
        String productCode = req.getProductCode();
        String productName = req.getProductName();
        logger.info("#traceId={}# [IN][selectProductByProjectId] param: projectId={}", rpcHeader.getTraceId(), projectId);
        try {

            FoundationProject project = projectDao.getByProjectId(projectId);
            String tablePrefix = project.getProjectTablePrefix();

            PageHelper.startPage(pageNumber, pageSize);
            List<FoundationProductBusiness> foundationProductBusinesses
                    = productBusinessDao.selectProductByCondition(tablePrefix, productCode, productName, "",0);
            if (foundationProductBusinesses.size() == 0) {
                logger.info("[selectProductByProjectId]  没有拿到货品信息 traceId={}, projectId={}", rpcHeader.getTraceId(), projectId);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            PageInfo<FoundationProductBusiness> purchaseOrderPageInfo = new PageInfo<>(foundationProductBusinesses);
            for (FoundationProductBusiness productBusiness : foundationProductBusinesses) {
                ProductStructure.ProductBusiness productBusiness1 = ProductStructure.ProductBusiness.newBuilder()
                        .setProductBasicId(productBusiness.getProductBasicId())
                        .setProductBusinessId(productBusiness.getProductBusinessId())
                        .setGrossProfitValue(GrpcCommonUtil.getProtoParam(productBusiness.getGrossProfitValue()))
                        .setProjectId(productBusiness.getProjectId())
                        .setProjectName(productBusiness.getProjectName())
                        .setRecordStatus(productBusiness.getRecordStatus())
                        .setEasSynStatus(productBusiness.getEasSynStatus())
                        .setProductModel(productBusiness.getProductCode())
                        .setProductName(productBusiness.getProductName())
                        .setEasCode(productBusiness.getEasCode())
                        .setWmsCode(productBusiness.getWmsCode())
                        .setEasUnitCode(productBusiness.getEasUnitCode() != null ? productBusiness.getEasUnitCode() : "")
                        .setCustomerSKUCode(productBusiness.getCustomerSKUCode() != null ? productBusiness.getCustomerSKUCode() : "")
                        .setCustomerProductCode(productBusiness.getCustomerProductCode() != null ? productBusiness.getCustomerProductCode() : "")
                        .setPurchaseGuidePrice(productBusiness.getPurchaseGuidePrice())
                        .setTaxRate(productBusiness.getTaxRate())
                        .setTaxCode(productBusiness.getTaxCode())
                        .setSaleGuidePrice(productBusiness.getSaleGuidePrice())
                        .setActualSaleReturn(productBusiness.getActualSaleReturn())
                        .setShouldSaleReturn(productBusiness.getShouldSaleReturn())
                        .setFactorySendReturn(productBusiness.getFactorySendReturn())
                        .setCostPrice(productBusiness.getCostPrice())
                        .setOutPrice(productBusiness.getOutPrice())
                        .setGenerateCoupon(productBusiness.getGenerateCoupon())
                        .setCreateTime(DateUtil.getTime(productBusiness.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(productBusiness.getLastUpdateTime()))
                        .setTraceLog(productBusiness.getTraceLog() != null ? productBusiness.getTraceLog() : "").build();
                productBusinessPage.addProductBusiness(productBusiness1);
            }
            productBusinessPage.setTotal((int) purchaseOrderPageInfo.getTotal());
            respBuilder.setProductBusinessPage(productBusinessPage);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][selectProductByProjectId] success param: projectId={}, size={}", rpcHeader.getTraceId(), projectId, foundationProductBusinesses.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //通过货品ID获取当前货品信息(不包含基础信息)
    public void getByProductId(ProductStructure.GetByProductIdReq req,
                               StreamObserver<ProductStructure.GetByProductIdResp> responseObserver) {
        ProductStructure.GetByProductIdResp.Builder respBuilder = ProductStructure.GetByProductIdResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long projectId = req.getProjectId();
        long productId = req.getProductId();
        logger.info("#traceId={}# [IN][getByProductId] param: projectId={}, productId={}", rpcHeader.getTraceId(), projectId, productId);
        try {
            FoundationProject project = projectDao.getByProjectId(projectId);
            String tablePrefix = project.getProjectTablePrefix();

            FoundationProductBusiness productBusiness = productBusinessDao.getProductBusinessById(tablePrefix,productId);
            if (productBusiness == null) {
                logger.info("[getByProductId]  没有拿到货品信息 traceId={}, projectId={}, productId={}", rpcHeader.getTraceId(), projectId, productId);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            ProductStructure.ProductBusiness productBusiness1 = ProductStructure.ProductBusiness.newBuilder()
                    .setProductBasicId(productBusiness.getProductBasicId())
                    .setProductBusinessId(productBusiness.getProductBusinessId())
                    .setGrossProfitValue(GrpcCommonUtil.getProtoParam(productBusiness.getGrossProfitValue()))
                    .setProjectId(productBusiness.getProjectId())
                    .setProjectName(productBusiness.getProjectName())
                    .setRecordStatus(productBusiness.getRecordStatus())
                    .setEasSynStatus(productBusiness.getEasSynStatus())
                    .setProductModel(productBusiness.getProductCode())
                    .setProductName(productBusiness.getProductName())
                    .setEasCode(productBusiness.getEasCode())
                    .setWmsCode(productBusiness.getWmsCode())
                    .setEasUnitCode(productBusiness.getEasUnitCode() != null ? productBusiness.getEasUnitCode() : "")
                    .setCustomerSKUCode(productBusiness.getCustomerSKUCode() != null ? productBusiness.getCustomerSKUCode() : "")
                    .setCustomerProductCode(productBusiness.getCustomerProductCode() != null ? productBusiness.getCustomerProductCode() : "")
                    .setPurchaseGuidePrice(productBusiness.getPurchaseGuidePrice())
                    .setTaxRate(productBusiness.getTaxRate())
                    .setTaxCode(productBusiness.getTaxCode())
                    .setSaleGuidePrice(productBusiness.getSaleGuidePrice())
                    .setActualSaleReturn(productBusiness.getActualSaleReturn())
                    .setShouldSaleReturn(productBusiness.getShouldSaleReturn())
                    .setFactorySendReturn(productBusiness.getFactorySendReturn())
                    .setCostPrice(productBusiness.getCostPrice())
                    .setOutPrice(productBusiness.getOutPrice())
                    .setGenerateCoupon(productBusiness.getGenerateCoupon())
                    .setCreateTime(DateUtil.getTime(productBusiness.getCreateTime()))
                    .setLastUpdateTime(DateUtil.getTime(productBusiness.getLastUpdateTime()))
                    .setTraceLog(productBusiness.getTraceLog() != null ? productBusiness.getTraceLog() : "").build();
            respBuilder.setProductBusiness(productBusiness1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][getByProductId] success param: projectId={}, productId={}", rpcHeader.getTraceId(), projectId, productId);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //通过货品code 获取当前货品信息(不包含基础信息)
    public void getByProductModel(ProductStructure.GetByProductModelReq req,
                                  StreamObserver<ProductStructure.GetByProductModelResp> responseObserver) {
        ProductStructure.GetByProductModelResp.Builder respBuilder = ProductStructure.GetByProductModelResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long projectId = req.getProjectId();
        String productModel = req.getProductModel();
        logger.info("#traceId={}# [IN][GetByProductCode] param: projectId={}, productId={}", rpcHeader.getTraceId(), projectId, productModel);
        try {
            FoundationProject project = projectDao.getByProjectId(projectId);
            String tablePrefix = project.getProjectTablePrefix();

            FoundationProductBusiness productBusiness = productBusinessDao.getProductBusinessByModel(tablePrefix,productModel);
            if (productBusiness == null) {
                logger.info("[getByProductModel]  没有拿到货品信息 traceId={}, projectId={}, productModel={}", rpcHeader.getTraceId(), projectId, productModel);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            ProductStructure.ProductBusiness productBusiness1 = ProductStructure.ProductBusiness.newBuilder()
                    .setProductBasicId(productBusiness.getProductBasicId())
                    .setGrossProfitValue(GrpcCommonUtil.getProtoParam(productBusiness.getGrossProfitValue()))
                    .setProductBusinessId(productBusiness.getProductBusinessId())
                    .setProjectId(productBusiness.getProjectId())
                    .setProjectName(productBusiness.getProjectName())
                    .setRecordStatus(productBusiness.getRecordStatus())
                    .setEasSynStatus(productBusiness.getEasSynStatus())
                    .setProductModel(productBusiness.getProductCode())
                    .setProductName(productBusiness.getProductName())
                    .setEasCode(productBusiness.getEasCode())
                    .setWmsCode(productBusiness.getWmsCode())
                    .setEasUnitCode(productBusiness.getEasUnitCode() != null ? productBusiness.getEasUnitCode() : "")
                    .setCustomerSKUCode(productBusiness.getCustomerSKUCode() != null ? productBusiness.getCustomerSKUCode() : "")
                    .setCustomerProductCode(productBusiness.getCustomerProductCode() != null ? productBusiness.getCustomerProductCode() : "")
                    .setPurchaseGuidePrice(productBusiness.getPurchaseGuidePrice())
                    .setTaxRate(productBusiness.getTaxRate())
                    .setTaxCode(productBusiness.getTaxCode())
                    .setSaleGuidePrice(productBusiness.getSaleGuidePrice())
                    .setActualSaleReturn(productBusiness.getActualSaleReturn())
                    .setShouldSaleReturn(productBusiness.getShouldSaleReturn())
                    .setFactorySendReturn(productBusiness.getFactorySendReturn())
                    .setCostPrice(productBusiness.getCostPrice())
                    .setOutPrice(productBusiness.getOutPrice())
                    .setGenerateCoupon(productBusiness.getGenerateCoupon())
                    .setCreateTime(DateUtil.getTime(productBusiness.getCreateTime()))
                    .setLastUpdateTime(DateUtil.getTime(productBusiness.getLastUpdateTime()))
                    .setTraceLog(productBusiness.getTraceLog() != null ? productBusiness.getTraceLog() : "").build();
            respBuilder.setProductBusiness(productBusiness1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][GetByProductCode] success param: projectId={}, productId={}", rpcHeader.getTraceId(), projectId, productModel);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //通过货品code 获取当前货品信息(含基础信息)
    public void getProductDetailByModel(ProductStructure.GetProductDetailByModelReq req,
                                        StreamObserver<ProductStructure.GetProductDetailByModelResp> responseObserver) {
        ProductStructure.GetProductDetailByModelResp.Builder respBuilder = ProductStructure.GetProductDetailByModelResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long projectId = req.getProjectId();
        String productModel = req.getProductModel();
        logger.info("#traceId={}# [IN][getProductDetailByModel] param:  productModel={}", rpcHeader.getTraceId(), productModel);
        try {
            FoundationProject project = projectDao.getByProjectId(projectId);
            String tablePrefix = project.getProjectTablePrefix();

            FoundationProductBusiness productBusiness = productBusinessDao.getProductBusinessByModel(tablePrefix,productModel);
            if (productBusiness == null) {
                logger.info("[getProductDetailByModel]  没有拿到货品业务信息 traceId={}, productModel={}", rpcHeader.getTraceId(), productModel);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            FoundationProductBasic productBasic = productBasicDao.getProductBasicInfoById(productBusiness.getProductBasicId());
            if (productBasic == null) {
                logger.info("[getProductDetailByModel]  没有拿到货品基础信息 traceId={}, productModel={}", rpcHeader.getTraceId(), productModel);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            ProductStructure.ProductBasicDetail productBasicDetail = ProductStructure.ProductBasicDetail.newBuilder()
                    .setProductBasicId(productBusiness.getProductBasicId())
                    .setProductBusinessId(productBusiness.getProductBusinessId())
                    .setProjectId(productBusiness.getProjectId())
                    .setProjectName(productBusiness.getProjectName())
                    .setRecordStatus(productBusiness.getRecordStatus())
                    .setEasSynStatus(productBusiness.getEasSynStatus())
                    .setProductModel(productBusiness.getProductCode())
                    .setProductName(productBusiness.getProductName())
                    .setEasCode(productBusiness.getEasCode())
                    .setWmsCode(productBusiness.getWmsCode())
                    .setEasUnitCode(productBusiness.getEasUnitCode() != null ? productBusiness.getEasUnitCode() : "")
                    .setCustomerSKUCode(productBusiness.getCustomerSKUCode())
                    .setCustomerProductCode(productBusiness.getCustomerProductCode())
                    .setPurchaseGuidePrice(productBusiness.getPurchaseGuidePrice())
                    .setTaxRate(productBusiness.getTaxRate())
                    .setTaxCode(productBusiness.getTaxCode())
                    .setSaleGuidePrice(productBusiness.getSaleGuidePrice())
                    .setActualSaleReturn(productBusiness.getActualSaleReturn())
                    .setShouldSaleReturn(productBusiness.getShouldSaleReturn())
                    .setFactorySendReturn(productBusiness.getFactorySendReturn())
                    .setCostPrice(productBusiness.getCostPrice())
                    .setOutPrice(productBusiness.getOutPrice())
                    .setGenerateCoupon(productBusiness.getGenerateCoupon())
                    .setProductBasicId(productBasic.getId())
                    .setProductLine(productBasic.getProductLine())
                    .setProductLength(productBasic.getProductLength())
                    .setProductWidth(productBasic.getProductWidth())
                    .setProductHeight(productBasic.getProductHeight())
                    .setProductWeight(productBasic.getProductWeight())
                    .setProductNetWeight(productBasic.getProductNetWeight())
                    .setProductGrossWeight(productBasic.getProductGrossWeight())
                    .setBoxWidth(productBasic.getBoxWidth())
                    .setBoxHeight(productBasic.getBoxHeight())
                    .setBoxLength(productBasic.getBoxLength())
                    .setBoxWeight(productBasic.getBoxWeight())
                    .setGuaranteePeriod(productBasic.getGuaranteePeriod())
                    .setPackageNumber(productBasic.getPackageNumber())
                    .build();
            respBuilder.setProductBasicDetail(productBasicDetail);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][getProductDetailByModel] success param: productModel={}", rpcHeader.getTraceId(), productModel);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    //通过wms商品编码获取当前货品信息(不含基础信息)
    public void getByWmsProductCode(ProductStructure.GetByWmsProductCodeReq req,
                                    StreamObserver<ProductStructure.GetByWmsProductCodeResp> responseObserver) {
        ProductStructure.GetByWmsProductCodeResp.Builder respBuilder = ProductStructure.GetByWmsProductCodeResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String productWmsCode = req.getProductWmsCode();
        long projectId = req.getProjectId();
        logger.info("#traceId={}# [IN][getByWmsProductCode] param:  productWmsCode={}", rpcHeader.getTraceId(), productWmsCode);

        try {
            FoundationProject project = projectDao.getByProjectId((long)projectId);
            String tablePrefix = project.getProjectTablePrefix();

            FoundationProductBusiness productBusiness = productBusinessDao.getProductBusinessByWmsCode(tablePrefix,productWmsCode);
            if (productBusiness == null) {
                logger.info("[getByWmsProductCode]  没有拿到货品业务信息 traceId={}, productWmsCode={}", rpcHeader.getTraceId(), productWmsCode);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            ProductStructure.ProductBusiness productBusiness1 = ProductStructure.ProductBusiness.newBuilder()
                    .setProductBasicId(productBusiness.getProductBasicId())
                    .setProductBusinessId(productBusiness.getProductBusinessId())
                    .setProjectId(productBusiness.getProjectId())
                    .setProjectName(productBusiness.getProjectName())
                    .setRecordStatus(productBusiness.getRecordStatus())
                    .setEasSynStatus(productBusiness.getEasSynStatus())
                    .setProductModel(productBusiness.getProductCode())
                    .setProductName(productBusiness.getProductName())
                    .setEasCode(productBusiness.getEasCode())
                    .setWmsCode(productBusiness.getWmsCode())
                    .setEasUnitCode(productBusiness.getEasUnitCode() != null ? productBusiness.getEasUnitCode() : "")
                    .setCustomerSKUCode(productBusiness.getCustomerSKUCode() != null ? productBusiness.getCustomerSKUCode() : "")
                    .setCustomerProductCode(productBusiness.getCustomerProductCode() != null ? productBusiness.getCustomerProductCode() : "")
                    .setPurchaseGuidePrice(productBusiness.getPurchaseGuidePrice())
                    .setTaxRate(productBusiness.getTaxRate())
                    .setTaxCode(productBusiness.getTaxCode())
                    .setSaleGuidePrice(productBusiness.getSaleGuidePrice())
                    .setActualSaleReturn(productBusiness.getActualSaleReturn())
                    .setShouldSaleReturn(productBusiness.getShouldSaleReturn())
                    .setFactorySendReturn(productBusiness.getFactorySendReturn())
                    .setCostPrice(productBusiness.getCostPrice())
                    .setOutPrice(productBusiness.getOutPrice())
                    .setGenerateCoupon(productBusiness.getGenerateCoupon())
                    .setCreateTime(DateUtil.getTime(productBusiness.getCreateTime()))
                    .setLastUpdateTime(DateUtil.getTime(productBusiness.getLastUpdateTime()))
                    .setTraceLog(productBusiness.getTraceLog() != null ? productBusiness.getTraceLog() : "")
                    .build();
            respBuilder.setProductBusiness(productBusiness1);
            logger.info("#traceId={}# [OUT][getByWmsProductCode] param:  productWmsCode={}", rpcHeader.getTraceId(), productWmsCode);

            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //?? 待和原型核对
    public void selectProductByCondition(ProductStructure.SelectProductByConditionReq req,
                                         StreamObserver<ProductStructure.SelectProductByConditionResp> responseObserver) {
        ProductStructure.SelectProductByConditionResp.Builder respBuilder = ProductStructure.SelectProductByConditionResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long projectId = req.getProjectId();
        String productCode = req.getProductCode();
        String productEasCode = req.getProductEasCode();
        String productName = req.getProductName();
        int status = req.getStatus();
        int pageNumber = req.getPageNumber();
        int pageSize = req.getPageSize();
        Map<Integer, Byte> map = new HashMap<>();
        map.put(0, FoundationNormalStatus.UNKNOWN.getStatus());
        map.put(1, FoundationNormalStatus.INIT.getStatus());
        map.put(2, FoundationNormalStatus.COMMITTED.getStatus());
        map.put(3, FoundationNormalStatus.AUDITED.getStatus());
        map.put(4, FoundationNormalStatus.REJECTED.getStatus());
        map.put(4, FoundationNormalStatus.CANCEL.getStatus());
        logger.info("#traceId={}# [IN][selectProductByCondition] param:  projectId={}, productCode={}, productEasCode={}, productName={}, status={}, pageNumber={}, pageSize={}",
                rpcHeader.getTraceId(), projectId, productCode, productEasCode, productName, status, pageNumber, pageSize);
        try {
            FoundationProject project = projectDao.getByProjectId((long)projectId);
            String tablePrefix = project.getProjectTablePrefix();

            PageHelper.startPage(pageNumber, pageSize);
            List<FoundationProductBusiness> foundationProductBusinesses = productBusinessDao.selectProductByCondition(tablePrefix, productCode, productName, productEasCode, map.get(status));
            if (foundationProductBusinesses.size() == 0) {
                logger.warn("traceId={}# [IN][selectProductByCondition] not get product info");
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            PageInfo<FoundationProductBusiness> foundationProductBusinessPageInfo = new PageInfo<>(foundationProductBusinesses);
            for (FoundationProductBusiness productBusiness : foundationProductBusinesses) {
                ProductStructure.ProductBusiness productBusiness1 = ProductStructure.ProductBusiness.newBuilder()
                        .setProductBasicId(productBusiness.getProductBasicId())
                        .setGrossProfitValue(GrpcCommonUtil.getProtoParam(productBusiness.getGrossProfitValue()))
                        .setProductBusinessId(productBusiness.getProductBusinessId())
                        .setProjectId(productBusiness.getProjectId())
                        .setProjectName(productBusiness.getProjectName())
                        .setRecordStatus(productBusiness.getRecordStatus())
                        .setEasSynStatus(productBusiness.getEasSynStatus())
                        .setProductModel(productBusiness.getProductCode())
                        .setProductName(productBusiness.getProductName())
                        .setEasCode(productBusiness.getEasCode())
                        .setWmsCode(productBusiness.getWmsCode())
                        .setEasUnitCode(productBusiness.getEasUnitCode() != null ? productBusiness.getEasUnitCode() : "")
                        .setCustomerSKUCode(productBusiness.getCustomerSKUCode() != null ? productBusiness.getCustomerSKUCode() : "")
                        .setCustomerProductCode(productBusiness.getCustomerProductCode() != null ? productBusiness.getCustomerProductCode() : "")
                        .setPurchaseGuidePrice(productBusiness.getPurchaseGuidePrice())
                        .setTaxRate(productBusiness.getTaxRate())
                        .setTaxCode(productBusiness.getTaxCode())
                        .setSaleGuidePrice(productBusiness.getSaleGuidePrice())
                        .setActualSaleReturn(productBusiness.getActualSaleReturn())
                        .setShouldSaleReturn(productBusiness.getShouldSaleReturn())
                        .setFactorySendReturn(productBusiness.getFactorySendReturn())
                        .setCostPrice(productBusiness.getCostPrice())
                        .setOutPrice(productBusiness.getOutPrice())
                        .setGenerateCoupon(productBusiness.getGenerateCoupon())
                        .setCreateTime(DateUtil.getTime(productBusiness.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(productBusiness.getLastUpdateTime()))
                        .setTraceLog(productBusiness.getTraceLog() != null ? productBusiness.getTraceLog() : "").build();
                respBuilder.addProductBusiness(productBusiness1);
            }
            respBuilder.setTotal(foundationProductBusinessPageInfo.getTotal());
            logger.info("#traceId={}# [OUT][selectProductByCondition] success return size = {}", rpcHeader.getTraceId(), foundationProductBusinesses.size());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 3. 新增新货品时需要通过easCode, 判定是否有已审核的同easCode的货品
     * 3.1 如果有, 判定该项目是否有已审核该easCode的货品
     * 3.1.1 如果有, 返回货品已存在异常
     * 3.1.2 如果没有, 插入新的product_business记录
     * 3.2 如果没有, 插入新的product_basic记录和新的product_business记录
     *
     * @param req
     * @param responseObserver
     */
    public void insertProductInfo(ProductStructure.InsertProductInfoReq req,
                                  StreamObserver<ProductStructure.InsertProductInfoResp> responseObserver) {
        ProductStructure.InsertProductInfoResp.Builder respBuilder = ProductStructure.InsertProductInfoResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        ProductStructure.ProductBasicDetail productBasicDetail = req.getProductBasicDetail();
        logger.info("#traceId={}# [IN][insertProductInfo] param:  productBasicDetail={}", rpcHeader.getTraceId(), productBasicDetail.toString());
        try {

            String easCode = productBasicDetail.getEasCode();
            String projectId = productBasicDetail.getProjectId();
            long productBasicId = 0;
            FoundationProject project = projectDao.getByProjectId(Long.parseLong(projectId));
            String tablePrefix = project.getProjectTablePrefix();
            FoundationProductBasic productBasic = productBasicDao.getProductBasicByEasCode(easCode);
            if (productBasic != null) {
                FoundationProductBusiness foundationProductBusiness = productBusinessDao.getProductBusinessByEasCode(tablePrefix, easCode);
                if (foundationProductBusiness != null) {
                    logger.warn("#traceId={}, projectId = {} easCode = {} 的记录已经存在", rpcHeader.getTraceId(), projectId, easCode);
                    //1. 货品基础信息存在,货品业务信息存在,不然添加
                    GongxiaoRpc.RpcResult rpcResult = GongxiaoRpc.RpcResult.newBuilder()
                            .setReturnCode(ErrorCode.PRODUCT_ALREADY_EXIST.getErrorCode())
                            .setMsg(ErrorCode.PRODUCT_ALREADY_EXIST.getMessage())
                            .build();
                    respBuilder.setRpcResult(rpcResult);
                    return;
                }
            } else {
                productBasic = new FoundationProductBasic();
                productBasic.setEasCode(easCode);
                productBasic.setWmsCode(productBasicDetail.getWmsCode());
                productBasic.setRecordStatus(FoundationNormalStatus.INIT.getStatus());
                productBasic.setEasUnit(productBasicDetail.getEasUnitCode());
                productBasic.setCategoryId(productBasicDetail.getCategoryId());
                productBasic.setCategoryName(productBasicDetail.getCategoryName());
                productBasic.setProductLine(productBasicDetail.getProductLine());
                productBasic.setProductLength(productBasicDetail.getProductLength());
                productBasic.setProductWidth(productBasicDetail.getProductWidth());
                productBasic.setProductHeight(productBasicDetail.getProductHeight());
                productBasic.setProductWeight(productBasicDetail.getProductWeight());
                productBasic.setProductGrossWeight(productBasicDetail.getProductGrossWeight());
                productBasic.setProductNetWeight(productBasicDetail.getProductNetWeight());
                productBasic.setBoxWidth(productBasicDetail.getBoxWidth());
                productBasic.setBoxLength(productBasicDetail.getBoxLength());
                productBasic.setBoxHeight(productBasicDetail.getBoxHeight());
                productBasic.setBoxWeight(productBasicDetail.getBoxWeight());
                productBasic.setGuaranteePeriod(productBasicDetail.getGuaranteePeriod());
                productBasic.setPackageNumber(productBasicDetail.getPackageNumber());
                productBasic.setCreateTime(new Date());
                productBasic.setLastUpdateTime(new Date());
                productBasicDao.insertProductBasic(productBasic);
                productBasicId = productBasic.getId();
            }
            FoundationProductBusiness foundationProductBusiness = new FoundationProductBusiness();
            foundationProductBusiness.setProductBasicId(productBasicId);
            foundationProductBusiness.setProjectId(productBasicDetail.getProjectId());
            foundationProductBusiness.setProjectName(productBasicDetail.getProjectName());
            foundationProductBusiness.setRecordStatus(FoundationNormalStatus.INIT.getStatus());
            foundationProductBusiness.setEasSynStatus(EasSynStatus.INIT.getStatus());
            foundationProductBusiness.setProductName(productBasicDetail.getProductName());
            foundationProductBusiness.setProductCode(productBasicDetail.getProductModel());
            foundationProductBusiness.setEasCode(productBasicDetail.getEasCode());
            foundationProductBusiness.setEasUnitCode(productBasicDetail.getEasUnitCode());
            foundationProductBusiness.setWmsCode(productBasicDetail.getWmsCode());
            foundationProductBusiness.setCustomerSKUCode(productBasicDetail.getCustomerSKUCode());
            foundationProductBusiness.setCustomerProductCode(productBasicDetail.getCustomerProductCode());
            foundationProductBusiness.setPurchaseGuidePrice(DoubleScale.multipleMillion(productBasicDetail.getPurchaseGuidePrice()));
            foundationProductBusiness.setTaxRate(DoubleScale.multipleMillion(productBasicDetail.getTaxRate()));
            foundationProductBusiness.setTaxCode(productBasicDetail.getTaxCode());
            foundationProductBusiness.setSaleGuidePrice(DoubleScale.multipleMillion(productBasicDetail.getSaleGuidePrice()));
            foundationProductBusiness.setActualSaleReturn(DoubleScale.multipleHundred(productBasicDetail.getActualSaleReturn()));
            foundationProductBusiness.setShouldSaleReturn(DoubleScale.multipleHundred(productBasicDetail.getShouldSaleReturn()));
            foundationProductBusiness.setFactorySendReturn(DoubleScale.multipleHundred(productBasicDetail.getFactorySendReturn()));
            foundationProductBusiness.setCostPrice(DoubleScale.multipleHundred(productBasicDetail.getCostPrice()));
            foundationProductBusiness.setOutPrice(DoubleScale.multipleHundred(productBasicDetail.getOutPrice()));
            foundationProductBusiness.setGenerateCoupon((byte) productBasicDetail.getGenerateCoupon());
            foundationProductBusiness.setCreateTime(new Date());
            foundationProductBusiness.setLastUpdateTime(new Date());
            //设置日志
            ArrayList<OperateHistoryRecord> recordList
                    = JSON.parseObject("[]", new TypeReference<ArrayList<OperateHistoryRecord>>() {});
            OperateHistoryRecord operateRecord = new OperateHistoryRecord();
            operateRecord.setOperateFunction("新增");
            operateRecord.setOperateTime(new Date());
            operateRecord.setOperateId(rpcHeader.getUid());
            operateRecord.setOperateName(rpcHeader.getUsername());
            recordList.add(operateRecord);
            String traceJson = JSON.toJSONString(recordList);
            foundationProductBusiness.setTraceLog(traceJson);
            productBusinessDao.insertProductBasicInfo(tablePrefix,foundationProductBusiness);
            logger.info("#traceId={}# [IN][insertProductInfo] success:  productBasicDetail={}", rpcHeader.getTraceId(), productBasicDetail.toString());
            GongxiaoRpc.RpcResult rpcResult = GongxiaoRpc.RpcResult.newBuilder()
                    .setReturnCode(ErrorCode.SUCCESS.getErrorCode())
                    .setMsg(ErrorCode.SUCCESS.getMessage())
                    .build();
            respBuilder.setRpcResult(rpcResult);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    // 编辑货品信息(未曾编辑/曾编辑的区分是所传业务单号是否为空; 区分是否已生效(看主数据是否有该值), 判断basic状态)
    // 注：当前未用到
    public void editProduct(ProductStructure.EditProductReq req,
                            StreamObserver<ProductStructure.EditProductResp> responseObserver) {
        ProductStructure.EditProductResp.Builder respBuilder = ProductStructure.EditProductResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        ProductStructure.ProductBasicDetail productBasicDetail = req.getProductBasicDetail();
        int editType = req.getEditType();
        logger.info("#traceId={}# [IN][editProduct] param: editType={}, productBasicDetail={}", rpcHeader.getTraceId(), editType, productBasicDetail.toString());
        try {
            String easCode = productBasicDetail.getEasCode();
            String projectId = productBasicDetail.getProjectId();
            FoundationProject project = projectDao.getByProjectId(Long.parseLong(projectId));
            String tablePrefix = project.getProjectTablePrefix();
            long productBasicId = productBasicDetail.getProductBasicId();
            FoundationProductBusiness foundationProductBusiness = productBusinessDao.getProductBusinessByEasCode(tablePrefix, easCode);
            FoundationProductBasic foundationProductBasic = productBasicDao.getProductBasicInfoById(productBasicId);
            if (foundationProductBusiness == null || foundationProductBasic == null) {
                logger.warn("#traceId={}[editProduct], projectId = {} easCode = {} 的记录不存在存在", rpcHeader.getTraceId(), projectId, easCode);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }

            Byte easSynStatus = foundationProductBusiness.getEasSynStatus();
            Byte recordStatus = foundationProductBusiness.getRecordStatus();
            /**
             4.1 未提交,未生效货品编辑   都可编辑
             4.2 未提交,已生效货品编辑   核心信息不可编辑,编辑的时候需要判定货品是否生效
             4.3 已提交,未生效货品编辑   核心信息不可编辑
             */
            if (FoundationNormalStatus.getStatusByCode(recordStatus) == FoundationNormalStatus.INIT) {
                if (EasSynStatus.getStatusByCode(easSynStatus) != EasSynStatus.SYN_COMPLETE) {
                    foundationProductBusiness.setProductName(productBasicDetail.getProductName());
                    foundationProductBusiness.setProductCode(productBasicDetail.getProductModel());
                    foundationProductBusiness.setEasCode(productBasicDetail.getEasCode());
                    foundationProductBusiness.setEasUnitCode(productBasicDetail.getEasUnitCode());
                    foundationProductBusiness.setWmsCode(productBasicDetail.getWmsCode());

                    foundationProductBasic.setEasCode(productBasicDetail.getEasCode());
                    foundationProductBasic.setWmsCode(productBasicDetail.getWmsCode());
                    foundationProductBasic.setEasUnit(productBasicDetail.getEasUnitCode());
                }
            }
            //货品业务信息
            foundationProductBusiness.setCustomerSKUCode(productBasicDetail.getCustomerSKUCode());
            foundationProductBusiness.setCustomerProductCode(productBasicDetail.getCustomerProductCode());
            foundationProductBusiness.setPurchaseGuidePrice(DoubleScale.multipleMillion(productBasicDetail.getPurchaseGuidePrice()));
            foundationProductBusiness.setTaxRate(DoubleScale.multipleMillion(productBasicDetail.getTaxRate()));
            foundationProductBusiness.setTaxCode(productBasicDetail.getTaxCode());
            foundationProductBusiness.setSaleGuidePrice(DoubleScale.multipleMillion(productBasicDetail.getTaxRate()));
            foundationProductBusiness.setActualSaleReturn(DoubleScale.multipleMillion(productBasicDetail.getActualSaleReturn()));
            foundationProductBusiness.setShouldSaleReturn(DoubleScale.multipleMillion(productBasicDetail.getShouldSaleReturn()));
            foundationProductBusiness.setFactorySendReturn(DoubleScale.multipleMillion(productBasicDetail.getFactorySendReturn()));
            foundationProductBusiness.setCostPrice(DoubleScale.multipleMillion(productBasicDetail.getCostPrice()));
            foundationProductBusiness.setOutPrice(DoubleScale.multipleMillion(productBasicDetail.getOutPrice()));
            foundationProductBusiness.setGenerateCoupon((byte) productBasicDetail.getGenerateCoupon());
            foundationProductBusiness.setLastUpdateTime(new Date());
            //日志信息
            String traceLog = foundationProductBusiness.getTraceLog();
            ArrayList<OperateHistoryRecord> recordList
                    = JSON.parseObject(traceLog, new TypeReference<ArrayList<OperateHistoryRecord>>() {
            });
            OperateHistoryRecord operateRecord = new OperateHistoryRecord();
            operateRecord.setOperateFunction("编辑");
            operateRecord.setOperateTime(new Date());
            operateRecord.setOperateId(rpcHeader.getUid());
            operateRecord.setOperateName(rpcHeader.getUsername());
            recordList.add(operateRecord);
            String traceJson = JSON.toJSONString(recordList);
            foundationProductBusiness.setTraceLog(traceJson);
            //货品基础信息
            foundationProductBasic.setProductLength(productBasicDetail.getProductLength());
            foundationProductBasic.setProductWidth(productBasicDetail.getProductWidth());
            foundationProductBasic.setProductHeight(productBasicDetail.getProductHeight());
            foundationProductBasic.setProductWeight(productBasicDetail.getProductWeight());
            foundationProductBasic.setProductNetWeight(productBasicDetail.getProductNetWeight());
            foundationProductBasic.setProductGrossWeight(productBasicDetail.getProductGrossWeight());
            foundationProductBasic.setBoxLength(productBasicDetail.getBoxLength());
            foundationProductBasic.setBoxWidth(productBasicDetail.getBoxWidth());
            foundationProductBasic.setBoxHeight(productBasicDetail.getBoxHeight());
            foundationProductBasic.setBoxWeight(productBasicDetail.getBoxWeight());
            foundationProductBasic.setPackageNumber(productBasicDetail.getPackageNumber());
            foundationProductBasic.setLastUpdateTime(new Date());

            productBusinessDao.updateProductBusiness(tablePrefix,foundationProductBusiness);
            productBasicDao.updateProductById(foundationProductBasic);
            logger.info("#traceId={}# [OUT][editProduct] success:  ", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    // 审核货品信息(审核通过/拒绝)
    // 注：当前未用到
    public void auditProduct(ProductStructure.AuditProductReq req,
                             StreamObserver<ProductStructure.AuditProductResp> responseObserver) {
        ProductStructure.AuditProductResp.Builder respBuilder = ProductStructure.AuditProductResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long productBusinessId = req.getProductBusinessId();
        int auditStatus = req.getAuditStatus();
        long projectId = req.getProjectId();

        Map<Integer, Byte> map = new HashMap<>();
        map.put(1, FoundationNormalStatus.AUDITED.getStatus());
        map.put(2, FoundationNormalStatus.REJECTED.getStatus());
        logger.info("#traceId={}# [IN][auditProduct] param: productBusinessId={}, auditStatus={}", rpcHeader.getTraceId(), productBusinessId, auditStatus);
        try {

            FoundationProject project = projectDao.getByProjectId((long)projectId);
            String tablePrefix = project.getProjectTablePrefix();

            FoundationProductBusiness foundationProductBusiness = productBusinessDao.getProductBusinessById(tablePrefix,productBusinessId);
            Long productBasicId = foundationProductBusiness.getProductBasicId();
            FoundationProductBasic foundationProductBasic = productBasicDao.getProductBasicInfoById(productBasicId);
            Byte recordStatus = foundationProductBasic.getRecordStatus();

            if (FoundationNormalStatus.getStatusByCode(recordStatus) != FoundationNormalStatus.AUDITED) {
                int i = productBasicDao.updateRecordStatus(productBasicId, map.get(auditStatus));
            }
            productBusinessDao.updateRecordStatus(tablePrefix,productBusinessId, map.get(auditStatus));
            logger.info("#traceId={}# [IN][auditProduct] success ", rpcHeader.getTraceId());
            GongxiaoRpc.RpcResult rpcResult = GongxiaoRpc.RpcResult.newBuilder()
                    .setReturnCode(ErrorCode.SUCCESS.getErrorCode())
                    .setMsg(ErrorCode.SUCCESS.getMessage()).build();
            respBuilder.setRpcResult(rpcResult);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    // 根据货品id获取货品信息详情(含基础信息)
    public void getProductDetailById(ProductStructure.GetProductDetailByIdReq req,
                                     StreamObserver<ProductStructure.GetProductDetailByIdResp> responseObserver) {
        ProductStructure.GetProductDetailByIdResp.Builder respBuilder = ProductStructure.GetProductDetailByIdResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long projectId = req.getProjectId();
        long productBusinessId = req.getProductBusinessId();
        logger.info("#traceId={}# [IN][getProductDetailById] param: productBusinessId={}", rpcHeader.getTraceId(), productBusinessId);
        try {
            FoundationProject project = projectDao.getByProjectId((long)projectId);
            String tablePrefix = project.getProjectTablePrefix();

            FoundationProductBusiness foundationProductBusiness = productBusinessDao.getProductBusinessById(tablePrefix,productBusinessId);
            FoundationProductBasic foundationProductBasic = productBasicDao.getProductBasicInfoById(foundationProductBusiness.getProductBasicId());

            ProductStructure.ProductBasicDetail.Builder productBasicDetail = ProductStructure.ProductBasicDetail.newBuilder();
            productBasicDetail.setProductBasicId(foundationProductBasic.getId());
            productBasicDetail.setProductBusinessId(foundationProductBusiness.getProductBusinessId());
            productBasicDetail.setEasCode(foundationProductBusiness.getEasCode());
            productBasicDetail.setWmsCode(foundationProductBusiness.getWmsCode());
            productBasicDetail.setCategoryId(foundationProductBasic.getCategoryId());
            productBasicDetail.setCategoryName(foundationProductBasic.getCategoryName());
            productBasicDetail.setProductLine(foundationProductBasic.getProductLine());
            productBasicDetail.setProductLength(foundationProductBasic.getProductLength());
            productBasicDetail.setProductWidth(foundationProductBasic.getProductWidth());
            productBasicDetail.setProductHeight(foundationProductBasic.getProductHeight());
            productBasicDetail.setProductWeight(foundationProductBasic.getProductWeight());
            productBasicDetail.setProductNetWeight(foundationProductBasic.getProductNetWeight());
            productBasicDetail.setProductGrossWeight(foundationProductBasic.getProductGrossWeight());
            productBasicDetail.setBoxLength(foundationProductBasic.getBoxLength());
            productBasicDetail.setBoxWidth(foundationProductBasic.getBoxWidth());
            productBasicDetail.setBoxHeight(foundationProductBasic.getBoxHeight());
            productBasicDetail.setBoxWeight(foundationProductBasic.getBoxWeight());
            productBasicDetail.setGuaranteePeriod(foundationProductBasic.getGuaranteePeriod());
            productBasicDetail.setPackageNumber(foundationProductBasic.getPackageNumber());
            productBasicDetail.setProjectId(foundationProductBusiness.getProjectId());
            productBasicDetail.setProjectName(foundationProductBusiness.getProjectName());
            productBasicDetail.setRecordStatus(foundationProductBusiness.getRecordStatus());
            productBasicDetail.setEasSynStatus(foundationProductBusiness.getEasSynStatus());
            productBasicDetail.setProductName(foundationProductBusiness.getProductName());
            productBasicDetail.setProductModel(foundationProductBusiness.getProductCode());
            productBasicDetail.setCustomerSKUCode(foundationProductBusiness.getCustomerSKUCode());
            productBasicDetail.setCustomerProductCode(foundationProductBusiness.getCustomerProductCode());
            productBasicDetail.setPurchaseGuidePrice(foundationProductBusiness.getPurchaseGuidePrice());
            productBasicDetail.setTaxRate(foundationProductBusiness.getTaxRate());
            productBasicDetail.setTaxCode(foundationProductBusiness.getTaxCode());
            productBasicDetail.setSaleGuidePrice(foundationProductBusiness.getSaleGuidePrice());
            productBasicDetail.setActualSaleReturn(foundationProductBusiness.getActualSaleReturn());
            productBasicDetail.setShouldSaleReturn(foundationProductBusiness.getShouldSaleReturn());
            productBasicDetail.setFactorySendReturn(foundationProductBusiness.getFactorySendReturn());
            productBasicDetail.setCostPrice(foundationProductBusiness.getCostPrice());
            productBasicDetail.setOutPrice(foundationProductBusiness.getOutPrice());
            productBasicDetail.setGenerateCoupon(foundationProductBusiness.getGenerateCoupon());
            productBasicDetail.setEasUnitCode(foundationProductBusiness.getEasUnitCode());

            //日志
            String traceLog = foundationProductBusiness.getTraceLog();
            ArrayList<OperateHistoryRecord> recordList
                    = JSON.parseObject(traceLog, new TypeReference<ArrayList<OperateHistoryRecord>>() {});
            for (OperateHistoryRecord operateHistoryRecord:recordList){
                ProductStructure.OperateHistoryRecord operateHistoryRecord1 = ProductStructure.OperateHistoryRecord.newBuilder()
                        .setOperateFunction(operateHistoryRecord.getOperateFunction())
                        .setOperateName(operateHistoryRecord.getOperateName())
                        .setOperateTime(DateUtil.getTime(operateHistoryRecord.getOperateTime()))
                        .build();
                productBasicDetail.addOperateHistoryRecord(operateHistoryRecord1);
            }
            respBuilder.setProductBasicDetail(productBasicDetail);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //提交货品业务信息(提交新的关系,通过businessID判断是否之前有编辑)
    // 注：当前未用到
    public void commitProductBusinessInfo(ProductStructure.CommitProductBusinessInfoReq req,
                                          StreamObserver<ProductStructure.CommitProductBusinessInfoResp> responseObserver) {
        ProductStructure.CommitProductBusinessInfoResp.Builder respBuilder = ProductStructure.CommitProductBusinessInfoResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long productBusinessId = req.getProductBusinessId();
        long projectId = req.getProjectId();

        logger.info("#traceId={}# [IN][commitProductBusinessInfo] param: productBusinessId={}", rpcHeader.getTraceId(), productBusinessId);
        try {
            FoundationProject project = projectDao.getByProjectId((long)projectId);
            String tablePrefix = project.getProjectTablePrefix();
            FoundationProductBusiness foundationProductBusiness = productBusinessDao.getProductBusinessById(tablePrefix,productBusinessId);
            FoundationProductBasic foundationProductBasic = productBasicDao.getProductBasicInfoById(foundationProductBusiness.getProductBasicId());
            Byte recordStatus = foundationProductBasic.getRecordStatus();
            if (FoundationNormalStatus.getStatusByCode(recordStatus) != FoundationNormalStatus.AUDITED){
                productBasicDao.updateRecordStatus(foundationProductBusiness.getProductBasicId(),FoundationNormalStatus.COMMITTED.getStatus());
            }
            int i = productBusinessDao.updateRecordStatus(tablePrefix,
                    foundationProductBusiness.getProductBusinessId(),
                    FoundationNormalStatus.COMMITTED.getStatus());
            GongxiaoRpc.RpcResult rpcResult = GongxiaoRpc.RpcResult.newBuilder()
                    .setReturnCode(ErrorCode.SUCCESS.getErrorCode())
                    .setMsg(ErrorCode.SUCCESS.getMessage())
                    .build();
            respBuilder.setRpcResult(rpcResult);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][commitProductBusinessInfo] success", rpcHeader.getTraceId());
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    //查询某项目下 所有货品信息（未分页）
    public void selectAllProduct(ProductStructure.SelectAllProductReq req,
                                 StreamObserver<ProductStructure.SelectAllProductResp> responseObserver) {
        ProductStructure.SelectAllProductResp.Builder respBuilder = ProductStructure.SelectAllProductResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long projectId = req.getProjectId();
        try {
            logger.info("#traceId={}# [OUT][selectAllProduct] param: projectId={}", rpcHeader.getTraceId(),projectId);
            FoundationProject project = projectDao.getByProjectId((long)projectId);
            List<FoundationProductBusiness> foundationProductBusinesses = productBusinessDao.selectProductBusienssByProject(project.getProjectTablePrefix());
            if (foundationProductBusinesses.size()==0){
                logger.warn("projectId = {} 的项目下没有分配货品",projectId);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            for (FoundationProductBusiness productBusiness : foundationProductBusinesses){
                ProductStructure.ProductBusiness productBusiness1 = ProductStructure.ProductBusiness.newBuilder()
                        .setProductBasicId(productBusiness.getProductBasicId())
                        .setProductBusinessId(productBusiness.getProductBusinessId())
                        .setGrossProfitValue(GrpcCommonUtil.getProtoParam(productBusiness.getGrossProfitValue()))
                        .setProjectId(productBusiness.getProjectId())
                        .setProjectName(productBusiness.getProjectName())
                        .setRecordStatus(productBusiness.getRecordStatus())
                        .setEasSynStatus(productBusiness.getEasSynStatus())
                        .setProductModel(productBusiness.getProductCode())
                        .setProductName(productBusiness.getProductName())
                        .setEasCode(productBusiness.getEasCode())
                        .setWmsCode(productBusiness.getWmsCode())
                        .setEasUnitCode(productBusiness.getEasUnitCode() != null ? productBusiness.getEasUnitCode() : "")
                        .setCustomerSKUCode(productBusiness.getCustomerSKUCode() != null ? productBusiness.getCustomerSKUCode() : "")
                        .setCustomerProductCode(productBusiness.getCustomerProductCode() != null ? productBusiness.getCustomerProductCode() : "")
                        .setPurchaseGuidePrice(productBusiness.getPurchaseGuidePrice())
                        .setTaxRate(productBusiness.getTaxRate())
                        .setTaxCode(productBusiness.getTaxCode())
                        .setSaleGuidePrice(productBusiness.getSaleGuidePrice())
                        .setActualSaleReturn(productBusiness.getActualSaleReturn())
                        .setShouldSaleReturn(productBusiness.getShouldSaleReturn())
                        .setFactorySendReturn(productBusiness.getFactorySendReturn())
                        .setCostPrice(productBusiness.getCostPrice())
                        .setOutPrice(productBusiness.getOutPrice())
                        .setGenerateCoupon(productBusiness.getGenerateCoupon())
                        .setCreateTime(DateUtil.getTime(productBusiness.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(productBusiness.getLastUpdateTime()))
                        .setTraceLog(productBusiness.getTraceLog() != null ? productBusiness.getTraceLog() : "").build();
                respBuilder.addProductBusiness(productBusiness1);
            }
            logger.info("#traceId={}# [OUT][selectAllProduct] success", rpcHeader.getTraceId());

            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            return;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

}
