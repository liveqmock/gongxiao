package com.yhglobal.gongxiao.payment.model;


import java.io.Serializable;
import java.util.Date;

/**
 * 现金台账
 *
 * @Author: 葛灿
 */
public class CashLedger implements Serializable, Cloneable {

    /**
     * 是否已取消
     */
    private boolean canceled;
    /**
     * 主键id
     */
    private Long ledgerId;
    /**
     * 收款确认流水号
     */
    private String flowNo;
    /**
     * 审批状态 (已审批、未审批)
     */
    private boolean approvalStatus;
    /**
     * 客户id
     */
    private Long distributorId;
    /**
     * 客户名称
     */
    private String distributorName;
    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 确认收款金额
     */
    private Long confirmAmount;
    /**
     * 确认收款金额
     */
    private String confirmAmountDouble;
    /**
     * 确认收款币种
     */
    private String confirmCurrency;
    /**
     * 实收金额
     */
    private Long recipientAmount;
    /**
     * 实收金额
     */
    private String recipientAmountDouble;
    /**
     * 实收币种
     */
    private String recipientCurrency;
    /**
     * 确认时间
     */
    private Date confirmTime;
    /**
     * 审批时间
     */
    private Date approveTime;
    /**
     * 审批人id
     */
    private Long approvalUserId;
    /**
     * 审批人
     */
    private String approvalUserName;
    /**
     * 收款时间
     */
    private Date receiveTime;
    /**
     * 收款方
     */
    private String recipient;
    /**
     * 收款银行
     */
    private String recipientBank;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 数据版本
     */
    private Long dataVersion;
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
    /**
     * 银行
     */
    private String bankName;
    /**
     * 操作日志
     */
    private String tracelog;
    /**
     * 银行流水号
     */
    private String bankFlowNo;

    public String getBankFlowNo() {
        return bankFlowNo;
    }

    public void setBankFlowNo(String bankFlowNo) {
        this.bankFlowNo = bankFlowNo;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Long getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Long ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public boolean isApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
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

    public Long getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(Long confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public String getConfirmAmountDouble() {
        return confirmAmountDouble;
    }

    public void setConfirmAmountDouble(String confirmAmountDouble) {
        this.confirmAmountDouble = confirmAmountDouble;
    }

    public String getConfirmCurrency() {
        return confirmCurrency;
    }

    public void setConfirmCurrency(String confirmCurrency) {
        this.confirmCurrency = confirmCurrency;
    }

    public Long getRecipientAmount() {
        return recipientAmount;
    }

    public void setRecipientAmount(Long recipientAmount) {
        this.recipientAmount = recipientAmount;
    }

    public String getRecipientAmountDouble() {
        return recipientAmountDouble;
    }

    public void setRecipientAmountDouble(String recipientAmountDouble) {
        this.recipientAmountDouble = recipientAmountDouble;
    }

    public String getRecipientCurrency() {
        return recipientCurrency;
    }

    public void setRecipientCurrency(String recipientCurrency) {
        this.recipientCurrency = recipientCurrency;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public Long getApprovalUserId() {
        return approvalUserId;
    }

    public void setApprovalUserId(Long approvalUserId) {
        this.approvalUserId = approvalUserId;
    }

    public String getApprovalUserName() {
        return approvalUserName;
    }

    public void setApprovalUserName(String approvalUserName) {
        this.approvalUserName = approvalUserName;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientBank() {
        return recipientBank;
    }

    public void setRecipientBank(String recipientBank) {
        this.recipientBank = recipientBank;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Override
    public CashLedger clone() throws CloneNotSupportedException {
        return (CashLedger) super.clone();
    }
}