package com.yhglobal.gongxiao.purchase.bo;

import java.io.Serializable;

/**
 * 出库通知单回写
 *
 * @author: 陈浩
 **/
public class OutboundNotificationBackItem implements Serializable{
    /**
     * 业务发起方货明细D
     */
    private String businessItemId;
    /**
     * 货品ID
     */
    private String productCode;
    /**
     *本次出库商品数量
     */
    private int outboundQuantity;
    /**
     * 本次出库次品数量
     */
    private int imperfectQuantity;
    /**
     * 已签收数量
     */
    private int signedReceiptQuantity;

    public int getImperfectQuantity() {
        return imperfectQuantity;
    }

    public void setImperfectQuantity(int imperfectQuantity) {
        this.imperfectQuantity = imperfectQuantity;
    }

    public int getSignedReceiptQuantity() {
        return signedReceiptQuantity;
    }

    public void setSignedReceiptQuantity(int signedReceiptQuantity) {
        this.signedReceiptQuantity = signedReceiptQuantity;
    }

    public String getBusinessItemId() {
        return businessItemId;
    }

    public void setBusinessItemId(String businessItemId) {
        this.businessItemId = businessItemId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getOutboundQuantity() {
        return outboundQuantity;
    }

    public void setOutboundQuantity(int outboundQuantity) {
        this.outboundQuantity = outboundQuantity;
    }
}
