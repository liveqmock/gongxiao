package com.yhglobal.gongxiao.transport.model;

import java.io.Serializable;

/**
 * 运输出库单
 *
 * @author 葛灿
 */
public class TransportationOutboundOrder implements Serializable {
    /**
     * 主键id
     */
    private long oid;
    /**
     * 出库单号
     */
    private String outboundOrderNo;
    /**
     * 频道id
     */
    private long channelId;
    /**
     * 频道令牌
     */
    private String channelToken;
    /**
     * 发送给tms的请求json
     */
    private String requestJson;

    private long dataVersion;

    private long projectId;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public String getOutboundOrderNo() {
        return outboundOrderNo;
    }

    public void setOutboundOrderNo(String outboundOrderNo) {
        this.outboundOrderNo = outboundOrderNo;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public String getChannelToken() {
        return channelToken;
    }

    public void setChannelToken(String channelToken) {
        this.channelToken = channelToken;
    }

    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }
}
