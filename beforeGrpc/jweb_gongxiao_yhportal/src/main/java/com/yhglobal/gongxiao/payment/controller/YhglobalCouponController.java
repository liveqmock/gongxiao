package com.yhglobal.gongxiao.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.excel.ExcelUtil;
import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.flow.YhglobalCouponWriteoffFlow;
import com.yhglobal.gongxiao.payment.flow.service.YhglobalCouponWriteroffService;
import com.yhglobal.gongxiao.purchase.service.YhglobalCouponLedgerService;
import com.yhglobal.gongxiao.util.*;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Reference
    YhglobalCouponWriteroffService yhglobalCouponWriteroffService;

    /**
     * 越海返利确认的入口
     * @param request
     * @param response
     * @param projectId       项目ID
     * @param projectName     项目名称
     * @param useDate         使用日期
     * @param accountType     使用账户
     * @param confirmInfoJson 返利确认信息的json串
     * @return
     */
    @RequestMapping(value = "/writeroff")
    @ResponseBody
    public GongxiaoResult yhglobalCouponWriteroff(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  Long projectId,String projectName,String useDate,Integer accountType,
                                                  String philipDocumentNo, String confirmInfoJson){
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(),ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        // philipDocumentNo 飞利浦单据号为必填字段 如果为空则返回参数不合法
        if(philipDocumentNo == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(),ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        String traceId = null;
        Date useDateS = null;
        RpcResult rpcResult;
        try{
            //useDateS = DateUtil.stringToDate(useDate,DateUtil.DATE_FORMAT);
            useDateS = DateUtil.stringToDate(useDate);
        }catch(Exception e){ }
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: projectId={}, projectName={}, useDate={}, accountType={}, confirmInfoJson={}",
                    traceId, projectId,projectName,useDate,accountType,confirmInfoJson);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

            List<YhglobalCouponLedger> checkList = JSON.parseObject(confirmInfoJson, new TypeReference<List<YhglobalCouponLedger>>() {});
            for (YhglobalCouponLedger yhglobalCouponLedger:checkList){
                    if ( yhglobalCouponLedger.getConfirmAmountDouble() ==null || yhglobalCouponLedger.getReceiptAmountDouble() == null
                            || "".equals(yhglobalCouponLedger.getConfirmAmountDouble()) || "".equals(yhglobalCouponLedger.getReceiptAmountDouble())
                            || (yhglobalCouponLedger.getConfirmAmountDouble() ==0 && yhglobalCouponLedger.getReceiptAmountDouble()==0)) {
                        // 输入的确认额度 或 实收额度数据为空则非法
                        return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
                    }
            }
            rpcResult = yhglobalCouponWriteroffService.yhCouponWriteroff(rpcHeader,projectId,projectName,useDateS,accountType,philipDocumentNo,confirmInfoJson);

            logger.info("#traceId={}# [OUT] coupon writeoff  success: result={}",rpcResult );
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(),rpcResult);
    }

    /**
     * 查询出全部的返利及对应记录
     * @param projectId  项目ID
     * */
    @RequestMapping(value = "/selectAllByProjectId")
    @ResponseBody
    public GongxiaoResult selectAllByProjectId(Long projectId ,int pageNumber , int  pageSize, HttpServletRequest request){
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(),ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectAllByProjectId] params: projectId={}, pageNumber={}, pageSize={}", rpcHeader.traceId,projectId, pageNumber,pageSize);

            GongxiaoResult gongxiaoResult = null;
            PageInfo<PurchaseCouponFlow> list = yhglobalCouponWriteroffService.selectAllByProjectId(rpcHeader, projectId, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] select all yhcouponledger by projectID success: result={}",list.toString());
            return gongxiaoResult;
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }


    /**
     * 返利确认界面条件查询的入口
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
    public GongxiaoResult selectYhglobalCouponLedgerByManyConditions(HttpServletRequest request,Long projectId,
                                                                             String purchaseOrderNo,
                                                                             String supplierOrderNo,String dateStart,
                                                                             String dateEnd,Byte[] couponStatus,String flowNo,
                                                                             Integer pageNumber, Integer pageSize){
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(),ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        logger.info("#traceId={}# [IN][getProductInfo] params:  projectId={}, purchaseOrderNo={}," +
                        "supplierOrderNo={}, dateStart={}, dateEnd={}, couponStatus={}, flowNo={}, pageNumber={}, pageSize={} ", traceId, projectId,purchaseOrderNo,
                supplierOrderNo,dateStart,dateEnd,couponStatus.toString(),flowNo,pageNumber,pageSize);
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
        PageInfo<YhglobalCouponLedgerItem> result = null;
        try {
            result = yhglobalCouponWriteroffService.selectByManyCondiitons(rpcHeader, projectId, purchaseOrderNo, supplierOrderNo, dateS
                    , dateE, s, flowNo, pageNumber, pageSize);
            if (result == null) {
                logger.info("#traceId={}# [OUT] : selectYhglobalCouponLedgerByManyConditions fail the result is null list={}", result);
            }
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), result);
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;


    }

    /**
     * 返利确认界面数据导出
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(HttpServletRequest request,HttpServletResponse response,Long projectId,
                                                                     String purchaseOrderNo,
                                                                     String supplierOrderNo,String dateStart,
                                                                     String dateEnd,Byte[] couponStatus,String flowNo){
        if (projectId == null){
            throw new RuntimeException("the projectId is null");
        }
        String traceId = null;
        traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
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
        List<YhglobalCouponLedgerItem> result = null;
        try {
            result = yhglobalCouponWriteroffService.selectByManyCondiitons(rpcHeader, projectId, purchaseOrderNo, supplierOrderNo, dateS
                    , dateE, s, flowNo);
//            if (result == null) {
//                logger.info("#traceId={}# [OUT] : selectYhglobalCouponLedgerByManyConditions fail the result is null list={}", result);
//            }
            String downFileName = new String("供应商返利流水列表.xls");
            ExcelUtil<YhglobalCouponLedgerItem> util = new ExcelUtil<YhglobalCouponLedgerItem>(YhglobalCouponLedgerItem.class);
            Workbook workbook = util.getListToExcel(result,"流水列表");
            ExcelDownUtil.resetResponse(response,workbook,downFileName);
            logger.info("#traceId={}# [select coupon ledger and record to export success][OUT]");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
        }


    }
    /**
     * 统计选中的返利的应收等金额总和
     * */
    @RequestMapping(value = "/getTotal")
    @ResponseBody
    public GongxiaoResult getTotalOfSelected(String[] ids, HttpServletRequest request){
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getTotalOfSelected] params: traceId:{}, ids={}", traceId,ids.toString());
            GongxiaoResult gongxiaoResult = null;
            if (ids.length == 0){
                Long receiveAmountTotal = 0l;  // 应收
                Long toReceiveAmountTotal = 0l; // 未收
                Long confirmAmountTotal = 0l;  // 确认
                Long receiptAmountTotal = 0l;  // 实收
                YhglobalCouponLedger yhglobalCouponLedger = new YhglobalCouponLedger();
                yhglobalCouponLedger.setEstimatedAmountDouble(receiveAmountTotal / FXConstant.HUNDRED_DOUBLE);
                yhglobalCouponLedger.setToBeConfirmAmountDouble(toReceiveAmountTotal / FXConstant.HUNDRED_DOUBLE);
                yhglobalCouponLedger.setConfirmAmountDouble(confirmAmountTotal / FXConstant.HUNDRED_DOUBLE);
                yhglobalCouponLedger.setReceiptAmountDouble(receiptAmountTotal / FXConstant.HUNDRED_DOUBLE);

                gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), yhglobalCouponLedger);
                return gongxiaoResult;
            }
            List<String> list1 = Arrays.asList(ids);
            List<String> buffList = list1;

            // 去重
            List<Long> idList = new ArrayList<>();
            for (int i = 0; i < buffList.size(); i++) {
                Long id = Long.parseLong(buffList.get(i));
                if (!idList.contains(id)) {
                    idList.add(id);
                }
            }
            List<YhglobalCouponLedger> list = yhglobalCouponWriteroffService.selectYhglobalCouponLedgerByPurchaseCouponLedgerId(idList);
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

            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), yhglobalCouponLedger);
            logger.info("#traceId={}# [OUT] select couponledger By PurchaseCouponLedgerId success: result={}",list.toString());
            return gongxiaoResult;
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 根据选中的返利主键查询返利详情
     * @param ids  返利ID拼接的字符串
     * */
    @RequestMapping(value = "/selectByPurchaseCouponLedgerId")
    @ResponseBody
    public GongxiaoResult selectYhglobalCouponLedgerByPurchaseCouponLedgerId(String[] ids, HttpServletRequest request){
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectYhglobalCouponLedgerByPurchaseCouponLedgerId] params: traceId:{}, ids={}", traceId,ids.toString());
            GongxiaoResult gongxiaoResult = null;
            // 去重
            List<Long> idList = new ArrayList<>();
            for (int i = 0; i < ids.length; i++) {
                Long id = Long.parseLong(ids[i]);
                if (!idList.contains(id)) {
                    idList.add(id);
                }
            }
            List<YhglobalCouponLedger> list = yhglobalCouponWriteroffService.selectYhglobalCouponLedgerByPurchaseCouponLedgerId(idList);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] select couponledger By PurchaseCouponLedgerId success: result={}",list.toString());
            return gongxiaoResult;
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }


    /**
     * 获取台账数据
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
    public GongxiaoResult getsCouponConfirmList(HttpServletRequest request, Long projectId,String flowCode,
                                                                                                Integer accountType,
                                                                                                String useDateStart,
                                                                                                String useDateEnd,
                                                                                                String dateStart,
                                                                                                String dateEnd,
                                                                                               Integer pageNumber,
                                                                                                Integer pageSize){
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(),ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        PageInfo<YhglobalCouponWriteoffRecord> result = null;
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
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
           // 查询流水
            result = yhglobalCouponWriteroffService.searchCouponConfirm(rpcHeader,projectId,flowCode,accountType,useDateS,useDateE,dateS,dateE,pageNumber,pageSize);
            logger.info("#traceId={}# [OUT] getsCouponConfirmList success:result={}", traceId,result);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), result);
        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 取消确认的入口
     * @param flowCodes   使用逗号拼接的流水号的字符串
     * */
    @RequestMapping("/cancelConfirm")
    @ResponseBody
    public GongxiaoResult cancelCouponConfirm(HttpServletRequest request,
                                              HttpServletResponse response,
                                              String[] flowCodes){
        String traceId = null;
        boolean result = false;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: traceId:{}, flowCodes={}", traceId, flowCodes.toString());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            result = yhglobalCouponWriteroffService.receiveCancelConfirmBatch(rpcHeader,flowCodes);
            logger.info("#traceId={}# [OUT] get getsPrepaidInfoList success:result={}", traceId,result);

            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), result);

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }





}
