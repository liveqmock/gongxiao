package com.yhglobal.gongxiao.warehouse;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 调拨controller
 * @author liukai
 */
@Controller
@RequestMapping("/allocte")
public class AllocationController {

    private static Logger logger = LoggerFactory.getLogger(AllocationController.class);


    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象

    @Autowired
    PortalConfig portalConfig;

    /**
     * 查询调拨单
     * @param projectId  项目id
     * @param allocateNo  调拨单号
     * @param gongxiaoOutboundOrderNo  调拨出库单号
     * @param gongxiaoInboundOrderNo   调拨入库单号
     * @param warehouseOut     调拨出库仓
     * @param warehouseEnter    调拨入库仓
     * @param createBeginTime   创建起始时间
     * @param createEndTime     创建结束时间
     * @param pageNumber        页码
     * @param pageSize          页数
     * @param request
     * @return
     */
    @RequestMapping("/selectAllocateOrder")
    @ResponseBody
    public GongxiaoResult selectAllocateOrder(String projectId, String allocateNo, String gongxiaoOutboundOrderNo, String gongxiaoInboundOrderNo,
                                              String warehouseOut, String warehouseEnter, String createBeginTime, String createEndTime,int pageNumber, int pageSize, HttpServletRequest request){
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectAllocateOrder] params: projectId={},allocateNo={},gongxiaoOutboundOrderNo={},gongxiaoInboundOrderNo={},warehouseOut={},warehouseEnter={},createBeginTime={},createEndTime={}" , traceId,projectId,allocateNo,gongxiaoOutboundOrderNo,gongxiaoInboundOrderNo,warehouseOut,warehouseEnter,createBeginTime,createEndTime);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "123546568";
            gongxiaoResult = XpsWarehouseManager.selectAllocateOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken, pageNumber,pageSize,projectId,allocateNo,gongxiaoOutboundOrderNo,gongxiaoInboundOrderNo,warehouseOut,warehouseEnter,createBeginTime,createEndTime);
            logger.info("#traceId={}# [OUT] get selectAllocateOrder success", traceId);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 根据项目ID和调拨单号查询调拨单
     * @param projectId
     * @param allocateNo
     * @param request
     * @return
     */
    @RequestMapping("/selectByAllocateNo")
    @ResponseBody
    public GongxiaoResult selectByAllocateNo(String projectId, String allocateNo, HttpServletRequest request){
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectByAllocateNo] params: projectId={},allocateNo={}" , traceId,projectId,allocateNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "34566783653";
            gongxiaoResult = XpsWarehouseManager.selectInfoByAllocateNo(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken,projectId,allocateNo);
            logger.info("#traceId={}# [OUT] get selectByAllocateNo success", traceId);
            return gongxiaoResult;
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
            return gongxiaoResult;
        }

    }

    /**
     * 根据项目id和调拨单号查询调拨单明细
     * @param projectId
     * @param allocateNo
     * @param request
     * @return
     */
    @RequestMapping("/selectItemByAllocateNo")
    @ResponseBody
    public GongxiaoResult selectItemByAllocateNo(String projectId, String allocateNo, HttpServletRequest request){
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectItemByAllocateNo] params: projectId={},allocateNo={}" , traceId,projectId,allocateNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "34566783653";
            gongxiaoResult = XpsWarehouseManager.selectItemByAllocateNo(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken,projectId,allocateNo);
            logger.info("#traceId={}# [OUT] get selectItemByAllocateNo success", traceId);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 新增调拨单
     * @param companyout
     * @param companyentry
     * @param projectoutId
     * @param projectEntryId
     * @param warehouseOutId
     * @param warehouseEntryId
     * @param deliveraddress
     * @param receiveaddress
     * @param allocateway
     * @param yhbusiness_date
     * @param ask_date
     * @param dead_date
     * @param remask
     * @param itemsInfo
     * @param request
     * @return
     */
    @RequestMapping("/insertAllocateOrder")
    @ResponseBody
    public GongxiaoResult insertAllocateOrder(String companyout, String companyentry, String projectoutId, String projectEntryId, String warehouseOutId,
                                              String warehouseEntryId, String deliveraddress, String receiveaddress, String allocateway, String yhbusiness_date,
                                              String ask_date, String dead_date, String remask, String itemsInfo, HttpServletRequest request){
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][insertAllocateOrder] params: companyout={},companyentry={},projectoutId={},projectEntryId={},warehouseOutId={},warehouseEntryId={},deliveraddress={},receiveaddress={},allocateway={},yhbusiness_date={},ask_date={},dead_date={},remask={},itemsInfo={}" ,
                    traceId,companyout,companyentry,projectoutId,projectEntryId,warehouseOutId,warehouseEntryId,
                    deliveraddress,receiveaddress,allocateway,yhbusiness_date,ask_date,dead_date,remask,itemsInfo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

            String channelToken = "23456467";
            XpsWarehouseManager.insertAllocateOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken, companyout, companyentry, projectoutId, projectEntryId, warehouseOutId,
                    warehouseEntryId, deliveraddress, receiveaddress, allocateway, yhbusiness_date,
                    ask_date, dead_date, remask, itemsInfo);

            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setMessage(ErrorCode.SUCCESS.getMessage());
            logger.info("#traceId={}# [OUT] get insertAllocateOrder success", traceId);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

//    /**
//     * 修改调拨单
//     * @param projectId
//     * @param allocateNo
//     * @param request
//     * @return
//     */
//    @RequestMapping("/updateByAllocateNo")
//    @ResponseBody
//    public GongxiaoResult updateByAllocateNo(String projectId, String allocateNo, HttpServletRequest request){
//        String traceId = null;
//        GongxiaoResult gongxiaoResult = new GongxiaoResult();
//        try {
//            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
//            logger.info("#traceId={}# [IN][selectByAllocateNo] params: projectId={},allocateNo={}" , traceId,projectId,allocateNo);
//            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            allocationService.updateByAllocateNo(rpcHeader,projectId,allocateNo);
//            gongxiaoResult.setReturnCode(0);
//            gongxiaoResult.setMessage(ErrorCode.SUCCESS.getMessage());
//            logger.info("#traceId={}# [OUT] get selectByAllocateNo success", traceId);
//        }catch (Exception e) {
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
//        }
//        return gongxiaoResult;
//    }
}
