package com.yhglobal.gongxiao.foundation.viewobject;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable {

    private Integer projectId;

    private String projectName;

    private String easProjectCode;

    private String easProjectName;

    private String orgCode;

    private String orgName;

    private Byte status;

    private Integer brandId;

    private String brandName;

    private Integer supplierId;

    private String supplierName;

    private String distributionInfo;

    private Integer prepaidUseReferRate;

    private Integer couponUseReferRate;

    private Integer monthlyCouponGenerateRate;

    private Integer quarterlyCouponGenerateRate;

    private Integer annualCouponGenerateRate;

    private Integer createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    /**
     *  新增字段 前返现金返利 和 后返现金返利
     * */
    private Integer cashRebateRateBefore;

    private Integer cashRebateRateAfter;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
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

    public Integer getPrepaidUseReferRate() {
        return prepaidUseReferRate;
    }

    public void setPrepaidUseReferRate(Integer prepaidUseReferRate) {
        this.prepaidUseReferRate = prepaidUseReferRate;
    }

    public Integer getCouponUseReferRate() {
        return couponUseReferRate;
    }

    public void setCouponUseReferRate(Integer couponUseReferRate) {
        this.couponUseReferRate = couponUseReferRate;
    }

    public Integer getMonthlyCouponGenerateRate() {
        return monthlyCouponGenerateRate;
    }

    public void setMonthlyCouponGenerateRate(Integer monthlyCouponGenerateRate) {
        this.monthlyCouponGenerateRate = monthlyCouponGenerateRate;
    }

    public Integer getQuarterlyCouponGenerateRate() {
        return quarterlyCouponGenerateRate;
    }

    public void setQuarterlyCouponGenerateRate(Integer quarterlyCouponGenerateRate) {
        this.quarterlyCouponGenerateRate = quarterlyCouponGenerateRate;
    }

    public Integer getAnnualCouponGenerateRate() {
        return annualCouponGenerateRate;
    }

    public void setAnnualCouponGenerateRate(Integer annualCouponGenerateRate) {
        this.annualCouponGenerateRate = annualCouponGenerateRate;
    }

    public Integer getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Integer createdById) {
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

    public Integer getCashRebateRateBefore() { return cashRebateRateBefore; }

    public void setCashRebateRateBefore(Integer cashRebateRateBefore) { this.cashRebateRateBefore = cashRebateRateBefore; }

    public Integer getCashRebateRateAfter() { return cashRebateRateAfter; }

    public void setCashRebateRateAfter(Integer cashRebateRateAfter) { this.cashRebateRateAfter = cashRebateRateAfter; }
}