package com.yhglobal.gongxiao.phjd.model;

/**
 * purchaseOrder 表的 productInfo
 *
 * @author: 陈浩
 * @create: 2018-03-13 12:13
 **/
public class ProductItem {
    /**
     * 产品型号
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 采购数量
     */
    private int purchaseNumber;

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

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }
}
