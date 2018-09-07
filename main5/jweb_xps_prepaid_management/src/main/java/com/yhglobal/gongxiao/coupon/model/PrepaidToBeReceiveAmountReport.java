package com.yhglobal.gongxiao.coupon.model;

import java.io.Serializable;
import java.util.Date;

public class PrepaidToBeReceiveAmountReport implements Serializable{
    private Long id;

    private Long timeTypeOne;

    private Long timeTypeTwo;

    private Long timeTypeThree;

    private Long timeTypeFour;

    private Long projectId;

    private Date lastUpdateTime;

    private Long dataVersion;
private String tablePrefix;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimeTypeOne() {
        return timeTypeOne;
    }

    public void setTimeTypeOne(Long timeTypeOne) {
        this.timeTypeOne = timeTypeOne;
    }

    public Long getTimeTypeTwo() {
        return timeTypeTwo;
    }

    public void setTimeTypeTwo(Long timeTypeTwo) {
        this.timeTypeTwo = timeTypeTwo;
    }

    public Long getTimeTypeThree() {
        return timeTypeThree;
    }

    public void setTimeTypeThree(Long timeTypeThree) {
        this.timeTypeThree = timeTypeThree;
    }

    public Long getTimeTypeFour() {
        return timeTypeFour;
    }

    public void setTimeTypeFour(Long timeTypeFour) {
        this.timeTypeFour = timeTypeFour;
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
}