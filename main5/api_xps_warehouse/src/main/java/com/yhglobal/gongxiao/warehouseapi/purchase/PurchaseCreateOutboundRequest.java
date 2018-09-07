package com.yhglobal.gongxiao.warehouseapi.purchase;

import com.yhglobal.gongxiao.warehouseapi.model.PlanOutboundOrderItem;

import java.util.List;

/**
 * 采购退货 创建出库单请求
 */
public class PurchaseCreateOutboundRequest {

    private String sourceChannelId;
    private int outboundType;
    private String projectId;
    private String customerId;
    private String traceNo;
    private String recipientName;
    private String recipientPhoneNumber;
    private String recipientAddress;
    private String warehouseId;
    private String warehouseName;
    private int shippingMode;
    private String logisticsCompanyName;
    private String logisticsNo;
    private String note;
    private int totalQuantity;
    private List<PlanOutboundOrderItem> itemList;
    private String signature;
    private String uniqueNo;

    public PurchaseCreateOutboundRequest(String sourceChannelId, int outboundType, String projectId, String customerId, String traceNo, String recipientName, String recipientPhoneNumber, String recipientAddress, String warehouseId, String warehouseName, int shippingMode, String logisticsCompanyName, String logisticsNo, String note, int totalQuantity, List<PlanOutboundOrderItem> itemList, String signature, String uniqueNo) {
        this.sourceChannelId = sourceChannelId;
        this.outboundType = outboundType;
        this.projectId = projectId;
        this.customerId = customerId;
        this.traceNo = traceNo;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.recipientAddress = recipientAddress;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.shippingMode = shippingMode;
        this.logisticsCompanyName = logisticsCompanyName;
        this.logisticsNo = logisticsNo;
        this.note = note;
        this.totalQuantity = totalQuantity;
        this.itemList = itemList;
        this.signature = signature;
        this.uniqueNo = uniqueNo;
    }

    public String getSourceChannelId() {
        return sourceChannelId;
    }

    public void setSourceChannelId(String sourceChannelId) {
        this.sourceChannelId = sourceChannelId;
    }

    public int getOutboundType() {
        return outboundType;
    }

    public void setOutboundType(int outboundType) {
        this.outboundType = outboundType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public void setRecipientPhoneNumber(String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
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

    public List<PlanOutboundOrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<PlanOutboundOrderItem> itemList) {
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
