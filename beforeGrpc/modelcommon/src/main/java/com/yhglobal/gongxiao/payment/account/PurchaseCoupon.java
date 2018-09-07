//package com.yhglobal.gongxiao.payment.account;
//
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * 采购返利表
// *
// * @Author: 葛灿
// * @Date:2018/1/30--17:48
// */
//public class PurchaseCoupon implements Serializable {
//
//    /**
//     * Id
//     */
//    private Long couponId;
//    /**
//     * 返利状态 1:已发放未进入使用时间 2:可用 3:已使用 9:已过期
//     */
//    private Byte couponStatus;
//    /**
//     * 项目id
//     */
//    private Long projectId;
//    /**
//     * 项目名称
//     */
//    private String projectName;
//    /**
//     * 供应商Id
//     */
//    private Long supplierId;
//    /**
//     * 供应商名称
//     */
//    private String supplierName;
//    /**
//     * 返利金额
//     */
//    private Long couponAmount;
//    /**
//     * 有效期起
//     */
//    private Date startDate;
//    /**
//     * 有效期止
//     */
//    private Date endDate;
//    /**
//     * 发生返利关联的采购单号
//     */
//    private String sourcePurchaseOrderNumber;
//    /**
//     * 使用该返利的采购单号
//     */
//    private String targetPurchaseOrderNumber;
//    /**
//     * 数据版本
//     */
//    private Long dataVersion;
//    /**
//     * 创建时间
//     */
//    private Date createTime;
//    /**
//     * 最后更新时间
//     */
//    private Date lastUpdateTime;
//
//    public Long getCouponId() {
//        return couponId;
//    }
//
//    public void setCouponId(Long couponId) {
//        this.couponId = couponId;
//    }
//
//    public Byte getCouponStatus() {
//        return couponStatus;
//    }
//
//    public void setCouponStatus(Byte couponStatus) {
//        this.couponStatus = couponStatus;
//    }
//
//    public Long getProjectId() {
//        return projectId;
//    }
//
//    public void setProjectId(Long projectId) {
//        this.projectId = projectId;
//    }
//
//    public String getProjectName() {
//        return projectName;
//    }
//
//    public void setProjectName(String projectName) {
//        this.projectName = projectName == null ? null : projectName.trim();
//    }
//
//    public Long getSupplierId() {
//        return supplierId;
//    }
//
//    public void setSupplierId(Long supplierId) {
//        this.supplierId = supplierId;
//    }
//
//    public String getSupplierName() {
//        return supplierName;
//    }
//
//    public void setSupplierName(String supplierName) {
//        this.supplierName = supplierName == null ? null : supplierName.trim();
//    }
//
//    public Long getCouponAmount() {
//        return couponAmount;
//    }
//
//    public void setCouponAmount(Long couponAmount) {
//        this.couponAmount = couponAmount;
//    }
//
//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }
//
//    public String getSourcePurchaseOrderNumber() {
//        return sourcePurchaseOrderNumber;
//    }
//
//    public void setSourcePurchaseOrderNumber(String sourcePurchaseOrderNumber) {
//        this.sourcePurchaseOrderNumber = sourcePurchaseOrderNumber == null ? null : sourcePurchaseOrderNumber.trim();
//    }
//
//    public String getTargetPurchaseOrderNumber() {
//        return targetPurchaseOrderNumber;
//    }
//
//    public void setTargetPurchaseOrderNumber(String targetPurchaseOrderNumber) {
//        this.targetPurchaseOrderNumber = targetPurchaseOrderNumber == null ? null : targetPurchaseOrderNumber.trim();
//    }
//
//    public Long getDataVersion() {
//        return dataVersion;
//    }
//
//    public void setDataVersion(Long dataVersion) {
//        this.dataVersion = dataVersion;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getLastUpdateTime() {
//        return lastUpdateTime;
//    }
//
//    public void setLastUpdateTime(Date lastUpdateTime) {
//        this.lastUpdateTime = lastUpdateTime;
//    }
//}