package com.yhglobal.gongxiao.transportation.model;

import java.util.Date;

/**
 * 取消的出库单
 *
 * @author 葛灿
 */
public class TransportationCancelledOutboundOrder {
    /**主键id*/
    private long oid;
    /**出库单号*/
    private String outboundOrderNo;
    /**请求json*/
    private String requestJson;
    /**同步TMS状态*/
    private long syncTmsStatus;
    /**数据版本*/
    private long dataVersion;
    /**TMS响应*/
    private String tmsResponse;

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

    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    public long getSyncTmsStatus() {
        return syncTmsStatus;
    }

    public void setSyncTmsStatus(long syncTmsStatus) {
        this.syncTmsStatus = syncTmsStatus;
    }

    public long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getTmsResponse() {
        return tmsResponse;
    }

    public void setTmsResponse(String tmsResponse) {
        this.tmsResponse = tmsResponse;
    }
}
