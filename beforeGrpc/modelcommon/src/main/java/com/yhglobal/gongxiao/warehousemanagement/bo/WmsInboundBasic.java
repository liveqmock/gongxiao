package com.yhglobal.gongxiao.warehousemanagement.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * wms入库通知单基本信息
 */
public class WmsInboundBasic implements Serializable{
    private Long id;                         //主键id
    private String projectId;                     //项目id
    private String supplier;                    //供应商
    private int purchaseType;                   //0：普通采购 1：货补  2：赠品
    private String inboundType;                   //入库单类型
    private String gongxiaoInboundOrderNo;     //入库单号
    private String wmsInboundOrderNo;          //WMS那边记录的入库单号
    private String easInboundOrderNo;           //EAS回执单号
    private String purchaseOrderNo;             //采购单号
    private String batchNo;                      //批次号
    private String warehouseId;                 //仓库ID
    private String warehouseName;               //仓库名称
    private String productCode;                 //商品编码
    private int inStockQuantity;                //已入库的商品数量
    private Date createTime;                    //创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getInboundType() {
        return inboundType;
    }

    public void setInboundType(String inboundType) {
        this.inboundType = inboundType;
    }

    public String getGongxiaoInboundOrderNo() {
        return gongxiaoInboundOrderNo;
    }

    public void setGongxiaoInboundOrderNo(String gongxiaoInboundOrderNo) {
        this.gongxiaoInboundOrderNo = gongxiaoInboundOrderNo;
    }

    public String getWmsInboundOrderNo() {
        return wmsInboundOrderNo;
    }

    public void setWmsInboundOrderNo(String wmsInboundOrderNo) {
        this.wmsInboundOrderNo = wmsInboundOrderNo;
    }

    public String getEasInboundOrderNo() {
        return easInboundOrderNo;
    }

    public void setEasInboundOrderNo(String easInboundOrderNo) {
        this.easInboundOrderNo = easInboundOrderNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
