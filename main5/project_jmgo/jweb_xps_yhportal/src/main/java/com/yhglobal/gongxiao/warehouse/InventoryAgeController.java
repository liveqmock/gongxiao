package com.yhglobal.gongxiao.warehouse;

import com.github.pagehelper.PageInfo;
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
import com.yhglobal.gongxiao.warehouseapi.model.InventoryAge;
import com.yhglobal.gongxiao.warehouseapi.model.InventoryLedger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/inventoryage")
public class InventoryAgeController {

    private static Logger logger = LoggerFactory.getLogger(InventoryAgeController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 查询批次库存库龄报表信息
     *
     * @param pageNumber
     * @param pageSize
     * @param projectId   项目id
     * @param batchNo 商品编码
     * @param inboundOrderNo 入库单号
     * @param outboundOrderNo 出库单号
     * @param startDate   起始时间
     * @param endDate     结束时间
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getInventoryAge", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getInventoryAgeInfo(String projectId, String batchNo, String inboundOrderNo, String outboundOrderNo,  String startDate, String endDate, int pageNumber, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        String traceId = null;
        PageInfo<InventoryAge> inventoryAgePageInfo = new PageInfo<>();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getInventoryAgeInfo] params: projectId={}, batchNo={}, inboundOrderNo={}, outboundOrderNo={}, startDate={}, endDate={}, pageNumber={}, pageSize={}", traceId, projectId, batchNo, inboundOrderNo, outboundOrderNo, startDate, endDate, pageNumber, pageSize);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String projectIdReq = (projectId == null) ? "" : projectId;
            String batchNoReq = (batchNo == null) ? "" : batchNo;
            String inboundOrderNoReq = (inboundOrderNo == null) ? "" : inboundOrderNo;
            String outboundOrderNoReq = (outboundOrderNo == null) ? "" : outboundOrderNo;
            String startDateReq = (startDate == null) ? "" : startDate;
            String endDateReq = (endDate == null) ? "" : endDate;
            String channelToken = "4455668789";
            inventoryAgePageInfo = XpsWarehouseManager.getInventoryAgeInfo(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_JMGO.getChannelId(), channelToken, projectIdReq, batchNoReq, inboundOrderNoReq, outboundOrderNoReq, startDateReq, endDateReq, pageNumber, pageSize);
            logger.info("#traceId={}# [OUT] get getInventoryAgeInfo success", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), inventoryAgePageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }
}
