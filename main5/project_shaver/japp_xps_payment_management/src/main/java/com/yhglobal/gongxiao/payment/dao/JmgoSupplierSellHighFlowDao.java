package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.JmgoSupplierSellHighFlowMapper;
import com.yhglobal.gongxiao.payment.model.JmgoSupplierSellHighFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 坚果高卖账户流水DAO
 *
 * @author 葛灿
 */
@Repository
public class JmgoSupplierSellHighFlowDao {

    @Autowired
    JmgoSupplierSellHighFlowMapper jmgoSupplierSellHighFlowMapper;

    /**
     * 插入对象
     *
     * @param prefix                   表前缀
     * @param jmgoSupplierSellHighFlow 对象
     * @return 数据库新增记录数
     */
    public int insertFlow(String prefix, JmgoSupplierSellHighFlow jmgoSupplierSellHighFlow) {
        return jmgoSupplierSellHighFlowMapper.insertFlow(prefix, jmgoSupplierSellHighFlow);
    }
}
