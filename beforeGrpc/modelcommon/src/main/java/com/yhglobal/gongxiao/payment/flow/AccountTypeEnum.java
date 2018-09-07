package com.yhglobal.gongxiao.payment.flow;

/**
 * 流水类型枚举
 * @author: 葛灿
 */
public enum AccountTypeEnum {
    CASH(1,"现金"),
    COUPON(2,"返利"),
    PREPAID(3,"代垫"),
    PRESTORED(4,"预付");

    AccountTypeEnum(int type, String description) {
        this.type = type;
        this.description = description;
    }

    private int type;
    private String description;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
