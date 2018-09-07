package com.yhglobal.gongxiao.foundation.portal.user.model;

import java.io.Serializable;
import java.util.Date;

public class DistributorPortalUser implements Serializable{

    private String userId;

    private String userName;

    private String passwd;

    private Byte userStatus;

    private Long projectId;

    private String projectName;

    private Long distributorId;

    private String distributorName;

    private String phoneNumber;

    private Byte isAdmin;

    private String roleInfo;

    private String tracelog;

    private Long createdById;

    private String createdByName;

    private Date createTime;


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
        this.userName = (userName == null) ? null : userName.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = (passwd == null) ? null : passwd.trim();
    }

    public Byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
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
        this.projectName = (projectName == null) ? null : projectName.trim();
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = (distributorName == null) ? null : distributorName.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = (phoneNumber == null) ? null : phoneNumber.trim();
    }

    public Byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(String roleInfo) {
        this.roleInfo = (roleInfo == null) ? null : roleInfo.trim();
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = (tracelog == null) ? null : tracelog.trim();
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
        this.createdByName = (createdByName == null) ? null : createdByName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DistributorPortalUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", passwd='" + passwd + '\'' +
                ", userStatus=" + userStatus +
                ", projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", distributorId=" + distributorId +
                ", distributorName='" + distributorName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isAdmin=" + isAdmin +
                ", roleInfo='" + roleInfo + '\'' +
                ", tracelog='" + tracelog + '\'' +
                ", createdById=" + createdById +
                ", createdByName='" + createdByName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}