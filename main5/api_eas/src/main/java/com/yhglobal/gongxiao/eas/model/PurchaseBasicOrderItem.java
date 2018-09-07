package com.yhglobal.gongxiao.eas.model;

import java.io.Serializable;
import java.util.Date;

/**
 * eas需要的采购订单明细信息
 *
 * @author: 陈浩
 **/
public class PurchaseBasicOrderItem implements Serializable {

    private int number; //采购数量
    private double taxPrice;   //含税单价
    private double discountRate; //折扣率 默认为0
    private double taxRate; //税率
    private Date deliveryDate; //交货日期
    private String warehouseId;//eas仓库编号
    private Date bizDate; //业务日期
    private String materialId;
    private String unit;//计量单位
    private String productCode;//产品编码

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
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
