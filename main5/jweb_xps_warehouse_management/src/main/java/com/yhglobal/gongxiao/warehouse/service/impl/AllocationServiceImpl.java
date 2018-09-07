package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.SyncEasEnum;
import com.yhglobal.gongxiao.type.WmsAllocateWay;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.*;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrder;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrderItem;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.bo.AllocationOrderShowModel;
import com.yhglobal.gongxiao.warehouse.service.AllocationService;
import com.yhglobal.gongxiao.warehouse.task.NotificationWmsAllocateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

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
    InBoundOrderDao inBoundOrderDao;
    @Autowired
    InboundOrderItemDao inboundOrderItemDao;
    @Autowired
    OutBoundOrderDao outBoundOrderDao;
    @Autowired
    OutBoundOrderItemDao outBoundOrderItemDao;
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    WarehouseConfig warehouseConfig;

    @Override
    public int createAllocationRecord(GongxiaoRpc.RpcHeader rpcHeader, AllocationOrder allocationOrder, List<AllocationOrderItem> allocationOrderItemList) {
        logger.info("#traceId={}# [IN][createAllocationRecord] params: allocationOrder={},allocationOrderItemList={}", rpcHeader.getTraceId(), JSON.toJSONString(allocationOrder), JSON.toJSONString(allocationOrderItemList));
        int i;
        String entryProjectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(allocationOrder.getProjectIdEnter()));
        String outProjectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(allocationOrder.getProjectIdOut()));
        String transferNo = DateTimeIdGenerator.nextId(entryProjectPrefix, BizNumberType.TRANSFER_NO);
        String gongxiaoOutboundOrderNo = DateTimeIdGenerator.nextId(outProjectPrefix, BizNumberType.STOCK_TSF_OUT_NO);
        String gongxiaoInboundOrderNo = DateTimeIdGenerator.nextId(entryProjectPrefix, BizNumberType.STOCK_TSF_IN_NO);
        TraceLog traceLog = new TraceLog();
        traceLog.setOpUid(String.valueOf(rpcHeader.getUid()));
        traceLog.setOpName(rpcHeader.getUsername());
        traceLog.setContent("创建调拨单");
        allocationOrder.setAllocateNo(transferNo);
        List<TraceLog> tracelogs = new ArrayList<>();
        tracelogs.add(traceLog);
        allocationOrder.setTraceLog(JSON.toJSONString(tracelogs));
        allocationOrder.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);
        allocationOrder.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);
        allocationOrder.setSyncEas(SyncEasEnum.SYNC_EAS_FAIL.getStatus()); //同步EAS的状态
        allocationOrder.setDataVersion(0);
        //调用基础模块的仓库grpc服务查仓库编码
        WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
        WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(allocationOrder.getWarehouseOutId()).build();
        WarehouseStructure.GetWarehouseByIdResp rpcResponse = warehouseService.getWarehouseById(getWarehouseByIdReq);
        WarehouseStructure.Warehouse warehouse = rpcResponse.getWarehouse();

        WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq2 = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(allocationOrder.getWarehouseEnterId()).build();
        WarehouseStructure.GetWarehouseByIdResp rpcResponse2 = warehouseService.getWarehouseById(getWarehouseByIdReq2);
        WarehouseStructure.Warehouse warehouse2 = rpcResponse2.getWarehouse();

        allocationOrder.setWarehouseOut(warehouse.getWarehouseName());
        allocationOrder.setWarehouseEnter(warehouse2.getWarehouseName());

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

            NotificationWmsAllocateTask notificationWmsAllocateTask = new NotificationWmsAllocateTask(rpcHeader, allocationOrder, inBoundOrderDao, inboundOrderItemDao, outBoundOrderDao, outBoundOrderItemDao, allocationOrderItemList, warehouseConfig);
            threadPoolTaskExecutor.submit(notificationWmsAllocateTask);
            logger.info("#traceId={}# [OUT] get createAllocationRecord success", rpcHeader.getTraceId());
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<AllocationOrderShowModel> getAllRecordByCondition(GongxiaoRpc.RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String allocateNo, String gongxiaoOutboundOrderNo, String gongxiaoInboundOrderNo, String warehouseOut, String warehouseEnter, String createBeginTime, String createEndTime) {

        logger.info("#traceId={}# [IN][getAllRecordByCondition] params: projectId={},allocateNo={},gongxiaoOutboundOrderNo={},gongxiaoInboundOrderNo={},warehouseOut={},warehouseEnter={},status={},createBeginTime={},createEndTime={}", rpcHeader.getTraceId(), projectId, allocateNo, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, warehouseEnter, createBeginTime, createEndTime);
        try {
            PageInfo<AllocationOrderShowModel> pageInfo;
            PageHelper.startPage(pageNumber, pageSize);
            List<AllocationOrder> allocationOrderList = allocateOrderDao.selectrRecordByCondition(projectId, allocateNo, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, warehouseEnter, createBeginTime, createEndTime);
            List<AllocationOrderShowModel> resultList = new ArrayList<>();
            int total = allocationOrderList.size();
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
            pageInfo.setPageNum(pageNumber);
            pageInfo.setPageSize(pageSize);
            pageInfo.setTotal(total);
            logger.info("#traceId={}# [OUT] get getAllRecordByCondition success: resultList.size()={}", rpcHeader.getTraceId(), resultList.size());
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public AllocationOrder selectInfoByAllocateNo(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String allocateNo) {

        logger.info("#traceId={}# [IN][selectInfoByAllocateNo] params: projectId={},allocateNo={}", rpcHeader.getTraceId(), projectId, allocateNo);
        try {
            AllocationOrder result = allocateOrderDao.selectInfoByAllocateNo(projectId, allocateNo);
            logger.info("#traceId={}# [OUT] get selectInfoByAllocateNo success", rpcHeader.getTraceId());
            return result;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<AllocationOrderItem> getAllocationOrderItemInfos(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String allocateNo) {

        logger.info("#traceId={}# [IN][getAllocationOrderItemInfos] params: projectId={},allocateNo={}", rpcHeader.getTraceId(), projectId, allocateNo);
        try {
            List<AllocationOrderItem> resultList = allocateOrderItemDao.getAllocationOrderItemInfos(projectId, allocateNo);
            logger.info("#traceId={}# [OUT] get getAllocationOrderItemInfos success: resultList.size()={}", rpcHeader.getTraceId(), resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int updateAllocateOrderEntry(GongxiaoRpc.RpcHeader rpcHeader, String alllocateNo, int quantity) {
        logger.info("#traceId={}# [IN][updateAllocateOrder] params: alllocateNo={}，quantity={}", rpcHeader.getTraceId(), alllocateNo, quantity);
        try {
            allocateOrderDao.updateAllocateOrderEntry(alllocateNo, quantity);
            logger.info("#traceId={}# [OUT] get updateAllocateOrder success", rpcHeader.getTraceId());
            return ErrorCode.SUCCESS.getErrorCode();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int updateAllocateOrderOut(GongxiaoRpc.RpcHeader rpcHeader, String alllocateNo, int quantity) {
        logger.info("#traceId={}# [IN][updateAllocateOrderOut] params: alllocateNo={}，quantity={}", rpcHeader.getTraceId(), alllocateNo, quantity);
        try {
            allocateOrderDao.updateAllocateOrderOut(alllocateNo, quantity);
            logger.info("#traceId={}# [OUT] get updateAllocateOrderOut success", rpcHeader.getTraceId());
            return ErrorCode.SUCCESS.getErrorCode();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int updateAllocateEntryItem(GongxiaoRpc.RpcHeader rpcHeader, String alllocateNo, String productCode, int quantity) {
        logger.info("#traceId={}# [IN][updateAllocateItem] params: alllocateNo={}，quantity={}", rpcHeader.getTraceId(), alllocateNo, quantity);
        try {
            AllocationOrderItem order = allocateOrderItemDao.getAllocationOrderItem(alllocateNo,productCode);
            allocateOrderItemDao.updateAllocateEntryItem(order.getId(),quantity);
            logger.info("#traceId={}# [OUT] get updateAllocateItem success", rpcHeader.getTraceId());
            return ErrorCode.SUCCESS.getErrorCode();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int updateAllocateOutItem(GongxiaoRpc.RpcHeader rpcHeader, String alllocateNo, String productCode, int quantity) {
        logger.info("#traceId={}# [IN][updateAllocateOutItem] params: alllocateNo={}，quantity={}", rpcHeader.getTraceId(), alllocateNo, quantity);
        try {
            AllocationOrderItem order = allocateOrderItemDao.getAllocationOrderItem(alllocateNo,productCode);
            allocateOrderItemDao.updateAllocateOutItem(order.getId(),quantity);
            logger.info("#traceId={}# [OUT] get updateAllocateOutItem success", rpcHeader.getTraceId());
            return ErrorCode.SUCCESS.getErrorCode();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
