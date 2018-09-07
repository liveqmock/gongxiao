package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierPrepaidFlowMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierPrepaidFlowDao {

    @Autowired
    SupplierPrepaidFlowMapper supplierPrepaidFlowMapper;

    public int insert(String prefix, SupplierPrepaidFlow record) {
        return supplierPrepaidFlowMapper.insert( prefix, record);
    }

    /**
     * 查询供应商返利流水 包括收入和支出
     *
     * @param supplierId 供应商ID
     **/
    public List<SupplierPrepaidFlow> selectAllBySupplierId(String prefix, String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return supplierPrepaidFlowMapper.selectAllBySupplierId(prefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
    }


    public List<FlowSubtotal> selectIncomeAndExpenditure(String prefix,
                                                         String currencyCode,
                                                         long supplierId,
                                                         long projectId,
                                                         Integer moneyFlow,
                                                         Date beginDate,
                                                         Date endDate) {
        return supplierPrepaidFlowMapper.selectIncomeAndExpenditure(prefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
    }
}
