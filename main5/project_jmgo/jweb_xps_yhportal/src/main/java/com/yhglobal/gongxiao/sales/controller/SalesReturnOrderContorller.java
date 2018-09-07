package com.yhglobal.gongxiao.sales.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.constant.warehouse.OutboundOrderStatusEnum;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.sales.entity.*;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.DateUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrder;
import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Description: 销售退货contorller
 * @Author: LUOYI
 * @Date: Created in 11:00 2018/3/21
 */

@Controller
@RequestMapping("/sales/return")
public class SalesReturnOrderContorller {


    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    private static Logger logger = LoggerFactory.getLogger(SalesReturnOrderContorller.class);

    /**
     * "销售管理 > 销售退换货管理" 获取退换货单列表
     */
    @RequestMapping("/getsByProjectId")
    @ResponseBody
    public GongxiaoResult getsByProjectId(HttpServletRequest request, HttpServletResponse response, Integer projectId,
                                          @RequestParam(defaultValue = "0") Integer returnedType,
                                          @RequestParam(defaultValue = "") String salesReturnedOrderNo,
                                          @RequestParam(defaultValue = "") String warehouseId,
                                          @RequestParam(defaultValue = "") String timeStart,
                                          @RequestParam(defaultValue = "") String timeEnd,
                                          @RequestParam(defaultValue = "0") Integer returnedOrderStatus,
                                          @RequestParam(required = true, defaultValue = "1") Integer page,
                                          @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsByProjectId] params: projectId={}, returnedType={}, salesReturnedOrderNo={}, warehouseId={}, timeStart={}, timeEnd={}, returnedOrderStatus={} "
                    , traceId, projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd, returnedOrderStatus);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            SalesOrderReturnServiceStructure.SearchOrderReturnReq rpcReq = SalesOrderReturnServiceStructure.SearchOrderReturnReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setReturnedType(returnedType)
                    .setSalesReturnedOrderNo(salesReturnedOrderNo)
                    .setWarehouseId(warehouseId)
                    .setTimeEnd(timeEnd)
                    .setTimeStart(timeStart)
                    .setReturnedType(returnedType)
                    .setPageNumber(page)
                    .setPageSize(pageSize)
                    .setReturnedOrderStatus(returnedOrderStatus)
                    .build();
            SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub.class);
            SalesOrderReturnServiceStructure.SalesReturnPageInfoResp rpcResponse = rpcStub.getsOrderReturn(rpcReq);

            //组装前端需要的数据类型
            PageInfo<SalesReturnOrder> pageInfo = new PageInfo<>();
            pageInfo.setPageNum(rpcResponse.getPageNum());
            pageInfo.setPageSize(rpcResponse.getPageSize());
            pageInfo.setSize(rpcResponse.getSize());
            pageInfo.setSize(rpcResponse.getSize());
            pageInfo.setEndRow(rpcResponse.getEndRow());
            pageInfo.setTotal(rpcResponse.getTotal());
            pageInfo.setPages(rpcResponse.getPages());
            List<SalesOrderReturnServiceStructure.SalesReturn> salesReturnList = rpcResponse.getListList();
            List<SalesReturnOrder> infoList = new ArrayList<>();
            for (SalesOrderReturnServiceStructure.SalesReturn salesReturn : salesReturnList) {
                SalesReturnOrder salesReturnOrder = new SalesReturnOrder();
                salesReturnOrder.setSalesReturnedOrderId(salesReturn.getSalesReturnedOrderId());
                salesReturnOrder.setReturnedOrderStatus(salesReturn.getReturnedOrderStatus());
                salesReturnOrder.setReturnedType(salesReturn.getReturnedType());
                salesReturnOrder.setSalesReturnedOrderNo(salesReturn.getSalesReturnedOrderNo());
                salesReturnOrder.setDistributorName(salesReturn.getSenderName());
                salesReturnOrder.setOriginalOutboundWarehouseName(salesReturn.getOriginalOutboundWarehouseName());
                salesReturnOrder.setTargetWarehouseName(salesReturn.getTargetWarehouseName());
                salesReturnOrder.setDataVersion(salesReturn.getDataVersion());
                salesReturnOrder.setOriginalSalesOrderNo(salesReturn.getOriginalSalesOrderNo());
                Date createTime = null;
                try {
                    createTime = DateUtil.long2Date(salesReturn.getCreateTime());
                } catch (Exception e) {
                }
                salesReturnOrder.setCreateTime(createTime);
                Date lastUpdateTime = null;
                try {
                    lastUpdateTime = DateUtil.long2Date(salesReturn.getLastUpdateTime());
                } catch (Exception e) {
                }
                salesReturnOrder.setLastUpdateTime(lastUpdateTime);
                salesReturnOrder.setCreateTime(createTime);
                infoList.add(salesReturnOrder);
            }
            pageInfo.setList(infoList);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
            logger.info("#traceId={}# [OUT] get getsByProjectId success:pageInfo={}", traceId, pageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * "销售管理 > 销售退换货管理" 列表表头部分 获取退货各状态的统计数据
     * web端请求参数为 查询的各筛选条件
     * 返回值为: 所有的状态 和 对应的统计数量
     */
    @RequestMapping("/getClassificationCount")
    @ResponseBody
    public GongxiaoResult selectClassificationCount(HttpServletRequest request, HttpServletResponse response, Integer projectId,
                                                    @RequestParam(defaultValue = "0") Integer returnedType,
                                                    @RequestParam(defaultValue = "") String salesReturnedOrderNo,
                                                    @RequestParam(defaultValue = "") String warehouseId,
                                                    @RequestParam(defaultValue = "") String timeStart,
                                                    @RequestParam(defaultValue = "") String timeEnd) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectClassificationCount] params: projectId={}, returnedType={}, salesReturnedOrderNo={}, warehouseId={}, timeStart={}, timeEnd={} "
                    , traceId, projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            SalesOrderReturnServiceStructure.SelectClassificationCountReq rpcReq = SalesOrderReturnServiceStructure.SelectClassificationCountReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesReturnedOrderNo(salesReturnedOrderNo)
                    .setWarehouseId(warehouseId)
                    .setTimeEnd(timeEnd)
                    .setTimeStart(timeStart)
                    .setReturnedType(returnedType)
                    .build();
            SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub.class);
            SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp rpcResponse = rpcStub.selectClassificationCount(rpcReq);
            List<SalesReturnClassificationCount> list = new ArrayList<>();
            List<SalesOrderReturnServiceStructure.SalesReturnClassificationCount> countList = rpcResponse.getCountListList();
            for (SalesOrderReturnServiceStructure.SalesReturnClassificationCount count : countList) {
                SalesReturnClassificationCount classificationCount = new SalesReturnClassificationCount();
                classificationCount.setCount(count.getCount());
                classificationCount.setStatus(count.getStatus());
                list.add(classificationCount);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] get selectClassificationCount success: resultList.size()={}", traceId, list.size());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * "销售管理 > 销售退货退货单管理 > 创建销售退货单 > 选择退货商品按钮" 在弹框书输入单号 进行查询
     * 返回值为 出库单列表
     */
    @RequestMapping("/getRecordList")
    @ResponseBody
    public GongxiaoResult getRecordList(HttpServletRequest request,
                                        String projectId, String salesNo) {
        String traceId = null;
        List<OutBound> outBoundList = new ArrayList<>();
        try {
            long projectIdLong = portalUserInfo.getProjectId();
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getRecordList] params: projectId={}, salesNo={}, ", traceId, projectId, salesNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            SalesOrderServiceStructure.CommonSalesOrderRequest rpcReq = SalesOrderServiceStructure.CommonSalesOrderRequest.newBuilder()
                    .setProjectId(projectIdLong)
                    .setRpcHeader(rpcHeader)
                    .setSalesOrderNo(salesNo)
                    .build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub stub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            SalesOrderServiceStructure.GetOrderByOrderNoResponse rpcResp = stub.getOrderByOrderNo(rpcReq);
            // 根据channelId查询channelToken
            String warehouseChannelId = portalConfig.getXpsChannelId();
            //调用基础模块的SourceChannel服务
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(warehouseChannelId + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();


            List<OutboundOrder> outboundOrderList = XpsWarehouseManager.selectRecordBySalesNo(portalConfig.getWarehouseUrl(), warehouseChannelId, xpsChannelSecret, projectId, salesNo);
            for (OutboundOrder outboundOrder : outboundOrderList) {
                OutBound outBound = new OutBound();
                outBound.setGongxiaoOutboundOrderNo(outboundOrder.getGongxiaoOutboundOrderNo());         //GX出库单号
                outBound.setOrderStatus(outboundOrder.getOrderStatus());                        //出库单状态
                outBound.setOrderStatusStr(OutboundOrderStatusEnum.getOutboundOrderStatusByCode(outboundOrder.getOrderStatus()).getStatusDesc());
                outBound.setContactsPeople(outboundOrder.getContactsPeople());                   //收件人名字
                outBound.setPhoneNum(outboundOrder.getPhoneNum());                 //收件人手机号
                outBound.setSalesOrderNo(outboundOrder.getSalesOrderNo());
                outBound.setLastUpdateTime(outboundOrder.getLastUpdateTime());
                outBound.setShippingAddress(rpcResp.getShippingAddress());
                outBound.setProvinceId(rpcResp.getProvinceId());
                outBound.setProvinceName(rpcResp.getProvinceName());
                outBound.setCityId(rpcResp.getCityId());
                outBound.setCityName(rpcResp.getCityName());
                outBound.setDistrictId(rpcResp.getDistrictId());
                outBound.setDistrictName(rpcResp.getDistrictName());
                outBoundList.add(outBound);
            }
            logger.info("#traceId={}# [OUT] get getRecordList success: resultList.size()={}", traceId, outBoundList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), outBoundList);
    }

    /**
     * "销售管理 > 销售退货退货单管理 > 创建销售退货单 > 选择退货商品按钮" 在弹框书输入单号查询得到出库单列表, 选择其中的某一条
     * 返回值为 出库单明细的列表
     */
    @RequestMapping("/getRecordItemList")
    @ResponseBody
    public GongxiaoResult getRecordItemList(HttpServletRequest request, HttpServletResponse response, String projectId, String salesOrderNo, String gongxiaoOutboundOrderNo) {
        String traceId = null;
        List<OutBoundItem> list = new ArrayList<>();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getRecordItemList] params: salesOrderNo={}, gongxiaoOutboundOrderNo={}, ", traceId, salesOrderNo, gongxiaoOutboundOrderNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

            // 根据channelId查询channelToken
            String warehouseChannelId = portalConfig.getXpsChannelId();
            //调用基础模块的SourceChannel服务
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(warehouseChannelId + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            List<OutboundOrderItem> outboundOrderItemList = XpsWarehouseManager.selectRecordItemByOutboundOrderNo(
                    portalConfig.getWarehouseUrl(),
                    portalConfig.getXpsChannelId(),
                    xpsChannelSecret,
                    projectId, gongxiaoOutboundOrderNo);

            for (OutboundOrderItem outboundOrderItem : outboundOrderItemList) {
                OutBoundItem outBoundItem = new OutBoundItem();
                outBoundItem.setProductId(outboundOrderItem.getProductId());
                outBoundItem.setProductCode(outboundOrderItem.getProductCode());
                outBoundItem.setProductName(outboundOrderItem.getProductName());
                outBoundItem.setOutStockQuantity(outboundOrderItem.getOutStockQuantity());
                outBoundItem.setProductUnit(outboundOrderItem.getProductUnit());
                outBoundItem.setInventoryType(outboundOrderItem.getInventoryType());
                outBoundItem.setCanReturnQuantity(outboundOrderItem.getReturnQuantity());
                SalesOrderServiceStructure.GetOrderItemRequest rpcReq = SalesOrderServiceStructure.GetOrderItemRequest.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setSalesOrderNo(outboundOrderItem.getSalesOrderNo())
                        .setProductCode(outboundOrderItem.getProductCode())
                        .setProjectId(Long.parseLong(projectId))
                        .build();

                //到销售模块获取币种/指导价等信息
                SalesOrderServiceGrpc.SalesOrderServiceBlockingStub stub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
                SalesOrderServiceStructure.GetOrderItemResponse rpcResp = stub.getOrderDetailBySalesOrderNoAndProjectCode(rpcReq);

                outBoundItem.setCurrencyCode(rpcResp.getCurrencyCode());
                outBoundItem.setCurrencyName(rpcResp.getCurrencyName());
                outBoundItem.setGuidePrice(Double.valueOf(rpcResp.getSalesGuidePrice()));
                outBoundItem.setWholesalePrice(Double.valueOf(rpcResp.getWholesalePrice()));

                list.add(outBoundItem);
            }
            logger.info("#traceId={}# [OUT] get getRecordItemList success: resultList.size()={}", traceId, list.size());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), list);
    }

    /**
     * "销售管理 > 销售退货单管理 > 创建销售退货单" 页面中点提交按钮 新创建销售退货单
     */
    @RequestMapping("/addSalesReturn")
    @ResponseBody
    public GongxiaoResult addSalesReturn(HttpServletRequest request, HttpServletResponse response, SalesReturnOrder salesReturn) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;

        try {
            long projectId = portalUserInfo.getProjectId();
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][addSalesReturn] params: salesReturnOrder={} ", traceId, salesReturn);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String salesReturnOrderItemListJson = salesReturn.getSalesReturnOrderItemListJson();
            List<SalesReturnOrderItem> salesReturnOrderItemList
                    = JSON.parseObject(salesReturnOrderItemListJson, new TypeReference<List<SalesReturnOrderItem>>() {
            });
            for (SalesReturnOrderItem item : salesReturnOrderItemList) {
                int totalReturnedQuantity = item.getTotalReturnedQuantity();
                if (totalReturnedQuantity <= 0) {
                    logger.info("#traceId={}# [OUT] addSalesReturn faild : quantity can NOT be less than 0 ", traceId);
                    return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
                }
            }

            if (salesReturn.getTargetWarehouseId() == null || salesReturn.getTargetWarehouseId().length() == 0) {
                return GongxiaoResult.newResult(ErrorCode.RETURN_WAREHOUSE_NULL.getErrorCode(), ErrorCode.RETURN_WAREHOUSE_NULL.getMessage());
            }
            SalesOrderReturnServiceStructure.SalesReturnOrderReq.Builder rpcBuilder = SalesOrderReturnServiceStructure.SalesReturnOrderReq.newBuilder();
            SalesOrderReturnServiceStructure.SalesReturnOrderReq rpcReq;
            rpcBuilder.setRpcHeader(rpcHeader);
            rpcBuilder.setProjectId(projectId);
            SalesOrderReturnServiceStructure.SalesReturnOrder salesReturnOrder = SalesOrderReturnServiceStructure.SalesReturnOrder.newBuilder()
                    .setProjectId(salesReturn.getProjectId())// 项目ID
                    .setProjectName(salesReturn.getProjectName() != null ? salesReturn.getProjectName() : "")       // 项目名称
                    .setOriginalSalesOrderNo(salesReturn.getOriginalSalesOrderNo() != null ? salesReturn.getOriginalSalesOrderNo() : "") // 原销售单号
                    .setOriginalGongxiaoOutboundOrderNo(salesReturn.getOriginalGongxiaoOutboundOrderNo() != null ? salesReturn.getOriginalGongxiaoOutboundOrderNo() : "") // 原销售单的出库单号
                    .setTargetWarehouseId(salesReturn.getTargetWarehouseId() != null ? salesReturn.getTargetWarehouseId() : "")// 退货目标仓库ID(
                    .setTargetWarehouseName(salesReturn.getTargetWarehouseName() != null ? salesReturn.getTargetWarehouseName() : "")// 退货目标仓库名称
                    .setOriginalOutboundWarehouseId(salesReturn.getOriginalOutboundWarehouseId() != null ? salesReturn.getOriginalOutboundWarehouseId() : "")// 发货仓库ID(
                    .setOriginalOutboundWarehouseName(salesReturn.getOriginalOutboundWarehouseName() != null ? salesReturn.getOriginalOutboundWarehouseName() : "")// 发货仓库名称
                    .setSenderName(salesReturn.getSenderName() != null ? salesReturn.getSenderName() : "")// 发件人名字
                    .setSenderMobile(salesReturn.getSenderMobile() != null ? salesReturn.getSenderMobile() : "")// 收件人手机号
                    .setLogisticsType(salesReturn.getLogisticsType() != null ? salesReturn.getLogisticsType() : 0)// 运输方式
                    .setFreight(salesReturn.getFreight() != null ? salesReturn.getFreight() : 0)// 运费
                    .setFreightPaidBy(salesReturn.getFreightPaidBy() != null ? salesReturn.getFreightPaidBy() : 0)// 运费承担方
                    .setLogisticsOrderNo(salesReturn.getLogisticsOrderNo() != null ? salesReturn.getLogisticsOrderNo() : "") // 物流单号
                    .setLogisticsCompany(salesReturn.getLogisticsCompany() != null ? salesReturn.getLogisticsCompany() : "")// 物流公司名称
                    .setProvinceId(salesReturn.getProvinceId() != null ? salesReturn.getProvinceId() : "")
                    .setProjectName(salesReturn.getProvinceName() != null ? salesReturn.getProvinceName() : "")
                    .setCityId(salesReturn.getCityId() != null ? salesReturn.getCityId() : "")
                    .setCityName(salesReturn.getCityName() != null ? salesReturn.getCityName() : "")
                    .setDistrictId(salesReturn.getDistrictId() != null ? salesReturn.getDistrictId() : "")
                    .setDistrictName(salesReturn.getDistrictName() != null ? salesReturn.getDistrictName() : "")
                    .setSenderAddressItem(salesReturn.getSenderAddressItem() != null ? salesReturn.getSenderAddressItem() : "")
                    .setWarehouseAddress(salesReturn.getWarehouseAddress() != null ? salesReturn.getWarehouseAddress() : "")
                    .build();
            rpcBuilder.setSalesReturnOrder(salesReturnOrder);
            for (SalesReturnOrderItem item : salesReturnOrderItemList) {
                SalesOrderReturnServiceStructure.SalesReturnOrderItem salesReturnOrderItem = SalesOrderReturnServiceStructure.SalesReturnOrderItem.newBuilder()
                        .setProductId(item.getProductId() != null ? item.getProductId() : "")
                        .setProductCode(item.getProductCode() != null ? item.getProductCode() : "")
                        .setProductName(item.getProductName() != null ? item.getProductName() : "")
                        .setProductUnit(item.getProductUnit() != null ? item.getProductUnit() : "")
                        .setReturnCause(item.getReturnCause() != null ? item.getReturnCause() : "")
                        .setTotalReturnedQuantity(item.getTotalReturnedQuantity())
                        .setWarehouseId(item.getWarehouseId() != null ? item.getWarehouseName() : "")
                        .setWarehouseName(item.getWarehouseName() != null ? item.getWarehouseName() : "")
                        .setInventoryType(item.getInventoryType())
                        .build();
                rpcBuilder.addItemList(salesReturnOrderItem);
            }
            rpcReq = rpcBuilder.build();
            SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub stub = RpcStubStore.getRpcStub(SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub.class);
            //TODO 待返回新创建的退货单号及主键
            GongxiaoRpc.RpcResult rpcResult = stub.saveSalesReturnOrder(rpcReq);
            if (ErrorCode.SUCCESS.getErrorCode() == rpcResult.getReturnCode()) {
                logger.info("#traceId={}# [OUT] get addSalesReturn success ", traceId);
                return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
            } else {
                logger.error("#traceId={}# [OUT] get addSalesReturn faild ", traceId);
                return GongxiaoResult.newResult(rpcResult.getReturnCode(), rpcResult.getMsg());
            }
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 越海商务 审核退货单(支持批量审核操作)
     */
    @RequestMapping("/check")
    @ResponseBody
    public GongxiaoResult checkSalesReturn(HttpServletRequest request, HttpServletResponse response, long salesReturnedOrderId, long dataVersion) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        try {
            // 从session中获取projectId
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][checkSalesReturn] params: salesReturnedOrderId={}, dataVersion={}", traceId, salesReturnedOrderId, dataVersion);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq.Builder reqBuilder = SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq.newBuilder();
            SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq rpcReq;
            reqBuilder.setRpcHeader(rpcHeader);
            SalesOrderReturnServiceStructure.CheckSalesReturnOrder checkSalesReturnOrder = SalesOrderReturnServiceStructure.CheckSalesReturnOrder.newBuilder()
                    .setSalesReturnedOrderId(salesReturnedOrderId)
                    .setDataVersion(dataVersion)
                    .build();
            reqBuilder.addCheckList(checkSalesReturnOrder);
            reqBuilder.setProjectId(projectId);
            rpcReq = reqBuilder.build();
            SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub stub = RpcStubStore.getRpcStub(SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResult = stub.checkSalesReturnOrder(rpcReq);
            if (ErrorCode.SUCCESS.getErrorCode() == rpcResult.getReturnCode()) {
                logger.info("#traceId={}# [OUT] get checkSalesReturn success ", traceId);
                return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
            } else {
                logger.error("#traceId={}# [OUT] get checkSalesReturn faild ", traceId);
                return GongxiaoResult.newResult(rpcResult.getReturnCode(), rpcResult.getMsg());
            }
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 点击某一退货单号 进入详情页
     */
    @RequestMapping("/get")
    @ResponseBody
    public GongxiaoResult get(HttpServletRequest request, HttpServletResponse response, Long salesReturnOrderId) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        try {
            // 从session中获取projectId
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][get] params: salesReturnOrderId={} ", traceId, salesReturnOrderId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            SalesOrderReturnServiceStructure.GetSalesReturnReq rpcReq = SalesOrderReturnServiceStructure.GetSalesReturnReq.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setSalesReturnedOrderId(salesReturnOrderId)
                    .build();
            SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub stub = RpcStubStore.getRpcStub(SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub.class);
            SalesOrderReturnServiceStructure.SalesReturnResp rpcResp = stub.getSalesReturn(rpcReq);

            //转换为web端需要的结构
            SalesOrderReturnServiceStructure.SalesReturnOrder salesReturnOrder = rpcResp.getSalesReturnOrder();
            List<SalesOrderReturnServiceStructure.SalesReturnOrderItem> salesReturnOrderItemList = rpcResp.getItemListList();

            SalesReturnOrder salesReturn = new SalesReturnOrder();
            salesReturn.setSalesReturnedOrderNo(salesReturnOrder.getSalesReturnedOrderNo());
            Date createdTime = null;
            if (salesReturnOrder.getCreateTime() != "") {
                createdTime = DateUtil.stringToDate(salesReturnOrder.getCreateTime(), DateUtil.DATE_TIME_FORMAT);
            }
            salesReturn.setCreateTime(createdTime);
            salesReturn.setTracelog(salesReturnOrder.getTracelog());
            salesReturn.setFreightPaidBy(salesReturnOrder.getFreightPaidBy());
            salesReturn.setFreight(salesReturnOrder.getFreight());
            salesReturn.setProvinceName(salesReturnOrder.getProvinceName());
            salesReturn.setCityName(salesReturnOrder.getCityName());
            salesReturn.setDistrictName(salesReturnOrder.getDistrictName());
            salesReturn.setSenderName(salesReturnOrder.getSenderName());
            salesReturn.setSenderAddressItem(salesReturnOrder.getSenderAddressItem());
            salesReturn.setSenderMobile(salesReturnOrder.getSenderMobile());
            salesReturn.setDistributorName(salesReturnOrder.getDistributorName());
            salesReturn.setLogisticsType(salesReturnOrder.getLogisticsType());
            salesReturn.setLogisticsCompany(salesReturnOrder.getLogisticsCompany());
            salesReturn.setLogisticsOrderNo(salesReturnOrder.getLogisticsOrderNo());
            salesReturn.setTargetWarehouseName(salesReturnOrder.getTargetWarehouseName());
            salesReturn.setReturnedOrderStatus(salesReturnOrder.getReturnedOrderStatus());
            salesReturn.setReturnedType(salesReturnOrder.getReturnedType());
            List<SalesReturnItem> itemList = new ArrayList<>();
            for (SalesOrderReturnServiceStructure.SalesReturnOrderItem salesReturnOrderItem : salesReturnOrderItemList) {
                SalesReturnItem item = new SalesReturnItem();
                item.setProductName(salesReturnOrderItem.getProductName());//货品名称
                item.setProductCode(salesReturnOrderItem.getProductCode());//货品编码
                item.setTotalReturnedQuantity(salesReturnOrderItem.getTotalReturnedQuantity()); //退货数量
                item.setTotalInStockQuantity(salesReturnOrderItem.getTotalInStockQuantity());//仓库实收
                item.setReturnCause(salesReturnOrderItem.getReturnCause());//退货原因
                item.setCurrencyCode(salesReturnOrderItem.getCurrencyCode());//货币编码
                item.setCurrencyName(salesReturnOrderItem.getCurrencyName());//货币名称
                item.setWholesalePrice(salesReturnOrderItem.getWholesalePrice());//出货价
                item.setTotalQuantity(salesReturnOrderItem.getTotalQuantity());//原始数量
                item.setReturnAmount(salesReturnOrderItem.getReturnAmount());//商品退款金额
                itemList.add(item);
            }
            salesReturn.setItemList(itemList);
            logger.info("#traceId={}# [OUT] get getRecordList success: result={}", traceId, salesReturn);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), salesReturn);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }
}

