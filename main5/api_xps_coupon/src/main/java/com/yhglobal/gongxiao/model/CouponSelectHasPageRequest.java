package com.yhglobal.gongxiao.model;

/**
 * @author 王帅
 */
public class CouponSelectHasPageRequest {

    private String channelId;
    private String channelToken;
    private String userId;
    private String userName;
   private Long projectId;
   private String purchaseOrderNo;
   private String supplierOrderNo;
   private String dateStart;
   private String dateEnd;
   private Byte[] couponStatus;
   private Byte[] couponType;
   private String flowNo;
   private Integer pageNumber;
   private Integer pageSize;

    public Byte[] getCouponType() {
        return couponType;
    }

    public void setCouponType(Byte[] couponType) {
        this.couponType = couponType;
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

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getSupplierOrderNo() {
        return supplierOrderNo;
    }

    public void setSupplierOrderNo(String supplierOrderNo) {
        this.supplierOrderNo = supplierOrderNo;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Byte[] getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Byte[] couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
