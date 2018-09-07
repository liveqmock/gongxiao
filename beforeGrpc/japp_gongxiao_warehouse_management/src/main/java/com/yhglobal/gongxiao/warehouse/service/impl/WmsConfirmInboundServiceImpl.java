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
import com.yhglobal.gongxiao.model.PurchaseOrder;
import com.yhglobal.gongxiao.sales.service.SalesReturnService;
import com.yhglobal.gongxiao.warehouse.service.InboundNotificationService;
import com.yhglobal.gongxiao.warehouse.service.WmsConfirmInboundService;
import com.yhglobal.gongxiao.warehouse.task.*;
import com.yhglobal.gongxiao.warehousemanagement.dao.*;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;

/**
 * WMS系统入库单回告 确认接口
 */
@Service
public class WmsConfirmInboundServiceImpl implements WmsConfirmInboundService {

    private static Logger logger = LoggerFactory.getLogger(WmsConfirmInboundServiceImpl.class);

    @Autowired
    InBoundOrderDao inBoundOrderDao;

    @Autowired
    InboundOrderItemDao inboundOrderItemDao;

    @Autowired
    ManualInboudDao manualInboudDao;

    @Autowired
    ManualInboundItemDao manualInboundItemDao;

    @Autowired
    WmsInboundDao wmsInboundDao;

    @Autowired
    WmsInboundItemDao wmsInboundItemDao;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Reference(check = false)
    InventorySyncService inventorySyncService;

    @Reference(check = false)
    InboundNotificationService inboundNotificationService;

    @Reference(check = false)
    SalesReturnService salesReturnService;

    @Reference(check = false)
    ProjectService projectService;

    @Reference(check = false)
    WarehouseService warehouseService;

    @Reference(check = false)
    ProductService productService;

    @Override
    public void confirmWmsInboundInfo(RpcHeader rpcHeader,Data inStockConfirmRequest) {

        logger.info("#traceId={}# [IN][confirmWmsInboundInfo] params: wmsInStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(inStockConfirmRequest));

        String gongxiaoInboundOrderNo = inStockConfirmRequest.getOrderNo();

        InboundOrder stockRecord = inBoundOrderDao.getRecordByInBoundNo(gongxiaoInboundOrderNo);  //获取对应的入库单
        String projectId = String.valueOf(stockRecord.getProjectId());
        String batchNo = stockRecord.getBatchNo();
        String purchaseOrderNo = stockRecord.getPurchaseOrderNo();
        String warehouseId = stockRecord.getWarehouseId();

        //记录WMS入库反馈流水
        SyncWmsInboundInfoToDBTask syncToDBTask = new SyncWmsInboundInfoToDBTask(rpcHeader, inStockConfirmRequest, stockRecord, wmsInboundDao, wmsInboundItemDao, productService);
        threadPoolTaskExecutor.submit(syncToDBTask);

        //更新<仓储模块>中入库汇总单和明细单，以及通知EAS，通知采购模块
        SyncWmsInboundInfoToStockManageTask syncToStockManageTask = new SyncWmsInboundInfoToStockManageTask(rpcHeader, ApplicationContextProvider.getApplicationContext(), inStockConfirmRequest, projectId, purchaseOrderNo,productService,stockRecord,inboundNotificationService);
        threadPoolTaskExecutor.submit(syncToStockManageTask);

        //wms的入库确认信息反馈给<库存中心模块>
        SyncWmsInboundInfoToInventoryTask syncToInventoryTask = new SyncWmsInboundInfoToInventoryTask(rpcHeader, inventorySyncService, stockRecord,inStockConfirmRequest, projectId,purchaseOrderNo,productService);
        threadPoolTaskExecutor.submit(syncToInventoryTask);

        //注: 若是采购退入库或者其他入库 已在syncToStockManageTask中处理
        if (inStockConfirmRequest.getOrderNo().startsWith(BizNumberType.STOCK_SOIN_ORDER_NO.getPrefix())) { //若是销售退货入库 则反馈给<销售模块>
            SyncWmsInboundInfoToSalesTask task5 = new SyncWmsInboundInfoToSalesTask(rpcHeader,inStockConfirmRequest,productService,salesReturnService);
            threadPoolTaskExecutor.submit(task5);
        } else if (inStockConfirmRequest.getOrderNo().startsWith(BizNumberType.STOCK_TSF_IN_NO.getPrefix())) { //若是调拨入库 则反馈给调拨
            SyncWmsInboundInfoToAllocateTask task6 = new SyncWmsInboundInfoToAllocateTask(rpcHeader, ApplicationContextProvider.getApplicationContext(),inStockConfirmRequest,productService);
            threadPoolTaskExecutor.submit(task6);
        } else if (inStockConfirmRequest.getOrderNo().startsWith(BizNumberType.STOCK_OTHER_IN_NO.getPrefix())) { //若是其他入库 则反馈给其他入库
            SyncWmsInboundInfoToOtherTask task7 = new SyncWmsInboundInfoToOtherTask(rpcHeader,manualInboudDao,manualInboundItemDao,inStockConfirmRequest,projectId,batchNo,warehouseId,projectService,productService,warehouseService);
            threadPoolTaskExecutor.submit(task7);
        } else {
            logger.error("# errorMessage: 没有对应的入库类型" );
        }

    }

}
