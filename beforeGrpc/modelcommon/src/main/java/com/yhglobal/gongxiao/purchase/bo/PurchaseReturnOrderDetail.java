package com.yhglobal.gongxiao.purchase.bo;

import com.yhglobal.gongxiao.purchase.temp.OperateHistoryRecord;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 采购退货详情
 *
 * @author: 陈浩
 * @create: 2018-03-15 18:14
 **/
public class PurchaseReturnOrderDetail implements Serializable {
    /**
     * 原采购单号
     */
    private String originalPurchaseOrderNo;
    /**
     * 原采购单的入库单号
     */
    private String originalGongxiaoInboundOrderNo;
    /**
     * 退货单号
     */
    private String purchaseReturnedOrderNo;
    /**
     * 退货单状态 0-已提交 1-出库中 2-出库完成 3-已签收
     */
    private int returnedOrderStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 收件人名字
     */
    private String recipientName;
    /**
     * 收件人公司名称
     */
    private String recipientCompanyName;
    /**
     * 收件人地址
     */
    private String recipientAddress;
    /**
     * 收件人详细地址
     */
    private String recipientDetailAddress;
    /**
     * 收件人电话
     */
    private String recipientMobile;
    /**
     * 物流方式     0:不详 1:自提 2:第三方物流
     */
    private int shippingMode;
    /**
     * 物流单号
     */
    private String logisticsOrderNo;
    /**
     *物流公司名称
     */
    private String logisticsCompany;
    /**
     * 仓库ID
     */
    private String warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 运费承担方 1:越海 2:品牌商 3:经销商
     */
    private int freightPaidBy;
    /**
     * 运费
     */
    private double freight;
    /**
     * FX-WMS 发货单单号
     */
    private String outboundOrderNumber;
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 退货单类型 1.采购退货 2.采购换货
     */
    private int returnType;

    /**
     * 创建人id
     */
    private int createdById;
    /**
     * 创建人名称
     */
    private String createdByName;
    /**
     * 上次更新时间
     */
    private Date lastUpdateTime;
    /**
     * 货品基本信息
     */
    List<ProductReturnInfo> productReturnInfoList;
    /**
     * 订单历史操作记录
     */
    List<OperateHistoryRecord> operateHistoryRecordList;



    public String getRecipientCompanyName() {
        return recipientCompanyName;
    }

    public void setRecipientCompanyName(String recipientCompanyName) {
        this.recipientCompanyName = recipientCompanyName;
    }

    public String getPurchaseReturnedOrderNo() {
        return purchaseReturnedOrderNo;
    }

    public void setPurchaseReturnedOrderNo(String purchaseReturnedOrderNo) {
        this.purchaseReturnedOrderNo = purchaseReturnedOrderNo;
    }

    public int getReturnedOrderStatus() {
        return returnedOrderStatus;
    }

    public void setReturnedOrderStatus(int returnedOrderStatus) {
        this.returnedOrderStatus = returnedOrderStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getRecipientDetailAddress() {
        return recipientDetailAddress;
    }

    public void setRecipientDetailAddress(String recipientDetailAddress) {
        this.recipientDetailAddress = recipientDetailAddress;
    }

    public String getRecipientMobile() {
        return recipientMobile;
    }

    public void setRecipientMobile(String recipientMobile) {
        this.recipientMobile = recipientMobile;
    }

    public int getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(int shippingMode) {
        this.shippingMode = shippingMode;
    }

    public String getLogisticsOrderNo() {
        return logisticsOrderNo;
    }

    public void setLogisticsOrderNo(String logisticsOrderNo) {
        this.logisticsOrderNo = logisticsOrderNo;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
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

    public int getFreightPaidBy() {
        return freightPaidBy;
    }

    public void setFreightPaidBy(int freightPaidBy) {
        this.freightPaidBy = freightPaidBy;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getOutboundOrderNumber() {
        return outboundOrderNumber;
    }

    public void setOutboundOrderNumber(String outboundOrderNumber) {
        this.outboundOrderNumber = outboundOrderNumber;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getReturnType() {
        return returnType;
    }

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }


    public int getCreatedById() {
        return createdById;
    }

    public void setCreatedById(int createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public List<ProductReturnInfo> getProductReturnInfoList() {
        return productReturnInfoList;
    }

    public void setProductReturnInfoList(List<ProductReturnInfo> productReturnInfoList) {
        this.productReturnInfoList = productReturnInfoList;
    }

    public List<OperateHistoryRecord> getOperateHistoryRecordList() {
        return operateHistoryRecordList;
    }

    public void setOperateHistoryRecordList(List<OperateHistoryRecord> operateHistoryRecordList) {
        this.operateHistoryRecordList = operateHistoryRecordList;
    }

    public String getOriginalPurchaseOrderNo() {
        return originalPurchaseOrderNo;
    }

    public void setOriginalPurchaseOrderNo(String originalPurchaseOrderNo) {
        this.originalPurchaseOrderNo = originalPurchaseOrderNo;
    }

    public String getOriginalGongxiaoInboundOrderNo() {
        return originalGongxiaoInboundOrderNo;
    }

    public void setOriginalGongxiaoInboundOrderNo(String originalGongxiaoInboundOrderNo) {
        this.originalGongxiaoInboundOrderNo = originalGongxiaoInboundOrderNo;
    }
}
