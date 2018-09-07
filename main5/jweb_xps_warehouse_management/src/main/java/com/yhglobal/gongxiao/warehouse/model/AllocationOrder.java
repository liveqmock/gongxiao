package com.yhglobal.gongxiao.warehouse.model;

import java.io.Serializable;

/**
 * 调拨单
 * @author liukai
 */
public class AllocationOrder implements Serializable {
    private int id;                             //主键id
    private String allocateNo;                    //调拨单号
    private String projectIdOut;                //调出项目
    private String projectIdEnter;              //调入项目
    private String gongxiaoOutboundOrderNo;     //出库单号
    private String gongxiaoInboundOrderNo;      //入库单号
    private String warehouseOutId;              //调出仓库id
    private String warehouseOut;                //调出仓库
    private String warehouseEnterId;            //调入仓库id
    private String warehouseEnter;              //调入仓库
    private String companyNameOut;              //调出公司主体
    private String companyNameEnter;         //调入公司主体
    private String deliverAddress;              //发货地址
    private String receiveAddress;              //收货地址
    private int alloteWay;                      //调拨方式
    private int status;                         //状态
    private String batchNo;                  //调拨批次
    private int totalQuantity;                //调拨数量
    private int realInQuantity;              //实际调入数量
    private int realOutQuantity;             //实际调出数量
    private String requireTime;                   //要求到货日期
    private String deadline;                      //截止日期
    private int syncEas;                         //同步eas的状态
    private String easId;                        //EAS id
    private String easOrderNumber;               //EAS 单号
    private String note;                        //备注
    private String traceLog;                    //操作日志
    private long dataVersion;                   //版本号
    private String createTime;                  //创建时间(业务时间)
    private String lastUpdateTime;              //更新时间

    public long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public int getSyncEas() {
        return syncEas;
    }

    public void setSyncEas(int syncEas) {
        this.syncEas = syncEas;
    }

    public String getEasId() {
        return easId;
    }

    public void setEasId(String easId) {
        this.easId = easId;
    }

    public String getEasOrderNumber() {
        return easOrderNumber;
    }

    public void setEasOrderNumber(String easOrderNumber) {
        this.easOrderNumber = easOrderNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAllocateNo() {
        return allocateNo;
    }

    public void setAllocateNo(String allocateNo) {
        this.allocateNo = allocateNo;
    }

    public String getProjectIdOut() {
        return projectIdOut;
    }

    public void setProjectIdOut(String projectIdOut) {
        this.projectIdOut = projectIdOut;
    }

    public String getProjectIdEnter() {
        return projectIdEnter;
    }

    public void setProjectIdEnter(String projectIdEnter) {
        this.projectIdEnter = projectIdEnter;
    }

    public String getGongxiaoOutboundOrderNo() {
        return gongxiaoOutboundOrderNo;
    }

    public void setGongxiaoOutboundOrderNo(String gongxiaoOutboundOrderNo) {
        this.gongxiaoOutboundOrderNo = gongxiaoOutboundOrderNo;
    }

    public String getGongxiaoInboundOrderNo() {
        return gongxiaoInboundOrderNo;
    }

    public void setGongxiaoInboundOrderNo(String gongxiaoInboundOrderNo) {
        this.gongxiaoInboundOrderNo = gongxiaoInboundOrderNo;
    }

    public String getWarehouseOutId() {
        return warehouseOutId;
    }

    public void setWarehouseOutId(String warehouseOutId) {
        this.warehouseOutId = warehouseOutId;
    }

    public String getWarehouseEnterId() {
        return warehouseEnterId;
    }

    public void setWarehouseEnterId(String warehouseEnterId) {
        this.warehouseEnterId = warehouseEnterId;
    }

    public String getWarehouseOut() {
        return warehouseOut;
    }

    public void setWarehouseOut(String warehouseOut) {
        this.warehouseOut = warehouseOut;
    }

    public String getWarehouseEnter() {
        return warehouseEnter;
    }

    public void setWarehouseEnter(String warehouseEnter) {
        this.warehouseEnter = warehouseEnter;
    }

    public String getCompanyNameOut() {
        return companyNameOut;
    }

    public void setCompanyNameOut(String companyNameOut) {
        this.companyNameOut = companyNameOut;
    }

    public String getCompanyNameEnter() {
        return companyNameEnter;
    }

    public void setCompanyNameEnter(String companyNameEnter) {
        this.companyNameEnter = companyNameEnter;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public int getAlloteWay() {
        return alloteWay;
    }

    public void setAlloteWay(int alloteWay) {
        this.alloteWay = alloteWay;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getRealInQuantity() {
        return realInQuantity;
    }

    public void setRealInQuantity(int realInQuantity) {
        this.realInQuantity = realInQuantity;
    }

    public int getRealOutQuantity() {
        return realOutQuantity;
    }

    public void setRealOutQuantity(int realOutQuantity) {
        this.realOutQuantity = realOutQuantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRequireTime() {
        return requireTime;
    }

    public void setRequireTime(String requireTime) {
        this.requireTime = requireTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTraceLog() {
        return traceLog;
    }

    public void setTraceLog(String traceLog) {
        this.traceLog = traceLog;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
