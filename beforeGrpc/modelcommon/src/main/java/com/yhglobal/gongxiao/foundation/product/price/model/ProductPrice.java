package com.yhglobal.gongxiao.foundation.product.price.model;

import java.io.Serializable;
import java.util.Date;

public class ProductPrice implements Serializable {
    private Long productId;

    private String projectId;

    private String projectName;

    private String productName;

    private Integer orderStatus;

    private String productCode;

    private String easCode;

    private String easName;

    private String easModel;

    private String barCode;

    private Long brandId;

    private String brandName;

    private Long categoryId;

    private String categoryName;

    private Integer guidePrice;

    private Integer listedPrice;

    private String historyGuidancePriceInfo;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
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

    public Integer getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(Integer guidePrice) {
        this.guidePrice = guidePrice;
    }

    public Integer getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(Integer listedPrice) {
        this.listedPrice = listedPrice;
    }

    public String getHistoryGuidancePriceInfo() {
        return historyGuidancePriceInfo;
    }

    public void setHistoryGuidancePriceInfo(String historyGuidancePriceInfo) {
        this.historyGuidancePriceInfo = historyGuidancePriceInfo == null ? null : historyGuidancePriceInfo.trim();
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