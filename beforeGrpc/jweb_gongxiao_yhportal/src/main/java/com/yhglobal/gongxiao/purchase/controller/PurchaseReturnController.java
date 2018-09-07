package com.yhglobal.gongxiao.purchase.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.purchase.bo.PurchaseReturnInboundInfo;
import com.yhglobal.gongxiao.purchase.bo.ProductReturnInfo;
import com.yhglobal.gongxiao.purchase.bo.PurchaseReturnOrderDetail;
import com.yhglobal.gongxiao.purchase.bo.PurchaseReturnOrderInfo;
import com.yhglobal.gongxiao.purchase.service.PurchaseReturnService;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.ValidationUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 采购退货
 *
 * @author: 陈浩
 * @create: 2018-03-15 11:16
 **/
@Controller
@RequestMapping("/purchaseReturn")
public class PurchaseReturnController {

    private static Logger logger = LoggerFactory.getLogger(PurchaseReturnController.class);

    @Reference
    PurchaseReturnService purchaseReturnService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 查询退货单数据
     *
     * @param projectId   项目ID
     * @param warehouseId 仓库ID
     * @param returnType  退货类型
     * @param orderNumber 单号(采购单号/退货单号)
     * @param startDate   开始时间
     * @param endDate     结束时间
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPurchaseReturnList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getPurchaseReturnList(String projectId,
                                                String warehouseId,
                                                int returnType,
                                                String orderNumber,
                                                String startDate,
                                                String endDate,
                                                HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),  request.getServletPath());
            logger.info("#traceId={}# [IN][getPurchaseReturnList] params: projectId:{}, warehouseId:{}, returnType:{}, orderNumber:{}, startDate:{}, endDate:{} ",
                    projectId,warehouseId,returnType,orderNumber,startDate,endDate);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<PurchaseReturnOrderInfo> purchaseReturnList = purchaseReturnService.getPurchaseReturnList(rpcHeader,projectId, warehouseId, returnType, orderNumber, startDate, endDate);
            logger.info("#traceId={}# [OUT] getPurchaseReturnList success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), purchaseReturnList);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 通过采购单获取入库单列表
     *
     * @param projectId           项目ID
     * @param purchaseOrderNumber 采购单号
     * @param request
     * @return
     */
    @RequestMapping(value = "/getInboundOrderList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getInboundOrderList(String projectId, String purchaseOrderNumber, HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),  request.getServletPath());
        logger.info("#traceId={}# [IN][handlePurchaseFlow] params: projectId:{}, purchaseOrderNumber:{} ",projectId,purchaseOrderNumber);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        boolean notEmpty = ValidationUtil.isNotEmpty(projectId, purchaseOrderNumber);
        if (!notEmpty) {
            logger.error("#traceId={}# [OUT] fail to get currency: ", traceId);
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        try {
            List<PurchaseReturnInboundInfo> purchaseReturnInboundInfoList = purchaseReturnService.getInboundOrderList(rpcHeader,projectId, purchaseOrderNumber);
            logger.info("#traceId={}# [OUT] get InventoryFlow success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), purchaseReturnInboundInfoList);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 通过入库单号获取入库货品明细
     *
     * @param gongxiaoInboundOrderNo 入库单号
     * @param request
     * @return
     */
    @RequestMapping(value = "/getInboundItemList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getInboundItemList(String projectId, String gongxiaoInboundOrderNo, HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),  request.getServletPath());
        logger.info("#traceId={}# [IN][getInboundItemList] params: projectId:{}, gongxiaoInboundOrderNo:{} ",projectId,gongxiaoInboundOrderNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        boolean notEmpty = ValidationUtil.isNotEmpty(gongxiaoInboundOrderNo);
        if (!notEmpty) {
            logger.error("#traceId={}# [OUT] fail to getInboundItemList: ", traceId);
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        try{
            List<ProductReturnInfo> inboundItemList = purchaseReturnService.getInboundItemList(rpcHeader,projectId, gongxiaoInboundOrderNo);
            logger.info("#traceId={}# [OUT] getInboundItemList success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), inboundItemList);
        } catch(Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

}

    /**
     * 新增采购退货信息
     *
     * @param originalPurchaseOrderNo        原始采购单号
     * @param originalGongxiaoInboundOrderNo 原始入库单号
     * @param recipientName                  收件人姓名
     * @param recipientCompanyName           收件人公司名称
     * @param recipientAddress               收件人地址
     * @param recipientDetailAddress         收件人详细地址
     * @param recipientMobile                收件人电话
     * @param shippingMode                   物流方式
     * @param logisticsOrderNo               物流单号
     * @param logisticsCompany               物流公司
     * @param warehouseId                    仓库ID
     * @param warehouseName                  仓库名称
     * @param freightPaidBy                  运费承担方
     * @param freight                        运费金额
     * @param projectId                      项目ID
     * @param projectName                    项目名称
     * @param returnType                     退货类型
     * @param purchaseReturnOrderItemJson    采购退货单货品信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/putPurchaseDetail", method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult putPurchaseDetail(String originalPurchaseOrderNo,
                                            String originalGongxiaoInboundOrderNo,
                                            String recipientName,
                                            String recipientCompanyName,
                                            String recipientAddress,
                                            String recipientDetailAddress,
                                            String recipientMobile,
                                            int shippingMode,
                                            String logisticsOrderNo,
                                            String logisticsCompany,
                                            String warehouseId,
                                            String warehouseName,
                                            int freightPaidBy,
                                            double freight,
                                            String projectId,
                                            String projectName,
                                            int returnType,
                                            String purchaseReturnOrderItemJson,
                                            HttpServletRequest request) throws Exception {

        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),  request.getServletPath());
            logger.info("#traceId={}# [IN][putPurchaseDetail] params: originalPurchaseOrderNo:{}, originalGongxiaoInboundOrderNo:{}, recipientName:{}, recipientCompanyName:{}, recipientAddress:{}, recipientDetailAddress:{}. " +
                            "recipientMobile:{}, shippingMode:{}, logisticsOrderNo:{}, warehouseId:{}, warehouseName:{}. " +
                            "freightPaidBy:{}, freight:{}, projectId:{}, projectName:{}, returnType:{} ",
                    originalPurchaseOrderNo,originalGongxiaoInboundOrderNo,recipientName,recipientCompanyName,recipientAddress,recipientDetailAddress,
                    recipientMobile,shippingMode,logisticsOrderNo,warehouseId,warehouseName,freightPaidBy,freight,projectId,projectName,returnType);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

            PurchaseReturnOrderDetail purchaseReturnOrderDetail = new PurchaseReturnOrderDetail();
            purchaseReturnOrderDetail.setOriginalPurchaseOrderNo(originalPurchaseOrderNo);
            purchaseReturnOrderDetail.setOriginalGongxiaoInboundOrderNo(originalGongxiaoInboundOrderNo);
            purchaseReturnOrderDetail.setReturnedOrderStatus(0);
            purchaseReturnOrderDetail.setRecipientName(recipientName);
            purchaseReturnOrderDetail.setRecipientCompanyName(recipientCompanyName);
            purchaseReturnOrderDetail.setRecipientAddress(recipientAddress);
            purchaseReturnOrderDetail.setRecipientDetailAddress(recipientDetailAddress);
            purchaseReturnOrderDetail.setRecipientMobile(recipientMobile);
            purchaseReturnOrderDetail.setShippingMode(shippingMode);
            purchaseReturnOrderDetail.setLogisticsOrderNo(logisticsOrderNo);
            purchaseReturnOrderDetail.setLogisticsCompany(logisticsCompany);
            purchaseReturnOrderDetail.setWarehouseId(warehouseId);
            purchaseReturnOrderDetail.setWarehouseName(warehouseName);
            purchaseReturnOrderDetail.setFreightPaidBy(freightPaidBy);
            purchaseReturnOrderDetail.setFreight(freight);
            purchaseReturnOrderDetail.setProjectId(projectId);
            purchaseReturnOrderDetail.setProjectName(projectName);
            purchaseReturnOrderDetail.setReturnType(returnType);

            List<ProductReturnInfo> productReturnInfos = JSON.parseObject(purchaseReturnOrderItemJson, new TypeReference<List<ProductReturnInfo>>() {
            });
            purchaseReturnOrderDetail.setProductReturnInfoList(productReturnInfos);
            int i = purchaseReturnService.putPurchaseReturnDetail(rpcHeader,purchaseReturnOrderDetail);
            logger.info("#traceId={}# [OUT] putPurchaseDetail success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), i);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 获取采购详情
     *
     * @param purchaseReturnedOrderNo 退货单号
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPurchaseReturnDetail", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getPurchaseReturnDetail(String purchaseReturnedOrderNo, HttpServletRequest request) throws Exception {
        String traceId =null;
        boolean notEmpty = ValidationUtil.isNotEmpty(purchaseReturnedOrderNo);
        logger.info("#traceId={}# [IN][getPurchaseReturnDetail] params: purchaseReturnedOrderNo:{} ",purchaseReturnedOrderNo);
        traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),  request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        if (!notEmpty) {
            logger.error("#traceId={}# [OUT] fail to getPurchaseReturnDetail: ", traceId);
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        try {
            PurchaseReturnOrderDetail purchaseDetail = purchaseReturnService.getPurchaseDetail(rpcHeader,purchaseReturnedOrderNo);
            logger.info("#traceId={}# [OUT] getPurchaseReturnDetail success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), purchaseDetail);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

}
