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

    public int insert(String prefix,SupplierSellHeightTransferRecord record) {
        return supplierSellHeightTransferRecordMapper.insert( prefix,record);
    }


    public List<SupplierSellHeightTransferRecord> selectSupplierSellHighRecordList(String prefix,String currencyCode, long projectId, Integer moneyFlow, String beginDate, String endDate) {
        List<SupplierSellHeightTransferRecord> supplierSellHeightTransferRecords = supplierSellHeightTransferRecordMapper.selectSupplierSellHighRecordList(prefix, currencyCode, projectId, moneyFlow, beginDate, endDate);
        return supplierSellHeightTransferRecords;
    }

    public  List<FlowSubtotal> selectIncomeAndExpenditure(String prefix,String currencyCode, long projectId, Integer moneyFlow, String beginDate, String endDate) {
        return supplierSellHeightTransferRecordMapper.selectIncomeAndExpenditure( prefix,currencyCode,  projectId, moneyFlow, beginDate, endDate);

    }

}
