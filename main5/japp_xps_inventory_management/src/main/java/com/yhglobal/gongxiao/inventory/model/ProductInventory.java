package com.yhglobal.gongxiao.inventory.model;

import java.io.Serializable;
import java.util.Date;

/**
 *商品库存表模型
 */
public class ProductInventory implements Serializable {

    private Long id;                         //主键id
    private int purchaseType;                //采购方式  （0:普通采购 1：货补 2：赠品）
    private int inventoryStatus;             //库存类型 良品/残次品/机损/箱损/冻结/在途
    private long projectId;                  //项目id
    private String batchNo;                  //批次
    private String inboundOrderNo;           //入库单号
    private String purchaseOrderNo;          //采购单号
    private String productId;                //商品Id
    private String productCode;              //商品编码
    private String productModel;             //商品型号
    private String productName;              //商品名称
    private long purchaseGuidPrice;          //采购指导价格
    private long purchasePrice;            //采购价格
    private String material;                 //物料
    private long costPrice;                //成本价
    private String warehouseId;              //仓库id
    private String warehouseCode;            //仓库编码
    private String warehouseName;            //仓库名称
    private int inboundQuantity;             //入库数量
    private int onHandQuantity;              //当前在库总数量(良品/残次品/机损/箱损/冻结/在途)
    private int onSalesOrderQuantity;        //已销售待出库数量
    private String onSalesOrderInfo;         //已销售待出库相关信息(JSON)
    private double shouldRebate;                   //应收返利
    private double actualRebate;             //实收返利
    private double salesTotalPrice;          //销售总价
    private int dataVersion;                 //数据版本
    private Date createTime;                 //创建时间
    private Date lastUpdateTime;             //跟新时间

    public int getInboundQuantity() {
        return inboundQuantity;
    }

    public void setInboundQuantity(int inboundQuantity) {
        this.inboundQuantity = inboundQuantity;
    }

    public String getInboundOrderNo() {
        return inboundOrderNo;
    }

    public void setInboundOrderNo(String inboundOrderNo) {
        this.inboundOrderNo = inboundOrderNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(long costPrice) {
        this.costPrice = costPrice;
    }

    public double getShouldRebate() {
        return shouldRebate;
    }

    public void setShouldRebate(double shouldRebate) {
        this.shouldRebate = shouldRebate;
    }

    public double getActualRebate() {
        return actualRebate;
    }

    public void setActualRebate(double actualRebate) {
        this.actualRebate = actualRebate;
    }

    public double getSalesTotalPrice() {
        return salesTotalPrice;
    }

    public void setSalesTotalPrice(double salesTotalPrice) {
        this.salesTotalPrice = salesTotalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    public int getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(int inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public long getPurchaseGuidPrice() {
        return purchaseGuidPrice;
    }

    public void setPurchaseGuidPrice(long purchaseGuidPrice) {
        this.purchaseGuidPrice = purchaseGuidPrice;
    }

    public long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(long purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getOnHandQuantity() {
        return onHandQuantity;
    }

    public void setOnHandQuantity(int onHandQuantity) {
        this.onHandQuantity = onHandQuantity;
    }

    public int getOnSalesOrderQuantity() {
        return onSalesOrderQuantity;
    }

    public void setOnSalesOrderQuantity(int onSalesOrderQuantity) {
        this.onSalesOrderQuantity = onSalesOrderQuantity;
    }

    public String getOnSalesOrderInfo() {
        return onSalesOrderInfo;
    }

    public void setOnSalesOrderInfo(String onSalesOrderInfo) {
        this.onSalesOrderInfo = onSalesOrderInfo;
    }

    public int getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(int dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
