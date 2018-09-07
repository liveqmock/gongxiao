package com.yhglobal.gongxiao.warehousemanagement.dto.tms;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 通知tms出库的请求
 * @author liukai
 */
public class TmsOutStockNotifyRequest implements Serializable {
    private String custOrdNo;       //客户订单号
    private Date orderCreateTime;   //订单创建时间
    private Date workDt;            //作业时间
    private Date reqDt;             //要求到达时间
    private int orderType;          //操作类型/订单类型
    private int workType;           //送货方式
    private String customerName;    //客户名称
    private String customerCode;    //客户代码
    private String pO;              //PO号
    private String invNO;           //发票号
    private String property;        //运输性质
    private String attributes;      //运输属性
    private String billingType;     //计费类型
    private String hold;            //控货
    private String projectCode;     //项目代码
    private String projectName;     //项目代码
    private String lineName;        //线路
    private int ordSource;       //来源
    private Receiver receiver;        //收货方信息
    private Sender sender;          //发货方信息
    private List<TmsStockInOrderItem> orderItemList;   //订单商品信息
    private String remark;          //备注

    public String getCustOrdNo() {
        return custOrdNo;
    }

    public void setCustOrdNo(String custOrdNo) {
        this.custOrdNo = custOrdNo;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Date getWorkDt() {
        return workDt;
    }

    public void setWorkDt(Date workDt) {
        this.workDt = workDt;
    }

    public Date getReqDt() {
        return reqDt;
    }

    public void setReqDt(Date reqDt) {
        this.reqDt = reqDt;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
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

    public String getpO() {
        return pO;
    }

    public void setpO(String pO) {
        this.pO = pO;
    }

    public String getInvNO() {
        return invNO;
    }

    public void setInvNO(String invNO) {
        this.invNO = invNO;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public String getHold() {
        return hold;
    }

    public void setHold(String hold) {
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

    public int getOrdSource() {
        return ordSource;
    }

    public void setOrdSource(int ordSource) {
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

    public List<TmsStockInOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<TmsStockInOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
