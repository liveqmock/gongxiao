package com.yhglobal.gongxiao.status;

import com.yhglobal.gongxiao.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public enum  PurchaseTypeStatus {

    GENERAL_PURCHASE((byte) 1, "普通采购"),

    PRODUCT_ADDITIONAL((byte)2,"货补采购"),

    GIFT_PURCHASE((byte)3,"赠品采购")
    ;
    private byte status;

    private String message;

    private volatile static Map<Byte, PurchaseTypeStatus> mapping = null;


    PurchaseTypeStatus(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static PurchaseTypeStatus getTypeByCode(int code) {
        if (mapping == null) {
            synchronized (ErrorCode.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (PurchaseTypeStatus purchaseTypeStatus : PurchaseTypeStatus.values()) {
                        mapping.put(purchaseTypeStatus.status, purchaseTypeStatus);
                    }
                }
            }
        }
        return mapping.get(code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

}
