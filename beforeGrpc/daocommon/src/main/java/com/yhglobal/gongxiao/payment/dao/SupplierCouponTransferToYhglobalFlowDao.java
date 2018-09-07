package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierCouponTransferToYhglobalFlowMapper;
import com.yhglobal.gongxiao.payment.model.SupplierCouponTransferToYhglobalFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierCouponTransferToYhglobalFlowDao {

    @Autowired
    SupplierCouponTransferToYhglobalFlowMapper supplierCouponTransferToYhglobalFlowMapper ;

    public int insert(SupplierCouponTransferToYhglobalFlow record){
        return supplierCouponTransferToYhglobalFlowMapper.insert(record);
    }
}
