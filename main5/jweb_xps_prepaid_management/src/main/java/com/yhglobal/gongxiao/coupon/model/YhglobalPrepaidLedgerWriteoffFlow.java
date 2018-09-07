package com.yhglobal.gongxiao.coupon.model;


import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 15:26 2018/4/27
 */
public class YhglobalPrepaidLedgerWriteoffFlow implements Serializable {
    String tablePrefix;
    private Long flowId;

    private String flowCode;

    private Long projectId;

    private String projectName;

    private Long amountBeforeTransaction;

    private Long transactionAmount;

    private Long amountAfterTransaction;

    private Date transactionTime;

    private Integer supplierId;

    private String supplierName;

    private String salesOrderId;

    private Long distributorId;

    private String distributorName;

    private String extraInfo;

    private Byte statementCheckingFlag;

    private Date statementCheckingTime;

    private Date createTime;

    private String transferPattern;

    private String differenceAmountAdjustPattern;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode == null ? null : flowCode.trim();
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

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
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

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId == null ? null : salesOrderId.trim();
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


    public String getTransferPattern() {
        return transferPattern;
    }

    public void setTransferPattern(String transferPattern) {
        this.transferPattern = transferPattern == null ? null : transferPattern.trim();
    }

    public String getDifferenceAmountAdjustPattern() {
        return differenceAmountAdjustPattern;
    }

    public void setDifferenceAmountAdjustPattern(String differenceAmountAdjustPattern) {
        this.differenceAmountAdjustPattern = differenceAmountAdjustPattern == null ? null : differenceAmountAdjustPattern.trim();
    }
}
