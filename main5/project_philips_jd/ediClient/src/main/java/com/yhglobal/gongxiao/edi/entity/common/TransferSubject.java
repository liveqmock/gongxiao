package com.yhglobal.gongxiao.edi.entity.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author: 葛灿
 */
public class TransferSubject {
    @XmlElement(name = "DataLable")
    private String dataLable;
    @XmlElement(name = "TransferFileID")
    private String transferFileId;
    @XmlElement(name = "TransferLable")
    private String transferLable;
    @XmlElement(name = "DataSender")
    private String dataSender;
    @XmlElement(name = "DataReceiver")
    private String dataReceiver;
    @XmlElement(name = "TransferRecordCount")
    private String transferRecordCount;
    @XmlElement(name = "DataGenerationDate")
    private String dataGenerationDate;
    @XmlElement(name = "DocumentType")
    private String documentType;
    @XmlElement(name = "VendorName")
    private String vendorName;
    @XmlElement(name = "VendorID")
    private String vendorId;
    @XmlElement(name = "BuyerName")
    private String buyerName;
    @XmlElement(name = "BuyerID")
    private String BuyerID;
    @XmlElement(name = "Comments")
    private String comments;
    /**
     * 备件库出库单号(退货备件单ros中独有)
     */
    @XmlElement(name = "DeliveryNumber")
    private String deliveryNumber;
    @XmlElement(name = "OrderStatus")
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
    public String getTransferFileId() {
        return transferFileId;
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
    public String getVendorName() {
        return vendorName;
    }

    @XmlTransient
    public String getVendorId() {
        return vendorId;
    }

    @XmlTransient
    public String getBuyerName() {
        return buyerName;
    }

    @XmlTransient
    public String getComments() {
        return comments;
    }
    @XmlTransient
    public String getBuyerID() {
        return BuyerID;
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

    public void setTransferFileId(String transferFileId) {
        this.transferFileId = transferFileId;
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


    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setBuyerID(String buyerID) {
        BuyerID = buyerID;
    }
}
