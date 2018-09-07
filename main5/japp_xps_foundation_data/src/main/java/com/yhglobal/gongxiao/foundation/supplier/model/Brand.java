package com.yhglobal.gongxiao.foundation.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class Brand implements Serializable{
    private Integer brandId;

    private String brandName;

    private Byte status;

    private Integer supplierId;

    private String supplierName;

    private String currentProjectInfo;

    private String historyProjectInfo;

    private Long createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public String getCurrentProjectInfo() {
        return currentProjectInfo;
    }

    public void setCurrentProjectInfo(String currentProjectInfo) {
        this.currentProjectInfo = currentProjectInfo == null ? null : currentProjectInfo.trim();
    }

    public String getHistoryProjectInfo() {
        return historyProjectInfo;
    }

    public void setHistoryProjectInfo(String historyProjectInfo) {
        this.historyProjectInfo = historyProjectInfo == null ? null : historyProjectInfo.trim();
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