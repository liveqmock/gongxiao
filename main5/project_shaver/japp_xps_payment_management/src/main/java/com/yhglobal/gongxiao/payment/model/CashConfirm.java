package com.yhglobal.gongxiao.payment.model;


import java.io.Serializable;
import java.util.Date;

/**
 * 销售收款确认表
 *
 * @Author: 葛灿
 */
public class CashConfirm implements Serializable, Cloneable {

    /**
     * 是否取消
     */
    private boolean canceled;
    /**
     * 主键id
     */
    private Long confirmId;
    /**
     * 销售单号
     */
    private String salesOrderNo;
    /**
     * 流水号
     */
    private String flowNo;
    /**
     * 收款状态
     */
    private boolean recipientStatus;
    /**
     * 确认状态
     */
    private int confirmStatus;
    /**
     * 客户id
     */
    private long distributorId;
    /**
     * 客户名称
     */
    private String distributorName;
    /**
     * 项目id
     */
    private long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 应收币种
     */
    private String shouldReceiveCurrency;
    /**
     * 应收金额
     */
    private long shouldReceiveAmount;
    /**
     * 应收金额
     */
    private String shouldReceiveAmountDouble;
    /**
     * 未收币种
     */
    private String unreceiveCurrency;
    /**
     * 未收金额
     */
    private long unreceiveAmount;
    /**
     * 未收金额
     */
    private String unreceiveAmountDouble;
    /**
     * 确认收款金额
     */
    private long confirmAmount;
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
    private long recipientAmount;
    /**
     * 实收币种
     */
    private String recipientCurrency;
    /**
     * 实收金额
     */
    private String recipientAmountDouble;
    /**
     * 确认时间
     */
    private Date confirmTime;
    /**
     * 收款时间
     */
    private Date receiveTime;
    /**
     * 收款方
     */
    private String recipient;
    /**
     * 付款方
     */
    private String payer;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 数据版本
     */
    private long dataVersion;
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
    /**
     * 操作日志
     */
    private String tracelog;
    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 银行
     */
    private String bankName;
    /**
     * 银行流水号
     */
    private String bankFlowNo;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 备注
     */
    private String remark;


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBankFlowNo() {
        return bankFlowNo;
    }

    public void setBankFlowNo(String bankFlowNo) {
        this.bankFlowNo = bankFlowNo;
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

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getRecipientCurrency() {
        return recipientCurrency;
    }

    public void setRecipientCurrency(String recipientCurrency) {
        this.recipientCurrency = recipientCurrency;
    }

    public long getRecipientAmount() {
        return recipientAmount;
    }

    public void setRecipientAmount(long recipientAmount) {
        this.recipientAmount = recipientAmount;
    }

    public void setShouldReceiveAmountDouble(String shouldReceiveAmountDouble) {
        this.shouldReceiveAmountDouble = shouldReceiveAmountDouble;
    }

    public void setUnreceiveAmountDouble(String unreceiveAmountDouble) {
        this.unreceiveAmountDouble = unreceiveAmountDouble;
    }

    public void setConfirmAmountDouble(String confirmAmountDouble) {
        this.confirmAmountDouble = confirmAmountDouble;
    }

    public String getRecipientAmountDouble() {
        return recipientAmountDouble;
    }

    public void setRecipientAmountDouble(String recipientAmountDouble) {
        this.recipientAmountDouble = recipientAmountDouble;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Long getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(Long confirmId) {
        this.confirmId = confirmId;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public boolean isRecipientStatus() {
        return recipientStatus;
    }

    public void setRecipientStatus(boolean recipientStatus) {
        this.recipientStatus = recipientStatus;
    }

    public int getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(int confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(long distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getShouldReceiveCurrency() {
        return shouldReceiveCurrency;
    }

    public void setShouldReceiveCurrency(String shouldReceiveCurrency) {
        this.shouldReceiveCurrency = shouldReceiveCurrency;
    }

    public long getShouldReceiveAmount() {
        return shouldReceiveAmount;
    }

    public void setShouldReceiveAmount(long shouldReceiveAmount) {
        this.shouldReceiveAmount = shouldReceiveAmount;
    }

    public String getUnreceiveCurrency() {
        return unreceiveCurrency;
    }

    public void setUnreceiveCurrency(String unreceiveCurrency) {
        this.unreceiveCurrency = unreceiveCurrency;
    }

    public long getUnreceiveAmount() {
        return unreceiveAmount;
    }

    public void setUnreceiveAmount(long unreceiveAmount) {
        this.unreceiveAmount = unreceiveAmount;
    }

    public long getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(long confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public String getConfirmCurrency() {
        return confirmCurrency;
    }

    public void setConfirmCurrency(String confirmCurrency) {
        this.confirmCurrency = confirmCurrency;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
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

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
    }

    public String getShouldReceiveAmountDouble() {
        return shouldReceiveAmountDouble;
    }

    public String getUnreceiveAmountDouble() {
        return unreceiveAmountDouble;
    }

    public String getConfirmAmountDouble() {
        return confirmAmountDouble;
    }

    @Override
    public CashConfirm clone() throws CloneNotSupportedException {
        return (CashConfirm) super.clone();
    }
}