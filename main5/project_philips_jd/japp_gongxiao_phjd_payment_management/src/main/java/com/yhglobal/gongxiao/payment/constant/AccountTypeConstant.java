package com.yhglobal.gongxiao.payment.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王帅
 */
public enum AccountTypeConstant {

    SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT(1,"供应商单价折扣冻结账户"),
    SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT(2,"供应商采购预留冻结账户"),
    SUPPLIER_SALES_DIFFERENCE_RESERVED_ACCOUNT(3,"供应商销售差价预留账户"),
    SUPPLIER_UNIT_PRICE_DISCOUNT_RESERVED_ACCOUNT(4,"供应商单价折扣预留账户"),
    SUPPLIER_PURCHASE_RESERVED_ACCOUNT(5,"供应商采购预留预留账户"),
    SUPPLIER_COUPON_POSTING_ACCOUNT(6,"供应商返利上账账户"),
    SUPPLIER_PREPAID_POSTING_ACCOUNT(7,"供应商代垫上账账户"),
    YH_COUPON_RECEIVED_ACCOUNT(8,"越海返利实收账户"),
    YH_PREPAID_RECEIVED_ACCOUNT(9,"越海代垫实收账户"),
    YH_COUPON_TRANSFER_ACCOUNT(10,"越海返利过账账户"),
    YH_PREPAID_TRANSFER_ACCOUNT(11,"越海代垫过账账户"),
    ;
    Integer code;
    String AccountName;

    public Integer getCode() {
        return code;
    }

    public String getAccountName() {
        return AccountName;
    }

    AccountTypeConstant(Integer code, String accountName) {
        this.code = code;
        AccountName = accountName;
    }

    private volatile static Map<Integer, AccountTypeConstant> mapping = null;

    public static AccountTypeConstant getAccountTypeConstantByCode(Integer code){
        if (mapping == null) {
            synchronized (AccountTypeConstant.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (AccountTypeConstant accountTypeConstant : AccountTypeConstant.values()) {
                        mapping.put(accountTypeConstant.code, accountTypeConstant);
                    }
                }
            }
        }
        return mapping.get(code);
    }
}
