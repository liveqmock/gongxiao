package com.yhglobal.gongxiao.sales.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.purchase.controller.FoundationInfoController;
import com.yhglobal.gongxiao.sales.bo.SalesReturnClassificationCount;
import com.yhglobal.gongxiao.sales.model.cancel.dto.OutBound;
import com.yhglobal.gongxiao.sales.model.cancel.dto.OutBoundItem;
import com.yhglobal.gongxiao.sales.model.cancel.dto.SalesReturn;
import com.yhglobal.gongxiao.sales.model.cancel.model.SalesReturnOrder;
import com.yhglobal.gongxiao.sales.service.SalesReturnService;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouse.service.OutboundShowService;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 * @Description: 销售退货contorller
 * @Author: LUOYI
 * @Date: Created in 11:00 2018/3/21
 */
@Controller
@RequestMapping("/sales/return")
public class SalesReturnOrderContorller {

    @Reference
    private SalesReturnService salesReturnService;
    @Reference
    private OutboundShowService outboundShowService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    private static Logger logger = LoggerFactory.getLogger(FoundationInfoController.class);

    @RequestMapping("/getsByProjectId")
    @ResponseBody
    public GongxiaoResult<PageInfo<SalesReturnOrder>> getsByProjectId(HttpServletRequest request, HttpServletResponse response, Integer projectId,
                                                                      Integer returnedType,
                                                                      String salesReturnedOrderNo,
                                                                      String warehouseId,
                                                                      String timeStart,
                                                                      String timeEnd,
                                                                      Integer returnedOrderStatus,
                                                                      @RequestParam(required=true,defaultValue="1") Integer page,
                                                                      @RequestParam(required=false,defaultValue="10") Integer pageSize){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        PageInfo<SalesReturnOrder> pageInfo = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsByProjectId] params: projectId={}, returnedType={}, salesReturnedOrderNo={}, warehouseId={}, timeStart={}, timeEnd={}, returnedOrderStatus={} "
                    , traceId,projectId,returnedType,salesReturnedOrderNo,warehouseId,timeStart,timeEnd,returnedOrderStatus);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            pageInfo = salesReturnService.getsSalesReturnOrderByProject(rpcHeader,projectId,returnedType,salesReturnedOrderNo,warehouseId,timeStart,timeEnd,returnedOrderStatus,page,pageSize);
            logger.info("#traceId={}# [OUT] get getsByProjectId success:pageInfo={}", traceId,pageInfo);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
    }
    @RequestMapping("/getClassificationCount")
    @ResponseBody
    public GongxiaoResult<List<SalesReturnClassificationCount>> selectClassificationCount(HttpServletRequest request, HttpServletResponse response, Integer projectId,
                                                                                          Integer returnedType,
                                                                                          String salesReturnedOrderNo,
                                                                                          String warehouseId,
                                                                                          String timeStart,
                                                                                          String timeEnd){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        List<SalesReturnClassificationCount> list = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectClassificationCount] params: projectId={}, returnedType={}, salesReturnedOrderNo={}, warehouseId={}, timeStart={}, timeEnd={} "
                    , traceId,projectId,returnedType,salesReturnedOrderNo,warehouseId,timeStart,timeEnd);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            list = salesReturnService.selectClassificationCount(rpcHeader,projectId,returnedType,salesReturnedOrderNo,warehouseId,timeStart,timeEnd);
            logger.info("#traceId={}# [OUT] get selectClassificationCount success: resultList.size()={}", traceId, list.size());
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), list);
    }
    @RequestMapping("/getRecordList")
    @ResponseBody
    public GongxiaoResult<List<OutBound>> getRecordList(HttpServletRequest request, HttpServletResponse response, String projectId, String salesNo){
        String traceId = null;
        List<OutBound> list = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getRecordList] params: projectId={}, salesNo={}, ", traceId,projectId,salesNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            list = salesReturnService.getOutBound(rpcHeader,projectId,salesNo);
            logger.info("#traceId={}# [OUT] get getRecordList success: resultList.size()={}", traceId, list.size());
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), list);
    }
    @RequestMapping("/getRecordItemList")
    @ResponseBody
    public GongxiaoResult<List<OutBoundItem>> getRecordItemList(HttpServletRequest request,HttpServletResponse response, String projectId,String salesOrderNo, String gongxiaoOutboundOrderNo){
        String traceId = null;
        List<OutBoundItem> list = null;
        try{
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getRecordItemList] params: salesOrderNo={}, gongxiaoOutboundOrderNo={}, ", traceId,salesOrderNo,gongxiaoOutboundOrderNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            list = salesReturnService.getOutBoundItem(rpcHeader,projectId,salesOrderNo,gongxiaoOutboundOrderNo);
            logger.info("#traceId={}# [OUT] get getRecordItemList success: resultList.size()={}", traceId, list.size());
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), list);
    }
    @RequestMapping("/addSalesReturn")
    @ResponseBody
    public GongxiaoResult<Integer> addSalesReturn(HttpServletRequest request, HttpServletResponse response, SalesReturnOrder salesReturnOrder){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        RpcResult<Integer> result = null;
        try{
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][addSalesReturn] params: salesReturnOrder={} ", traceId,salesReturnOrder);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            if(salesReturnOrder.getTargetWarehouseId() == null || salesReturnOrder.getTargetWarehouseId().length()==0){
                return GongxiaoResult.newResult(ErrorCode.RETURN_WAREHOUSE_NULL.getErrorCode(),ErrorCode.RETURN_WAREHOUSE_NULL.getMessage());
            }
            result = salesReturnService.addSalesReturnOrder(rpcHeader,salesReturnOrder);

            if(result.getSuccess()){
                logger.info("#traceId={}# [OUT] get addSalesReturn success: result={}", traceId, result);
                return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), result);
            }else{
                return GongxiaoResult.newResult(result.getCode(),result.getMessage());
            }
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }
    @RequestMapping("/check")
    @ResponseBody
    public GongxiaoResult<Integer> checkSalesReturn(HttpServletRequest request,HttpServletResponse response,@RequestBody SalesReturnOrder[] salesReturnOrder) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        Integer result = 0;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][checkSalesReturn] params: salesReturnOrder={} ", traceId,salesReturnOrder);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            result = salesReturnService.checkSalesReturnOrder(rpcHeader,salesReturnOrder);
            logger.info("#traceId={}# [OUT] get checkSalesReturn success: result={}", traceId, result);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), result);
    }
    @RequestMapping("/get")
    @ResponseBody
    public GongxiaoResult<SalesReturn> get(HttpServletRequest request, HttpServletResponse response, Long salesReturnOrderId){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        SalesReturn result = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][get] params: salesReturnOrderId={} ", traceId,salesReturnOrderId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            result = salesReturnService.getSalesReturn(rpcHeader,salesReturnOrderId);
            logger.info("#traceId={}# [OUT] get getRecordList success: result={}", traceId, result);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), result);
    }


}
