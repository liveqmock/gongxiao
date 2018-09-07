package com.yhglobal.gongxiao.warehouse;

import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 取消订单controller
 * @author liukai
 */
@RequestMapping("/stock")
@Controller
public class CancelOrderController {
    private static Logger logger = LoggerFactory.getLogger(CancelOrderController.class);


    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象

    @Autowired
    PortalConfig portalConfig;

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
    public GongxiaoResult notifyWmsCancelOutStock(String projectId, String productCode, String warehouseId, String gongxiaoOutboundOrderNo, HttpServletRequest request, HttpServletResponse response){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][notifyWmsCancelOutStock] params:  projectId={}，productCode={}, warehouseId={}，gongxiaoOutboundOrderNo={}", traceId,projectId,productCode,warehouseId,gongxiaoOutboundOrderNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String signature = rpcHeader.getUsername();
            String channelTocken = "43523645676";
            XpsWarehouseManager.cancelOutboundOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),channelTocken,projectId,warehouseId,gongxiaoOutboundOrderNo);
            logger.info("#traceId={}# [OUT] get notifyWmsCancelOutStock success", traceId);
            gongxiaoResult= new GongxiaoResult(0,"success");
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
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
    public GongxiaoResult notifyWmsCancelInStock(String projectId, String warehouseId, String gongxiaoInboundOrderNo, HttpServletRequest request, HttpServletResponse response){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][notifyWmsCancelInStock] params:  projectId={}，warehouseId={}，gongxiaoInboundOrderNo={}", traceId,warehouseId,projectId,gongxiaoInboundOrderNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String signature = rpcHeader.getUsername();
            String channelTocken = "43523645676";
            XpsWarehouseManager.cancelInboundOrder(portalConfig.getWarehouseUrl(),WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),channelTocken,projectId,warehouseId,gongxiaoInboundOrderNo);
            gongxiaoResult = new GongxiaoResult(0,"success");
            logger.info("#traceId={}# [OUT] get notifyWmsCancelInStock success", traceId);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
