package com.yhglobal.gongxiao.purchase.model;

/**
 * 新增采购单 web传给后台的货品信息
 *
 * @author: 陈浩
 * @create: 2018-03-12 17:39
 **/
public class ProductItem {
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
     *采购折扣点
     */
    private double purchaseDiscount;
    /**
     * 输入返利金额
     */
    private double couponAmount;
    /**
     * 输入代垫金额
     */
    private double prepaidAmount;

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

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public double getPurchaseDiscount() {
        return purchaseDiscount;
    }

    public void setPurchaseDiscount(double purchaseDiscount) {
        this.purchaseDiscount = purchaseDiscount;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "productId='" + productId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", guidePrice=" + guidePrice +
                ", purchaseNumber=" + purchaseNumber +
                ", purchaseDiscount=" + purchaseDiscount +
                ", couponAmount=" + couponAmount +
                ", prepaidAmount=" + prepaidAmount +
                '}';
    }
}
