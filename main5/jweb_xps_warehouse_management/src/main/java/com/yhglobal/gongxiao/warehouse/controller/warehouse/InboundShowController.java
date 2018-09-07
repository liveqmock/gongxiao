package com.yhglobal.gongxiao.warehouse.controller.warehouse;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.bo.InboundOrderBasicInfo;
import com.yhglobal.gongxiao.warehouse.model.bo.InboundOrderItemShowModel;
import com.yhglobal.gongxiao.warehouse.model.bo.InboundOrderShowModel;
import com.yhglobal.gongxiao.warehouse.service.InboundShowService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 仓储模块的入库单管理controller
 */
@RequestMapping("/inbound")
@Controller
public class InboundShowController {

    private static Logger logger = LoggerFactory.getLogger(InboundShowController.class);

    @Autowired
    InboundShowService inboundShowService;

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
    @RequestMapping("/selectInboundOrderselect")
    @ResponseBody
    public GongxiaoResult selectInboundOrder(String projectId, String gonxiaoInboundNo, String purchaseNo, String productCode, String goodCode, String dateTime, String warehouseId, String supplier,int pageNumber, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][handleSelectInboundOrder] params: projectId={},gonxiaoInboundNo={},purchaseNo={},productCode={},goodCode={},dateTime={},warehouseId={},supplier={}" , traceId,projectId,gonxiaoInboundNo,purchaseNo,productCode,goodCode,dateTime,warehouseId,supplier);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            if (goodCode.equals("null")){
                goodCode = "";
            }
            if (supplier.equals("null")){
                supplier = "";
            }
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
    @RequestMapping(value = "/selectInboundByInboundNum",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectInboundBasicInfo(String projectId,String inventoryNum,HttpServletRequest request,HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectInboundBasicInfo] params: projectId={},inventoryNum={}" , traceId,projectId,inventoryNum);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
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
    @RequestMapping("/selectInboundOrderItem")
    @ResponseBody
    public GongxiaoResult selectInboundOrItem(String projectId,String inventoryNum,HttpServletRequest request,HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectInboundOrItem] params: projectId={},inventoryNum={}" , traceId,projectId,inventoryNum);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
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

}
