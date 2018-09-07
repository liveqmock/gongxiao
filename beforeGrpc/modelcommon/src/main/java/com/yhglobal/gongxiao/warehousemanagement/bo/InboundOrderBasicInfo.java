package com.yhglobal.gongxiao.warehousemanagement.bo;

import com.yhglobal.gongxiao.common.TraceLog;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InboundOrderBasicInfo implements Serializable{
    private Long projectId;                 //项目id
    private String batchNo;                 //批次号
    private String orderStatus;                //入库单状态
    private String inboundType;                //入库单类型 0:不详 1:采购入库 6:销售退货
    private String gongxiaoInboundOrderNo;  //入库单号
    private String purchaseOrderNo;         //采购单号(采购入库场景)
    private String warehouseName;           //仓库名称
    private String warehouseDetailAddress; //仓库地址详情
    private String brandName;               //品牌商
    private String contactsPeople;          //发货联系人
    private String phoneNum;                //联系电话
    private String deliverAddress;          //发货地址
    private int shippingMode;               //物流方式 0:不详 1:自提 2:第三方物流
    private String note;                    //备注
    private List<TraceLog> tracelog;                //操作日记
    private Date createTime;                //创建时间
    private Date estimateOutTime;           //预计出库时间
    private Date lastUpdateTime;            //上次更新时间

    public String getWarehouseDetailAddress() {
        return warehouseDetailAddress;
    }

    public void setWarehouseDetailAddress(String warehouseDetailAddress) {
        this.warehouseDetailAddress = warehouseDetailAddress;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getContactsPeople() {
        return contactsPeople;
    }

    public void setContactsPeople(String contactsPeople) {
        this.contactsPeople = contactsPeople;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public int getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(int shippingMode) {
        this.shippingMode = shippingMode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public List<TraceLog> getTracelog() {
        return tracelog;
    }

    public void setTracelog(List<TraceLog> tracelog) {
        this.tracelog = tracelog;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEstimateOutTime() {
        return estimateOutTime;
    }

    public void setEstimateOutTime(Date estimateOutTime) {
        this.estimateOutTime = estimateOutTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
