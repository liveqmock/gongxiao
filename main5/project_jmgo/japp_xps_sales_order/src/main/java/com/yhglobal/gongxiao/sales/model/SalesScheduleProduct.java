package com.yhglobal.gongxiao.sales.model;

import java.io.Serializable;

/**
 * 销售预约货品信息
 *
 * @Author: 葛灿
 * @Date:2018/3/5--11:28
 */
public class SalesScheduleProduct implements Serializable {

    /**
     * 销售单号
     */
    private String salesOrderNo;
    /**
     * 销售货品id
     */
    private Long salesOrderItemId;
    /**
     * 货品名称
     */
    private String productName;
    /**
     * 货品编码
     */
    private String productCode;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 币种
     */
    private String currency;
    /**
     * 挂牌价
     */
    private int retailPrice;
    /**
     * 挂牌价
     */
    private double retailPriceDouble;
    /**
     * 出货价
     */
    private int wholesalePrice;
    /**
     * 出货价
     */
    private double wholesalePriceDouble;
    /**
     * 订单数量
     */
    private int totalQuantity;
    /**
     * 剩余发货数量
     */
    private int unhandledQuantity;
    /**
     * 仓库可出数量
     */
    private int warehouseQuantity;
    /**
     * 本次预约数量
     */
    private int scheduleQuantity;

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public double getRetailPriceDouble() {
        return retailPriceDouble;
    }

    public void setRetailPriceDouble(double retailPriceDouble) {
        this.retailPriceDouble = retailPriceDouble;
    }

    public double getWholesalePriceDouble() {
        return wholesalePriceDouble;
    }

    public void setWholesalePriceDouble(double wholesalePriceDouble) {
        this.wholesalePriceDouble = wholesalePriceDouble;
    }

    public int getScheduleQuantity() {
        return scheduleQuantity;
    }

    public void setScheduleQuantity(int scheduleQuantity) {
        this.scheduleQuantity = scheduleQuantity;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
    }

    public int getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(int wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getUnhandledQuantity() {
        return unhandledQuantity;
    }

    public void setUnhandledQuantity(int unhandledQuantity) {
        this.unhandledQuantity = unhandledQuantity;
    }

    public int getWarehouseQuantity() {
        return warehouseQuantity;
    }

    public void setWarehouseQuantity(int warehouseQuantity) {
        this.warehouseQuantity = warehouseQuantity;
    }

    public Long getSalesOrderItemId() {
        return salesOrderItemId;
    }

    public void setSalesOrderItemId(Long salesOrderItemId) {
        this.salesOrderItemId = salesOrderItemId;
    }
}
