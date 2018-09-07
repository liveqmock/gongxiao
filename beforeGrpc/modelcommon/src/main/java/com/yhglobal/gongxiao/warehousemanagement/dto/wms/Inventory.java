package com.yhglobal.gongxiao.warehousemanagement.dto.wms;

import java.util.List;

public class Inventory {
    private String ckid;
    private String warehouseCode;
    private String ownerCode;
    private List<Item> items;

    public Inventory() {
    }

    public Inventory(String ckid, String warehouseCode, String ownerCode, List<Item> items) {
        this.ckid = ckid;
        this.warehouseCode = warehouseCode;
        this.ownerCode = ownerCode;
        this.items = items;
    }

    public String getCkid() {
        return ckid;
    }

    public void setCkid(String ckid) {
        this.ckid = ckid;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
