package com.yhglobal.gongxiao.sales.model.cancel.dto;

import com.yhglobal.gongxiao.util.NumberFormat;

import java.io.Serializable;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 18:44 2018/3/30
 */
public class SalesReturnItem implements Serializable {
    private String productName;//货品名称
    private String productCode;//货品编码
    private String currencyCode;//货币编码
    private String currencyName;//货币名称
    private Double wholesalePrice;//出货价
    private Double returnAmount;//商品退款金额
    private int totalReturnedQuantity; //退货数量
    private int totalQuantity;//原始数量
    private int totalInStockQuantity;//仓库实收
    private String returnCause;//退货原因

    public String getWholesalePriceStr(){
        return NumberFormat.format(this.getWholesalePrice(),"#,##0.000000");
    }
    public String getReturnAmountStr(){
        return NumberFormat.format(this.getReturnAmount(),"#,##0.00");
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

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

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(Double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public int getTotalReturnedQuantity() {
        return totalReturnedQuantity;
    }

    public void setTotalReturnedQuantity(int totalReturnedQuantity) {
        this.totalReturnedQuantity = totalReturnedQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalInStockQuantity() {
        return totalInStockQuantity;
    }

    public void setTotalInStockQuantity(int totalInStockQuantity) {
        this.totalInStockQuantity = totalInStockQuantity;
    }

    public String getReturnCause() {
        return returnCause;
    }

    public void setReturnCause(String returnCause) {
        this.returnCause = returnCause;
    }
}
