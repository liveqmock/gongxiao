package com.yhglobal.gongxiao.warehouse.entity;

import java.io.Serializable;

/**
 * 货品库存页面展示模型
 */
public class ProductInventoryShowModel implements Serializable{

    private long projectId;          //项目id
    private String productName;     //商品名称
    private String productCode;     //商品编码
    private int availableQuantity;  //可用数量
    private int occupancyQuantity;  //占用数量
    private int defectiveQuantity;  //残品数量
    private int physicalInventory;  //实物库存
    private int onWayQuantity;      //在途数量
    private int frozenQuantity;     //冻结数量

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getOccupancyQuantity() {
        return occupancyQuantity;
    }

    public void setOccupancyQuantity(int occupancyQuantity) {
        this.occupancyQuantity = occupancyQuantity;
    }

    public int getDefectiveQuantity() {
        return defectiveQuantity;
    }

    public void setDefectiveQuantity(int defectiveQuantity) {
        this.defectiveQuantity = defectiveQuantity;
    }

    public int getPhysicalInventory() {
        return physicalInventory;
    }

    public void setPhysicalInventory(int physicalInventory) {
        this.physicalInventory = physicalInventory;
    }

    public int getOnWayQuantity() {
        return onWayQuantity;
    }

    public void setOnWayQuantity(int onWayQuantity) {
        this.onWayQuantity = onWayQuantity;
    }

    public int getFrozenQuantity() {
        return frozenQuantity;
    }

    public void setFrozenQuantity(int frozenQuantity) {
        this.frozenQuantity = frozenQuantity;
    }
}
