package com.yhglobal.gongxiao.sales.model.plan.model;

import java.io.Serializable;
import java.util.Date;

public class SalesPlan implements Serializable {
    /**
     * 销售计划单id
     */
    private Long salesPlanId;
    /**
     * 计划单状态
     */
    private Byte salesPlanStatus;
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
     * 品牌Id
     */
    private int brandId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 供应商Id
     */
    private Long supplierId;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 商品分类
     */
    private Byte productCategory;
    /**
     * 商品id
     */
    private Long productId;
    /**
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
    private long guidePrice;
    /**
     * 销售指导价
     */
    private long saleGuidePrice;
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
     * 仓库库存数量
     */
    private int inStockQuantity;
    /**
     * 有效日期起
     */
    private Date startTime;
    /**
     * 有效日期止
     */
    private Date endTime;

    private Long dataVersion;

    private Long createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;
    /**
     * 操作日记
     */
    private String tracelog;

    public Long getSalesPlanId() {
        return salesPlanId;
    }

    public void setSalesPlanId(Long salesPlanId) {
        this.salesPlanId = salesPlanId;
    }

    public Byte getSalesPlanStatus() {
        return salesPlanStatus;
    }

    public void setSalesPlanStatus(Byte salesPlanStatus) {
        this.salesPlanStatus = salesPlanStatus;
    }

    public String getSalesPlanNo() {
        return salesPlanNo;
    }

    public void setSalesPlanNo(String salesPlanNo) {
        this.salesPlanNo = salesPlanNo == null ? null : salesPlanNo.trim();
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
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public Byte getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Byte productCategory) {
        this.productCategory = productCategory;
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
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public long getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(long guidePrice) {
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

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName == null ? null : createdByName.trim();
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

    public long getSaleGuidePrice() {
        return saleGuidePrice;
    }

    public void setSaleGuidePrice(long saleGuidePrice) {
        this.saleGuidePrice = saleGuidePrice;
    }
}