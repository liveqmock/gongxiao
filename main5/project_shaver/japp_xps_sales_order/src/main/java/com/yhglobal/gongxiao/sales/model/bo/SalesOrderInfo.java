package com.yhglobal.gongxiao.sales.model.bo;



import com.yhglobal.gongxiao.common.ExcelField;
import com.yhglobal.gongxiao.constant.sales.SalesOrderStatusEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售单信息BO对象
 * 用于 销售订单--订单管理 页面
 *
 * @Author: 葛灿
 */
public class SalesOrderInfo implements Serializable {

    /**
     * 销售单号
     */
    @ExcelField(name = "销售单号")
    private String salesOrderNo;
    /**
     * 销售单状态
     * 0--待审核
     * 1--订单审核完毕
     * 2--水单上传完毕
     * 3--已收款
     * 4--已下发仓库
     * 5--仓库已处理
     * 6--已派车
     * 7--已出库
     * 8--已签收
     * 9--已驳回
     */

    private int salesOrderStatus;
    @ExcelField(name = "订单状态")
    private String salesOrderStatusStr;
    /**
     * 采购总金额(浮点数)
     */
    @ExcelField(name = "采购总金额")
    private double totalOrderAmountDouble;
    /**
     * 优惠总额
     */
    @ExcelField(name = "优惠总额")
    private Double discountAmountTotal;
    /**
     * 使用的返利金额
     */
    @ExcelField(name = "返利金额")
    private double couponAmountDouble;

    /**
     * 使用的代垫金额
     */
    @ExcelField(name = "代垫金额")
    private double prepaidAmountDouble;



    /**
     * 经销商名称
     */
    @ExcelField(name = "经销商名称")
    private String distributorName;
    /**
     * 下单时间
     */
    @ExcelField(name = "下单时间")
    private Date createTime;
    /**
     * 付款时间
     */
    @ExcelField(name = "付款时间")
    private Date paidTime;
    /**
     * 商品总件数
     */
    @ExcelField(name = "商品总件数")
    private Integer totalQuantity;
    /**
     * 剩余待预约发货的商品数量
     */
    @ExcelField(name = " 剩余发货数量")
    private Integer unhandledQuantity;
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
    public String getSalesOrderStatusStr() {
        return SalesOrderStatusEnum.getMessage(this.getSalesOrderStatus());
    }

    public void setSalesOrderStatusStr(String salesOrderStatusStr) {
        this.salesOrderStatusStr = salesOrderStatusStr;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public int getSalesOrderStatus() {
        return salesOrderStatus;
    }

    public void setSalesOrderStatus(int salesOrderStatus) {
        this.salesOrderStatus = salesOrderStatus;
    }

    public double getTotalOrderAmountDouble() {
        return totalOrderAmountDouble;
    }

    public void setTotalOrderAmountDouble(double totalOrderAmountDouble) {
        this.totalOrderAmountDouble = totalOrderAmountDouble;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(Date paidTime) {
        this.paidTime = paidTime;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getUnhandledQuantity() {
        return unhandledQuantity;
    }

    public void setUnhandledQuantity(Integer unhandledQuantity) {
        this.unhandledQuantity = unhandledQuantity;
    }
    public Double getDiscountAmountTotal() {
        return discountAmountTotal;
    }

    public void setDiscountAmountTotal(Double discountAmountTotal) {
        this.discountAmountTotal = discountAmountTotal;
    }
}
