package com.yhglobal.gongxiao.coupon.model;

import java.io.Serializable;
import java.util.Date;

public class PrepaidReceivedAmountReport implements Serializable{
    private Long id;

    private Integer month;

    private Long projectId;

    private Date lastUpdateTime;

    private Long dataVersion;

    private Long toReceiveAmountTotal;

    private Long receiptAmountTotal;

    private Double rate;

    private String tablePrefix;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Long getToReceiveAmountTotal() {
        return toReceiveAmountTotal;
    }

    public void setToReceiveAmountTotal(Long toReceiveAmountTotal) {
        this.toReceiveAmountTotal = toReceiveAmountTotal;
    }

    public Long getReceiptAmountTotal() {
        return receiptAmountTotal;
    }

    public void setReceiptAmountTotal(Long receiptAmountTotal) {
        this.receiptAmountTotal = receiptAmountTotal;
    }
}