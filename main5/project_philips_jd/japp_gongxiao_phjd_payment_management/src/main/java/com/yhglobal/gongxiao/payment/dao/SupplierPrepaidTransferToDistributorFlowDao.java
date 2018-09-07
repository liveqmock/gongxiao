package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierPrepaidTransferToDistributorFlowMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidTransferToDistributorFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierPrepaidTransferToDistributorFlowDao {

    @Autowired
    SupplierPrepaidTransferToDistributorFlowMapper supplierPrepaidTransferToDistributorFlowMapper ;


    public int insert(SupplierPrepaidTransferToDistributorFlow record){
        return supplierPrepaidTransferToDistributorFlowMapper.insert(record);
    }

    public List<SupplierPrepaidTransferToDistributorFlow> selectBufferPrepaidFlowList(String currencyCode, long projectId, Integer moneyFlow, String beginDate, String endDate) {
        return supplierPrepaidTransferToDistributorFlowMapper.selectBufferPrepaidFlowList(currencyCode,  projectId, moneyFlow, beginDate, endDate);
    }

    public List<FlowSubtotal>  selectIncomeAndExpenditure(String currencyCode, long projectId,   Integer moneyFlow, String beginDate, String endDate) {
        return supplierPrepaidTransferToDistributorFlowMapper.selectIncomeAndExpenditure(currencyCode, projectId, moneyFlow, beginDate, endDate);
    }

}
