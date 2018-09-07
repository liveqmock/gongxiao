package com.yhglobal.gongxiao.sales.model.cancel.dto;

import com.yhglobal.gongxiao.util.NumberFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 18:42 2018/3/30
 */
public class SalesReturn implements Serializable {
    private String salesReturnedOrderNo;//退货订单号
    private Integer returnedOrderStatus;//状态
    private Date createTime;//退单创建时间
    private String senderName;//退货人
    /**
     * 省id
     */
    private String provinceId;
    /**
     * 省
     */
    private String provinceName;
    /**
     * 市id
     */
    private String cityId;
    /**
     * 市
     */
    private String cityName;
    /**
     * 区id
     */
    private String districtId;
    /**
     * 区
     */
    private String districtName;
    private String senderAddressItem;//详细地址
    private String senderMobile;//手机
    private String distributorName;//退货公司名称
    private Integer logisticsType;//运输方式
    private String logisticsCompany;//退回物流公司
    private String logisticsOrderNo;//物流运单号
    private String targetWarehouseName;//退回仓库名称
    private Integer freightPaidBy;//运费承担方
    private Double freight;//运费
    private String originalGongxiaoOutboundOrderNo;//发货单编号
    private String projectName;//项目名称
    private Integer returnedType;//退货单类型
    private String createdByName;//退货单创建人
    private Date lastUpdateTime;//状态更新时间
    private List<SalesReturnItem> itemList;//退货详情
    private String tracelog;//操作日志
    public String getFreightPaidByStr(){
        switch (this.freightPaidBy){
            case 0: return "越海";
            case 1: return "品牌商";
            case 2: return "经销商";
        }
        return "";
    }
    private String getFreightStr(){
        return NumberFormat.format(this.freight,"#,##0.00");
    }
    public String getLogisticsTypeStr(){
        return this.getLogisticsType() == 0?"自提":"第三方物流";
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public Integer getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(Integer logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsOrderNo() {
        return logisticsOrderNo;
    }

    public void setLogisticsOrderNo(String logisticsOrderNo) {
        this.logisticsOrderNo = logisticsOrderNo;
    }

    public String getTargetWarehouseName() {
        return targetWarehouseName;
    }

    public void setTargetWarehouseName(String targetWarehouseName) {
        this.targetWarehouseName = targetWarehouseName;
    }

    public Integer getFreightPaidBy() {
        return freightPaidBy;
    }

    public void setFreightPaidBy(Integer freightPaidBy) {
        this.freightPaidBy = freightPaidBy;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public String getOriginalGongxiaoOutboundOrderNo() {
        return originalGongxiaoOutboundOrderNo;
    }

    public void setOriginalGongxiaoOutboundOrderNo(String originalGongxiaoOutboundOrderNo) {
        this.originalGongxiaoOutboundOrderNo = originalGongxiaoOutboundOrderNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getReturnedType() {
        return returnedType;
    }

    public void setReturnedType(Integer returnedType) {
        this.returnedType = returnedType;
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

    public List<SalesReturnItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SalesReturnItem> itemList) {
        this.itemList = itemList;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
    }
}
