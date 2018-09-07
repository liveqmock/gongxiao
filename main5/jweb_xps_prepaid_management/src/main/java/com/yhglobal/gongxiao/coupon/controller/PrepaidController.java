package com.yhglobal.gongxiao.coupon.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ConfirmStatus;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.coupon.Service.ChannelService;
import com.yhglobal.gongxiao.coupon.Service.PrepaidService;
import com.yhglobal.gongxiao.coupon.config.PortalConfig;
import com.yhglobal.gongxiao.coupon.model.*;
import com.yhglobal.gongxiao.coupon.model.PrepaidPaymentOrder.YhglobalPrepaidInfo;

import com.yhglobal.gongxiao.coupon.model.YhglobalPrepaidLedgerWriteoffRecord;
import com.yhglobal.gongxiao.coupon.model.YhglobalToReceivePrepaidLedger;
import com.yhglobal.gongxiao.coupon.utils.RpcHeaderUtil;
import com.yhglobal.gongxiao.coupon.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.model.WriteOffReturnObject;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import org.apache.axis.holders.YearHolder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 返利的相关操作入口
 *
 * @author 王帅
 */
@Controller
@RequestMapping("/prepaid")
public class PrepaidController {

     static Logger logger = LoggerFactory.getLogger(PrepaidController.class);

    @Autowired
    private PortalConfig portalConfig;


    @Autowired
    PrepaidService prepaidService;

    @Autowired
    ChannelService channelService;



    public static boolean checkChannel(String channelToken,PrepaidChannel prepaidChannel){

        // PrepaidChannel prepaidChannel = channelService.selectByChannelId(channelId);
        // 根据set方法 数据库存储明文 查出结果加密为密文 传入参数为密文
        // 比较参数channelToken 与查询结果 的channelSecret
        PrepaidChannel token = new PrepaidChannel();
        token.setChannelSecret(channelToken);
        if (token.getChannelSecret() == prepaidChannel.getChannelSecret()) {
            return true;
        }else {
            return false;
        }
    }
    /**************************************************代垫付款单相关******************************************************/
    /**
     * 收付款管理>代垫付款单管理  新增保存
     */
    @RequestMapping("/addInfo")
    @ResponseBody
    public GongxiaoResult addPrepaidInfo(HttpServletRequest request){

        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try{
        // 从请求中获取对象
        ServletInputStream inputStream = request.getInputStream();
        String jsonStr = IOUtils.toString(inputStream, "utf-8");
        AddPrepaidPaymentOrderRequest parseObject = JSON.parseObject(jsonStr, AddPrepaidPaymentOrderRequest.class);

        // 获取参数
            Long projectId = parseObject.getProjectId();
            String projectName = parseObject.getProjectName();
            Integer supplierId = parseObject.getSupplierId();
            String supplierName = parseObject.getSupplierName();
            String payer = parseObject.getPayer();
            String receivables = parseObject.getReceivables();
            String settlementNo = parseObject.getSettlementNo();
            String dateBusiness = parseObject.getDateBusiness();
            String taxNo = parseObject.getTaxNo();
            String contactInfo = parseObject.getContactInfo();
            Integer provinceId = parseObject.getProvinceId();
            String provinceName = parseObject.getProvinceName();
            Integer cityId = parseObject.getCityId();
            String cityName = parseObject.getCityName();
            Integer districtId = parseObject.getDistrictId();
            String districtName = parseObject.getDistrictName();
            String streetAddress = parseObject.getStreetAddress();
            String accountCNY = parseObject.getAccountCNY();
            String bankNameCNY = parseObject.getBankNameCNY();
            String remark = parseObject.getRemark();
            String itemJson = parseObject.getItemJson();
            String userId = parseObject.getUserId();
            String userName = parseObject.getUserName();
            Integer settlementType = parseObject.getSettlementType();


            traceId= YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][addPrepaidInfo] params: projectId={}, ", traceId,projectId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createGRpcHeader(traceId, projectId.intValue(), userId, userName);

            RpcResult rpcResult = prepaidService.addPrepaidInfo(rpcHeader, projectId, projectName, supplierId, supplierName, payer, receivables, settlementNo,settlementType,
                    dateBusiness, taxNo, contactInfo, provinceId, provinceName, cityId, cityName, districtId, districtName, streetAddress, accountCNY, bankNameCNY, remark, itemJson);
//            int returnCode = rpcResult.getReturnCode();
//            if (returnCode == ErrorCode.SUCCESS.getErrorCode()) {
//                logger.info("#traceId={}# [OUT] get addPrepaidInfo success:resultCode={}", traceId, returnCode);
//            } else {
//                logger.error("#traceId={}# [OUT] fail to get addPrepaidInfo:resultCode={}", traceId, returnCode);
//            }
            gongxiaoResult = new GongxiaoResult(rpcResult.getCode(), rpcResult.getMessage());
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 收付款管理>代垫付款单管理  代垫付款单列表查询
     */
    @RequestMapping("/select")
    @ResponseBody
    public GongxiaoResult getsPrepaidInfoList(HttpServletRequest request){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try{
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            SelectPrepaidPaymentOrderRequest selectPrepaidPaymentOrderRequest = JSON.parseObject(jsonStr, SelectPrepaidPaymentOrderRequest.class);

            // 获取参数
            Long projectId = selectPrepaidPaymentOrderRequest.getProjectId();
            String prepaidNo = selectPrepaidPaymentOrderRequest.getPrepaidNo();
            String receivables = selectPrepaidPaymentOrderRequest.getReceivables();
            String dateStart = selectPrepaidPaymentOrderRequest.getDateStart();
            String dateEnd = selectPrepaidPaymentOrderRequest.getDateEnd();
            Integer pageNumber = selectPrepaidPaymentOrderRequest.getPageNumber();
            Integer pageSize = selectPrepaidPaymentOrderRequest.getPageSize();

            //todo 校验
            String channelId = selectPrepaidPaymentOrderRequest.getChannelId();
            String channelToken = selectPrepaidPaymentOrderRequest.getChannelToken();
            String userId = selectPrepaidPaymentOrderRequest.getUserId();
            String userName = selectPrepaidPaymentOrderRequest.getUserName();

            Date dateS = DateUtil.stringToDate(dateStart);
            Date dateE = DateUtil.stringToDate(dateEnd);

            traceId= YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][addPrepaidInfo] params: projectId={}, ", traceId,projectId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createGRpcHeader(traceId, projectId.intValue(), userId, userName);

            PageInfo<YhglobalPrepaidInfo> pageInfo = prepaidService.getsPrepaidInfoList(rpcHeader, projectId, prepaidNo, receivables, dateS, dateE, pageNumber, pageSize);
//            int returnCode = rpcResult.getReturnCode();
//            if (returnCode == ErrorCode.SUCCESS.getErrorCode()) {
//                logger.info("#traceId={}# [OUT] get addPrepaidInfo success:resultCode={}", traceId, returnCode);
//            } else {
//                logger.error("#traceId={}# [OUT] fail to get addPrepaidInfo:resultCode={}", traceId, returnCode);
//            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;

    }


    /**
     * 收付款管理>代垫付款单管理  代垫付款单详情
     */
    @RequestMapping("/getDetailInfo")
    @ResponseBody
    public GongxiaoResult getPrepaidInfoById(HttpServletRequest request){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try{
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            GetPrepaidPaymentOrderDetailRequest parseObject = JSON.parseObject(jsonStr, GetPrepaidPaymentOrderDetailRequest.class);

            // 获取参数
            Long projectId = parseObject.getProjectId();
            String channelId = parseObject.getChannelId();
            String channelToken = parseObject.getChannelToken();
            String userId = parseObject.getUserId();
            String userName = parseObject.getUserName();
            Integer id = parseObject.getId();


            traceId= YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][addPrepaidInfo] params: projectId={}, ", traceId,projectId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createGRpcHeader(traceId, projectId.intValue(), userId, userName);

            YhglobalPrepaidInfo prepaidInfoById = prepaidService.getPrepaidInfoById(rpcHeader, projectId, id);

//            int returnCode = rpcResult.getReturnCode();
//            if (returnCode == ErrorCode.SUCCESS.getErrorCode()) {
//                logger.info("#traceId={}# [OUT] get addPrepaidInfo success:resultCode={}", traceId, returnCode);
//            } else {
//                logger.error("#traceId={}# [OUT] fail to get addPrepaidInfo:resultCode={}", traceId, returnCode);
//            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), prepaidInfoById);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;

    }





    /*************************************************应收代垫相关********************************************************/
    /**
     * 代垫生成
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/generate")
    public GongxiaoResult prepaidGenerate(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            PrepaidGenerateRequest prepaidGenerateRequest = JSON.parseObject(jsonStr, PrepaidGenerateRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = prepaidGenerateRequest.getChannelId();
            String channelToken = prepaidGenerateRequest.getChannelToken();

            // 查询channel表校验token
//            PrepaidChannel prepaidChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken, prepaidChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }


            // 获取其他业务参数 并调用方法
            Long projectId = prepaidGenerateRequest.getProjectId();
            String currencyCode = prepaidGenerateRequest.getCurrencyCode();
            String userId = prepaidGenerateRequest.getUserId();
            String userName = prepaidGenerateRequest.getUserName();
            String orderId = prepaidGenerateRequest.getOrderId();
            String projectName = prepaidGenerateRequest.getProjectName();
            Long receiveAmount = prepaidGenerateRequest.getReceiveAmount();
            String salesContractNo = prepaidGenerateRequest.getSalesContractNo();
            Integer supplierId = prepaidGenerateRequest.getSupplierId();
            String supplierName = prepaidGenerateRequest.getSupplierName();
            Date outWarehouseDate = prepaidGenerateRequest.getOutWarehouseDate();

            traceId = YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][addReceive] params: orderId={}, projectId={}, projectName={}, supplierId={}, supplierName={},  salesContractNo={},  receiveAmount={},  currencyCode={},",
                    traceId, orderId, projectId, projectName, supplierId, supplierName, salesContractNo, receiveAmount, currencyCode);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createGRpcHeader(traceId, projectId.intValue(), userId, userName);

            RpcResult rpcResult = prepaidService.generateYhglobalPrepaidLedger(rpcHeader, orderId, projectId, projectName, supplierId, supplierName, salesContractNo, receiveAmount, currencyCode, outWarehouseDate);
            gongxiaoResult = new GongxiaoResult(rpcResult.getCode(), rpcResult.getMessage());

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(),ErrorCode.UNKNOWN_ERROR.getMessage());
        }

        return gongxiaoResult;
    }

    @ResponseBody
    @RequestMapping("/writeoff")
    public GongxiaoResult prepaidWriteoff(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            PrepaidWriteoffRequest prepaidWriteoffRequest = JSON.parseObject(jsonStr, PrepaidWriteoffRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = prepaidWriteoffRequest.getChannelId();
            String channelToken = prepaidWriteoffRequest.getChannelToken();

            // 查询channel表校验token
//            PrepaidChannel prepaidChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken, prepaidChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Integer accountType = prepaidWriteoffRequest.getAccountType();
            Long projectId = prepaidWriteoffRequest.getProjectId();
            String projectName = prepaidWriteoffRequest.getProjectName();
            String confirmInfoJson = prepaidWriteoffRequest.getConfirmInfoJson();
            String philipDocumentNo = prepaidWriteoffRequest.getPhilipDocumentNo();
            String useDate = prepaidWriteoffRequest.getUseDate();

            String userId = prepaidWriteoffRequest.getUserId();
            String userName = prepaidWriteoffRequest.getUserName();
            List<WriteOffReturnObject> list = prepaidWriteoffRequest.getList();

            traceId = YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: projectId={}, projectName={}, accountType={}, philipDocumentNo={}, " +
                    "useDate={}, confirmInfoJson={}", projectId,projectName, accountType, philipDocumentNo, useDate, confirmInfoJson);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createGRpcHeader(traceId, projectId.intValue(),userId, userName);

            RpcResult rpcResult = prepaidService.yhPrepaidWriteroff(rpcHeader, projectId, useDate, accountType, confirmInfoJson, projectName, philipDocumentNo, list);
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
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            PrepaidSelectHasPageRequest prepaidSelectHasPageRequest = JSON.parseObject(jsonStr, PrepaidSelectHasPageRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = prepaidSelectHasPageRequest.getChannelId();
            String channelToken = prepaidSelectHasPageRequest.getChannelToken();

//            // 查询channel表校验token
//            PrepaidChannel prepaidChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken, prepaidChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Byte[] couponStatus = prepaidSelectHasPageRequest.getPrepaidStatus();
            String dateStart = prepaidSelectHasPageRequest.getDateStart();
            String dateEnd = prepaidSelectHasPageRequest.getDateEnd();
            String flowNo = prepaidSelectHasPageRequest.getFlowNo();
            Long projectId = prepaidSelectHasPageRequest.getProjectId();
            Integer pageNumber = prepaidSelectHasPageRequest.getPageNumber();
            Integer pageSize = prepaidSelectHasPageRequest.getPageSize();
            String orderId = prepaidSelectHasPageRequest.getOrderId();
            String dateStartConfirm = prepaidSelectHasPageRequest.getDateStartConfirm();
            String dateEndConfirm = prepaidSelectHasPageRequest.getDateEndConfirm();
            Integer accountType = prepaidSelectHasPageRequest.getAccountType();
            String userId = prepaidSelectHasPageRequest.getUserId();
            String userName = prepaidSelectHasPageRequest.getUserName();

            Date dateS = DateUtil.stringToDate(dateStart);
            Date dateE = DateUtil.stringToDate(dateEnd);
            Date dateSc = DateUtil.stringToDate(dateStartConfirm);
            Date dateEc = DateUtil.stringToDate(dateEndConfirm);

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
            traceId = YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: projectId={},  projectId={}, purchaseOrderNo={}," +
                            " supplierOrderNo={}, dateS={}, dateE={}, s={},flowNo={}, pageNumber={}, pageSize={}", projectId,  dateS, dateE, s,
                    flowNo, pageNumber, pageSize);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createGRpcHeader(traceId, projectId.intValue(), userId, userName);
            PageInfo<YhglobalToReceivePrepaidLedger> yhglobalToReceivePrepaidLedgerPageInfo = prepaidService.selectByManyConditionsHasPage(rpcHeader, projectId, orderId, dateS, dateE,
                    dateSc, dateEc, s, accountType, flowNo, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),yhglobalToReceivePrepaidLedgerPageInfo);

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
            PrepaidSelectByIdRequest prepaidSelectByIdRequest = JSON.parseObject(jsonStr, PrepaidSelectByIdRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = prepaidSelectByIdRequest.getChannelId();
            String channelToken = prepaidSelectByIdRequest.getChannelToken();
            String userId = prepaidSelectByIdRequest.getUserId();
            String userName = prepaidSelectByIdRequest.getUserName();
            Long projectId = prepaidSelectByIdRequest.getProjectId();

            // 查询channel表校验token
//            PrepaidChannel prepaidChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken, prepaidChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            String[] ids = prepaidSelectByIdRequest.getIds();
            // 去重
            Set<String> set = new TreeSet<>();
            set.addAll(Arrays.asList(ids));
            List<String> idList = new ArrayList<>(set);


            traceId = YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createGRpcHeader(traceId, projectId.intValue(), userId, userName);
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: traceId:{}, ids={}", traceId,ids.toString());
            List<YhglobalToReceivePrepaidLedger> list = prepaidService.selectPreapidById(rpcHeader, projectId, idList);
            // 过滤掉确认状态为全部确认的数据
            for (int i = 0;i<list.size();i++){
                if (list.get(i).getReceiveStatus() == ConfirmStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode()){
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
    @RequestMapping("/searchPrepaidConfirm")
    public GongxiaoResult searchPrepaidConfirm(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            SearchPrepaidConfirmRequest searchPrepaidConfirmRequest = JSON.parseObject(jsonStr, SearchPrepaidConfirmRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = searchPrepaidConfirmRequest.getChannelId();
            String channelToken = searchPrepaidConfirmRequest.getChannelToken();

            // 查询channel表校验token
//            PrepaidChannel prepaidChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken, prepaidChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Integer accountType = searchPrepaidConfirmRequest.getAccountType();
            String dateStart = searchPrepaidConfirmRequest.getDateStart();
            String dateEnd = searchPrepaidConfirmRequest.getDateEnd();
            String flowCode = searchPrepaidConfirmRequest.getFlowCode();
            Long projectId = searchPrepaidConfirmRequest.getProjectId();
            String useDateStart = searchPrepaidConfirmRequest.getUseDateStart();
            String useDateEnd = searchPrepaidConfirmRequest.getUseDateEnd();
            Integer pageNumber = searchPrepaidConfirmRequest.getPageNumber();
            Integer pageSize = searchPrepaidConfirmRequest.getPageSize();

            String userId = searchPrepaidConfirmRequest.getUserId();
            String userName = searchPrepaidConfirmRequest.getUserName();

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

                traceId= YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
                logger.info("#traceId={}# [IN][getsPrepaidInfoList] params: projectId={},flowCode={}, accountType={},useDateStart={},useDateEnd={}," +
                        "dateStart={},dateEnd={}, pageNumber={}, pageSize={}", traceId,projectId, flowCode, accountType,useDateStart,useDateEnd,dateStart,dateEnd,pageNumber,pageSize);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createGRpcHeader(traceId, projectId.intValue(), userId, userName);
                // 查询流水
            PageInfo<YhglobalPrepaidLedgerWriteoffRecord> yhglobalPrepaidLedgerWriteoffRecordPageInfo = prepaidService.searchPrepaidConfirm(rpcHeader, projectId, flowCode, accountType, useDateS, useDateE, dateS, dateE, pageNumber, pageSize);


            logger.info("#traceId={}# [OUT] getsCouponConfirmList success:result={}", traceId,yhglobalPrepaidLedgerWriteoffRecordPageInfo);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), yhglobalPrepaidLedgerWriteoffRecordPageInfo);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

        return gongxiaoResult;
    }



    @ResponseBody
    @RequestMapping("/reportLeftOne")
    public GongxiaoResult getLeftOneData(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
           PrepaidReceiveCancelConfirmBatchRequest prepaidReceiveCancelConfirmBatchRequest = JSON.parseObject(jsonStr, PrepaidReceiveCancelConfirmBatchRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = prepaidReceiveCancelConfirmBatchRequest.getChannelId();
            String channelToken = prepaidReceiveCancelConfirmBatchRequest.getChannelToken();

            // 查询channel表校验token
//            PrepaidChannel prepaidChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken, prepaidChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Long projectId = prepaidReceiveCancelConfirmBatchRequest.getProjectId();
            String userId = prepaidReceiveCancelConfirmBatchRequest.getUserId();
            String userName = prepaidReceiveCancelConfirmBatchRequest.getUserName();


            traceId = YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getDataLeftOne] params: traceId:{}, flowCodes={}", traceId);
            GongxiaoRpc.RpcHeader rpcHeader = com.yhglobal.gongxiao.util.RpcHeaderUtil.createRpcHeader(traceId, userId, userName);

            RpcResult rpcResult = prepaidService.getReportLeftOneData(rpcHeader, projectId);

            if (rpcResult.getCode() == ErrorCode.SUCCESS.getErrorCode()) {
                logger.info("#traceId={}# [OUT] getDataLeftOne success", traceId);
            } else {
                logger.error("#traceId={}# [OUT] getDataLeftOne confirm", traceId);
            }
            // 根据返回码设置对应的gongxiaoResult
            gongxiaoResult = new GongxiaoResult(rpcResult.getCode(), rpcResult.getResult());

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(),ErrorCode.UNKNOWN_ERROR.getMessage());

        }

        return gongxiaoResult;
    }


    @ResponseBody
    @RequestMapping("/reportRightOne")
    public GongxiaoResult getRightOneData(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            PrepaidReceiveCancelConfirmBatchRequest prepaidReceiveCancelConfirmBatchRequest = JSON.parseObject(jsonStr, PrepaidReceiveCancelConfirmBatchRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = prepaidReceiveCancelConfirmBatchRequest.getChannelId();
            String channelToken = prepaidReceiveCancelConfirmBatchRequest.getChannelToken();

            // 查询channel表校验token
//            PrepaidChannel prepaidChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken, prepaidChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Long projectId = prepaidReceiveCancelConfirmBatchRequest.getProjectId();
            String userId = prepaidReceiveCancelConfirmBatchRequest.getUserId();
            String userName = prepaidReceiveCancelConfirmBatchRequest.getUserName();


            traceId = YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getDataLeftOne] params: traceId:{}, flowCodes={}", traceId);
            GongxiaoRpc.RpcHeader rpcHeader = com.yhglobal.gongxiao.util.RpcHeaderUtil.createRpcHeader(traceId, userId, userName);

            RpcResult rpcResult = prepaidService.getReportRightOneData(rpcHeader, projectId);

            if (rpcResult.getCode() == ErrorCode.SUCCESS.getErrorCode()) {
                logger.info("#traceId={}# [OUT] getDataRightOne success", traceId);
            } else {
                logger.error("#traceId={}# [OUT] getDataRightOne confirm", traceId);
            }
            // 根据返回码设置对应的gongxiaoResult
            gongxiaoResult = new GongxiaoResult(rpcResult.getCode(), rpcResult.getResult());

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
            PrepaidReceiveCancelConfirmBatchRequest prepaidReceiveCancelConfirmBatchRequest = JSON.parseObject(jsonStr, PrepaidReceiveCancelConfirmBatchRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = prepaidReceiveCancelConfirmBatchRequest.getChannelId();
            String channelToken = prepaidReceiveCancelConfirmBatchRequest.getChannelToken();

            // 查询channel表校验token
//            PrepaidChannel prepaidChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken, prepaidChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
            Long projectId = prepaidReceiveCancelConfirmBatchRequest.getProjectId();
            String flowCodes = prepaidReceiveCancelConfirmBatchRequest.getFlowCodes();
            WriteOffReturnObject object = prepaidReceiveCancelConfirmBatchRequest.getObject();
            String flow = prepaidReceiveCancelConfirmBatchRequest.getFlow();
            String userId = prepaidReceiveCancelConfirmBatchRequest.getUserId();
            String userName = prepaidReceiveCancelConfirmBatchRequest.getUserName();


            traceId = YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalPrepaidWriteroffCancel] params: traceId:{}, flowCodes={}", traceId, flowCodes);
            GongxiaoRpc.RpcHeader rpcHeader = com.yhglobal.gongxiao.util.RpcHeaderUtil.createRpcHeader(traceId, userId, userName);

            RpcResult rpcResult = prepaidService.receiveCancelConfirmBatch(rpcHeader,projectId, flowCodes, object, flow);
            if (rpcResult.getCode() == ErrorCode.SUCCESS.getErrorCode()) {
                logger.info("#traceId={}# [OUT] cancel prepaid confirm success: flowCodes={}", traceId, flowCodes);
            } else {
                logger.error("#traceId={}# [OUT] fail to cancel prepaid confirm: flowCodes={}", traceId, flowCodes);
            }
            // 根据返回码设置对应的gongxiaoResult
            gongxiaoResult = new GongxiaoResult(rpcResult.getCode(), rpcResult.getMessage());

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(),ErrorCode.UNKNOWN_ERROR.getMessage());

        }

        return gongxiaoResult;
    }
    @ResponseBody
    @RequestMapping("/getsMergeCount")
    public GongxiaoResult getsMergeCount(HttpServletRequest request){

        GongxiaoResult gongxiaoResult = null;
        String   traceId = null;
        try {
            // 从请求中获取对象
            ServletInputStream inputStream = request.getInputStream();
            String jsonStr = IOUtils.toString(inputStream, "utf-8");
            PrepaidGetMergeCountRequest prepaidGetMergeCountRequest = JSON.parseObject(jsonStr, PrepaidGetMergeCountRequest.class);

            // 从对象中获取channelId channelToken
            String channelId = prepaidGetMergeCountRequest.getChannelId();
            String channelToken = prepaidGetMergeCountRequest.getChannelToken();
            String userId = prepaidGetMergeCountRequest.getUserId();
            String userName = prepaidGetMergeCountRequest.getUserName();
            Long projectId = prepaidGetMergeCountRequest.getProjectId();

            // 查询channel表校验token
//            PrepaidChannel prepaidChannel = channelService.selectByChannelId(channelId);
//            if (!checkChannel(channelToken, prepaidChannel)){
//                return new GongxiaoResult(ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getErrorCode(), ErrorCode.CHANNEL_ID_OR_TOKEN_WRONG.getMessage());
//            }

            // 从对象中回去参数 并调用方法
//            String[] ids = prepaidSelectByIdRequest.getIds();
//            String idStr = "";
//            // 去重
//            Set<String> set = new TreeSet<>();
//            if (ids != null) {
//                set.addAll(Arrays.asList(ids));
//                List<String> idList = new ArrayList<>(set);
//                //  拼接ID 一次查出所有对象
//                StringBuffer sb = new StringBuffer();
//                boolean flag = false;
//                for (String id : idList) {
//                    if (flag) {
//                        sb.append(",");
//                    } else {
//                        flag = true;
//                    }
//                    sb.append(id);
//                }
//                idStr = sb.toString();
//            }
            String ids = prepaidGetMergeCountRequest.getReceiveIds();

            traceId = YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createGRpcHeader(traceId, projectId.intValue(), userId, userName);
            logger.info("#traceId={}# [IN][getsMergeCount] params: traceId:{}, ids={}", traceId,ids);
            YhglobalToReceivePrepaidCount yhglobalToReceivePrepaidCount = new YhglobalToReceivePrepaidCount();
            if (StringUtils.isNotBlank(ids)) {// 选中的ID不为空时
                yhglobalToReceivePrepaidCount = prepaidService.selectReceiveAndRecordCount(rpcHeader, projectId, ids);
                yhglobalToReceivePrepaidCount.setConfirmAmountDouble(yhglobalToReceivePrepaidCount.getConfirmAmount() != null ? yhglobalToReceivePrepaidCount.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE : 0);
                yhglobalToReceivePrepaidCount.setReceiveAmountDouble(yhglobalToReceivePrepaidCount.getReceiveAmount() != null ? yhglobalToReceivePrepaidCount.getReceiveAmount() / FXConstant.HUNDRED_DOUBLE : 0);
                yhglobalToReceivePrepaidCount.setReceiptAmountDouble(yhglobalToReceivePrepaidCount.getReceiptAmount() != null ? yhglobalToReceivePrepaidCount.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE : 0);
                yhglobalToReceivePrepaidCount.setToBeConfirmAmountDouble(yhglobalToReceivePrepaidCount.getToBeConfirmAmount() != null ? yhglobalToReceivePrepaidCount.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : 0);
            }else {
                yhglobalToReceivePrepaidCount.setConfirmAmountDouble(0D);
                yhglobalToReceivePrepaidCount.setReceiveAmountDouble(0D);
                yhglobalToReceivePrepaidCount.setReceiptAmountDouble(0D);
                yhglobalToReceivePrepaidCount.setToBeConfirmAmountDouble(0D);
            }
           gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),yhglobalToReceivePrepaidCount);
            logger.info("#traceId={}# [OUT] getsMergeCount success: result={}",yhglobalToReceivePrepaidCount.toString());

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
