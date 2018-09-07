package com.yhglobal.gongxiao.plansale.controller.vo;

import java.io.Serializable;

/**
 * 销售计划单wet -> system
 *
 * @author: 陈浩
 * @create: 2018-03-13 14:15
 **/
public class SalePlanInfoIn implements Serializable {
    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 货品编码
     */
    private String productCode;
    /**
     * 可售总数
     */
    private Integer onSaleQuantity;
    /**
     * 有效日期起
     */
    private String  startTime;
    /**
     * 有效日期止
     */
    private String endTime;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getOnSaleQuantity() {
        return onSaleQuantity;
    }

    public void setOnSaleQuantity(Integer onSaleQuantity) {
        this.onSaleQuantity = onSaleQuantity;
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
