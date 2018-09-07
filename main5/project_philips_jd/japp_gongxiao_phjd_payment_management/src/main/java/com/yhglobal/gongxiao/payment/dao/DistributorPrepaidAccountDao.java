package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorPrepaidAccountMapper;
import com.yhglobal.gongxiao.payment.model.DistributorPrepaidAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class DistributorPrepaidAccountDao {
    @Autowired
    DistributorPrepaidAccountMapper distributorPrepaidAccountMapper;

    /**
     * 插入新账户
     *
     * @param record 账户
     * @return
     */
    public int insert(DistributorPrepaidAccount record) {
        return distributorPrepaidAccountMapper.insert(record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(DistributorPrepaidAccount record) {
        return distributorPrepaidAccountMapper.updateByPrimaryKey(record);
    }

    /**
     * 查询账户
     *
     * @param projectId     项目id
     * @param distributorId 经销商id
     * @return
     */
    public DistributorPrepaidAccount getAccount(long projectId, long distributorId) {
        return distributorPrepaidAccountMapper.getAccount(projectId, distributorId);
    }

    /**
     * 查询项目下的所有账户
     *
     * @param projectId 项目id
     * @return
     */
    public List<DistributorPrepaidAccount> getAccountList(long projectId) {
        return distributorPrepaidAccountMapper.getAccountList(projectId);
    }
}
