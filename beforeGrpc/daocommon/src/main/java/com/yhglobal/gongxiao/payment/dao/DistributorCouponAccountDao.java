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
    public int insert(DistributorCouponAccount record) {
        return distributorCouponAccountMapper.insert(record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(DistributorCouponAccount record) {
        return distributorCouponAccountMapper.updateByPrimaryKey(record);
    }

    /**
     * 查询账户
     *
     * @param projectId     项目id
     * @param distributorId 经销商id
     * @return
     */
    public DistributorCouponAccount getAccount(long projectId, long distributorId) {
        return distributorCouponAccountMapper.getAccount(projectId, distributorId);
    }

    /**
     * 查询所有的ad账户
     *
     * @param projectId 项目id
     * @return
     */
    public List<DistributorCouponAccount> getAccountList(long projectId) {
        return distributorCouponAccountMapper.getAccountList(projectId);
    }
}
