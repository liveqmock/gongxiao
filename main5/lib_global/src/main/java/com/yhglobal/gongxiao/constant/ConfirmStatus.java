package com.yhglobal.gongxiao.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 定义PurchaseCouponLedger中的常量
 * @author  王帅
 */
public enum ConfirmStatus {

    // 确认状态 1:未发放 2:部分发放 3:已全部发放 4:已过期
    /**
    * 确认状态 : 未发放
    * */
    COUPON_STATUS_NOT_TO_ISSUE((byte)1, "未发放"),

    /**
     * 确认状态 : 部分发放
     * */
    COUPON_STATUS_PART_OF_ISSUE((byte)2,"部分发放"),

    /**
     * 确认状态 : 已全部发放
     * */
    COUPON_STATUS_ALL_ISSUE_NOT_USING((byte)3, "已全部发放"),

    /**
     * 确认状态 : 已过期
     * */
    COUPON_STATUS_OVERDUE((byte)4, "已过期"),

 ;

    byte code;

    String meaning;

    public byte getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    ConfirmStatus(byte code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    private volatile static Map<Byte, ConfirmStatus> mapping = null;

    public static ConfirmStatus getCouponLedgerCouponStatusByCode(Byte code){
        if (mapping == null) {
            synchronized (ConfirmStatus.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (ConfirmStatus confirmStatus : ConfirmStatus.values()) {
                        mapping.put(confirmStatus.code, confirmStatus);
                    }
                }
            }
        }
        return mapping.get(code);
    }

}
