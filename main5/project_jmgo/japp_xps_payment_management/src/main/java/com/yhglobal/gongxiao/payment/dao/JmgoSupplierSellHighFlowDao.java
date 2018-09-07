package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.JmgoSupplierSellHighFlowMapper;
import com.yhglobal.gongxiao.payment.model.JmgoSupplierSellHighFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 获取低买高卖流水记录
     *
     * @param prefix       表前缀
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @param moneyFlow    资金流向
     * @param beginDate    起始日期
     * @param endDate
     * @return
     */
    public List<JmgoSupplierSellHighFlow> selectSupplierSellHighFlowList(String prefix, String currencyCode, long projectId, int moneyFlow, String beginDate, String endDate) {
        return jmgoSupplierSellHighFlowMapper.selectSupplierSellHighFlowList(prefix, currencyCode, projectId, moneyFlow, beginDate, endDate);
    }
}
