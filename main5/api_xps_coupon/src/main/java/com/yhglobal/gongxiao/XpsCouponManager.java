package com.yhglobal.gongxiao;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.coreutil.OkHttpManager;
import com.yhglobal.gongxiao.util.SendRequestAndGetResponseUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 组装生成应收返利的报文
 * @author 王帅
 */
public class XpsCouponManager {

    private static final String GENERATE_COUPON_URL = "/coupon/generate";
    private static final String WRITEOFF_COUPON_URL = "/coupon/writeoff";
    private static final String SELECT_COUPON_HAS_PAGE_URL = "/coupon/selectHasPage";
    private static final String SELECT_COUPON_BY_ID_URL = "/coupon/selectById";
    private static final String SEARCH_COUPON_CONFIRM_URL = "/coupon/searchCouponConfirm";
    private static final String SELECT_COUPON_NO_PAGE_URL = "/coupon/selectNoPage";
    private static final String COUPON_RECEIVE_CANCEL_URL = "/coupon/receiveCancel";
    private static final String GET_TOTAL_OF_SELECTED = "/coupon/getTotalOfSelected";

    private static Logger logger = LoggerFactory.getLogger(XpsCouponManager.class);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 生成应收返利的调用入口
     * @param url
     * @param projectId 项目ID 必传
     * @param currencyCode 货比码 必传
     * @param brandOrderNo 品牌商订单号 必传
     * @param purchaseOrderNo 采购单号 必传
     * @param purchaseTime 采购时间 必传
     * @param purchaseShouldPayAmount 采购应付金额 必传
     */
    public static GongxiaoResult generateYhglobalCouponLedger(  String url,
                                                                String channelId,
                                                                String channelToken,
                                                                String uId,
                                                                String uName,
                                                                Long projectId ,
                                                                String currencyCode,
                                                                String brandOrderNo ,
                                                                String purchaseOrderNo ,
                                                                String purchaseTime,
                                                                Long  purchaseShouldPayAmount,
                                                                Byte couponType ,
                                                                long couponRatio ,
                                                                String projectName ,
                                                                long supplierId ,
                                                                String supplierName ){

        logger.info("[generateYhglobalCouponLedger] params:  url={}, projectId={} , currencyCode={},brandOrderNo={}," +
                "purchaseOrderNo={}, purchaseTime={} , purchaseShouldPayAmount={}", url, projectId, currencyCode, brandOrderNo,
                purchaseOrderNo, purchaseTime, purchaseShouldPayAmount);
        GongxiaoResult gongxiaoResult = null;
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
        if (brandOrderNo == null || brandOrderNo.trim().length() == 0){
            String msg = String.format("brandOrderNo can not be empty: brandOrderNo=%s ", brandOrderNo);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (purchaseOrderNo == null || purchaseOrderNo.trim().length() == 0){
            String msg = String.format("purchaseOrderNo can not be empty: purchaseOrderNo=%s ", purchaseOrderNo);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
//        if (purchaseTime == null || purchaseTime.trim().length() == 0){
//            String msg = String.format("purchaseTime can not be empty: purchaseTime=%s ", purchaseTime);
//            logger.error(msg);
//            throw new IllegalArgumentException(msg);
//        }
        if (purchaseShouldPayAmount == null){
            String msg = String.format("purchaseShouldPayAmount can not be empty: purchaseShouldPayAmount=%s ", purchaseShouldPayAmount);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 封装参数
        CouponGenerateRequest data = new CouponGenerateRequest();
        data.setProjectId(projectId);
        data.setCurrencyCode(currencyCode);
        data.setBrandOrderNo(brandOrderNo);
        data.setPurchaseOrderNo(purchaseOrderNo);
        data.setPurchaseTime(purchaseTime);
        data.setPurchaseShouldPayAmount(purchaseShouldPayAmount);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setCouponType(couponType);
        data.setCouponRatio(couponRatio);
        data.setProjectName(projectName);
        data.setSupplierId(supplierId);
        data.setSupplierName(supplierName);

        // 发送请求
        String dataJson = JSON.toJSONString(data);
        logger.info("send request to jweb_coupon, dataJson :{}",dataJson);
        MediaType jsonMediaType = MediaType.parse("application/json-patch+json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, dataJson);
        Request request = new Request.Builder()
                .url(url + GENERATE_COUPON_URL)
                .post(requestBody)
                .build();

        // 获取响应
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#coupon generate response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from jweb_coupon: statusCode=%d", statusCode);
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 返利核销的调用入口
     * @param projectId 项目ID 必传
     * @param accountType 账户类型必传
     * @param useDate 使用日期 可选
     * @param confirmInfoJson 确认信息的json串 必传
     * @param projectName 项目名称 可选
     * @param philipDocumentNo 飞利浦单据号 京东界面暂时没有而剃须刀必传 此处暂时可选
     */
    public static GongxiaoResult yhCouponWriteroff( String url,
                                                    String channelId,
                                                    String channelToken,
                                                    String uId,
                                                    String uName,
                                                    Long projectId ,
                                                    String useDate ,
                                                    Integer accountType,
                                                    String confirmInfoJson ,
                                                    String projectName,
                                                    String philipDocumentNo,String flowNo, List<WriteOffReturnObject> list ){

        logger.info("[coupon writeoff] params: url={}, projectId={} , useDate={} ,accountType={},confirmInfoJson={} , projectName={}," +
                "philipDocumentNo={}", url, projectId, useDate, accountType, confirmInfoJson, projectName, philipDocumentNo);
        GongxiaoResult gongxiaoResult = null;
        // 参数校验
        if (philipDocumentNo == null || philipDocumentNo.trim().length() == 0){
            return new GongxiaoResult(ErrorCode.PHILIP_DOCUMENT_NO_IS_NULL.getErrorCode(), ErrorCode.PHILIP_DOCUMENT_NO_IS_NULL.getMessage());
        }
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (accountType == null){
            String msg = String.format("accountType can not be empty: accountType=%s ", accountType);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (confirmInfoJson == null || confirmInfoJson.trim().length() == 0){
            String msg = String.format("confirmInfoJson can not be empty: confirmInfoJson=%s ", confirmInfoJson);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 组装参数
        CouponWriteoffRequest data = new CouponWriteoffRequest();
        data.setAccountType(accountType);
        data.setConfirmInfoJson(confirmInfoJson);
        data.setProjectId(projectId);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setUserId(uId);
        data.setUserName(uName);
        data.setFlowNo(flowNo);
        data.setList(list);

        if (useDate != null) data.setUseDate(useDate);
        if (philipDocumentNo != null) data.setPhilipDocumentNo(philipDocumentNo);
        if (projectName != null) data.setProjectName(projectName);
        // 发送请求
        String dataJson = JSON.toJSONString(data);
        logger.info("send request to jweb_coupon, dataJson :{}",dataJson);
        MediaType jsonMediaType = MediaType.parse("application/json-patch+json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, dataJson);
        Request request = new Request.Builder()
                .url(url + WRITEOFF_COUPON_URL)
                .post(requestBody)
                .build();

        // 获取响应
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#coupon writeoff response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from jweb_coupon: statusCode=%d", statusCode);
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * "收付款管理 > 返利确认处理" 条件查询的入口
     * @param projectId 项目ID 必传
     * @param purchaseOrderNo 采购单号 查询条件可选
     * @param couponStatus 返利状态 可选
     * @param flowNo 流水号 查询条件 可选
     * @param supplierOrderNo 供应商订单号
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
                                                               String supplierOrderNo,
                                                               String dateStart,
                                                               String dateEnd,
                                                               Byte[] couponStatus,Byte[] couponType,
                                                               String flowNo,
                                                               Integer pageNumber,
                                                               Integer pageSize){

        logger.info("[coupon select has page] params: url={}, projectId={} , purchaseOrderNo={} ,supplierOrderNo={},dateStart={} , dateEnd={}," +
                "couponStatus={}, flowNo={}, pageNumber={}, pageSize={}", url, projectId, purchaseOrderNo, supplierOrderNo, dateStart,
                dateEnd, couponStatus, flowNo, pageNumber, pageSize);
        GongxiaoResult gongxiaoResult = null;
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
        CouponSelectHasPageRequest data = new CouponSelectHasPageRequest();
        data.setProjectId(projectId);
        data.setPageNumber(pageNumber);
        data.setPageSize(pageSize);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);

        if (purchaseOrderNo != null) data.setPurchaseOrderNo(purchaseOrderNo);
        if (supplierOrderNo != null) data.setSupplierOrderNo(supplierOrderNo);
        if (dateStart != null) data.setDateStart(dateStart);
        if (dateEnd != null) data.setDateEnd(dateEnd);
        if (couponStatus.length != 0) data.setCouponStatus(couponStatus);
        //if (couponType.length != 0)
            data.setCouponType(couponType);
        if (flowNo != null) data.setFlowNo(flowNo);

        // 发送请求
        String dataJson = JSON.toJSONString(data);
        logger.info("send request to jweb_coupon, dataJson :{}",dataJson);
        MediaType jsonMediaType = MediaType.parse("application/json-patch+json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, dataJson);
        Request request = new Request.Builder()
                .url(url + SELECT_COUPON_HAS_PAGE_URL)
                .post(requestBody)
                .build();

        // 获取响应
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#coupon select has page response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from jweb_coupon: statusCode=%d", statusCode);
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;

    }

    /**
     * 根据选中的返利主键查询返利详情 以便加载"收付款管理 > 返利确认处理 > 返利确认列表"页面时填写默认值
     * @param ids 返利主键 必传
     */
    public static GongxiaoResult selectYhglobalCouponLedgerByPurchaseCouponLedgerId(String url,
                                                                                    String channelId,
                                                                                    String channelToken,
                                                                                    String uId,
                                                                                    String uName,
                                                                                    Long projectId,
                                                                                    String[] ids){

        logger.info("[coupon select yhglobalCouponLedger By purchaseCouponLedgerId] params: url={}, ids={}, projectId={} }", url, ids, projectId);

        GongxiaoResult gongxiaoResult = null;
        // 数据校验
        if (ids == null || ids.length == 0){
            String msg = String.format("ids can not be empty: ids=%s ", ids);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 数据封装
        CouponSelectByIdRequest data = new CouponSelectByIdRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setProjectId(projectId);
        data.setIds(ids);

        // 发送请求
        String dataJson = JSON.toJSONString(data);
        logger.info("send request to jweb_coupon, dataJson :{}",dataJson);
        MediaType jsonMediaType = MediaType.parse("application/json-patch+json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, dataJson);
        Request request = new Request.Builder()
                .url(url + SELECT_COUPON_BY_ID_URL)
                .post(requestBody)
                .build();

        // 获取响应
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#coupon select by id response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from jweb_coupon: statusCode=%d", statusCode);
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
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
        CouponGetTotalRequest data = new CouponGetTotalRequest();
        data.setProjectId(projectId);
        data.setIds(ids);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);


        GongxiaoResult gongxiaoResult = SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+GET_TOTAL_OF_SELECTED,"getTotalOfSelected");

        return gongxiaoResult;
    }

    /**
     * "付款管理 > 返利台帐"页面  获取台账数据
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
    public static GongxiaoResult searchCouponConfirm(String url,
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

        logger.info("[search coupon confirm] params: url={}, ids={}, projectId={}, flowCode={}, accountType={}, useDateStart={}, " +
                "useDateEnd={}, dateStart={}, dateEnd={}, pageNumber={}, pageSize={} }", url, projectId, flowCode, accountType,
                useDateStart, useDateEnd, dateStart, dateEnd, pageNumber, pageSize);
        GongxiaoResult gongxiaoResult = null;
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
        SearchCouponConfirmRequest data = new SearchCouponConfirmRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setProjectId(projectId);
        data.setPageNumber(pageNumber);
        data.setPageSize(pageSize);

        if (!(flowCode == null || flowCode.trim().length() == 0))data.setFlowCode(flowCode);
        if (!(accountType == null))data.setAccountType(accountType);
        if (useDateStart != null && useDateStart.trim().length() != 0)data.setUseDateStart(useDateStart);
        if (useDateEnd != null && useDateEnd.trim().length() != 0)data.setUseDateEnd(useDateEnd);
        if (dateStart != null && dateStart.trim().length() != 0)data.setDateStart(dateStart);
        if (dateEnd != null && dateEnd.trim().length() != 0)data.setDateEnd(dateEnd);

        // 发送请求
        String dataJson = JSON.toJSONString(data);
        logger.info("send request to jweb_coupon, dataJson :{}",dataJson);
        MediaType jsonMediaType = MediaType.parse("application/json-patch+json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, dataJson);
        Request request = new Request.Builder()
                .url(url + SEARCH_COUPON_CONFIRM_URL)
                .post(requestBody)
                .build();

        // 获取响应
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#search coupon confirm response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from jweb_coupon: statusCode=%d", statusCode);
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 条件查询返利 导出数据
     * */
    public static GongxiaoResult selectByManyCondiitonsNoPage(String url,
                                                              String channelId,
                                                              String channelToken,
                                                              String uId,
                                                              String uName,
                                                              Long projectId,
                                                              String purchaseOrderNo,
                                                              String supplierOrderNo,
                                                              String dateStart,
                                                              String dateEnd,
                                                              Byte[] couponStatus,Byte[] couponType,
                                                              String flowNo){

        logger.info("[coupon select has page] params: url={}, projectId={} , purchaseOrderNo={} ,supplierOrderNo={},dateStart={} , dateEnd={}," +
                        "couponStatus={}, flowNo={}", url, projectId, purchaseOrderNo, supplierOrderNo, dateStart,
                dateEnd, couponStatus, flowNo);

        // 参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 封装参数
        CouponSelectHasPageRequest data = new CouponSelectHasPageRequest();
        data.setProjectId(projectId);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);

        if (purchaseOrderNo != null) data.setPurchaseOrderNo(purchaseOrderNo);
        if (supplierOrderNo != null) data.setSupplierOrderNo(supplierOrderNo);
        if (dateStart != null) data.setDateStart(dateStart);
        if (dateEnd != null) data.setDateEnd(dateEnd);
        if (couponStatus.length != 0) data.setCouponStatus(couponStatus);
        if (couponType.length != 0) data.setCouponType(couponType);
        if (flowNo != null) data.setFlowNo(flowNo);

        GongxiaoResult gongxiaoResult = SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+SELECT_COUPON_NO_PAGE_URL,"selectByManyCondiitonsNoPage");

        return new GongxiaoResult();
    }

    /**
     * 返利台账界面 实现返利确认的取消功能
     * 注 参数是否要加@nonnull 非空注解 以及@nullable 可空注解
     * */
    public static GongxiaoResult couponReceiveCancelConfirmBatch(   String url,
                                                                    String channelId,
                                                                    String channelToken,
                                                                    String uId,
                                                                    String uName,
                                                                    Long projectId,
                                                                    String flowCodes, WriteOffReturnObject object, String flow){
        logger.info("[coupon select has page] params: url={}, projectId={} , flowCodes={}", url, projectId, flowCodes);

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
        CouponReceiveCancelConfirmBatchRequest data = new CouponReceiveCancelConfirmBatchRequest();
        data.setProjectId(projectId);
        data.setFlowCodes(flowCodes);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setUserId(uId);
        data.setUserName(uName);
        data.setObject(object);
        data.setFlow(flow);


        GongxiaoResult gongxiaoResult = SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+COUPON_RECEIVE_CANCEL_URL,"couponReceiveCancelConfirmBatch");

        return gongxiaoResult;
    }



}
