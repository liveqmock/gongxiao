package com.yhglobal.gongxiao.warehouseapi.inbound;

import java.io.Serializable;

/**
 * 插其他入库单请求
 */
public class InsertManualInboundRequest implements Serializable{
    private String projectId;
    private String warehouseId;
    private String warehouseName;
    private String recieveAddress;
    private String supplierName;
    private String businessDate;
    private String remark;
    private int inboundType;
    private String purchaseItemInfoJson;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

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

    public String getRecieveAddress() {
        return recieveAddress;
    }

    public void setRecieveAddress(String recieveAddress) {
        this.recieveAddress = recieveAddress;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getInboundType() {
        return inboundType;
    }

    public void setInboundType(int inboundType) {
        this.inboundType = inboundType;
    }

    public String getPurchaseItemInfoJson() {
        return purchaseItemInfoJson;
    }

    public void setPurchaseItemInfoJson(String purchaseItemInfoJson) {
        this.purchaseItemInfoJson = purchaseItemInfoJson;
    }
}
