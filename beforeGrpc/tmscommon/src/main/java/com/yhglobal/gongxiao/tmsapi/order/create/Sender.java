package com.yhglobal.gongxiao.tmsapi.order.create;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 发件人对象
 *
 * 注: 为了和TMS的字段名称一样，这里字段都用大写开始
 */
public class Sender {

    //@JSONField(name="Sender")
    String sender; //必须 发货地

    String senderZipCode; //可选 发货方邮编
    String senderProvince; //必选 发货方省份
    String senderCity; //必选 发货方城市
    String senderCounty; //可选 发货方区县
    String senderTown; //可选 发货方镇
    String senderAddress; //必选 发货方地址
    String senderName; //必选 发货方名称
    String senderMobile; //必选 发货方手机
    String senderPhone; //可选 发货方电话

    public Sender(String sender, String senderProvince, String senderCity, String senderCounty, String senderAddress, String senderName, String senderMobile) {
        this.sender = sender;
        this.senderProvince = senderProvince;
        this.senderCity = senderCity;
        this.senderCounty = senderCounty;
        this.senderAddress = senderAddress;
        this.senderName = senderName;
        this.senderMobile = senderMobile;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderZipCode() {
        return senderZipCode;
    }

    public void setSenderZipCode(String senderZipCode) {
        this.senderZipCode = senderZipCode;
    }

    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderCounty() {
        return senderCounty;
    }

    public void setSenderCounty(String senderCounty) {
        this.senderCounty = senderCounty;
    }

    public String getSenderTown() {
        return senderTown;
    }

    public void setSenderTown(String senderTown) {
        this.senderTown = senderTown;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }
}
