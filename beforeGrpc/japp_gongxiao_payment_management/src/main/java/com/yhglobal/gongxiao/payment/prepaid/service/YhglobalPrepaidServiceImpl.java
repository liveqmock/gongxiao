package com.yhglobal.gongxiao.payment.prepaid.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.payment.constant.AccountType;
import com.yhglobal.gongxiao.payment.constant.ReceiveStatus;
import com.yhglobal.gongxiao.payment.dao.SupplierSellHeightTransferAccountDao;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedPrepaidAccountDao;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightAccount;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedPrepaidAccount;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidInfo;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidInfoDao;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidInfoItem;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidInfoItemDao;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidLedgerWriteoffFlow;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidLedgerWriteoffFlowDao;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidLedgerWriteoffRecord;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidLedgerWriteoffRecordDao;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalToReceivePrepaidCount;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalToReceivePrepaidLedger;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalToReceivePrepaidLedgerDao;
import com.yhglobal.gongxiao.payment.service.SupplierSellHeightTransferAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedPrepaidAccountService;
import com.yhglobal.gongxiao.util.ValidationUtil;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Description: 越海代垫service实现类
 * @author: LUOYI
 * @Date: Created in 17:46 2018/5/2
 */
@Service(timeout = 8000)
public class YhglobalPrepaidServiceImpl implements YhglobalPrepaidService {

    private static Logger logger = LoggerFactory.getLogger(YhglobalPrepaidServiceImpl.class);

    @Autowired
    private YhglobalPrepaidLedgerWriteoffFlowDao yhglobalPrepaidLedgerWriteoffFlowDao;

    @Autowired
    private YhglobalPrepaidLedgerWriteoffRecordDao yhglobalPrepaidLedgerWriteoffRecordDao;

    @Autowired
    private YhglobalToReceivePrepaidLedgerDao yhglobalToReceivePrepaidLedgerDao;

    @Autowired
    private YhglobalReceivedPrepaidAccountDao yhglobalReceivedPrepaidAccountDao;

    @Autowired
    private YhglobalPrepaidInfoDao yhglobalPrepaidInfoDao;

    @Autowired
    private YhglobalPrepaidInfoItemDao yhglobalPrepaidInfoItemDao;

    @Autowired
    private YhglobalReceivedPrepaidAccountService yhglobalReceivedPrepaidAccountService;
    @Autowired
    private SupplierSellHeightTransferAccountDao supplierSellHeightTransferAccountDao;
    @Autowired
    private SupplierSellHeightTransferAccountService supplierSellHeightTransferAccountService;

    @Override
    public int addReceive(RpcHeader rpcHeader, String orderId, Long projectId, String projectName, Integer supplierId, String supplierName, String salesContractNo, Long receiveAmount, String currencyCode) {
        logger.info("#traceId={}# [IN][addReceive] params: orderId={}, projectId={}, projectName={}, supplierId={}, supplierName={},  salesContractNo={},  receiveAmount={},  currencyCode={},",
                rpcHeader, orderId, projectId, projectName, supplierId, supplierName, salesContractNo, receiveAmount, currencyCode);
        int result = 0;
        try {

            // 判断是否已经生成 如果没有生成则插入
            YhglobalToReceivePrepaidLedger yhprepaid = yhglobalToReceivePrepaidLedgerDao.selectByOrderId(orderId);
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
                model.setCreateTime(now);
                model.setLastUpdateTime(now);
                //插入数据
                result = yhglobalToReceivePrepaidLedgerDao.addReceive(model);
            } else {
                logger.info("#traceId={}# [OUT]: the to receive prepaid ledger has been existed the orderId is  ", rpcHeader.traceId, orderId);
                return result;
            }
            if (result == 1) {
                logger.info("#traceId={}# [OUT]: addReceive success ", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT]: addReceive failure ", rpcHeader.traceId);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("addReceive error!", e);
        }
        return result;
    }

    @Override
    public boolean receiveCancelConfirmBatch(RpcHeader rpcHeader, String[] flowCodes) {
        logger.info("#traceId={}# [IN][receiveCancelConfirmBatch] params:flowCodes={}",
                rpcHeader, flowCodes);
        for (String flowCode : flowCodes) {
            try {
                receiveCancelConfirm(rpcHeader, flowCode);
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            }
        }
        return true;
    }

    private void receiveCancelConfirm(RpcHeader rpcHeader, String flowCode) {
        logger.info("#traceId={}# [IN][receiveCancelConfirm] params:flowCode={}", rpcHeader, flowCode);
        try {
            Long projectId = 0l;
            String projectName = "";
            Integer supplierId = 0;
            Long transactionAmount = 0l;
            Integer accountType = 0;
            //按流水号查确认列表
            List<YhglobalPrepaidLedgerWriteoffRecord> recordList = yhglobalPrepaidLedgerWriteoffRecordDao.selectByFlowCode(flowCode);
            for (YhglobalPrepaidLedgerWriteoffRecord record : recordList) {
                //按应收ID查应收记录
                YhglobalToReceivePrepaidLedger receive = yhglobalToReceivePrepaidLedgerDao.selectById(record.getReceiveId());
                if (projectId != null && projectId == 0l) {
                    projectId = receive.getProjectId();
                }
                if (projectName != null && projectName == "") {
                    projectName = receive.getProjectName();
                }
                if ((supplierId != null)&&supplierId == 0) {
                    supplierId = receive.getSupplierId();
                }
                if (accountType!= null && accountType == 0) {
                    accountType = record.getAccountType();
                }
                //合计金额累加
                transactionAmount += record.getConfirmAmount();
                //修改应收记录（确认金额、未收金额、状态）
                receive.setConfirmAmount(receive.getConfirmAmount() - record.getConfirmAmount());
                receive.setReceiptAmount(receive.getReceiptAmount() - record.getReceiptAmount());
                receive.setToBeConfirmAmount(receive.getToBeConfirmAmount() + record.getConfirmAmount());
                if(receive.getReceiveAmount() > 0){
                    if (receive.getToBeConfirmAmount() <= 0 ) {
                        // 全部确认
                        receive.setReceiveStatus(ReceiveStatus.RECEIVE_STATUS_COMPLETE_CONFIRMED.getCode());
                    }else if (receive.getToBeConfirmAmount() < receive.getReceiveAmount() || receive.getToBeConfirmAmount() > receive.getReceiveAmount()){
                        // 部分确认
                        receive.setReceiveStatus(ReceiveStatus.RECEIVE_STATUS_PARTIAL_CONFIRMED.getCode());
                    } else if (receive.getToBeConfirmAmount() - receive.getReceiveAmount() == 0){
                        // 未确认
                        receive.setReceiveStatus(ReceiveStatus.RECEIVE_STATUS_UNCONFIRMED.getCode());
                    }
                }
                if(receive.getReceiveAmount() < 0){
                    if (receive.getToBeConfirmAmount() >= 0 ) {
                        // 全部确认
                        receive.setReceiveStatus(ReceiveStatus.RECEIVE_STATUS_COMPLETE_CONFIRMED.getCode());
                    }else if (receive.getToBeConfirmAmount() < receive.getReceiveAmount() || receive.getToBeConfirmAmount() > receive.getReceiveAmount()){
                        // 部分确认
                        receive.setReceiveStatus(ReceiveStatus.RECEIVE_STATUS_PARTIAL_CONFIRMED.getCode());
                    } else if (receive.getToBeConfirmAmount() - receive.getReceiveAmount() == 0){
                        // 未确认
                        receive.setReceiveStatus(ReceiveStatus.RECEIVE_STATUS_UNCONFIRMED.getCode());
                    }
                }
                //更新应收记录
                yhglobalToReceivePrepaidLedgerDao.updateById(receive);
                //修改确认记录为已回滚
                record.setIsRollback(1);
                yhglobalPrepaidLedgerWriteoffRecordDao.updateById(record);
            }

            //查询帐户余额
            Long accountTotalAmount = 0l;
            if (AccountType.ACCOUNT_PREPAID_RECEIPTED.getCode() == accountType) {
                YhglobalReceivedPrepaidAccount account = yhglobalReceivedPrepaidAccountDao.getAccount(projectId);
                accountTotalAmount = account.getTotalAmount();
            } else if (AccountType.ACCOUNT_SALE_DIFFERENCE.getCode() == accountType) {
                SupplierSellHeightAccount account = supplierSellHeightTransferAccountDao.getAccount(projectId);
                accountTotalAmount = account.getTotalAmount();
            }
            Long amountBeforeTransaction = accountTotalAmount;//流水发生前的余额
            Long amountAfterTransaction = amountBeforeTransaction + transactionAmount;//流水发生后的余额

            //生成应收确认对冲流水
            try {
                //按帐户类型查询帐户余额

                flowCode = addWriteoffFlow(rpcHeader, projectId, projectName,
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
                logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + ex.getMessage(), ex);
            }
            //修改帐户余额
            if (AccountType.ACCOUNT_PREPAID_RECEIPTED.getCode() == accountType) {
                //修改实收帐户余额
                yhglobalReceivedPrepaidAccountService.writeoffYhglobalPrepaidReceivedAccount(rpcHeader, "CNY", 1l, projectId, -transactionAmount, new Date(), null);
            } else if (AccountType.ACCOUNT_SALE_DIFFERENCE.getCode() == accountType) {
                supplierSellHeightTransferAccountService.writeoffSellHeightAmount(rpcHeader, "CNY", projectId, -transactionAmount, new Date());
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("receiveCancelConfirm error!", e);
        }
    }

    @Override
    public RpcResult<T> receiveConfirm(RpcHeader rpcHeader, Long projectId, String projectName, Date useDate, Integer accountType, String philipNo, String confirmInfo) {
        logger.info("#traceId={}# [IN][receiveConfirm] params:projectId={}, useDate={},  accountType={},  confirmInfo={}",
                rpcHeader, projectId, useDate, accountType, confirmInfo);
        try {
            Date now = new Date();
            //查询帐户余额
            Long accountTotalAmount = 0l;
            if (AccountType.ACCOUNT_PREPAID_RECEIPTED.getCode() == accountType) {
                YhglobalReceivedPrepaidAccount account = yhglobalReceivedPrepaidAccountDao.getAccount(projectId);
                accountTotalAmount = account.getTotalAmount();
            } else if (AccountType.ACCOUNT_SALE_DIFFERENCE.getCode() == accountType) {
                SupplierSellHeightAccount account = supplierSellHeightTransferAccountDao.getAccount(projectId);
                accountTotalAmount = account.getTotalAmount();
            }
            List<YhglobalToReceivePrepaidLedger> receiveList = null;
            try {
                receiveList = JSON.parseObject(confirmInfo, new TypeReference<List<YhglobalToReceivePrepaidLedger>>() {
                });
            } catch (JSONException e) {
                return RpcResult.newFailureResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
            }

            long confirmAmountTotal = 0l;
            for (YhglobalToReceivePrepaidLedger receive : receiveList) {
                confirmAmountTotal += Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED_DOUBLE);
            }

            //判断本次确认金额是否超出余额
            if (accountTotalAmount == null || accountTotalAmount < confirmAmountTotal) {
                //已超出
                return RpcResult.newFailureResult(ErrorCode.ACCOUNT_PREPAID_RECEIPT_NOT_SUFFICIENT_FUNDS.getErrorCode(), ErrorCode.ACCOUNT_PREPAID_RECEIPT_NOT_SUFFICIENT_FUNDS.getMessage());
            }
            Long amountBeforeTransaction = accountTotalAmount;//流水发生前的余额
            Long transactionAmount = -confirmAmountTotal;//交易金额
            Long amountAfterTransaction = amountBeforeTransaction + transactionAmount;//流水发生后的余额
            if (AccountType.ACCOUNT_PREPAID_RECEIPTED.getCode() == accountType) {
                //修改实收帐户余额
                yhglobalReceivedPrepaidAccountService.writeoffYhglobalPrepaidReceivedAccount(rpcHeader, "CNY", 1l, projectId, confirmAmountTotal, now, null);
            } else if (AccountType.ACCOUNT_SALE_DIFFERENCE.getCode() == accountType) {
                supplierSellHeightTransferAccountService.writeoffSellHeightAmount(rpcHeader, "CNY", projectId, confirmAmountTotal, now);
            }

            //记录流水
            String flowCode = null;
            try {
                flowCode = addWriteoffFlow(rpcHeader, projectId, projectName,
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
                logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + ex.getMessage(), ex);
            }
            for (YhglobalToReceivePrepaidLedger receive : receiveList) {

                //查询应收记录
                YhglobalToReceivePrepaidLedger receiveInfo = yhglobalToReceivePrepaidLedgerDao.selectById(receive.getReceiveId());
                if (receiveInfo.getDataVersion() != receive.getDataVersion()) {
                    //数据版本不一致
                    return RpcResult.newFailureResult(ErrorCode.DATE_VERSION_DIFFER.getErrorCode(), ErrorCode.DATE_VERSION_DIFFER.getMessage());
                }
//                if (receiveInfo.getToBeConfirmAmount() < receive.getConfirmAmountDouble() * FXConstant.HUNDRED_DOUBLE) {
//                    //超出
//                    return RpcResult.newFailureResult(ErrorCode.ACCOUNT_PREPAID_CONFIRM_AMOUNT_EXCESS.getErrorCode(), ErrorCode.ACCOUNT_PREPAID_CONFIRM_AMOUNT_EXCESS.getMessage());
//                }

                YhglobalPrepaidLedgerWriteoffRecord record = new YhglobalPrepaidLedgerWriteoffRecord();
                record.setReceiveId(receive.getReceiveId());
                record.setConfirmAmount(Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED_DOUBLE));
                record.setReceiptAmount(Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED_DOUBLE));
                record.setProjectId(projectId);
                record.setUseDate(useDate);
                record.setAccountType(accountType);
                record.setPhilipNo(philipNo);
                record.setCurrencyCode("CNY");
                record.setFlowCode(flowCode);
                record.setAccountType(accountType);
                record.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                record.setDataVersion(0L);
                record.setCreatedByName(rpcHeader.getUsername());
                record.setCreateTime(now);
                record.setLastUpdateTime(now);
                //保存核销记录
                yhglobalPrepaidLedgerWriteoffRecordDao.addWriteoffRecord(record);
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
                if(receiveInfo.getReceiveAmount()>0) {
                    if (receiveInfo.getToBeConfirmAmount() <= 0) {
                        receiveInfo.setReceiveStatus(ReceiveStatus.RECEIVE_STATUS_COMPLETE_CONFIRMED.getCode());
                    } else {
                        receiveInfo.setReceiveStatus(ReceiveStatus.RECEIVE_STATUS_PARTIAL_CONFIRMED.getCode());
                    }
                }else if(receiveInfo.getReceiveAmount()<0) {
                    if (receiveInfo.getToBeConfirmAmount() >= 0) {
                        receiveInfo.setReceiveStatus(ReceiveStatus.RECEIVE_STATUS_COMPLETE_CONFIRMED.getCode());
                    } else {
                        receiveInfo.setReceiveStatus(ReceiveStatus.RECEIVE_STATUS_PARTIAL_CONFIRMED.getCode());
                    }
                }
                if (ValidationUtil.isNotEmpty(receiveInfo.getConfirmRecord())) {
                    receiveInfo.setConfirmRecord(receiveInfo.getConfirmRecord() + "," + record.getReceiveId());
                } else {
                    receiveInfo.setConfirmRecord(record.getReceiveId().toString());
                }
                yhglobalToReceivePrepaidLedgerDao.updateById(receiveInfo);
            }

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("receiveConfirm error!", e);
        }
        return RpcResult.newSuccessResult();
    }

    public String addWriteoffFlow(RpcHeader rpcHeader,
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
        String flowCode = DateTimeIdGenerator.nextId(BizNumberType.YHGLOBAL_PREPAID_WRITEOFF_FLOW);
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
        int count = yhglobalPrepaidLedgerWriteoffFlowDao.addFlow(flow);
        if (count == 1) {
            logger.info("#traceId={}# [OUT]: addReceive success flowCode={}", rpcHeader.traceId, flowCode);
            return flowCode;
        }
        return null;
    }

    @Override
    public List<YhglobalToReceivePrepaidLedger> selectByProjectId(RpcHeader rpcHeader, Long projectId) {
        logger.info("#traceId={}# [IN][selectByProjectId] params:projectId={}", rpcHeader, projectId);
        List<YhglobalToReceivePrepaidLedger> receiveList = null;
        try {
            receiveList = yhglobalToReceivePrepaidLedgerDao.selectByProjectId(projectId);
            for (YhglobalToReceivePrepaidLedger receive : receiveList) {
                receive.setToBeConfirmAmountDouble(receive.getToBeConfirmAmount() != null ? receive.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
                receive.setReceiveAmountDouble(receive.getReceiveAmount() != null ? receive.getReceiveAmount() / FXConstant.HUNDRED_DOUBLE : null);
                receive.setConfirmAmountDouble(receive.getConfirmAmount() != null ? receive.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
                receive.setReceiptAmountDouble(receive.getReceiptAmount() != null ? receive.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE : null);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByProjectId error!", e);
        }
        return receiveList;
    }

    @Override
    public List<YhglobalToReceivePrepaidLedger> selectByIds(RpcHeader rpcHeader, String[] ids) {
        logger.info("#traceId={}# [IN][selectByIds] params:ids={}", rpcHeader, ids);
        List<YhglobalToReceivePrepaidLedger> receiveList = new ArrayList<>();
        try {
            //去重
            List<String> idList = new ArrayList<>();
            for (int i = 0; i < ids.length; i++) {
                if (!idList.contains(ids[i])) {
                    idList.add(ids[i]);
                }
            }

            for (String id : idList) {
                YhglobalToReceivePrepaidLedger receive = yhglobalToReceivePrepaidLedgerDao.selectById(Long.parseLong(id));
                if (receive != null) {
                    receive.setToBeConfirmAmountDouble(receive.getToBeConfirmAmount() != null ? receive.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
                    receive.setReceiveAmountDouble(receive.getReceiveAmount() != null ? receive.getReceiveAmount() / FXConstant.HUNDRED_DOUBLE : null);
                    receive.setConfirmAmountDouble(receive.getConfirmAmount() != null ? receive.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
                    receive.setReceiptAmountDouble(receive.getReceiptAmount() != null ? receive.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE : null);
                    receiveList.add(receive);
                }
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByIds error!", e);
        }
        return receiveList;
    }

    @Override
    public List<YhglobalPrepaidLedgerWriteoffRecord> selectWriteoffRecordByReceiveId(RpcHeader rpcHeader, Long receiveId) {
        logger.info("#traceId={}# [IN][selectWriteoffRecordByReceiveId] params:receiveId={}", rpcHeader, receiveId);
        List<YhglobalPrepaidLedgerWriteoffRecord> recordList = null;
        try {
            recordList = yhglobalPrepaidLedgerWriteoffRecordDao.selectByReceiveId(receiveId);
            for (YhglobalPrepaidLedgerWriteoffRecord record : recordList) {
                record.setReceiptAmountDouble(record.getReceiptAmount() == null ? null : record.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE);
                record.setConfirmAmountDouble(record.getConfirmAmount() == null ? null : record.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByIds error!", e);
        }
        return recordList;
    }

    @Override
    public PageInfo<YhglobalToReceivePrepaidLedger> selectReceiveAndRecordByProjectId(RpcHeader rpcHeader, Long projectId,
                                                                                      String orderId,
                                                                                      String flowCode,
                                                                                      Integer accountType,
                                                                                      Date dateStart,
                                                                                      Date dateEnd,
                                                                                      Date dateStartConfirm,
                                                                                      Date dateEndConfirm,
                                                                                      String receiveStatus,
                                                                                      Integer pageNumber,
                                                                                      Integer pageSize) {
        logger.info("#traceId={}# [IN][selectByProjectId] params:projectId={}", rpcHeader, projectId);
        List<YhglobalToReceivePrepaidLedger> receiveList = null;
        PageInfo<YhglobalToReceivePrepaidLedger> pageInfo = null;
        try {
            PageHelper.startPage(pageNumber, pageSize);
            receiveList = yhglobalToReceivePrepaidLedgerDao.selectReceiveAndRecordByProjectId(projectId, orderId, flowCode, accountType, dateStart, dateEnd, dateStartConfirm, dateEndConfirm, receiveStatus);
            for (YhglobalToReceivePrepaidLedger receive : receiveList) {
                receive.setToBeConfirmAmountDouble(receive.getToBeConfirmAmount() != null ? receive.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
                receive.setReceiveAmountDouble(receive.getReceiveAmount() != null ? receive.getReceiveAmount() / FXConstant.HUNDRED_DOUBLE : null);
                receive.setConfirmAmountDouble(receive.getConfirmAmount() != null ? receive.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
                receive.setReceiptAmountDouble(receive.getReceiptAmount() != null ? receive.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE : null);
            }
            pageInfo = new PageInfo<>(receiveList);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByProjectId error!", e);
        }
        return pageInfo;
    }

    @Override
    public YhglobalToReceivePrepaidCount selectReceiveAndRecordCount(RpcHeader rpcHeader, Long projectId, String receiveIds) {
        logger.info("#traceId={}# [IN][selectReceiveAndRecordCount] params:projectId={}", rpcHeader, projectId);
        YhglobalToReceivePrepaidCount result = new YhglobalToReceivePrepaidCount();
        try {
            List<YhglobalToReceivePrepaidCount> countList = yhglobalToReceivePrepaidLedgerDao.selectReceiveAndRecordCount(projectId, receiveIds);
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
            result.setReceiveAmountDouble(result.getReceiveAmount() != null ? result.getReceiveAmount() / FXConstant.HUNDRED_DOUBLE : null);
            result.setReceiptAmountDouble(result.getReceiptAmount() != null ? result.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE : null);
            result.setToBeConfirmAmountDouble(result.getToBeConfirmAmount() != null ? result.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
            result.setConfirmAmountDouble(result.getConfirmAmount() != null ? result.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByProjectId error!", e);
        }
        return result;
    }

    @Override
    public int addPrepaidInfo(RpcHeader rpcHeader, Long projectId, String projectName, Integer supplierId, String supplierName, String payer,
                              String receivables, String settlementNo, Integer settlementType,Date dateBusiness, String taxNo,
                              String contactInfo, Integer provinceId, String provinceName, Integer cityId,
                              String cityName, Integer districtId, String districtName, String streetAddress,
                              String accountCNY, String bankNameCNY, String remark, String itemJson) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, projectName={}, supplierId={}, supplierName={}, payer={}, receivables={}, " +
                        "settlementNo={}, dateBusiness={}, taxNo={}, contactInfo={}, provinceId={}, provinceName={}, cityId={}, " +
                        "cityName={}, districtId={}, districtName={}, streetAddress={}, accountCNY={}, bankNameCNY={},  bankNameCNY={}, itemJson={}",
                rpcHeader, projectId, projectName, supplierId, supplierName, payer, receivables, settlementNo, dateBusiness, taxNo, contactInfo, provinceId, provinceName, cityId,
                cityName, districtId, districtName, streetAddress, accountCNY, bankNameCNY, itemJson);
        int result = 0;
        Date now = new Date();
        try {
            //明细json转list
            List<YhglobalPrepaidInfoItem> itemList = JSON.parseObject(itemJson, new TypeReference<List<YhglobalPrepaidInfoItem>>() {
            });
            //封装代垫付款单信息
            YhglobalPrepaidInfo info = new YhglobalPrepaidInfo();
            info.setPrepaidNo(DateTimeIdGenerator.nextId(BizNumberType.YHGLOBAL_PREPAID_INFO));
            info.setProjectId(projectId);
            info.setProjectName(projectName);
            info.setSupplierId(supplierId);
            info.setSupplierName(supplierName);
            info.setPayer(payer);
            info.setReceivables(receivables);
            info.setSettlementNo(settlementNo);
            info.setSettlementType(settlementType);
            info.setDateBusiness(dateBusiness);
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
            //保存代垫付款单
            result = yhglobalPrepaidInfoDao.insert(info);
            //保存代垫付款单明细
            for (YhglobalPrepaidInfoItem item : itemList) {
                item.setInfoId(info.getPrepaidId());
                item.setApplicationAmount(Math.round(item.getApplicationAmountDouble() * FXConstant.HUNDRED_DOUBLE));
                item.setExchangeRate(Math.round(item.getExchangeRateDouble() * FXConstant.HUNDRED_DOUBLE));
                item.setTaxPoint(Math.round(item.getTaxPointDouble() * FXConstant.MILLION_DOUBLE));
                item.setStandardCurrencyAmount(Math.round(item.getStandardCurrencyAmountDouble() * FXConstant.HUNDRED_DOUBLE));
                item.setTaxSubsidy(Math.round(item.getTaxSubsidyDouble() * FXConstant.HUNDRED_DOUBLE));
                item.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                item.setDataVersion(0L);
                item.setCreatedByName(rpcHeader.getUsername());
                item.setCreateTime(now);
                item.setLastUpdateTime(now);
                yhglobalPrepaidInfoItemDao.insert(item);
            }
            //获取中国货币编码
            Currency currency = Currency.getInstance(Locale.CHINA);
            String currencyCode = currency.getCurrencyCode();
            //记录应收
            this.addReceive(rpcHeader, null, projectId, projectName, supplierId, supplierName, null, Math.round(standardCurrencyAmount * FXConstant.HUNDRED_DOUBLE), currencyCode);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("addPrepaidInfo error!", e);
        }
        return result;
    }

    @Override
    public PageInfo<YhglobalPrepaidInfo> getsPrepaidInfoList(RpcHeader rpcHeader, Long projectId, String prepaidNo, String receivables, Date dateStart, Date dateEnd, Integer page, Integer pageSize) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:page={}, pageSize={}", rpcHeader, page, pageSize);
        List<YhglobalPrepaidInfo> infoList = null;
        PageInfo<YhglobalPrepaidInfo> pageInfo = null;
        try {
            PageHelper.startPage(page, pageSize);
            infoList = yhglobalPrepaidInfoDao.selectAll(projectId, prepaidNo, receivables, dateStart, dateEnd);
            for (YhglobalPrepaidInfo info : infoList) {
                info.setStandardCurrencyAmountDouble(info.getStandardCurrencyAmount() / FXConstant.HUNDRED_DOUBLE);
            }
            pageInfo = new PageInfo<YhglobalPrepaidInfo>(infoList);
            logger.info("#traceId={}# [OUT]: getPrepaidInfoById success pageInfo={}", rpcHeader.traceId, pageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("getPrepaidInfoById error!", e);
        }
        return pageInfo;
    }

    @Override
    public YhglobalPrepaidInfo getPrepaidInfoById(RpcHeader rpcHeader, Integer id) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:id={}", rpcHeader, id);
        YhglobalPrepaidInfo yhglobalPrepaidInfo = null;
        try {
            yhglobalPrepaidInfo = yhglobalPrepaidInfoDao.selectByPrimaryKey(id);
            if (yhglobalPrepaidInfo != null) {
                List<YhglobalPrepaidInfoItem> itemList = yhglobalPrepaidInfoItemDao.selectByInfoId(yhglobalPrepaidInfo.getPrepaidId());
                yhglobalPrepaidInfo.setItemList(itemList);
            }
            logger.info("#traceId={}# [OUT]: getPrepaidInfoById success yhglobalPrepaidInfo={}", rpcHeader.traceId, yhglobalPrepaidInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("getPrepaidInfoById error!", e);
        }
        return yhglobalPrepaidInfo;
    }

    @Override
    public PageInfo<YhglobalPrepaidLedgerWriteoffRecord> searchPrepaidConfirm(RpcHeader rpcHeader,
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
        PageInfo<YhglobalPrepaidLedgerWriteoffRecord> resultPage = null;
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<YhglobalPrepaidLedgerWriteoffRecord> recordList = yhglobalPrepaidLedgerWriteoffRecordDao.searchPrepaidConfirm(projectId, flowCode, accountType, useDateStart, useDateEnd, dateStart, dateEnd);
            for (YhglobalPrepaidLedgerWriteoffRecord record : recordList) {
                record.setConfirmAmountDouble(record.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                record.setReceiptAmountDouble(record.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE);
            }
            resultPage = new PageInfo<>(recordList);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("searchPrepaidConfirm error!", e);
        }
        return resultPage;
    }
}
