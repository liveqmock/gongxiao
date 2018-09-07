package com.yhglobal.gongxiao.foundation.distributor.dao;

import com.yhglobal.gongxiao.foundation.distributor.dao.mapping.DistributorProjectMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class DistributorProjectDao {

    @Autowired
    DistributorProjectMapper distributorProjectMapper;

    /**
     * 插入新的经销商项目关系
     * @param distributorProject
     * @return
     */
    public int insertDistributorProject(DistributorProject distributorProject){
        return distributorProjectMapper.insert(distributorProject);
    }

    /**
     * 通过ID获取经销商跟项目关系
     * @param id
     * @return
     */
    public DistributorProject getDistributorProjectById(long id){
        return distributorProjectMapper.getDistributorProjectById(id);
    }

    /**
     * 更新经销商项目关系
     * @param distributorProject
     * @return
     */
    public int updateDistributorProject(DistributorProject distributorProject){
        return distributorProjectMapper.updateDistributor(distributorProject);
    }

}
