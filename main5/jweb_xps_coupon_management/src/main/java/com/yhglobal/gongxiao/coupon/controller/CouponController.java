package com.yhglobal.gongxiao.coupon.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.coupon.Service.ChannelService;
import com.yhglobal.gongxiao.coupon.Service.CouponService;
import com.yhglobal.gongxiao.coupon.config.PortalConfig;
import com.yhglobal.gongxiao.coupon.constant.CouponLedgerCouponStatus;
import com.yhglobal.gongxiao.coupon.model.*;
import com.yhglobal.gongxiao.coupon.utils.GetChannelById;
import com.yhglobal.gongxiao.coupon.utils.RpcHeaderUtil;
import com.yhglobal.gongxiao.coupon.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.model.WriteOffReturnObject;

import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 返利的相关操作入口
 *
 * @author 王帅
 */
@Controller
@RequestMapping("/coupon")
public class CouponController {

     static Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private PortalConfig portalConfig;

    @Autowired
    CouponService couponService;

    @Autowired
    ChannelService channelService;



    public static boolean checkChannel(String channelId, String channelToken,ChannelServiceStructure.FoundationXpsSourceChannel couponChannel ){

        // CouponChannel couponChannel = channelService.selectByChannelId(channelId);
        // 数据库存储密文  传入参数为明文 对明文加密后与数据库数据对比
        // 比较参数channelToken 与查询结果 的channelSecret id与secret均相同则验证通过
        CouponChannel token = new CouponChannel();
        token.setChannelSecret(channelToken);
        if (token.getChannelSecret().equals(couponChannel.getXpsChannelSecret()) &&
                channelId.equals(couponChannel.getXpsChannelId()) ) {
            return true;
        }else {
            return false;
        }
    }

    @ResponseBody
    @RequestMapping("/generate")
    public GongxiaoResult couponGenerate(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            CouponGenerateRequest couponGenerateRequest = JSON.parseObject(jsonStr, CouponGenerateRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = couponGenerateRequest.getChannelId();
            String channelToken = couponGenerateRequest.getChannelToken();

            //todo 查询channel表校验token 使用grpc调用陈浩接口
//            ChannelServiceStructure.FoundationXpsSourceChannel channel = GetChannelById.getChannel(channelId);
//            if (!checkChannel(channelToken,channel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }


            // 获取其他业务参数 并调用方法
            Long projectId = couponGenerateRequest.getProjectId();
            String currencyCode = couponGenerateRequest.getCurrencyCode();
            String brandOrderNo = couponGenerateRequest.getBrandOrderNo();
            String purchaseOrderNo = couponGenerateRequest.getPurchaseOrderNo();
            String purchaseTime = couponGenerateRequest.getPurchaseTime();
            Long  purchaseShouldPayAmount = couponGenerateRequest.getPurchaseShouldPayAmount();
            Byte couponType = couponGenerateRequest.getCouponType();
            long couponRatio  = couponGenerateRequest.getCouponRatio();
            String projectName = couponGenerateRequest.getProjectName();
            long supplierId  = couponGenerateRequest.getSupplierId();
            String supplierName = couponGenerateRequest.getSupplierName();

            String uId = couponGenerateRequest.getUserId();
            String uName = couponGenerateRequest.getUserName();
            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][couponGenerate] params: projectId={}, currencyCode={}, brandOrderNo={}, " +
                            "purchaseOrderNo={}, purchaseTime={}, purchaseShouldPayAmount={}", projectId,currencyCode, brandOrderNo, purchaseOrderNo, purchaseTime, purchaseShouldPayAmount);
            // RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, projectId+"","123", "gecan");
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, projectId+"",uId, uName);

            RpcResult rpcResult = couponService.generateYhglobalCouponLedger(rpcHeader,projectId, currencyCode, brandOrderNo, purchaseOrderNo, purchaseTime,
                    purchaseShouldPayAmount,(int)couponType, couponRatio, projectName, supplierId, supplierName);
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
            CouponWriteoffRequest couponWriteoffRequest = JSON.parseObject(jsonStr, CouponWriteoffRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = couponWriteoffRequest.getChannelId();
            String channelToken = couponWriteoffRequest.getChannelToken();

            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Integer accountType = couponWriteoffRequest.getAccountType();
            Long projectId = couponWriteoffRequest.getProjectId();
            String projectName = couponWriteoffRequest.getProjectName();
            String confirmInfoJson = couponWriteoffRequest.getConfirmInfoJson();
            String philipDocumentNo = couponWriteoffRequest.getPhilipDocumentNo();
            String useDate = couponWriteoffRequest.getUseDate();

            String uId = couponWriteoffRequest.getUserId();
            String uName = couponWriteoffRequest.getUserName();
            String flowNo = couponWriteoffRequest.getFlowNo();
            List<WriteOffReturnObject> list = couponWriteoffRequest.getList();

            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: projectId={}, projectName={}, accountType={}, philipDocumentNo={}, " +
                    "useDate={}, confirmInfoJson={}", projectId,projectName, accountType, philipDocumentNo, useDate, confirmInfoJson);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createGRpcHeader(traceId, projectId.intValue(),uId, uName);

            RpcResult rpcResult = couponService.yhCouponWriteroff(rpcHeader, projectId, useDate, accountType, confirmInfoJson, projectName, philipDocumentNo, flowNo, list);
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
            CouponSelectHasPageRequest couponSelectHasPageRequest = JSON.parseObject(requestJson, CouponSelectHasPageRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = couponSelectHasPageRequest.getChannelId();
            String channelToken = couponSelectHasPageRequest.getChannelToken();

            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }
            if ("philip".equals(channelId)){
                logger.info("check success");
            }

            // 从对象中回去参数 并调用方法
            Byte[] couponStatus = couponSelectHasPageRequest.getCouponStatus();
            Byte[] couponType = couponSelectHasPageRequest.getCouponType();
            String dateStart = couponSelectHasPageRequest.getDateStart();
            String dateEnd = couponSelectHasPageRequest.getDateEnd();
            String flowNo = couponSelectHasPageRequest.getFlowNo();
            Long projectId = couponSelectHasPageRequest.getProjectId();
            String supplierOrderNo = couponSelectHasPageRequest.getSupplierOrderNo();
            String purchaseOrderNo = couponSelectHasPageRequest.getPurchaseOrderNo();
            Integer pageNumber = couponSelectHasPageRequest.getPageNumber();
            Integer pageSize = couponSelectHasPageRequest.getPageSize();

            Date dateS = DateUtil.stringToDate(dateStart);
            Date dateE = DateUtil.stringToDate(dateEnd);

            String uId = couponSelectHasPageRequest.getUserId();
            String uName = couponSelectHasPageRequest.getUserName();

            String s = null;
            if (couponStatus != null) {
                if (couponStatus.length != 0) {
                    StringBuffer sb = new StringBuffer();
                    for (Byte b : couponStatus) {
                        sb.append(b).append(",");
                    }
                    s = sb.toString().substring(0, sb.lastIndexOf(","));
                }
            }

            String sType = null;
            if (couponType != null) {
                if (couponType.length != 0) {
                    StringBuffer sb = new StringBuffer();
                    for (Byte b : couponType) {
                        sb.append(b).append(",");
                    }
                    sType = sb.toString().substring(0, sb.lastIndexOf(","));
                }
            }
            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: projectId={},  projectId={}, purchaseOrderNo={}," +
                            " supplierOrderNo={}, dateS={}, dateE={}, s={},flowNo={}, pageNumber={}, pageSize={}", projectId, purchaseOrderNo, supplierOrderNo, dateS, dateE, s,
                    flowNo, pageNumber, pageSize);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, projectId+"",uId, uName);
            PageInfo<YhglobalCouponLedgerItem> yhglobalCouponLedgerItemPageInfo = couponService.selectByManyConditionsHasPage(rpcHeader, projectId, purchaseOrderNo, supplierOrderNo, dateS, dateE, s,sType,
                    flowNo, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),yhglobalCouponLedgerItemPageInfo);

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(),ErrorCode.UNKNOWN_ERROR.getMessage());

        }

        return gongxiaoResult;
    }

    @ResponseBody
    @RequestMapping("/selectById")
    public GongxiaoResult selectById(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            CouponSelectByIdRequest couponSelectByIdRequest = JSON.parseObject(jsonStr, CouponSelectByIdRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = couponSelectByIdRequest.getChannelId();
            String channelToken = couponSelectByIdRequest.getChannelToken();

            String uId = couponSelectByIdRequest.getUserId();
            String uName = couponSelectByIdRequest.getUserName();
            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            String[] ids = couponSelectByIdRequest.getIds();
            Long projectId = couponSelectByIdRequest.getProjectId();
            // 去重
            Set<String> set = new TreeSet<>();
            set.addAll(Arrays.asList(ids));
            List<String> idList = new ArrayList<>(set);

            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: traceId:{}, ids={}", traceId,ids);
            List<YhglobalCouponLedger> list = couponService.selectYhglobalCouponLedgerByPurchaseCouponLedgerId(projectId,idList);
            // 过滤掉确认状态为全部确认的数据
            for (int i = 0;i<list.size();i++){
                if (list.get(i).getCouponStatus() == CouponLedgerCouponStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode()){
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
            CouponSelectByIdRequest couponSelectByIdRequest = JSON.parseObject(jsonStr, CouponSelectByIdRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = couponSelectByIdRequest.getChannelId();
            String channelToken = couponSelectByIdRequest.getChannelToken();

            String uId = couponSelectByIdRequest.getUserId();
            String uName = couponSelectByIdRequest.getUserName();

            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            String[] ids = couponSelectByIdRequest.getIds();
            if (ids == null){
                YhglobalCouponLedger yhglobalCouponLedger = new YhglobalCouponLedger();
                yhglobalCouponLedger.setEstimatedAmountDouble(0D);
                yhglobalCouponLedger.setToBeConfirmAmountDouble(0D);
                yhglobalCouponLedger.setConfirmAmountDouble(0D);
                yhglobalCouponLedger.setReceiptAmountDouble(0D);

                return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), yhglobalCouponLedger);
            }

            Long projectId = couponSelectByIdRequest.getProjectId();
            // 去重
            Set<String> set = new TreeSet<>();
            set.addAll(Arrays.asList(ids));
            List<String> idList = new ArrayList<>(set);
            // String[] arrayIds = (String[]) idList.toArray();

            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: traceId:{}, ids={}", traceId, ids);
            List<YhglobalCouponLedger> list = couponService.selectYhglobalCouponLedgerByPurchaseCouponLedgerId(projectId, idList);

            // 此处返回模型不再新建 直接使用YhglobalCouponLedger部分字段
            Long receiveAmountTotal = 0l;  // 应收
            Long toReceiveAmountTotal = 0l; // 未收
            Long confirmAmountTotal = 0l;  // 确认
            Long receiptAmountTotal = 0l;  // 实收
            if (ids.length!=0) {
                for (YhglobalCouponLedger ledger : list) {
                    receiveAmountTotal += ledger.getEstimatedCouponAmount();
                    toReceiveAmountTotal += ledger.getToBeConfirmAmount();
                    confirmAmountTotal += ledger.getConfirmedCouponAmount();
                    receiptAmountTotal += ledger.getReceivedCouponAmount();
                }
            }
            YhglobalCouponLedger yhglobalCouponLedger = new YhglobalCouponLedger();
            yhglobalCouponLedger.setEstimatedAmountDouble(receiveAmountTotal / FXConstant.HUNDRED_DOUBLE);
            yhglobalCouponLedger.setToBeConfirmAmountDouble(toReceiveAmountTotal / FXConstant.HUNDRED_DOUBLE);
            yhglobalCouponLedger.setConfirmAmountDouble(confirmAmountTotal / FXConstant.HUNDRED_DOUBLE);
            yhglobalCouponLedger.setReceiptAmountDouble(receiptAmountTotal / FXConstant.HUNDRED_DOUBLE);

            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), yhglobalCouponLedger);
            logger.info("#traceId={}# [OUT] select couponledger By PurchaseCouponLedgerId success: result={}",list.toString());

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(),ErrorCode.UNKNOWN_ERROR.getMessage());

        }

        return gongxiaoResult;
    }

    @ResponseBody
    @RequestMapping("/searchCouponConfirm")
    public GongxiaoResult searchCouponConfirm(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            SearchCouponConfirmRequest searchCouponConfirmRequest = JSON.parseObject(jsonStr, SearchCouponConfirmRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = searchCouponConfirmRequest.getChannelId();
            String channelToken = searchCouponConfirmRequest.getChannelToken();

            String uId = searchCouponConfirmRequest.getUserId();
            String uName = searchCouponConfirmRequest.getUserName();

            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Integer accountType = searchCouponConfirmRequest.getAccountType();
            String dateStart = searchCouponConfirmRequest.getDateStart();
            String dateEnd = searchCouponConfirmRequest.getDateEnd();
            String flowCode = searchCouponConfirmRequest.getFlowCode();
            Long projectId = searchCouponConfirmRequest.getProjectId();
            String useDateStart = searchCouponConfirmRequest.getUseDateStart();
            String useDateEnd = searchCouponConfirmRequest.getUseDateEnd();
            Integer pageNumber = searchCouponConfirmRequest.getPageNumber();
            Integer pageSize = searchCouponConfirmRequest.getPageSize();

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
                RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, projectId+"",uId, uName);
                // 查询流水
                PageInfo<YhglobalCouponWriteoffRecord> pageInfo = couponService.searchCouponConfirm(rpcHeader, projectId, flowCode, accountType, useDateS, useDateE, dateS, dateE, pageNumber, pageSize);


            logger.info("#traceId={}# [OUT] getsCouponConfirmList success:result={}", traceId,pageInfo);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
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
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            CouponSelectHasPageRequest couponSelectHasPageRequest = JSON.parseObject(jsonStr, CouponSelectHasPageRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = couponSelectHasPageRequest.getChannelId();
            String channelToken = couponSelectHasPageRequest.getChannelToken();

            String uId = couponSelectHasPageRequest.getUserId();
            String uName = couponSelectHasPageRequest.getUserName();

            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中获取参数 并调用方法
            Byte[] couponStatus = couponSelectHasPageRequest.getCouponStatus();
            Byte[] couponType = couponSelectHasPageRequest.getCouponType();
            String dateStart = couponSelectHasPageRequest.getDateStart();
            String dateEnd = couponSelectHasPageRequest.getDateEnd();
            String flowNo = couponSelectHasPageRequest.getFlowNo();
            Long projectId = couponSelectHasPageRequest.getProjectId();
            String supplierOrderNo = couponSelectHasPageRequest.getSupplierOrderNo();
            String purchaseOrderNo = couponSelectHasPageRequest.getPurchaseOrderNo();

            Date dateS = DateUtil.stringToDate(dateStart);
            Date dateE = DateUtil.stringToDate(dateEnd);

            String s = null;
            if (couponStatus.length != 0) {
                StringBuffer sb = new StringBuffer();
                for (Byte b : couponStatus) {
                    sb.append(b).append(",");
                }
                s = sb.toString().substring(0, sb.lastIndexOf(","));
            }

            String sType = null;
            if (couponType != null) {
                if (couponType.length != 0) {
                    StringBuffer sb = new StringBuffer();
                    for (Byte b : couponType) {
                        sb.append(b).append(",");
                    }
                    sType = sb.toString().substring(0, sb.lastIndexOf(","));
                }
            }
            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: projectId={},  projectId={}, purchaseOrderNo={}," +
                            " supplierOrderNo={}, dateS={}, dateE={}, s={},flowNo={}, pageNumber={}, pageSize={}", projectId, purchaseOrderNo, supplierOrderNo, dateS, dateE, s,
                    flowNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, projectId+"",uId, uName);
            List<YhglobalCouponLedgerItem> yhglobalCouponLedgerItems = couponService.selectByManyConditionsNoPage(rpcHeader, projectId, purchaseOrderNo, supplierOrderNo, dateS, dateE, s,sType,
                    flowNo);

//            String downFileName = new String("供应商返利流水列表.xls");
//            ExcelUtil<YhglobalCouponLedgerItem> util = new ExcelUtil<YhglobalCouponLedgerItem>(YhglobalCouponLedgerItem.class);
//            Workbook workbook = util.getListToExcel(yhglobalCouponLedgerItems,"流水列表");
//            ExcelDownUtil.resetResponse(response,workbook,downFileName);
//            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),yhglobalCouponLedgerItems);

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
            CouponReceiveCancelConfirmBatchRequest couponReceiveCancelConfirmBatchRequest = JSON.parseObject(jsonStr, CouponReceiveCancelConfirmBatchRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = couponReceiveCancelConfirmBatchRequest.getChannelId();
            String channelToken = couponReceiveCancelConfirmBatchRequest.getChannelToken();

            String uId = couponReceiveCancelConfirmBatchRequest.getUserId();
            String uName = couponReceiveCancelConfirmBatchRequest.getUserName();
            WriteOffReturnObject object = couponReceiveCancelConfirmBatchRequest.getObject();
            // 查询channel表校验token
//            CouponChannel couponChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken,couponChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Long projectId = couponReceiveCancelConfirmBatchRequest.getProjectId();
            String flowCodes = couponReceiveCancelConfirmBatchRequest.getFlowCodes();
            String flow = couponReceiveCancelConfirmBatchRequest.getFlow();

            traceId = YhPortalTraceIdGenerator.genTraceId(uId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: traceId:{}, flowCodes={}", traceId, flowCodes);
            GongxiaoRpc.RpcHeader rpcHeader = com.yhglobal.gongxiao.util.RpcHeaderUtil.createRpcHeader(traceId, uId, uName);
            // result = yhglobalCouponWriteroffService.receiveCancelConfirmBatch(rpcHeader,flowCodes);
            RpcResult rpcResult = couponService.receiveCancelConfirmBatch(rpcHeader, projectId, flowCodes, object, flow);
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
