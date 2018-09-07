package com.yhglobal.gongxiao.wmscomfirm.service;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.instockconfirm.Data;
import com.yhglobal.gongxiao.wmsconfirm.WmsConfirmServiceGrpc;
import com.yhglobal.gongxiao.wmsconfirm.WmsConfirmStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 收到WMS入库确认信息后，通知仓储模块
 */
public class NotifyWarehouseInboundTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(NotifyWarehouseInboundTask.class);

    private Data inStockConfirmRequest;

    public NotifyWarehouseInboundTask(Data inStockConfirmRequest) {
        this.inStockConfirmRequest = inStockConfirmRequest;
    }

    @Override
    public void run() {
        logger.info("[IN][run] start to NotifyWarehouseInboundTask params: inStockConfirmRequest={}", JSON.toJSONString(inStockConfirmRequest));

        //调用仓储模块
        try {
            WmsConfirmServiceGrpc.WmsConfirmServiceBlockingStub wmsConfirmService = WarehouseRpcStubStore.getRpcStub(WmsConfirmServiceGrpc.WmsConfirmServiceBlockingStub.class);
            WmsConfirmStructure.InStockConfirmRequest inStockRequest = WmsConfirmStructure.InStockConfirmRequest.newBuilder().setInStockRequest(JSON.toJSONString(inStockConfirmRequest)).build();
            wmsConfirmService.confirmWmsInboundInfo(inStockRequest);
        } catch (Exception e) {
            logger.error("#OrderNo=" + inStockConfirmRequest.getOrderNo() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

        logger.info("#traceId={}# [OUT] get NotifyWarehouseInboundTask.run() to recordWmsSystemInfo success");
    }
}
