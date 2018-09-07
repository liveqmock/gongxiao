package com.yhglobal.gongxiao.phjd.sales.model.vo;

import java.io.Serializable;

/**
 @Author weizecheng
 @Date 2018/8/20 18:28
 **/
public class SalesOrderItemVO implements Serializable {

    private String currencyCode;
    private String currencyName;
    private String salesGuidePrice;
    private String wholesalePrice;

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

    public String getSalesGuidePrice() {
        return salesGuidePrice;
    }

    public void setSalesGuidePrice(String salesGuidePrice) {
        this.salesGuidePrice = salesGuidePrice;
    }

    public String getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(String wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SalesOrderItemVO{");
        sb.append("currencyCode='").append(currencyCode).append('\'');
        sb.append(", currencyName='").append(currencyName).append('\'');
        sb.append(", salesGuidePrice='").append(salesGuidePrice).append('\'');
        sb.append(", wholesalePrice='").append(wholesalePrice).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
