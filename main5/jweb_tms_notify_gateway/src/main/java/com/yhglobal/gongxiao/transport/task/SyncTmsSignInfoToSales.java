package com.yhglobal.gongxiao.transport.task;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.transport.channel.TransportationChannelDao;
import com.yhglobal.gongxiao.transport.common.dao.TransportationOutboundOrderDao;
import com.yhglobal.gongxiao.transport.model.TransportationOutboundOrder;
import com.yhglobal.gongxiao.transportataion.eventnotification.XpsTransportationNotifyManager;
import com.yhglobal.gongxiao.transportataion.sendtotransportation.sales.model.SignInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: 葛灿
 */
public class SyncTmsSignInfoToSales implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SyncTmsSignInfoToSales.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private TransportationOutboundOrderDao outboundOrderDao;

    private TransportationChannelDao transportationChannelDao;

    private SignInfo signInfo;

    public SyncTmsSignInfoToSales(TransportationOutboundOrderDao outboundOrderDao,
                                  TransportationChannelDao transportationChannelDao,
                                  GongxiaoRpc.RpcHeader rpcHeader,
                                  SignInfo signInfo) {
        this.transportationChannelDao = transportationChannelDao;
        this.outboundOrderDao = outboundOrderDao;
        this.rpcHeader = rpcHeader;
        this.signInfo = signInfo;
    }

    @Override
    public void run() {
        try {
            logger.info("#traceId={}# [IN][SyncTmsSignInfoToSales] synchronize start: ", rpcHeader.getTraceId());
            // 根据单号截取项目表名前缀
            String custOrdNo = signInfo.getCustOrdNo();
            String prefix;
            String channelUrl;
            if (custOrdNo.startsWith(BizNumberType.STOCK_SOOUT_ORDER_NO.getPrefix())) {
                // 如果是旧版本的单号,锁定项目为小家电
                prefix = "shaver";
                signInfo.setProjectId(146798161);
                channelUrl = "http://127.0.0.1:12010";
            } else {
                String[] split = custOrdNo.split("_");
                prefix = split[1];
                TransportationOutboundOrder outboundOrder = outboundOrderDao.getOrderByOrderNo(prefix, custOrdNo);
                if (outboundOrder == null) {
                    throw new RuntimeException("order NOT found. orderNo=" + custOrdNo);
                }
                signInfo.setProjectId(outboundOrder.getProjectId());
                long channelId = outboundOrder.getChannelId();
                //调用基础模块的SourceChannel服务
                ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
                ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setXpsChannelId(channelId + "").build();
                ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
                ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
                channelUrl = sourceChannel.getXpsTransportationNotifyUrl();
            }
            logger.info("channelUrl={}", channelUrl);
            GongxiaoResult gongxiaoResult = XpsTransportationNotifyManager.salesOutboundSigned(channelUrl, signInfo);
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
