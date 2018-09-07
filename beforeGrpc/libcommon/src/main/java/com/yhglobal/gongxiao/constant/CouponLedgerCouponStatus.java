package com.yhglobal.gongxiao.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 定义PurchaseCouponLedger中的常量
 * @author  王帅
 */
public enum CouponLedgerCouponStatus {

    // 返利状态 1:未发放 2:部分发放 3:已全部发放未进入使用时间 4:已使用 5:已过期
    /**
     * 返利状态 : 未发放
     * */
    COUPON_STATUS_NOT_TO_ISSUE((byte)1, "未发放"),

    /**
     * 返利状态 : 部分发放
     * */
    COUPON_STATUS_PART_OF_ISSUE((byte)2,"部分发放"),

    /**
     * 返利状态 : 已全部发放
     * */
    COUPON_STATUS_ALL_ISSUE_NOT_USING((byte)3, "已全部发放"),

    /**
     * 返利状态 : 已使用
     * */
    COUPON_STATUS_USED((byte)4, "已使用"),

    /**
     * 返利状态 : 已过期
     * */
    COUPON_STATUS_OVERDUE((byte)9, "已过期"),

    /**
     *  返利类型  月度返利
     * */
    COUPON_TYPE_MONTHLY((byte)1, "月度返利"),

    /**
     *  返利类型  季度返利
     * */
    COUPON_TYPE_QUARTERLY((byte)2, "季度返利"),

    /**
     *  返利类型  年度返利
     * */
    COUPON_TYPE_ANNUAL((byte)3, "年度返利"),

    /**
     *  返利类型  后返现金返利
     * */
    COUPON_TYPE_CASH_REBATE_AFTER((byte)7, "后返现金返利");

    byte code;

    String meaning;

    public byte getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    CouponLedgerCouponStatus(byte code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    private volatile static Map<Byte, CouponLedgerCouponStatus> mapping = null;

    public static CouponLedgerCouponStatus getCouponLedgerCouponStatusByCode(Byte code){
        if (mapping == null) {
            synchronized (CouponLedgerCouponStatus.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (CouponLedgerCouponStatus couponLedgerCouponStatus : CouponLedgerCouponStatus.values()) {
                        mapping.put(couponLedgerCouponStatus.code, couponLedgerCouponStatus);
                    }
                }
            }
        }
        return mapping.get(code);
    }

}
