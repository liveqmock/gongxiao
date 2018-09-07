package com.yhglobal.gongxiao.warehouse.model.dto.wms;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
@XmlRootElement(name="batchSendCtrlParam")
public class BatchSendCtrlParam implements Serializable{
    private int totalOrderItemCount;        //总共ITEM数量当distributeType为1时为必选
    private int distributeType;             //是否有更多商品0 一次发送 1 多次发送
    private String receiverZipCode;         //收货方邮编
    private String receiverProvince;        //收货方省份
    private String receiverCity;            //收货方城市
    private String receiverArea;            //收货方区县
    private String receiverAddress;         //收货方地址
    private String receiverName;            //收货人名称
    private String receiverMobile;          //收货人手机
    private String receiverPhone;           //收货人电话

    public int getTotalOrderItemCount() {
        return totalOrderItemCount;
    }

    public void setTotalOrderItemCount(int totalOrderItemCount) {
        this.totalOrderItemCount = totalOrderItemCount;
    }

    public int getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(int distributeType) {
        this.distributeType = distributeType;
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

    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
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
