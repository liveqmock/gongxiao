package com.yhglobal.gongxiao.foundation.distributor.dao;

import com.yhglobal.gongxiao.foundation.distributor.dao.mapping.DistributorBasicMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class DistributorBasicDao {

    @Autowired
    DistributorBasicMapper distributorBasicMapper;

    /**
     * 插入经销商基础信息
     * @param distributorBasic
     * @return
     */
    public int insertDistributorBasic(DistributorBasic distributorBasic){
        return distributorBasicMapper.insert(distributorBasic);
    }

    /**
     * 通过id获取经销商基础信息
     * @param id id
     * @return
     */
    public DistributorBasic getDistributroBasicInfoById(long id){
        return distributorBasicMapper.getByDistributorBasicById(id);
    }

    /**
     * 更新经销商基础信息
     * @param distributorBasic
     * @return
     */
    public int updateDistributorInfo(DistributorBasic distributorBasic){
        return distributorBasicMapper.updateDistributorInfo(distributorBasic);
    }



}
