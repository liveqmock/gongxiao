package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierPurchaseReservedFrozenAccountMapper;
import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierSalesDifferenceReservedAccountMapper;
import com.yhglobal.gongxiao.payment.model.SupplierPurchaseReservedFrozenAccount;
import com.yhglobal.gongxiao.payment.model.SupplierSalesDifferenceReservedAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 王帅
 */
@Repository
public class SupplierSalesDifferenceReservedAccountDao {

    @Autowired
    SupplierSalesDifferenceReservedAccountMapper supplierSalesDifferenceReservedAccountMapper;


    public SupplierSalesDifferenceReservedAccount selectByProjectId(Long projectId, String tablePrefix){
        return supplierSalesDifferenceReservedAccountMapper.selectByProjectId(projectId, tablePrefix);}

    public int updateAccountAmount(Long supplierId,BigDecimal frozenAmount, Long dataVersion, Date lastUpdateTime, String tracelog, String tablePrefix){
        return supplierSalesDifferenceReservedAccountMapper.updateAccountAmount(supplierId, frozenAmount, dataVersion, lastUpdateTime, tracelog, tablePrefix);
    }

}
