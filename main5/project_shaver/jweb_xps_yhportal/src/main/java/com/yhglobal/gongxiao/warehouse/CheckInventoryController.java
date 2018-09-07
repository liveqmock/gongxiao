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
import com.yhglobal.gongxiao.warehouseapi.model.InventoryCheckModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/duizhan")
@Controller
public class CheckInventoryController {
    private static Logger logger = LoggerFactory.getLogger(CheckInventoryController.class);

    @Autowired
    PortalConfig portalConfig;
    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象


    /**
     * 核对wms库存
     * @return
     */
    @RequestMapping("/checkinventory")
    @ResponseBody
    public GongxiaoResult checkinventory(String projectId, String warehouseId, String productCode, String productName, int pageNumber, int pageSize, HttpServletRequest request){
        PageInfo<InventoryCheckModel> inventoryCheckModelPageInfo = new PageInfo<>();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String signature = rpcHeader.getUsername();
            logger.info("#traceId={}# [IN][checkinventory] params: projectId={},warehouseId={},projectId={},productCode={},productName={},pageNumber={},pageSize={}", traceId,projectId,projectId,warehouseId,productCode,productName,pageNumber,pageSize);
            String channelToken = "574656879";
            inventoryCheckModelPageInfo = XpsWarehouseManager.selectCheckInventory(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken, projectId,warehouseId,productCode,productName,pageNumber, pageSize);
            logger.info("#traceId={}# [OUT] get checkinventory success", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), inventoryCheckModelPageInfo);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }


}
