package com.yhglobal.gongxiao.model;

import java.util.Date;

public class PurchaseReverseDelivery {
    private Long reverseId;

    private Byte reverseStatus;

    private Byte reverseType;

    private String purchaseReturnedOrderNo;

    private Byte syncToGongxiaoWarehouseFlag;

    private String gongxiaoWarehouseOutboundOrderNo;

    private String originalPurchaseOrderNo;

    private String warehouseId;

    private String warehouseName;

    private Byte shippingMode;

    private String logisticsOrderNo;

    private String logisticsCompany;

    private String productInfo;

    private Integer totalQuantity;

    private Long dataVersion;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    public Long getReverseId() {
        return reverseId;
    }

    public void setReverseId(Long reverseId) {
        this.reverseId = reverseId;
    }

    public Byte getReverseStatus() {
        return reverseStatus;
    }

    public void setReverseStatus(Byte reverseStatus) {
        this.reverseStatus = reverseStatus;
    }

    public Byte getReverseType() {
        return reverseType;
    }

    public void setReverseType(Byte reverseType) {
        this.reverseType = reverseType;
    }

    public String getPurchaseReturnedOrderNo() {
        return purchaseReturnedOrderNo;
    }

    public void setPurchaseReturnedOrderNo(String purchaseReturnedOrderNo) {
        this.purchaseReturnedOrderNo = purchaseReturnedOrderNo;
    }

    public Byte getSyncToGongxiaoWarehouseFlag() {
        return syncToGongxiaoWarehouseFlag;
    }

    public void setSyncToGongxiaoWarehouseFlag(Byte syncToGongxiaoWarehouseFlag) {
        this.syncToGongxiaoWarehouseFlag = syncToGongxiaoWarehouseFlag;
    }

    public String getGongxiaoWarehouseOutboundOrderNo() {
        return gongxiaoWarehouseOutboundOrderNo;
    }

    public void setGongxiaoWarehouseOutboundOrderNo(String gongxiaoWarehouseOutboundOrderNo) {
        this.gongxiaoWarehouseOutboundOrderNo = gongxiaoWarehouseOutboundOrderNo == null ? null : gongxiaoWarehouseOutboundOrderNo.trim();
    }

    public String getOriginalPurchaseOrderNo() {
        return originalPurchaseOrderNo;
    }

    public void setOriginalPurchaseOrderNo(String originalPurchaseOrderNo) {
        this.originalPurchaseOrderNo = originalPurchaseOrderNo == null ? null : originalPurchaseOrderNo.trim();
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

    public Byte getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(Byte shippingMode) {
        this.shippingMode = shippingMode;
    }

    public String getLogisticsOrderNo() {
        return logisticsOrderNo;
    }

    public void setLogisticsOrderNo(String logisticsOrderNo) {
        this.logisticsOrderNo = logisticsOrderNo == null ? null : logisticsOrderNo.trim();
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany == null ? null : logisticsCompany.trim();
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo == null ? null : productInfo.trim();
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
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