package com.yhglobal.gongxiao.wmscomfirm.dao;


import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrderItem;
import com.yhglobal.gongxiao.wmscomfirm.dao.mapper.OutboundOrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OutBoundOrderItemDao {
    @Autowired
    OutboundOrderItemMapper outboundOrderItemMapper;

    public OutboundOrderItem getOutboundOrderItemByItemNo(String outboundOrderItemNo,String projectPrefix) {
        return outboundOrderItemMapper.getOutboundOrderItemByItemNo(outboundOrderItemNo, projectPrefix);
    }

}
