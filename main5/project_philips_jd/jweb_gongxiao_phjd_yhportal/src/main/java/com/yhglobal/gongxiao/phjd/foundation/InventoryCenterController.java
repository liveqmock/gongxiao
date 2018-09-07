package com.yhglobal.gongxiao.phjd.foundation;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import com.yhglobal.gongxiao.warehouseapi.model.InventoryAge;
import com.yhglobal.gongxiao.warehouseapi.model.InventoryCheckModel;
import com.yhglobal.gongxiao.warehouseapi.model.InventoryLedger;
import com.yhglobal.gongxiao.warehouseapi.model.ProductInventoryFlowShow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 库存中心
 *
 * @author yuping.lin
 */
@Controller
@RequestMapping("/inventory/center")
public class InventoryCenterController {
    private static Logger logger = LoggerFactory.getLogger(InventoryCenterController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    /**
     * 货品库存查询
     *
     * @param request
     * @param response
     * @param productCodes       货品编码
     * @param descriptionOfGoods 货品名称
     * @param pageNumber         每页条数
     * @param pageSize           当前几页
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectProductInventoryInfo")
    public GongxiaoResult selectProductInventoryInfo(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(defaultValue = "") String productCodes,
                                                     @RequestParam(defaultValue = "") String descriptionOfGoods,
                                                     @RequestParam(defaultValue = "1") int pageNumber,
                                                     @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectProductInventoryInfo] params: productCodes = {}, descriptionOfGoods={}, pageNumber = {}, pageSize = {}",
                    productCodes, descriptionOfGoods, pageNumber, pageSize);
            gongxiaoResult = XpsWarehouseManager.selectProductInventoryInfo(portalConfig.getWarehouseUrl(),
                    portalConfig.getXpsChannelId(), "",
                    String.valueOf(portalUserInfo.getProjectId()), productCodes, descriptionOfGoods, pageNumber, pageSize);
            logger.info("#traceId={}# [OUT][selectProductInventoryInfo] success");
//            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), gongxiaoResult);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 仓库库存查询
     *
     * @param request
     * @param response
     * @param productCodes       货品编码
     * @param descriptionOfGoods 货品名称
     * @param warehouse          仓库
     * @param pageNumber         每页条数
     * @param pageSize           当前几页
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectWarehouseInventory")
    public GongxiaoResult selectWarehouseInventory(HttpServletRequest request, HttpServletResponse response,
                                                   @RequestParam(defaultValue = "") String productCodes,
                                                   @RequestParam(defaultValue = "") String descriptionOfGoods,
                                                   @RequestParam(defaultValue = "") String warehouse,
                                                   @RequestParam(defaultValue = "") String batchNo,
                                                   @RequestParam(defaultValue = "1") int pageNumber,
                                                   @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectWarehouseInventory] params: productCodes = {}, descriptionOfGoods={}, warehouse = {}, pageNumber = {}, pageSize = {}",
                    productCodes, descriptionOfGoods, warehouse, pageNumber, pageSize);
            gongxiaoResult = XpsWarehouseManager.selecWarehouseInventoryInfo(portalConfig.getWarehouseUrl(),
                    portalConfig.getXpsChannelId(), "",
                    String.valueOf(portalUserInfo.getProjectId()), productCodes, descriptionOfGoods, warehouse, batchNo);
            logger.info("#traceId={}# [OUT][selectWarehouseInventory] success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 进销存台账
     *
     * @param request
     * @param response
     * @param productCodes       货品编码
     * @param descriptionOfGoods 货品名称
     * @param warehouse          仓库
     * @param startDate          开始日期
     * @param endDate            结束日期
     * @param pageNumber         当前页数
     * @param pageSize           每页条数
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectInvoicingLedgers")
    public GongxiaoResult selectInvoicingLedgers(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestParam(defaultValue = "") String productCodes,
                                                 @RequestParam(defaultValue = "") String descriptionOfGoods,
                                                 @RequestParam(defaultValue = "") String warehouse,
                                                 @RequestParam(defaultValue = "") String startDate,
                                                 @RequestParam(defaultValue = "") String endDate,
                                                 @RequestParam(defaultValue = "1") int pageNumber,
                                                 @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        PageInfo<InventoryLedger> pageinfo = new PageInfo<>();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectInvoicingLedgers] params: productCodes = {}, descriptionOfGoods={}, warehouse = {}, inventoryState = {}, startDate = {}, endDate = {}, pageNumber = {}, pageSize = {}",
                    productCodes, descriptionOfGoods, warehouse, startDate, endDate, pageNumber, pageSize);
            pageinfo = XpsWarehouseManager.selectAccountInfo(portalConfig.getWarehouseUrl(), portalConfig.getXpsChannelId(), "", String.valueOf(portalUserInfo.getProjectId()),
                    productCodes, descriptionOfGoods, warehouse, startDate, endDate, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageinfo);
            logger.info("#traceId={}# [OUT][selectInvoicingLedgers] success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 出入库流水台账
     *
     * @param productCodes       货品编码
     * @param descriptionOfGoods 货品名称
     * @param warehouse          仓库
     * @param oddNumbers         单号
     * @param startDate          开始日期
     * @param endDate            结束日期
     * @param pageNumber         当前页数
     * @param pageSize           每页条数
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectOutgoingAccount")
    public GongxiaoResult selectOutgoingAccount(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(defaultValue = "") String productCodes,
                                                @RequestParam(defaultValue = "") String descriptionOfGoods,
                                                @RequestParam(defaultValue = "") String warehouse,
                                                @RequestParam(defaultValue = "") String oddNumbers,
                                                @RequestParam(defaultValue = "") String startDate,
                                                @RequestParam(defaultValue = "") String endDate,
                                                @RequestParam(defaultValue = "1") int pageNumber,
                                                @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        PageInfo<ProductInventoryFlowShow> pageinfo = new PageInfo<>();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectOutgoingAccount] params: productCodes = {}, descriptionOfGoods={}, warehouse = {}, inventoryState = {}, oddNumbers = {}, startDate = {}, endDate = {}, pageNumber = {}, pageSize = {}",
                    productCodes, descriptionOfGoods, warehouse, oddNumbers, startDate, endDate, pageNumber, pageSize);
            pageinfo = XpsWarehouseManager.selectInventoryFlow(portalConfig.getWarehouseUrl(), portalConfig.getXpsChannelId(), "", String.valueOf(portalUserInfo.getProjectId()),
                    productCodes, descriptionOfGoods, warehouse, oddNumbers, startDate, endDate, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageinfo);
            logger.info("#traceId={}# [OUT][selectOutgoingAccount] success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 批次库存报表
     *
     * @param request
     * @param response
     * @param batchNumber              批次号
     * @param warehouseNumber          入库单号
     * @param outWarehouseSingleNumber 出库单号
     * @param startDate                开始生成日期
     * @param endDate                  结束生成日期
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectDailyInventoryReport")
    public GongxiaoResult selectDailyInventoryReport(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(defaultValue = "") String batchNumber,
                                                     @RequestParam(defaultValue = "") String warehouseNumber,
                                                     @RequestParam(defaultValue = "") String outWarehouseSingleNumber,
                                                     @RequestParam(defaultValue = "") String startDate,
                                                     @RequestParam(defaultValue = "") String endDate,
                                                     @RequestParam(defaultValue = "1") int pageNumber,
                                                     @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        PageInfo<InventoryAge> inventoryAgePageInfo = new PageInfo<>();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectDailyInventoryReport] params: batchNumber = {}, warehouseNumber={}, outWarehouseSingleNumber = {},startDate = {}, endDate = {}, pageNumber = {}, pageSize = {}",
                    batchNumber, warehouseNumber, outWarehouseSingleNumber, startDate, endDate, pageNumber, pageSize);
            inventoryAgePageInfo = XpsWarehouseManager.getInventoryAgeInfo(portalConfig.getWarehouseUrl(),
                    portalConfig.getXpsChannelId(), "", String.valueOf(portalUserInfo.getProjectId()),
                    batchNumber, warehouseNumber, outWarehouseSingleNumber, startDate, endDate, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), inventoryAgePageInfo);
            logger.info("#traceId={}# [OUT][selectDailyInventoryReport] success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 库存核对
     *
     * @param request
     * @param response
     * @param productCode 货品编码
     * @param productName 货品名称
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectBatchInventoryReport")
    public GongxiaoResult selectBatchInventoryReport(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(defaultValue = "") String productCode,
                                                     @RequestParam(defaultValue = "") String productName,
                                                     @RequestParam(defaultValue = "1") int pageNumber,
                                                     @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectBatchInventoryReport] params: productCode = {}, productName={}",
                    productCode, productName, pageNumber, pageSize);
            PageInfo<InventoryCheckModel> inventoryCheckModelPageInfo = new PageInfo<>();
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "574656879";
            inventoryCheckModelPageInfo = XpsWarehouseManager.selectCheckInventory(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, String.valueOf(portalUserInfo.getProjectId()), "", productCode, productName, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), inventoryCheckModelPageInfo);
            logger.info("#traceId={}# [OUT][selectBatchInventoryReport] success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
