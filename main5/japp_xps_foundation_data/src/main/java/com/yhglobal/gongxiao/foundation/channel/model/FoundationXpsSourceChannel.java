package com.yhglobal.gongxiao.foundation.channel.model;

public class FoundationXpsSourceChannel {
    private Integer rowId;

    private Short channelStatus;

    private Short deleteFlag;

    private Long xpsProjectId;

    private String xpsChannelId;

    private String xpsChannelSecret;

    private String xpsChannelName;

    private String wmsCustCode;

    private String wmsOrderSource;

    private String xpsWarehouseNotifyUrl;

    private String tmsCustomerCode;

    private String tmsCustomerName;

    private String tmsProjectName;

    private String xpsTransportationNotifyUrl;

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public Short getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(Short channelStatus) {
        this.channelStatus = channelStatus;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getXpsProjectId() {
        return xpsProjectId;
    }

    public void setXpsProjectId(Long xpsProjectId) {
        this.xpsProjectId = xpsProjectId;
    }

    public String getXpsChannelId() {
        return xpsChannelId;
    }

    public void setXpsChannelId(String xpsChannelId) {
        this.xpsChannelId = xpsChannelId == null ? null : xpsChannelId.trim();
    }

    public String getXpsChannelSecret() {
        return xpsChannelSecret;
    }

    public void setXpsChannelSecret(String xpsChannelSecret) {
        this.xpsChannelSecret = xpsChannelSecret == null ? null : xpsChannelSecret.trim();
    }

    public String getXpsChannelName() {
        return xpsChannelName;
    }

    public void setXpsChannelName(String xpsChannelName) {
        this.xpsChannelName = xpsChannelName == null ? null : xpsChannelName.trim();
    }

    public String getWmsCustCode() {
        return wmsCustCode;
    }

    public void setWmsCustCode(String wmsCustCode) {
        this.wmsCustCode = wmsCustCode == null ? null : wmsCustCode.trim();
    }

    public String getWmsOrderSource() {
        return wmsOrderSource;
    }

    public void setWmsOrderSource(String wmsOrderSource) {
        this.wmsOrderSource = wmsOrderSource == null ? null : wmsOrderSource.trim();
    }

    public String getXpsWarehouseNotifyUrl() {
        return xpsWarehouseNotifyUrl;
    }

    public void setXpsWarehouseNotifyUrl(String xpsWarehouseNotifyUrl) {
        this.xpsWarehouseNotifyUrl = xpsWarehouseNotifyUrl == null ? null : xpsWarehouseNotifyUrl.trim();
    }

    public String getTmsCustomerCode() {
        return tmsCustomerCode;
    }

    public void setTmsCustomerCode(String tmsCustomerCode) {
        this.tmsCustomerCode = tmsCustomerCode == null ? null : tmsCustomerCode.trim();
    }

    public String getTmsCustomerName() {
        return tmsCustomerName;
    }

    public void setTmsCustomerName(String tmsCustomerName) {
        this.tmsCustomerName = tmsCustomerName == null ? null : tmsCustomerName.trim();
    }

    public String getTmsProjectName() {
        return tmsProjectName;
    }

    public void setTmsProjectName(String tmsProjectName) {
        this.tmsProjectName = tmsProjectName == null ? null : tmsProjectName.trim();
    }

    public String getXpsTransportationNotifyUrl() {
        return xpsTransportationNotifyUrl;
    }

    public void setXpsTransportationNotifyUrl(String xpsTransportationNotifyUrl) {
        this.xpsTransportationNotifyUrl = xpsTransportationNotifyUrl == null ? null : xpsTransportationNotifyUrl.trim();
    }
}