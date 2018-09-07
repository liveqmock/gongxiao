package com.yhglobal.gongxiao.eas;

import java.io.Serializable;

/**
 * 其他入货品信息
 *
 * @author: 陈浩
 **/
public class OtherInWareItem implements Serializable {

    private double taxPrice; //单价

    private double number;//数量

    private String warehouseId;//仓库

    private String locationId; //库位

    private String materialId;//物料号

    private String sourceBillId; //来源单据编号

    private String lot;//批次

    private String invUpdateType;//更新类型

    public String getInvUpdateType() {
        return invUpdateType;
    }

    public void setInvUpdateType(String invUpdateType) {
        this.invUpdateType = invUpdateType;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
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

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getSourceBillId() {
        return sourceBillId;
    }

    public void setSourceBillId(String sourceBillId) {
        this.sourceBillId = sourceBillId;
    }
}
