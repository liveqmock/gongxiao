package com.yhglobal.gongxiao.purchase.controller.vo;

import java.io.Serializable;

/**
 * 创建采购单的货品信息
 *
 * @author: 陈浩
 * @create: 2018-02-05 11:06
 **/
public class CreatePurchaseItemInfo implements Serializable{
    /**
     * 货品ID
     */
    private String productId;
    /**
     * 货品编码
     */
    private String productCode;
    /**
     * 货品名称
     */
    private String productName;
    /**
     * 指导价
     */
    private double guidePrice;
    /**
     * 采购数量
     */
    private int purchaseNumber;
    /**
     * 采购折扣点
     */
    private double purchaseDiscount;
    /**
     * 使用返利
     */
    private double couponAmount;
    /**
     * 使用代垫
     */
    private double prepaidAmount;
    /**
     * 采购单价
     */
    private double purchasePrice;
    /**
     * 成本价
     */
    private double costPrice;

    private double factoryPrice;
    /**
     * 返利单价基数
     */
    private double couponBasePrice;

    private double priceDiscount;

    public double getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(double priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public double getCouponBasePrice() {
        return couponBasePrice;
    }

    public void setCouponBasePrice(double couponBasePrice) {
        this.couponBasePrice = couponBasePrice;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(double guidePrice) {
        this.guidePrice = guidePrice;
    }

    public double getPurchaseDiscount() {
        return purchaseDiscount;
    }

    public void setPurchaseDiscount(double purchaseDiscount) {
        this.purchaseDiscount = purchaseDiscount;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public double getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(double prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getFactoryPrice() {
        return factoryPrice;
    }

    public void setFactoryPrice(double factoryPrice) {
        this.factoryPrice = factoryPrice;
    }
}
