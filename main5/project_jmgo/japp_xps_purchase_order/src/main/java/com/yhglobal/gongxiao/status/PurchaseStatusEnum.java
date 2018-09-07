package com.yhglobal.gongxiao.status;

import com.yhglobal.gongxiao.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/

public enum PurchaseStatusEnum {
    DRAFT((byte)1, "草稿"),
    COMMIT((byte)2, "已提交"),
    ALREADY_PAY((byte)3, "已预约"),
    ALREADY_SHIPPING((byte)70, "已发货"),
    PART_INBOUND((byte)80, "部分入库"),
    NORMAL_COMPLETE((byte)90, "正常完成"),
    EXCEPTION_COMPLETE_HANDLED((byte)91, "异常完成,已处理"),
    EXCEPTION_COMPLETE_NOT_HANDLE((byte)92, "异常完成未处理"),
    GENERATE_COUPON((byte)95,"生成返利"),
    CANCEL((byte)99, "已取消");

    private byte code;

    private String message;

    private volatile static Map<Byte, PurchaseStatusEnum> mapping = null;


    PurchaseStatusEnum(byte code, String message) {
        this.code = code;
        this.message = message;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static PurchaseStatusEnum getErrorCodeByCode(byte code) {
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
