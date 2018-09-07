package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierUnitPriceDiscountFrozenAccountTransferRecordMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierUnitPriceDiscountFrozenAccountTransferRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Repository
public class SupplierUnitPriceDiscountFrozenAccountTransferRecordDao {

    @Autowired
    SupplierUnitPriceDiscountFrozenAccountTransferRecordMapper supplierUnitPriceDiscountFrozenAccountTransferRecordMapper;

    public int insert(SupplierUnitPriceDiscountFrozenAccountTransferRecord record){return supplierUnitPriceDiscountFrozenAccountTransferRecordMapper.insert(record);}


//    public List<SupplierUnitPriceDiscountFrozenAccountTransferRecord> selectByConditions(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
//        return supplierUnitPriceDiscountFrozenAccountTransferRecordMapper.selectByConditions(moneyFlow,dateS,dateE, tablePrefix);
//    }
//
//    public List<FlowSubtotal> selectTotal(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
//        return supplierUnitPriceDiscountFrozenAccountTransferRecordMapper.selectTotal(moneyFlow,dateS,dateE, tablePrefix);
//    }
}
