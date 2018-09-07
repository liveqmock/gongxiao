package com.yhglobal.gongxiao.sales.model;

import com.yhglobal.gongxiao.model.TraceLog;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售出库单
 *
 * @author 葛灿
 */
public class SalesOutboundOrder implements Serializable {

    // 主键id
    private long oid;
    // 出库单号
    private String outboundOrderNo;
    // 销售单号
    private String salesOrderNo;
    // 总数量
    private int totalQuantity;
    // 订单状态 1 出库中 2 已出库 3 已签收
    private int orderStatus;
    //出库完成时间
    private Date outboundFinishedTime;
    // 同步eas状态
    private int syncToEas;
    // 同步tms状态
    private int syncToTms;
    // tms方运单号
    private String tmsOrdNo;
    // 签收时间维护人
    private String tmsPostedBy;
    // 实际签收人
    private String tmsSignedBy;
    // 签收人电话
    private String tmsSignedPhone;
    // 运输商
    private String transporter;
    // 签收时间
    private Date tmsSignedTime;
    // 签收备注
    private String signedRemark;
    // 创建人
    private String createdById;
    // 创建人
    private String createdByName;
    // 预计到货时间
    private Date expectedArrivalTime;
    // 创建时间
    private Date createTime;
    // 最后更新时间
    private Date lastUpdateTime;
    // 数据版本
    private long dataVersion;
    // 货品JSON
    private String items;
    //仓库id
    private String warehouseId;

    private TraceLog traceLog;


    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public TraceLog getTraceLog() {
        return traceLog;
    }

    public void setTraceLog(TraceLog traceLog) {
        this.traceLog = traceLog;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public String getOutboundOrderNo() {
        return outboundOrderNo;
    }

    public void setOutboundOrderNo(String outboundOrderNo) {
        this.outboundOrderNo = outboundOrderNo;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOutboundFinishedTime() {
        return outboundFinishedTime;
    }

    public void setOutboundFinishedTime(Date outboundFinishedTime) {
        this.outboundFinishedTime = outboundFinishedTime;
    }

    public int getSyncToEas() {
        return syncToEas;
    }

    public void setSyncToEas(int syncToEas) {
        this.syncToEas = syncToEas;
    }

    public int getSyncToTms() {
        return syncToTms;
    }

    public void setSyncToTms(int syncToTms) {
        this.syncToTms = syncToTms;
    }

    public String getTmsOrdNo() {
        return tmsOrdNo;
    }

    public void setTmsOrdNo(String tmsOrdNo) {
        this.tmsOrdNo = tmsOrdNo;
    }

    public String getTmsPostedBy() {
        return tmsPostedBy;
    }

    public void setTmsPostedBy(String tmsPostedBy) {
        this.tmsPostedBy = tmsPostedBy;
    }

    public String getTmsSignedBy() {
        return tmsSignedBy;
    }

    public void setTmsSignedBy(String tmsSignedBy) {
        this.tmsSignedBy = tmsSignedBy;
    }

    public String getTmsSignedPhone() {
        return tmsSignedPhone;
    }

    public void setTmsSignedPhone(String tmsSignedPhone) {
        this.tmsSignedPhone = tmsSignedPhone;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public Date getTmsSignedTime() {
        return tmsSignedTime;
    }

    public void setTmsSignedTime(Date tmsSignedTime) {
        this.tmsSignedTime = tmsSignedTime;
    }

    public String getSignedRemark() {
        return signedRemark;
    }

    public void setSignedRemark(String signedRemark) {
        this.signedRemark = signedRemark;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Date getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public void setExpectedArrivalTime(Date expectedArrivalTime) {
        this.expectedArrivalTime = expectedArrivalTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(long dataVersion) {
        this.dataVersion = dataVersion;
    }

    @Override
    public String toString() {
        return outboundOrderNo;
    }
}
