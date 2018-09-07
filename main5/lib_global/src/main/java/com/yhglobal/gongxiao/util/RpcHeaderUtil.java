package com.yhglobal.gongxiao.util;


import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

public class RpcHeaderUtil {

    /*// TODO: 待去除
    public static GongxiaoRpc.RpcHeader createRpcHeader(String traceId, int projectId, String uid, String username) {
        GongxiaoRpc.RpcHeader.Builder builder = GongxiaoRpc.RpcHeader.newBuilder();
        builder.setTraceId(traceId);
        //builder.setProjectId(projectId);
        builder.setUid(uid);
        builder.setUsername(username);
        return builder.build();
    }
    //*/

    public static GongxiaoRpc.RpcHeader createRpcHeader(String traceId, String uid, String username) {
        GongxiaoRpc.RpcHeader.Builder builder = GongxiaoRpc.RpcHeader.newBuilder();
        builder.setTraceId(traceId);
        builder.setUid(uid);
        builder.setUsername(username);
        return builder.build();
    }

}
