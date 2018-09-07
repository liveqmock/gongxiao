package com.yhglobal.gongxiao.warehousemanagement.bo;

import java.io.Serializable;

/**
 * 调拨单信息页面模型
 * @author liukai
 */
public class AllocationOrderShowModel implements Serializable{
    private String allocateNo;                   //调拨单号
    private String projectIdOut;                //调出项目
    private String projectIdEnter;              //调入项目
    private String gongxiaoOutboundOrderNo;     //出库单号
    private String gongxiaoInboundOrderNo;      //入库单号
    private String warehouseOut;                //调出仓库
    private String warehouseEnter;              //调入仓库
    private String alloteWay;                      //调拨方式
    private int status;                         //状态
    private String createTime;                  //创建时间(业务时间)

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

    public String getAlloteWay() {
        return alloteWay;
    }

    public void setAlloteWay(String alloteWay) {
        this.alloteWay = alloteWay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
