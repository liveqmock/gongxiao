package com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class TransferReceivedNotification {
    private String projectId;
    private String traceNo;
    private String gongxiaoInboundOrderNo;
    private String uniqueNumbe;
    private List<InboundNotificationBackItem> inboundNotificationBackItemList;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getGongxiaoInboundOrderNo() {
        return gongxiaoInboundOrderNo;
    }

    public void setGongxiaoInboundOrderNo(String gongxiaoInboundOrderNo) {
        this.gongxiaoInboundOrderNo = gongxiaoInboundOrderNo;
    }

    public String getUniqueNumbe() {
        return uniqueNumbe;
    }

    public void setUniqueNumbe(String uniqueNumbe) {
        this.uniqueNumbe = uniqueNumbe;
    }

    public List<InboundNotificationBackItem> getInboundNotificationBackItemList() {
        return inboundNotificationBackItemList;
    }

    public void setInboundNotificationBackItemList(List<InboundNotificationBackItem> inboundNotificationBackItemList) {
        this.inboundNotificationBackItemList = inboundNotificationBackItemList;
    }
}
