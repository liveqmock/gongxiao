package com.yhglobal.gongxiao.payment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class YhglobalToPayMoney implements Serializable{
    /**
     * 付款表id
     */
    private Long paymentId;

    /**
     * 确认状态 1:未付款 2:部分付款 3:已全部付款  9:已过期
     */
    private Byte confirmStatus;

    /**
     * 驳回状态 1未驳回: 2:已驳回
     */
    private Byte backStatus;

    /**
     * 付款申请单号
     */
    private String paymentApplyNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 计划付款时间
     */
    private Date estimatedPaymentTime;

    /**
     * 供应商Id
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 结算方式
     */
    private Byte settlementType;

    /**
     * 信用账期(当结算方式为信用结账时需要)
     */
    private Integer creditPaymentDays;

    /**
     * 付款方式
     */
    private Byte paymentType;

    /**
     * 银行承兑期(当付款方式为银行承兑时需要)
     */
    private Integer bankAcceptancePeriod;

    /**
     * 采购单号
     */
    private String purchaseNo;

    /**
     * 采购单日期
     */
    private Date orderTime;

    /**
     * 应收币种编码
     */
    private String currencyCode;

    /**
     * 应付金额
     */
    private BigDecimal receiveAmount;

    /**
     * 未付金额
     */
    private BigDecimal toBeConfirmAmount;

    /**
     * 确认金额
     */
    private BigDecimal confirmAmount;

    /**
     * 实付金额
     */
    private BigDecimal receiptAmount;

    /**
     * 收款方名称
     */
    private String receiverName;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 数据版本
     */
    private Long dataVersion;

    /**
     * 上次更新时间
     */
    private Date lastUpdateTime;

    /**
     * 确认记录
     */
    private String confirmRecord;

    /**
     * 操作日记
     */
    private String tracelog;

    /**
     * 付款表id
     * @return paymentId 付款表id
     */
    public Long getPaymentId() {
        return paymentId;
    }

    /**
     * 付款表id
     * @param paymentId 付款表id
     */
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * 确认状态 1:未付款 2:部分付款 3:已全部付款  9:已过期
     * @return confirmStatus 确认状态 1:未付款 2:部分付款 3:已全部付款  9:已过期
     */
    public Byte getConfirmStatus() {
        return confirmStatus;
    }

    /**
     * 确认状态 1:未付款 2:部分付款 3:已全部付款  9:已过期
     * @param confirmStatus 确认状态 1:未付款 2:部分付款 3:已全部付款  9:已过期
     */
    public void setConfirmStatus(Byte confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    /**
     * 驳回状态 1未驳回: 2:已驳回
     * @return backStatus 驳回状态 1未驳回: 2:已驳回
     */
    public Byte getBackStatus() {
        return backStatus;
    }

    /**
     * 驳回状态 1未驳回: 2:已驳回
     * @param backStatus 驳回状态 1未驳回: 2:已驳回
     */
    public void setBackStatus(Byte backStatus) {
        this.backStatus = backStatus;
    }

    /**
     * 付款申请单号
     * @return paymentApplyNo 付款申请单号
     */
    public String getPaymentApplyNo() {
        return paymentApplyNo;
    }

    /**
     * 付款申请单号
     * @param paymentApplyNo 付款申请单号
     */
    public void setPaymentApplyNo(String paymentApplyNo) {
        this.paymentApplyNo = paymentApplyNo == null ? null : paymentApplyNo.trim();
    }

    /**
     * 创建时间
     * @return createTime 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 计划付款时间
     * @return estimatedPaymentTime 计划付款时间
     */
    public Date getEstimatedPaymentTime() {
        return estimatedPaymentTime;
    }

    /**
     * 计划付款时间
     * @param estimatedPaymentTime 计划付款时间
     */
    public void setEstimatedPaymentTime(Date estimatedPaymentTime) {
        this.estimatedPaymentTime = estimatedPaymentTime;
    }

    /**
     * 供应商Id
     * @return supplierId 供应商Id
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 供应商Id
     * @param supplierId 供应商Id
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 供应商名称
     * @return supplierName 供应商名称
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 供应商名称
     * @param supplierName 供应商名称
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    /**
     * 结算方式
     * @return settlementType 结算方式
     */
    public Byte getSettlementType() {
        return settlementType;
    }

    /**
     * 结算方式
     * @param settlementType 结算方式
     */
    public void setSettlementType(Byte settlementType) {
        this.settlementType = settlementType;
    }

    /**
     * 信用账期(当结算方式为信用结账时需要)
     * @return creditPaymentDays 信用账期(当结算方式为信用结账时需要)
     */
    public Integer getCreditPaymentDays() {
        return creditPaymentDays;
    }

    /**
     * 信用账期(当结算方式为信用结账时需要)
     * @param creditPaymentDays 信用账期(当结算方式为信用结账时需要)
     */
    public void setCreditPaymentDays(Integer creditPaymentDays) {
        this.creditPaymentDays = creditPaymentDays;
    }

    /**
     * 付款方式
     * @return paymentType 付款方式
     */
    public Byte getPaymentType() {
        return paymentType;
    }

    /**
     * 付款方式
     * @param paymentType 付款方式
     */
    public void setPaymentType(Byte paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 银行承兑期(当付款方式为银行承兑时需要)
     * @return bankAcceptancePeriod 银行承兑期(当付款方式为银行承兑时需要)
     */
    public Integer getBankAcceptancePeriod() {
        return bankAcceptancePeriod;
    }

    /**
     * 银行承兑期(当付款方式为银行承兑时需要)
     * @param bankAcceptancePeriod 银行承兑期(当付款方式为银行承兑时需要)
     */
    public void setBankAcceptancePeriod(Integer bankAcceptancePeriod) {
        this.bankAcceptancePeriod = bankAcceptancePeriod;
    }

    /**
     * 采购单号
     * @return purchaseNo 采购单号
     */
    public String getPurchaseNo() {
        return purchaseNo;
    }

    /**
     * 采购单号
     * @param purchaseNo 采购单号
     */
    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo == null ? null : purchaseNo.trim();
    }

    /**
     * 采购单日期
     * @return orderTime 采购单日期
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * 采购单日期
     * @param orderTime 采购单日期
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * 应收币种编码
     * @return currencyCode 应收币种编码
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 应收币种编码
     * @param currencyCode 应收币种编码
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    /**
     * 应付金额
     * @return receiveAmount 应付金额
     */
    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    /**
     * 应付金额
     * @param receiveAmount 应付金额
     */
    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    /**
     * 未付金额
     * @return toBeConfirmAmount 未付金额
     */
    public BigDecimal getToBeConfirmAmount() {
        return toBeConfirmAmount;
    }

    /**
     * 未付金额
     * @param toBeConfirmAmount 未付金额
     */
    public void setToBeConfirmAmount(BigDecimal toBeConfirmAmount) {
        this.toBeConfirmAmount = toBeConfirmAmount;
    }

    /**
     * 确认金额
     * @return confirmAmount 确认金额
     */
    public BigDecimal getConfirmAmount() {
        return confirmAmount;
    }

    /**
     * 确认金额
     * @param confirmAmount 确认金额
     */
    public void setConfirmAmount(BigDecimal confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    /**
     * 实付金额
     * @return receiptAmount 实付金额
     */
    public BigDecimal getReceiptAmount() {
        return receiptAmount;
    }

    /**
     * 实付金额
     * @param receiptAmount 实付金额
     */
    public void setReceiptAmount(BigDecimal receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    /**
     * 收款方名称
     * @return receiverName 收款方名称
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * 收款方名称
     * @param receiverName 收款方名称
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    /**
     * 项目id
     * @return projectId 项目id
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * 项目id
     * @param projectId 项目id
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * 项目名称
     * @return projectName 项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 项目名称
     * @param projectName 项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    /**
     * 数据版本
     * @return dataVersion 数据版本
     */
    public Long getDataVersion() {
        return dataVersion;
    }

    /**
     * 数据版本
     * @param dataVersion 数据版本
     */
    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    /**
     * 上次更新时间
     * @return lastUpdateTime 上次更新时间
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 上次更新时间
     * @param lastUpdateTime 上次更新时间
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * 确认记录
     * @return confirmRecord 确认记录
     */
    public String getConfirmRecord() {
        return confirmRecord;
    }

    /**
     * 确认记录
     * @param confirmRecord 确认记录
     */
    public void setConfirmRecord(String confirmRecord) {
        this.confirmRecord = confirmRecord == null ? null : confirmRecord.trim();
    }

    /**
     * 操作日记
     * @return tracelog 操作日记
     */
    public String getTracelog() {
        return tracelog;
    }

    /**
     * 操作日记
     * @param tracelog 操作日记
     */
    public void setTracelog(String tracelog) {
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }
}