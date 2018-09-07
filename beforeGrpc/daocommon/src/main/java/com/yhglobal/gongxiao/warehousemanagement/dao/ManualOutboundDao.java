package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.ManualOutboundMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualOutboundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManualOutboundDao {
    @Autowired
    ManualOutboundMapper manualOutboundMapper;

    public List<ManualOutboundOrder> selectAllRecord(){
        return manualOutboundMapper.selectAllRecord();
    }

    public int insertManualOutboundInfo(ManualOutboundOrder manualOutboundOrder) {
        return manualOutboundMapper.insertManualOutboundInfo(manualOutboundOrder);
    }

    public int updateManualOutboundInfo(ManualOutboundOrder manualOutboundOrder) {
        return manualOutboundMapper.updateManualOutboundInfo(manualOutboundOrder);
    }

    public int updateEasFlagInfo(String orderNumber){
        return manualOutboundMapper.updateEasFlagInfo(orderNumber);
    }

}
