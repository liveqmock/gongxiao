package com.yhglobal.gongxiao.warehousemanagement.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 调拨单详情
 * @author liukai
 */
public class AllocationOrderItem implements Serializable {
    private int id;                             //主键id
    private String batchNo;                     //批次
    private String allocateNo;                  //调拨单号
    private String projectIdOut;                //调出项目
    private String projectIdEnter;              //调入项目
    private String gongxiaoOutboundOrderNo;     //出库单号
    private String gongxiaoInboundOrderNo;      //入库单号
    private String warehouseOutId;              //调出仓库id
    private String warehouseOut;                //调出仓库
    private String warehouseEnterId;            //调入仓库id
    private String warehouseEnter;              //调入仓库
    private String productId;                   //商品id
    private String productCode;                 //商品型号
    private String productName;                 //商品名称
    private String productUnit;                 //商品数量
    private int inventoryQuantity;           //可发库存
    private int alloteQuantity;              //调拨数量
    private double guidPrice;                   //
    private double puchasePrice;                //
    private double costPrice;                   //
    private int inventoryStatus;                //库存类型
    private int status;                         //状态
    private Date createTime;                    //创建时间
    private Date lastUpdateTime;                //更新时间

    public double getGuidPrice() {
        return guidPrice;
    }

    public void setGuidPrice(double guidPrice) {
        this.guidPrice = guidPrice;
    }

    public double getPuchasePrice() {
        return puchasePrice;
    }

    public void setPuchasePrice(double puchasePrice) {
        this.puchasePrice = puchasePrice;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getAllocateNo() {
        return allocateNo;
    }

    public void setAllocateNo(String allocateNo) {
        this.allocateNo = allocateNo;
    }

    public String getProjectIdOut() {
        return projectIdOut;
    }

    public void setProjectIdOut(String projectIdOut) {
        this.projectIdOut = projectIdOut;
    }

    public String getProjectIdEnter() {
        return projectIdEnter;
    }

    public void setProjectIdEnter(String projectIdEnter) {
        this.projectIdEnter = projectIdEnter;
    }

    public String getGongxiaoOutboundOrderNo() {
        return gongxiaoOutboundOrderNo;
    }

    public void setGongxiaoOutboundOrderNo(String gongxiaoOutboundOrderNo) {
        this.gongxiaoOutboundOrderNo = gongxiaoOutboundOrderNo;
    }

    public String getGongxiaoInboundOrderNo() {
        return gongxiaoInboundOrderNo;
    }

    public void setGongxiaoInboundOrderNo(String gongxiaoInboundOrderNo) {
        this.gongxiaoInboundOrderNo = gongxiaoInboundOrderNo;
    }

    public String getWarehouseOutId() {
        return warehouseOutId;
    }

    public void setWarehouseOutId(String warehouseOutId) {
        this.warehouseOutId = warehouseOutId;
    }

    public String getWarehouseEnterId() {
        return warehouseEnterId;
    }

    public void setWarehouseEnterId(String warehouseEnterId) {
        this.warehouseEnterId = warehouseEnterId;
    }

    public String getWarehouseOut() {
        return warehouseOut;
    }

    public void setWarehouseOut(String warehouseOut) {
        this.warehouseOut = warehouseOut;
    }

    public String getWarehouseEnter() {
        return warehouseEnter;
    }

    public void setWarehouseEnter(String warehouseEnter) {
        this.warehouseEnter = warehouseEnter;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public int getAlloteQuantity() {
        return alloteQuantity;
    }

    public void setAlloteQuantity(int alloteQuantity) {
        this.alloteQuantity = alloteQuantity;
    }

    public int getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(int inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
