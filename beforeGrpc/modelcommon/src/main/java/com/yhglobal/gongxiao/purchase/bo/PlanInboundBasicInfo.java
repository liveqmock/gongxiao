package com.yhglobal.gongxiao.purchase.bo;


import java.io.Serializable;
import java.util.Date;

/**
 * 预约入库基础信息
 *
 * @author: 陈浩
 * @create: 2018-02-07 11:12
 **/
public class PlanInboundBasicInfo implements Serializable {
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 采购单号
     */
    private String purchaseOrderNo;
    /**
     * 仓库ID
     */
    private String warehouseId;
    /**
     *仓库名称
     */
    private String warehouseName;
    /**
     *各种类商品本次入库数量总和
     */
    private int totalQuantity;

    /**
     * 在途商品数量
     */
    private int planInstockNumberTotal;

    /**
     * 要求到货日期
     */
    private Date requireInboundDate;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    private int createdById;

    private String  createdByName;

    public Date getRequireInboundDate() {
        return requireInboundDate;
    }

    public void setRequireInboundDate(Date requireInboundDate) {
        this.requireInboundDate = requireInboundDate;
    }

    public int getCreatedById() {
        return createdById;
    }

    public void setCreatedById(int createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public int getPlanInstockNumberTotal() {
        return planInstockNumberTotal;
    }

    public void setPlanInstockNumberTotal(int planInstockNumberTotal) {
        this.planInstockNumberTotal = planInstockNumberTotal;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
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

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "PlanInboundBasicInfo{" +
                "projectId='" + projectId + '\'' +
                ", purchaseOrderNo='" + purchaseOrderNo + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", planInstockNumberTotal=" + planInstockNumberTotal +
                ", requireInboundDate=" + requireInboundDate +
                ", createdById=" + createdById +
                ", createdByName='" + createdByName + '\'' +
                '}';
    }
}
