package com.yhglobal.gongxiao.sales.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.inventory.bo.InventoryBatch;
import com.yhglobal.gongxiao.inventory.service.InventoryBatchService;
import com.yhglobal.gongxiao.purchase.bo.OutboundNotificationBackItem;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.service.SalesOrderService;
import com.yhglobal.gongxiao.sales.service.SalesScheduleDeliveryService;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.yhglobal.gongxiao.constant.ErrorCode.ARGUMENTS_INVALID;

/**
 * 预约发货表现层
 *
 * @Author: 葛灿
 */
@Controller
@RequestMapping("/sales/schedule")
public class SalesScheduleDeliveryController {
    private static Logger logger = LoggerFactory.getLogger(SalesOrderController.class);
    @Autowired
    private PortalConfig portalConfig;
    @Reference
    private SalesScheduleDeliveryService salesScheduleDeliveryService;
    @Reference
    private SalesOrderService salesOrderService;
    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象
    @Reference
    InventoryBatchService inventoryBatchService;

    /**
     * 查询销售预约商品信息
     *
     * @param salesOrderNo 销售单号
     * @param productCodes 货品编码 有多条，使用","分割
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public GongxiaoResult getScheduleInfo(HttpServletRequest request, HttpServletResponse response, String salesOrderNo, String productCodes) {
        GongxiaoResult gongxiaoResult;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderList][IN] params:  salesOrderNo={}", traceId, salesOrderNo);
            List<InventoryBatch> inventoryBatches = salesScheduleDeliveryService.selectSaleScheduleProductList(rpcHeader, salesOrderNo, productCodes);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), inventoryBatches);
            logger.info("#traceId={}# [getSalesOrderList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 预约商品批次查询
     *
     * @param projectId   项目id
     * @param productCode 货品编码
     * @return
     */
    @RequestMapping("/batch")
    @ResponseBody
    public GongxiaoResult detail(HttpServletRequest request, HttpServletResponse response, int pageNumber, int pageSize, String projectId, String productCode) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderList][IN] params:  salesOrderNo={}", traceId);
            PageInfo<InventoryBatch> list = inventoryBatchService.getBatchDetail(rpcHeader, projectId, productCode, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [getSalesOrderList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 发起预约
     *
     * @param salesOrderNo   销售单号
     * @param itemsInfo      产品信息（JSON）
     * @param arrivalDateStr 到货日期
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public GongxiaoResult getList(HttpServletRequest request, HttpServletResponse response,
                                  String salesOrderNo, String projectId, String itemsInfo,
                                  String arrivalDateStr) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSalesOrderList][IN] params:  salesOrderNo={}", traceId, salesOrderNo);
            List<InventoryBatch> inventoryBatches = JSON.parseArray(itemsInfo, InventoryBatch.class);
            // set存放了 productCode + batchNo + warehouseId +inventoryType + purchaseType ，做重复校验
            Set<String> checkSet = new HashSet<>();
            //key 商品型号 value 本次预约的总数量
            HashMap<String, Integer> checkMap = new HashMap<>();
            if (StringUtils.isEmpty(arrivalDateStr)) {
                gongxiaoResult = new GongxiaoResult<>(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                logger.info("#traceId={}# [getSalesOrderList][OUT] arguments invalid!");
                return gongxiaoResult;
            }
            for (InventoryBatch inventoryBatch : inventoryBatches) {
                String productCode = inventoryBatch.getProductCode(); // 货品编码
                int scheduleQuantity = inventoryBatch.getScheduleQuantity(); // 预约数量
                int orderUnhandledQuantity = inventoryBatch.getOrderUnhandledQuantity();
                int inventoryBatchAmount = inventoryBatch.getInventoryBatchAmount();
                //校验是否存在 预约数量》未发货数量  预约数量》库存数量 渔业数量=0
                if (scheduleQuantity > orderUnhandledQuantity
                        || scheduleQuantity > inventoryBatchAmount
                        || scheduleQuantity == 0) {
                    gongxiaoResult = new GongxiaoResult<>(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                    logger.info("#traceId={}# [getSalesOrderList][OUT] arguments invalid!");
                    return gongxiaoResult;
                }
                String uniqueString = productCode + inventoryBatch.getBatchNo() + inventoryBatch.getWarehouseId()
                        + inventoryBatch.getInventoryStatus() + inventoryBatch.getPurchaseType();
                //校验是否存在相同行
                if (checkSet.contains(uniqueString)) {
                    gongxiaoResult = new GongxiaoResult<>(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                    logger.info("#traceId={}# [getSalesOrderList][OUT] arguments invalid!");
                    return gongxiaoResult;
                } else {
                    checkSet.add(uniqueString);
                }
                if (checkMap.get(productCode) == null) {
                    checkMap.put(productCode, scheduleQuantity);
                } else {
                    checkMap.put(productCode, checkMap.get(productCode) + scheduleQuantity);
                }
            }
            for (InventoryBatch inventoryBatch : inventoryBatches) {
                String productCode = inventoryBatch.getProductCode();
                int orderUnhandledQuantity = inventoryBatch.getOrderUnhandledQuantity();
                Integer codeTotalQuantity = checkMap.get(productCode);
                if (codeTotalQuantity > orderUnhandledQuantity) {
                    gongxiaoResult = new GongxiaoResult<>(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                    logger.info("#traceId={}# [getSalesOrderList][OUT] arguments invalid!");
                    return gongxiaoResult;
                }
            }
            Date arrivalDate = DateUtils.parseDate(arrivalDateStr, "yyyy-MM-dd");
            RpcResult rpcResult = salesScheduleDeliveryService.createScheduleOrder(rpcHeader, projectId, salesOrderNo, inventoryBatches, arrivalDate);
            gongxiaoResult = new GongxiaoResult<>(rpcResult.getCode(), rpcResult.getMessage());
            logger.info("#traceId={}# [getSalesOrderList][OUT] create success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response,
                     String outboundOrderNo) {
        try {
            RpcHeader rpcHeader = new RpcHeader("test");
            rpcHeader.setUid("111");
            rpcHeader.setUsername("geeeeeeeeeeeee");
            //出库通知
            OutboundNotificationBackItem item1 = new OutboundNotificationBackItem();
            item1.setProductCode("04.01.01.01.027");
            item1.setOutboundQuantity(1);
            OutboundNotificationBackItem item2 = new OutboundNotificationBackItem();
            ArrayList<OutboundNotificationBackItem> list = new ArrayList<>();
            list.add(item1);
//            list.add(item2);
//            salesScheduleDeliveryService.outboundNotification(rpcHeader, "OUT17405014825763072", list);
//            salesScheduleDeliveryService.outboundOrderFinished(rpcHeader,"OUT17405014825763072");
//            salesScheduleDeliveryService.outboundSigned(rpcHeader, "OUT17405014825763072");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
