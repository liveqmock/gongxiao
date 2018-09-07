package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.InboundOrderMapper;
import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.ManualInboundMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualInboundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManualInboudDao {
    @Autowired
    ManualInboundMapper manualInboundMapper;

    public List<ManualInboundOrder> selectAllRecord(){
        return manualInboundMapper.selectAllRecord();
    }

    public int insertManualInboundInfo(ManualInboundOrder manualInboundOrder) {
        return manualInboundMapper.insertManualInboundInfo(manualInboundOrder);
    }

    public int updateManualInboundInfo(ManualInboundOrder manualInboundOrder) {
        return manualInboundMapper.updateManualInboundInfo(manualInboundOrder);
    }

    public int updateEasFlagInfo(String orderNumber){
        return manualInboundMapper.updateEasFlagInfo(orderNumber);
    }

}
