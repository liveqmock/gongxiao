package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierPurchaseReservedAccountTransferFlowMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierPurchaseReservedAccountTransferFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Repository
public class SupplierPurchaseReservedAccountFlowDao {

    @Autowired
    SupplierPurchaseReservedAccountTransferFlowMapper supplierPurchaseReservedAccountTransferFlowMapper;

    public int insert(SupplierPurchaseReservedAccountTransferFlow flow){
        return supplierPurchaseReservedAccountTransferFlowMapper.insert(flow);
    }
    public List<SupplierPurchaseReservedAccountTransferFlow> selectByConditions(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
        return supplierPurchaseReservedAccountTransferFlowMapper.selectByConditions(moneyFlow,dateS,dateE, tablePrefix);
    }
    public List<FlowSubtotal> selectTotal(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
        return supplierPurchaseReservedAccountTransferFlowMapper.selectTotal(moneyFlow, dateS, dateE, tablePrefix);
    }
}
