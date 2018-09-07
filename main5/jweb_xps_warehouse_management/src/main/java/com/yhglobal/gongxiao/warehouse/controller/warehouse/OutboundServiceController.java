package com.yhglobal.gongxiao.warehouse.controller.warehouse;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.controller.warehouseModel.SalesCreateOutboundRequest;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.PlanOutboundOrderItem;
import com.yhglobal.gongxiao.warehouse.service.OutboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 仓储模块向外暴露的  出库相关接口
 */
@Controller
@RequestMapping("/warehouse")
public class OutboundServiceController {
    private static final Logger logger = LoggerFactory.getLogger(OutboundServiceController.class);

    @Autowired
    OutboundService outboundService;

    /**
     * 采购退货 创建出库单
     *
     * @param rpcHeader
     * @param sourceChannelId
     * @param outboundType
     * @param projectId
     * @param customerId
     * @param traceNo
     * @param recipientName
     * @param recipientPhoneNumber
     * @param recipientAddress
     * @param warehouseId
     * @param warehouseName
     * @param shippingMode
     * @param logisticsCompanyName
     * @param logisticsNo
     * @param note
     * @param totalQuantity
     * @param itemList
     * @param signature
     * @param uniqueNo
     * @return
     */
    @RequestMapping("/createOutboundOrder")
    public String createOutboundOrder(GongxiaoRpc.RpcHeader rpcHeader, String sourceChannelId, int outboundType, String projectId, String customerId, String traceNo, String recipientName, String recipientPhoneNumber, String recipientAddress, String warehouseId, String warehouseName, int shippingMode, String logisticsCompanyName, String logisticsNo, String note, int totalQuantity, List<PlanOutboundOrderItem> itemList, String signature, String uniqueNo, long userId, String userName) {
        logger.info("#traceId={}# [IN][createOutboundOrder]: sourceChannelId={},outboundType={},projectId={},customerId={},traceNo={},recipientName={},recipientPhoneNumber={},recipientAddress={},warehouseId={},warehouseName={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={},uniqueNo={}, userId={}, userName={}", rpcHeader.getTraceId(), sourceChannelId, outboundType, projectId, customerId, traceNo, recipientName, recipientPhoneNumber, recipientAddress, warehouseId, warehouseName, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, itemList, signature, uniqueNo, userId, userName);
        String gongxiaoOutboundOrderNo = null;
        try {
            gongxiaoOutboundOrderNo = outboundService.createOutboundOrder(rpcHeader, sourceChannelId, outboundType, projectId, customerId, traceNo, recipientName, recipientPhoneNumber, recipientAddress, warehouseId, warehouseName, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, itemList, signature, uniqueNo, userId, userName);
            logger.info("#traceId={}# [OUT][createOutboundOrder]: get createOutboundOrder success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
        return gongxiaoOutboundOrderNo;

    }


    /**
     * 销售模块 创建出库单
     *
     * @param requst
     * @return
     */
    @RequestMapping("/createOutboundOrder2")
    @ResponseBody
    public Map<String, List<OutboundOrderItem>> createOutboundOrder2(@RequestBody SalesCreateOutboundRequest requst) {
        Map<String, List<OutboundOrderItem>> map = new HashMap<>();
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setTraceId("01").setUid(String.valueOf(requst.getUserId())).setUsername(requst.getUserName()).build();
        try {
            String sourceChannelId = requst.getSourceChannelId();
            int outboundType = requst.getOutboundType();
            String projectId = requst.getProjectId();
            String customerId = requst.getCustomerId();
            String traceNo = requst.getTraceNo();
            String recipientName = requst.getRecipientName();
            String recipientPhoneNumber = requst.getRecipientPhoneNumbe();
            String recipientAddress = requst.getRecipientAddress();
            String provinceName = requst.getProvinceName();
            String cityName = requst.getCityName();
            int shippingMode = requst.getShippingMode();
            String logisticsCompanyName = requst.getLogisticsCompanyName();
            String logisticsNo = requst.getLogisticsNo();
            String note = requst.getNote();
            int totalQuantity = requst.getTotalQuantity();
            List<PlanOutboundOrderItem> itemList = requst.getItemLis();
            String signature = requst.getSignature();
            String uniqueNo = requst.getUniqueNo();
            Date arrivalDate = requst.getArrivalDate();
            long userId = requst.getUserId();
            String userName = requst.getUserName();
            logger.info("#traceId={}# [IN][createOutboundOrder2]: sourceChannelId={},outboundType={},projectId={},customerId={},traceNo={},recipientName={},recipientPhoneNumber={},recipientAddress={},provinceName={},cityName={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={},uniqueNo={},arrivalDate={}", rpcHeader.getTraceId(), sourceChannelId, outboundType, projectId, customerId, traceNo, recipientName, recipientPhoneNumber, recipientAddress, provinceName, cityName, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature, uniqueNo, arrivalDate);
            map = outboundService.createOutboundOrder2(rpcHeader, sourceChannelId, outboundType, projectId, customerId, traceNo, recipientName, recipientPhoneNumber, recipientAddress, provinceName, cityName, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, itemList, signature, uniqueNo, arrivalDate, userId, userName);
            logger.info("#traceId={}# [OUT][createOutboundOrder2]: get createOutboundOrder2 success", rpcHeader.getTraceId());

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
        return map;
    }

    /**
     * 出库单签收
     *
     * @param rpcHeader
     * @param transporter
     * @param custOrdNo
     * @param tmsOrdNo
     * @param remark
     * @param signedBy
     * @param postedBy
     * @param signedPhone
     * @param signedTime
     */
    @RequestMapping("/sureSighIn")
    @ResponseBody
    public GongxiaoResult sureSighIn(GongxiaoRpc.RpcHeader rpcHeader, String transporter, String custOrdNo, String tmsOrdNo, String remark, String signedBy, String postedBy, String signedPhone, Date signedTime) {
        logger.info("#traceId={}# [IN][sureSighIn]: transporter={},custOrdNo={},tmsOrdNo={},remark={},signedBy={},postedBy={},signedPhone={},signedTime={}", rpcHeader.getTraceId(), transporter, custOrdNo, tmsOrdNo, remark, signedBy, postedBy, signedPhone, signedTime);
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            outboundService.sureSighIn(rpcHeader, transporter, custOrdNo, tmsOrdNo, remark, signedBy, postedBy, signedPhone, signedTime);
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            logger.info("#traceId={}# [OUT][sureSighIn]: get sureSighIn success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
