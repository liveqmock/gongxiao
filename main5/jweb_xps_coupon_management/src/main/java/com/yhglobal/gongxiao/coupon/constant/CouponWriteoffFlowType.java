package com.yhglobal.gongxiao.coupon.constant;

/**
 * @author 王帅
 */
public enum CouponWriteoffFlowType {

    CONFIRM_COUPON_WRITEOFF(1,"核销返利确认"),
    CANCEL_COUPON_WRITEOFF(9,"取消返利确认");

    Integer code;
    String flowTypeName;

    CouponWriteoffFlowType(Integer code, String flowTypeName) {
        this.code = code;
        this.flowTypeName = flowTypeName;
    }

    public Integer getCode() {
        return code;
    }

    public String getFlowTypeName() {
        return flowTypeName;
    }

}
