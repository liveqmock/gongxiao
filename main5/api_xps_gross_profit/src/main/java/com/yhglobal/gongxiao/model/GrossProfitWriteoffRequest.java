package com.yhglobal.gongxiao.model;

import java.util.List;

/**
 * @author 王帅
 */
public class GrossProfitWriteoffRequest {

    private String channelId;
    private String channelToken;
    private String userId;
    private String userName;
    private Long projectId ;
    private String useDate ;
    private Byte transferIntoPattern;
    private String currencyCode;
    private Byte differenceAmountAdjustPattern;
    private String confirmInfoJson;

    private String flowNo;
    private List<WriteOffReturnObject> list;

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public List<WriteOffReturnObject> getList() {
        return list;
    }

    public void setList(List<WriteOffReturnObject> list) {
        this.list = list;
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

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public Byte getTransferIntoPattern() {
        return transferIntoPattern;
    }

    public void setTransferIntoPattern(Byte transferIntoPattern) {
        this.transferIntoPattern = transferIntoPattern;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Byte getDifferenceAmountAdjustPattern() {
        return differenceAmountAdjustPattern;
    }

    public void setDifferenceAmountAdjustPattern(Byte differenceAmountAdjustPattern) {
        this.differenceAmountAdjustPattern = differenceAmountAdjustPattern;
    }

    public String getConfirmInfoJson() {
        return confirmInfoJson;
    }

    public void setConfirmInfoJson(String confirmInfoJson) {
        this.confirmInfoJson = confirmInfoJson;
    }
}
