package com.yhglobal.gongxiao.sales.model.plan;

import java.io.Serializable;

/**
 * 客户预售
 *
 * @author: 陈浩
 * @create: 2018-03-09 17:27
 **/
public class SalePlanItemInfo implements Serializable {
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 产品ID
     */
    private String productId;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 销售计划单号
     */
    private String salesPlanNo;
    /**
     * 分配数量
     */
    private int onSaleQuantity;
    /**
     * 客户ID
     */
    private String customerId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 销售占用
     */
    private int saleOccupationQuantity;
    /**
     * 已售数量
     */
    private int soldQuantity;
    /**
     * 待分配的数量
     */
    private int unallocatedQuantity;
    /**
     * 供应商代垫
     */
    private double brandPrepaidUnit ;
    /**
     * 品牌商单价支持折扣
     */
    private double brandPrepaidDiscount;
    /**
     * 越海商单价支持
     */
    private double yhPrepaidUnit;
    /**
     * 越海单价支持折扣
     */
    private double yhPrepaidDiscount;
    /**
     * 有效期起
     */
    private String startTime;
    /**
     * 有效期止
     */
    private String endTime;


    public int getUnallocatedQuantity() {
        return unallocatedQuantity;
    }

    public void setUnallocatedQuantity(int unallocatedQuantity) {
        this.unallocatedQuantity = unallocatedQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSalesPlanNo() {
        return salesPlanNo;
    }

    public void setSalesPlanNo(String salesPlanNo) {
        this.salesPlanNo = salesPlanNo;
    }

    public int getOnSaleQuantity() {
        return onSaleQuantity;
    }

    public void setOnSaleQuantity(int onSaleQuantity) {
        this.onSaleQuantity = onSaleQuantity;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getBrandPrepaidUnit() {
        return brandPrepaidUnit;
    }

    public void setBrandPrepaidUnit(double brandPrepaidUnit) {
        this.brandPrepaidUnit = brandPrepaidUnit;
    }

    public double getBrandPrepaidDiscount() {
        return brandPrepaidDiscount;
    }

    public void setBrandPrepaidDiscount(double brandPrepaidDiscount) {
        this.brandPrepaidDiscount = brandPrepaidDiscount;
    }

    public double getYhPrepaidUnit() {
        return yhPrepaidUnit;
    }

    public void setYhPrepaidUnit(double yhPrepaidUnit) {
        this.yhPrepaidUnit = yhPrepaidUnit;
    }

    public double getYhPrepaidDiscount() {
        return yhPrepaidDiscount;
    }

    public void setYhPrepaidDiscount(double yhPrepaidDiscount) {
        this.yhPrepaidDiscount = yhPrepaidDiscount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getSaleOccupationQuantity() {
        return saleOccupationQuantity;
    }

    public void setSaleOccupationQuantity(int saleOccupationQuantity) {
        this.saleOccupationQuantity = saleOccupationQuantity;
    }
}
