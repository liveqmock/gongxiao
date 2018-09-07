package com.yhglobal.gongxiao.warehouse;

import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 插入库存数据的工具类controller
 * @author liukai
 */
@RequestMapping("/inventory")
@Controller
public class InSertInventoryController {

    private static Logger logger = LoggerFactory.getLogger(InSertInventoryController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    @RequestMapping("/insert")
    @ResponseBody
    public GongxiaoResult insertInventory(String projectId,HttpServletRequest request){
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
        logger.info("#traceId={}# [IN] [insertInventory] params: projectId={}", traceId,projectId);
        GongxiaoResult result = new GongxiaoResult();
        try {
            String channelTocken = "437834345";
            result = XpsWarehouseManager.insertInventory(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),channelTocken,projectId);
            logger.info("[OUT] get insertInventory success");
            return result;
        }catch (Exception e){
            logger.error("#errorMessage：{}",e.getMessage());
            return new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(), ErrorCode.UNKNOWN_ERROR);
        }

    }

    @RequestMapping("/checkeasinventory")
    @ResponseBody
    public GongxiaoResult checkEasInventory(String projectId,HttpServletRequest request){
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
        logger.info("#traceId={}# [IN] [checkEasInventory] params: projectId={}", traceId, projectId);
        GongxiaoResult result = new GongxiaoResult();
        try {
            String channelTocken = "437834345";
            result = XpsWarehouseManager.checkEasInventory(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),channelTocken,projectId);
            logger.info("[OUT] get checkEasInventory success");
            return result;
        }catch (Exception e){
            logger.error("#errorMessage：{}",e.getMessage());
            return new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(), ErrorCode.UNKNOWN_ERROR);
        }

    }

    @RequestMapping("/selectEasInventoryCheck")
    @ResponseBody
    public GongxiaoResult selectEasInventoryCheck(String projectId,HttpServletRequest request){
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
        logger.info("#traceId={}# [IN] [selectEasInventoryCheck] params: projectId={}", traceId, projectId);
        GongxiaoResult result = new GongxiaoResult();
        try {
            String channelTocken = "437834345";
            result = XpsWarehouseManager.selectEasInventoryCheck(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),channelTocken,projectId);
            logger.info("[OUT] get selectEasInventoryCheck success");
            return result;
        }catch (Exception e){
            logger.error("#errorMessage：{}",e.getMessage());
            return new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(), ErrorCode.UNKNOWN_ERROR);
        }

    }
}
