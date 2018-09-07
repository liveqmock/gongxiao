package com.yhglobal.gongxiao.model;

/**
 * 封装生成返利的参数
 * @author 王帅
 */
public class CouponGenerateRequest {

    private String channelId;
    private String channelToken;
    private String userId;
    private String userName;
    private Long projectId ;
    private String currencyCode;
    private String brandOrderNo;
    private String purchaseOrderNo;
    private String purchaseTime ;
    private Long  purchaseShouldPayAmount;

   private Byte couponType ;
    private long couponRatio  ;
    private String projectName ;
    private long supplierId ;
    private String supplierName;

    private String tablePrefix;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
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

    public Byte getCouponType() {
        return couponType;
    }

    public void setCouponType(Byte couponType) {
        this.couponType = couponType;
    }

    public long getCouponRatio() {
        return couponRatio;
    }

    public void setCouponRatio(long couponRatio) {
        this.couponRatio = couponRatio;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getBrandOrderNo() {
        return brandOrderNo;
    }

    public void setBrandOrderNo(String brandOrderNo) {
        this.brandOrderNo = brandOrderNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Long getPurchaseShouldPayAmount() {
        return purchaseShouldPayAmount;
    }

    public void setPurchaseShouldPayAmount(Long purchaseShouldPayAmount) {
        this.purchaseShouldPayAmount = purchaseShouldPayAmount;
    }
}
