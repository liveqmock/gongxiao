package com.yhglobal.gongxiao.inventory.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.inventory.service.InventoryLedgerService;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouse.model.InventoryLedger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 进销存台账
 * @author liukai
 */
@Controller
@RequestMapping("/inventory")
public class StandAccountFlowController {

    private static Logger logger = LoggerFactory.getLogger(StandAccountFlowController.class);

    @Reference
    InventoryLedgerService inventoryLedgerService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 查询台账信息
     * @param pageNumber
     * @param pageSize
     * @param projectId         项目id
     * @param productCode       商品编码
     * @param productName       商品名称
     * @param warehouseId       仓库id
     * @param startDate         起始时间
     * @param endDate            结束时间
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/standFlow",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectAccountInfo(int pageNumber, int pageSize, String projectId, String productCode, String productName, String warehouseId, Date startDate, Date endDate, HttpServletRequest request, HttpServletResponse response){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectAccountInfo] params: projectId={}, productCode={}, productName={}, warehouseId={}, startDate={}, endDate={}" , traceId, projectId,productCode,productName,warehouseId,startDate,endDate);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PageInfo<InventoryLedger> resultList = inventoryLedgerService.conditionalSelectInventoryLedger(rpcHeader,pageNumber,pageSize,projectId,productCode,productName,warehouseId,startDate,endDate);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectAccountInfo success: resultList.size()={}", traceId, resultList.getTotal());
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
