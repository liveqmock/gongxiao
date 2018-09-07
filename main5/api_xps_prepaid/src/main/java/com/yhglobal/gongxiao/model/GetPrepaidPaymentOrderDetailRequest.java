package com.yhglobal.gongxiao.model;

/**
 * @author 王帅
 */
public class GetPrepaidPaymentOrderDetailRequest {

    private String channelId;
    private String channelToken;
    private String userId;
    private String userName;
    private Integer id;

    private Long projectId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
