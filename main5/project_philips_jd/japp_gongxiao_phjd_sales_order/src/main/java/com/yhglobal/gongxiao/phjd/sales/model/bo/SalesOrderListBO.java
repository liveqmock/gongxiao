package com.yhglobal.gongxiao.phjd.sales.model.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用于listSalesOrderByDistributor返回值
 * 
 * @author weizecheng
 * @date 2018/8/22 11:19
 */
public class SalesOrderListBO implements Serializable {

    /**
     * 主键id
     */
    private Long salesOrderId;
    /**
     * 销售单状态
     * 对应枚举类 SalesOrderStatusEnum
     */
    private Integer salesOrderStatus;
    /**
     * 结算方式
     * 对应枚举类，SalesOrderSettlementModeEnum
     */
    private Integer settlementMode;
    /**
     * 销售单号
     */
    private String salesOrderNo;
    /**
     * 经销商名称
     */
    private String distributorName;

    /**
     * 账期天数
     */
    private Integer paymentDays;
    /**
     * 客户采购号 --EDI生成
     */
    private String purchaseNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 订单属性
     */
    private String salesOrderAttribute;

    /**
     * 剩余待预约发货的商品数量
     */
    private Integer unhandledQuantity;

    /**
     * 订单总数量
     */
    private Integer totalQuantity;

    /**
     * 付款时间
     */
    private Date paidTime;

    /**
     * 使用现金金额
     */
    private BigDecimal cashAmount;

    /**
     * 使用现金金额 双精度数据
     */
    private String cashAmountDouble;

    /**
     * 采购总金额
     */
    private BigDecimal totalOrderAmount;

    /**
     * 采购总金额 双精度数据
     */
    private String totalOrderAmountDouble;

    public String getCashAmountDouble() {
        return cashAmountDouble;
    }

    public void setCashAmountDouble(String cashAmountDouble) {
        this.cashAmountDouble = cashAmountDouble;
    }

    public String getTotalOrderAmountDouble() {
        return totalOrderAmountDouble;
    }

    public void setTotalOrderAmountDouble(String totalOrderAmountDouble) {
        this.totalOrderAmountDouble = totalOrderAmountDouble;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Integer getSalesOrderStatus() {
        return salesOrderStatus;
    }

    public void setSalesOrderStatus(Integer salesOrderStatus) {
        this.salesOrderStatus = salesOrderStatus;
    }

    public Integer getSettlementMode() {
        return settlementMode;
    }

    public void setSettlementMode(Integer settlementMode) {
        this.settlementMode = settlementMode;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSalesOrderAttribute() {
        return salesOrderAttribute;
    }

    public void setSalesOrderAttribute(String salesOrderAttribute) {
        this.salesOrderAttribute = salesOrderAttribute;
    }

    public Integer getUnhandledQuantity() {
        return unhandledQuantity;
    }

    public void setUnhandledQuantity(Integer unhandledQuantity) {
        this.unhandledQuantity = unhandledQuantity;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Date getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(Date paidTime) {
        this.paidTime = paidTime;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SalesOrderListBO{");
        sb.append("salesOrderId=").append(salesOrderId);
        sb.append(", salesOrderStatus=").append(salesOrderStatus);
        sb.append(", settlementMode=").append(settlementMode);
        sb.append(", salesOrderNo='").append(salesOrderNo).append('\'');
        sb.append(", distributorName='").append(distributorName).append('\'');
        sb.append(", paymentDays=").append(paymentDays);
        sb.append(", purchaseNo='").append(purchaseNo).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", salesOrderAttribute=").append(salesOrderAttribute);
        sb.append(", unhandledQuantity=").append(unhandledQuantity);
        sb.append(", totalQuantity=").append(totalQuantity);
        sb.append(", paidTime=").append(paidTime);
        sb.append(", cashAmount=").append(cashAmount);
        sb.append(", cashAmountDouble='").append(cashAmountDouble).append('\'');
        sb.append(", totalOrderAmount=").append(totalOrderAmount);
        sb.append(", totalOrderAmountDouble='").append(totalOrderAmountDouble).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
