package com.yhglobal.gongxiao.model;

import java.io.Serializable;

/**
 * 采购回写订单信息
 *
 * @author: 陈浩
 * @create: 2018-02-26 16:54
 **/
public class PurchaseWriteBackOrder implements Serializable{
    /**
     * 采购单号
     */
    private String purchaseOrderNo;

    /**
     * 预约收货单单号
     */
    private String gongxiaoInboundOrderNo;


    public String getGongxiaoInboundOrderNo() {
        return gongxiaoInboundOrderNo;
    }

    public void setGongxiaoInboundOrderNo(String gongxiaoInboundOrderNo) {
        this.gongxiaoInboundOrderNo = gongxiaoInboundOrderNo;
    }


    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }


}
