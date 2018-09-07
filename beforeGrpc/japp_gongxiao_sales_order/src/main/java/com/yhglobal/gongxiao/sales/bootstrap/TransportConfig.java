package com.yhglobal.gongxiao.sales.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransportConfig {


    @Value("${tms.url}")
    private String tmsUrl;


    public String getTmsUrl() {
        return tmsUrl;
    }

    public void setTmsUrl(String tmsUrl) {
        this.tmsUrl = tmsUrl;
    }
}
