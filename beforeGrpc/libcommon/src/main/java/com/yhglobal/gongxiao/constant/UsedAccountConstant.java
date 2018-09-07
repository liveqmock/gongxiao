package com.yhglobal.gongxiao.constant;

/**
 * @author 王帅
 */
public enum UsedAccountConstant {

    RECEIVED_COUPON_ACCOUNT("实收返利账户"),
    SALES_DIFFERENCE_ACCOUNT("销售差价账户")
    ;
    String accountType;

    public String getAccountType() {
        return accountType;
    }

    UsedAccountConstant(String accountType) {
        this.accountType = accountType;
    }
}
