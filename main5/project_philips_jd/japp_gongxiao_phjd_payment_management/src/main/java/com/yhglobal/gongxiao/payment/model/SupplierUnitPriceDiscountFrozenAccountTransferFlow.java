package com.yhglobal.gongxiao.payment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierUnitPriceDiscountFrozenAccountTransferFlow implements Serializable{
    /**
     * 流水id
     */
    private Long flowId;

    /**
     * 该流水的流水号(用于返回给供应商过账流水表和对应的buffer表)
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

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    /**
     * 流水id
     * @return flowId 流水id
     */
    public Long getFlowId() {
        return flowId;
    }

    /**
     * 流水id
     * @param flowId 流水id
     */
    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    /**
     * 该流水的流水号(用于返回给供应商过账流水表和对应的buffer表)
     * @return flowNo 该流水的流水号(用于返回给供应商过账流水表和对应的buffer表)
     */
    public String getFlowNo() {
        return flowNo;
    }

    /**
     * 该流水的流水号(用于返回给供应商过账流水表和对应的buffer表)
     * @param flowNo 该流水的流水号(用于返回给供应商过账流水表和对应的buffer表)
     */
    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
    }

    /**
     * 货币编码
     * @return currencyCode 货币编码
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 货币编码
     * @param currencyCode 货币编码
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    /**
     * 转账前金额
     * @return amountBeforeTransaction 转账前金额
     */
    public BigDecimal getAmountBeforeTransaction() {
        return amountBeforeTransaction;
    }

    /**
     * 转账前金额
     * @param amountBeforeTransaction 转账前金额
     */
    public void setAmountBeforeTransaction(BigDecimal amountBeforeTransaction) {
        this.amountBeforeTransaction = amountBeforeTransaction;
    }

    /**
     * 过账金额
     * @return transactionAmount 过账金额
     */
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * 过账金额
     * @param transactionAmount 过账金额
     */
    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * 转账后金额
     * @return amountAfterTransaction 转账后金额
     */
    public BigDecimal getAmountAfterTransaction() {
        return amountAfterTransaction;
    }

    /**
     * 转账后金额
     * @param amountAfterTransaction 转账后金额
     */
    public void setAmountAfterTransaction(BigDecimal amountAfterTransaction) {
        this.amountAfterTransaction = amountAfterTransaction;
    }

    /**
     * 过账时间
     * @return transferTime 过账时间
     */
    public Date getTransferTime() {
        return transferTime;
    }

    /**
     * 过账时间
     * @param transferTime 过账时间
     */
    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
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
     * 关联的项目id
     * @return projectId 关联的项目id
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * 关联的项目id
     * @param projectId 关联的项目id
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * 关联的项目名称
     * @return projectName 关联的项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 关联的项目名称
     * @param projectName 关联的项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    /**
     * 创建人id
     * @return createdById 创建人id
     */
    public Long getCreatedById() {
        return createdById;
    }

    /**
     * 创建人id
     * @param createdById 创建人id
     */
    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    /**
     * 创建人名称
     * @return createdByName 创建人名称
     */
    public String getCreatedByName() {
        return createdByName;
    }

    /**
     * 创建人名称
     * @param createdByName 创建人名称
     */
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName == null ? null : createdByName.trim();
    }

    /**
     * 关联的采购单号
     * @return purchaseOrderNo 关联的采购单号
     */
    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    /**
     * 关联的采购单号
     * @param purchaseOrderNo 关联的采购单号
     */
    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo == null ? null : purchaseOrderNo.trim();
    }

    /**
     * 流水类型 305:支出 306:转入 
     * @return flowType 流水类型 305:支出 306:转入 
     */
    public Integer getFlowType() {
        return flowType;
    }

    /**
     * 流水类型 305:支出 306:转入 
     * @param flowType 流水类型 305:支出 306:转入 
     */
    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
    }

    /**
     * 流水创建时间
     * @return createTime 流水创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 流水创建时间
     * @param createTime 流水创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 来源流水号
     * @return sourceFlowNo 来源流水号
     */
    public String getSourceFlowNo() {
        return sourceFlowNo;
    }

    /**
     * 来源流水号
     * @param sourceFlowNo 来源流水号
     */
    public void setSourceFlowNo(String sourceFlowNo) {
        this.sourceFlowNo = sourceFlowNo == null ? null : sourceFlowNo.trim();
    }

    /**
     * 备注信息
     * @return remark 备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注信息
     * @param remark 备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}