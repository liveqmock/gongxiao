package com.yhglobal.gongxiao.exception;

/**
 * 余额账户未找到异常
 *
 * @author: 葛灿
 */
public class AccountNotFoundException extends Exception {

    static String ACCOUNT_NOT_FOUND = "account not found.";

    public AccountNotFoundException() {
        super(ACCOUNT_NOT_FOUND);
    }

}
