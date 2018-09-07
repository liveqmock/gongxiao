package com.yhglobal.gongxiao.foudation.model;

/**
 * 主数据获得项目
 *
 * @author: 陈浩
 **/
public class Project {

    private String code;    //项目编码
    private String name;    //项目名称
    private String eName;
    private String parentCode;
    private String orgCode; //公司主体编码
    private String orgName; //公司主体名称

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
