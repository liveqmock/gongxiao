package com.yhglobal.gongxiao.inventory.bo;

import java.io.Serializable;
import java.util.Date;

public class InventoryBatchDetail implements Serializable {
    private String projectId;           //项目id
    private String batchNo;             //批次
    private String warehouseName;       //仓库
    private Date createTime;            //入库时间
    private double purchasePrice;       //采购价格
    private int inventoryBatchAmount;   //剩余数量

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getInventoryBatchAmount() {
        return inventoryBatchAmount;
    }

    public void setInventoryBatchAmount(int inventoryBatchAmount) {
        this.inventoryBatchAmount = inventoryBatchAmount;
    }


}
