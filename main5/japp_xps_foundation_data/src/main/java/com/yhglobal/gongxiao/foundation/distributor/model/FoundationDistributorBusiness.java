package com.yhglobal.gongxiao.foundation.distributor.model;

import java.util.Date;

public class FoundationDistributorBusiness {
    private Long distributorBusinessId;

    private String distributorCode;

    private Long distributorBasicId;

    private String distributorName;

    private Byte recordStatus;

    private String easCode;

    private String projectId;

    private String projectName;

    private Long distributorPurchaseCouponDiscount;

    private Long actualSaleReturn;

    private Long shouldSaleReturn;

    private Byte accordingRealInventorySale;

    private Byte settlementType;

    private Byte accountPeriod;

    private Long createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    public String getEasCode() {
        return easCode;
    }

    public void setEasCode(String easCode) {
        this.easCode = easCode;
    }

    public Long getDistributorBusinessId() {
        return distributorBusinessId;
    }

    public void setDistributorBusinessId(Long distributorBusinessId) {
        this.distributorBusinessId = distributorBusinessId;
    }

    public Long getDistributorBasicId() {
        return distributorBasicId;
    }

    public void setDistributorBasicId(Long distributorBasicId) {
        this.distributorBasicId = distributorBasicId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName == null ? null : distributorName.trim();
    }

    public Byte getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Byte recordStatus) {
        this.recordStatus = recordStatus;
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

    public Long getDistributorPurchaseCouponDiscount() {
        return distributorPurchaseCouponDiscount;
    }

    public void setDistributorPurchaseCouponDiscount(Long distributorPurchaseCouponDiscount) {
        this.distributorPurchaseCouponDiscount = distributorPurchaseCouponDiscount;
    }

    public Long getActualSaleReturn() {
        return actualSaleReturn;
    }

    public void setActualSaleReturn(Long actualSaleReturn) {
        this.actualSaleReturn = actualSaleReturn;
    }

    public Long getShouldSaleReturn() {
        return shouldSaleReturn;
    }

    public void setShouldSaleReturn(Long shouldSaleReturn) {
        this.shouldSaleReturn = shouldSaleReturn;
    }

    public Byte getAccordingRealInventorySale() {
        return accordingRealInventorySale;
    }

    public void setAccordingRealInventorySale(Byte accordingRealInventorySale) {
        this.accordingRealInventorySale = accordingRealInventorySale;
    }

    public Byte getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(Byte settlementType) {
        this.settlementType = settlementType;
    }

    public Byte getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(Byte accountPeriod) {
        this.accountPeriod = accountPeriod;
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

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }
}