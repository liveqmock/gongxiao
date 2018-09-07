package com.yhglobal.gongxiao.model;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class PurchaseItemVo implements Serializable {
    /**
     * 采购单货品id
     */
    private Long purchaseItemId;
    /**
     * 商品采购状态
     */
    private Byte orderStatus;
    /**
     * 采购单号
     */
    private String purchaseOrderNo;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品单位
     */
    private String productUnit;
    /**
     * 目标仓库id
     */
    private Integer warehouseId;
    /**
     * 目标仓库名称
     */
    private String warehouseName;
    /**
     * 物流方式 0:不详 1:自提 2:第三方物流
     */
    private Byte shippingMode;
    /**
     * 采购折扣
     */
    private String discountPercent;
    /**
     * 使用的返利金额
     */
    private String couponAmount;
    /**
     * 使用的代垫金额
     */
    private String prepaidAmount;
    /**
     * 指导价
     */
    private String guidePrice;
    /**
     * 采购价
     */
    private String purchasePrice;
    /**
     * 进货价
     */
    private String factoryPrice;
    /**
     * 成本价
     */
    private String costPrice;
    /**
     * 返利单价基数
     */
    private String couponBasePrice;
    /**
     * 商品采购数量
     */
    private Integer purchaseNumber;

    public Long getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(Long purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
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

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Byte getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(Byte shippingMode) {
        this.shippingMode = shippingMode;
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

    public String getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(String guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getFactoryPrice() {
        return factoryPrice;
    }

    public void setFactoryPrice(String factoryPrice) {
        this.factoryPrice = factoryPrice;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public String getCouponBasePrice() {
        return couponBasePrice;
    }

    public void setCouponBasePrice(String couponBasePrice) {
        this.couponBasePrice = couponBasePrice;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }
}
