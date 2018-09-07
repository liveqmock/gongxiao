package com.yhglobal.gongxiao.warehousemanager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.inventory.service.InventoryCheckService;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehousemanagement.bo.InventoryCheckModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/duizhan")
@Controller
public class InventoryQuerryController {
    private static Logger logger = LoggerFactory.getLogger(InventoryQuerryController.class);

    @Autowired
    private PortalConfig portalConfig;
    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象

    @Reference
    InventoryCheckService inventoryCheckService;


    /**
     * 核对wms库存
     * @return
     */
    @RequestMapping("/checkinventory")
    @ResponseBody
    public GongxiaoResult checkinventory(String projectId, String warehouseId, String productCode, String productName, int pageNumber, int pageSize,HttpServletRequest request){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String signature = rpcHeader.getUsername();
            logger.info("#traceId={}# [IN][checkinventory] params: projectId={},warehouseId={},projectId={},productCode={},productName={},pageNumber={},pageSize={}", traceId,projectId,projectId,warehouseId,productCode,productName,pageNumber,pageSize);
            PageInfo<InventoryCheckModel> inventoryCheckModelPageInfo = inventoryCheckService.getInventoryCheck(rpcHeader, projectId,warehouseId,productCode,productName,pageNumber, pageSize);
            logger.info("#traceId={}# [OUT] get checkinventory success", traceId);
            System.out.println(JSON.toJSONString(inventoryCheckModelPageInfo));
            gongxiaoResult= new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),inventoryCheckModelPageInfo);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


}
