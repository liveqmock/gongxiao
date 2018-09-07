package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierPrepaidBufferToDistributorFlowMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidBufferToDistributorFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierPrepaidBufferToDistributorFlowDao {

    @Autowired
    SupplierPrepaidBufferToDistributorFlowMapper supplierPrepaidBufferToDistributorFlowMapper;


    public int insert(String prefix,SupplierPrepaidBufferToDistributorFlow record){
        return supplierPrepaidBufferToDistributorFlowMapper.insert( prefix,record);
    }

    public List<SupplierPrepaidBufferToDistributorFlow> selectBufferPrepaidFlowList(String prefix,String currencyCode, long projectId, Integer moneyFlow, String beginDate, String endDate) {
        return supplierPrepaidBufferToDistributorFlowMapper.selectBufferPrepaidFlowList( prefix,currencyCode,  projectId, moneyFlow, beginDate, endDate);
    }

    public List<FlowSubtotal>  selectIncomeAndExpenditure(String prefix,String currencyCode, long projectId,   Integer moneyFlow, String beginDate, String endDate) {
        return supplierPrepaidBufferToDistributorFlowMapper.selectIncomeAndExpenditure( prefix,currencyCode, projectId, moneyFlow, beginDate, endDate);
    }

}
