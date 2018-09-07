package com.yhglobal.gongxiao.warehousemanager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouse.service.InboundService;
import com.yhglobal.gongxiao.warehouse.service.OutboundService;
import com.yhglobal.gongxiao.warehousemanagement.dto.PlanInboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.dto.PlanOutboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 取消订单controller
 * @author liukai
 */
@RequestMapping("/stock")
@Controller
public class CancelOrderController {
    private static Logger logger = LoggerFactory.getLogger(CancelOrderController.class);

    @Autowired
    private PortalConfig portalConfig;
    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象
    @Reference
    InboundService inboundService;

    @Reference
    OutboundService outboundService;

    /**
     *
     * 通知wmx取消出库单
     * @param projectId     项目id
     * @param warehouseId   仓库id
     * @param gongxiaoOutboundOrderNo  出库单号
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/notifyWmsCancelOutStock")
    @ResponseBody
    public GongxiaoResult notifyWmsCancelOutStock(String projectId,String productCode,String warehouseId,String gongxiaoOutboundOrderNo, HttpServletRequest request, HttpServletResponse response){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            logger.info("#traceId={}# [IN][notifyWmsCancelOutStock] params:  projectId={}，productCode={}, warehouseId={}，gongxiaoOutboundOrderNo={}", traceId,projectId,productCode,warehouseId,gongxiaoOutboundOrderNo);
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String signature = rpcHeader.getUsername();
            outboundService.cancelOrder(rpcHeader,projectId,productCode,warehouseId,gongxiaoOutboundOrderNo,signature);
            logger.info("#traceId={}# [OUT] get notifyWmsCancelOutStock success", traceId);
            gongxiaoResult= new GongxiaoResult(0,"success");
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     *
     * 通知wmx取消出库单
     * @param projectId     项目id
     * @param warehouseId   仓库id
     * @param gongxiaoInboundOrderNo  出库单号
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/notifyWmsCancelInStock")
    @ResponseBody
    public GongxiaoResult notifyWmsCancelInStock(String projectId,String warehouseId,String gongxiaoInboundOrderNo,HttpServletRequest request, HttpServletResponse response){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            logger.info("#traceId={}# [IN][notifyWmsCancelInStock] params:  projectId={}，warehouseId={}，gongxiaoInboundOrderNo={}", traceId,warehouseId,projectId,gongxiaoInboundOrderNo);
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String signature = rpcHeader.getUsername();
            inboundService.cancelInboundOrder(rpcHeader,projectId,warehouseId,gongxiaoInboundOrderNo,signature);
            gongxiaoResult = new GongxiaoResult(0,"success");
            logger.info("#traceId={}# [OUT] get notifyWmsCancelInStock success", traceId);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
