package com.yhglobal.gongxiao.foundation.channel.impl;

import com.yhglobal.gongxiao.foundation.channel.dao.SourceChannelDao;
import com.yhglobal.gongxiao.foundation.channel.dao.mapper.FoundationXpsSourceChannelMapper;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.channel.model.FoundationXpsSourceChannel;
import com.yhglobal.gongxiao.foundation.currency.impl.CurrencyServiceImpl;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Service
public class SourceChannelServiceImpl extends ChannelServiceGrpc.ChannelServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(SourceChannelServiceImpl.class);

    @Autowired
    SourceChannelDao sourceChannelDao;

    //通过ChannelId获取channel信息
    public void getChannelByChannelId(ChannelServiceStructure.GetChannelByChannelIdReq request,
                                      StreamObserver<ChannelServiceStructure.GetChannelByChannelIdResp> responseObserver) {
        ChannelServiceStructure.GetChannelByChannelIdResp.Builder respBuilder = ChannelServiceStructure.GetChannelByChannelIdResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String xpsChannelId = request.getXpsChannelId();
        logger.info("#traceId={}# [IN][getChannelByChannelId] params: xpsChannelId={}", rpcHeader.getTraceId(), xpsChannelId);
        try {
            FoundationXpsSourceChannel sourceChannel = sourceChannelDao.getChannelById(xpsChannelId);
            if (sourceChannel == null){
                logger.warn("#traceId={}# [IN][getChannelByChannelId] not get channel", rpcHeader.getTraceId());
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            ChannelServiceStructure.FoundationXpsSourceChannel xpsSourceChannel = ChannelServiceStructure.FoundationXpsSourceChannel.newBuilder()
                    .setChannelStatus(sourceChannel.getChannelStatus())
                    .setDeleteFlag(sourceChannel.getDeleteFlag())
                    .setXpsProjectId(sourceChannel.getXpsProjectId())
                    .setXpsChannelId(sourceChannel.getXpsChannelId())
                    .setXpsChannelSecret(sourceChannel.getXpsChannelSecret())
                    .setXpsChannelName(sourceChannel.getXpsChannelName())
                    .setWmsCustCode(sourceChannel.getWmsCustCode())
                    .setWmsOrderSource(sourceChannel.getWmsOrderSource())
                    .setXpsWarehouseNotifyUrl(sourceChannel.getXpsWarehouseNotifyUrl())
                    .setTmsCustomerCode(sourceChannel.getTmsCustomerCode())
                    .setTmsCustomerName(sourceChannel.getTmsCustomerName())
                    .setTmsProjectName(sourceChannel.getTmsProjectName())
                    .setXpsTransportationNotifyUrl(sourceChannel.getXpsTransportationNotifyUrl())
                    .build();
            respBuilder.setSourceChannel(xpsSourceChannel);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][getChannelByChannelId] success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }

}
