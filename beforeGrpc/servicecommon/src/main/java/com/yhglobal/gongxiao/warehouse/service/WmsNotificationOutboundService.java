package com.yhglobal.gongxiao.warehouse.service;

import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstock.WmsOutStockNotifyRequest;


/**
 * [与WMS系统交互] > wms出库通知接口
 * String notificationWmsInboundInfo(WmsOutStockNotifyRequest wmsOutStockNotify); 出库订单通知接口  (将出库订单推送给WMS)
 * String confirmWmsInboundInfo(WmsOutStockConfirmRequest wmsOutStockConfirm);  出库订单确认接口   (WMS将出库数据推送给分销系统)
 * @author : liukai
 */

public interface WmsNotificationOutboundService {

    /**
     *  通知WMS系统出库
     * @param wmsOutStockNotifyRequest  出库通知信息
     * @return
     */
    String notificationWmsOutboundInfo(WmsOutStockNotifyRequest wmsOutStockNotifyRequest,String wmsIp);


}
