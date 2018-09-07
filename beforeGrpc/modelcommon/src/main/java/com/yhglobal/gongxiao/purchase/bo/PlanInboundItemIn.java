package com.yhglobal.gongxiao.purchase.bo;

/**
 * 计划入库单货品信息
 *
 * @author: 陈浩
 * @create: 2018-03-14 11:35
 **/
public class PlanInboundItemIn {
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
     * 本次预约数量
     */
    private int planInstockNumber;
    /**
     * 货币编码
     */
    private String currencyCode;
    /**
     * 货货币名称
     */
    private String currencyName;
    /**
     * 采购数量
     */
    private int purchaseNumber;

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

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
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

    public int getPlanInstockNumber() {
        return planInstockNumber;
    }

    public void setPlanInstockNumber(int planInstockNumber) {
        this.planInstockNumber = planInstockNumber;
    }
}
