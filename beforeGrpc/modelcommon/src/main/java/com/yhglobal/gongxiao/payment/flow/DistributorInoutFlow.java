package com.yhglobal.gongxiao.payment.flow;

import java.io.Serializable;
import java.util.Date;

/**
 * 经销商流水表
 *
 * @Author: 葛灿
 * @Date:2018/1/30--18:14
 */
public class DistributorInoutFlow implements Serializable {

    /**
     * 返利流水id
     */
    private Long flowId;
    /**
     * 流水号
     */
    private String flowNo;
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
    private int accountType;
    /**
     * 流水类型 305:下单扣返利 306:供应商返利
     */
    private int flowType;
    /**
     * 账号类型
     */
    private String accountTypeString;
    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 流水发生前的余额
     */
    private Long amountBeforeTransaction;
    /**
     * 交易金额（原金额乘以1000）
     */
    private Long transactionAmount;
    /**
     * 交易类型（支出/收入）
     */
    private String transactionType;
    /**
     * 交易金额
     */
    private double transactionAmountDouble;
    /**
     * 流水发生后的余额
     */
    private Long amountAfterTransaction;
    /**
     * 流水发生后的余额Double
     */
    private double amountAfterTransactionDouble;
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
     * 该流水是否已对账
     */
    private boolean statementCheckingFlag;
    /**
     * 该流水对账时间
     */
    private Date statementCheckingTime;
    /**
     * 创建时间
     */
    private Date createTime;

    public String getAccountTypeString() {
        return accountTypeString;
    }

    public void setAccountTypeString(String accountTypeString) {
        this.accountTypeString = accountTypeString;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getTransactionAmountDouble() {
        return transactionAmountDouble;
    }

    public void setTransactionAmountDouble(double transactionAmountDouble) {
        this.transactionAmountDouble = transactionAmountDouble;
    }

    public double getAmountAfterTransactionDouble() {
        return amountAfterTransactionDouble;
    }

    public void setAmountAfterTransactionDouble(double amountAfterTransactionDouble) {
        this.amountAfterTransactionDouble = amountAfterTransactionDouble;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
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

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getFlowType() {
        return flowType;
    }

    public void setFlowType(int flowType) {
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

    public boolean isStatementCheckingFlag() {
        return statementCheckingFlag;
    }

    public void setStatementCheckingFlag(boolean statementCheckingFlag) {
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
