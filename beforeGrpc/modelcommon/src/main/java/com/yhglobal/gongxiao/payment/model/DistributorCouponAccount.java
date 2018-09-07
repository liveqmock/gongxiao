package com.yhglobal.gongxiao.payment.model;

import java.io.Serializable;
import java.util.Date;

public class DistributorCouponAccount implements Serializable {
    private Long rowId;

    private String currencyCode;

    private Long projectId;

    private String projectName;

    private Long distributorId;

    private String distributorName;

    private String clientAccount;

    private String clientName;

    private Byte status;

    private Long frozenAmount;

    private Long totalAmount;

    private Long dataVersion;

    private Date createTime;

    private Date lastUpdateTime;

    private String frozenInfo;

    private String tracelog;

    /**************
     * 以下为页面使用的字段
     **************/
    private double transferAmountDouble;

    public double getTransferAmountDouble() {
        return transferAmountDouble;
    }

    public void setTransferAmountDouble(double transferAmountDouble) {
        this.transferAmountDouble = transferAmountDouble;
    }

    public String getFrozenInfo() {
        return frozenInfo;
    }

    public void setFrozenInfo(String frozenInfo) {
        this.frozenInfo = frozenInfo == null ? null : frozenInfo.trim();
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
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

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName == null ? null : distributorName.trim();
    }

    public String getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(String clientAccount) {
        this.clientAccount = clientAccount == null ? null : clientAccount.trim();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(Long frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
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
}