package com.yhglobal.gongxiao.warehouse.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 采购入库模型
 *
 * @author: 陈浩
 * @create: 2018-02-05 11:48
 **/
public class PurchaseInboundModel implements Serializable{
    /**
     * 项目id
     */
    private long projectId;
    /**
     * 入库单号
     */
    private String inboundOrderNumber;
    /**
     * 采购单号
     */
    private String purchaseOrderNumber;
    /**
     * 供应商ID
     */
    private String SupplierId;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 货品编码
     */
    private String productCode;
    /**
     *货品名称
     */
    private String productName;
    /**
     * 收货仓ID
     */
    private String receiveWarehouseId;
    /**
     * 收货仓名称
     */
    private String receiveWareHouseName;
    /**
     * 收货仓地址
     */
    private String receiveWarehouseAddress;
    /**
     * 计划入库数量
     */
    private int planInboundAmount;
    /**
     * 本次入库数量
     */
    private int inboundAmount;
    /**
     * 预计所有商品入库完成日期
     */
    private Date expireInboundDate;
    /**
     * 本次入库日期
     */
    private Date inboundDate;
    /**
     * ERP单号
     */
    private String erpOrderNumber;
    /**
     * 外部流水号
     */
    private String outFlowingNumber;

    public PurchaseInboundModel() {
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getInboundOrderNumber() {
        return inboundOrderNumber;
    }

    public void setInboundOrderNumber(String inboundOrderNumber) {
        this.inboundOrderNumber = inboundOrderNumber;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getReceiveWarehouseId() {
        return receiveWarehouseId;
    }

    public void setReceiveWarehouseId(String receiveWarehouseId) {
        this.receiveWarehouseId = receiveWarehouseId;
    }

    public String getReceiveWareHouseName() {
        return receiveWareHouseName;
    }

    public void setReceiveWareHouseName(String receiveWareHouseName) {
        this.receiveWareHouseName = receiveWareHouseName;
    }

    public String getReceiveWarehouseAddress() {
        return receiveWarehouseAddress;
    }

    public void setReceiveWarehouseAddress(String receiveWarehouseAddress) {
        this.receiveWarehouseAddress = receiveWarehouseAddress;
    }

    public int getPlanInboundAmount() {
        return planInboundAmount;
    }

    public void setPlanInboundAmount(int planInboundAmount) {
        this.planInboundAmount = planInboundAmount;
    }

    public int getInboundAmount() {
        return inboundAmount;
    }

    public void setInboundAmount(int inboundAmount) {
        this.inboundAmount = inboundAmount;
    }

    public Date getExpireInboundDate() {
        return expireInboundDate;
    }

    public void setExpireInboundDate(Date expireInboundDate) {
        this.expireInboundDate = expireInboundDate;
    }

    public Date getInboundDate() {
        return inboundDate;
    }

    public void setInboundDate(Date inboundDate) {
        this.inboundDate = inboundDate;
    }

    public String getErpOrderNumber() {
        return erpOrderNumber;
    }

    public void setErpOrderNumber(String erpOrderNumber) {
        this.erpOrderNumber = erpOrderNumber;
    }

    public String getOutFlowingNumber() {
        return outFlowingNumber;
    }

    public void setOutFlowingNumber(String outFlowingNumber) {
        this.outFlowingNumber = outFlowingNumber;
    }
}
