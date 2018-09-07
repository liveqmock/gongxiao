package com.yhglobal.gongxiao.sales.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author 葛灿
 */
@Component
public class SalesConfig implements Serializable {

    @Value("${xpsChannelId}")
    private String channelId;

    @Value("${xpsWarehouse.url}")
    private String warehouseUrl;

    @Value("${xpsTransportation.url}")
    private String transportationUrl;

    @Value("${xpsPrepaid.url}")
    private String prepaidUrl;

    @Value("${xpsGrossProfit.url}")
    private String grossProfitUrl;

    /**项目id 用于定时任务时获取*/
    @Value("${targetProjectId}")
    private long targetProjectId;

    public long getTargetProjectId() {
        return targetProjectId;
    }

    public void setTargetProjectId(long targetProjectId) {
        this.targetProjectId = targetProjectId;
    }

    public String getGrossProfitUrl() {
        return grossProfitUrl;
    }

    public void setGrossProfitUrl(String grossProfitUrl) {
        this.grossProfitUrl = grossProfitUrl;
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

    public String getPrepaidUrl() {
        return prepaidUrl;
    }

    public void setPrepaidUrl(String prepaidUrl) {
        this.prepaidUrl = prepaidUrl;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

}
