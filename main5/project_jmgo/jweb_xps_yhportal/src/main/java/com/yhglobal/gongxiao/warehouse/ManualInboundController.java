package com.yhglobal.gongxiao.warehouse;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 其他入单管理controller
 * @author liukai
 */
@Controller
@RequestMapping("/manualInbound")
public class ManualInboundController {

    private static Logger logger = LoggerFactory.getLogger(ManualInboundController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;


    /**
     * 添加手工入库单
     * @param projectId         项目id
     * @param warehouseId       仓库id
     * @param warehouseName     仓库名称
     * @param recieveAddress    收货地址
     * @param supplierName      供应商
     * @param businessDate      业务日期
     * @param remark            备注
     * @param inboundType       入库类型
     * @param purchaseItemInfoJson      入库单商品明细
     * @param request
     * @return
     */
    @RequestMapping("/insertInbound")
    @ResponseBody
    public GongxiaoResult insertManualInboundOrder(String projectId,
                                                   String warehouseId,
                                                   String warehouseName,
                                                   String recieveAddress,
                                                   String supplierName,
                                                   String businessDate,
                                                   String remark,
                                                   int inboundType,
                                                   String purchaseItemInfoJson,
                                                   HttpServletRequest request){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][insertManualInboundOrder] params: projectId={},warehouseId={},warehouseName={},recieveAddress={},supplierName={},businessDate={},remark={},inboundType={},purchaseItemInfoJson={} ",traceId, projectId,warehouseId,warehouseName,recieveAddress,supplierName,businessDate,remark,inboundType,purchaseItemInfoJson);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "23453467";
            gongxiaoResult = XpsWarehouseManager.insertManualInboundOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_JMGO.getChannelId(),channelToken,projectId,warehouseId,warehouseName,recieveAddress,supplierName,businessDate,remark,inboundType,purchaseItemInfoJson);
            logger.info("#traceId={}# [OUT] insertManualInboundOrder success: ", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
