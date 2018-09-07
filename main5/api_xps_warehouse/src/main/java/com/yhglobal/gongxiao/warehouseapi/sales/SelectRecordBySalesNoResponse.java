package com.yhglobal.gongxiao.warehouseapi.sales;

import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrder;

import java.util.List;

/**
 * 根据销售单号查询出库单基本信息 响应
 */
public class SelectRecordBySalesNoResponse {
    List<OutboundOrder> outboundOrderList;

    public List<OutboundOrder> getOutboundOrderList() {
        return outboundOrderList;
    }

    public void setOutboundOrderList(List<OutboundOrder> outboundOrderList) {
        this.outboundOrderList = outboundOrderList;
    }
}
