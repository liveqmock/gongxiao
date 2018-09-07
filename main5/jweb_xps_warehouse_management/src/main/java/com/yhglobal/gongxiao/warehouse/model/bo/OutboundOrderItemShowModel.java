package com.yhglobal.gongxiao.warehouse.model.bo;

import java.io.Serializable;
import java.util.Date;

public class OutboundOrderItemShowModel implements Serializable{
    private String projectId;                       //项目id
    private String outboundOrderItemNo;             //出库明细单号
    private String batchNo;                         //批次
    private String productCode;                     //商品编码
    private String productName;                     //商品名称
    private String inventoryType;                  //库存类型
    private String productUnit;                     //商品单位
    private int planOutStockQuantity;               //计划出库数量
    private int actualOutStockQuantity;             //实际出库数量
    private int orderStatus;                        //订单状态
    private int differentOutStockQuantity;          //差异数量
    private Date createTime;                        //创建时间

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getOutboundOrderItemNo() {
        return outboundOrderItemNo;
    }

    public void setOutboundOrderItemNo(String outboundOrderItemNo) {
        this.outboundOrderItemNo = outboundOrderItemNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public int getPlanOutStockQuantity() {
        return planOutStockQuantity;
    }

    public void setPlanOutStockQuantity(int planOutStockQuantity) {
        this.planOutStockQuantity = planOutStockQuantity;
    }

    public int getActualOutStockQuantity() {
        return actualOutStockQuantity;
    }

    public void setActualOutStockQuantity(int actualOutStockQuantity) {
        this.actualOutStockQuantity = actualOutStockQuantity;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getDifferentOutStockQuantity() {
        return differentOutStockQuantity;
    }

    public void setDifferentOutStockQuantity(int differentOutStockQuantity) {
        this.differentOutStockQuantity = differentOutStockQuantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
