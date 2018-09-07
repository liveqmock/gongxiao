package com.yhglobal.gongxiao.phjd.foundation.storage;

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
 * 出库通知单
 */
@Controller
@RequestMapping("/storage/outstorenotice")
public class OutStoreNoticeController {
    private static Logger logger = LoggerFactory.getLogger(OutStoreNoticeController.class);
    @Autowired
    PortalConfig portalConfig;
    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 查询所有的出库单
     *
     * @param gongxiaoOutNo    出库单号
     * @param salseNo          销售单号
     * @param createTimeBeging 创建起始时间
     * @param createTimeLast   创建结束时间
     * @param warehouseId      仓库ID
     * @param productCode      商品编码
     * @param finishTimeBegin  创建结束时间起
     * @param finishTimeLast   创建结束时间终
     * @param supplier         供应商
     * @param customer         客户
     * @param pageNumber       页数
     * @param pageSize         页码
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectOutboundOrder")
    public GongxiaoResult selectOutboundOrder(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(defaultValue = "") String gongxiaoOutNo,
                                              @RequestParam(defaultValue = "") String salseNo,
                                              @RequestParam(defaultValue = "") String createTimeBeging,
                                              @RequestParam(defaultValue = "") String createTimeLast,
                                              @RequestParam(defaultValue = "") String warehouseId,
                                              @RequestParam(defaultValue = "") String productCode,
                                              @RequestParam(defaultValue = "") String finishTimeBegin,
                                              @RequestParam(defaultValue = "") String finishTimeLast,
                                              @RequestParam(defaultValue = "") String supplier,
                                              @RequestParam(defaultValue = "") String customer,
                                              @RequestParam(defaultValue = "1") int pageNumber,
                                              @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectOutboundOrder] params: inventoryNum={}, salseNum={}, createTimeBeging={}, createTimeLast={}," +
                            " warehouseId={}, productCode={}, finishTimeBegin={}, finishTimeLast={}, supplier={}, customer={}",
                    traceId, gongxiaoOutNo, salseNo, createTimeBeging, createTimeLast, warehouseId,
                    productCode, finishTimeBegin, finishTimeLast, supplier, customer);
            String channelToken = "474565353";
            gongxiaoResult = XpsWarehouseManager.selectOutboundOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, String.valueOf(portalUserInfo.getProjectId()), gongxiaoOutNo, salseNo, createTimeBeging, createTimeLast, warehouseId, productCode,
                    finishTimeBegin, finishTimeLast, supplier, customer, pageNumber, pageSize);
            logger.info("#traceId={}# [OUT] get selectOutboundOrder success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 通知wmx取消出库单
     *
     * @param warehouseId             仓库id
     * @param gongxiaoOutboundOrderNo 出库单号
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cancelOutboundOrder")
    public GongxiaoResult cancelOutboundOrder(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(defaultValue = "") String warehouseId,
                                              @RequestParam(defaultValue = "") String gongxiaoOutboundOrderNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][cancelOutboundOrder] params:  projectId={}，productCode={}, warehouseId={}，gongxiaoOutboundOrderNo={}",
                    traceId, warehouseId, gongxiaoOutboundOrderNo);
            String channelTocken = "43523645676";
            XpsWarehouseManager.cancelOutboundOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelTocken, String.valueOf(portalUserInfo.getProjectId()), warehouseId, gongxiaoOutboundOrderNo);
            logger.info("#traceId={}# [OUT] get notifyWmsCancelOutStock success", traceId);
            gongxiaoResult = new GongxiaoResult(0, "success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 通知wms关闭出库单
     *
     * @param warehouseId
     * @param gongxiaoOutboundOrderNo
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/closeOutboundOrder")
    public GongxiaoResult closeOutboundOrder(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "") String warehouseId,
                                             @RequestParam(defaultValue = "") String gongxiaoOutboundOrderNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            logger.info("#traceId={}# [IN][closeOutboundOrder] params:  projectId={}，warehouseId={}，gongxiaoOutboundOrderNo={}",
                    traceId, warehouseId, gongxiaoOutboundOrderNo);
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            String channelToken = "453764787";
            XpsWarehouseManager.closeOutboundOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken, String.valueOf(portalUserInfo.getProjectId()), warehouseId, gongxiaoOutboundOrderNo, String.valueOf(portalUserInfo.getProjectId()));
            logger.info("#traceId={}# [OUT] get closeOutboundOrder success", traceId);
            gongxiaoResult = new GongxiaoResult(0, "success");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 新增出库单
     *
     * @param warehouseId     仓库id
     * @param warehouseName   仓库名称
     * @param delieverAddress 发货地址
     * @param supplierName    供应商
     * @param businessDate    业务日期
     * @param outboundType    出库类型
     * @param remark          备注
     * @param itemsInfo       出库商品明细
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertOutboundOrder")
    public GongxiaoResult insertOutboundOrder(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(defaultValue = "") String warehouseId,
                                              @RequestParam(defaultValue = "") String warehouseName,
                                              @RequestParam(defaultValue = "") String delieverAddress,
                                              @RequestParam(defaultValue = "") String supplierName,
                                              @RequestParam(defaultValue = "") String businessDate,
                                              @RequestParam(defaultValue = "0") int outboundType,
                                              @RequestParam(defaultValue = "") String remark,
                                              @RequestParam(defaultValue = "") String itemsInfo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        if ((warehouseId.isEmpty() || warehouseId == null) || (warehouseName.isEmpty() || warehouseName == null)
                || (delieverAddress.isEmpty() || delieverAddress == null) || (supplierName.isEmpty() || supplierName == null)
                || (outboundType == 0) || (businessDate.isEmpty() || businessDate == null)) {
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), "请提交前填写必要的字段值!");
            return gongxiaoResult;
        }
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][insertOutboundOrder] params: projectId={},warehouseId={},warehouseName={},delieverAddress={},supplierName={},businessDate={},outboundType={},remark={},itemsInfo={} ",
                    traceId, warehouseId, warehouseName, delieverAddress, supplierName, businessDate, outboundType, remark, itemsInfo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String channelToken = "352365457";
            gongxiaoResult = XpsWarehouseManager.insertManualOutboundOrder(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(), channelToken, String.valueOf(portalUserInfo.getProjectId()), warehouseId, warehouseName, delieverAddress, supplierName, businessDate, outboundType, remark, itemsInfo);
            logger.info("#traceId={}# [OUT] insertOutboundOrder success: ", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult.setReturnCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
        }
        return gongxiaoResult;
    }

    /**
     * 根据出库单号查询出库单基本信息
     *
     * @param gongxiaoOutboundOrderNo 出库通知单号
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectOutboundOrderBasicInfo")
    public GongxiaoResult selectOutboundOrderBasicInfo(HttpServletRequest request, HttpServletResponse response,
                                                       @RequestParam(defaultValue = "") String gongxiaoOutboundOrderNo) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectOutboundOrderBasicInfo] params: projectId={},outboundOrderItemNo={}", traceId, String.valueOf(portalUserInfo.getProjectId()), gongxiaoOutboundOrderNo);
            String channelToken = "474565353";
            gongxiaoResult = XpsWarehouseManager.selectByOutboundNum(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, String.valueOf(portalUserInfo.getProjectId()), gongxiaoOutboundOrderNo);
            logger.info("#traceId={}# [OUT] get selectOutboundOrderBasicInfo success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据出库单号查询出库单明细
     *
     * @param gongxiaoOutboundOrderNo 出库通知单号
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectOutboundOrderDetailed")
    public GongxiaoResult selectOutboundOrderDetailed(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestParam(defaultValue = "") String gongxiaoOutboundOrderNo,
                                                      @RequestParam(defaultValue = "") String productCode,
                                                      @RequestParam(defaultValue = "0") int inventoryType) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectOutboundOrderDetailed] params: projectId={}, gongxiaoOutboundOrderNo={},productCode={},inventoryType={}",
                    traceId, String.valueOf(portalUserInfo.getProjectId()), gongxiaoOutboundOrderNo, productCode, inventoryType);
            String channelToken = "474565353";
            gongxiaoResult = XpsWarehouseManager.selectOutboundOrderItem(portalConfig.getWarehouseUrl(), WmsSourceChannel.CHANNEL_SHAVER.getChannelId(),
                    channelToken, String.valueOf(portalUserInfo.getProjectId()), gongxiaoOutboundOrderNo);
            logger.info("#traceId={}# [OUT] get selectOutboundOrderDetailed success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
