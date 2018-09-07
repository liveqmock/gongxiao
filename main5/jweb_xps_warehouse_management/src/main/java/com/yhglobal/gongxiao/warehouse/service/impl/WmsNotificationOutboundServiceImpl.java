package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.outstock.WmsOutStockNotifyRequest;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationOutboundService;
import com.yhglobal.gongxiao.warehouse.util.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *  [与WMS系统交互实现类] > 出库单通知
 *  @author : liukai
 */
@Service
public class WmsNotificationOutboundServiceImpl implements WmsNotificationOutboundService {

    private static Logger logger = LoggerFactory.getLogger(WmsNotificationOutboundServiceImpl.class);

    @Autowired
    OutBoundOrderDao outBoundOrderDao;


    public static final String STOCK_OUT_ORDER_NOTIFY = "/api/services/app/order/StockOutOrderNotify";

    @Override
    public String notificationWmsOutboundInfo(WmsOutStockNotifyRequest wmsOutStockNotifyRequest, String wmsIp){

        String wmsOutboundUrl = wmsIp+STOCK_OUT_ORDER_NOTIFY;
        logger.info("[IN][notificationWmsOutboundInfo]: wmsInStockNotify={},projectId={},traceNo={}",  JSON.toJSONString(wmsOutStockNotifyRequest));
        try {
            String result = HttpRequestUtil.sendPost(wmsOutboundUrl, JSON.toJSONString(wmsOutStockNotifyRequest),"utf-8");
            logger.info("返回报文：{}",result);
            logger.info("[OUT] get notificationWmsOutboundInfo success");
            return result;
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            return null;
        }
    }
}
