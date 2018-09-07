package com.yhglobal.gongxiao.payment.flow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;

import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.constant.*;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.model.*;

import com.yhglobal.gongxiao.payment.constant.AccountType;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponTransferRecordDao;
import com.yhglobal.gongxiao.payment.dao.SupplierSellHeightTransferAccountDao;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedCouponAccountDao;
import com.yhglobal.gongxiao.payment.flow.YhglobalCouponWriteoffFlow;
import com.yhglobal.gongxiao.payment.flow.dao.YhglobalCouponWriteoffFlowDao;
import com.yhglobal.gongxiao.payment.flow.dao.YhglobalCouponWriteoffRecordDao;
import com.yhglobal.gongxiao.payment.flow.service.YhglobalCouponWriteroffService;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightAccount;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponAccount;
import com.yhglobal.gongxiao.payment.service.SupplierSellHeightTransferAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponAccountService;
import com.yhglobal.gongxiao.purchase.dao.YhglobalToReceiveCouponLedgerDao;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;

/**
 * 越海返利流水核销Service实现类
 *
 * @author : 王帅
 */
@Service(timeout = 8000)
public class YhglobalCouponWriteroffServiceImpl implements YhglobalCouponWriteroffService {

    private static Logger logger = LoggerFactory.getLogger(YhglobalCouponWriteroffServiceImpl.class);


    @Autowired
    YhglobalToReceiveCouponLedgerDao yhglobalToReceiveCouponLedgerDao;

    @Autowired
    SupplierCouponTransferRecordDao supplierCouponTransferRecordDao;

    @Autowired
    YhglobalCouponWriteoffFlowDao yhglobalCouponWriteoffFlowDao;
    @Autowired
    YhglobalCouponWriteoffRecordDao yhglobalCouponWriteoffRecordDao;

    @Autowired
    YhglobalReceivedCouponAccountDao yhglobalReceivedCouponAccountDao;

    @Autowired
    SupplierSellHeightTransferAccountDao supplierSellHeightTransferAccountDao;

    @Autowired
    YhglobalReceivedCouponAccountService yhglobalReceivedCouponAccountService;

    @Autowired
    SupplierSellHeightTransferAccountService supplierSellHeightTransferAccountService;

    /**
     * 返利确认
     * @param rpcHeader
     * @param projectId 关联的项目的ID
     */
    @Override
    public RpcResult yhCouponWriteroff(RpcHeader rpcHeader, Long projectId, String projectName, Date useDate, Integer accountType,
                                       String philipDocumentNo,String confirmInfoJson) throws Exception {
        logger.info("#traceId={}# [IN][receiveConfirm] params:projectId={}, useDate={},  accountType={},  confirmInfo={}",
                rpcHeader, projectId, useDate, accountType, confirmInfoJson);
        try {
            Date now = new Date();
            //查询帐户余额 根据账户类型选择不同的账户
            Long accountTotalAmount = 0l;
            if (CouponConfrimAccountType.COUPON_RECEIVED_ACCOUNT.getCode() == accountType) {
                accountTotalAmount = yhglobalReceivedCouponAccountDao.getAccount(projectId).getTotalAmount();
            } else if (CouponConfrimAccountType.SALES_DIFFERENCE_ACCOUNT.getCode() == accountType) {
                accountTotalAmount = supplierSellHeightTransferAccountDao.getAccount(projectId).getTotalAmount();
            }
            // 解析前端封装的json格式数据
            List<YhglobalCouponLedger> receiveList
                    = JSON.parseObject(confirmInfoJson, new TypeReference<List<YhglobalCouponLedger>>() {
            });
            long confirmAmountTotal = 0l;
            // 统计核销确认的总额度
            for (YhglobalCouponLedger receive : receiveList) {
                confirmAmountTotal += Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED);
            }

            //判断本次确认金额是否超出
            if (accountTotalAmount == null || accountTotalAmount < confirmAmountTotal) {
                //已超出
                logger.info("the receivedcouponamount is not enough");
                RpcResult rpcResult = RpcResult.newFailureResult(ErrorCode.SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(),
                        ErrorCode.SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
                return rpcResult ;
            }
            Long amountBeforeTransaction = accountTotalAmount;//流水发生前的余额
            Long transactionAmount = -confirmAmountTotal;//交易金额
            Long amountAfterTransaction = amountBeforeTransaction + transactionAmount;//流水发生后的余额
            if (CouponConfrimAccountType.COUPON_RECEIVED_ACCOUNT.getCode() == accountType) {
                //修改实收帐户余额
                yhglobalReceivedCouponAccountService.writeoffYhglobalCouponReceivedAccount(rpcHeader, "CNY", 1l, projectId, -transactionAmount, now,null);
            } else if (CouponConfrimAccountType.SALES_DIFFERENCE_ACCOUNT.getCode() == accountType) {
                // 修改销售差价账户
                supplierSellHeightTransferAccountService.writeoffSellHeightAmount(rpcHeader, "CNY", projectId,  -transactionAmount, now);
            }

            //记录流水  供应商及经销商信息为预留字段 目前不用存储
            String flowCode = null;
            try {
                flowCode = addWriteoffFlow(rpcHeader, accountType, projectId, projectName,
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
            for (YhglobalCouponLedger receive : receiveList) {

                //查询应收记录
                // YhglobalToReceivePrepaidLedger receiveInfo = yhglobalToReceivePrepaidLedgerDao.selectById(receive.getReceiveId());
                YhglobalCouponLedger receiveInfo = yhglobalToReceiveCouponLedgerDao.selectByPrimaryKey(receive.getPurchaseCouponLedgerId());

                YhglobalCouponWriteoffRecord record = new YhglobalCouponWriteoffRecord();
                record.setToReceiveCouponId(receive.getPurchaseCouponLedgerId());
                record.setConfirmAmount(Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED));
                record.setReceiptAmount(Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED));
                record.setConfirmCurrencyCode("CNY");
                record.setReceivedCurrencyCode("CNY");
                record.setFlowNo(flowCode);
                record.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                record.setDataVersion(0l);
                record.setCreatedByName(rpcHeader.getUsername());
                record.setCreateTime(now);
                record.setAccountType(accountType);
                record.setProjectId(projectId);
                record.setUseDate(useDate);
                // 设置飞利浦单据号
                record.setPhilipDocumentNo(philipDocumentNo);
                if (flowCode == null){
                    throw new RuntimeException("insert coupon writer off flow fail");
                }
                //保存核销记录
                // yhglobalPrepaidLedgerWriteoffRecordDao.addWriteoffRecord(record);
                yhglobalCouponWriteoffRecordDao.insert(record);
                //修改应收记录
                Long confirmAmount = 0l;
                if (receiveInfo.getConfirmedCouponAmount() == null) {
                    confirmAmount = Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED);
                } else {
                    confirmAmount = receiveInfo.getConfirmedCouponAmount() + Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED);
                }
                receiveInfo.setConfirmedCouponAmount(confirmAmount);//确认金额
                Long receiptAmount = 0l;
                if (receiveInfo.getReceivedCouponAmount() == null) {
                    receiptAmount = Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED);
                } else {
                    receiptAmount = receiveInfo.getReceivedCouponAmount() + Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED);
                }
                receiveInfo.setReceivedCouponAmount(receiptAmount);//实收金额

                receiveInfo.setToBeConfirmAmount(receiveInfo.getEstimatedCouponAmount() - confirmAmount);//待确认金额
                if (receiveInfo.getEstimatedCouponAmount() > 0) {
                    if (receiveInfo.getToBeConfirmAmount() <= 0) {
                        receiveInfo.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode());//全部发放
                    } else if (receiveInfo.getToBeConfirmAmount() < receiveInfo.getEstimatedCouponAmount()||receiveInfo.getToBeConfirmAmount() > receiveInfo.getEstimatedCouponAmount()) {
                        // 当应收返利为正时,未收 为 非正时 均为全部发放
                        // 未收为正且小于 应收 或者 大于应收(即此时确认额度为负数) 则为部分发放
                        receiveInfo.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_PART_OF_ISSUE.getCode());//部分发放
                    }else if (receiveInfo.getToBeConfirmAmount() - receiveInfo.getEstimatedCouponAmount()==0){
                        // 此时为未发放
                        receiveInfo.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode());
                    }
                }else {
                    // 应收返利为负时
                    if (receiveInfo.getToBeConfirmAmount() >= 0) {
                        receiveInfo.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode());//全部发放
                    } else if (receiveInfo.getToBeConfirmAmount() < receiveInfo.getEstimatedCouponAmount()||receiveInfo.getToBeConfirmAmount() > receiveInfo.getEstimatedCouponAmount()) {
                        // 未收 为 非负时 均为全部发放
                        // 未收为负且小于 应收 或者 大于应收(即此时确认额度为负数) 则为部分发放
                        receiveInfo.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_PART_OF_ISSUE.getCode());//部分发放
                    }else if (receiveInfo.getToBeConfirmAmount() - receiveInfo.getEstimatedCouponAmount()==0){
                        // 此时为未发放
                        receiveInfo.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode());
                    }

                }

                // 设置确认记录
                if (ValidationUtil.isNotEmpty(receiveInfo.getConfirmRecord())) {
                    receiveInfo.setConfirmRecord(receiveInfo.getConfirmRecord() + "," + record.getToReceiveCouponId());
                } else {
                    receiveInfo.setConfirmRecord(receive.getConfirmRecord());
                }
                receiveInfo.setDataVersion(receiveInfo.getDataVersion()+1);
                // receiveInfo.setTracelog(receiveInfo.getTracelog()+";"+rpcHeader.username+"进行了返利确认");
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "返利确认");
                String appendTraceLog = TraceLogUtil.appendTraceLog(receiveInfo.getTracelog(), traceLog);
                receiveInfo.setTracelog(appendTraceLog);
                yhglobalToReceiveCouponLedgerDao.update(receiveInfo);
            }

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("receiveConfirm error!", e);
        }
        return new RpcResult();
    }

    /**
     * 生成流水
     */
    public String addWriteoffFlow(RpcHeader rpcHeader,
                                  Integer accountType,
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
        YhglobalCouponWriteoffFlow flow = new YhglobalCouponWriteoffFlow();
        String flowCode = DateTimeIdGenerator.nextId(BizNumberType.YHGLOBAL_COUPON_WRITEROFF_FLOW);
        try {
            flow.setFlowNo(flowCode);
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
            flow.setCurrencyCode("CNY");
            if (CouponConfrimAccountType.COUPON_RECEIVED_ACCOUNT.getCode().equals(accountType)) {
                flow.setTransferPattern(CouponConfrimAccountType.COUPON_RECEIVED_ACCOUNT.getAccountName());
            } else if (CouponConfrimAccountType.SALES_DIFFERENCE_ACCOUNT.getCode().equals(accountType)) {
                flow.setTransferPattern(CouponConfrimAccountType.SALES_DIFFERENCE_ACCOUNT.getAccountName());
            }

            int count = yhglobalCouponWriteoffFlowDao.insert(flow);
            if (count != 1) {
                logger.info("#traceId={}# [OUT]: insert coupon writeroff flow fail flowCode={}", rpcHeader.traceId, flowCode);
                return null;
            }
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("add coupon writeroff flow error!", e);
        }
        return flowCode;
    }

    /**
     * 获取项目对应的返利
     */
    @Override
    public PageInfo<PurchaseCouponFlow> selectAllByProjectId(RpcHeader rpcHeader, long projectId, int pageNumber,
                                                             int pageSize) {
        PageInfo<PurchaseCouponFlow> pageInfo = null;
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<YhglobalCouponLedger> list = yhglobalToReceiveCouponLedgerDao.selectAllByProjectId(projectId);
            String flowNo = null;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectAllByProjectId error!", e);
        }
        return pageInfo;
    }


    /**
     * 获取返利详情
     */
    @Override
    public List<YhglobalCouponLedger> selectYhglobalCouponLedgerByPurchaseCouponLedgerId(List<Long> list) {

        List<YhglobalCouponLedger> yhglobalCouponLedgerList = new ArrayList<>();
        // 根据选中的采购返利ID 查询出对应的返利详情
        // 要去重
        // YhglobalCouponLedger yhglobalCouponLedger = yhglobalToReceiveCouponLedgerDao.selectByPrimaryKey(id);
        StringBuffer ids = new StringBuffer();
        // ids.append("(");
        for (int i = 0;i<list.size();i++) {
            Long id =list.get(i);
            String idStr = id + "";
            ids.append(idStr).append(",");
        }
        // ids.append(")");
        List<YhglobalCouponLedger> yhglobalCouponLedgerList1 = yhglobalToReceiveCouponLedgerDao.searchMany(ids.toString().substring(0,ids.lastIndexOf(",")));
        for (YhglobalCouponLedger yhglobalCouponLedger:yhglobalCouponLedgerList1) {
            yhglobalCouponLedger.setConfirmAmountDouble(yhglobalCouponLedger.getToBeConfirmAmount() != null ? yhglobalCouponLedger.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
            yhglobalCouponLedger.setReceiptAmountDouble(yhglobalCouponLedger.getToBeConfirmAmount() != null ? yhglobalCouponLedger.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
            yhglobalCouponLedger.setEstimatedAmountDouble(yhglobalCouponLedger.getEstimatedCouponAmount() != null ? yhglobalCouponLedger.getEstimatedCouponAmount() / FXConstant.HUNDRED_DOUBLE : null);
            yhglobalCouponLedger.setToBeConfirmAmountDouble(yhglobalCouponLedger.getToBeConfirmAmount() != null ? yhglobalCouponLedger.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
            byte status = yhglobalCouponLedger.getCouponStatus();
            if (status == 1) {
                yhglobalCouponLedger.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getMeaning());
            }else if (status == 2) {
                yhglobalCouponLedger.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_PART_OF_ISSUE.getMeaning());
            }else if (status == 3) {
                yhglobalCouponLedger.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getMeaning());
            }else if (status == 4) {
                yhglobalCouponLedger.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_USED.getMeaning());
            }else if (status == 9) {
                yhglobalCouponLedger.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_OVERDUE.getMeaning());
            }

            byte type = yhglobalCouponLedger.getCouponType();
            if (type == 1) {
                yhglobalCouponLedger.setCouponTypeString(CouponLedgerCouponStatus.COUPON_TYPE_MONTHLY.getMeaning());
            }else if (type == 2) {
                yhglobalCouponLedger.setCouponTypeString(CouponLedgerCouponStatus.COUPON_TYPE_QUARTERLY.getMeaning());
            }else if (type == 3) {
                yhglobalCouponLedger.setCouponTypeString(CouponLedgerCouponStatus.COUPON_TYPE_ANNUAL.getMeaning());
            }else if (type == 7) {
                yhglobalCouponLedger.setCouponTypeString(CouponLedgerCouponStatus.COUPON_TYPE_CASH_REBATE_AFTER.getMeaning());
            }

            yhglobalCouponLedgerList.add(yhglobalCouponLedger);
        }
        System.out.println("success");
        //pageInfo.setTotal((long) list.size());
        return yhglobalCouponLedgerList;
    }



    /**
     * 返利台账流水查询
     */
    @Override
    public PageInfo<YhglobalCouponWriteoffRecord> searchCouponConfirm(RpcHeader rpcHeader,
                                                                      Long projectId,
                                                                      String flowNo,
                                                                      Integer accountType,
                                                                      Date useDateStart,
                                                                      Date useDateEnd,
                                                                      Date dateStart,
                                                                      Date dateEnd,
                                                                      Integer pageNumber,
                                                                      Integer pageSize) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateStart={}, useDateEnd={}, dateStart={}, dateEnd={}", rpcHeader, projectId, flowNo, accountType, useDateStart, useDateEnd, dateStart, dateEnd);
        PageInfo<YhglobalCouponWriteoffRecord> resultPage;
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<YhglobalCouponWriteoffRecord> recordList = yhglobalCouponWriteoffRecordDao.searchCouponConfirm(projectId, flowNo, accountType, useDateStart, useDateEnd, dateStart, dateEnd);
            for (YhglobalCouponWriteoffRecord record : recordList) {
                record.setConfirmAmountDouble(record.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                record.setReceiptAmountDouble(record.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE);
                record.setDifferenceAmountDouble(record.getConfirmAmountDouble() - record.getReceiptAmountDouble());
                int type = record.getAccountType();
                if (type == 1){
                    record.setAccountTypeName(CouponConfrimAccountType.COUPON_RECEIVED_ACCOUNT.getAccountName());
                }else if (type == 2){
                    record.setAccountTypeName(CouponConfrimAccountType.SALES_DIFFERENCE_ACCOUNT.getAccountName());
                }
            }
            resultPage = new PageInfo<>(recordList);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("search coupon Confirm error!", e);
        }
        return resultPage;
    }

    /**
     * 条件查询返利
     * */
    @Override
    public PageInfo<YhglobalCouponLedgerItem> selectByManyCondiitons(RpcHeader rpcHeader, Long projectId, String purchaseOrderNo,
                                                               String supplierOrderNo, Date dateS, Date dateE, String couponStatus,
                                                               String flowNo,Integer pageNumber,
                                                               Integer pageSize) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateStart={}," +
                " useDateEnd={}, dateStart={}, dateEnd={}", rpcHeader, projectId, purchaseOrderNo, supplierOrderNo, dateS, dateE,
                couponStatus, flowNo, pageNumber, pageSize);
        PageInfo<YhglobalCouponLedgerItem> resultPage;
        try {
            PageHelper.startPage(pageNumber, pageSize);
            // 条件查询
            List<YhglobalCouponLedgerItem> yhglobalCouponLedgerItemList = yhglobalToReceiveCouponLedgerDao.searchByManyCondition(projectId, purchaseOrderNo, supplierOrderNo
                    , flowNo, dateS, dateE, couponStatus);
            // 封装部分前端显示的数据
            for (YhglobalCouponLedgerItem yhglobalCouponLedgerItem:yhglobalCouponLedgerItemList) {
                byte status = yhglobalCouponLedgerItem.getCouponStatus();
                if (status == 1) {
                    yhglobalCouponLedgerItem.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getMeaning());
                }
                if (status == 2) {
                    yhglobalCouponLedgerItem.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_PART_OF_ISSUE.getMeaning());
                }
                if (status == 3) {
                    yhglobalCouponLedgerItem.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getMeaning());
                }
                if (status == 4) {
                    yhglobalCouponLedgerItem.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_USED.getMeaning());
                }
                if (status == 9) {
                    yhglobalCouponLedgerItem.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_OVERDUE.getMeaning());
                }
                byte type = yhglobalCouponLedgerItem.getCouponType();
                if (type == 1) {
                    yhglobalCouponLedgerItem.setCouponTypeString(CouponLedgerCouponStatus.COUPON_TYPE_MONTHLY.getMeaning());
                }
                if (type == 2) {
                    yhglobalCouponLedgerItem.setCouponTypeString(CouponLedgerCouponStatus.COUPON_TYPE_QUARTERLY.getMeaning());
                }
                if (type == 3) {
                    yhglobalCouponLedgerItem.setCouponTypeString(CouponLedgerCouponStatus.COUPON_TYPE_ANNUAL.getMeaning());
                }
                if (type == 7) {
                    yhglobalCouponLedgerItem.setCouponTypeString(CouponLedgerCouponStatus.COUPON_TYPE_CASH_REBATE_AFTER.getMeaning());
                }
                Integer account = yhglobalCouponLedgerItem.getAccountType();
                if (account !=null) {
                    if (account == 1) {
                        yhglobalCouponLedgerItem.setAccountTypeString(CouponConfrimAccountType.COUPON_RECEIVED_ACCOUNT.getAccountName());
                    }
                    if (account == 2) {
                        yhglobalCouponLedgerItem.setAccountTypeString(CouponConfrimAccountType.SALES_DIFFERENCE_ACCOUNT.getAccountName());
                    }
                }
                yhglobalCouponLedgerItem.setEstimatedCouponAmountDouble(yhglobalCouponLedgerItem.getEstimatedCouponAmount()==null?0:yhglobalCouponLedgerItem.getEstimatedCouponAmount() / FXConstant.HUNDRED_DOUBLE);
                yhglobalCouponLedgerItem.setToBeConfirmAmountDouble(yhglobalCouponLedgerItem.getToBeConfirmAmount()==null?0:yhglobalCouponLedgerItem.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                if (yhglobalCouponLedgerItem.getConfirmAmount()!=null) {
                    yhglobalCouponLedgerItem.setConfirmAmountDouble(yhglobalCouponLedgerItem.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                }
                if (yhglobalCouponLedgerItem.getReceiptAmount()!=null) {
                    yhglobalCouponLedgerItem.setReceiptAmountDouble(yhglobalCouponLedgerItem.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE);
                }
            }

            resultPage = new PageInfo<>(yhglobalCouponLedgerItemList);

//            List<Long> integerList = new ArrayList<>();
//            for (YhglobalCouponLedgerItem yhglobalCouponLedgerItem:yhglobalCouponLedgerItemList){
//                if (!integerList.contains(yhglobalCouponLedgerItem.getPurchaseCouponLedgerId())){
//                    integerList.add(yhglobalCouponLedgerItem.getPurchaseCouponLedgerId());
//                }
//            }
//            resultPage.setTotal(integerList.size());

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByManyCondiitons error!", e);
        }
        return resultPage;
    }


    /**
     * 条件查询返利 导出数据
     * */
    @Override
    public List<YhglobalCouponLedgerItem> selectByManyCondiitons(RpcHeader rpcHeader, Long projectId, String purchaseOrderNo,
                                                                     String supplierOrderNo, Date dateS, Date dateE, String couponStatus,
                                                                     String flowNo) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateStart={}," +
                        " useDateEnd={}, dateStart={}, dateEnd={}", rpcHeader, projectId, purchaseOrderNo, supplierOrderNo, dateS, dateE,
                couponStatus, flowNo);
        List<YhglobalCouponLedgerItem> yhglobalCouponLedgerItemList;
        try {
            // 条件查询
            yhglobalCouponLedgerItemList = yhglobalToReceiveCouponLedgerDao.searchByManyCondition(projectId, purchaseOrderNo, supplierOrderNo
                    , flowNo, dateS, dateE, couponStatus);
            // 封装部分前端显示的数据
            for (YhglobalCouponLedgerItem yhglobalCouponLedgerItem:yhglobalCouponLedgerItemList) {
                byte status = yhglobalCouponLedgerItem.getCouponStatus();
                if (status == 1) {
                    yhglobalCouponLedgerItem.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getMeaning());
                }
                if (status == 2) {
                    yhglobalCouponLedgerItem.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_PART_OF_ISSUE.getMeaning());
                }
                if (status == 3) {
                    yhglobalCouponLedgerItem.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getMeaning());
                }
                if (status == 4) {
                    yhglobalCouponLedgerItem.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_USED.getMeaning());
                }
                if (status == 9) {
                    yhglobalCouponLedgerItem.setCouponStatusString(CouponLedgerCouponStatus.COUPON_STATUS_OVERDUE.getMeaning());
                }
                byte type = yhglobalCouponLedgerItem.getCouponType();
                if (type == 1) {
                    yhglobalCouponLedgerItem.setCouponTypeString(CouponLedgerCouponStatus.COUPON_TYPE_MONTHLY.getMeaning());
                }
                if (type == 2) {
                    yhglobalCouponLedgerItem.setCouponTypeString(CouponLedgerCouponStatus.COUPON_TYPE_QUARTERLY.getMeaning());
                }
                if (type == 3) {
                    yhglobalCouponLedgerItem.setCouponTypeString(CouponLedgerCouponStatus.COUPON_TYPE_ANNUAL.getMeaning());
                }
                if (type == 7) {
                    yhglobalCouponLedgerItem.setCouponTypeString(CouponLedgerCouponStatus.COUPON_TYPE_CASH_REBATE_AFTER.getMeaning());
                }
                Integer account = yhglobalCouponLedgerItem.getAccountType();
                if (account !=null) {
                    if (account == 1) {
                        yhglobalCouponLedgerItem.setAccountTypeString(CouponConfrimAccountType.COUPON_RECEIVED_ACCOUNT.getAccountName());
                    }
                    if (account == 2) {
                        yhglobalCouponLedgerItem.setAccountTypeString(CouponConfrimAccountType.SALES_DIFFERENCE_ACCOUNT.getAccountName());
                    }
                }
                yhglobalCouponLedgerItem.setEstimatedCouponAmountDouble(yhglobalCouponLedgerItem.getEstimatedCouponAmount()==null?0:yhglobalCouponLedgerItem.getEstimatedCouponAmount() / FXConstant.HUNDRED_DOUBLE);
                yhglobalCouponLedgerItem.setToBeConfirmAmountDouble(yhglobalCouponLedgerItem.getToBeConfirmAmount()==null?0:yhglobalCouponLedgerItem.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                if (yhglobalCouponLedgerItem.getConfirmAmount()!=null) {
                    yhglobalCouponLedgerItem.setConfirmAmountDouble(yhglobalCouponLedgerItem.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                }
                if (yhglobalCouponLedgerItem.getReceiptAmount()!=null) {
                    yhglobalCouponLedgerItem.setReceiptAmountDouble(yhglobalCouponLedgerItem.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE);
                }

                yhglobalCouponLedgerItem.setEstimatedCouponAmountStr(yhglobalCouponLedgerItem.getEstimatedCouponAmountStr());
                yhglobalCouponLedgerItem.setToBeConfirmAmountStr(yhglobalCouponLedgerItem.getToBeConfirmAmountStr());
                yhglobalCouponLedgerItem.setReceiptAmountStr(yhglobalCouponLedgerItem.getReceiptAmountStr());
                yhglobalCouponLedgerItem.setConfirmAmountStr(yhglobalCouponLedgerItem.getConfirmAmountStr());
            }



        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByManyCondiitons error!", e);
        }
        return yhglobalCouponLedgerItemList;
    }
    /**
     * 实现返利确认的取消功能
     * */
    @Override
    public boolean receiveCancelConfirmBatch(RpcHeader rpcHeader, String[] flowCodes) {
        logger.info("#traceId={}# [IN][receiveCancelConfirmBatch] params:flowCodes={}",
                rpcHeader, flowCodes);
        boolean flag = true;
        for (String flowCode : flowCodes) {
            try {
                flag = receiveCancelConfirm(rpcHeader, flowCode);
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
                return false;
            }
        }
        return flag;
    }

    /**
     * 对每个台账对象进行取消确认
     * */
    private boolean receiveCancelConfirm(RpcHeader rpcHeader, String flowCode) {
        logger.info("#traceId={}# [IN][receiveCancelConfirm] params:flowCode={}", rpcHeader, flowCode);
        try {
            Long projectId = 0l;
            String projectName = "";
            Long supplierId = 0l;
            Long transactionAmount = 0l;
            Integer accountType = 0;
            // 按流水号查确认列表
            List<YhglobalCouponWriteoffRecord> yhglobalCouponWriteoffRecordList = yhglobalCouponWriteoffRecordDao.selectByFlowCode(flowCode);
            for (YhglobalCouponWriteoffRecord record : yhglobalCouponWriteoffRecordList) {
                // 按应收ID查应收记录
                YhglobalCouponLedger receive = yhglobalToReceiveCouponLedgerDao.selectByPrimaryKey(record.getToReceiveCouponId());
                if (projectId != null && projectId == 0l) {
                    projectId = receive.getProjectId();
                }
                if (projectName != null && projectName == "") {
                    projectName = receive.getProjectName();
                }
                if (supplierId != null && supplierId == 0) {
                    supplierId = receive.getSupplierId();
                }
                if (accountType != null && accountType == 0) {
                    accountType = record.getAccountType();
                }
                //合计金额累加
                transactionAmount += record.getConfirmAmount();
                //修改应收记录（确认金额、未收金额、状态）
                // 更新应收返利数据
                receive.setConfirmedCouponAmount(receive.getConfirmedCouponAmount() - record.getConfirmAmount());
                receive.setReceivedCouponAmount(receive.getReceivedCouponAmount() - record.getReceiptAmount());
                receive.setToBeConfirmAmount(receive.getToBeConfirmAmount() + record.getConfirmAmount());
                if(receive.getEstimatedCouponAmount() > 0) {
                     if (receive.getToBeConfirmAmount() <= 0) {
                        // 全部发放
                        receive.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode());
                    }else if (receive.getToBeConfirmAmount() < receive.getEstimatedCouponAmount() || receive.getToBeConfirmAmount() > receive.getEstimatedCouponAmount()) {
                        // 部分确认
                        receive.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_PART_OF_ISSUE.getCode());
                    } else if (receive.getToBeConfirmAmount() - receive.getEstimatedCouponAmount()==0) {
                        // 未确认
                        receive.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode());
                    }
                }else if (receive.getEstimatedCouponAmount() < 0){
                    if (receive.getToBeConfirmAmount() >= 0) {
                        // 全部发放
                        receive.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode());
                    }else if (receive.getToBeConfirmAmount() < receive.getEstimatedCouponAmount() || receive.getToBeConfirmAmount()>receive.getEstimatedCouponAmount()) {
                        // 部分确认
                        receive.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_PART_OF_ISSUE.getCode());
                    } else if (receive.getToBeConfirmAmount() - receive.getEstimatedCouponAmount()==0) {
                        // 未确认
                        receive.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode());
                    }
                }
                //更新应收记录
                receive.setDataVersion(receive.getDataVersion()+1);
                // receive.setTracelog(receive.getTracelog()+";"+rpcHeader.username+"进行了返利确认取消");
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消返利确认");
                String appendTraceLog = TraceLogUtil.appendTraceLog(receive.getTracelog(), traceLog);
                receive.setTracelog(appendTraceLog);
                yhglobalToReceiveCouponLedgerDao.update(receive);
                //修改确认记录为已回滚
                record.setIsRollback(1);
                yhglobalCouponWriteoffRecordDao.update(record);
            }

            //查询帐户余额
            Long accountTotalAmount = 0l;
            // 返利实收账户
            if (CouponConfrimAccountType.COUPON_RECEIVED_ACCOUNT.getCode() == accountType) {
                YhglobalReceivedCouponAccount account = yhglobalReceivedCouponAccountDao.getAccount(projectId);
                accountTotalAmount = account.getTotalAmount();
            } else if (CouponConfrimAccountType.SALES_DIFFERENCE_ACCOUNT.getCode() == accountType) {
                // 销售差价账户
                SupplierSellHeightAccount account = supplierSellHeightTransferAccountDao.getAccount(projectId);
                accountTotalAmount = account.getTotalAmount();
            }
            Long amountBeforeTransaction = accountTotalAmount;//流水发生前的余额
            Long amountAfterTransaction = amountBeforeTransaction + transactionAmount;//流水发生后的余额

            //生成应收确认对冲流水
            try {
                //按帐户类型查询帐户余额
                flowCode = addWriteoffFlow(rpcHeader, accountType, projectId, projectName,
                        amountBeforeTransaction, transactionAmount, amountAfterTransaction, new Date(),
                        new Long(supplierId).intValue(), "", "",
                        null,
                        null,
                        "");
            } catch (Exception ex) {
                logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + ex.getMessage(), ex);
            }
            //修改帐户余额
            if (AccountType.ACCOUNT_PREPAID_RECEIPTED.getCode() == accountType) {
                //修改实收帐户余额
                yhglobalReceivedCouponAccountService.writeoffYhglobalCouponReceivedAccount(rpcHeader, "CNY", supplierId, projectId, -transactionAmount, new Date(),null);
            } else if (AccountType.ACCOUNT_SALE_DIFFERENCE.getCode() == accountType) {
                supplierSellHeightTransferAccountService.writeoffSellHeightAmount(rpcHeader, "CNY", projectId, -transactionAmount, new Date());
            }
            return true;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("receiveCancelConfirm error!", e);
        }
    }






}
