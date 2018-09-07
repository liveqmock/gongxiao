package com.yhglobal.gongxiao;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.coreutil.OkHttpManager;
import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.util.SendRequestAndGetResponseUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 组装生成应收返利的报文
 * @author 王帅
 */
public class XpsGrossProfitManager {

    private static final String GENERATE_GROSSPROFIT_URL = "/grossProfit/generate";
    private static final String WRITEOFF_GROSSPROFIT_URL = "/grossProfit/writeoff";
    private static final String SELECT_GROSSPROFIT_HAS_PAGE_URL = "/grossProfit/selectHasPage";
    private static final String SELECT_GROSSPROFIT_BY_ID_URL = "/grossProfit/selectByIds";
    private static final String SEARCH_GROSSPROFIT_CONFIRM_URL = "/grossProfit/searchGrossProfitConfirm";
    private static final String SELECT_GROSSPROFIT_NO_PAGE_URL = "/grossProfit/selectNoPage";
    private static final String GROSSPROFIT_RECEIVE_CANCEL_URL = "/grossProfit/receiveCancel";
    private static final String GET_TOTAL_OF_SELECTED = "/grossProfit/getTotalOfSelected";

    private static Logger logger = LoggerFactory.getLogger(XpsGrossProfitManager.class);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 生成应收毛利的调用入口
     * @param url   请求路径
     * @param channelId   渠道ID
     * @param channelToken  渠道密码
     * @param uId  用户ID     必传
     * @param uName   用户名称  必传
     * @param projectId   项目ID  必传
     * @param projectName 项目名   必传
     * @param purchaseOrderNo   采购方订单号 必传
     * @param salesOrderNo  销售单号 必传
     * @param currencyCode   货币码  必传
     * @param estimatedGrossProfitAmount   应收额度  必传
     * @param receivedAmount   实收额度  必传
     * @param salesTime   销售时间     必传
     * @param tablePrefix 表前缀名   必传
     */
    public static GongxiaoResult generateYhglobalGrossProfit(  String url,
                                                                String channelId,
                                                                String channelToken,
                                                                String uId,
                                                                String uName,
                                                                Long projectId ,
                                                                String projectName,
                                                                String currencyCode,
                                                               String salesOrderNo,
                                                               Long estimatedGrossProfitAmount,
                                                               String purchaseOrderNo,
                                                               Date salesTime,
                                                               Long receivedAmount, String tablePrefix ){

        logger.info("[generateYhglobalGROSSPROFITLedger] params:  url={}, projectId={} , currencyCode={},brandOrderNo={}," +
                "purchaseOrderNo={}, purchaseTime={} , purchaseShouldPayAmount={}", url,channelId, channelToken, uId, uName,  projectId,projectName,
                currencyCode, salesOrderNo, estimatedGrossProfitAmount, purchaseOrderNo,  salesTime,  receivedAmount);
        // 先做参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (currencyCode == null || currencyCode.trim().length() == 0){
            String msg = String.format("currencyCode can not be empty: currencyCode=%s ", currencyCode);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (salesOrderNo == null || salesOrderNo.trim().length() == 0){
            String msg = String.format("brandOrderNo can not be empty: salesOrderNo=%s ", salesOrderNo);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (purchaseOrderNo == null || purchaseOrderNo.trim().length() == 0){
            String msg = String.format("purchaseOrderNo can not be empty: purchaseOrderNo=%s ", purchaseOrderNo);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (estimatedGrossProfitAmount == null){
            String msg = String.format("estimatedGrossProfitAmount can not be empty: estimatedGrossProfitAmount=%s ", estimatedGrossProfitAmount);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (receivedAmount == null){
            String msg = String.format("receivedAmount can not be empty: receivedAmount=%s ", receivedAmount);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        BigDecimal number = new BigDecimal("100.0");
        // 封装参数
        GrossProfitGenerateRequest data = new GrossProfitGenerateRequest();
        data.setProjectId(projectId);
        data.setProjectName(projectName);
        data.setConfirmedAmount(new BigDecimal(receivedAmount+"").divide(number));
        data.setEstimatedGrossProfitAmount(new BigDecimal(estimatedGrossProfitAmount).divide(number));
        data.setToBeConfirmAmount(new BigDecimal(estimatedGrossProfitAmount - receivedAmount +"").divide(number));
        data.setReceivedAmount(new BigDecimal(receivedAmount+"").divide(number));
        data.setCurrencyCode(currencyCode);
        data.setPurchaseOrderNo(purchaseOrderNo);
        data.setSalesOrderNo(salesOrderNo);
        data.setTablePrefix(tablePrefix);
        data.setUserId(uId);
        data.setUserName(uName);

//        // 发送请求
        GongxiaoResult gongxiaoResult = SendRequestAndGetResponseUtil.sendRequestGetResponse(data, url+GENERATE_GROSSPROFIT_URL, "generateYhglobalGrossProfit");
        return gongxiaoResult;
    }

    /**
     * 毛利核销的调用入口
     * @param projectId 项目ID 必传
     * @param transferIntoPattern 账户类型必传(转入方式)
     * @param useDate 使用日期 可选
     * @param confirmInfoJson 确认信息的json串 必传
     * @param differenceAmountAdjustPattern  差额调整方式
     * @param currencyCode 货币码
     */
    public static GongxiaoResult yhGrossProfitWriteroff( String url,
                                                    String channelId,
                                                    String channelToken,
                                                    String uId,
                                                    String uName,
                                                    Long projectId ,
                                                    String useDate ,
                                                    Byte transferIntoPattern,
                                                    String currencyCode,
                                                    Byte differenceAmountAdjustPattern,
                                                    String confirmInfoJson, String flowNo, List<WriteOffReturnObject> list ){

        logger.info("[grossProfit writeoff] params: url={}, projectId={} , useDate={} ,accountType={},confirmInfoJson={} , projectName={}," +
                "philipDocumentNo={}", url, projectId, useDate, transferIntoPattern, confirmInfoJson, currencyCode, differenceAmountAdjustPattern);

        // 参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (transferIntoPattern == null){
            String msg = String.format("transferIntoPattern can not be empty: transferIntoPattern=%s ", transferIntoPattern);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (confirmInfoJson == null || confirmInfoJson.trim().length() == 0){
            String msg = String.format("confirmInfoJson can not be empty: confirmInfoJson=%s ", confirmInfoJson);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 组装参数
        GrossProfitWriteoffRequest data = new GrossProfitWriteoffRequest();

        data.setConfirmInfoJson(confirmInfoJson);
        data.setProjectId(projectId);
        data.setConfirmInfoJson(confirmInfoJson);
        data.setCurrencyCode(currencyCode);
        data.setDifferenceAmountAdjustPattern(differenceAmountAdjustPattern);
        data.setTransferIntoPattern(transferIntoPattern);
        data.setUserId(uId);
        data.setUserName(uName);

        data.setList(list);
        data.setFlowNo(flowNo);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);

        if (useDate != null) data.setUseDate(useDate);
        // 发送请求
        GongxiaoResult gongxiaoResult = SendRequestAndGetResponseUtil.sendRequestGetResponse(data, url + WRITEOFF_GROSSPROFIT_URL, "yhGrossProfitWriteroff");
        return gongxiaoResult;
    }

    /**
     * "毛利确认处理" 条件查询的入口
     * @param projectId 项目ID 必传
     * @param purchaseOrderNo 采购单号(AD)
     * @param grossProfitStatus 毛利状态 可选
     * @param flowNo 流水号 查询条件 可选
     * @param salesOrderNo 销售订单号(越海)
     * @param dateStart 开始时间 查询条件 可选
     * @param dateEnd 结束时间 查询条件 可选
     * @param pageSize 分页大小 必传
     * @param pageNumber 分页参数 必传
     * */
    public static GongxiaoResult selectByManyCondiitonsHasPage(String url,
                                                               String channelId,
                                                               String channelToken,
                                                               String uId,
                                                               String uName,
                                                               Long projectId,
                                                               String purchaseOrderNo,
                                                               String salesOrderNo,
                                                               String dateStart,
                                                               String dateEnd,
                                                               Byte[] grossProfitStatus,
                                                               String flowNo,
                                                               Integer pageNumber,
                                                               Integer pageSize){

        logger.info("[grossProfit select has page] params: url={}, projectId={} , purchaseOrderNo={} ,supplierOrderNo={},dateStart={} , dateEnd={}," +
                "GROSSPROFITStatus={}, flowNo={}, pageNumber={}, pageSize={}", url, projectId, purchaseOrderNo, salesOrderNo, dateStart,
                dateEnd, grossProfitStatus, flowNo, pageNumber, pageSize);

        // 参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (pageNumber == null){
            String msg = String.format("pageNumber can not be empty:pageNumber=%s ", pageNumber);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (pageSize == null){
            String msg = String.format("pageSize can not be empty: pageSize=%s ", pageSize);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        // 封装参数
        SelectGrossProfitRequest data = new SelectGrossProfitRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setFlowNo(flowNo);
        data.setDateStart(dateStart);
        data.setDateEnd(dateEnd);
        data.setGrossProfitStatus(grossProfitStatus);
        data.setProjectId(projectId);
        data.setPurchaseOrderNo(purchaseOrderNo);
        data.setSalesOrderNo(salesOrderNo);
        data.setPageNumber(pageNumber);
        data.setPageSize(pageSize);
        data.setUserId(uId);
        data.setUserName(uName);

        GongxiaoResult gongxiaoResult = SendRequestAndGetResponseUtil.sendRequestGetResponse(data, url + SELECT_GROSSPROFIT_HAS_PAGE_URL, "selectGrossProfitHasPage");

        return gongxiaoResult;

    }

    /**
     * 导出
     * @param url
     * @param channelId
     * @param channelToken
     * @param uId
     * @param uName
     * @param projectId
     * @param purchaseOrderNo
     * @param salesOrderNo
     * @param dateStart
     * @param dateEnd
     * @param grossProfitStatus
     * @param flowNo
     * @return
     */
    public static GongxiaoResult selectByManyCondiitonsNoPage(String url,
                                                               String channelId,
                                                               String channelToken,
                                                               String uId,
                                                               String uName,
                                                               Long projectId,
                                                               String purchaseOrderNo,
                                                               String salesOrderNo,
                                                               String dateStart,
                                                               String dateEnd,
                                                               Byte[] grossProfitStatus,
                                                               String flowNo){

        logger.info("[grossProfit select has page] params: url={}, projectId={} , purchaseOrderNo={} ,supplierOrderNo={},dateStart={} , dateEnd={}," +
                        "GROSSPROFITStatus={}, flowNo={}, pageNumber={}, pageSize={}", url, projectId, purchaseOrderNo, salesOrderNo, dateStart,
                dateEnd, grossProfitStatus, flowNo);

        // 参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 封装参数
        SelectGrossProfitRequest data = new SelectGrossProfitRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setFlowNo(flowNo);
        data.setDateStart(dateStart);
        data.setDateEnd(dateEnd);
        data.setGrossProfitStatus(grossProfitStatus);
        data.setProjectId(projectId);
        data.setPurchaseOrderNo(purchaseOrderNo);
        data.setSalesOrderNo(salesOrderNo);

        data.setUserId(uId);
        data.setUserName(uName);

        GongxiaoResult gongxiaoResult = null;

        // 发送请求
        String dataJson = JSON.toJSONString(data);
        logger.info("send request to jweb_gross_profit, dataJson :{}",dataJson);
        MediaType jsonMediaType = MediaType.parse("application/json-patch+json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, dataJson);
        Request request = new Request.Builder()
                .url(url+SELECT_GROSSPROFIT_NO_PAGE_URL)
                .post(requestBody)
                .build();

        // 获取响应
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            //logger.info("#export response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from jweb_gross_profit: statusCode=%d", statusCode);
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }


    }
    /**
     * 根据选中的毛利主键查询毛利详情 以便加载"收付款管理 > 毛利确认处理 > 毛利确认列表"页面时填写默认值
     * @param ids 返利主键 必传
     */
    public static GongxiaoResult selectYhglobalGrossProfitLedgerById(String url,
                                                                                    String channelId,
                                                                                    String channelToken,
                                                                                    String uId,
                                                                                    String uName,
                                                                                    Long projectId,
                                                                                    String[] ids){

        logger.info("[grossProfit select yhglobalGROSSPROFITLedger By purchaseGROSSPROFITLedgerId] params: url={}, ids={}, projectId={} }", url, ids, projectId);

        GongxiaoResult gongxiaoResult = null;
        // 数据校验
        if (ids == null || ids.length == 0){
            String msg = String.format("ids can not be empty: ids=%s ", ids);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        SelectGrossProfitByIdRequest data = new SelectGrossProfitByIdRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setProjectId(projectId);
        data.setIds(ids);
        data.setUserId(uId);
        data.setUserName(uName);
        // 数据封装
        gongxiaoResult = SendRequestAndGetResponseUtil.sendRequestGetResponse(data, url+SELECT_GROSSPROFIT_BY_ID_URL, "selectGrossProfitById");

        return gongxiaoResult;
    }

    /**
     * 统计金额
     */
    public static GongxiaoResult getTotalOfSelected(String url,
                                                    String channelId,
                                                    String channelToken,
                                                    String uId,
                                                    String uName,
                                                    Long projectId,
                                                    String[] ids){
        logger.info("[getTotalOfSelected] params: url={}, ids={}, projectId={} }", url, ids.toString(), projectId);

        // 参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 封装参数
        GrossProfitGetTotalRequest data = new GrossProfitGetTotalRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setIds(ids);
        data.setUserId(uId);
        data.setUserName(uName);

        GongxiaoResult gongxiaoResult = SendRequestAndGetResponseUtil.sendRequestGetResponse(data, url + GET_TOTAL_OF_SELECTED, "getTotalOfSelected");

        return gongxiaoResult;
    }

    /**
     * "付款管理 > 毛利台帐"页面  获取台账数据
     * @param projectId  项目ID 必传
     * @param accountType 账户类型 条件 可选
     * @param flowCode 流水号 条件 可选
     * @param useDateStart 使用开始日期 可选
     * @param useDateEnd 使用结束日期 可选
     * @param dateStart 开始时间 可选
     * @param dateEnd 结束时间 可选
     * @param pageNumber 分页参数 必传
     * @param pageSize 分页参数 必传
     */
    public static GongxiaoResult searchGrossProfitConfirm(String url,
                                                     String channelId,
                                                     String channelToken,
                                                     String uId,
                                                     String uName,
                                                     Long projectId,
                                                     String flowCode,
                                                     Integer accountType,
                                                     String useDateStart,
                                                     String useDateEnd,
                                                     String dateStart,
                                                     String dateEnd,
                                                     Integer pageNumber,
                                                     Integer pageSize){

        logger.info("[search grossProfit confirm] params: url={}, ids={}, projectId={}, flowCode={}, accountType={}, useDateStart={}, " +
                "useDateEnd={}, dateStart={}, dateEnd={}, pageNumber={}, pageSize={} }", url, projectId, flowCode, accountType,
                useDateStart, useDateEnd, dateStart, dateEnd, pageNumber, pageSize);
        // 数据校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: ids=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (pageNumber == null){
            String msg = String.format("pageNumber can not be empty: ids=%s ", pageNumber);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (pageSize == null){
            String msg = String.format("pageSize can not be empty: ids=%s ", pageSize);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        // 封装参数
        SearchGrossProfitConfirmRequest data = new SearchGrossProfitConfirmRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setProjectId(projectId);
        data.setPageNumber(pageNumber);
        data.setPageSize(pageSize);
        data.setUserId(uId);
        data.setUserName(uName);

        if (!(flowCode == null || flowCode.trim().length() == 0))data.setFlowCode(flowCode);
        if (!(accountType == null))data.setAccountType(accountType);
        if (useDateStart != null && useDateStart.trim().length() != 0)data.setUseDateStart(useDateStart);
        if (useDateEnd != null && useDateEnd.trim().length() != 0)data.setUseDateEnd(useDateEnd);
        if (dateStart != null && dateStart.trim().length() != 0)data.setDateStart(dateStart);
        if (dateEnd != null && dateEnd.trim().length() != 0)data.setDateEnd(dateEnd);

        GongxiaoResult gongxiaoResult = SendRequestAndGetResponseUtil.sendRequestGetResponse(data, url + SEARCH_GROSSPROFIT_CONFIRM_URL, "GrossProfitSearchGrossProfitConfirm");

        return gongxiaoResult;
    }

    /**
     * 条件查询毛利 导出数据
     * */
//    public static GongxiaoResult selectByManyCondiitonsNoPage(String url,
//                                                              String channelId,
//                                                              String channelToken,
//                                                              String uId,
//                                                              String uName,
//                                                              Long projectId,
//                                                              String purchaseOrderNo,
//                                                              String supplierOrderNo,
//                                                              String dateStart,
//                                                              String dateEnd,
//                                                              Byte[] GROSSPROFITStatus,
//                                                              String flowNo){
//
//        logger.info("[grossProfit select has page] params: url={}, projectId={} , purchaseOrderNo={} ,supplierOrderNo={},dateStart={} , dateEnd={}," +
//                        "GROSSPROFITStatus={}, flowNo={}", url, projectId, purchaseOrderNo, supplierOrderNo, dateStart,
//                dateEnd, GROSSPROFITStatus, flowNo);
//
//        // 参数校验
//        if (projectId == null){
//            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
//            logger.error(msg);
//            throw new IllegalArgumentException(msg);
//        }
//
//        // 封装参数
//
//        return new GongxiaoResult();
//    }

    /**
     * 毛利台账界面 实现毛利确认的取消功能
     * */
    public static GongxiaoResult GrossProfitReceiveCancelConfirmBatch(   String url,
                                                                    String channelId,
                                                                    String channelToken,
                                                                    String uId,
                                                                    String uName,
                                                                    Long projectId,
                                                                    String flowCodes, WriteOffReturnObject object , String flow){
        logger.info("[grossProfit select has page] params: url={}, projectId={} , flowCodes={}", url, projectId, flowCodes);

        // 参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (flowCodes == null){
            String msg = String.format("flowCodes can not be empty: flowCodes=%s ", flowCodes);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 封装参数
        GrossProfitReceiveCancelConfirmBatchRequest data = new GrossProfitReceiveCancelConfirmBatchRequest();
        data.setProjectId(projectId);
        data.setFlowCodes(flowCodes);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setUserId(uId);
        data.setUserName(uName);

        data.setObject(object);
        data.setFlow(flow);
        GongxiaoResult gongxiaoResult = SendRequestAndGetResponseUtil.sendRequestGetResponse(data, url + GROSSPROFIT_RECEIVE_CANCEL_URL, " GrossProfitReceiveCancelConfirmBatch");
        return gongxiaoResult;
    }



}
