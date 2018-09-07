package com.yhglobal.gongxiao.sales.entity;

import com.yhglobal.gongxiao.constant.sales.SalesOrderStatusEnum;
import com.yhglobal.gongxiao.model.TraceLog;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 销售单详情
 *
 * @Author: 葛灿
 */
public class SalesOrderDetail implements Serializable {
    /**
     * 销售单号
     */
    private String salesOrderNo;
    /**
     * 销售合同号
     */
    private String salesContractNo;
    /**
     * 销售单状态
     * 0--待审核
     * 1--订单审核完毕
     * 2--已收款
     * 3--已下发仓库
     * 4--仓库已处理
     * 5--已派车
     * 6--已出库
     * 7--已签收
     * 9--已驳回
     */
    private Integer salesOrderStatus;
    private String salesOrderStatusString;
    /**
     * 订单总金额(浮点数)
     */
    private String totalOrderAmountDouble;
    /**
     * 使用的返利金额(浮点数)
     */
    private String couponAmountDouble;
    /**
     * 使用的代垫金额(浮点数)
     */
    private String prepaidAmountDouble;
    /**
     * 使用预存金额
     */
    private String prestoredAmountDouble;
    /**
     * 使用完各种折扣后还需付的现金金额(浮点数)
     */
    private String cashAmountDouble;
    /**
     * 收货公司
     */
    private String recipientCompany;
    /**
     * 经销商的收货地址
     */
    private String shippingAddress;
    /**
     * 省id
     */
    private String provinceId;
    /**
     * 省
     */
    private String provinceName;
    /**
     * 市id
     */
    private String cityId;
    /**
     * 市
     */
    private String cityName;
    /**
     * 区id
     */
    private String districtId;
    /**
     * 区
     */
    private String districtName;
    /**
     * 收件人名字
     */
    private String recipientName;
    /**
     * 收件人手机号
     */
    private String recipientMobile;

    /**
     * 出库时间
     */
    private Date outBoundTime;
    /**
     * 创建时间
     */
//    @JSONField(format = "yyyy年MM月dd日 HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private Date createTime;
    /**
     * 订单处理时间
     */
    private Date orderCheckTime;
    /**
     * 驳回时间
     */
    private Date rejectTime;
    /**
     * 付款时间
     */
//    @JSONField(format = "yyyy年MM月dd日 HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private Date paidTime;
    /**
     * 下发仓库时间
     */
    private Date informWarehouseTime;
    /**
     * 仓库处理时间
     */
    private Date warehouseHandleTime;
    /**
     * 派车时间
     */
    private Date sendCarTime;
    /**
     * 签收时间
     */
    private Date sighTime;
    /**
     * 供应商支持金Double
     */
    private String supplierDiscountAmountDouble;
    /**
     * 越海代垫金Double
     */
    private String yhDiscountAmountDouble;

    /**
     * 分销商返利余额
     */
    private String distributorCouponAmountDouble;
    /**
     * 分销商代垫余额
     */
    private String distributorPrepaidAmountDouble;
    /**
     * 操作日志
     */
    private List<TraceLog> traceLog;
    /**
     * 销售单商品详情
     */
    private List<SalesOrderItemInfo> salesOrderItemInfos;

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getSalesContractNo() {
        return salesContractNo;
    }

    public void setSalesContractNo(String salesContractNo) {
        this.salesContractNo = salesContractNo;
    }

    public String getSalesOrderStatusStr(){
        return SalesOrderStatusEnum.getMessage(this.getSalesOrderStatus());
    }

    public Integer getSalesOrderStatus() {
        return salesOrderStatus;
    }

    public void setSalesOrderStatus(Integer salesOrderStatus) {
        this.salesOrderStatus = salesOrderStatus;
    }

    public String getSalesOrderStatusString() {
        return salesOrderStatusString;
    }

    public void setSalesOrderStatusString(String salesOrderStatusString) {
        this.salesOrderStatusString = salesOrderStatusString;
    }

    public String getTotalOrderAmountDouble() {
        return totalOrderAmountDouble;
    }

    public void setTotalOrderAmountDouble(String totalOrderAmountDouble) {
        this.totalOrderAmountDouble = totalOrderAmountDouble;
    }

    public String getCouponAmountDouble() {
        return couponAmountDouble;
    }

    public void setCouponAmountDouble(String couponAmountDouble) {
        this.couponAmountDouble = couponAmountDouble;
    }

    public String getPrepaidAmountDouble() {
        return prepaidAmountDouble;
    }

    public void setPrepaidAmountDouble(String prepaidAmountDouble) {
        this.prepaidAmountDouble = prepaidAmountDouble;
    }

    public String getPrestoredAmountDouble() {
        return prestoredAmountDouble;
    }

    public void setPrestoredAmountDouble(String prestoredAmountDouble) {
        this.prestoredAmountDouble = prestoredAmountDouble;
    }

    public String getCashAmountDouble() {
        return cashAmountDouble;
    }

    public void setCashAmountDouble(String cashAmountDouble) {
        this.cashAmountDouble = cashAmountDouble;
    }

    public String getRecipientCompany() {
        return recipientCompany;
    }

    public void setRecipientCompany(String recipientCompany) {
        this.recipientCompany = recipientCompany;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientMobile() {
        return recipientMobile;
    }

    public void setRecipientMobile(String recipientMobile) {
        this.recipientMobile = recipientMobile;
    }

    public Date getOutBoundTime() {
        return outBoundTime;
    }

    public void setOutBoundTime(Date outBoundTime) {
        this.outBoundTime = outBoundTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getOrderCheckTime() {
        return orderCheckTime;
    }

    public void setOrderCheckTime(Date orderCheckTime) {
        this.orderCheckTime = orderCheckTime;
    }

    public Date getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(Date rejectTime) {
        this.rejectTime = rejectTime;
    }

    public Date getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(Date paidTime) {
        this.paidTime = paidTime;
    }

    public Date getInformWarehouseTime() {
        return informWarehouseTime;
    }

    public void setInformWarehouseTime(Date informWarehouseTime) {
        this.informWarehouseTime = informWarehouseTime;
    }

    public Date getWarehouseHandleTime() {
        return warehouseHandleTime;
    }

    public void setWarehouseHandleTime(Date warehouseHandleTime) {
        this.warehouseHandleTime = warehouseHandleTime;
    }

    public Date getSendCarTime() {
        return sendCarTime;
    }

    public void setSendCarTime(Date sendCarTime) {
        this.sendCarTime = sendCarTime;
    }

    public Date getSighTime() {
        return sighTime;
    }

    public void setSighTime(Date sighTime) {
        this.sighTime = sighTime;
    }

    public String getSupplierDiscountAmountDouble() {
        return supplierDiscountAmountDouble;
    }

    public void setSupplierDiscountAmountDouble(String supplierDiscountAmountDouble) {
        this.supplierDiscountAmountDouble = supplierDiscountAmountDouble;
    }

    public String getYhDiscountAmountDouble() {
        return yhDiscountAmountDouble;
    }

    public void setYhDiscountAmountDouble(String yhDiscountAmountDouble) {
        this.yhDiscountAmountDouble = yhDiscountAmountDouble;
    }

    public String getDistributorCouponAmountDouble() {
        return distributorCouponAmountDouble;
    }

    public void setDistributorCouponAmountDouble(String distributorCouponAmountDouble) {
        this.distributorCouponAmountDouble = distributorCouponAmountDouble;
    }

    public String getDistributorPrepaidAmountDouble() {
        return distributorPrepaidAmountDouble;
    }

    public void setDistributorPrepaidAmountDouble(String distributorPrepaidAmountDouble) {
        this.distributorPrepaidAmountDouble = distributorPrepaidAmountDouble;
    }

    public List<TraceLog> getTraceLog() {
        return traceLog;
    }

    public void setTraceLog(List<TraceLog> traceLog) {
        this.traceLog = traceLog;
    }

    public List<SalesOrderItemInfo> getSalesOrderItemInfos() {
        return salesOrderItemInfos;
    }

    public void setSalesOrderItemInfos(List<SalesOrderItemInfo> salesOrderItemInfos) {
        this.salesOrderItemInfos = salesOrderItemInfos;
    }

}
