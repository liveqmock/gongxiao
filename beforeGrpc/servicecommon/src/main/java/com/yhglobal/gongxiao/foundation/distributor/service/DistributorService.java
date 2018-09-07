package com.yhglobal.gongxiao.foundation.distributor.service;


import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.distributor.model.Distributor;

public interface DistributorService {
    /**
     *
     * @param rpcHeader
     * @param distributorId
     * @return
     */
    Distributor getDistributorByDistributorId(RpcHeader rpcHeader , String distributorId);
}
