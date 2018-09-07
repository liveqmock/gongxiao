package com.yhglobal.gongxiao.model;

/**
 * @author 王帅
 */
public class SearchPrepaidConfirmRequest {
    private String channelId;
    private String channelToken;
    private String userId;
    private String userName;
    private Long projectId;
    private String flowCode;
    private Integer accountType;
    private String useDateStart;
    private String useDateEnd;
    private String dateStart;
    private String dateEnd;
    private Integer pageNumber;
    private Integer pageSize;

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

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getUseDateStart() {
        return useDateStart;
    }

    public void setUseDateStart(String useDateStart) {
        this.useDateStart = useDateStart;
    }

    public String getUseDateEnd() {
        return useDateEnd;
    }

    public void setUseDateEnd(String useDateEnd) {
        this.useDateEnd = useDateEnd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
