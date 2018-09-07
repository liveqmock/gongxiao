package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.cancel.WmsCancelOrderRequest;
import com.yhglobal.gongxiao.warehouse.service.WmsCancelOrderService;
import com.yhglobal.gongxiao.warehouse.util.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 取消订单接口实现类
 * @author : liukai
 */
@Service
public class WmsCancelOrderServiceImpl implements WmsCancelOrderService {

    private static Logger logger = LoggerFactory.getLogger(WmsCancelOrderServiceImpl.class);

    public static final String GET_CANCEL_ORDER_INFO = "/api/services/app/order/GetCancelOrderInfo";

    @Override
    public String cancelOrderService(WmsCancelOrderRequest wmsCancelOrderRequest, String wmsIp){

        String cancelOrderUrl = wmsIp+GET_CANCEL_ORDER_INFO;
        logger.info("[IN][cancelOrderService]: wmsCancelOrderRequest={}", JSON.toJSONString(wmsCancelOrderRequest));
        try {
            String result = HttpRequestUtil.sendPost(cancelOrderUrl, JSON.toJSONString(wmsCancelOrderRequest),"utf-8");
            logger.info("[OUT] get cancelOrderService success: result={}", result);
            return result;
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            return null;
        }
    }
}
