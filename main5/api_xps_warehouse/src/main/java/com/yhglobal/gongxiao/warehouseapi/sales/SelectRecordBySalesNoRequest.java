package com.yhglobal.gongxiao.warehouseapi.sales;

/**
 * 根据销售单号查询出库单基本信息 请求
 */
public class SelectRecordBySalesNoRequest {
    private String projectId;
    private String salesNo;

    public SelectRecordBySalesNoRequest(String projectId, String salesNo) {
        this.projectId = projectId;
        this.salesNo = salesNo;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSalesNo() {
        return salesNo;
    }

    public void setSalesNo(String salesNo) {
        this.salesNo = salesNo;
    }
}
