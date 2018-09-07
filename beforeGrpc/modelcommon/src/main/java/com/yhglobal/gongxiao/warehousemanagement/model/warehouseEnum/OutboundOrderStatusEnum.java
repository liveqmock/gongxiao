package com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum;

/**
 * 出库单状态枚举类
 */
public enum OutboundOrderStatusEnum {
    OUTBOUND_ORDER_WAIT(1,"等待出库"),
    OUTBOUND_ORDER_DELIVER_PART(2,"部分发货"),
    OUTBOUND_ORDER_DELIVER_FINISH(3,"发货完成"),
    OUTBOUND_ORDER_CANCEL(4,"订单已取消"),
    OUTBOUND_ORDER_CLOSE(5,"订单已关闭"),
    OUTBOUND_ORDER_SIGHT(6,"订单已签收"),
    OUTBOUND_ORDER_SYNCWMS(7,"订单正在同步wms");

    int status;
    String statusDesc;

    OutboundOrderStatusEnum(int status, String statusDesc) {
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
