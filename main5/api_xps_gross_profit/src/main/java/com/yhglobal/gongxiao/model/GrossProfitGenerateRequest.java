package com.yhglobal.gongxiao.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 封装生成返利的参数
 * @author 王帅
 */
public class GrossProfitGenerateRequest {

    private String channelId;
    private String channelToken;
    private String userId;
    private String userName;
    private Long projectId ;
    private String currencyCode;


    private String tablePrefix;

    private String projectName;
    private Byte confirmStatus;
    private String salesOrderNo;
    private BigDecimal estimatedGrossProfitAmount;
    private String purchaseOrderNo;
    private BigDecimal toBeConfirmAmount;
    private Date salesTime;
    private BigDecimal confirmedAmount;
    private BigDecimal receivedAmount;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelToken() {
        return channelToken;
    }

    public void setChannelToken(String channelToken) {
        this.channelToken = channelToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Byte getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Byte confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public BigDecimal getEstimatedGrossProfitAmount() {
        return estimatedGrossProfitAmount;
    }

    public void setEstimatedGrossProfitAmount(BigDecimal estimatedGrossProfitAmount) {
        this.estimatedGrossProfitAmount = estimatedGrossProfitAmount;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public BigDecimal getToBeConfirmAmount() {
        return toBeConfirmAmount;
    }

    public void setToBeConfirmAmount(BigDecimal toBeConfirmAmount) {
        this.toBeConfirmAmount = toBeConfirmAmount;
    }

    public Date getSalesTime() {
        return salesTime;
    }

    public void setSalesTime(Date salesTime) {
        this.salesTime = salesTime;
    }

    public BigDecimal getConfirmedAmount() {
        return confirmedAmount;
    }

    public void setConfirmedAmount(BigDecimal confirmedAmount) {
        this.confirmedAmount = confirmedAmount;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
    }
}
