package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierPurchaseReservedFrozenAccountTransferRecordMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierPurchaseReservedFrozenAccountTransferRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Repository
public class SupplierPurchaseReservedFrozenAccountTransferRecordDao {

    @Autowired
    SupplierPurchaseReservedFrozenAccountTransferRecordMapper supplierPurchaseReservedFrozenAccountTransferRecordMapper;

    public int insert(SupplierPurchaseReservedFrozenAccountTransferRecord record){return supplierPurchaseReservedFrozenAccountTransferRecordMapper.insert(record);}

    public int update(SupplierPurchaseReservedFrozenAccountTransferRecord record){return supplierPurchaseReservedFrozenAccountTransferRecordMapper.updateByPrimaryKey(record);}

    public int delete(Long id, String tablePrefix){return supplierPurchaseReservedFrozenAccountTransferRecordMapper.deleteByPrimaryKey(id, tablePrefix);}

//    public List<SupplierPurchaseReservedFrozenAccountTransferRecord> selectByConditions(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
//        return supplierPurchaseReservedFrozenAccountTransferRecordMapper.selectByConditions(moneyFlow,dateS,dateE, tablePrefix);
//    }
//
//    public List<FlowSubtotal> selectTotal( Integer moneyFlow,  Date dateS, Date dateE, String tablePrefix){
//        return supplierPurchaseReservedFrozenAccountTransferRecordMapper.selectTotal(moneyFlow,dateS,dateE, tablePrefix);
//    }

}
