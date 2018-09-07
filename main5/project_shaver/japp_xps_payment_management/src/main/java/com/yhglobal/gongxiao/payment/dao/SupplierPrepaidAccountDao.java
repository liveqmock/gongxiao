package com.yhglobal.gongxiao.payment.dao;

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
    public int insert(String prefix,SupplierPrepaidAccount record) {
        return supplierPrepaidAccountMapper.insert( prefix,record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(String prefix,SupplierPrepaidAccount record) {
        return supplierPrepaidAccountMapper.updateByPrimaryKeyWithBLOBs( prefix,record);
    }

    /**
     * 查询账户
     *
     * @param projectId 项目id
     * @return
     */
    public SupplierPrepaidAccount getAccount(String prefix,long projectId) {
        return supplierPrepaidAccountMapper.getAccount( prefix,projectId);
    }

}
