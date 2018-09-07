package com.yhglobal.gongxiao.purchase.bo;

import java.io.Serializable;

/**
 * 仓库信息
 *
 * @author: 陈浩
 * @create: 2018-02-27 14:50
 **/
public class WarehouseInfo implements Serializable {
    /**
     * 仓库ID
     */
    private String warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 仓库所在地址
     */
    private String warehouseAddress;

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }
}
