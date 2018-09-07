package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.service.AllocationService;
import com.yhglobal.gongxiao.warehouse.task.NotificationWmsAllocateTask;
import com.yhglobal.gongxiao.warehouse.type.WmsAllocateWay;
import com.yhglobal.gongxiao.warehousemanagement.bo.AllocationOrderShowModel;
import com.yhglobal.gongxiao.warehousemanagement.dao.AllocateOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.AllocateOrderItemDao;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllocationServiceImpl implements AllocationService {

    private static Logger logger = LoggerFactory.getLogger(AllocationServiceImpl.class);

    @Autowired
    AllocateOrderDao allocateOrderDao;

    @Autowired
    AllocateOrderItemDao allocateOrderItemDao;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    WarehouseConfig warehouseConfig;

    @Override
    public int createAllocationRecord(RpcHeader rpcHeader, AllocationOrder allocationOrder, List<AllocationOrderItem> allocationOrderItemList) {
        logger.info("#traceId={}# [IN][createAllocationRecord] params: allocationOrder={},allocationOrderItemList={}", rpcHeader.traceId, JSON.toJSONString(allocationOrder), JSON.toJSONString(allocationOrderItemList));
        int i;
        String transferNo = DateTimeIdGenerator.nextId(BizNumberType.TRANSFER_NO);
        String gongxiaoOutboundOrderNo = DateTimeIdGenerator.nextId(BizNumberType.STOCK_TSF_OUT_NO);
        String gongxiaoInboundOrderNo = DateTimeIdGenerator.nextId(BizNumberType.STOCK_TSF_IN_NO);
        TraceLog traceLog = new TraceLog();
        traceLog.setOpUid(String.valueOf(rpcHeader.getUid()));
        traceLog.setOpName(rpcHeader.getUsername());
        traceLog.setContent("创建调拨单");
        allocationOrder.setAllocateNo(transferNo);
        List<TraceLog> tracelogs= new ArrayList<>();
        tracelogs.add(traceLog);
        allocationOrder.setTraceLog(JSON.toJSONString(tracelogs));
        allocationOrder.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);
        allocationOrder.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);
        for (AllocationOrderItem record : allocationOrderItemList) {
            record.setAllocateNo(transferNo);
            record.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);
            record.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);
            record.setWarehouseOut(allocationOrder.getWarehouseOut());
            record.setWarehouseEnter(allocationOrder.getWarehouseEnter());
        }
        try {
            i = allocateOrderDao.insertAllocateOrder(allocationOrder);
            if (0 != i) {
                allocateOrderItemDao.insertAllocateOrderItems(allocationOrderItemList);
            }

            NotificationWmsAllocateTask task1 = new NotificationWmsAllocateTask(rpcHeader, ApplicationContextProvider.getApplicationContext(), allocationOrder, allocationOrderItemList,warehouseConfig);
            threadPoolTaskExecutor.submit(task1);
            logger.info("#traceId={}# [OUT] get createAllocationRecord success", rpcHeader.traceId);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<AllocationOrderShowModel> getAllRecordByCondition(RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String allocateNo, String gongxiaoOutboundOrderNo, String gongxiaoInboundOrderNo, String warehouseOut, String warehouseEnter, String status, String createBeginTime, String createEndTime) {

        logger.info("#traceId={}# [IN][getAllRecordByCondition] params: projectId={},allocateNo={},gongxiaoOutboundOrderNo={},gongxiaoInboundOrderNo={},warehouseOut={},warehouseEnter={},status={},createBeginTime={},createEndTime={}", rpcHeader.traceId, projectId, allocateNo, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, warehouseEnter, status, createBeginTime, createEndTime);
        try {
            PageInfo<AllocationOrderShowModel> pageInfo;
            PageHelper.startPage(pageNumber, pageSize);
            List<AllocationOrder> allocationOrderList = allocateOrderDao.selectrRecordByCondition(projectId, allocateNo, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, warehouseEnter, status, createBeginTime, createEndTime);
            List<AllocationOrderShowModel> resultList = new ArrayList<>();
            for (AllocationOrder record : allocationOrderList) {
                AllocationOrderShowModel newRecord = new AllocationOrderShowModel();
                newRecord.setAllocateNo(record.getAllocateNo());
                if (record.getAlloteWay() == WmsAllocateWay.ALLOCATE_SAME_STOCK.getAllocateWay()) {
                    newRecord.setAlloteWay("同仓调拨");
                } else {
                    newRecord.setAlloteWay("跨仓调拨");
                }
                newRecord.setStatus(record.getStatus());
                newRecord.setCreateTime(record.getCreateTime());
                newRecord.setGongxiaoInboundOrderNo(record.getGongxiaoInboundOrderNo());
                newRecord.setGongxiaoOutboundOrderNo(record.getGongxiaoOutboundOrderNo());
                newRecord.setProjectIdEnter(record.getProjectIdEnter());
                newRecord.setProjectIdOut(record.getProjectIdOut());
                newRecord.setWarehouseEnter(record.getWarehouseEnter());
                newRecord.setWarehouseOut(record.getWarehouseOut());
                resultList.add(newRecord);
            }
            pageInfo = new PageInfo<AllocationOrderShowModel>(resultList);
            logger.info("#traceId={}# [OUT] get getAllRecordByCondition success: resultList.size()={}", rpcHeader.traceId, resultList.size());
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public AllocationOrder selectInfoByAllocateNo(RpcHeader rpcHeader, String projectId, String allocateNo) {

        logger.info("#traceId={}# [IN][selectInfoByAllocateNo] params: projectId={},allocateNo={}", rpcHeader.traceId, projectId, allocateNo);
        try {
            AllocationOrder result = allocateOrderDao.selectInfoByAllocateNo(projectId, allocateNo);
            logger.info("#traceId={}# [OUT] get selectInfoByAllocateNo success", rpcHeader.traceId);
            return result;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<AllocationOrderItem> getAllocationOrderItemInfos(RpcHeader rpcHeader, String projectId, String allocateNo) {

        logger.info("#traceId={}# [IN][getAllocationOrderItemInfos] params: projectId={},allocateNo={}", rpcHeader.traceId, projectId, allocateNo);
        try {
            List<AllocationOrderItem> resultList = allocateOrderItemDao.getAllocationOrderItemInfos(projectId, allocateNo);
            logger.info("#traceId={}# [OUT] get getAllocationOrderItemInfos success: resultList.size()={}", rpcHeader.traceId, resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int updateByAllocateNo(RpcHeader rpcHeader, String projectId, String allocateNo) {
        logger.info("#traceId={}# [IN][updateByAllocateNo] params: projectId={},allocateNo={}", rpcHeader.traceId, projectId, allocateNo);
        try {
           int i = allocateOrderDao.updateByAllocateNo(projectId,allocateNo);
            if (0 != i) {
                allocateOrderItemDao.updateByAllocateNo(projectId,allocateNo);
            }
            logger.info("#traceId={}# [OUT] get updateByAllocateNo success", rpcHeader.traceId);
            return 0;
            } catch(Exception e){
                logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
        }
    }
