package com.yhglobal.gongxiao.warehouse.model.dto.wms;


import java.util.List;

/**
 * WMS库存查询信息
 */
public class WmsSelectStockRequest {
    private List<Inventory> inventoryList;

    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
    }
}
