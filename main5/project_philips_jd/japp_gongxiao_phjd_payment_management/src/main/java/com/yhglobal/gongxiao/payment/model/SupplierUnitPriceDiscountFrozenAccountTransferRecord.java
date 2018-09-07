package com.yhglobal.gongxiao.payment.model;

import java.math.BigDecimal;
import java.util.Date;
/***
 * @author 王帅
 */
public class SupplierUnitPriceDiscountFrozenAccountTransferRecord {
    private Long recordId;

    private String currencyCode;

    private BigDecimal amountBeforeTransaction;

    private BigDecimal transactionAmount;

    private BigDecimal amountAfterTransaction;

    private Date transferTime;

    private Long supplierId;

    private String supplierName;

    private Long projectId;

    private String projectName;

    private Long createdById;

    private String createdByName;

    private String purchaseOrderNo;

    private String flowNo;

    private Integer recordType;

    private Date createTime;

    private String tablePrefix;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
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

    public BigDecimal getAmountBeforeTransaction() {
        return amountBeforeTransaction;
    }

    public void setAmountBeforeTransaction(BigDecimal amountBeforeTransaction) {
        this.amountBeforeTransaction = amountBeforeTransaction;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getAmountAfterTransaction() {
        return amountAfterTransaction;
    }

    public void setAmountAfterTransaction(BigDecimal amountAfterTransaction) {
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
        this.projectName = projectName == null ? null : projectName.trim();
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

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo == null ? null : purchaseOrderNo.trim();
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
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