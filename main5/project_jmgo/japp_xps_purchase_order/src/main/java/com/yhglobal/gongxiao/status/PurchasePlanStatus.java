package com.yhglobal.gongxiao.status;

import com.yhglobal.gongxiao.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public enum PurchasePlanStatus {

    INIT((byte) (1),"初始化"),

    ORDER_COMPLETE((byte)90,"正常完成"),

    EXCEPTION_COMPLETE_HANDLED((byte)91,"异常完成,已处理"),

    EXCEPTION_COMPLETE_UNHANDLED((byte)92,"异常完成,未处理");

    private byte status;

    private String message;

    private volatile static Map<Byte, PurchasePlanStatus> mapping = null;


    PurchasePlanStatus(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static PurchasePlanStatus getStatusByCode(int code) {
        if (mapping == null) {
            synchronized (ErrorCode.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (PurchasePlanStatus purchasePlanStatus : PurchasePlanStatus.values()) {
                        mapping.put(purchasePlanStatus.status, purchasePlanStatus);
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
