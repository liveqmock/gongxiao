package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.SupplierUnitPriceDiscountFrozenAccountTransferFlowMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierUnitPriceDiscountFrozenAccountTransferFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Repository
public class SupplierUnitPriceDiscountFrozenAccountTransferFlowDao {

    @Autowired
    SupplierUnitPriceDiscountFrozenAccountTransferFlowMapper supplierUnitPriceDiscountFrozenAccountTransferFlowMapper;

    public int insert(SupplierUnitPriceDiscountFrozenAccountTransferFlow flow){return supplierUnitPriceDiscountFrozenAccountTransferFlowMapper.insert(flow);}

    public int update(SupplierUnitPriceDiscountFrozenAccountTransferFlow flow){return supplierUnitPriceDiscountFrozenAccountTransferFlowMapper.updateByPrimaryKey(flow);}

    public int delete(Long id){return supplierUnitPriceDiscountFrozenAccountTransferFlowMapper.deleteByPrimaryKey(id);}

    public List<SupplierUnitPriceDiscountFrozenAccountTransferFlow> selectByConditions(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
        return supplierUnitPriceDiscountFrozenAccountTransferFlowMapper.selectByConditions(moneyFlow,dateS,dateE, tablePrefix);
    }

    public List<FlowSubtotal> selectTotal(Integer moneyFlow, Date dateS, Date dateE, String tablePrefix){
        return supplierUnitPriceDiscountFrozenAccountTransferFlowMapper.selectTotal(moneyFlow,dateS,dateE, tablePrefix);
    }
}
