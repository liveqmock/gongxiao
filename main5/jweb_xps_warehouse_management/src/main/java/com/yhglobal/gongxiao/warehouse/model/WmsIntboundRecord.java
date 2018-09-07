package com.yhglobal.gongxiao.warehouse.model;

import java.io.Serializable;
import java.util.Date;

public class WmsIntboundRecord implements Serializable {
    private Long id;                         //主键id
    private String projectId;                     //项目id
    private String supplier;                    //供应商
    private int purchaseType;                   //0：普通采购 1：货补  2：赠品
    private int inboundType;                   //入库单类型 0:不详 1:采购入库 6:销售退货
    private String gongxiaoInboundOrderNo;     //入库单号
    private String wmsInboundOrderNo;          //WMS那边记录的入库单号
    private String easInboundOrderNo;           //EAS回执单号
    private String purchaseOrderNo;             //采购单号
    private String batchNo;                      //批次号
    private String warehouseId;                 //仓库ID
    private String warehouseName;               //仓库名称
    private String productCode;                 //商品编码
    private int inStockQuantity;                //已入库的商品数量
    private int easFlag;                        //是否同步到eas
    private int dataVersion;                    //版本号
    private Date createTime;                    //创建时间

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

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

    public int getInboundType() {
        return inboundType;
    }

    public void setInboundType(int inboundType) {
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

    public int getEasFlag() {
        return easFlag;
    }

    public void setEasFlag(int easFlag) {
        this.easFlag = easFlag;
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
