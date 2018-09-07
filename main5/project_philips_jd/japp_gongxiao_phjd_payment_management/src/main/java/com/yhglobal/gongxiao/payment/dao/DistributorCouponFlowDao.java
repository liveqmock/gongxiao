package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorCouponFlowMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCouponFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class DistributorCouponFlowDao {

    @Autowired
    DistributorCouponFlowMapper distributorCouponFlowMapper;

    public int insert(DistributorCouponFlow record){
        return distributorCouponFlowMapper.insert(record);
    }

    public List<DistributorCouponFlow> selectAll(long projectId, long supplierId){
        return distributorCouponFlowMapper.selectAll(projectId,supplierId);
    }

    public List<DistributorCouponFlow> selectAllBydistributorId(String currencyCode, long distributorId, long projectId, Integer moneyFlow,
                                                                Date beginDate,
                                                                Date endDate) {
        return distributorCouponFlowMapper.selectAllBydistributorId(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }

    public int selectIncomeCount(String currencyCode, long distributorId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return distributorCouponFlowMapper.selectIncomeCount(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }

    public BigDecimal selectIncomeAmount(String currencyCode, long distributorId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return distributorCouponFlowMapper.selectIncomeAmount(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }

    public int selectExpenditureCount(String currencyCode, long distributorId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return distributorCouponFlowMapper.selectExpenditureCount(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }

    public BigDecimal selectExpenditureAmount(String currencyCode, long supplierId, long distributorId, Integer moneyFlow, Date beginDate, Date endDate) {
        return distributorCouponFlowMapper.selectExpenditureAmount(currencyCode, supplierId, distributorId, moneyFlow, beginDate, endDate);
    }

}
