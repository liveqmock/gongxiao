package com.yhglobal.gongxiao.warehouse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WarehouseConfig {

    @Value("${wms.url}")
    private String wmsUrl;

    @Value("${warehouse.notify}")
    private String warehouseNtf;


    public String getWmsUrl() {
        return wmsUrl;
    }

    public void setWmsUrl(String wmsUrl) {
        this.wmsUrl = wmsUrl;
    }

    public String getWarehouseNtf() {
        return warehouseNtf;
    }

    public void setWarehouseNtf(String warehouseNtf) {
        this.warehouseNtf = warehouseNtf;
    }
}
