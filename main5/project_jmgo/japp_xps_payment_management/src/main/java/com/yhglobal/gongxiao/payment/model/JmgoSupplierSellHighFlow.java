package com.yhglobal.gongxiao.payment.model;

import java.util.Date;

/**
 * 坚果高卖账户流水
 *
 * @author 葛灿
 */
public class JmgoSupplierSellHighFlow {
    /**主键id*/
    private Long recordId;
    /**货币编码*/
    private String currencyCode;
    /**交易前金额*/
    private Long amountBeforeTransaction;
    /**交易金额*/
    private Long transactionAmount;
    /**交易后金额*/
    private Long amountAfterTransaction;
    /**交易时间*/
    private Date transactionTime;
    /**项目id*/
    private Long projectId;
    /**项目名称*/
    private String projectName;
    /**经销商id*/
    private Long distributorId;
    /**经销商名称*/
    private String distributorName;
    /**创建人id*/
    private Long createdById;
    /**创建人名称*/
    private String createdByName;
    /**创建时间*/
    private Date createTime;
    /**流水类型*/
    private Integer flowType;
    /*** 流水号*/
    private String flowNo;
    /**备注*/
    private String remark;
    /**销售单号*/
    private String salesOrderNo;

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
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
        this.currencyCode = currencyCode;
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
        this.createdByName = createdByName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getFlowType() {
        return flowType;
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}