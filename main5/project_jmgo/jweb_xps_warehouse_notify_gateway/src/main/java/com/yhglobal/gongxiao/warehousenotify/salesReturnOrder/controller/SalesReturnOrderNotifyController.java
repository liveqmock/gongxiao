package com.yhglobal.gongxiao.warehousenotify.salesReturnOrder.controller;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 销售退货模块 接收XPS warehouse的回告信息 入口
 */

@Controller
@RequestMapping("/salesReturnOrder")
public class SalesReturnOrderNotifyController {

    private static Logger logger = LoggerFactory.getLogger(SalesReturnOrderNotifyController.class);


    /**
     * 注： 请求结果 需要按 xps warehouse的要求返回去
     */
    @RequestMapping("/xpsNotification")
    @ResponseBody
    public String xpsNotificationEntry() {
        return null;
    }


    /**
     * 仓储模块入库完成通知销售模块
     *
     * @param request
     * @param inboundOrderNo
     * @param traceNo
     * @param productCode
     * @param productName
     * @param productUnit
     * @param inStockQuantity
     * @return
     */
    @RequestMapping("/inboundFinished")
    @ResponseBody
    public GongxiaoResult inboundFinished(HttpServletRequest request,
                                          long projectId,
                                          @RequestParam(defaultValue = "") String inboundOrderNo,
                                          @RequestParam(defaultValue = "") String traceNo,
                                          @RequestParam(defaultValue = "") String productCode,
                                          @RequestParam(defaultValue = "") String productName,
                                          @RequestParam(defaultValue = "") String productUnit,
                                          int inStockQuantity) {
        String traceId = request.getServletPath();
        GongxiaoResult gongxiaoResult = null;
        try {
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "0", "system");
            logger.info("#traceId={}# [IN][itemsOutboundFinishedController] params: inboundOrderNo={}, traceNo={}, productCode={}, productName={}, productUnit={}, inStockQuantity={}",
                    rpcHeader.getTraceId(), inboundOrderNo, traceNo, productCode, productName, productUnit, inStockQuantity);
            SalesOrderReturnServiceStructure.SalesReturnInboundReq rpcReq = SalesOrderReturnServiceStructure.SalesReturnInboundReq.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setInboundOrderNo(inboundOrderNo)
                    .setTraceNo(traceNo)
                    .setProductCode(productCode)
                    .setProductName(productName)
                    .setProductUnit(productUnit)
                    .setInStockQuantity(inStockQuantity)
                    .build();
            SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResult = rpcStub.salesReturnInbound(rpcReq);
            if (ErrorCode.SUCCESS.getErrorCode() != rpcResult.getReturnCode()) {
                logger.error("#traceId={}# [inboundFinished][OUT] inboundFinished faild,returnCode={}", rpcResult.getReturnCode());
                gongxiaoResult = new GongxiaoResult(rpcResult.getReturnCode(), rpcResult.getMsg());
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
            logger.info("#traceId={}# [inboundFinished][OUT] returnCode={}", traceId, gongxiaoResult.getReturnCode());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


}
