package com.yhglobal.gongxiao.payment.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.entity.CashConfirm;
import com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure;
import com.yhglobal.gongxiao.payment.microservice.CashConfirmServiceGrpc;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yhglobal.gongxiao.constant.ErrorCode.ARGUMENTS_INVALID;

/**
 * 收款确认
 *
 * @author: 葛灿
 */
@Controller
@RequestMapping("/payment/cashConfirm")
public class CashConfirmController {

    private static Logger logger = LoggerFactory.getLogger(CashConfirmController.class);
    @Autowired
    private PortalConfig portalConfig;
    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象

    /**
     * 选择性查询(均为可选条件)
     *
     * @param salesOrderNo    销售单号
     * @param distributorName ad名称
     * @param bankName        收款银行
     * @param confirmBegin    确认时间开始
     * @param confirmEnd      确认时间截止
     * @param orderBegin      订单时间起始
     * @param orderEnd        订单时间截止
     * @param orderStatus     订单状态
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public GongxiaoResult selectList(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(defaultValue = "") String salesOrderNo,
                                     @RequestParam(defaultValue = "") String distributorName,
                                     @RequestParam(defaultValue = "") String bankName,
                                     @RequestParam(defaultValue = "") String orderBegin,
                                     @RequestParam(defaultValue = "") String orderEnd,
                                     @RequestParam(defaultValue = "") String confirmBegin,
                                     @RequestParam(defaultValue = "") String confirmEnd,
                                     @RequestParam(defaultValue = "") String orderStatus,
                                     int pageNumber,
                                     int pageSize) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            // 从session中获取projectId
            long projectId = portalUserInfo.getProjectId();

            CashConfirmSerivceStructure.SelectListSelectiveRequest rpcRequest = CashConfirmSerivceStructure.SelectListSelectiveRequest.newBuilder()
                    .setSalesOrderNo(salesOrderNo)
                    .setProjectId(projectId)
                    .setDistributorName(distributorName)
                    .setBankName(bankName)
                    .setOrderBegin(orderBegin)
                    .setOrderEnd(orderEnd)
                    .setConfirmBegin(confirmBegin)
                    .setConfirmEnd(confirmEnd)
                    .setOrderStatus(orderStatus)
                    .setPageNum(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectList] params: salesOrderNo={}, projectId={}, distributorName={}, bankName={}, orderBegin={}, orderEnd={}, confirmBegin={}, confirmEnd={}, orderStatus={}",
                    traceId, salesOrderNo, projectId, distributorName, bankName, orderBegin, orderEnd, confirmBegin, confirmEnd, orderStatus);
            CashConfirmServiceGrpc.CashConfirmServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(CashConfirmServiceGrpc.CashConfirmServiceBlockingStub.class);
            CashConfirmSerivceStructure.SelectListSelectiveResponse rpcResponse = rpcStub.selectListSelective(rpcRequest);
            List<CashConfirmSerivceStructure.CashConfirmSelectiveResponse> protoList = rpcResponse.getListList();
            List<CashConfirm> javaList = new ArrayList<>();
            for (CashConfirmSerivceStructure.CashConfirmSelectiveResponse protoObject : protoList) {
                CashConfirm javaObject = new CashConfirm();
                javaObject.setConfirmId(protoObject.getConfirmId());
                javaObject.setSalesOrderNo(protoObject.getSalesOrderNo());
                javaObject.setFlowNo(protoObject.getFlowNo());
                javaObject.setRecipientStatus(protoObject.getRecipientStatus());
                javaObject.setConfirmStatus(protoObject.getConfirmStatus());
                javaObject.setDistributorName(protoObject.getDistributorName());
                javaObject.setShouldReceiveAmountDouble(protoObject.getShouldReceiveAmountDouble());
                javaObject.setUnreceiveAmountDouble(protoObject.getUnreceiveAmountDouble());
                javaObject.setConfirmAmountDouble(protoObject.getConfirmAmountDouble());
                javaObject.setRecipientAmountDouble(protoObject.getRecipientAmountDouble());
                javaObject.setConfirmTime(GrpcCommonUtil.getJavaParam(protoObject.getConfirmTime()));
                javaObject.setReceiveTime(GrpcCommonUtil.getJavaParam(protoObject.getReceiveTime()));
                javaObject.setOrderTime(GrpcCommonUtil.getJavaParam(protoObject.getOrderTime()));
                javaObject.setBankName(protoObject.getBankName());
                javaObject.setBankFlowNo(protoObject.getBankFlowNo());
                javaList.add(javaObject);
            }

            PageInfo<CashConfirm> salesCashConfirmPageInfo =
                    new PageInfo(javaList);
            salesCashConfirmPageInfo.setTotal(rpcResponse.getTotal());
            salesCashConfirmPageInfo.setPageSize(rpcResponse.getPageSize());
            salesCashConfirmPageInfo.setPageNum(rpcResponse.getPageNum());
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), salesCashConfirmPageInfo);
            logger.info("#traceId={}# [OUT] get list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 确认收款页面，展示可确认的列表
     *
     * @param salesOrderNoList 销售单号数组
     * @return
     */
    @RequestMapping("/confirmList")
    @ResponseBody
    public GongxiaoResult selectConfirmList(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(required = true) String[] salesOrderNoList) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            // 从session中获取projectId
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectConfirmList] params: salesOrderNo={}, projectId={}, distributorId={}, orderBegin={}, orderEnd={}, orderStatus={}, pageNumber={}, pageSize={} ",
                    traceId);
            CashConfirmSerivceStructure.SelectConfirmListRequest.Builder builder = CashConfirmSerivceStructure.SelectConfirmListRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader);
            for (String salesOrderNo : salesOrderNoList) {
                builder.addSalesOrderNoList(salesOrderNo);
            }
            CashConfirmSerivceStructure.SelectConfirmListRequest rpcRequest = builder.build();
            CashConfirmServiceGrpc.CashConfirmServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(CashConfirmServiceGrpc.CashConfirmServiceBlockingStub.class);
            CashConfirmSerivceStructure.SelectConfirmListResponse rprResponse = rpcStub.selectConfirmList(rpcRequest);
            ArrayList<CashConfirm> list = new ArrayList<>();
            for (CashConfirmSerivceStructure.CashConfirmResponse protoObject : rprResponse.getListList()) {
                CashConfirm javaObject = new CashConfirm();
                javaObject.setConfirmId(protoObject.getConfirmId());
                javaObject.setConfirmStatus(protoObject.getConfirmStatus());
                javaObject.setSalesOrderNo(protoObject.getSalesOrderNo());
                javaObject.setShouldReceiveAmountDouble(protoObject.getShouldReceiveAmountDouble());
                javaObject.setUnreceiveAmountDouble(protoObject.getUnreceiveAmountDouble());
                javaObject.setConfirmAmountDouble(protoObject.getConfirmAmountDouble());
                javaObject.setRecipientAmountDouble(protoObject.getRecipientAmountDouble());
                javaObject.setConfirmCurrency(protoObject.getCurrencyCode());
                javaObject.setRecipientCurrency(protoObject.getCurrencyCode());
                javaObject.setUnreceiveCurrency(protoObject.getCurrencyCode());
                javaObject.setShouldReceiveCurrency(protoObject.getCurrencyCode());
                javaObject.setDataVersion(protoObject.getDataVersion());
                list.add(javaObject);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] get confirm list success.");
        } catch (TooManyResultsException | NullPointerException e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.CASH_DATA_ALREADY_MODIFIED);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 确认收款
     *
     * @param bankName            收款银行
     * @param recipientDateString 收款日期
     * @param itemsJson           确认收款详情
     * @param bankFlowNo          银行流水号(选填)
     * @param clientName          客户名称
     * @param remark              备注(选填)
     * @return
     */
    @RequestMapping("/confirm")
    @ResponseBody
    public GongxiaoResult confirmCash(HttpServletRequest request, HttpServletResponse response,
                                      String bankName, String recipientDateString, String itemsJson,
                                      String bankFlowNo, String clientName, String remark) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            // 从session中获取projectId
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][confirmCash] params:  bankName={}, recipientDate={}, itemsJson={}",
                    traceId, bankName, recipientDateString, itemsJson);
            // 没有填写收款银行
            if (StringUtils.isEmpty(bankName)) {
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ARGUMENTS_INVALID);
                return gongxiaoResult;
            }
            // 没有填写收款日期
            if (StringUtils.isEmpty(recipientDateString)) {
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ARGUMENTS_INVALID);
                return gongxiaoResult;
            }
            // 没有填写客户名称
            if (StringUtils.isEmpty(clientName)) {
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ARGUMENTS_INVALID);
                return gongxiaoResult;
            }
            List<CashConfirm> cashConfirms = JSON.parseArray(itemsJson, CashConfirm.class);
            for (CashConfirm cashConfirm : cashConfirms) {
                double confirmAmountDouble = Double.parseDouble(cashConfirm.getConfirmAmountDouble());
                if (confirmAmountDouble == 0) {
                    gongxiaoResult = new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                    return gongxiaoResult;
                }
            }
            Date recipientDate = DateUtils.parseDate(recipientDateString, "yyyy-MM-dd");
            CashConfirmSerivceStructure.ConfirmCashRequest.Builder builder = CashConfirmSerivceStructure.ConfirmCashRequest.newBuilder();
            builder.setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setBankName(bankName)
                    .setRecipientDate(recipientDate.getTime())
                    .setBankFlowNo(GrpcCommonUtil.getProtoParam(bankFlowNo))
                    .setClientName(clientName)
                    .setRemark(GrpcCommonUtil.getProtoParam(remark))
            ;
            for (CashConfirm cashConfirm : cashConfirms) {
                CashConfirmSerivceStructure.CashConfirmItems protoObject =
                        CashConfirmSerivceStructure.CashConfirmItems.newBuilder()
                                .setConfirmId(cashConfirm.getConfirmId())
                                .setConfirmStatus(cashConfirm.getConfirmStatus())
                                .setSalesOrderNo(cashConfirm.getSalesOrderNo())
                                .setConfirmAmountDouble(cashConfirm.getConfirmAmountDouble())
                                .setRecipientAmountDouble(cashConfirm.getRecipientAmountDouble())
                                .setDataVersion(cashConfirm.getDataVersion())
                                .build();
                builder.addItems(protoObject);
            }
            CashConfirmSerivceStructure.ConfirmCashRequest rpcRequest = builder.build();
            CashConfirmServiceGrpc.CashConfirmServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(CashConfirmServiceGrpc.CashConfirmServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.confirmCash(rpcRequest);
            gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
            logger.info("#traceId={}# [OUT] confirm success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
