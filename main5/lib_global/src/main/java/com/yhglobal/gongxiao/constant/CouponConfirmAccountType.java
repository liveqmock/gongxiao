package com.yhglobal.gongxiao.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王帅
 */
public enum CouponConfirmAccountType {
    COUPON_RECEIVED_ACCOUNT(1,"返利实收账户"),
    SALES_DIFFERENCE_ACCOUNT(2,"销售差价账户"),
    UNIT_PRICE_DISCOUNT_ACCOUNT(3,"单价折扣账户"),
    PURCHASE_RESERVED_ACCOUNT(4,"采购预留账户"),
    SUPPLIER_CASH_TRANSFER(5,"供应商现金转入")
    ;
    Integer code;
    String AccountName;

    public Integer getCode() {
        return code;
    }

    public String getAccountName() {
        return AccountName;
    }

    CouponConfirmAccountType(Integer code, String accountName) {
        this.code = code;
        AccountName = accountName;
    }

    private volatile static Map<Integer, CouponConfirmAccountType> mapping = null;

    public static CouponConfirmAccountType getCouponConfirmAccountTypeByCode(Integer code){
        if (mapping == null) {
            synchronized (CouponConfirmAccountType.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (CouponConfirmAccountType couponConfirmAccountType : CouponConfirmAccountType.values()) {
                        mapping.put(couponConfirmAccountType.code, couponConfirmAccountType);
                    }
                }
            }
        }
        return mapping.get(code);
    }

    private volatile static Map<String, CouponConfirmAccountType> mapping1 = null;

    public static CouponConfirmAccountType getCouponConfirmAccountTypeByName(String accountName){
        if (mapping1 == null) {
            synchronized (CouponConfirmAccountType.class) {
                if (mapping1 == null) {
                    mapping1 = new HashMap<>();
                    for (CouponConfirmAccountType couponConfirmAccountType : CouponConfirmAccountType.values()) {
                        mapping1.put(couponConfirmAccountType.AccountName, couponConfirmAccountType);
                    }
                }
            }
        }
        return mapping1.get(accountName);
    }
}
