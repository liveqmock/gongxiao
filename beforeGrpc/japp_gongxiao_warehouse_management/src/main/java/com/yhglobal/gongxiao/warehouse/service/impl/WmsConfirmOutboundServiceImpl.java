package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.inventory.service.InventorySyncService;
import com.yhglobal.gongxiao.sales.service.SalesScheduleDeliveryService;
import com.yhglobal.gongxiao.warehouse.service.WmsConfirmOutboundService;
import com.yhglobal.gongxiao.warehouse.task.*;
import com.yhglobal.gongxiao.warehousemanagement.dao.*;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;

/**
 * WMS出库单确认接口
 */
@Service
public class WmsConfirmOutboundServiceImpl implements WmsConfirmOutboundService {

    private static Logger logger = LoggerFactory.getLogger(WmsConfirmOutboundServiceImpl.class);

    @Autowired
    OutBoundOrderDao outBoundOrderDao;

    @Autowired
    OutBoundOrderItemDao outBoundOrderItemDao;

    @Autowired
    ManualOutboundDao manualOutboundDao;

    @Autowired
    ManualOutboundItemDao manualOutboundItemDao;

    @Autowired
    WmsOutboundDao wmsOutboundDao;

    @Autowired
    WmsOutboundItemDao wmsOutboundItemDao;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Reference(check = false)
    ProjectService projectService;

    @Reference(check = false)
    ProductService productService;

    @Reference(check = false)
    WarehouseService warehouseService;

    @Reference(check = false)
    SalesScheduleDeliveryService salesScheduleDeliveryService;

    @Reference(check = false)
    InventorySyncService inventorySyncService;


    @Override
    public void confirmWmsOutboundInfo(Data outStockConfirmRequest) {
        RpcHeader rpcHeader = new RpcHeader();
        Date traceId = new Date();
        rpcHeader.setTraceId(String.valueOf(traceId.getTime()));
        rpcHeader.setUid("WMS");
        rpcHeader.setUsername("WMS系统");
        logger.info("#traceId={}# [IN][confirmWmsOutboundInfo] params: wmsOutStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(outStockConfirmRequest));

        String gongxiaoOutboundOrderNo = outStockConfirmRequest.getOrderNo();

        OutboundOrder olderRecord = outBoundOrderDao.getOutboundRecordByGoxiaoOutNo(gongxiaoOutboundOrderNo);
        String projectId = olderRecord.getProjectId();
        String batchNo = olderRecord.getBatchNo();
        String warehouseId = olderRecord.getWarehouseId();

        //1. 记录WMS系统出库确认信息 写流水
        SyncWmsOutboundInfoToDBTask syncToDBTask = new SyncWmsOutboundInfoToDBTask(rpcHeader, outStockConfirmRequest, olderRecord, outBoundOrderItemDao, wmsOutboundDao, wmsOutboundItemDao, productService);
        threadPoolTaskExecutor.submit(syncToDBTask);

        /**
         * 2. 根据WMS系统出库确认信息 更新<仓储模块>的出库单
         * 注: WMS的信息中本身不带有批次信息 故在SyncWmsOutboundInfoToStockTask会进行批次分配
         *     所有需要批次信息的任务 则放到SyncWmsOutboundInfoToStockTask任务中
         */
        SyncWmsOutboundInfoToStockTask syncToStockTask = new SyncWmsOutboundInfoToStockTask(rpcHeader, productService, outStockConfirmRequest,olderRecord,outBoundOrderDao,outBoundOrderItemDao,salesScheduleDeliveryService,inventorySyncService);
        threadPoolTaskExecutor.submit(syncToStockTask);

        /**
         * 3. 往上游反馈
         *
         * 注1: 若是销售出库 由于反馈时需要批次号 在SyncWmsOutboundInfoToStockTask任务中反馈到销售模块
         * 注2: 同步给EAS由定时任务来发起
         */
        if (outStockConfirmRequest.getOrderNo().startsWith(BizNumberType.STOCK_POOUT_ORDER_NO.getPrefix())) { //若是采购退货出库 则反馈给<采购模块>
            SyncWmsOutboundInfoToPurchaseTask syncToPurchaseTask = new SyncWmsOutboundInfoToPurchaseTask(rpcHeader, ApplicationContextProvider.getApplicationContext(), outStockConfirmRequest,projectId,olderRecord);
            threadPoolTaskExecutor.submit(syncToPurchaseTask);
        } else if (outStockConfirmRequest.getOrderNo().startsWith(BizNumberType.STOCK_TSF_OUT_NO.getPrefix())){ //若是调拨出库 则反馈给调拨
            SyncWmsOutboundInfoToAllocateTask syncToAllocateTask = new SyncWmsOutboundInfoToAllocateTask(rpcHeader,ApplicationContextProvider.getApplicationContext(),outStockConfirmRequest);
            threadPoolTaskExecutor.submit(syncToAllocateTask);
        } else if (outStockConfirmRequest.getOrderNo().startsWith(BizNumberType.STOCK_OTHER_OUT_NO.getPrefix())){ //若果是其他出库 则反馈给其他出库
            SyncWmsOutboundInfoToOtherTask syncToOtherTask = new SyncWmsOutboundInfoToOtherTask(rpcHeader,manualOutboundDao,manualOutboundItemDao,outStockConfirmRequest,projectId,batchNo,warehouseId,projectService,productService,warehouseService,olderRecord);
            threadPoolTaskExecutor.submit(syncToOtherTask);
        }

        logger.info("#traceId={}# [OUT][confirmWmsOutboundInfo] params: wmsOutStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(outStockConfirmRequest));

    }

}
