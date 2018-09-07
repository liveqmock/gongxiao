package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.InboundOrderStatusEnum;
import com.yhglobal.gongxiao.type.WmsOrderType;
import com.yhglobal.gongxiao.warehouse.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.InboundOrderItemDao;
import com.yhglobal.gongxiao.warehouse.dao.WmsInboundDao;
import com.yhglobal.gongxiao.warehouse.model.InboundOrder;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.bo.InboundOrderBasicInfo;
import com.yhglobal.gongxiao.warehouse.model.bo.InboundOrderItemShowModel;
import com.yhglobal.gongxiao.warehouse.model.bo.InboundOrderShowModel;
import com.yhglobal.gongxiao.warehouse.service.InboundShowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 入库单与页面交互实现类
 *
 * @author : liukai
 */
@Service
public class InboundShowServiceImpl implements InboundShowService {

    private Logger logger = LoggerFactory.getLogger(InboundShowServiceImpl.class);

    @Autowired
    InBoundOrderDao inBoundOrderDao;

    @Autowired
    InboundOrderItemDao inboundOrderItemDao;

    @Autowired
    WmsInboundDao wmsInboundDao;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public PageInfo<InboundOrderShowModel> selectInStorageInfo(GongxiaoRpc.RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String gonxiaoInboundNo, String purchaseNo, String productCode, String goodCode, String createTime, String warehouseId, String supplierName) {
        try {
            logger.info("#traceId={}# [IN][selectInStorageInfo] params: projectId={},inventoryNum={},purchaseNo={},productCode={},goodCode={},createTime={},warehouseId={},supplierName={}", rpcHeader.getTraceId(), projectId, gonxiaoInboundNo, purchaseNo, productCode, goodCode, createTime, warehouseId, supplierName);
            //调用基础模块的项目的grpc查询项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
            ProjectStructure.GetByProjectIdResp rpcResponse = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = rpcResponse.getProject();
            String prefix = project.getProjectTablePrefix();
            PageInfo<InboundOrderShowModel> pageInfo;
            PageHelper.startPage(pageNumber, pageSize);
            List<InboundOrder> recordList = inBoundOrderDao.selectInStorageInfo(projectId, gonxiaoInboundNo, purchaseNo, productCode, goodCode, createTime, warehouseId, supplierName, prefix);  //查询入库单列表
            PageInfo<InboundOrder> inboundOrderPageInfo = new PageInfo<>(recordList);
            long total = inboundOrderPageInfo.getTotal();
            List<InboundOrderShowModel> resultList = new ArrayList<>();
            for (InboundOrder record : recordList) {
                InboundOrderShowModel newRecord = new InboundOrderShowModel();
                newRecord.setProjectId(record.getProjectId());
                newRecord.setWarehouseId(record.getWarehouseId());
                newRecord.setOrderStatus(record.getOrderStatus());
                if (WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode() == record.getInboundType()) {
                    newRecord.setInboundType("采购入库");
                } else if (WmsOrderType.INBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode() == record.getInboundType()) {
                    newRecord.setInboundType("调拨入库");
                } else if (WmsOrderType.INBOUND_FOR_OTHER_REASON.getInboundOrderCode() == record.getInboundType()) {
                    newRecord.setInboundType("其他入库");
                } else if (WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode() == record.getInboundType()) {
                    newRecord.setInboundType("退货入库");
                } else {
                    newRecord.setInboundType("不详");
                }
                newRecord.setGongxiaoInboundOrderNo(record.getGongxiaoInboundOrderNo());
                newRecord.setPurchaseOrderNo(record.getPurchaseOrderNo());
                newRecord.setWarehouseName(record.getWarehouseName());
                newRecord.setPlanInStockQuantity(record.getTotalQuantity());
                newRecord.setActualInStockQuantity(record.getRealInStockQuantity());
                newRecord.setDifferentQuantity(record.getTotalQuantity() - record.getRealInStockQuantity());
                newRecord.setEstimateArriveTime(record.getExpectedArrivalTime());
                newRecord.setCreateTime(record.getCreateTime());
                resultList.add(newRecord);
            }

            pageInfo = new PageInfo<InboundOrderShowModel>(resultList);
            pageInfo.setPageNum(pageNumber);
            pageInfo.setPageSize(pageSize);
            pageInfo.setTotal(total);
            logger.info("#traceId={}# [OUT] get selectInStorageInfo success: resultList.size():{}", rpcHeader.getTraceId(), resultList.size());
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public InboundOrderBasicInfo selectRecordById(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String inventoryNum) {
        try {
            logger.info("#traceId={}# [IN][selectRecordById] params: projectId={},inventoryNum={}", rpcHeader.getTraceId(), projectId, inventoryNum);
            //调用基础模块的项目的grpc查询项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
            ProjectStructure.GetByProjectIdResp projectIdResp = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = projectIdResp.getProject();
            String prefix = project.getProjectTablePrefix();
            InboundOrder record = inBoundOrderDao.getRecordById(projectId, inventoryNum, prefix);   //查询入库单
            String warehouseId = record.getWarehouseId();
            //调用基础模块的仓库grpc服务查仓库编码
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(warehouseId).build();
            WarehouseStructure.GetWarehouseByIdResp rpcResponse = warehouseService.getWarehouseById(getWarehouseByIdReq);
            WarehouseStructure.Warehouse warehouse = rpcResponse.getWarehouse();

            if (record != null) {
                InboundOrderBasicInfo result = new InboundOrderBasicInfo();
                result.setProjectId(record.getProjectId());
                result.setBatchNo(record.getBatchNo());
                if (InboundOrderStatusEnum.INBOUND_ORDER_WAIT.getStatus() == record.getOrderStatus()) {
                    result.setOrderStatus("等待收货");
                } else if (InboundOrderStatusEnum.INBOUND_ORDER_RECEIVE_PART.getStatus() == record.getOrderStatus()) {
                    result.setOrderStatus("部分收货");
                } else if (InboundOrderStatusEnum.INBOUND_ORDER_RECEIVE_FINISH.getStatus() == record.getOrderStatus()) {
                    result.setOrderStatus("收货完成");
                } else if (InboundOrderStatusEnum.INBOUND_ORDER_CANCEL.getStatus() == record.getOrderStatus()) {
                    result.setOrderStatus("订单已取消");
                } else if (InboundOrderStatusEnum.INBOUND_ORDER_CLOSE.getStatus() == record.getOrderStatus()) {
                    result.setOrderStatus("订单已关闭");
                } else {
                    result.setOrderStatus("不详");
                }
                if (WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode() == record.getInboundType()) {
                    result.setInboundType("采购入库");
                } else if (WmsOrderType.INBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode() == record.getInboundType()) {
                    result.setInboundType("调拨入库");
                } else if (WmsOrderType.INBOUND_FOR_OTHER_REASON.getInboundOrderCode() == record.getInboundType()) {
                    result.setInboundType("其他入库");
                } else if (WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode() == record.getInboundType()) {
                    result.setInboundType("退货入库");
                } else {
                    result.setInboundType("不详");
                }
                result.setGongxiaoInboundOrderNo(record.getGongxiaoInboundOrderNo());
                result.setPurchaseOrderNo(record.getPurchaseOrderNo());
                result.setWarehouseName(record.getWarehouseName());
                result.setWarehouseDetailAddress(warehouse.getStreetAddress());
                result.setDeliverAddress(record.getDeliverAddress());
                result.setShippingMode(record.getShippingMode());
                result.setNote(record.getNote());
                List<TraceLog> traceLog = JSON.parseArray(record.getTracelog(), TraceLog.class);
                result.setTracelog(traceLog);
                result.setCreateTime(record.getCreateTime());
                result.setEstimateOutTime(record.getCreateTime());      //预计出库时间
                result.setLastUpdateTime(record.getLastUpdateTime());
                logger.info("#traceId={}# [OUT] get selectRecordById success: result:{}", rpcHeader.getTraceId(), JSON.toJSONString(result));
                return result;
            } else {
                logger.info("#traceId={}# [OUT] get selectRecordById success: result:{}", rpcHeader.getTraceId(), null);
                return null;
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public List<InboundOrder> selectInboundRecordByPurchaseNo(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String purchaseNo) {
        logger.info("#traceId={}# [IN][selectInboundRecordByPurchaseNo] params: projectId={},PurchaseNo={}", rpcHeader.getTraceId(), projectId, purchaseNo);
        try {
            //调用基础模块的项目的grpc查询项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
            ProjectStructure.GetByProjectIdResp rpcResponse = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = rpcResponse.getProject();
            String prefix = project.getProjectTablePrefix();
            List<InboundOrder> resultList = inBoundOrderDao.selectInboundRecordByPurchaseNo(projectId, purchaseNo, prefix);
            logger.info("#traceId={}# [OUT] get selectInboundRecordByPurchaseNo success: resultList.size:{}", rpcHeader.getTraceId(), resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<InboundOrderItem> selectInStorageDetailInfoByinventoryNum(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String inventoryNum) {
        logger.info("#traceId={}# [IN][selectInStorageDetailInfoByinventoryNum] params: projectId={},inventoryNum={}", rpcHeader.getTraceId(), projectId, inventoryNum);
        try {
            //调用基础模块的项目的grpc查询项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
            ProjectStructure.GetByProjectIdResp rpcResponse = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = rpcResponse.getProject();
            String prefix = project.getProjectTablePrefix();
            List<InboundOrderItem> resultList = inboundOrderItemDao.selectInStorageDetailInfoByPurchaseNo(projectId, inventoryNum, prefix);
            logger.info("#traceId={}# [OUT] get selectInStorageDetailInfoByinventoryNum success: resultList.size:{}", rpcHeader.getTraceId(), resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<InboundOrderItemShowModel> selectInStorageDetailInfoById(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String inventoryNum) {
        try {
            logger.info("#traceId={}# [IN][selectInStorageDetailInfoById] params: projectId={},inventoryNum={}", rpcHeader.getTraceId(), projectId, inventoryNum);
            //调用基础模块的项目的grpc查询项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
            ProjectStructure.GetByProjectIdResp rpcResponse = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = rpcResponse.getProject();
            String prefix = project.getProjectTablePrefix();
            List<InboundOrderItem> inboundOrderItemList = inboundOrderItemDao.selectInboundOrderItemInfosById(projectId, inventoryNum, prefix);
            if (inboundOrderItemList != null) {
                List<InboundOrderItemShowModel> resultList = new ArrayList<>();
                for (InboundOrderItem record : inboundOrderItemList) {
                    InboundOrderItemShowModel newRecord = new InboundOrderItemShowModel();
                    newRecord.setProjectId(record.getProjectId());
                    newRecord.setPurchaseOrderNo(record.getPurchaseOrderNo());
                    newRecord.setItemStatus(record.getItemStatus());
                    newRecord.setProductCode(record.getProductCode());
                    newRecord.setProductName(record.getProductName());
                    newRecord.setProductUnit(record.getProductUnit());
                    newRecord.setPlanInStockQuantity(record.getTotalQuantity());
                    newRecord.setActualInStockQuantity(record.getRealInStockQuantity());
                    newRecord.setNonImperfectQuantity(record.getInStockQuantity());  //良品数量
                    newRecord.setImperfectQuantity(record.getImperfectQuantity());
                    newRecord.setDifferentQuantity(record.getTotalQuantity() - record.getRealInStockQuantity());
                    newRecord.setCreateTime(record.getCreateTime());
                    resultList.add(newRecord);
                }
                logger.info("#traceId={}# [OUT] get selectInStorageDetailInfoById success: resultList.size:{}", rpcHeader.getTraceId(), resultList.size());
                return resultList;
            } else {
                logger.info("#traceId={}# [OUT] get selectInStorageDetailInfoById success: resultList.size:{}", rpcHeader.getTraceId(), 0);
                return null;
            }

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

}
