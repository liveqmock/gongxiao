package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierPrepaidTransferRecordMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidTransferRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierPrepaidTransferRecordDao {

    @Autowired
    SupplierPrepaidTransferRecordMapper supplierPrepaidTransferRecordMapper;

    public int insert(SupplierPrepaidTransferRecord record) {
        return supplierPrepaidTransferRecordMapper.insert(record);
    }

    /**
     * 查询供应商返利流水 包括收入和支出
     * @param supplierId    供应商ID
     **/
    public List<SupplierPrepaidTransferRecord> selectAllBySupplierId(String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate){
        return supplierPrepaidTransferRecordMapper.selectAllBySupplierId(currencyCode, supplierId,projectId,moneyFlow,beginDate,endDate);
    }


    public List<FlowSubtotal> selectIncomeAndExpenditure(@Param("currencyCode") String currencyCode,
                                                  @Param("supplierId") long supplierId,
                                                  @Param("projectId") long projectId,
                                                  @Param("moneyFlow") Integer moneyFlow,
                                                  @Param("beginDate") Date beginDate,
                                                  @Param("endDate") Date endDate){
        return supplierPrepaidTransferRecordMapper.selectIncomeAndExpenditure(currencyCode,supplierId,projectId,moneyFlow,beginDate,endDate);
    }
}
