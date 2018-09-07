package com.yhglobal.gongxiao.warehousemanagement.model;

import java.io.Serializable;
import java.util.Date;

/**
 * wms出库单详情
 * @author liukai
 */
public class WmsOutboundRecordItem implements Serializable {
    private Long id;                            //主键id
    private String projectId;                   //项目id
    private int purchaseType;                   //采购类型 （采购类型 0:普通采购 1:货补 2:赠品）
    private int inventoryType;                  //库存品质
    private String gongxiaoOutboundOrderNo;     //gongxiao出库单号
    private String wmsOutboundOrderNo;          //wms出库单号
    private String outboundOrderItemNo;         //gongxiao出库单明细单号
    private String salesOrderNo;                //销售单号(销售发货场景)
    private String batchNo;                     //批次号
    private String warehouseId;                 //仓库ID
    private String warehouseName;               //仓库名称
    private String productId;                   //商品id
    private String productCode;                 //商品编码
    private String productName;                 //商品名称
    private String productUnit;                 //商品单位
    private int outStockQuantity;               //已出库的商品数量
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

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getGongxiaoOutboundOrderNo() {
        return gongxiaoOutboundOrderNo;
    }

    public void setGongxiaoOutboundOrderNo(String gongxiaoOutboundOrderNo) {
        this.gongxiaoOutboundOrderNo = gongxiaoOutboundOrderNo;
    }

    public String getWmsOutboundOrderNo() {
        return wmsOutboundOrderNo;
    }

    public void setWmsOutboundOrderNo(String wmsOutboundOrderNo) {
        this.wmsOutboundOrderNo = wmsOutboundOrderNo;
    }

    public String getOutboundOrderItemNo() {
        return outboundOrderItemNo;
    }

    public void setOutboundOrderItemNo(String outboundOrderItemNo) {
        this.outboundOrderItemNo = outboundOrderItemNo;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
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

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public int getOutStockQuantity() {
        return outStockQuantity;
    }

    public void setOutStockQuantity(int outStockQuantity) {
        this.outStockQuantity = outStockQuantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
