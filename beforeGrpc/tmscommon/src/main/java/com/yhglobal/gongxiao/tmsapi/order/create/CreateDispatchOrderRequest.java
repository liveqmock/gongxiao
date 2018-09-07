package com.yhglobal.gongxiao.tmsapi.order.create;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 创建运单请求
 */
public class CreateDispatchOrderRequest {

    public String custOrdNo; //必选 客户订单号

    public String orderCreateTime; //必选 订单创建时间 格式yyyy-MM-dd HH:mm:ss

    public String workDt; //可选 作业时间 格式yyyy-MM-dd HH:mm:ss

    public String reqDt; //必选 要求到达时间 格式yyyy-MM-dd HH:mm:ss

    public Integer orderType; //必选 订单类型

    public Integer workType; //必选 送货方式

    public String customerName; //可选 客户名称

    public String customerCode; //可选 客户代码

    public String po; //可选 PO号

    public String invNO; //可选 发票号

    public Integer property; //可选 运输性质

    public Integer attributes; //可选 运输属性

    public Integer billingType; //可选 计费类型

    public Integer hold; //可选 是否控货: 1是 0否

    public String projectCode; //可选 项目代码

    public String projectName; //可选 项目名称

    public String lineName; //可选 线路

    public Integer ordSource; //必选 来源

    public Receiver receiver; //必选 收货方信息

    public Sender sender; //必选 发货方信息

    public List<StockInOrderItem> orderItemList; //必选 订单商品信息

    public String remark; //可选 备注


    public String getCustOrdNo() {
        return custOrdNo;
    }

    public void setCustOrdNo(String custOrdNo) {
        this.custOrdNo = custOrdNo;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getWorkDt() {
        return workDt;
    }

    public void setWorkDt(String workDt) {
        this.workDt = workDt;
    }

    public String getReqDt() {
        return reqDt;
    }

    public void setReqDt(String reqDt) {
        this.reqDt = reqDt;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getInvNO() {
        return invNO;
    }

    public void setInvNO(String invNO) {
        this.invNO = invNO;
    }

    public Integer getProperty() {
        return property;
    }

    public void setProperty(Integer property) {
        this.property = property;
    }

    public Integer getAttributes() {
        return attributes;
    }

    public void setAttributes(Integer attributes) {
        this.attributes = attributes;
    }

    public Integer getBillingType() {
        return billingType;
    }

    public void setBillingType(Integer billingType) {
        this.billingType = billingType;
    }

    public Integer getHold() {
        return hold;
    }

    public void setHold(Integer hold) {
        this.hold = hold;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Integer getOrdSource() {
        return ordSource;
    }

    public void setOrdSource(Integer ordSource) {
        this.ordSource = ordSource;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public List<StockInOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<StockInOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
