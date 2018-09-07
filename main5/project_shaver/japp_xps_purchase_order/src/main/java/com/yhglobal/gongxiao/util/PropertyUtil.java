package com.yhglobal.gongxiao.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Component
public class PropertyUtil {

    @Value("${warehouse.url}")
    private String warehouseUrl;

    @Value("${coupon.url}")
    private String couponUrl;

    @Value("${prepaid.url}")
    private String prepaidUrl;

    @Value("${channel}")
    private String channel;

    @Value("${targetProjectId}")
    private long targetProjectId;

    public long getTargetProjectId() {
        return targetProjectId;
    }

    public void setTargetProjectId(long targetProjectId) {
        this.targetProjectId = targetProjectId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getWarehouseUrl() {
        return warehouseUrl;
    }

    public void setWarehouseUrl(String warehouseUrl) {
        this.warehouseUrl = warehouseUrl;
    }

    public String getCouponUrl() {
        return couponUrl;
    }

    public void setCouponUrl(String couponUrl) {
        this.couponUrl = couponUrl;
    }

    public String getPrepaidUrl() {
        return prepaidUrl;
    }

    public void setPrepaidUrl(String prepaidUrl) {
        this.prepaidUrl = prepaidUrl;
    }
}
