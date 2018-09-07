package com.yhglobal.gongxiao.foudation.distributor.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.distributor.dao.DistributorCouponSettingDao;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorCouponSetting;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorCouponSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: ad返利代垫
 *
 * @Date: Created in 11:20 2018/4/20
 */
@Service
public class DistributorCouponSettingServiceImpl implements DistributorCouponSettingService {
    private static final Logger logger = LoggerFactory.getLogger(DistributorAddressServiceImpl.class);
    @Autowired
    private DistributorCouponSettingDao distributorCouponSettingDao;
    @Override
    public DistributorCouponSetting getDistributorCoupon(RpcHeader rpcHeader, String projectId, String distributorId) {
        logger.info("#traceId={}# [IN][getDistributorCoupon] params: distributorId={}, projectId={} ", rpcHeader,distributorId,projectId);
        DistributorCouponSetting distributorCouponSetting = null;
        try{
            distributorCouponSetting = distributorCouponSettingDao.getDistributorCoupon(projectId,distributorId);
        }catch(Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw new RuntimeException("getDistributorCoupon error!",e);
        }
        logger.info("#traceId={}# [OUT] getDistributorCoupon success distributorCouponSetting={}", rpcHeader.traceId,distributorCouponSetting);
        return distributorCouponSetting;
    }
}
