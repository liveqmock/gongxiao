package com.yhglobal.gongxiao.distributor.purchase.bean;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 18:34 2018/7/17
 */
public class ProductInfo {
    private String productCode;
    private String productName;
    private Long productId;

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
