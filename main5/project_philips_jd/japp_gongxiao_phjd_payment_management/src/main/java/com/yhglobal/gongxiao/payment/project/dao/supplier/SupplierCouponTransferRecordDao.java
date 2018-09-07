package com.yhglobal.gongxiao.payment.project.dao.supplier;


import com.yhglobal.gongxiao.payment.project.dao.supplier.mapping.SupplierCouponFlowMapper;
import com.yhglobal.gongxiao.payment.project.model.SupplierCouponFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierCouponTransferRecordDao {

    @Autowired
    SupplierCouponFlowMapper supplierCouponFlowMapper;

    public int insert(String prefix, SupplierCouponFlow record) {
        return supplierCouponFlowMapper.insert(prefix, record);
    }

    /**
     * 查询供应商返利流水 包括收入和支出
     *
     * @param supplierId 供应商ID
     **/
    public List<SupplierCouponFlow> selectAllBySupplierId(String prefix, String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return supplierCouponFlowMapper.selectAllBySupplierId(prefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
    }


    public int selectIncomeCount(String prefix, String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return supplierCouponFlowMapper.selectIncomeCount(prefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
    }


    public BigDecimal selectIncomeAmount(String prefix, String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return supplierCouponFlowMapper.selectIncomeAmount(prefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
    }


    public int selectExpenditureCount(String prefix, String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return supplierCouponFlowMapper.selectExpenditureCount(prefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
    }


    public BigDecimal selectExpenditureAmount(String prefix, String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return supplierCouponFlowMapper.selectExpenditureAmount(prefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
    }

}
