package com.yhglobal.gongxiao.payment.model;

/**
 * 流水类型枚举类
 *
 * @author: 葛灿
 */
public enum FlowTypeEnum {

    OUT(305,"支出"),
    IN(306,"转入"),
    ;
    private int type;

    private String message;

    FlowTypeEnum(int errorCode, String message) {
        this.type = errorCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

}
