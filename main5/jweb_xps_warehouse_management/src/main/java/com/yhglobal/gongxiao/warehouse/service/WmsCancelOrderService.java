package com.yhglobal.gongxiao.warehouse.service;


import com.yhglobal.gongxiao.warehouse.model.dto.wms.cancel.WmsCancelOrderRequest;

/**
 * 取消订单接口
 * @author : liukai
 */
public interface WmsCancelOrderService {
    /**
     * 取消订单（入库单/出库单）
     * @param wmsCancelOrderRequest   orderType = 10表示入库单; orderType = 20表示出库单
     * @return
     */
    String cancelOrderService(WmsCancelOrderRequest wmsCancelOrderRequest, String wmsIp);
}
