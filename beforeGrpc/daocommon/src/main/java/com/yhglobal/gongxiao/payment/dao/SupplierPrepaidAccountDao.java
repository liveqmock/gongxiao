package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorCouponAccountMapper;
import com.yhglobal.gongxiao.payment.dao.mapping.SupplierPrepaidAccountMapper;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierPrepaidAccountDao {

    @Autowired
    SupplierPrepaidAccountMapper supplierPrepaidAccountMapper;

    /**
     * 插入新账户
     *
     * @param record 账户
     * @return
     */
    public int insert(SupplierPrepaidAccount record) {
        return supplierPrepaidAccountMapper.insert(record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(SupplierPrepaidAccount record) {
        return supplierPrepaidAccountMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    /**
     * 查询账户
     *
     * @param projectId 项目id
     * @return
     */
    public SupplierPrepaidAccount getAccount(long projectId) {
        return supplierPrepaidAccountMapper.getAccount(projectId);
    }

}
