package com.yhglobal.gongxiao.eas;

import java.io.Serializable;

/**
 * 销售出库单明细
 *
 * @author: 陈浩
 **/
public class SaleOutOrderItem implements Serializable{

    private double taxRate; //税率

    private double taxPrice; //含税单价

    private int number; //销售出库数量

    private String warehouseId; //仓库ID;

    private String locationId; //库位

    private String lot; //批次

    private String sourceBillId; //来源单头据ID

    private String sourceOrderNumber;   //来源单数量

    private String sourceItemBillId; // 来源明细ID

    private String unit; //单位

    private String materialId; //物料ID

    private String customerId; //客户编码

    private double discount; //折扣率

    public String getSourceOrderNumber() {
        return sourceOrderNumber;
    }

    public void setSourceOrderNumber(String sourceOrderNumber) {
        this.sourceOrderNumber = sourceOrderNumber;
    }

    public String getSourceItemBillId() {
        return sourceItemBillId;
    }

    public void setSourceItemBillId(String sourceItemBillId) {
        this.sourceItemBillId = sourceItemBillId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getSourceBillId() {
        return sourceBillId;
    }

    public void setSourceBillId(String sourceBillId) {
        this.sourceBillId = sourceBillId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }
}
