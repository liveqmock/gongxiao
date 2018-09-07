package com.yhglobal.gongxiao.diaobo;


import java.io.Serializable;
import java.util.Date;

/**
 *仓储管理------入库单管理
 */
public class InboundOrder implements Serializable {
    private Long rowId;                     //主键id
    private String custCode;                //客户代码
    private Long projectId;                 //项目id
    private String batchNo;                 //批次号
    private int orderStatus;                //入库单状态 (0:等待入库 1:部分收货 2:收货完成 3:订单已取消)
    private int purchaseType;               //采购类型   (0:普通采购 1：货补 2：赠品)
    private int inboundType;                //入库单类型
    private String gongxiaoInboundOrderNo;  //入库单号
    private int sourceChannel;              //发起入库通知单的渠道
    private int syncToWmsFlag;              //是否已同步到WMS
    private int syncToTmsFlag;              //是否已同步到TMS
    private String wmsInboundOrderNo;       //WMS那边记录的入库单号
    private String purchaseOrderNo;         //采购单号(采购入库场景)
    private String salesOrderNo;            //销售退货单对应的原销售单号(销售退货场景)
    private String returnedSalesOrderNo;    //销售退货单号(销售退货场景)
    private String warehouseId;             //仓库ID
    private String warehouseName;           //仓库名称
    private String supplierName;                //供应商
    private String contactsPeople;          //发货联系人
    private String phoneNum;                //联系电话
    private String deliverAddress;          //发货地址
    private int shippingMode;               //物流方式 0:不详 1:自提 2:第三方物流
    private String note;                    //备注
    private String connectedProduct;  //入库单关联的商品编码
    private String connectedGood;     //入库单关联的产品编码
    private int totalQuantity;              //各种类商品本次入库数量总和
    private int inTransitQuantity;          //在途商品数量
    private int inStockQuantity;            //已入库的商品数量
    private int imperfectQuantity;          //已入库的残次品数量
    private int rejectedQuantity;           //被拒收未入库的商品数量(残次品)
    private int realInStockQuantity;        //实际入库数量
    private long dataVersion;               //数据版本
    private String tracelog;                //操作日记
    private long createdById;               //创建人id
    private String createdByName;           //创建人名称
    private String uniqueNo;                //请求唯一号
    private Date expectedArrivalTime;       //预计到货时间
    private Date createTime;                //创建时间
    private Date lastUpdateTime;            //上次更新时间

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
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

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    public int getInboundType() {
        return inboundType;
    }

    public void setInboundType(int inboundType) {
        this.inboundType = inboundType;
    }

    public String getGongxiaoInboundOrderNo() {
        return gongxiaoInboundOrderNo;
    }

    public void setGongxiaoInboundOrderNo(String gongxiaoInboundOrderNo) {
        this.gongxiaoInboundOrderNo = gongxiaoInboundOrderNo;
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

    public String getWmsInboundOrderNo() {
        return wmsInboundOrderNo;
    }

    public void setWmsInboundOrderNo(String wmsInboundOrderNo) {
        this.wmsInboundOrderNo = wmsInboundOrderNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getReturnedSalesOrderNo() {
        return returnedSalesOrderNo;
    }

    public void setReturnedSalesOrderNo(String returnedSalesOrderNo) {
        this.returnedSalesOrderNo = returnedSalesOrderNo;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getConnectedProduct() {
        return connectedProduct;
    }

    public void setConnectedProduct(String connectedProduct) {
        this.connectedProduct = connectedProduct;
    }

    public String getConnectedGood() {
        return connectedGood;
    }

    public void setConnectedGood(String connectedGood) {
       this.connectedGood = connectedGood;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getInTransitQuantity() {
        return inTransitQuantity;
    }

    public void setInTransitQuantity(int inTransitQuantity) {
        this.inTransitQuantity = inTransitQuantity;
    }

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public int getImperfectQuantity() {
        return imperfectQuantity;
    }

    public void setImperfectQuantity(int imperfectQuantity) {
        this.imperfectQuantity = imperfectQuantity;
    }

    public int getRejectedQuantity() {
        return rejectedQuantity;
    }

    public void setRejectedQuantity(int rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
    }

    public int getRealInStockQuantity() {
        return realInStockQuantity;
    }

    public void setRealInStockQuantity(int realInStockQuantity) {
        this.realInStockQuantity = realInStockQuantity;
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

    public long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(long createdById) {
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
