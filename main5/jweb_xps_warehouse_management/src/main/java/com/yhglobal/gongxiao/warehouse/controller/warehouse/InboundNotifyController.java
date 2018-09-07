package com.yhglobal.gongxiao.warehouse.controller.warehouse;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.model.WmsIntboundRecord;
import com.yhglobal.gongxiao.warehouse.model.WmsIntboundRecordItem;
import com.yhglobal.gongxiao.warehouse.service.InboundNotifyOrderService;
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
 * WMS入库通知单管理controller
 *
 * @author liukai
 */
@Controller
@RequestMapping("/warehouseManage")
public class InboundNotifyController {

    private static Logger logger = LoggerFactory.getLogger(InboundNotifyController.class);

    @Autowired
    InboundNotifyOrderService inboundNotifyOrderService;

    /**
     * 查询所有的入库通知单
     *
     * @param projectId        项目id
     * @param gonxiaoInboundNo 入库单号
     * @param purchaseNo       采购单号
     * @param productCode      商品编码
     * @param createTimeBegin  创建起始时间
     * @param createTimeTo     创建结束时间
     * @param warehouseId      仓库id
     * @param supplier         供应商
     * @param request
     * @param pageNumber       页码
     * @param pageSize         页数
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectWmsInboundNotifyOrder", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectInboundOrder(String projectId, String gonxiaoInboundNo, String purchaseNo, String productCode, String createTimeBegin, String createTimeTo, String warehouseId, String supplier, int pageNumber, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][handleSelectInboundOrder] params: projectId={},gonxiaoInboundNo={},purchaseNo={},productCode={},createTimeBegin={},String createTimeTo={},warehouseId={},supplier={}", traceId, projectId, gonxiaoInboundNo, purchaseNo, productCode, createTimeBegin, createTimeTo, warehouseId, supplier);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            PageInfo<WmsIntboundRecord> resultList = inboundNotifyOrderService.selectInStorageInfo(rpcHeader, pageNumber, pageSize, projectId, gonxiaoInboundNo, purchaseNo, productCode, createTimeBegin, createTimeTo, warehouseId, supplier);
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectInboundOrder success: resultList.size()={}", traceId, resultList.getTotal());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据wms入库单查询wms入库单基础信息
     *
     * @param projectId              项目id
     * @param gongxiaoInboundOrderNo 入库单号
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectWmsInboundNotifyByOrderNo", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectInboundBasicInfo(String projectId, String gongxiaoInboundOrderNo, String wmsInboundOrderNo, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectInboundBasicInfo] params: projectId={},gongxiaoInboundOrderNo={},wmsInboundOrderNo={}", traceId, projectId, gongxiaoInboundOrderNo, wmsInboundOrderNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            WmsIntboundRecord wmsIntboundRecord = inboundNotifyOrderService.selectRecordByOrderNo(rpcHeader, projectId, gongxiaoInboundOrderNo, wmsInboundOrderNo);
            logger.info("#{}# get selectInboundBasicInfo success", JSON.toJSONString(wmsIntboundRecord));
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            gongxiaoResult.setData(wmsIntboundRecord);
            logger.info("#traceId={}# [OUT] get selectInboundBasicInfo success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据wms入库单查询入库单明细
     *
     * @param projectId              项目id
     * @param gongxiaoInboundOrderNo 入库单号
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectWmsInboundNotifyOrderItem", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectWmsInboundOrItem(String projectId, String gongxiaoInboundOrderNo, String wmsInboundOrderNo, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectWmsInboundOrItem] params: projectId={},gongxiaoInboundOrderNo={},wmsInboundOrderNo={}", traceId, projectId, gongxiaoInboundOrderNo,wmsInboundOrderNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            List<WmsIntboundRecordItem> resultList = inboundNotifyOrderService.selectWmsItemRecordByOrderNo(rpcHeader, projectId, gongxiaoInboundOrderNo,wmsInboundOrderNo);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectWmsInboundOrItem success: resultList.size()={}", traceId, resultList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


}
