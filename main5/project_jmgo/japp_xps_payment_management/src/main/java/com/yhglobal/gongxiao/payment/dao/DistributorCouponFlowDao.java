package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorCouponFlowMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCouponFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class DistributorCouponFlowDao {

    @Autowired
    DistributorCouponFlowMapper distributorCouponFlowMapper;

    public int insert(String prefix,DistributorCouponFlow record){
        return distributorCouponFlowMapper.insert( prefix,record);
    }



    public List<DistributorCouponFlow> selectAllBydistributorId(String prefix,String currencyCode, long distributorId, long projectId, Integer moneyFlow,
                                                                Date beginDate,
                                                                Date endDate) {
        return distributorCouponFlowMapper.selectAllBydistributorId( prefix,currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }

    public int selectIncomeCount(String prefix,String currencyCode, long distributorId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return distributorCouponFlowMapper.selectIncomeCount( prefix,currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }

    public Long selectIncomeAmount(String prefix,String currencyCode, long distributorId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return distributorCouponFlowMapper.selectIncomeAmount( prefix,currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }

    public int selectExpenditureCount(String prefix,String currencyCode, long distributorId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return distributorCouponFlowMapper.selectExpenditureCount( prefix,currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }

    public Long selectExpenditureAmount(String prefix,String currencyCode, long supplierId, long distributorId, Integer moneyFlow, Date beginDate, Date endDate) {
        return distributorCouponFlowMapper.selectExpenditureAmount( prefix,currencyCode, supplierId, distributorId, moneyFlow, beginDate, endDate);
    }

}
