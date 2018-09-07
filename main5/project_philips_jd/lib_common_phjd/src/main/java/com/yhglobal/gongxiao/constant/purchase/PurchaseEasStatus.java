package com.yhglobal.gongxiao.constant.purchase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 采购单EAS同步状态
 *
 * @author weizecheng
 * @date 2018/9/5 11:03
 */
public enum PurchaseEasStatus {

    /**
     * 未成功推送给EAS
     */
    SYN_EAS_FAILURE((byte) 1, "未成功推送给EAS"),
    /**
     *成功推送给EAS
     */
    SYN_EAS_SUCCESS((byte) 2, "成功推送给EAS"),
    /**
     *锁定状态
     */
    SYN_EAS_TEMP((byte) 3, "锁定状态"),
    /**
     *待人工处理
     */
    WAIT_HANDLE((byte)4,"待人工处理");

    private Byte status;

    private String message;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    PurchaseEasStatus(Byte status, String message) {
        this.status = status;
        this.message = message;
    }
    private final static Map<Byte, PurchaseEasStatus> EnumMap = new ConcurrentHashMap<Byte, PurchaseEasStatus>();

    static {
        init();
    }

    private static void init() {
        for (PurchaseEasStatus purchaseStatus : PurchaseEasStatus.values()) {
            EnumMap.put(purchaseStatus.status, purchaseStatus);
        }
    }

    public static PurchaseEasStatus getPurchaseStatusByCode(Byte code) {
        if(EnumMap.containsKey(code)){
            return EnumMap.get(code);
        }
        return null;
    }

}
