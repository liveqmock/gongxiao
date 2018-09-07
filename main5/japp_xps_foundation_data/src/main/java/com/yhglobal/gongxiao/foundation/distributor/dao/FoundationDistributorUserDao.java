package com.yhglobal.gongxiao.foundation.distributor.dao;

import com.yhglobal.gongxiao.foundation.distributor.dao.mapping.FoundationDistributorUserMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.FoundationDistributorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoundationDistributorUserDao {

    @Autowired
    private FoundationDistributorUserMapper distributorPortalUserMapper;

    /**
     * 新增经销商用户信息
     *
     * @param record
     * @return
     */
    public int insertDistributorUser(FoundationDistributorUser record) {
        return distributorPortalUserMapper.insert(record);
    }

    /**
     * 通过用户ID或去用户明细
     *
     * @param distributorUserId
     * @return
     */
    public FoundationDistributorUser getByDistributorUserId(long distributorUserId) {
        return distributorPortalUserMapper.getUserByUserId(distributorUserId);
    }

    /**
     * 通过经销商名称获取经销商用户详情
     *
     * @param distributorPortalUserName
     * @return
     */
    public FoundationDistributorUser getUserByName(String distributorPortalUserName) {
        return distributorPortalUserMapper.getUserByName(distributorPortalUserName);
    }

    /**
     * 通过账号查询用户
     * @param account
     * @return
     */
    public FoundationDistributorUser getUserByAccount(long projectId,String account){
        return distributorPortalUserMapper.getUserByAccount(projectId, account);
    }
    /**
     * 通过经销商业务ID获取该经销商在该项目下所有的子账号
     *
     * @param distributorBusinessId
     * @return
     */
    public List<FoundationDistributorUser> selectUserByBusinessId(long distributorBusinessId) {
        return distributorPortalUserMapper.selectUserByBusinessId(distributorBusinessId);
    }

    /**
     * 获取所有的经销商用户列表
     *
     * @return
     */
    public List<FoundationDistributorUser> selectAllUser() {
        return distributorPortalUserMapper.selectAllUser();
    }


    /**
     * 
     * @param projectId
     * @param account
     * @param distributorName
     * @return
     */
    public List<FoundationDistributorUser> selectUserByCondition(long projectId,
                                                                 String account,
                                                                 String distributorName){
        return distributorPortalUserMapper.selectUserByCondition(projectId,account,distributorName);
    }

    /**
     * 通过账号获取账号列表
     * @param accont
     * @return
     */
    public List<FoundationDistributorUser> selectUserListByAccount(String accont){
        return distributorPortalUserMapper.selectUserListByAccount(accont);
    }

}
