package com.yhglobal.gongxiao.edi.entity.po;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author: 葛灿
 */
public class PurchaseOrderDetail {
    @XmlElement(name = "DataLable")
    private String dataLable;
    @XmlElement(name = "DocumentNumber")
    private String documentNumber;
    @XmlElement(name = "CurrentRecordNumber")
    private Integer currentRecordNumber;
    @XmlElement(name = "VendorProductID")
    private String vendorProductId;
    @XmlElement(name = "BuyerProductID")
    private String buyerProductId;
    @XmlElement(name = "ProductCode")
    private String productCode;
    @XmlElement(name = "ProductName")
    private String productName;
    @XmlElement(name = "ListPrice")
    private String listPrice;
    @XmlElement(name = "Quantity")
    private Integer quantity;
    @XmlElement(name = "CostPrice")
    private String costPrice;
    @XmlElement(name = "Discount")
    private String discount;
    @XmlElement(name = "BackorderProcessing")
    private String backorderProcessing;
    @XmlElement(name = "Comments")
    private String comments;

    @XmlTransient
    public String getDataLable() {
        return dataLable;
    }
    @XmlTransient
    public String getDocumentNumber() {
        return documentNumber;
    }
    @XmlTransient
    public Integer getCurrentRecordNumber() {
        return currentRecordNumber;
    }
    @XmlTransient
    public String getVendorProductId() {
        return vendorProductId;
    }
    @XmlTransient
    public String getBuyerProductId() {
        return buyerProductId;
    }
    @XmlTransient
    public String getProductCode() {
        return productCode;
    }
    @XmlTransient
    public String getProductName() {
        return productName;
    }
    @XmlTransient
    public String getListPrice() {
        return listPrice;
    }
    @XmlTransient
    public Integer getQuantity() {
        return quantity;
    }
    @XmlTransient
    public String getCostPrice() {
        return costPrice;
    }
    @XmlTransient
    public String getDiscount() {
        return discount;
    }
    @XmlTransient
    public String getBackorderProcessing() {
        return backorderProcessing;
    }
    @XmlTransient
    public String getComments() {
        return comments;
    }

    public void setDataLable(String dataLable) {
        this.dataLable = dataLable;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public void setCurrentRecordNumber(Integer currentRecordNumber) {
        this.currentRecordNumber = currentRecordNumber;
    }

    public void setVendorProductId(String vendorProductId) {
        this.vendorProductId = vendorProductId;
    }

    public void setBuyerProductId(String buyerProductId) {
        this.buyerProductId = buyerProductId;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setBackorderProcessing(String backorderProcessing) {
        this.backorderProcessing = backorderProcessing;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
