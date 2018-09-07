package com.yhglobal.gongxiao.payment.model;

import java.util.Date;

public class SupplierSellHeightTransferRecord {
    private Long recordId;

    private String currencyCode;

    private Long amountBeforeTransaction;

    private Long transactionAmount;

    private Long amountAfterTransaction;

    private Date transferTime;

    private Long supplierId;

    private String supplierName;

    private Long createdById;

    private String createdByName;

    private Integer recordType;

    private Date createTime;

    private Long projectId;

    private String projectName;
    /**流水号*/
    private String flowNo;

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public Long getAmountBeforeTransaction() {
        return amountBeforeTransaction;
    }

    public void setAmountBeforeTransaction(Long amountBeforeTransaction) {
        this.amountBeforeTransaction = amountBeforeTransaction;
    }

    public Long getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Long getAmountAfterTransaction() {
        return amountAfterTransaction;
    }

    public void setAmountAfterTransaction(Long amountAfterTransaction) {
        this.amountAfterTransaction = amountAfterTransaction;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
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

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}