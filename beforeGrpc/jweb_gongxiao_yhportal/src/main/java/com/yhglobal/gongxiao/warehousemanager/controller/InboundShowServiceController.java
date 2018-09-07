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
import com.yhglobal.gongxiao.warehouse.service.InboundShowService;
import com.yhglobal.gongxiao.warehousemanagement.bo.InboundOrderBasicInfo;
import com.yhglobal.gongxiao.warehousemanagement.bo.InboundOrderItemShowModel;
import com.yhglobal.gongxiao.warehousemanagement.bo.InboundOrderShowModel;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 入库单管理controller
 * @author liukai
 */
@Controller
@RequestMapping("/warehouseManage/inbound")
public class InboundShowServiceController {

    private static Logger logger = LoggerFactory.getLogger(InboundShowServiceController.class);

    @Reference
    InboundShowService inboundShowService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     *  查询所有的入库单
     * @param projectId     项目id
     * @param gonxiaoInboundNo      入库单号
     * @param purchaseNo            采购单号
     * @param productCode           商品编码
     * @param goodCode              产品编码
     * @param dateTime              创建时间
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
    public GongxiaoResult selectInboundOrder(String projectId, String gonxiaoInboundNo,String purchaseNo, String productCode, String goodCode, String dateTime, String warehouseId, String supplier,HttpServletRequest request,int pageNumber, int pageSize, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][handleSelectInboundOrder] params: projectId={},gonxiaoInboundNo={},purchaseNo={},productCode={},goodCode={},dateTime={},warehouseId={},supplier={}" , traceId,projectId,gonxiaoInboundNo,purchaseNo,productCode,goodCode,dateTime,warehouseId,supplier);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PageInfo<InboundOrderShowModel> resultList = inboundShowService.selectInStorageInfo(rpcHeader,pageNumber,pageSize,projectId,gonxiaoInboundNo,purchaseNo,productCode,goodCode,dateTime,warehouseId,supplier);
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
     * 根据入库单号查询入库单
     * @param projectId
     * @param inventoryNum
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/inboundOrder/selectByInboundNum",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectInboundBasicInfo(String projectId,String inventoryNum,HttpServletRequest request,HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectInboundBasicInfo] params: projectId={},inventoryNum={}" , traceId,projectId,inventoryNum);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            InboundOrderBasicInfo inboundOrderBasicInfo = inboundShowService.selectRecordById(rpcHeader,projectId,inventoryNum);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(inboundOrderBasicInfo);
            logger.info("#traceId={}# [OUT] get selectInboundBasicInfo success: result.size()={}", traceId, 1);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据入库单查询入库单明细
     * @param projectId     项目id
     * @param inventoryNum  入库单号
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/inboundOrderItem/select",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectInboundOrItem(String projectId,String inventoryNum,HttpServletRequest request,HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectInboundOrItem] params: projectId={},inventoryNum={}" , traceId,projectId,inventoryNum);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<InboundOrderItemShowModel> resultList = inboundShowService.selectInStorageDetailInfoById(rpcHeader,projectId,inventoryNum);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectInboundOrItem success: resultList.size()={}", traceId, resultList.size());
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

//    /**
//     *  采购单跳转入库单
//     * @param projectId
//     * @param purchaseNo
//     * @param inventoryNum
//     * @param productCode
//     * @param dateTime
//     * @param warehouseId
//     * @param supplierName
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(value = "/purchseToInstock",method = RequestMethod.GET)
//    @ResponseBody
//    public GongxiaoResult selectInboundByPurchase(String projectId, String purchaseNo, String inventoryNum, String productCode, String dateTime, String warehouseId, String supplierName, HttpServletRequest request,HttpServletResponse response) {
//        GongxiaoResult gongxiaoResult = new GongxiaoResult();
//        String traceId = null;
//        try {
//            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//            logger.info("#traceId={}# [IN][selectInboundByPurchase] params: projectId={},purchaseNo={},inventoryNum={},productCode={},warehouseId={},dateTime={},supplierName={}" , traceId,projectId,purchaseNo,inventoryNum,productCode,warehouseId,dateTime,supplierName);
//            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            List<InboundOrder> resultList = inboundShowService.selectRecordByPurchaseNo(rpcHeader,projectId,purchaseNo,inventoryNum,productCode,dateTime,warehouseId,supplierName);
//            gongxiaoResult.setReturnCode(0);
//            gongxiaoResult.setData(resultList);
//            logger.info("#traceId={}# [OUT] get selectInboundByPurchase success: resultList.size()={}", traceId, resultList.size());
//        }catch (Exception e) {
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
//        }
//        return gongxiaoResult;
//    }

//    /**
//     * 入库单的签收
//     * @param projectId
//     * @param orderNo
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/sureInbound")
//    @ResponseBody
//    public GongxiaoResult sureInbound(String projectId, String orderNo, HttpServletRequest request) {
//
//        GongxiaoResult gongxiaoResult = new GongxiaoResult();
//        String traceId = null;
//        try {
//            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//            logger.info("#traceId={}# [IN][sureInbound] params: projectId={},purchaseNo={},inventoryNum={},productCode={},warehouseId={},dateTime={},supplierName={}" , traceId,projectId,orderNo);
//            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            gongxiaoResult = inboundShowService.sureInbound(rpcHeader,projectId,orderNo);
////            gongxiaoResult.setReturnCode(0);
//            logger.info("#traceId={}# [OUT] get sureInbound success: resultList.size()={}", traceId);
//        }catch (Exception e) {
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
//        }
//        return gongxiaoResult;
//    }


}
