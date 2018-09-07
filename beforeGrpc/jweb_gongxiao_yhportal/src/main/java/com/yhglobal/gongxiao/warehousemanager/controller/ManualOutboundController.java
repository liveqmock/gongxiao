package com.yhglobal.gongxiao.warehousemanager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.inventory.bo.InventoryBatch;
import com.yhglobal.gongxiao.inventory.service.InventoryBatchService;
import com.yhglobal.gongxiao.purchase.bo.CreatePurchaseItemInfo;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouse.service.ManualOutboundService;
import com.yhglobal.gongxiao.warehouse.service.OutboundService;
import com.yhglobal.gongxiao.warehousemanagement.model.CreateOutboundItemInfo;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualOutboundOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 其它出库单
 * @author liukai
 */
@Controller
@RequestMapping("/manualOutbound")
public class ManualOutboundController {

    private static Logger logger = LoggerFactory.getLogger(ManualInboundController.class);

    @Reference
    ProductService productService;

    @Reference
    ManualOutboundService manualOutboundService;

    @Reference
    InventoryBatchService inventoryBatchService;

    @Reference
    OutboundService outboundService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;


    /**
     * 查询所有的其他出库单
     * @param request
     * @return
     */
    @RequestMapping("/getManualOutboundList")
    @ResponseBody
    public GongxiaoResult getManualOutboundListList(HttpServletRequest request){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getManualOutboundListList] ", traceId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<ManualOutboundOrder> resultList = manualOutboundService.getManualOutboundList(rpcHeader);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(resultList);logger.info("#traceId={}# [OUT] get getManualOutboundListList success: resultList.size()={}", traceId, resultList.size());
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 新增其他出库单
     * @param projectId             项目id
     * @param warehouseId           仓库id
     * @param warehouseName         仓库名称
     * @param delieverAddress       发货地址
     * @param supplierName          供应商
     * @param businessDate          业务日期
     * @param outboundType          出库类型
     * @param remark                备注
     * @param itemsInfo             出库商品明细
     * @param request
     * @return
     */
    @RequestMapping("/insertOutbound")
    @ResponseBody
    public GongxiaoResult insertManualOutboundOrder(String projectId,
                                                    String warehouseId,
                                                    String warehouseName,
                                                    String delieverAddress,
                                                    String supplierName,
                                                    String businessDate,
                                                    int outboundType,
                                                    String remark,
                                                    String itemsInfo, HttpServletRequest request){
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][insertManualOutboundOrder] params: projectId={},warehouseId={},warehouseName={},delieverAddress={},supplierName={},businessDate={},outboundType={},remark={},itemsInfo={} ",traceId, projectId,warehouseId,warehouseName,delieverAddress,supplierName,businessDate,outboundType,remark,itemsInfo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            //将货品列表json数组的字符串转换为List

            ArrayList<CreateOutboundItemInfo> createOutboundItemInfos = JSON.parseObject(itemsInfo, new TypeReference<ArrayList<CreateOutboundItemInfo>>() {});

            ManualOutboundOrder manualOutboundOrder = new ManualOutboundOrder();
            manualOutboundOrder.setOutboundType(outboundType);
            manualOutboundOrder.setStatus(0);
            manualOutboundOrder.setCreatedByName(rpcHeader.getUsername());
            manualOutboundOrder.setCreatedById(rpcHeader.getUid());
            String format = "yyyy-MM-dd";
            manualOutboundOrder.setCreateTime(CommonUtil.StringToDateWithFormat(businessDate, format));
            manualOutboundOrder.setNote(remark);
            manualOutboundOrder.setProjectId(projectId);
            manualOutboundOrder.setDeliverAddress(delieverAddress);
            manualOutboundOrder.setWarehouseId(warehouseId);
            manualOutboundOrder.setWarehouseName(warehouseName);


            int insertNumber = manualOutboundService.createManualOutbound(rpcHeader,manualOutboundOrder,createOutboundItemInfos);
            logger.info("#traceId={}# [OUT] insertManualOutboundOrder success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), insertNumber);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 修改其他出库单
     * @param manualJson
     * @param request
     * @return
     */
    @RequestMapping("/updateOutbound")
    @ResponseBody
    public GongxiaoResult updateManualOutboundOrder(@RequestBody String manualJson, HttpServletRequest request){
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][updateManualOutboundOrder] params: manualJson={} ",traceId, manualJson);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            //将货品列表json数组的字符串转换为List
            ManualOutboundOrder manualOutboundOrder = JSON.parseObject(manualJson,ManualOutboundOrder.class);
            int updateNumber = manualOutboundService.updateManualOutboundOrder(rpcHeader, manualOutboundOrder);
            logger.info("#traceId={}# [OUT] updateManualOutboundOrder success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), updateNumber);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 获取商品信息
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping("/getProductList")
    @ResponseBody
    public GongxiaoResult getProductList(int pageNo, int pageSize, HttpServletRequest request){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getProductList] ", traceId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<ProductBasic>  resultList = productService.selectAll(rpcHeader,pageNo,pageSize);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(resultList);logger.info("#traceId={}# [OUT] get getProductList success: resultList.size()={}", traceId, resultList.size());
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 其他出库批次查询
     *
     * @param projectId   项目id
     * @param productCode 货品编码
     * @param warehouseId 货品编码
     * @return
     */
    @RequestMapping("/batch")
    @ResponseBody
    public GongxiaoResult detail(HttpServletRequest request, HttpServletResponse response, int pageNumber, int pageSize, String projectId, String productCode,String warehouseId) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderList][IN] params:  salesOrderNo={}", traceId);
            PageInfo<InventoryBatch> list = inventoryBatchService.getBatchDetailByWarehouse(rpcHeader, projectId, productCode, warehouseId, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [getSalesOrderList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

//    @RequestMapping("/confirmOutbound")
//    @ResponseBody
//    public GongxiaoResult confirmManualOutboundOrder(String projectId, String supplierName, int outboundType, String warehouseId, String dateTime
//            , String note, String manualJson, HttpServletRequest request){
//        String traceId = null;
//        GongxiaoResult gongxiaoResult = new GongxiaoResult();
//        try {
//            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//            logger.info("#traceId={}# [IN][confirmManualOutboundOrder] params: projectId={},supplierName={},outboundType={},warehouseId={},dateTime={},note={},manualJson={} ", projectId,supplierName,outboundType,warehouseId,dateTime,note,manualJson);
//            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            //将货品列表json数组的字符串转换为List
//            List<ManualOutboundOrder> manualOutboundOrderList = JSON.parseArray(manualJson,ManualOutboundOrder.class);
//            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//            Date date = format.parse(dateTime);
//            outboundService.insertNewOutboundOrder(rpcHeader,projectId,supplierName,warehouseId,date,outboundType,note,manualOutboundOrderList);
//            logger.info("#traceId={}# [OUT] confirmManualOutboundOrder success: ", traceId);
//            gongxiaoResult.setReturnCode(0);
//        } catch (Exception e) {
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
//        }
//        return gongxiaoResult;
//    }

}
