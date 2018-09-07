package com.yhglobal.gongxiao.phjd.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.yhglobal.gongxiao.utils.NumberFormat;

import java.util.Date;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 16:56 2018/6/29
 */
public class PurchasePlanBean {
    Long purchasePlanId;
    String purchasePlanNo;
    Long supplierId;
    String supplierName;
    Double expectedPayAmount;
    Double applyPayAmount;
    Double realPayAmount;
    Double orderAmount;
    Double orderCashAmount;
    Double orderPurchaseReserveAmount;
    Double orderCouponAmount;
    Double orderPrepaidAmount;
    Double cashSurplusAmount;
    Integer status;
    @JSONField(format="yyyy-MM-dd HH:mm")
    Date createTime;
    String createdByName;


    public String getExpectedPayAmountStr(){
        return NumberFormat.format(this.expectedPayAmount,"###0.00");
    }
    public String getApplyPayAmountStr(){
        return NumberFormat.format(this.applyPayAmount,"###0.00");
    }
    public String getRealPayAmountStr(){
        return NumberFormat.format(this.realPayAmount,"###0.00");
    }
    public String getOrderAmountStr(){
        return NumberFormat.format(this.orderAmount,"###0.00");
    }
    public String getOrderCashAmountStr(){
        return NumberFormat.format(this.orderCashAmount,"###0.00");
    }
    public String getOrderPurchaseReserveAmountStr(){
        return NumberFormat.format(this.orderPurchaseReserveAmount,"###0.00");
    }
    public String getOrderCouponAmountStr(){
        return NumberFormat.format(this.orderCouponAmount,"###0.00");
    }
    public String getOrderPrepaidAmountStr(){
        return NumberFormat.format(this.orderPrepaidAmount,"###0.00");
    }
    public String getCashSurplusAmountStr(){
        return NumberFormat.format(this.cashSurplusAmount,"###0.00");
    }
    public Long getPurchasePlanId() {
        return purchasePlanId;
    }

    public void setPurchasePlanId(Long purchasePlanId) {
        this.purchasePlanId = purchasePlanId;
    }

    public String getPurchasePlanNo() {
        return purchasePlanNo;
    }

    public void setPurchasePlanNo(String purchasePlanNo) {
        this.purchasePlanNo = purchasePlanNo;
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
        this.supplierName = supplierName;
    }

    public Double getExpectedPayAmount() {
        return expectedPayAmount;
    }

    public void setExpectedPayAmount(Double expectedPayAmount) {
        this.expectedPayAmount = expectedPayAmount;
    }

    public Double getApplyPayAmount() {
        return applyPayAmount;
    }

    public void setApplyPayAmount(Double applyPayAmount) {
        this.applyPayAmount = applyPayAmount;
    }

    public Double getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(Double realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getOrderCashAmount() {
        return orderCashAmount;
    }

    public void setOrderCashAmount(Double orderCashAmount) {
        this.orderCashAmount = orderCashAmount;
    }

    public Double getOrderPurchaseReserveAmount() {
        return orderPurchaseReserveAmount;
    }

    public void setOrderPurchaseReserveAmount(Double orderPurchaseReserveAmount) {
        this.orderPurchaseReserveAmount = orderPurchaseReserveAmount;
    }

    public Double getOrderCouponAmount() {
        return orderCouponAmount;
    }

    public void setOrderCouponAmount(Double orderCouponAmount) {
        this.orderCouponAmount = orderCouponAmount;
    }

    public Double getOrderPrepaidAmount() {
        return orderPrepaidAmount;
    }

    public void setOrderPrepaidAmount(Double orderPrepaidAmount) {
        this.orderPrepaidAmount = orderPrepaidAmount;
    }

    public Double getCashSurplusAmount() {
        return cashSurplusAmount;
    }

    public void setCashSurplusAmount(Double cashSurplusAmount) {
        this.cashSurplusAmount = cashSurplusAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
}
