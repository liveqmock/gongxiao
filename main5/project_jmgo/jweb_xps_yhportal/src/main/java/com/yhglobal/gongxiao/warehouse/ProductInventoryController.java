package com.yhglobal.gongxiao.warehouse;

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
 * 库存管理
 *
 * @author liukai
 */
@Controller
@RequestMapping("/inventoryQuerry")
public class ProductInventoryController {

    private static Logger logger = LoggerFactory.getLogger(ProductInventoryController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 商品库存查询
     *
     * @param projectId   项目id
     * @param productCode 商品编码
     * @param productName 商品名称
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectProductInventoryInfo(long projectId, String productCode, String productName, int pageNumber, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectProductInventoryInfo] params: projectId={}, productCode={}, productName={}", traceId, projectId, productCode, productName);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "52123454";
            gongxiaoResult = XpsWarehouseManager.selectProductInventoryInfo(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_JMGO.getChannelId(), channelToken, String.valueOf(projectId), productCode, productName, pageNumber, pageSize);
            logger.info("#traceId={}# [OUT] get selectProductInventoryInfo success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

        return gongxiaoResult;
    }

    /**
     * 仓库库存查询
     *
     * @param projectId   项目id
     * @param productCode 商品编码
     * @param productName 商品名
     * @param warehouseId 仓库id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/stock", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selecWarehouseInventoryInfo(String projectId, String productCode, String productName, String warehouseId, String batchNo, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selecWarehouseInventoryInfo] params: projectId={}, productCode={}, productName={}, warehouseId={}, ", traceId, projectId, productCode, productName, warehouseId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "52123454";
            gongxiaoResult = XpsWarehouseManager.selecWarehouseInventoryInfo(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_JMGO.getChannelId(), channelToken, projectId, productCode, productName, warehouseId, batchNo);
            logger.info("#traceId={}# [OUT] get selecWarehouseInventoryInfo success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

//    /**
//     * 导出商品库存Excel
//     * @param
//     * @return
//     */
//    @RequestMapping("/product/export")
//    public void  exportProdctInventory(long projectId, String productCode, String productName, HttpServletRequest request, HttpServletResponse response) {
//
//        String traceId = null;
//        traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//        logger.info("#traceId={}# [IN][exportProdctInventory] params: projectId:{}, productCode:{}, productName:{}" , traceId, projectId,productCode,productName);
//        String downFileName = new String("商品库存列表.xls");
//        try {
//            List<ProductInventoryShowModel> resultList = productInventoryService.selectProductInventoryByName(rpcHeader,0,10,projectId,productCode,productName);
//            ExcelUtil<ProductInventoryShowModel> util = new ExcelUtil<ProductInventoryShowModel>(InventoryquerryStructure.ProductInventoryShowModel.class);
//            Workbook workbook = util.getListToExcel(resultList,"订单列表");
//            ExcelDownUtil.resetResponse(response,workbook,downFileName);
//            logger.info("#traceId={}# [exportProdctInventory][OUT] get exportProdctInventory success",traceId);
//        }catch(Exception e){
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//        }
//    }
//
//    /**
//     * 导出仓库库存Excel
//     * @param
//     * @return
//     */
//    @RequestMapping("/stock/export")
//    public void  exportStockInventory(String projectId,String productCode,String productName,String warehouseId,HttpServletRequest request,HttpServletResponse response) {
//
//        String traceId = null;
//        traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//        logger.info("#traceId={}# [IN][exportStockInventory] params: projectId={}, productCode={}, productName={}, warehouseId={}, " , traceId, projectId,productCode,productName,warehouseId);
//        String downFileName = new String("仓库库存列表.xls");
//        try {
//            List<WarehouseInventoryShowModel> resultList = productInventoryService.conditionalSelectWarehouseInventory(rpcHeader,0,10,projectId,productCode,productName,warehouseId);
//            ExcelUtil<WarehouseInventoryShowModel> util = new ExcelUtil<WarehouseInventoryShowModel>(InventoryquerryStructure.WarehouseInventoryShowModel.class);
//            Workbook workbook = util.getListToExcel(resultList,"订单列表");
//            ExcelDownUtil.resetResponse(response,workbook,downFileName);
//            logger.info("#traceId={}# [exportStockInventory][OUT] get exportStockInventory success",traceId);
//        }catch(Exception e){
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
//        }
//    }
}
