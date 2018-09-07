package com.yhglobal.gongxiao.type;

/**
 * 入库单状态枚举类
 */
public enum InboundOrderStatusEnum {
    INBOUND_ORDER_WAIT(1,"等待入库"),
    INBOUND_ORDER_RECEIVE_PART(2,"部分收货"),
    INBOUND_ORDER_RECEIVE_FINISH(3,"收货完成"),
    INBOUND_ORDER_CANCEL(4,"订单已取消"),
    INBOUND_ORDER_CLOSE(5,"订单已关闭");

    int status;
    String statusDesc;

    InboundOrderStatusEnum(int status, String statusDesc) {
        this.status = status;
        this.statusDesc = statusDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
