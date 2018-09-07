package com.yhglobal.gongxiao.eas;

import java.io.Serializable;

/**
 * 其他出库货品信息
 *
 * @author: 陈浩
 **/
public class OtherOutWareItem implements Serializable {

    private double taxPrice; //单价

    private int number;//数量

    private String warehouseId; //仓库编码

    private String locationId; //库位

    private String lot;//批次

    private String materialId; //物料编号

    private String unit; //单位

    private String sourceBillId; //来源单据ID

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

    public String getSourceBillId() {
        return sourceBillId;
    }

    public void setSourceBillId(String sourceBillId) {
        this.sourceBillId = sourceBillId;
    }

}
