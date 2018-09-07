package com.yhglobal.gongxiao.warehouseapi.sales;

import com.yhglobal.gongxiao.warehouseapi.model.PlanOutboundOrderItem;

import java.util.Date;
import java.util.List;

/**
 * 销售出库 创建出库单 请求
 */
public class SalesCreateOutboundRequest {
    private String sourceChannelId;
    private int outboundType;
    private String projectId;
    private String customerId;
    private String traceNo;
    private String recipientName;
    private String recipientPhoneNumbe;
    private String recipientAddress;
    private String provinceName;
    private String cityName;
    private int shippingMode;
    private String logisticsCompanyName;
    private String logisticsNo;
    private String note;
    private int totalQuantity;
    private List<PlanOutboundOrderItem> itemLis;
    private String signature;
    private String uniqueNo;
    private Date arrivalDate;
    private long userId;
    private String userName;

    public SalesCreateOutboundRequest() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getRecipientPhoneNumbe() {
        return recipientPhoneNumbe;
    }

    public void setRecipientPhoneNumbe(String recipientPhoneNumbe) {
        this.recipientPhoneNumbe = recipientPhoneNumbe;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public List<PlanOutboundOrderItem> getItemLis() {
        return itemLis;
    }

    public void setItemLis(List<PlanOutboundOrderItem> itemLis) {
        this.itemLis = itemLis;
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

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
