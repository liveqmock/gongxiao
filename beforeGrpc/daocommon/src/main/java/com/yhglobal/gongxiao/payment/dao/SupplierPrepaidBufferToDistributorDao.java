package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorCouponAccountMapper;
import com.yhglobal.gongxiao.payment.dao.mapping.SupplierPrepaidBufferToDistributorMapper;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidBufferToDistributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierPrepaidBufferToDistributorDao {

    @Autowired
    SupplierPrepaidBufferToDistributorMapper supplierPrepaidBufferToDistributorMapper ;

    /**
     * 插入新账户
     * @param record 账户
     * @return
     */
    public int insert(SupplierPrepaidBufferToDistributor record) {
        return supplierPrepaidBufferToDistributorMapper.insert(record);
    }

    /**
     * 更新账户
     * @param record 账户
     * @return
     */
    public int update(SupplierPrepaidBufferToDistributor record) {
        return supplierPrepaidBufferToDistributorMapper.updateByPrimaryKeyWithBLOBs(record);
    }
    /**
     * 查询账户
     *
     * @param supplierId 供应商id
     * @param projectId     项目id
     * @return
     */
    public  SupplierPrepaidBufferToDistributor  getAccount(long projectId){
        return supplierPrepaidBufferToDistributorMapper.getAccount( projectId);
    }
}
