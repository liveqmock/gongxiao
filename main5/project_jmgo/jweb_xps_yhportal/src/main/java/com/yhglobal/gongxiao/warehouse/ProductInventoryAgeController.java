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
 * 报表库龄180天以上库存情况
 */

@Controller
@RequestMapping("/inventory180Age")
public class ProductInventoryAgeController {
    private static Logger logger = LoggerFactory.getLogger(ProductInventoryAgeController.class);


    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    @RequestMapping(value = "/get180Age", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getInventory180Age(String projectId, HttpServletRequest request, HttpServletResponse response) {
        logger.info("#traceId={}# [IN][getInventory180Age] params: projectId = {} ", projectId);
        String traceId = "1325456";
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String channelToken = "4455668789";
        try {
            gongxiaoResult = XpsWarehouseManager.get180InventoryAge(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_JMGO.getChannelId(),channelToken,projectId);
            logger.info("#traceId={}# [OUT] get getInventoryAgeInfo success", rpcHeader.getTraceId());
            return gongxiaoResult;
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }
}
