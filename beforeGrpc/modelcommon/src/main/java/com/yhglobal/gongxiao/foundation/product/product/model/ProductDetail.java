package com.yhglobal.gongxiao.foundation.product.product.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 货品详情
 *
 * @author: 陈浩
 **/
public class ProductDetail implements Serializable {
    private Long id;

    private Long productBasicId;

    private String projectId;

    private Byte recordStatus;

    private String productName;

    private String productModel;

    private String easCode;

    private String wmsCode;

    private String customerSKUCode;

    private String customerProductCode;

    private String purchaseGuidePrice;

    private String  taxRate;

    private String taxCode;

    private String saleGuidePrice;

    private String actualSaleReturn;

    private String shouldSaleReturn;

    private String factorySendReturn;

    private String costPrice;

    private String outPrice;

    private Byte generateCoupon;

    private Long categoryId;

    private String categoryName;

    private String productLine;

    private Integer productLength;

    private Integer productWidth;

    private Integer productHeight;

    private Integer productWeight;

    private Integer productNetWeight;

    private Integer productGrossWeight;

    private Integer boxLength;

    private Integer boxWidth;

    private Integer boxHeight;

    private Integer boxWeight;

    private Integer guaranteePeriod;

    private Integer packageNumber;

    private Date createTime;

    private Date lastUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductBasicId() {
        return productBasicId;
    }

    public void setProductBasicId(Long productBasicId) {
        this.productBasicId = productBasicId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Byte getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Byte recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getEasCode() {
        return easCode;
    }

    public void setEasCode(String easCode) {
        this.easCode = easCode;
    }

    public String getWmsCode() {
        return wmsCode;
    }

    public void setWmsCode(String wmsCode) {
        this.wmsCode = wmsCode;
    }

    public String getCustomerSKUCode() {
        return customerSKUCode;
    }

    public void setCustomerSKUCode(String customerSKUCode) {
        this.customerSKUCode = customerSKUCode;
    }

    public String getCustomerProductCode() {
        return customerProductCode;
    }

    public void setCustomerProductCode(String customerProductCode) {
        this.customerProductCode = customerProductCode;
    }

    public String getPurchaseGuidePrice() {
        return purchaseGuidePrice;
    }

    public void setPurchaseGuidePrice(String purchaseGuidePrice) {
        this.purchaseGuidePrice = purchaseGuidePrice;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getSaleGuidePrice() {
        return saleGuidePrice;
    }

    public void setSaleGuidePrice(String saleGuidePrice) {
        this.saleGuidePrice = saleGuidePrice;
    }

    public String getActualSaleReturn() {
        return actualSaleReturn;
    }

    public void setActualSaleReturn(String actualSaleReturn) {
        this.actualSaleReturn = actualSaleReturn;
    }

    public String getShouldSaleReturn() {
        return shouldSaleReturn;
    }

    public void setShouldSaleReturn(String shouldSaleReturn) {
        this.shouldSaleReturn = shouldSaleReturn;
    }

    public String getFactorySendReturn() {
        return factorySendReturn;
    }

    public void setFactorySendReturn(String factorySendReturn) {
        this.factorySendReturn = factorySendReturn;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(String outPrice) {
        this.outPrice = outPrice;
    }

    public Byte getGenerateCoupon() {
        return generateCoupon;
    }

    public void setGenerateCoupon(Byte generateCoupon) {
        this.generateCoupon = generateCoupon;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public Integer getProductLength() {
        return productLength;
    }

    public void setProductLength(Integer productLength) {
        this.productLength = productLength;
    }

    public Integer getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(Integer productWidth) {
        this.productWidth = productWidth;
    }

    public Integer getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(Integer productHeight) {
        this.productHeight = productHeight;
    }

    public Integer getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(Integer productWeight) {
        this.productWeight = productWeight;
    }

    public Integer getProductNetWeight() {
        return productNetWeight;
    }

    public void setProductNetWeight(Integer productNetWeight) {
        this.productNetWeight = productNetWeight;
    }

    public Integer getProductGrossWeight() {
        return productGrossWeight;
    }

    public void setProductGrossWeight(Integer productGrossWeight) {
        this.productGrossWeight = productGrossWeight;
    }

    public Integer getBoxLength() {
        return boxLength;
    }

    public void setBoxLength(Integer boxLength) {
        this.boxLength = boxLength;
    }

    public Integer getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(Integer boxWidth) {
        this.boxWidth = boxWidth;
    }

    public Integer getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(Integer boxHeight) {
        this.boxHeight = boxHeight;
    }

    public Integer getBoxWeight() {
        return boxWeight;
    }

    public void setBoxWeight(Integer boxWeight) {
        this.boxWeight = boxWeight;
    }

    public Integer getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteePeriod(Integer guaranteePeriod) {
        this.guaranteePeriod = guaranteePeriod;
    }

    public Integer getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(Integer packageNumber) {
        this.packageNumber = packageNumber;
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

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", productBasicId=" + productBasicId +
                ", projectId=" + projectId +
                ", recordStatus=" + recordStatus +
                ", productName='" + productName + '\'' +
                ", productModel='" + productModel + '\'' +
                ", easCode='" + easCode + '\'' +
                ", wmsCode='" + wmsCode + '\'' +
                ", customerSKUCode='" + customerSKUCode + '\'' +
                ", customerProductCode='" + customerProductCode + '\'' +
                ", purchaseGuidePrice=" + purchaseGuidePrice +
                ", taxRate=" + taxRate +
                ", taxCode='" + taxCode + '\'' +
                ", saleGuidePrice=" + saleGuidePrice +
                ", actualSaleReturn=" + actualSaleReturn +
                ", shouldSaleReturn=" + shouldSaleReturn +
                ", factorySendReturn=" + factorySendReturn +
                ", costPrice=" + costPrice +
                ", outPrice=" + outPrice +
                ", generateCoupon=" + generateCoupon +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", productLine='" + productLine + '\'' +
                ", productLength=" + productLength +
                ", productWidth=" + productWidth +
                ", productHeight=" + productHeight +
                ", productWeight=" + productWeight +
                ", productNetWeight=" + productNetWeight +
                ", productGrossWeight=" + productGrossWeight +
                ", boxLength=" + boxLength +
                ", boxWidth=" + boxWidth +
                ", boxHeight=" + boxHeight +
                ", boxWeight=" + boxWeight +
                ", guaranteePeriod=" + guaranteePeriod +
                ", packageNumber=" + packageNumber +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
