package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorCashFlowMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCashFlow;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class DistributorCashFlowDao {

    @Autowired
    DistributorCashFlowMapper distributorCouponFlowMapper;

    public int insert(DistributorCashFlow record) {
        return distributorCouponFlowMapper.insert(record);
    }

    public List<DistributorCashFlow> selectAllBydistributorId(String currencyCode, long distributorId, long projectId, Integer moneyFlow,
                                                              Date beginDate,
                                                              Date endDate) {
        return distributorCouponFlowMapper.selectAllBydistributorId(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }

    public  List<FlowSubtotal> selectIncomeAndExpenditure(String currencyCode, long distributorId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return distributorCouponFlowMapper.selectIncomeAndExpenditure(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }



}
