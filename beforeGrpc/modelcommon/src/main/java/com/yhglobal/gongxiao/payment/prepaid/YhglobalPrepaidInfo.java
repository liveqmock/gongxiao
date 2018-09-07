package com.yhglobal.gongxiao.payment.prepaid;

import com.yhglobal.gongxiao.BaseModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 代垫付款单
 * @author: LUOYI
 * @Date: Created in 15:26 2018/4/27
 */
public class YhglobalPrepaidInfo extends BaseModel implements Serializable {
    private Integer prepaidId;

    private String prepaidNo;

    private Long projectId;

    private String projectName;

    private Integer supplierId;

    private String supplierName;

    private String payer;

    private String receivables;

    private String settlementNo;

    private Date dateBusiness;

    private String taxNo;

    private String contactInfo;

    private Integer provinceId;

    private String provinceName;

    private Integer cityId;

    private String cityName;

    private Integer districtId;

    private String districtName;

    private String streetAddress;

    private String accountCNY;

    private String bankNameCNY;

    private Long standardCurrencyAmount;

    private Double standardCurrencyAmountDouble;

    private Integer settlementType;

    private String remark;


    public Integer getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(Integer settlementType) {
        this.settlementType = settlementType;
    }

    private List<YhglobalPrepaidInfoItem> itemList = new ArrayList<>();

    public String getPrepaidNo() {
        return prepaidNo;
    }

    public void setPrepaidNo(String prepaidNo) {
        this.prepaidNo = prepaidNo;
    }

    public Double getStandardCurrencyAmountDouble() {
        return standardCurrencyAmountDouble;
    }

    public void setStandardCurrencyAmountDouble(Double standardCurrencyAmountDouble) {
        this.standardCurrencyAmountDouble = standardCurrencyAmountDouble;
    }

    public Long getStandardCurrencyAmount() {
        return standardCurrencyAmount;
    }

    public void setStandardCurrencyAmount(Long standardCurrencyAmount) {
        this.standardCurrencyAmount = standardCurrencyAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<YhglobalPrepaidInfoItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<YhglobalPrepaidInfoItem> itemList) {
        this.itemList = itemList;
    }

    public Integer getPrepaidId() {
        return prepaidId;
    }

    public void setPrepaidId(Integer prepaidId) {
        this.prepaidId = prepaidId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer == null ? null : payer.trim();
    }

    public String getReceivables() {
        return receivables;
    }

    public void setReceivables(String receivables) {
        this.receivables = receivables == null ? null : receivables.trim();
    }

    public String getSettlementNo() {
        return settlementNo;
    }

    public void setSettlementNo(String settlementNo) {
        this.settlementNo = settlementNo == null ? null : settlementNo.trim();
    }

    public Date getDateBusiness() {
        return dateBusiness;
    }

    public void setDateBusiness(Date dateBusiness) {
        this.dateBusiness = dateBusiness;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo == null ? null : taxNo.trim();
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo == null ? null : contactInfo.trim();
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName == null ? null : districtName.trim();
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress == null ? null : streetAddress.trim();
    }

    public String getAccountCNY() {
        return accountCNY;
    }

    public void setAccountCNY(String accountCNY) {
        this.accountCNY = accountCNY == null ? null : accountCNY.trim();
    }

    public String getBankNameCNY() {
        return bankNameCNY;
    }

    public void setBankNameCNY(String bankNameCNY) {
        this.bankNameCNY = bankNameCNY == null ? null : bankNameCNY.trim();
    }

}