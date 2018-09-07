package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierUnitPriceDiscountFrozenAccountMapper;
import com.yhglobal.gongxiao.payment.model.SupplierUnitPriceDiscountFrozenAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 王帅
 */
@Repository
public class SupplierUnitPriceDiscountFrozenAccountDao {

    @Autowired
    SupplierUnitPriceDiscountFrozenAccountMapper supplierUnitPriceDiscountFrozenAccountMapper;

    public SupplierUnitPriceDiscountFrozenAccount selectByPrimaryKey(Long supplierId, String tablePrefix){return supplierUnitPriceDiscountFrozenAccountMapper.selectByPrimaryKey(supplierId,tablePrefix);}

    public int insert(SupplierUnitPriceDiscountFrozenAccount account){return supplierUnitPriceDiscountFrozenAccountMapper.insert(account);}

    public int update(SupplierUnitPriceDiscountFrozenAccount account){return supplierUnitPriceDiscountFrozenAccountMapper.updateByPrimaryKey(account);}

    public int delete(Long supplierId, String tablePrefix){return supplierUnitPriceDiscountFrozenAccountMapper.deleteByPrimaryKey(supplierId, tablePrefix);}

    public SupplierUnitPriceDiscountFrozenAccount selectByProjectId(Long projectId, String tablePrefix){return supplierUnitPriceDiscountFrozenAccountMapper.selectByProjectId(projectId, tablePrefix);}

    public int updateAccountAmount(Long supplierId, BigDecimal frozenAmount, Long dataVersion,Date lastUpdateTime, String tracelog, String tablePrefix){
        return supplierUnitPriceDiscountFrozenAccountMapper.updateAccountAmount(supplierId, frozenAmount, dataVersion, lastUpdateTime, tracelog, tablePrefix);
    }
}
