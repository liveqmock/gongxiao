package com.yhglobal.gongxiao.warehouseapi.inbound;

/**
 * 根据入库单号查询入库单
 */
public class SelectInboundByInboundNumRequest {
    private String projectId;
    private String inventoryNum;

    public SelectInboundByInboundNumRequest(String projectId, String inventoryNum) {
        this.projectId = projectId;
        this.inventoryNum = inventoryNum;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }
}
