package com.yhglobal.gongxiao.foundation.product.basic.model;

import java.io.Serializable;
import java.util.Date;

public class ProductBasic implements Serializable {
    private long id;

    private String productName;

    private Integer status;

    private String productCode;

    private String productModel;

    private String easCode;

    private String easName;

    private String easModel;

    private String easUnitCode;

    private String WMSCode;

    private String p12NC;

    private String easMaterialCode;

    private long guidePrice;

    private long listedPrice;

    private long saleGuidePrice;

    private String barCode;

    private String productType;

    private long brandId;

    private String brandName;

    private long categoryId;

    private String categoryName;

    private String productLine;

    private Integer productLength;

    private Integer productWidth;

    private Integer productHeight;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel == null ? null : productModel.trim();
    }

    public String getEasCode() {
        return easCode;
    }

    public void setEasCode(String easCode) {
        this.easCode = easCode == null ? null : easCode.trim();
    }

    public String getEasName() {
        return easName;
    }

    public void setEasName(String easName) {
        this.easName = easName == null ? null : easName.trim();
    }

    public String getEasModel() {
        return easModel;
    }

    public void setEasModel(String easModel) {
        this.easModel = easModel == null ? null : easModel.trim();
    }

    public String getP12NC() {
        return p12NC;
    }

    public void setP12NC(String p12NC) {
        this.p12NC = p12NC == null ? null : p12NC.trim();
    }

    public String getEasMaterialCode() {
        return easMaterialCode;
    }

    public void setEasMaterialCode(String easMaterialCode) {
        this.easMaterialCode = easMaterialCode == null ? null : easMaterialCode.trim();
    }

    public long getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(long guidePrice) {
        this.guidePrice = guidePrice;
    }

    public long getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(long listedPrice) {
        this.listedPrice = listedPrice;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine == null ? null : productLine.trim();
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

    public String getWMSCode() {
        return WMSCode;
    }

    public void setWMSCode(String WMSCode) {
        this.WMSCode = WMSCode;
    }

    public long getSaleGuidePrice() {
        return saleGuidePrice;
    }

    public void setSaleGuidePrice(long saleGuidePrice) {
        this.saleGuidePrice = saleGuidePrice;
    }

    public String getEasUnitCode() {
        return easUnitCode;
    }

    public void setEasUnitCode(String easUnitCode) {
        this.easUnitCode = easUnitCode;
    }
}