package com.yhglobal.gongxiao.grossProfit.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class YhglobalToReceiveGrossProfitLedger implements Serializable {
    private Long grossProfitId;

    private Byte confirmStatus;
    private String confirmStatusStr;

    private String salesOrderNo;

    private String purchaseOrderNo;

    private Date createTime;

    private BigDecimal estimatedGrossProfitAmount;

    private BigDecimal toBeConfirmAmount;

    private Long projectId;

    private String projectName;

    private Long supplierId;

    private String supplierName;

    private String currencyCode;

    private String supplierOrderNo;

    private Date salesTime;

    private BigDecimal confirmedAmount;

    private BigDecimal receivedAmount;

    private Long dataVersion;

    private Date lastUpdateTime;

    private String confirmRecord;

    private String tracelog;

    private String tablePrefix;

    public String getConfirmStatusStr() {
        return confirmStatusStr;
    }

    public void setConfirmStatusStr(String confirmStatusStr) {
        this.confirmStatusStr = confirmStatusStr;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public Long getGrossProfitId() {
        return grossProfitId;
    }

    public void setGrossProfitId(Long grossProfitId) {
        this.grossProfitId = grossProfitId;
    }

    public Byte getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Byte confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo == null ? null : salesOrderNo.trim();
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo == null ? null : purchaseOrderNo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getEstimatedGrossProfitAmount() {
        return estimatedGrossProfitAmount;
    }

    public void setEstimatedGrossProfitAmount(BigDecimal estimatedGrossProfitAmount) {
        this.estimatedGrossProfitAmount = estimatedGrossProfitAmount;
    }

    public BigDecimal getToBeConfirmAmount() {
        return toBeConfirmAmount;
    }

    public void setToBeConfirmAmount(BigDecimal toBeConfirmAmount) {
        this.toBeConfirmAmount = toBeConfirmAmount;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public String getSupplierOrderNo() {
        return supplierOrderNo;
    }

    public void setSupplierOrderNo(String supplierOrderNo) {
        this.supplierOrderNo = supplierOrderNo == null ? null : supplierOrderNo.trim();
    }

    public Date getSalesTime() {
        return salesTime;
    }

    public void setSalesTime(Date salesTime) {
        this.salesTime = salesTime;
    }

    public BigDecimal getConfirmedAmount() {
        return confirmedAmount;
    }

    public void setConfirmedAmount(BigDecimal confirmedAmount) {
        this.confirmedAmount = confirmedAmount;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getConfirmRecord() {
        return confirmRecord;
    }

    public void setConfirmRecord(String confirmRecord) {
        this.confirmRecord = confirmRecord == null ? null : confirmRecord.trim();
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }
}