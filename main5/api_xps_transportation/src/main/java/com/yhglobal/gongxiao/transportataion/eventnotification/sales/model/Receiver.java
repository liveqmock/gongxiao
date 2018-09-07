package com.yhglobal.gongxiao.transportataion.eventnotification.sales.model;

/**
 * 收货人对象
 *
 * 注: 为了和TMS的字段名称一样，这里字段都用大写开始
 */
public class Receiver {

    //@JSONField(name="Receiver")
    String receiver; //必须 发货地

    String receiverZipCode; //可选 收货方邮编

    String receiverProvince; //必选 收货方省份
    String receiverCity; //必选 收货方城市
    String receiverCounty; //必选 收货方区县
    String receiverTown; //可选 收货方街道/镇
    String receiverAddress; //必选 收货方地址
    String receiverName; //必选 收货人名称
    String receiverMobile; //必选 收货人手机
    String receiverPhone; //可选 收货人电话

    public Receiver() {
    }

    public Receiver(String receiver, String receiverProvince, String receiverCity, String receiverCounty, String receiverAddress, String receiverName, String receiverMobile) {
        this.receiver = receiver;
        this.receiverProvince = receiverProvince;
        this.receiverCity = receiverCity;
        this.receiverCounty = receiverCounty;
        this.receiverAddress = receiverAddress;
        this.receiverName = receiverName;
        this.receiverMobile = receiverMobile;
    }

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
