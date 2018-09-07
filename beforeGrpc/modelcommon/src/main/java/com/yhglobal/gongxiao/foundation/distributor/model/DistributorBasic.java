package com.yhglobal.gongxiao.foundation.distributor.model;

import java.io.Serializable;
import java.util.Date;

public class DistributorBasic implements Serializable {
    private Long distributorBasicId;

    private String distributorCode;

    private String distributorName;

    private String easDistributorCode;

    private String easDistributorName;

    private Byte status;

    private Long supplierId;

    private String supplierName;

    private String email;

    private String contactNumber;

    private String contactName;

    private String businessAddress;

    private String countryCode;

    private String taxNumber;

    private String cnyBank;

    private String cnyAccount;

    private String bankHisInfo;

    private Long createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    public Long getDistributorBasicId() {
        return distributorBasicId;
    }

    public void setDistributorBasicId(Long distributorBasicId) {
        this.distributorBasicId = distributorBasicId;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode == null ? null : distributorCode.trim();
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName == null ? null : distributorName.trim();
    }

    public String getEasDistributorCode() {
        return easDistributorCode;
    }

    public void setEasDistributorCode(String easDistributorCode) {
        this.easDistributorCode = easDistributorCode == null ? null : easDistributorCode.trim();
    }

    public String getEasDistributorName() {
        return easDistributorName;
    }

    public void setEasDistributorName(String easDistributorName) {
        this.easDistributorName = easDistributorName == null ? null : easDistributorName.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber == null ? null : contactNumber.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress == null ? null : businessAddress.trim();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber == null ? null : taxNumber.trim();
    }

    public String getCnyBank() {
        return cnyBank;
    }

    public void setCnyBank(String cnyBank) {
        this.cnyBank = cnyBank == null ? null : cnyBank.trim();
    }

    public String getCnyAccount() {
        return cnyAccount;
    }

    public void setCnyAccount(String cnyAccount) {
        this.cnyAccount = cnyAccount == null ? null : cnyAccount.trim();
    }

    public String getBankHisInfo() {
        return bankHisInfo;
    }

    public void setBankHisInfo(String bankHisInfo) {
        this.bankHisInfo = bankHisInfo == null ? null : bankHisInfo.trim();
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