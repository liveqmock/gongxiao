package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.close.WmsCloseOrderRequest;
import com.yhglobal.gongxiao.warehouse.service.WmsCloseOrderService;
import com.yhglobal.gongxiao.warehouse.util.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 关闭订单实现类
 * @author liukai
 */
@Service
public class WmsCloseOrderServiceImpl implements WmsCloseOrderService {

    private static Logger logger = LoggerFactory.getLogger(WmsCancelOrderServiceImpl.class);

    public static final String GET_CANCEL_ORDER_INFO = "/api/services/app/order/CloseOrder";


    @Override
    public String closeOrderService(WmsCloseOrderRequest wmsCloseOrderRequest, String wmsIp){

        String closeUrl = wmsIp+GET_CANCEL_ORDER_INFO;
        logger.info("[IN][closeOrderService]: wmsCancelOrderRequest={}", JSON.toJSONString(wmsCloseOrderRequest));
        try {
            String result = HttpRequestUtil.sendPost(closeUrl, JSON.toJSONString(wmsCloseOrderRequest),"utf-8");
            logger.info("[OUT] get closeOrderService success: result={}", result);
            return result;
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            return null;
        }
    }
}
