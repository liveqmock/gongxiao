package com.yhglobal.gongxiao.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidInfo;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidLedgerWriteoffRecord;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalToReceivePrepaidCount;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalToReceivePrepaidLedger;
import com.yhglobal.gongxiao.payment.prepaid.service.YhglobalPrepaidService;
import com.yhglobal.gongxiao.purchase.controller.FoundationInfoController;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
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
    @Reference
    YhglobalPrepaidService yhglobalPrepaidService;
    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    private static Logger logger = LoggerFactory.getLogger(FoundationInfoController.class);

    @RequestMapping("/receive/confirm")
    @ResponseBody
    public GongxiaoResult<Boolean> receiveConfirm(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                  Long projectId,String projectName,String useDate,Integer accountType,String philipNo,String confirmInfoJson){
        String traceId = null;
        Date useDateS = null;
        RpcResult result = null;
        try{
            useDateS = DateUtil.stringToDate(useDate,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][receiveConfirm] params: projectId={}, projectName={},  useDate={},  accountType={},  confirmInfoJson={}", traceId,projectId, projectName, useDate, accountType, confirmInfoJson);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            result = yhglobalPrepaidService.receiveConfirm(rpcHeader,projectId,projectName,useDateS,accountType,philipNo,confirmInfoJson);
            logger.info("#traceId={}# [OUT] get receiveConfirm success:result={}", traceId,result);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(result.getCode(),result.getMessage(),result.getResult());
    }
    @RequestMapping("/receive/confirm/cancel")
    @ResponseBody
    public GongxiaoResult<Boolean> receiveCancelConfirmBatch(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  String[] flowCodes){
        String traceId = null;
        boolean result = false;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][receiveCancelConfirmBatch] params: flowCodes={}", traceId,flowCodes);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            result = yhglobalPrepaidService.receiveCancelConfirmBatch(rpcHeader,flowCodes);
            logger.info("#traceId={}# [OUT] get receiveConfirm success:result={}", traceId,result);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), result);
    }
    @RequestMapping("/receive/getsByProjectId")
    @ResponseBody
    public GongxiaoResult<Boolean> getsReceiveByProjectId(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  Long projectId){
        String traceId = null;
        List<YhglobalToReceivePrepaidLedger> resultList = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsReceiveByProjectId] params: projectId={}, ", traceId,projectId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            resultList = yhglobalPrepaidService.selectByProjectId(rpcHeader,projectId);
            logger.info("#traceId={}# [OUT] get getsReceiveByProjectId success:resultList.size={}", traceId,resultList);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resultList);
    }
    @RequestMapping("/receive/getsByIds")
    @ResponseBody
    public GongxiaoResult<List<YhglobalToReceivePrepaidLedger>> getsReceiveByIds(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          String[] ids){
        String traceId = null;
        List<YhglobalToReceivePrepaidLedger> resultList = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsReceiveByIds] params: ids={}, ", traceId,ids);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            resultList = yhglobalPrepaidService.selectByIds(rpcHeader,ids);
            logger.info("#traceId={}# [OUT] get getsReceiveByIds success:resultList.size={}", traceId,resultList);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resultList);
    }
    @RequestMapping("/record/getsByReceiveId")
    @ResponseBody
    public GongxiaoResult<Boolean> getsRecordByReceiveId(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    Long receiveId){
        //方法已废弃
        String traceId = null;
        List<YhglobalPrepaidLedgerWriteoffRecord> resultList = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsRecordByReceiveId] params: receiveId={}, ", traceId,receiveId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            resultList = yhglobalPrepaidService.selectWriteoffRecordByReceiveId(rpcHeader,receiveId);
            logger.info("#traceId={}# [OUT] get getsRecordByReceiveId success:resultList.size={}", traceId,resultList);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resultList);
    }
    @RequestMapping("/receive/getsMergeByProjectId")
    @ResponseBody
    public GongxiaoResult<PageInfo<YhglobalToReceivePrepaidLedger>> getsMergeByProjectId(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                        Long projectId,
                                                        String orderId,
                                                        String flowCode,
                                                        Integer accountType,
                                                        String dateStart,
                                                        String dateEnd,
                                                        String dateStartConfirm,
                                                        String dateEndConfirm,
                                                        Integer[] receiveStatus,
                                                        @RequestParam(required=true,defaultValue="1") Integer pageNumber,
                                                        @RequestParam(required=false,defaultValue="60") Integer pageSize){
        String traceId = null;
        Date dateS = null;
        Date dateE = null;
        Date dateStartC = null;
        Date dateEndC = null;
        try{
            dateS = DateUtil.stringToDate(dateStart,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        try{
            dateE = DateUtil.stringToDate(dateEnd,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        try{
            dateStartC = DateUtil.stringToDate(dateStartConfirm,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        try{
            dateEndC = DateUtil.stringToDate(dateEndConfirm,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        PageInfo<YhglobalToReceivePrepaidLedger> resultPage = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsReceiveByProjectId] params: projectId={}, ", traceId,projectId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String status="";
            if(receiveStatus !=null && receiveStatus.length>0){
                for(Integer item:receiveStatus){
                    if(status==""){
                        status = item+"";
                    }else{
                        status += ","+item;
                    }
                }
            }

            resultPage = yhglobalPrepaidService.selectReceiveAndRecordByProjectId(rpcHeader,projectId,orderId,flowCode,accountType,dateS,dateE,dateStartC,dateEndC, status,pageNumber,pageSize);
            logger.info("#traceId={}# [OUT] get getsReceiveByProjectId success:resultList.size={}", traceId,resultPage);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resultPage);
    }
    @RequestMapping("/receive/getsMergeCount")
    @ResponseBody
    public GongxiaoResult<YhglobalToReceivePrepaidCount> getsMergeCount(HttpServletRequest request,
                                                                              HttpServletResponse response,
                                                                              Long projectId,
                                                                                String receiveIds){
        String traceId = null;

        YhglobalToReceivePrepaidCount result = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsReceiveByProjectId] params: projectId={}, ", traceId,projectId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            result = yhglobalPrepaidService.selectReceiveAndRecordCount(rpcHeader,projectId,receiveIds);
            logger.info("#traceId={}# [OUT] get getsReceiveByProjectId success:result={}", traceId,result);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), result);
    }
    @RequestMapping("/info/add")
    @ResponseBody
    public GongxiaoResult<Integer> addPrepaidInfo(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  Long projectId, String projectName, Integer supplierId, String supplierName, String payer,
                                                  String receivables, String settlementNo,Integer settlementType, String dateBusiness, String taxNo,
                                                  String contactInfo, Integer provinceId, String provinceName, Integer cityId,
                                                  String cityName, Integer districtId, String districtName, String streetAddress,
                                                  String accountCNY, String bankNameCNY,String remark, String itemJson){
        String traceId = null;
        Integer result = null;
        Date dateB = null;
        try{
            dateB = DateUtil.stringToDate(dateBusiness,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][addPrepaidInfo] params: projectId={}, ", traceId,projectId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            result = yhglobalPrepaidService.addPrepaidInfo(rpcHeader, projectId, projectName, supplierId, supplierName, payer, receivables, settlementNo,settlementType, dateB, taxNo,contactInfo, provinceId, provinceName, cityId,
                    cityName, districtId, districtName, streetAddress,accountCNY, bankNameCNY,remark, itemJson);
            logger.info("#traceId={}# [OUT] get addPrepaidInfo success:result={}", traceId,result);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), result);
    }
    @RequestMapping("/info/gets")
    @ResponseBody
    public GongxiaoResult<PageInfo<YhglobalPrepaidInfo>> getsPrepaidInfoList(HttpServletRequest request,
                                                                             HttpServletResponse response,
                                                                             Long projectId,String prepaidNo,String receivables,String dateStart,String dateEnd,
                                                                             @RequestParam(required=true,defaultValue="1") Integer page,
                                                                             @RequestParam(required=false,defaultValue="10") Integer pageSize){
        String traceId = null;
        PageInfo<YhglobalPrepaidInfo> result = null;
        Date dateS = null;
        Date dateE = null;
        try{
            dateS = DateUtil.stringToDate(dateStart,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        try{
            dateE = DateUtil.stringToDate(dateEnd,DateUtil.DATE_FORMAT);
        }catch(Exception e){ }
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsPrepaidInfoList] params: projectId={}, page={}, pageSize={}", traceId,projectId,page,pageSize);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            result = yhglobalPrepaidService.getsPrepaidInfoList(rpcHeader,projectId,prepaidNo,receivables,dateS,dateE,page,pageSize);
            logger.info("#traceId={}# [OUT] get getsPrepaidInfoList success:result={}", traceId,result);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), result);
    }
    @RequestMapping("/info/get")
    @ResponseBody
    public GongxiaoResult<YhglobalPrepaidInfo> getsMergeByProjectId(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        Integer id){
        String traceId = null;
        YhglobalPrepaidInfo info = null;
        try{
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getsMergeByProjectId] params: id={}, ", traceId,id);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            info = yhglobalPrepaidService.getPrepaidInfoById(rpcHeader,id);
            logger.info("#traceId={}# [OUT] get getsMergeByProjectId success:info={}", traceId,info);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), info);
    }
    @RequestMapping("/confirm/gets")
    @ResponseBody
    public GongxiaoResult<PageInfo<YhglobalPrepaidLedgerWriteoffRecord>> getsPrepaidConfirmList(HttpServletRequest request,
                                                                             HttpServletResponse response,
                                                                                Long projectId,
                                                                                String flowCode,
                                                                                Integer accountType,
                                                                                String useDateStart,
                                                                                String useDateEnd,
                                                                                String dateStart,
                                                                                String dateEnd,
                                                                             @RequestParam(required=true,defaultValue="1") Integer pageNumber,
                                                                             @RequestParam(required=false,defaultValue="10") Integer pageSize){
        String traceId = null;
        PageInfo<YhglobalPrepaidLedgerWriteoffRecord> result = null;
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
            logger.info("#traceId={}# [IN][getsPrepaidInfoList] params: projectId={}, pageNumber={}, pageSize={}", traceId,projectId,pageNumber,pageSize);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            result = yhglobalPrepaidService.searchPrepaidConfirm(rpcHeader,projectId,flowCode,accountType,useDateS,useDateE,dateS,dateE,pageNumber,pageSize);
            logger.info("#traceId={}# [OUT] get getsPrepaidInfoList success:result={}", traceId,result);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), result);
    }
}
