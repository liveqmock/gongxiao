package com.yhglobal.gongxiao.warehouse.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WarehouseConfig {

    @Value("${wms.url}")
    private String wmsUrl;


    public String getWmsUrl() {
        return wmsUrl;
    }

    public void setWmsUrl(String wmsUrl) {
        this.wmsUrl = wmsUrl;
    }

}
