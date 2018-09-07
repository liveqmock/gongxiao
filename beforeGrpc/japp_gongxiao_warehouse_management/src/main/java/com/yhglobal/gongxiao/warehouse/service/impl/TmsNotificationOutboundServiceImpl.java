package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.warehouse.service.TmsNotificationOutboundService;
import com.yhglobal.gongxiao.warehouse.util.HttpRequestUtil;
import com.yhglobal.gongxiao.warehousemanagement.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.tms.TmsOutStockNotifyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 通知wms出库
 * @author liukai
 */
@Service
public class TmsNotificationOutboundServiceImpl implements TmsNotificationOutboundService {

    private static Logger logger = LoggerFactory.getLogger(TmsNotificationOutboundServiceImpl.class);

    public static final String STOCK_OUT_ORDER_NOTIFY = "/api/TmsApi/CreateDispatchOrder";

    @Autowired
    OutBoundOrderDao outBoundOrderDao;

    @Override
    public String notificationTmsOutboundInfo(TmsOutStockNotifyRequest tmsOutStockNotifyRequest, String tmsIp) {

        String tmsOutboundUrl = tmsIp+STOCK_OUT_ORDER_NOTIFY;
        logger.info("[IN][notificationTmsOutboundInfo]: tmsOutStockNotifyRequest={},tmsIp={}",  JSON.toJSONString(tmsOutStockNotifyRequest),tmsIp);
        try {
            String result = HttpRequestUtil.sendPost(tmsOutboundUrl, tmsOutStockNotifyRequest,"utf-8");
            logger.info("返回报文：{}",result);
            logger.info("[OUT] get notificationTmsOutboundInfo success");
            return result;
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            return null;
        }
    }
}
