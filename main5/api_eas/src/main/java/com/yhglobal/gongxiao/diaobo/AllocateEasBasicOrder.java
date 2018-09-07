package com.yhglobal.gongxiao.diaobo;

import java.io.Serializable;

/**
 * 调拨单通知EAS
 */
public class AllocateEasBasicOrder implements Serializable {
    private String allocateNo;  //必传
    private String creatorID;    //必传
    private String createTime;   //必传
    private String lastUpdateTime;  //必传
    private String bizDate;  //必传
    private int hasEffected;      //必传
    private String bizType;   //必传
    private String issueCompanyorgUnitID;   //调出财务组织 必传
    private String receIptCompanyorgUnitID;   //调入财务组织 必传
    private String issueStorageorgUnitID;  //调出库存组织 必传
    private String issueAdminorgUnitID;
    private String receIptStorageorgUnitID; //调入库存组织 必传
    private int isShipment;        //必传
    private String modificationTime;  //必传
    private String currencyID;     //必传
    private String exchangeRate;   //必传
    private int isIntax;      //必传

    public String getAllocateNo() {
        return allocateNo;
    }

    public void setAllocateNo(String allocateNo) {
        this.allocateNo = allocateNo;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getBizDate() {
        return bizDate;
    }

    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
    }

    public int getHasEffected() {
        return hasEffected;
    }

    public void setHasEffected(int hasEffected) {
        this.hasEffected = hasEffected;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getIssueCompanyorgUnitID() {
        return issueCompanyorgUnitID;
    }

    public void setIssueCompanyorgUnitID(String issueCompanyorgUnitID) {
        this.issueCompanyorgUnitID = issueCompanyorgUnitID;
    }

    public String getReceIptCompanyorgUnitID() {
        return receIptCompanyorgUnitID;
    }

    public void setReceIptCompanyorgUnitID(String receIptCompanyorgUnitID) {
        this.receIptCompanyorgUnitID = receIptCompanyorgUnitID;
    }

    public String getIssueStorageorgUnitID() {
        return issueStorageorgUnitID;
    }

    public void setIssueStorageorgUnitID(String issueStorageorgUnitID) {
        this.issueStorageorgUnitID = issueStorageorgUnitID;
    }

    public String getIssueAdminorgUnitID() {
        return issueAdminorgUnitID;
    }

    public void setIssueAdminorgUnitID(String issueAdminorgUnitID) {
        this.issueAdminorgUnitID = issueAdminorgUnitID;
    }

    public String getReceIptStorageorgUnitID() {
        return receIptStorageorgUnitID;
    }

    public void setReceIptStorageorgUnitID(String receIptStorageorgUnitID) {
        this.receIptStorageorgUnitID = receIptStorageorgUnitID;
    }

    public int getIsShipment() {
        return isShipment;
    }

    public void setIsShipment(int isShipment) {
        this.isShipment = isShipment;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public int getIsIntax() {
        return isIntax;
    }

    public void setIsIntax(int isIntax) {
        this.isIntax = isIntax;
    }
}
