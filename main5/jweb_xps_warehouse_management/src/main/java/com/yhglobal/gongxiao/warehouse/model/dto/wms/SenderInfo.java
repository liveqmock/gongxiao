package com.yhglobal.gongxiao.warehouse.model.dto.wms;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
@XmlRootElement(name="senderInfo")
public class SenderInfo implements Serializable {
    private String senderZipCode;            //发货方邮编
    private String senderProvince;           //发货方省份
    private String senderCity;               //发货方城市
    private String senderArea;               //发货方区县
    private String senderAddress;            //发货方区县    必选
    private String senderName;               //发货方名称    必选
    private String senderMobile;             //发货方手机
    private String senderPhone;              //发货方电话

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

    public String getSenderArea() {
        return senderArea;
    }

    public void setSenderArea(String senderArea) {
        this.senderArea = senderArea;
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
