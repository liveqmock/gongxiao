package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorCouponAccountMapper;
import com.yhglobal.gongxiao.payment.dao.mapping.SupplierCouponBufferToDistributorMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCouponAccount;
import com.yhglobal.gongxiao.payment.model.SupplierCouponBufferToDistributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierCouponBufferToDistributorDao {

    @Autowired
    SupplierCouponBufferToDistributorMapper supplierCouponBufferToDistributorMapper;

    /**
     * 插入新账户
     *
     * @param record 账户
     * @return
     */
    public int insert(SupplierCouponBufferToDistributor record) {
        return supplierCouponBufferToDistributorMapper.insert(record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(SupplierCouponBufferToDistributor record) {
        return supplierCouponBufferToDistributorMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    /**
     * 查询账户
     *
     * @param supplierId 供应商id
     * @param projectId     项目id
     * @return
     */
    public SupplierCouponBufferToDistributor getAccount(long projectId) {
        return supplierCouponBufferToDistributorMapper.getAccount( projectId);
    }
}
