package com.yhglobal.gongxiao.tms.task;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.sales.service.SalesScheduleDeliveryService;
import com.yhglobal.gongxiao.tms.model.SignInfo;
import com.yhglobal.gongxiao.warehouse.service.OutboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author: 葛灿
 */
public class SyncTmsSignInfo implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SyncTmsSignInfo.class);


    private ApplicationContext applicationContext;

    private RpcHeader rpcHeader;

    private SalesScheduleDeliveryService salesScheduleDeliveryService;

    private SignInfo signInfo;

    public SyncTmsSignInfo(ApplicationContext applicationContext,
                           SalesScheduleDeliveryService salesScheduleDeliveryService,
                           RpcHeader rpcHeader,
                           SignInfo signInfo) {
        this.rpcHeader = rpcHeader;
        this.applicationContext = applicationContext;
        this.salesScheduleDeliveryService = salesScheduleDeliveryService;
        this.signInfo = signInfo;

    }

    @Override
    public void run() {
        try {
            logger.info("#traceId={}# [IN][SyncTmsSignInfo] synchronize start: ", rpcHeader.traceId);
            salesScheduleDeliveryService.outboundSigned(rpcHeader,
                    signInfo.getCustOrdNo(),
                    signInfo.getTmsOrdNo(),
                    signInfo.getRemark(),
                    signInfo.getSignedBy(),
                    signInfo.getPostedBy(),
                    signInfo.getSignedPhone(),
                    signInfo.getSignedTime(),
                    signInfo.getTransporter()
            );
            logger.info("#traceId={}# [OUT] synchronize finished: ", rpcHeader.traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }
    }
}
