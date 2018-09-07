package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierSellHeightTransferRecordMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightTransferRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierSellHeightTransferRecordDao {
    @Autowired
    SupplierSellHeightTransferRecordMapper supplierSellHeightTransferRecordMapper;

    public int insert(SupplierSellHeightTransferRecord record) {
        return supplierSellHeightTransferRecordMapper.insert(record);
    }


    public List<SupplierSellHeightTransferRecord> selectSupplierSellHighRecordList(String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return supplierSellHeightTransferRecordMapper.selectSupplierSellHighRecordList(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
    }

    public  List<FlowSubtotal> selectIncomeAndExpenditure(String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return supplierSellHeightTransferRecordMapper.selectIncomeAndExpenditure(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);

    }

}
