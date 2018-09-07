package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierPurchaseReservedFrozenAccountMapper;
import com.yhglobal.gongxiao.payment.model.SupplierPurchaseReservedFrozenAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 王帅
 */
@Repository
public class SupplierPurchaseReservedFrozenAccountDao {

    @Autowired
    SupplierPurchaseReservedFrozenAccountMapper supplierPurchaseReservedFrozenAccountMapper;

    public SupplierPurchaseReservedFrozenAccount selectByPrimaryKey(Long supplierId){return supplierPurchaseReservedFrozenAccountMapper.selectByPrimaryKey(supplierId);}

    public int insert(SupplierPurchaseReservedFrozenAccount account){return supplierPurchaseReservedFrozenAccountMapper.insert(account);}

    public int update(SupplierPurchaseReservedFrozenAccount account){return supplierPurchaseReservedFrozenAccountMapper.updateByPrimaryKey(account);}

    public int delete(Long id, String tablePrefix){return supplierPurchaseReservedFrozenAccountMapper.deleteByPrimaryKey(id, tablePrefix);}

    public SupplierPurchaseReservedFrozenAccount selectByProjectId(Long projectId, String tablePrefix){return supplierPurchaseReservedFrozenAccountMapper.selectByProjectId(projectId, tablePrefix);}

    public int updateAccountAmount(Long supplierId, BigDecimal frozenAmount, Long dataVersion, Date lastUpdateTime, String tracelog, String tablePrefix){
        return supplierPurchaseReservedFrozenAccountMapper.updateAccountAmount(supplierId, frozenAmount, dataVersion, lastUpdateTime, tracelog, tablePrefix);
    }

}
