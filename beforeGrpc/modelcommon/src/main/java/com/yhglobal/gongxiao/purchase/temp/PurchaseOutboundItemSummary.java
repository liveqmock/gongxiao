package com.yhglobal.gongxiao.purchase.temp;

/**
 * 采购方-预约记录,货品信息
 *
 * @author: 陈浩
 **/
public class PurchaseOutboundItemSummary {
    /**
     * 商品id
     */
    private String productCode;
    /**
     * 货品编码
     */
    private String currencyCode;
    /**
     * 某一种类的商品采购退货数量
     */
    private int purchaseOutboundQuantity;
    /**
     * 实际出库数量
     */
    private int actualOutboundQuantity;
    /**
     * 已签收数量
     */
    private int signedReceiptQuantity;
    /**
     * 预约状态
     */
    private byte orderStatus;


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getPurchaseOutboundQuantity() {
        return purchaseOutboundQuantity;
    }

    public void setPurchaseOutboundQuantity(int purchaseOutboundQuantity) {
        this.purchaseOutboundQuantity = purchaseOutboundQuantity;
    }

    public int getActualOutboundQuantity() {
        return actualOutboundQuantity;
    }

    public void setActualOutboundQuantity(int actualOutboundQuantity) {
        this.actualOutboundQuantity = actualOutboundQuantity;
    }

    public int getSignedReceiptQuantity() {
        return signedReceiptQuantity;
    }

    public void setSignedReceiptQuantity(int signedReceiptQuantity) {
        this.signedReceiptQuantity = signedReceiptQuantity;
    }

    public byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(byte orderStatus) {
        this.orderStatus = orderStatus;
    }
}
