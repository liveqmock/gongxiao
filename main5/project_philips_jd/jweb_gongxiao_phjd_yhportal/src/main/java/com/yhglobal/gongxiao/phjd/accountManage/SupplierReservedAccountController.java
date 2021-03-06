package com.yhglobal.gongxiao.phjd.accountManage;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.ErrorCodeJd;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.microservice.*;
import com.yhglobal.gongxiao.phjd.base.model.FlowTypeEnum;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.bean.AccountAmount;
import com.yhglobal.gongxiao.phjd.bean.FrontPageFlow;
import com.yhglobal.gongxiao.phjd.bean.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
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
import java.util.Date;
import java.util.List;

/**
 *  供应商冻结账户的接口
 * @author 王帅
 */
@Controller
@RequestMapping("/supplier/reserved")
public class SupplierReservedAccountController {

    private static Logger logger = LoggerFactory.getLogger(SupplierReservedAccountController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    // 查询单价折扣账户 和 采购预留账户余额 销售差价余额
    @RequestMapping("/accountAmount")
    @ResponseBody
    public GongxiaoResult getFrozenAccountAmount(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestParam(defaultValue = "CNY") String currencyCode,
                                                 Long projectId){


        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage() + ": projectId is null");
        }
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getFrozenAccountAmount] params: projectId={}", traceId, projectId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

            SupplierReservedAccountServiceStructure.GetReservedAccountAmountReq.Builder reqBuilder = SupplierReservedAccountServiceStructure.GetReservedAccountAmountReq.newBuilder();
            reqBuilder.setRpcHeader(rpcHeader);
            reqBuilder.setProjectId(projectId);

            SupplierReservedAccountServiceGrpc.SupplierReservedAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub( SupplierReservedAccountServiceGrpc.SupplierReservedAccountServiceBlockingStub.class);
            SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp reservedAccountAmount = blockingStub.getReservedAccountAmount(reqBuilder.build());
            AccountAmount accountAmount = new AccountAmount();
            accountAmount.setReservedDiscountAccount(reservedAccountAmount.getDiscountAccount());
            accountAmount.setReservedPurchaseAccount(reservedAccountAmount.getReservedAccount());
            accountAmount.setReservedSalesDifferenceAccount(reservedAccountAmount.getSalesDifference());
            gongxiaoResult = new GongxiaoResult(ErrorCodeJd.SUCCESS.getErrorCode(), accountAmount);

            return gongxiaoResult;
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }



    }

    // 条件查询账户流水
    @RequestMapping("/flow")
    @ResponseBody
    public GongxiaoResult getAccountFlow(HttpServletRequest request, HttpServletResponse response,
                                         String dateStartStr,
                                         String dateEndStr,String accountType, String flowType,
                                         Long projectId, Integer pageNumber, Integer pageSize){
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage() + ": projectId is null");
        }
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        Date dateS = null;
        Date dateE = null;
        try {
            dateS = DateUtil.stringToDate(dateStartStr);
            dateE = DateUtil.stringToDate(dateEndStr);
        }catch (Exception e){
            //返回日期格式错误
            return new GongxiaoResult(ErrorCodeJd.DATE_FORMAT_WRONG.getErrorCode(), ErrorCodeJd.DATE_FORMAT_WRONG.getMessage());
        }
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getFrozenAccountAmount] params: projectId={}", traceId, projectId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

            SupplierReservedAccountServiceStructure.GetReservedAccountFlowReq.Builder reqBuilder = SupplierReservedAccountServiceStructure.GetReservedAccountFlowReq.newBuilder();
            reqBuilder.setRpcHeader(rpcHeader);
            reqBuilder.setProjectId(projectId);
            reqBuilder.setDateS(dateS == null?-1:dateS.getTime());
            reqBuilder.setDateE(dateE == null?-1:dateE.getTime());
            reqBuilder.setAccountType(accountType);
            reqBuilder.setFlowType(flowType==null?"":flowType);
            reqBuilder.setPageNum(pageNumber);
            reqBuilder.setPageSize(pageSize);

            SupplierReservedAccountServiceGrpc.SupplierReservedAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub( SupplierReservedAccountServiceGrpc.SupplierReservedAccountServiceBlockingStub.class);
            SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp resp = blockingStub.getReservedAccountFlow(reqBuilder.build());
            List<PaymentCommonGrpc.FrontPageFlow> flowListList = resp.getFlowListList();
            List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
            for (PaymentCommonGrpc.FrontPageFlow frontPageFlow:flowListList){
                FrontPageFlow flow = new FrontPageFlow();
                flow.setCreateTime(new Date(frontPageFlow.getCreateTime()));
                flow.setAmountAfterTransaction(frontPageFlow.getAmountAfterTransaction());
                flow.setCreateByName(frontPageFlow.getCreateByName());
                flow.setCurrencyCode(frontPageFlow.getCurrencyCode());
                flow.setFlowNo(frontPageFlow.getFlowNo());
                flow.setRemark(frontPageFlow.getRemark());
                flow.setTransactionAmount(frontPageFlow.getTransactionAmount());
                flow.setCreateTime(new Date(frontPageFlow.getCreateTime()));
                flow.setTypeStr(frontPageFlow.getTransactionAmount()<0? FlowTypeEnum.OUT.getMessage():FlowTypeEnum.IN.getMessage());
                frontPageFlowList.add(flow);
            }
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlowList);
            pageInfo.setTotal(frontPageFlowList.size());
            pageInfo.setPageNum(pageNumber);
            pageInfo.setPageSize(pageSize);

            gongxiaoResult = new GongxiaoResult(ErrorCodeJd.SUCCESS.getErrorCode(), pageInfo);
            return gongxiaoResult;
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }
//    // 账户额度转出
//    @RequestMapping("/transferout")
//    @ResponseBody
//    public GongxiaoResult accountTransferOut(HttpServletRequest request,long supplierId, String supplierName,
//                                             @RequestParam(defaultValue = "CNY")String currencyCode, String remark,
//                                             Double postingAmount, Long projectId){
//        GongxiaoResult gongxiaoResult = null;
//        return gongxiaoResult;
//    }

//    // 流水导出
//    @RequestMapping("/exportFlow")
//    @ResponseBody
//    public GongxiaoResult export(HttpServletRequest request, HttpServletResponse response,
//                                 @RequestParam(defaultValue = "CNY") String currencyCode,
//                                 Long projectId){
//        GongxiaoResult gongxiaoResult = null;
//        return gongxiaoResult;
//    }
    // 统计金额
    @RequestMapping("/getSubtotal")
    @ResponseBody
    public GongxiaoResult getSubtotal(HttpServletRequest request, HttpServletResponse response,
                                      String dateStartStr,
                                      String dateEndStr,String accountType, String flowType,
                                      Long projectId){
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage() + ": projectId is null");
        }
        Date dateS = null;
        Date dateE = null;
        try {
            dateS = DateUtil.stringToDate(dateStartStr);
            dateE = DateUtil.stringToDate(dateEndStr);
        }catch (Exception e){
            //返回日期格式错误
            return new GongxiaoResult(ErrorCodeJd.DATE_FORMAT_WRONG.getErrorCode(), ErrorCodeJd.DATE_FORMAT_WRONG.getMessage());
        }
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getSubtotal] params: projectId={}", traceId, projectId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalReq.Builder req =  SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalReq.newBuilder();
            req.setAccountType(accountType);
            req.setFlowType(flowType==null?"":flowType);
            req.setDateS(dateS!=null?dateS.getTime():-1);
            req.setDateE(dateE!=null?dateE.getTime():-1);
            req.setProjectId(projectId);
            req.setRpcHeader(rpcHeader);

            SupplierReservedAccountServiceGrpc.SupplierReservedAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub( SupplierReservedAccountServiceGrpc.SupplierReservedAccountServiceBlockingStub.class);
            SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp resp = blockingStub.getReservedAccountSubtotal(req.build());
            PaymentCommonGrpc.FrontPageFlowSubtotal respSubtotal = resp.getSubtotal();
            FrontPageFlowSubtotal subtotal = new FrontPageFlowSubtotal();
            subtotal.setExpenditureQuantity(respSubtotal.getExpenditureQuantity());
            subtotal.setExpenditureAmount(respSubtotal.getExpenditureAmount());
            subtotal.setIncomeQuantity(respSubtotal.getIncomeQuantity());
            subtotal.setIncomeAmount(respSubtotal.getIncomeAmount());

            gongxiaoResult = new GongxiaoResult(resp.getReturnCode(), subtotal);
        }catch (Exception e){
            logger.error("#traceId={}# [IN][getSubtotal fail] params: projectId={}", traceId, projectId);
            gongxiaoResult = new GongxiaoResult(ErrorCodeJd.SUPPLIER_FROZEN_ACCOUNT_GET_SUBTOTAL_FAIL.getErrorCode(), ErrorCodeJd.SUPPLIER_FROZEN_ACCOUNT_GET_SUBTOTAL_FAIL.getMessage());
        }
        return gongxiaoResult;

    }
}