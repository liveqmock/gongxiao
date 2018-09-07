package com.yhglobal.gongxiao.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 回滚状态
 * @author: LUOYI
 * @Date: Created in 15:19 2018/7/19
 */
public enum RollbackStatus {

    ROLLBACK_STATUS_YES(1, "已回滚"),

    ROLLBACK_STATUS_NOES(2, "未回滚");


    int code;

    String message;

    RollbackStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private volatile static Map<Integer, String> mapping = null;

    public static String getMessageByCode(Integer code){
        if (mapping == null) {
            synchronized (RollbackStatus.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (RollbackStatus rollbackStatus : RollbackStatus.values()) {
                        mapping.put(rollbackStatus.code, rollbackStatus.message);
                    }
                }
            }
        }
        return mapping.get(code);
    }
}
