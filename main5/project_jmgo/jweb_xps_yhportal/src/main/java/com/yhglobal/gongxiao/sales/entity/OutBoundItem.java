package com.yhglobal.gongxiao.sales.entity;


import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.utils.NumberFormat;

import java.io.Serializable;

/**
 * @Description: 出库单明细
 * @author: LUOYI
 * @Date: Created in 11:20 2018/3/30
 */
public class OutBoundItem implements Serializable {
    private String productCode;                     //商品编码
    private String productName;                     //商品名称
    private String productId;//商品ID
    private String productUnit; //商品单位
    private int outStockQuantity;                   //已出库的商品数量
    private int canReturnQuantity;                   //可退数量

    private Double guidePrice;//指导价
    private Double wholesalePrice;//出货价
    private String currencyCode;//货币编码
    private String currencyName;//货币名称
    private int inventoryType;

    public String getInventoryTypeStr() {
        return WmsInventoryType.getInventoryTypeByNumValue(this.inventoryType).getInventotyDesc();
    }

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getWholesalePriceStr() {
        return NumberFormat.format(this.wholesalePrice, "#,##0.000000");
    }

    public String getGuidePriceStr() {
        return NumberFormat.format(this.guidePrice, "#,##0.000000");
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
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

    public int getOutStockQuantity() {
        return outStockQuantity;
    }

    public void setOutStockQuantity(int outStockQuantity) {
        this.outStockQuantity = outStockQuantity;
    }

    public int getCanReturnQuantity() {
        return canReturnQuantity;
    }

    public void setCanReturnQuantity(int canReturnQuantity) {
        this.canReturnQuantity = canReturnQuantity;
    }

    public Double getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(Double guidePrice) {
        this.guidePrice = guidePrice;
    }

    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(Double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
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
}
