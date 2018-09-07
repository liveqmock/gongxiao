package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierPurchaseReservedFrozenAccountTransferFlowMapper;
import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierSalesDifferenceReservedAccountTransferFlowMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierPurchaseReservedFrozenAccountTransferFlow;
import com.yhglobal.gongxiao.payment.model.SupplierSalesDifferenceReservedAccountTransferFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Repository
public class SupplierSalesDifferenceReservedAccountFlowDao {

    @Autowired
    public SupplierSalesDifferenceReservedAccountTransferFlowMapper supplierSalesDifferenceReservedAccountTransferFlowMapper;

    public int insert(SupplierSalesDifferenceReservedAccountTransferFlow flow){return supplierSalesDifferenceReservedAccountTransferFlowMapper.insert(flow);}
    public List<SupplierSalesDifferenceReservedAccountTransferFlow> selectByConditions(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
        return supplierSalesDifferenceReservedAccountTransferFlowMapper.selectByConditions(moneyFlow,dateS,dateE, tablePrefix);
    }
    public List<FlowSubtotal> selectTotal(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
        return supplierSalesDifferenceReservedAccountTransferFlowMapper.selectTotal(moneyFlow, dateS, dateE, tablePrefix);
    }
}
