package com.yhglobal.gongxiao.exception;

/**
 * 账户已冻结异常
 *
 * @Author: 葛灿
 */
public class AccountIsFrozenException extends Exception {

    static String ACCOUNT_IS_FROZEN = "account is frozen";

    public AccountIsFrozenException() {
        super(ACCOUNT_IS_FROZEN);
    }

}
