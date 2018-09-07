package com.yhglobal.gongxiao.purchase.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * 采购单信息
 *
 * @author: 陈浩
 * @create: 2018-03-06 11:28
 **/
public class PurchaseOrderInfo implements Serializable {
    private String createPerson;
    /**
     * 采购单号
     */
    private String purchaseOrderNumber;
    /**
     * 品牌ID
     */
    private String brandId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 供应商ID
     */
    private String supplierId;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 订单金额
     */
    private String orderAmount;
    /**
     * 采购种类数目
     */
    private int purchaseCategory;
    /**
     * 采购总数量
     */
    private int purchaseNumber;
    /**
     * 付款方式
     */
    private int paymentMode;
    /**
     * 仓库ID
     */
    private int warehouseId;
    /**
     * 收货仓库
     */
    private String warehouseName;
    /**
     * 采购单状态
     */
    private String  purchaseStatus;
    /**
     * 采购单状态 int
     */
    private int purchaseStatusInt;
    /**
     * 要求到货日期
     */
    private Date requirArrivalDate;
    /**
     * 业务发生日期
     */
    private String  businessDate;

    private String supplierOrderNo;

    private String contractReferenceOrderNo;
    //显示编辑按钮
    private boolean enableEdit;
    //显示作废按钮
    private  boolean enableCancel;
    //显示预约入库按钮
    private boolean enablePlanInStock;
    //显示入库详情按钮
    private boolean enableDetail;

    public String getSupplierOrderNo() {
        return supplierOrderNo;
    }

    public void setSupplierOrderNo(String supplierOrderNo) {
        this.supplierOrderNo = supplierOrderNo;
    }

    public String getContractReferenceOrderNo() {
        return contractReferenceOrderNo;
    }

    public void setContractReferenceOrderNo(String contractReferenceOrderNo) {
        this.contractReferenceOrderNo = contractReferenceOrderNo;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
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

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(int purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public int getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public Date getRequirArrivalDate() {
        return requirArrivalDate;
    }

    public void setRequirArrivalDate(Date requirArrivalDate) {
        this.requirArrivalDate = requirArrivalDate;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public int getPurchaseStatusInt() {
        return purchaseStatusInt;
    }

    public void setPurchaseStatusInt(int purchaseStatusInt) {
        this.purchaseStatusInt = purchaseStatusInt;
    }

    public boolean isEnableEdit() {
        return enableEdit;
    }

    public void setEnableEdit(boolean enableEdit) {
        this.enableEdit = enableEdit;
    }

    public boolean isEnableCancel() {
        return enableCancel;
    }

    public void setEnableCancel(boolean enableCancel) {
        this.enableCancel = enableCancel;
    }

    public boolean isEnablePlanInStock() {
        return enablePlanInStock;
    }

    public void setEnablePlanInStock(boolean enablePlanInStock) {
        this.enablePlanInStock = enablePlanInStock;
    }

    public boolean isEnableDetail() {
        return enableDetail;
    }

    public void setEnableDetail(boolean enableDetail) {
        this.enableDetail = enableDetail;
    }
}
