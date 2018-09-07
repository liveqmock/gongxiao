package com.yhglobal.gongxiao.purchase.bo;

import java.io.Serializable;

/**
 * 采购货品信息
 *
 * @author: 陈浩
 * @create: 2018-03-07 16:05
 **/
public class PurchaseItem implements Serializable {
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
    private Integer discountPercent;
    /**
     * 使用的返利金额
     */
    private Long couponAmount;
    /**
     * 使用的代垫金额
     */
    private Long prepaidAmount;
    /**
     * 指导价
     */
    private Long guidePrice;
    /**
     * 采购价
     */
    private Long purchasePrice;
    /**
     * 成本价
     */
    private Long costPrice;
    /**
     * 返利单价基数
     */
    private Long couponBasePrice;
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

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Long getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(Long prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public Long getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(Long guidePrice) {
        this.guidePrice = guidePrice;
    }

    public Long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getCouponBasePrice() {
        return couponBasePrice;
    }

    public void setCouponBasePrice(Long couponBasePrice) {
        this.couponBasePrice = couponBasePrice;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }
}
