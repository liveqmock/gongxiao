package com.yhglobal.gongxiao.edi.entity.ro;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author 葛灿
 */
public class MainWarehouseTransferSubject {
    @XmlElement(name = "dataLable")
    private String dataLable;
    @XmlElement(name = "transferFileID")
    private String transferFileID;
    @XmlElement(name = "transferLable")
    private String transferLable;
    @XmlElement(name = "dataSender")
    private String dataSender;
    @XmlElement(name = "dataReceiver")
    private String dataReceiver;
    @XmlElement(name = "transferRecordCount")
    private String transferRecordCount;
    @XmlElement(name = "dataGenerationDate")
    private String dataGenerationDate;
    @XmlElement(name = "documentType")
    private String documentType;
    @XmlElement(name = "buyerName")
    private String buyerName;
    @XmlElement(name = "vendorName")
    private String vendorName;
    @XmlElement(name = "vendorId")
    private String vendorId;
    @XmlElement(name = "comments")
    private String comments;
    /**备件库出库单号(退货备件单ros中独有)*/
    @XmlElement(name = "deliveryNumber")
    private String deliveryNumber;
    @XmlElement(name = "orderStatus")
    private String orderStatus;

    @XmlTransient
    public String getOrderStatus() {
        return orderStatus;
    }
    @XmlTransient
    public String getDeliveryNumber() {
        return deliveryNumber;
    }
    @XmlTransient
    public String getDataLable() {
        return dataLable;
    }
    @XmlTransient
    public String getTransferFileID() {
        return transferFileID;
    }
    @XmlTransient
    public String getTransferLable() {
        return transferLable;
    }
    @XmlTransient
    public String getDataSender() {
        return dataSender;
    }
    @XmlTransient
    public String getDataReceiver() {
        return dataReceiver;
    }
    @XmlTransient
    public String getTransferRecordCount() {
        return transferRecordCount;
    }
    @XmlTransient
    public String getDataGenerationDate() {
        return dataGenerationDate;
    }
    @XmlTransient
    public String getDocumentType() {
        return documentType;
    }
    @XmlTransient
    public String getBuyerName() {
        return buyerName;
    }
    @XmlTransient
    public String getVendorName() {
        return vendorName;
    }
    @XmlTransient
    public String getVendorId() {
        return vendorId;
    }
    @XmlTransient
    public String getComments() {
        return comments;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public void setDataLable(String dataLable) {
        this.dataLable = dataLable;
    }

    public void setTransferFileId(String transferFileID) {
        this.transferFileID = transferFileID;
    }

    public void setTransferLable(String transferLable) {
        this.transferLable = transferLable;
    }

    public void setDataSender(String dataSender) {
        this.dataSender = dataSender;
    }

    public void setDataReceiver(String dataReceiver) {
        this.dataReceiver = dataReceiver;
    }

    public void setTransferRecordCount(String transferRecordCount) {
        this.transferRecordCount = transferRecordCount;
    }

    public void setDataGenerationDate(String dataGenerationDate) {
        this.dataGenerationDate = dataGenerationDate;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
