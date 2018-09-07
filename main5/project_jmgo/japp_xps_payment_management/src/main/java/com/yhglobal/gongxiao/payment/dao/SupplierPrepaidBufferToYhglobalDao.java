package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierPrepaidBufferToYhglobalMapper;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidBufferToYhglobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierPrepaidBufferToYhglobalDao {

    @Autowired
    SupplierPrepaidBufferToYhglobalMapper supplierPrepaidBufferToYhglobalMapper;

    /**
     * 插入新账户
     *
     * @param record 账户
     * @return
     */
    public int insert(String prefix, SupplierPrepaidBufferToYhglobal record) {
        return supplierPrepaidBufferToYhglobalMapper.insert(prefix, record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(String prefix, SupplierPrepaidBufferToYhglobal record) {
        return supplierPrepaidBufferToYhglobalMapper.updateByPrimaryKeyWithBLOBs(prefix, record);
    }

    /**
     * 查询账户
     *
     * @param projectId 项目id
     * @return
     */
    public SupplierPrepaidBufferToYhglobal getAccount(String prefix, long projectId) {
        return supplierPrepaidBufferToYhglobalMapper.getAccount(prefix, projectId);
    }
}
