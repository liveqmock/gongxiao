package com.yhglobal.gongxiao.sales.model.plan.dto;

import java.io.Serializable;

/**
 * 销售计划单 system -> web
 *
 * @author: 陈浩
 * @create: 2018-03-08 19:51
 **/
public class SalePlanInfoOut implements Serializable {
    /**
     * 状态 0正常 1作废
     */
    private int orderStatus;
    /**
     * 销售计划单号
     */
    private String salesPlanNo;
    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 商品id
     */
    private Long productId;
    /**
     *
     * 商品编码
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品指导价
     */
    private String guidePrice;

    /**
     * 预售指导价
     */
    private String saleGuidePrice;
    /**
     * 可售总数
     */
    private int onSaleQuantity;
    /**
     * 已分配的数量
     */
    private int allocatedQuantity;
    /**
     * 待分配的数量
     */
    private int unallocatedQuantity;
    /**
     * 货品实物库存
     */
    private int productActualQuantity;
    /**
     * 有效日期起
     */
    private String  startTime;
    /**
     * 有效日期止
     */
    private String endTime;
    /**
     * 创建时间
     */
    private String  createTime;

    public int getProductActualQuantity() {
        return productActualQuantity;
    }

    public void setProductActualQuantity(int productActualQuantity) {
        this.productActualQuantity = productActualQuantity;
    }

    public String getSalesPlanNo() {
        return salesPlanNo;
    }

    public void setSalesPlanNo(String salesPlanNo) {
        this.salesPlanNo = salesPlanNo;
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
        this.projectName = projectName;
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
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(String guidePrice) {
        this.guidePrice = guidePrice;
    }

    public int getOnSaleQuantity() {
        return onSaleQuantity;
    }

    public void setOnSaleQuantity(int onSaleQuantity) {
        this.onSaleQuantity = onSaleQuantity;
    }

    public int getAllocatedQuantity() {
        return allocatedQuantity;
    }

    public void setAllocatedQuantity(int allocatedQuantity) {
        this.allocatedQuantity = allocatedQuantity;
    }

    public int getUnallocatedQuantity() {
        return unallocatedQuantity;
    }

    public void setUnallocatedQuantity(int unallocatedQuantity) {
        this.unallocatedQuantity = unallocatedQuantity;
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

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSaleGuidePrice() {
        return saleGuidePrice;
    }

    public void setSaleGuidePrice(String saleGuidePrice) {
        this.saleGuidePrice = saleGuidePrice;
    }
}
