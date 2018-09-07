package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierPrepaidTransferToDistributorFlowMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidTransferToDistributorFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    public List<SupplierPrepaidTransferToDistributorFlow> selectBufferPrepaidFlowList(String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return supplierPrepaidTransferToDistributorFlowMapper.selectBufferPrepaidFlowList(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
    }

    public List<FlowSubtotal>  selectIncomeAndExpenditure(String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return supplierPrepaidTransferToDistributorFlowMapper.selectIncomeAndExpenditure(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
    }

}
