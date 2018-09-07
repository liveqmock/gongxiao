package com.yhglobal.gongxiao.warehousemanagement.bo;

import java.io.Serializable;
import java.util.Date;

public class OutboundOrderShowModel implements Serializable{
    private String projectId;                         //项目id
    private String salesOrderNo;                    //销售单号(销售出货场景)
    private String gongxiaoOutboundOrderNo;         //GX出库单号
    private String outboundOrderItemNo;             //出库明细单号
    private String productCode;             //商品编码
    private int inventoryType;           //库存类型
    private String outStorageType;         //出库单类型(销售出库,其他出库)
    private int orderStatus;             //单据状态(等待出库，部分出库，出库完成)
    private String warehouseId;           //仓库id
    private String deliverWarehouse;      //发货仓库
    private String deliverAddress;        //发货地址
    private String supplier;              //供应商
    private String customer;              //客户
    private Date createTime;              //创建时间
    private int plantQuantity;            //计划出库数量
    private int actualQuantity;           //实际出库数量
    private int differentQuantity;          //差异数

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getGongxiaoOutboundOrderNo() {
        return gongxiaoOutboundOrderNo;
    }

    public void setGongxiaoOutboundOrderNo(String gongxiaoOutboundOrderNo) {
        this.gongxiaoOutboundOrderNo = gongxiaoOutboundOrderNo;
    }

    public String getOutboundOrderItemNo() {
        return outboundOrderItemNo;
    }

    public void setOutboundOrderItemNo(String outboundOrderItemNo) {
        this.outboundOrderItemNo = outboundOrderItemNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getOutStorageType() {
        return outStorageType;
    }

    public void setOutStorageType(String outStorageType) {
        this.outStorageType = outStorageType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getDeliverWarehouse() {
        return deliverWarehouse;
    }

    public void setDeliverWarehouse(String deliverWarehouse) {
        this.deliverWarehouse = deliverWarehouse;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getPlantQuantity() {
        return plantQuantity;
    }

    public void setPlantQuantity(int plantQuantity) {
        this.plantQuantity = plantQuantity;
    }

    public int getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(int actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public int getDifferentQuantity() {
        return differentQuantity;
    }

    public void setDifferentQuantity(int differentQuantity) {
        this.differentQuantity = differentQuantity;
    }
}
