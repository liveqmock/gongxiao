package com.yhglobal.gongxiao.coupon.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王帅
 */
public enum CouponLedgerCouponType {

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
    COUPON_TYPE_CASH_REBATE_AFTER((byte)7, "后返现金返利"),

    ;
    byte code;

    String meaning;

    public byte getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    CouponLedgerCouponType(byte code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    private volatile static Map<Byte, CouponLedgerCouponType> mapping = null;

    public static CouponLedgerCouponType getCouponLedgerCouponTypeByCode(Byte code){
        if (mapping == null) {
            synchronized (CouponLedgerCouponType.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (CouponLedgerCouponType couponLedgerCouponType : CouponLedgerCouponType.values()) {
                        mapping.put(couponLedgerCouponType.code, couponLedgerCouponType);
                    }
                }
            }
        }
        return mapping.get(code);
    }
}
