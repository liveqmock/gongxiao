package com.yhglobal.gongxiao.constant;

/**
 * @author 王帅
 */
public enum CouponConfrimAccountType {
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

    CouponConfrimAccountType(Integer code, String accountName) {
        this.code = code;
        AccountName = accountName;
    }
}
