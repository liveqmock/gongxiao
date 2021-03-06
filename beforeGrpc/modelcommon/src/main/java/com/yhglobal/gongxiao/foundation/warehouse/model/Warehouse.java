package com.yhglobal.gongxiao.foundation.warehouse.model;

import java.io.Serializable;
import java.util.Date;

public class Warehouse implements Serializable {
    private Integer id;

    private Long warehouseId;

    private String warehouseName;

    private String warehouseCode;

    private String easWarehouseCode;

    private String easWarehouseName;

    private String wmsWarehouseCode;

    private String wmsWarehouseName;

    private Byte status;

    private Integer locationNumber;

    private String longitude;

    private String latitude;

    private String countryCode;

    private String countryName;

    private Integer provinceId;

    private String provinceName;

    private Integer cityId;

    private String cityName;

    private String districtName;

    private String streetAddress;

    private String shortAddress;

    private String generalContactName;

    private String generalphoneNumber;

    private String generalMobile;

    private String reservationContactName;

    private String reservationPhoneNumber;

    private String reservationMobile;

    private Long createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getEasWarehouseCode() {
        return easWarehouseCode;
    }

    public void setEasWarehouseCode(String easWarehouseCode) {
        this.easWarehouseCode = easWarehouseCode == null ? null : easWarehouseCode.trim();
    }

    public String getEasWarehouseName() {
        return easWarehouseName;
    }

    public void setEasWarehouseName(String easWarehouseName) {
        this.easWarehouseName = easWarehouseName == null ? null : easWarehouseName.trim();
    }

    public String getWmsWarehouseCode() {
        return wmsWarehouseCode;
    }

    public void setWmsWarehouseCode(String wmsWarehouseCode) {
        this.wmsWarehouseCode = wmsWarehouseCode == null ? null : wmsWarehouseCode.trim();
    }

    public String getWmsWarehouseName() {
        return wmsWarehouseName;
    }

    public void setWmsWarehouseName(String wmsWarehouseName) {
        this.wmsWarehouseName = wmsWarehouseName == null ? null : wmsWarehouseName.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(Integer locationNumber) {
        this.locationNumber = locationNumber;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName == null ? null : countryName.trim();
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

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress == null ? null : shortAddress.trim();
    }

    public String getGeneralContactName() {
        return generalContactName;
    }

    public void setGeneralContactName(String generalContactName) {
        this.generalContactName = generalContactName == null ? null : generalContactName.trim();
    }

    public String getGeneralphoneNumber() {
        return generalphoneNumber;
    }

    public void setGeneralphoneNumber(String generalphoneNumber) {
        this.generalphoneNumber = generalphoneNumber == null ? null : generalphoneNumber.trim();
    }

    public String getGeneralMobile() {
        return generalMobile;
    }

    public void setGeneralMobile(String generalMobile) {
        this.generalMobile = generalMobile == null ? null : generalMobile.trim();
    }

    public String getReservationContactName() {
        return reservationContactName;
    }

    public void setReservationContactName(String reservationContactName) {
        this.reservationContactName = reservationContactName == null ? null : reservationContactName.trim();
    }

    public String getReservationPhoneNumber() {
        return reservationPhoneNumber;
    }

    public void setReservationPhoneNumber(String reservationPhoneNumber) {
        this.reservationPhoneNumber = reservationPhoneNumber == null ? null : reservationPhoneNumber.trim();
    }

    public String getReservationMobile() {
        return reservationMobile;
    }

    public void setReservationMobile(String reservationMobile) {
        this.reservationMobile = reservationMobile == null ? null : reservationMobile.trim();
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
        this.createdByName = createdByName == null ? null : createdByName.trim();
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

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }
}