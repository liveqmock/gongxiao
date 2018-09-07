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

    public int insert(String prefix,DistributorCashFlow record) {
        return distributorCouponFlowMapper.insert( prefix,record);
    }

    public List<DistributorCashFlow> selectAllBydistributorId(String prefix,String currencyCode, long distributorId, long projectId, int moneyFlow,
                                                              String beginDate,
                                                              String endDate) {
        return distributorCouponFlowMapper.selectAllBydistributorId( prefix,currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }

    public  List<FlowSubtotal> selectIncomeAndExpenditure(String prefix,String currencyCode, long distributorId, long projectId, int moneyFlow, String beginDate, String endDate) {
        return distributorCouponFlowMapper.selectIncomeAndExpenditure( prefix,currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }



}
