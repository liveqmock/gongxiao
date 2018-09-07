package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierSellHeightAccountMapper;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierSellHeightTransferAccountDao {

    @Autowired
    SupplierSellHeightAccountMapper supplierSellHeightAccountMapper;

    /**
     * 插入新账户
     *
     * @param record 账户
     * @return
     */
    public int insert(SupplierSellHeightAccount record) {
        return supplierSellHeightAccountMapper.insert(record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(SupplierSellHeightAccount record) {
        return supplierSellHeightAccountMapper.updateByPrimaryKeyWithBLOBs(record);
    }
    /**
     * 查询账户
     *
     * @param projectId     项目id
     * @return
     */
    public SupplierSellHeightAccount getAccount(long projectId) {
        return supplierSellHeightAccountMapper.getAccount(projectId);
    }

}
