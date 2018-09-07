package com.yhglobal.gongxiao.status;

import com.yhglobal.gongxiao.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public enum FoundationNormalStatus {

    UNKNOWN((byte)0,"未知"),

    INIT((byte) 1, "初始化"),

    COMMITTED((byte)2,"已提交"),

    AUDITED((byte)3,"已审核"),

    REJECTED((byte)4,"已驳回"),

    CANCEL((byte)9,"已废弃")
    ;
    private byte status;

    private String message;

    private volatile static Map<Byte, FoundationNormalStatus> mapping = null;


    FoundationNormalStatus(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static FoundationNormalStatus getStatusByCode(byte code) {
        if (mapping == null) {
            synchronized (FoundationNormalStatus.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (FoundationNormalStatus foundationNormalStatus : FoundationNormalStatus.values()) {
                        mapping.put(foundationNormalStatus.status, foundationNormalStatus);
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
