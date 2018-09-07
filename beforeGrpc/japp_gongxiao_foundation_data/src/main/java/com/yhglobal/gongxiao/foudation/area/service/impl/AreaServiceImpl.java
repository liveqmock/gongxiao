package com.yhglobal.gongxiao.foudation.area.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhglobal.gongxiao.area.City;
import com.yhglobal.gongxiao.area.District;
import com.yhglobal.gongxiao.area.Province;
import com.yhglobal.gongxiao.area.service.AreaService;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.area.AreaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 省市区服务类
 *
 * @author: 陈浩
 **/
@Service
public class AreaServiceImpl implements AreaService {

    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    AreaDao areaDao;

    @Override
    public List<Province> selectProvinceList(RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][selectProvinceList] params: ", rpcHeader.traceId);
        try {
            List<Province> provinces = areaDao.selectProvinceList();
            if (provinces.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectProvinceList:  provinces=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] selectProvinceList success: provinces.size={}", rpcHeader.traceId, provinces.size());
            }
            return provinces;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<City> selectCityByProvince(RpcHeader rpcHeader, String provinceCode) {
        logger.info("#traceId={}# [IN][selectCityByProvince] params: provinceCode={}", rpcHeader.traceId, provinceCode);
        try {
            List<City> cities = areaDao.selectCitysByProvince(provinceCode);
            if (cities.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectCityByProvince:  provinces=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] selectCityByProvince success: cities.size={}", rpcHeader.traceId, cities.size());
            }
            return cities;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<City> selectAllCity(RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][selectAllCity] params: ", rpcHeader.traceId);
        try {
            List<City> cities = areaDao.selectAllCity();
            if (cities.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectAllCity:  provinces=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] selectAllCity success: cities.size={}", rpcHeader.traceId, cities.size());
            }
            return cities;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<District> selectDistrictsByCity(RpcHeader rpcHeader, String cityCode) {
        logger.info("#traceId={}# [IN][selectDistrictsByCity] params: provinceCode={}", rpcHeader.traceId, cityCode);
        try {
            List<District> districts = areaDao.selectDstrictsByCity(cityCode);
            if (districts.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectDistrictsByCity:  provinces=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] selectDistrictsByCity success: districts.size={}", rpcHeader.traceId, districts.size());
            }
            return districts;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<District> selectAllDistrict(RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][selectDistrictsByCity] params:", rpcHeader.traceId);
        try {
            List<District> districts = areaDao.selectAllDistricts();
            if (districts.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectDistrictsByCity:  provinces=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] selectDistrictsByCity success: districts.size={}", rpcHeader.traceId, districts.size());
            }
            return districts;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
