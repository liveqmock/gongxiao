package com.yhglobal.gongxiao.warehouse;

import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
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
 * 报表整体库龄情况
 */
@Controller
@RequestMapping("/wholeinventoryAge")
public class WholeInventoryAgeController {

    private static Logger logger = LoggerFactory.getLogger(WholeInventoryAgeController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    @RequestMapping(value = "/getInventoryAge", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getWholeInventoryAge(String projectId, HttpServletRequest request, HttpServletResponse response) {
        logger.info("#traceId={}# [IN][getWholeInventoryAge] params: projectId = {} ", projectId);
        String traceId = "15827545";
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
        String channelToken = "4455668789";
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            gongxiaoResult = XpsWarehouseManager.getWholeInventoryAge(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken, projectId);
            logger.info("#traceId={}# [OUT] get getInventoryAgeInfo success", rpcHeader.getTraceId());
            return gongxiaoResult;
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }
}
