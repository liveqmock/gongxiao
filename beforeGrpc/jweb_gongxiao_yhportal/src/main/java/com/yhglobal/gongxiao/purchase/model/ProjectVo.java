package com.yhglobal.gongxiao.purchase.model;

import java.util.Date;

/**
 * 项目
 *
 * @author: 陈浩
 **/
public class ProjectVo {
    private int projectId;

    private String projectName;

    private Byte status;

    private int brandId;

    private String brandName;

    private int supplierId;

    private String supplierName;

    private String distributionInfo;

    private String prepaidUseReferRate;

    private String couponUseReferRate;

    private String monthlyCouponGenerateRate;

    private String quarterlyCouponGenerateRate;

    private String annualCouponGenerateRate;

    private String createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

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
        this.projectName = projectName;
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
        this.brandName = brandName;
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
        this.supplierName = supplierName;
    }

    public String getDistributionInfo() {
        return distributionInfo;
    }

    public void setDistributionInfo(String distributionInfo) {
        this.distributionInfo = distributionInfo;
    }

    public String getPrepaidUseReferRate() {
        return prepaidUseReferRate;
    }

    public void setPrepaidUseReferRate(String prepaidUseReferRate) {
        this.prepaidUseReferRate = prepaidUseReferRate;
    }

    public String getCouponUseReferRate() {
        return couponUseReferRate;
    }

    public void setCouponUseReferRate(String couponUseReferRate) {
        this.couponUseReferRate = couponUseReferRate;
    }

    public String getMonthlyCouponGenerateRate() {
        return monthlyCouponGenerateRate;
    }

    public void setMonthlyCouponGenerateRate(String monthlyCouponGenerateRate) {
        this.monthlyCouponGenerateRate = monthlyCouponGenerateRate;
    }

    public String getQuarterlyCouponGenerateRate() {
        return quarterlyCouponGenerateRate;
    }

    public void setQuarterlyCouponGenerateRate(String quarterlyCouponGenerateRate) {
        this.quarterlyCouponGenerateRate = quarterlyCouponGenerateRate;
    }

    public String getAnnualCouponGenerateRate() {
        return annualCouponGenerateRate;
    }

    public void setAnnualCouponGenerateRate(String annualCouponGenerateRate) {
        this.annualCouponGenerateRate = annualCouponGenerateRate;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
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
        this.tracelog = tracelog;
    }
}
