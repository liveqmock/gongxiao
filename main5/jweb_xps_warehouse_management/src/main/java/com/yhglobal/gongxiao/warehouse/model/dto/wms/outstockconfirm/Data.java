package com.yhglobal.gongxiao.warehouse.model.dto.wms.outstockconfirm;


import com.yhglobal.gongxiao.warehouse.model.dto.wms.DriverInfo;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StockOutOrderConfirmOrderItemDto;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    private int ckid;                                               //仓库ID  必选
    private int messageID;                                          //报文ID  必选
    private String orderNo;                                         //订单号   必选
    private String sourceOrderNo;                                   //来源单号
    private String outBizCode;                                      //仓库号  必选
    private String orderType;                                      //订单操作类型    可选
    private int confirmType;                                        //出库单多次确认   必选
    private DriverInfo driverInfo;                                  //可选
    private List<StockOutOrderConfirmOrderItemDto> orderItems;      //出库明细  必选

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }

    public int getCkid() {
        return ckid;
    }

    public void setCkid(int ckid) {
        this.ckid = ckid;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSourceOrderNo() {
        return sourceOrderNo;
    }

    public void setSourceOrderNo(String sourceOrderNo) {
        this.sourceOrderNo = sourceOrderNo;
    }

    public String getOutBizCode() {
        return outBizCode;
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(int confirmType) {
        this.confirmType = confirmType;
    }

    public List<StockOutOrderConfirmOrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<StockOutOrderConfirmOrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }
}
