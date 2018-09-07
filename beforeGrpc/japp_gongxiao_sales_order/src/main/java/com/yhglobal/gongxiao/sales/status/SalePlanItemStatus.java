package com.yhglobal.gongxiao.sales.status;

import com.yhglobal.gongxiao.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public enum  SalePlanItemStatus {

    INIT((byte)1,"初始化"),

    COMPLETE((byte)90,"完成"),

    CANCEL((byte)91,"已取消")

    ;

    private byte status;

    private String message;

    private volatile static Map<Byte, SalePlanItemStatus> mapping = null;


    SalePlanItemStatus(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static SalePlanItemStatus getStatusByCode(int code) {
        if (mapping == null) {
            synchronized (ErrorCode.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (SalePlanItemStatus salePlanItemStatus : SalePlanItemStatus.values()) {
                        mapping.put(salePlanItemStatus.status, salePlanItemStatus);
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
