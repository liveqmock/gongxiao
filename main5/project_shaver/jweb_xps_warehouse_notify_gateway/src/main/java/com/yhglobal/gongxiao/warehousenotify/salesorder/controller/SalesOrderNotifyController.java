package com.yhglobal.gongxiao.warehousenotify.salesorder.controller;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure;
import com.yhglobal.gongxiao.sales.microservice.SalesScheduleDeliveryServiceGrpc;
import com.yhglobal.gongxiao.transportataion.sendtotransportation.sales.model.SignInfo;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.sales.model.EasOutboundItem;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.sales.model.EasOutboundOrderRequest;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.sales.model.SyncEasRequest;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 销售模块 接收XPS warehouse的回告信息 入口
 */

@Controller
@RequestMapping("/salesorder")
public class SalesOrderNotifyController {

    private static Logger logger = LoggerFactory.getLogger(SalesOrderNotifyController.class);


    @PostConstruct
    public void sayHello() {
        System.out.println("hello");
    }


    /**
     * 注： 请求结果 需要按 xps warehouse的要求返回去
     */
    @RequestMapping("/xpsNotification")
    @ResponseBody
    public String xpsNotificationEntry() {
        return null;
    }


    /**
     * 仓库模块 通知 销售模块,
     * 出库完成
     *
     * @param outboundOrderNo 出库单号
     * @return GongxiaoResult
     */
    @RequestMapping("/outboundFinished")
    @ResponseBody
    public GongxiaoResult itemsOutboundFinishedController(HttpServletRequest request, HttpServletResponse response,
                                                          String outboundOrderNo, long projectId) {
        String traceId = request.getServletPath();
        GongxiaoResult gongxiaoResult = null;
        try {
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "0", "system");
            logger.info("#traceId={}# [IN][itemsOutboundFinishedController] params: salesOrderNo={}", rpcHeader.getTraceId(), outboundOrderNo);
            SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub salesScheduleDeliveryService = RpcStubStore.getRpcStub(SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub.class);
            DeliverScheduleStructure.OutboundNoRequest rpcRequest = DeliverScheduleStructure.OutboundNoRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader).setOutboundOrderNo(outboundOrderNo).build();
            GongxiaoRpc.RpcResult rpcResult = salesScheduleDeliveryService.itemsOutboundFinished(rpcRequest);

            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResultByCode(rpcResult.getReturnCode());
            logger.info("#traceId={}# [OUT][itemsOutboundFinishedController] returnCode={}", traceId, gongxiaoResult.getReturnCode());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 仓库模块 通知 销售模块,
     * 出库完成
     *
     * @param outboundOrderNo 出库单号
     * @return GongxiaoResult
     */
    @RequestMapping("/sendCar")
    @ResponseBody
    public GongxiaoResult informTmsSendCarController(HttpServletRequest request, HttpServletResponse response,
                                                     String outboundOrderNo, long projectId) {
        String traceId = request.getServletPath();
        GongxiaoResult gongxiaoResult = null;
        try {
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "0", "system");
            logger.info("#traceId={}# [IN][informTmsSendCarController] params: outboundOrderNo={}", traceId, outboundOrderNo);
            SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub salesScheduleDeliveryService = RpcStubStore.getRpcStub(SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub.class);
            DeliverScheduleStructure.OutboundNoRequest rpcRequest = DeliverScheduleStructure.OutboundNoRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader).setOutboundOrderNo(outboundOrderNo).build();
            GongxiaoRpc.RpcResult rpcResult = salesScheduleDeliveryService.submitOutboundOrderToTms(rpcRequest);

            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResultByCode(rpcResult.getReturnCode());
            logger.info("#traceId={}# [informTmsSendCarController][OUT] returnCode={}", traceId, gongxiaoResult.getReturnCode());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 运输模块 通知 销售模块
     * 出库单签收
     * <p>
     * @return GongxiaoResult
     */
    @RequestMapping("/cancelOutbound")
    @ResponseBody
    public GongxiaoResult cancelOutbound(HttpServletRequest request, HttpServletResponse response,
                                      String outboundOrderNo, long projectId) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = request.getServletPath();
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "0", "system");
        GongxiaoRpc.RpcResult rpcResult = null;
        logger.info("#traceId={}# [IN][cancelOutbound] params: outboundOrderNo={}, projectId={}", outboundOrderNo, projectId);
        try {
            SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub salesScheduleDeliveryService = RpcStubStore.getRpcStub(SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub.class);
            DeliverScheduleStructure.OutboundNoRequest rpcRequest = DeliverScheduleStructure.OutboundNoRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader).setOutboundOrderNo(outboundOrderNo).build();
            rpcResult = salesScheduleDeliveryService.cancelSalesOutboundOrder(rpcRequest);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResultByCode(rpcResult.getReturnCode());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 运输模块 通知 销售模块
     * 出库单签收
     * <p>
     * //     * @param signedInfo 签收信息
     *
     * @return GongxiaoResult
     */
    @RequestMapping("/outboundSigned")
    @ResponseBody
    public GongxiaoResult outboundSignedController(HttpServletRequest request, HttpServletResponse response
            , @RequestBody SignInfo signedInfo
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = request.getServletPath();
        try {
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "0", "system");
            logger.info("#traceId={}# [IN][outboundSignedController] params: signedInfo={}", traceId, JSON.toJSONString(signedInfo));
            DeliverScheduleStructure.OutboundSignedRequest outboundSignedRequest = DeliverScheduleStructure.OutboundSignedRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(signedInfo.getProjectId())
                    .setOutboundOrderNo(GrpcCommonUtil.getProtoParam(signedInfo.getCustOrdNo()))
                    .setTmsOrderNo(GrpcCommonUtil.getProtoParam(signedInfo.getTmsOrdNo()))
                    .setTmsRemark(GrpcCommonUtil.getProtoParam(signedInfo.getRemark()))
                    .setSignedBy(GrpcCommonUtil.getProtoParam(signedInfo.getSignedBy()))
                    .setPostedBy(GrpcCommonUtil.getProtoParam(signedInfo.getPostedBy()))
                    .setSignedPhone(GrpcCommonUtil.getProtoParam(signedInfo.getSignedPhone()))
                    .setSignedTime(GrpcCommonUtil.getProtoParam(signedInfo.getSignedTime()))
                    .setTransporter(GrpcCommonUtil.getProtoParam(signedInfo.getTransporter()))
                    .build();
            SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub salesScheduleDeliveryService = RpcStubStore.getRpcStub(SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResult = salesScheduleDeliveryService.outboundSigned(outboundSignedRequest);

            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResultByCode(rpcResult.getReturnCode());
            logger.info("#traceId={}# [outboundSignedController][OUT] returnCode={}", traceId, gongxiaoResult.getReturnCode());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 仓库模块 获取同步eas需要的出库单信息
     *
     * @param syncEasRequest 同步信息
     * @return EasOutboundOrderRequest
     */
    @RequestMapping("/syncEas")
    @ResponseBody
    public EasOutboundOrderRequest syncSalesOutboundOrderToEasController(HttpServletRequest request, HttpServletResponse response,
                                                                         @RequestBody SyncEasRequest syncEasRequest) {
        EasOutboundOrderRequest result;
        String traceId = request.getServletPath();
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "0", "system");
        try {
            String salesOrderNo = syncEasRequest.getSalesOrderNo();
            List<EasOutboundItem> items = syncEasRequest.getItems();
            logger.info("#traceId={}# [IN][syncSalesOutboundOrderToEasController][IN] params: salesOrderNo={}, items={}", traceId, salesOrderNo, JSON.toJSONString(items));
            DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest.Builder builder = DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest.newBuilder().setRpcHeader(rpcHeader).setSalesOrderNo(salesOrderNo);
            builder.setProjectId(syncEasRequest.getProjectId());
            for (EasOutboundItem item : items) {
                DeliverScheduleStructure.EasOutboundItem protoObject = DeliverScheduleStructure.EasOutboundItem.newBuilder()
                        .setProductCode(item.getProductCode())
                        .setQuantity(item.getQuantity())
                        .setBatchNo(item.getBatchNo())
                        .setWarehouseId(item.getWarehouseId())
                        .setGoodProduct(item.isGoodProduct())
                        .setGift(item.isGift())
                        .build();
                builder.addEasOutboundItems(protoObject);
            }
            DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest syncSalesOutboundOrderToEasRequest = builder.build();
            SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub salesScheduleDeliveryService = RpcStubStore.getRpcStub(SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub.class);
            DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse rpcResponse = salesScheduleDeliveryService.syncSalesOutboundOrderToEas(syncSalesOutboundOrderToEasRequest);
            String jsonStr = rpcResponse.getJsonStr();
            result = JSON.parseObject(jsonStr, EasOutboundOrderRequest.class);
            logger.info("#traceId={}# [syncSalesOutboundOrderToEasController][OUT] finished. jsonStr={}", traceId, jsonStr);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            result = new EasOutboundOrderRequest();
            result.setErrorCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
        }
        return result;
    }

}
