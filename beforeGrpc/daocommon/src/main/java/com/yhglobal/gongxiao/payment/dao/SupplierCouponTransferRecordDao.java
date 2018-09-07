package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierCouponTransferRecordMapper;
import com.yhglobal.gongxiao.payment.dao.mapping.SupplierCouponTransferRecordSqlProvider;
import com.yhglobal.gongxiao.payment.model.SupplierCouponTransferRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierCouponTransferRecordDao {

    @Autowired
    SupplierCouponTransferRecordMapper supplierCouponTransferRecordMapper;

    public int insert(SupplierCouponTransferRecord record) {
        return supplierCouponTransferRecordMapper.insert(record);
    }

    /**
     * 查询供应商返利流水 包括收入和支出
     * @param supplierId    供应商ID
     **/
    public List<SupplierCouponTransferRecord> selectAllBySupplierId(String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate){
        return supplierCouponTransferRecordMapper.selectAllBySupplierId(currencyCode, supplierId,projectId,moneyFlow,beginDate,endDate);
    }


    public int selectIncomeCount(String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate){
        return supplierCouponTransferRecordMapper.selectIncomeCount(currencyCode,supplierId,projectId,moneyFlow,beginDate,endDate);
    }


    public Long selectIncomeAmount(String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate){
        return supplierCouponTransferRecordMapper.selectIncomeAmount(currencyCode,supplierId,projectId,moneyFlow,beginDate,endDate);
    }


    public int selectExpenditureCount(String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate){
        return supplierCouponTransferRecordMapper.selectExpenditureCount(currencyCode,supplierId,projectId,moneyFlow,beginDate,endDate);
    }


    public Long selectExpenditureAmount(String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate){
        return supplierCouponTransferRecordMapper.selectExpenditureAmount(currencyCode,supplierId,projectId,moneyFlow,beginDate,endDate);
    }

}
