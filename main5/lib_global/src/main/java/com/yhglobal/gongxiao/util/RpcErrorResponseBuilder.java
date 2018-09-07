package com.yhglobal.gongxiao.util;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

public class RpcErrorResponseBuilder {

    //根据错误的枚举类型设置返回值 此时msg取值为枚举值中的错误信息
    public static <T extends Message.Builder> void buildWithEnumError(T builder, Descriptors.Descriptor descriptor, ErrorCode errorCode) {
        buildWithErrorCodeAndMsg(builder, descriptor, errorCode.getErrorCode(), errorCode.getMessage());
    }

    //定制返回去的错误信息时用这个接口(比如: 携带关键的参数信息)
    public static <T extends Message.Builder> void buildWithErrorCodeAndMsg(T builder,
                                                                            Descriptors.Descriptor descriptor,
                                                                            int errorCode,
                                                                            String errorMsg) {
        String returnCodeFieldName = "returnCode";
        String msgFieldName = "msg";
        Descriptors.FieldDescriptor fd;

        fd = descriptor.findFieldByName(returnCodeFieldName);
        builder.setField(fd, errorCode);

        if (errorMsg != null) {
            fd = descriptor.findFieldByName(msgFieldName);
            builder.setField(fd, errorMsg);
        }

    }

    public static void main(String[] args) {
        GongxiaoRpc.RpcResult.Builder respBuilder = GongxiaoRpc.RpcResult.newBuilder(); //每个proto对象都需要从builder构建出来
        Descriptors.Descriptor desc = GongxiaoRpc.RpcResult.getDescriptor();
        RpcErrorResponseBuilder.buildWithErrorCodeAndMsg(respBuilder, desc, 20, "haha");
        System.out.println(respBuilder.build());
    }

}
