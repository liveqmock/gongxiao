package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.*;
import com.yhglobal.gongxiao.warehouse.model.InboundOrder;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrder;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.instockconfirm.Data;
import com.yhglobal.gongxiao.warehouse.service.AllocationService;
import com.yhglobal.gongxiao.warehouse.service.ModifyInboundService;
import com.yhglobal.gongxiao.warehouse.task.*;
import com.yhglobal.gongxiao.wmsconfirm.WmsConfirmServiceGrpc;
import com.yhglobal.gongxiao.wmsconfirm.WmsConfirmStructure;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;


/**
 * WMS系统入库单回告 确认接口
 */
@Service
public class WmsConfirmServiceImpl extends WmsConfirmServiceGrpc.WmsConfirmServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(WmsConfirmServiceImpl.class);

    @Autowired
    InBoundOrderDao inBoundOrderDao;

    @Autowired
    InboundOrderItemDao inboundOrderItemDao;

    @Autowired
    OutBoundOrderDao outBoundOrderDao;

    @Autowired
    OutBoundOrderItemDao outBoundOrderItemDao;

    @Autowired
    ManualInboudDao manualInboudDao;

    @Autowired
    ManualInboundItemDao manualInboundItemDao;

    @Autowired
    ManualOutboundDao manualOutboundDao;

    @Autowired
    ManualOutboundItemDao manualOutboundItemDao;
    @Autowired
    AllocateOrderDao allocateOrderDao;

    @Autowired
    AllocateOrderItemDao allocateOrderItemDao;

    @Autowired
    WarehouseConfig warehouseConfig;

    @Autowired
    ModifyInboundService modifyInboundService;

    @Autowired
    AllocationService allocationService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void confirmWmsInboundInfo(WmsConfirmStructure.InStockConfirmRequest request, StreamObserver<WmsConfirmStructure.ConfirmResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setUid("").setTraceId("0001").setUsername("wms").build();
        String inStockConfirmRequestJson = request.getInStockRequest();
        WmsConfirmStructure.ConfirmResult response; //保存返回的值
        WmsConfirmStructure.ConfirmResult.Builder respBuilder = WmsConfirmStructure.ConfirmResult.newBuilder(); //每个proto对象都需要从builder构建出来
        logger.info("[IN][confirmWmsInboundInfo] params: inStockConfirmRequestJson={}", inStockConfirmRequestJson);
        Data inStockConfirmRequest = JSON.parseObject(inStockConfirmRequestJson, Data.class);
        String gongxiaoInboundOrderNo = inStockConfirmRequest.getOrderNo();

        String[] orderNoArg = gongxiaoInboundOrderNo.split("_");
        String projectPrefix = orderNoArg[1];
        InboundOrder stockRecord = inBoundOrderDao.getRecordByInBoundNo(gongxiaoInboundOrderNo, projectPrefix);  //获取对应的入库单
        String projectId = String.valueOf(stockRecord.getProjectId());
        String batchNo = stockRecord.getBatchNo();
        String purchaseOrderNo = stockRecord.getPurchaseOrderNo();
        String warehouseId = stockRecord.getWarehouseId();

        //更新<仓储模块>中入库汇总单和明细单，以及通知EAS，通知采购模块
        SyncWmsInboundInfoToStockManageTask syncToStockManageTask = new SyncWmsInboundInfoToStockManageTask(rpcHeader, inStockConfirmRequest, projectId, stockRecord, warehouseConfig, modifyInboundService);
        threadPoolTaskExecutor.submit(syncToStockManageTask);

        //wms的入库确认信息反馈给<库存中心模块>
        SyncWmsInboundInfoToInventoryTask syncToInventoryTask = new SyncWmsInboundInfoToInventoryTask(rpcHeader, stockRecord, inStockConfirmRequest, projectId, inboundOrderItemDao);
        threadPoolTaskExecutor.submit(syncToInventoryTask);

        //注: 若是采购退入库或者其他入库 已在syncToStockManageTask中处理
        if (inStockConfirmRequest.getOrderNo().contains(BizNumberType.STOCK_SOIN_ORDER_NO.getPrefix())) { //若是销售退货入库 则反馈给<销售模块>
            SyncWmsInboundInfoToSalesTask salesTask = new SyncWmsInboundInfoToSalesTask(rpcHeader, inStockConfirmRequest, warehouseConfig, stockRecord);
            threadPoolTaskExecutor.submit(salesTask);
        } else if (inStockConfirmRequest.getOrderNo().contains(BizNumberType.STOCK_TSF_IN_NO.getPrefix())) { //若是调拨入库 则反馈给调拨
            SyncWmsInboundInfoToAllocateTask allocateTask = new SyncWmsInboundInfoToAllocateTask(rpcHeader, inStockConfirmRequest, stockRecord, allocationService);
            threadPoolTaskExecutor.submit(allocateTask);
        } else if (inStockConfirmRequest.getOrderNo().contains(BizNumberType.STOCK_OTHER_IN_NO.getPrefix())) { //若是其他入库 则反馈给其他入库
            SyncWmsInboundInfoToOtherTask otherTask = new SyncWmsInboundInfoToOtherTask(rpcHeader, manualInboudDao, manualInboundItemDao, inStockConfirmRequest, projectId, batchNo, warehouseId);
            threadPoolTaskExecutor.submit(otherTask);
        } else {
            logger.error("# errorMessage: 没有对应的入库类型");
        }
        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();

    }

    @Override
    public void confirmWmsOutboundInfo(WmsConfirmStructure.OutStockConfirmRequest request, StreamObserver<WmsConfirmStructure.ConfirmResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setUid("").setTraceId("0001").setUsername("wms").build();
        String outStockConfirmRequestJson = request.getOutStockRequest();
        WmsConfirmStructure.ConfirmResult response; //保存返回的值
        WmsConfirmStructure.ConfirmResult.Builder respBuilder = WmsConfirmStructure.ConfirmResult.newBuilder(); //每个proto对象都需要从builder构建出来
        logger.info("[IN][confirmWmsOutboundInfo] params: outStockConfirmRequestJson={}", outStockConfirmRequestJson);
        com.yhglobal.gongxiao.warehouse.model.dto.wms.outstockconfirm.Data outStockConfirmRequest = JSON.parseObject(outStockConfirmRequestJson, com.yhglobal.gongxiao.warehouse.model.dto.wms.outstockconfirm.Data.class);
        String gongxiaoOutboundOrderNo = outStockConfirmRequest.getOrderNo();

        String projectPrefix;

        if (gongxiaoOutboundOrderNo.startsWith("SOOUT")) {     //为了兼容之前做的单，所以做了此判断（待删除）
            projectPrefix = "shaver";
        } else {
            String[] orderNoArg = gongxiaoOutboundOrderNo.split("_");
            projectPrefix = orderNoArg[1];
        }

        OutboundOrder olderRecord = outBoundOrderDao.getOutboundRecordByGoxiaoOutNo(gongxiaoOutboundOrderNo, projectPrefix);
        String projectId = olderRecord.getProjectId();
        String batchNo = olderRecord.getBatchNo();
        String warehouseId = olderRecord.getWarehouseId();

        /**
         * 2. 根据WMS系统出库确认信息 更新<仓储模块>的出库单
         * 注: WMS的信息中本身不带有批次信息 故在SyncWmsOutboundInfoToStockTask会进行批次分配
         *     所有需要批次信息的任务 则放到SyncWmsOutboundInfoToStockTask任务中
         */
        SyncWmsOutboundInfoToStockTask syncToStockTask = new SyncWmsOutboundInfoToStockTask(rpcHeader, outStockConfirmRequest, olderRecord, outBoundOrderDao, outBoundOrderItemDao, warehouseConfig);
        threadPoolTaskExecutor.submit(syncToStockTask);

        /**
         * 3. 往上游反馈
         *
         * 注1: 若是销售出库 由于反馈时需要批次号 在SyncWmsOutboundInfoToStockTask任务中反馈到销售模块
         * 注2: 同步给EAS由定时任务来发起
         */
        if (outStockConfirmRequest.getOrderNo().contains(BizNumberType.STOCK_POOUT_ORDER_NO.getPrefix())) { //若是采购退货出库 则反馈给<采购模块>
            SyncWmsOutboundInfoToPurchaseTask syncToPurchaseTask = new SyncWmsOutboundInfoToPurchaseTask(rpcHeader, outStockConfirmRequest, projectId, olderRecord);
            threadPoolTaskExecutor.submit(syncToPurchaseTask);
        } else if (outStockConfirmRequest.getOrderNo().contains(BizNumberType.STOCK_TSF_OUT_NO.getPrefix())) { //若是调拨出库 则反馈给调拨
            SyncWmsOutboundInfoToAllocateTask syncToAllocateTask = new SyncWmsOutboundInfoToAllocateTask(rpcHeader, outStockConfirmRequest, olderRecord, allocateOrderDao, allocateOrderItemDao, allocationService);
            threadPoolTaskExecutor.submit(syncToAllocateTask);
        } else if (outStockConfirmRequest.getOrderNo().contains(BizNumberType.STOCK_OTHER_OUT_NO.getPrefix())) { //若果是其他出库 则反馈给其他出库
            SyncWmsOutboundInfoToOtherTask syncToOtherTask = new SyncWmsOutboundInfoToOtherTask(rpcHeader, manualOutboundDao, manualOutboundItemDao, outStockConfirmRequest, projectId, batchNo, warehouseId, olderRecord);
            threadPoolTaskExecutor.submit(syncToOtherTask);
        }
        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
        logger.info("#traceId={}# [OUT][confirmWmsOutboundInfo] params: wmsOutStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(outStockConfirmRequest));

    }

}
