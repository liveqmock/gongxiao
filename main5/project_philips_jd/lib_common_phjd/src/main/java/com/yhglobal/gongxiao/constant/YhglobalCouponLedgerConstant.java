package com.yhglobal.gongxiao.constant;



/**
 * 定义PurchaseCouponLedger中的常量
 * @author  王帅
 */
public enum YhglobalCouponLedgerConstant {

    // 返利状态 0:未发放 1:部分发放 2:已全部发放未进入使用时间 3:已使用 9:已过期
    /**
    * 返利状态 : 未发放
    * */
    COUPON_STATUS_NOT_TO_ISSUE((byte)0, "未发放"),

    /**
     * 返利状态 : 部分发放
     * */
    COUPON_STATUS_PART_OF_ISSUE((byte)1,"部分发放"),

    /**
     * 返利状态 : 已全部发放
     * */
    COUPON_STATUS_ALL_ISSUE_NOT_USING((byte)2, "已全部发放"),

    /**
     * 返利状态 : 已使用
     * */
    COUPON_STATUS_USED((byte)3, "已使用"),

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

    byte num;

    String meaning;

    public byte getNum() {
        return num;
    }



    public String getMeaning() {
        return meaning;
    }

    YhglobalCouponLedgerConstant(byte num, String meaning) {
        this.num = num;
        this.meaning = meaning;
    }
}
