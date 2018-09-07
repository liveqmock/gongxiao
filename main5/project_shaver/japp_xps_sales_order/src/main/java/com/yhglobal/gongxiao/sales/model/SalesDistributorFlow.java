package com.yhglobal.gongxiao.sales.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售过程中经销商发生的流水表
 *
 * @Author: 葛灿
 */
public class SalesDistributorFlow implements Serializable {

    /**
     * 流水id
     */
    private Long distributorFlowId;
    /**流水号*/
    private String flowNo;
    /**
     * 流水状态 1:待实际入账确认  9:已入账
     */
    private Integer flowStatus;
    /**
     * 经销商Id
     */
    private Long distributorId;
    /**
     * 经销商名称
     */
    private String distributorName;
    /**
     * 帐号类型 0:未定义 1:cash 2:coupon 3:prepaid 9:其它
     */
    private Integer accountType;
    /**
     * 流水类型 305:下单扣返利 306:供应商返利
     */
    private Integer flowType;
    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 交易金额（原金额乘以1000）
     */
    private Long transactionAmount;
    /**
     * 实际交易时间
     */
    private Date transactionTime;
    /**
     * 关联的销售订单id
     */
    private String salesOrderId;
    /**
     * 交易的其它信息(JSON格式)
     */
    private String extraInfo;
    /**
     * 入账的流水号
     */
    private String officialFlowNo;
    /**
     * 该流水是否已对账
     */
    private Integer statementCheckingFlag;
    /**
     * 该流水对账时间
     */
    private Date statementCheckingTime;
    /**
     * 创建时间
     */
    private Date createTime;

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public Long getDistributorFlowId() {
        return distributorFlowId;
    }

    public void setDistributorFlowId(Long distributorFlowId) {
        this.distributorFlowId = distributorFlowId;
    }

    public Integer getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
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

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
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

    public Long getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId == null ? null : salesOrderId.trim();
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo == null ? null : extraInfo.trim();
    }

    public String getOfficialFlowNo() {
        return officialFlowNo;
    }

    public void setOfficialFlowNo(String officialFlowNo) {
        this.officialFlowNo = officialFlowNo == null ? null : officialFlowNo.trim();
    }

    public Integer getStatementCheckingFlag() {
        return statementCheckingFlag;
    }

    public void setStatementCheckingFlag(Integer statementCheckingFlag) {
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
}