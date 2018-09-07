package com.yhglobal.gongxiao.sales.model.plan.vo;

import java.io.Serializable;

public class CustomerPlanInfo implements Serializable {

    private String brandName;

    private String brandId;

    private String projectId;

    private String projectName;

    private String distributorId;

    private String distributorName;

    private int onSaleQuantity; //可售总数

    private int onSaleCategory; //可售种类

    private int saleOccupationQuantity; //销售占用

    private int soldQuantity; //已售数量

    private int remainQuantity; //剩余可售数量

    public int getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(int remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
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

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public int getOnSaleQuantity() {
        return onSaleQuantity;
    }

    public void setOnSaleQuantity(int onSaleQuantity) {
        this.onSaleQuantity = onSaleQuantity;
    }

    public int getOnSaleCategory() {
        return onSaleCategory;
    }

    public void setOnSaleCategory(int onSaleCategory) {
        this.onSaleCategory = onSaleCategory;
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
}
