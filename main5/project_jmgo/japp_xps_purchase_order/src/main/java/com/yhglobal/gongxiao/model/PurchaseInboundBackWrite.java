package com.yhglobal.gongxiao.model;

import java.io.Serializable;

/**
 * 采购入库单回写
 *
 * @author: 陈浩
 * @create: 2018-02-06 18:36
 **/
public class PurchaseInboundBackWrite implements Serializable{

    /**
     * 入库单id
     */
    private String  gongxiaoInboundOrderNo;
    /**
     * 在途商品数量
     */
    private int inTransitQuantity;
    /**
     * 已入库的商品数量
     */
    private int inStockQuantity;
    /**
     *当前被拒收的次品数量
     */
    private int imperfectQuantity;
    /**
     *商品入库状态
     */
    private byte orderStatus;

    public String getGongxiaoInboundOrderNo() {
        return gongxiaoInboundOrderNo;
    }

    public void setGongxiaoInboundOrderNo(String gongxiaoInboundOrderNo) {
        this.gongxiaoInboundOrderNo = gongxiaoInboundOrderNo;
    }

    public int getInTransitQuantity() {
        return inTransitQuantity;
    }

    public void setInTransitQuantity(int inTransitQuantity) {
        this.inTransitQuantity = inTransitQuantity;
    }

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public int getImperfectQuantity() {
        return imperfectQuantity;
    }

    public void setImperfectQuantity(int imperfectQuantity) {
        this.imperfectQuantity = imperfectQuantity;
    }

    public byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(byte orderStatus) {
        this.orderStatus = orderStatus;
    }
}
