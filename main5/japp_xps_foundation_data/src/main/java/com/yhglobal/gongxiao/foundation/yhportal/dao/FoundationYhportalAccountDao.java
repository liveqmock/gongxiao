package com.yhglobal.gongxiao.foundation.yhportal.dao;

import com.yhglobal.gongxiao.foundation.yhportal.dao.mapping.FoundationYhportalAccountMapper;
import com.yhglobal.gongxiao.foundation.yhportal.model.FoundationYhportalAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class FoundationYhportalAccountDao {

    @Autowired
    FoundationYhportalAccountMapper foundationYhportalAccountMapper;

    /**
     * 插入新的yhport账号
     * @param foundationYhportalAccount
     * @return
     */
    public int insertYhportAccount(FoundationYhportalAccount foundationYhportalAccount){
        return foundationYhportalAccountMapper.insert(foundationYhportalAccount);
    }

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    public FoundationYhportalAccount getAccountByUserName(String userName){
        return foundationYhportalAccountMapper.getUserByName(userName);
    }


    /**
     *
     * @param name
     * @return
     */
    public List<FoundationYhportalAccount> selectAccountListByName(String name){
        return foundationYhportalAccountMapper.getUserListByAccount(name);
    }

    /**
     * 获取所有用户列表
     * @return
     */
    public List<FoundationYhportalAccount> selectAllUser(){
        return foundationYhportalAccountMapper.selectAllUser();
    }

}
