package com.yhglobal.gongxiao.diaobo;

import java.io.Serializable;

/**
 * EAS调拨出库单/EAS调拨出库单 分路
 */
public class AllocateStockBrandEasOrder implements Serializable {
    private int seq;
    private int sourceBillEntrySeq;
    private String invUpdateTypeID;
    private String sourceBillEntryID;
    private String materialID;
    private String lot;
    private String unitID;
    private int qty;
    private String locationID;
    private String warehouseID;
    private String storageOrgUnitID;
    private String companyOrgUnitID;
    private double price;
    private double amount;

    public String getSourceBillEntryID() {
        return sourceBillEntryID;
    }

    public void setSourceBillEntryID(String sourceBillEntryID) {
        this.sourceBillEntryID = sourceBillEntryID;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getSourceBillEntrySeq() {
        return sourceBillEntrySeq;
    }

    public void setSourceBillEntrySeq(int sourceBillEntrySeq) {
        this.sourceBillEntrySeq = sourceBillEntrySeq;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getInvUpdateTypeID() {
        return invUpdateTypeID;
    }

    public void setInvUpdateTypeID(String invUpdateTypeID) {
        this.invUpdateTypeID = invUpdateTypeID;
    }

    public String getMaterialID() {
        return materialID;
    }

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }


    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getStorageOrgUnitID() {
        return storageOrgUnitID;
    }

    public void setStorageOrgUnitID(String storageOrgUnitID) {
        this.storageOrgUnitID = storageOrgUnitID;
    }

    public String getCompanyOrgUnitID() {
        return companyOrgUnitID;
    }

    public void setCompanyOrgUnitID(String companyOrgUnitID) {
        this.companyOrgUnitID = companyOrgUnitID;
    }

}
