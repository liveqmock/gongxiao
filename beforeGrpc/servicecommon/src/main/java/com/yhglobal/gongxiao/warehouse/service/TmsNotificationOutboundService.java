package com.yhglobal.gongxiao.warehouse.service;


import com.yhglobal.gongxiao.warehousemanagement.dto.tms.TmsOutStockNotifyRequest;

/**
 * 通知tms出库
 * @author liukai
 */
public interface TmsNotificationOutboundService {
    /**
     *  通知TMS系统出库
     * @param tmsOutStockNotifyRequest  出库通知信息
     * @return
     */
    String notificationTmsOutboundInfo(TmsOutStockNotifyRequest tmsOutStockNotifyRequest, String wmsIp);

}
