package com.yhglobal.gongxiao.coupon.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ConfirmStatus;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;

import com.yhglobal.gongxiao.constant.RollbackStatus;
import com.yhglobal.gongxiao.coupon.Service.PrepaidService;
import com.yhglobal.gongxiao.coupon.constant.*;
import com.yhglobal.gongxiao.coupon.dao.*;
import com.yhglobal.gongxiao.coupon.dao.prepaidPaymentOrderDao.YhglobalPrepaidInfoDao;
import com.yhglobal.gongxiao.coupon.dao.prepaidPaymentOrderDao.YhglobalPrepaidInfoItemDao;
import com.yhglobal.gongxiao.coupon.model.*;
import com.yhglobal.gongxiao.coupon.model.PrepaidPaymentOrder.YhglobalPrepaidInfo;
import com.yhglobal.gongxiao.coupon.model.PrepaidPaymentOrder.YhglobalPrepaidInfoItem;
import com.yhglobal.gongxiao.coupon.utils.GetFirstAndLastDayOfMonthUtil;
import com.yhglobal.gongxiao.coupon.utils.GetPastSixMonthUtil;
import com.yhglobal.gongxiao.coupon.utils.GetRateUtil;

import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.model.WriteOffReturnObject;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.util.ValidationUtil;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author 王帅
 */
@Service
public class PrepaidServiceImpl implements PrepaidService {


     static Logger logger = LoggerFactory.getLogger(PrepaidServiceImpl.class);


    @Autowired
    private YhglobalPrepaidInfoDao yhglobalPrepaidInfoDao;

    @Autowired
    private YhglobalPrepaidInfoItemDao yhglobalPrepaidInfoItemDao;

    @Autowired
    private YhglobalPrepaidLedgerWriteoffFlowDao yhglobalPrepaidLedgerWriteoffFlowDao;

    @Autowired
    private YhglobalPrepaidLedgerWriteoffRecordDao yhglobalPrepaidLedgerWriteoffRecordDao;

    @Autowired
    private YhglobalToReceivePrepaidLedgerDao yhglobalToReceivePrepaidLedgerDao;


    @Autowired
    private PrepaidToBeReceiveAmountReportDao prepaidToBeReceiveAmountReportDao;

    @Autowired
    private PrepaidReceivedAmountReportDao prepaidReceivedAmountReportDao;

    /**********************************************代垫付款单相关*******************************************************/

    @Override
    public RpcResult addPrepaidInfo(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, String projectName, Integer supplierId, String supplierName, String payer,
                              String receivables, String settlementNo,Integer settlementType,String dateBusiness, String taxNo,
                              String contactInfo, Integer provinceId, String provinceName, Integer cityId,
                              String cityName, Integer districtId, String districtName, String streetAddress,
                              String accountCNY, String bankNameCNY, String remark, String itemJson) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, projectName={}, supplierId={}, supplierName={}, payer={}, receivables={}, " +
                        "settlementNo={}, dateBusiness={}, taxNo={}, contactInfo={}, provinceId={}, provinceName={}, cityId={}, " +
                        "cityName={}, districtId={}, districtName={}, streetAddress={}, accountCNY={}, bankNameCNY={},  bankNameCNY={}, itemJson={}",
                rpcHeader, projectId, projectName, supplierId, supplierName, payer, receivables, settlementNo, dateBusiness, taxNo, contactInfo, provinceId, provinceName, cityId,
                cityName, districtId, districtName, streetAddress, accountCNY, bankNameCNY, itemJson);
        RpcResult rpcResult = null;
        try{
            // String tablePrefix = getTablePrefix(projectId);
             String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            //String tablePrefix = "shaver";
            Date now = new Date();
            //明细json转list
            List<YhglobalPrepaidInfoItem> itemList = JSON.parseObject(itemJson, new TypeReference<List<YhglobalPrepaidInfoItem>>() {});
            //封装代垫付款单信息
            YhglobalPrepaidInfo info = new YhglobalPrepaidInfo();
            info.setPrepaidNo(DateTimeIdGenerator.nextId(tablePrefix, BizNumberType.YHGLOBAL_PREPAID_INFO));
            info.setPrepaidNo(DateTimeIdGenerator.nextId(tablePrefix, BizNumberType.YHGLOBAL_PREPAID_INFO));
            info.setProjectId(projectId);
            info.setProjectName(projectName);
            info.setSupplierId(supplierId);
            info.setSupplierName(supplierName);
            info.setPayer(payer);
            info.setReceivables(receivables);
            info.setSettlementNo(settlementNo);
            Date date = null;
            try{
                date = DateUtil.stringToDate(dateBusiness, DateUtil.DATE_FORMAT);
            }catch(Exception ex){
                return new RpcResult(ErrorCode.DATE_FORMAT_WRONG.getErrorCode(), ErrorCode.DATE_FORMAT_WRONG.getMessage());
            }
            info.setDateBusiness(date);
            info.setTaxNo(taxNo);
            info.setContactInfo(contactInfo);
            info.setProvinceId(provinceId);
            info.setProvinceName(provinceName);
            info.setCityId(cityId);
            info.setCityName(cityName);
            info.setDistrictId(districtId);
            info.setDistrictName(districtName);
            info.setStreetAddress(streetAddress);
            info.setAccountCNY(accountCNY);
            info.setBankNameCNY(bankNameCNY);
            info.setRemark(remark);

            double standardCurrencyAmount = 0.0;
            for (YhglobalPrepaidInfoItem item : itemList) {
                standardCurrencyAmount += item.getStandardCurrencyAmountDouble();
            }
            info.setStandardCurrencyAmount(Math.round(standardCurrencyAmount * FXConstant.HUNDRED_DOUBLE)); //本位币金额
            info.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            info.setDataVersion(0L);
            info.setCreatedByName(rpcHeader.getUsername());
            info.setCreateTime(now);
            info.setLastUpdateTime(now);
            info.setSettlementType(settlementType);
            info.setTablePrefix(tablePrefix);
            //保存代垫付款单
            yhglobalPrepaidInfoDao.insert(info);
            //保存代垫付款单明细
            for (YhglobalPrepaidInfoItem infoItem : itemList) {
                YhglobalPrepaidInfoItem item = new YhglobalPrepaidInfoItem();
                item.setInfoId(info.getPrepaidId());
                item.setApplicationAmount(Math.round(infoItem.getApplicationAmountDouble() * FXConstant.HUNDRED_DOUBLE));
                item.setExchangeRate(Math.round(infoItem.getExchangeRateDouble() * FXConstant.HUNDRED_DOUBLE));
                item.setTaxPoint(Math.round(infoItem.getTaxPointDouble() * FXConstant.MILLION_DOUBLE));
                item.setStandardCurrencyAmount(Math.round(infoItem.getStandardCurrencyAmountDouble() * FXConstant.HUNDRED_DOUBLE));
                item.setTaxSubsidy(Math.round(infoItem.getTaxSubsidyDouble() * FXConstant.HUNDRED_DOUBLE));
                item.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                item.setDataVersion(0L);
                item.setCreatedByName(rpcHeader.getUsername());
                item.setCreateTime(now);
                item.setLastUpdateTime(now);
                item.setCostType(infoItem.getCostType());
                item.setCurrencyCode(infoItem.getCurrencyCode());
                item.setInvoiceType(infoItem.getInvoiceType());
                item.setReason(infoItem.getReason());
                item.setIsTaxSubsidy(infoItem.getIsTaxSubsidy());
                item.setTablePrefix(tablePrefix);
                yhglobalPrepaidInfoItemDao.insert(item);
            }
            //获取中国货币编码
            Currency currency = Currency.getInstance(Locale.CHINA);
            String currencyCode = currency.getCurrencyCode();
            Date outWarehouseDate = null;
            try {
                outWarehouseDate = DateUtil.stringToDate(dateBusiness);
            }catch (Exception e){
                logger.error("the date format is wrong");
                throw new RuntimeException(e.getMessage());
            }

            //记录应收
            ErrorCode  code = this.addReceive(rpcHeader, null, info.getProjectId(), info.getProjectName(),
                    info.getSupplierId(), info.getSupplierName(), null, Math.round(standardCurrencyAmount * FXConstant.HUNDRED_DOUBLE),
                    currencyCode, outWarehouseDate);
            rpcResult = new RpcResult(code.getErrorCode(), code.getMessage());
        }catch(Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("addPrepaidInfo error!", e);
        }


        return rpcResult;
    }

    @Override
    public PageInfo<YhglobalPrepaidInfo> getsPrepaidInfoList(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, String prepaidNo, String receivables, Date dateStart, Date dateEnd, Integer page, Integer pageSize) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:page={}, pageSize={}", rpcHeader, page, pageSize);
        List<YhglobalPrepaidInfo> infoList = null;
        PageInfo<YhglobalPrepaidInfo> pageInfo = null;
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            PageHelper.startPage(page, pageSize);
            infoList = yhglobalPrepaidInfoDao.selectAll(tablePrefix, projectId, prepaidNo, receivables, dateStart, dateEnd);
            for (YhglobalPrepaidInfo info : infoList) {
                info.setStandardCurrencyAmountDouble(info.getStandardCurrencyAmount() / FXConstant.HUNDRED_DOUBLE);
            }
            pageInfo = new PageInfo<YhglobalPrepaidInfo>(infoList);
            logger.info("#traceId={}# [OUT]: getPrepaidInfoById success pageInfo={}", rpcHeader.getTraceId(), pageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("getPrepaidInfoById error!", e);
        }
        return pageInfo;
    }

    @Override
    public YhglobalPrepaidInfo getPrepaidInfoById(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, Integer id) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:id={}", rpcHeader, id);

        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            // 常量赋值
            YhglobalPrepaidInfo yhglobalPrepaidInfo  = yhglobalPrepaidInfoDao.selectByPrimaryKey(tablePrefix, id);
            SettlementTypeConstant settlementTypeConstantByCode = SettlementTypeConstant.getSettlementTypeConstantByCode(yhglobalPrepaidInfo.getSettlementType());
            yhglobalPrepaidInfo.setSettlementTypeString(settlementTypeConstantByCode.getSettlementType());
            if (yhglobalPrepaidInfo != null) {
                List<YhglobalPrepaidInfoItem> itemList = yhglobalPrepaidInfoItemDao.selectByInfoId(tablePrefix, yhglobalPrepaidInfo.getPrepaidId());
                // 常量赋值
                for (YhglobalPrepaidInfoItem infoItem:itemList){
                   InvoiceTypeConstant invoiceTypeConstant = InvoiceTypeConstant.getInvoiceTypeConstantByCode(infoItem.getInvoiceType());
                   infoItem.setInvoiceTypeString(invoiceTypeConstant.getInvoiceType());
                   TaxRateSsubsidyConstant taxRateSsubsidyConstant = TaxRateSsubsidyConstant.getTaxRateSsubsidyConstantByCode(infoItem.getIsTaxSubsidy());
                   infoItem.setIsTaxSubsidyString(taxRateSsubsidyConstant.getYesOrNo());
               }

                yhglobalPrepaidInfo.setItemList(itemList);
            }
            logger.info("#traceId={}# [OUT]: getPrepaidInfoById success yhglobalPrepaidInfo={}", rpcHeader.getTraceId(), yhglobalPrepaidInfo);
            return yhglobalPrepaidInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("getPrepaidInfoById error!", e);
        }

    }






    /**********************************************应收代垫相关*******************************************************/

    private ErrorCode addReceive(GongxiaoRpc.RpcHeader rpcHeader, String orderId, Long projectId, String projectName, Integer supplierId, String supplierName,
                                 String salesContractNo, Long receiveAmount, String currencyCode, Date  outWarehouseDate) {
        logger.info("#traceId={}# [IN][addReceive] params: orderId={}, projectId={}, projectName={}, supplierId={}, supplierName={},  salesContractNo={},  receiveAmount={},  currencyCode={},",
                rpcHeader.getTraceId(), orderId, projectId, projectName, supplierId, supplierName, salesContractNo, receiveAmount, currencyCode);
        int result = 0;
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            // 判断是否已经生成 如果没有生成则插入
            YhglobalToReceivePrepaidLedger yhprepaid = yhglobalToReceivePrepaidLedgerDao.selectByOrderId(tablePrefix, orderId);
            if (yhprepaid == null) {
                //封装参数
                YhglobalToReceivePrepaidLedger model = new YhglobalToReceivePrepaidLedger();
                model.setOrderId(orderId);//订单号
                model.setProjectId(projectId);//项目ID
                model.setSupplierId(supplierId);
                model.setSupplierName(supplierName);
                model.setProjectName(projectName);//项目名称
                model.setSalesContractNo(salesContractNo);//销售合同号
                model.setReceiveAmount(receiveAmount);//应收金额
                model.setCurrencyCode(currencyCode);//币种
                model.setReceiveStatus(ReceiveStatus.RECEIVE_STATUS_UNCONFIRMED.getCode());//应收状态 未确认
                model.setToBeConfirmAmount(model.getReceiveAmount());
                model.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                model.setDataVersion(0L);
                model.setCreatedByName(rpcHeader.getUsername());
                Date now = new Date();
                model.setCreateTime(outWarehouseDate);
                model.setLastUpdateTime(now);
                //插入数据
                model.setTablePrefix(tablePrefix);
                result = yhglobalToReceivePrepaidLedgerDao.addReceive(model);
            } else {
                logger.info("#traceId={}# [OUT]: the to receive coupon ledger has been existed the orderId is  ", rpcHeader.getTraceId(), orderId);
                return ErrorCode.PREPAID_RECEIVE_ALREADY_EXISTED;
            }
            if (result == 1) {
                logger.info("#traceId={}# [OUT]: addReceive success ", rpcHeader.getTraceId());
            } else {
                logger.info("#traceId={}# [OUT]: addReceive failure ", rpcHeader.getTraceId());
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return ErrorCode.UNKNOWN_ERROR;
        }
        return ErrorCode.SUCCESS;
    }
    /**
     * 销售单生成应收代垫
     * */
    @Override
    public RpcResult generateYhglobalPrepaidLedger(GongxiaoRpc.RpcHeader rpcHeader, String orderId, Long projectId, String projectName, Integer supplierId,
                                                  String supplierName, String salesContractNo, Long receiveAmount, String currencyCode, Date outWarehouseDate) {

        logger.info("#traceId={}# [IN][addReceive] params: orderId={}, projectId={}, projectName={}, supplierId={}, supplierName={},  salesContractNo={},  receiveAmount={},  currencyCode={},",
                rpcHeader, orderId, projectId, projectName, supplierId, supplierName, salesContractNo, receiveAmount, currencyCode);
        ErrorCode result = addReceive(rpcHeader, orderId, projectId, projectName, supplierId, supplierName, salesContractNo, receiveAmount, currencyCode, outWarehouseDate);
        return new RpcResult(result.getErrorCode(), result.getMessage());

    }




    /**
     * 代垫核销
     */
    @Override
    public RpcResult yhPrepaidWriteroff( GongxiaoRpc.RpcHeader rpcHeader, Long projectId,
                                         String useDate ,
                                         Integer accountType,
                                         String confirmInfoJson ,
                                         String projectName,
                                         String philipDocumentNo, List<WriteOffReturnObject> list) {
        Date date = null;

        try {
            date = DateUtil.stringToDate(useDate);
        } catch (Exception e) {
        }
        logger.info("#traceId={}# [IN][receiveConfirm] params:projectId={}, useDate={},  accountType={},  confirmInfo={}",
                rpcHeader, projectId, useDate, accountType, confirmInfoJson, projectName, philipDocumentNo);
        try {

            Date now = new Date();

            List<YhglobalToReceivePrepaidLedger> receiveList = null;
            try {
                receiveList = JSON.parseObject(confirmInfoJson, new TypeReference<List<YhglobalToReceivePrepaidLedger>>() {
                });
            } catch (JSONException e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);

            }
//            long confirmAmountTotal = 0l;
//            String currencyCode = "";
//            for (YhglobalToReceivePrepaidLedger receive : receiveList) {
//                confirmAmountTotal += Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED_DOUBLE);
//                if ("".equals(currencyCode))currencyCode = receive.getCurrencyCode();
//            }
            // 获得货币编码
            String currencyCode = "";
            Integer i = 0; // 成功条数
            Integer length = receiveList.size();// 总条数
            Integer failNo = 0;
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            // 生成流水号
            String flowCode = DateTimeIdGenerator.nextId(tablePrefix, BizNumberType.YHGLOBAL_PREPAID_WRITEOFF_FLOW);
            // 获取账户类型常量
            AccountType type = AccountType.getAccountTypeByCode(accountType);

            for (int j = 0;j< receiveList.size();j++ ) {
                WriteOffReturnObject writeOffReturnObject = list.get(j);
                if (writeOffReturnObject.getReturnCode() != 0 || writeOffReturnObject == null){
                    continue; // 当条核销数据 交易失败或返回数据为空时 跳出该条数据  执行下一条
                }
                YhglobalToReceivePrepaidLedger receive = receiveList.get(j);
                Long transferAmount = Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED); // 每笔核销扣减的额度
                failNo = length - i;
//            switch (type) { //根据确认的账户类型加载对应的账户余额
//                case ACCOUNT_PREPAID_RECEIPTED:
//                    YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest rpcRequest = YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest.newBuilder()
//                            .setRpcHeader(rpcHeader)
//                            .setProjectId(projectId)
//                            .build();
//                    YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
//                    PaymentCommonGrpc.AccountAmountResponse rpcResponse = yhglobalAccountService.getYhglobalReceivedAccountAmount(rpcRequest);
//                    if (rpcResponse.getPrepaidAmount() < transferAmount){
//                        // 账户额度不足
//                        logger.error("accountTotalAmount insufficient: philipDocumentNo={}", philipDocumentNo);
//                        return new RpcResult(ErrorCode.ACCOUNT_PREPAID_RECEIPT_NOT_SUFFICIENT_FUNDS.getErrorCode(),"账户额度不足,共选择了"+length+"条代垫,已成功核销"+i+"条, 核销失败"+failNo+"条");
//                    }
//                    break;
//                case ACCOUNT_SALE_DIFFERENCE:
//                    SupplierAccountServiceStructure.GetSellHighAccountRequest rpcRequest1 = SupplierAccountServiceStructure.GetSellHighAccountRequest.newBuilder()
//                            .setRpcHeader(rpcHeader)
//                            .setProjectId(projectId)
//                            .build();
//                    SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
//                    SupplierAccountServiceStructure.GetSellHighAccountResponse rpcResponse1 = rpcStub.getSellHighAccount(rpcRequest1);
//                    if (rpcResponse1.getTotalAmount() < transferAmount){
//                        // 差价账户余额不足
//                        logger.error("accountTotalAmount insufficient: philipDocumentNo={}", philipDocumentNo);
//                        return new RpcResult(ErrorCode.SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), "账户额度不足,共选择了"+length+"条代垫,已成功核销"+i+"条, 核销失败"+failNo+"条");
//                    }
//                    break;
//                default:
//                    throw  new IllegalArgumentException("unknown couponConfirmAccountType: " + type.getMessage());
//            }

            Long amountBeforeTransaction = 0L;//流水发生前的余额
            Long transactionAmount = -transferAmount;//交易金额
            Long amountAfterTransaction = 0L;//流水发生后的余额
//            if (AccountType.ACCOUNT_PREPAID_RECEIPTED.getCode() == accountType) {
//                //修改实收帐户余额
//                // WriteOffReturnObject object = yhglobalReceivedPrepaidAccountService.updateYhglobalReceivedPrepaidAccount(rpcHeader, "CNY", projectId, transactionAmount, now,"","" );
//                PaymentCommonGrpc.WriteOffRequest rpcRequest = PaymentCommonGrpc.WriteOffRequest.newBuilder()
//                        .setRpcHeader(rpcHeader)
//                        .setProjectId(projectId)
//                        .setAmount(transactionAmount).setWriteOffFlowNo(flowCode).setCurrencyCode(currencyCode).setTransactionTime(now.getTime())
//                        .build();
//                YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
//                PaymentCommonGrpc.WriteOffResponse rpcResponse = yhglobalAccountService.updateYhglobalReceivedPrepaidAccount(rpcRequest);
//
//                if(rpcResponse.getReturnCode() == ErrorCode.SUCCESS.getErrorCode()){
//                    amountBeforeTransaction = rpcResponse.getAmountBeforeTransaction();
//                    amountAfterTransaction = rpcResponse.getAmountAfterTransaction();
//                }else {
//                    return new RpcResult(ErrorCode.UPDATE_ACCOUNT_WRONG.getErrorCode(), ErrorCode.UPDATE_ACCOUNT_WRONG.getMessage());
//                }
//            } else if (AccountType.ACCOUNT_SALE_DIFFERENCE.getCode() == accountType) {
//                // supplierSellHeightTransferAccountService.writeoffSellHeightAmount(rpcHeader, "CNY", projectId, confirmAmountTotal, now);
//                PaymentCommonGrpc.WriteOffRequest rpcRequest1 = PaymentCommonGrpc.WriteOffRequest.newBuilder()
//                        .setRpcHeader(rpcHeader)
//                        .setProjectId(projectId)
//                        .setAmount(transactionAmount).setTransactionTime(now.getTime()).setWriteOffFlowNo(flowCode)
//                        .build();
//                SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
//                PaymentCommonGrpc.WriteOffResponse rpcResponse1 = rpcStub.writeOffUpdateSellHighAccount(rpcRequest1);
//                if (rpcResponse1.getReturnCode() == 0){
//                    // 调用成功 返回账户交易前后的额度
//                    amountBeforeTransaction = rpcResponse1.getAmountBeforeTransaction();
//                    amountAfterTransaction = rpcResponse1.getAmountAfterTransaction();
//                }else {
//                    logger.error("updateSellHighAccount  fail ");
//                }
//            }
            //记录流水
            try {
                addWriteoffFlow(rpcHeader,flowCode, projectId, projectName,
                        amountBeforeTransaction,
                        transactionAmount,
                        amountAfterTransaction,
                        now,
                        null,
                        "",
                        "",
                        null,
                        null,
                        "");
            } catch (Exception ex) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + ex.getMessage(), ex);
            }


                //查询应收记录
                YhglobalToReceivePrepaidLedger receiveInfo = yhglobalToReceivePrepaidLedgerDao.selectById(tablePrefix, receive.getReceiveId());
                if (receiveInfo.getDataVersion() != receive.getDataVersion()) {
                    //数据版本不一致
                    return new RpcResult(ErrorCode.DATE_VERSION_DIFFER.getErrorCode(), ErrorCode.DATE_VERSION_DIFFER.getMessage());
                }
//                if (receiveInfo.getToBeConfirmAmount().longValue() < receive.getConfirmAmountDouble() * FXConstant.HUNDRED) {
//                    //超出
//                    return new RpcResult(ErrorCode.ACCOUNT_PREPAID_CONFIRM_AMOUNT_EXCESS.getErrorCode(), ErrorCode.ACCOUNT_PREPAID_CONFIRM_AMOUNT_EXCESS.getMessage());
//                }

                YhglobalPrepaidLedgerWriteoffRecord record = new YhglobalPrepaidLedgerWriteoffRecord();
                record.setReceiveId(receive.getReceiveId());
                record.setConfirmAmount(Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED_DOUBLE));
                record.setReceiptAmount(Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED_DOUBLE));
                record.setProjectId(projectId);
                record.setUseDate(date);
                record.setAccountType(accountType);
                record.setPhilipNo(philipDocumentNo);
                record.setCurrencyCode(currencyCode);  // 从应收中拿
                record.setFlowCode(flowCode);
                record.setAccountType(accountType);
                record.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                record.setDataVersion(0L);
                record.setCreatedByName(rpcHeader.getUsername());
                record.setCreateTime(now);
                record.setLastUpdateTime(now);
                record.setIsRollback(RollbackStatus.ROLLBACK_STATUS_NOES.getCode());
                //保存核销记录
                record.setTablePrefix(tablePrefix);
                yhglobalPrepaidLedgerWriteoffRecordDao.addWriteoffRecord( record);
                //修改应收记录
                Long confirmAmount = 0l;
                if (receiveInfo.getConfirmAmount() == null) {
                    confirmAmount = Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED_DOUBLE);
                } else {
                    confirmAmount = receiveInfo.getConfirmAmount() + Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED_DOUBLE);
                }
                receiveInfo.setConfirmAmount(confirmAmount);//确认金额
                Long receiptAmount = 0l;
                if (receiveInfo.getReceiptAmount() == null) {
                    receiptAmount = Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED_DOUBLE);
                } else {
                    receiptAmount = receiveInfo.getReceiptAmount() + Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED_DOUBLE);
                }
                receiveInfo.setReceiptAmount(receiptAmount);//实收金额
                receiveInfo.setToBeConfirmAmount(receiveInfo.getToBeConfirmAmount() - Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED_DOUBLE));//待确认金额

                Byte targetStatus = updateCouponStatus(receiveInfo.getToBeConfirmAmount(), receiveInfo.getReceiveAmount());

                receiveInfo.setReceiveStatus(targetStatus);
                if (ValidationUtil.isNotEmpty(receiveInfo.getConfirmRecord())) {
                    receiveInfo.setConfirmRecord(receiveInfo.getConfirmRecord() + "," + record.getReceiveId());
                } else {
                    receiveInfo.setConfirmRecord(record.getReceiveId().toString());
                }
                // yhglobalToReceivePrepaidLedgerDao.updateById(receiveInfo);//
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "代垫确认");
                String appendTraceLog = TraceLogUtil.appendTraceLog(receiveInfo.getTracelog(), traceLog);
                yhglobalToReceivePrepaidLedgerDao.updatePrepaidLedgerForWriteoff(tablePrefix, receiveInfo.getReceiveId(), receiveInfo.getConfirmAmount(), receiveInfo.getReceiptAmount(), receiveInfo.getToBeConfirmAmount(),
                        (int) targetStatus, receiveInfo.getDataVersion(), appendTraceLog, now);

                //i++; // 统计核销成功的条数

            }
            return new RpcResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("receiveConfirm error!", e);
        }


    }

    /**
     * 生成流水
     */
    private boolean addWriteoffFlow(GongxiaoRpc.RpcHeader rpcHeader,
                                   String flowCode,
                                   Long projectId,
                                   String projectName,
                                   Long amountBeforeTransaction,
                                   Long transactionAmount,
                                   Long amountAfterTransaction,
                                   Date transactionTime,
                                   Integer supplierId,
                                   String supplierName,
                                   String salesOrderId,
                                   Long distributorId,
                                   String distributorName,
                                   String differenceAmountAdjustPattern) {
        logger.info("#traceId={}# [IN][receiveConfirm] params:projectId={}, projectName={},  amountBeforeTransaction={},  transactionAmount={}, " +
                        " amountAfterTransaction={},  transactionTime={}, supplierId={}, supplierName={}, salesOrderId={}, distributorId={}, distributorName={}, differenceAmountAdjustPattern={}",
                rpcHeader, projectId, projectName, amountBeforeTransaction, transactionAmount, amountAfterTransaction, transactionTime, supplierId, supplierName, salesOrderId, distributorId, distributorName, differenceAmountAdjustPattern);
        YhglobalPrepaidLedgerWriteoffFlow flow = new YhglobalPrepaidLedgerWriteoffFlow();

        flow.setFlowCode(flowCode);
        flow.setProjectId(projectId);
        flow.setProjectName(projectName);
        flow.setAmountBeforeTransaction(amountBeforeTransaction);
        flow.setTransactionAmount(transactionAmount);
        flow.setAmountAfterTransaction(amountAfterTransaction);
        flow.setTransactionTime(transactionTime);
        flow.setSupplierId(supplierId);
        flow.setSupplierName(supplierName);
        flow.setSalesOrderId(salesOrderId);
        flow.setDistributorId(distributorId);
        flow.setDistributorName(distributorName);
        flow.setDifferenceAmountAdjustPattern(differenceAmountAdjustPattern);
        flow.setCreateTime(new Date());
        String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

        flow.setTablePrefix(tablePrefix);
        int count = yhglobalPrepaidLedgerWriteoffFlowDao.addFlow( flow);
        if (count == 1) {
            logger.info("#traceId={}# [OUT]: addReceive success flowCode={}", rpcHeader.getTraceId(), flowCode);
            return true;
        }
        return false;
    }



    /**
     * 根据选中的代垫主键查询代垫详情 以便加载"收付款管理 > 代垫确认处理 > 代垫确认列表"页面时填写默认值
     */
    @Override
    public List<YhglobalToReceivePrepaidLedger> selectPreapidById(GongxiaoRpc.RpcHeader rpcHeader,  Long projectId, List<String> idsList) {

        logger.info("#traceId={}# [IN][selectByIds] params:ids.size={}", idsList.size());
        try {
            //去重
            List<String> idList = new ArrayList<>();
            for (int i = 0; i < idsList.size(); i++) {
                if (!idList.contains(idsList.get(i))) {
                    idList.add(idsList.get(i));
                }
            }

            //  拼接ID 一次查出所有对象
            StringBuffer sb = new StringBuffer();
            boolean flag = false;
            for (String id : idList){
                if (flag){
                    sb.append(",");
                }else {
                    flag = true;
                }
                sb.append(id);
            }
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            // 使用逗号拼接ID in语句一次查出
            List<YhglobalToReceivePrepaidLedger> list = yhglobalToReceivePrepaidLedgerDao.selectByIdsStr(tablePrefix, sb.toString());
            for (YhglobalToReceivePrepaidLedger ledger : list){
                ledger.setReceiveAmountDouble(ledger.getReceiveAmount() != null ? ledger.getReceiveAmount() / FXConstant.HUNDRED_DOUBLE : 0);
                ledger.setToBeConfirmAmountDouble(ledger.getToBeConfirmAmount() != null ? ledger.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : 0);
            }
            return  list;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByIds error!", e);
        }

    }
    @Override
    public YhglobalToReceivePrepaidCount selectReceiveAndRecordCount(GongxiaoRpc.RpcHeader rpcHeader, Long projectId,  String ids){

        logger.info("#traceId={}# [IN][selectReceiveAndRecordCount] params:projectId={}", rpcHeader, projectId);
        YhglobalToReceivePrepaidCount result = new YhglobalToReceivePrepaidCount();
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            List<YhglobalToReceivePrepaidCount> countList = yhglobalToReceivePrepaidLedgerDao.selectReceiveAndRecordCount(tablePrefix, projectId, ids);
            for (YhglobalToReceivePrepaidCount count : countList) {
                if (count.getReceiveAmount() != null) {
                    if (result.getReceiveAmount() == null) {
                        result.setReceiveAmount(count.getReceiveAmount());
                    } else {
                        result.setReceiveAmount(result.getReceiveAmount() + count.getReceiveAmount());
                    }
                }
                if (count.getReceiptAmount() != null) {
                    if (result.getReceiptAmount() == null) {
                        result.setReceiptAmount(count.getReceiptAmount());
                    } else {
                        result.setReceiptAmount(result.getReceiptAmount() + count.getReceiptAmount());
                    }
                }
                if (count.getToBeConfirmAmount() != null) {
                    if (result.getToBeConfirmAmount() == null) {
                        result.setToBeConfirmAmount(count.getToBeConfirmAmount());
                    } else {
                        result.setToBeConfirmAmount(result.getToBeConfirmAmount() + count.getToBeConfirmAmount());
                    }
                }
                if (count.getConfirmAmount() != null) {
                    if (result.getConfirmAmount() == null) {
                        result.setConfirmAmount(count.getConfirmAmount());
                    } else {
                        result.setConfirmAmount(result.getConfirmAmount() + count.getConfirmAmount());
                    }
                }
            }
            return result;

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByProjectId error!", e);
        }

    }

    @Override
    public RpcResult getReportLeftOneData(GongxiaoRpc.RpcHeader rpcHeader, Long projectId) {

        try {
            // 需求点：先把未收代垫分为四个阶段：30-=60天、60-=90天、90-=180天、180天以上，然后根据金额来判断占比。
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
//            Long thirtyDaysTimes = 30 * 24 * 60 * 60 * 1000L; // 计算30天的毫秒数
//            Date now = new Date(); // 当前时间
            List<BigDecimal> amountList = new ArrayList<>();
//            Date startDateStatus1 = new Date(now.getTime() - thirtyDaysTimes * 2);
//            Date endDateStatus1 = new Date(now.getTime() - thirtyDaysTimes);
           BigDecimal number = new BigDecimal("100.0");
//            YhglobalToReceivePrepaidLedger count1 = yhglobalToReceivePrepaidLedgerDao.getDataCountByTime(tablePrefix, projectId, startDateStatus1, endDateStatus1);
//            Long amount1;
//            if (count1 != null) {
//                amount1 = count1.getToBeConfirmAmount();
//            }else {
//                amount1 = 0L;
//            }
//            amountList.add(new BigDecimal(amount1+"").divide(number));
//
//            Date startDateStatus2 = new Date(now.getTime() - thirtyDaysTimes * 3);
//            Date endDateStatus2 = new Date(now.getTime() - thirtyDaysTimes * 2);
//            YhglobalToReceivePrepaidLedger count2 = yhglobalToReceivePrepaidLedgerDao.getDataCountByTime(tablePrefix, projectId, startDateStatus2, endDateStatus2);
//            Long amount2 = count2!=null?count2.getToBeConfirmAmount():0L;
//            amountList.add(new BigDecimal(amount2 + "").divide(number));
//
//            Date startDateStatus3 = new Date(now.getTime() - thirtyDaysTimes * 6);
//            Date endDateStatus3 = new Date(now.getTime() - thirtyDaysTimes * 3);
//            YhglobalToReceivePrepaidLedger count3 = yhglobalToReceivePrepaidLedgerDao.getDataCountByTime(tablePrefix, projectId, startDateStatus3, endDateStatus3);
//            Long amount3 = count3!=null?count3.getToBeConfirmAmount():0L;
//            amountList.add(new BigDecimal(amount3  + "").divide(number));
//
//            Date endDateStatus4 = new Date(now.getTime() - thirtyDaysTimes * 6);
//            YhglobalToReceivePrepaidLedger count4 = yhglobalToReceivePrepaidLedgerDao.getDataCountByTime(tablePrefix, projectId, null, endDateStatus4);
//            Long amount4 = count4!=null?count4.getToBeConfirmAmount():0L;
//            amountList.add(new BigDecimal(amount4 + "").divide(number));

            PrepaidToBeReceiveAmountReport data = prepaidToBeReceiveAmountReportDao.getData(tablePrefix,1L);
            Long amount1 = data.getTimeTypeOne();
            amountList.add(new BigDecimal(amount1+"").divide(number));
            Long amount2 = data.getTimeTypeTwo();
            amountList.add(new BigDecimal(amount2 + "").divide(number));
            Long amount3 = data.getTimeTypeThree();
            amountList.add(new BigDecimal(amount3  + "").divide(number));
            Long amount4 = data.getTimeTypeFour();
            amountList.add(new BigDecimal(amount4 + "").divide(number));
            // 计算各部分数据比率
            Long amountTotal = amount1 + amount2 + amount3 + amount4;
            List<String> rateList = new ArrayList<>();
            if (amountTotal != 0) {
                String rate1 = GetRateUtil.getRate(amount1, amountTotal);
                rateList.add(rate1);
                String rate2 = GetRateUtil.getRate(amount2, amountTotal);
                rateList.add(rate2);
                String rate3 = GetRateUtil.getRate(amount3, amountTotal);
                rateList.add(rate3);
                String rate4 = GetRateUtil.getRate(amount4, amountTotal);
                rateList.add(rate4);
            }
            List<String> dateStrList = new ArrayList<>();
            dateStrList.add("30天-60天");
            dateStrList.add("60天-90天");
            dateStrList.add("90天-180天");
            dateStrList.add("180天以上");

            Map<String, List> map = new LinkedHashMap<>();
            map.put("rateList",rateList);
            map.put("dateStrList",dateStrList);

            List<Map<String, String>> list = new ArrayList<>();
            for (int i = 0; i < dateStrList.size(); i++){
                Map<String, String> map1 = new LinkedHashMap<>();
                map1.put("name", dateStrList.get(i));
                map1.put("value", amountList.get(i).doubleValue()+"");
                list.add(map1);
            }
            map.put("amountList",list);
            return new RpcResult(ErrorCode.SUCCESS.getErrorCode(), map);
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("searchPrepaidConfirm error!", e);
        }
    }

    @Override
    public RpcResult getReportRightOneData(GongxiaoRpc.RpcHeader rpcHeader, Long projectId) {
        try {
            // 需求点：显示以往半年的每个月应收代垫产生多少金额（蓝色柱状），实际回收多少（红色柱状），回收率（曲线，回收金额/应收金额）。
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            List<Date> sixMonthDate = GetPastSixMonthUtil.getSixMonthDate();
            List<BigDecimal> receiveList = new ArrayList<>();
            List<BigDecimal> receiptList = new ArrayList<>();
            List<Double> rateList = new ArrayList<>();
            List<String> monthList = new ArrayList<>();
            //List<List> dataList = new ArrayList<>();
            //  查询这六个月的数据
            List<PrepaidReceivedAmountReport> reports = prepaidReceivedAmountReportDao.selectData(tablePrefix);
            for (PrepaidReceivedAmountReport report: reports) {
                    Long receiveLong = report.getToReceiveAmountTotal(); // 应收额度
                    Long receiptLong = report.getReceiptAmountTotal(); // 实收额度
                    //Double rateDouble = GetRateUtil.getRateDouble(receiptLong, receiveLong); // 回收率 实收/应收
                    receiveList.add(new BigDecimal(receiveLong / FXConstant.HUNDRED_DOUBLE + ""));
                    receiptList.add(new BigDecimal(receiptLong / FXConstant.HUNDRED_DOUBLE + ""));
                    rateList.add(report.getRate());
                monthList.add(report.getMonth()+"月");
            }
            List<Map<String, List>> dataList = new ArrayList<>();
            Map<String, List> map1 = new LinkedHashMap<>();
            map1.put("receiveAmountList",receiveList);
            map1.put("rateList",rateList);
            map1.put("receiptList",receiptList);
            map1.put("monthList",monthList);
            dataList.add(map1);

            return new RpcResult(ErrorCode.SUCCESS.getErrorCode(), dataList);
         }catch (Exception e){
             logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("searchPrepaidConfirm error!", e);
    }
    }

    /**
     * 代垫台账流水查询
     */
    @Override
    public PageInfo<YhglobalPrepaidLedgerWriteoffRecord> searchPrepaidConfirm(GongxiaoRpc.RpcHeader rpcHeader,
                                                                      Long projectId,
                                                                      String flowCode,
                                                                      Integer accountType,
                                                                      Date useDateStart,
                                                                      Date useDateEnd,
                                                                      Date dateStart,
                                                                      Date dateEnd,
                                                                      Integer pageNumber,
                                                                      Integer pageSize) {

        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateStart={}, useDateEnd={}, dateStart={}, dateEnd={}", rpcHeader, projectId, flowCode, accountType, useDateStart, useDateEnd, dateStart, dateEnd);

        PageInfo<YhglobalPrepaidLedgerWriteoffRecord> pageResult = null;
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            PageHelper.startPage(pageNumber, pageSize);
            List<YhglobalPrepaidLedgerWriteoffRecord> recordList = yhglobalPrepaidLedgerWriteoffRecordDao.searchPrepaidConfirm(tablePrefix, projectId, flowCode,
                    accountType, useDateStart, useDateEnd, dateStart, dateEnd);
            for (YhglobalPrepaidLedgerWriteoffRecord record : recordList){
                record.setConfirmAmountDouble(record.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                record.setReceiptAmountDouble(record.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE);
                AccountType accountTypeByCode = AccountType.getAccountTypeByCode(record.getAccountType());
                record.setAccountTypeStr(accountTypeByCode.getMessage());
            }

            logger.info("#traceId={}# [OUT] searchPrepaidConfirm success:list.size={}", rpcHeader.getTraceId(),recordList.size());
            pageResult = new PageInfo<>(recordList);//包装分页对象
           return pageResult;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("searchPrepaidConfirm error!", e);
        }
    }

    /**
     * 条件查询代垫
     * */
    @Override
    public PageInfo<YhglobalToReceivePrepaidLedger> selectByManyConditionsHasPage(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, String orderId,
                                                                      Date dateStart, Date dateEnd,Date dateStartConfirm, Date dateEndConfirm, String receiveStatus,
                                                                     Integer accountType,
                                                                     String flowCode, Integer pageNumber,
                                                                     Integer pageSize){

        logger.info("#traceId={}# [IN][selectByProjectId] params:projectId={}, orderId={}, flowCode={}, accountType={}, dateStart={}, dateEnd={}, dateStartConfirm={}, dateEndConfirm={}, " +
                "receiveStatus={}, pageNumber={}, pageSize={}", rpcHeader, projectId,orderId,flowCode,accountType,dateStart,dateEnd,dateStartConfirm,dateEndConfirm,receiveStatus,pageNumber,pageSize);

        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            PageHelper.startPage(pageNumber, pageSize);
            List<YhglobalToReceivePrepaidLedger> receiveList = yhglobalToReceivePrepaidLedgerDao.selectReceiveAndRecordByProjectId(tablePrefix, projectId, orderId,
                    flowCode, accountType, dateStart, dateEnd, dateStartConfirm, dateEndConfirm, receiveStatus);
            for (YhglobalToReceivePrepaidLedger ledger : receiveList){
                ledger.setReceiveAmountDouble(ledger.getReceiveAmount() / FXConstant.HUNDRED_DOUBLE);
                ledger.setToBeConfirmAmountDouble(ledger.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                ledger.setConfirmAmountDouble(ledger.getConfirmAmount()!=null?ledger.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE:0);
                ledger.setReceiptAmountDouble(ledger.getReceiptAmount()!=null?ledger.getReceiptAmount()/FXConstant.HUNDRED_DOUBLE:0);
            }
            PageInfo<YhglobalToReceivePrepaidLedger> pageInfo = new PageInfo<>(receiveList);

            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByProjectId error!", e);
        }

    }




    /**
     * 实现代垫确认的取消功能
     * */
    @Override
    public RpcResult receiveCancelConfirmBatch(GongxiaoRpc.RpcHeader rpcHeader,Long projectId, String flowCode,
                                               WriteOffReturnObject object,
                                               String flow) {

        // List<String> flowCodeList = Arrays.asList(flowCodes.split(","));
        //logger.info("#traceId={}# [IN][receiveCancelConfirmBatch] params:flowCodeList={}", rpcHeader, flowCodes.toString());
        //List<String> list = Arrays.asList(flowCodes);
        String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
        //for (String flowCode : list) {
            try {
                receiveCancelConfirm(rpcHeader,tablePrefix, flowCode, object, flow);
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
                return new RpcResult(ErrorCode.COUPON_CANCEL_CONFIRM_FAIL.getErrorCode(), ErrorCode.COUPON_CANCEL_CONFIRM_FAIL.getMessage());
            }
        // }

        return new RpcResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());
    }

    /**
     * 对每个台账对象进行取消确认
     * */
     void receiveCancelConfirm(GongxiaoRpc.RpcHeader rpcHeader,String tablePrefix, String flowCode,  WriteOffReturnObject object,
                               String flow) {

         logger.info("#traceId={}# [IN][receiveCancelConfirm] params:flowCode={}", rpcHeader, flowCode);
         try {

             Long projectId = 0l;
             String projectName = "";
             Integer supplierId = 0;
             Long transactionAmount = 0l;
             Integer accountType = 0;
             Date now = new Date();
             //按流水号查确认列表
             List<YhglobalPrepaidLedgerWriteoffRecord> recordList = yhglobalPrepaidLedgerWriteoffRecordDao.selectByFlowCode(tablePrefix, flowCode);
             for (YhglobalPrepaidLedgerWriteoffRecord record : recordList) {
                 //按应收ID查应收记录
                 YhglobalToReceivePrepaidLedger receive = yhglobalToReceivePrepaidLedgerDao.selectById(tablePrefix, record.getReceiveId());
                 if (projectId == 0l) {
                     projectId = receive.getProjectId();
                 }
                 if (projectName == "") {
                     projectName = receive.getProjectName();
                 }
                 if (supplierId == 0) {
                     supplierId = receive.getSupplierId();
                 }
                 if (accountType == 0) {
                     accountType = record.getAccountType();
                 }

                 //合计金额累加
                 transactionAmount += record.getReceiptAmount();
                 //修改应收记录（确认金额、未收金额、状态）
                 receive.setConfirmAmount(receive.getConfirmAmount() - record.getConfirmAmount());
                 receive.setReceiptAmount(receive.getReceiptAmount() - record.getReceiptAmount());
                 receive.setToBeConfirmAmount(receive.getToBeConfirmAmount() + record.getConfirmAmount());

                 Byte targetStatus = updateCouponStatus(receive.getToBeConfirmAmount(), receive.getReceiveAmount());
                 receive.setReceiveStatus(targetStatus);

                 //添加操作日志
                 TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消代垫确认");
                 String appendTraceLog = TraceLogUtil.appendTraceLog(receive.getTracelog(), traceLog);
                 yhglobalToReceivePrepaidLedgerDao.updatePrepaidLedgerForWriteoff(tablePrefix, receive.getReceiveId(), receive.getConfirmAmount(),
                         receive.getReceiptAmount(), receive.getToBeConfirmAmount(), (int) targetStatus, receive.getDataVersion(), appendTraceLog, now);
                 //修改确认记录为已回滚
                 record.setIsRollback(RollbackStatus.ROLLBACK_STATUS_YES.getCode());
                 // yhglobalPrepaidLedgerWriteoffRecordDao.updateById(record);
                 yhglobalPrepaidLedgerWriteoffRecordDao.updateIsRollback(tablePrefix, record.getWriteoffId(),  RollbackStatus.ROLLBACK_STATUS_YES.getCode(),
                         record.getDataVersion(), now);
             }

             // 生成流水号
             //String writeoffCode = DateTimeIdGenerator.nextId(tablePrefix, BizNumberType.YHGLOBAL_PREPAID_WRITEOFF_FLOW);

             // 修改对应的账户额度
             //AccountType accountType1 = AccountType.getAccountTypeByCode(accountType);
             //WriteOffReturnObject object = updateAccount(rpcHeader, projectId, transactionAmount, now , writeoffCode, accountType1);
             Long amountBeforeTransaction = object.getAmountBeforeTrans();//流水发生前的余额
             Long amountAfterTransaction = object.getAmountAfterTrans();//流水发生后的余额

             //生成应收确认对冲流水
             try {
                 addWriteoffFlow(rpcHeader, flow,projectId, projectName,
                         amountBeforeTransaction,
                         transactionAmount,
                         amountAfterTransaction,
                         new Date(),
                         supplierId,
                         "",
                         "",
                         null,
                         null,
                         "");
             } catch (Exception ex) {
                 logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + ex.getMessage(), ex);
             }
         } catch (Exception e) {
             logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
             throw new RuntimeException("receiveCancelConfirm error!", e);
         }
//         return ErrorCode.SUCCESS;

    }



    /**
     * 代垫确认和取消确认都要调用的 设置应收代垫的 代垫状态的方法
     * @param toBeConfirmAmount 代垫的未确认额度
     * @param estimatedAmount 代垫的应收额度
     * @return
     */
     Byte updateCouponStatus(Long toBeConfirmAmount, Long estimatedAmount){ //TODO 拆成具体的业务参数
        Byte targetCouponStatus = 0; //保存目标应收状态

            if (toBeConfirmAmount == 0) { //应收已全部发放
                targetCouponStatus = ConfirmStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode();
            }  else if (toBeConfirmAmount.longValue() == estimatedAmount.longValue()){ //从未有过代垫核销确认
                targetCouponStatus = ConfirmStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode();
            } else { //其他情况为部分确认
                // 当应收代垫为正时,未收 为 非正时 均为全部发放
                // 未收为正且小于 应收 或者 大于应收(即此时确认额度为负数) 则为部分发放
                targetCouponStatus = ConfirmStatus.COUPON_STATUS_PART_OF_ISSUE.getCode();//部分发放
            }


        return targetCouponStatus;

    }




}
