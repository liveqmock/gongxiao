package com.yhglobal.gongxiao.model;

/**
 * @author 王帅
 */
public class PrepaidReceiveCancelConfirmBatchRequest {

    private String channelId;
    private String channelToken;
    private String userId;
    private String userName;
    private Long projectId;
    private String flowCodes;

    private WriteOffReturnObject object;
    private String flow;

    public WriteOffReturnObject getObject() {
        return object;
    }

    public void setObject(WriteOffReturnObject object) {
        this.object = object;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public String getFlowCodes() {
        return flowCodes;
    }

    public void setFlowCodes(String flowCodes) {
        this.flowCodes = flowCodes;
    }
}
