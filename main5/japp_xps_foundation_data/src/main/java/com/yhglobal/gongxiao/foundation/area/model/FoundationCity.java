package com.yhglobal.gongxiao.foundation.area.model;

public class FoundationCity {
    private Integer cityId;

    private Integer cityCode;

    private String cityName;

    private Integer provinceCode;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Integer getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Integer provinceCode) {
        this.provinceCode = provinceCode;
    }
}