package com.yhglobal.gongxiao.warehouse.model.dto.wms.close;

import java.io.Serializable;

/**
 * 已出入库商品信息
 * @author liukai
 */
public class OrderItemDto implements Serializable {
    private String itemCode;
    private String itemName;
    private int itemQuantity;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
