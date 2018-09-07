package com.yhglobal.gongxiao.distributor.account.controller;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.accountmanage.microservice.DistributorCouponTransferServiceGrpc;
import com.yhglobal.gongxiao.accountmanage.microservice.DistributorCouponTransferServiceStructure;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.distributor.purchase.controller.OrderController;
import com.yhglobal.gongxiao.distributor.util.DistributorPortalTraceIdGenerator;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.util.AccountProtoUtil;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
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

/**
 * AD账户Controller
 *
 * @author: 葛灿
 */
@Controller
@RequestMapping("/account")
public class AdDistributorAccountController {

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象

    /**
     * 查询现金余额
     *
     * @param currencyCode 货币编码
     * @return
     */
    @RequestMapping("/accountAmount")
    @ResponseBody
    public GongxiaoResult getDistributorCashAccountAmount(HttpServletRequest request, HttpServletResponse response,
                                                          @RequestParam(defaultValue = "CNY") String currencyCode) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getYhglobalReceivedAccount] params: currencyCode={}, projectId={}",
                    traceId, currencyCode);

            long projectId = portalUserInfo.getProjectId();
            long distributorId = portalUserInfo.getDistributorId();

            //rpc distributorAccount
            DistributorAccountServiceStructure.GetDistributorAccountAmountRequest getDistributorAccountAmountRequest = DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.newBuilder()
                    .setProjectId(projectId)
                    .setDistributorId(distributorId)
                    .setCurrencyCode(currencyCode)
                    .setRpcHeader(rpcHeader).build();
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub distributorAccountService = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            PaymentCommonGrpc.AccountAmountResponse rpcResponse = distributorAccountService.getDistributorAccountAmount(getDistributorAccountAmountRequest);
            AccountAmount yhglobalReceivedAccountAmount = AccountProtoUtil.getJavaAccountAmount(rpcResponse);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), yhglobalReceivedAccountAmount);
            logger.info("#traceId={}# [OUT] get account amount success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据项目ID和供应商ID查询现金流水
     *
     * @param accountType     账户类型 1:现金 2 返利 3 代垫
     * @param currencyCode    货币编码 (可选)
     * @param moneyFlow       资金流向 305支出 306收入 (可选)
     * @param beginDateString 查询开始时间(可选)
     * @param endDateString   查询截止时间(可选)
     * @param pageNumber      页码
     * @param pageSize        条数
     *
     * @return
     */
    @RequestMapping("/flowList")
    @ResponseBody
    public GongxiaoResult selectCashFlow(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(defaultValue = "CNY") String currencyCode,
                                         @RequestParam(defaultValue = "0") Integer moneyFlow,
                                         int accountType,
                                         @RequestParam(defaultValue = "") String beginDateString,
                                         @RequestParam(defaultValue = "") String endDateString,
                                         @RequestParam(defaultValue = "1") int pageNumber,
                                         @RequestParam(defaultValue = "50") int pageSize
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        portalUserInfo.setUserId("1");
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectCashFlow] params: currencyCode={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, moneyFlow, beginDateString, endDateString);
            // session中获取信息
            long projectId = portalUserInfo.getProjectId();
            long distributorId = portalUserInfo.getDistributorId();
            // 结果对象
            PageInfo<FrontPageFlow> list = null;
            //查询流水
            switch (accountType) {
                case 1:
                    //现金账户
                    DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub distributorAccountService = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
                    DistributorAccountServiceStructure.SelectDistributorFlowRqeust selectDistributorFlowRqeust = DistributorAccountServiceStructure.SelectDistributorFlowRqeust.newBuilder()
                            .setRpcHeader(rpcHeader)
                            .setCurrencyCode(currencyCode)
                            .setDistributorId(distributorId)
                            .setProjectId(projectId)
                            .setMoneyFlow(moneyFlow)
                            .setBeginDate(beginDateString)
                            .setEndDate(endDateString)
                            .setPageNum(pageNumber)
                            .setPageSize(pageSize).build();
                    PaymentCommonGrpc.FrontPageFlowPageInfo rpcPageInfo = distributorAccountService.selectCashFlow(selectDistributorFlowRqeust);
                    list = AccountProtoUtil.getJavaPageFlow(rpcPageInfo);
                    break;
                case 2:
                    //返利账户
//                    list = distributorCouponTransferService.selectCouponFlow(rpcHeader, currencyCode, distributorPortalUser.getDistributorId(), distributorPortalUser.getProjectId(), moneyFlow, beginDate, endDate, pageNumber, pageSize);
                    DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq.Builder reqBuilder = DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq.newBuilder();

                    reqBuilder.setRpcHeader(rpcHeader);
                    reqBuilder.setCurrencyCode(currencyCode);
                    reqBuilder.setProjectId(projectId);
                    if (moneyFlow == 0 || moneyFlow == null){
                        moneyFlow = -1;
                    }
                    reqBuilder.setMoneyFlow(moneyFlow);

                    Date beginDate = null;
                    Date endDate = null;
                    try {
                        beginDate = DateUtil.stringToDate(beginDateString);
                        endDate = DateUtil.stringToDate(endDateString);
                    }catch (Exception e){
                        logger.error("date format is wrong ");
                    }

                    long beginDateLong = beginDate==null?-1:beginDate.getTime();
                    reqBuilder.setBeginDate(beginDateLong);
                    long endDateLong = endDate==null?-1:endDate.getTime();
                    reqBuilder.setEndDate(endDateLong);
                    reqBuilder.setPageNumber(pageNumber);
                    reqBuilder.setPageSize(pageSize);
                    reqBuilder.setSupplierId(distributorId);// 此处grpc内定义的字段错误应为distributorId
                    // SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq req = reqBuilder.build();
                    DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq req  = reqBuilder.build();

                    //2. 获取该gRpc对应的stub 进行gRpc调用
                    DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub blockingStubStaticTest = RpcStubStore.getRpcStub(DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub.class);
                    PaymentCommonGrpc.FrontPageFlowPageInfo frontPageFlowPageInfo = blockingStubStaticTest.selectCouponFlowHasPage(req);
                    // 把返回的数据封装到返给前端的数据对象
                    List<PaymentCommonGrpc.FrontPageFlow> listGrpc = frontPageFlowPageInfo.getFlowsList();
                    List<FrontPageFlow> list1 = new ArrayList<>();
                    // 查询流水
                    for (PaymentCommonGrpc.FrontPageFlow frontPageFlow:listGrpc){
                        FrontPageFlow frontPageFlow1 = new FrontPageFlow();
                        frontPageFlow1.setFlowNo(frontPageFlow.getFlowNo());
                        frontPageFlow1.setType(frontPageFlow.getType());
                        //frontPageFlow1.setTypeStr(frontPageFlow.getTypeStr());
                        frontPageFlow1.setCurrencyCode(frontPageFlow.getCurrencyCode());
                        frontPageFlow1.setTransactionAmount(frontPageFlow.getTransactionAmount());
                        //frontPageFlow1.setTransactionAmountStr(frontPageFlow.getTransactionAmountStr());
                        frontPageFlow1.setAmountAfterTransaction(frontPageFlow.getAmountAfterTransaction());
                        //frontPageFlow1.setAmountAfterTransactionStr(frontPageFlow.getAmountAfterTransactionStr());
                        frontPageFlow1.setCreateTime(new Date(frontPageFlow.getCreateTime()));
                        frontPageFlow1.setCreateByName(frontPageFlow.getCreateByName());
                        //frontPageFlow1.setRemark(frontPageFlow.getRemark());
                        list1.add(frontPageFlow1);

                    }
                    list = new PageInfo<>(list1);
                    break;
                case 3:
                    //代垫账户
//                    list = distributorAccountService.selectPrepaidFlow(rpcHeader, currencyCode, distributorPortalUser.getDistributorId(), distributorPortalUser.getProjectId(), accountType, moneyFlow, beginDate, endDate, pageNumber, pageSize);
                    DistributorAccountServiceStructure.SelectPrepaidFlowRequest rpcReq = DistributorAccountServiceStructure.SelectPrepaidFlowRequest.newBuilder()
                            .setRpcHeader(rpcHeader)
                            .setDistributorId(distributorId)
                            .setProjectId(projectId)
                            .setMoneyFlow(moneyFlow)
                            .setCurrencyCode(currencyCode)
                            .setAccountType(accountType)
                            .setBeginDate(beginDateString)
                            .setEndDate(endDateString)
                            .setPageNum(pageNumber)
                            .setPageSize(pageSize)
                            .build();
                    DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
                    PaymentCommonGrpc.FrontPageFlowPageInfo rpcResult = blockingStub.selectPrepaidFlow(rpcReq);
                    list = AccountProtoUtil.getJavaPageFlow(rpcResult);

                    break;
                default:
                    list = null;
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] get flow list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 根据项目ID和供应商ID查询现金流水
     *
     * @param currencyCode    货币编码
     * @param moneyFlow       资金流向
     * @param beginDateString 查询开始时间
     * @param endDateString   查询截止时间
     * @return
     */
    @RequestMapping("/subtotal")
    @ResponseBody
    public GongxiaoResult selectCashFlow(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(defaultValue = "CNY") String currencyCode,
                                         @RequestParam(defaultValue = "0") Integer moneyFlow,
                                         int accountType,
                                         @RequestParam(defaultValue = "") String beginDateString,
                                         @RequestParam(defaultValue = "") String endDateString
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        portalUserInfo.setUserId("1");
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectCashFlow] params: currencyCode={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, moneyFlow, beginDateString, endDateString);
            // session中获取信息
            long projectId = portalUserInfo.getProjectId();
            long distributorId = portalUserInfo.getDistributorId();

            Date beginDate = null;
            Date endDate = null;
            try {
                beginDate = DateUtil.stringToDate(beginDateString);
                endDate = DateUtil.stringToDate(endDateString);
            }catch (Exception e){
                logger.error("date format is wrong ");
            }
            // 结果对象
            FrontPageFlowSubtotal frontPageFlowSubtotal = null;
            //查询流水
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub distributorAccountService;
            switch (accountType) {
                case 1:
                    //现金账户
                    distributorAccountService = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
                    DistributorAccountServiceStructure.GetDistributorSubtotalRequest getDistributorSubtotalRequest = DistributorAccountServiceStructure.GetDistributorSubtotalRequest.newBuilder()
                            .setRpcHeader(rpcHeader)
                            .setCurrencyCode(currencyCode)
                            .setDistributorId(distributorId)
                            .setProjectId(projectId)
                            .setMoneyFlow(moneyFlow)
                            .setBeginDate(beginDateString)
                            .setEndDate(endDateString).build();
                    PaymentCommonGrpc.FrontPageFlowSubtotal rpcResponse = distributorAccountService.getCashSubtotal(getDistributorSubtotalRequest);
                    frontPageFlowSubtotal = AccountProtoUtil.getJavaPageSubtotal(rpcResponse);
                    break;
                case 2:
                    //返利账户
//                    frontPageFlowSubtotal = distributorCouponTransferService.getSubtotal(rpcHeader, currencyCode, distributorPortalUser.getDistributorId(), distributorPortalUser.getProjectId(), accountType, moneyFlow, beginDate, endDate);
                    DistributorCouponTransferServiceStructure.GetSubtotalReq.Builder reqBuilder = DistributorCouponTransferServiceStructure.GetSubtotalReq.newBuilder();
                    reqBuilder.setRpcHeader(rpcHeader);
                    reqBuilder.setMoneyFlow(moneyFlow==null?-1:moneyFlow);
                    reqBuilder.setCurrencyCode(currencyCode);
                    reqBuilder.setSupplierId(distributorId);
                    reqBuilder.setProjectId(projectId);

                    reqBuilder.setBeginDate(beginDate==null?-1:beginDate.getTime());
                    reqBuilder.setEndDate(endDate==null?-1:endDate.getTime());

                    DistributorCouponTransferServiceStructure.GetSubtotalReq req = reqBuilder.build();
                    //2. 获取该gRpc对应的stub
                    DistributorCouponTransferServiceStructure.GetSubtotalResp resp;
                    DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub blockingStubStatic = RpcStubStore.getRpcStub(DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub.class);
                    //3. 进行gRpc调用
                    resp = blockingStubStatic.getSubtotal(req);
                    frontPageFlowSubtotal.setExpenditureAmount(resp.getExpenditureAmount());
                    frontPageFlowSubtotal.setExpenditureQuantity(resp.getExpenditureQuantity());
                    frontPageFlowSubtotal.setIncomeAmount(resp.getIncomeAmount());
                    frontPageFlowSubtotal.setIncomeQuantity(resp.getIncomeQuantity());
                    break;
                case 3:
                    //代垫账户
//                    frontPageFlowSubtotal = distributorAccountService.getPrepaidSubtotal(rpcHeader, currencyCode, distributorPortalUser.getDistributorId(), distributorPortalUser.getProjectId(), moneyFlow, beginDate, endDate);
                    DistributorAccountServiceStructure.GetPrepaidSubtotalRequest rpcReq = DistributorAccountServiceStructure.GetPrepaidSubtotalRequest.newBuilder()
                            .setRpcHeader(rpcHeader)
                            .setDistributorId(distributorId)
                            .setProjectId(projectId)
                            .setMoneyFlow(moneyFlow)
                            .setCurrencyCode(currencyCode)
                            .setBeginDate(beginDateString)
                            .setEndDate(endDateString)
                            .build();
                    DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
                    PaymentCommonGrpc.FrontPageFlowSubtotal rpcResult = blockingStub.getPrepaidSubtotal(rpcReq);
                    frontPageFlowSubtotal = AccountProtoUtil.getJavaPageSubtotal(rpcResult);
                    break;
                default:
                    frontPageFlowSubtotal = null;
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), frontPageFlowSubtotal);
            logger.info("#traceId={}# [OUT] get flow list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
