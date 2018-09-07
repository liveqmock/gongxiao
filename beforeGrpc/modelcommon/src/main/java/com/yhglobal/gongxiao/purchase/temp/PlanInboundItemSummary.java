package com.yhglobal.gongxiao.purchase.temp;

import java.io.Serializable;

/**
 *  预约入库单货品信息摘要
 *
 * @author: 陈浩
 * @create: 2018-03-02 12:27
 **/
public class PlanInboundItemSummary implements Serializable {
    /**
     * 入采购单货品id
     */
    private long purchaseItemId;
    /**
     * 商品id
     */
    private String productId;

    private String productCode;
    /**
     * 货品编码
     */
    private String currencyCode;
    /**
     * 某一种类的商品预约入库数量
     */
    private int planInboundQuantity;
    /**
     * 实际入库数量
     */
    private int actualInboundQuantity;
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

    public byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(long purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getPlanInboundQuantity() {
        return planInboundQuantity;
    }

    public void setPlanInboundQuantity(int planInboundQuantity) {
        this.planInboundQuantity = planInboundQuantity;
    }

    public int getActualInboundQuantity() {
        return actualInboundQuantity;
    }

    public void setActualInboundQuantity(int actualInboundQuantity) {
        this.actualInboundQuantity = actualInboundQuantity;
    }
}
