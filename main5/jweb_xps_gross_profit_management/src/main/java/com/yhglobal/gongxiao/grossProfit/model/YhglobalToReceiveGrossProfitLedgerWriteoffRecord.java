package com.yhglobal.gongxiao.grossProfit.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class YhglobalToReceiveGrossProfitLedgerWriteoffRecord implements Serializable {
    private Long confirmId;

    private Long grossProfitId;

    private BigDecimal confirmAmount;

    private BigDecimal receiptAmount;

    private String flowNo;

    private Long dataVersion;

    private Date createTime;
    private String createTimeStr;

    private Date lastUpdateTime;

    private String receivedCurrencyCode;

    private String confirmCurrencyCode;

    private Long createdById;

    private String createdByName;

    private Integer isRollback;

    private Long projectId;

    private Date useDate;
    private String useDateStr;

    private Byte transferIntoPattern;
    private String transferIntoPatternStr;

    private Byte differenceAmountAdjustPattern;

    private String remark;

    private String tablePrefix;

    private BigDecimal differenceAmount;

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUseDateStr() {
        return useDateStr;
    }

    public void setUseDateStr(String useDateStr) {
        this.useDateStr = useDateStr;
    }

    public BigDecimal getDifferenceAmount() {
        return differenceAmount;
    }

    public void setDifferenceAmount(BigDecimal differenceAmount) {
        this.differenceAmount = differenceAmount;
    }

    public String getTransferIntoPatternStr() {
        return transferIntoPatternStr;
    }

    public void setTransferIntoPatternStr(String transferIntoPatternStr) {
        this.transferIntoPatternStr = transferIntoPatternStr;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public Long getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(Long confirmId) {
        this.confirmId = confirmId;
    }

    public Long getGrossProfitId() {
        return grossProfitId;
    }

    public void setGrossProfitId(Long grossProfitId) {
        this.grossProfitId = grossProfitId;
    }

    public BigDecimal getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(BigDecimal confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public BigDecimal getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(BigDecimal receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
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

    public String getReceivedCurrencyCode() {
        return receivedCurrencyCode;
    }

    public void setReceivedCurrencyCode(String receivedCurrencyCode) {
        this.receivedCurrencyCode = receivedCurrencyCode == null ? null : receivedCurrencyCode.trim();
    }

    public String getConfirmCurrencyCode() {
        return confirmCurrencyCode;
    }

    public void setConfirmCurrencyCode(String confirmCurrencyCode) {
        this.confirmCurrencyCode = confirmCurrencyCode == null ? null : confirmCurrencyCode.trim();
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

    public Integer getIsRollback() {
        return isRollback;
    }

    public void setIsRollback(Integer isRollback) {
        this.isRollback = isRollback;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public Byte getTransferIntoPattern() {
        return transferIntoPattern;
    }

    public void setTransferIntoPattern(Byte transferIntoPattern) {
        this.transferIntoPattern = transferIntoPattern;
    }

    public Byte getDifferenceAmountAdjustPattern() {
        return differenceAmountAdjustPattern;
    }

    public void setDifferenceAmountAdjustPattern(Byte differenceAmountAdjustPattern) {
        this.differenceAmountAdjustPattern = differenceAmountAdjustPattern;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}