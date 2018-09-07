package com.yhglobal.gongxiao.phjd.sales.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售出库单
 *
 * @author weizecheng
 * @date 2018/8/29 9:52
 */
public class SalesOutboundOrder implements Serializable {

    /**
     * 主键id
     */
    private Long oid;

    /**
     * 出库单号
     */
    private String outboundOrderNo;

    /**
     * 预计到货时间
     */
    private Date expectedArrivalTime;

    /**
     * 销售单号
     */
    private String salesOrderNo;

    /**
     * 总数量
     */
    private Integer totalQuantity;

    /**
     * 订单状态 1 出库中 2 已出库 3 已签收
     */
    private Integer orderStatus;

    /**
     * 出库完成时间
     */
    private Date outboundFinishedTime;

    /**
     * 同步eas状态
     */
    private Integer syncToEas;

    /**
     * 同步tms状态
     */
    private Integer syncToTms;

    /**
     * tms方运单号
     */
    private String tmsOrdNo;

    /**
     * 签收时间维护人
     */
    private String tmsPostedBy;

    /**
     * 签收人电话
     */
    private String tmsSignedPhone;

    /**
     * 实际签收人
     */
    private String tmsSignedBy;

    /**
     * 签收时间
     */
    private String transporter;

    /**
     * 签收备注
     */
    private String signedRemark;

    /**
     * 数据版本
     */
    private Long dataVersion;

    /**
     * 创建人id
     */
    private String createdById;

    /**
     * 创建人名称
     */
    private String createdByName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *
     */
    private Date lastUpdateTime;

    /**
     * 仓库id
     */
    private String warehouseId;

    /**
     *
     */
    private Date tmsSignedTime;

    /**
     * 货品JSON
     */
    private String items;

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    /**
     * 出库单号
     * @return outboundOrderNo 出库单号
     */
    public String getOutboundOrderNo() {
        return outboundOrderNo;
    }

    /**
     * 出库单号
     * @param outboundOrderNo 出库单号
     */
    public void setOutboundOrderNo(String outboundOrderNo) {
        this.outboundOrderNo = outboundOrderNo == null ? null : outboundOrderNo.trim();
    }

    /**
     * 预计到货时间
     * @return expectedArrivalTime 预计到货时间
     */
    public Date getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    /**
     * 预计到货时间
     * @param expectedArrivalTime 预计到货时间
     */
    public void setExpectedArrivalTime(Date expectedArrivalTime) {
        this.expectedArrivalTime = expectedArrivalTime;
    }

    /**
     * 销售单号
     * @return salesOrderNo 销售单号
     */
    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    /**
     * 销售单号
     * @param salesOrderNo 销售单号
     */
    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo == null ? null : salesOrderNo.trim();
    }

    /**
     * 总数量
     * @return totalQuantity 总数量
     */
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * 总数量
     * @param totalQuantity 总数量
     */
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }



    /**
     * 出库完成时间
     * @return outboundFinishedTime 出库完成时间
     */
    public Date getOutboundFinishedTime() {
        return outboundFinishedTime;
    }

    /**
     * 出库完成时间
     * @param outboundFinishedTime 出库完成时间
     */
    public void setOutboundFinishedTime(Date outboundFinishedTime) {
        this.outboundFinishedTime = outboundFinishedTime;
    }
    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getSyncToEas() {
        return syncToEas;
    }

    public void setSyncToEas(Integer syncToEas) {
        this.syncToEas = syncToEas;
    }

    public Integer getSyncToTms() {
        return syncToTms;
    }

    public void setSyncToTms(Integer syncToTms) {
        this.syncToTms = syncToTms;
    }

    /**
     * tms方运单号
     * @return tmsOrdNo tms方运单号
     */
    public String getTmsOrdNo() {
        return tmsOrdNo;
    }

    /**
     * tms方运单号
     * @param tmsOrdNo tms方运单号
     */
    public void setTmsOrdNo(String tmsOrdNo) {
        this.tmsOrdNo = tmsOrdNo == null ? null : tmsOrdNo.trim();
    }

    /**
     * 签收时间维护人
     * @return tmsPostedBy 签收时间维护人
     */
    public String getTmsPostedBy() {
        return tmsPostedBy;
    }

    /**
     * 签收时间维护人
     * @param tmsPostedBy 签收时间维护人
     */
    public void setTmsPostedBy(String tmsPostedBy) {
        this.tmsPostedBy = tmsPostedBy == null ? null : tmsPostedBy.trim();
    }

    /**
     * 签收人电话
     * @return tmsSignedPhone 签收人电话
     */
    public String getTmsSignedPhone() {
        return tmsSignedPhone;
    }

    /**
     * 签收人电话
     * @param tmsSignedPhone 签收人电话
     */
    public void setTmsSignedPhone(String tmsSignedPhone) {
        this.tmsSignedPhone = tmsSignedPhone == null ? null : tmsSignedPhone.trim();
    }

    /**
     * 实际签收人
     * @return tmsSignedBy 实际签收人
     */
    public String getTmsSignedBy() {
        return tmsSignedBy;
    }

    /**
     * 实际签收人
     * @param tmsSignedBy 实际签收人
     */
    public void setTmsSignedBy(String tmsSignedBy) {
        this.tmsSignedBy = tmsSignedBy == null ? null : tmsSignedBy.trim();
    }

    /**
     * 签收时间
     * @return transporter 签收时间
     */
    public String getTransporter() {
        return transporter;
    }

    /**
     * 签收时间
     * @param transporter 签收时间
     */
    public void setTransporter(String transporter) {
        this.transporter = transporter == null ? null : transporter.trim();
    }

    /**
     * 签收备注
     * @return signedRemark 签收备注
     */
    public String getSignedRemark() {
        return signedRemark;
    }

    /**
     * 签收备注
     * @param signedRemark 签收备注
     */
    public void setSignedRemark(String signedRemark) {
        this.signedRemark = signedRemark == null ? null : signedRemark.trim();
    }

    /**
     * 数据版本
     * @return dataVersion 数据版本
     */
    public Long getDataVersion() {
        return dataVersion;
    }

    /**
     * 数据版本
     * @param dataVersion 数据版本
     */
    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }



    /**
     * 创建人名称
     * @return createdByName 创建人名称
     */
    public String getCreatedByName() {
        return createdByName;
    }

    /**
     * 创建人名称
     * @param createdByName 创建人名称
     */
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName == null ? null : createdByName.trim();
    }

    /**
     * 创建时间
     * @return createTime 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     *
     * @return lastUpdateTime
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     *
     * @param lastUpdateTime
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * 仓库id
     * @return warehouseId 仓库id
     */
    public String getWarehouseId() {
        return warehouseId;
    }

    /**
     * 仓库id
     * @param warehouseId 仓库id
     */
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId == null ? null : warehouseId.trim();
    }

    /**
     *
     * @return tmsSignedTime
     */
    public Date getTmsSignedTime() {
        return tmsSignedTime;
    }

    /**
     *
     * @param tmsSignedTime
     */
    public void setTmsSignedTime(Date tmsSignedTime) {
        this.tmsSignedTime = tmsSignedTime;
    }

    /**
     * 货品JSON
     * @return items 货品JSON
     */
    public String getItems() {
        return items;
    }

    /**
     * 货品JSON
     * @param items 货品JSON
     */
    public void setItems(String items) {
        this.items = items == null ? null : items.trim();
    }
}
