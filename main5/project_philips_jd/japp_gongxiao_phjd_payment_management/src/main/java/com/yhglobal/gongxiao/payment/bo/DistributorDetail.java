package com.yhglobal.gongxiao.payment.bo;

import com.yhglobal.gongxiao.utils.NumberFormat;

import java.io.Serializable;

/**
 * @author 王帅
 */
public class DistributorDetail implements Serializable {

    // 客户明细页显示的对象数据
    // id对应客户账号
    // 关联的项目ID
    private Long projectId;

    private String distributorId;

    private String distributorName;

    private String userId;
    //  现金账户
    // 返利账户余额

    // 返利余额可用比例
    //   代垫账户余额
    // 代垫余额可用比例
    private Long cashAmount;
    private Double cashAmountDouble;

    private Long couponAccountAmount;
    private Double couponAmountDouble;

    private String couponUseableRate;
    private Long prepaidAmount;
    private Double prepaidAmountDouble;
    private String prepaidUseableRate;

    private String cashAmountDoubleString;
    private String couponAmountDoubleString;
    private String prepaidAmountDoubleString;

    public void setCashAmountDoubleString(String cashAmountDoubleString) {
        this.cashAmountDoubleString = cashAmountDoubleString;
    }

    public void setCouponAmountDoubleString(String couponAmountDoubleString) {
        this.couponAmountDoubleString = couponAmountDoubleString;
    }

    public void setPrepaidAmountDoubleString(String prepaidAmountDoubleString) {
        this.prepaidAmountDoubleString = prepaidAmountDoubleString;
    }

    public String getCashAmountDoubleString(){
        return NumberFormat.format(this.cashAmountDouble,"#,##0.00");
    }

    public String getCouponAmountDoubleString(){
        return NumberFormat.format(this.couponAmountDouble,"#,##0.00");
    }

    public String getPrepaidAmountDoubleString(){
        return NumberFormat.format(this.prepaidAmountDouble,"#,##0.00");
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getCashAmountDouble() {
        return cashAmountDouble;
    }

    public void setCashAmountDouble(Double cashAmountDouble) {
        this.cashAmountDouble = cashAmountDouble;
    }

    public Double getCouponAmountDouble() {
        return couponAmountDouble;
    }

    public void setCouponAmountDouble(Double couponAmountDouble) {
        this.couponAmountDouble = couponAmountDouble;
    }

    public Double getPrepaidAmountDouble() {
        return prepaidAmountDouble;
    }

    public void setPrepaidAmountDouble(Double prepaidAmountDouble) {
        this.prepaidAmountDouble = prepaidAmountDouble;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public Long getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Long cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Long getCouponAccountAmount() {
        return couponAccountAmount;
    }

    public void setCouponAccountAmount(Long couponAccountAmount) {
        this.couponAccountAmount = couponAccountAmount;
    }

    public String getCouponUseableRate() {
        return couponUseableRate;
    }

    public void setCouponUseableRate(String couponUseableRate) {
        this.couponUseableRate = couponUseableRate;
    }

    public Long getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(Long prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public String getPrepaidUseableRate() {
        return prepaidUseableRate;
    }

    public void setPrepaidUseableRate(String prepaidUseableRate) {
        this.prepaidUseableRate = prepaidUseableRate;
    }


}
