package com.yhglobal.gongxiao.warehousemanagement.bo;

import java.io.Serializable;
import java.util.Date;

public class InboundOrderShowModel implements Serializable{
    private Long projectId;                     //项目id
    private int orderStatus;                   //入库单状态  (0:等待入库 1:收货中 3:收货完成)
    private String inboundType;                //入库单类型
    private String gongxiaoInboundOrderNo;      //入库单号
    private String purchaseOrderNo;         //采购单号(采购入库场景)
    private String warehouseId;           //仓库id
    private String warehouseName;           //仓库名称
    private int planInStockQuantity;              //各种类商品本次入库数量总和
    private int actualInStockQuantity;            //已入库的商品数量
    private int differentQuantity;          //差异数量
    private Date estimateArriveTime;        //预计到仓时间
    private Date createTime;                //创建时间

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getInboundType() {
        return inboundType;
    }

    public void setInboundType(String inboundType) {
        this.inboundType = inboundType;
    }

    public String getGongxiaoInboundOrderNo() {
        return gongxiaoInboundOrderNo;
    }

    public void setGongxiaoInboundOrderNo(String gongxiaoInboundOrderNo) {
        this.gongxiaoInboundOrderNo = gongxiaoInboundOrderNo;
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

    public int getPlanInStockQuantity() {
        return planInStockQuantity;
    }

    public void setPlanInStockQuantity(int planInStockQuantity) {
        this.planInStockQuantity = planInStockQuantity;
    }

    public int getActualInStockQuantity() {
        return actualInStockQuantity;
    }

    public void setActualInStockQuantity(int actualInStockQuantity) {
        this.actualInStockQuantity = actualInStockQuantity;
    }

    public Date getEstimateArriveTime() {
        return estimateArriveTime;
    }

    public void setEstimateArriveTime(Date estimateArriveTime) {
        this.estimateArriveTime = estimateArriveTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getDifferentQuantity() {
        return differentQuantity;
    }

    public void setDifferentQuantity(int differentQuantity) {
        this.differentQuantity = differentQuantity;
    }
}
