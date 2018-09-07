package com.yhglobal.gongxiao.model;

import java.io.Serializable;

public class RpcHeader implements Serializable {

    public String traceId;  //请求追踪id
    public String uid;  //用户id
    public String username;  //用户名字

    public RpcHeader() {
    }

    public RpcHeader(String traceId) {
        this.traceId = traceId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
