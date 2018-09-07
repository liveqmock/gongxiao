package com.yhglobal.gongxiao.warehouse.controller.warehouse;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.model.WmsOutboundRecord;
import com.yhglobal.gongxiao.warehouse.model.WmsOutboundRecordItem;
import com.yhglobal.gongxiao.warehouse.model.bo.WmsOutboundBasic;
import com.yhglobal.gongxiao.warehouse.service.OutboundNotifyOrderService;
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
 * wms出库通知单管理
 * @author liukai
 *
 */
@Controller
@RequestMapping("/warehouseManage")
public class OutboundNotifyController {

    private static Logger logger = LoggerFactory.getLogger(OutboundNotifyController.class);

    @Autowired
    OutboundNotifyOrderService outboundNotifyOrderService;


    /**
     * 查询所有wms的出库单
     * @param projectId         项目id
     * @param gongxiaoOutNo     出库单号
     * @param salseNo           销售单号
     * @param createTimeBeging  创建起始时间
     * @param createTimeLast    创建结束时间
     * @param warehouseId       仓库ID
     * @param productCode       商品编码
     * @param customer          客户
     * @param pageNumber        页数
     * @param pageSize          页码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectWmsOutboundOrder", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectWmsOutboundOrder(String projectId, String gongxiaoOutNo, String salseNo, String
            createTimeBeging, String createTimeLast, String warehouseId, String productCode, String customer, int pageNumber, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectWmsOutboundOrder] params: projectId={}, inventoryNum={}, salseNum={}, createTimeBeging={}, createTimeLast={}, warehouseId={}, productCode={}, customer={}", traceId, projectId, gongxiaoOutNo, salseNo, createTimeBeging, createTimeLast, warehouseId, productCode, customer);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            PageInfo<WmsOutboundRecord> resultList  = outboundNotifyOrderService.selectOutStorageInfo(rpcHeader, pageNumber, pageSize, projectId, gongxiaoOutNo, salseNo, createTimeBeging, createTimeLast, warehouseId, productCode, customer);
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            gongxiaoResult.setData(resultList);
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
    @RequestMapping(value = "/selectWmsByOutboundNum",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectOutboundBasicInfo(String projectId, String gongxiaoOutboundOrderNo, String wmsOutboundOrderNo, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectOutboundBasicInfo] params: projectId={},gongxiaoOutboundOrderNo={},wmsOutboundOrderNo={}" , traceId,projectId,gongxiaoOutboundOrderNo,wmsOutboundOrderNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            WmsOutboundBasic wmsOutboundBasic = outboundNotifyOrderService.selectRecordByOrderNo(rpcHeader,gongxiaoOutboundOrderNo,wmsOutboundOrderNo);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(wmsOutboundBasic);
            logger.info("#traceId={}# [OUT] get selectOutboundBasicInfo success", traceId);
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
    @RequestMapping(value = "/selectWmsOutboundOrderItem", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectWmsOutboundOrderItem(String projectId, String gongxiaoOutboundOrderNo, String wmsOutboundOrderNo, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectWmsOutboundOrderItem] params: projectId={}, gongxiaoOutboundOrderNo={},wmsOutboundOrderNo={}", traceId, projectId, gongxiaoOutboundOrderNo,wmsOutboundOrderNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
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
