package com.yhglobal.gongxiao.warehouse.model.dto.wms.cancel;

import java.io.Serializable;

/**
 * 取消订单请求类
 * @author : liukai
 */
public class WmsCancelOrderRequest implements Serializable{
    private int ckid;               //仓库ID
    private String warehouseCode;   //仓库编码
    private String orderNo;         //订单编码
    private String ownerCode;       //货主
    private String orderType;       //取消订单

    public int getCkid() {
        return ckid;
    }

    public void setCkid(int ckid) {
        this.ckid = ckid;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
