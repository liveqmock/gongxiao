package com.yhglobal.gongxiao.constant.sales;

/**
 * 销售出库单订单状态枚举
 *
 * @author 葛灿
 */
public enum SalesOutboundOrderStatusEnum {

    IN_OUTBOUND(1, "出库中"),

    OUTBOUND_FINISHED(2, "已出库"),

    OUTBOUND_SIGNED(3, "已签收");

    private int status;

    private String message;

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    SalesOutboundOrderStatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public static String getMessage(int code) {
        for (SalesOutboundOrderStatusEnum status : SalesOutboundOrderStatusEnum.values()) {
            if (status.getStatus() == code) {
                return status.getMessage();
            }
        }
        return null;
    }

    public static SalesOutboundOrderStatusEnum getEnumByCode(int code) {
        for (SalesOutboundOrderStatusEnum status : SalesOutboundOrderStatusEnum.values()) {
            if (status.getStatus() == code) {
                return status;
            }
        }
        return null;
    }
}
