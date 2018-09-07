package com.yhglobal.gongxiao.warehouse.model.bo;

import java.io.Serializable;
import java.util.Date;

public class InboundOrderItemShowModel implements Serializable{
    private String projectId;               //项目id
    private String purchaseOrderNo;         //采购单号
    private Boolean itemStatus;                 //入库单状态
    private String productCode;             //商品编码
    private String productName;             //商品名称
    private String productUnit;             //商品单位
    private int planInStockQuantity;        //预约入库数量
    private int actualInStockQuantity;      //已入库的商品数量
    private int nonImperfectQuantity;       //已入库的良品数量
    private int imperfectQuantity;          //已入库的残次品数量
    private int differentQuantity;           //差异数量
    private Date createTime;                //创建时间

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public Boolean getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Boolean itemStatus) {
        this.itemStatus = itemStatus;
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

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public int getPlanInStockQuantity() {
        return planInStockQuantity;
    }

    public void setPlanInStockQuantity(int planInStockQuantity) {
        this.planInStockQuantity = planInStockQuantity;
    }

    public int getActualInStockQuantity() {
        return actualInStockQuantity;
    }

    public void setActualInStockQuantity(int actualInStockQuantity) {
        this.actualInStockQuantity = actualInStockQuantity;
    }

    public int getImperfectQuantity() {
        return imperfectQuantity;
    }

    public void setImperfectQuantity(int imperfectQuantity) {
        this.imperfectQuantity = imperfectQuantity;
    }

    public int getDifferentQuantity() {
        return differentQuantity;
    }

    public void setDifferentQuantity(int differentQuantity) {
        this.differentQuantity = differentQuantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getNonImperfectQuantity() {
        return nonImperfectQuantity;
    }

    public void setNonImperfectQuantity(int nonImperfectQuantity) {
        this.nonImperfectQuantity = nonImperfectQuantity;
    }
}
