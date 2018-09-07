package com.yhglobal.gongxiao.edi.entity.po;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author: 葛灿
 */
public class PurchaseOrderSummary {
    @XmlElement(name = "DataLable")
    private String dataLable;
    @XmlElement(name = "PrePurchaseCode")
    private String prePurchaseCOde;
    @XmlElement(name = "DocumentNumber")
    private String documentNumber;
    @XmlElement(name = "DocumentRecordCount")
    private Integer documentRecordCount;
    @XmlElement(name = "TotalCategory")
    private Integer totalCategory;
    @XmlElement(name = "TotalQuantity")
    private Integer totalQuantity;
    @XmlElement(name = "TotalAmount")
    private String totalAmount;
    @XmlElement(name = "TotalCostAmount")
    private String totalCostAmount;
    @XmlElement(name = "PorchaseDate")
    private String porchaseDate;
    @XmlElement(name = "RequiredArrivalDate")
    private String requiredArrivalDate;
    @XmlElement(name = "PurchasedBy")
    private String purchasedBy;
    @XmlElement(name = "PurchaseContact")
    private String purchaseContact;
    @XmlElement(name = "DistributionCenter")
    private String distributionCenter;
    @XmlElement(name = "distributionCenterID")
    private String distributionCenterID;
    @XmlElement(name = "WarehouseID")
    private String warehouseId;
    @XmlElement(name = "ReceivingAddress")
    private String receivingAddress;
    @XmlElement(name = "Arrived")
    private String arrived;
    @XmlElement(name = "Comments")
    private String comments;

    @XmlTransient
    public String getDataLable() {
        return dataLable;
    }
    @XmlTransient
    public String getPrePurchaseCOde() {
        return prePurchaseCOde;
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
    public String getPorchaseDate() {
        return porchaseDate;
    }
    @XmlTransient
    public String getRequiredArrivalDate() {
        return requiredArrivalDate;
    }
    @XmlTransient
    public String getPurchasedBy() {
        return purchasedBy;
    }
    @XmlTransient
    public String getPurchaseContact() {
        return purchaseContact;
    }
    @XmlTransient
    public String getDistributionCenter() {
        return distributionCenter;
    }
    @XmlTransient
    public String getDistributionCenterID() {
        return distributionCenterID;
    }
    @XmlTransient
    public String getWarehouseId() {
        return warehouseId;
    }
    @XmlTransient
    public String getReceivingAddress() {
        return receivingAddress;
    }
    @XmlTransient
    public String getArrived() {
        return arrived;
    }
    @XmlTransient
    public String getComments() {
        return comments;
    }

    public void setDataLable(String dataLable) {
        this.dataLable = dataLable;
    }

    public void setPrePurchaseCOde(String prePurchaseCOde) {
        this.prePurchaseCOde = prePurchaseCOde;
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

    public void setPorchaseDate(String porchaseDate) {
        this.porchaseDate = porchaseDate;
    }

    public void setRequiredArrivalDate(String requiredArrivalDate) {
        this.requiredArrivalDate = requiredArrivalDate;
    }

    public void setPurchasedBy(String purchasedBy) {
        this.purchasedBy = purchasedBy;
    }

    public void setPurchaseContact(String purchaseContact) {
        this.purchaseContact = purchaseContact;
    }

    public void setDistributionCenter(String distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    public void setDistributionCenterID(String distributionCenterID) {
        this.distributionCenterID = distributionCenterID;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public void setArrived(String arrived) {
        this.arrived = arrived;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
