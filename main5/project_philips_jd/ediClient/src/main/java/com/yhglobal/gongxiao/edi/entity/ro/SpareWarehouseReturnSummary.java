package com.yhglobal.gongxiao.edi.entity.ro;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author 葛灿
 */
public class SpareWarehouseReturnSummary {

    /**数据标识*/
    @XmlElement(name = "DataLable")
    private String dataLable;
    /**单据号码*/
    @XmlElement(name = "DocumentNumber")
    private String documentNumber;
    /**单据细目记录数*/
    @XmlElement(name = "ReturnDetailCount")
    private Integer returnDetailCount;
    /**合计品种*/
    @XmlElement(name = "TotalCategory")
    private Integer totalCategory;
    /**合计数量*/
    @XmlElement(name = "TotalQuantity")
    private Integer totalQuantity;
    /**总金额*/
    @XmlElement(name = "TotalAmount")
    private String totalAmount;
    /**退货日期*/
    @XmlElement(name = "RefundDate")
    private String refundDate;
    /**发货方式*/
    @XmlElement(name = "DeliveryWay")
    private String deliveryWay;
    /**收货地址*/
    @XmlElement(name = "ReceivingAddress")
    private String receivingAddress;
    /**退货类型*/
    @XmlElement(name = "ReturnType")
    private String returnType;
    /**订单状态*/
    @XmlElement(name = "OrderStatus")
    private String orderStatus;
    /**备注*/
    @XmlElement(name = "Comments")
    private String comments;
    /**机构编码*/
    @XmlElement(name = "OrgId")
    private String orgId;
    /**机构名称*/
    @XmlElement(name = "OrgName")
    private String orgName;
    /**备件库编码*/
    @XmlElement(name = "StoreId")
    private String storeId;
    /**备件库名称*/
    @XmlElement(name = "StoreName")
    private String storeName;

    @XmlTransient
    public String getDataLable() {
        return dataLable;
    }
    @XmlTransient
    public String getDocumentNumber() {
        return documentNumber;
    }
    @XmlTransient
    public Integer getReturnDetailCount() {
        return returnDetailCount;
    }
    @XmlTransient
    public Integer getTotalCategory() {
        return totalCategory;
    }
    @XmlTransient
    public Integer getTotalQuantity() {
        return totalQuantity;
    }
    @XmlTransient
    public String getTotalAmount() {
        return totalAmount;
    }
    @XmlTransient
    public String getRefundDate() {
        return refundDate;
    }
    @XmlTransient
    public String getDeliveryWay() {
        return deliveryWay;
    }
    @XmlTransient
    public String getReceiveAddress() {
        return receivingAddress;
    }
    @XmlTransient
    public String getReturnType() {
        return returnType;
    }
    @XmlTransient
    public String getOrderStatus() {
        return orderStatus;
    }
    @XmlTransient
    public String getComments() {
        return comments;
    }
    @XmlTransient
    public String getOrgId() {
        return orgId;
    }
    @XmlTransient
    public String getOrgName() {
        return orgName;
    }
    @XmlTransient
    public String getStoreId() {
        return storeId;
    }
    @XmlTransient
    public String getStoreName() {
        return storeName;
    }

    public void setDataLable(String dataLable) {
        this.dataLable = dataLable;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public void setReturnDetailCount(Integer returnDetailCount) {
        this.returnDetailCount = returnDetailCount;
    }

    public void setTotalCategory(Integer totalCategory) {
        this.totalCategory = totalCategory;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public void setDeliveryWay(String deliveryWay) {
        this.deliveryWay = deliveryWay;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receivingAddress = receiveAddress;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
