package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationInboundService;
import com.yhglobal.gongxiao.warehouse.util.HttpRequestUtil;
import com.yhglobal.gongxiao.warehousemanagement.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instock.WmsInStockNotifyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * [与WMS系统交互实现类] > 入库单通知和确认
 * @author : liukai
 */
@Service
public class WmsNotificationInboundServiceImpl implements WmsNotificationInboundService {

    private static Logger logger = LoggerFactory.getLogger(WmsNotificationInboundServiceImpl.class);

    @Autowired
    InBoundOrderDao inBoundOrderDao;


    public static final String STOCK_IN_ORDER_NOTIFY = "/api/services/app/order/StockInOrderNotify";

    @Override
    public String notificationWmsInboundInfo(WmsInStockNotifyRequest wmsInStockNotifyRequest,String wmIp){

        String wmsInboundUrl = wmIp+STOCK_IN_ORDER_NOTIFY;
        logger.info("[IN][notificationWmsInboundInfo]: wmsInStockNotifyRequest={},projectId={},traceNo={}", JSON.toJSONString(wmsInStockNotifyRequest));
        try {
            String result = HttpRequestUtil.sendPost(wmsInboundUrl, wmsInStockNotifyRequest,"utf-8");

            logger.info("返回报文：{}",result);
            logger.info("[OUT] get notificationWmsInboundInfo success" );
            return result;
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            return null;
        }

    }

}
