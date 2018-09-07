package com.yhglobal.gongxiao.distributor.purchase.bean;


import com.yhglobal.gongxiao.utils.NumberFormat;

import java.io.Serializable;

/**
 * 销售单货品详情
 *
 * @Author: 葛灿
 */
public class SalesOrderItemInfo implements Serializable {

    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 商品总件数
     */
    private Integer totalQuantity;
    /**
     * 货币id
     */
    private long currencyId;
    /**
     * 货币代码
     */
    private String currencyCode;
    /**
     * 货币名称
     */
    private String currencyName;
    /**
     * 指导价
     */
    private String guidePriceDouble;
    /**
     * 出货价
     */
    private String wholesalePriceDouble;
    /**
     * 供应商折扣
     */
    private String supplierDiscountPercentDouble;
    /**
     * 供应商支持金
     */
    private String supplierDiscountAmountDouble;
    /**
     * 越海折扣
     */
    private String yhDiscountPercentDouble;
    /**
     * 越海支持金
     */
    private String yhDiscountAmountDouble;
    /**
     * 返利金额
     */
    private String couponAmountDouble;
    /**
     * 代垫金额
     */
    private String prepaidAmountDouble;
    /**
     * 现金金额
     */
    private String cashAmountDouble;
    /**
     * 总金额(折扣后的价格)
     */
    private String totalOrderAmountDouble;

    /**
     * 箱装数
     */
    private double packageNumber;
    /**
     * 客户优惠金额
     */
    private double customerDiscountAmountDouble;
    private int deliveredQuantity;//实收数量
    private int sendQuantity;//发货数量

    public String getDiscountPercentStr(){
        return NumberFormat.format(Double.valueOf(this.getYhDiscountPercentDouble())+Double.valueOf(this.getSupplierDiscountPercentDouble()),"#,##0.0000%");
    }
    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }
    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public int getSendQuantity() {
        return sendQuantity;
    }

    public void setSendQuantity(int sendQuantity) {
        this.sendQuantity = sendQuantity;
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

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
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

    public String getGuidePriceDouble() {
        return guidePriceDouble;
    }

    public void setGuidePriceDouble(String guidePriceDouble) {
        this.guidePriceDouble = guidePriceDouble;
    }

    public String getWholesalePriceDouble() {
        return wholesalePriceDouble;
    }

    public void setWholesalePriceDouble(String wholesalePriceDouble) {
        this.wholesalePriceDouble = wholesalePriceDouble;
    }

    public String getSupplierDiscountPercentDouble() {
        return supplierDiscountPercentDouble;
    }

    public void setSupplierDiscountPercentDouble(String supplierDiscountPercentDouble) {
        this.supplierDiscountPercentDouble = supplierDiscountPercentDouble;
    }

    public String getSupplierDiscountAmountDouble() {
        return supplierDiscountAmountDouble;
    }

    public void setSupplierDiscountAmountDouble(String supplierDiscountAmountDouble) {
        this.supplierDiscountAmountDouble = supplierDiscountAmountDouble;
    }

    public String getYhDiscountPercentDouble() {
        return yhDiscountPercentDouble;
    }

    public void setYhDiscountPercentDouble(String yhDiscountPercentDouble) {
        this.yhDiscountPercentDouble = yhDiscountPercentDouble;
    }

    public String getYhDiscountAmountDouble() {
        return yhDiscountAmountDouble;
    }

    public void setYhDiscountAmountDouble(String yhDiscountAmountDouble) {
        this.yhDiscountAmountDouble = yhDiscountAmountDouble;
    }

    public String getCouponAmountDouble() {
        return couponAmountDouble;
    }

    public void setCouponAmountDouble(String couponAmountDouble) {
        this.couponAmountDouble = couponAmountDouble;
    }

    public String getPrepaidAmountDouble() {
        return prepaidAmountDouble;
    }

    public void setPrepaidAmountDouble(String prepaidAmountDouble) {
        this.prepaidAmountDouble = prepaidAmountDouble;
    }

    public String getCashAmountDouble() {
        return cashAmountDouble;
    }

    public void setCashAmountDouble(String cashAmountDouble) {
        this.cashAmountDouble = cashAmountDouble;
    }

    public String getTotalOrderAmountDouble() {
        return totalOrderAmountDouble;
    }

    public void setTotalOrderAmountDouble(String totalOrderAmountDouble) {
        this.totalOrderAmountDouble = totalOrderAmountDouble;
    }

    public double getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(double packageNumber) {
        this.packageNumber = packageNumber;
    }

    public double getCustomerDiscountAmountDouble() {
        return customerDiscountAmountDouble;
    }

    public void setCustomerDiscountAmountDouble(double customerDiscountAmountDouble) {
        this.customerDiscountAmountDouble = customerDiscountAmountDouble;
    }
}
