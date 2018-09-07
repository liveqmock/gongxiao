package com.yhglobal.gongxiao.phjd.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class PurchaseOrderDetailBean implements Serializable {
    /**
     * 采购单ID
     */
    private Long purchaseOrderId;
    /**
     * 采购单状态
     */
    private Byte orderStatus;
    /**
     * 采购单号
     */
    private String purchaseOrderNo;
    /**
     * 项目id
     */
    private long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 品牌Id
     */
    private Integer brandId;
    /**
     * 品牌名称
     */
    private String brandName;

    private String supplierId;

    private String supplierName;
    /**
     * 付款模式: 1预付费 2后付费
     */
    private Byte paymentMode;
    /**
     * 物流方式 0:不详 1:自提 2:第三方物流
     */
    private Byte shippingMode;
    /**
     * 业务发生时间
     */
    private Date businessDate;
    /**
     * 期望入库(到货)日期
     */
    private Date expectedInboundDate;
    /**
     * 采购单截止时间
     */
    private Date orderDeadlineDate;
    /**
     * 仓库ID
     */
    private int warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 仓库地址
     */
    private String address;
    /**
     * 品牌商订单号
     */
    private String brandOrderNo;
    /**
     * 合同参考号
     */
    private String contractReferenceOrderNo;
    /**
     * 备注
     */
    private String remark;
    /**
     * 采购商品种类
     */
    private int purchaseCategory;
    /**
     * 采购单总数量
     */
    private String totalQuantity;
    /**
     * 返利使用金额
     */
    private String couponAmountUse;
    /**
     * 代垫使用金额
     */
    private String prepaidAmountUse;
    /**
     * 采购指导金额
     */
    private String purchaseGuideAmount;
    /**
     * 采购优惠金额
     */
    private String purchasePrivilegeAmount;
    /**
     * 现金返点金额
     */
    private String returnCash;
    /**
     * 采购应付金额
     */
    private String purchaseShouldPayAmount;
    /**
     * 采购实付金额
     */
    private String purchaseActualPayAmount;

    /**
     *操作日志
     */
    List<OperateHistoryRecord> traceLogList;
    /**
     * 采购货品列表
     */
    private List<PurchaseItemVo> itemList;

    private String purchaseType;

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Byte getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Byte paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Byte getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(Byte shippingMode) {
        this.shippingMode = shippingMode;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Date getExpectedInboundDate() {
        return expectedInboundDate;
    }

    public void setExpectedInboundDate(Date expectedInboundDate) {
        this.expectedInboundDate = expectedInboundDate;
    }

    public Date getOrderDeadlineDate() {
        return orderDeadlineDate;
    }

    public void setOrderDeadlineDate(Date orderDeadlineDate) {
        this.orderDeadlineDate = orderDeadlineDate;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrandOrderNo() {
        return brandOrderNo;
    }

    public void setBrandOrderNo(String brandOrderNo) {
        this.brandOrderNo = brandOrderNo;
    }

    public String getContractReferenceOrderNo() {
        return contractReferenceOrderNo;
    }

    public void setContractReferenceOrderNo(String contractReferenceOrderNo) {
        this.contractReferenceOrderNo = contractReferenceOrderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(int purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getCouponAmountUse() {
        return couponAmountUse;
    }

    public void setCouponAmountUse(String  couponAmountUse) {
        this.couponAmountUse = couponAmountUse;
    }

    public String getPrepaidAmountUse() {
        return prepaidAmountUse;
    }

    public void setPrepaidAmountUse(String prepaidAmountUse) {
        this.prepaidAmountUse = prepaidAmountUse;
    }

    public String getPurchaseGuideAmount() {
        return purchaseGuideAmount;
    }

    public void setPurchaseGuideAmount(String purchaseGuideAmount) {
        this.purchaseGuideAmount = purchaseGuideAmount;
    }

    public String getPurchasePrivilegeAmount() {
        return purchasePrivilegeAmount;
    }

    public void setPurchasePrivilegeAmount(String purchasePrivilegeAmount) {
        this.purchasePrivilegeAmount = purchasePrivilegeAmount;
    }

    public String getReturnCash() {
        return returnCash;
    }

    public void setReturnCash(String returnCash) {
        this.returnCash = returnCash;
    }

    public String getPurchaseShouldPayAmount() {
        return purchaseShouldPayAmount;
    }

    public void setPurchaseShouldPayAmount(String purchaseShouldPayAmount) {
        this.purchaseShouldPayAmount = purchaseShouldPayAmount;
    }

    public String getPurchaseActualPayAmount() {
        return purchaseActualPayAmount;
    }

    public void setPurchaseActualPayAmount(String purchaseActualPayAmount) {
        this.purchaseActualPayAmount = purchaseActualPayAmount;
    }

    public List<OperateHistoryRecord> getTraceLogList() {
        return traceLogList;
    }

    public void setTraceLogList(List<OperateHistoryRecord> traceLogList) {
        this.traceLogList = traceLogList;
    }

    public List<PurchaseItemVo> getItemList() {
        return itemList;
    }

    public void setItemList(List<PurchaseItemVo> itemList) {
        this.itemList = itemList;
    }
}
