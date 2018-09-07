package com.yhglobal.gongxiao.warehousemanager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouse.service.InboundNotifyOrderService;
import com.yhglobal.gongxiao.warehousemanagement.bo.WmsInboundBasic;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecord;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecordItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 入库单管理controller
 * @author liukai
 */
@Controller
@RequestMapping("/warehouseManage/inboundNotify")
public class InboundNotifyController {

    private static Logger logger = LoggerFactory.getLogger(InboundNotifyController.class);

    @Reference
    InboundNotifyOrderService inboundNotifyOrderService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     *  查询所有的入库单
     * @param projectId     项目id
     * @param gonxiaoInboundNo      入库单号
     * @param batchNo            批次号
     * @param orderType           订单类型
     * @param createTimeBegin              创建起始时间
     * @param createTimeTo              创建结束时间
     * @param warehouseId           仓库id
     * @param supplier              供应商
     * @param request
     * @param pageNumber            页码
     * @param pageSize              页数
     * @param response
     * @return
     */
    @RequestMapping(value = "/inboundOrder/select",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectInboundOrder(String projectId, String gonxiaoInboundNo,String batchNo, String orderType,String warehouseId, String supplier, String wmsInboundOrderNo, String createTimeBegin, String createTimeTo, int pageNumber, int pageSize,HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][handleSelectInboundOrder] params: projectId={},gonxiaoInboundNo={},batchNo={},orderType={},warehouseId={},supplier={},wmsInboundOrderNo={},createTimeBegin={},String createTimeTo={}" , traceId,projectId,gonxiaoInboundNo,batchNo,orderType,warehouseId,supplier,wmsInboundOrderNo,createTimeBegin, createTimeTo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PageInfo<WmsIntboundRecord> resultList = inboundNotifyOrderService.selectInStorageInfo(rpcHeader,pageNumber,pageSize,projectId,gonxiaoInboundNo,batchNo,orderType,warehouseId,supplier,wmsInboundOrderNo,createTimeBegin,createTimeTo);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectInboundOrder success: resultList.size()={}", traceId, resultList.getTotal());
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据入库单查询入库单基础信息
     * @param projectId     项目id
     * @param gongxiaoInboundOrderNo  入库单号
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/inboundOrder/selectByOrderNo",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectInboundBasicInfo(String projectId,String gongxiaoInboundOrderNo,String wmsInboundOrderNo, HttpServletRequest request,HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectInboundBasicInfo] params: projectId={},gongxiaoInboundOrderNo={},wmsInboundOrderNo={}" , traceId,projectId,gongxiaoInboundOrderNo,wmsInboundOrderNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            WmsInboundBasic wmsInboundBasic = inboundNotifyOrderService.selectRecordByOrderNo(rpcHeader,projectId,gongxiaoInboundOrderNo,wmsInboundOrderNo);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(wmsInboundBasic);
            logger.info("#traceId={}# [OUT] get selectInboundBasicInfo success: result.size()={}", traceId, 1);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据wms入库单查询入库单明细
     * @param projectId     项目id
     * @param gongxiaoInboundOrderNo  入库单号
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/inboundOrderItem/select",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectWmsInboundOrItem(String projectId,String gongxiaoInboundOrderNo,String wmsInboundOrderNo,HttpServletRequest request,HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectWmsInboundOrItem] params: projectId={},gongxiaoInboundOrderNo={}" , traceId,projectId,gongxiaoInboundOrderNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<WmsIntboundRecordItem> resultList = inboundNotifyOrderService.selectWmsItemRecordByOrderNo(rpcHeader,projectId,gongxiaoInboundOrderNo,wmsInboundOrderNo);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectWmsInboundOrItem success: resultList.size()={}", traceId, resultList.size());
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


}
