package com.yhglobal.gongxiao.purchase.controller.vo;

import java.util.List;

/**
 * 新增采购,总计信息
 *
 * @author: 陈浩
 * @create: 2018-03-12 18:00
 **/
public class CalculateOrderInfo {
    /**
     * 返利余额
     */
    private String  couponRemainMoney;
    /**
     * 代垫余额
     */
    private String  prepaidRemainMoney;
    /**
     * 返利使用金额
     */
    private String couponAmount;
    /**
     * 代垫使用金额
     */
    private String prepaidAmount;
    /**
     * AD返利使用金额
     */
    private String adCouponAmount;
    /**
     * AD代垫使用金额
     */
    private String adPrepaidAmount;
    /**
     * 采购指导金额
     */
    private String purchaseGuideAmount;
    /**
     * 采购优惠金额
     */
    private String purchasePrivilegeAmount;
    /**
     * 现金返点金额
     */
    private String returnCash;
    /**
     * 采购应付金额
     */
    private String purchaseShouldPayAmount;
    /**
     * 采购实付金额
     */
    private String purchaseActualPayAmount;
    /**
     * 货品信息
     */
    private List<CalculateProductInfo> calculateProductInfoList;

    public String getAdCouponAmount() {
        return adCouponAmount;
    }

    public void setAdCouponAmount(String adCouponAmount) {
        this.adCouponAmount = adCouponAmount;
    }

    public String getAdPrepaidAmount() {
        return adPrepaidAmount;
    }

    public void setAdPrepaidAmount(String adPrepaidAmount) {
        this.adPrepaidAmount = adPrepaidAmount;
    }

    public List<CalculateProductInfo> getCalculateProductInfoList() {
        return calculateProductInfoList;
    }

    public void setCalculateProductInfoList(List<CalculateProductInfo> calculateProductInfoList) {
        this.calculateProductInfoList = calculateProductInfoList;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(String prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public String getPurchaseGuideAmount() {
        return purchaseGuideAmount;
    }

    public void setPurchaseGuideAmount(String purchaseGuideAmount) {
        this.purchaseGuideAmount = purchaseGuideAmount;
    }

    public String getPurchasePrivilegeAmount() {
        return purchasePrivilegeAmount;
    }

    public void setPurchasePrivilegeAmount(String purchasePrivilegeAmount) {
        this.purchasePrivilegeAmount = purchasePrivilegeAmount;
    }

    public String getReturnCash() {
        return returnCash;
    }

    public void setReturnCash(String returnCash) {
        this.returnCash = returnCash;
    }

    public String getPurchaseShouldPayAmount() {
        return purchaseShouldPayAmount;
    }

    public void setPurchaseShouldPayAmount(String purchaseShouldPayAmount) {
        this.purchaseShouldPayAmount = purchaseShouldPayAmount;
    }

    public String getPurchaseActualPayAmount() {
        return purchaseActualPayAmount;
    }

    public void setPurchaseActualPayAmount(String purchaseActualPayAmount) {
        this.purchaseActualPayAmount = purchaseActualPayAmount;
    }

    public String getCouponRemainMoney() {
        return couponRemainMoney;
    }

    public void setCouponRemainMoney(String couponRemainMoney) {
        this.couponRemainMoney = couponRemainMoney;
    }

    public String getPrepaidRemainMoney() {
        return prepaidRemainMoney;
    }

    public void setPrepaidRemainMoney(String prepaidRemainMoney) {
        this.prepaidRemainMoney = prepaidRemainMoney;
    }

    @Override
    public String toString() {
        return "CalculateOrderInfo{" +
                "couponRemainMoney='" + couponRemainMoney + '\'' +
                ", prepaidRemainMoney='" + prepaidRemainMoney + '\'' +
                ", couponAmount='" + couponAmount + '\'' +
                ", prepaidAmount='" + prepaidAmount + '\'' +
                ", purchaseGuideAmount='" + purchaseGuideAmount + '\'' +
                ", purchasePrivilegeAmount='" + purchasePrivilegeAmount + '\'' +
                ", returnCash='" + returnCash + '\'' +
                ", purchaseShouldPayAmount='" + purchaseShouldPayAmount + '\'' +
                ", purchaseActualPayAmount='" + purchaseActualPayAmount + '\'' +
                ", calculateProductInfoList=" + calculateProductInfoList +
                '}';
    }
}
