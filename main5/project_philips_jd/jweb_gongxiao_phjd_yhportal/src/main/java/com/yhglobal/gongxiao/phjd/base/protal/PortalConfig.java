package com.yhglobal.gongxiao.phjd.base.protal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PortalConfig {

    @Value("${portalId}")
    String portalId;

    @Value("${loginUrl}")
    String loginUrl; //WEB端登陆的URL

    @Value("${filePath}") //上传文件保存路径
    private String filePath;

    @Value("${xpsWarehouse.url}")
    private String warehouseUrl;

    @Value("${xpsTransportation.url}")
    private String transportationUrl;

    @Value("${xpsCoupon.url}")
    private String couponUrl;

    @Value("${xpsGrossProfit.url}")
    private String grossProfitUrl;

    @Value("${xpsPrepaid.url}")
    private String prepaidUrl;

    @Value("${xpsChannelId}")
    private String xpsChannelId;

    @Value("${targetProjectId}")
    private long targetProjectId;

    @Value("${contextPath}")
    private String contextPath;

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getWarehouseUrl() {
        return warehouseUrl;
    }

    public void setWarehouseUrl(String warehouseUrl) {
        this.warehouseUrl = warehouseUrl;
    }

    public String getTransportationUrl() {
        return transportationUrl;
    }

    public void setTransportationUrl(String transportationUrl) {
        this.transportationUrl = transportationUrl;
    }

    public String getCouponUrl() {
        return couponUrl;
    }

    public void setCouponUrl(String couponUrl) {
        this.couponUrl = couponUrl;
    }

    public String getGrossProfitUrl() {
        return grossProfitUrl;
    }

    public void setGrossProfitUrl(String grossProfitUrl) {
        this.grossProfitUrl = grossProfitUrl;
    }

    public String getPrepaidUrl() {
        return prepaidUrl;
    }

    public void setPrepaidUrl(String prepaidUrl) {
        this.prepaidUrl = prepaidUrl;
    }

    public String getXpsChannelId() {
        return xpsChannelId;
    }

    public void setXpsChannelId(String xpsChannelId) {
        this.xpsChannelId = xpsChannelId;
    }

    public long getTargetProjectId() {
        return targetProjectId;
    }

    public void setTargetProjectId(long targetProjectId) {
        this.targetProjectId = targetProjectId;
    }
}
