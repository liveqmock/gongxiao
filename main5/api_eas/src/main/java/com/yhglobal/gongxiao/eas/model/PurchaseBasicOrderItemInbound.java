package com.yhglobal.gongxiao.eas.model;

import java.io.Serializable;

/**
 * 采购入库单明细
 *
 * @author: 陈浩
 **/
public class PurchaseBasicOrderItemInbound implements Cloneable, Serializable {

    private double taxRate;     //税率
    private double taxPrice;    //含税单价(挂牌价)
    private String warehouseCode;//仓库编号
    private String locationId;   //库位
    private String lot;          //批次
    private int number;             //数量
    private String materialId;   //物料编号
    private String purchaseOrderId; //采购订单ID
    private String purchaseOrderEntryId; //采购单分录ID
    private String unit;
    private double discount; //折扣率

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getPurchaseOrderEntryId() {
        return purchaseOrderEntryId;
    }

    public void setPurchaseOrderEntryId(String purchaseOrderEntryId) {
        this.purchaseOrderEntryId = purchaseOrderEntryId;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
