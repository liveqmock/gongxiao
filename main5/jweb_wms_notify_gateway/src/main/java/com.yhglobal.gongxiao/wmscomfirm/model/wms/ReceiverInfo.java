package com.yhglobal.gongxiao.wmscomfirm.model.wms;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
@XmlRootElement(name="receiverInfo")
public class ReceiverInfo implements Serializable {
    private String receiverZipCode;         //收货方邮编
    private String receiverProvince;        //收货方省份
    private String receiverCity;            //收货方城市
    private String receiverArea;            //收货方区县
    private String receiverAddress;         //收货方地址     必选
    private String receiverName;            //收货人名称     必选
    private String receiverMobile;          //收货人手机
    private String receiverPhone;           //收货人电话

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
