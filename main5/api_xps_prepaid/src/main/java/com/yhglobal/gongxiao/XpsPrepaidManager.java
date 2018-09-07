package com.yhglobal.gongxiao;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.util.OkHttpUtil;
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
 * 组装代垫功能相关的报文
 * @author 王帅
 */
public class XpsPrepaidManager {

    private static final String GENERATE_PREPAID_URL = "/prepaid/generate";  // 生成新的应收代垫
    private static final String WRITEOFF_PREPAID_URL = "/prepaid/writeoff";  // 代垫确认
    private static final String SELECT_PREPAID_HAS_PAGE_URL = "/prepaid/selectHasPage";  // 分页查询列表
    private static final String SELECT_PREPAID_BY_ID_URL = "/prepaid/selectById";  // 根据ID查询
    private static final String GET_MERGE_TOTAL_URL = "/prepaid/getsMergeCount";  // 统计代垫金额
    private static final String ADD_NEW_PREPAID_PAYMENT_ORDER = "/prepaid/addInfo";  // 新增代垫付款单
    private static final String SELECT_PREPAID_PAYMENT_ORDER = "/prepaid/select";  // 条件查询代垫付款单
    private static final String GET_PREPAID_PAYMENT_ORDER_DETAIL = "/prepaid/getDetailInfo";  // 代垫付款单详情
    private static final String SEARCH_PREPAID_CONFIRM_URL = "/prepaid/searchPrepaidConfirm";  // 代垫台账查询
    private static final String SELECT_PREPAID_NO_PAGE_URL = "/prepaid/selectNoPage";  // 不分页查询列表
    private static final String PREPAID_RECEIVE_CANCEL_URL = "/prepaid/receiveCancel"; // 取消确认

    private static final String REPORT_DATA_LEFT_ONE = "/prepaid/reportLeftOne"; // 左1报表
    private static final String REPORT_DATA_RIGHT_ONE = "/prepaid/reportRightOne"; // 右1报表

    private static Logger logger = LoggerFactory.getLogger(XpsPrepaidManager.class);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**************************************代垫付款单相关***********************************************/
    /**
     * 新增代垫付款单
     */
    public static GongxiaoResult addPrepaidInfo( String url,
                                                 String channelId,
                                                 String channelToken,
                                                 String userId,
                                                 String userName,
                                                 Long projectId,
                                                 String projectName,
                                                 Integer supplierId,
                                                 String supplierName,
                                                 String payer,
                                                 String receivables,
                                                 String settlementNo,
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
                                                 String itemJson, Integer settlementType){

        logger.info("[addPrepaidInfo] params: projectId={}, projectName={}, supplierId={}, supplierName={}, payer={}, receivables={}, settlementNo={}, " +
                        "dateBusiness={},taxNo={}, contactInfo={}, provinceId={}, provinceName={}, cityId={}, cityName={}, districtId={}, districtName={}," +
                        " streetAddress={}, accountCNY={}, bankNameCNY={}, remark={}, itemJson={} ", projectId, projectName, supplierId, supplierName, payer,
                receivables, settlementNo, dateBusiness, taxNo, contactInfo, provinceId, provinceName, cityId, cityName, districtId, districtName, streetAddress,
                accountCNY, bankNameCNY, remark, itemJson, settlementType);
        // if (projectId == null) projectId = 146798161L;
        // 先做参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        AddPrepaidPaymentOrderRequest data = new AddPrepaidPaymentOrderRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setProjectId(projectId);
        data.setProjectName(projectName);
        data.setSupplierId(supplierId);
        data.setSupplierName(supplierName);
        data.setPayer(payer);
        data.setReceivables(receivables);
        data.setSettlementNo(settlementNo);
        data.setDateBusiness(dateBusiness);
        data.setTaxNo(taxNo);
        data.setContactInfo(contactInfo);
        data.setProvinceId(provinceId);
        data.setProvinceName(provinceName);
        data.setCityId(cityId);
        data.setCityName(cityName);
        data.setDistrictId(districtId);
        data.setDistrictName(districtName);
        data.setStreetAddress(streetAddress);
        data.setAccountCNY(accountCNY);
        data.setBankNameCNY(bankNameCNY);
        data.setRemark(remark);
        data.setItemJson(itemJson);
        data.setUserId(userId);
        data.setUserName(userName);

        data.setSettlementType(settlementType);

        return SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+ADD_NEW_PREPAID_PAYMENT_ORDER,"addPrepaidInfo");
    }


    /**
     * 收付款管理>代垫付款单管理  代垫付款单列表查询
     */
    public static GongxiaoResult getsPrepaidInfoList(  String url,
                                                       String channelId,
                                                       String channelToken,
                                                       String userId,
                                                       String userName,
                                                        Long projectId,
                                                       String prepaidNo,
                                                       String receivables,
                                                       String dateStart,
                                                       String dateEnd,
                                                      Integer pageNumber,
                                                        Integer pageSize){
        //if (projectId == null) projectId = 146798161L; // 测试防空
        // 先做参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        logger.info("[getsPrepaidInfoList] params: projectId={}, prepaidNo={},receivables={}, dateStart={}, dateEnd={},pageNumber={}, pageSize={}",
                projectId,prepaidNo,receivables, dateStart, dateEnd, pageNumber,pageSize);

        SelectPrepaidPaymentOrderRequest data = new SelectPrepaidPaymentOrderRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setProjectId(projectId);
        data.setPrepaidNo(prepaidNo);
        data.setReceivables(receivables);
        data.setDateStart(dateStart);
        data.setDateEnd(dateEnd);
        data.setPageNumber(pageNumber);
        data.setPageSize(pageSize);
        data.setUserId(userId);
        data.setUserName(userName);

        return SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+SELECT_PREPAID_PAYMENT_ORDER,"getsPrepaidInfoList");
    }

    /**
     * 收付款管理>代垫付款单管理  代垫付款单详情
     */
    public static GongxiaoResult getPrepaidInfoById( String url,
                                              String channelId,
                                              String channelToken,
                                              String userId,
                                              String userName,
                                              Long projectId,
                                              Integer id){
        logger.info("[getPrepaidInfoById] params: id={}, ",id);

        GetPrepaidPaymentOrderDetailRequest data = new GetPrepaidPaymentOrderDetailRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setUserId(userId);
        data.setUserName(userName);
        data.setProjectId(projectId);
        data.setId(id);

        return SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+GET_PREPAID_PAYMENT_ORDER_DETAIL,"getPrepaidInfoById");
    }


    /***********************************************应收代垫相关*************************************************************/
    /**
     * 生成应收代垫的调用入口  参数均为必传
     * @param url
     * @param projectId 项目ID 必传
     * @param currencyCode 货比码 必传
     * @param salesContractNo   销售单号9-3更新
     */
    public static GongxiaoResult generateYhglobalPrepaidLedger(String url,
                                                               String channelId,
                                                               String channelToken,
                                                               String userId,
                                                               String userName,
                                                               Long projectId ,
                                                               String currencyCode,
                                                               String orderId ,
                                                               String projectName ,
                                                               Integer supplierId,
                                                               String supplierName,
                                                               String salesContractNo ,
                                                               Long receiveAmount , Date outWarehouseDate){ // , Date outWarehouseDate
        //if (projectId == null) projectId = 146798161L; // 测试防空
        logger.info("[addReceive] params: url={}, orderId={}, projectId={}, projectName={}, supplierId={}, supplierName={},  salesContractNo={},  receiveAmount={},  currencyCode={},",
                 orderId, projectId, projectName, supplierId, supplierName, salesContractNo, receiveAmount, currencyCode);
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
        if (orderId == null || orderId.trim().length() == 0){
            String msg = String.format("orderId can not be empty: orderId=%s ", orderId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (projectName == null || projectName.trim().length() == 0){
            String msg = String.format("projectName can not be empty: projectName=%s ", projectName);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (supplierId == null){
            String msg = String.format("supplierId can not be empty: supplierId=%s ", supplierId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (supplierName == null || supplierName.trim().length() == 0 ){
            String msg = String.format("supplierName can not be empty: supplierName=%s ", supplierName);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (salesContractNo == null || salesContractNo.trim().length() == 0 ){
            String msg = String.format("salesContractNo can not be empty: salesContractNo=%s ", salesContractNo);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (receiveAmount == null ){
            String msg = String.format("receiveAmount can not be empty: receiveAmount=%s ", receiveAmount);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 封装参数
        PrepaidGenerateRequest data = new PrepaidGenerateRequest();
        data.setProjectId(projectId);
        data.setCurrencyCode(currencyCode);
        data.setOrderId(orderId);
        data.setProjectName(projectName);
        data.setReceiveAmount(receiveAmount);
        data.setSalesContractNo(salesContractNo);
        data.setSupplierId(supplierId);
        data.setSupplierName(supplierName);

        data.setOutWarehouseDate(outWarehouseDate);

        data.setUserId(userId);
        data.setUserName(userName);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);

        return gongxiaoResult = SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+GENERATE_PREPAID_URL,"generateYhglobalPrepaidLedger");

    }

    /**
     * 代垫核销的调用入口
     * @param projectId 项目ID 必传
     * @param accountType 账户类型必传
     * @param useDate 使用日期 可选
     * @param confirmInfoJson 确认信息的json串 必传
     * @param projectName 项目名称 可选
     * @param philipDocumentNo 飞利浦单据号 京东界面暂时没有而剃须刀必传 此处暂时可选
     */
    public static GongxiaoResult yhPrepaidWriteroff( String url,
                                                    String channelId,
                                                    String channelToken,
                                                     String userId,
                                                     String userName,
                                                    Long projectId ,
                                                     String projectName,
                                                     String useDate,
                                                     Integer accountType,
                                                    String philipDocumentNo,
                                                     String confirmInfoJson ,List<WriteOffReturnObject> list){
        //if (projectId == null) projectId = 146798161L; // 测试防空
        logger.info("[Prepaid writeoff] params: url={}, projectId={} , useDate={} ,accountType={},confirmInfoJson={} , projectName={}," +
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
        PrepaidWriteoffRequest data = new PrepaidWriteoffRequest();
        data.setAccountType(accountType);
        data.setConfirmInfoJson(confirmInfoJson);
        data.setProjectId(projectId);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setList(list);
        data.setUserId(userId);
        data.setUserName(userName);

        data.setList(list);
        if (useDate != null) data.setUseDate(useDate);
        if (philipDocumentNo != null) data.setPhilipDocumentNo(philipDocumentNo);
        if (projectName != null) data.setProjectName(projectName);


        // 获取响应
        return   gongxiaoResult = SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+WRITEOFF_PREPAID_URL,"prepaidWriteoff");

    }

    /**
     * "收付款管理 > 代垫确认处理" 条件查询的入口
     * @param projectId 项目ID 必传
     * @param PrepaidStatus 代垫状态 可选
     * @param flowNo 流水号 查询条件 可选
     * @param dateStart 开始时间 查询条件 可选
     * @param dateEnd 结束时间 查询条件 可选
     * @param pageSize 分页大小 必传
     * @param pageNumber 分页参数 必传
     * */
    public static GongxiaoResult selectByManyCondiitonsHasPage(String url,
                                                               String channelId,
                                                               String channelToken,
                                                               String userId,
                                                               String userName,
                                                               Long projectId,
                                                               String dateStart,
                                                               String dateEnd,
                                                               String dateStartConfirm,
                                                                String dateEndConfirm,
                                                               Byte[] PrepaidStatus,
                                                               String flowNo,
                                                               Integer pageNumber,
                                                               Integer pageSize,
                                                               String orderId,
                                                               Integer accountType
                                                             ){
        //if (projectId == null) projectId = 146798161L; // 测试防空
        logger.info("[Prepaid select has page] params: url={}, projectId={} , purchaseOrderNo={} ,supplierOrderNo={},dateStart={} , dateEnd={}," +
                "PrepaidStatus={}, flowNo={}, pageNumber={}, pageSize={}, orderId={}, accountType={}", url, projectId,  dateStart,
                dateEnd, PrepaidStatus, flowNo, pageNumber, pageSize, orderId, accountType);
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
        PrepaidSelectHasPageRequest data = new PrepaidSelectHasPageRequest();
        data.setProjectId(projectId);
        data.setPageNumber(pageNumber);
        data.setPageSize(pageSize);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);

        data.setUserId(userId);
        data.setUserName(userName);

        if (dateStart != null) data.setDateStart(dateStart);
        if (dateEnd != null) data.setDateEnd(dateEnd);
        if (dateStartConfirm != null) data.setDateStartConfirm(dateStartConfirm);
        if (dateEndConfirm != null) data.setDateEndConfirm(dateEndConfirm);
        if (PrepaidStatus.length != 0) data.setPrepaidStatus(PrepaidStatus);
        if (flowNo != null) data.setFlowNo(flowNo);
        if (accountType != null) data.setAccountType(accountType);
        if (orderId != null) data.setOrderId(orderId);


        return SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+SELECT_PREPAID_HAS_PAGE_URL,"selectByManyCondiitonsHasPage");

    }

    /**
     * 根据选中的代垫主键查询代垫详情 以便加载"收付款管理 > 代垫确认处理 > 代垫确认列表"页面时填写默认值
     * @param ids 代垫主键 必传
     */
    public static GongxiaoResult selectYhglobalPrepaidLedgerByPurchasePrepaidLedgerId(String url,
                                                                                    String channelId,
                                                                                    String channelToken,
                                                                                      String userId,
                                                                                      String userName,
                                                                                    Long projectId,
                                                                                    String[] ids){

        logger.info("[Prepaid select yhglobalPrepaidLedger By purchasePrepaidLedgerId] params: url={}, ids={} }", url, ids);

        // 数据校验
        if (ids == null || ids.length == 0){
            String msg = String.format("ids can not be empty: ids=%s ", ids);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 数据封装
        PrepaidSelectByIdRequest data = new PrepaidSelectByIdRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setIds(ids);
        data.setProjectId(projectId);

        data.setUserId(userId);
        data.setUserName(userName);
        return SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+SELECT_PREPAID_BY_ID_URL,"selectPrepaidById");
    }


    /**
     * 代垫确认界面 点击统计金额的功能入口
     */
    public static GongxiaoResult getsMergeCount(String url,
                                                String channelId,
                                                String channelToken,
                                                String userId,
                                                String userName,
                                                Long projectId,
                                                String receiveIds){
        //if (projectId == null) projectId = 146798161L; // 测试防空
        logger.info("prepaidGetsMergeCount params: projectId={},  receiveIds={}", projectId, receiveIds);
        // 参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        PrepaidGetMergeCountRequest data = new PrepaidGetMergeCountRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setProjectId(projectId);
        data.setReceiveIds(receiveIds);

        data.setUserId(userId);
        data.setUserName(userName);

        return SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+GET_MERGE_TOTAL_URL,"prepaidGetsMergeCount");
    }

    /**
     * "付款管理 > 代垫台帐"页面  获取台账数据
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
    public static GongxiaoResult searchPrepaidConfirm(String url,
                                                     String channelId,
                                                     String channelToken,
                                                      String userId,
                                                      String userName,
                                                     Long projectId,
                                                     String flowCode,
                                                     Integer accountType,
                                                     String useDateStart,
                                                     String useDateEnd,
                                                     String dateStart,
                                                     String dateEnd,
                                                     Integer pageNumber,
                                                     Integer pageSize){
        //if (projectId == null) projectId = 146798161L; // 测试防空
        logger.info("[search Prepaid confirm] params: url={}, ids={}, projectId={}, flowCode={}, accountType={}, useDateStart={}, " +
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
        SearchPrepaidConfirmRequest data = new SearchPrepaidConfirmRequest();
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setProjectId(projectId);
        data.setPageNumber(pageNumber);
        data.setPageSize(pageSize);

        data.setUserId(userId);
        data.setUserName(userName);

        if (!(flowCode == null || flowCode.trim().length() == 0))data.setFlowCode(flowCode);
        if (!(accountType == null))data.setAccountType(accountType);
        if (useDateStart != null && useDateStart.trim().length() != 0)data.setUseDateStart(useDateStart);
        if (useDateEnd != null && useDateEnd.trim().length() != 0)data.setUseDateEnd(useDateEnd);
        if (dateStart != null && dateStart.trim().length() != 0)data.setDateStart(dateStart);
        if (dateEnd != null && dateEnd.trim().length() != 0)data.setDateEnd(dateEnd);

       return SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+SEARCH_PREPAID_CONFIRM_URL,"searchPrepaidConfirm");
    }

//    /**
//     * 条件查询代垫 导出数据
//     * */
//    public static GongxiaoResult selectByManyCondiitonsNoPage(String url,
//                                                              String channelId,
//                                                              String channelToken,
//                                                              Long projectId,
//                                                              String purchaseOrderNo,
//                                                              String supplierOrderNo,
//                                                              String dateStart,
//                                                              String dateEnd,
//                                                              Byte[] PrepaidStatus,
//                                                              String flowNo){
//
//        logger.info("[Prepaid select has page] params: url={}, projectId={} , purchaseOrderNo={} ,supplierOrderNo={},dateStart={} , dateEnd={}," +
//                        "PrepaidStatus={}, flowNo={}", url, projectId, purchaseOrderNo, supplierOrderNo, dateStart,
//                dateEnd, PrepaidStatus, flowNo);
//
//        // 参数校验
//        if (projectId == null){
//            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
//            logger.error(msg);
//            throw new IllegalArgumentException(msg);
//        }
//
//        // 封装参数
//        PrepaidSelectHasPageRequest data = new PrepaidSelectHasPageRequest();
//        data.setProjectId(projectId);
//        data.setChannelId(channelId);
//        data.setChannelToken(channelToken);
//
//        if (purchaseOrderNo != null) data.setPurchaseOrderNo(purchaseOrderNo);
//        if (supplierOrderNo != null) data.setSupplierOrderNo(supplierOrderNo);
//        if (dateStart != null) data.setDateStart(dateStart);
//        if (dateEnd != null) data.setDateEnd(dateEnd);
//        if (PrepaidStatus.length != 0) data.setPrepaidStatus(PrepaidStatus);
//        if (flowNo != null) data.setFlowNo(flowNo);
//
//        return SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+SELECT_PREPAID_NO_PAGE_URL,"selectByManyCondiitonsNoPage");
//
//    }

    /**
     * 代垫台账界面 实现代垫确认的取消功能
     * 注 参数是否要加@nonnull 非空注解 以及@nullable 可空注解
     * */
    public static GongxiaoResult PrepaidReceiveCancelConfirmBatch(   String url,
                                                                    String channelId,
                                                                    String channelToken,
                                                                     String userId,
                                                                     String userName,
                                                                    Long projectId,
                                                                     String flowCodes, WriteOffReturnObject object, String flow){
        logger.info("[Prepaid select has page] params: url={}, projectId={} , flowCodes={}", url, projectId, flowCodes);
        //if (projectId == null) projectId = 146798161L; // 测试防空
        // 参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 封装参数
        PrepaidReceiveCancelConfirmBatchRequest data = new PrepaidReceiveCancelConfirmBatchRequest();
        data.setProjectId(projectId);
        data.setFlowCodes(flowCodes);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);
        data.setObject(object);
        data.setFlow(flow);
        data.setUserId(userId);
        data.setUserName(userName);


        return SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+PREPAID_RECEIVE_CANCEL_URL,"PrepaidReceiveCancelConfirmBatch");

    }


    /**
     * 报表左1
     * 注 参数是否要加@nonnull 非空注解 以及@nullable 可空注解
     * */
    public static GongxiaoResult ReportLeftOne(   String url,
                                                                     String channelId,
                                                                     String channelToken,
                                                                     String userId,
                                                                     String userName,
                                                                     Long projectId){
        logger.info("[Prepaid select has page] params: url={}, projectId={} , flowCodes={}", url, projectId);
        //if (projectId == null) projectId = 146798161L; // 测试防空
        // 参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 封装参数
        PrepaidReceiveCancelConfirmBatchRequest data = new PrepaidReceiveCancelConfirmBatchRequest();
        data.setProjectId(projectId);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);

        data.setUserId(userId);
        data.setUserName(userName);


        return SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+REPORT_DATA_LEFT_ONE,"getReportLeftOneData");

    }

    /**
     * 报表右1
     * 注 参数是否要加@nonnull 非空注解 以及@nullable 可空注解
     * */
    public static GongxiaoResult ReportRightOne(   String url,
                                                                     String channelId,
                                                                     String channelToken,
                                                                     String userId,
                                                                     String userName,
                                                                     Long projectId){
        logger.info("[Prepaid select has page] params: url={}, projectId={} , flowCodes={}", url, projectId);
        //if (projectId == null) projectId = 146798161L; // 测试防空
        // 参数校验
        if (projectId == null){
            String msg = String.format("projectId can not be empty: projectId=%s ", projectId);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        // 封装参数
        PrepaidReceiveCancelConfirmBatchRequest data = new PrepaidReceiveCancelConfirmBatchRequest();
        data.setProjectId(projectId);
        data.setChannelId(channelId);
        data.setChannelToken(channelToken);

        data.setUserId(userId);
        data.setUserName(userName);


        return SendRequestAndGetResponseUtil.sendRequestGetResponse(data,url+REPORT_DATA_RIGHT_ONE,"getReportRightOneData");

    }

}
