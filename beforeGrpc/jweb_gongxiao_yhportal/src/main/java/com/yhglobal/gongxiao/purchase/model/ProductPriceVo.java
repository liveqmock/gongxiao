package com.yhglobal.gongxiao.purchase.model;

import java.util.Date;

/**
 * 产品价格VO
 *
 * @author: 陈浩
 **/
public class ProductPriceVo {
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

    private String guidePrice;

    private String listedPrice;

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
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
        this.productCode = productCode;
    }

    public String getEasCode() {
        return easCode;
    }

    public void setEasCode(String easCode) {
        this.easCode = easCode;
    }

    public String getEasName() {
        return easName;
    }

    public void setEasName(String easName) {
        this.easName = easName;
    }

    public String getEasModel() {
        return easModel;
    }

    public void setEasModel(String easModel) {
        this.easModel = easModel;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
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
        this.brandName = brandName;
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

    public String getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(String guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(String listedPrice) {
        this.listedPrice = listedPrice;
    }

    public String getHistoryGuidancePriceInfo() {
        return historyGuidancePriceInfo;
    }

    public void setHistoryGuidancePriceInfo(String historyGuidancePriceInfo) {
        this.historyGuidancePriceInfo = historyGuidancePriceInfo;
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
}
