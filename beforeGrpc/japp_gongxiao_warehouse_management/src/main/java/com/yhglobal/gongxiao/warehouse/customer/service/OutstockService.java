package com.yhglobal.gongxiao.warehouse.customer.service;

import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;

import java.util.List;

public interface OutstockService {

    void createInstockRecor(OutboundOrder outboundOrder, List<OutboundOrderItem> outboundOrderItemList);


}
