package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierCouponBufferToYhglobalMapper;
import com.yhglobal.gongxiao.payment.model.SupplierCouponBufferToYhglobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierCouponBufferToYhglobalDao {

    @Autowired
    SupplierCouponBufferToYhglobalMapper supplierCouponBufferToYhglobalMapper;

    /**
     * 插入新账户
     *
     * @param record 账户
     * @return
     */
    public int insert(String prefix,SupplierCouponBufferToYhglobal record) {
        return supplierCouponBufferToYhglobalMapper.insert( prefix,record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(String prefix,SupplierCouponBufferToYhglobal record) {
        return supplierCouponBufferToYhglobalMapper.updateByPrimaryKeyWithBLOBs( prefix,record);
    }
    /**
     * 查询账户
     *
     * @param projectId     项目id
     * @return
     */
    public SupplierCouponBufferToYhglobal getAccount(String prefix,long projectId) {
        return supplierCouponBufferToYhglobalMapper.getAccount( prefix,projectId);
    }
}
