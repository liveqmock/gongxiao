package com.yhglobal.gongxiao.grossProfit.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王帅
 */
public enum GrossProfitTransferPatternConstant {
    // 使用账户：返利实收账户/代垫实收账户/销售差价预留账户/单价折扣预留账户/采购预留账户/供应商现金转账。
    COUPON_RECEIVED_ACCOUNT(1,"返利实收账户"),
    SALES_DIFFERENCE_ACCOUNT(2,"销售差价预留账户"),
    PREPAID_RECEIVED_ACCOUNT(3,"代垫实收账户"),
    UNIT_PRICE_DISCOUNT_RESERVED_ACCOUNT(4,"单价折扣预留账户"),
    PURCHASE_RESERVED_ACCOUNT(5,"采购预留账户"),
    SUPPLIER_CASH_TRANSFER_IN(6,"供应商现金转入")
    ;
    Integer code;
    String AccountName;

    public Integer getCode() {
        return code;
    }

    public String getAccountName() {
        return AccountName;
    }

    GrossProfitTransferPatternConstant(Integer code, String accountName) {
        this.code = code;
        AccountName = accountName;
    }

    private volatile static Map<Integer, GrossProfitTransferPatternConstant> mapping = null;

    public static GrossProfitTransferPatternConstant getGrossProfitTransferPatternConstantByCode(Integer code){
        if (mapping == null) {
            synchronized (GrossProfitTransferPatternConstant.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (GrossProfitTransferPatternConstant grossProfitTransferPatternConstan : GrossProfitTransferPatternConstant.values()) {
                        mapping.put(grossProfitTransferPatternConstan.code, grossProfitTransferPatternConstan);
                    }
                }
            }
        }
        return mapping.get(code);
    }
}
