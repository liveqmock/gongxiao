package com.yhglobal.gongxiao.foundation.product.product.model;

import java.io.Serializable;
import java.util.Date;

public class ProductBasic implements Serializable {
    private Long id;

    private String easCode;

    private String wmsCode;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}