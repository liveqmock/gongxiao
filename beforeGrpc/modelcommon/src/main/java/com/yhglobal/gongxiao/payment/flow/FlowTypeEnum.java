package com.yhglobal.gongxiao.payment.flow;

/**
 * 流水类型枚举
 * @author: 葛灿
 */
public enum  FlowTypeEnum {
    OUT(305,"下单扣返利"),
    IN(306,"供应商返利");

    FlowTypeEnum(int type, String description) {
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
