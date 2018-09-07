package com.yhglobal.gongxiao.wmscomfirm.model.wms;

import java.io.Serializable;
import java.util.Date;

/**
 * WMS报过来的出库记录
 */
public class WmsOutboundRecord implements Serializable {
    private Long id;                         //主键id
    private String projectId;                   //项目id
    private int outboundType;                   //出库类型 0:不详 1:销售发货  6:采购退货
    private String customer;                    //客户
    private String gongxiaoOutboundOrderNo;     //gongxiao出库单号
    private String outboundOrderItemNo;         //gongxiao出库单明细单号
    private String purchaseOrderNo;                  //采购单号
    private String wmsOutboundOrderNo;          //WMS那边记录的出库单号
    private String easOutboundOrderNo;           //EAS出库回执单号
    private String tmsOutboundOrderNo;           //TMS单号
    private String salesOrderNo;                //销售单号(销售发货场景)
    private String warehouseId;                 //仓库ID
    private String warehouseName;               //仓库名称
    private String productCode;                 //商品编码
    private int outStockQuantity;            //已出库的商品数量(含残次品)
    private int easFlag;                        //是否同步到eas
    private int dataVersion;                    //版本号
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

    public int getOutboundType() {
        return outboundType;
    }

    public void setOutboundType(int outboundType) {
        this.outboundType = outboundType;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getGongxiaoOutboundOrderNo() {
        return gongxiaoOutboundOrderNo;
    }

    public void setGongxiaoOutboundOrderNo(String gongxiaoOutboundOrderNo) {
        this.gongxiaoOutboundOrderNo = gongxiaoOutboundOrderNo;
    }

    public String getOutboundOrderItemNo() {
        return outboundOrderItemNo;
    }

    public void setOutboundOrderItemNo(String outboundOrderItemNo) {
        this.outboundOrderItemNo = outboundOrderItemNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getWmsOutboundOrderNo() {
        return wmsOutboundOrderNo;
    }

    public void setWmsOutboundOrderNo(String wmsOutboundOrderNo) {
        this.wmsOutboundOrderNo = wmsOutboundOrderNo;
    }

    public String getEasOutboundOrderNo() {
        return easOutboundOrderNo;
    }

    public void setEasOutboundOrderNo(String easOutboundOrderNo) {
        this.easOutboundOrderNo = easOutboundOrderNo;
    }

    public String getTmsOutboundOrderNo() {
        return tmsOutboundOrderNo;
    }

    public void setTmsOutboundOrderNo(String tmsOutboundOrderNo) {
        this.tmsOutboundOrderNo = tmsOutboundOrderNo;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
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

    public int getOutStockQuantity() {
        return outStockQuantity;
    }

    public void setOutStockQuantity(int outStockQuantity) {
        this.outStockQuantity = outStockQuantity;
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
