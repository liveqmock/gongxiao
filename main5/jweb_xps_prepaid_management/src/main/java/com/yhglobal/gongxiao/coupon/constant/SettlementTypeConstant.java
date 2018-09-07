package com.yhglobal.gongxiao.coupon.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王帅
 */
public enum SettlementTypeConstant {
    // 代垫付款单详情界面的结算方式1">结算单"2">现金付款 "3"其它代垫
    SETTLEMENT_DOCUMENT(1,"结算单"),
    CASH_PAYMENT(2,"现金付款"),
    OTHER_PREPAID(3,"其它代垫"),
    ;
    Integer code;
    String settlementType;

    public Integer getCode() {
        return code;
    }

    public String getSettlementType() {
        return settlementType;
    }

    SettlementTypeConstant(Integer code, String settlementType) {
        this.code = code;
        this.settlementType = settlementType;
    }

    private volatile static Map<Integer, SettlementTypeConstant> mapping = null;

    public static SettlementTypeConstant getSettlementTypeConstantByCode(Integer code){
        if (mapping == null) {
            synchronized (SettlementTypeConstant.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (SettlementTypeConstant settlementTypeConstant : SettlementTypeConstant.values()) {
                        mapping.put(settlementTypeConstant.code, settlementTypeConstant);
                    }
                }
            }
        }
        return mapping.get(code);
    }

}
