package com.yhglobal.gongxiao.wmscomfirm.service;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.outstockconfirm.Data;
import com.yhglobal.gongxiao.wmsconfirm.WmsConfirmServiceGrpc;
import com.yhglobal.gongxiao.wmsconfirm.WmsConfirmStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 收到WMS出库确认信息后，通知仓储模块
 */
public class NotifyWarehouseOutboundTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(NotifyWarehouseOutboundTask.class);
    private GongxiaoRpc.RpcHeader rpcHeader;
    private Data outStockConfirmRequest; //wms反馈的出库信息

    public NotifyWarehouseOutboundTask(GongxiaoRpc.RpcHeader rpcHeader, Data outStockConfirmRequest) {
        this.rpcHeader = rpcHeader;
        this.outStockConfirmRequest = outStockConfirmRequest;
    }

    @Override
    public void run() {
        logger.info("[IN][run] start NotifyWarehouseOutboundTask: wmsOutStockConfirmRequest={}", JSON.toJSONString(outStockConfirmRequest));

        try {
            //grpc调用仓储模块
            WmsConfirmServiceGrpc.WmsConfirmServiceBlockingStub wmsConfirmService = WarehouseRpcStubStore.getRpcStub(WmsConfirmServiceGrpc.WmsConfirmServiceBlockingStub.class);
            WmsConfirmStructure.OutStockConfirmRequest outStockRequest = WmsConfirmStructure.OutStockConfirmRequest.newBuilder().setOutStockRequest(JSON.toJSONString(outStockConfirmRequest)).build();
            wmsConfirmService.confirmWmsOutboundInfo(outStockRequest);
            logger.info("#traceId={}# [OUT] get NotifyWarehouseOutboundTask success",rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#OrderNo=" + outStockConfirmRequest.getOrderNo() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

        logger.info("[OUT] get NotifyWarehouseOutboundTask success",rpcHeader.getTraceId());
    }
}
