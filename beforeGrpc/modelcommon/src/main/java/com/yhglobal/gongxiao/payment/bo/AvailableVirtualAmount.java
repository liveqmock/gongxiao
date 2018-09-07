package com.yhglobal.gongxiao.payment.bo;

import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;

import java.io.Serializable;
import java.util.List;

/**
 * 可用虚拟金额
 * （用于销售下单时，查询AD返利、代垫余额）
 *
 * @author: 葛灿
 */
public class AvailableVirtualAmount implements Serializable {
    /**
     * 返利金额
     */
    private long couponAmount;
    private double couponAmountDouble;
    /**
     * 代垫金额
     */
    private long prepaidAmount;
    private double prepaidAmountDouble;
    private double couponRateDouble;
    private double prepaidRateDouble;
    /**
     * 预售商品信息，新增销售单页面使用
     */
    private List<SalesPlanItem> salesPlanItems;
    /**本次交易使用的返利    页面使用*/
    private double usedCoupon;
    /**本次交易使用的代垫    页面使用*/
    private double usedPrepaid;
    /**销售指导金额   页面使用*/
    private double salesGuideSubtotal;
    /**销售金额   页面使用*/
    private double  salesSubtotal ;
    /**销售应收金额   页面使用*/
    private double  salesShouldReceiveSubtotal ;
    /**销售出货金额   页面使用*/
    private double  salesExportSubtotal ;

    public double getSalesGuideSubtotal() {
        return salesGuideSubtotal;
    }

    public void setSalesGuideSubtotal(double salesGuideSubtotal) {
        this.salesGuideSubtotal = salesGuideSubtotal;
    }

    public double getSalesSubtotal() {
        return salesSubtotal;
    }

    public void setSalesSubtotal(double salesSubtotal) {
        this.salesSubtotal = salesSubtotal;
    }

    public double getSalesShouldReceiveSubtotal() {
        return salesShouldReceiveSubtotal;
    }

    public void setSalesShouldReceiveSubtotal(double salesShouldReceiveSubtotal) {
        this.salesShouldReceiveSubtotal = salesShouldReceiveSubtotal;
    }

    public double getSalesExportSubtotal() {
        return salesExportSubtotal;
    }

    public void setSalesExportSubtotal(double salesExportSubtotal) {
        this.salesExportSubtotal = salesExportSubtotal;
    }

    public double getUsedCoupon() {
        return usedCoupon;
    }

    public void setUsedCoupon(double usedCoupon) {
        this.usedCoupon = usedCoupon;
    }

    public double getUsedPrepaid() {
        return usedPrepaid;
    }

    public void setUsedPrepaid(double usedPrepaid) {
        this.usedPrepaid = usedPrepaid;
    }

    public List<SalesPlanItem> getSalesPlanItems() {
        return salesPlanItems;
    }

    public void setSalesPlanItems(List<SalesPlanItem> salesPlanItems) {
        this.salesPlanItems = salesPlanItems;
    }

    public double getCouponAmountDouble() {
        return couponAmountDouble;
    }

    public void setCouponAmountDouble(double couponAmountDouble) {
        this.couponAmountDouble = couponAmountDouble;
    }

    public double getPrepaidAmountDouble() {
        return prepaidAmountDouble;
    }

    public void setPrepaidAmountDouble(double prepaidAmountDouble) {
        this.prepaidAmountDouble = prepaidAmountDouble;
    }

    public double getCouponRateDouble() {
        return couponRateDouble;
    }

    public void setCouponRateDouble(double couponRateDouble) {
        this.couponRateDouble = couponRateDouble;
    }

    public double getPrepaidRateDouble() {
        return prepaidRateDouble;
    }

    public void setPrepaidRateDouble(double prepaidRateDouble) {
        this.prepaidRateDouble = prepaidRateDouble;
    }

    public long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public long getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(long prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }
}
