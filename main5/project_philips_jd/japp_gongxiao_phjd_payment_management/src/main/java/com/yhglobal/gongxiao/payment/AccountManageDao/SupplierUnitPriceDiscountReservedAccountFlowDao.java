package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierUnitPriceDiscountReservedAccountTransferFlowMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierUnitPriceDiscountReservedAccountTransferFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Repository
public class SupplierUnitPriceDiscountReservedAccountFlowDao {

    @Autowired
    SupplierUnitPriceDiscountReservedAccountTransferFlowMapper supplierUnitPriceDiscountReservedAccountTransferFlowMapper;

    public int insert(SupplierUnitPriceDiscountReservedAccountTransferFlow flow){return supplierUnitPriceDiscountReservedAccountTransferFlowMapper.insert(flow);}

    public List<SupplierUnitPriceDiscountReservedAccountTransferFlow> selectByConditions(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
        return supplierUnitPriceDiscountReservedAccountTransferFlowMapper.selectByConditions(moneyFlow,dateS,dateE, tablePrefix);
    }

    public List<FlowSubtotal> selectTotal(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
        return supplierUnitPriceDiscountReservedAccountTransferFlowMapper.selectTotal(moneyFlow,dateS,dateE, tablePrefix);
    }
}
