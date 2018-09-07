package com.yhglobal.gongxiao.purchase.bo;

import java.io.Serializable;

/**
 * 货品信息
 *
 * @author: 陈浩
 * @create: 2018-03-15 15:07
 **/
public class ProductReturnInfo implements Serializable {
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
     * 币种code
     */
    private String currencyCode;
    /**
     * 币种名称
     */
    private String currencyName;
    /**
     * 品牌商ID
     */
    private String brandId;
    /**
     * 品牌商名称
     */
    private String brandName;
    /**
     * 指导价
     */
    private double guidePrice;
    /**
     * 采购价(进货价)
     */
    private double purchasePrice;
    /**
     * 退款金额
     */
    private double returnAmount;
    /**
     * 退货数量
     */
    private int returnNumber;
    /**
     * 退货原因
     */
    private String returnReason;
    /**
     * 可退数量
     */
    private int returnReferNumber;
    /**
     * 仓库实收
     */
    private String warehouseActualReceiptQuantity;

    public int getReturnReferNumber() {
        return returnReferNumber;
    }

    public void setReturnReferNumber(int returnReferNumber) {
        this.returnReferNumber = returnReferNumber;
    }

    public String getWarehouseActualReceiptQuantity() {
        return warehouseActualReceiptQuantity;
    }

    public void setWarehouseActualReceiptQuantity(String warehouseActualReceiptQuantity) {
        this.warehouseActualReceiptQuantity = warehouseActualReceiptQuantity;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public double getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(double guidePrice) {
        this.guidePrice = guidePrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public int getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(int returnNumber) {
        this.returnNumber = returnNumber;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }



}
