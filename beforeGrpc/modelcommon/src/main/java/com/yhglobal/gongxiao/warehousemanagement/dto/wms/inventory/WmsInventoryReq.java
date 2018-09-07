package com.yhglobal.gongxiao.warehousemanagement.dto.wms.inventory;

import java.io.Serializable;
import java.util.List;

/**
 * 查询wms库存的请求
 * @author liukai
 */
public class WmsInventoryReq implements Serializable {
    private List<InventoryQueryDetailDto> inventoryList;

    public List<InventoryQueryDetailDto> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<InventoryQueryDetailDto> inventoryList) {
        this.inventoryList = inventoryList;
    }
}
