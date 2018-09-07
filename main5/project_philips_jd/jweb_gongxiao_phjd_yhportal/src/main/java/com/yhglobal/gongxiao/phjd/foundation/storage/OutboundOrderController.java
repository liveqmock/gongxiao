package com.yhglobal.gongxiao.phjd.foundation.storage;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 出库单
 */
@Controller
@RequestMapping("/storage/outboundorder")
public class OutboundOrderController {
    private static Logger logger = LoggerFactory.getLogger(OutboundOrderController.class);
    @Autowired
    PortalConfig portalConfig; //property注入类
    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    /**
     * 查询所有出库单
     *
     * @param request
     * @param response
     * @param gongxiaoOutNo    出库单号
     * @param salesNo          销售单号
     * @param orderType        单据类型
     * @param customer         客户
     * @param createTimeBeging 创建起始时间
     * @param createTimeLast   创建结束时间
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectOutboundOrder")
    public GongxiaoResult selectOutboundOrder(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(defaultValue = "") String gongxiaoOutNo,
                                              @RequestParam(defaultValue = "") String salesNo,
                                              @RequestParam(defaultValue = "") String orderType,
                                              @RequestParam(defaultValue = "") String customer,
                                              @RequestParam(defaultValue = "") String createTimeBeging,
                                              @RequestParam(defaultValue = "") String createTimeLast,
                                              @RequestParam(defaultValue = "1") int pageNumber,
                                              @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectOutboundOrder] params: projectId={}, gongxiaoOutNo={}, wmsOutboundNo={}, orderType={}, customer={},createTimeBeging={}, createTimeLast={}",
                    traceId, String.valueOf(portalUserInfo.getProjectId()), gongxiaoOutNo, salesNo, orderType, customer, createTimeBeging, createTimeLast);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "233564356";
            gongxiaoResult = XpsWarehouseManager.selectWmsOutboundOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, String.valueOf(portalUserInfo.getProjectId()), gongxiaoOutNo, salesNo, orderType, customer, createTimeBeging, createTimeLast, pageNumber, pageSize);
            logger.info("#traceId={}# [OUT] get selectOutboundOrder success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据出库单号查询wms出库单基本信息
     *
     * @param gongxiaoOutboundOrderNo 出库单号
     * @param wmsOutboundOrderNo      wms出库单号
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectByOutboundNum")
    public GongxiaoResult selectByOutboundNum(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(defaultValue = "") String gongxiaoOutboundOrderNo,
                                              @RequestParam(defaultValue = "") String wmsOutboundOrderNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectByOutboundNum] params: projectId={},gongxiaoOutboundOrderNo={},wmsOutboundOrderNo={}",
                    traceId, String.valueOf(portalUserInfo.getProjectId()), gongxiaoOutboundOrderNo, wmsOutboundOrderNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "233564356";
            gongxiaoResult = XpsWarehouseManager.selectWmsByOutboundNum(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, String.valueOf(portalUserInfo.getProjectId()), gongxiaoOutboundOrderNo, wmsOutboundOrderNo);
            logger.info("#traceId={}# [OUT] get selectByOutboundNum success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据出库单号查询wms出库单明细
     *
     * @param gongxiaoOutboundOrderNo 出库单号
     * @param wmsOutboundOrderNo      wms出库单号
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectOutboundOrderItem")
    public GongxiaoResult selectOutboundOrderItem(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam(defaultValue = "") String gongxiaoOutboundOrderNo,
                                                  @RequestParam(defaultValue = "") String wmsOutboundOrderNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectOutboundOrderItem] params: projectId={}, gongxiaoOutboundOrderNo={},wmsOutboundOrderNo={}",
                    traceId, String.valueOf(portalUserInfo.getProjectId()), gongxiaoOutboundOrderNo, wmsOutboundOrderNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "233564356";
            gongxiaoResult = XpsWarehouseManager.selectWmsOutboundOrderItem(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, String.valueOf(portalUserInfo.getProjectId()), gongxiaoOutboundOrderNo, wmsOutboundOrderNo);
            logger.info("#traceId={}# [OUT] get selectOutboundOrderItem success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
