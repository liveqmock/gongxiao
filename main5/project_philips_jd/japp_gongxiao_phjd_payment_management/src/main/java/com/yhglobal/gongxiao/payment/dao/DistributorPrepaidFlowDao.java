package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorPrepaidFlowMapper;
import com.yhglobal.gongxiao.payment.model.DistributorPrepaidFlow;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class DistributorPrepaidFlowDao {

    @Autowired
    DistributorPrepaidFlowMapper distributorPrepaidFlowMapper ;

    public int insert(DistributorPrepaidFlow record){
        return distributorPrepaidFlowMapper.insert(record);
    }
    public List<DistributorPrepaidFlow> selectAllBydistributorId(String currencyCode, long distributorId, long projectId, Integer moneyFlow,
                                                                 Date beginDate,
                                                                 Date endDate) {
        return distributorPrepaidFlowMapper.selectAllBydistributorId(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }

    public List<FlowSubtotal> selectIncomeAndExpenditure(@Param("currencyCode") String currencyCode,
                                                         @Param("distributorId") long distributorId,
                                                         @Param("projectId") long projectId,
                                                         @Param("moneyFlow") Integer moneyFlow,
                                                         @Param("beginDate") Date beginDate,
                                                         @Param("endDate") Date endDate){
        return distributorPrepaidFlowMapper.selectIncomeAndExpenditure(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
    }
}
