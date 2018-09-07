package com.yhglobal.gongxiao.exception;

/**
 * 账户余额不足异常
 *
 * @Author: 葛灿
 */
public class AccountBalanceNotEnoughException extends Exception {

    static String BALANCE_NOT_ENOUTH = "account balance is not enough";

    public AccountBalanceNotEnoughException() {
        super(BALANCE_NOT_ENOUTH);
    }

}
