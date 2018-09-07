package com.yhglobal.gongxiao.foundation.distributor.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 越海portal用户模型
 */
public class YhPortalUser implements Serializable {

    private String userId;//yhglobal用户Id

    private String userName;//用户名字

    private String passwd;//用户密码

    private int userStatus;// 用户状态 0:初始化未启用 1:正常使用 8:已冻结 9:已废弃',

    private String phoneNumber;//联系电话

    private int isAdmin;//是否管理员

    private  String roleInfo;//该用户拥有的角色 JSON格式

    private String tracelog;//操作日记

    private Date createTime;//创建时间

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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(String roleInfo) {
        this.roleInfo = roleInfo;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
