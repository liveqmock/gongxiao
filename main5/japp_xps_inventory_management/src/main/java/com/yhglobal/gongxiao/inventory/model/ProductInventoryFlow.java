package com.yhglobal.gongxiao.inventory.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品库存流水表模型
 */
public class ProductInventoryFlow implements Serializable {

    private Long inventoryFlowId;           //库存流水id
    private String orderNo;                   //入库对应的入库单号，出库对应的出库单号
    private String batchNo;                 //批次号
    private int inventoryFlowType;          //库存类型 良品 残次品
    private int orderType;                  //单据类型 采购 销售 调拨 其他
    private long projectId;                 //项目id
    private String productId;               //商品Id
    private String productCode;             //商品编码
    private String productModel;            //商品型号
    private String productName;             //商品名称
    private String warehouseId;             //仓库id
    private String warehouseCode;           //仓库编码
    private String warehouseName;           //仓库名称
    private int amountBeforeTransaction;    //流水发生前的数量
    private int transactionAmount;          //库存变动的数量(入库为正 出库为负)
    private int amountAfterTransaction;     //流水发生后的数量
    private Date transactionTime;           //实际发生变动的时间
    private String relatedOrderId;          //关联的订单id(入库对应采购单号， 出库对应销售单号)
    private String extraInfo;               //交易的其它信息(JSON格式)
    private String statementCheckingFlag;   //该流水是否已对账
    private Date statementCheckingTime;     //该流水对账时间
    private Date createTime;                //创建时间

    public Long getInventoryFlowId() {
        return inventoryFlowId;
    }

    public void setInventoryFlowId(Long inventoryFlowId) {
        this.inventoryFlowId = inventoryFlowId;
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

    public int getInventoryFlowType() {
        return inventoryFlowType;
    }

    public void setInventoryFlowType(int inventoryFlowType) {
        this.inventoryFlowType = inventoryFlowType;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
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

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
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

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getRelatedOrderId() {
        return relatedOrderId;
    }

    public void setRelatedOrderId(String relatedOrderId) {
        this.relatedOrderId = relatedOrderId;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getStatementCheckingFlag() {
        return statementCheckingFlag;
    }

    public void setStatementCheckingFlag(String statementCheckingFlag) {
        this.statementCheckingFlag = statementCheckingFlag;
    }

    public Date getStatementCheckingTime() {
        return statementCheckingTime;
    }

    public void setStatementCheckingTime(Date statementCheckingTime) {
        this.statementCheckingTime = statementCheckingTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

}
