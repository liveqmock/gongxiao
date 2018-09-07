package com.yhglobal.gongxiao.warehouse.dao;

import com.yhglobal.gongxiao.warehouse.dao.mapping.ManualOutboundMapper;
import com.yhglobal.gongxiao.warehouse.model.ManualOutboundOrder;
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
