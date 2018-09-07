package com.yhglobal.gongxiao.sales.model;


import java.io.Serializable;
import java.util.Date;

/**
 * 销售退货单明细
 * @author : liukai
 */
public class SalesReturnOrderItem  implements Serializable {
    private long rowId;
    private int itemStatus;
    private int syncToGongxiaoWarehouseFlag;
    private String gongxiaoWarehouseInboundOrderNo;
    private String salesReturnedOrderNo;
    private String warehouseId;
    private String warehouseName;
    private String productId;
    private String productCode;
    private String productName;
    private String productUnit;
    private int totalReturnedQuantity;
    private int totalImperfectQuantity;
    private int totalInStockQuantity;
    private int inStockImperfectQuantity;
    private int deliveredQuantity;
    private String wmsInboundRecord;
    private String returnCause;//退货原因
    private long createdById;
    private Long dataVersion;
    private String tracelog;
    private Date createTime;
    private Date lastUpdateTime;
    private String createdByName;
    /**库存类型*/
    private int inventoryType;

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(long createdById) {
        this.createdById = createdById;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
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

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getReturnCause() {
        return returnCause;
    }

    public void setReturnCause(String returnCause) {
        this.returnCause = returnCause;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public int getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(int itemStatus) {
        this.itemStatus = itemStatus;
    }

    public int getSyncToGongxiaoWarehouseFlag() {
        return syncToGongxiaoWarehouseFlag;
    }

    public void setSyncToGongxiaoWarehouseFlag(int syncToGongxiaoWarehouseFlag) {
        this.syncToGongxiaoWarehouseFlag = syncToGongxiaoWarehouseFlag;
    }

    public String getGongxiaoWarehouseInboundOrderNo() {
        return gongxiaoWarehouseInboundOrderNo;
    }

    public void setGongxiaoWarehouseInboundOrderNo(String gongxiaoWarehouseInboundOrderNo) {
        this.gongxiaoWarehouseInboundOrderNo = gongxiaoWarehouseInboundOrderNo;
    }

    public String getSalesReturnedOrderNo() {
        return salesReturnedOrderNo;
    }

    public void setSalesReturnedOrderNo(String salesReturnedOrderNo) {
        this.salesReturnedOrderNo = salesReturnedOrderNo;
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

    public int getTotalReturnedQuantity() {
        return totalReturnedQuantity;
    }

    public void setTotalReturnedQuantity(int totalReturnedQuantity) {
        this.totalReturnedQuantity = totalReturnedQuantity;
    }

    public int getTotalImperfectQuantity() {
        return totalImperfectQuantity;
    }

    public void setTotalImperfectQuantity(int totalImperfectQuantity) {
        this.totalImperfectQuantity = totalImperfectQuantity;
    }

    public int getTotalInStockQuantity() {
        return totalInStockQuantity;
    }

    public void setTotalInStockQuantity(int totalInStockQuantity) {
        this.totalInStockQuantity = totalInStockQuantity;
    }

    public int getInStockImperfectQuantity() {
        return inStockImperfectQuantity;
    }

    public void setInStockImperfectQuantity(int inStockImperfectQuantity) {
        this.inStockImperfectQuantity = inStockImperfectQuantity;
    }

    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public String getWmsInboundRecord() {
        return wmsInboundRecord;
    }

    public void setWmsInboundRecord(String wmsInboundRecord) {
        this.wmsInboundRecord = wmsInboundRecord;
    }





}
