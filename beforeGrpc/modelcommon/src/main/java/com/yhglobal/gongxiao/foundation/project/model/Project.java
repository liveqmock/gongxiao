package com.yhglobal.gongxiao.foundation.project.model;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable {
    private int projectId;

    private String projectName;

    private String easProjectCode;

    private String easProjectName;

    private String orgCode;

    private String orgName;

    private Byte status;

    private int brandId;

    private String brandName;

    private int supplierId;

    private String supplierName;

    private String distributionInfo;

    private int prepaidUseReferRate;

    private int couponUseReferRate;

    private int monthlyCouponGenerateRate;

    private int quarterlyCouponGenerateRate;

    private int annualCouponGenerateRate;

    private int createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    /**
     *  新增字段 前返现金返利 和 后返现金返利
     * */
    private int cashRebateRateBefore;

    private int cashRebateRateAfter;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getDistributionInfo() {
        return distributionInfo;
    }

    public void setDistributionInfo(String distributionInfo) {
        this.distributionInfo = distributionInfo == null ? null : distributionInfo.trim();
    }

    public int getPrepaidUseReferRate() {
        return prepaidUseReferRate;
    }

    public void setPrepaidUseReferRate(int prepaidUseReferRate) {
        this.prepaidUseReferRate = prepaidUseReferRate;
    }

    public int getCouponUseReferRate() {
        return couponUseReferRate;
    }

    public void setCouponUseReferRate(int couponUseReferRate) {
        this.couponUseReferRate = couponUseReferRate;
    }

    public int getMonthlyCouponGenerateRate() {
        return monthlyCouponGenerateRate;
    }

    public void setMonthlyCouponGenerateRate(int monthlyCouponGenerateRate) {
        this.monthlyCouponGenerateRate = monthlyCouponGenerateRate;
    }

    public int getQuarterlyCouponGenerateRate() {
        return quarterlyCouponGenerateRate;
    }

    public void setQuarterlyCouponGenerateRate(int quarterlyCouponGenerateRate) {
        this.quarterlyCouponGenerateRate = quarterlyCouponGenerateRate;
    }

    public int getAnnualCouponGenerateRate() {
        return annualCouponGenerateRate;
    }

    public void setAnnualCouponGenerateRate(int annualCouponGenerateRate) {
        this.annualCouponGenerateRate = annualCouponGenerateRate;
    }

    public int getCreatedById() {
        return createdById;
    }

    public void setCreatedById(int createdById) {
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

    public int getCashRebateRateBefore() { return cashRebateRateBefore; }

    public void setCashRebateRateBefore(int cashRebateRateBefore) { this.cashRebateRateBefore = cashRebateRateBefore; }

    public int getCashRebateRateAfter() { return cashRebateRateAfter; }

    public void setCashRebateRateAfter(int cashRebateRateAfter) { this.cashRebateRateAfter = cashRebateRateAfter; }
}