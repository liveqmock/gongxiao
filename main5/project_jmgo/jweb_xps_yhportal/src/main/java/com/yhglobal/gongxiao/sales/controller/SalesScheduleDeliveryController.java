package com.yhglobal.gongxiao.sales.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.entity.EasOutboundItem;
import com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure;
import com.yhglobal.gongxiao.sales.microservice.SalesScheduleDeliveryServiceGrpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import com.yhglobal.gongxiao.warehouseapi.model.InventoryBatch;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.yhglobal.gongxiao.constant.ErrorCode.ARGUMENTS_INVALID;
import static com.yhglobal.gongxiao.constant.ErrorCode.SUCCESS;

/**
 * 预约发货表现层
 *
 * @Author: 葛灿
 */
@Controller
@RequestMapping("/sales/schedule")
public class SalesScheduleDeliveryController {
    private static Logger logger = LoggerFactory.getLogger(SalesOrderController.class);
    @Autowired
    private PortalConfig portalConfig;
    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象

    /**
     * 查询销售预约商品信息
     *
     * @param salesOrderNo 销售单号
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public GongxiaoResult getScheduleInfo(HttpServletRequest request, HttpServletResponse response, String salesOrderNo) {
        GongxiaoResult gongxiaoResult;
        String traceId = null;
        try {
            //从session中获取项目id
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderList][IN] params:  salesOrderNo={}", traceId, salesOrderNo);
            DeliverScheduleStructure.SelectSaleScheduleProductListRequest rpcRequest = DeliverScheduleStructure.SelectSaleScheduleProductListRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcheader(rpcHeader)
                    .setSalesOrderNo(salesOrderNo)
                    .build();
            SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub.class);
            DeliverScheduleStructure.SelectSaleScheduleProductListResponse rpcResponse = rpcStub.selectSaleScheduleProductList(rpcRequest);
            List<InventoryBatch> inventoryBatches = new LinkedList<>();
            for (DeliverScheduleStructure.ScheduleItems protoObject : rpcResponse.getListList()) {
                InventoryBatch javaObject = new InventoryBatch();
                javaObject.setProductName(protoObject.getProductName());
                javaObject.setProductCode(protoObject.getProductCode());
                javaObject.setOrderTotalQuantity(protoObject.getOrderTotalQuantity());
                javaObject.setOrderUnhandledQuantity(protoObject.getOrderUnhandledQuantity());
                inventoryBatches.add(javaObject);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), inventoryBatches);
            logger.info("#traceId={}# [getSalesOrderList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;

    }


    /**
     * 预约商品批次查询
     *
     * @param productCode 货品编码
     * @return
     */
    @RequestMapping("/batch")
    @ResponseBody
    public GongxiaoResult detail(HttpServletRequest request, HttpServletResponse response, int pageNumber, int pageSize, String productCode) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            //从session中获取项目id
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderList][IN] params:  salesOrderNo={}", traceId);

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

            PageInfo<InventoryBatch> batchDetail =
                    XpsWarehouseManager.getBatchDetail(portalConfig.getWarehouseUrl(),
                            portalConfig.getXpsChannelId(),
                            xpsChannelSecret,
                            projectId + "", productCode, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), batchDetail);
            logger.info("#traceId={}# [getSalesOrderList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 发起预约
     *
     * @param salesOrderNo   销售单号
     * @param itemsInfo      产品信息（JSON）
     * @param arrivalDateStr 到货日期
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public GongxiaoResult getList(HttpServletRequest request, HttpServletResponse response,
                                  String salesOrderNo, String itemsInfo,
                                  String arrivalDateStr) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            //从session中获取项目id
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderList][IN] params:  salesOrderNo={}", traceId, salesOrderNo);
            List<InventoryBatch> inventoryBatches = JSON.parseArray(itemsInfo, InventoryBatch.class);
            // set存放了 productCode + batchNo + warehouseId +inventoryType + purchaseType ，做重复校验
            Set<String> checkSet = new HashSet<>();
            //key 商品型号 value 本次预约的总数量
            HashMap<String, Integer> checkMap = new HashMap<>();
            if (StringUtils.isEmpty(arrivalDateStr)) {
                gongxiaoResult = new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                logger.info("#traceId={}# [getSalesOrderList][OUT] arguments invalid!");
                return gongxiaoResult;
            }
            for (InventoryBatch inventoryBatch : inventoryBatches) {
                String productCode = inventoryBatch.getProductCode(); // 货品编码
                int scheduleQuantity = inventoryBatch.getScheduleQuantity(); // 预约数量
                int orderUnhandledQuantity = inventoryBatch.getOrderUnhandledQuantity();
                int inventoryBatchAmount = inventoryBatch.getInventoryBatchAmount();
                //校验是否存在 预约数量》未发货数量  预约数量》库存数量 渔业数量=0
                if (scheduleQuantity > orderUnhandledQuantity
                        || scheduleQuantity > inventoryBatchAmount
                        || scheduleQuantity == 0) {
                    gongxiaoResult = new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                    logger.info("#traceId={}# [getSalesOrderList][OUT] arguments invalid!");
                    return gongxiaoResult;
                }
                String uniqueString = productCode + inventoryBatch.getBatchNo() + inventoryBatch.getWarehouseId()
                        + inventoryBatch.getInventoryStatus() + inventoryBatch.getPurchaseType();
                //校验是否存在相同行
                if (checkSet.contains(uniqueString)) {
                    gongxiaoResult = new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                    logger.info("#traceId={}# [getSalesOrderList][OUT] arguments invalid!");
                    return gongxiaoResult;
                } else {
                    checkSet.add(uniqueString);
                }
                if (checkMap.get(productCode) == null) {
                    checkMap.put(productCode, scheduleQuantity);
                } else {
                    checkMap.put(productCode, checkMap.get(productCode) + scheduleQuantity);
                }
            }
            Date arrivalDate = DateUtils.parseDate(arrivalDateStr, "yyyy-MM-dd");
            DeliverScheduleStructure.CreateScheduleOrderRequest.Builder builder = DeliverScheduleStructure.CreateScheduleOrderRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesOrderNo(salesOrderNo)
                    .setArrivalDate(arrivalDate.getTime());
            for (InventoryBatch inventoryBatch : inventoryBatches) {
                String productCode = inventoryBatch.getProductCode();
                int orderUnhandledQuantity = inventoryBatch.getOrderUnhandledQuantity();
                Integer codeTotalQuantity = checkMap.get(productCode);
                if (codeTotalQuantity > orderUnhandledQuantity) {
                    gongxiaoResult = new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                    logger.info("#traceId={}# [getSalesOrderList][OUT] arguments invalid!");
                    return gongxiaoResult;
                }
                DeliverScheduleStructure.ScheduleItems scheduleItem = DeliverScheduleStructure.ScheduleItems.newBuilder()
                        .setBatchNo(inventoryBatch.getBatchNo())
                        .setProductId(inventoryBatch.getProductId())
                        .setProductName(inventoryBatch.getProductName())
                        .setProductCode(inventoryBatch.getProductCode())
                        .setWarehouseId(inventoryBatch.getWarehouseId())
                        .setWarehouseName(inventoryBatch.getWarehouseName())
                        .setPurchaseType(inventoryBatch.getPurchaseType())
                        .setScheduleQuantity(inventoryBatch.getScheduleQuantity())
                        .setInventoryStatus(inventoryBatch.getInventoryStatus())
                        .setOrderTotalQuantity(inventoryBatch.getOrderTotalQuantity())
                        .setOrderUnhandledQuantity(inventoryBatch.getOrderUnhandledQuantity())
                        .setInventoryBatchAmount(inventoryBatch.getInventoryBatchAmount())
                        .build();
                builder.addProductInfo(scheduleItem);
            }
            SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub.class);
            DeliverScheduleStructure.CreateScheduleOrderRequest rpcRequest = builder.build();
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.createScheduleOrder(rpcRequest);
            gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
            logger.info("#traceId={}# [getSalesOrderList][OUT] create success.");
            return gongxiaoResult;
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


}
