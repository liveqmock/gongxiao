package com.yhglobal.gongxiao.phjd.bean;

/**
 * 收货地址
 * @author yuping.lin
 */
public class CollectGoodsBean {
    private Integer addressId;          //'经销商用户Id'
    private Integer distributorId;          //'经销商Id'
    private String distributorName;          //'经销商名称'
    private String receiver;          //'收货人'
    private Integer provinceId;          //'省份id'
    private String provinceName;          //'省份名称'
    private Integer cityId;          //'城市id'
    private String cityName;          //'市'
    private Integer districtId;          //'区id'
    private String districtName;          //'区'
    private String streetAddress;          //'街道地址(区之后的部分)'
    private String phoneNumber;          //'电话/手机'
    private String postCode;          //'邮编'
    private String auditStatus;          //'状态  1初始化 2 已提交,3 已审核,4 已驳回 9已废弃'


    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
        this.provinceName = provinceName;
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
        this.cityName = cityName;
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
        this.districtName = districtName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

}
