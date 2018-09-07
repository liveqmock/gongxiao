package com.yhglobal.gongxiao.transportation.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 葛灿
 */
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
