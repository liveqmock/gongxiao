package com.yhglobal.gongxiao.inventory.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.excel.ExcelUtil;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import com.yhglobal.gongxiao.inventory.service.InventoryFlowService;
import com.yhglobal.gongxiao.util.ExcelDownUtil;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouse.bo.WarehouseInventoryShowModel;
import com.yhglobal.gongxiao.warehousemanagement.bo.ProductInventoryFlowShow;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 出入库流水
 * @author liukai
 */
@Controller
@RequestMapping("/inventory")
public class InAndOutStockFlowController {

    private static Logger logger = LoggerFactory.getLogger(InAndOutStockFlowController.class);

    @Reference
    InventoryFlowService inventoryFlowService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象

    /**
     * 查询出入库流水
     * @param pageNumber        页数
     * @param pageSize          页码
     * @param projectId         项目id
     * @param productCode       商品编码
     * @param productName       商品名称
     * @param warehouseId       仓库id
     * @param orderNo           单号
     * @param startDate         起始时间
     * @param endDate           结束时间
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/purchaseFlow", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult handlePurchaseFlow(int pageNumber, int pageSize, String projectId, String productCode, String productName, String warehouseId, String orderNo, String startDate, String endDate, HttpServletRequest request, HttpServletResponse response){
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][handlePurchaseFlow] params: projectId={}, productCode={}, productName={}, warehouseid={}, " +
                    "orderno={}, startdate={}, enddate={}", traceId, projectId,productCode, productName,
                    warehouseId, orderNo, startDate, endDate);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PageInfo<ProductInventoryFlowShow> resultList = inventoryFlowService.conditionalSelectInventoryFlow(rpcHeader, pageNumber, pageSize, projectId, productCode, productName, warehouseId, orderNo, startDate, endDate);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resultList);
            logger.info("#traceId={}# [OUT] get handlePurchaseFlow success: resultList.size()={}", traceId, resultList.getTotal());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

//    /**
//     * 导出出入库流水Excel
//     * @param
//     * @return
//     */
//    @RequestMapping("/purchaseFlow/export")
//    public void  exportStocFlow(int pageNumber, int pageSize, String projectId, String productCode, String productName, String warehouseId, String orderNo, String startDate, String endDate, HttpServletRequest request, HttpServletResponse response) {
//
//        String traceId = null;
//        traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//        logger.info("#traceId={}# [IN][handlePurchaseFlow] params: projectId={}, productCode={}, productName={}, warehouseid={}, " +
//                        "orderno={}, startdate={}, enddate={}", traceId, projectId,productCode, productName,
//                warehouseId, orderNo, startDate, endDate);
//        String downFileName = new String("出入库流水列表.xls");
//        try {
//            PageInfo<ProductInventoryFlowShow> resultList = inventoryFlowService.conditionalSelectInventoryFlow(rpcHeader, pageNumber, pageSize, projectId, productCode, productName, warehouseId, orderNo, startDate, endDate);
//            ExcelUtil<ProductInventoryFlowShow> util = new ExcelUtil<ProductInventoryFlowShow>(ProductInventoryFlowShow.class);
//            Workbook workbook = util.getListToExcel(resultList,"订单列表");
//            ExcelDownUtil.resetResponse(response,workbook,downFileName);
//            logger.info("#traceId={}# [exportStockInventory][OUT] get exportStockInventory success",traceId);
//        }catch(Exception e){
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//        }
//    }

}
