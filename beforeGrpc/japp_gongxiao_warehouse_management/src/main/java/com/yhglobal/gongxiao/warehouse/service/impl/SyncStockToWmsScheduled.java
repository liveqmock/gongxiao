package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.warehouse.dao.WarehouseDao;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.sales.service.SalesScheduleDeliveryService;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.service.InboundNotificationService;
import com.yhglobal.gongxiao.warehouse.task.*;
import com.yhglobal.gongxiao.warehousemanagement.dao.*;
import com.yhglobal.gongxiao.warehousemanagement.model.*;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.SyncEasEnum;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.SyncWmsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SyncStockToWmsScheduled {

    private static Logger logger = LoggerFactory.getLogger(SyncStockToWmsScheduled.class);

    @Autowired
    InBoundOrderDao inBoundOrderDao;

    @Autowired
    InboundOrderItemDao inboundOrderItemDao;

    @Autowired
    OutBoundOrderDao outBoundOrderDao;

    @Autowired
    OutBoundOrderItemDao outBoundOrderItemDao;

    @Autowired
    WarehouseDao warehouseDao;

    @Autowired
    WmsInboundDao wmsInboundDao;

    @Autowired
    WmsInboundItemDao wmsInboundItemDao;

    @Autowired
    WmsOutboundDao wmsOutboundDao;

    @Autowired
    WmsOutboundItemDao wmsOutboundItemDao;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    WarehouseConfig warehouseConfig;

    @Reference(check = false)
    WarehouseService warehouseService;

    @Reference(check = false)
    ProductService productService;

    @Reference(check = false)
    ProjectService projectService;

    @Reference(check = false)
    InboundNotificationService inboundNotificationService;

    @Reference(check = false)
    SalesScheduleDeliveryService salesScheduleDeliveryService;

    /**
     * 同步入库数据到WMS
     * @throws InterruptedException
     */
    @Scheduled(cron="0 */2 * * * ?")   //每3分钟执行一次
    public void syncInStockInfoToWms() throws InterruptedException {
        logger.info("#traceId={}# [IN][syncInStockInfoToWms] start check if there have inboundInfo did not sync to wms ");
        RpcHeader rpcHeader = new RpcHeader();
        String traceId = "000001";
        String uid = "fenxiao";
        String userName = "fenxiao";
        rpcHeader.setTraceId(traceId);
        rpcHeader.setUid(uid);
        rpcHeader.setUsername(userName);

        try{
            List<InboundOrder> inboundOrderList = inBoundOrderDao.selectInboundRecordByWmsFlag(SyncWmsEnum.SYNC_WMS_FAIL.getStatus());  //查询没有同不到wms的记录,且重次数小与8次
            List<InboundOrderItem> inboundOrderItemList = new ArrayList<>();
            if (inboundOrderList.size() == 0){  //如果不存在没有同步到wms系统的入库记录,直接返回
                return;
            }else {
                for (InboundOrder record : inboundOrderList){
                    Thread.sleep(5000);
                    inboundOrderItemList = inboundOrderItemDao.selectInboundOrderItemByNo(record.getGongxiaoInboundOrderNo());
                    NotificationWmsInboundTask task = new NotificationWmsInboundTask(rpcHeader,ApplicationContextProvider.getApplicationContext(), record.getGongxiaoInboundOrderNo(),record,inboundOrderItemList,inBoundOrderDao,warehouseService,productService,projectService,warehouseConfig);
                    threadPoolTaskExecutor.submit(task);
                }
            }
            logger.info("[OUT][syncInStockInfoToWms] get syncInStockInfoToWms success");
        }catch (Exception e){
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 同步出库数据到WMS
     * @throws InterruptedException
     */
    @Scheduled(cron="0 */2 * * * ?")   //每3分钟执行一次
    public void syncOutStockInfoToWms() throws InterruptedException {
        logger.info("#traceId={}# [IN][syncOutStockInfoToWms] start check if there have outboundInfo did not sync to wms ");
        RpcHeader rpcHeader = new RpcHeader();
        String traceId = "000001";
        String uid = "fenxiao";
        String userName = "fenxiao";
        rpcHeader.setTraceId(traceId);
        rpcHeader.setUid(uid);
        rpcHeader.setUsername(userName);
        try {
            List<OutboundOrder> outboundOrderList = outBoundOrderDao.selectOutboundRecordByWmsFlag(SyncWmsEnum.SYNC_WMS_FAIL.getStatus());  //查询没有同步到wms的记录
            List<OutboundOrderItem> outboundOrderItemList = new ArrayList<>();
            if (outboundOrderList.size() == 0){  //如果不存在没有同步到wms系统的入库记录,直接返回
                return;
            }else {
                for (OutboundOrder record : outboundOrderList){
                    Thread.sleep(5000);
                    int i = outBoundOrderDao.updateWmsFlagByOrder(record.getGongxiaoOutboundOrderNo(),SyncWmsEnum.SYNC_WMS_ING.getStatus(),record.getDataVersion());
                    if (1==i){        //向WMS同步之前，先将订单状态改为“同步中”，如果跟新成功，说明只有这个线程拿到这条数据,否则该数据已经被其他线程拿到
                        outboundOrderItemList = outBoundOrderItemDao.selectOutboundOrderItemByNo(record.getGongxiaoOutboundOrderNo());
                        NotificationWmsOutboundTask task = new NotificationWmsOutboundTask(rpcHeader,ApplicationContextProvider.getApplicationContext(), record, outboundOrderItemList,outBoundOrderDao,warehouseService,projectService,productService,warehouseConfig,salesScheduleDeliveryService);
                        threadPoolTaskExecutor.submit(task);
                    }else {
                        continue;
                    }

                }
            }
            logger.info("[OUT][syncOutStockInfoToWms] get syncOutStockInfoToWms success");
        }catch (Exception e){
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 同步入库数据到EAS
     * @throws InterruptedException
     */
    @Scheduled(cron = "0 */5 * * * ?")   //每yige执行一次
    public void syncInStockInfoToEas() throws InterruptedException {
        logger.info("#traceId={}# [IN][syncInStockInfoToEas] start check if there have inboundInfo did not sync to EAS ");
        RpcHeader rpcHeader = new RpcHeader();
        String traceId = "000001";
        String uid = "fenxiao";
        String userName = "fenxiao";
        rpcHeader.setTraceId(traceId);
        rpcHeader.setUid(uid);
        rpcHeader.setUsername(userName);

        try{
            List<WmsIntboundRecord> wmsIntboundRecordList = wmsInboundDao.selectInboundRecordByEasFlag(SyncEasEnum.SYNC_EAS_FAIL.getStatus());  //查询没有同不到wms的记录
            List<WmsIntboundRecordItem> wmsIntboundRecordItemList = new ArrayList<>();
            if (wmsIntboundRecordList.size() == 0){  //如果不存在没有同步到wms系统的入库记录,直接返回
                return;
            }else {
                for (WmsIntboundRecord record : wmsIntboundRecordList){
                    Thread.sleep(5000);
                    int i = wmsInboundDao.updateEasFlagToIng(record.getGongxiaoInboundOrderNo(),record.getWmsInboundOrderNo(),SyncEasEnum.SYNC_EAS_ING.getStatus(),record.getDataVersion());
                    if (i == 1){    //向EAS同步之前，先将订单状态改为“同步中”，如果跟新成功，说明只有这个线程拿到这条数据,否则该数据已经被其他线程拿到
                        wmsIntboundRecordItemList = wmsInboundItemDao.selectInboundOrderItemByNo(record.getGongxiaoInboundOrderNo(),record.getWmsInboundOrderNo());   //根据 入库单单号+wms入库单号 查wms入库单对应的明细
                        NotificationPurchaseEasTask task = new NotificationPurchaseEasTask(rpcHeader,
                                ApplicationContextProvider.getApplicationContext(),
                                record,
                                wmsIntboundRecordItemList,
                                wmsInboundDao,
                                inboundNotificationService);
                        threadPoolTaskExecutor.submit(task);
                    }else {
                        continue;
                    }

                }
            }
            logger.info("[OUT][syncInStockInfoToEas] get syncInStockInfoToEas success");
        }catch (Exception e){
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 同步出库数据到EAS
     * @throws InterruptedException
     */
    @Scheduled(cron="0 */5 * * * ?")   //每6分钟执行一次
    public void syncOutStockInfoToEas() throws InterruptedException {
        logger.info("#traceId={}# [IN][syncOutStockInfoToEas] start check if there have outboundInfo did not sync to EAS");
        RpcHeader rpcHeader = new RpcHeader();
        String traceId = "000001";
        String uid = "fenxiao";
        String userName = "fenxiao";
        rpcHeader.setTraceId(traceId);
        rpcHeader.setUid(uid);
        rpcHeader.setUsername(userName);
        try {
            List<WmsOutboundRecord> wmsOutboundRecordList = wmsOutboundDao.selectRecordByEasFlag(SyncEasEnum.SYNC_EAS_FAIL.getStatus());  //查询已经同步到WMS但是没有同步到TMS的记录
            List<WmsOutboundRecordItem> wmsOutboundRecordItemList = new ArrayList<>();
            if (wmsOutboundRecordList.size() == 0){  //如果不存在没有同步到tms系统的出库记录,直接返回
                return;
            }else {
                for (WmsOutboundRecord record : wmsOutboundRecordList){
                    Thread.sleep(5000);
                    int i = wmsOutboundDao.updateEasFlagToIng(record.getGongxiaoOutboundOrderNo(),record.getWmsOutboundOrderNo(),SyncEasEnum.SYNC_EAS_ING.getStatus(),record.getDataVersion());
                    if (i == 1){     //向EAS同步之前，先将订单状态改为“同步中”，如果跟新成功，说明只有这个线程拿到这条数据,否则该数据已经被其他线程拿到
                        wmsOutboundRecordItemList = wmsOutboundItemDao.selectOutboundOrderItemByNo(record.getGongxiaoOutboundOrderNo(),record.getWmsOutboundOrderNo());  //根据 入库单单号+wms入库单号 查wms入库单对应的明细
                        NotificationSalesEasTask task = new NotificationSalesEasTask(rpcHeader,
                                record,
                                wmsOutboundRecordItemList,
                                wmsOutboundDao,
                                salesScheduleDeliveryService);
                        threadPoolTaskExecutor.submit(task);
                    }else {
                        continue;
                    }

                }
            }
            logger.info("[OUT][syncOutStockInfoToEas] get syncOutStockInfoToEas success");
        }catch (Exception e){
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

}
