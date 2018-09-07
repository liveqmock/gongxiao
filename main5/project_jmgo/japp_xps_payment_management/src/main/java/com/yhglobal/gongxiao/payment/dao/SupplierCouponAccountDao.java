package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierCouponAccountMapper;
import com.yhglobal.gongxiao.payment.model.SupplierCouponAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierCouponAccountDao {

    @Autowired
    SupplierCouponAccountMapper supplierCouponAccountMapper;

    /**
     * 插入新账户
     *
     * @param record 账户
     * @return
     */
    public int insert(String prefix,SupplierCouponAccount record) {
        return supplierCouponAccountMapper.insert( prefix,record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(String prefix,SupplierCouponAccount record) {
        return supplierCouponAccountMapper.updateByPrimaryKeyWithBLOBs( prefix,record);
    }

    /**
     * 查询账户
     *
     * @param projectId     项目id
     * @return
     */
    public SupplierCouponAccount getAccount(String prefix,long projectId) {
        return supplierCouponAccountMapper.getAccount( prefix, projectId);
    }
}
