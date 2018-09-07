package com.yhglobal.gongxiao.util;

import com.yhglobal.gongxiao.common.RpcHeader;

public class RpcHeaderUtil {

    public static RpcHeader createRpcHeader(String traceId, String uid, String username) {
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setTraceId(traceId);
        rpcHeader.setUid(uid);
        rpcHeader.setUsername(username);
        return rpcHeader;
    }

}
