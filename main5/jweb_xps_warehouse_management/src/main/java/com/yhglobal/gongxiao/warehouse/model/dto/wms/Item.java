package com.yhglobal.gongxiao.warehouse.model.dto.wms;

public class Item {
    private String itemCode;
    private String inventoryType;

    public Item() {
    }

    public Item(String itemCode, String inventoryType) {
        this.itemCode = itemCode;
        this.inventoryType = inventoryType;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }
}
