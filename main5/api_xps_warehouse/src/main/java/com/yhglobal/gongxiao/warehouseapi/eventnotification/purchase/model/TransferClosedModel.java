package com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class TransferClosedModel {
    private String projectId;
    private String traceNo;
    private String gongxiaoInboundOrderNo;
    private String batchNo;
    private String uniqueNumbe;

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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getUniqueNumbe() {
        return uniqueNumbe;
    }

    public void setUniqueNumbe(String uniqueNumbe) {
        this.uniqueNumbe = uniqueNumbe;
    }
}
