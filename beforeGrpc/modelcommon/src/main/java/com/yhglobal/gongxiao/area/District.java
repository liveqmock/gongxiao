package com.yhglobal.gongxiao.area;

import java.io.Serializable;

public class District  implements Serializable {
    private Integer districtId;

    private int districtCode;

    private String districtName;

    private int cityCode;

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public int getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(int districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName == null ? null : districtName.trim();
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }
}