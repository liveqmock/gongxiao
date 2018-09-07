package com.yhglobal.gongxiao.wmscomfirm.dao;

import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrder;
import com.yhglobal.gongxiao.wmscomfirm.dao.mapper.OutboundOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OutBoundOrderDao {
    @Autowired
    OutboundOrderMapper outboundOrderMapper;

    public OutboundOrder getOutboundRecordByGoxiaoOutNo(String gongxiaoOutboundOrderNo,String projectPrefix){
        return outboundOrderMapper.getOutboundRecordByGoxiaoOutNo(gongxiaoOutboundOrderNo,projectPrefix);
    }

}
