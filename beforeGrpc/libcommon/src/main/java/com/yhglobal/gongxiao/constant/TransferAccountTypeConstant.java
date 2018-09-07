package com.yhglobal.gongxiao.constant;

/**
 * @author 王帅
 */
public enum TransferAccountTypeConstant {

    TRANSFER_ACCOUNT_TYPE_1("YH","越海"),
    TRANSFER_ACCOUNT_TYPE_2("AD","经销商")
    ;
    String type;
    String typeName;

    public String getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    TransferAccountTypeConstant(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
}
