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
import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrder;
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
 * 出库单管理
 * @author liukai
 *
 */
@Controller
@RequestMapping("/warehouseManage/outbound")
public class OutboundShowServiceController {

    private static Logger logger = LoggerFactory.getLogger(OutboundShowServiceController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;


    /**
     * 查询所有的出库单
     * @param projectId         项目id
     * @param gongxiaoOutNo     出库单号
     * @param salseNo           销售单号
     * @param createTimeBeging  创建起始时间
     * @param createTimeLast    创建结束时间
     * @param warehouseId       仓库ID
     * @param productCode       商品编码
     * @param finishTimeBegin   创建结束时间起
     * @param finishTimeLast    创建结束时间终
     * @param supplier          供应商
     * @param customer          客户
     * @param pageNumber        页数
     * @param pageSize          页码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectOutboundOrder", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectOutboundOrder(String projectId, String gongxiaoOutNo, String salseNo, String
            createTimeBeging, String createTimeLast, String warehouseId, String productCode, String finishTimeBegin, String finishTimeLast, String supplier, String customer, int pageNumber, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectOutboundOrder] params: projectId={}, inventoryNum={}, salseNum={}, createTimeBeging={}, createTimeLast={}, warehouseId={}, productCode={}, finishTimeBegin={}, finishTimeLast={}, supplier={}, customer={}", traceId, projectId, gongxiaoOutNo, salseNo, createTimeBeging, createTimeLast, warehouseId, productCode, finishTimeBegin, finishTimeLast, supplier, customer);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "474565353";
            gongxiaoResult  = XpsWarehouseManager.selectOutboundOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),channelToken, projectId, gongxiaoOutNo, salseNo, createTimeBeging, createTimeLast, warehouseId, productCode, finishTimeBegin, finishTimeLast, supplier, customer,pageNumber, pageSize);
            logger.info("#traceId={}# [OUT] get selectOutboundOrder success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据出库单号查询出库单明细
     * @param projectId
     * @param gongxiaoOutboundOrderNo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectOutboundOrderItem", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectOutboundOrderItem(String projectId, String gongxiaoOutboundOrderNo, String productCode, int inventoryType, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectOutboundOrderItem] params: projectId={}, gongxiaoOutboundOrderNo={},productCode={},inventoryType={}", traceId, projectId, gongxiaoOutboundOrderNo,productCode,inventoryType);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "474565353";
            gongxiaoResult = XpsWarehouseManager.selectOutboundOrderItem(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),channelToken, projectId, gongxiaoOutboundOrderNo);
            logger.info("#traceId={}# [OUT] get selectOutboundOrderItem success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;

    }

    /**
     * 根据出库单号查询出库单基本信息
     * @param projectId
     * @param gongxiaoOutboundOrderNo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/outboundOrder/selectByOutboundNum",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectOutboundBasicInfo(String projectId, String gongxiaoOutboundOrderNo, String productCode, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectOutboundBasicInfo] params: projectId={},outboundOrderItemNo={}" , traceId,projectId,gongxiaoOutboundOrderNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "474565353";
            gongxiaoResult = XpsWarehouseManager.selectByOutboundNum(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),channelToken,projectId,gongxiaoOutboundOrderNo);
            logger.info("#traceId={}# [OUT] get selectOutboundBasicInfo success", traceId);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 销售单跳转出库单
     * @param projectId
     * @param inventoryNum
     * @param salseNo
     * @param createTimeBegin
     * @param createTimeTo
     * @param warehouseId
     * @param productCode
     * @param finishTimeBegin
     * @param finishTimeTo
     * @param supplierName
     * @param customer
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/salseToOutbound", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectSalseToOutboundOrder(String projectId, String inventoryNum, String salseNo, String
            createTimeBegin, String createTimeTo, String warehouseId, String productCode, String finishTimeBegin, String finishTimeTo, String supplierName, String customer,HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectSalseToOutboundOrder] params: projectId={}, inventoryNum={}, salseNum={}, createTimeBeging={}, createTimeLast={}, warehouseName={}, productCode={}, finishTimeBegin={}, finishTimeLast={}, supplier={}, customer={}", traceId, projectId, inventoryNum, salseNo, createTimeBegin, createTimeTo, warehouseId, productCode, finishTimeBegin, finishTimeTo, supplierName, customer);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "474565353";
            List<OutboundOrder> resultList = XpsWarehouseManager.selectRecordBySalesNo(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),channelToken, projectId, salseNo);
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectSalseToOutboundOrder success: resultList.size()={}", traceId, resultList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
