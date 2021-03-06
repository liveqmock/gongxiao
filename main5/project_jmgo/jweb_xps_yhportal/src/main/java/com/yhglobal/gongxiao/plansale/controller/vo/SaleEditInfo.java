package com.yhglobal.gongxiao.plansale.controller.vo;

import java.io.Serializable;

/**
 * 修改预售计划的模型
 *
 * @author: 陈浩
 * @create: 2018-03-14 14:46
 **/
public class SaleEditInfo implements Serializable {
    /**
     * 预销单号
     */
    private String salePlanNo;
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
     * 预销总数
     */
    private int onSaleQuantity;
    /**
     * 实物库存
     */
    private int inStockQuantity;
    /**
     * 已分配数量
     */
    private int allocatedQuantity;
    /**
     * 有效日期起
     */
    private String  startTime;
    /**
     * 有效日期止
     */
    private String endTime;

    public String getSalePlanNo() {
        return salePlanNo;
    }

    public void setSalePlanNo(String salePlanNo) {
        this.salePlanNo = salePlanNo;
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

    public int getOnSaleQuantity() {
        return onSaleQuantity;
    }

    public void setOnSaleQuantity(int onSaleQuantity) {
        this.onSaleQuantity = onSaleQuantity;
    }

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public int getAllocatedQuantity() {
        return allocatedQuantity;
    }

    public void setAllocatedQuantity(int allocatedQuantity) {
        this.allocatedQuantity = allocatedQuantity;
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
}
