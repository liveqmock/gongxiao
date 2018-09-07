package com.yhglobal.gongxiao.foudation.model;

/**
 * item信息
 *
 * @author: 陈浩
 **/
public class ResultDataItem {
    //接口穿过来是大写开头
    private int RowId;
    private String Code;
    private String Name;
    private String HelpCode;
    private String NameEnglish;
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

    public String getHelpCode() {
        return HelpCode;
    }

    public void setHelpCode(String helpCode) {
        HelpCode = helpCode;
    }

    public String getNameEnglish() {
        return NameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        NameEnglish = nameEnglish;
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
