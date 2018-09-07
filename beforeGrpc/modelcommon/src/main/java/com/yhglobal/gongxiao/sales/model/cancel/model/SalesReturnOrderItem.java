package com.yhglobal.gongxiao.sales.model.cancel.model;

import com.yhglobal.gongxiao.BaseModel;

import java.io.Serializable;

/**
 * 销售退货单明细
 * @author : liukai
 */
public class SalesReturnOrderItem extends BaseModel implements Serializable {
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
