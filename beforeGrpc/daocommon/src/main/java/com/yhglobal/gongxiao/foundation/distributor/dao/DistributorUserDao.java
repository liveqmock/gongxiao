package com.yhglobal.gongxiao.foundation.distributor.dao;

import com.yhglobal.gongxiao.foundation.distributor.dao.mapping.DistributorUserMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class DistributorUserDao {

    @Autowired
    DistributorUserMapper distributorUserMapper;

    /**
     * 插入经销商用户信息
     * @param distributorUser
     * @return
     */
    public int insertUserInfo(DistributorUser distributorUser){
        return distributorUserMapper.insert(distributorUser);
    }

    /**
     * 通过经销商用户id获取经销商信息
     * @param id
     * @return
     */
    public DistributorUser getDistributorUserById(long id){
        return distributorUserMapper.getDistributorById(id);
    }

    /**
     * 更新密码
     * @param id
     * @param password
     * @return
     */
    public int updatePassword(long id,String password){
        return distributorUserMapper.updatePassword(id,password);
    }

    /**
     * 更新账号和密码
     * @param id
     * @param account
     * @param password
     * @return
     */
    public int updateAccountPassword(long id,String account,String password){
        return distributorUserMapper.updateAccountAndPassword(id,account,password);
    }

}
