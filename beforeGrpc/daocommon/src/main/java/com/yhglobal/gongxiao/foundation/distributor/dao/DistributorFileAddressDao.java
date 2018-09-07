package com.yhglobal.gongxiao.foundation.distributor.dao;

import com.yhglobal.gongxiao.foundation.distributor.dao.mapping.DistributorFileAddressMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorFileAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DistributorFileAddressDao {
    @Autowired
    private DistributorFileAddressMapper distributorFileAddressMapper;

    public List<DistributorFileAddress> selectByIdAndName(Long distributorId, String distributorName){
        return distributorFileAddressMapper.selectByIdAndName(distributorId,distributorName);
    }

}
