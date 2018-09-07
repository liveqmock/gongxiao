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
import com.yhglobal.gongxiao.warehouse.service.OutboundNotifyOrderService;
import com.yhglobal.gongxiao.warehousemanagement.bo.WmsOutboundBasic;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecord;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecordItem;
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
 * wms出库单管理
 * @author liukai
 *
 */
@Controller
@RequestMapping("/warehouseManage/outboundNotify")
public class OutboundNotifyController {

    private static Logger logger = LoggerFactory.getLogger(OutboundNotifyController.class);

    @Reference
    OutboundNotifyOrderService outboundNotifyOrderService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;


    /**
     * 查询所有wms的出库单
     * @param projectId         项目id
     * @param gongxiaoOutNo     出库单号
     * @param createTimeBeging  创建起始时间
     * @param createTimeLast    创建结束时间
     * @param customer          客户
     * @param pageNumber        页数
     * @param pageSize          页码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectOutboundOrder", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult<PageInfo<WmsOutboundRecord>> selectWmsOutboundOrder(String projectId, String gongxiaoOutNo, String salesNo, String orderType,String customer, String
            createTimeBeging, String createTimeLast, int pageNumber, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectWmsOutboundOrder] params: projectId={}, gongxiaoOutNo={}, wmsOutboundNo={}, orderType={}, customer={},createTimeBeging={}, createTimeLast={}", traceId, projectId, gongxiaoOutNo, salesNo,orderType,customer, createTimeBeging, createTimeLast);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PageInfo<WmsOutboundRecord> resultList  = outboundNotifyOrderService.selectOutStorageInfo(rpcHeader, pageNumber, pageSize, projectId, gongxiaoOutNo, salesNo, orderType, customer,createTimeBeging, createTimeLast);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), resultList);
            logger.info("#traceId={}# [OUT] get selectWmsOutboundOrder success: resultList.size()={}", traceId, resultList.getTotal());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据出库单号查询wms出库单基本信息
     * @param projectId
     * @param gongxiaoOutboundOrderNo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectByOutboundNum",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectOutboundBasicInfo(String projectId,String gongxiaoOutboundOrderNo,String wmsOutboundOrderNo, HttpServletRequest request,HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectOutboundBasicInfo] params: projectId={},gongxiaoOutboundOrderNo={},wmsOutboundOrderNo={}" , traceId,projectId,gongxiaoOutboundOrderNo,wmsOutboundOrderNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            WmsOutboundBasic wmsOutboundBasic = outboundNotifyOrderService.selectRecordByOrderNo(rpcHeader,gongxiaoOutboundOrderNo,wmsOutboundOrderNo);
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            gongxiaoResult.setData(wmsOutboundBasic);
            logger.info("#traceId={}# [OUT] get selectOutboundBasicInfo success: result.size()={}", traceId, 1);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据出库单号查询wms出库单明细
     * @param projectId
     * @param gongxiaoOutboundOrderNo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectOutboundOrderItem", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectWmsOutboundOrderItem(String projectId, String gongxiaoOutboundOrderNo,String wmsOutboundOrderNo,HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectWmsOutboundOrderItem] params: projectId={}, gongxiaoOutboundOrderNo={},wmsOutboundOrderNo={}", traceId, projectId, gongxiaoOutboundOrderNo,wmsOutboundOrderNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<WmsOutboundRecordItem> resultList = outboundNotifyOrderService.selectWmsItemRecordByOrderNo(rpcHeader, projectId, gongxiaoOutboundOrderNo,wmsOutboundOrderNo);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectWmsOutboundOrderItem success: resultList.size()={}", traceId, resultList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;

    }


}
