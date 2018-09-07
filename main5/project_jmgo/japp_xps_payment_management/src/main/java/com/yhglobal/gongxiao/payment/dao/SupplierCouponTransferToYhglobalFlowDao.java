package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierCouponBufferToYhglobalFlowMapper;
import com.yhglobal.gongxiao.payment.model.SupplierCouponBufferToYhglobalFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierCouponTransferToYhglobalFlowDao {

    @Autowired
    SupplierCouponBufferToYhglobalFlowMapper supplierCouponBufferToYhglobalFlowMapper;

    public int insert(String prefix,SupplierCouponBufferToYhglobalFlow record){
        return supplierCouponBufferToYhglobalFlowMapper.insert( prefix,record);
    }
}
