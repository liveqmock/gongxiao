package com.yhglobal.gongxiao.foudation.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foudation.distributor.service.impl.DistributorPortalUserServiceImpl;
import com.yhglobal.gongxiao.foundation.product.brand.dao.BrandDao;
import com.yhglobal.gongxiao.foundation.product.brand.model.Brand;
import com.yhglobal.gongxiao.foundation.product.brand.service.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceName = "com.yhglobal.gongxiao.foundation.product.brand.service.BrandService", timeout = 500)
public class BrandServiceImpl implements BrandService {

    private static Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Autowired
    private BrandDao brandDao;

    @Override
    public Brand getBrandById(RpcHeader rpcHeader, String brandId) {
        logger.info("#traceId={}# [IN][getBrandById] params: brandId={}  ", rpcHeader.traceId, brandId);
        try {
            Brand brand = brandDao.getByBrandId(brandId);
            if (brand==null) {
                logger.info("#traceId={}# [OUT] fail to getBrandById: brand=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] getBrandById success: brand={} ", rpcHeader.traceId, brand);
            }
            return brand;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Brand getBrandByName(RpcHeader rpcHeader, String brandName) {
        logger.info("#traceId={}# [IN][getBrandById] params: brandName={}", rpcHeader.traceId, brandName);
        try {
            Brand brand = brandDao.getByBrandName(brandName);
            if (brand==null) {
                logger.info("#traceId={}# [OUT] fail to getBrandById: brand=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] getBrandByName success: brand={} ", rpcHeader.traceId, brand);
            }
            return brand;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public List<Brand> selectAll(RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][getBrandById] params: ", rpcHeader.traceId);
        try {
            List<Brand> brands = brandDao.selectAll();
            if (brands.size()==0) {
                logger.info("#traceId={}# [OUT] fail to selectAll: brands=null ", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] selectAll success: brands={} ", rpcHeader.traceId, brands.size());
            }
            return brands;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
