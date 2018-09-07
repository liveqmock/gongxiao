package com.yhglobal.gongxiao.warehousemanagement.bo;

import java.io.Serializable;

public class CreateNewInStockrder implements Serializable {
    /**
     * 货品id
     */
    private String productID;
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
    private int quantity;
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
    /**
     * 返利单价基数
     */
    private double couponBasePrice;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getCouponBasePrice() {
        return couponBasePrice;
    }

    public void setCouponBasePrice(double couponBasePrice) {
        this.couponBasePrice = couponBasePrice;
    }
}
