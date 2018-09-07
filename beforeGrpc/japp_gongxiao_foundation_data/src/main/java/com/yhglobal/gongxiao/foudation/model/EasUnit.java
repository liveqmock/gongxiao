package com.yhglobal.gongxiao.foudation.model;

/**
 *eas单位
 *
 * @author: 陈浩
 **/
public class EasUnit {

    private int RowId;

    private String Code;

    private String Name;

    private String GroupId;

    private String FIsBaseUnit;

    private String Coefficient;

    private String Remark;

    private String Id;

    public int getRowId() {
        return RowId;
    }

    public void setRowId(int rowId) {
        RowId = rowId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getFIsBaseUnit() {
        return FIsBaseUnit;
    }

    public void setFIsBaseUnit(String FIsBaseUnit) {
        this.FIsBaseUnit = FIsBaseUnit;
    }

    public String getCoefficient() {
        return Coefficient;
    }

    public void setCoefficient(String coefficient) {
        Coefficient = coefficient;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
