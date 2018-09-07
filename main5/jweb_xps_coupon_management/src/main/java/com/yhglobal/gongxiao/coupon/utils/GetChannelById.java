package com.yhglobal.gongxiao.coupon.utils;

import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;

/**
 * @author 王帅
 */
public class GetChannelById {
    // 通用的根据channelId 获取channel对象的工具类
//    public static ChannelServiceStructure.FoundationXpsSourceChannel getChannel(String xpsChannelId) {
//        ChannelServiceStructure.GetChannelByChannelIdReq.Builder reqBuilder = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder();
//        reqBuilder.setXpsChannelId(xpsChannelId);
//        ChannelServiceGrpc.ChannelServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
//        ChannelServiceStructure.GetChannelByChannelIdResp channel = rpcStub.getChannelByChannelId(reqBuilder.build());
//        ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = channel.getSourceChannel();
//        return sourceChannel;
//    }
}
