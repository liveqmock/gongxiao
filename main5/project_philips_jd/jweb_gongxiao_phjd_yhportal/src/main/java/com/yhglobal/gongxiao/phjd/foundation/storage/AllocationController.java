package com.yhglobal.gongxiao.phjd.foundation.storage;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import com.yhglobal.gongxiao.warehouseapi.model.InventoryBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 调拨单
 */
@Controller
@RequestMapping("/storage/allocation")
public class AllocationController {
    private static Logger logger = LoggerFactory.getLogger(AllocationController.class);
    @Autowired
    PortalConfig portalConfig; //property注入类
    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    /**
     * 查询调拨单
     *
     * @param allocateNo              调拨单号
     * @param gongxiaoOutboundOrderNo 调拨出库单号
     * @param gongxiaoInboundOrderNo  调拨入库单号
     * @param warehouseOut            调拨出库仓
     * @param warehouseEnter          调拨入库仓
     * @param createBeginTime         创建起始时间
     * @param createEndTime           创建结束时间
     * @param pageNumber              页码
     * @param pageSize                页数
     * @param request
     * @return
     */
    @RequestMapping("/selectAllocateOrder")
    @ResponseBody
    public GongxiaoResult selectAllocateOrder(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(defaultValue = "") String allocateNo,
                                              @RequestParam(defaultValue = "") String gongxiaoOutboundOrderNo,
                                              @RequestParam(defaultValue = "") String gongxiaoInboundOrderNo,
                                              @RequestParam(defaultValue = "") String warehouseOut,
                                              @RequestParam(defaultValue = "") String warehouseEnter,
                                              @RequestParam(defaultValue = "") String createBeginTime,
                                              @RequestParam(defaultValue = "") String createEndTime,
                                              @RequestParam(defaultValue = "1") int pageNumber,
                                              @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectAllocateOrder] params: projectId={},allocateNo={},gongxiaoOutboundOrderNo={},gongxiaoInboundOrderNo={},warehouseOut={}," +
                            "warehouseEnter={},status={},createBeginTime={},createEndTime={}",
                    traceId, String.valueOf(portalUserInfo.getProjectId()), allocateNo, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, warehouseEnter, createBeginTime, createEndTime);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "123546568";
            gongxiaoResult = XpsWarehouseManager.selectAllocateOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken, pageNumber, pageSize, String.valueOf(portalUserInfo.getProjectId()),
                    allocateNo, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, warehouseEnter, createBeginTime, createEndTime);
            logger.info("#traceId={}# [OUT] get selectAllocateOrder success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 新增调拨单
     *
     * @param companyout       调出公司主体
     * @param companyentry     调入公司主体
     * @param warehouseOutId   调出仓库
     * @param warehouseEntryId 调入仓库
     * @param deliveraddress   发货地址
     * @param receiveaddress   收货地址
     * @param allocateway      调拨方式
     * @param yhbusinessDate   业务日期
     * @param askDate          要求到货日期
     * @param deadDate         到货截止日期
     * @param remask           备注
     * @param itemsInfo        货品详情
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertAllocateOrder")
    public GongxiaoResult insertAllocateOrder(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(defaultValue = "") String companyout,
                                              @RequestParam(defaultValue = "") String companyentry,
                                              @RequestParam(defaultValue = "") String warehouseOutId,
                                              @RequestParam(defaultValue = "") String warehouseEntryId,
                                              @RequestParam(defaultValue = "") String deliveraddress,
                                              @RequestParam(defaultValue = "") String receiveaddress,
                                              @RequestParam(defaultValue = "") String allocateway,
                                              @RequestParam(defaultValue = "") String yhbusinessDate,
                                              @RequestParam(defaultValue = "") String askDate,
                                              @RequestParam(defaultValue = "") String deadDate,
                                              @RequestParam(defaultValue = "") String remask,
                                              @RequestParam(defaultValue = "") String itemsInfo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][insertAllocateOrder] params: companyout={},companyentry={},projectoutId={},projectEntryId={},warehouseOutId={}," +
                            "warehouseEntryId={}, deliveraddress={},receiveaddress={},allocateway={},yhbusiness_date={},ask_date={},dead_date={},remask={},itemsInfo={}",
                    traceId, companyout, companyentry, String.valueOf(portalUserInfo.getProjectId()), String.valueOf(portalUserInfo.getProjectId()), warehouseOutId, warehouseEntryId,
                    deliveraddress, receiveaddress, allocateway, yhbusinessDate, askDate, deadDate, remask, itemsInfo);
            String channelToken = "23456467";
            gongxiaoResult = XpsWarehouseManager.insertAllocateOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, companyout, companyentry, String.valueOf(portalUserInfo.getProjectId()), String.valueOf(portalUserInfo.getProjectId()), warehouseOutId, warehouseEntryId,
                    deliveraddress, receiveaddress, allocateway, yhbusinessDate, askDate, deadDate, remask, itemsInfo);
            logger.info("#traceId={}# [OUT] insertAllocateOrder success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据项目ID和调拨单号查询调拨单
     *
     * @param allocateNo 调拨单号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectByAllocateNo")
    public GongxiaoResult selectByAllocateNo(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "") String allocateNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectByAllocateNo] params: projectId={},allocateNo={}", traceId, String.valueOf(portalUserInfo.getProjectId()), allocateNo);
            String channelToken = "34566783653";
            gongxiaoResult = XpsWarehouseManager.selectInfoByAllocateNo(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, String.valueOf(portalUserInfo.getProjectId()), allocateNo);
            logger.info("#traceId={}# [OUT] selectByAllocateNo success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据项目id和调拨单号查询调拨单明细
     *
     * @param allocateNo 调拨单号
     * @param request
     * @return
     */
    @RequestMapping("/selectItemByAllocateNo")
    @ResponseBody
    public GongxiaoResult selectItemByAllocateNo(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestParam(defaultValue = "") String allocateNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectItemByAllocateNo] params: projectId={},allocateNo={}", traceId, String.valueOf(portalUserInfo.getProjectId()), allocateNo);
            String channelToken = "34566783653";
            gongxiaoResult = XpsWarehouseManager.selectItemByAllocateNo(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, String.valueOf(portalUserInfo.getProjectId()), allocateNo);
            logger.info("#traceId={}# [OUT] selectItemByAllocateNo success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 获取批次号信息
     *
     * @param request
     * @param response
     * @param productCode 产品ID
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/getBatchNumber")
    @ResponseBody
    public GongxiaoResult getBatchNumber(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(defaultValue = "") String productCode,
                                         @RequestParam(defaultValue = "1") int pageNumber,
                                         @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getBatchNumber][IN] params:  salesOrderNo={}", traceId);
            String warehouseChannelId = portalConfig.getXpsChannelId();
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(warehouseChannelId + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            PageInfo<InventoryBatch> batchDetail = XpsWarehouseManager.getBatchDetail(portalConfig.getWarehouseUrl(),
                    portalConfig.getXpsChannelId(),
                    xpsChannelSecret,
                    String.valueOf(portalUserInfo.getProjectId()), productCode, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), batchDetail);
            logger.info("#traceId={}# [getBatchNumber][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


//    /**
//     * 确认调拨
//     *
//     * @param gongxiaoOutboundOrderNo 调拨单号
//     * @param request
//     * @return
//     */
//    @RequestMapping("/confirmationOfAllocation")
//    @ResponseBody
//    public GongxiaoResult confirmationOfAllocation(HttpServletRequest request, HttpServletResponse response,
//                                                 @RequestParam(defaultValue = "") String gongxiaoOutboundOrderNo) {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        String traceId = null;
//        GongxiaoResult gongxiaoResult = new GongxiaoResult();
//        try {
//            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//            logger.info("#traceId={}# [IN][selectItemByAllocateNo] params: projectId={},allocateNo={}", traceId, String.valueOf(portalUserInfo.getProjectId()), allocateNo);
//            String channelToken = "34566783653";
//            gongxiaoResult = XpsWarehouseManager.selectItemByAllocateNo(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
//                    channelToken, String.valueOf(portalUserInfo.getProjectId()), gongxiaoOutboundOrderNo);
//            logger.info("#traceId={}# [OUT] selectItemByAllocateNo success", traceId);
//        } catch (Exception e) {
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
//            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
//        }
//        return gongxiaoResult;
//    }


}
