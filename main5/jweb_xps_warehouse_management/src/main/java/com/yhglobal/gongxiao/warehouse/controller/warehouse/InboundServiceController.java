package com.yhglobal.gongxiao.warehouse.controller.warehouse;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.controller.warehouseModel.CreateInboundOrder2Request;
import com.yhglobal.gongxiao.warehouse.controller.warehouseModel.CreateInboundOrderRequest;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.PlanInboundOrderItem;
import com.yhglobal.gongxiao.warehouse.service.InboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 仓储模块向外暴露的  入库相关http接口
 */
@Controller
@RequestMapping("/warehouse")
public class InboundServiceController {

    private static final Logger logger = LoggerFactory.getLogger(InboundServiceController.class);

    @Autowired
    InboundService inboundService;

    /**
     * 采购模块 创建入库单
     * @param request
     * @return
     */
    @RequestMapping(value = "/createInboundOrder",method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult createInboundOrder(@RequestBody CreateInboundOrderRequest request) {
        logger.info("#traceId={}# [IN][createInboundOrder] params:  request={}", JSON.toJSONString(request));
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setTraceId("1").setUid("01").setUsername(request.getSenderName()).build();
        String sourceChannelId = request.getSourceChannelId();
        Date expectArrivalTime = request.getExpectArrivalTime();
        int purchaseType = request.getPurchaseType();
        String orderSourceNo = request.getOrderSourceNo();
        int inboundType = request.getInboundType();
        String projectId =request.getProjectId();
        String traceNo = request.getTraceNo();
        String senderName = request.getSenderName();
        String senderPhoneNo = request.getSenderPhoneNo();
        String warehouseId = request.getWarehouseId();
        String warehouseName = request.getWarehouseName();
        String deliverAddress = request.getDeliverAddress();
        int shippingMode = request.getShippingMode();
        String logisticsCompanyName = request.getLogisticsCompanyName();
        String logisticsNo = request.getLogisticsNo();
        String note = request.getNote();
        int totalQuantity = request.getTotalQuantity();
        List<PlanInboundOrderItem> itemList = request.getItemList();
        String signature =request.getSignature();
        String uniqueNo = request.getUniqueNo();
        logger.info("#traceId# [IN][createInboundOrder] params:  expectArrivalTime={},purchaseType={},orderSourceNo={},sourceChannelId={},projectId={},traceNo={},senderName={},senderPhoneNo={},warehouseId={},warehouseName={},deliverAddress={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={},uniqueNo={}",
                expectArrivalTime, purchaseType, orderSourceNo, sourceChannelId, projectId, traceNo, senderName, senderPhoneNo, warehouseId, warehouseName, deliverAddress, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature,uniqueNo);
        GongxiaoResult gongxiaoResult = inboundService.createInboundOrder(rpcHeader, expectArrivalTime, purchaseType, orderSourceNo, sourceChannelId, inboundType, projectId, traceNo, senderName, senderPhoneNo, warehouseId, warehouseName, deliverAddress, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, itemList, signature, uniqueNo);
        return gongxiaoResult;
    }

    /**
     * 销售模块 退货时创建入库单
     * @param request
     * @return
     */
    @RequestMapping("/createInboundOrder2")
    @ResponseBody
    public GongxiaoResult createInboundOrder2(@RequestBody CreateInboundOrder2Request request) {
        logger.info("#traceId={}# [IN][createInboundOrder] params:  request={}", JSON.toJSONString(request));
        GongxiaoRpc.RpcHeader rpcHeader= GongxiaoRpc.RpcHeader.newBuilder().setTraceId("1").setUid("01").setUsername(request.getSenderName()).build();
        String orderSourceNo= request.getOrderSourceNo();
        String sourceChannelId= request.getSourceChannelId();
        int inboundType= request.getInboundType();
        String projectId= request.getProjectId();
        String traceNo= request.getTraceNo();
        String senderName= request.getSenderName();
        String senderPhoneNo= request.getSenderPhoneNo();
        String warehouseId= request.getWarehouseId();
        String warehouseName= request.getWarehouseName();
        String deliverAddress= request.getDeliverAddress();
        int shippingMode= request.getShippingMode();
        String logisticsCompanyName= request.getLogisticsCompanyName();
        String logisticsNo= request.getLogisticsNo();
        String note= request.getNote();
        int totalQuantity= request.getTotalQuantity();
        List<PlanInboundOrderItem> itemList= request.getItemList();
        String signature= request.getSignature();
        String uniqueNo = request.getUniqueNo();
        logger.info("#traceId={}# [IN][createInboundOrder] params: orderSourceNo={},sourceChannelId={},inboundType={},projectId={},traceNo={},senderName={},senderPhoneNo={},warehouseId={},warehouseName={},phoneNum={},deliverAddress={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={}", rpcHeader.getTraceId(), orderSourceNo, sourceChannelId, inboundType,projectId, traceNo, senderName, senderPhoneNo, warehouseId, warehouseName, deliverAddress, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature);
        GongxiaoResult gongxiaoResult = inboundService.createInboundOrder2(rpcHeader,orderSourceNo, sourceChannelId, inboundType,projectId, traceNo, senderName, senderPhoneNo, warehouseId, warehouseName, deliverAddress, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, itemList, signature,uniqueNo);
        return gongxiaoResult;
    }

    /**
     * 根据采购单号查询入库单明细
     * @param rpcHeader
     * @param projectId
     * @param purchaseOrderNo
     * @return
     */
    @RequestMapping("/getInboundItemRecord")
    @ResponseBody
    public List<InboundOrderItem> getInboundItemRecord(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String purchaseOrderNo) {
        logger.info("#traceId={}# [IN][getInboundItemRecord] params: projectId={},purchaseOrderNo={}", rpcHeader.getTraceId(), projectId, purchaseOrderNo);
        List<InboundOrderItem> inboundOrderItemList = inboundService.getInboundItemRecord(rpcHeader,projectId,purchaseOrderNo);
        logger.info("#traceId={}# [OUT][getInboundItemRecord] get getInboundItemRecord success", rpcHeader.getTraceId());
        return inboundOrderItemList;
    }


}
