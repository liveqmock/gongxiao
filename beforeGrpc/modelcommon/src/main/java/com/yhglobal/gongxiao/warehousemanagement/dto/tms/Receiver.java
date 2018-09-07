package com.yhglobal.gongxiao.warehousemanagement.dto.tms;

import java.io.Serializable;

/**
 * tms收货人信息
 * @author liukai
 */
public class Receiver implements Serializable {
    private String receiver;            //收货地
    private String receiverZipCode;     //收货方邮编
    private String receiverProvince;    //收货方省份
    private String receiverCity;        //收货方城市
    private String receiverCounty;      //收货方区县
    private String receiverTown;        //收货方街道/镇
    private String receiverAddress;     //收货方地址
    private String receiverName;        //收货人名称
    private String receiverMobile;      //收货人手机
    private String receiverPhone;       //收货人电话

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverZipCode() {
        return receiverZipCode;
    }

    public void setReceiverZipCode(String receiverZipCode) {
        this.receiverZipCode = receiverZipCode;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverCounty() {
        return receiverCounty;
    }

    public void setReceiverCounty(String receiverCounty) {
        this.receiverCounty = receiverCounty;
    }

    public String getReceiverTown() {
        return receiverTown;
    }

    public void setReceiverTown(String receiverTown) {
        this.receiverTown = receiverTown;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }
}
