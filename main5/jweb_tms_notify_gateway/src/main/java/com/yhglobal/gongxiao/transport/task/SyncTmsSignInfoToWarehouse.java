package com.yhglobal.gongxiao.transport.task;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.transport.config.PortalConfig;
import com.yhglobal.gongxiao.transportataion.sendtotransportation.sales.model.SignInfo;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: 葛灿
 */
public class SyncTmsSignInfoToWarehouse implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SyncTmsSignInfoToWarehouse.class);


    private GongxiaoRpc.RpcHeader rpcHeader;


    private SignInfo signInfo;

    private PortalConfig portalConfig;

    public SyncTmsSignInfoToWarehouse(GongxiaoRpc.RpcHeader rpcHeader,
                                      PortalConfig portalConfig,
                                      SignInfo signInfo) {
        this.rpcHeader = rpcHeader;
        this.portalConfig = portalConfig;
        this.signInfo = signInfo;

    }

    @Override
    public void run() {
        try {
            logger.info("#traceId={}# [IN][SyncTmsSignInfoToWarehouse] synchronize start: ", rpcHeader.getTraceId());

            // 根据channelId查询channelToken
            String channelId = portalConfig.getChannelId();
            //调用基础模块的SourceChannel服务
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(channelId + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();


            GongxiaoResult gongxiaoResult = XpsWarehouseManager.sureSighIn(portalConfig.getWarehouseUrl(),
                    channelId, xpsChannelSecret,
                    signInfo.getTransporter(), signInfo.getCustOrdNo(),
                    signInfo.getTmsOrdNo(), signInfo.getRemark(),
                    signInfo.getSignedBy(), signInfo.getPostedBy(),
                    signInfo.getSignedPhone(), signInfo.getSignedTime());
            if (gongxiaoResult.getReturnCode() == 0) {
                logger.info("#traceId={}# [OUT] synchronize success. ", rpcHeader.getTraceId());
            } else {
                logger.info("#traceId={}# [OUT] synchronize failed: {} ", rpcHeader.getTraceId(), gongxiaoResult.getMessage());
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }
    }
}
