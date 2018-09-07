package com.yhglobal.gongxiao.phjd.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class JdQualityGuaranteeDeposit implements Serializable{
    private Long depositId;

    private Byte depositStatus;

    private Byte documentCode;

    private BigDecimal amount;

    private Date documentTime;

    private String buyerName;

    private Long projectId;

    private String projectName;

    private Long supplierId;

    private String supplierName;

    private String settlementNo;

    private Long createdById;

    private String createdByName;

    private Date createTime;

    private String remark;

    private Long jdProjectId;

    public Long getJdProjectId() {
        return jdProjectId;
    }

    public void setJdProjectId(Long jdProjectId) {
        this.jdProjectId = jdProjectId;
    }

    public Long getDepositId() {
        return depositId;
    }

    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }

    public Byte getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(Byte depositStatus) {
        this.depositStatus = depositStatus;
    }

    public Byte getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(Byte documentCode) {
        this.documentCode = documentCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDocumentTime() {
        return documentTime;
    }

    public void setDocumentTime(Date documentTime) {
        this.documentTime = documentTime;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName == null ? null : buyerName.trim();
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
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getSettlementNo() {
        return settlementNo;
    }

    public void setSettlementNo(String settlementNo) {
        this.settlementNo = settlementNo == null ? null : settlementNo.trim();
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
        this.createdByName = createdByName == null ? null : createdByName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}