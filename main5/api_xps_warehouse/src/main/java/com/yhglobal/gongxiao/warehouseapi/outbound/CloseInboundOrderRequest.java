package com.yhglobal.gongxiao.warehouseapi.outbound;

public class CloseInboundOrderRequest {

    private String channelId;

    private String channelToken;

    private String projectId;

    private String gongxiaoInboundOrderNo;

    private String productCodes;


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelToken() {
        return channelToken;
    }

    public void setChannelToken(String channelToken) {
        this.channelToken = channelToken;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getGongxiaoInboundOrderNo() {
        return gongxiaoInboundOrderNo;
    }

    public void setGongxiaoInboundOrderNo(String gongxiaoInboundOrderNo) {
        this.gongxiaoInboundOrderNo = gongxiaoInboundOrderNo;
    }

    public String getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(String productCodes) {
        this.productCodes = productCodes;
    }

}
