package com.yhglobal.gongxiao.foundation.distributor.model;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class DistributorDetail implements Serializable {

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

    private Long distributorProjectId;

    private Byte recordStatus;

    private String projectId;

    private String projectName;

    private Long distibutorPurchaseCouponDiscount;

    private Long actualSaleReturn;

    private Long shouldSaleReturn;

    private Byte accordingRealInventorySale;

    private Byte settlementType;

    private Byte accountPeriod;

    private Long distributorUserId;

    private String account;

    private String password;

    private Byte priority;

    private String roleInfo;

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
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public Long getDistributorUserId() {
        return distributorUserId;
    }

    public void setDistributorUserId(Long distributorUserId) {
        this.distributorUserId = distributorUserId;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    public String getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(String roleInfo) {
        this.roleInfo = roleInfo;
    }

    public String getEasDistributorCode() {
        return easDistributorCode;
    }

    public void setEasDistributorCode(String easDistributorCode) {
        this.easDistributorCode = easDistributorCode;
    }

    public String getEasDistributorName() {
        return easDistributorName;
    }

    public void setEasDistributorName(String easDistributorName) {
        this.easDistributorName = easDistributorName;
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
        this.supplierName = supplierName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getCnyBank() {
        return cnyBank;
    }

    public void setCnyBank(String cnyBank) {
        this.cnyBank = cnyBank;
    }

    public String getCnyAccount() {
        return cnyAccount;
    }

    public void setCnyAccount(String cnyAccount) {
        this.cnyAccount = cnyAccount;
    }

    public String getBankHisInfo() {
        return bankHisInfo;
    }

    public void setBankHisInfo(String bankHisInfo) {
        this.bankHisInfo = bankHisInfo;
    }

    public Long getDistributorProjectId() {
        return distributorProjectId;
    }

    public void setDistributorProjectId(Long distributorProjectId) {
        this.distributorProjectId = distributorProjectId;
    }
}
