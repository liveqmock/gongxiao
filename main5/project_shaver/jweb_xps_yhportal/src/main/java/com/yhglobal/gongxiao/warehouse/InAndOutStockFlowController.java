package com.yhglobal.gongxiao.warehouse;

import com.github.pagehelper.PageInfo;
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
import com.yhglobal.gongxiao.warehouseapi.model.ProductInventoryFlowShow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 出入库流水
 * @author liukai
 */
@Controller
@RequestMapping("/inventory")
public class InAndOutStockFlowController {

    private static Logger logger = LoggerFactory.getLogger(InAndOutStockFlowController.class);

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
    @RequestMapping(value = "/inventoryFlow", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult handlePurchaseFlow(int pageNumber, int pageSize, String projectId, String productCode, String productName, String warehouseId, String orderNo, String startDate, String endDate, HttpServletRequest request, HttpServletResponse response){
        PageInfo<ProductInventoryFlowShow> productInventoryFlowShowPageInfo = new PageInfo<>();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][handlePurchaseFlow] params: projectId={}, productCode={}, productName={}, warehouseid={}, " +
                    "orderno={}, startdate={}, enddate={}", traceId, projectId,productCode, productName,
                    warehouseId, orderNo, startDate, endDate);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "4353654567";
            productInventoryFlowShowPageInfo = XpsWarehouseManager.selectInventoryFlow(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken, projectId, productCode, productName, warehouseId, orderNo, startDate, endDate, pageNumber,pageSize);
            logger.info("#traceId={}# [OUT] get handlePurchaseFlow success", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), productInventoryFlowShowPageInfo);

        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
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
