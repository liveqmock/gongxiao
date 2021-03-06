package com.yhglobal.gongxiao.inventory.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售发货通知批次查询model
 *
 * @author liukai
 */
public class InventoryBatch implements Serializable {
    private String projectId;           //项目id
    private String batchNo;             //批次
    private int purchaseType;           //采购类型(0:赠品 1:非赠品)
    private int inventoryStatus;        //库存类型
    private String productId;       //货品id
    private String productName;         //商品名称
    private String productCode;         //商品编码
    private String warehouseId;         //仓库id
    private String warehouseName;       //仓库
    private int inventoryTotalAmount;   //货品库存
    private int inventoryBatchAmount;   //批次库存

    private Date createTime;            //入库时间
    private double purchasePrice;       //采购价格
    private double costPrice;           //成本价

    /***************************
     以下字段用于发货通知页面使用
     ***************************/
    /**
     * 订单总数
     */
    private int orderTotalQuantity;
    /**
     * 订单未处理数量
     */
    private int orderUnhandledQuantity;
    /**
     * 本次预约数量
     */
    private int scheduleQuantity;
    /**
     * 备注
     */
    private String remark;
    /**
     * 出库数量
     */
    private int outboundQuantity;
    /**
     * 可退货数量
     */
    private int returnableQuantity;
    /**
     * 出货价
     */
    private long wholesalePrice;
    /**
     * eas货品编码
     */
    private String easProductCode;
    /**
     * easId
     */
    private String easId;

    public String getEasId() {
        return easId;
    }

    public void setEasId(String easId) {
        this.easId = easId;
    }

    public String getEasProductCode() {
        return easProductCode;
    }

    public void setEasProductCode(String easProductCode) {
        this.easProductCode = easProductCode;
    }

    public long getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(long wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    public int getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(int inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public int getReturnableQuantity() {
        return returnableQuantity;
    }

    public void setReturnableQuantity(int returnableQuantity) {
        this.returnableQuantity = returnableQuantity;
    }

    public int getOutboundQuantity() {
        return outboundQuantity;
    }

    public void setOutboundQuantity(int outboundQuantity) {
        this.outboundQuantity = outboundQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getOrderTotalQuantity() {
        return orderTotalQuantity;
    }

    public void setOrderTotalQuantity(int orderTotalQuantity) {
        this.orderTotalQuantity = orderTotalQuantity;
    }

    public int getOrderUnhandledQuantity() {
        return orderUnhandledQuantity;
    }

    public void setOrderUnhandledQuantity(int orderUnhandledQuantity) {
        this.orderUnhandledQuantity = orderUnhandledQuantity;
    }

    public int getScheduleQuantity() {
        return scheduleQuantity;
    }

    public void setScheduleQuantity(int scheduleQuantity) {
        this.scheduleQuantity = scheduleQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getInventoryTotalAmount() {
        return inventoryTotalAmount;
    }

    public void setInventoryTotalAmount(int inventoryTotalAmount) {
        this.inventoryTotalAmount = inventoryTotalAmount;
    }

    public int getInventoryBatchAmount() {
        return inventoryBatchAmount;
    }

    public void setInventoryBatchAmount(int inventoryBatchAmount) {
        this.inventoryBatchAmount = inventoryBatchAmount;
    }
}
