package com.yhglobal.gongxiao.foundation.distributor.model;

import java.io.Serializable;
import java.util.Date;

public class DistributorCouponSetting implements Serializable {
    private Integer rowId;

    private Byte status;

    private String projectId;

    private String projectName;

    private Integer distributorId;

    private String distributorName;

    private Integer prepaidReferenceRate;

    private Integer prepaidCouponReferenceRate;

    private Date createTime;

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName == null ? null : distributorName.trim();
    }

    public Integer getPrepaidReferenceRate() {
        return prepaidReferenceRate;
    }

    public void setPrepaidReferenceRate(Integer prepaidReferenceRate) {
        this.prepaidReferenceRate = prepaidReferenceRate;
    }

    public Integer getPrepaidCouponReferenceRate() {
        return prepaidCouponReferenceRate;
    }

    public void setPrepaidCouponReferenceRate(Integer prepaidCouponReferenceRate) {
        this.prepaidCouponReferenceRate = prepaidCouponReferenceRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}