package com.yhglobal.gongxiao.grossProfit.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class YhglobalToReceiveGrossProfitLedgerWriteoffFlow implements Serializable {
    private Long flowId;

    private Long projectId;

    private String projectName;

    private BigDecimal amountBeforeTransaction;

    private BigDecimal transactionAmount;

    private BigDecimal amountAfterTransaction;

    private Date transactionTime;

    private String purchaseOrderId;

    private Integer supplierId;

    private String supplierName;

    private String extraInfo;

    private Byte statementCheckingFlag;

    private Date statementCheckingTime;

    private Date createTime;

    private String flowNo;

    private String currencyCode;

    private Integer flowType;
    private String salesOrderId;

    private String tablePrefix;
    private String transferPattern;
    private Long distributorId;
    private String distributorName;
    private String differenceAmountAdjustPattern;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getTransferPattern() {
        return transferPattern;
    }

    public void setTransferPattern(String transferPattern) {
        this.transferPattern = transferPattern;
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
        this.distributorName = distributorName;
    }

    public String getDifferenceAmountAdjustPattern() {
        return differenceAmountAdjustPattern;
    }

    public void setDifferenceAmountAdjustPattern(String differenceAmountAdjustPattern) {
        this.differenceAmountAdjustPattern = differenceAmountAdjustPattern;
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Integer getFlowType() {
        return flowType;
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
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

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId == null ? null : purchaseOrderId.trim();
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

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo == null ? null : extraInfo.trim();
    }

    public Byte getStatementCheckingFlag() {
        return statementCheckingFlag;
    }

    public void setStatementCheckingFlag(Byte statementCheckingFlag) {
        this.statementCheckingFlag = statementCheckingFlag;
    }

    public Date getStatementCheckingTime() {
        return statementCheckingTime;
    }

    public void setStatementCheckingTime(Date statementCheckingTime) {
        this.statementCheckingTime = statementCheckingTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }
}