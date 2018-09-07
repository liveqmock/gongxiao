package com.yhglobal.gongxiao.warehousemanagement.model;

import java.io.Serializable;
import java.util.Date;

/**
 *仓储管理------出库单管理
 */
public class OutboundOrder implements Serializable{
    private Long rowId;                             //主键id
    private String projectId;                       //项目id
    private String batchNo;                         //批次号
    private int inventoryType;                      //库存类型
    private int orderStatus;                        //出库单状态  (1:等待出库 2:部分出仓 3:发货完成 4:订单已取消 6:订单已签收 5:订单已关闭 7:正在同步到WMS)
    private int outboundType;                       //出库类型 0:不详 1:销售发货  6:采购退货
    private String gongxiaoOutboundOrderNo;         //GX出库单号
    private String outboundOrderItemNo;             //出库明细单号
    private int sourceChannel;                      //发起出库通知单的渠道
    private int syncToWmsFlag;                      //是否已同步到WMS
    private int syncToTmsFlag;              //是否已同步到TMS
    private String wmsOutboundOrderNo;              //WMS那边记录的入库单号
    private String purchaseOrderNo;                 //采购退货单对应的采购单号(采购退货场景)
    private String returnedOrderNo;                 //采购退货单号(采购退货场景)
    private String salesOrderNo;                    //销售单号(销售出货场景)
    private String warehouseId;                     //仓库ID
    private String warehouseName;                   //仓库名称
    private String deliverAddress;                  //发货地址(发货仓库地址)
    private int shippingMode;                       //物流方式 0:不详 1:自提 2:第三方物流
    private String supplierName;                    //供应商
    private String shippingAddress;                 //供应商的收货地址
    private String provinceName;                    //省份
    private String cityName;                        //城市
    private String customer;                        //客户
    private String contactsPeople;                   //收件人名字
    private String phoneNum;                 //收件人手机号
    private long shippingExpense;                   //运费
    private int paidBy;                             //运费承担方 1:越海 2:品牌商 3:经销商
    private String transporter;                     //运输商
    private String tmsOrdNo;                        //tms运单号
    private int tmsOrderStatus;                     //运输单状态
    private String tmsSignedBy;                        //tms签收人
    private String tmsSignedPhone;                     //tms签收人电话
    private Date tmsSingedTime;                      //tms签收时间
    private String note;                             //备注
    private String connectedProduct;                //出库单关联的商品编码
    private int totalQuantity;                      //商品数量总和
    private int outStockQuantity;                   //已出库的商品数量
    private int imperfectQuantity;                  //已出库的残次品数量
    private int transferQuantity;                   //在途数量
    private int canceledQuantity;                  //已取消的商品数量
    private int realOutStockQuantity;               //实际出库数量
    private long dataVersion;                       //数据版本
    private String tracelog;                        //操作日志
    private Long createdById;                       //创建人id
    private String createdByName;                     //创建人名称
    private String uniqueNo;                        //唯一号
    private Date expectedArrivalTime;               //预计出库时间
    private Date createTime;                        //创建时间
    private Date lastUpdateTime;                   //上次更新时间

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOutboundType() {
        return outboundType;
    }

    public void setOutboundType(int outboundType) {
        this.outboundType = outboundType;
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

    public int getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(int sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    public int getSyncToWmsFlag() {
        return syncToWmsFlag;
    }

    public void setSyncToWmsFlag(int syncToWmsFlag) {
        this.syncToWmsFlag = syncToWmsFlag;
    }

    public int getSyncToTmsFlag() {
        return syncToTmsFlag;
    }

    public void setSyncToTmsFlag(int syncToTmsFlag) {
        this.syncToTmsFlag = syncToTmsFlag;
    }

    public String getWmsOutboundOrderNo() {
        return wmsOutboundOrderNo;
    }

    public void setWmsOutboundOrderNo(String wmsOutboundOrderNo) {
        this.wmsOutboundOrderNo = wmsOutboundOrderNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getReturnedOrderNo() {
        return returnedOrderNo;
    }

    public void setReturnedOrderNo(String returnedOrderNo) {
        this.returnedOrderNo = returnedOrderNo;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public long getShippingExpense() {
        return shippingExpense;
    }

    public void setShippingExpense(long shippingExpense) {
        this.shippingExpense = shippingExpense;
    }

    public int getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(int paidBy) {
        this.paidBy = paidBy;
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

    public int getTmsOrderStatus() {
        return tmsOrderStatus;
    }

    public void setTmsOrderStatus(int tmsOrderStatus) {
        this.tmsOrderStatus = tmsOrderStatus;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getConnectedProduct() {
        return connectedProduct;
    }

    public void setConnectedProduct(String connectedProduct) {
        this.connectedProduct = connectedProduct;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getOutStockQuantity() {
        return outStockQuantity;
    }

    public void setOutStockQuantity(int outStockQuantity) {
        this.outStockQuantity = outStockQuantity;
    }

    public int getImperfectQuantity() {
        return imperfectQuantity;
    }

    public void setImperfectQuantity(int imperfectQuantity) {
        this.imperfectQuantity = imperfectQuantity;
    }

    public int getTransferQuantity() {
        return transferQuantity;
    }

    public void setTransferQuantity(int transferQuantity) {
        this.transferQuantity = transferQuantity;
    }

    public int getCanceledQuantity() {
        return canceledQuantity;
    }

    public void setCanceledQuantity(int canceledQuantity) {
        this.canceledQuantity = canceledQuantity;
    }

    public int getRealOutStockQuantity() {
        return realOutStockQuantity;
    }

    public void setRealOutStockQuantity(int realOutStockQuantity) {
        this.realOutStockQuantity = realOutStockQuantity;
    }

    public long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
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
}
