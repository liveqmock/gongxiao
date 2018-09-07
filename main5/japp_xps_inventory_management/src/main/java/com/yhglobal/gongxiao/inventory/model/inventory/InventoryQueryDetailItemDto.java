package com.yhglobal.gongxiao.inventory.model.inventory;

import java.io.Serializable;

/**
 * 库存查询商品列表明细
 * @author liukai
 */
public class InventoryQueryDetailItemDto implements Serializable {
    private String itemCode;       //商品编码
    private String inventoryType;   //品质/库存

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
