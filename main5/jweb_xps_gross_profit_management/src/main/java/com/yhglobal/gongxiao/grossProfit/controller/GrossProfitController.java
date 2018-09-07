package com.yhglobal.gongxiao.grossProfit.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ConfirmStatus;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.grossProfit.Service.GrossProfitService;
import com.yhglobal.gongxiao.coupon.config.PortalConfig;

import com.yhglobal.gongxiao.grossProfit.model.GrossProfitItem;
import com.yhglobal.gongxiao.grossProfit.model.YhglobalToReceiveGrossProfitLedger;
import com.yhglobal.gongxiao.grossProfit.model.YhglobalToReceiveGrossProfitLedgerWriteoffRecord;
import com.yhglobal.gongxiao.grossProfit.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 毛利的相关操作入口
 *
 * @author 王帅
 */
@Controller
@RequestMapping("/grossProfit")
public class GrossProfitController {

     static Logger logger = LoggerFactory.getLogger(GrossProfitController.class);

    @Autowired
    private PortalConfig portalConfig;

    @Autowired
    GrossProfitService grossProfitService;

//    public static boolean checkChannel(String channelId, String channelToken,ChannelServiceStructure.FoundationXpsSourceChannel couponChannel ){
//
//        // CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//        // 数据库存储密文  传入参数为明文 对明文加密后与数据库数据对比
//        // 比较参数channelToken 与查询结果 的channelSecret id与secret均相同则验证通过
//        CouponChannel token = new CouponChannel();
//        token.setChannelSecret(channelToken);
//        if (token.getChannelSecret().equals(couponChannel.getXpsChannelSecret()) &&
//                channelId.equals(couponChannel.getXpsChannelId()) ) {
//            return true;
//        }else {
//            return false;
//        }
//    }

    @ResponseBody
    @RequestMapping("/generate")
    public GongxiaoResult couponGenerate(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            GrossProfitGenerateRequest grossProfitGenerateRequest = JSON.parseObject(jsonStr, GrossProfitGenerateRequest.class);

            // 从对象中获取channelId channelToken
            String channelId =  grossProfitGenerateRequest.getChannelId();
            String channelToken =  grossProfitGenerateRequest.getChannelToken();

            //todo 查询channel表校验token 使用grpc调用陈浩接口 已有公共的工具类
//            ChannelServiceStructure.FoundationXpsSourceChannel channel = GetChannelById.getChannel(channelId);
//            if (!checkChannel(channelToken,channel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }
            // 获取其他业务参数 并调用方法
            Long projectId =  grossProfitGenerateRequest.getProjectId();
            String currencyCode =  grossProfitGenerateRequest.getCurrencyCode();
            String purchaseOrderNo =  grossProfitGenerateRequest.getPurchaseOrderNo();
            BigDecimal estimatedGrossProfitAmount = grossProfitGenerateRequest.getEstimatedGrossProfitAmount();
            BigDecimal confirmedAmount = grossProfitGenerateRequest.getConfirmedAmount();
            String projectName = grossProfitGenerateRequest.getProjectName();
            BigDecimal receivedAmount = grossProfitGenerateRequest.getReceivedAmount();
            String salesOrderNo = grossProfitGenerateRequest.getSalesOrderNo();
            Date salesTime = grossProfitGenerateRequest.getSalesTime();
            String tablePrefix = grossProfitGenerateRequest.getTablePrefix();
            BigDecimal toBeConfirmAmount = grossProfitGenerateRequest.getToBeConfirmAmount();

            String uId =  grossProfitGenerateRequest.getUserId();
            String uName =  grossProfitGenerateRequest.getUserName();
            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][grossProfitGenerate] params: projectId={}, currencyCode={}, purchaseOrderNo={}, purchaseTime={}," +
                            " estimatedGrossProfitAmount ={},confirmedAmount={},toBeConfirmAmount={}, receivedAmount={},confirmStatus={}, projectName={}," +
                            "salesOrderNo={}, salesTime={},tablePrefix={}  ", projectId,currencyCode, purchaseOrderNo,estimatedGrossProfitAmount,
                    confirmedAmount, toBeConfirmAmount, receivedAmount,  projectName, salesOrderNo, salesTime, tablePrefix);
            // RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, projectId+"","123", "gecan");
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, uId, uName);

            RpcResult rpcResult = grossProfitService.generateGrossProfitLedger(rpcHeader, projectId, currencyCode, purchaseOrderNo, estimatedGrossProfitAmount,
                    confirmedAmount, projectName, receivedAmount, salesOrderNo, salesTime, tablePrefix, toBeConfirmAmount);
            gongxiaoResult = new GongxiaoResult(rpcResult.getCode(), rpcResult.getMessage());

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(),ErrorCode.UNKNOWN_ERROR.getMessage());
        }

        return gongxiaoResult;
    }

    @ResponseBody
    @RequestMapping("/writeoff")
    public GongxiaoResult couponWriteoff(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            GrossProfitWriteoffRequest grossProfitWriteoffRequest = JSON.parseObject(jsonStr, GrossProfitWriteoffRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = grossProfitWriteoffRequest.getChannelId();
            String channelToken = grossProfitWriteoffRequest.getChannelToken();

            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            String confirmInfoJson = grossProfitWriteoffRequest.getConfirmInfoJson();
            String currencyCode = grossProfitWriteoffRequest.getCurrencyCode();
            Byte differenceAmountAdjustPattern = grossProfitWriteoffRequest.getDifferenceAmountAdjustPattern();
            Long projectId = grossProfitWriteoffRequest.getProjectId();
            Byte transferIntoPattern = grossProfitWriteoffRequest.getTransferIntoPattern();
            String useDate = grossProfitWriteoffRequest.getUseDate();

            List<WriteOffReturnObject> list = grossProfitWriteoffRequest.getList();
            String flowNo = grossProfitWriteoffRequest.getFlowNo();
            String uId = grossProfitWriteoffRequest.getUserId();
            String uName = grossProfitWriteoffRequest.getUserName();

            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: projectId={}, currencyCode={}, differenceAmountAdjustPattern={}, transferIntoPattern={}, " +
                    "useDate={}, confirmInfoJson={}", projectId,currencyCode, differenceAmountAdjustPattern, transferIntoPattern, useDate, confirmInfoJson);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, uId, uName);

            RpcResult rpcResult = grossProfitService.yhGrossProfitWriteroff(rpcHeader, confirmInfoJson, currencyCode, differenceAmountAdjustPattern, projectId,
                    transferIntoPattern, useDate, flowNo, list);
            // 根据返回码设置对应的gongxiaoResult
            gongxiaoResult = new GongxiaoResult(rpcResult.getCode(), rpcResult.getMessage());

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(),ErrorCode.UNKNOWN_ERROR.getMessage());

        }

        return gongxiaoResult;
    }

    @ResponseBody
    @RequestMapping("/selectHasPage")
    public GongxiaoResult selectHasPage(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String requestJson = IOUtils.toString(inputStream, "utf-8");
            SelectGrossProfitRequest selectGrossProfitRequest = JSON.parseObject(requestJson, SelectGrossProfitRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = selectGrossProfitRequest.getChannelId();
            String channelToken = selectGrossProfitRequest.getChannelToken();

            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Byte[] grossProfitStatus = selectGrossProfitRequest.getGrossProfitStatus();
            String dateStart = selectGrossProfitRequest.getDateStart();
            String dateEnd = selectGrossProfitRequest.getDateEnd();
            String flowNo = selectGrossProfitRequest.getFlowNo();
            Long projectId = selectGrossProfitRequest.getProjectId();
            String purchaseOrderNo = selectGrossProfitRequest.getPurchaseOrderNo();
            Integer pageNumber = selectGrossProfitRequest.getPageNumber();
            Integer pageSize = selectGrossProfitRequest.getPageSize();
            String salesOrderNo = selectGrossProfitRequest.getSalesOrderNo();

            Date dateS = DateUtil.stringToDate(dateStart);
            Date dateE = DateUtil.stringToDate(dateEnd);

            String uId = selectGrossProfitRequest.getUserId();
            String uName = selectGrossProfitRequest.getUserName();

            String s = null;
            if (grossProfitStatus != null) {
                if (grossProfitStatus.length != 0) {
                    StringBuffer sb = new StringBuffer();
                    for (Byte b : grossProfitStatus) {
                        sb.append(b).append(",");
                    }
                    s = sb.toString().substring(0, sb.lastIndexOf(","));
                }
            }
            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: projectId={},  projectId={}, purchaseOrderNo={}," +
                            " salesOrderNo={}, dateS={}, dateE={}, s={},flowNo={}, pageNumber={}, pageSize={}", projectId, purchaseOrderNo, salesOrderNo,
                    dateS, dateE, s, flowNo, pageNumber, pageSize);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,uId, uName);
            PageInfo<GrossProfitItem> grossProfitItemPageInfo = grossProfitService.selectByManyConditionsHasPage(rpcHeader, s, flowNo, projectId, purchaseOrderNo, pageNumber, pageSize, salesOrderNo,
                    dateS, dateE);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),grossProfitItemPageInfo);

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(),ErrorCode.UNKNOWN_ERROR.getMessage());

        }

        return gongxiaoResult;
    }

    @ResponseBody
    @RequestMapping("/selectByIds")
    public GongxiaoResult selectById(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            SelectGrossProfitByIdRequest selectGrossProfitByIdRequest = JSON.parseObject(jsonStr, SelectGrossProfitByIdRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = selectGrossProfitByIdRequest.getChannelId();
            String channelToken = selectGrossProfitByIdRequest.getChannelToken();

            String uId = selectGrossProfitByIdRequest.getUserId();
            String uName = selectGrossProfitByIdRequest.getUserName();
            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            String[] ids = selectGrossProfitByIdRequest.getIds();
            Long projectId = selectGrossProfitByIdRequest.getProjectId();
            // 去重
            Set<String> set = new TreeSet<>();
            set.addAll(Arrays.asList(ids));
            List<String> idList = new ArrayList<>(set);

            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: traceId:{}, ids={}", traceId,ids);
            List<YhglobalToReceiveGrossProfitLedger> list = grossProfitService.selectGrossProfitLedgerByIds(projectId, idList);

            // 过滤掉确认状态为全部确认的数据
            for (int i = 0;i<list.size();i++){
                if (list.get(i).getConfirmStatus() == ConfirmStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode()){
                    list.remove(i);
                    i--;
                }
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] select couponledger By PurchaseCouponLedgerId success: result={}",list.toString());

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(),ErrorCode.UNKNOWN_ERROR.getMessage());

        }

        return gongxiaoResult;
    }

    @ResponseBody
    @RequestMapping("/getTotalOfSelected")
    public GongxiaoResult getTotalOfSelected(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            GrossProfitGetTotalRequest grossProfitGetTotalRequest = JSON.parseObject(jsonStr, GrossProfitGetTotalRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = grossProfitGetTotalRequest.getChannelId();
            String channelToken = grossProfitGetTotalRequest.getChannelToken();

            String uId = grossProfitGetTotalRequest.getUserId();
            String uName = grossProfitGetTotalRequest.getUserName();

            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            String[] ids = grossProfitGetTotalRequest.getIds();
            if (ids == null){
                YhglobalToReceiveGrossProfitLedger yhglobalToReceiveGrossProfitLedger = new YhglobalToReceiveGrossProfitLedger();
                yhglobalToReceiveGrossProfitLedger.setEstimatedGrossProfitAmount(new BigDecimal(0.0));
                yhglobalToReceiveGrossProfitLedger.setToBeConfirmAmount(new BigDecimal(0.0));
                yhglobalToReceiveGrossProfitLedger.setConfirmedAmount(new BigDecimal(0.0));
                yhglobalToReceiveGrossProfitLedger.setReceivedAmount(new BigDecimal(0.0));

                return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), yhglobalToReceiveGrossProfitLedger);
            }

            Long projectId = grossProfitGetTotalRequest.getProjectId();
            // 去重
            Set<String> set = new TreeSet<>();
            set.addAll(Arrays.asList(ids));
            List<String> idList = new ArrayList<>(set);
            // String[] arrayIds = (String[]) idList.toArray();
            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: traceId:{}, ids={}", traceId, ids);
            List<YhglobalToReceiveGrossProfitLedger> list = grossProfitService.selectGrossProfitLedgerByIds(projectId, idList);

            // 此处返回模型不再新建 直接使用YhglobalCouponLedger部分字段
            BigDecimal receiveAmountTotal = new BigDecimal(0.0);  // 应收
            BigDecimal toReceiveAmountTotal = new BigDecimal(0.0); // 未收
            BigDecimal confirmAmountTotal = new BigDecimal(0.0);  // 确认
            BigDecimal receiptAmountTotal = new BigDecimal(0.0);  // 实收
            if (ids.length!=0) {
                for (YhglobalToReceiveGrossProfitLedger ledger : list) {
                    receiveAmountTotal = ledger.getEstimatedGrossProfitAmount().add(receiveAmountTotal);
                    toReceiveAmountTotal = ledger.getToBeConfirmAmount().add(toReceiveAmountTotal);
                    confirmAmountTotal = ledger.getConfirmedAmount().add(confirmAmountTotal);
                    receiptAmountTotal = ledger.getReceivedAmount().add(receiptAmountTotal);
                }
            }
            YhglobalToReceiveGrossProfitLedger yhglobalToReceiveGrossProfitLedger = new YhglobalToReceiveGrossProfitLedger();
            yhglobalToReceiveGrossProfitLedger.setEstimatedGrossProfitAmount(receiveAmountTotal);
            yhglobalToReceiveGrossProfitLedger.setToBeConfirmAmount(toReceiveAmountTotal);
            yhglobalToReceiveGrossProfitLedger.setConfirmedAmount(confirmAmountTotal);
            yhglobalToReceiveGrossProfitLedger.setReceivedAmount(receiptAmountTotal);

            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), yhglobalToReceiveGrossProfitLedger);
            logger.info("#traceId={}# [OUT] select couponledger By PurchaseCouponLedgerId success: result={}",list.toString());

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(),ErrorCode.UNKNOWN_ERROR.getMessage());

        }

        return gongxiaoResult;
    }

    @ResponseBody
    @RequestMapping("/searchGrossProfitConfirm")
    public GongxiaoResult searchCouponConfirm(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            SearchGrossProfitConfirmRequest searchGrossProfitConfirmRequest = JSON.parseObject(jsonStr, SearchGrossProfitConfirmRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = searchGrossProfitConfirmRequest.getChannelId();
            String channelToken = searchGrossProfitConfirmRequest.getChannelToken();

            String uId = searchGrossProfitConfirmRequest.getUserId();
            String uName = searchGrossProfitConfirmRequest.getUserName();

            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Integer accountType = searchGrossProfitConfirmRequest.getAccountType();
            String dateStart = searchGrossProfitConfirmRequest.getDateStart();
            String dateEnd = searchGrossProfitConfirmRequest.getDateEnd();
            String flowCode = searchGrossProfitConfirmRequest.getFlowCode();
            Long projectId = searchGrossProfitConfirmRequest.getProjectId();
            String useDateStart = searchGrossProfitConfirmRequest.getUseDateStart();
            String useDateEnd = searchGrossProfitConfirmRequest.getUseDateEnd();
            Integer pageNumber = searchGrossProfitConfirmRequest.getPageNumber();
            Integer pageSize = searchGrossProfitConfirmRequest.getPageSize();

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

                traceId= YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
                logger.info("#traceId={}# [IN][getsPrepaidInfoList] params: projectId={},flowCode={}, accountType={},useDateStart={},useDateEnd={}," +
                        "dateStart={},dateEnd={}, pageNumber={}, pageSize={}", traceId,projectId, flowCode, accountType,useDateStart,useDateEnd,dateStart,dateEnd,pageNumber,pageSize);
                GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, uId, uName);
                // 查询流水
            PageInfo<YhglobalToReceiveGrossProfitLedgerWriteoffRecord> recordPageInfo = grossProfitService.searchGrossProfitConfirm(rpcHeader, accountType, flowCode, projectId, pageNumber, pageSize, dateS, dateE, useDateS, useDateE);


            logger.info("#traceId={}# [OUT] getsCouponConfirmList success:result={}", traceId,recordPageInfo);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), recordPageInfo);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

        return gongxiaoResult;
    }

    @ResponseBody
    @RequestMapping("/selectNoPage")
    public GongxiaoResult selectNoPage(HttpServletRequest request, HttpServletResponse response){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String requestJson = IOUtils.toString(inputStream, "utf-8");
            SelectGrossProfitRequest selectGrossProfitRequest = JSON.parseObject(requestJson, SelectGrossProfitRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = selectGrossProfitRequest.getChannelId();
            String channelToken = selectGrossProfitRequest.getChannelToken();

            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Byte[] grossProfitStatus = selectGrossProfitRequest.getGrossProfitStatus();
            String dateStart = selectGrossProfitRequest.getDateStart();
            String dateEnd = selectGrossProfitRequest.getDateEnd();
            String flowNo = selectGrossProfitRequest.getFlowNo();
            Long projectId = selectGrossProfitRequest.getProjectId();
            String purchaseOrderNo = selectGrossProfitRequest.getPurchaseOrderNo();
            String salesOrderNo = selectGrossProfitRequest.getSalesOrderNo();

            Date dateS = DateUtil.stringToDate(dateStart);
            Date dateE = DateUtil.stringToDate(dateEnd);

            String uId = selectGrossProfitRequest.getUserId();
            String uName = selectGrossProfitRequest.getUserName();

            String s = null;
            if (grossProfitStatus != null) {
                if (grossProfitStatus.length != 0) {
                    StringBuffer sb = new StringBuffer();
                    for (Byte b : grossProfitStatus) {
                        sb.append(b).append(",");
                    }
                    s = sb.toString().substring(0, sb.lastIndexOf(","));
                }
            }
            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: projectId={},  projectId={}, purchaseOrderNo={}," +
                            " salesOrderNo={}, dateS={}, dateE={}, s={},flowNo={}, pageNumber={}, pageSize={}", projectId, purchaseOrderNo, salesOrderNo,
                    dateS, dateE, s, flowNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,uId, uName);
            List<GrossProfitItem> grossProfitItemList = grossProfitService.selectByManyConditionsNoPage(rpcHeader, s, flowNo, projectId, purchaseOrderNo,  salesOrderNo,
                    dateS, dateE);

            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),grossProfitItemList);

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(),ErrorCode.UNKNOWN_ERROR.getMessage());

        }

        return gongxiaoResult;

    }

    @ResponseBody
    @RequestMapping("/receiveCancel")
    public GongxiaoResult receiveCancel(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            GrossProfitReceiveCancelConfirmBatchRequest cancelConfirmBatchRequest = JSON.parseObject(jsonStr, GrossProfitReceiveCancelConfirmBatchRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = cancelConfirmBatchRequest.getChannelId();
            String channelToken = cancelConfirmBatchRequest.getChannelToken();

            String uId = cancelConfirmBatchRequest.getUserId();
            String uName = cancelConfirmBatchRequest.getUserName();

            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Long projectId = cancelConfirmBatchRequest.getProjectId();
            String flowCodes = cancelConfirmBatchRequest.getFlowCodes();

            WriteOffReturnObject object = cancelConfirmBatchRequest.getObject();
            String flow = cancelConfirmBatchRequest.getFlow();

            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: traceId:{}, flowCodes={}", traceId, flowCodes);
            GongxiaoRpc.RpcHeader rpcHeader = com.yhglobal.gongxiao.util.RpcHeaderUtil.createRpcHeader(traceId, uId, uName);

            RpcResult rpcResult = grossProfitService.receiveCancelConfirmBatch(rpcHeader, projectId, flowCodes, object, flow);
            if (rpcResult.getCode() == ErrorCode.SUCCESS.getErrorCode()) {
                logger.info("#traceId={}# [OUT] cancel coupon confirm success: flowCodes={}", traceId, flowCodes);
            } else {
                logger.error("#traceId={}# [OUT] fail to cancel coupon confirm: flowCodes={}", traceId, flowCodes);
            }
            // 根据返回码设置对应的gongxiaoResult
            gongxiaoResult = new GongxiaoResult(rpcResult.getCode(), rpcResult.getMessage());

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(),ErrorCode.UNKNOWN_ERROR.getMessage());

        }

        return gongxiaoResult;
    }

//    public static <T> getObjectFromRequest(ServletRequest request,T t){
//
//        T t1 = null;
//        try {
//            ServletInputStream inputStream = request.getInputStream();
//            String jsonStr = IOUtils.toString(inputStream, "utf-8");
//            t1 = JSON.parseObject(jsonStr, T.class);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return t1;
//    }

}
