package com.yhglobal.gongxiao.payment.model;

import java.util.Date;

public class DistributorPrepaidFlow {
    private Long flowId;

    private String flowNo;

    private Long distributorId;

    private String distributorName;

    private Integer flowType;

    private Long projectId;

    private String projectName;

    private String currencyCode;

    private Long amountBeforeTransaction;

    private Long transactionAmount;

    private Long amountAfterTransaction;

    private Date transactionTime;

    private String orderNo;

    private String extraInfo;

    private Byte statementCheckingFlag;

    private Date statementCheckingTime;

    private Date createTime;

    private Long createById;

    private String createByName;

    private String unfreezeFlowNo;

    private String bufferAccountFlowNo;

    private String sourceFlowNo;

    public String getSourceFlowNo() {
        return sourceFlowNo;
    }

    public void setSourceFlowNo(String sourceFlowNo) {
        this.sourceFlowNo = sourceFlowNo;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
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

    public Integer getFlowType() {
        return flowType;
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
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

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
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

    public Long getCreateById() {
        return createById;
    }

    public void setCreateById(Long createById) {
        this.createById = createById;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName == null ? null : createByName.trim();
    }

    public String getUnfreezeFlowNo() {
        return unfreezeFlowNo;
    }

    public void setUnfreezeFlowNo(String unfreezeFlowNo) {
        this.unfreezeFlowNo = unfreezeFlowNo == null ? null : unfreezeFlowNo.trim();
    }

    public String getBufferAccountFlowNo() {
        return bufferAccountFlowNo;
    }

    public void setBufferAccountFlowNo(String bufferAccountFlowNo) {
        this.bufferAccountFlowNo = bufferAccountFlowNo == null ? null : bufferAccountFlowNo.trim();
    }
}