package com.yhglobal.gongxiao.phjd.model;

import java.util.Date;

public class PurchaseReturnedOrderItem {
    private Long rowId;

    private String purchaseReturnedOrderNo;

    private Byte itemStatus;

    private Byte syncToWmsFlag;

    private String returnReason;

    private String outboundOrderNo;

    private String warehouseId;

    private String warehouseName;

    private String productId;

    private String productCode;

    private String productName;

    private String productUnit;

    private String currencyCode;

    private String currencyName;

    private Long guidePrice;

    private Long purchasePrice;

    private Long returnAmount;

    private int returnReferNumber;

    private int totalReturnedQuantity;

    private int totalImperfectQuantity;

    private int totalOutStockQuantity;

    private int outStockImperfectQuantity;

    private int deliveredQuantity;

    private Long dataVersion;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public String getPurchaseReturnedOrderNo() {
        return purchaseReturnedOrderNo;
    }

    public void setPurchaseReturnedOrderNo(String purchaseReturnedOrderNo) {
        this.purchaseReturnedOrderNo = purchaseReturnedOrderNo == null ? null : purchaseReturnedOrderNo.trim();
    }

    public Byte getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Byte itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Byte getSyncToWmsFlag() {
        return syncToWmsFlag;
    }

    public void setSyncToWmsFlag(Byte syncToWmsFlag) {
        this.syncToWmsFlag = syncToWmsFlag;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason == null ? null : returnReason.trim();
    }

    public String getOutboundOrderNo() {
        return outboundOrderNo;
    }

    public void setOutboundOrderNo(String outboundOrderNo) {
        this.outboundOrderNo = outboundOrderNo == null ? null : outboundOrderNo.trim();
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId == null ? null : warehouseId.trim();
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit == null ? null : productUnit.trim();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName == null ? null : currencyName.trim();
    }

    public Long getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(Long guidePrice) {
        this.guidePrice = guidePrice;
    }

    public Long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Long getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Long returnAmount) {
        this.returnAmount = returnAmount;
    }

    public int getReturnReferNumber() {
        return returnReferNumber;
    }

    public void setReturnReferNumber(int returnReferNumber) {
        this.returnReferNumber = returnReferNumber;
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

    public int getTotalOutStockQuantity() {
        return totalOutStockQuantity;
    }

    public void setTotalOutStockQuantity(int totalOutStockQuantity) {
        this.totalOutStockQuantity = totalOutStockQuantity;
    }

    public int getOutStockImperfectQuantity() {
        return outStockImperfectQuantity;
    }

    public void setOutStockImperfectQuantity(int outStockImperfectQuantity) {
        this.outStockImperfectQuantity = outStockImperfectQuantity;
    }

    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
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

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }
}