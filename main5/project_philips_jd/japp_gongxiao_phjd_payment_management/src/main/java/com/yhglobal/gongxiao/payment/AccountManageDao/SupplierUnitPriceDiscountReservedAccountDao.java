package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierUnitPriceDiscountReservedAccountMapper;
import com.yhglobal.gongxiao.payment.model.SupplierUnitPriceDiscountReservedAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 单价折扣预留账户dao
 */
@Repository
public class SupplierUnitPriceDiscountReservedAccountDao {

    @Autowired
    SupplierUnitPriceDiscountReservedAccountMapper supplierUnitPriceDiscountReservedAccountMapper;

    public int insert(SupplierUnitPriceDiscountReservedAccount account){return supplierUnitPriceDiscountReservedAccountMapper.insert(account);}
    public SupplierUnitPriceDiscountReservedAccount selectByProjectId(Long projectId, String tablePrefix){
        return supplierUnitPriceDiscountReservedAccountMapper.selectByProjectId(projectId, tablePrefix);
    }

    public int updateAccountAmount(Long supplierId, BigDecimal frozenAmount, Long dataVersion, Date lastUpdateTime, String tracelog, String tablePrefix){
        return supplierUnitPriceDiscountReservedAccountMapper.updateAccountAmount(supplierId, frozenAmount, dataVersion, lastUpdateTime, tracelog, tablePrefix);
    }
}