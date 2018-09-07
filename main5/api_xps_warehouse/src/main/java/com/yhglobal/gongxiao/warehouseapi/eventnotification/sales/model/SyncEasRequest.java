package com.yhglobal.gongxiao.warehouseapi.eventnotification.sales.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 葛灿
 */
public class SyncEasRequest implements Serializable {


    private long projectId;
    private String salesOrderNo;
    private List<EasOutboundItem> items;

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public List<EasOutboundItem> getItems() {
        return items;
    }

    public void setItems(List<EasOutboundItem> items) {
        this.items = items;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}
