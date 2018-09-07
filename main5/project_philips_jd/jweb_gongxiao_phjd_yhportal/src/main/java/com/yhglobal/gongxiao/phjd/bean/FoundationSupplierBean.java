package com.yhglobal.gongxiao.phjd.bean;

import java.io.Serializable;
import java.util.Date;

public class FoundationSupplierBean implements Serializable {
    private Long supplierId;

    private String supplierCode;

    private String supplierName;

    private String easSupplierCode;

    private String easSupplierName;

    private Byte recordStatus;

    private String address;

    private String email;

    private String contactName;

    private String countryCode;

    private String phoneNumber;

    private String currentProjectInfo;

    private String historyProjectInfo;

    private Long createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getEasSupplierCode() {
        return easSupplierCode;
    }

    public void setEasSupplierCode(String easSupplierCode) {
        this.easSupplierCode = easSupplierCode == null ? null : easSupplierCode.trim();
    }

    public String getEasSupplierName() {
        return easSupplierName;
    }

    public void setEasSupplierName(String easSupplierName) {
        this.easSupplierName = easSupplierName == null ? null : easSupplierName.trim();
    }

    public Byte getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Byte recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
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