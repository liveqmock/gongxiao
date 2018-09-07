package com.yhglobal.gongxiao.warehouse.controller.warehouse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.model.ManualInboundOrder;
import com.yhglobal.gongxiao.warehouse.model.bo.CreateNewInStockrder;
import com.yhglobal.gongxiao.warehouse.service.InboundService;
import com.yhglobal.gongxiao.warehouse.service.ManualInboundService;
import com.yhglobal.gongxiao.warehouseapi.inbound.InsertManualInboundRequest;
import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 其他入单管理controller
 * @author liukai
 */
@Controller
@RequestMapping("/manualInbound")
public class ManualInboundController {

    private static Logger logger = LoggerFactory.getLogger(ManualInboundController.class);

    @Autowired
    ManualInboundService manualInboundService;

    @Autowired
    InboundService inboundService;

    /**
     * 添加手工入库单
     * @param requestJson      入库单商品明细
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertManualInboundOrder",method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult insertManualInboundOrder(String requestJson,HttpServletRequest request){
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][insertManualInboundOrder] params: requestJson={} ",traceId, requestJson);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            //将货品列表json数组的字符串转换为List
            InsertManualInboundRequest record = JSON.parseObject(requestJson, InsertManualInboundRequest.class);
            List<CreateNewInStockrder> createNewInStockrders = JSON.parseObject(record.getPurchaseItemInfoJson(), new TypeReference<List<CreateNewInStockrder>>() {
            });

            ManualInboundOrder manualInboundOrderBasicInfo = new ManualInboundOrder();
            manualInboundOrderBasicInfo.setProjectId(record.getProjectId());
            manualInboundOrderBasicInfo.setSupplierName(record.getSupplierName());
            manualInboundOrderBasicInfo.setCreatedById("");
            manualInboundOrderBasicInfo.setCreatedByName("");
            String format = "yyyy-MM-dd";
            manualInboundOrderBasicInfo.setCreateTime(CommonUtil.StringToDateWithFormat(record.getBusinessDate(), format));
            manualInboundOrderBasicInfo.setStatus(0);
            manualInboundOrderBasicInfo.setWarehouseId(record.getWarehouseId());
            manualInboundOrderBasicInfo.setWarehouseName(record.getWarehouseName());
            manualInboundOrderBasicInfo.setRecieveAddress(record.getRecieveAddress());
            manualInboundOrderBasicInfo.setInboundType(record.getInboundType());
            manualInboundOrderBasicInfo.setNote(record.getRemark());


            int insertNumber = manualInboundService.createManualInbound(rpcHeader, manualInboundOrderBasicInfo,createNewInStockrders);
            logger.info("#traceId={}# [OUT] insertManualInboundOrder success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), insertNumber);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 修改其他入库单
     * @param manualJson
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateIntbound", method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult updateManualInboundOrder(@RequestBody String manualJson, HttpServletRequest request){
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][updateManualInboundOrder] params: manualJson={} ", manualJson);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            //将货品列表json数组的字符串转换为List
            ManualInboundOrder manualInboundOrder = JSON.parseObject(manualJson,ManualInboundOrder.class);
            int updateNumber = manualInboundService.updateManualInboundOrder(rpcHeader, manualInboundOrder);
            logger.info("#traceId={}# [OUT] updateManualInboundOrder success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), updateNumber);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

}
