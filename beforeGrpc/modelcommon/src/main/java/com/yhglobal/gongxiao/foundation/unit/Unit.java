package com.yhglobal.gongxiao.foundation.unit;

import java.io.Serializable;

public class Unit implements Serializable {
    private Long id;

    private String unitCode;

    private String unitName;

    private String easUnitCode;

    private String easUnitName;

    private Integer recordStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode == null ? null : unitCode.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getEasUnitCode() {
        return easUnitCode;
    }

    public void setEasUnitCode(String easUnitCode) {
        this.easUnitCode = easUnitCode == null ? null : easUnitCode.trim();
    }

    public String getEasUnitName() {
        return easUnitName;
    }

    public void setEasUnitName(String easUnitName) {
        this.easUnitName = easUnitName == null ? null : easUnitName.trim();
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }
}