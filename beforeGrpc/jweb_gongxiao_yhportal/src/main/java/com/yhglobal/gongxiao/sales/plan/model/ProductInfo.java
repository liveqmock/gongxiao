package com.yhglobal.gongxiao.sales.plan.model;

import java.io.Serializable;

/**
 * 产品信息服务类
 *
 * @author: 陈浩
 * @create: 2018-03-09 11:46
 **/
public class ProductInfo implements Serializable {
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
    private String guidePrice;
    /**
     * 销售指导价
     */
    private String saleGuidePrice;
    /**
     * 库存
     */
    private int inStockQuantity;

    public String getSaleGuidePrice() {
        return saleGuidePrice;
    }

    public void setSaleGuidePrice(String saleGuidePrice) {
        this.saleGuidePrice = saleGuidePrice;
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

    public String getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(String guidePrice) {
        this.guidePrice = guidePrice;
    }

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }
}
