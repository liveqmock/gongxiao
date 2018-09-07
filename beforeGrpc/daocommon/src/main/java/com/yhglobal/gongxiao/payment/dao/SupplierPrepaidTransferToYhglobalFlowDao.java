package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.SupplierPrepaidTransferToYhglobalFlowMapper;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidTransferToYhglobalFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SupplierPrepaidTransferToYhglobalFlowDao {

    @Autowired
    SupplierPrepaidTransferToYhglobalFlowMapper supplierPrepaidTransferToYhglobalFlowMapper ;


    public int insert(SupplierPrepaidTransferToYhglobalFlow record){
        return supplierPrepaidTransferToYhglobalFlowMapper.insert(record);
    }
}
