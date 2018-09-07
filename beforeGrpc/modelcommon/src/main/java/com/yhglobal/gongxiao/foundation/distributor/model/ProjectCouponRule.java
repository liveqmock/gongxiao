package com.yhglobal.gongxiao.foundation.distributor.model;

import java.io.Serializable;
import java.util.Date;

public class ProjectCouponRule implements Serializable {
    private Integer ruleId;

    private Byte status;

    private String projectId;

    private String projectName;

    private Byte couponType;

    private String couponCategoryName;

    private String couponName;

    private Integer couponRatio;

    private Date startDate;

    private Date endDate;

    private Date createTime;

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
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

    public Byte getCouponType() {
        return couponType;
    }

    public void setCouponType(Byte couponType) {
        this.couponType = couponType;
    }

    public String getCouponCategoryName() {
        return couponCategoryName;
    }

    public void setCouponCategoryName(String couponCategoryName) {
        this.couponCategoryName = couponCategoryName == null ? null : couponCategoryName.trim();
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName == null ? null : couponName.trim();
    }

    public Integer getCouponRatio() {
        return couponRatio;
    }

    public void setCouponRatio(Integer couponRatio) {
        this.couponRatio = couponRatio;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}