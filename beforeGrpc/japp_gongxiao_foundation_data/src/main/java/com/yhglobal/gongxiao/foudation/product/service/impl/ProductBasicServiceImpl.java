package com.yhglobal.gongxiao.foudation.product.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.distributor.dao.DistributorPortalUserDao;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.product.dto.ProductDto;
import com.yhglobal.gongxiao.foundation.product.price.model.ProductPrice;
import com.yhglobal.gongxiao.foundation.product.productBasic.dao.ProductBasicDao;
import com.yhglobal.gongxiao.foundation.product.productprice.dao.ProductPriceDao;
import com.yhglobal.gongxiao.model.PurchaseOrder;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.service.PlanSaleItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service(timeout = 5000)
public class ProductBasicServiceImpl implements ProductService {
    private static Logger logger = LoggerFactory.getLogger(ProductBasicServiceImpl.class);

    @Autowired
    private ProductBasicDao productBasicDao;

    @Autowired
    private ProductPriceDao productPriceDao;

    @Override
    public ProductBasic getByProductCode(RpcHeader rpcHeader, String productCode) {
        logger.info("#traceId={}# [IN][getByProductCode] params: #{productCode}  ", rpcHeader.traceId, productCode);
        try {
            ProductBasic productBasic = productBasicDao.getByProductCode(productCode);
            if (productBasic==null) {
                logger.info("#traceId={}# [OUT] fail to getProductById: distributorPortalUserId=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] getByProductCode success: productBasic={} ", rpcHeader.traceId, productBasic.toString());
            }
            return productBasic;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ProductBasic getByProductModel(RpcHeader rpcHeader, String productModel) {
        logger.info("#traceId={}# [IN][getByProductModel] params: #{productModel}  ", rpcHeader.traceId, productModel);
        try {
            ProductBasic productBasic = productBasicDao.getByProductModel(productModel);
            if (productBasic==null) {
                logger.info("#traceId={}# [OUT] fail to getByProductModel: distributorPortalUserId=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] getByProductModel success: productBasic={} ", rpcHeader.traceId, productBasic.toString());
            }
            return productBasic;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ProductBasic getByProductId(RpcHeader rpcHeader, String productId) {
        logger.info("#traceId={}# [IN][getByProductId] params: #{productId}  ", rpcHeader.traceId, productId);
        try {
            ProductBasic productBasic = productBasicDao.getProductById(productId);
            if (productBasic==null) {
                logger.info("#traceId={}# [OUT] fail to getProductById: distributorPortalUserId=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] getProductById success: productBasic={} ", rpcHeader.traceId, productBasic.toString());
            }
            return productBasic;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<ProductBasic> selectProductByProjectId(RpcHeader rpcHeader,
                                                           Long projectId,
                                                           String productCode,
                                                           String productName,
                                                           int pageNumber,
                                                           int pageSize) {
        logger.info("#traceId={}# [IN][selectProductByProjectId] params: #{projectId}  ", rpcHeader.traceId, projectId);
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<ProductBasic> productBasics = productBasicDao.selectProductListByCondition(projectId,productCode,productName);
            PageInfo<ProductBasic> purchaseOrderPageInfo = new PageInfo<>(productBasics);

            if (productBasics.size()==0) {
                logger.info("#traceId={}# [OUT] fail to selectProductByProjectId: brands=null ", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] selectProductByProjectId success: productBasics={} ", rpcHeader.traceId, productBasics.size());
            }
            return purchaseOrderPageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<ProductBasic> selectAll(RpcHeader rpcHeader, int pageNo, int pageSize) {
        logger.info("#traceId={}# [IN][selectAll] params: #{pageNo} #{pageSize} ", rpcHeader.traceId, pageNo, pageSize);
        try {
            List<ProductBasic> productBasics = productBasicDao.selectAll();
            if (productBasics.size()==0) {
                logger.info("#traceId={}# [OUT] fail to selectAll: brands=null ", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] selectAll success: productBasics={} ", rpcHeader.traceId, productBasics.size());
            }
            return productBasics;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

//    @Override
//    public int selectCount(RpcHeader rpcHeader,
//                           String productCode,
//                           String productName,
//                           String barCode,
//                           Long productCategory,
//                           Long brand,
//                           Long productId,
//                           Long sellerProductCategory) {
//
//        logger.info("[IN] [selectCount] #traceId={} #{productCode} #{productName} #{barCode} #{productCategory} #{brand} #{productId}",
//                rpcHeader.traceId, productCode, productName, barCode, productCategory, brand, productId);
//        try {
//            int i = productBasicDao.selectCount(productCode, productName,
//                    barCode, productCategory, brand, productId, sellerProductCategory);
//            logger.info("[OUT] #traceId={}# : productBasics={} ", rpcHeader.traceId, i);
//            return i;
//        } catch (Exception e) {
//            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
//            throw e;
//        }
//    }

    @Override
    public List<ProductBasic> selectWithCondition(RpcHeader rpcHeader,
                                                  int pageNumber,
                                                  int pageSize,
                                                  String productCode,
                                                  String productName,
                                                  String barCode,
                                                  Long productCategory,
                                                  Long brand,
                                                  Long productId,
                                                  Long sellerProductCategory) {
        logger.info("#traceId={}# [IN][selectWithCondition] params: pageNumber={}, pageSize={}, productCode={}, productName={}" +
                ", barCode={}, productCategory={}, brand={}, productId={}, sellerProductCategory={}",
                rpcHeader.traceId,pageNumber,pageSize,productCode,productName,barCode,productCategory,brand,productId,sellerProductCategory);
        logger.info(rpcHeader.traceId);
        if (pageNumber < 1 || pageSize <= 0) {
            logger.warn("传入的页码/每页显示数量参数有误！");
            return null;
        }
        try {
            List<ProductBasic> productBasics =
                    productBasicDao.selectWithCondition(pageNumber, pageSize, productCode, productName, barCode, productCategory, brand, productId, sellerProductCategory);
            if (productBasics == null) {
                logger.info("#traceId={}# [OUT] fail to selectWithCondition: productBasics=null ", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] selectWithCondition success: productBasics={} ", rpcHeader.traceId, productBasics.size());
            }
            return productBasics;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<ProductBasic> search(RpcHeader rpcHeader, String queryStr) {
        logger.info("#traceId={}# [IN][search] params: queryStr={} ",
                rpcHeader.traceId,queryStr);
        List<ProductBasic> resultList;
        try{
            resultList = productBasicDao.getProductList(queryStr);
        }catch(Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw new RuntimeException("Search ProductBasic error",e);
        }
        logger.info("#traceId={}# [OUT] search success: resultList.size={} ", rpcHeader.traceId, resultList.size());
        return resultList;
    }

    @Override
    public ProductBasic getByWmsProductCode(RpcHeader rpcHeader, String wmsProductCode) {
        logger.info("#traceId={}# [IN][getByWmsProductCode] params: #wmsProductCode={}  ", rpcHeader.traceId, wmsProductCode);
        try {
            ProductBasic productBasic = productBasicDao.getByWmsProductCode(wmsProductCode);
            if (productBasic==null) {
                logger.info("#traceId={}# [OUT] fail to getByWmsProductCode: distributorPortalUserId=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] getByWmsProductCode success: productBasic={} ", rpcHeader.traceId, productBasic.toString());
            }
            return productBasic;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ProductBasic getByEasModel(String easModel) {
        logger.info("#traceId={}# [IN][getByEasProductCode] params: #wmsProductCode={}  ", easModel);
        try {
            ProductBasic productBasic = productBasicDao.getByEasModel(easModel);
            if (productBasic==null) {
                logger.info("[OUT] fail to getByEasProductCode: distributorPortalUserId=NULL");
            } else {
                logger.info("[OUT] getByEasProductCode success: productBasic={} ", productBasic.toString());
            }
            return productBasic;
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


}
