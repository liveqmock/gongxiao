package com.yhglobal.gongxiao.foundation.status;

import com.yhglobal.gongxiao.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public enum  OperateType {

    SAVE((byte) 1, "保存"),

    COMMIT((byte) 2,"提交")
    ;

    private byte status;

    private String message;

    private volatile static Map<Byte, OperateType> mapping = null;


    OperateType(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static OperateType getTypeByCode(int code) {
        if (mapping == null) {
            synchronized (ErrorCode.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (OperateType operateType : OperateType.values()) {
                        mapping.put(operateType.status, operateType);
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
