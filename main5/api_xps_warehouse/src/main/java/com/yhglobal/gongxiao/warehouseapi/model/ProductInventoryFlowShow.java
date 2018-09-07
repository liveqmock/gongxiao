package com.yhglobal.gongxiao.warehouseapi.model;

import java.io.Serializable;
import java.util.Date;

public class ProductInventoryFlowShow implements Serializable {
    private Long inventoryFlowId;           //库存流水id
    private String orderNo;                 //入库对应的入库单号，出库对应的出库单号
    private String batchNo;                 //批次号
    private String inventoryFlowType;          //库存类型 良品 残次品
    private String orderType;                  //单据类型 采购 销售 调拨 其他
    private long projectId;                 //项目id
    private String relatedOrderNo;                 //入库对应的采购单号,出库对应的销售单号
    private String productId;               //商品Id
    private String productCode;             //商品编码
    private String productModel;            //商品型号
    private String productName;             //商品名称
    private String warehouseId;             //仓库id
    private String warehouseName;           //仓库名称
    private int amountBeforeTransaction;    //流水发生前的数量
    private int transactionAmount;          //库存变动的数量(入库为正 出库为负)
    private int amountAfterTransaction;     //流水发生后的数量
    private int perfectQuantity;              //良品
    private int imperfectQuantity;              //残次品
    private Date createTime;                //创建时间

    public String getRelatedOrderNo() {
        return relatedOrderNo;
    }

    public void setRelatedOrderNo(String relatedOrderNo) {
        this.relatedOrderNo = relatedOrderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getPerfectQuantity() {
        return perfectQuantity;
    }

    public void setPerfectQuantity(int perfectQuantity) {
        this.perfectQuantity = perfectQuantity;
    }

    public Long getInventoryFlowId() {
        return inventoryFlowId;
    }

    public void setInventoryFlowId(Long inventoryFlowId) {
        this.inventoryFlowId = inventoryFlowId;
    }

    public String getInventoryFlowType() {
        return inventoryFlowType;
    }

    public void setInventoryFlowType(String inventoryFlowType) {
        this.inventoryFlowType = inventoryFlowType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getAmountBeforeTransaction() {
        return amountBeforeTransaction;
    }

    public void setAmountBeforeTransaction(int amountBeforeTransaction) {
        this.amountBeforeTransaction = amountBeforeTransaction;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public int getAmountAfterTransaction() {
        return amountAfterTransaction;
    }

    public void setAmountAfterTransaction(int amountAfterTransaction) {
        this.amountAfterTransaction = amountAfterTransaction;
    }

    public int getImperfectQuantity() {
        return imperfectQuantity;
    }

    public void setImperfectQuantity(int imperfectQuantity) {
        this.imperfectQuantity = imperfectQuantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
