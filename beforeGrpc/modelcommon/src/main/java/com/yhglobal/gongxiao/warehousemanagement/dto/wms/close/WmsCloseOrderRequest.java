package com.yhglobal.gongxiao.warehousemanagement.dto.wms.close;


import java.io.Serializable;
import java.util.List;

/**
 * 关闭订单的请求类
 * @author liukai
 */
public class WmsCloseOrderRequest implements Serializable {
    private int ckid;               //仓库ID
    private String warehouseCode;   //仓库编码
    private String orderNo;         //订单编码
    private String ownerCode;       //货主
    private String orderType;       //取消订单
    private String note;            //备注
    private List<OrderItemDto> items;       //已出入库商品

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}
