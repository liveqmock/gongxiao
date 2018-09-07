package com.yhglobal.gongxiao.wmscomfirm.dao;

import com.yhglobal.gongxiao.warehouseapi.model.InboundOrder;
import com.yhglobal.gongxiao.wmscomfirm.dao.mapper.InboundOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InBoundOrderDao {

    @Autowired
    InboundOrderMapper inboundOrderMapper;

    public InboundOrder getInboundRecordByOrderNo(String gongxiaoInboundOrder,String projectPrefix) {
        return inboundOrderMapper.getInboundRecordByOrderNo(gongxiaoInboundOrder,projectPrefix);
    }

}
