package com.yhglobal.gongxiao.purchase.controller.vo;

import java.io.Serializable;

/**
 * 计算货品信息
 *
 * @author: 陈浩
 * @create: 2018-03-01 10:23
 **/
public class CalculateProductInfo implements Serializable {
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
     * 品牌
     */
    private String brandId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 指导价
     */
    private String  guidePrice;
    /**
     * 采购数量
     */
    private int purchaseNumber;
    /**
     * 采购折扣点
     */
    private String discountPercent;
    /**
     * 使用返利
     */
    private String couponAmount;
    /**
     * 使用代垫
     */
    private String prepaidAmount;
    /**
     * 采购单价
     */
    private String purchasePrice;
    /**
     * 成本价
     */
    private String costPrice;
    /**
     * 进货价
     */
    private String factoryPrice;
    /**
     * 返利单价基数
     */
    private String  couponBasePrice;

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


    public String getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(String guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
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

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public String getCostPrice() {

        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getCouponBasePrice() {
        return couponBasePrice;
    }

    public void setCouponBasePrice(String couponBasePrice) {
        this.couponBasePrice = couponBasePrice;
    }



    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getFactoryPrice() {
        return factoryPrice;
    }

    public void setFactoryPrice(String factoryPrice) {
        this.factoryPrice = factoryPrice;
    }

    @Override
    public String toString() {
        return "CalculateProductInfo{" +
                "productID='" + productId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", brandId='" + brandId + '\'' +
                ", brandName='" + brandName + '\'' +
                ", guidePrice='" + guidePrice + '\'' +
                ", purchaseNumber=" + purchaseNumber +
                ", couponAmount='" + couponAmount + '\'' +
                ", prepaidAmount='" + prepaidAmount + '\'' +
                ", purchasePrice='" + purchasePrice + '\'' +
                ", couponBasePrice='" + couponBasePrice + '\'' +
                '}';
    }
}
