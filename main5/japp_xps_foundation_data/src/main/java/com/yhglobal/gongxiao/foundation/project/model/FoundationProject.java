package com.yhglobal.gongxiao.foundation.project.model;

import java.util.Date;

public class FoundationProject {
    private Long projectId;

    private String projectCode;

    private String projectName;

    private String easProjectCode;

    private String easProjectName;

    private Byte recordStatus;

    private String company;

    private Long monthCouponRate;

    private Long quarterCouponRate;

    private Long annualCouponRate;

    private Long beforeCouponAmount;

    private Long afterCouponAmount;

    private String supplierId;

    private String supplierName;

    private Long createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    /**
     * 项目前缀
     */
    private String projectTablePrefix;

    private String wmsProjectCode;

    private String wmsProjectName;

    private String tmsProjectCode;

    private String tmsProjectName;

    public String getWmsProjectCode() {
        return wmsProjectCode;
    }

    public void setWmsProjectCode(String wmsProjectCode) {
        this.wmsProjectCode = wmsProjectCode;
    }

    public String getTmsProjectCode() {
        return tmsProjectCode;
    }

    public void setTmsProjectCode(String tmsProjectCode) {
        this.tmsProjectCode = tmsProjectCode;
    }

    public String getWmsProjectName() {
        return wmsProjectName;
    }

    public void setWmsProjectName(String wmsProjectName) {
        this.wmsProjectName = wmsProjectName;
    }

    public String getTmsProjectName() {
        return tmsProjectName;
    }

    public void setTmsProjectName(String tmsProjectName) {
        this.tmsProjectName = tmsProjectName;
    }

    public String getProjectTablePrefix() {
        return projectTablePrefix;
    }

    public void setProjectTablePrefix(String projectTablePrefix) {
        this.projectTablePrefix = projectTablePrefix;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode == null ? null : projectCode.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getEasProjectCode() {
        return easProjectCode;
    }

    public void setEasProjectCode(String easProjectCode) {
        this.easProjectCode = easProjectCode == null ? null : easProjectCode.trim();
    }

    public String getEasProjectName() {
        return easProjectName;
    }

    public void setEasProjectName(String easProjectName) {
        this.easProjectName = easProjectName == null ? null : easProjectName.trim();
    }

    public Byte getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Byte recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public Long getMonthCouponRate() {
        return monthCouponRate;
    }

    public void setMonthCouponRate(Long monthCouponRate) {
        this.monthCouponRate = monthCouponRate;
    }

    public Long getQuarterCouponRate() {
        return quarterCouponRate;
    }

    public void setQuarterCouponRate(Long quarterCouponRate) {
        this.quarterCouponRate = quarterCouponRate;
    }

    public Long getAnnualCouponRate() {
        return annualCouponRate;
    }

    public void setAnnualCouponRate(Long annualCouponRate) {
        this.annualCouponRate = annualCouponRate;
    }

    public Long getBeforeCouponAmount() {
        return beforeCouponAmount;
    }

    public void setBeforeCouponAmount(Long beforeCouponAmount) {
        this.beforeCouponAmount = beforeCouponAmount;
    }

    public Long getAfterCouponAmount() {
        return afterCouponAmount;
    }

    public void setAfterCouponAmount(Long afterCouponAmount) {
        this.afterCouponAmount = afterCouponAmount;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName == null ? null : createdByName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }
}