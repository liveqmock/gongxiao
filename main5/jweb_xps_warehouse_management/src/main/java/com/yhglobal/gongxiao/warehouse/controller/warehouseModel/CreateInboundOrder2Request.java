package com.yhglobal.gongxiao.warehouse.controller.warehouseModel;


import com.yhglobal.gongxiao.warehouse.model.dto.PlanInboundOrderItem;

import java.util.List;

/**
 * 创建入库单的响应类
 */
public class CreateInboundOrder2Request {
    private String orderSourceNo;
    private String sourceChannelId;
    private int inboundType;
    private String projectId;
    private String traceNo;
    private String senderName;
    private String senderPhoneNo;
    private String warehouseId;
    private String warehouseName;
    private String deliverAddress;
    private int shippingMode;
    private String logisticsCompanyName;
    private String logisticsNo;
    private String note;
    private int totalQuantity;
    private List<PlanInboundOrderItem> itemList;
    private String signature;
    private String uniqueNo;

    public String getOrderSourceNo() {
        return orderSourceNo;
    }

    public void setOrderSourceNo(String orderSourceNo) {
        this.orderSourceNo = orderSourceNo;
    }

    public String getSourceChannelId() {
        return sourceChannelId;
    }

    public void setSourceChannelId(String sourceChannelId) {
        this.sourceChannelId = sourceChannelId;
    }

    public int getInboundType() {
        return inboundType;
    }

    public void setInboundType(int inboundType) {
        this.inboundType = inboundType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhoneNo() {
        return senderPhoneNo;
    }

    public void setSenderPhoneNo(String senderPhoneNo) {
        this.senderPhoneNo = senderPhoneNo;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public int getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(int shippingMode) {
        this.shippingMode = shippingMode;
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<PlanInboundOrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<PlanInboundOrderItem> itemList) {
        this.itemList = itemList;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }
}
