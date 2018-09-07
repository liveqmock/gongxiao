package com.yhglobal.gongxiao.sales.model.cancel.model;

import com.yhglobal.gongxiao.BaseModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 退货单
 * @author : liukai
 */
public class SalesReturnOrder extends BaseModel implements Serializable {
    private long salesReturnedOrderId;
    private int returnedOrderStatus;
    private int returnedType;
    private String salesReturnedOrderNo;
    private long projectId;
    private String projectName;
    private Long brandId;
    private String brandName;
    private long supplierId;
    private String supplierName;
    private long distributorId;
    private String distributorName;
    private String originalSalesOrderNo;
    private String originalGongxiaoOutboundOrderNo;
    private String originalOutboundWarehouseId;
    private String originalOutboundWarehouseName;
    private String targetWarehouseId;
    private String targetWarehouseName;
    private String warehouseAddress;
    private String senderName;
    private String senderMobile;
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
    private String senderAddressItem;
    private Integer logisticsType;
    private long freight;
    private Double freightDouble;
    private int freightPaidBy;
    private String logisticsOrderNo;
    private String logisticsCompany;
    private long returnedCouponAmount;
    private Double returnedCouponAmountDouble;
    private long returnedPrepaidAmount;
    private Double returnedPrepaidAmountDouble;
    private long returnedCashAmount;
    private Double returnedCashAmountDouble;
    private int totalReturnedQuantity;
    private int totalImperfectQuantity;
    private int inTransitQuantity;
    private int inStockQuantity;
    private String inboundOrderInfo;
    private String gongxiaoWarehouseInboundOrderNo;
    private String  salesReturnOrderItemListJson;
    private List<SalesReturnOrderItem> itemList = new ArrayList<>();

    public Double getFreightDouble() {
        return freightDouble;
    }

    public void setFreightDouble(Double freightDouble) {
        this.freightDouble = freightDouble;
    }

    public List<SalesReturnOrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SalesReturnOrderItem> itemList) {
        this.itemList = itemList;
    }

    public Integer getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(Integer logisticsType) {
        this.logisticsType = logisticsType;
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

    public String getGongxiaoWarehouseInboundOrderNo() {
        return gongxiaoWarehouseInboundOrderNo;
    }

    public void setGongxiaoWarehouseInboundOrderNo(String gongxiaoWarehouseInboundOrderNo) {
        this.gongxiaoWarehouseInboundOrderNo = gongxiaoWarehouseInboundOrderNo;
    }

    public long getSalesReturnedOrderId() {
        return salesReturnedOrderId;
    }

    public void setSalesReturnedOrderId(long salesReturnedOrderId) {
        this.salesReturnedOrderId = salesReturnedOrderId;
    }

    public int getReturnedOrderStatus() {
        return returnedOrderStatus;
    }

    public void setReturnedOrderStatus(int returnedOrderStatus) {
        this.returnedOrderStatus = returnedOrderStatus;
    }

    public int getReturnedType() {
        return returnedType;
    }

    public void setReturnedType(int returnedType) {
        this.returnedType = returnedType;
    }

    public String getSalesReturnedOrderNo() {
        return salesReturnedOrderNo;
    }

    public void setSalesReturnedOrderNo(String salesReturnedOrderNo) {
        this.salesReturnedOrderNo = salesReturnedOrderNo;
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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(long distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
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

    public String getOriginalOutboundWarehouseName() {
        return originalOutboundWarehouseName;
    }

    public void setOriginalOutboundWarehouseName(String originalOutboundWarehouseName) {
        this.originalOutboundWarehouseName = originalOutboundWarehouseName;
    }

    public String getTargetWarehouseId() {
        return targetWarehouseId;
    }

    public void setTargetWarehouseId(String targetWarehouseId) {
        this.targetWarehouseId = targetWarehouseId;
    }

    public String getTargetWarehouseName() {
        return targetWarehouseName;
    }

    public void setTargetWarehouseName(String targetWarehouseName) {
        this.targetWarehouseName = targetWarehouseName;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
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

    public long getFreight() {
        return freight;
    }

    public void setFreight(long freight) {
        this.freight = freight;
    }

    public int getFreightPaidBy() {
        return freightPaidBy;
    }

    public void setFreightPaidBy(int freightPaidBy) {
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

    public long getReturnedCouponAmount() {
        return returnedCouponAmount;
    }

    public void setReturnedCouponAmount(long returnedCouponAmount) {
        this.returnedCouponAmount = returnedCouponAmount;
    }

    public Double getReturnedCouponAmountDouble() {
        return returnedCouponAmountDouble;
    }

    public void setReturnedCouponAmountDouble(Double returnedCouponAmountDouble) {
        this.returnedCouponAmountDouble = returnedCouponAmountDouble;
    }

    public long getReturnedPrepaidAmount() {
        return returnedPrepaidAmount;
    }

    public void setReturnedPrepaidAmount(long returnedPrepaidAmount) {
        this.returnedPrepaidAmount = returnedPrepaidAmount;
    }

    public Double getReturnedPrepaidAmountDouble() {
        return returnedPrepaidAmountDouble;
    }

    public void setReturnedPrepaidAmountDouble(Double returnedPrepaidAmountDouble) {
        this.returnedPrepaidAmountDouble = returnedPrepaidAmountDouble;
    }

    public long getReturnedCashAmount() {
        return returnedCashAmount;
    }

    public void setReturnedCashAmount(long returnedCashAmount) {
        this.returnedCashAmount = returnedCashAmount;
    }

    public Double getReturnedCashAmountDouble() {
        return returnedCashAmountDouble;
    }

    public void setReturnedCashAmountDouble(Double returnedCashAmountDouble) {
        this.returnedCashAmountDouble = returnedCashAmountDouble;
    }

    public int getTotalReturnedQuantity() {
        return totalReturnedQuantity;
    }

    public void setTotalReturnedQuantity(int totalReturnedQuantity) {
        this.totalReturnedQuantity = totalReturnedQuantity;
    }

    public int getTotalImperfectQuantity() {
        return totalImperfectQuantity;
    }

    public void setTotalImperfectQuantity(int totalImperfectQuantity) {
        this.totalImperfectQuantity = totalImperfectQuantity;
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

    public String getInboundOrderInfo() {
        return inboundOrderInfo;
    };

    public void setInboundOrderInfo(String inboundOrderInfo) {
        this.inboundOrderInfo = inboundOrderInfo;
    }

    public String getSalesReturnOrderItemListJson() {
        return salesReturnOrderItemListJson;
    }

    public void setSalesReturnOrderItemListJson(String salesReturnOrderItemListJson) {
        this.salesReturnOrderItemListJson = salesReturnOrderItemListJson;
    }
}
