package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierPurchaseReservedFrozenAccountTransferFlowMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierPurchaseReservedFrozenAccountTransferFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Repository
public class SupplierPurchaseReservedFrozenAccountTransferFlowDao {

    @Autowired
    public SupplierPurchaseReservedFrozenAccountTransferFlowMapper supplierPurchaseReservedFrozenAccountTransferFlowMapper;

    public int insert(SupplierPurchaseReservedFrozenAccountTransferFlow flow){return supplierPurchaseReservedFrozenAccountTransferFlowMapper.insert(flow);}

    public int update(SupplierPurchaseReservedFrozenAccountTransferFlow flow){return supplierPurchaseReservedFrozenAccountTransferFlowMapper.updateByPrimaryKey(flow);}

    public int delete(Long id){return supplierPurchaseReservedFrozenAccountTransferFlowMapper.deleteByPrimaryKey(id);}

    public List<SupplierPurchaseReservedFrozenAccountTransferFlow> selectByConditions(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
        return supplierPurchaseReservedFrozenAccountTransferFlowMapper.selectByConditions(moneyFlow,dateS,dateE, tablePrefix);
    }
    public List<FlowSubtotal> selectTotal(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
        return supplierPurchaseReservedFrozenAccountTransferFlowMapper.selectTotal(moneyFlow, dateS, dateE, tablePrefix);
    }
}
