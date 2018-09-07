package com.yhglobal.gongxiao.sales.model;

/**
 * 销售单对应的出库单信息
 *
 * @author: 葛灿
 */
public class SalesOutboundOrderInfo {

    /**
     * 出库单号
     */
    private String outboundOrderNo;

    /**
     * 出库单状态
     * 1--已下发仓库
     * 2--仓库已出库
     * 3 -已签收
     */
    private int status;
    /**出库单总数量*/
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOutboundOrderNo() {
        return outboundOrderNo;
    }

    public void setOutboundOrderNo(String outboundOrderNo) {
        this.outboundOrderNo = outboundOrderNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
