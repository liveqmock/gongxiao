package com.yhglobal.gongxiao.warehouse.controller.warehouse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrder;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.bo.OutboundOrderBasicInfo;
import com.yhglobal.gongxiao.warehouse.model.bo.OutboundOrderItemShowModel;
import com.yhglobal.gongxiao.warehouse.model.bo.OutboundOrderShowModel;
import com.yhglobal.gongxiao.warehouse.service.OutboundShowService;
import com.yhglobal.gongxiao.warehouseapi.sales.ReturnItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 仓储模块的出库单管理controller
 */
@Controller
@RequestMapping("/outbound")
public class OutboundShowController {

    private static Logger logger = LoggerFactory.getLogger(OutboundShowController.class);

    @Autowired
    OutboundShowService outboundShowService;


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
    @RequestMapping("/selectOutboundOrder")
    @ResponseBody
    public GongxiaoResult selectOutboundOrder(String projectId, String gongxiaoOutNo, String salseNo, String
            createTimeBeging, String createTimeLast, String warehouseId, String productCode, String finishTimeBegin, String finishTimeLast, String supplier, String customer, int pageNumber, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectOutboundOrder] params: projectId={}, inventoryNum={}, salseNum={}, createTimeBeging={}, createTimeLast={}, warehouseId={}, productCode={}, finishTimeBegin={}, finishTimeLast={}, supplier={}, customer={}", traceId, projectId, gongxiaoOutNo, salseNo, createTimeBeging, createTimeLast, warehouseId, productCode, finishTimeBegin, finishTimeLast, supplier, customer);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,"", "");
            PageInfo<OutboundOrderShowModel> resultList  = outboundShowService.selectOutStorageInfo(rpcHeader, pageNumber, pageSize, projectId, gongxiaoOutNo, salseNo, createTimeBeging, createTimeLast, warehouseId, productCode, finishTimeBegin, finishTimeLast, supplier, customer);
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectOutboundOrder success: resultList.size()={}", traceId, resultList.getTotal());
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
    @RequestMapping("/selectOutboundOrderItem")
    @ResponseBody
    public GongxiaoResult selectOutboundOrderItem(String projectId, String gongxiaoOutboundOrderNo,HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectOutboundOrderItem] params: projectId={}, gongxiaoOutboundOrderNo={},", traceId, projectId, gongxiaoOutboundOrderNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            List<OutboundOrderItemShowModel> resultList = outboundShowService.selectOutboundOrderItem(rpcHeader, projectId, gongxiaoOutboundOrderNo);
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectOutboundOrderItem success: resultList.size()={}", traceId, resultList.size());
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
    @RequestMapping(value = "/selectByOutboundNum",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectOutboundBasicInfo(String projectId,String gongxiaoOutboundOrderNo, HttpServletRequest request,HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectOutboundBasicInfo] params: projectId={},gongxiaoOutboundOrderNo={}" , traceId,projectId,gongxiaoOutboundOrderNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            OutboundOrderBasicInfo outboundOrderBasicInfo = outboundShowService.selectOutStorageInfoById(rpcHeader,projectId,gongxiaoOutboundOrderNo);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(outboundOrderBasicInfo);
            logger.info("#traceId={}# [OUT] get selectOutboundBasicInfo success: result.size()={}", traceId, 1);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据销售单号查询出库单基本信息
     * @param projectId
     * @param salesNo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectRecordBySalesNo",method = RequestMethod.GET)
    @ResponseBody
    public List<OutboundOrder> selectRecordBySalesNo(String projectId, String salesNo, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setTraceId("01").setUid("001").setUsername("liukai").build();
        List<OutboundOrder> outboundOrderList = new ArrayList<>();
        try {
            logger.info("#traceId={}# [IN][selectOutboundBasicInfo] params: projectId={},salesNo={}" , rpcHeader.getTraceId(),projectId,salesNo);
            outboundOrderList = outboundShowService.selectRecordBySalesNo(rpcHeader,projectId, salesNo);
            logger.info("#traceId={}# [OUT] get selectOutboundBasicInfo success: result.size()={}", rpcHeader.getTraceId(), 1);
            return outboundOrderList;
        }catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }

    /**
     * 根据出库单好查询 出库单明细
     * @param projectId
     * @param gongxiaoOutboundOrderNo
     * @return
     */
    @RequestMapping(value = "/selectRecordItemByOutboundOrderNo",method = RequestMethod.GET)
    @ResponseBody
    public List<OutboundOrderItem> selectRecordItemByOutboundOrderNo(String projectId, String gongxiaoOutboundOrderNo){
        GongxiaoRpc.RpcHeader rpcHeader =GongxiaoRpc.RpcHeader.newBuilder().setTraceId("01").setUid("001").setUsername("liukai").build();
        List<OutboundOrderItem> outboundOrderItemList = new ArrayList<>();
        try {
            logger.info("#traceId={}# [IN][selectRecordItemByOutboundOrderNo] params: projectId={},gongxiaoOutboundOrderNo={}" , rpcHeader.getTraceId(),projectId,gongxiaoOutboundOrderNo);
            outboundOrderItemList = outboundShowService.selectRecordItemByOutboundOrderNo(rpcHeader,projectId, gongxiaoOutboundOrderNo);
            logger.info("#traceId={}# [OUT] get selectRecordItemByOutboundOrderNo success", rpcHeader.getTraceId());
            return outboundOrderItemList;
        }catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }

    /**
     * 根据销售单号和商品编码 查询出库单
     * @param projectId          项目id
     * @param salesOrderNo       销售单号
     * @param productCode       商品编
     * @return
     */
    @RequestMapping(value = "/selectRecordBySalseNoAndProduct",method = RequestMethod.GET)
    @ResponseBody
    public OutboundOrderItem selectRecordBySalseNoAndProduct(String projectId, String salesOrderNo, String productCode){
        GongxiaoRpc.RpcHeader rpcHeader =GongxiaoRpc.RpcHeader.newBuilder().setTraceId("01").setUid("001").setUsername("liukai").build();
                OutboundOrderItem outboundOrderItem = null;
        try {
            logger.info("#traceId={}# [IN][selectRecordBySalseNoAndProduct] params: projectId={},salesOrderNo={},productCode={}" , rpcHeader.getTraceId(),projectId,salesOrderNo,productCode);
            outboundOrderItem = outboundShowService.selectRecordBySalseNoAndProduct(rpcHeader,projectId, salesOrderNo,productCode);
            logger.info("#traceId={}# [OUT] get selectRecordBySalseNoAndProduct success", rpcHeader.getTraceId());
            return outboundOrderItem;
        }catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }

    }


    /**
     * 根据出库单号和商品编码 修改出库单明细可退货数量
     * @param  returnItemListReq       项目id
     * @return
     */
    @RequestMapping(value = "/modifyReturnQantity",method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult modifyReturnQuantity(String returnItemListReq, HttpServletRequest request){
        GongxiaoRpc.RpcHeader rpcHeader =GongxiaoRpc.RpcHeader.newBuilder().setTraceId("01").setUid("001").setUsername("liukai").build();
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            logger.info("#traceId={}# [IN][selectRecordBySalseNoAndProduct] params: projectId={},gongxiaoOutboundOrderNo={},productCode={}" , rpcHeader.getTraceId());
            List<ReturnItem> returnItemList = JSON.parseObject(returnItemListReq, new TypeReference<List<ReturnItem>>() {
            });
            if (returnItemList.size() > 0){
                for (ReturnItem record : returnItemList){
                    outboundShowService.modifyReturnQuantity(rpcHeader,record.getProjectId(), record.getGongxiaoOutboundOrderNo(),record.getProductCode(),record.getQuantity());
                }
            }


            logger.info("#traceId={}# [OUT] get selectRecordBySalseNoAndProduct success", rpcHeader.getTraceId());
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
        }catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }



}
