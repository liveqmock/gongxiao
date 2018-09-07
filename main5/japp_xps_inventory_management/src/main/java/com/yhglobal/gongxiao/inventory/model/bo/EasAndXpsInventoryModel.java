package com.yhglobal.gongxiao.inventory.model.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * EAS和xps库出核对
 */
public class EasAndXpsInventoryModel implements Serializable{
    private long id;
    private Date dateTime;      //日期
    private String projectId;   //项目id
    private String projectName;  //项目名称
    private String warehouseId;  //仓库id
    private String warehouseName;  //仓库名称
    private String productId;       //商品id
    private String productCode;     //商品code
    private String productName;     //商品名称
    private String batchNo;         //批次号
    private int inventoryType;      //库存类型
    private int purchaseType;       //采购类型
    private int fenxiaoQuantity;    //分销库存数量
    private int easQuantity;        //eas库存数量
    private int differentQuantity;  //差异数理
    private Date createTime;        //创建时间

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    public int getFenxiaoQuantity() {
        return fenxiaoQuantity;
    }

    public void setFenxiaoQuantity(int fenxiaoQuantity) {
        this.fenxiaoQuantity = fenxiaoQuantity;
    }

    public int getEasQuantity() {
        return easQuantity;
    }

    public void setEasQuantity(int easQuantity) {
        this.easQuantity = easQuantity;
    }

    public int getDifferentQuantity() {
        return differentQuantity;
    }

    public void setDifferentQuantity(int differentQuantity) {
        this.differentQuantity = differentQuantity;
    }
}

