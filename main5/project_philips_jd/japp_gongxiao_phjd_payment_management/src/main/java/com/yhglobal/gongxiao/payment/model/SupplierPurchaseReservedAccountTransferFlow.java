package com.yhglobal.gongxiao.payment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierPurchaseReservedAccountTransferFlow implements Serializable{
    /**
     * 流水id
     */
    private Long flowId;

    /**
     * 该流水的流水号(用于返回给对应的供应商过账流水表和对应的buffer表)
     */
    private String flowNo;

    /**
     * 货币编码
     */
    private String currencyCode;

    /**
     * 转账前金额
     */
    private BigDecimal amountBeforeTransaction;

    /**
     * 过账金额
     */
    private BigDecimal transactionAmount;

    /**
     * 转账后金额
     */
    private BigDecimal amountAfterTransaction;

    /**
     * 过账时间
     */
    private Date transferTime;

    /**
     * 供应商Id
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 关联的项目id
     */
    private Long projectId;

    /**
     * 关联的项目名称
     */
    private String projectName;

    /**
     * 创建人id
     */
    private Long createdById;

    /**
     * 创建人名称
     */
    private String createdByName;

    /**
     * 关联的采购单号
     */
    private String purchaseOrderNo;

    /**
     * 流水类型 305:支出 306:转入
     */
    private Integer flowType;

    /**
     * 流水创建时间
     */
    private Date createTime;

    /**
     * 来源流水号
     */
    private String sourceFlowNo;

    /**
     * 备注信息
     */
    private String remark;
    private String tablePrefix;

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
        this.flowNo = flowNo;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public Integer getFlowType() {
        return flowType;
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSourceFlowNo() {
        return sourceFlowNo;
    }

    public void setSourceFlowNo(String sourceFlowNo) {
        this.sourceFlowNo = sourceFlowNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}