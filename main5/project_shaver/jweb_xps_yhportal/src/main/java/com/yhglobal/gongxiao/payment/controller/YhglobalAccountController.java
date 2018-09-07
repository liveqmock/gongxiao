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
import com.yhglobal.gongxiao.payment.entity.AccountAmount;
import com.yhglobal.gongxiao.payment.entity.DistributorCouponAccount;
import com.yhglobal.gongxiao.payment.entity.FrontPageFlow;
import com.yhglobal.gongxiao.payment.entity.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure;
import com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure;
import com.yhglobal.gongxiao.payment.util.AccountProtoUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.yhglobal.gongxiao.constant.ErrorCode.ARGUMENTS_INVALID;

/**
 * YH账户管理
 * 页面位于账户管理-越海账户管理
 *
 * @author: 葛灿
 */
@Controller
@RequestMapping("/payment/yhglobal")
public class YhglobalAccountController {

    private static Logger logger = LoggerFactory.getLogger(YhglobalAccountController.class);
    @Autowired
    private PortalConfig portalConfig;
    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象


    /**
     * 查询实收账户余额
     *
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    @RequestMapping("/receivedAccount")
    @ResponseBody
    public GongxiaoResult getYhglobalReceivedAccount(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(defaultValue = "CNY") String currencyCode,
                                                     long projectId) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getYhglobalReceivedAccount] params: currencyCode={}, projectId={}",
                    traceId, currencyCode, projectId);
            YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest rpcRequest = YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest.newBuilder()
                    .setProjectId(projectId)
                    .setCurrencyCode(currencyCode)
                    .build();
            YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub stub = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
            PaymentCommonGrpc.AccountAmountResponse rpcResponse = stub.getYhglobalReceivedAccountAmount(rpcRequest);
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
     * 查询ad过账账户余额
     *
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    @RequestMapping("/bufferAccount")
    @ResponseBody
    public GongxiaoResult getDistributorBufferAccount(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestParam(defaultValue = "CNY") String currencyCode,
                                                      long projectId) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getDistributorBufferAccount] params: currencyCode={}, projectId={}",
                    traceId, currencyCode, projectId);
            SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest rpcRequest = SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setCurrencyCode(currencyCode)
                    .build();
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            PaymentCommonGrpc.AccountAmountResponse rpcResponse = rpcStub.getSupplierCouponBufferToDistributor(rpcRequest);
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
     * 查询返利账户list
     *
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    @RequestMapping("/couponAccountList")
    @ResponseBody
    public GongxiaoResult getCouponAccountList(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam(defaultValue = "CNY") String currencyCode,
                                               long projectId,
                                               @RequestParam(defaultValue = "1") int pageNumber,
                                               @RequestParam(defaultValue = "5") int pageSize

    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getCouponAccountList] params: currencyCode={}, projectId={} ",
                    traceId, currencyCode, projectId);
            DistributorAccountServiceStructure.SelectDistributorAccountListRequest rpcRequest = DistributorAccountServiceStructure.SelectDistributorAccountListRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode(currencyCode)
                    .setProjectId(projectId)
                    .setPageNum(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            DistributorAccountServiceStructure.DistributorAccountListResponse rpcResponse = rpcStub.selectDistributorCouponAccountList(rpcRequest);
            PageInfo<DistributorCouponAccount> distributorCouponAccounts = new PageInfo<>();
            distributorCouponAccounts.setPageNum(rpcResponse.getPageNum());
            distributorCouponAccounts.setPageSize(rpcResponse.getPageSize());
            distributorCouponAccounts.setTotal(rpcResponse.getTotal());
            ArrayList<DistributorCouponAccount> list = new ArrayList<>();
            for (DistributorAccountServiceStructure.DistributorAccount protoModel : rpcResponse.getListList()) {
                DistributorCouponAccount javaModel = new DistributorCouponAccount();
                javaModel.setProjectId(protoModel.getProjectId());
                javaModel.setDistributorId(protoModel.getDistributorId());
                javaModel.setDistributorName(protoModel.getDistributorName());
                javaModel.setTransferAmountDouble(protoModel.getTransferAmountDouble());
                list.add(javaModel);
            }
            distributorCouponAccounts.setList(list);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), distributorCouponAccounts);
            logger.info("#traceId={}# [OUT] get account list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 查询返利账户list
     *
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    @RequestMapping("/prepaidAccountList")
    @ResponseBody
    public GongxiaoResult getPrepaidAccountList(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(defaultValue = "CNY") String currencyCode,
                                                long projectId,
                                                @RequestParam(defaultValue = "1") int pageNumber,
                                                @RequestParam(defaultValue = "5") int pageSize

    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getPrepaidAccountList] params: currencyCode={}, projectId={} ",
                    traceId, currencyCode, projectId);
            DistributorAccountServiceStructure.SelectDistributorAccountListRequest rpcRequest = DistributorAccountServiceStructure.SelectDistributorAccountListRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode(currencyCode)
                    .setProjectId(projectId)
                    .setPageNum(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            DistributorAccountServiceStructure.DistributorAccountListResponse rpcResponse = rpcStub.selectDistributorPrepaidAccountList(rpcRequest);
            PageInfo<DistributorCouponAccount> distributorCouponAccounts = new PageInfo<>();
            distributorCouponAccounts.setPageNum(rpcResponse.getPageNum());
            distributorCouponAccounts.setPageSize(rpcResponse.getPageSize());
            distributorCouponAccounts.setTotal(rpcResponse.getTotal());
            ArrayList<DistributorCouponAccount> list = new ArrayList<>();
            for (DistributorAccountServiceStructure.DistributorAccount protoModel : rpcResponse.getListList()) {
                DistributorCouponAccount javaModel = new DistributorCouponAccount();
                javaModel.setProjectId(protoModel.getProjectId());
                javaModel.setDistributorId(protoModel.getDistributorId());
                javaModel.setDistributorName(protoModel.getDistributorName());
                javaModel.setTransferAmountDouble(protoModel.getTransferAmountDouble());
                list.add(javaModel);
            }
            distributorCouponAccounts.setList(list);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), distributorCouponAccounts);
            logger.info("#traceId={}# [OUT] get account list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 发放返利
     *
     * @param accounts 分配详情
     * @return
     */
    @RequestMapping("/transCoupon")
    @ResponseBody
    public GongxiaoResult transCoupon(HttpServletRequest request, HttpServletResponse response,
                                      String accounts, long projectId
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][transCoupon] params: distributorCouponAccounts={}",
                    traceId, accounts);
            DistributorAccountServiceStructure.DepositReceivedAccountsRequest.Builder builder = DistributorAccountServiceStructure.DepositReceivedAccountsRequest.newBuilder();
            builder.setRpcHeader(rpcHeader);
            builder.setProjectId(projectId);
            List<DistributorCouponAccount> distributorCouponAccounts = JSON.parseArray(accounts, DistributorCouponAccount.class);
            for (DistributorCouponAccount javaModel : distributorCouponAccounts) {
                if (javaModel.getTransferAmountDouble() <= 0) {
                    return new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                }
                DistributorAccountServiceStructure.DistributorAccount account = DistributorAccountServiceStructure.DistributorAccount.newBuilder()
                        .setProjectId(javaModel.getProjectId())
                        .setDistributorId(javaModel.getDistributorId())
                        .setDistributorName(javaModel.getDistributorName())
                        .setTransferAmountDouble(javaModel.getTransferAmountDouble())
                        .build();
                builder.addList(account);
            }
            DistributorAccountServiceStructure.DepositReceivedAccountsRequest rpcRequest = builder.build();
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.depositCouponReceivedAccounts(rpcRequest);
            gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
            logger.info("#traceId={}# [OUT] trans coupon success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 发放代垫
     *
     * @param accounts 分配详情
     * @return
     */
    @RequestMapping("/transPrepaid")
    @ResponseBody
    public GongxiaoResult transPrepaid(HttpServletRequest request, HttpServletResponse response,
                                       String accounts, long projectId
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][transPrepaid] params: distributorPrepaidAccounts={}",
                    traceId, accounts);
            DistributorAccountServiceStructure.DepositReceivedAccountsRequest.Builder builder = DistributorAccountServiceStructure.DepositReceivedAccountsRequest.newBuilder();
            builder.setRpcHeader(rpcHeader);
            builder.setProjectId(projectId);
            List<DistributorCouponAccount> distributorCouponAccounts = JSON.parseArray(accounts, DistributorCouponAccount.class);
            for (DistributorCouponAccount javaModel : distributorCouponAccounts) {
                if (javaModel.getTransferAmountDouble() <= 0) {
                    return new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                }
                DistributorAccountServiceStructure.DistributorAccount account = DistributorAccountServiceStructure.DistributorAccount.newBuilder()
                        .setProjectId(javaModel.getProjectId())
                        .setDistributorId(javaModel.getDistributorId())
                        .setDistributorName(javaModel.getDistributorName())
                        .setTransferAmountDouble(javaModel.getTransferAmountDouble())
                        .build();
                builder.addList(account);
            }
            DistributorAccountServiceStructure.DepositReceivedAccountsRequest rpcRequest = builder.build();
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.depositPrepaidReceivedAccounts(rpcRequest);
            gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
            logger.info("#traceId={}# [OUT] trans prepaid success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 按类型查询账户记录
     * <p>
     * accountType 对应账户类型
     * 查询流水的账户类型 1：返利实收账户 2：代垫实收账户 3：返利过账账户 4：代垫过账账户
     *
     * @param currencyCode    货币编码
     * @param projectId       项目id
     * @param accountType     查询流水的账户类型 1：返利实收账户 2：代垫实收账户 3：返利过账账户 4：代垫过账账户
     * @param moneyFlow       资金流向
     * @param beginDateString 日期开始
     * @param endDateString   日期结束
     * @return
     */
    @RequestMapping("/flowList")
    @ResponseBody
    public GongxiaoResult selectFlowList(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(defaultValue = "CNY") String currencyCode,
                                         int accountType,
                                         long projectId,
                                         @RequestParam(defaultValue = "0") Integer moneyFlow,
                                         @RequestParam(defaultValue = "") String beginDateString,
                                         @RequestParam(defaultValue = "") String endDateString,
                                         @RequestParam(defaultValue = "1") int pageNumber,
                                         @RequestParam(defaultValue = "50") int pageSize
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectFlowList] params: currencyCode={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, projectId, moneyFlow, beginDateString, endDateString);
            PageInfo<FrontPageFlow> list;
            PaymentCommonGrpc.FrontPageFlowPageInfo rpcPageInfo;
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub supplierStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalStub = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
            SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest supplierRequest = SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode(currencyCode)
                    .setProjectId(projectId)
                    .setMoneyFlow(moneyFlow)
                    .setBeginDate(beginDateString)
                    .setEndDate(endDateString)
                    .setPageNum(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest yhglobalRequest = YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode(currencyCode)
                    .setProjectId(projectId)
                    .setMoneyFlow(moneyFlow)
                    .setBeginDate(beginDateString)
                    .setEndDate(endDateString)
                    .setPageNum(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            //查询流水 1：返利实收账户 2：代垫实收账户 3：返利过账账户 4：代垫过账账户
            switch (accountType) {
                case 1:
                    //返利实收账户
                    rpcPageInfo = yhglobalStub.selectCouponReceivedRecords(yhglobalRequest);
                    list = AccountProtoUtil.getJavaPageFlow(rpcPageInfo);
                    break;
                case 2:
                    //代垫实收账户
                    rpcPageInfo = yhglobalStub.selectPrepaidReceivedRecords(yhglobalRequest);
                    list = AccountProtoUtil.getJavaPageFlow(rpcPageInfo);
                    break;
                case 3:
                    //返利过账账户
                    rpcPageInfo = supplierStub.selectBufferCouponFlowList(supplierRequest);
                    list = AccountProtoUtil.getJavaPageFlow(rpcPageInfo);
                    break;
                case 4:
                    //代垫过账账户
                    rpcPageInfo = supplierStub.selectBufferPrepaidFlowList(supplierRequest);
                    list = AccountProtoUtil.getJavaPageFlow(rpcPageInfo);
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
     * 查询返利实收账户记录
     * <p>
     * accountType 对应账户类型
     * 查询小计的账户类型 1：返利实收账户 2：代垫实收账户 3：返利过账账户 4：代垫过账账户
     *
     * @param currencyCode    货币编码
     * @param projectId       项目id
     * @param accountType     查询流水的账户类型 1：返利实收账户 2：代垫实收账户 3：返利过账账户 4：代垫过账账户
     * @param moneyFlow       资金流向
     * @param beginDateString 日期开始
     * @param endDateString   日期结束
     * @return
     */
    @RequestMapping("/flowSubtotal")
    @ResponseBody
    public GongxiaoResult getFlowSubtotal(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(defaultValue = "CNY") String currencyCode,
                                          int accountType,
                                          long projectId,
                                          @RequestParam(defaultValue = "0") Integer moneyFlow,
                                          @RequestParam(defaultValue = "") String beginDateString,
                                          @RequestParam(defaultValue = "") String endDateString
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getFlowSubtotal] params: currencyCode={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, projectId, moneyFlow, beginDateString, endDateString);
            FrontPageFlowSubtotal couponSubtotal;
            PaymentCommonGrpc.FrontPageFlowSubtotal rpcSubtotal;
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub supplierStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalStub = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
            SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest supplierRequest = SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode(currencyCode)
                    .setProjectId(projectId)
                    .setMoneyFlow(moneyFlow)
                    .setBeginDate(beginDateString)
                    .setEndDate(endDateString)
                    .build();
            YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest yhglobalRequest = YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode(currencyCode)
                    .setProjectId(projectId)
                    .setMoneyFlow(moneyFlow)
                    .setBeginDate(beginDateString)
                    .setEndDate(endDateString)
                    .build();
            //查询流水 1：返利实收账户 2：代垫实收账户 3：返利过账账户 4：代垫过账账户
            switch (accountType) {
                case 1:
                    //返利实收账户
                    rpcSubtotal = yhglobalStub.getCouponSubtotal(yhglobalRequest);
                    couponSubtotal = AccountProtoUtil.getJavaPageSubtotal(rpcSubtotal);
                    break;
                case 2:
                    //代垫实收账户
                    rpcSubtotal = yhglobalStub.getPrepaidSubtotal(yhglobalRequest);
                    couponSubtotal = AccountProtoUtil.getJavaPageSubtotal(rpcSubtotal);
                    break;
                case 3:
                    //返利过账账户
                    rpcSubtotal = supplierStub.getCouponBufferToDistributorSubtotal(supplierRequest);
                    couponSubtotal = AccountProtoUtil.getJavaPageSubtotal(rpcSubtotal);
                    break;
                case 4:
                    //代垫过账账户
                    rpcSubtotal = supplierStub.getPrepaidBufferToDistributorSubtotal(supplierRequest);
                    couponSubtotal = AccountProtoUtil.getJavaPageSubtotal(rpcSubtotal);
                    break;
                default:
                    couponSubtotal = null;
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), couponSubtotal);
            logger.info("#traceId={}# [OUT] get flow subtotal success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 实收账户转出返利
     * <p>
     *
     * @param projectId 项目id
     * @param amount    转出金额
     * @return
     */
    @RequestMapping("/transReceivedCoupon")
    @ResponseBody
    public GongxiaoResult transferCoupon(HttpServletRequest request, HttpServletResponse response,
                                         long projectId, double amount,
                                         @RequestParam(defaultValue = "") String reamark) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            if (amount <= 0) {
                return new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
            }
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][transferCoupon] params: projectId={}, amount={}",
                    traceId, projectId, amount);
            YhglobalAccountServiceStructure.TransferReceivedAccountRequest rpcRequest = YhglobalAccountServiceStructure.TransferReceivedAccountRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setAmountDouble(amount)
                    .setRemark(reamark)
                    .build();
            YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResult = rpcStub.transferYhglobalReceivedCoupon(rpcRequest);
            gongxiaoResult = new GongxiaoResult(rpcResult.getReturnCode(), rpcResult.getMsg());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 实收账户转出返利
     * <p>
     *
     * @param projectId 项目id
     * @param amount    转出金额
     * @return
     */
    @RequestMapping("/transReceivedPrepaid")
    @ResponseBody
    public GongxiaoResult transferPrepaid(HttpServletRequest request, HttpServletResponse response,
                                          long projectId, double amount,
                                          @RequestParam(defaultValue = "") String reamark) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            if (amount <= 0) {
                return new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
            }
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][transferPrepaid] params: projectId={}, amount={}",
                    traceId, projectId, amount);
            YhglobalAccountServiceStructure.TransferReceivedAccountRequest rpcRequest = YhglobalAccountServiceStructure.TransferReceivedAccountRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setAmountDouble(amount)
                    .setRemark(reamark)
                    .build();
            YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResult = rpcStub.transferYhglobalReceivedPrepaid(rpcRequest);
            gongxiaoResult = new GongxiaoResult(rpcResult.getReturnCode(), rpcResult.getMsg());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
