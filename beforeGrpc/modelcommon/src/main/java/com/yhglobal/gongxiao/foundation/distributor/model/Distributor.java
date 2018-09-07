package com.yhglobal.gongxiao.foundation.distributor.model;

import java.io.Serializable;
import java.util.Date;

public class Distributor implements Serializable{
    private Integer distributorId;

    private String distributorName;

    private String easCustomerCode;

    private String easCustomerName;

    private Byte status;

    private Long supplierId;

    private String supplierName;

    private String address;

    private String email;

    private String contactName;

    private String countryCode;

    private String phoneNumber;

    private Byte paymentMode;

    private Integer creditDays;

    private String taxNumber;

    private String bankInfo;

    private String currentProjectInfo;

    private String historyProjectInfo;

    private Long createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    public String getEasCustomerCode() {
        return easCustomerCode;
    }

    public void setEasCustomerCode(String easCustomerCode) {
        this.easCustomerCode = easCustomerCode;
    }

    public String getEasCustomerName() {
        return easCustomerName;
    }

    public void setEasCustomerName(String easCustomerName) {
        this.easCustomerName = easCustomerName;
    }

    public Byte getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Byte paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(Integer creditDays) {
        this.creditDays = creditDays;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName == null ? null : distributorName.trim();
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