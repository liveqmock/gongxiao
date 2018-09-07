package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.ManualInboundItemMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualInboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualInboundOrderIterm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManualInboundItemDao {
    @Autowired
    ManualInboundItemMapper manualInboundItemMapper;

    public int insertOrderList(List<ManualInboundOrderIterm> manualInboundOrderItermList){
        return manualInboundItemMapper.insertOrderList(manualInboundOrderItermList);
    }

    public int updateManualItemInfo(ManualInboundOrderIterm manualInboundOrderIterm){
        return manualInboundItemMapper.updateManualItemInfo(manualInboundOrderIterm);
    }

    public ManualInboundOrderIterm selectRecordByGonxiaoNumAndProductCode(String gonxiaoInboundOrderNo,String productCode){
        return manualInboundItemMapper.selectRecordByGonxiaoNumAndProductCode(gonxiaoInboundOrderNo,productCode);
    }
}
