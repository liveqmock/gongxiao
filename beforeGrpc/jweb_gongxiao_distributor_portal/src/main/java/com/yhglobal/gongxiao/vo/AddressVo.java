package com.yhglobal.gongxiao.vo;

/**
 * 地址
 *
 * @author: 陈浩
 **/
public class AddressVo {

    private int code;

    private String address;

    public AddressVo(int code, String address) {
        this.code = code;
        this.address = address;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
