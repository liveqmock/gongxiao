package com.yhglobal.gongxiao.sales.entity;

import com.yhglobal.gongxiao.utils.NumberFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 17:32 2018/7/19
 */
public class SalesReturnOrder {
    private long projectId;
    private String projectName;
    private String originalSalesOrderNo;
    private String originalGongxiaoOutboundOrderNo;
    private Long salesReturnedOrderId;
    private String salesReturnedOrderNo;
    private Integer returnedOrderStatus;
    private String distributorName;
    private Integer returnedType;
    private String originalOutboundWarehouseId;
    private String originalOutboundWarehouseName;
    private String targetWarehouseId;
    private String targetWarehouseName;
    private String senderName;
    private String senderMobile;
    private Integer logisticsType;
    private Double freight;
    private Integer freightPaidBy;
    private String logisticsOrderNo;
    private String logisticsCompany;
    private String provinceId;
    private String provinceName;
    private String cityId;
    private String cityName;
    private String districtId;
    private String districtName;
    private String senderAddressItem;
    private String warehouseAddress;
    private Date lastUpdateTime;
    private Date createTime;
    private Integer dataVersion;
    private String salesReturnOrderItemListJson;
    private String tracelog;
    private List<SalesReturnItem> itemList;//退货详情

    public List<SalesReturnItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SalesReturnItem> itemList) {
        this.itemList = itemList;
    }

    public String getFreightPaidByStr(){
        if (null!=this.freightPaidBy){
            switch (this.freightPaidBy){
                case 1: return "越海";
                case 2: return "品牌商";
                case 3: return "经销商";
            }
        }
        return "";
    }
    private String getFreightStr(){
        return NumberFormat.format(this.freight,"#,##0.00");
    }
    public String getLogisticsTypeStr(){
        if(this.logisticsType !=null){
           return this.getLogisticsType() == 1?"自提":"第三方物流";
        }
        return "";
    }
    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
    }

    public String getSalesReturnOrderItemListJson() {
        return salesReturnOrderItemListJson;
    }

    public void setSalesReturnOrderItemListJson(String salesReturnOrderItemListJson) {
        this.salesReturnOrderItemListJson = salesReturnOrderItemListJson;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOriginalSalesOrderNo() {
        return originalSalesOrderNo;
    }

    public void setOriginalSalesOrderNo(String originalSalesOrderNo) {
        this.originalSalesOrderNo = originalSalesOrderNo;
    }

    public String getOriginalGongxiaoOutboundOrderNo() {
        return originalGongxiaoOutboundOrderNo;
    }

    public void setOriginalGongxiaoOutboundOrderNo(String originalGongxiaoOutboundOrderNo) {
        this.originalGongxiaoOutboundOrderNo = originalGongxiaoOutboundOrderNo;
    }

    public String getOriginalOutboundWarehouseId() {
        return originalOutboundWarehouseId;
    }

    public void setOriginalOutboundWarehouseId(String originalOutboundWarehouseId) {
        this.originalOutboundWarehouseId = originalOutboundWarehouseId;
    }

    public String getTargetWarehouseId() {
        return targetWarehouseId;
    }

    public void setTargetWarehouseId(String targetWarehouseId) {
        this.targetWarehouseId = targetWarehouseId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public Integer getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(Integer logisticsType) {
        this.logisticsType = logisticsType;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Integer getFreightPaidBy() {
        return freightPaidBy;
    }

    public void setFreightPaidBy(Integer freightPaidBy) {
        this.freightPaidBy = freightPaidBy;
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

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getSenderAddressItem() {
        return senderAddressItem;
    }

    public void setSenderAddressItem(String senderAddressItem) {
        this.senderAddressItem = senderAddressItem;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    public Integer getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Long getSalesReturnedOrderId() {
        return salesReturnedOrderId;
    }

    public void setSalesReturnedOrderId(Long salesReturnedOrderId) {
        this.salesReturnedOrderId = salesReturnedOrderId;
    }

    public String getSalesReturnedOrderNo() {
        return salesReturnedOrderNo;
    }

    public void setSalesReturnedOrderNo(String salesReturnedOrderNo) {
        this.salesReturnedOrderNo = salesReturnedOrderNo;
    }

    public Integer getReturnedOrderStatus() {
        return returnedOrderStatus;
    }

    public void setReturnedOrderStatus(Integer returnedOrderStatus) {
        this.returnedOrderStatus = returnedOrderStatus;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public Integer getReturnedType() {
        return returnedType;
    }

    public void setReturnedType(Integer returnedType) {
        this.returnedType = returnedType;
    }

    public String getOriginalOutboundWarehouseName() {
        return originalOutboundWarehouseName;
    }

    public void setOriginalOutboundWarehouseName(String originalOutboundWarehouseName) {
        this.originalOutboundWarehouseName = originalOutboundWarehouseName;
    }

    public String getTargetWarehouseName() {
        return targetWarehouseName;
    }

    public void setTargetWarehouseName(String targetWarehouseName) {
        this.targetWarehouseName = targetWarehouseName;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
