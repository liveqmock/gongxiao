package com.yhglobal.gongxiao.diaobo;

import java.io.Serializable;

/**
 * EAS调拨入库单/EAS调拨出库单  单头
 */
public class AllocateStockEasOrder implements Serializable {
    private String wms;    //必传
    private String creatorID;    //必传
    private String bizType;    //必传
    private String bizDate;    //必传
    private String createTime;    //必传
    private String sourceBillID;    //必传
    private String transactionTypeID;    //必传
    private String issueStorageOrgUnitID;    //必传
    private String receiptStorageOrgUnitID;    //必传
    private String issueCompanyOrgUnitID;    //必传
    private String receiptCompanyOrgUnitID;    //必传
    private int totalQty;
    private double totalAmount;
    private String entrys;    //必传

    public String getWms() {
        return wms;
    }

    public void setWms(String wms) {
        this.wms = wms;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizDate() {
        return bizDate;
    }

    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSourceBillID() {
        return sourceBillID;
    }

    public void setSourceBillID(String sourceBillID) {
        this.sourceBillID = sourceBillID;
    }

    public String getTransactionTypeID() {
        return transactionTypeID;
    }

    public void setTransactionTypeID(String transactionTypeID) {
        this.transactionTypeID = transactionTypeID;
    }

    public String getIssueStorageOrgUnitID() {
        return issueStorageOrgUnitID;
    }

    public void setIssueStorageOrgUnitID(String issueStorageOrgUnitID) {
        this.issueStorageOrgUnitID = issueStorageOrgUnitID;
    }

    public String getReceiptStorageOrgUnitID() {
        return receiptStorageOrgUnitID;
    }

    public void setReceiptStorageOrgUnitID(String receiptStorageOrgUnitID) {
        this.receiptStorageOrgUnitID = receiptStorageOrgUnitID;
    }

    public String getIssueCompanyOrgUnitID() {
        return issueCompanyOrgUnitID;
    }

    public void setIssueCompanyOrgUnitID(String issueCompanyOrgUnitID) {
        this.issueCompanyOrgUnitID = issueCompanyOrgUnitID;
    }

    public String getReceiptCompanyOrgUnitID() {
        return receiptCompanyOrgUnitID;
    }

    public void setReceiptCompanyOrgUnitID(String receiptCompanyOrgUnitID) {
        this.receiptCompanyOrgUnitID = receiptCompanyOrgUnitID;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getEntrys() {
        return entrys;
    }

    public void setEntrys(String entrys) {
        this.entrys = entrys;
    }
}
