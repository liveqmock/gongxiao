package com.yhglobal.gongxiao.purchase.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建采购单需要的信息
 * 各种金额是从前台传过来的,仅具备参考意义,需要后台校验
 *
 * @author: 陈浩
 * @create: 2018-02-05 10:40
 **/
public class CreatePurchaseBasicInfo implements Serializable{
    private String purchaseOrderNo;
    private String userId;
    private String userName;
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 仓库ID
     */
    private String warehouseId;
    /**
     * 品牌商ID
     */
    private String brandId; //非必传
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 付款方式
     */
    private int paymentMode;
    /**
     * 物流方式 0,自提 1,第三方物流
     */
    private int logisticType;
//    /**
//     * 采购方式
//     */
//    private int purchaseModel;
    /**
     * 采购类型 0,普通采购 1货补 2正品采购
     */
    private int purchaseType;
    /**
     * 业务发生日期
     */
    private Date businessDate;
    /**
     * 要求到货日期
     */
    private Date requirArrivalDate;
    /**
     * 到货截止日期
     */
    private Date arrivalDeadline;
    /**
     * 品牌商订单号
     */
    private String brandOrderNo;
    /**
     * 合同参考号
     */
    private String contractReferenceOrderNo;
    /**
     * 备注
     */
    private String remark;
    /**
     * 采购种类数目
     */
    private int purchaseCategory;
    /**
     * 采购总数量
     */
    private int purchaseNumber;
    /**
     * 返利使用金额
     */
    private double couponAmountUse;
    /**
     * 代垫使用金额
     */
    private double prepaidAmountUse;
    /**
     * 现金使用金额
     */
    /*private double cashAmountUse;*/
    /**
     * 采购指导金额
     */
    private double purchaseGuideAmount;
    /**
     * 采购优惠金额
     */
    private double purchasePrivilegeAmount;
    /**
     * 现金返点金额
     */
    private double returnCash;
    /**
     * 采购应付金额
     */
    private double purchaseShouldPayAmount;
    /**
     * 采购实付金额
     */
    private double purchaseActualPayAmount;

    private double adCouponAmountUse;

    private double adPrepaidAmountUse;

    public double getAdCouponAmountUse() {
        return adCouponAmountUse;
    }

    public void setAdCouponAmountUse(double adCouponAmountUse) {
        this.adCouponAmountUse = adCouponAmountUse;
    }

    public double getAdPrepaidAmountUse() {
        return adPrepaidAmountUse;
    }

    public void setAdPrepaidAmountUse(double adPrepaidAmountUse) {
        this.adPrepaidAmountUse = adPrepaidAmountUse;
    }

    public String getBrandOrderNo() {
        return brandOrderNo;
    }

    public void setBrandOrderNo(String brandOrderNo) {
        this.brandOrderNo = brandOrderNo;
    }

    public String getContractReferenceOrderNo() {
        return contractReferenceOrderNo;
    }

    public void setContractReferenceOrderNo(String contractReferenceOrderNo) {
        this.contractReferenceOrderNo = contractReferenceOrderNo;
    }

    public int getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public int getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(int purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public int getLogisticType() {
        return logisticType;
    }

    public void setLogisticType(int logisticType) {
        this.logisticType = logisticType;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Date getRequirArrivalDate() {
        return requirArrivalDate;
    }

    public void setRequirArrivalDate(Date requirArrivalDate) {
        this.requirArrivalDate = requirArrivalDate;
    }

    public Date getArrivalDeadline() {
        return arrivalDeadline;
    }

    public void setArrivalDeadline(Date arrivalDeadline) {
        this.arrivalDeadline = arrivalDeadline;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getCouponAmountUse() {
        return couponAmountUse;
    }

    public void setCouponAmountUse(double couponAmountUse) {
        this.couponAmountUse = couponAmountUse;
    }

    public double getPrepaidAmountUse() {
        return prepaidAmountUse;
    }

    public void setPrepaidAmountUse(double prepaidAmountUse) {
        this.prepaidAmountUse = prepaidAmountUse;
    }

/*    public double getCashAmountUse() { return cashAmountUse; }

    public void setCashAmountUse(double cashAmountUse) { this.cashAmountUse = cashAmountUse; }*/

    public double getPurchaseGuideAmount() {
        return purchaseGuideAmount;
    }

    public void setPurchaseGuideAmount(double purchaseGuideAmount) {
        this.purchaseGuideAmount = purchaseGuideAmount;
    }

    public double getPurchasePrivilegeAmount() {
        return purchasePrivilegeAmount;
    }

    public void setPurchasePrivilegeAmount(double purchasePrivilegeAmount) {
        this.purchasePrivilegeAmount = purchasePrivilegeAmount;
    }

    public double getReturnCash() {
        return returnCash;
    }

    public void setReturnCash(double returnCash) {
        this.returnCash = returnCash;
    }

    public double getPurchaseShouldPayAmount() {
        return purchaseShouldPayAmount;
    }

    public void setPurchaseShouldPayAmount(double purchaseShouldPayAmount) {
        this.purchaseShouldPayAmount = purchaseShouldPayAmount;
    }

    public double getPurchaseActualPayAmount() {
        return purchaseActualPayAmount;
    }

    public void setPurchaseActualPayAmount(double purchaseActualPayAmount) {
        this.purchaseActualPayAmount = purchaseActualPayAmount;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
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

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }


}
