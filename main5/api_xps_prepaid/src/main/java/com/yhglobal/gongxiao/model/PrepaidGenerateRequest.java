package com.yhglobal.gongxiao.model;

import java.util.Date;

/**
 * 封装生成代垫的参数
 * @author 王帅
 */
public class PrepaidGenerateRequest {

    private String channelId;
    private String channelToken;
    private String userId;
    private String userName;
    private Long projectId ;
    private String currencyCode;
    private String orderId ;
    private String projectName ;
    private Integer supplierId;
    private String supplierName;
    private String salesContractNo ;
    private Long receiveAmount ;

    private Date outWarehouseDate;

    public Date getOutWarehouseDate() {
        return outWarehouseDate;
    }

    public void setOutWarehouseDate(Date outWarehouseDate) {
        this.outWarehouseDate = outWarehouseDate;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSalesContractNo() {
        return salesContractNo;
    }

    public void setSalesContractNo(String salesContractNo) {
        this.salesContractNo = salesContractNo;
    }

    public Long getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Long receiveAmount) {
        this.receiveAmount = receiveAmount;
    }
}
