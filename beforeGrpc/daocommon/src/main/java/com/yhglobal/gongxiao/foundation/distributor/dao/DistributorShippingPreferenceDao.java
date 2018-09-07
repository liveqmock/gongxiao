package com.yhglobal.gongxiao.foundation.distributor.dao;

import com.yhglobal.gongxiao.foundation.distributor.model.DistributorShippingPreference;
import com.yhglobal.gongxiao.foundation.distributor.dao.mapping.DistributorShippingPreferenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 经销商地址偏好表
 *
 * @author: 陈浩
 **/
@Repository
public class DistributorShippingPreferenceDao {
    @Autowired
    DistributorShippingPreferenceMapper distributorShippingPreferenceMapper;

    public int insert(DistributorShippingPreference record){
        return distributorShippingPreferenceMapper.insert(record);
    }

    /**
     * 通过经销商ID获取经销商信息
     * @param distributorId 经销商ID
     * @return
     */
    public DistributorShippingPreference getPreferenceAddressById(String distributorId){
        return distributorShippingPreferenceMapper.getPreferenceAddressById(distributorId);
    }

    /**
     * 更新
     * @param record
     * @return
     */
    public int updateByPrimaryKey(DistributorShippingPreference record){
        return distributorShippingPreferenceMapper.updateByPrimaryKey(record);
    }
}
