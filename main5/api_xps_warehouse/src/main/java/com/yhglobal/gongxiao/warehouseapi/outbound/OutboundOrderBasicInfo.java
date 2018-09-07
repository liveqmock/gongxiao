package com.yhglobal.gongxiao.warehouseapi.outbound;


import com.yhglobal.gongxiao.model.TraceLog;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OutboundOrderBasicInfo implements Serializable{
    private String projectId;                         //项目id
    private String salesOrderNo;                    //销售单号(销售出货场景)
    private String gongxiaoOutboundOrderNo;         //GX出库单号
    private String outboundOrderItemNo;         //出库明细单号
    private String bachNo;                  //批次号
    private String outStorageType;             //出库类型
    private String orderStatus;                //单据状态
    private String deliverWarehouse;        //发货仓库
    private String deliverAddress;          //发货地址
    private String supplier;                //供应商
    private String customer;                //客户
    private Date createTime;                //创建时间
    private Date estimateOutTime;           //预计出库时间
    private Date lastUpdateTime;            //更新时间
    private String contectPeople;            //客户户联系人
    private String phoneNum;                //联系电话
    private String receiveAdrress;          //收货地址
    private List<TraceLog> traceLog;    //操作日志
    private String transporter;             //运输商
    private String tmsOrdNo;            //运单号
    private String transportStatus;         //运输状态
    private String tmsSignedBy;                        //tms签收人
    private String tmsSignedPhone;                     //tms签收人电话
    private Date tmsSingedTime;                      //tms签收时间
    private String remark;                  //备注

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
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

    public String getBachNo() {
        return bachNo;
    }

    public void setBachNo(String bachNo) {
        this.bachNo = bachNo;
    }

    public String getOutStorageType() {
        return outStorageType;
    }

    public void setOutStorageType(String outStorageType) {
        this.outStorageType = outStorageType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliverWarehouse() {
        return deliverWarehouse;
    }

    public void setDeliverWarehouse(String deliverWarehouse) {
        this.deliverWarehouse = deliverWarehouse;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public String getContectPeople() {
        return contectPeople;
    }

    public void setContectPeople(String contectPeople) {
        this.contectPeople = contectPeople;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getReceiveAdrress() {
        return receiveAdrress;
    }

    public void setReceiveAdrress(String receiveAdrress) {
        this.receiveAdrress = receiveAdrress;
    }

    public List<TraceLog> getTraceLog() {
        return traceLog;
    }

    public void setTraceLog(List<TraceLog> traceLog) {
        this.traceLog = traceLog;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public String getTmsOrdNo() {
        return tmsOrdNo;
    }

    public void setTmsOrdNo(String tmsOrdNo) {
        this.tmsOrdNo = tmsOrdNo;
    }

    public String getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(String transportStatus) {
        this.transportStatus = transportStatus;
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

    public Date getTmsSingedTime() {
        return tmsSingedTime;
    }

    public void setTmsSingedTime(Date tmsSingedTime) {
        this.tmsSingedTime = tmsSingedTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
