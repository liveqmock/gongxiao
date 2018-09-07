package com.yhglobal.gongxiao.wmscomfirm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WarehouseConfig {

    @Value("${warehouse.url}")
    private String warehouseUrl;
    @Value("${channelId}")
    private String channelId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getWarehouseUrl() {
        return warehouseUrl;
    }

    public void setWarehouseUrl(String warehouseUrl) {
        this.warehouseUrl = warehouseUrl;
    }
}
