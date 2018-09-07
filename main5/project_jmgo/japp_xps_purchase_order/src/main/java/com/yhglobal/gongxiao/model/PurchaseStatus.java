package com.yhglobal.gongxiao.model;

/**
 * 采购状态
 *
 * @author: 陈浩
 **/
public enum PurchaseStatus {

    //采购
    ORDER_IMPORT((byte)0,"编辑状态"),
    ORDER_SUBMIT((byte)1,"采购单提交"),
    ORDER_CUT_PAYMENT((byte)3,"已扣款"),
    ALREADY_PLAN((byte)70,"已发货,待入库"),
    PART_INSTOCK((byte)80,"部分入库"),

    PURCHASE_RETURN_SIGNED((byte)3,"已签收"),
    //流水
    ORDER_COMPLETE((byte)90,"正常完成"),

    EXCEPTION_COMPLETE_HANDLED((byte)91,"异常完成,已处理"),

    EXCEPTION_COMPLETE_UNHANDLED((byte)92,"异常完成,未处理"),

    GENERATE_COUPON((byte)95,"生成返利成功")
    ;
    private byte status;

    private String message;

    PurchaseStatus(byte status, String message) {
        this.status = status;
        this.message = message;
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
