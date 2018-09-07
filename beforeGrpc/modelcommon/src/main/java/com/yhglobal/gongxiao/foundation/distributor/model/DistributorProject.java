package com.yhglobal.gongxiao.foundation.distributor.model;

import java.io.Serializable;
import java.util.Date;

public class DistributorProject implements Serializable {
    private Long distributorProjectId;

    private Long distributorBasicId;

    private Byte recordStatus;

    private String projectId;

    private String projectName;

    private Long distibutorPurchaseCouponDiscount;

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

    public Long getDistributorProjectId() {
        return distributorProjectId;
    }

    public void setDistributorProjectId(Long distributorProjectId) {
        this.distributorProjectId = distributorProjectId;
    }

    public Long getDistributorBasicId() {
        return distributorBasicId;
    }

    public void setDistributorBasicId(Long distributorBasicId) {
        this.distributorBasicId = distributorBasicId;
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

    public Long getDistibutorPurchaseCouponDiscount() {
        return distibutorPurchaseCouponDiscount;
    }

    public void setDistibutorPurchaseCouponDiscount(Long distibutorPurchaseCouponDiscount) {
        this.distibutorPurchaseCouponDiscount = distibutorPurchaseCouponDiscount;
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
}