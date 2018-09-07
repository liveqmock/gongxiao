package com.yhglobal.gongxiao.foudation.distributor.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.distributor.dao.DistributorDao;
import com.yhglobal.gongxiao.foundation.distributor.model.Distributor;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: 葛灿
 */
@Service
public class DistributorServiceImpl implements DistributorService {

    private static final Logger logger = LoggerFactory.getLogger(DistributorServiceImpl.class);

    @Autowired
    private DistributorDao distributorDao;

    @Override
    public Distributor getDistributorByDistributorId(RpcHeader rpcHeader, String distributorId) {
        logger.info("#traceId={}# [IN][getDistributorByDistributorId] params: distributorId={}", rpcHeader.traceId, distributorId);

        Distributor distributor = distributorDao.selectRecordById(distributorId);
        logger.info("#traceId={}# [OUT] get distributor success: ", rpcHeader.traceId);
        return distributor;
    }
}
