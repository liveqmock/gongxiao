package com.yhglobal.gongxiao.warehouse.controller.warehouse;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.service.InboundService;
import com.yhglobal.gongxiao.warehouse.service.OutboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 取消订单controller
 * @author liukai
 */
@RequestMapping("/cancel")
@Controller
public class CancelOrderController {
    private static Logger logger = LoggerFactory.getLogger(CancelOrderController.class);

    @Autowired
    InboundService inboundService;

    @Autowired
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
    @RequestMapping(value = "/outStock",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult notifyWmsCancelOutStock(String projectId, String productCode, String warehouseId, String gongxiaoOutboundOrderNo, HttpServletRequest request, HttpServletResponse response){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            logger.info("#traceId={}# [IN][notifyWmsCancelOutStock] params:  projectId={}，productCode={}, warehouseId={}，gongxiaoOutboundOrderNo={}", traceId,projectId,productCode,warehouseId,gongxiaoOutboundOrderNo);
            traceId = "";
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "","");
            String signature = rpcHeader.getUsername();
            outboundService.cancelOrder(rpcHeader,projectId,warehouseId,gongxiaoOutboundOrderNo,productCode,signature);
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
    @RequestMapping(value = "/inStock",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult notifyWmsCancelInStock(String projectId, String warehouseId, String gongxiaoInboundOrderNo, HttpServletRequest request, HttpServletResponse response){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            logger.info("#traceId={}# [IN][notifyWmsCancelInStock] params:  projectId={}，warehouseId={}，gongxiaoInboundOrderNo={}", traceId,warehouseId,projectId,gongxiaoInboundOrderNo);
            traceId = "";
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
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
