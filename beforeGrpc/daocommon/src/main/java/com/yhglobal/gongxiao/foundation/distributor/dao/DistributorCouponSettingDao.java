package com.yhglobal.gongxiao.foundation.distributor.dao;

import com.yhglobal.gongxiao.foundation.distributor.dao.mapping.DistributorCouponSettingMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorCouponSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 经销商返利
 *
 * @author: 陈浩
 **/
@Repository
public class DistributorCouponSettingDao {

    @Autowired
    DistributorCouponSettingMapper distributorCouponSettingMapper;

    /**
     * 插入经销商返利
     * @param distributorCouponSetting
     * @return
     */
    public int insertDistributor(DistributorCouponSetting distributorCouponSetting){
        return distributorCouponSettingMapper.insert(distributorCouponSetting);
    }

    /**
     * 获取某项目下某经销商的返利代垫使用比例
     * @param projectId 项目ID
     * @param distributorId 经销商ID
     * @return
     */
    public DistributorCouponSetting getDistributorCoupon(String projectId,String distributorId){
        return distributorCouponSettingMapper.getDistributorCoupon(projectId,distributorId);
    }
}
