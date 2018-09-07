package com.yhglobal.gongxiao.model;

import java.util.Date;

public class PurchaseOrderItem {

    private long priceDiscount;

    private String tablePrefix;

    private Long purchaseItemId;

    private Byte orderStatus;

    private String purchaseOrderNo;

    private String productId;

    private String productCode;

    private String productName;

    private String productUnit;

    private Integer warehouseId;

    private String warehouseName;

    private Byte shippingMode;

    private Long couponAmount;

    private Long prepaidAmount;

    private Long guidePrice;

    private Long purchasePrice;

    private Long costPrice;

    private Long couponBasePrice;

    private Long factoryPrice;

    private int purchaseNumber;

    private int inTransitQuantity;

    private int inStockQuantity;

    private Integer imperfectQuantity;

    private Integer canceledQuantity;

    private Integer returnedQuantity;

    private String ongoingInboundOrderInfo;

    private String finishedInboundOrderInfo;

    private Long dataVersion;

    private Date createTime;

    private Date lastUpdateTime;

    /*
    *  新增字段 easEntryId  easMateriaCode
    * */

    private String tracelog;

    private String easEntryId;

    private String easMateriaCode;

    private Long cashAmount;

    public Long getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Long cashAmount) {
        this.cashAmount = cashAmount;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public Long getFactoryPrice() {
        return factoryPrice;
    }

    public void setFactoryPrice(Long factoryPrice) {
        this.factoryPrice = factoryPrice;
    }

    public int getInTransitQuantity() {
        return inTransitQuantity;
    }

    public Long getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(Long purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo == null ? null : purchaseOrderNo.trim();
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

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
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

    public Long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Long getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(Long prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
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

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getCouponBasePrice() {
        return couponBasePrice;
    }

    public void setCouponBasePrice(Long couponBasePrice) {
        this.couponBasePrice = couponBasePrice;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public Integer getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(Integer inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public Integer getImperfectQuantity() {
        return imperfectQuantity;
    }

    public void setImperfectQuantity(Integer imperfectQuantity) {
        this.imperfectQuantity = imperfectQuantity;
    }

    public Integer getCanceledQuantity() {
        return canceledQuantity;
    }

    public void setCanceledQuantity(Integer canceledQuantity) {
        this.canceledQuantity = canceledQuantity;
    }

    public Integer getReturnedQuantity() {
        return returnedQuantity;
    }

    public void setReturnedQuantity(Integer returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }

    public String getOngoingInboundOrderInfo() {
        return ongoingInboundOrderInfo;
    }

    public void setOngoingInboundOrderInfo(String ongoingInboundOrderInfo) {
        this.ongoingInboundOrderInfo = ongoingInboundOrderInfo == null ? null : ongoingInboundOrderInfo.trim();
    }

    public String getFinishedInboundOrderInfo() {
        return finishedInboundOrderInfo;
    }

    public void setFinishedInboundOrderInfo(String finishedInboundOrderInfo) {
        this.finishedInboundOrderInfo = finishedInboundOrderInfo == null ? null : finishedInboundOrderInfo.trim();
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

    public void setPurchaseNumber(int purchaseNumber) { this.purchaseNumber = purchaseNumber; }

    public void setInTransitQuantity(int inTransitQuantity) { this.inTransitQuantity = inTransitQuantity; }

    public void setInStockQuantity(int inStockQuantity) { this.inStockQuantity = inStockQuantity; }

    public String getEasEntryId() { return easEntryId; }

    public void setEasEntryId(String easEntryId) { this.easEntryId = easEntryId; }

    public String getEasMateriaCode() { return easMateriaCode; }

    public void setEasMateriaCode(String easMateriaCode) { this.easMateriaCode = easMateriaCode; }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }

    public long getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(long priceDiscount) {
        this.priceDiscount = priceDiscount;
    }
}