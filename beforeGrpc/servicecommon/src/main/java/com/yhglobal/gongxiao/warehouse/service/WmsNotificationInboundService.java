package com.yhglobal.gongxiao.warehouse.service;


import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instock.WmsInStockNotifyRequest;

/**
 *  [与WMS系统交互] > wms入库通知接口
 *  String notificationWmsInboundInfo(); 入库订单通知接口  (将入库订单推送给WMS)
 *  String confirmWmsInboundInfo(WmsInStockConfirmRequest wmsInStockConfirm);  入库订单确认接口   (WMS将收货数据推送给分销系统)
 *  @author : liukai
 */
public interface WmsNotificationInboundService {

    /**
     * 通知WMS系统收货,调用WMS的入库单通知接口
     * @param wmsInStockNotifyRequest   入库通知信息
     * @return
     */
    String notificationWmsInboundInfo(WmsInStockNotifyRequest wmsInStockNotifyRequest,String wmsIp);

}
