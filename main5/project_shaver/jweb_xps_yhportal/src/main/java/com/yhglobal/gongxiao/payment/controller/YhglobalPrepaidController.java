package com.yhglobal.gongxiao.payment.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.XpsPrepaidManager;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.constant.payment.AccountType;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.model.WriteOffReturnObject;
import com.yhglobal.gongxiao.payment.entity.*;
import com.yhglobal.gongxiao.payment.microservice.*;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import org.apache.ibatis.annotations.Param;
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
 * @Description:
 * @author: LUOYI
 * @Date: Created in 19:28 2018/5/3tr
 */
@Controller
@RequestMapping("/prepaid")
public class YhglobalPrepaidController {

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    private static Logger logger = LoggerFactory.getLogger(YhglobalPrepaidController.class);

    /**
     * 收付款管理 > 代垫确认
     * @return
     */
    @RequestMapping("/receive/confirm")
    @ResponseBody
    public GongxiaoResult receiveConfirm(HttpServletRequest request,
                                                  Long projectId,
                                                  @RequestParam(defaultValue = "") String projectName,
                                                  @RequestParam(defaultValue = "")String useDate,
                                                  @RequestParam(defaultValue = "0")Integer accountType,
                                                  @RequestParam(defaultValue = "")String philipNo,
                                                  @RequestParam(defaultValue = "")String confirmInfoJson){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][receiveConfirm] params: projectId={}, projectName={},  useDate={},  accountType={},  confirmInfoJson={}", traceId,projectId, projectName, useDate, accountType, confirmInfoJson);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
          //  GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            List<YhglobalToReceivePrepaidLedger> receiveList = null;
            try {
                receiveList = JSON.parseObject(confirmInfoJson, new TypeReference<List<YhglobalToReceivePrepaidLedger>>() {
                });
            } catch (JSONException e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            }
            Date now = new Date();
            String currencyCode = "";
            Integer i = 0; // 成功条数
            Integer length = receiveList.size();// 总条数
            Integer failNo = 0;
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            // 生成流水号
            String flowCode = DateTimeIdGenerator.nextId(tablePrefix, BizNumberType.YHGLOBAL_PREPAID_WRITEOFF_FLOW);
            List<WriteOffReturnObject> list = new ArrayList<>();
            // 获取账户类型常量
            AccountType type = AccountType.getAccountTypeByCode(accountType);
            for (YhglobalToReceivePrepaidLedger receive : receiveList) {
                Long transferAmount = Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED); // 每笔核销扣减的额度
                //failNo = length - i;
                switch (type) { //根据确认的账户类型加载对应的账户余额
                    case ACCOUNT_PREPAID_RECEIPTED:
                        YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest rpcRequest = YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest.newBuilder()
                                .setRpcHeader(rpcHeader)
                                .setProjectId(projectId)
                                .build();
                        YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
                        PaymentCommonGrpc.AccountAmountResponse rpcResponse = yhglobalAccountService.getYhglobalReceivedAccountAmount(rpcRequest);
                        if (rpcResponse.getPrepaidAmount() < transferAmount) {
                            // 账户额度不足
                            logger.error("accountTotalAmount insufficient: philipDocumentNo={}", philipNo);
                            // return new RpcResult(ErrorCode.ACCOUNT_PREPAID_RECEIPT_NOT_SUFFICIENT_FUNDS.getErrorCode(), "账户额度不足,共选择了" + length + "条代垫,已成功核销" + i + "条, 核销失败" + failNo + "条");
                        }
                        break;
                    case ACCOUNT_SALE_DIFFERENCE:
                        SupplierAccountServiceStructure.GetSellHighAccountRequest rpcRequest1 = SupplierAccountServiceStructure.GetSellHighAccountRequest.newBuilder()
                                .setRpcHeader(rpcHeader)
                                .setProjectId(projectId)
                                .build();
                        SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
                        SupplierAccountServiceStructure.GetSellHighAccountResponse rpcResponse1 = rpcStub.getSellHighAccount(rpcRequest1);
                        if (rpcResponse1.getTotalAmount() < transferAmount) {
                            // 差价账户余额不足
                            logger.error("accountTotalAmount insufficient: philipDocumentNo={}", philipNo);
                            //return new RpcResult(ErrorCode.SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), "账户额度不足,共选择了" + length + "条代垫,已成功核销" + i + "条, 核销失败" + failNo + "条");
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("unknown couponConfirmAccountType: " + type.getMessage());
                }


                Long amountBeforeTransaction = 0L;//流水发生前的余额
                Long transactionAmount = -transferAmount;//交易金额
                Long amountAfterTransaction = 0L;//流水发生后的余额
                WriteOffReturnObject object = new WriteOffReturnObject();
                if (AccountType.ACCOUNT_PREPAID_RECEIPTED.getCode() == accountType) {
                    //修改实收帐户余额
                    // WriteOffReturnObject object = yhglobalReceivedPrepaidAccountService.updateYhglobalReceivedPrepaidAccount(rpcHeader, "CNY", projectId, transactionAmount, now,"","" );
                    PaymentCommonGrpc.WriteOffRequest rpcRequest = PaymentCommonGrpc.WriteOffRequest.newBuilder()
                            .setRpcHeader(rpcHeader)
                            .setProjectId(projectId)
                            .setAmount(transactionAmount).setWriteOffFlowNo(flowCode).setCurrencyCode(currencyCode).setTransactionTime(now.getTime())
                            .build();
                    YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
                    PaymentCommonGrpc.WriteOffResponse rpcResponse = yhglobalAccountService.updateYhglobalReceivedPrepaidAccount(rpcRequest);

                    if(rpcResponse.getReturnCode() == ErrorCode.SUCCESS.getErrorCode()){
                        amountBeforeTransaction = rpcResponse.getAmountBeforeTransaction();
                        amountAfterTransaction = rpcResponse.getAmountAfterTransaction();
                    }else {
                        //return new RpcResult(ErrorCode.UPDATE_ACCOUNT_WRONG.getErrorCode(), ErrorCode.UPDATE_ACCOUNT_WRONG.getMessage());
                    }
                } else if (AccountType.ACCOUNT_SALE_DIFFERENCE.getCode() == accountType) {
                    // supplierSellHeightTransferAccountService.writeoffSellHeightAmount(rpcHeader, "CNY", projectId, confirmAmountTotal, now);
                    PaymentCommonGrpc.WriteOffRequest rpcRequest1 = PaymentCommonGrpc.WriteOffRequest.newBuilder()
                            .setRpcHeader(rpcHeader)
                            .setProjectId(projectId)
                            .setAmount(transactionAmount).setTransactionTime(now.getTime()).setWriteOffFlowNo(flowCode)
                            .build();
                    SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
                    PaymentCommonGrpc.WriteOffResponse rpcResponse1 = rpcStub.writeOffUpdateSellHighAccount(rpcRequest1);
                    if (rpcResponse1.getReturnCode() == 0){
                        // 调用成功 返回账户交易前后的额度
                        amountBeforeTransaction = rpcResponse1.getAmountBeforeTransaction();
                        amountAfterTransaction = rpcResponse1.getAmountAfterTransaction();
                    }else {
                        logger.error("updateSellHighAccount  fail ");
                    }
                }
                object.setAmountBeforeTrans(amountBeforeTransaction);
                object.setAmountAfterTrans(amountAfterTransaction);
                list.add(object);


                i++; // 统计核销成功的条数
            }
            gongxiaoResult =  XpsPrepaidManager.yhPrepaidWriteroff(portalConfig.getPrepaidUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, projectName, useDate, accountType, philipNo, confirmInfoJson, list);
            if (gongxiaoResult.getReturnCode() != 0){
                throw new RuntimeException("error in api");
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), "共选择了"+length+"条代垫,已成功核销"+i+"条, 核销失败"+(length - i)+"条");
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 代垫确认和取消确认都要调用的根据账户类型修改 账户额度的方法 此处账户包括 代垫实收账户 和销售差价账户
     * @param transactionAmount 正值表示增加 负值表示扣款
     */
    WriteOffReturnObject updateAccount(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, long transactionAmount,
                                       Date now, String sourceFlowNo, AccountType accountType){

        WriteOffReturnObject writeOffReturnObject = new WriteOffReturnObject();
        switch (accountType) {
            case ACCOUNT_PREPAID_RECEIPTED:
                //扣减代垫实收帐户余额
                PaymentCommonGrpc.WriteOffRequest rpcRequest = PaymentCommonGrpc.WriteOffRequest.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(projectId)
                        .setAmount(transactionAmount).setWriteOffFlowNo(sourceFlowNo).setTransactionTime(now.getTime())
                        .build();
                YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
                PaymentCommonGrpc.WriteOffResponse rpcResponse = yhglobalAccountService.updateYhglobalReceivedPrepaidAccount(rpcRequest);
                if (rpcResponse.getReturnCode() == 0){
                    // 调用成功 返回账户交易前后的额度
                    writeOffReturnObject.setReturnCode(rpcResponse.getReturnCode());
                    writeOffReturnObject.setAmountBeforeTrans(rpcResponse.getAmountBeforeTransaction());
                    writeOffReturnObject.setAmountAfterTrans(rpcResponse.getAmountAfterTransaction());
                }else {
                    logger.error("updateYhglobalReceivedPrepaidAccount  fail ");
                }
                break;
            case ACCOUNT_SALE_DIFFERENCE:
                // 扣减销售差价账户
//                supplierSellHeightTransferAccountService.updateSupplierSellHeightTransferAccount(rpcHeader, "CNY", projectId, transactionAmount,"", now);
                PaymentCommonGrpc.WriteOffRequest rpcRequest1 = PaymentCommonGrpc.WriteOffRequest.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(projectId)
                        .setAmount(transactionAmount).setTransactionTime(now.getTime()).setWriteOffFlowNo(sourceFlowNo)
                        .build();
                SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
                PaymentCommonGrpc.WriteOffResponse rpcResponse1 = rpcStub.writeOffUpdateSellHighAccount(rpcRequest1);
                if (rpcResponse1.getReturnCode() == 0){
                    // 调用成功 返回账户交易前后的额度
                    writeOffReturnObject.setReturnCode(rpcResponse1.getReturnCode());
                    writeOffReturnObject.setAmountBeforeTrans(rpcResponse1.getAmountBeforeTransaction());
                    writeOffReturnObject.setAmountAfterTrans(rpcResponse1.getAmountAfterTransaction());
                }else {
                    logger.error("updateSellHighAccount  fail ");
                }
                break;
            default:
                logger.error("unsupported account type: {}", accountType);
        }
        return writeOffReturnObject;
    }
    /**
     * 收付款管理 > 代垫台帐> 代垫取消确认
     * @return
     */
    @RequestMapping("/receive/confirm/cancel")
    @ResponseBody
    public GongxiaoResult receiveCancelConfirmBatch(HttpServletRequest request,Long projectId,
                                                    String cancelConfirmJson){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            //logger.info("#traceId={}# [IN][receiveCancelConfirmBatch] params: flowCodes={}", traceId,flowCodes);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            List<YhglobalPrepaidLedgerWriteoffRecord> receiveList = null;
            try {
                receiveList = JSON.parseObject(cancelConfirmJson, new TypeReference<List<YhglobalPrepaidLedgerWriteoffRecord>>() {
                });
            } catch (JSONException e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            }
            Date now = new Date();
            for (YhglobalPrepaidLedgerWriteoffRecord record : receiveList) {
                AccountType accountType1 = AccountType.getAccountTypeByName(record.getAccountTypeStr());
                Long transactionAmount = Math.round(record.getReceiptAmountDouble()*FXConstant.HUNDRED);
                // 生成流水号
                String writeoffCode = DateTimeIdGenerator.nextId(TablePrefixUtil.getTablePrefixByProjectId(projectId), BizNumberType.YHGLOBAL_PREPAID_WRITEOFF_FLOW);
                WriteOffReturnObject object = updateAccount(rpcHeader, projectId, transactionAmount, now , writeoffCode, accountType1);

                gongxiaoResult = XpsPrepaidManager.PrepaidReceiveCancelConfirmBatch(portalConfig.getPrepaidUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                        portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId ,record.getFlowCode(), object, writeoffCode);
                if (gongxiaoResult.getReturnCode() != 0){
                    throw new RuntimeException("");
                }
            }

        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    //proto对象转换为Java对象
    private PageInfo<YhglobalPrepaidReceive>  receivePageInfoToJava(YhglobalPrepaidServiceStructure.ReceivePageInfoResp rpcResult){
        PageInfo<YhglobalPrepaidReceive> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(rpcResult.getPageNum());
        pageInfo.setPageSize(rpcResult.getPageSize());
        pageInfo.setSize(rpcResult.getSize());
        pageInfo.setSize(rpcResult.getSize());
        pageInfo.setEndRow(rpcResult.getEndRow());
        pageInfo.setTotal(rpcResult.getTotal());
        pageInfo.setPages(rpcResult.getPages());
        List<YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidLedger> receiveList = rpcResult.getReceiveListList();
        List<YhglobalPrepaidReceive> prepaidReceiveList = new ArrayList<>();
        for(YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidLedger receive:receiveList){
            YhglobalPrepaidReceive prepaidReceive = new YhglobalPrepaidReceive();
            prepaidReceive.setOrderId(receive.getOrderId());
            prepaidReceive.setCurrencyCode(receive.getCurrencyCode());
            prepaidReceive.setReceiveAmount(receive.getReceiveAmount());
            prepaidReceive.setReceiveStatus(receive.getReceiveStatus());
            prepaidReceive.setToBeConfirmAmount(receive.getToBeConfirmAmount());
            prepaidReceiveList.add(prepaidReceive);
        }
        pageInfo.setList(prepaidReceiveList);
        return pageInfo;
    }

    /**
     * 收付款管理>代垫确认处理>点击确认跳转
     */
    @RequestMapping("/receive/getsByIds")
    @ResponseBody
    public GongxiaoResult getsReceiveByIds(HttpServletRequest request,Long projectId, String[] ids){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsReceiveByIds] params: ids={}, ", traceId,ids);
//            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            YhglobalPrepaidServiceStructure.SelectByIdsReq.Builder reqBuilder = YhglobalPrepaidServiceStructure.SelectByIdsReq.newBuilder();
//            YhglobalPrepaidServiceStructure.SelectByIdsReq rpcRequest;
//            reqBuilder.setRpcHeader(rpcHeader);
//            if(ids != null){
//                for(String id:ids){
//                    reqBuilder.addIds(id);
//                }
//            }
//            rpcRequest = reqBuilder.build();
//            YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub stub = RpcStubStore.getRpcStub(YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub.class);
//            YhglobalPrepaidServiceStructure.ReceiveListResp receiveListResp = stub.selectByIds(rpcRequest);
//            gongxiaoResult = new GongxiaoResult(receiveListResp.getReturnCode(),receiveListResp.getMsg());
//            List<YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidLedger> receiveList = receiveListResp.getReceiveListList();
//
//            //转换为java对象
//            List<YhglobalPrepaidReceive> prepaidReceiveList = new ArrayList<>();
//            for(YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidLedger receive:receiveList){
//                YhglobalPrepaidReceive prepaidReceive = new YhglobalPrepaidReceive();
//                prepaidReceive.setOrderId(receive.getOrderId());
//                prepaidReceive.setCurrencyCode(receive.getCurrencyCode());
//                prepaidReceive.setReceiveAmount(receive.getReceiveAmount());
//                prepaidReceive.setReceiveStatus(receive.getReceiveStatus());
//                prepaidReceive.setToBeConfirmAmount(receive.getToBeConfirmAmount());
//                prepaidReceiveList.add(prepaidReceive);
//            }
//            gongxiaoResult.setData(prepaidReceiveList);
//            int returnCode = receiveListResp.getReturnCode();
//            if (returnCode == ErrorCode.SUCCESS.getErrorCode()) {
//                logger.info("#traceId={}# [OUT] get getsReceiveByIds success:resultCode={}", traceId, returnCode);
//            } else {
//                logger.error("#traceId={}# [OUT] fail to get getsReceiveByIds:resultCode={}", traceId, returnCode);
//            }
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
           gongxiaoResult = XpsPrepaidManager.selectYhglobalPrepaidLedgerByPurchasePrepaidLedgerId(portalConfig.getPrepaidUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                   portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, ids);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult =  GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 收付款管理>代垫确认处理  列表查询
     */
    @RequestMapping("/receive/getsMergeByProjectId")
    @ResponseBody
    public GongxiaoResult getsMergeByProjectId(HttpServletRequest request,
                                                                                 @RequestParam(defaultValue = "0")Long projectId,
                                                                                 @RequestParam(defaultValue = "")String orderId,
                                                                                 @RequestParam(defaultValue = "")String flowCode,
                                                                                 @RequestParam(defaultValue = "0")Integer accountType,
                                                                                 @RequestParam(defaultValue = "")String dateStart,
                                                                                 @RequestParam(defaultValue = "")String dateEnd,
                                                                                 @RequestParam(defaultValue = "")String dateStartConfirm,
                                                                                 @RequestParam(defaultValue = "")String dateEndConfirm,
                                                                                 Byte[] receiveStatus,
                                                                                 @RequestParam(required=true,defaultValue="1") Integer pageNumber,
                                                                                 @RequestParam(required=false,defaultValue="60") Integer pageSize){
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),ErrorCode.SUCCESS.getMessage());
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsReceiveByProjectId] params: projectId={}, ", traceId,projectId);
//            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            String status="";
//            if(receiveStatus !=null && receiveStatus.length>0){
//                for(Integer item:receiveStatus){
//                    if(status==""){
//                        status = item+"";
//                    }else{
//                        status += ","+item;
//                    }
//                }
//            }
//            YhglobalPrepaidServiceStructure.SelectReceiveAndRecordByProjectIdReq.Builder reqBuilder = YhglobalPrepaidServiceStructure.SelectReceiveAndRecordByProjectIdReq.newBuilder();
//            YhglobalPrepaidServiceStructure.SelectReceiveAndRecordByProjectIdReq rpcRequest;
//            reqBuilder.setRpcHeader(rpcHeader);
//            reqBuilder.setProjectId(projectId);
//            reqBuilder.setOrderId(orderId);
//            reqBuilder.setFlowCode(flowCode);
//            reqBuilder.setAccountType(accountType);
//            reqBuilder.setDateStart(dateStart);
//            reqBuilder.setDateEnd(dateEnd);
//            reqBuilder.setDateEndConfirm(dateEndConfirm);
//            reqBuilder.setDateStartConfirm(dateStartConfirm);
//            reqBuilder.setReceiveStatus(status);
//            reqBuilder.setPageNumber(pageNumber);
//            reqBuilder.setPageSize(pageSize);
//            rpcRequest = reqBuilder.build();
//            YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub stub = RpcStubStore.getRpcStub(YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub.class);
//            YhglobalPrepaidServiceStructure.ReceivePageInfoResp receivePageInfoResp = stub.selectReceiveAndRecordByProjectId(rpcRequest);
//            gongxiaoResult.setReturnCode(receivePageInfoResp.getReturnCode());
//            gongxiaoResult.setMessage(receivePageInfoResp.getMsg());
//            //GRPC resp转pageInfo
//            PageInfo<YhglobalPrepaidReceive> pageInfo = receivePageInfoToJava(receivePageInfoResp);
//            gongxiaoResult.setData(pageInfo);
//            int returnCode = receivePageInfoResp.getReturnCode();
//            if (returnCode == ErrorCode.SUCCESS.getErrorCode()) {
//                logger.info("#traceId={}# [OUT] get getsMergeByProjectId success:resultCode={}", traceId, returnCode);
//            } else {
//                logger.error("#traceId={}# [OUT] fail to get getsMergeByProjectId:resultCode={}", traceId, returnCode);
//            }
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
           gongxiaoResult = XpsPrepaidManager.selectByManyCondiitonsHasPage(portalConfig.getPrepaidUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                   portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId,dateStart,dateEnd, dateStartConfirm, dateEndConfirm, receiveStatus, flowCode, pageNumber, pageSize,orderId, accountType );
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 收付款管理>代垫确认处理 金额统计
     */
    @RequestMapping("/receive/getsMergeCount")
    @ResponseBody
    public GongxiaoResult getsMergeCount(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              @RequestParam(defaultValue = "0")Long projectId,
                                                              @RequestParam(defaultValue = "")String receiveIds){
        GongxiaoResult gongxiaoResult;
        String traceId = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsReceiveByProjectId] params: projectId={}, ", traceId,projectId);
//            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            YhglobalPrepaidServiceStructure.SelectReceiveAndRecordCountReq.Builder reqBuilder = YhglobalPrepaidServiceStructure.SelectReceiveAndRecordCountReq.newBuilder();
//            YhglobalPrepaidServiceStructure.SelectReceiveAndRecordCountReq rpcRequest;
//            reqBuilder.setProjectId(projectId);
//            reqBuilder.setReceiveIds(receiveIds);
//            rpcRequest = reqBuilder.build();
//            YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub stub = RpcStubStore.getRpcStub(YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub.class);
//            YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidCountResp rpcResp = stub.selectReceiveAndRecordCount(rpcRequest);
//            PrepaidReceiveCount prepaidReceiveCount = new PrepaidReceiveCount();
//            prepaidReceiveCount.setConfirmAmount(rpcResp.getConfirmAmount());
//            prepaidReceiveCount.setReceiptAmount(rpcResp.getReceiptAmount());
//            prepaidReceiveCount.setReceiveAmount(rpcResp.getReceiveAmount());
//            prepaidReceiveCount.setToBeConfirmAmount(rpcResp.getToBeConfirmAmount());
//            gongxiaoResult = new GongxiaoResult(rpcResp.getReturnCode(),rpcResp.getMsg(),prepaidReceiveCount);
//            int returnCode = rpcResp.getReturnCode();
//            if (returnCode == ErrorCode.SUCCESS.getErrorCode()) {
//                logger.info("#traceId={}# [OUT] get getsMergeCount success:resultCode={}", traceId, returnCode);
//            } else {
//                logger.error("#traceId={}# [OUT] fail to get getsMergeCount:resultCode={}", traceId, returnCode);
//            }
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            gongxiaoResult = XpsPrepaidManager.getsMergeCount(portalConfig.getPrepaidUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(),projectId,receiveIds);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
    /**
     * 收付款管理>代垫付款单管理  新增保存
     */
    @RequestMapping("/info/add")
    @ResponseBody
    public GongxiaoResult addPrepaidInfo(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  Long projectId,
                                                  String projectName,
                                                  Integer supplierId,
                                                  String supplierName,
                                                  String payer,
                                                  String receivables,
                                                  String settlementNo,
                                                  Integer settlementType,
                                                  String dateBusiness,
                                                  String taxNo,
                                                  String contactInfo,
                                                  Integer provinceId,
                                                  String provinceName,
                                                  Integer cityId,
                                                  String cityName,
                                                  Integer districtId,
                                                  String districtName,
                                                  String streetAddress,
                                                  String accountCNY,
                                                  String bankNameCNY,
                                                  String remark,
                                                  String itemJson){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][addPrepaidInfo] params: projectId={}, ", traceId,projectId);
//            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            YhglobalPrepaidServiceStructure.AddPrepaidInfoReq.Builder reqBuilder = YhglobalPrepaidServiceStructure.AddPrepaidInfoReq.newBuilder();
//            YhglobalPrepaidServiceStructure.AddPrepaidInfoReq rpcRequest;
//            reqBuilder.setRpcHeader(rpcHeader);
//            YhglobalPrepaidServiceStructure.YhglobalPrepaidInfo.Builder infoBuilder  = YhglobalPrepaidServiceStructure.YhglobalPrepaidInfo.newBuilder();
//            YhglobalPrepaidServiceStructure.YhglobalPrepaidInfo info;
//
//            infoBuilder.setProjectId(projectId);
//            infoBuilder.setProjectName(projectName);
//            infoBuilder.setSupplierId(supplierId);
//            infoBuilder.setSupplierName(supplierName);
//            infoBuilder.setPayer(payer);
//            infoBuilder.setReceivables(receivables);
//            infoBuilder.setSettlementNo(settlementNo);
//            infoBuilder.setDateBusiness(dateBusiness);
//            infoBuilder.setTaxNo(taxNo);
//            infoBuilder.setContactInfo(contactInfo);
//            infoBuilder.setProvinceId(provinceId);
//            infoBuilder.setProvinceName(provinceName);
//            infoBuilder.setCityId(cityId);
//            infoBuilder.setCityName(cityName);
//            infoBuilder.setDistrictId(districtId);
//            infoBuilder.setDistrictName(districtName);
//            infoBuilder.setStreetAddress(streetAddress);
//            infoBuilder.setAccountCNY(accountCNY);
//            infoBuilder.setBankNameCNY(bankNameCNY);
//            infoBuilder.setRemark(remark);
//            List<YhglobalPrepaidInfoItem> itemList = JSON.parseObject(itemJson, new TypeReference<List<YhglobalPrepaidInfoItem>>() {
//            });
//            for(YhglobalPrepaidInfoItem item:itemList){
//                YhglobalPrepaidServiceStructure.YhglobalPrepaidInfoItem.Builder itemBuilder = YhglobalPrepaidServiceStructure.YhglobalPrepaidInfoItem.newBuilder();
//                YhglobalPrepaidServiceStructure.YhglobalPrepaidInfoItem infoItem;
//                itemBuilder.setApplicationAmount(item.getApplicationAmountDouble());
//                itemBuilder.setExchangeRate(item.getExchangeRateDouble());
//                itemBuilder.setTaxPoint(item.getTaxPointDouble());
//                itemBuilder.setStandardCurrencyAmount(item.getStandardCurrencyAmountDouble());
//                itemBuilder.setTaxSubsidy(item.getTaxSubsidyDouble());
//                infoItem = itemBuilder.build();
//                reqBuilder.addItemList(infoItem);
//            }
//
//            info = infoBuilder.build();
//            reqBuilder.setYhglobalPrepaidInfo(info);
//            rpcRequest = reqBuilder.build();
//            YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub stub = RpcStubStore.getRpcStub(YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub.class);
//            GongxiaoRpc.RpcResult rpcResult = stub.addPrepaidInfo(rpcRequest);
//            gongxiaoResult = new GongxiaoResult(rpcResult.getReturnCode(),rpcResult.getMsg());
//            int returnCode = rpcResult.getReturnCode();
//            if (returnCode == ErrorCode.SUCCESS.getErrorCode()) {
//                logger.info("#traceId={}# [OUT] get addPrepaidInfo success:resultCode={}", traceId, returnCode);
//            } else {
//                logger.error("#traceId={}# [OUT] fail to get addPrepaidInfo:resultCode={}", traceId, returnCode);
//            }

            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            gongxiaoResult = XpsPrepaidManager.addPrepaidInfo(portalConfig.getPrepaidUrl(),portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, projectName, supplierId, supplierName, payer, receivables, settlementNo, dateBusiness, taxNo, contactInfo, provinceId,
                    provinceName, cityId, cityName, districtId, districtName, streetAddress,accountCNY, bankNameCNY, remark, itemJson, settlementType);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 收付款管理>代垫付款单管理  代垫付款单列表查询
     */
    @RequestMapping("/info/gets")
    @ResponseBody
    public GongxiaoResult getsPrepaidInfoList(HttpServletRequest request,
                                                                             HttpServletResponse response,
                                                                             @RequestParam(defaultValue="0")Long projectId,
                                                                             @RequestParam(defaultValue="")String prepaidNo,
                                                                             @RequestParam(defaultValue="")String receivables,
                                                                             @RequestParam(defaultValue="")String dateStart,
                                                                             @RequestParam(defaultValue="")String dateEnd,
                                                                             @RequestParam(required=true,defaultValue="1") Integer page,
                                                                             @RequestParam(required=false,defaultValue="10") Integer pageSize){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsPrepaidInfoList] params: projectId={}, page={}, pageSize={}", traceId,projectId,page,pageSize);
//            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            YhglobalPrepaidServiceStructure.GetsPrepaidInfoListReq.Builder reqBuilder = YhglobalPrepaidServiceStructure.GetsPrepaidInfoListReq.newBuilder();
//            YhglobalPrepaidServiceStructure.GetsPrepaidInfoListReq rpcRequest;
//            reqBuilder.setRpcHeader(rpcHeader);
//            reqBuilder.setProjectId(projectId);
//            reqBuilder.setPrepaidNo(prepaidNo);
//            reqBuilder.setReceivables(receivables);
//            reqBuilder.setDateStart(dateStart);
//            reqBuilder.setDateEnd(dateEnd);
//            reqBuilder.setPageSize(pageSize);
//            rpcRequest = reqBuilder.build();
//            YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub stub = RpcStubStore.getRpcStub(YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub.class);
//            YhglobalPrepaidServiceStructure.PrepaidPageInfoResp rpcResp = stub.getsPrepaidInfoList(rpcRequest);
//            gongxiaoResult = new GongxiaoResult(rpcResp.getReturnCode(),rpcResp.getMsg());
//            //GRPC resp转pageInfo
//            PageInfo<YhglobalPrepaidInfo> pageInfo = prepaidPageInfoToJava(rpcResp);
//            gongxiaoResult.setData(pageInfo);
//            int returnCode = rpcResp.getReturnCode();
//            if (returnCode == ErrorCode.SUCCESS.getErrorCode()) {
//                logger.info("#traceId={}# [OUT] get getsPrepaidInfoList success:resultCode={}", traceId, returnCode);
//            } else {
//                logger.error("#traceId={}# [OUT] fail to get getsPrepaidInfoList:resultCode={}", traceId, returnCode);
//            }

            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            gongxiaoResult = XpsPrepaidManager.getsPrepaidInfoList(portalConfig.getPrepaidUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, prepaidNo, receivables, dateStart, dateEnd, page, pageSize);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
    private PageInfo<YhglobalPrepaidInfo> prepaidPageInfoToJava(YhglobalPrepaidServiceStructure.PrepaidPageInfoResp rpcResult){
        PageInfo<YhglobalPrepaidInfo> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(rpcResult.getPageNum());
        pageInfo.setPageSize(rpcResult.getPageSize());
        pageInfo.setSize(rpcResult.getSize());
        pageInfo.setSize(rpcResult.getSize());
        pageInfo.setEndRow(rpcResult.getEndRow());
        pageInfo.setTotal(rpcResult.getTotal());
        pageInfo.setPages(rpcResult.getPages());
        List<YhglobalPrepaidServiceStructure.YhglobalPrepaidInfo> prepaidInfoList = rpcResult.getPrepaidListList();
        List<YhglobalPrepaidInfo> infoList = new ArrayList<>();
        for(YhglobalPrepaidServiceStructure.YhglobalPrepaidInfo prepaidInfo:prepaidInfoList){
            YhglobalPrepaidInfo info = new YhglobalPrepaidInfo();
            info.setPrepaidNo(prepaidInfo.getPrepaidNo());
            info.setStandardCurrencyAmountDouble(prepaidInfo.getStandardCurrencyAmount());
            info.setReceivables(prepaidInfo.getReceivables());
            info.setSupplierName(prepaidInfo.getSupplierName());
            Date createTime = null;
            try{
                createTime = DateUtil.stringToDate(prepaidInfo.getCreateTime(),DateUtil.DATE_TIME_FORMAT);
            }catch(Exception e){}
            info.setCreateTime(createTime);
            info.setCreatedByName(prepaidInfo.getCreatedByName());
            infoList.add(info);
        }
        pageInfo.setList(infoList);
        return pageInfo;
    }
    /**
     * 收付款管理>代垫付款单管理  代垫付款单详情
     */
    @RequestMapping("/info/get")
    @ResponseBody
    public GongxiaoResult getPrepaidInfoById(HttpServletRequest request,
                                                                  HttpServletResponse response,
                                                                  Long projectId,
                                                                  @RequestParam(defaultValue="0")Integer id){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsMergeByProjectId] params: id={}, ", traceId,id);
//            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            YhglobalPrepaidServiceStructure.GetPrepaidInfoByIdReq.Builder reqBuilder = YhglobalPrepaidServiceStructure.GetPrepaidInfoByIdReq.newBuilder();
//            YhglobalPrepaidServiceStructure.GetPrepaidInfoByIdReq rpcRequest;
//            reqBuilder.setId(id);
//            rpcRequest = reqBuilder.build();
//            YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub stub = RpcStubStore.getRpcStub(YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub.class);
//            YhglobalPrepaidServiceStructure.PrepaidInfoResp prepaidInfoResp = stub.getPrepaidInfoById(rpcRequest);
//            gongxiaoResult = new GongxiaoResult(prepaidInfoResp.getReturnCode(),prepaidInfoResp.getMsg());
//
//            YhglobalPrepaidInfo yhglobalPrepaidInfo = new YhglobalPrepaidInfo();
//            List<YhglobalPrepaidInfoItem> itemList = new ArrayList<>();
//            YhglobalPrepaidServiceStructure.YhglobalPrepaidInfo prepaidInfo = prepaidInfoResp.getYhglobalPrepaidInfo();
//            List<YhglobalPrepaidServiceStructure.YhglobalPrepaidInfoItem> infoItemList = prepaidInfoResp.getItemListList();
//            yhglobalPrepaidInfo.setProjectName(prepaidInfo.getProjectName());
//            yhglobalPrepaidInfo.setProvinceName(prepaidInfo.getProvinceName());
//            yhglobalPrepaidInfo.setCityName(prepaidInfo.getCityName());
//            yhglobalPrepaidInfo.setDistrictName(prepaidInfo.getDistrictName());
//            yhglobalPrepaidInfo.setSupplierName(prepaidInfo.getSupplierName());
//            yhglobalPrepaidInfo.setPayer(prepaidInfo.getPayer());
//            yhglobalPrepaidInfo.setSettlementNo(prepaidInfo.getSettlementNo());
//            yhglobalPrepaidInfo.setReceivables(prepaidInfo.getReceivables());
//            Date dateBusiness = null;
//            if(prepaidInfo.getDateBusiness()!=null && prepaidInfo.getDateBusiness()!="") {
//                dateBusiness = DateUtil.stringToDate(prepaidInfo.getDateBusiness());
//            }
//            yhglobalPrepaidInfo.setDateBusiness(dateBusiness);
//            yhglobalPrepaidInfo.setTaxNo(prepaidInfo.getTaxNo());
//            yhglobalPrepaidInfo.setContactInfo(prepaidInfo.getContactInfo());
//            yhglobalPrepaidInfo.setBankNameCNY(prepaidInfo.getBankNameCNY());
//            yhglobalPrepaidInfo.setAccountCNY(prepaidInfo.getAccountCNY());
//
//            for(YhglobalPrepaidServiceStructure.YhglobalPrepaidInfoItem prepaidItem:infoItemList){
//                YhglobalPrepaidInfoItem prepaidInfoItem = new YhglobalPrepaidInfoItem();
//                prepaidInfoItem.setCostType(prepaidItem.getCostType());
//                prepaidInfoItem.setCurrencyCode(prepaidItem.getCurrencyCode());
//                prepaidInfoItem.setExchangeRateDouble(prepaidItem.getExchangeRate());
//                prepaidInfoItem.setApplicationAmountDouble(prepaidItem.getApplicationAmount());
//                prepaidInfoItem.setInvoiceType(prepaidItem.getInvoiceType());
//                prepaidInfoItem.setTaxPointDouble(prepaidItem.getTaxPoint());
//                prepaidInfoItem.setStandardCurrencyAmountDouble(prepaidItem.getStandardCurrencyAmount());
//                prepaidInfoItem.setTaxSubsidyDouble(prepaidItem.getTaxSubsidy());
//                prepaidInfoItem.setIsTaxSubsidy(prepaidItem.getIsTaxSubsidy());
//                prepaidInfoItem.setReason(prepaidItem.getReason());
//                itemList.add(prepaidInfoItem);
//            }
//            yhglobalPrepaidInfo.setItemList(itemList);
//            gongxiaoResult.setData(yhglobalPrepaidInfo);
//            int returnCode = prepaidInfoResp.getReturnCode();
//            if (returnCode == ErrorCode.SUCCESS.getErrorCode()) {
//                logger.info("#traceId={}# [OUT] get getPrepaidInfoById success:resultCode={}", traceId, returnCode);
//            } else {
//                logger.error("#traceId={}# [OUT] fail to get getPrepaidInfoById:resultCode={}", traceId, returnCode);
//            }


            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            gongxiaoResult = XpsPrepaidManager.getPrepaidInfoById(portalConfig.getPrepaidUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(),projectId, id);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
    /**
     *  收付款管理 > 代垫台帐
     */
    @RequestMapping("/confirm/gets")
    @ResponseBody
    public GongxiaoResult getsPrepaidConfirmList(HttpServletRequest request,
                                                                                                HttpServletResponse response,
                                                                                          @RequestParam(defaultValue="0")Long projectId,
                                                                                          @RequestParam(defaultValue="")String flowCode,
                                                                                          @RequestParam(defaultValue="0")Integer accountType,
                                                                                          @RequestParam(defaultValue="")String useDateStart,
                                                                                          @RequestParam(defaultValue="")String useDateEnd,
                                                                                          @RequestParam(defaultValue="")String dateStart,
                                                                                          @RequestParam(defaultValue="")String dateEnd,
                                                                                                @RequestParam(required=true,defaultValue="1") Integer pageNumber,
                                                                                                @RequestParam(required=false,defaultValue="10") Integer pageSize){
        String traceId = null;
        GongxiaoResult gongxiaoResult;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsPrepaidInfoList] params: projectId={}, pageNumber={}, pageSize={}", traceId,projectId,pageNumber,pageSize);
//            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            YhglobalPrepaidServiceStructure.SearchPrepaidConfirmReq.Builder reqBuilder = YhglobalPrepaidServiceStructure.SearchPrepaidConfirmReq.newBuilder();
//            YhglobalPrepaidServiceStructure.SearchPrepaidConfirmReq rpcRequest;
//            reqBuilder.setRpcHeader(rpcHeader);
//            reqBuilder.setProjectId(projectId);
//            reqBuilder.setFlowCode(flowCode);
//            reqBuilder.setAccountType(accountType);
//            reqBuilder.setDateStart(dateStart);
//            reqBuilder.setDateEnd(dateEnd);
//            reqBuilder.setUseDateStart(useDateStart);
//            reqBuilder.setUseDateEnd(useDateEnd);
//            reqBuilder.setPage(pageNumber);
//            reqBuilder.setPageSize(pageSize);
//            rpcRequest = reqBuilder.build();
//            YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub stub = RpcStubStore.getRpcStub(YhglobalPrepaidServiceGrpc.YhglobalPrepaidServiceBlockingStub.class);
//            YhglobalPrepaidServiceStructure.WriteoffRecordPageInfoResp recordPageInfoResp = stub.searchPrepaidConfirm(rpcRequest);
//            gongxiaoResult = new GongxiaoResult(recordPageInfoResp.getReturnCode(),recordPageInfoResp.getMsg());
//            //GRPC resp转pageInfo
//            PageInfo<YhglobalPrepaidWriteoffRecord> pageInfo = recordPageInfoToJava(recordPageInfoResp);
//            gongxiaoResult.setData(pageInfo);
//            int returnCode = recordPageInfoResp.getReturnCode();
//            if (returnCode == ErrorCode.SUCCESS.getErrorCode()) {
//                logger.info("#traceId={}# [OUT] get getsPrepaidConfirmList success:resultCode={}", traceId, returnCode);
//            } else {
//                logger.error("#traceId={}# [OUT] fail to get getsPrepaidConfirmList:resultCode={}", traceId, returnCode);
//            }

            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            gongxiaoResult = XpsPrepaidManager.searchPrepaidConfirm(portalConfig.getPrepaidUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, flowCode, accountType, useDateStart, useDateEnd, dateStart, dateEnd, pageNumber, pageSize);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
    private PageInfo<YhglobalPrepaidWriteoffRecord> recordPageInfoToJava(YhglobalPrepaidServiceStructure.WriteoffRecordPageInfoResp rpcResult){
        PageInfo<YhglobalPrepaidWriteoffRecord> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(rpcResult.getPageNum());
        pageInfo.setPageSize(rpcResult.getPageSize());
        pageInfo.setSize(rpcResult.getSize());
        pageInfo.setSize(rpcResult.getSize());
        pageInfo.setEndRow(rpcResult.getEndRow());
        pageInfo.setTotal(rpcResult.getTotal());
        pageInfo.setPages(rpcResult.getPages());
        List<YhglobalPrepaidServiceStructure.YhglobalPrepaidLedgerWriteoffRecord> recordList = rpcResult.getRecordListList();
        List<YhglobalPrepaidWriteoffRecord> prepaidRecordList = new ArrayList<>();
        for(YhglobalPrepaidServiceStructure.YhglobalPrepaidLedgerWriteoffRecord record:recordList){
            YhglobalPrepaidWriteoffRecord prepaidRecord = new YhglobalPrepaidWriteoffRecord();
            prepaidRecord.setFlowCode(record.getFlowCode());
            prepaidRecord.setConfirmAmountDouble(record.getConfirmAmount());
            prepaidRecord.setReceiptAmountDouble(record.getReceiptAmount());
            Date useDate = null;
            if(record.getUseDate()!=null){
                try{
                    useDate = DateUtil.stringToDate(record.getUseDate());
                }catch(Exception e){}
            }
            prepaidRecord.setUseDate(useDate);
            prepaidRecord.setAccountType(record.getAccountType());
            prepaidRecord.setPhilipNo(record.getPhilipNo());
            Date createTime = null;
            if(record.getCreateTime()!=null){
                try{
                    createTime = DateUtil.stringToDate(record.getCreateTime(),DateUtil.DATE_TIME_FORMAT);
                }catch(Exception e){}
            }
            prepaidRecord.setCreateTime(createTime);
            prepaidRecord.setCreatedByName(record.getCreatedByName());
            prepaidRecordList.add(prepaidRecord);
        }
        pageInfo.setList(prepaidRecordList);
        return pageInfo;
    }

    /**
     * 生成代垫报表 左一数据入口
     */
    @RequestMapping("/report/leftOne")
    @ResponseBody
    public GongxiaoResult getReportDataLeftOne(HttpServletRequest request,
                                             HttpServletResponse response,
                                             Long projectId){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try{
            // 测试用
//            portalUserInfo.setUserId("123");
//            portalUserInfo.setUserName("test");
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsMergeByProjectId] params: id={}, ", traceId);

            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            gongxiaoResult = XpsPrepaidManager.ReportLeftOne(portalConfig.getPrepaidUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId);

        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 生成代垫报表 右一数据入口
     */
    @RequestMapping("/report/rightOne")
    @ResponseBody
    public GongxiaoResult getReportDataRighOne(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Long projectId){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try{
            // 测试用
//            portalUserInfo.setUserId("123");
//            portalUserInfo.setUserName("test");
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsMergeByProjectId] params: id={}, ", traceId);

            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            gongxiaoResult = XpsPrepaidManager.ReportRightOne(portalConfig.getPrepaidUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
