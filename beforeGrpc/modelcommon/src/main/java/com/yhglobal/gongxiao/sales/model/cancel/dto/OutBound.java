package com.yhglobal.gongxiao.sales.model.cancel.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 出库单
 * @author: LUOYI
 * @Date: Created in 11:20 2018/3/30
 */
public class OutBound implements Serializable {
    private String gongxiaoOutboundOrderNo;         //GX出库单号
    private int orderStatus;                        //出库单状态
    private String orderStatusStr;                        //出库单状态
    private String contactsPeople;                   //收件人名字
    private String phoneNum;                 //收件人手机号
    private Date lastUpdateTime;                   //上次更新时间
    private String salesOrderNo;

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getOrderStatusStr() {
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }

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

    public String getGongxiaoOutboundOrderNo() {
        return gongxiaoOutboundOrderNo;
    }

    public void setGongxiaoOutboundOrderNo(String gongxiaoOutboundOrderNo) {
        this.gongxiaoOutboundOrderNo = gongxiaoOutboundOrderNo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getContactsPeople() {
        return contactsPeople;
    }

    public void setContactsPeople(String contactsPeople) {
        this.contactsPeople = contactsPeople;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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
}
