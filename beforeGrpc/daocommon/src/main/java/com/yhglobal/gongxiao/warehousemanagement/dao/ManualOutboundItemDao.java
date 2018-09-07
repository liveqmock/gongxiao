package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.ManualOutboundItemMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualOutboundOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManualOutboundItemDao {

    @Autowired
    ManualOutboundItemMapper manualOutboundItemMapper;

    public int insertOrderList(List<ManualOutboundOrderItem> manualOutboundOrderItemList){
        return manualOutboundItemMapper.insertOrderList(manualOutboundOrderItemList);
    }

    public int updateManualOutboundInfo(ManualOutboundOrderItem manualOutboundOrderItem){
        return manualOutboundItemMapper.updateManualOutboundInfo(manualOutboundOrderItem);
    }

    public ManualOutboundOrderItem selectRecordByGonxiaoNumAndProductCode(String gonxiaoOutboundOrderNo, String productCode){
        return manualOutboundItemMapper.selectRecordByGonxiaoNumAndProductCode(gonxiaoOutboundOrderNo,productCode);
    }
}
