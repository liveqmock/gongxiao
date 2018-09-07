package com.yhglobal.gongxiao.foudation.product.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.price.model.ProductPrice;
import com.yhglobal.gongxiao.foundation.product.price.service.ProductPriceService;
import com.yhglobal.gongxiao.foundation.product.productprice.dao.ProductPriceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Service(timeout =2000)
public class ProductPriceServiceImpl implements ProductPriceService {
    private static Logger logger = LoggerFactory.getLogger(ProductPriceServiceImpl.class);

    @Autowired
    private ProductPriceDao productPriceDao;

    @Override
    public ProductPrice getByProductId(RpcHeader rpcHeader, Long productId) {
        logger.info("#traceId={}# [IN][getByProductId] params: productId={}", rpcHeader.traceId, productId);
        try {
            ProductPrice productPrice = productPriceDao.getByProductId(productId);
            if (productPrice == null) {
                logger.info("#traceId={}# [OUT] fail to getByProductId: productPrice=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] fail to getByProductId: productPrice={}", rpcHeader.traceId, productPrice.toString());
            }
            return productPrice;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(),  e);
            throw e;
        }
    }

    @Override
    public List<ProductPrice> selectAll(RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][selectAll] params: ", rpcHeader.traceId);
        try{
            List<ProductPrice> productPriceList = productPriceDao.selectAll();
            if (productPriceList==null) {
                logger.info("#traceId={}# [OUT] fail to selectAll: distributorPortalUserId=NULL", rpcHeader.traceId, productPriceList);
            } else {
                logger.info("#traceId={}# [OUT] selectAll success: distributorPortalUser={}", rpcHeader.traceId, productPriceList.size());
            }
            return productPriceList;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(),  e);
            throw e;
        }
    }

    @Override
    public List<ProductPrice> selectByBrandId(RpcHeader rpcHeader,String brandId) {
        logger.info("#traceId={}# [IN][getCurrencyByCode] params: brandId={}", rpcHeader.traceId,brandId);
        try {
            List<ProductPrice> productPriceList = productPriceDao.selectByBrandId(brandId);
            if (productPriceList==null) {
                logger.info("#traceId={}# [OUT] fail to selectByBrandId: distributorPortalUserId=NULL", rpcHeader.traceId, productPriceList);
            } else {
                logger.info("#traceId={}# [OUT] selectByBrandId success: distributorPortalUser={}", rpcHeader.traceId, productPriceList.size());
            }
            return productPriceList;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(),  e);
            throw e;
        }
    }

    @Override
    public List<ProductPrice> selectByBrandName(RpcHeader rpcHeader,String brandName) {
        logger.info("#traceId={}# [IN][selectByBrandName] params: brandName={}", rpcHeader.traceId,brandName);
        try{
            List<ProductPrice> productPriceList = productPriceDao.selectByBrandName(brandName);
            if (productPriceList==null) {
                logger.info("#traceId={}# [OUT] fail to selectByBrandName: distributorPortalUserId=NULL", rpcHeader.traceId, productPriceList);
            } else {
                logger.info("#traceId={}# [OUT] selectByBrandName success: distributorPortalUser={}", rpcHeader.traceId, productPriceList.size());
            }
            return productPriceList;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(),  e);
            throw e;
        }
    }

    public List<ProductPrice> selectByProjectId(RpcHeader rpcHeader,String projectId){
        logger.info("#traceId={}# [IN][getCurrencyByCode] params: projectId={}", rpcHeader.traceId,projectId);
      try {
          List<ProductPrice> productPriceList = productPriceDao.selectByProjectId(projectId);
          if (productPriceList==null) {
              logger.info("#traceId={}# [OUT] fail to selectByProjectId: distributorPortalUserId=NULL", rpcHeader.traceId, productPriceList);
          } else {
              logger.info("#traceId={}# [OUT] selectByProjectId success: distributorPortalUser={}", rpcHeader.traceId, productPriceList.size());
          }
          return productPriceList;
      }catch (Exception e){
          logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(),  e);
          throw e;
      }
    }

}
