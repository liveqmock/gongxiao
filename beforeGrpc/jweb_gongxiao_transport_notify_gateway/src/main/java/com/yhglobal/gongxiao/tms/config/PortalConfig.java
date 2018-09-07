package com.yhglobal.gongxiao.tms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PortalConfig {

    @Value("${portalId}")
    String portalId;

    @Value("${loginUrl}")
    String loginUrl; //WEB端登陆的URL

    public String getPortalId() {
        return portalId;
    }

    public void setPortalId(String portalId) {
        this.portalId = portalId;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

}
