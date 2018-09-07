package com.yhglobal.gongxiao.wmscomfirm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.warehouse.service.WmsConfirmInboundService;
import com.yhglobal.gongxiao.warehouse.service.WmsConfirmOutboundService;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.*;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 接收WMS确认信息Controller
 * @author liukai
 */
@Controller
public class WmsConfirmController {
    private static Logger logger = LoggerFactory.getLogger(WmsConfirmController.class);

    @Reference
    WmsConfirmInboundService wmsConfirmInboundService;

    @Reference
    WmsConfirmOutboundService wmsConfirmOutboundService;


    @RequestMapping("/wmsNotification")
    @ResponseBody
    public WmsConfirmResponse confirmFromWms(@RequestBody String requestJson, HttpServletRequest request) {

        String traceId = DateTimeIdGenerator.nextId(BizNumberType.WMS_TRACE_ID);
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "1", "WMS系统");
        WmsConfirmResponse responseResult; //返回值
        WmsConfirmRequst wmsConfirmRequest; //WMS的确认信息
        try {
            logger.info("[IN][confirmFromWms] params: contextPath={} requestJson:{}", request.getServletPath(), requestJson);
            wmsConfirmRequest = JSON.parseObject(requestJson, WmsConfirmRequst.class);
            String errorMsg = null;
            if (wmsConfirmRequest == null) {
                logger.error("got null object after parsing json string: class={} jsonString={}", WmsConfirmRequst.class.getName(), requestJson);
                errorMsg = "json string not correct";
                return responseResult = new WmsConfirmResponse(false, null, errorMsg, "1");
            }

            if (wmsConfirmRequest.getMethod().equals("yunji.esb.entryorder.confirm")) { //如果是入库确认信息
                Data instockConfirmRequest = JSON.parseObject(wmsConfirmRequest.getData(), Data.class);
                if ("".equals(instockConfirmRequest.getCkid())) {
                    errorMsg = "字段:ckid是必传的";
                    responseResult = new WmsConfirmResponse(false, null, errorMsg, "1");
                    logger.error("errorMessage:" + JSON.toJSONString(responseResult));
                    return responseResult;
                } else if ("".equals(instockConfirmRequest.getOrderNo())) {
                    errorMsg = "字段:orderNo是必传的";
                    responseResult = new WmsConfirmResponse(false, null, errorMsg, "1");
                    logger.error("errorMessage:" + JSON.toJSONString(responseResult));
                    return responseResult;
                }else if ("".equals(instockConfirmRequest.getConfirmType())) {
                    errorMsg = "字段:confirmType是必传的";
                    responseResult = new WmsConfirmResponse(false, null, errorMsg, "1");
                    logger.error("errorMessage:" + JSON.toJSONString(responseResult));
                    return responseResult;
                } else if ("".equals(instockConfirmRequest.getInboundBatchNo())) {
                    errorMsg = "字段:inboundBatchNo是必传的";
                    responseResult = new WmsConfirmResponse(false, null, errorMsg, "1");
                    logger.error("errorMessage:" + JSON.toJSONString(responseResult));
                    return responseResult;
                } else if (null == instockConfirmRequest.getStockInDetails()) {
                    errorMsg = "字段:stockInDetails是必传的";
                    responseResult = new WmsConfirmResponse(false, null, errorMsg, "1");
                    logger.error("errorMessage:" + JSON.toJSONString(responseResult));
                    return responseResult;
                } else {
                    try {
                        wmsConfirmInboundService.confirmWmsInboundInfo(rpcHeader,instockConfirmRequest);
                        responseResult = new WmsConfirmResponse(true, "请求成功", null, "0");
                        logger.info("请求结果："+ JSON.toJSONString(responseResult));
                        return responseResult;
                    }catch (Exception e){
                        responseResult = new WmsConfirmResponse(true, "请求成功", null, "0");
                        logger.warn("#WMS response#: {}", JSON.toJSONString(responseResult));
                        logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                        return responseResult;
                    }

                }
            } else if (wmsConfirmRequest.getMethod().equals("yunji.esb.stockout.confirm")) { //如果是出库确认信息
                com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm.Data outStockConfirmRequest = JSON.parseObject(wmsConfirmRequest.getData(), com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm.Data.class);

                if ("".equals(outStockConfirmRequest.getOrderNo())) {
                    errorMsg = "字段:orderNo是必传的";
                    responseResult = new WmsConfirmResponse(false, null, errorMsg, "1");
                    logger.error("#WMS response#: {}", JSON.toJSONString(responseResult));
                    return responseResult;
                } else if ("".equals(outStockConfirmRequest.getConfirmType())) {
                    errorMsg = "字段:confirmType是必传的";
                    responseResult = new WmsConfirmResponse(false, null, errorMsg, "1");
                    logger.error("#WMS response#: {}", JSON.toJSONString(responseResult));
                    return responseResult;
                } else if ("".equals(outStockConfirmRequest.getOutBizCode())) {
                    errorMsg = "字段:outBizCode是必传的";
                    responseResult = new WmsConfirmResponse(false, null, errorMsg, "1");
                    logger.error("#WMS response#: {}", JSON.toJSONString(responseResult));
                    return responseResult;
                } else if ("".equals(outStockConfirmRequest.getOrderType()) || null == outStockConfirmRequest.getOrderType()) {
                    errorMsg = "字段:orederType是必传的";
                    responseResult = new WmsConfirmResponse(false, null, errorMsg, "1");
                    logger.error("#WMS response#: {}", JSON.toJSONString(responseResult));
                    return responseResult;
                } else if (null == outStockConfirmRequest.getOrderItems()) {
                    errorMsg = "字段:orderItems是必传的";
                    responseResult = new WmsConfirmResponse(false, null, errorMsg, "1");
                    logger.error("#WMS response#: {}", JSON.toJSONString(responseResult));
                    return responseResult;
                } else {

                    try {
                        wmsConfirmOutboundService.confirmWmsOutboundInfo(outStockConfirmRequest);
                        responseResult = new WmsConfirmResponse(true, "请求发送成功", null, "0");
                        logger.info("#WMS response#: {}", JSON.toJSONString(responseResult));
                        return responseResult;
                    } catch (Exception e) {
                        responseResult = new WmsConfirmResponse(true, "请求发送成功", null, "0");
                        logger.error("#WMS response#: {}", JSON.toJSONString(responseResult));
                        logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                        return responseResult;
                    }

                }
            } else { //如果不是入库或者出库
                errorMsg = wmsConfirmRequest.getMethod() + "方法不存在";
                responseResult = new WmsConfirmResponse(false, null, errorMsg, "1");
                logger.error("#WMS response#: {}", JSON.toJSONString(responseResult));
                return responseResult;
            }
        } catch (Exception e) {
            responseResult = new WmsConfirmResponse(false, null, "你输入的请求不合法,请检查输入的格式", "1");
            logger.error("#WMS response#: {}", JSON.toJSONString(responseResult));
            return responseResult;
        }

    }

    @RequestMapping(value="/wmsNotification/isOnline", produces="application/json;charset=utf-8")
    @ResponseBody
    public String isOnline() { //用于探测当前接受wms通知的服务是否在线
        return "true";
    }
}
