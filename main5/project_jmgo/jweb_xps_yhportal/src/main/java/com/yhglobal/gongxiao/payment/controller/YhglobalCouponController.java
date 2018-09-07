package com.yhglobal.gongxiao.payment.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.XpsCouponManager;

import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.model.YhglobalCouponLedger;
import com.yhglobal.gongxiao.base.model.YhglobalCouponWriteoffRecord;

import com.yhglobal.gongxiao.base.protal.PortalConfig;


import com.yhglobal.gongxiao.common.GongxiaoResult;

import com.yhglobal.gongxiao.constant.CouponConfirmAccountType;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;

import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.WriteOffReturnObject;
import com.yhglobal.gongxiao.payment.microservice.*;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 越海返利核销的controller
 * @author 王帅
 */
@Controller
@RequestMapping("/yhglobal/coupon")
public class YhglobalCouponController {
    private static Logger logger = LoggerFactory.getLogger(YhglobalCouponController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类



    /**
     * 越海返利确认的入口
     *
     * @param request
     * @param projectId       项目ID
     * @param projectName     项目名称
     * @param useDate         使用日期 可选填写   对应页面[收付款管理] > [返利确认处理] > [返利确认列表] 的使用日期
     * @param accountType     使用账户  对应页面[收付款管理] > [返利确认处理] > [返利确认列表] 的使用账户
     * @param confirmInfoJson 返利确认信息的json串 (List<YhglobalCouponLedger>)
     * @return
     */
    @RequestMapping(value = "/writeroff")
    @ResponseBody
    public GongxiaoResult yhglobalCouponWriteroff(HttpServletRequest request,
                                                  String philipDocumentNo,
                                                  Long projectId, String projectName, @RequestParam(required = false) String useDate,
                                                  Integer accountType, String confirmInfoJson){
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage() + ": projectId is null");
        }

        if (philipDocumentNo == null || "".equals(philipDocumentNo)){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage() + ": philipDocumentNo is null");
        }

        if (confirmInfoJson == null || "".equals(confirmInfoJson)){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage() + ": confirmInfoJson is null");
        }

        Date useDateS = null;
        if (useDate != null) { //若前端传递了使用日期 则转换为Date对象
            try {
                useDateS = DateUtil.stringToDate(useDate);
            } catch (Exception e) {
                logger.error("illegalArgument: useDate={}", useDate);
                return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage() + ": useDate=" + useDate);
            }
        }

        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: projectId={}, projectName={}, philipDocumentNo={}, useDate={}, accountType={}, confirmInfoJson={}",
                    traceId, projectId,projectName, philipDocumentNo, useDate,accountType,confirmInfoJson);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

            //GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            // 先调用支付模块完成扣款 再把返回参数传给公共部分返利
            List<YhglobalCouponLedger> receiveList
                    = JSON.parseObject(confirmInfoJson, new TypeReference<List<YhglobalCouponLedger>>() {
            });
            // 获得货币编码
            String currencyCode = "";
            Integer i = 0; // 成功条数
            Integer length = receiveList.size();// 总条数
            Integer failNo = 0;
            List<WriteOffReturnObject> list = new ArrayList<>();
            String flowCode = DateTimeIdGenerator.nextId(TablePrefixUtil.getTablePrefixByProjectId(projectId), BizNumberType.YHGLOBAL_COUPON_WRITEROFF_FLOW);
            CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByCode(accountType);
            for (YhglobalCouponLedger receive : receiveList) { // 根据选择的多条核销数据进行逐条账户扣减
                Long transferAmount = Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED); // 每笔核销扣减的额度
                failNo = length - i;
                switch (couponConfirmAccountType) { //根据确认的账户类型加载对应的账户余额
                    case COUPON_RECEIVED_ACCOUNT:
                        //todo 账户额度查询使用grpc调用葛灿接口
                        YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest rpcRequest = YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest.newBuilder()
                                .setRpcHeader(rpcHeader)
                                .setProjectId(projectId)
                                .build();
                        YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
                        PaymentCommonGrpc.AccountAmountResponse rpcResponse = yhglobalAccountService.getYhglobalReceivedAccountAmount(rpcRequest);
                        if (rpcResponse.getCouponAmount() < transferAmount) {
                            // 账户额度不足
                            logger.error("accountTotalAmount insufficient: philipDocumentNo={}", philipDocumentNo, receive.getPurchaseCouponLedgerId());
                            //return new RpcResult(ErrorCode.YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), "账户额度不足,共选择了"+length+"条返利,已成功核销"+i+"条, 核销失败"+failNo+"条");
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("unknown couponConfirmAccountType: " + couponConfirmAccountType.getAccountName());
                }
                Long transactionAmount = -transferAmount;//交易金额
                // 调用根据账户类型修改账户额度的方法  有返回值
                WriteOffReturnObject writeOffReturnObject = updateAccount(rpcHeader, projectId, transactionAmount, new Date(), flowCode, couponConfirmAccountType);

                list.add(writeOffReturnObject);// 获取所有核销数据账户的核销结果
                i++; // 统计核销成功的条数

            }
            gongxiaoResult = XpsCouponManager.yhCouponWriteroff(portalConfig.getCouponUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, useDate, accountType, confirmInfoJson, projectName, philipDocumentNo, flowCode, list);

            return new GongxiaoResult(gongxiaoResult.getReturnCode(), "共选择了"+length+"条返利,已成功核销"+i+"条, 核销失败"+(length - i)+"条");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    /**
     * 返利确认和取消确认都要调用的根据账户类型修改 账户额度的方法
     * @param transactionAmount 正值表示增加 负值表示扣款
     * @param couponConfirmAccountType
     */
    WriteOffReturnObject updateAccount(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, long transactionAmount,
                                       Date now, String sourceFlowNo, CouponConfirmAccountType couponConfirmAccountType){

        WriteOffReturnObject writeOffReturnObject = new WriteOffReturnObject();
        switch (couponConfirmAccountType) {
            case COUPON_RECEIVED_ACCOUNT:
                //扣减实收帐户余额
                PaymentCommonGrpc.WriteOffRequest rpcRequest = PaymentCommonGrpc.WriteOffRequest.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(projectId)
                        .setAmount(transactionAmount).setWriteOffFlowNo(sourceFlowNo).setTransactionTime(now.getTime())
                        .build();
                YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
                PaymentCommonGrpc.WriteOffResponse rpcResponse = yhglobalAccountService.updateYhglobalReceivedCouponAccount(rpcRequest);
                if (rpcResponse.getReturnCode() == 0){
                    // 调用成功 返回账户交易前后的额度
                    writeOffReturnObject.setReturnCode(rpcResponse.getReturnCode());
                    writeOffReturnObject.setAmountBeforeTrans(rpcResponse.getAmountBeforeTransaction());
                    writeOffReturnObject.setAmountAfterTrans(rpcResponse.getAmountAfterTransaction());
                }else {
                    logger.error("updateYhglobalReceivedCouponAccount  fail ");
                }
                break;
            default:
                logger.error("unsupported account type: {}", couponConfirmAccountType);
        }
        return writeOffReturnObject;
    }

    /**
     * [收付款管理] > [返利确认处理] 条件查询的入口
     *
     * @param request
     * @param projectId         项目ID
     * @param purchaseOrderNo   采购单号
     * @param supplierOrderNo   订单号
     * @param dateStart         开始日期
     * @param dateEnd           结束日期
     * @param couponStatus      确认状态
     * @param flowNo            流水号
     * @return
     */
    @RequestMapping(value = "/selectByManyConditions")
    @ResponseBody
    public GongxiaoResult selectYhglobalCouponLedgerByManyConditions(HttpServletRequest request, Long projectId,
                                                                     String purchaseOrderNo,
                                                                     String supplierOrderNo, String dateStart,
                                                                     String dateEnd, Byte[] couponStatus,Byte[] couponType, String flowNo,
                                                                     Integer pageNumber, Integer pageSize){
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        String traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        logger.info("#traceId={}# [IN][getProductInfo] params:  projectId={}, purchaseOrderNo={}," +
                        "supplierOrderNo={}, dateStart={}, dateEnd={}, couponStatus={}, flowNo={}, pageNumber={}, pageSize={} ", traceId, projectId,purchaseOrderNo,
                supplierOrderNo,dateStart,dateEnd,couponStatus.toString(),flowNo,pageNumber,pageSize);

        Date dateS = null;
        if (dateStart != null) { //若前端传递了起始日期 则转换为Date对象
            try {
                dateS = DateUtil.stringToDate(dateStart, DateUtil.DATE_FORMAT);
            } catch (Exception e) {
                logger.error("illegalArgument: dateStart={}", dateStart);
                return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage() + ": dateStart=" + dateStart);
            }
        }

        Date dateE = null;
        if (dateEnd != null) { //若前端传递了终止日期 则转换为Date对象
            try {
                dateS = DateUtil.stringToDate(dateEnd, DateUtil.DATE_FORMAT);
            } catch (Exception e) {
                logger.error("illegalArgument: dateStart={}", dateStart);
                return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage() + ": dateEnd=" + dateEnd);
            }
        }


        //TODO: 放到查询的sqlBuider中拼
        String s = null;
        if (couponStatus.length != 0) {
            StringBuffer sb = new StringBuffer();
            for (Byte b : couponStatus) {
                sb.append(b).append(",");
            }
            s = sb.toString().substring(0, sb.lastIndexOf(","));
        }

        GongxiaoResult gongxiaoResult = null;
        try {
//            //通过rpc查询返利确认记录列表

            // GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            gongxiaoResult = XpsCouponManager.selectByManyCondiitonsHasPage(portalConfig.getCouponUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, purchaseOrderNo, supplierOrderNo, dateStart, dateEnd,
                    couponStatus,couponType, flowNo, pageNumber, pageSize);

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;


    }

    /**
     * 返利确认界面数据导出
     *
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response, Long projectId,
                       String purchaseOrderNo,
                       String supplierOrderNo, String dateStart,
                       String dateEnd, Byte[] couponStatus, Byte[] couponType, String flowNo){
        if (projectId == null){
            throw new RuntimeException("the projectId is null");
        }
        String traceId = null;
        traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        logger.info("#traceId={}# [IN][getProductInfo] params:  projectId={}, purchaseOrderNo={}," +
                        "supplierOrderNo={}, dateStart={}, dateEnd={}, couponStatus={}, flowNo={} ", traceId, projectId,purchaseOrderNo,
                supplierOrderNo,dateStart,dateEnd,couponStatus.toString(),flowNo);
        Date dateS = null;
        Date dateE = null;
        try{
            dateS = DateUtil.stringToDate(dateStart,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        try{
            dateE = DateUtil.stringToDate(dateEnd,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        String s = null;
        if (couponStatus.length != 0) {
            StringBuffer sb = new StringBuffer();
            for (Byte b : couponStatus) {
                sb.append(b).append(",");
            }
            s = sb.toString().substring(0, sb.lastIndexOf(","));
        }
        try {
            //GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            XpsCouponManager.selectByManyCondiitonsNoPage(portalConfig.getCouponUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, purchaseOrderNo, supplierOrderNo, dateStart, dateEnd, couponStatus,couponType, flowNo);

            logger.info("#traceId={}# [select transportation ledger and record to export success][OUT]");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
        }


    }
    /**
     *
     * 根据选中的返利主键查询返利详情 以便加载"收付款管理 > 返利确认处理 > 返利确认列表"页面时填写默认值
     *
     * @param ids  返利ID拼接的字符串
     * */
    @RequestMapping(value = "/selectByPurchaseCouponLedgerId")
    @ResponseBody
    public GongxiaoResult selectYhglobalCouponLedgerByPurchaseCouponLedgerId(HttpServletRequest request, String[] ids, Long projectId){
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: traceId:{}, ids={}", traceId,ids.toString());
            GongxiaoResult gongxiaoResult = null;
            // 去重

            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            gongxiaoResult = XpsCouponManager.selectYhglobalCouponLedgerByPurchaseCouponLedgerId(portalConfig.getCouponUrl(), portalConfig.getXpsChannelId(),
                    xpsChannelSecret, portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, ids);

            // 待定
            //  PageInfo<YhglobalCouponLedgerItem> yhglobalCouponLedgerItemPageInfo = ( PageInfo<YhglobalCouponLedgerItem>)gongxiaoResult.getData();
            //   logger.info("#traceId={}# [OUT] select couponledger By PurchaseCouponLedgerId success: result={}",yhglobalCouponLedgerItemPageInfo.toString());
            // gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), list);
            return gongxiaoResult;
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 统计选中的返利的应收等金额总和
     * */
    @RequestMapping(value = "/getTotal")
    @ResponseBody
    public GongxiaoResult getTotalOfSelected(HttpServletRequest request, String[] ids, Long projectId){
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getTotalOfSelected] params: traceId:{}, ids={}", traceId,ids.toString());
            GongxiaoResult gongxiaoResult = null;


            //  logger.info("#traceId={}# [OUT] select couponledger By PurchaseCouponLedgerId success: result={}",grpcList.toString());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            gongxiaoResult = XpsCouponManager.getTotalOfSelected(portalConfig.getCouponUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(),projectId , ids);
            return gongxiaoResult;
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }


    /**
     * "付款管理 > 返利台帐"页面  获取台账数据
     *
     * @param request
     * @param projectId         项目Id
     * @param flowCode          流水号
     * @param accountType       账户类型
     * @param useDateStart      使用开始时间
     * @param useDateEnd
     * @param dateStart
     * @param dateEnd
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/confirm/gets")
    @ResponseBody
    public GongxiaoResult getsCouponConfirmList(HttpServletRequest request, Long projectId, String flowCode,
                                                Integer accountType,
                                                String useDateStart,
                                                String useDateEnd,
                                                String dateStart,
                                                String dateEnd,
                                                Integer pageNumber,
                                                Integer pageSize){
        if (projectId == null) {
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;

        Date dateS = null;
        Date dateE = null;
        Date useDateS = null;
        Date useDateE = null;
        try{
            dateS = DateUtil.stringToDate(dateStart,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        try{
            dateE = DateUtil.stringToDate(dateEnd,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        try{
            useDateS = DateUtil.stringToDate(useDateStart,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        try{
            useDateE = DateUtil.stringToDate(useDateEnd,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsPrepaidInfoList] params: projectId={},flowCode={}, accountType={},useDateStart={},useDateEnd={}," +
                    "dateStart={},dateEnd={}, pageNumber={}, pageSize={}", traceId,projectId, flowCode, accountType,useDateStart,useDateEnd,dateStart,dateEnd,pageNumber,pageSize);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            gongxiaoResult = XpsCouponManager.searchCouponConfirm(portalConfig.getCouponUrl(), portalConfig.getXpsChannelId(),xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, flowCode, accountType, useDateStart, useDateEnd, dateStart, dateEnd, pageNumber, pageSize);


        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * "首付款管理 > 返利台帐"页面 选中返利后，点"取消确认"的入口
     *  @param cancelConfirmJson 取消确认数据的json串包括 流水号 账户类型 实收金额
     * */
    @RequestMapping("/cancelConfirm")
    @ResponseBody
    public GongxiaoResult cancelCouponConfirm(HttpServletRequest request,
                                              Long projectId,
                                              String cancelConfirmJson){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            //logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: traceId:{}, flowCodes={}", traceId, flowCodes.toString());

            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            // List<String> flowCodeList = Arrays.asList(flowCodes.split(","));
            List<YhglobalCouponWriteoffRecord> receiveList
                    = JSON.parseObject(cancelConfirmJson, new TypeReference<List<YhglobalCouponWriteoffRecord>>() {
            });

            for (YhglobalCouponWriteoffRecord record : receiveList) {
                CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByName(record.getAccountTypeName());
                String flow = DateTimeIdGenerator.nextId(TablePrefixUtil.getTablePrefixByProjectId(projectId), BizNumberType.YHGLOBAL_COUPON_WRITEROFF_FLOW);
                Long transactionAmount = Math.round(record.getReceiptAmountDouble()*FXConstant.HUNDRED);
                WriteOffReturnObject object = updateAccount(rpcHeader, projectId, transactionAmount, new Date(), flow, couponConfirmAccountType);
                gongxiaoResult = XpsCouponManager.couponReceiveCancelConfirmBatch(portalConfig.getCouponUrl(), portalConfig.getXpsChannelId(),
                        xpsChannelSecret, portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, record.getFlowNo(), object, flow);
                if (gongxiaoResult.getReturnCode() != 0){
                    throw new RuntimeException("cancel confirm error : in send http to api");
                }
            }
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    @RequestMapping("/generate")
    @ResponseBody
    public GongxiaoResult coupon(HttpServletRequest request,
                                 Long projectId ,
                                 String currencyCode,
                                 String brandOrderNo,
                                 String purchaseOrderNo ,
                                 String purchaseTime ,
                                 Long  purchaseShouldPayAmount ,
                                 Byte couponType,
                                 long couponRatio ,
                                 String projectName,
                                 long supplierId ,
                                 String supplierName ,
                                 String uId ,
                                 String uName){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            String channelId = "1";
            String channelToken="shaver";

            gongxiaoResult = XpsCouponManager.generateYhglobalCouponLedger(portalConfig.getCouponUrl(),channelId, channelToken,uId, uName, projectId, currencyCode, brandOrderNo,
                    purchaseOrderNo,purchaseTime,purchaseShouldPayAmount, couponType, couponRatio, projectName, supplierId, supplierName );
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }



}
