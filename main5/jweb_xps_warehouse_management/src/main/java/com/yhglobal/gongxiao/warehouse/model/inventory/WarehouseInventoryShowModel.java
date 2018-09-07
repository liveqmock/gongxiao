package com.yhglobal.gongxiao.warehouse.model.inventory;

import java.io.Serializable;

public class WarehouseInventoryShowModel implements Serializable{
    private long projectId;         //项目id
    private String productName;     //商品名称
    private String productCode;     //商品编码
    private String warehouseName;   //仓库名称
    private String batchNo;         //批次号
    private String purchaseType;    //采购方式
    private int availableQuantity;  //数量
    private int occupancyQuantity;  //占用数量
    private String status;             //库存状态

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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
