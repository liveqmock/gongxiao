package com.yhglobal.gongxiao.purchase.controller.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PurchaseEditDetail implements Serializable {
    //basic data
    private String projectId; //1
    private String projectName;//1
    private String supplierId;
    private String supplierName;
    private String warehouseId;
    private String warehouseName;
    private String warehouseAddress;
    private Date businessDate;//1
    private String purchaseType;//1
    private Date expectedInboundDate;
    private Date orderDeadlineDate;
    private String supplierOrderNo;//1
    private String contractReferenceOrderNo;//1

    //余额
    private String accountCouponAmount;
    private String accountPrepaidAmount;
    //money
    private int purchaseCategory;//1
    private int purchaseTotalNumber;//1
    private String couponAmountUse;//1
    private String prepaidAmountUse;//1
    private String adCouponAmountUse;//1
    private String adPrepaidAmountUse;//1
    private String purchaseGuideAmount;//采购指导金额 //1
    private String purchaseShouldPayAmount; //采购应付金额    //1
    private String purchasePrivilegeAmount;//采购优惠   //1
    private String purchaseActualPayAmount;//采购实付   //1
    private String returnCash;  //现金返点金额    //1
    private String cashAmountUse;//现金使用金额


    private List<PurchaseItemVo> itemList;  //1

    public String getCashAmountUse() {
        return cashAmountUse;
    }

    public void setCashAmountUse(String cashAmountUse) {
        this.cashAmountUse = cashAmountUse;
    }

    public String getAccountCouponAmount() {
        return accountCouponAmount;
    }

    public void setAccountCouponAmount(String accountCouponAmount) {
        this.accountCouponAmount = accountCouponAmount;
    }

    public String getAccountPrepaidAmount() {
        return accountPrepaidAmount;
    }

    public void setAccountPrepaidAmount(String accountPrepaidAmount) {
        this.accountPrepaidAmount = accountPrepaidAmount;
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

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
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

    public int getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(int purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public int getPurchaseTotalNumber() {
        return purchaseTotalNumber;
    }

    public void setPurchaseTotalNumber(int purchaseTotalNumber) {
        this.purchaseTotalNumber = purchaseTotalNumber;
    }

    public String getCouponAmountUse() {
        return couponAmountUse;
    }

    public void setCouponAmountUse(String couponAmountUse) {
        this.couponAmountUse = couponAmountUse;
    }

    public String getPrepaidAmountUse() {
        return prepaidAmountUse;
    }

    public void setPrepaidAmountUse(String prepaidAmountUse) {
        this.prepaidAmountUse = prepaidAmountUse;
    }

    public String getAdCouponAmountUse() {
        return adCouponAmountUse;
    }

    public void setAdCouponAmountUse(String adCouponAmountUse) {
        this.adCouponAmountUse = adCouponAmountUse;
    }

    public String getAdPrepaidAmountUse() {
        return adPrepaidAmountUse;
    }

    public void setAdPrepaidAmountUse(String adPrepaidAmountUse) {
        this.adPrepaidAmountUse = adPrepaidAmountUse;
    }

    public String getPurchaseGuideAmount() {
        return purchaseGuideAmount;
    }

    public void setPurchaseGuideAmount(String purchaseGuideAmount) {
        this.purchaseGuideAmount = purchaseGuideAmount;
    }

    public String getPurchaseShouldPayAmount() {
        return purchaseShouldPayAmount;
    }

    public void setPurchaseShouldPayAmount(String purchaseShouldPayAmount) {
        this.purchaseShouldPayAmount = purchaseShouldPayAmount;
    }

    public String getPurchasePrivilegeAmount() {
        return purchasePrivilegeAmount;
    }

    public void setPurchasePrivilegeAmount(String purchasePrivilegeAmount) {
        this.purchasePrivilegeAmount = purchasePrivilegeAmount;
    }

    public String getPurchaseActualPayAmount() {
        return purchaseActualPayAmount;
    }

    public void setPurchaseActualPayAmount(String purchaseActualPayAmount) {
        this.purchaseActualPayAmount = purchaseActualPayAmount;
    }

    public String getReturnCash() {
        return returnCash;
    }

    public void setReturnCash(String returnCash) {
        this.returnCash = returnCash;
    }

    public List<PurchaseItemVo> getItemList() {
        return itemList;
    }

    public void setItemList(List<PurchaseItemVo> itemList) {
        this.itemList = itemList;
    }
}
