package com.yhglobal.gongxiao.grossProfit.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 定义PurchaseCouponLedger中的常量
 * @author  王帅
 */
public enum GrossProfitLedgerConfirmStatus {

    // 毛利状态 1:未发放 2:部分发放 3:已全部发放 4:已过期
    /**
    * 毛利状态 : 未发放
    * */
    STATUS_NOT_TO_ISSUE((byte)1, "未发放"),

    /**
     * 毛利状态 : 部分发放
     * */
    STATUS_PART_OF_ISSUE((byte)2,"部分发放"),

    /**
     * 毛利状态 : 已全部发放
     * */
    STATUS_ALL_ISSUE_NOT_USING((byte)3, "已全部发放"),

    /**
     * 毛利状态 : 已过期
     * */
    STATUS_OVERDUE((byte)4, "已过期"),

 ;

    byte code;

    String meaning;

    public byte getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    GrossProfitLedgerConfirmStatus(byte code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    private volatile static Map<Byte, GrossProfitLedgerConfirmStatus> mapping = null;

    public static GrossProfitLedgerConfirmStatus getGrossProfitLedgerConfirmStatusByCode(Byte code){
        if (mapping == null) {
            synchronized (GrossProfitLedgerConfirmStatus.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (GrossProfitLedgerConfirmStatus couponLedgerCouponStatus : GrossProfitLedgerConfirmStatus.values()) {
                        mapping.put(couponLedgerCouponStatus.code, couponLedgerCouponStatus);
                    }
                }
            }
        }
        return mapping.get(code);
    }

}
