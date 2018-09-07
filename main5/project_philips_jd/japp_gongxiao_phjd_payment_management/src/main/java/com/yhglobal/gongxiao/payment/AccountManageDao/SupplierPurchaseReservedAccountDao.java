package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierPurchaseReservedAccountMapper;
import com.yhglobal.gongxiao.payment.model.SupplierPurchaseReservedAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购预留账户dao
 */
@Repository
public class SupplierPurchaseReservedAccountDao {

    @Autowired
    SupplierPurchaseReservedAccountMapper supplierPurchaseReservedAccountMapper;

    public int updateAccountAmount(Long supplierId, BigDecimal frozenAmount, Long dataVersion, Date lastUpdateTime, String tracelog, String tablePrefix){
        return supplierPurchaseReservedAccountMapper.updateAccountAmount(supplierId, frozenAmount, dataVersion, lastUpdateTime, tracelog, tablePrefix);
    }
    public SupplierPurchaseReservedAccount selectByProjectId(Long projectId, String tablePrefix){
        return supplierPurchaseReservedAccountMapper.selectByProjectId(projectId, tablePrefix);
    }
}