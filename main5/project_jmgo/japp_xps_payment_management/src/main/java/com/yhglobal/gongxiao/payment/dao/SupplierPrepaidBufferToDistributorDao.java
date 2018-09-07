package com.yhglobal.gongxiao.payment.dao;

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
    public int insert(String prefix,SupplierPrepaidBufferToDistributor record) {
        return supplierPrepaidBufferToDistributorMapper.insert( prefix,record);
    }

    /**
     * 更新账户
     * @param record 账户
     * @return
     */
    public int update(String prefix,SupplierPrepaidBufferToDistributor record) {
        return supplierPrepaidBufferToDistributorMapper.updateByPrimaryKeyWithBLOBs( prefix,record);
    }
    /**
     * 查询账户
     *
     * @param projectId     项目id
     * @return
     */
    public SupplierPrepaidBufferToDistributor getAccount(String prefix,long projectId){
        return supplierPrepaidBufferToDistributorMapper.getAccount( prefix, projectId);
    }
}
