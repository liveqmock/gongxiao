package com.yhglobal.gongxiao.eas.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售订单货品信息
 *
 * @author: 陈浩
 **/
public class SaleOrderItem implements Serializable {

    private String customerId; //客户ID

    private int number; //销售数量

    private double taxPrice; //含税单价

    private double taxRate;  //税率

    private String warehouseId; //仓库编码

    private String materialId; //物料编码

    private String unit;    //计量单位

    private Date deliveryDate; //交货日期

    private Date sendDate;//发货日期

    private double discount;//折扣率

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}