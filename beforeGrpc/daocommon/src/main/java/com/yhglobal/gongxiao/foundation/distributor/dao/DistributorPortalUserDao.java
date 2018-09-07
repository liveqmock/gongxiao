package com.yhglobal.gongxiao.foundation.distributor.dao;

import com.yhglobal.gongxiao.foundation.distributor.dao.mapping.DistributorPortalUserMapper;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DistributorPortalUserDao {

    @Autowired
    private DistributorPortalUserMapper distributorPortalUserMapper;

    public DistributorPortalUser getByDistributorId(String distributorId){
        return distributorPortalUserMapper.getByDistributorId(distributorId);
    }

    public int createDistributorPortalUser(DistributorPortalUser record) {
        return distributorPortalUserMapper.createDistributorPortalUser(record);
    }

    public DistributorPortalUser getDistributorPortalUserById(String distributorPortalUserId) {
        return distributorPortalUserMapper.getDistributorPortalUserById(distributorPortalUserId);
    }

    public DistributorPortalUser getDistributorPortalUserByName(String distributorPortalUserName) {
        return distributorPortalUserMapper.getDistributorPortalUserByName(distributorPortalUserName);
    }

    public List<DistributorPortalUser> selectDistributorPortalUserByProjectIdAndDistributorId(long projectId, long distributorId) {
        return distributorPortalUserMapper.selectDistributorPortalUserByProjectIdAndDistributorId(projectId, distributorId);
    }

    public List<DistributorPortalUser> selectAll(){
        return distributorPortalUserMapper.selectAll();
    }


    public  List<DistributorPortalUser> selectAllByProjectIdAndUserIdAndUserName( Long projectId,
                                                                                  String distributorId,
                                                                                 String distributorName){
        return distributorPortalUserMapper.selectAllByProjectIdAndUserIdAndUserName(projectId,distributorId,distributorName);
    }
}
