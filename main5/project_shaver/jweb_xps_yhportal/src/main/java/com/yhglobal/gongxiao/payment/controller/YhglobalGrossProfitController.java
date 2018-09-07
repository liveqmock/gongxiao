package com.yhglobal.gongxiao.payment.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.XpsCouponManager;
import com.yhglobal.gongxiao.XpsGrossProfitManager;
import com.yhglobal.gongxiao.base.model.GrossProfitItem;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.GrossProfitTransferPatternConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.model.WriteOffReturnObject;
import com.yhglobal.gongxiao.payment.entity.YhglobalToReceiveGrossProfitLedger;
import com.yhglobal.gongxiao.payment.entity.YhglobalToReceiveGrossProfitLedgerWriteoffRecord;
import com.yhglobal.gongxiao.payment.microservice.*;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.*;
import okhttp3.Response;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 越海毛利核销的controller
 * @author 王帅
 */
@Controller
@RequestMapping("/yhglobal/grossProfit")
public class YhglobalGrossProfitController {
    private static Logger logger = LoggerFactory.getLogger(YhglobalGrossProfitController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类



    /**
     * 越海毛利确认的入口
     *
     * @param request
     * @param projectId       项目ID
     * @param useDate         使用日期 可选填写   对应页面[收付款管理] > [毛利确认处理] > [毛利确认列表] 的使用日期
     * @param confirmInfoJson 毛利确认信息的json串
     * @return
     */
    @RequestMapping(value = "/writeroff")
    @ResponseBody
    public GongxiaoResult yhglobalGrossProfitWriteroff(HttpServletRequest request,
                                                  Long projectId ,
                                                  String useDate ,
                                                  Byte transferIntoPattern,
                                                  String currencyCode,
                                                  Byte differenceAmountAdjustPattern,
                                                  String confirmInfoJson ){
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage() + ": projectId is null");
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
            logger.info("#traceId={}# [IN][yhglobalgrossProfitWriteroff] params: projectId={}, projectName={}, philipDocumentNo={}, useDate={}, accountType={}, confirmInfoJson={}",
                    traceId, projectId, useDate, transferIntoPattern, currencyCode, differenceAmountAdjustPattern, confirmInfoJson);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            Date now = new Date();
            //根据项目得表前缀
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            // 解析前端封装的json格式数据
            List<YhglobalToReceiveGrossProfitLedger> receiveList
                    = JSON.parseObject(confirmInfoJson, new TypeReference<List<YhglobalToReceiveGrossProfitLedger>>() {
            });

            //查询帐户余额 根据账户类型选择不同的账户
            Long accountTotalAmount = 0L;
            GrossProfitTransferPatternConstant grossProfitTransferPatternConstant = GrossProfitTransferPatternConstant.getGrossProfitTransferPatternConstantByCode(transferIntoPattern.intValue());

            // 获得货币编码
            // String currencyCode = "";
            Integer i = 0; // 成功条数
            Integer length = receiveList.size();// 总条数
            Integer failNo = 0;
            // 生成流水号 给流水,记录以及 调用账户接口时使用
            List<WriteOffReturnObject> list = new ArrayList<>();
            String flowCode = DateTimeIdGenerator.nextId(tablePrefix, BizNumberType.YHGLOBAL_GROSS_PROFIT_WRITEROFF_FLOW);
            for (YhglobalToReceiveGrossProfitLedger receive : receiveList) { // 根据选择的多条核销数据进行逐条账户扣减
                BigDecimal transferAmount = receive.getReceivedAmount(); // 每笔核销扣减的额度
                failNo = length - i;
                switch (grossProfitTransferPatternConstant) { //根据确认的账户类型加载对应的账户余额
                    case COUPON_RECEIVED_ACCOUNT:
                        // 账户额度查询使用grpc调用葛灿接口
                        YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest rpcRequest = YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest.newBuilder()
                                .setRpcHeader(rpcHeader)
                                .setProjectId(projectId)
                                .build();
                        YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
                        PaymentCommonGrpc.AccountAmountResponse rpcResponse = yhglobalAccountService.getYhglobalReceivedAccountAmount(rpcRequest);
                        BigDecimal accountAmount = new BigDecimal(rpcResponse.getCouponAmount() / FXConstant.HUNDRED_DOUBLE + "");
                        if (accountAmount.compareTo(transferAmount) == -1) { // 说明账户额度比交易额度小 及账户额度不足
                            // 账户额度不足
                            logger.error("accountTotalAmount insufficient: GrossProfitId={}", receive.getGrossProfitId());
                            return new GongxiaoResult(ErrorCode.YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), "账户额度不足,共选择了"+length+"条毛利,已成功核销"+i+"条, 核销失败"+failNo+"条");
                        }
                        break;
                    case SALES_DIFFERENCE_ACCOUNT:
                        // 账户额度查询使用grpc调用葛灿接口
                        SupplierAccountServiceStructure.GetSellHighAccountRequest rpcRequest1 = SupplierAccountServiceStructure.GetSellHighAccountRequest.newBuilder()
                                .setRpcHeader(rpcHeader)
                                .setProjectId(projectId)
                                .build();
                        SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
                        SupplierAccountServiceStructure.GetSellHighAccountResponse rpcResponse2 = rpcStub.getSellHighAccount(rpcRequest1);
                        BigDecimal accountAmount2 = new BigDecimal(rpcResponse2.getTotalAmount() / FXConstant.HUNDRED_DOUBLE + "");
                        if (accountAmount2.compareTo(transferAmount) == -1) {
                            // 差价账户余额不足
                            logger.error("accountTotalAmount insufficient: GrossProfitId={}", receive.getGrossProfitId());
                            return new GongxiaoResult(ErrorCode.SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), "账户额度不足,共选择了"+length+"条毛利,已成功核销"+i+"条, 核销失败"+failNo+"条");
                        }
                        break;
                    case PREPAID_RECEIVED_ACCOUNT:
                        //  调用葛灿代垫实收账户接口
                        YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest rpcRequest3 = YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest.newBuilder()
                                .setRpcHeader(rpcHeader)
                                .setProjectId(projectId)
                                .build();
                        YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService3 = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
                        PaymentCommonGrpc.AccountAmountResponse rpcResponse3 = yhglobalAccountService3.getYhglobalReceivedAccountAmount(rpcRequest3);
                        BigDecimal accountAmount3 = new BigDecimal(rpcResponse3.getPrepaidAmount() / FXConstant.HUNDRED_DOUBLE + "");
                        if (accountAmount3.compareTo(transferAmount) == -1) {
                            // 账户额度不足
                            logger.error("accountTotalAmount insufficient: GrossProfitId={}", receive.getGrossProfitId());
                            return new GongxiaoResult(ErrorCode.ACCOUNT_PREPAID_RECEIPT_NOT_SUFFICIENT_FUNDS.getErrorCode(),"账户额度不足,共选择了"+length+"条毛利,已成功核销"+i+"条, 核销失败"+failNo+"条");
                        }
                        break;
                    case PURCHASE_RESERVED_ACCOUNT:
                        // TODO 调用采购预留账户修改接口(账户表以表名前缀区分不同项目)
                        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),"本项目暂不支持该账户类型");

                    case UNIT_PRICE_DISCOUNT_RESERVED_ACCOUNT:
                        // TODO 调用单价折扣预留账户修改接口(账户表以表名前缀区分不同项目)
                        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),"本项目暂不支持该账户类型");
                    case SUPPLIER_CASH_TRANSFER_IN:
                        // TODO 供应商现金转入 (账户表以表名前缀区分不同项目) 是供应商现金账户?
                        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),"本项目暂不支持该账户类型");
                    default:
                        throw new IllegalArgumentException("unknown grossProfitTransferPatternConstant: " + grossProfitTransferPatternConstant.getAccountName());
                }

                Long amount = - Math.round(transferAmount.doubleValue() * FXConstant.HUNDRED); // 交易金额类型转换
                WriteOffReturnObject object = updateAccount(rpcHeader, projectId, amount, now, flowCode, grossProfitTransferPatternConstant);
                list.add(object);
                i++; // 统计核销成功的条数
            }
                gongxiaoResult = XpsGrossProfitManager.yhGrossProfitWriteroff(portalConfig.getGrossProfitUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, useDate, transferIntoPattern, currencyCode, differenceAmountAdjustPattern, confirmInfoJson,
                        flowCode, list);
           return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), "共选择了"+length+"条毛利,已成功核销"+i+"条, 核销失败"+(length - i)+"条");

        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    /**
     * 确认和取消确认都要调用的根据账户类型修改 账户额度的方法
     * @param transactionAmount 正值表示增加 负值表示扣款
     * @param grossProfitTransferPatternConstant
     */
    WriteOffReturnObject updateAccount(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, long transactionAmount,
                                       Date now, String sourceFlowNo, GrossProfitTransferPatternConstant grossProfitTransferPatternConstant){

        WriteOffReturnObject writeOffReturnObject = new WriteOffReturnObject();
        switch (grossProfitTransferPatternConstant) {
            case COUPON_RECEIVED_ACCOUNT:
                //扣减返利实收帐户余额
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
            case SALES_DIFFERENCE_ACCOUNT:
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
            case PREPAID_RECEIVED_ACCOUNT:
                //扣减代垫实收帐户余额
                PaymentCommonGrpc.WriteOffRequest rpcRequest3 = PaymentCommonGrpc.WriteOffRequest.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(projectId)
                        .setAmount(transactionAmount).setWriteOffFlowNo(sourceFlowNo).setTransactionTime(now.getTime())
                        .build();
                YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService3 = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
                PaymentCommonGrpc.WriteOffResponse rpcResponse3 = yhglobalAccountService3.updateYhglobalReceivedPrepaidAccount(rpcRequest3);
                if (rpcResponse3.getReturnCode() == 0){
                    // 调用成功 返回账户交易前后的额度
                    writeOffReturnObject.setReturnCode(rpcResponse3.getReturnCode());
                    writeOffReturnObject.setAmountBeforeTrans(rpcResponse3.getAmountBeforeTransaction());
                    writeOffReturnObject.setAmountAfterTrans(rpcResponse3.getAmountAfterTransaction());
                }else {
                    logger.error("updateYhglobalReceivedPrepaidAccount  fail ");
                }
                break;
            case PURCHASE_RESERVED_ACCOUNT:
                break;
            case UNIT_PRICE_DISCOUNT_RESERVED_ACCOUNT:
                break;
            case SUPPLIER_CASH_TRANSFER_IN:
                break;
            default:
                logger.error("unsupported account type: {}", grossProfitTransferPatternConstant);
        }
        return writeOffReturnObject;
    }

    /**
     * [收付款管理] > [毛利确认处理] 条件查询的入口
     *
     * @param request
     * @param projectId         项目ID
     * @param purchaseOrderNo   采购单号
     * @param dateStart         开始日期
     * @param dateEnd           结束日期
     * @param flowNo            流水号
     * @return
     */
    @RequestMapping(value = "/selectByManyConditions")
    @ResponseBody
    public GongxiaoResult selectByManyConditions(HttpServletRequest request, Long projectId,
                                                                     String purchaseOrderNo,
                                                                     String salesOrderNo,
                                                                     String dateStart,
                                                                     String dateEnd,
                                                                     Byte[] grossProfitStatus,
                                                                     String flowNo,
                                                                     Integer pageNumber,
                                                                     Integer pageSize){
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        String traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        logger.info("#traceId={}# [IN][getProductInfo] params:  projectId={}, purchaseOrderNo={}," +
                        "supplierOrderNo={}, dateStart={}, dateEnd={}, couponStatus={}, flowNo={}, pageNumber={}, pageSize={} ", traceId, projectId,purchaseOrderNo,
                salesOrderNo,dateStart,dateEnd,grossProfitStatus.toString(),flowNo,pageNumber,pageSize);

        GongxiaoResult gongxiaoResult = null;
        try {
            // GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            gongxiaoResult = XpsGrossProfitManager.selectByManyCondiitonsHasPage(portalConfig.getGrossProfitUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, purchaseOrderNo, salesOrderNo, dateStart, dateEnd, grossProfitStatus,
                    flowNo, pageNumber, pageSize);

        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;


    }

    /**
     * 毛利确认界面数据导出
     *
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public GongxiaoResult export(HttpServletRequest request, HttpServletResponse response, Long projectId,
                           String purchaseOrderNo,
                           String salesOrderNo,
                           String dateStart,
                           String dateEnd,
                           Byte[] grossProfitStatus,
                           String flowNo){
//        if (projectId == null){
//            throw new RuntimeException("the projectId is null");
//        }
//        String traceId = null;
//        traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//        logger.info("#traceId={}# [IN][getProductInfo] params:  projectId={}, purchaseOrderNo={}," +
//                        "supplierOrderNo={}, dateStart={}, dateEnd={}, couponStatus={}, flowNo={} ", traceId, projectId,purchaseOrderNo,
//                salesOrderNo,dateStart,dateEnd,grossProfitStatus.toString(),flowNo);
       GongxiaoResult gongxiaoResult = null;
        try {
            //GongxiaoResult gongxiaoResult = XpsGrossProfitManager.selectByManyCondiitonsNoPage(portalConfig.getGrossProfitUrl(), portalConfig.getXpsChannelId(), portalConfig.getGrossProfitChannelToken(),
                //    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, purchaseOrderNo, salesOrderNo, dateStart, dateEnd, grossProfitStatus,
                //    flowNo);
            //List<GrossProfitItem> grossProfitItemList = (List<GrossProfitItem>) gongxiaoResult.getData();

            List<GrossProfitItem> grossProfitItemList = new ArrayList<>();
            GrossProfitItem item1 = new GrossProfitItem();
            item1.setEstimatedGrossProfitAmount(new BigDecimal("34"));
            item1.setConfirmStatusStr("大斧子");
            GrossProfitItem item2 = new GrossProfitItem();
            item2.setEstimatedGrossProfitAmount(new BigDecimal("341"));
            item2.setConfirmStatusStr("");

            GrossProfitItem item3 = new GrossProfitItem();
            item3.setEstimatedGrossProfitAmount(new BigDecimal("341"));
            item3.setConfirmStatusStr(null);
            grossProfitItemList.add(item1);
            grossProfitItemList.add(item2);
            grossProfitItemList.add(item3);

            String downFileName = new String("毛利确认列表.xls");
            ExcelUtil<GrossProfitItem> util = new ExcelUtil<GrossProfitItem>(GrossProfitItem.class);
            Workbook workbook = util.getListToExcel(grossProfitItemList,"流水列表");
            ExcelDownUtil.resetResponse(response,workbook,downFileName);

            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),grossProfitItemList);
            return gongxiaoResult;
        }catch (Exception e){
            // logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }


    }
    /**
     *
     * 根据选中的毛利主键查询毛利详情 以便加载"收付款管理 > 毛利确认处理 > 毛利确认列表"页面时填写默认值
     *
     * @param ids  毛利ID拼接的字符串
     * */
    @RequestMapping(value = "/selectById")
    @ResponseBody
    public GongxiaoResult selectYhglobalCouponLedgerByPurchaseCouponLedgerId(HttpServletRequest request, String[] ids, Long projectId){
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][yhglobalgrossProfitWriteroff] params: traceId:{}, ids={}", traceId,ids.toString());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            GongxiaoResult gongxiaoResult  = XpsGrossProfitManager.selectYhglobalGrossProfitLedgerById(portalConfig.getGrossProfitUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, ids);

            return gongxiaoResult;
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 统计选中的毛利的应收等金额总和
     * */
    @RequestMapping(value = "/getTotal")
    @ResponseBody
    public GongxiaoResult getTotalOfSelected(HttpServletRequest request, String[] ids, Long projectId){
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getTotalOfSelected] params: traceId:{}, ids={}", traceId,ids.toString());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            GongxiaoResult gongxiaoResult = XpsGrossProfitManager.getTotalOfSelected(portalConfig.getGrossProfitUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, ids);
            return gongxiaoResult;
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }


    /**
     * "付款管理 > 毛利台帐"页面  获取台账数据
     *
     * @param request
     * @param projectId         项目Id
     * @param flowCode          流水号
     * @param transferIntoPattern       账户类型
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
    public GongxiaoResult getsCouponConfirmList(HttpServletRequest request, Long projectId,
                                                String flowCode,
                                                Integer transferIntoPattern,
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
                            "dateStart={},dateEnd={}, pageNumber={}, pageSize={}", traceId,projectId, flowCode, transferIntoPattern,useDateStart,useDateEnd,dateStart,dateEnd,pageNumber,pageSize);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
            gongxiaoResult = XpsGrossProfitManager.searchGrossProfitConfirm(portalConfig.getGrossProfitUrl(), portalConfig.getXpsChannelId(), xpsChannelSecret,
                    portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, flowCode, transferIntoPattern, useDateStart, useDateEnd, dateStart, dateEnd, pageNumber, pageSize);


        }catch(Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * "首付款管理 > 毛利台帐"页面 选中毛利后，点"取消确认"的入口
     *
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
            logger.info("#traceId={}# [IN][yhglobalCouponWriteroff] params: traceId:{}, flowCodes={}", traceId);

            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            List<YhglobalToReceiveGrossProfitLedgerWriteoffRecord> receiveList
                    = JSON.parseObject(cancelConfirmJson, new TypeReference<List<YhglobalToReceiveGrossProfitLedgerWriteoffRecord>>() {
            });
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            for (YhglobalToReceiveGrossProfitLedgerWriteoffRecord record : receiveList){
                GrossProfitTransferPatternConstant transferPatternConstant = GrossProfitTransferPatternConstant.getGrossProfitTransferPatternConstantByName(record.getTransferIntoPatternStr());
                Date now = new Date();  // 该流水生成时间 也在调用账户中使用
                // 生成流水号
                String flow = DateTimeIdGenerator.nextId(tablePrefix, BizNumberType.YHGLOBAL_GROSS_PROFIT_CANCEL_WRITEROFF_FLOW);
                BigDecimal receiptAmount = record.getReceiptAmount();
                long amount = Math.round(receiptAmount.doubleValue() * FXConstant.HUNDRED);// 类型转换
                WriteOffReturnObject object = updateAccount(rpcHeader, projectId, amount, now, flow, transferPatternConstant);

                gongxiaoResult = XpsGrossProfitManager.GrossProfitReceiveCancelConfirmBatch(portalConfig.getGrossProfitUrl(), portalConfig.getXpsChannelId(),
                        xpsChannelSecret,portalUserInfo.getUserId(), portalUserInfo.getUserName(), projectId, record.getFlowNo(), object, flow);
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
                                 String projectName,
                                 String currencyCode,
                                 Byte confirmStatus,
                                 String salesOrderNo,
                                 BigDecimal estimatedGrossProfitAmount,
                                 String purchaseOrderNo,
                                 BigDecimal toBeConfirmAmount,
                                 Date salesTime,
                                 BigDecimal confirmedAmount,
                                 BigDecimal receivedAmount, String tablePrefix){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            String channelId = "1";
            String channelToken="shaver";
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();
//            gongxiaoResult = XpsGrossProfitManager.generateYhglobalGrossProfit(portalConfig.getGrossProfitUrl(), portalConfig.getXpsChannelId(),
//                    xpsChannelSecret,"123", "gecan",projectId, projectName, currencyCode, confirmStatus, salesOrderNo, estimatedGrossProfitAmount,
//                    purchaseOrderNo, toBeConfirmAmount, salesTime, confirmedAmount, receivedAmount, tablePrefix);
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }



}
