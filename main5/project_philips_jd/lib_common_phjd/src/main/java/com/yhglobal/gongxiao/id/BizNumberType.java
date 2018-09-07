package com.yhglobal.gongxiao.id;


/**
 * 定义需要生成编号的 业务类型
 */
public enum BizNumberType {

    //请业务层按需添加各自的业务类型和编号前缀
    WMS_TRACE_ID("WMS系统","WMS"),

    PURCHASE_UNIQUE_NO("采购唯一号", "PU"),
    PURCHASE_ORDER_NO("采购单号", "PUR"),
    PURCHASE_ORDER_ITEM("采购明细单号", "PIO"),
    PURCHASE_COUPON_CONFIRM_FLOW("采购返利确认流水", "PCCF"),
    PLANE_INBOUND_NO("计划入库单号", "PL"),
    PURCHASE_RETURN_ORDER_NO("采购退货单号", "PRO"),

    STOCK_UNIQUE_NO("仓储唯一号", "SU"),
    STOCK_POIN_ORDER_NO("采购入库单号", "POIN"),
    STOCK_SOIN_ORDER_NO("销售退入库单号", "SOIN"),
    STOCK_POOUT_ORDER_NO("采购退货出库单号", "POOUT"),
    STOCK_SOOUT_ORDER_NO("销售出库单号", "SOOUT"),
    STOCK_OTHER_IN_NO("其他入库单号", "OTIN"),
    STOCK_OTHER_OUT_NO("其他出库单号", "OTOUT"),
    STOCK_TSF_IN_NO("调拨入库单号", "TSFIN"),
    STOCK_TSF_OUT_NO("调拨出库单号", "TSFOUT"),
    OTHER_IN_NO("其他入库","OT"),
    OTHER_OUT_NO("其他出库","OT"),
    STOCK_BATCH_NO("批次号", "PC"),
    TRANSFER_NO("调拨单号","TSF"),


    SALES_RETURN_ORDER_NO("销售退货单号", "SRET"),
    SALE_PLANE_ORDER_NO("预售单号","SP"),
    SALES_ORDER_NO("销售单号", "SAL"),
    SALES_CASH_CONFIRM("收款确认流水号", "CRNO"),
    SALES_SCHEDULE_ORDER_NO("预约发货单号", "SON"),
    SALE_UNIQUE_NO("销售唯一号", "SU"),
    SALES_CASH_FLOW("销售单现金流水号", "SCAF"),
    SALES_COUPON_FLOW("销售单返利流水号", "SCOF"),
    SALES_PREPAID_FLOW("销售单代垫流水号", "SPPF"),
    SALES_PRESTORED_FLOW("销售单预存流水号", "SPSF"),

    PAYMENT_DISTRIBUTOR_CASH_FLOW("AD现金流水号", "DCAF"),
    PAYMENT_DISTRIBUTOR_COUPON_FLOW("AD返利流水号", "DCOF"),
    PAYMENT_DISTRIBUTOR_PREPAID_FLOW("AD代垫流水号", "DPPF"),

    PAYMENT_YHGLOBAL_COUPON_BUFFER_FLOW("越海返利缓冲账号流水", "YCBF"),
    PAYMENT_YHGLOBAL_PREPAID_BUFFER_FLOW("越海代垫缓冲账号流水", "YPBF"),

    PAYMENT_YHGLOBAL_COUPON_RECEIVED_FLOW("越海返利实收账号流水", "YCRF"),
    PAYMENT_YHGLOBAL_PREPAID_RECEIVED_FLOW("越海代垫实收账号流水", "YPRF"),

    PAYMENT_DISTRUBUTOR_COUPON_BUFFER_FLOW("ad返利过账账户流水", "DCBF"),
    PAYMENT_DISTRUBUTOR_PREPAID_BUFFER_FLOW("ad代垫过账账户流水", "DPBF"),

    PAYMENT_SUPPLIER_COUPON_FLOW("供应商返利上账账户流水", "SCF"),
    PAYMENT_SUPPLIER_PREPAID_FLOW("供应商返利上账账户流水", "SPF"),
    PAYMENT_SUPPLIER_SELL_HIGH_FLOW("差价账户流水", "SSHF"),

    SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT_TRANSFER_FLOW("供应商单价折扣冻结账户交易流水", "SUPDFATF"),


    YHGLOBAL_PREPAID_WRITEOFF_FLOW("越海代垫核销流水", "YPWF"),
    YHGLOBAL_PREPAID_INFO("越海代垫付款单", "YPI"),

    YHGLOBAL_COUPON_WRITEROFF_FLOW("越海返利核销流水", "YCWF"),

    DIAO_BO_OREDER("调拨单", "DB");

    BizNumberType(String bizName, String prefix) {
        this.bizName = bizName;
        this.prefix = prefix;
    }

    String bizName;  //业务名称

    String prefix;  //业务编号的固定前缀 正常应该是大写的字母组合


    public String getBizName() {
        return bizName;
    }

    public String getPrefix() {
        return prefix;
    }

}
