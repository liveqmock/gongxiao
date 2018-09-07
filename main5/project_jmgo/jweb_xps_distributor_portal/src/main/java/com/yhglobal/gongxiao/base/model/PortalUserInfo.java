package com.yhglobal.gongxiao.base.model;

public class PortalUserInfo {

    private String userId;  //portal用户Id

    private String userName;  //用户名字

    private String ipaddr;  //用户ip

    private long projectId; //该用户当前所属的projectId（可能会对应多个project?)

    private long distributorId; //该用户归属的AD id

    private String distributorName; //该用户归属的AD名字

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
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

}
