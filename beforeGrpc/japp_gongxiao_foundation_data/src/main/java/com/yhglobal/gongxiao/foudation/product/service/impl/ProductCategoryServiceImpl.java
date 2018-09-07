package com.yhglobal.gongxiao.foudation.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.category.dao.ProductCategoryDao;
import com.yhglobal.gongxiao.foundation.product.category.model.ProductCategory;
import com.yhglobal.gongxiao.foundation.product.category.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private static Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> selectAll(RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][getCurrencyByCode] params: ", rpcHeader.traceId);
        try {
            List<ProductCategory> productCategories = productCategoryDao.selectAll();
            if (productCategories.size()==0) {
                logger.info("#traceId={}# [OUT] fail to selectAll:productCategories=NULL", rpcHeader.traceId, productCategories);
            } else {
                logger.info("#traceId={}# [OUT] selectAll success: productCategories={}", rpcHeader.traceId, productCategories.size());
            }
            return productCategories;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(),  e);
            throw e;
        }
    }

}
