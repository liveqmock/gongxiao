package com.yhglobal.gongxiao.phjd.foundation.storage;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
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
 * 入库单
 */
@Controller
@RequestMapping("/storage/warehousereceipt")
public class WarehouseReceiptController {
    private static Logger logger = LoggerFactory.getLogger(WarehouseReceiptController.class);
    @Autowired
    PortalConfig portalConfig;
    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 查询所有的WMS入库单
     *
     * @param gonxiaoInboundNo  入库单号
     * @param batchNo           批次号
     * @param orderType         订单类型
     * @param createTimeBegin   创建起始时间
     * @param createTimeTo      创建结束时间
     * @param warehouseId       仓库id
     * @param supplier          供应商
     * @param wmsInboundOrderNo 入库通知单号
     * @param request
     * @param pageNumber        页码
     * @param pageSize          页数
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectInboundOrder")
    public GongxiaoResult selectInboundOrder(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "") String gonxiaoInboundNo,
                                             @RequestParam(defaultValue = "") String batchNo,
                                             @RequestParam(defaultValue = "") String orderType,
                                             @RequestParam(defaultValue = "") String warehouseId,
                                             @RequestParam(defaultValue = "") String supplier,
                                             @RequestParam(defaultValue = "") String wmsInboundOrderNo,
                                             @RequestParam(defaultValue = "") String createTimeBegin,
                                             @RequestParam(defaultValue = "") String createTimeTo,
                                             @RequestParam(defaultValue = "1") int pageNumber,
                                             @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectInboundOrder] params: projectId={},gonxiaoInboundNo={},batchNo={},orderType={},warehouseId={},supplier={},wmsInboundOrderNo={},createTimeBegin={},String createTimeTo={}",
                    traceId, String.valueOf(portalUserInfo.getProjectId()), gonxiaoInboundNo, batchNo, orderType, warehouseId, supplier, wmsInboundOrderNo, createTimeBegin, createTimeTo);
            String channelToken = "343467456";
            gongxiaoResult = XpsWarehouseManager.selectWmsInboundNotifyOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, String.valueOf(portalUserInfo.getProjectId()), gonxiaoInboundNo, batchNo, createTimeBegin, createTimeTo, warehouseId, supplier, pageNumber, pageSize);
            logger.info("#traceId={}# [OUT] get selectInboundOrder success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 根据wms入库单查询wms入库单基础信息
     *
     * @param gongxiaoInboundOrderNo 入库单号
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectInboundOrderByOrderNo")
    public GongxiaoResult selectInboundOrderByOrderNo(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestParam(defaultValue = "") String gongxiaoInboundOrderNo,
                                                      @RequestParam(defaultValue = "") String wmsInboundOrderNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectInboundOrderByOrderNo] params: projectId={},gongxiaoInboundOrderNo={},wmsInboundOrderNo={}",
                    traceId, String.valueOf(portalUserInfo.getProjectId()), gongxiaoInboundOrderNo, wmsInboundOrderNo);
            String channelToken = "457665867";
            gongxiaoResult = XpsWarehouseManager.selectWmsInboundNotifyByOrderNo(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, String.valueOf(portalUserInfo.getProjectId()), gongxiaoInboundOrderNo, wmsInboundOrderNo);
            logger.info("#traceId={}# [OUT] get selectInboundOrderByOrderNo success: result.size()={}", traceId, 1);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据wms入库单查询入库单明细
     *
     * @param gongxiaoInboundOrderNo 入库单号
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectInboundOrderInfoByOrderNo")
    public GongxiaoResult selectInboundOrderInfoByOrderNo(HttpServletRequest request, HttpServletResponse response,
                                                          @RequestParam(defaultValue = "") String gongxiaoInboundOrderNo,
                                                          @RequestParam(defaultValue = "") String wmsInboundOrderNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectInboundOrderInfoByOrderNo] params: projectId={},gongxiaoInboundOrderNo={}", traceId, String.valueOf(portalUserInfo.getProjectId()), gongxiaoInboundOrderNo);
            String channelToken = "457665867";
            gongxiaoResult = XpsWarehouseManager.selectWmsInboundNotifyOrderItem(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, String.valueOf(portalUserInfo.getProjectId()), gongxiaoInboundOrderNo, wmsInboundOrderNo);
            logger.info("#traceId={}# [OUT] get selectInboundOrderInfoByOrderNo success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
