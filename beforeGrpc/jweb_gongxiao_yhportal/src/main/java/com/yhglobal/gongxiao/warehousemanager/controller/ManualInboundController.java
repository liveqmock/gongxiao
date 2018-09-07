package com.yhglobal.gongxiao.warehousemanager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.purchase.bo.CreatePurchaseItemInfo;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouse.service.InboundService;
import com.yhglobal.gongxiao.warehouse.service.ManualInboundService;
import com.yhglobal.gongxiao.warehousemanagement.bo.CreateNewInStockrder;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualInboundOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 其他入单管理controller
 * @author liukai
 */
@Controller
@RequestMapping("/manualInbound")
public class ManualInboundController {

    private static Logger logger = LoggerFactory.getLogger(ManualInboundController.class);

    @Reference
    ProductService productService;

    @Reference
    ManualInboundService manualInboundService;

    @Reference
    InboundService inboundService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;



    /**
     * 查询所有的手工入库单
     * @return
     */
    @RequestMapping("/getManualOrderList")
    @ResponseBody
    public GongxiaoResult insertManualInboundOrder(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][insertManualInboundOrder] ", traceId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<ManualInboundOrder> resultList = manualInboundService.getManualInboundList(rpcHeader);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(resultList);logger.info("#traceId={}# [OUT] get selectInboundOrder success: resultList.size()={}", traceId, resultList.size());
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
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
            logger.info("#traceId={}# [IN][insertManualInboundOrder] ", traceId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<ProductBasic>  resultList = productService.selectAll(rpcHeader,pageNo,pageSize);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(resultList);logger.info("#traceId={}# [OUT] get selectInboundOrder success: resultList.size()={}", traceId, resultList.size());
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 添加手工入库单
     * @param projectId         项目id
     * @param warehouseId       仓库id
     * @param warehouseName     仓库名称
     * @param recieveAddress    收货地址
     * @param supplierName      供应商
     * @param businessDate      业务日期
     * @param remark            备注
     * @param inboundType       入库类型
     * @param purchaseItemInfoJson      入库单商品明细
     * @param request
     * @return
     */
    @RequestMapping("/insertInbound")
    @ResponseBody
    public GongxiaoResult insertManualInboundOrder(String projectId,
                                                   String warehouseId,
                                                   String warehouseName,
                                                   String recieveAddress,
                                                   String supplierName,
                                                   String businessDate,
                                                   String remark,
                                                   int inboundType,
                                                   String purchaseItemInfoJson,
                                                   HttpServletRequest request){
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][insertManualInboundOrder] params: projectId={},warehouseId={},warehouseName={},recieveAddress={},supplierName={},businessDate={},remark={},inboundType={},purchaseItemInfoJson={} ",traceId, projectId,warehouseId,warehouseName,recieveAddress,supplierName,businessDate,remark,inboundType,purchaseItemInfoJson);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            //将货品列表json数组的字符串转换为List
            ArrayList<CreateNewInStockrder> createNewInStockrders = JSON.parseObject(purchaseItemInfoJson, new TypeReference<ArrayList<CreateNewInStockrder>>() {});

            ManualInboundOrder manualInboundOrderBasicInfo = new ManualInboundOrder();
            manualInboundOrderBasicInfo.setProjectId(projectId);
            manualInboundOrderBasicInfo.setSupplierName(supplierName);
            manualInboundOrderBasicInfo.setCreatedById(portalUserInfo.getUserId());
            manualInboundOrderBasicInfo.setCreatedByName(portalUserInfo.getUserName());
            String format = "yyyy-MM-dd";
            manualInboundOrderBasicInfo.setCreateTime(CommonUtil.StringToDateWithFormat(businessDate, format));
            manualInboundOrderBasicInfo.setStatus(0);
            manualInboundOrderBasicInfo.setWarehouseId(warehouseId);
            manualInboundOrderBasicInfo.setWarehouseName(warehouseName);
            manualInboundOrderBasicInfo.setRecieveAddress(recieveAddress);
            manualInboundOrderBasicInfo.setInboundType(inboundType);
            manualInboundOrderBasicInfo.setNote(remark);


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
    @RequestMapping("/updateIntbound")
    @ResponseBody
    public GongxiaoResult updateManualInboundOrder(@RequestBody String manualJson, HttpServletRequest request){
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][updateManualInboundOrder] params: manualJson={} ", manualJson);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
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

//    @RequestMapping("/confirmIntbound")
//    @ResponseBody
//    public GongxiaoResult confirmManualInboundOrder( String projectId, String supplierName, int inboundType, String warehouseId, String dateTime
//            , String recieveAddress, String note, String manualJson, HttpServletRequest request){
//        String traceId = null;
//        GongxiaoResult gongxiaoResult = new GongxiaoResult();
//        try {
//            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//            logger.info("#traceId={}# [IN][confirmManualInboundOrder] params: projectId={},supplierName={},inboundType={},warehouseId={},dateTime={},note={},manualJson={}", traceId,projectId,supplierName,inboundType,warehouseId,dateTime,note,manualJson);
//            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            //将货品列表json数组的字符串转换为List
//            List<ManualInboundOrder> manualInboundOrderList = JSON.parseArray(manualJson,ManualInboundOrder.class);
//            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//            Date date = format.parse(dateTime);
//            inboundService.insertNewInboundOrder(rpcHeader,projectId,supplierName,inboundType,warehouseId,date,recieveAddress,note,manualInboundOrderList);
//            logger.info("#traceId={}# [OUT] confirmManualInboundOrder success: ", traceId);
//            gongxiaoResult.setReturnCode(0);
//        } catch (Exception e) {
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
//
//        }
//        return gongxiaoResult;
//    }

}
