package com.yhglobal.gongxiao.edi.entity.ro;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author 葛灿
 */
public class MainWarehouseReturnSummary {

    /**
     * 数据标识
     */
    @XmlElement(name = "DataLable")
    private String dataLable;
    /**
     * 退货单号
     */
    @XmlElement(name = "DocumentNumber")
    private String documentNumber;
    /**
     * 单据细目记录数
     */
    @XmlElement(name = "DocumentRecordCount")
    private Integer documentRecordCount;
    /**
     * 合计品种
     */
    @XmlElement(name = "TotalCategory")
    private Integer totalCategory;
    /**
     * 合计数量
     */
    @XmlElement(name = "TotalQuantity")
    private Integer totalQuantity;
    /**
     * 总金额
     */
    @XmlElement(name = "TotalAmount")
    private String totalAmount;
    /**
     * 总实际金额
     */
    @XmlElement(name = "TotalCostAmount")
    private String totalCostAmount;
    /**
     * 退货日期
     */
    @XmlElement(name = "RefundDate")
    private String refundDate;
    /**
     * 运输方式
     */
    @XmlElement(name = "DeliveryWay")
    private String deliveryWay;
    /**
     * 收货地址
     */
    @XmlElement(name = "ReceivingAddress")
    private String receivingAddress;
    /**
     * 退货类型
     */
    @XmlElement(name = "ReturnType")
    private String returnType;
    /**
     * 退货商品类型
     */
    @XmlElement(name = "ReturnsOrderType")
    private String returnsOrderType;
    /**
     * 订单状态
     */
    @XmlElement(name = "OrderStatus")
    private String orderStatus;
    /**
     * 备注
     */
    @XmlElement(name = "Comments")
    private String comments;
    /**
     * 配送中心ID
     */
    @XmlElement(name = "OrgId")
    private String orgId;
    /**
     * 配送中心名称
     */
    @XmlElement(name = "OrgName")
    private String orgName;
    /**
     * 仓库ID
     */
    @XmlElement(name = "StoreId")
    private String storeId;
    /**
     * 仓库名称
     */
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
    public Integer getDocumentRecordCount() {
        return documentRecordCount;
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
    public String getTotalCostAmount() {
        return totalCostAmount;
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
    public String getReceivingAddress() {
        return receivingAddress;
    }
    @XmlTransient
    public String getReturnType() {
        return returnType;
    }
    @XmlTransient
    public String getReturnsOrderType() {
        return returnsOrderType;
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

    public void setDocumentRecordCount(Integer documentRecordCount) {
        this.documentRecordCount = documentRecordCount;
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

    public void setTotalCostAmount(String totalCostAmount) {
        this.totalCostAmount = totalCostAmount;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public void setDeliveryWay(String deliveryWay) {
        this.deliveryWay = deliveryWay;
    }

    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public void setReturnsOrderType(String returnsOrderType) {
        this.returnsOrderType = returnsOrderType;
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
