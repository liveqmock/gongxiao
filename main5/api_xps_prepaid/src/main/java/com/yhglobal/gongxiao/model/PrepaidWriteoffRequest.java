package com.yhglobal.gongxiao.model;

import java.util.List;

/**
 * @author 王帅
 */
public class PrepaidWriteoffRequest {

    private String channelId;
    private String channelToken;
    private String userId;
    private String userName;
    private Long projectId ;
    private String useDate ;
    private Integer accountType;
    private String confirmInfoJson ;
    private String projectName;
    private String philipDocumentNo;

    private List<WriteOffReturnObject> list;

    public List<WriteOffReturnObject> getList() {
        return list;
    }

    public void setList(List<WriteOffReturnObject> list) {
        this.list = list;
    }

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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelToken() {
        return channelToken;
    }

    public void setChannelToken(String channelToken) {
        this.channelToken = channelToken;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getConfirmInfoJson() {
        return confirmInfoJson;
    }

    public void setConfirmInfoJson(String confirmInfoJson) {
        this.confirmInfoJson = confirmInfoJson;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPhilipDocumentNo() {
        return philipDocumentNo;
    }

    public void setPhilipDocumentNo(String philipDocumentNo) {
        this.philipDocumentNo = philipDocumentNo;
    }
}
