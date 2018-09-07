package com.yhglobal.gongxiao.foudation.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.product.dao.ProductNewBasicDao;
import com.yhglobal.gongxiao.foundation.product.product.dao.ProductNewDao;
import com.yhglobal.gongxiao.foundation.product.product.model.Product;
import com.yhglobal.gongxiao.foundation.product.product.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.product.model.ProductDetail;
import com.yhglobal.gongxiao.foundation.product.product.service.ProductService;
import com.yhglobal.gongxiao.purchase.temp.OperateHistoryRecord;
import com.yhglobal.gongxiao.util.DoubleScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 新 货品管理服务类
 *
 * @author: 陈浩
 **/
@Service
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductNewDao productDao;

    @Autowired
    ProductNewBasicDao productBasicDao;

    @Override
    public int addProduct(RpcHeader rpcHeader, ProductDetail productDetail) {
        logger.info("#traceId={}# [IN][addProduct] params: productDetail={}  ", rpcHeader.traceId, productDetail.toString());
        try {
            Product product = new Product();
            ProductBasic productBasic = new ProductBasic();

            productBasic.setCategoryId(productDetail.getCategoryId());
            productBasic.setCategoryName(productDetail.getCategoryName());
            productBasic.setProductLine(productDetail.getProductLine());
            productBasic.setProductLength(productDetail.getProductLength());
            productBasic.setProductWidth(productDetail.getProductWidth());
            productBasic.setProductHeight(productDetail.getProductHeight());
            productBasic.setProductWeight(productDetail.getProductWeight());
            productBasic.setProductGrossWeight(productDetail.getProductGrossWeight());
            productBasic.setProductNetWeight(productDetail.getProductNetWeight());
            productBasic.setBoxWidth(productDetail.getBoxWidth());
            productBasic.setBoxLength(productDetail.getBoxLength());
            productBasic.setBoxHeight(productDetail.getBoxHeight());
            productBasic.setBoxWeight(productDetail.getBoxWeight());
            int i = productBasicDao.insertProductBasic(productBasic);
            product.setProductBasicId(productBasic.getId());
            product.setProjectId(productDetail.getProjectId());
            product.setRecordStatus((byte) 1);
            product.setProductName(productDetail.getProductName());
            product.setProductModel(productDetail.getProductModel());
            product.setEasCode(productDetail.getEasCode());
            product.setWmsCode(productDetail.getWmsCode());
            product.setCustomerSKUCode(productDetail.getCustomerSKUCode());
            product.setCustomerProductCode(productDetail.getCustomerProductCode());
            product.setPurchaseGuidePrice(DoubleScale.multipleMillion(productDetail.getPurchaseGuidePrice()));
            product.setTaxRate(DoubleScale.multipleMillion(productDetail.getTaxRate()));
            product.setTaxCode(productDetail.getTaxCode());
            product.setSaleGuidePrice(DoubleScale.multipleMillion(productDetail.getSaleGuidePrice()));
            product.setActualSaleReturn(DoubleScale.multipleMillion(productDetail.getActualSaleReturn()));
            product.setShouldSaleReturn(DoubleScale.multipleMillion(productDetail.getShouldSaleReturn()));
            product.setFactorySendReturn(DoubleScale.multipleMillion(productDetail.getFactorySendReturn()));
            product.setCostPrice(DoubleScale.multipleMillion(productDetail.getCostPrice()));
            product.setOutPrice(DoubleScale.multipleMillion(productDetail.getOutPrice()));
            product.setGenerateCoupon(productDetail.getGenerateCoupon());
            product.setCreateTime(new Date());
            product.setLastUpdateTime(new Date());

            List<OperateHistoryRecord> recordList = new ArrayList<>();
            OperateHistoryRecord operateHistoryRecord = new OperateHistoryRecord();
            operateHistoryRecord.setOperateFunction("新建");
            operateHistoryRecord.setOperateTime(new Date());
            operateHistoryRecord.setOperateId(rpcHeader.getUid());
            operateHistoryRecord.setOperateName(rpcHeader.getUsername());
            recordList.add(operateHistoryRecord);
            String traceJson = JSON.toJSONString(recordList);
            product.setTraceLog(traceJson);
            productDao.insertProduct(product);
            logger.info("#traceId={}# [OUT] addProduct success:  ", rpcHeader.traceId);
            return 1;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int submitProduct(RpcHeader rpcHeader, String projectId, long id) {
        logger.info("#traceId={}# [IN][auditProduct] params: projectId={} ,id={} ", rpcHeader.traceId, projectId, id);
        try {
            Product product = productDao.getProductById(id);
            product.setProjectId(projectId);
            product.setId((long) 0);
            product.setRecordStatus((byte) 1);

            List<OperateHistoryRecord> recordList = new ArrayList<>();
            OperateHistoryRecord operateHistoryRecord = new OperateHistoryRecord();
            operateHistoryRecord.setOperateFunction("新建");
            operateHistoryRecord.setOperateTime(new Date());
            operateHistoryRecord.setOperateId(rpcHeader.getUid());
            operateHistoryRecord.setOperateName(rpcHeader.getUsername());
            recordList.add(operateHistoryRecord);
            String traceJson = JSON.toJSONString(recordList);
            product.setTraceLog(traceJson);

            int i = productDao.insertProduct(product);
            logger.info("#traceId={}# [OUT] auditProduct success: i={} ", rpcHeader.traceId, i);

            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int rebutProduct(RpcHeader rpcHeader, long id) {
        logger.info("#traceId={}# [IN][rebutProduct] params: projectId={} ,id={} ", rpcHeader.traceId, id);
        try {
            Product product = productDao.getProductById(id);
            String traceLog = product.getTraceLog();
            List<OperateHistoryRecord> traceLogList = JSON.parseObject(product.getTraceLog(), new TypeReference<List<OperateHistoryRecord>>() {
            });
            OperateHistoryRecord operateHistoryRecord = new OperateHistoryRecord();
            operateHistoryRecord.setOperateName("驳回");
            operateHistoryRecord.setOperateTime(new Date());
            operateHistoryRecord.setOperateId(rpcHeader.getUid());
            operateHistoryRecord.setOperateName(rpcHeader.getUsername());
            traceLogList.add(operateHistoryRecord);
            String traceJson = JSON.toJSONString(traceLogList);

            int i = productDao.updateProductStatus(id, 9,traceJson);
            logger.info("#traceId={}# [OUT] rebutProduct success: i={} ", rpcHeader.traceId, id);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int auditProduct(RpcHeader rpcHeader, long id) {
        logger.info("#traceId={}# [IN][auditProduct] params: projectId={} ,id={} ", rpcHeader.traceId, id);
        try {
            Product product = productDao.getProductById(id);
            String traceLog = product.getTraceLog();
            List<OperateHistoryRecord> traceLogList = JSON.parseObject(product.getTraceLog(), new TypeReference<List<OperateHistoryRecord>>() {
            });
            OperateHistoryRecord operateHistoryRecord = new OperateHistoryRecord();
            operateHistoryRecord.setOperateName("审核");
            operateHistoryRecord.setOperateTime(new Date());
            operateHistoryRecord.setOperateId(rpcHeader.getUid());
            operateHistoryRecord.setOperateName(rpcHeader.getUsername());
            traceLogList.add(operateHistoryRecord);
            String traceJson = JSON.toJSONString(traceLogList);

            int i = productDao.updateProductStatus(id, 2,traceJson);
            logger.info("#traceId={}# [OUT] auditProduct success: i={} ", rpcHeader.traceId, id);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Product> selectProductList(RpcHeader rpcHeader, String projectId, int auditStatus) {
        logger.info("#traceId={}# [IN][selectProductList] params: projectId={} ,projectId={} ,auditStatus={} ", rpcHeader.traceId, projectId, auditStatus);
        try {
            // 前端: 1 未提交,2 已提交,3 已审核 4 已驳回  数据库:1 已提交,2 已审核,9 已驳回
            //前端数据库映射关系
            Map<Integer, Integer> recordMap = new HashMap();
            recordMap.put(2, 1);
            recordMap.put(3, 2);
            recordMap.put(4, 9);
            List<Product> products = null;
            if (auditStatus != 1) {
                int recordStatus = recordMap.get(auditStatus);
                products = productDao.selectByAudit(projectId, recordStatus);
                logger.info("#traceId={}# [OUT] selectProductList success: i={} ", rpcHeader.traceId, products.size());
                return products;
            } else {
                products = productDao.selectNotCommitList(projectId);
            }
            logger.info("#traceId={}# [OUT] selectProductList success: i={} ", rpcHeader.traceId, products.size());
            return products;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ProductDetail getProductDetail(RpcHeader rpcHeader, long id) {
        logger.info("#traceId={}# [IN][getProductDetail] params: id={} ", rpcHeader.traceId, id);
        try {
            Product product = productDao.getProductById(id);
            Long productBasicId = product.getProductBasicId();
            ProductBasic productBasic = productBasicDao.getById(productBasicId);
            ProductDetail productDetail = new ProductDetail();

            productDetail.setId(product.getId());
            productDetail.setProductBasicId(product.getProductBasicId());
            productDetail.setRecordStatus(product.getRecordStatus());
            productDetail.setProductName(product.getProductName());
            productDetail.setProductModel(product.getProductModel());
            productDetail.setEasCode(product.getEasCode());
            productDetail.setWmsCode(product.getWmsCode());
            productDetail.setCustomerSKUCode(product.getCustomerSKUCode());
            productDetail.setCustomerProductCode(product.getCustomerProductCode());
            productDetail.setPurchaseGuidePrice(DoubleScale.keepSixBits(product.getPurchaseGuidePrice()));
            productDetail.setTaxRate(DoubleScale.keepSixBits(product.getTaxRate()));
            productDetail.setTaxCode(product.getTaxCode());
            productDetail.setSaleGuidePrice(DoubleScale.keepSixBits(product.getSaleGuidePrice()));
            productDetail.setActualSaleReturn(DoubleScale.keepSixBits(product.getActualSaleReturn()));
            productDetail.setShouldSaleReturn(DoubleScale.keepSixBits(product.getShouldSaleReturn()));
            productDetail.setFactorySendReturn(DoubleScale.keepSixBits(product.getFactorySendReturn()));
            productDetail.setCostPrice(DoubleScale.keepSixBits(product.getCostPrice()));
            productDetail.setOutPrice(DoubleScale.keepSixBits(product.getOutPrice()));
            productDetail.setGenerateCoupon(product.getGenerateCoupon());
            productDetail.setCreateTime(product.getCreateTime());
            productDetail.setLastUpdateTime(product.getLastUpdateTime());
            logger.info("#traceId={}# [OUT] getProductDetail success: productDetail={} ", rpcHeader.traceId, productDetail.toString());
            return productDetail;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Product> selectNeedAuditProductList(RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][selectNeedAuditProductList] params: ", rpcHeader.traceId);
        try {
            List<Product> products = productDao.selectNeedAuditList();
            return products;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
