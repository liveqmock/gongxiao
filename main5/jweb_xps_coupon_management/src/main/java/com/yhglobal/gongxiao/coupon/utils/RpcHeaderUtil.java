package com.yhglobal.gongxiao.coupon.utils;


import com.yhglobal.gongxiao.coupon.model.RpcHeader;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

public class RpcHeaderUtil {

    public static RpcHeader createRpcHeader(String traceId, String projectId, String uid, String username) {
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setTraceId(traceId);
        rpcHeader.setProjectId(projectId);
        rpcHeader.setUid(uid);
        rpcHeader.setUsername(username);
        return rpcHeader;
    }

    public static GongxiaoRpc.RpcHeader createRpcHeader(String traceId, String uid, String username) {
        GongxiaoRpc.RpcHeader.Builder builder = GongxiaoRpc.RpcHeader.newBuilder();
        builder.setTraceId(traceId);
        builder.setUid(uid);
        builder.setUsername(username);
        return builder.build();
    }

    public static GongxiaoRpc.RpcHeader createGRpcHeader(String traceId, Integer projectId,String uid, String username) {
        GongxiaoRpc.RpcHeader.Builder builder = GongxiaoRpc.RpcHeader.newBuilder();
        builder.setTraceId(traceId);
        builder.setUid(uid);
        builder.setUsername(username);
        return builder.build();
    }

}
