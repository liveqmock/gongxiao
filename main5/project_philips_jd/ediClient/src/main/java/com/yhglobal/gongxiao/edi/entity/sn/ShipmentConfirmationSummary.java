package com.yhglobal.gongxiao.edi.entity.sn;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 发货确认单据总目
 *
 * @author 葛灿
 */
public class ShipmentConfirmationSummary {

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
    /**实收合计品种*/
    @XmlElement(name = "TotalReceivingCategory")
    private Integer totalReceivingCategory;
    /**实收合计数量*/
    @XmlElement(name = "TotalReceivingQuantity")
    private Integer totalReceivingQuantity;
    /**实收总金额*/
    @XmlElement(name = "TotalReceivingAmount")
    private String totalReceivingAmount;
    /**实收总实际金额*/
    @XmlElement(name = "TotalReceivingCostAmount")
    private String totalReceivingCostAmount;
    /**残损合计品种*/
    @XmlElement(name = "TotalDamagedCategory")
    private Integer totalDamagedCategory;
    /**残损合计数量*/
    @XmlElement(name = "TotalDamagedQuantity")
    private Integer totalDamagedQuantity;
    /**残损总金额*/
    @XmlElement(name = "TotalDamagedAmount")
    private String totalDamagedAmount;
    /**残损总实际金额*/
    @XmlElement(name = "TotalDamagedCostAmount")
    private String totalDamagedCostAmount;
    /**拒收合计品种*/
    @XmlElement(name = "TotalRefuseCategory")
    private Integer totalRefuseCategory;
    /**拒收合计数量*/
    @XmlElement(name = "TotalRefuseQuantity")
    private Integer totalRefuseQuantity;
    /**拒收总金额*/
    @XmlElement(name = "TotalRefuseAmount")
    private String totalRefuseAmount;
    /**拒收总实际金额*/
    @XmlElement(name = "TotalRefuseCostAmount")
    private String totalRefuseCostAmount;
    /**发货日期	*/
    @XmlElement(name = "ShipmentDate")
    private String shipmentDate;
    /**收货日期	*/
    @XmlElement(name = "ReceivingDate")
    private String receivingDate;
    /**采购依据	*/
    @XmlElement(name = "PurchasedBy")
    private String purchasedBy;
    /**采购联系人*/
    @XmlElement(name = "PurchaseContact")
    private String purchaseContact;
    @XmlElement(name = "DistributionCenter")
    private String distributionCenter;
    @XmlElement(name = "DistributionCenterID")
    private String distributionCenterID;
    @XmlElement(name = "Warehouse")
    private String warehouse;
    @XmlElement(name = "WarehouseID")
    private String warehouseID;


    /**收货地址及单位	*/
    @XmlElement(name = "ReceivingAddress")
    private String receivingAddress;
    /**到站	*/
    @XmlElement(name = "Arrived")
    private String arrived;
    /**运号	*/
    @XmlElement(name = "ShipmentNumber")
    private String shipmentNumber;
    /**件数	*/
    @XmlElement(name = "ShipmentPackageNumber")
    private Integer shipmentPackageNumber;
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
    public Integer getTotalReceivingCategory() {
        return totalReceivingCategory;
    }
    @XmlTransient
    public Integer getTotalReceivingQuantity() {
        return totalReceivingQuantity;
    }
    @XmlTransient
    public String getTotalReceivingAmount() {
        return totalReceivingAmount;
    }
    @XmlTransient
    public String getTotalReceivingCostAmount() {
        return totalReceivingCostAmount;
    }
    @XmlTransient
    public Integer getTotalDamagedCategory() {
        return totalDamagedCategory;
    }
    @XmlTransient
    public Integer getTotalDamagedQuantity() {
        return totalDamagedQuantity;
    }
    @XmlTransient
    public String getTotalDamagedAmount() {
        return totalDamagedAmount;
    }
    @XmlTransient
    public String getTotalDamagedCostAmount() {
        return totalDamagedCostAmount;
    }
    @XmlTransient
    public Integer getTotalRefuseCategory() {
        return totalRefuseCategory;
    }
    @XmlTransient
    public Integer getTotalRefuseQuantity() {
        return totalRefuseQuantity;
    }
    @XmlTransient
    public String getTotalRefuseAmount() {
        return totalRefuseAmount;
    }
    @XmlTransient
    public String getTotalRefuseCostAmount() {
        return totalRefuseCostAmount;
    }
    @XmlTransient
    public String getShipmentDate() {
        return shipmentDate;
    }
    @XmlTransient
    public String getReceivingDate() {
        return receivingDate;
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
    public String getReceivingAddress() {
        return receivingAddress;
    }
    @XmlTransient
    public String getArrived() {
        return arrived;
    }
    @XmlTransient
    public String getShipmentNumber() {
        return shipmentNumber;
    }
    @XmlTransient
    public Integer getShipmentPackageNumber() {
        return shipmentPackageNumber;
    }
    @XmlTransient
    public String getComments() {
        return comments;
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

    public void setTotalReceivingCategory(Integer totalReceivingCategory) {
        this.totalReceivingCategory = totalReceivingCategory;
    }

    public void setTotalReceivingQuantity(Integer totalReceivingQuantity) {
        this.totalReceivingQuantity = totalReceivingQuantity;
    }

    public void setTotalReceivingAmount(String totalReceivingAmount) {
        this.totalReceivingAmount = totalReceivingAmount;
    }

    public void setTotalReceivingCostAmount(String totalReceivingCostAmount) {
        this.totalReceivingCostAmount = totalReceivingCostAmount;
    }

    public void setTotalDamagedCategory(Integer totalDamagedCategory) {
        this.totalDamagedCategory = totalDamagedCategory;
    }

    public void setTotalDamagedQuantity(Integer totalDamagedQuantity) {
        this.totalDamagedQuantity = totalDamagedQuantity;
    }

    public void setTotalDamagedAmount(String totalDamagedAmount) {
        this.totalDamagedAmount = totalDamagedAmount;
    }

    public void setTotalDamagedCostAmount(String totalDamagedCostAmount) {
        this.totalDamagedCostAmount = totalDamagedCostAmount;
    }

    public void setTotalRefuseCategory(Integer totalRefuseCategory) {
        this.totalRefuseCategory = totalRefuseCategory;
    }

    public void setTotalRefuseQuantity(Integer totalRefuseQuantity) {
        this.totalRefuseQuantity = totalRefuseQuantity;
    }

    public void setTotalRefuseAmount(String totalRefuseAmount) {
        this.totalRefuseAmount = totalRefuseAmount;
    }

    public void setTotalRefuseCostAmount(String totalRefuseCostAmount) {
        this.totalRefuseCostAmount = totalRefuseCostAmount;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public void setReceivingDate(String receivingDate) {
        this.receivingDate = receivingDate;
    }

    public void setPurchasedBy(String purchasedBy) {
        this.purchasedBy = purchasedBy;
    }

    public void setPurchaseContact(String purchaseContact) {
        this.purchaseContact = purchaseContact;
    }

    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public void setArrived(String arrived) {
        this.arrived = arrived;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public void setShipmentPackageNumber(Integer shipmentPackageNumber) {
        this.shipmentPackageNumber = shipmentPackageNumber;
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

    public void setComments(String comments) {
        this.comments = comments;
    }
}
