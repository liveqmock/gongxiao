package com.yhglobal.gongxiao.edi.entity.poa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author 葛灿
 */
public class POAcknowledgeSummary {
    /**数据标识*/
    @XmlElement(name = "DataLable")
    private String dataLable;
    /**单据号码*/
    @XmlElement(name = "DocumentNumber")
    private String documentNumber;
    /**单据细目记录数*/
    @XmlElement(name = "DocumentRecordCount")
    private Integer documentRecordCount;
    /**合计品种	*/
    @XmlElement(name = "TotalCategory")
    private Integer totalCategory;
    /**合计数量	*/
    @XmlElement(name = "TotalQuantity")
    private Integer totalQuantity;
    /**总金额	*/
    @XmlElement(name = "TotalAmount")
    private String totalAmount;
    /**总实际金额*/
    @XmlElement(name = "TotalCostAmount")
    private String totalCostAmount;
    /**采购日期	*/
    @XmlElement(name = "PurchaseDate")
    private String purchaseDate;
    /**预计发货日期*/
    @XmlElement(name = "ExpectShipmentDate")
    private String expectShipmentDate;
    /**采购依据	*/
    @XmlElement(name = "PurchasedBy")
    private String purchasedBy;
    /**采购联系人*/
    @XmlElement(name = "PurchaseContact")
    private String purchaseContact;
    /**配送中心名称*/
    @XmlElement(name = "DistributionCenter")
    private String distributionCenter;
    /**配送中心编码*/
    @XmlElement(name = "DistributionCenterID")
    private String distributionCenterID;
    /**仓库名称	*/
    @XmlElement(name = "Warehouse")
    private String warehouse;
    /**仓库编码	*/
    @XmlElement(name = "WarehouseID")
    private String warehouseID;
    /**收货地址及单位	*/
    @XmlElement(name = "ReceivingAddress")
    private String receivingAddress;
    /**到站	*/
    @XmlElement(name = "Arrived")
    private String arrived;
    /**备注	*/
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
    public String getPurchaseDate() {
        return purchaseDate;
    }
    @XmlTransient
    public String getExpectShipmentDate() {
        return expectShipmentDate;
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
    public String getWarehouse() {
        return warehouse;
    }
    @XmlTransient
    public String getWarehouseID() {
        return warehouseID;
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

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setExpectShipmentDate(String expectShipmentDate) {
        this.expectShipmentDate = expectShipmentDate;
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

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
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
