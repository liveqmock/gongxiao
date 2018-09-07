package com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm;

import com.yhglobal.gongxiao.warehousemanagement.dto.wms.SenderInfoDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockInDetailDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Data implements Serializable{
    private int ckid;                                   //仓库ID
    private int messageID;                              //报文ID
    private String orderType;                           //订单类型
    private String orderNo;                             //订单号
    private String sourceOrderNo;                       //来源单号
    private String warehouseCode;                       //仓库编号
    private int confirmType;                            //1是分批入库，0是最后一次完成或一次完成。
    private String inboundBatchNo;                      //入库批次号
    private String receiveDate;                           //收货时间
    private List<StockInDetailDto> stockInDetails;      //收货明细
    private List<SenderInfoDto> sender;                 //

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public int getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(int confirmType) {
        this.confirmType = confirmType;
    }

    public String getInboundBatchNo() {
        return inboundBatchNo;
    }

    public void setInboundBatchNo(String inboundBatchNo) {
        this.inboundBatchNo = inboundBatchNo;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public List<StockInDetailDto> getStockInDetails() {
        return stockInDetails;
    }

    public void setStockInDetails(List<StockInDetailDto> stockInDetails) {
        this.stockInDetails = stockInDetails;
    }

    public List<SenderInfoDto> getSender() {
        return sender;
    }

    public void setSender(List<SenderInfoDto> sender) {
        this.sender = sender;
    }

    public int getCkid() {
        return ckid;
    }

    public void setCkid(int ckid) {
        this.ckid = ckid;
    }

    public String getSourceOrderNo() {
        return sourceOrderNo;
    }

    public void setSourceOrderNo(String sourceOrderNo) {
        this.sourceOrderNo = sourceOrderNo;
    }
}
