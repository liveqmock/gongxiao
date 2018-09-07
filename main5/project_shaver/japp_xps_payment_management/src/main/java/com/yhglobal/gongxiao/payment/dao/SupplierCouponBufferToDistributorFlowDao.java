package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierCouponBufferToDistributorFlowMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierCouponBufferToDistributorFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierCouponBufferToDistributorFlowDao {

    @Autowired
    SupplierCouponBufferToDistributorFlowMapper supplierCouponBufferToDistributorFlowMapper;

    public int insert(String prefix,SupplierCouponBufferToDistributorFlow record) {
        return supplierCouponBufferToDistributorFlowMapper.insert( prefix,record);
    }

    public List<SupplierCouponBufferToDistributorFlow> selectBufferCouponFlowList(String prefix,String currencyCode, long projectId, Integer moneyFlow,
                                                                                  String beginDate,
                                                                                  String endDate) {
        return supplierCouponBufferToDistributorFlowMapper.selectBufferCouponFlowList( prefix,currencyCode,  projectId, moneyFlow, beginDate, endDate);
    }

    public List<FlowSubtotal>  selectIncomeAndExpenditure(String prefix,String currencyCode,  long projectId, Integer moneyFlow, String beginDate, String endDate) {
        return supplierCouponBufferToDistributorFlowMapper.selectIncomeAndExpenditure( prefix,currencyCode, projectId, moneyFlow, beginDate, endDate);
    }


}
