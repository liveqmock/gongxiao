package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierPrepaidBufferToYhglobalFlowMapper;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidBufferToYhglobalFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierPrepaidBufferToYhglobalFlowDao {

    @Autowired
    SupplierPrepaidBufferToYhglobalFlowMapper supplierPrepaidBufferToYhglobalFlowMapper;


    public int insert(String prefix,SupplierPrepaidBufferToYhglobalFlow record){
        return supplierPrepaidBufferToYhglobalFlowMapper.insert( prefix,record);
    }
}
