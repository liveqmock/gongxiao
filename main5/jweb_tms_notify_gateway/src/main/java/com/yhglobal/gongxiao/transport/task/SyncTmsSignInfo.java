//package com.yhglobal.gongxiao.transport.task;
//
//import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
//import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
//import com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure;
//import com.yhglobal.gongxiao.sales.microservice.SalesScheduleDeliveryServiceGrpc;
//import com.yhglobal.gongxiao.transport.model.SignInfo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
//
///**
// * @author: 葛灿
// */
//public class SyncTmsSignInfo implements Runnable {
//
//    private static final Logger logger = LoggerFactory.getLogger(SyncTmsSignInfo.class);
//
//
//    private ApplicationContext applicationContext;
//
//    private GongxiaoRpc.RpcHeader rpcHeader;
//
//
//    private SignInfo signInfo;
//
//    public SyncTmsSignInfo(ApplicationContext applicationContext,
//                           GongxiaoRpc.RpcHeader rpcHeader,
//                           SignInfo signInfo) {
//        this.rpcHeader = rpcHeader;
//        this.applicationContext = applicationContext;
//        this.signInfo = signInfo;
//
//    }
//
//    @Override
//    public void run() {
//        try {
//            logger.info("#traceId={}# [IN][SyncTmsSignInfo] synchronize start: ", rpcHeader.getTraceId());
//
//            DeliverScheduleStructure.OutboundSignedRequest outboundSignedRequest = DeliverScheduleStructure.OutboundSignedRequest.newBuilder()
//                    .setRpcHeader(rpcHeader)
//                    .setOutboundOrderNo(signInfo.getCustOrdNo())
//                    .setTmsOrderNo(signInfo.getCustOrdNo())
//                    .setTmsRemark(signInfo.getRemark())
//                    .setSignedBy(signInfo.getSignedBy())
//                    .setPostedBy(signInfo.getPostedBy())
//                    .setSignedPhone(signInfo.getSignedPhone())
//                    .setSignedTime(signInfo.getSignedTime().getTime())
//                    .setTransporter(signInfo.getTransporter()).build();
//            SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub salesScheduleDeliveryService = RpcStubStore.getRpcStub(SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub.class);
//            GongxiaoRpc.RpcResult rpcResult = salesScheduleDeliveryService.salesOutboundSigned(outboundSignedRequest);
//            if (rpcResult.getReturnCode() == 0) {
//                logger.info("#traceId={}# [OUT] synchronize success. ", rpcHeader.getTraceId());
//            } else {
//                logger.info("#traceId={}# [OUT] synchronize failed: {} ", rpcHeader.getTraceId(), rpcResult.getMsg());
//            }
//        } catch (Exception e) {
//            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
//        }
//    }
//}
