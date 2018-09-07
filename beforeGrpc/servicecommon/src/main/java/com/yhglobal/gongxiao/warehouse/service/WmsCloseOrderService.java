package com.yhglobal.gongxiao.warehouse.service;

import com.yhglobal.gongxiao.warehousemanagement.dto.wms.close.WmsCloseOrderRequest;

/**
 * wms关闭订单接口
 * @author liukai
 */
public interface WmsCloseOrderService {

    /**
     * 关闭订单
     * @param wmsCloseOrderRequest
     * @return
     */
    String closeOrderService(WmsCloseOrderRequest wmsCloseOrderRequest,String wmsIp);
}
