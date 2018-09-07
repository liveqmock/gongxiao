package com.yhglobal.gongxiao.type;

/**
 * WMS订单类型代码
 */
public enum WmsOrderType {

    INBOUND_ORDER(10,"入库单"),
    OUTBOUND_ORDER(20,"出库单"),

    INBOUND_FOR_PURCHASING_PRODUCT(101, "采购入库单/采购"),
    INBOUND_FOR_TRANSFERRING_PRODUCT(102, "调拨入库单/移库入库"),
    INBOUND_FOR_RETURNING_PRODUCT(103, "退货入库单/退货"),
    INBOUND_FOR_OTHER_REASON(90006, "其它入库"),

    OUTBOUND_FOR_TRANSFERRING_PRODUCT(301, "调拨出库单/移库"),
    OUTBOUND_FOR_RETURN_PRODUCT(5252,"采购退货出库"),
    OUTBOUND_FOR_B2B_SELLING_PRODUCT(901, "普通出库单"), //注：该类型用于B2B
    OUTBOUND_FOR_EXCHANGING_PRODUCT(502, "换货出库单/换货出库"),
    OUTBOUND_FOR_B2C_SELLING_PRODUCT(201, "交易出库单/成品出库"), //注：该类型专用于B2C
    OUTBOUND_FOR_DISCARDING_PRODUCT(90004, "报废出库"),
    OUTBOUND_FOR_OTHER_REASON(90005, "其它出库");

    //??(5381, "品质调整单"),
    //??(5380, "Plan调整单"),
    //??(10700, "增值服务单"),
    //??(10701, "加工服务单"),
    //??(10702, "包材耗材单"),
    //??(1300, "退供出库");


    WmsOrderType(int inboundOrderCode, String inboundOrderDesc) {
        this.inboundOrderCode = inboundOrderCode;
        this.inboundOrderDesc = inboundOrderDesc;
    }

    int inboundOrderCode; //WMS订单类型代码
    String inboundOrderDesc; //WMS订单类型名

    public int getInboundOrderCode() {
        return inboundOrderCode;
    }

    public String getInboundOrderDesc() {
        return inboundOrderDesc;
    }
}
