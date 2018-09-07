package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorCouponAccountMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCouponAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class DistributorCouponAccountDao {

    @Autowired
    DistributorCouponAccountMapper distributorCouponAccountMapper;

    /**
     * 插入新账户
     *
     * @param record 账户
     * @return
     */
    public int insert(String prefix,DistributorCouponAccount record) {
        return distributorCouponAccountMapper.insert( prefix,record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(String prefix,DistributorCouponAccount record) {
        return distributorCouponAccountMapper.updateByPrimaryKey( prefix,record);
    }

    /**
     * 查询账户
     *
     * @param projectId     项目id
     * @param distributorId 经销商id
     * @return
     */
    public DistributorCouponAccount getAccount(String prefix,long projectId, long distributorId) {
        return distributorCouponAccountMapper.getAccount( prefix,projectId, distributorId);
    }

    /**
     * 查询所有的ad账户
     *
     * @param projectId 项目id
     * @return
     */
    public List<DistributorCouponAccount> getAccountList(String prefix,long projectId) {
        return distributorCouponAccountMapper.getAccountList( prefix,projectId);
    }
}
