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
import com.yhglobal.gongxiao.warehouseapi.inbound.InboundOrderBasicInfo;
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
 * 入库单管理controller
 * @author liukai
 */
@Controller
@RequestMapping("/warehouseManage/inbound")
public class InboundShowServiceController {

    private static Logger logger = LoggerFactory.getLogger(InboundShowServiceController.class);

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
    public GongxiaoResult selectInboundOrder(String projectId, String gonxiaoInboundNo, String purchaseNo, String productCode, String goodCode, String dateTime, String warehouseId, String supplier, HttpServletRequest request, int pageNumber, int pageSize, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][handleSelectInboundOrder] params: projectId={},gonxiaoInboundNo={},purchaseNo={},productCode={},goodCode={},dateTime={},warehouseId={},supplier={}" , traceId,projectId,gonxiaoInboundNo,purchaseNo,productCode,goodCode,dateTime,warehouseId,supplier);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "474565353";
            gongxiaoResult = XpsWarehouseManager.selectInboundOrderselect(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_JMGO.getChannelId(),channelToken,projectId,gonxiaoInboundNo,purchaseNo,productCode,goodCode,dateTime,warehouseId,supplier,pageNumber,pageSize);
            logger.info("#traceId={}# [OUT] get selectInboundOrder success", traceId);
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
    public GongxiaoResult selectInboundBasicInfo(String projectId, String inventoryNum, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectInboundBasicInfo] params: projectId={},inventoryNum={}" , traceId,projectId,inventoryNum);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "474565353";
            gongxiaoResult = XpsWarehouseManager.selectInboundByInboundNum(portalConfig.getWarehouseUrl(),WmsSourceChannel.CHANNEL_JMGO.getChannelId(),channelToken,projectId,inventoryNum);
            logger.info("#traceId={}# [OUT] get selectInboundBasicInfo success", traceId);
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
    public GongxiaoResult selectInboundOrItem(String projectId, String inventoryNum, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectInboundOrItem] params: projectId={},inventoryNum={}" , traceId,projectId,inventoryNum);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "474565353";
            gongxiaoResult = XpsWarehouseManager.selectInboundOrderItem(portalConfig.getWarehouseUrl(),WmsSourceChannel.CHANNEL_JMGO.getChannelId(),channelToken,projectId,inventoryNum);

            logger.info("#traceId={}# [OUT] get selectInboundOrItem success", traceId);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
