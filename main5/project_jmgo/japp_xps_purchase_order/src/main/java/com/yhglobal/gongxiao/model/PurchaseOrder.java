package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.util.Date;

public class PurchaseOrder implements Serializable{

    private byte supplierReceipt;

    private String tablePrefix;

    private long purchaseOrderId;

    private String uniqueNumber;

    private Byte orderStatus;

    private byte easStatus;

    private String purchaseOrderNo;

    private long projectId;

    private String projectName;

    private int brandId;

    private String brandName;

    private String supplierId;

    private String supplierName;

    private int warehouseId;

    private String warehouseName;

    private int purchaseType;//1 普通采购  2货补

    private int provinceId;

    private String provinceName;

    private int cityId;

    private String cityName;

    private int districtId;

    private String districtName;

    private String currencyId;

    private String currencyName;

    private Byte paymentMode;

    private Byte paymentChannel;

    private Byte shippingMode;

    private Date expectedInboundDate;

    private Date orderDeadlineDate;

    private String brandOrderNo;

    private String contractReferenceOrderNo;

    private String remark;

    private int purchaseCategory;

    private int totalQuantity;

    private long couponAmountUse;

    private long prepaidAmountUse;

    private long adCouponAmountUse;

    private long adPrepaidAmountUse;

    private long cashAmount;

    private long estimatedCouponTotalAmount;

    private long purchaseGuideAmount;

    private long purchasePrivilegeAmount;

    private long returnCash;

    private long purchaseShouldPayAmount;

    private long purchaseActualPayAmount;

    private String couponActivityInfo;

    private Byte paymentFlag;

    private String productInfo;

    private int inTransitQuantity;

    private int inStockQuantity;

    private int canceledQuantity;

    private int returnedQuantity;

    private int unhandledQuantity;

    private String ongoingInboundOrderInfo;

    private String finishedInboundOrderInfo;

    private long dataVersion;

    private long createdById;

    private String createdByName;

    private Date businessDate;

    private Date createTime;

    private Date lastUpdateTime;

    private long cashAmountUse;

    private String couponConfirmReceipt;

    private String prepaidConfirmReceipt;

    private String cashConfirmReceipt;

    /*
    *  增加字段 easPurchaseOrderId, easOrderNumber
    * */
    private String easPurchaseOrderId;

    private String easOrderNumber;

    private String tracelog;

    // 采购总金额
    private long totalAmount;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public long getAdCouponAmountUse() {
        return adCouponAmountUse;
    }

    public void setAdCouponAmountUse(long adCouponAmountUse) {
        this.adCouponAmountUse = adCouponAmountUse;
    }

    public long getAdPrepaidAmountUse() {
        return adPrepaidAmountUse;
    }

    public void setAdPrepaidAmountUse(long adPrepaidAmountUse) {
        this.adPrepaidAmountUse = adPrepaidAmountUse;
    }

    public long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
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

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName == null ? null : districtName.trim();
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId == null ? null : currencyId.trim();
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName == null ? null : currencyName.trim();
    }

    public Byte getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Byte paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Byte getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(Byte paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public Byte getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(Byte shippingMode) {
        this.shippingMode = shippingMode;
    }

    public Date getExpectedInboundDate() {
        return expectedInboundDate;
    }

    public void setExpectedInboundDate(Date expectedInboundDate) {
        this.expectedInboundDate = expectedInboundDate;
    }

    public Date getOrderDeadlineDate() {
        return orderDeadlineDate;
    }

    public void setOrderDeadlineDate(Date orderDeadlineDate) {
        this.orderDeadlineDate = orderDeadlineDate;
    }

    public String getBrandOrderNo() {
        return brandOrderNo;
    }

    public void setBrandOrderNo(String brandOrderNo) {
        this.brandOrderNo = brandOrderNo == null ? null : brandOrderNo.trim();
    }

    public String getContractReferenceOrderNo() {
        return contractReferenceOrderNo;
    }

    public void setContractReferenceOrderNo(String contractReferenceOrderNo) {
        this.contractReferenceOrderNo = contractReferenceOrderNo == null ? null : contractReferenceOrderNo.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public int getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(int purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public long getCouponAmountUse() {
        return couponAmountUse;
    }

    public void setCouponAmountUse(long couponAmountUse) {
        this.couponAmountUse = couponAmountUse;
    }

    public long getPrepaidAmountUse() {
        return prepaidAmountUse;
    }

    public void setPrepaidAmountUse(long prepaidAmountUse) {
        this.prepaidAmountUse = prepaidAmountUse;
    }

    public long getEstimatedCouponTotalAmount() {
        return estimatedCouponTotalAmount;
    }

    public void setEstimatedCouponTotalAmount(long estimatedCouponTotalAmount) {
        this.estimatedCouponTotalAmount = estimatedCouponTotalAmount;
    }

    public long getPurchaseGuideAmount() {
        return purchaseGuideAmount;
    }

    public void setPurchaseGuideAmount(long purchaseGuideAmount) {
        this.purchaseGuideAmount = purchaseGuideAmount;
    }

    public long getPurchasePrivilegeAmount() {
        return purchasePrivilegeAmount;
    }

    public void setPurchasePrivilegeAmount(long purchasePrivilegeAmount) {
        this.purchasePrivilegeAmount = purchasePrivilegeAmount;
    }

    public long getReturnCash() {
        return returnCash;
    }

    public void setReturnCash(long returnCash) {
        this.returnCash = returnCash;
    }

    public long getPurchaseShouldPayAmount() {
        return purchaseShouldPayAmount;
    }

    public void setPurchaseShouldPayAmount(long purchaseShouldPayAmount) {
        this.purchaseShouldPayAmount = purchaseShouldPayAmount;
    }

    public long getPurchaseActualPayAmount() {
        return purchaseActualPayAmount;
    }

    public void setPurchaseActualPayAmount(long purchaseActualPayAmount) {
        this.purchaseActualPayAmount = purchaseActualPayAmount;
    }

    public String getCouponActivityInfo() {
        return couponActivityInfo;
    }

    public void setCouponActivityInfo(String couponActivityInfo) {
        this.couponActivityInfo = couponActivityInfo == null ? null : couponActivityInfo.trim();
    }

    public Byte getPaymentFlag() {
        return paymentFlag;
    }

    public void setPaymentFlag(Byte paymentFlag) {
        this.paymentFlag = paymentFlag;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo == null ? null : productInfo.trim();
    }

    public int getInTransitQuantity() {
        return inTransitQuantity;
    }

    public void setInTransitQuantity(int inTransitQuantity) {
        this.inTransitQuantity = inTransitQuantity;
    }

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public int getCanceledQuantity() {
        return canceledQuantity;
    }

    public void setCanceledQuantity(int canceledQuantity) {
        this.canceledQuantity = canceledQuantity;
    }

    public int getReturnedQuantity() {
        return returnedQuantity;
    }

    public void setReturnedQuantity(int returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }

    public int getUnhandledQuantity() {
        return unhandledQuantity;
    }

    public void setUnhandledQuantity(int unhandledQuantity) {
        this.unhandledQuantity = unhandledQuantity;
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

    public long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(long createdById) {
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

    public long getCashAmountUse() { return cashAmountUse; }

    public void setCashAmountUse(long cashAmountUse) { this.cashAmountUse = cashAmountUse; }

    public String getCouponConfirmReceipt() { return couponConfirmReceipt; }

    public void setCouponConfirmReceipt(String couponConfirmReceipt) { this.couponConfirmReceipt = couponConfirmReceipt; }

    public String getPrepaidConfirmReceipt() { return prepaidConfirmReceipt; }

    public void setPrepaidConfirmReceipt(String prepaidConfirmReceipt) { this.prepaidConfirmReceipt = prepaidConfirmReceipt; }

    public String getCashConfirmReceipt() { return cashConfirmReceipt; }

    public void setCashConfirmReceipt(String cashConfirmReceipt) { this.cashConfirmReceipt = cashConfirmReceipt; }

    public String getEasPurchaseOrderId() { return easPurchaseOrderId; }

    public void setEasPurchaseOrderId(String easPurchaseOrderId) { this.easPurchaseOrderId = easPurchaseOrderId; }

    public String getEasOrderNumber() { return easOrderNumber; }

    public void setEasOrderNumber(String easOrderNumber) { this.easOrderNumber = easOrderNumber; }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    public byte getEasStatus() {
        return easStatus;
    }

    public void setEasStatus(byte easStatus) {
        this.easStatus = easStatus;
    }

    public byte getSupplierReceipt() {
        return supplierReceipt;
    }

    public void setSupplierReceipt(byte supplierReceipt) {
        this.supplierReceipt = supplierReceipt;
    }

    public long getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(long cashAmount) {
        this.cashAmount = cashAmount;
    }
}