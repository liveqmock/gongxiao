package com.yhglobal.gongxiao.warehouseapi.model;

import java.io.Serializable;

public class PlanOutboundOrderItem implements Serializable{
    private int purchaseType;                       //库区(0:非赠品 1:赠品)
    private int inventoryType;                      //库存类型(对应WmsInventoryType类中)
    private String salesOrderNo;                    //订单号
    private String warehouseId;                     //仓库ID
    private String warehouseName;                   //仓库名称
    private String productId ;                      //货品id
    private String productCode;                     //商品编码
    private String productName;                     //商品名称
    private int totalQuantity;                      //商品数量总和
    private String batchNo;
    private long salesGuidePrice;                   //销售指导价
    private long wholesalePrice;                    //出货价格
    private long purchasePrice;                     //采购价格

    public long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public long getSalesGuidePrice() {
        return salesGuidePrice;
    }

    public void setSalesGuidePrice(long salesGuidePrice) {
        this.salesGuidePrice = salesGuidePrice;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
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

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
