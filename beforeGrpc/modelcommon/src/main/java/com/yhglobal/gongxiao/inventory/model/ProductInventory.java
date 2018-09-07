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
    private String productId;                //商品Id
    private String productCode;              //商品编码
    private String productModel;             //商品型号
    private String productName;              //商品名称
    private double purchasePrice;            //采购价格   物料、数量、采购价、成本价、应收返利、实收返利、销售总价
    private String material;                 //物料
    private double costPrice;                //成本价
    private String warehouseId;              //仓库id
    private String warehouseCode;            //仓库编码
    private String warehouseName;            //仓库名称
    private int onHandQuantity;              //当前在库总数量(良品/残次品/机损/箱损/冻结/在途)
    private int onSalesOrderQuantity;        //已销售待出库数量
    private String onSalesOrderInfo;         //已销售待出库相关信息(JSON)
    private double shouldRebate;                   //应收返利
    private double actualRebate;             //实收返利
    private double salesTotalPrice;          //销售总价
    private int dataVersion;                 //数据版本
    private Date createTime;                 //创建时间

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
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

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
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
