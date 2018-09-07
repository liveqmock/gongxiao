package com.yhglobal.gongxiao.sales.microservice.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.XpsGrossProfitManager;
import com.yhglobal.gongxiao.XpsPrepaidManager;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.sales.SalesReturnOrderType;
import com.yhglobal.gongxiao.constant.sales.SalesReturnStatus;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure;
import com.yhglobal.gongxiao.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderItemDao;
import com.yhglobal.gongxiao.sales.dao.SalesReturnOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesReturnOrderItemDao;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure;
import com.yhglobal.gongxiao.sales.model.SalesConfig;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import com.yhglobal.gongxiao.sales.model.SalesReturnOrder;
import com.yhglobal.gongxiao.sales.model.SalesReturnOrderItem;
import com.yhglobal.gongxiao.sales.model.bo.SalesReturnClassificationCount;
import com.yhglobal.gongxiao.type.WmsOrderType;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import com.yhglobal.gongxiao.warehouseapi.model.PlanInboundOrderItem;
import com.yhglobal.gongxiao.warehouseapi.sales.ReturnItem;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * @Description: 销退实现类
 * @author: LUOYI
 * @Date: Created in 19:27 2018/6/6
 */

@Service
public class SalesOrderReturnServiceImpl extends SalesOrderReturnServiceGrpc.SalesOrderReturnServiceImplBase {

    private Logger logger = LoggerFactory.getLogger(SalesOrderReturnServiceImpl.class);

    @Autowired
    private SalesReturnOrderDao salesReturnOrderDao;

    @Autowired
    private SalesReturnOrderItemDao salesReturnOrderItemDao;

    @Autowired
    private SalesOrderDao salesOrderDao;
    @Autowired
    private SalesOrderItemDao salesOrderItemDao;
    @Autowired
    private SalesConfig salesConfig;

    /**
     * 获取销售退货列表
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void getsOrderReturn(SalesOrderReturnServiceStructure.SearchOrderReturnReq request, StreamObserver<SalesOrderReturnServiceStructure.SalesReturnPageInfoResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        Long projectId = request.getProjectId();
        Integer returnedType = request.getReturnedType();
        String salesReturnedOrderNo = request.getSalesReturnedOrderNo();
        String warehouseId = request.getWarehouseId();
        String timeStart = request.getTimeStart();
        String timeEnd = request.getTimeEnd();
        Integer returnedOrderStatus = request.getReturnedOrderStatus();
        Integer pageNumber = request.getPageNumber();
        Integer pageSize = request.getPageSize();
        logger.info("#traceId={}# [IN][getsSalesReturnOrderByProject] params: projectId={}, returnedType={}, salesReturnedOrderNo={}, warehouseId={}, timeStart={}, timeEnd={}, returnedOrderStatus={}, pageNumber={}, pageSize={} "
                , rpcHeader.getTraceId(), projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd, returnedOrderStatus, pageNumber, pageSize);
        SalesOrderReturnServiceStructure.SalesReturnPageInfoResp.Builder respBuilder = SalesOrderReturnServiceStructure.SalesReturnPageInfoResp.newBuilder();
        PageInfo<SalesReturnOrder> pageResult = null;
        try {
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PageHelper.startPage(pageNumber, pageSize);// 启动分页
            List<SalesReturnOrder> salesReturn = salesReturnOrderDao.selectSalesReturnedOrderByProject(prefix,
                    projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd, returnedOrderStatus);
            logger.info("#traceId={}# [OUT] getsOrderReturn success:list.size={}", rpcHeader.getTraceId(), salesReturn.size());
            pageResult = new PageInfo<SalesReturnOrder>(salesReturn);// 包装分页对象
            javaToSalesReturnPageInfoResp(pageResult, respBuilder);// 数据转换
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    // 把java对象转换为proto对象
    private void javaToSalesReturnPageInfoResp(PageInfo<SalesReturnOrder> pageInfo, SalesOrderReturnServiceStructure.SalesReturnPageInfoResp.Builder respBuilder) {
        respBuilder.setPageNum(pageInfo.getPageNum());
        respBuilder.setPageSize(pageInfo.getPageSize());
        respBuilder.setSize(pageInfo.getSize());
        respBuilder.setSize(pageInfo.getSize());
        respBuilder.setEndRow(pageInfo.getEndRow());
        respBuilder.setTotal(pageInfo.getTotal());
        respBuilder.setPages(pageInfo.getPages());
        List<SalesReturnOrder> salesReturnOrderList = pageInfo.getList();
        if (salesReturnOrderList != null) {
            for (SalesReturnOrder salesReturnOrder : salesReturnOrderList) {
                SalesOrderReturnServiceStructure.SalesReturn.Builder salesReturnBuilder = SalesOrderReturnServiceStructure.SalesReturn.newBuilder();
                salesReturnBuilder.setSalesReturnedOrderId(salesReturnOrder.getSalesReturnedOrderId());
                salesReturnBuilder.setReturnedOrderStatus(salesReturnOrder.getReturnedOrderStatus());
                salesReturnBuilder.setReturnedType(salesReturnOrder.getReturnedType());
                salesReturnBuilder.setSalesReturnedOrderNo(salesReturnOrder.getSalesReturnedOrderNo());
                salesReturnBuilder.setOriginalGongxiaoOutboundOrderNo(salesReturnOrder.getOriginalGongxiaoOutboundOrderNo());
                salesReturnBuilder.setSenderName(salesReturnOrder.getSenderName());
                salesReturnBuilder.setOriginalSalesOrderNo(salesReturnOrder.getOriginalSalesOrderNo());
                salesReturnBuilder.setOriginalOutboundWarehouseName(salesReturnOrder.getOriginalOutboundWarehouseName() != null ? salesReturnOrder.getOriginalOutboundWarehouseName() : "");
                salesReturnBuilder.setTargetWarehouseName(salesReturnOrder.getTargetWarehouseName());
                salesReturnBuilder.setDataVersion(salesReturnOrder.getDataVersion());
                salesReturnBuilder.setLastUpdateTime(salesReturnOrder.getLastUpdateTime() != null ? salesReturnOrder.getLastUpdateTime().getTime() : 0);
                salesReturnBuilder.setCreateTime(salesReturnOrder.getCreateTime() != null ? salesReturnOrder.getCreateTime().getTime() : 0);
                SalesOrderReturnServiceStructure.SalesReturn salesReturn = salesReturnBuilder.build();
                respBuilder.addList(salesReturn);
            }
        }
    }

    /**
     * 获取退货各状态的统计数据
     */
    @Override
    public void selectClassificationCount(SalesOrderReturnServiceStructure.SelectClassificationCountReq request, StreamObserver<SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp> responseObserver) {

        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        Long projectId = request.getProjectId();
        Integer returnedType = request.getReturnedType();
        String salesReturnedOrderNo = request.getSalesReturnedOrderNo();
        String warehouseId = request.getWarehouseId();
        String timeStart = request.getTimeStart();
        String timeEnd = request.getTimeEnd();

        logger.info("#traceId={}# [IN][getsSalesReturnOrderByProject] params: projectId={}, returnedType={}, salesReturnedOrderNo={}, warehouseId={}, timeStart={}, timeEnd={} "
                , rpcHeader.getTraceId(), projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd);

        Integer count = 0;
        List<SalesReturnClassificationCount> returnList = null;
        List<SalesReturnClassificationCount> result = null;
        try {
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            count = salesReturnOrderDao.selectSalesReturnedOrderCount(prefix, projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd); // 总数量
            result = salesReturnOrderDao.selectClassificationCount(prefix, projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd); // 各状态的数量
            returnList = new ArrayList<>();
            for (SalesReturnStatus salesReturnStatus : SalesReturnStatus.values()) {
                SalesReturnClassificationCount salesCount = new SalesReturnClassificationCount();
                salesCount.setStatus(salesReturnStatus.getCode());
                if (SalesReturnStatus.RETURN_ORDER_STATUS_ALL.getCode() == salesReturnStatus.getCode()) {
                    salesCount.setCount(count);
                } else {
                    salesCount.setCount(0);
                    for (SalesReturnClassificationCount salesReturnCount : result) {
                        if (salesReturnStatus.getCode() == salesReturnCount.getStatus()) {
                            salesCount.setCount(salesReturnCount.getCount());
                        }
                    }
                }
                returnList.add(salesCount);
            }
            // 数据转换并返回
            SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp salesReturnClassificationCountResp;
            SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp.Builder respBuilder = SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp.newBuilder();
            for (SalesReturnClassificationCount salesReturnClassificationCount : returnList) {
                SalesOrderReturnServiceStructure.SalesReturnClassificationCount.Builder returnClassificationCountBuilder = SalesOrderReturnServiceStructure.SalesReturnClassificationCount.newBuilder();
                returnClassificationCountBuilder.setStatus(salesReturnClassificationCount.getStatus());
                returnClassificationCountBuilder.setCount(salesReturnClassificationCount.getCount());
                SalesOrderReturnServiceStructure.SalesReturnClassificationCount returnClassificationCount = returnClassificationCountBuilder.build();
                respBuilder.addCountList(returnClassificationCount);
            }
            salesReturnClassificationCountResp = respBuilder.build();
            responseObserver.onNext(salesReturnClassificationCountResp);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get selectClassificationCount success: result.zize={}", rpcHeader.getTraceId(), result.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 创建新的销售退货单
     */
    @Override
    public void saveSalesReturnOrder(SalesOrderReturnServiceStructure.SalesReturnOrderReq request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        long projectId = request.getProjectId();
        GongxiaoRpc.RpcResult.Builder respBuilder = GongxiaoRpc.RpcResult.newBuilder();
        GongxiaoRpc.RpcResult rpcResult;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        SalesOrderReturnServiceStructure.SalesReturnOrder salesReturn = request.getSalesReturnOrder();
        String originalSalesOrderNo = salesReturn.getOriginalSalesOrderNo();
        SalesReturnOrder salesReturnOrder = new SalesReturnOrder();
        salesReturnOrder.setProjectId(salesReturn.getProjectId());// 项目ID
        salesReturnOrder.setProjectName(salesReturn.getProjectName());       // 项目名称
        salesReturnOrder.setOriginalSalesOrderNo(salesReturn.getOriginalSalesOrderNo()); // 原销售单号
        salesReturnOrder.setOriginalGongxiaoOutboundOrderNo(salesReturn.getOriginalGongxiaoOutboundOrderNo()); // 原销售单的出库单号
        salesReturnOrder.setTargetWarehouseId(salesReturn.getTargetWarehouseId());// 退货目标仓库ID(
        salesReturnOrder.setTargetWarehouseName(salesReturn.getTargetWarehouseName());// 退货目标仓库名称
        salesReturnOrder.setOriginalOutboundWarehouseId(salesReturn.getOriginalOutboundWarehouseId());// 发货仓库ID(
        salesReturnOrder.setOriginalOutboundWarehouseName(salesReturn.getOriginalOutboundWarehouseName());// 发货仓库名称
        salesReturnOrder.setSenderName(salesReturn.getSenderName());// 发件人名字
        salesReturnOrder.setSenderMobile(salesReturn.getSenderMobile());// 收件人手机号
        salesReturnOrder.setLogisticsType(salesReturn.getLogisticsType());// 运输方式
        salesReturnOrder.setFreightDouble(salesReturn.getFreight());// 运费
        salesReturnOrder.setFreight((long) (salesReturn.getFreight() * FXConstant.HUNDRED));
        salesReturnOrder.setFreightPaidBy(salesReturn.getFreightPaidBy());// 运费承担方
        salesReturnOrder.setLogisticsOrderNo(salesReturn.getLogisticsOrderNo()); // 物流单号
        salesReturnOrder.setLogisticsCompany(salesReturn.getLogisticsCompany());// 物流公司名称
        salesReturnOrder.setProvinceId(salesReturn.getProvinceId());
        salesReturnOrder.setProjectName(salesReturn.getProvinceName());
        salesReturnOrder.setCityId(salesReturn.getCityId());
        salesReturnOrder.setCityName(salesReturn.getCityName());
        salesReturnOrder.setDistrictId(salesReturn.getDistrictId());
        salesReturnOrder.setDistrictName(salesReturn.getDistrictName());
        salesReturnOrder.setSenderAddressItem(salesReturn.getSenderAddressItem());
        salesReturnOrder.setWarehouseAddress(salesReturn.getWarehouseAddress());
        List<SalesOrderReturnServiceStructure.SalesReturnOrderItem> itemList = request.getItemListList();
        try {
            logger.info("#traceId={}# [IN][addSalesReturnOrder] params: salesReturnOrder={} ", rpcHeader.getTraceId(), salesReturnOrder);
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            // 缓存map<货品编码,销售单货品明细>
            Map<String, SalesOrderItem> salesItemMap = new HashMap<String, SalesOrderItem>(16);

            // 数据校验
            // 查询销售明细的可退货数量
            for (SalesOrderReturnServiceStructure.SalesReturnOrderItem item : itemList) {
                String productCode = item.getProductCode();
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, originalSalesOrderNo, productCode);
                int deliveredQuantity = salesOrderItem.getDeliveredQuantity();
                int returnedQuantity = salesOrderItem.getReturnedQuantity();
                int totalReturnedQuantity = item.getTotalReturnedQuantity();
                if (deliveredQuantity - returnedQuantity < totalReturnedQuantity) {
                    // 如果已送达数量 -  已退货数量 < 本次退货数量,无法退货
                    logger.info("#traceId={}# [OUT] get addSalesReturnOrder FAILED", rpcHeader.getTraceId());
                    rpcResult = GrpcCommonUtil.fail(ErrorCode.CAN_RETURN_QUANTITY_SCANTY);
                    responseObserver.onNext(rpcResult);
                    responseObserver.onCompleted();
                }
                salesItemMap.put(productCode, salesOrderItem);
            }
            // 对销售退货单数据封装
            salesReturnOrder.setReturnedType(SalesReturnOrderType.RETURN_ORDER_TYPE_SALES.getCode());// 退货单类型 销售退货
            salesReturnOrder.setReturnedOrderStatus(SalesReturnStatus.RETURN_ORDER_STATUS_NEW.getCode());// 退货单状态  新建待审
            salesReturnOrder.setSalesReturnedOrderNo(DateTimeIdGenerator.nextId(prefix, BizNumberType.SALES_RETURN_ORDER_NO));// 退货单号
            // 封装创建人信息
            salesReturnOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            salesReturnOrder.setCreatedByName(rpcHeader.getUsername());
            salesReturnOrder.setCreateTime(new Date());
            salesReturnOrder.setDataVersion(0);
            // 封装退货单明细
            List<SalesReturnOrderItem> salesReturnOrderItemList = new ArrayList<>();
            for (SalesOrderReturnServiceStructure.SalesReturnOrderItem item : itemList) {
                SalesReturnOrderItem salesReturnOrderItem = new SalesReturnOrderItem();
                salesReturnOrderItem.setProductId(item.getProductId());
                String productCode = item.getProductCode();
                salesReturnOrderItem.setProductCode(productCode);
                salesReturnOrderItem.setProductName(item.getProductName());
                salesReturnOrderItem.setProductUnit(item.getProductUnit());
                salesReturnOrderItem.setReturnCause(item.getReturnCause());
                salesReturnOrderItem.setTotalReturnedQuantity(item.getTotalReturnedQuantity());
                salesReturnOrderItem.setWarehouseId(item.getWarehouseId());
                salesReturnOrderItem.setWarehouseName(item.getWarehouseName());
                salesReturnOrderItem.setInventoryType(item.getInventoryType());
                salesReturnOrderItemList.add(salesReturnOrderItem);
                SalesOrderItem salesOrderItem = salesItemMap.get(productCode);
                int returnedQuantity = salesOrderItem.getReturnedQuantity() + salesReturnOrderItem.getTotalReturnedQuantity();
                // 修改销售退货数量
                salesOrderItemDao.updateReturnedQuantity(prefix, salesOrderItem.getSalesOrderItemId(), salesOrderItem.getDataVersion(),
                        returnedQuantity);
            }
            int totalReturnedQuantity = 0;
            // 逐条处理明细数据并保存
            for (SalesReturnOrderItem salesReturnOrderItem : salesReturnOrderItemList) {
                // 计算退货数量总和
                totalReturnedQuantity += salesReturnOrderItem.getTotalReturnedQuantity();
                // 封装创建人信息
                salesReturnOrderItem.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                salesReturnOrderItem.setCreatedByName(rpcHeader.getUsername());
                salesReturnOrderItem.setCreateTime(new Date());
                salesReturnOrderItem.setSalesReturnedOrderNo(salesReturnOrder.getSalesReturnedOrderNo());// 销售退货单号
                salesReturnOrderItem.setDataVersion(0L);
                try {
                    // 保存退货单明细
                    salesReturnOrderItemDao.addSalesReturnOrderItem(prefix, salesReturnOrderItem);
                } catch (Exception e) {
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                    throw e;
                }
            }
            salesReturnOrder.setTotalReturnedQuantity(totalReturnedQuantity);
            // 查询销售单信息
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesReturnOrder.getOriginalSalesOrderNo());
            // 品牌ID
            salesReturnOrder.setBrandId(salesOrder.getBrandId());
            // 品牌名称
            salesReturnOrder.setBrandName(salesOrder.getBrandName());
            // 供应商Id
            salesReturnOrder.setSupplierId(salesOrder.getSupplierId());
            // 供应商名称
            salesReturnOrder.setSupplierName(salesOrder.getSupplierName());
            // 经销商Id
            salesReturnOrder.setDistributorId(salesOrder.getDistributorId());
            // 经销商名称
            salesReturnOrder.setDistributorName(salesOrder.getDistributorName());
            salesReturnOrder.setFreight(salesReturnOrder.getFreightDouble() != null ? Math.round(salesReturnOrder.getFreightDouble() * FXConstant.HUNDRED_DOUBLE) : 0L);
            salesReturnOrder.setOriginalOutboundWarehouseId(salesOrder.getWarehouseId() + "");// 发货时仓库ID
            salesReturnOrder.setOriginalOutboundWarehouseName(salesOrder.getWarehouse());// 发货时仓库名称

            TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), SalesReturnStatus.RETURN_ORDER_STATUS_NEW.getCode(), SalesReturnStatus.RETURN_ORDER_STATUS_NEW.getMessage());
            salesReturnOrder.setTracelog(TraceLogUtil.appendTraceLog(null, traceLog));
            // 保存退货单
            int result = salesReturnOrderDao.addSalesReturnOrder(prefix, salesReturnOrder);
            if (result == 1) {
                logger.info("#traceId={}# [OUT] get addSalesReturnOrder success: salesReturnedOrderId={}", rpcHeader.getTraceId(), salesReturnOrder.getSalesReturnedOrderId());
            } else {
                logger.error("#traceId={}# [OUT] FAILED to addSalesReturnOrder:  result={}", rpcHeader.getTraceId(), result);
            }
            // 更新销售单退货数量
            int salesOrderTotalReturnedQuantity = salesOrder.getReturnedQuantity() + totalReturnedQuantity;
            salesOrderDao.updateReturnedQuantity(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), salesOrderTotalReturnedQuantity);

            // 前往仓储模块修改退货数量
            LinkedList<ReturnItem> warehouseReturnItemList = new LinkedList<>();
            for (SalesReturnOrderItem salesReturnOrderItem : salesReturnOrderItemList) {
                ReturnItem warehouseReturnItem = new ReturnItem();
                warehouseReturnItem.setProjectId(projectId + "");
                warehouseReturnItem.setGongxiaoOutboundOrderNo(salesReturn.getOriginalGongxiaoOutboundOrderNo());
                warehouseReturnItem.setProductCode(salesReturnOrderItem.getProductCode());
                warehouseReturnItem.setQuantity(salesReturnOrderItem.getTotalReturnedQuantity());
                warehouseReturnItemList.add(warehouseReturnItem);
            }

            // 根据channelId查询channelToken
            String channelId = salesConfig.getChannelId();
            //调用基础模块的SourceChannel服务
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(channelId + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            GongxiaoResult gongxiaoResult = XpsWarehouseManager.modifyReturnQuantity(salesConfig.getWarehouseUrl(), channelId, xpsChannelSecret,
                    warehouseReturnItemList);
            if (gongxiaoResult == null || gongxiaoResult.getReturnCode() != 0) {
                logger.error("FAILED to inform warehouse! url={}, channelId={}, channelToken={}, itemList={}",
                        salesConfig.getWarehouseUrl(), salesConfig.getChannelId(), xpsChannelSecret, JSON.toJSONString(warehouseReturnItemList));
            }
            rpcResult = respBuilder.build();
            responseObserver.onNext(rpcResult);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 越海商务 审核退货单(支持批量审核操作)
     */
    @Override
    public void checkSalesReturnOrder(SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        long projectId = request.getProjectId();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        List<SalesOrderReturnServiceStructure.CheckSalesReturnOrder> checkSalesReturnOrderList = request.getCheckListList();
        logger.info("#traceId={}# [IN][checkSalesReturnOrder] params: checkSalesReturnOrderList.size={} ", rpcHeader.getTraceId(), checkSalesReturnOrderList.size());
        // 0.查询表前缀
        String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

        GongxiaoRpc.RpcResult.Builder rpcBuilder = GongxiaoRpc.RpcResult.newBuilder();
        GongxiaoRpc.RpcResult rpcResult;
        for (SalesOrderReturnServiceStructure.CheckSalesReturnOrder checkSalesReturnOrder : checkSalesReturnOrderList) {
            ErrorCode errorCode = checkSalesReturnOrder(prefix, rpcHeader, checkSalesReturnOrder);
            if (errorCode == ErrorCode.SUCCESS) {
                logger.info("#traceId={}# checkSalesReturnOrder success: salesReturnedOrderId={}", rpcHeader.getTraceId(), checkSalesReturnOrder.getSalesReturnedOrderId());
            } else {
                logger.error("#traceId={}# fail to checkSalesReturnOrder: salesReturnOrderId={}", rpcHeader.getTraceId(), checkSalesReturnOrder.getSalesReturnedOrderId());
            }
        }
        rpcResult = rpcBuilder.build();
        responseObserver.onNext(rpcResult);
        responseObserver.onCompleted();

    }


    /**
     * 单个处理退货审核
     */
    private ErrorCode checkSalesReturnOrder(String prefix, GongxiaoRpc.RpcHeader rpcHeader, SalesOrderReturnServiceStructure.CheckSalesReturnOrder checkSalesReturnOrder) {
        logger.info("#traceId={}# [IN][checkSalesReturnOrder] params: salesReturnOrder={} ", rpcHeader.getTraceId(), checkSalesReturnOrder);
        long dateVersion = checkSalesReturnOrder.getDataVersion();
        // 1. 更新状态
        int result = updateSalesReturnOrderStatus(prefix, rpcHeader, checkSalesReturnOrder.getSalesReturnedOrderId(), dateVersion);
        if (result != 1) {
            logger.error("updateSalesReturnOrderStatus faild!#traceId=" + rpcHeader.getTraceId());
            return ErrorCode.UNKNOWN_ERROR;
        }
        // 查询退货单
        SalesReturnOrder returnOrder = salesReturnOrderDao.selectById(prefix, checkSalesReturnOrder.getSalesReturnedOrderId());
        // 查询退货单明细列表
        List<SalesReturnOrderItem> itemList = salesReturnOrderItemDao.selectBySalesReturnedOrderNo(prefix, returnOrder.getSalesReturnedOrderNo());
        // 2. 调用仓储模块 创建预约入库单
        GongxiaoResult gongxiaoResult = createInboundOrder(rpcHeader, returnOrder, itemList);
        if (ErrorCode.SUCCESS.getErrorCode() != gongxiaoResult.getReturnCode()) {
            logger.error("createInboundOrder faild!#traceId=" + rpcHeader.getTraceId());
            int returnCode = gongxiaoResult.getReturnCode();
            return ErrorCode.getErrorCodeByCode(returnCode);
        }
        // 仓储模块返回的入库单号
        String inboundOrderNo = (String) gongxiaoResult.getData();
        // 3. 把入库单号更新到退货汇总单
        int count = salesReturnOrderDao.updateInboundOrderNo(prefix, inboundOrderNo, returnOrder.getSalesReturnedOrderId(), (long) returnOrder.getDataVersion());
        // 4.  把入库单号更新到退货明细
        for (SalesReturnOrderItem item : itemList) {
            salesReturnOrderItemDao.updateInboundOrderNo(prefix, inboundOrderNo, item.getRowId(), item.getDataVersion());
        }
        logger.info("#traceId={}# [OUT] get checkSalesReturnOrder success: result={}", rpcHeader.getTraceId(), result);
        return ErrorCode.SUCCESS;
    }

    /**
     * 去仓储模块 创建退货入库单
     */
    private GongxiaoResult createInboundOrder(GongxiaoRpc.RpcHeader rpcHeader, SalesReturnOrder returnOrder, List<SalesReturnOrderItem> itemList) {
        logger.info("#traceId={}# [IN][createInboundOrder] params: returnOrder={} ,itemList={}", rpcHeader.getTraceId(), returnOrder, itemList);
        String sourceChannelId = null;
        String projectId = String.valueOf(returnOrder.getProjectId());           // 项目id
        String traceNo = returnOrder.getSalesReturnedOrderNo();              // 调用的发起方携带的标签
        String senderName = returnOrder.getSenderName();// 发件人名字
        String senderPhoneNo = returnOrder.getSenderMobile();        // 发件人联系电话
        String warehouseId = returnOrder.getTargetWarehouseId();//        入库目标仓库id
        String warehouseName = returnOrder.getTargetWarehouseName();//      入库目标仓库名称
        String deliverAddress = returnOrder.getProvinceName() + returnOrder.getCityName() + returnOrder.getDistrictName() + returnOrder.getSenderAddressItem();//    发货地址
        Integer shippingMode = returnOrder.getLogisticsType();// 物流方式
        String logisticsCompanyName = returnOrder.getLogisticsCompany(); // 物流公司的名字
        String logisticsNo = returnOrder.getLogisticsOrderNo();//         物流号
        String note = "";//                备注
        Integer totalQuantity = returnOrder.getTotalReturnedQuantity();//        各种类商品本次入库数量总和
        String signature = "";//           签名
        List<PlanInboundOrderItem> inboundItemList = new ArrayList<>(); // 货品明细
        // 遍历退货明细生成预约入库明细
        for (SalesReturnOrderItem item : itemList) {
            PlanInboundOrderItem inboundItem = new PlanInboundOrderItem();
            inboundItem.setProjectId(String.valueOf(returnOrder.getProjectId()));                // 项目id
            inboundItem.setPurchaseOrderNo(returnOrder.getOriginalSalesOrderNo());           // 采购单号|销售单号
            inboundItem.setWarehouseId(returnOrder.getTargetWarehouseId());              // 仓库ID
            inboundItem.setWarehouseName(returnOrder.getTargetWarehouseName());            // 仓库名称
            inboundItem.setBrandId(String.valueOf(returnOrder.getBrandId()));                  // 品牌id
            inboundItem.setBrandName(returnOrder.getBrandName());                // 品牌商
            inboundItem.setProjectId(item.getProductId());
            ;                // 商品id
            inboundItem.setProductCode(item.getProductCode());             // 商品编码
            inboundItem.setProductName(item.getProductName());             // 商品名称
            inboundItem.setProductUnit(item.getProductUnit());             // 商品单位
            inboundItem.setTotalQuantity(item.getTotalReturnedQuantity());              // 预约入库数量
            inboundItem.setInventoryType(item.getInventoryType());// 库存类型
            inboundItemList.add(inboundItem);
        }
        // 0.查询表前缀
        String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, Long.parseLong(projectId));

        // 调用仓储模块
        try {
            // 根据channelId查询channelToken
            String channelId = salesConfig.getChannelId();
            //调用基础模块的SourceChannel服务
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(channelId + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            String saleUniqueNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.SALE_UNIQUE_NO);
            GongxiaoResult gongxiaoResult = XpsWarehouseManager.createInboundOrder2(
                    salesConfig.getWarehouseUrl(),
                    channelId,
                    xpsChannelSecret,
                    returnOrder.getSalesReturnedOrderNo(),
                    sourceChannelId,
                    WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode(),
                    projectId,
                    traceNo,
                    senderName,
                    senderPhoneNo,
                    warehouseId,
                    warehouseName,
                    deliverAddress,
                    shippingMode,
                    logisticsCompanyName,
                    logisticsNo,
                    note,
                    totalQuantity,
                    inboundItemList,
                    signature,
                    saleUniqueNo);

            if (ErrorCode.SUCCESS.getErrorCode() != gongxiaoResult.getReturnCode()) {
                logger.error("createInboundOrder faild!#traceId=" + rpcHeader.getTraceId());
                return gongxiaoResult;
            }
            logger.info("#traceId={}# [OUT] get createInboundOrder success: result={}", rpcHeader.getTraceId());
            return gongxiaoResult;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            return GongxiaoResult.newResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(), ErrorCode.UNKNOWN_ERROR.getMessage());
        }


    }

    /**
     * 更新销售退货单状态为等待退货入仓
     *
     * @param salesReturnedOrderId
     * @param dataVersion
     * @return
     */
    private int updateSalesReturnOrderStatus(String prefix, GongxiaoRpc.RpcHeader rpcHeader, Long salesReturnedOrderId, Long dataVersion) {
        logger.info("#traceId={}# [IN][checkSalesReturnOrder] params: salesReturnedOrderId={} ,dataVersion={}", rpcHeader.getTraceId(), salesReturnedOrderId, dataVersion);
        SalesReturnOrder salesReturnOrder = salesReturnOrderDao.selectById(prefix, salesReturnedOrderId);
        TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), SalesReturnStatus.RETURN_ORDER_STATUS_NOT_IN_STORAGE.getCode(), SalesReturnStatus.RETURN_ORDER_STATUS_NOT_IN_STORAGE.getMessage());
        String traceLogs = TraceLogUtil.appendTraceLog(salesReturnOrder.getTracelog(), traceLog);
        int result = salesReturnOrderDao.updateStatus(prefix, salesReturnedOrderId, SalesReturnStatus.RETURN_ORDER_STATUS_NOT_IN_STORAGE.getCode(), dataVersion, traceLogs);
        logger.info("#traceId={}# [OUT] get checkSalesReturnOrder success: salesReturnedOrderId={}, result={}", rpcHeader.getTraceId(), salesReturnedOrderId, result);
        return result;

    }

    /**
     * 仓储模块退货入库后, 回告jweb_xps_warehouse_notify_gateway, 然后jweb_xps_warehouse_notify_gateway回告业务层的入口
     */
    @Override
    public void salesReturnInbound(SalesOrderReturnServiceStructure.SalesReturnInboundReq request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        long projectId = request.getProjectId();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String inboundOrderNo = request.getInboundOrderNo();
        String traceNo = request.getTraceNo();
        String productCode = request.getProductCode();
        String productName = request.getProductName();
        String productUnit = request.getProductUnit();
        int inStockQuantity = request.getInStockQuantity();
        GongxiaoRpc.RpcResult.Builder rpcBuilder = GongxiaoRpc.RpcResult.newBuilder();
        GongxiaoRpc.RpcResult rpcResult;
        logger.info("#traceId={}# [IN][salesReturnInbound] params: inboundOrderNo={}, traceNo={}, productCode={}, productName={}, inStockQuantity={} ",
                rpcHeader.getTraceId(), inboundOrderNo, traceNo, productCode, productName, productUnit, inStockQuantity);
        try {
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            // 按入库单号查询退货单
            SalesReturnOrder salesReturnOrder = salesReturnOrderDao.selectSalesReturnOrderByInBoundOrderNo(prefix, inboundOrderNo);
            // 按入库单号&产品编码查询退货单详情
            SalesReturnOrderItem salesReturnOrderItem = salesReturnOrderItemDao.selectSalesReturnOrderItemByInBoundOrderNo(prefix, inboundOrderNo, productCode);
            // 更新退货单详情（退货入库数量&wms入库记录）
            salesReturnOrderItem.setTotalInStockQuantity(salesReturnOrderItem.getTotalInStockQuantity() + inStockQuantity);

            // 组装历史入库记录
            List<String> strList;
            String currentJson = salesReturnOrderItem.getWmsInboundRecord();
            if (currentJson != null) {
                strList = JSON.parseObject(currentJson, new TypeReference<List<String>>() {
                });
            } else {
                strList = new ArrayList<>();
            }
            strList.add(inboundOrderNo);

            // 1. 更新退货单明细的入库记录
            salesReturnOrderItemDao.updateInboundRecord(prefix, salesReturnOrderItem.getTotalInStockQuantity(), JSON.toJSONString(strList), salesReturnOrderItem.getRowId(), salesReturnOrderItem.getDataVersion());
            // 查询销售单
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesReturnOrder.getOriginalSalesOrderNo());
            // 查询销售单明细
            SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, salesReturnOrder.getOriginalSalesOrderNo(), productCode);
            // 计算本次退货总金额  总金额/总数量*入库数量
            Long totalAmount = salesOrderItem.getTotalOrderAmount() / salesOrderItem.getTotalQuantity() * inStockQuantity;
            // 计算本次退货总金额在销售单中占比
            Double inboundAmountScale = totalAmount / salesOrder.getTotalOrderAmount() * 0.00;
            // 入库应归还返利
            Long returnedCoupon = Math.round(salesOrder.getCouponAmount() * inboundAmountScale);
            // 入库应归还代垫
            Long returnedPrepaid = Math.round(salesOrder.getPrepaidAmount() * inboundAmountScale);
            // 入库应归还预存现金 现金+预存
            Long returnedCash = Math.round(salesOrder.getCashAmount() * inboundAmountScale) + Math.round(salesOrder.getPrestoredAmount() * inboundAmountScale);
            // 已退还返利
            Long returnedCouponAmount = salesReturnOrder.getReturnedCouponAmount() + returnedCoupon;
            // 已退还代垫
            Long returnedPrepaidAmount = salesReturnOrder.getReturnedPrepaidAmount() + returnedPrepaid;
            // 已退还现金
            Long returnedCashAmount = salesReturnOrder.getReturnedCashAmount() + returnedCash;
            TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "退货到仓");
            String traceLogs = TraceLogUtil.appendTraceLog(salesReturnOrder.getTracelog(), traceLog);
            salesReturnOrder.setInStockQuantity(salesReturnOrder.getInStockQuantity() + inStockQuantity);
            if (salesReturnOrder.getInStockQuantity() >= salesReturnOrder.getTotalReturnedQuantity()) {
                traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), SalesReturnStatus.RETURN_ORDER_STATUS_FINISH.getCode(), SalesReturnStatus.RETURN_ORDER_STATUS_FINISH.getMessage());
                traceLogs = TraceLogUtil.appendTraceLog(traceLogs, traceLog);
            }
            // 2. 更新退货单（归还返利/归还代垫/归还现金）
            salesReturnOrderDao.updateInboundRecord(prefix, returnedCouponAmount, returnedPrepaidAmount, returnedCashAmount,
                    salesReturnOrder.getInStockQuantity(), salesReturnOrder.getSalesReturnedOrderId(), traceLogs, (long) salesReturnOrder.getDataVersion(),
                    SalesReturnStatus.RETURN_ORDER_STATUS_FINISH.getCode());

            // 3. 更新账户余额: 现金/返利/代垫
            DistributorAccountServiceStructure.PayForSalesOrderRequest accountReq = DistributorAccountServiceStructure.PayForSalesOrderRequest.newBuilder()
                    .setCurrencyCode(salesOrder.getCurrencyCode())
                    .setDistributorId(salesReturnOrder.getDistributorId())
                    .setProjectId(salesReturnOrder.getProjectId())
                    .setCouponAmount(returnedCouponAmount)
                    .setPrepaidAmount(returnedPrepaidAmount)
                    .setCashAmount(returnedCashAmount)
                    .setSalesOrderNo(salesOrder.getSalesOrderNo())
                    .setTransactionTime(DateUtil.getTime(new Date()))
                    .build();
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub accountStub = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            DistributorAccountServiceStructure.PayForSalesOrderResponse accountResp = accountStub.returnForSalesReturnOrder(accountReq);
            if (accountResp.getReturnCode() != ErrorCode.SUCCESS.getErrorCode()) {
                logger.error("#traceId={}# update distributor account faild: salesReturnedOrderNo={} returnedCouponAmount={} returnedPrepaidAmount={} returnedCashAmoun={} returnCode={}",
                        rpcHeader.getTraceId(), salesReturnOrder.getSalesReturnedOrderNo(), returnedCouponAmount, returnedPrepaidAmount, returnedCashAmount, accountResp.getReturnCode());
            }
            // 4. 更新销售汇总单和明细单的退货数量
            salesOrder.setReturnedQuantity(salesOrder.getReturnedQuantity() + inStockQuantity);
            salesOrderItem.setReturnedQuantity(salesOrderItem.getReturnedQuantity() + inStockQuantity);
//            salesOrderDao.updateReturnedQuantity(prefix, salesOrder.getSalesOrderId(), (long) salesOrder.getReturnedQuantity(), salesOrder.getDataVersion().intValue());
//            salesOrderItemDao.updateReturnedQuantity(prefix, salesOrderItem.getSalesOrderItemId(), salesOrderItem.getDataVersion(), salesOrderItem.getReturnedQuantity());

            // 5. 由于销售单在退货前已生成应收代垫, 所以这里需要 生成金额为负的应收代垫 进行冲销
            SalesOrderItem orderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, salesReturnOrder.getOriginalSalesOrderNo(), productCode);
            Long backPrepaid = Math.round(orderItem.getGeneratedPrepaid() * inStockQuantity / 10000.0);
            if (backPrepaid > 0) {
                // 根据channelId查询channelToken
                String channelId = salesConfig.getChannelId();
                //调用基础模块的SourceChannel服务
                ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
                ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setXpsChannelId(channelId + "").build();
                ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
                ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
                String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

                GongxiaoResult gongxiaoResult = XpsPrepaidManager.generateYhglobalPrepaidLedger(salesConfig.getPrepaidUrl(),
                        salesConfig.getChannelId(), xpsChannelSecret, rpcHeader.getUid(), rpcHeader.getUsername(),
                        projectId, salesOrder.getCurrencyCode(), salesReturnOrder.getSalesReturnedOrderNo(), salesOrder.getProjectName(),
                        salesOrder.getSupplierId().intValue(), salesOrder.getSupplierName(), salesOrder.getSalesContactNo(),
                        -backPrepaid, new Date());
                if (gongxiaoResult.getReturnCode() != 0) {
                    logger.error("add prepaid FAILED! returnOrderNo={}, amount(long)={}", salesReturnOrder.getSalesReturnedOrderNo(), backPrepaid);
                }

                // 如果产生了应收毛利,此时产生负数的应收毛利做冲抵
                long shouldReceiveGrossProfit = Math.round(orderItem.getShouldReceiveGrossProfit() * inStockQuantity / 10000.0);
                long receivedGrossProfit = Math.round(orderItem.getReceivedGrossProfit() * inStockQuantity / 10000.0);
                if (shouldReceiveGrossProfit > 0) {
                    XpsGrossProfitManager.generateYhglobalGrossProfit(salesConfig.getGrossProfitUrl(),
                            channelId,
                            xpsChannelSecret,
                            rpcHeader.getUid(),
                            rpcHeader.getUsername(),
                            projectId, salesOrder.getProjectName(), salesOrder.getCurrencyCode(),
                            salesReturnOrder.getSalesReturnedOrderNo(),
                            -shouldReceiveGrossProfit,
                            salesReturnOrder.getSalesReturnedOrderNo(),
                            new Date(),
                            -receivedGrossProfit,
                            prefix
                    );
                }
            }
            rpcResult = rpcBuilder.build();
            responseObserver.onNext(rpcResult);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw new RuntimeException("WMS callback error!", e);
        }
    }

    /**
     * 获取指定退货单号的详情
     */
    @Override
    public void getSalesReturn(SalesOrderReturnServiceStructure.GetSalesReturnReq request, StreamObserver<SalesOrderReturnServiceStructure.SalesReturnResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        Long salesReturnedOrderId = request.getSalesReturnedOrderId();
        logger.info("#traceId={}# [IN][SalesReturnOrder] params: salesReturnedOrderId={} ", rpcHeader.getTraceId(), salesReturnedOrderId);
        try {
            // 0.查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            SalesOrderReturnServiceStructure.SalesReturnResp.Builder respBuilder = SalesOrderReturnServiceStructure.SalesReturnResp.newBuilder();
            SalesOrderReturnServiceStructure.SalesReturnResp salesReturnResp;
            SalesReturnOrder salesReturnOrder = salesReturnOrderDao.selectById(prefix, salesReturnedOrderId);
            if (salesReturnOrder == null) {
                logger.info("#traceId={}# [OUT] fail to getSalesReturn: SalesReturnOrder ={}", salesReturnOrder);
                throw new RuntimeException("No data can be found", new Throwable());
            }


            SalesOrderReturnServiceStructure.SalesReturnOrder.Builder salesReturnOrderBuilder = SalesOrderReturnServiceStructure.SalesReturnOrder.newBuilder();
            SalesOrderReturnServiceStructure.SalesReturnOrder salesReturn;
            salesReturnOrderBuilder.setSalesReturnedOrderNo(salesReturnOrder.getSalesReturnedOrderNo() != null ? salesReturnOrder.getSalesReturnedOrderNo() : "");// 退货订单号
            salesReturnOrderBuilder.setReturnedOrderStatus(salesReturnOrder.getReturnedOrderStatus());// 状态
            String createTime = "";
            if (salesReturnOrder.getCreateTime() != null) {
                try {
                    createTime = DateUtil.dateToString(salesReturnOrder.getCreateTime(), DateUtil.DATE_TIME_FORMAT);
                } catch (Exception e) {
                }
            }
            salesReturnOrderBuilder.setCreateTime(createTime);// 退单创建时间
            salesReturnOrderBuilder.setSenderName(salesReturnOrder.getSenderName() != null ? salesReturnOrder.getSenderName() : "");// 退货人
            salesReturnOrderBuilder.setProvinceId(salesReturnOrder.getProvinceId() != null ? salesReturnOrder.getProvinceId() : "");// 退货人地址
            salesReturnOrderBuilder.setProvinceName(salesReturnOrder.getProvinceName() != null ? salesReturnOrder.getProvinceName() : "");
            salesReturnOrderBuilder.setCityId(salesReturnOrder.getCityId() != null ? salesReturnOrder.getCityId() : "");
            salesReturnOrderBuilder.setCityName(salesReturnOrder.getCityName() != null ? salesReturnOrder.getCityName() : "");
            salesReturnOrderBuilder.setDistrictId(salesReturnOrder.getDistrictId() != null ? salesReturnOrder.getDistrictId() : "");
            salesReturnOrderBuilder.setDistrictName(salesReturnOrder.getDistrictName() != null ? salesReturnOrder.getDistrictName() : "");
            salesReturnOrderBuilder.setSenderAddressItem(salesReturnOrder.getSenderAddressItem() != null ? salesReturnOrder.getSenderAddressItem() : "");// 详细地址
            salesReturnOrderBuilder.setSenderMobile(salesReturnOrder.getSenderMobile() != null ? salesReturnOrder.getSenderMobile() : "");// 手机
            salesReturnOrderBuilder.setDistributorName(salesReturnOrder.getDistributorName() != null ? salesReturnOrder.getDistributorName() : "");// 退货公司名称
            salesReturnOrderBuilder.setLogisticsType(salesReturnOrder.getLogisticsType() != null ? salesReturnOrder.getLogisticsType() : 0);// 运输方式
            salesReturnOrderBuilder.setLogisticsCompany(salesReturnOrder.getLogisticsCompany() != null ? salesReturnOrder.getLogisticsCompany() : "");// 退回物流公司
            salesReturnOrderBuilder.setLogisticsOrderNo(salesReturnOrder.getLogisticsOrderNo() != null ? salesReturnOrder.getLogisticsOrderNo() : "");// 物流运单号
            salesReturnOrderBuilder.setTargetWarehouseName(salesReturnOrder.getTargetWarehouseName() != null ? salesReturnOrder.getTargetWarehouseName() : "");// 退回仓库名称
            salesReturnOrderBuilder.setFreightPaidBy(salesReturnOrder.getFreightPaidBy());// 运费承担方
            salesReturnOrderBuilder.setFreight(salesReturnOrder.getFreight());// 运费
            salesReturnOrderBuilder.setOriginalGongxiaoOutboundOrderNo(salesReturnOrder.getOriginalGongxiaoOutboundOrderNo() != null ? salesReturnOrder.getOriginalGongxiaoOutboundOrderNo() : "");// 发货单编号
            salesReturnOrderBuilder.setProjectName(salesReturnOrder.getProjectName() != null ? salesReturnOrder.getProjectName() : "");// 项目名称
            salesReturnOrderBuilder.setReturnedType(salesReturnOrder.getReturnedType());// 退货单类型
            salesReturnOrderBuilder.setCreatedByName(salesReturnOrder.getCreatedByName() != null ? salesReturnOrder.getCreatedByName() : "");// 退货单创建人
            String lastUpdateTime = "";
            if (salesReturnOrder.getLastUpdateTime() != null) {
                try {
                    lastUpdateTime = DateUtil.dateToString(salesReturnOrder.getLastUpdateTime(), DateUtil.DATE_TIME_FORMAT);
                } catch (Exception e) {
                }
            }
            salesReturnOrderBuilder.setLastUpdateTime(lastUpdateTime);// 状态更新时间
            salesReturnOrderBuilder.setTracelog(salesReturnOrder.getTracelog() != null ? salesReturnOrder.getTracelog() : "");// 操作日志
            salesReturn = salesReturnOrderBuilder.build();

            respBuilder.setSalesReturnOrder(salesReturn);
            // 退货单明细
            List<SalesReturnOrderItem> itemList = salesReturnOrderItemDao.selectBySalesReturnedOrderNo(prefix, salesReturnOrder.getSalesReturnedOrderNo());
            for (SalesReturnOrderItem returnOrderItem : itemList) {
                SalesOrderReturnServiceStructure.SalesReturnOrderItem.Builder salesReturnOrderItemBuilder = SalesOrderReturnServiceStructure.SalesReturnOrderItem.newBuilder();
                SalesOrderReturnServiceStructure.SalesReturnOrderItem salesReturnItem;
                // 查询销售详情
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(prefix, salesReturnOrder.getOriginalSalesOrderNo(), returnOrderItem.getProductCode());
                salesReturnOrderItemBuilder.setProductName(returnOrderItem.getProductName() != null ? returnOrderItem.getProductName() : "");// 货品名称
                salesReturnOrderItemBuilder.setProductCode(returnOrderItem.getProductCode() != null ? returnOrderItem.getProductCode() : "");// 货品编码
                salesReturnOrderItemBuilder.setTotalReturnedQuantity(returnOrderItem.getTotalReturnedQuantity()); // 退货数量
                salesReturnOrderItemBuilder.setTotalInStockQuantity(returnOrderItem.getTotalInStockQuantity());// 仓库实收
                salesReturnOrderItemBuilder.setReturnCause(returnOrderItem.getReturnCause() != null ? returnOrderItem.getReturnCause() : "");// 退货原因
                if (salesOrderItem != null) {
                    salesReturnOrderItemBuilder.setCurrencyCode(salesOrderItem.getCurrencyCode());// 货币编码
                    salesReturnOrderItemBuilder.setCurrencyName(salesOrderItem.getCurrencyName());// 货币名称
                    salesReturnOrderItemBuilder.setWholesalePrice(Double.valueOf(salesOrderItem.getWholesalePrice() / FXConstant.MILLION_DOUBLE));// 出货价
                    salesReturnOrderItemBuilder.setTotalQuantity(salesOrderItem.getTotalQuantity());// 原始数量
                    salesReturnOrderItemBuilder.setReturnAmount(Double.valueOf(salesOrderItem.getWholesalePrice() * returnOrderItem.getTotalReturnedQuantity() / FXConstant.HUNDRED_DOUBLE));// 商品退款金额=出货价*数量
                }
                salesReturnItem = salesReturnOrderItemBuilder.build();
                respBuilder.addItemList(salesReturnItem);
            }
            salesReturnResp = respBuilder.build();
            responseObserver.onNext(salesReturnResp);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get getSalesReturn success}", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
        super.getSalesReturn(request, responseObserver);
    }
}
