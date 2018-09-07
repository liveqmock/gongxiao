package com.yhglobal.gongxiao.model;

import com.yhglobal.gongxiao.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/

public enum PurchaseStatusEnum {
    DRAFT(0, "草稿"),
    COMMIT(1, "已提交"),
    ALREADY_PAY(3, "已预约"),
    ALREADY_SHPPING(70, "已发货"),
    PART_INBOUND(80, "部分入库"),
    NORMAL_COMPLETE(90, "正常完成"),
    EXCEPTION_COMPLETE_HANDLED(91, "异常完成,已处理"),
    EXCEPTION_COMPLETE_NOT_HANDLE(92, "异常完成未处理"),
    GENERATE_COUPON(95,"生成返利"),
    CANCEL(99, "已取消");

    private int code;

    private String message;

    private volatile static Map<Integer, PurchaseStatusEnum> mapping = null;


    PurchaseStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static PurchaseStatusEnum getErrorCodeByCode(int code) {
        if (mapping == null) {
            synchronized (ErrorCode.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (PurchaseStatusEnum purchaseStatusEnum : PurchaseStatusEnum.values()) {
                        mapping.put(purchaseStatusEnum.code, purchaseStatusEnum);
                    }
                }
            }
        }
        return mapping.get(code);
    }

}
