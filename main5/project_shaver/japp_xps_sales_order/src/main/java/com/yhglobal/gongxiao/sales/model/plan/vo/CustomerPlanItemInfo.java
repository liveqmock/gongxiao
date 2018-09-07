package com.yhglobal.gongxiao.sales.model.plan.vo;

import java.io.Serializable;
import java.util.Date;

public class CustomerPlanItemInfo implements Serializable {

    private String productCode;

    private String productName;

    private int allocatedQuantity;

    private int saleOccupationQuantity;//销售占用

    private int soldQuantity; //已售数量

    private int couldBuyNumber;// 客户可下单数量

    private String currencyCode;

    private String guidePrice;

    private String brandPrepaidDiscount; //品牌商支持点

    private String yhPrepaidDiscount;//越海支持点

    private String wholesalePrice; //出货价

    private String recordStatus;

    private String salePlaneNo; //可售单号 管理的那个

    private String startDate;

    private String endDate;

    public int getCouldBuyNumber() {
        return couldBuyNumber;
    }

    public void setCouldBuyNumber(int couldBuyNumber) {
        this.couldBuyNumber = couldBuyNumber;
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

    public int getAllocatedQuantity() {
        return allocatedQuantity;
    }

    public void setAllocatedQuantity(int allocatedQuantity) {
        this.allocatedQuantity = allocatedQuantity;
    }

    public int getSaleOccupationQuantity() {
        return saleOccupationQuantity;
    }

    public void setSaleOccupationQuantity(int saleOccupationQuantity) {
        this.saleOccupationQuantity = saleOccupationQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(String guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getBrandPrepaidDiscount() {
        return brandPrepaidDiscount;
    }

    public void setBrandPrepaidDiscount(String brandPrepaidDiscount) {
        this.brandPrepaidDiscount = brandPrepaidDiscount;
    }

    public String getYhPrepaidDiscount() {
        return yhPrepaidDiscount;
    }

    public void setYhPrepaidDiscount(String yhPrepaidDiscount) {
        this.yhPrepaidDiscount = yhPrepaidDiscount;
    }

    public String getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(String wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getSalePlaneNo() {
        return salePlaneNo;
    }

    public void setSalePlaneNo(String salePlaneNo) {
        this.salePlaneNo = salePlaneNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
