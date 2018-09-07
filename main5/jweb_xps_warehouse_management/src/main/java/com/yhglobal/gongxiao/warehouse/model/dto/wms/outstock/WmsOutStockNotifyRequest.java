package com.yhglobal.gongxiao.warehouse.model.dto.wms.outstock;


import com.yhglobal.gongxiao.warehouse.model.dto.wms.DeliverRequirements;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.PickerInfo;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.ReceiverInfo;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.SenderInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 向WMS推送出库订单通知
 * @author liukai
 */
public class WmsOutStockNotifyRequest implements Serializable{
    private String ckid;                              //仓库id
    private String projectCode;            //项目编码
    private String custCode;                         //客户代码  必选
    private String warehouseCode;                    //仓库编码  可选
    private String orderNo;                          //订单号   必选
    private String sourceOrderNo;                      //订单来源  必选
    private String orderSource;                      //订单来源  必选
    private int orderType;                           //操作类型/订单类型 必选
    private String orderFlage;                       //
    private String transportMode;                    //出库方式  可选
    private Date orderCreateTime;                    //订单创建时间 可选
    private String expectSendTime;                   //要求出库时间  可选
    private DeliverRequirements deliverRequirements; //配送要求 可选
    private String supplierCode;                    //目的地编码
    private String supplierName;                    //目的地名称
    private PickerInfo pickerInfo;                   //提货人信息
    private ReceiverInfo receiverInfo;               //收货方信息   必选
    private SenderInfo senderInfo;                   //发货方信息   必选
    private List<StockOutOrderItem> orderItemList;    //订单商品信息  必选
    private String remark;                           //备注   可选

    public String getCkid() {
        return ckid;
    }

    public void setCkid(String ckid) {
        this.ckid = ckid;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
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

    public String getSourceOrderNo() {
        return sourceOrderNo;
    }

    public void setSourceOrderNo(String sourceOrderNo) {
        this.sourceOrderNo = sourceOrderNo;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getOrderFlage() {
        return orderFlage;
    }

    public void setOrderFlage(String orderFlage) {
        this.orderFlage = orderFlage;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getExpectSendTime() {
        return expectSendTime;
    }

    public void setExpectSendTime(String expectSendTime) {
        this.expectSendTime = expectSendTime;
    }

    public DeliverRequirements getDeliverRequirements() {
        return deliverRequirements;
    }

    public void setDeliverRequirements(DeliverRequirements deliverRequirements) {
        this.deliverRequirements = deliverRequirements;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public PickerInfo getPickerInfo() {
        return pickerInfo;
    }

    public void setPickerInfo(PickerInfo pickerInfo) {
        this.pickerInfo = pickerInfo;
    }

    public ReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(ReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public SenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(SenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public List<StockOutOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<StockOutOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
