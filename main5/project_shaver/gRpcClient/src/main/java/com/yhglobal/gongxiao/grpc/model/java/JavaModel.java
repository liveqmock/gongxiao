package com.yhglobal.gongxiao.grpc.model.java;

import java.util.List;
import java.util.Map;

public class JavaModel {

    String traceId;
    int projectId;
    boolean isDeleted;
    byte[] body;
    List<JavaInnerModel> innerModelList;
    Map<String, JavaInnerModel> mapping;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public List<JavaInnerModel> getInnerModelList() {
        return innerModelList;
    }

    public void setInnerModelList(List<JavaInnerModel> innerModelList) {
        this.innerModelList = innerModelList;
    }

    public Map<String, JavaInnerModel> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, JavaInnerModel> mapping) {
        this.mapping = mapping;
    }

}
