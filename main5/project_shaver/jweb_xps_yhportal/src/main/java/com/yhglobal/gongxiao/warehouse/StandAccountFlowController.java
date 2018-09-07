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

/**
 * 进销存台账
 *
 * @author liukai
 */
@Controller
@RequestMapping("/inventory")
public class StandAccountFlowController {

    private static Logger logger = LoggerFactory.getLogger(StandAccountFlowController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 查询台账信息
     *
     * @param pageNumber
     * @param pageSize
     * @param projectId   项目id
     * @param productCode 商品编码
     * @param productName 商品名称
     * @param warehouseId 仓库id
     * @param startDate   起始时间
     * @param endDate     结束时间
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/standFlow", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectAccountInfo(int pageNumber, int pageSize, String projectId, String productCode, String productName, String warehouseId, String startDate, String endDate, HttpServletRequest request, HttpServletResponse response) {
        String traceId = null;
        PageInfo<InventoryLedger> inventoryLedgerPageInfo = new PageInfo<>();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectAccountInfo] params: projectId={}, productCode={}, productName={}, warehouseId={}, startDate={}, endDate={}, pageNumber={}, pageSize={}", traceId, projectId, productCode, productName, warehouseId, startDate, endDate, pageNumber, pageSize);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String warehouseIdReq = (warehouseId == null) ? "" : warehouseId;
            String startDateReq = (startDate == null) ? "" : startDate;
            String endDateReq = (endDate == null) ? "" : endDate;
            String channelToken = "4455668789";
            inventoryLedgerPageInfo = XpsWarehouseManager.selectAccountInfo(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken, projectId, productCode, productName, warehouseIdReq, startDateReq, endDateReq, pageNumber, pageSize);
            logger.info("#traceId={}# [OUT] get selectAccountInfo success", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), inventoryLedgerPageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }
}
