package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorCashAccountMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCashAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class DistributorCashAccountDao {

    @Autowired
    DistributorCashAccountMapper distributorCashAccountMapper;

    /**
     * 插入新账户
     *
     * @param record 账户
     * @return
     */
    public int insert(String prefix,DistributorCashAccount record) {
        return distributorCashAccountMapper.insert( prefix,record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(String prefix,DistributorCashAccount record) {
        return distributorCashAccountMapper.updateByPrimaryKeyWithBLOBs( prefix,record);
    }

    /**
     * 查询账户
     *
     * @param projectId     项目id
     * @param distributorId 经销商id
     * @return
     */
    public DistributorCashAccount getAccount(String prefix,long projectId, long distributorId) {
        return distributorCashAccountMapper.getAccount( prefix,projectId, distributorId);
    }

}
