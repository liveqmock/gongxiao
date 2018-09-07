package com.yhglobal.gongxiao.phjd.model;

import java.util.Date;

public class PurchaseReturnedOrder {
    private Long purchaseReturnedOrderId;

    private Byte returnedOrderStatus;

    private Byte returnedType;

    private String purchaseReturnedOrderNo;

    private String outboundOrderNumber;

    private Long projectId;

    private String projectName;

    private Integer brandId;

    private String brandName;

    private String currencyCode;

    private String currencyName;

    private String originalPurchaseOrderNo;

    private String originalGongxiaoInboundOrderNo;

    private String warehouseId;

    private String warehouseName;

    private String recipientName;

    private String recipientMobile;

    private String recipientAddress;

    private String recipientDetailAddress;

    private String recipientCompanyName;

    private Byte shippingMode;

    private String logisticsOrderNo;

    private String logisticsCompany;

    private Long freight;

    private Byte freightPaidBy;

    private Long returnedCouponAmount;

    private Long returnedPrepaidAmount;

    private Long returnCouponGenerateAmount;

    private Long returnedCashAmount;

    private Integer sourceOrderNumber;

    private Integer totalReturnedQuantity;

    private Integer totalImperfectQuantity;

    private Integer outStockQuantity;

    private Integer deliveredQuantity;

    private String outboundOrderInfo;

    private String returnItemInfo;

    private Long dataVersion;

    private Long createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    public Long getPurchaseReturnedOrderId() {
        return purchaseReturnedOrderId;
    }

    public void setPurchaseReturnedOrderId(Long purchaseReturnedOrderId) {
        this.purchaseReturnedOrderId = purchaseReturnedOrderId;
    }

    public Byte getReturnedOrderStatus() {
        return returnedOrderStatus;
    }

    public void setReturnedOrderStatus(Byte returnedOrderStatus) {
        this.returnedOrderStatus = returnedOrderStatus;
    }

    public Byte getReturnedType() {
        return returnedType;
    }

    public void setReturnedType(Byte returnedType) {
        this.returnedType = returnedType;
    }

    public String getPurchaseReturnedOrderNo() {
        return purchaseReturnedOrderNo;
    }

    public void setPurchaseReturnedOrderNo(String purchaseReturnedOrderNo) {
        this.purchaseReturnedOrderNo = purchaseReturnedOrderNo == null ? null : purchaseReturnedOrderNo.trim();
    }

    public String getOutboundOrderNumber() {
        return outboundOrderNumber;
    }

    public void setOutboundOrderNumber(String outboundOrderNumber) {
        this.outboundOrderNumber = outboundOrderNumber == null ? null : outboundOrderNumber.trim();
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
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

    public String getOriginalPurchaseOrderNo() {
        return originalPurchaseOrderNo;
    }

    public void setOriginalPurchaseOrderNo(String originalPurchaseOrderNo) {
        this.originalPurchaseOrderNo = originalPurchaseOrderNo == null ? null : originalPurchaseOrderNo.trim();
    }

    public String getOriginalGongxiaoInboundOrderNo() {
        return originalGongxiaoInboundOrderNo;
    }

    public void setOriginalGongxiaoInboundOrderNo(String originalGongxiaoInboundOrderNo) {
        this.originalGongxiaoInboundOrderNo = originalGongxiaoInboundOrderNo == null ? null : originalGongxiaoInboundOrderNo.trim();
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

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName == null ? null : recipientName.trim();
    }

    public String getRecipientMobile() {
        return recipientMobile;
    }

    public void setRecipientMobile(String recipientMobile) {
        this.recipientMobile = recipientMobile == null ? null : recipientMobile.trim();
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress == null ? null : recipientAddress.trim();
    }

    public String getRecipientDetailAddress() {
        return recipientDetailAddress;
    }

    public void setRecipientDetailAddress(String recipientDetailAddress) {
        this.recipientDetailAddress = recipientDetailAddress == null ? null : recipientDetailAddress.trim();
    }

    public String getRecipientCompanyName() {
        return recipientCompanyName;
    }

    public void setRecipientCompanyName(String recipientCompanyName) {
        this.recipientCompanyName = recipientCompanyName == null ? null : recipientCompanyName.trim();
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

    public Long getFreight() {
        return freight;
    }

    public void setFreight(Long freight) {
        this.freight = freight;
    }

    public Byte getFreightPaidBy() {
        return freightPaidBy;
    }

    public void setFreightPaidBy(Byte freightPaidBy) {
        this.freightPaidBy = freightPaidBy;
    }

    public Long getReturnedCouponAmount() {
        return returnedCouponAmount;
    }

    public void setReturnedCouponAmount(Long returnedCouponAmount) {
        this.returnedCouponAmount = returnedCouponAmount;
    }

    public Long getReturnedPrepaidAmount() {
        return returnedPrepaidAmount;
    }

    public void setReturnedPrepaidAmount(Long returnedPrepaidAmount) {
        this.returnedPrepaidAmount = returnedPrepaidAmount;
    }

    public Long getReturnCouponGenerateAmount() {
        return returnCouponGenerateAmount;
    }

    public void setReturnCouponGenerateAmount(Long returnCouponGenerateAmount) {
        this.returnCouponGenerateAmount = returnCouponGenerateAmount;
    }

    public Long getReturnedCashAmount() {
        return returnedCashAmount;
    }

    public void setReturnedCashAmount(Long returnedCashAmount) {
        this.returnedCashAmount = returnedCashAmount;
    }

    public Integer getSourceOrderNumber() {
        return sourceOrderNumber;
    }

    public void setSourceOrderNumber(Integer sourceOrderNumber) {
        this.sourceOrderNumber = sourceOrderNumber;
    }

    public Integer getTotalReturnedQuantity() {
        return totalReturnedQuantity;
    }

    public void setTotalReturnedQuantity(Integer totalReturnedQuantity) {
        this.totalReturnedQuantity = totalReturnedQuantity;
    }

    public Integer getTotalImperfectQuantity() {
        return totalImperfectQuantity;
    }

    public void setTotalImperfectQuantity(Integer totalImperfectQuantity) {
        this.totalImperfectQuantity = totalImperfectQuantity;
    }

    public Integer getOutStockQuantity() {
        return outStockQuantity;
    }

    public void setOutStockQuantity(Integer outStockQuantity) {
        this.outStockQuantity = outStockQuantity;
    }

    public Integer getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(Integer deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public String getOutboundOrderInfo() {
        return outboundOrderInfo;
    }

    public void setOutboundOrderInfo(String outboundOrderInfo) {
        this.outboundOrderInfo = outboundOrderInfo == null ? null : outboundOrderInfo.trim();
    }

    public String getReturnItemInfo() {
        return returnItemInfo;
    }

    public void setReturnItemInfo(String returnItemInfo) {
        this.returnItemInfo = returnItemInfo == null ? null : returnItemInfo.trim();
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName == null ? null : createdByName.trim();
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