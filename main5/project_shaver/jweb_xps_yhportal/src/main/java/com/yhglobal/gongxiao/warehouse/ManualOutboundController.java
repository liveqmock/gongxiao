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
 * 其它出库单
 *
 * @author liukai
 */
@Controller
@RequestMapping("/manualOutbound")
public class ManualOutboundController {

    private static Logger logger = LoggerFactory.getLogger(ManualInboundController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 新增其他出库单
     *
     * @param projectId       项目id
     * @param warehouseId     仓库id
     * @param warehouseName   仓库名称
     * @param delieverAddress 发货地址
     * @param supplierName    供应商
     * @param businessDate    业务日期
     * @param outboundType    出库类型
     * @param remark          备注
     * @param itemsInfo       出库商品明细
     * @param request
     * @return
     */
    @RequestMapping("/insertOutbound")
    @ResponseBody
    public GongxiaoResult insertManualOutboundOrder(String projectId,
                                                    String warehouseId,
                                                    String warehouseName,
                                                    String delieverAddress,
                                                    String supplierName,
                                                    String businessDate,
                                                    int outboundType,
                                                    String remark,
                                                    String itemsInfo, HttpServletRequest request) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][insertManualOutboundOrder] params: projectId={},warehouseId={},warehouseName={},delieverAddress={},supplierName={},businessDate={},outboundType={},remark={},itemsInfo={} ", traceId, projectId, warehouseId, warehouseName, delieverAddress, supplierName, businessDate, outboundType, remark, itemsInfo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "352365457";
            gongxiaoResult = XpsWarehouseManager.insertManualOutboundOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken, projectId, warehouseId, warehouseName, delieverAddress, supplierName, businessDate, outboundType, remark, itemsInfo);
            logger.info("#traceId={}# [OUT] insertManualOutboundOrder success: ", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult.setReturnCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
        }
        return gongxiaoResult;
    }

    /**
     * 其他出库批次查询
     *
     * @param projectId   项目id
     * @param productCode 货品编码
     * @param warehouseId 货品编码
     * @return
     */
    @RequestMapping("/batch")
    @ResponseBody
    public GongxiaoResult getBatchInfo(HttpServletRequest request, HttpServletResponse response, int pageNumber, int pageSize, String projectId, String productCode, String warehouseId) {

        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getBatchInfo][IN] params:  salesOrderNo={}", traceId);
            String channelToken = "4345647586";
            gongxiaoResult = XpsWarehouseManager.getBatchDetailByWarehouse(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken, projectId, productCode, warehouseId, pageNumber, pageSize);
            logger.info("#traceId={}# [OUT][getBatchInfo] get getBatchInfo success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
