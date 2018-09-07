package com.yhglobal.gongxiao.warehousemanagement.dto.wms;

import java.io.Serializable;

/**
 * 提货人信息
 * @author liukai
 */
public class PickerInfo implements Serializable {
    private String pickerName;      //提货人
    private String pickerPhone;     //提货人电话
    private String pickerCarNo;     //提货人车牌号
    private String pickerCardID;    //提货人证件号
    private String pickerCarType;   //提货车型
    private String parriersName;    //承运公司名称
    private String parriersCode;    //承运公司代码

    public String getPickerName() {
        return pickerName;
    }

    public void setPickerName(String pickerName) {
        this.pickerName = pickerName;
    }

    public String getPickerPhone() {
        return pickerPhone;
    }

    public void setPickerPhone(String pickerPhone) {
        this.pickerPhone = pickerPhone;
    }

    public String getPickerCarNo() {
        return pickerCarNo;
    }

    public void setPickerCarNo(String pickerCarNo) {
        this.pickerCarNo = pickerCarNo;
    }

    public String getPickerCardID() {
        return pickerCardID;
    }

    public void setPickerCardID(String pickerCardID) {
        this.pickerCardID = pickerCardID;
    }

    public String getPickerCarType() {
        return pickerCarType;
    }

    public void setPickerCarType(String pickerCarType) {
        this.pickerCarType = pickerCarType;
    }

    public String getParriersName() {
        return parriersName;
    }

    public void setParriersName(String parriersName) {
        this.parriersName = parriersName;
    }

    public String getParriersCode() {
        return parriersCode;
    }

    public void setParriersCode(String parriersCode) {
        this.parriersCode = parriersCode;
    }
}
