package com.yhglobal.gongxiao.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 采购状态
 *
 * @author: 陈浩
 **/
public enum  PurchaseStatus {

    //采购
    ORDER_IMPORT((byte)1,"编辑状态"),
    ORDER_SUBMIT((byte)2,"采购单提交"),
    ORDER_CUT_PAYMENT((byte)3,"已扣款"),
    ALREADY_PLAN((byte)70,"已发货,待入库"),
    PART_INSTOCK((byte)80,"部分入库"),

    SYN_EAS_FAILURE((byte)1,"未成功推送给EAS"),
    SYN_EAS_SUCCESS((byte)2,"成功推送给EAS"),

    //采退
    PURCHASE_RETURN_COMPLETE((byte) 2,"出库完成"),

    PURCHASE_RETURN_SIGNED((byte)3,"已签收"),

    //流水
    ORDER_COMPLETE((byte)90,"正常完成"),

    EXCEPTION_COMPLETE_HANDLED((byte)91,"异常完成,已处理"),

    EXCEPTION_COMPLETE_UNHANDLED((byte)92,"异常完成,未处理"),

    GENERATE_COUPON((byte)95,"生成返利成功")
    ;
    private byte status;

    private String message;

    private volatile static Map<Byte, PurchaseStatus> mapping = null;


    PurchaseStatus(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static PurchaseStatus getPurchaseStatusByCode(int code) {
        if (mapping == null) {
            synchronized (ErrorCode.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (PurchaseStatus purchaseStatus : PurchaseStatus.values()) {
                        mapping.put(purchaseStatus.getStatus(), purchaseStatus);
                    }
                }
            }
        }
        return mapping.get(code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
