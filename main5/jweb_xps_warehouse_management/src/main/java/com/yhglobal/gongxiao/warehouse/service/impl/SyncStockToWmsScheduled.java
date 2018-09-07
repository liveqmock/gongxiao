package com.yhglobal.gongxiao.warehouse.service.impl;

import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.SyncEasEnum;
import com.yhglobal.gongxiao.type.SyncWmsEnum;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.*;
import com.yhglobal.gongxiao.warehouse.model.*;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationInboundService;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationOutboundService;
import com.yhglobal.gongxiao.warehouse.task.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    WmsInboundDao wmsInboundDao;

    @Autowired
    WmsInboundItemDao wmsInboundItemDao;

    @Autowired
    WmsOutboundDao wmsOutboundDao;

    @Autowired
    WmsOutboundItemDao wmsOutboundItemDao;

    @Autowired
    AllocateOrderDao allocateOrderDao;

    @Autowired
    AllocateOrderItemDao allocateOrderItemDao;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    WarehouseConfig warehouseConfig;

    @Autowired
    WmsNotificationInboundService wmsNotificationInboundService;

    @Autowired
    WmsNotificationOutboundService wmsNotificationOutboundService;

    /**
     * 同步入库数据到WMS
     *
     * @throws InterruptedException
     */
    @Scheduled(cron = "0 */5 * * * ?")   //每3分钟执行一次
    public void syncInStockInfoToWmsSchedule() throws InterruptedException {
        logger.info("#traceId={}# [IN][syncInStockInfoToWmsSchedule] start check if there have inboundInfo did not sync to wms ");
        Date date = new Date();
        String traceId = String.valueOf(date.getTime());
        String uid = "fenxiao";
        String userName = "fenxiao";
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,uid,userName);
        try {
            //调用基础模拟块的项目的grpc查询所有的项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.SelectProjectListReq selectProjectListReq = ProjectStructure.SelectProjectListReq.newBuilder().setRpcHeader(rpcHeader).build();
            ProjectStructure.SelectProjectListResp rpcResponse = projectService.selectProjectList(selectProjectListReq);
            /**
             * 1、查询所有的项目，获取项目前缀
             * 2、根据项目前缀到各个项目的入库单表_warehouse_inbound_order查找未同步到wms的入库单
             * 3、根据出库单号和项目到入库单明细表_warehouse_inbound_order_item查找对应的明细
             * 4、启用线程同步到wms
             */
            for (ProjectStructure.Project project : rpcResponse.getProjectListList()){
                String projectPrefix = project.getProjectTablePrefix();
                List<InboundOrder> inboundOrderList = inBoundOrderDao.selectInboundRecordByWmsFlag(SyncWmsEnum.SYNC_WMS_FAIL.getStatus(),projectPrefix);  //查询没有同不到wms的记录,且重次数小与8次
                List<InboundOrderItem> inboundOrderItemList = new ArrayList<>();
                if (inboundOrderList.size() == 0) {  //如果不存在没有同步到wms系统的入库记录,直接返回
                    logger.info("{}没有未同步到WMS的入库单",project.getProjectName());
                    continue;
                } else {
                    for (InboundOrder record : inboundOrderList) {
                        Thread.sleep(5000);
                        inboundOrderItemList = inboundOrderItemDao.selectInboundOrderItemByNo(record.getGongxiaoInboundOrderNo(),projectPrefix);
                        NotificationWmsInboundTask task = new NotificationWmsInboundTask(rpcHeader, record.getGongxiaoInboundOrderNo(), record, inboundOrderItemList, inBoundOrderDao, wmsNotificationInboundService, warehouseConfig);
                        threadPoolTaskExecutor.submit(task);
                    }
                }
            }
            logger.info("[OUT][syncInStockInfoToWmsSchedule] get syncInStockInfoToWms success");
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 同步出库数据到WMS
     *
     * @throws InterruptedException
     */
    @Scheduled(cron = "0 */5 * * * ?")   //每3分钟执行一次
    public void syncOutStockInfoToWmsSchedule() throws InterruptedException {
        logger.info("#traceId={}# [IN][syncOutStockInfoToWmsSchedule] start check if there have outboundInfo did not sync to wms ");
        Date date = new Date();
        String traceId = String.valueOf(date.getTime());
        String uid = "fenxiao";
        String userName = "fenxiao";
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,uid,userName);
        try {
            //调用基础模拟块的项目的grpc查询所有的项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.SelectProjectListReq selectProjectListReq = ProjectStructure.SelectProjectListReq.newBuilder().setRpcHeader(rpcHeader).build();
            ProjectStructure.SelectProjectListResp rpcResponse = projectService.selectProjectList(selectProjectListReq);
            /**
             * 1、查询所有的项目，获取项目前缀
             * 2、根据项目前缀到各个项目的出库单表_warehouse_outbound_order查找未同步到wms的出库单
             * 3、根据出库单号和项目到出库单明细表_warehouse_outbound_order_item查找对应的明细
             * 4、启用线程同步到wms
             */
            for (ProjectStructure.Project project : rpcResponse.getProjectListList()){
                String projectPrefix = project.getProjectTablePrefix();
                List<OutboundOrder> outboundOrderList = outBoundOrderDao.selectOutboundRecordByWmsFlag(SyncWmsEnum.SYNC_WMS_FAIL.getStatus(),projectPrefix);  //查询没有同步到wms的记录
                List<OutboundOrderItem> outboundOrderItemList = new ArrayList<>();
                if (outboundOrderList.size() == 0) {  //如果不存在没有同步到wms系统的入库记录,直接返回
                    logger.info("{}没有未同步到WMS的出库单",project.getProjectName());
                    continue;
                } else {
                    for (OutboundOrder record : outboundOrderList) {
                        Thread.sleep(5000);
                        outboundOrderItemList = outBoundOrderItemDao.selectOutboundOrderItemByNo(record.getGongxiaoOutboundOrderNo(),projectPrefix);
                        NotificationWmsOutboundTask task = new NotificationWmsOutboundTask(rpcHeader, record, outboundOrderItemList, outBoundOrderDao, wmsNotificationOutboundService, warehouseConfig);
                        threadPoolTaskExecutor.submit(task);
                    }
                }

            }

            logger.info("[OUT][syncOutStockInfoToWmsSchedule] get syncOutStockInfoToWms success");
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 同步入库数据到EAS
     *
     * @throws InterruptedException
     */
    @Scheduled(cron = "0 */5 * * * ?")   //每一个小时执行一次
    public void syncInStockInfoToEasSchedule() throws InterruptedException {
        logger.info("#traceId={}# [IN][syncInStockInfoToEasSchedule] start check if there have inboundInfo did not sync to EAS ");
        Date date = new Date();
        String traceId = String.valueOf(date.getTime());
        String uid = "fenxiao";
        String userName = "fenxiao";
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,uid,userName);
        try {
            List<WmsIntboundRecord> wmsIntboundRecordList = wmsInboundDao.selectInboundRecordByEasFlag(SyncEasEnum.SYNC_EAS_FAIL.getStatus());  //查询没有同不到eas的记录
            List<WmsIntboundRecordItem> wmsIntboundRecordItemList = new ArrayList<>();
            if (wmsIntboundRecordList.size() == 0) {  //如果不存在没有同步到wms系统的入库记录,直接返回
                return;
            } else {
                for (WmsIntboundRecord record : wmsIntboundRecordList) {
                    Thread.sleep(5000);
                    wmsIntboundRecordItemList = wmsInboundItemDao.selectInboundOrderItemByNo(record.getGongxiaoInboundOrderNo(), record.getWmsInboundOrderNo());   //根据 入库单单号+wms入库单号 查wms入库单对应的明细
                    NotificationPurchaseEasTask syncEasInboundTask = new NotificationPurchaseEasTask(rpcHeader,
                            record,
                            wmsIntboundRecordItemList,
                            wmsInboundDao,
                            allocateOrderDao,
                            allocateOrderItemDao);
                    threadPoolTaskExecutor.submit(syncEasInboundTask);

                }
            }
            logger.info("[OUT][syncInStockInfoToEasSchedule] get syncInStockInfoToEas success");
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 同步出库数据到EAS
     *
     * @throws InterruptedException
     */
    @Scheduled(cron = "0 */5 * * * ?")   //每一个小时执行一次
    public void syncOutStockInfoToEasSchedule() throws InterruptedException {
        logger.info("#traceId={}# [IN][syncOutStockInfoToEasSchedule] start check if there have outboundInfo did not sync to EAS");
        Date date = new Date();
        String traceId = String.valueOf(date.getTime());
        String uid = "fenxiao";
        String userName = "fenxiao";
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,uid,userName);
        try {
            List<WmsOutboundRecord> wmsOutboundRecordList = wmsOutboundDao.selectRecordByEasFlag(SyncEasEnum.SYNC_EAS_FAIL.getStatus());  //查询已经同步到WMS但是没有同步到TMS的记录
            List<WmsOutboundRecordItem> wmsOutboundRecordItemList = new ArrayList<>();
            if (wmsOutboundRecordList.size() == 0) {  //如果不存在没有同步到tms系统的出库记录,直接返回
                return;
            } else {
                for (WmsOutboundRecord record : wmsOutboundRecordList) {
                    Thread.sleep(5000);
                    wmsOutboundRecordItemList = wmsOutboundItemDao.selectOutboundOrderItemByNo(record.getGongxiaoOutboundOrderNo(), record.getWmsOutboundOrderNo());  //根据 入库单单号+wms入库单号 查wms入库单对应的明细
                    NotificationSalesEasTask task = new NotificationSalesEasTask(rpcHeader,
                            record,
                            wmsOutboundRecordItemList,
                            wmsOutboundDao,
                            allocateOrderDao,
                            allocateOrderItemDao);
                    threadPoolTaskExecutor.submit(task);

                }
            }
            logger.info("[OUT][syncOutStockInfoToEasSchedule] get syncOutStockInfoToEas success");
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 同步调拨订单到EAS
     *
     * @throws InterruptedException
     */
    @Scheduled(cron = "0 */3 * * * ?")   //每一个小时执行一次
    public void syncDbOrderToEasSchedule() throws InterruptedException {
        logger.info("#traceId={}# [IN][syncDbOrderToEasSchedule] start check if there have outboundInfo did not sync to EAS");
        Date date = new Date();
        String traceId = String.valueOf(date.getTime());
        String uid = "fenxiao";
        String userName = "fenxiao";
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,uid,userName);
        try {
            List<AllocationOrder> allocationOrderList = allocateOrderDao.selectRecordByEasFlag(SyncEasEnum.SYNC_EAS_FAIL.getStatus());  //查询没有同步到EAS的记录
            List<AllocationOrderItem> allocationOrderItemList = new ArrayList<>();
            if (allocationOrderList.size() == 0) {  //如果不存在没有同步到tms系统的出库记录,直接返回
                return;
            } else {
                for (AllocationOrder record : allocationOrderList) {
                    Thread.sleep(5000);
                    allocationOrderItemList = allocateOrderItemDao.selectOutboundOrderByNo(record.getAllocateNo());  //根据 入库单单号+wms入库单号 查wms入库单对应的明细
                    if (allocationOrderItemList.size() > 0){
                        NotificationAllocateEasTask allocateEasTask = new NotificationAllocateEasTask(rpcHeader,
                                record,
                                allocationOrderItemList,
                                allocateOrderDao,
                                allocateOrderItemDao,
                                inBoundOrderDao,
                                outBoundOrderDao);
                        threadPoolTaskExecutor.submit(allocateEasTask);
                    }

                }
            }
            logger.info("[OUT][syncDbOrderToEasSchedule] get syncOutStockInfoToEas success");
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

}
