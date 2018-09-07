package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierCouponTransferToDistributorFlowMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierCouponTransferToDistributorFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierCouponTransferToDistributorFlowDao {

    @Autowired
    SupplierCouponTransferToDistributorFlowMapper supplierCouponTransferToDistributorFlowMapper;

    public int insert(SupplierCouponTransferToDistributorFlow record) {
        return supplierCouponTransferToDistributorFlowMapper.insert(record);
    }

    public List<SupplierCouponTransferToDistributorFlow> selectBufferCouponFlowList(String currencyCode, long projectId, Integer moneyFlow,
                                                                                    String beginDate,
                                                                                    String endDate) {
        return supplierCouponTransferToDistributorFlowMapper.selectBufferCouponFlowList(currencyCode,  projectId, moneyFlow, beginDate, endDate);
    }

    public List<FlowSubtotal>  selectIncomeAndExpenditure(String currencyCode,  long projectId, Integer moneyFlow, String beginDate, String endDate) {
        return supplierCouponTransferToDistributorFlowMapper.selectIncomeAndExpenditure(currencyCode, projectId, moneyFlow, beginDate, endDate);
    }


}
