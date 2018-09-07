package com.yhglobal.gongxiao.phjd.foundation.storage;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
//import com.yhglobal.gongxiao.phjd.purchase.SupplierCouponPostingController;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import com.yhglobal.gongxiao.warehouseapi.outbound.CancelOrderResponse;
import com.yhglobal.gongxiao.warehouseapi.outbound.CloseOrderResponse;
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
 * 入库通知单
 */
@Controller
@RequestMapping("/storage/informingNotice")
public class InformingNoticeController {
    private static Logger logger = LoggerFactory.getLogger(InformingNoticeController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    /**
     * 入库通知单多条件查询
     *
     * @param gonxiaoInboundNo 入库单号
     * @param purchaseNo       采购单号
     * @param productCode      商品编码
     * @param goodCode         产品编码
     * @param dateTime         创建时间
     * @param warehouseId      仓库id
     * @param supplier         供应商
     * @param pageNumber       页码
     * @param pageSize         页数
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectInboundOrder")
    public GongxiaoResult selectInboundOrder(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "") String gonxiaoInboundNo,
                                             @RequestParam(defaultValue = "") String purchaseNo,
                                             @RequestParam(defaultValue = "") String productCode,
                                             @RequestParam(defaultValue = "") String goodCode,
                                             @RequestParam(defaultValue = "") String dateTime,
                                             @RequestParam(defaultValue = "") String warehouseId,
                                             @RequestParam(defaultValue = "") String supplier,
                                             @RequestParam(defaultValue = "1") int pageNumber,
                                             @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectInboundOrderselect] params: gonxiaoInboundNo = {}, purchaseNo={}, productCode = {}, goodCode = {}, dateTime = {}, warehouseId = {}, supplier = {}, pageNumber = {}, pageSize = {}",
                    gonxiaoInboundNo, purchaseNo, productCode, goodCode, dateTime, warehouseId, supplier, pageNumber, pageSize);
            gongxiaoResult = XpsWarehouseManager.selectInboundOrderselect(portalConfig.getWarehouseUrl(), portalConfig.getXpsChannelId(), "", String.valueOf(portalUserInfo.getProjectId()),
                    gonxiaoInboundNo, purchaseNo, productCode, goodCode, dateTime, warehouseId, supplier, pageNumber, pageSize);
            logger.info("#traceId={}# [OUT][selectInboundOrderselect] success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 新增入库通知单
     *
     * @param request
     * @param response
     * @param warehouseId       仓库id
     * @param warehouseName     仓库名称
     * @param recieveAddress    收货地址
     * @param supplierName      供应商
     * @param businessDate      业务日期
     * @param remark            备注
     * @param inboundType       入库类型
     * @param purchaseItemInfoJson      入库单商品明细
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertInwardNotice")
    public GongxiaoResult insertInwardNotice(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "") String warehouseId,
                                             @RequestParam(defaultValue = "") String warehouseName,
                                             @RequestParam(defaultValue = "") String recieveAddress,
                                             @RequestParam(defaultValue = "") String supplierName,
                                             @RequestParam(defaultValue = "") String businessDate,
                                             @RequestParam(defaultValue = "0") int inboundType,
                                             @RequestParam(defaultValue = "") String purchaseItemInfoJson,
                                             @RequestParam(defaultValue = "") String remark) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            logger.info("#traceId={}# [IN][insertInwardNotice] params: warehouseId = {}, warehouseName={}, supplierName={}, receivingAddress = {}, businessDate = {}, inboundType = {}, purchaseItemInfoJson = {}, remark = {}",
                    warehouseId, warehouseName, recieveAddress, supplierName, businessDate, inboundType, purchaseItemInfoJson, remark);
            if ((warehouseId.isEmpty() || warehouseId == null) || (warehouseName.isEmpty() || warehouseName == null)
                    || (recieveAddress.isEmpty() || recieveAddress == null) || (supplierName.isEmpty() || supplierName == null)
                    || (inboundType == 0)) {
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), "请提交前填写必要的字段值!");
                return gongxiaoResult;
            }
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            gongxiaoResult = XpsWarehouseManager.insertManualInboundOrder(portalConfig.getWarehouseUrl(), portalConfig.getXpsChannelId(), "", String.valueOf(portalUserInfo.getProjectId()),
                    warehouseId, warehouseName, recieveAddress, supplierName, businessDate, remark, inboundType,purchaseItemInfoJson);
            logger.info("#traceId={}# [OUT][insertInwardNotice] success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 取消
     *
     * @param request
     * @param response
     * @param warehouseId            仓库id
     * @param gongxiaoInboundOrderNo 出库单号
     * @return
     */
    @ResponseBody
    @RequestMapping("/cancelInboundOrder")
    public GongxiaoResult cancelInboundOrder(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "") String warehouseId,
                                             @RequestParam(defaultValue = "") String gongxiaoInboundOrderNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            logger.info("#traceId={}# [IN][cancelInboundOrder] params: warehouseId = {}, gongxiaoInboundOrderNo={}", warehouseId, gongxiaoInboundOrderNo);
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            CancelOrderResponse cancelOrderResponse = XpsWarehouseManager.cancelInboundOrder(portalConfig.getWarehouseUrl(), portalConfig.getXpsChannelId(), "",
                    String.valueOf(portalUserInfo.getProjectId()), warehouseId, gongxiaoInboundOrderNo);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), cancelOrderResponse);
            logger.info("#traceId={}# [OUT][cancelInboundOrder] success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 关闭
     *
     * @param request
     * @param response
     * @param warehouseId            仓库id
     * @param gongxiaoInboundOrderNo 出库单号
     * @return
     */
    @ResponseBody
    @RequestMapping("/closeInboundOrder")
    public GongxiaoResult closeInboundOrder(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(defaultValue = "") String warehouseId,
                                            @RequestParam(defaultValue = "") String gongxiaoInboundOrderNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            logger.info("#traceId={}# [IN][closeInboundOrder] params: warehouseId = {}, gongxiaoInboundOrderNo={}", warehouseId, gongxiaoInboundOrderNo);
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            CloseOrderResponse closeOrderResponse = XpsWarehouseManager.closeInboundOrder(portalConfig.getWarehouseUrl(), portalConfig.getXpsChannelId(), "",
                    String.valueOf(portalUserInfo.getProjectId()), warehouseId, gongxiaoInboundOrderNo);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), closeOrderResponse);
            logger.info("#traceId={}# [OUT][closeInboundOrder] success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据入库通知单号查询基本信息
     *
     * @param request
     * @param response
     * @param gonxiaoInboundOrderNo 入库通知单号
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectInboundByInboundNum")
    public GongxiaoResult selectInboundByInboundNum(HttpServletRequest request, HttpServletResponse response,
                                                    @RequestParam(defaultValue = "") String gonxiaoInboundOrderNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            logger.info("#traceId={}# [IN][selectInboundByInboundNum] params: gonxiaoInboundOrderNo = {}", gonxiaoInboundOrderNo);
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            gongxiaoResult = XpsWarehouseManager.selectInboundByInboundNum(portalConfig.getWarehouseUrl(), portalConfig.getXpsChannelId(), "",
                    String.valueOf(portalUserInfo.getProjectId()), gonxiaoInboundOrderNo);
            logger.info("#traceId={}# [OUT][selectInboundByInboundNum] success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据入库通知单号查询入库单明细
     *
     * @param request
     * @param response
     * @param gonxiaoInboundOrderNo 入库通知单号
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectInboundOrderItem")
    public GongxiaoResult selectInboundOrderItem(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestParam(defaultValue = "XPS_shaver_POIN2018082014525300") String gonxiaoInboundOrderNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            logger.info("#traceId={}# [IN][selectInboundOrderItem] params: gonxiaoInboundOrderNo = {}", gonxiaoInboundOrderNo);
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            gongxiaoResult = XpsWarehouseManager.selectInboundOrderItem(portalConfig.getWarehouseUrl(), portalConfig.getXpsChannelId(), "",
                    String.valueOf(portalUserInfo.getProjectId()), gonxiaoInboundOrderNo);
            logger.info("#traceId={}# [OUT][selectInboundOrderItem] success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
