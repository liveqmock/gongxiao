package com.yhglobal.gongxiao.warehouseapi.inbound;

import java.io.Serializable;

/**
 * 新增其他出库单(其它出库单)
 */
public class InsertManualOutboundRequest implements Serializable {
    private String projectId;
    private String warehouseId;
    private String warehouseName;
    private String delieverAddress;
    private String supplierName;
    private String businessDate;
    private int outboundType;
    private String remark;
    private String itemsInfo;

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

    public String getDelieverAddress() {
        return delieverAddress;
    }

    public void setDelieverAddress(String delieverAddress) {
        this.delieverAddress = delieverAddress;
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

    public int getOutboundType() {
        return outboundType;
    }

    public void setOutboundType(int outboundType) {
        this.outboundType = outboundType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getItemsInfo() {
        return itemsInfo;
    }

    public void setItemsInfo(String itemsInfo) {
        this.itemsInfo = itemsInfo;
    }
}
