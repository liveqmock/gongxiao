package com.yhglobal.gongxiao.status;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public enum EasSynStatus {

    INIT((byte) 1, "初始化"),

    PREPAID_SYN((byte) 2, "开始同步"),

    SYN_COMPLETE((byte) 3, "同步完成"),

    SYN_FAILURE((byte) 4, "同步失败"),

    CANCLE((byte) 9, "取消同步")

    ;

    private byte status;

    private String message;

    private volatile static Map<Byte, EasSynStatus> mapping = null;


    EasSynStatus(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static EasSynStatus getStatusByCode(byte code) {
        if (mapping == null) {
            synchronized (FoundationNormalStatus.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (EasSynStatus easSynStatus : EasSynStatus.values()) {
                        mapping.put(easSynStatus.status, easSynStatus);
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
