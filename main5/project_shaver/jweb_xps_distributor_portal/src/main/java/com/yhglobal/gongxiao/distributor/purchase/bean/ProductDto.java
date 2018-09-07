package com.yhglobal.gongxiao.distributor.purchase.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 10:56 2018/4/12
 */
public class ProductDto implements Serializable {
    private String productName;//商品名称
    private String productCode;//商品型号
    private int canBePurchasedPieces;//可采购件数
    private String currencyCode;//货币编码
    private Double listedPrice;//挂牌价
    private Double guidePrice;//指导价
    private Double discount;//折扣点 百分比
    private Double buyingPrice;//进货价
    private Date endTime;//有效日期止
    private String buyingPriceStr;
    private String discountStr;
    private String guidePriceStr;

    public String getBuyingPriceStr() {
        return buyingPriceStr;
    }

    public void setBuyingPriceStr(String buyingPriceStr) {
        this.buyingPriceStr = buyingPriceStr;
    }

    public String getDiscountStr() {
        return discountStr;
    }

    public void setDiscountStr(String discountStr) {
        this.discountStr = discountStr;
    }

    public String getGuidePriceStr() {
        return guidePriceStr;
    }

    public void setGuidePriceStr(String guidePriceStr) {
        this.guidePriceStr = guidePriceStr;
    }

    public Double getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(Double guidePrice) {
        this.guidePrice = guidePrice;
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

    public int getCanBePurchasedPieces() {
        return canBePurchasedPieces;
    }

    public void setCanBePurchasedPieces(int canBePurchasedPieces) {
        this.canBePurchasedPieces = canBePurchasedPieces;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(Double listedPrice) {
        this.listedPrice = listedPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getBuyingPrice() {
        return this.buyingPrice;
    }

    public void setBuyingPrice(Double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
