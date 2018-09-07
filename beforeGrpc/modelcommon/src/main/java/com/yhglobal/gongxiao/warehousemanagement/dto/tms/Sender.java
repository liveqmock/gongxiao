package com.yhglobal.gongxiao.warehousemanagement.dto.tms;

import java.io.Serializable;

/**
 * tms发货人信息
 * @author liukai
 */
public class Sender implements Serializable {
    private String sender;
    private String SenderZipCode;
    private String SenderProvince;
    private String SenderCity;
    private String SenderCounty;
    private String SenderTown;
    private String SenderAddress;
    private String SenderName;
    private String SenderMobile;
    private String SenderPhone;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderZipCode() {
        return SenderZipCode;
    }

    public void setSenderZipCode(String senderZipCode) {
        SenderZipCode = senderZipCode;
    }

    public String getSenderProvince() {
        return SenderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        SenderProvince = senderProvince;
    }

    public String getSenderCity() {
        return SenderCity;
    }

    public void setSenderCity(String senderCity) {
        SenderCity = senderCity;
    }

    public String getSenderCounty() {
        return SenderCounty;
    }

    public void setSenderCounty(String senderCounty) {
        SenderCounty = senderCounty;
    }

    public String getSenderTown() {
        return SenderTown;
    }

    public void setSenderTown(String senderTown) {
        SenderTown = senderTown;
    }

    public String getSenderAddress() {
        return SenderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        SenderAddress = senderAddress;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getSenderMobile() {
        return SenderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        SenderMobile = senderMobile;
    }

    public String getSenderPhone() {
        return SenderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        SenderPhone = senderPhone;
    }
}
