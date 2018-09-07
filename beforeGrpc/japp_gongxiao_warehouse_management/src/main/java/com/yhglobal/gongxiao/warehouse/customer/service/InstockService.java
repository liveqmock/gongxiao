package com.yhglobal.gongxiao.warehouse.customer.service;

import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrderItem;

import java.util.List;

public interface InstockService {

    void createInstockRecor(String orderSourceNo, InboundOrder newInbountOrder, List<InboundOrderItem> inboundOrderItemList);


}
