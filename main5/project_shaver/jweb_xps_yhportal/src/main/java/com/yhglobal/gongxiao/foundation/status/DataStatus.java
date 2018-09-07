package com.yhglobal.gongxiao.foundation.status;

import com.yhglobal.gongxiao.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public enum  DataStatus {

    INVALID((byte) 1, "无效的"),

    VALID((byte) 2,"有效的")
    ;

    private byte status;

    private String message;

    private volatile static Map<Byte, DataStatus> mapping = null;


    DataStatus(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static DataStatus getErrorCodeByCode(int code) {
        if (mapping == null) {
            synchronized (ErrorCode.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (DataStatus dataStatus : DataStatus.values()) {
                        mapping.put(dataStatus.status, dataStatus);
                    }
                }
            }
        }
        return mapping.get(code);
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
