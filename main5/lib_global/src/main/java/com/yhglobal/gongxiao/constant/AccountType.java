package com.yhglobal.gongxiao.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 帐户类型
 * @author: LUOYI
 * @Date: Created in 18:47 2018/5/2
 */
public enum AccountType {
    /**
     * 帐户类型 : 代垫实收账户
     * */
    ACCOUNT_PREPAID_RECEIPTED(1, "代垫实收账户"),
    /**
     * 帐户类型 : 销售差价帐户
     * */
    ACCOUNT_SALE_DIFFERENCE(2, "销售差价帐户");


    int code;

    String message;

    AccountType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private volatile static Map<Integer, AccountType> mapping = null;

    public static AccountType getAccountTypeByCode(Integer code){
        if (mapping == null) {
            synchronized (AccountType.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (AccountType accountType : AccountType.values()) {
                        mapping.put(accountType.code, accountType);
                    }
                }
            }
        }
        return mapping.get(code);
    }
    
}
