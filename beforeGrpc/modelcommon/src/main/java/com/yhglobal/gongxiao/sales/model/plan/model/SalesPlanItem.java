package com.yhglobal.gongxiao.sales.model.plan.model;

import java.io.Serializable;
import java.util.Date;

public class SalesPlanItem implements Serializable {
    private Long itemId;

    private Byte itemStatus;

    private String brandId;

    private String brandName;

    private String salesPlanNo;

    private String customerId;

    private String customerName;

    private Long projectId;

    private String projectName;

    private Long supplierId;

    private String supplierName;

    private Long productId;

    private String productCode;

    private String productName;

    private Long distributorId;

    private String distributorName;

    private int onSaleQuantity;

    private int saleOccupationQuantity;

    private int soldQuantity;

    private int freezedQuantity;

    private int unallocatedQuantity;

    private long guidePrice;

    private long saleGuidePrice;

    private double guidePriceDouble;

    private String currencyCode;

    private String currencyName;

    private long brandPrepaidUnit;

    private long brandPrepaidDiscount;

    private Long brandPrepaidAmount;

    private long yhPrepaidUnit;

    private long yhPrepaidDiscount;

    /**
     * 总折扣点
     */
    private double totalDiscountDouble;

    private Long yhPrepaidAmount;

    private long wholesalePrice;
    /**
     * 结算价-页面
     */
    private double wholesalePriceDouble;

    private Date startTime;

    private Date endTime;

    private Long dataVersion;

    private Long createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;
    /**
     * 客户优惠金额，页面计算时使用
     */
    private long customerDiscountAmount;
    /**
     * 客户优惠金额，页面计算时使用
     */
    private double customerDiscountAmountDouble;
    /**
     * 小计金额，页面计算使用
     */
    private double subtotalDouble;

    public double getSubtotalDouble() {
        return subtotalDouble;
    }

    public void setSubtotalDouble(double subtotalDouble) {
        this.subtotalDouble = subtotalDouble;
    }

    public long getCustomerDiscountAmount() {
        return customerDiscountAmount;
    }

    public void setCustomerDiscountAmount(long customerDiscountAmount) {
        this.customerDiscountAmount = customerDiscountAmount;
    }

    public double getCustomerDiscountAmountDouble() {
        return customerDiscountAmountDouble;
    }

    public void setCustomerDiscountAmountDouble(double customerDiscountAmountDouble) {
        this.customerDiscountAmountDouble = customerDiscountAmountDouble;
    }

    public double getGuidePriceDouble() {
        return guidePriceDouble;
    }

    public void setGuidePriceDouble(double guidePriceDouble) {
        this.guidePriceDouble = guidePriceDouble;
    }

    public double getTotalDiscountDouble() {
        return totalDiscountDouble;
    }

    public void setTotalDiscountDouble(double totalDiscountDouble) {
        this.totalDiscountDouble = totalDiscountDouble;
    }

    public double getWholesalePriceDouble() {
        return wholesalePriceDouble;
    }

    public void setWholesalePriceDouble(double wholesalePriceDouble) {
        this.wholesalePriceDouble = wholesalePriceDouble;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Byte getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Byte itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getSalesPlanNo() {
        return salesPlanNo;
    }

    public void setSalesPlanNo(String salesPlanNo) {
        this.salesPlanNo = salesPlanNo == null ? null : salesPlanNo.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName == null ? null : distributorName.trim();
    }

    public int getOnSaleQuantity() {
        return onSaleQuantity;
    }

    public void setOnSaleQuantity(int onSaleQuantity) {
        this.onSaleQuantity = onSaleQuantity;
    }

    public int getSaleOccupationQuantity() {
        return saleOccupationQuantity;
    }

    public void setSaleOccupationQuantity(int saleOccupationQuantity) {
        this.saleOccupationQuantity = saleOccupationQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public int getFreezedQuantity() {
        return freezedQuantity;
    }

    public void setFreezedQuantity(int freezedQuantity) {
        this.freezedQuantity = freezedQuantity;
    }

    public int getUnallocatedQuantity() {
        return unallocatedQuantity;
    }

    public void setUnallocatedQuantity(int unallocatedQuantity) {
        this.unallocatedQuantity = unallocatedQuantity;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public long getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(long guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public long getBrandPrepaidUnit() {
        return brandPrepaidUnit;
    }

    public void setBrandPrepaidUnit(long brandPrepaidUnit) {
        this.brandPrepaidUnit = brandPrepaidUnit;
    }

    public long getBrandPrepaidDiscount() {
        return brandPrepaidDiscount;
    }

    public void setBrandPrepaidDiscount(long brandPrepaidDiscount) {
        this.brandPrepaidDiscount = brandPrepaidDiscount;
    }

    public Long getBrandPrepaidAmount() {
        return brandPrepaidAmount;
    }

    public void setBrandPrepaidAmount(Long brandPrepaidAmount) {
        this.brandPrepaidAmount = brandPrepaidAmount;
    }

    public long getYhPrepaidUnit() {
        return yhPrepaidUnit;
    }

    public void setYhPrepaidUnit(long yhPrepaidUnit) {
        this.yhPrepaidUnit = yhPrepaidUnit;
    }

    public long getYhPrepaidDiscount() {
        return yhPrepaidDiscount;
    }

    public void setYhPrepaidDiscount(long yhPrepaidDiscount) {
        this.yhPrepaidDiscount = yhPrepaidDiscount;
    }

    public Long getYhPrepaidAmount() {
        return yhPrepaidAmount;
    }

    public void setYhPrepaidAmount(Long yhPrepaidAmount) {
        this.yhPrepaidAmount = yhPrepaidAmount;
    }

    public long getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(long wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
        this.createdByName = createdByName;
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
        this.tracelog = tracelog;
    }

    @Override
    public String toString() {
        return "SalesPlanItem{" +
                "itemId=" + itemId +
                ", itemStatus=" + itemStatus +
                ", salesPlanNo='" + salesPlanNo + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", productId=" + productId +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", distributorId=" + distributorId +
                ", distributorName='" + distributorName + '\'' +
                ", onSaleQuantity=" + onSaleQuantity +
                ", saleOccupationQuantity=" + saleOccupationQuantity +
                ", soldQuantity=" + soldQuantity +
                ", freezedQuantity=" + freezedQuantity +
                ", unallocatedQuantity=" + unallocatedQuantity +
                ", guidePrice=" + guidePrice +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", brandPrepaidUnit=" + brandPrepaidUnit +
                ", brandPrepaidDiscount=" + brandPrepaidDiscount +
                ", brandPrepaidAmount=" + brandPrepaidAmount +
                ", yhPrepaidUnit=" + yhPrepaidUnit +
                ", yhPrepaidDiscount=" + yhPrepaidDiscount +
                ", yhPrepaidAmount=" + yhPrepaidAmount +
                ", wholesalePrice=" + wholesalePrice +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", dataVersion=" + dataVersion +
                ", createdById=" + createdById +
                ", createdByName='" + createdByName + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", tracelog='" + tracelog + '\'' +
                '}';
    }

    public long getSaleGuidePrice() {
        return saleGuidePrice;
    }

    public void setSaleGuidePrice(long saleGuidePrice) {
        this.saleGuidePrice = saleGuidePrice;
    }
}