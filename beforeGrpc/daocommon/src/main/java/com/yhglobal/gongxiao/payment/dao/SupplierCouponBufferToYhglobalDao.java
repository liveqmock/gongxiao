package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorCouponAccountMapper;
import com.yhglobal.gongxiao.payment.dao.mapping.SupplierCouponBufferToYhglobalMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCouponAccount;
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
    public int insert(SupplierCouponBufferToYhglobal record) {
        return supplierCouponBufferToYhglobalMapper.insert(record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(SupplierCouponBufferToYhglobal record) {
        return supplierCouponBufferToYhglobalMapper.updateByPrimaryKeyWithBLOBs(record);
    }
    /**
     * 查询账户
     *
     * @param projectId     项目id
     * @return
     */
    public SupplierCouponBufferToYhglobal getAccount( long projectId) {
        return supplierCouponBufferToYhglobalMapper.getAccount(projectId);
    }
}
