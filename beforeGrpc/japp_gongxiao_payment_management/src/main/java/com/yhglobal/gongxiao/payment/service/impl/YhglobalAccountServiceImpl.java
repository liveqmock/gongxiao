package com.yhglobal.gongxiao.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.constant.YhGlobalInoutFlowConstant;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedCouponAccountDao;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedCouponRecordDao;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedPrepaidAccountDao;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedPrepaidRecordDao;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponAccount;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponRecord;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedPrepaidAccount;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedPrepaidRecord;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.YhglobalAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedPrepaidAccountService;
import com.yhglobal.gongxiao.util.AccountSubtotalUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yhglobal.gongxiao.constant.ErrorCode.YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH;
import static com.yhglobal.gongxiao.constant.ErrorCode.YHGLOBAL_RECEIVED_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH;
import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;

/**
 * @author: 葛灿
 */
@Service(timeout = 5000)
public class YhglobalAccountServiceImpl implements YhglobalAccountService {


    private static Logger logger = LoggerFactory.getLogger(YhglobalAccountServiceImpl.class);


    @Autowired
    SupplierCouponBufferToYhglobalService supplierCouponBufferToYhglobalService;

    @Autowired
    SupplierPrepaidBufferToYhglobalService supplierPrepaidBufferToYhglobalService;

    @Autowired
    YhglobalReceivedCouponAccountService yhglobalReceivedCouponAccountService;

    @Autowired
    YhglobalReceivedPrepaidAccountService yhglobalReceivedPrepaidAccountService;

    @Autowired
    YhglobalReceivedCouponAccountDao yhglobalReceivedCouponAccountDao;

    @Autowired
    YhglobalReceivedPrepaidAccountDao yhglobalReceivedPrepaidAccountDao;

    @Autowired
    YhglobalReceivedCouponRecordDao yhglobalReceivedCouponRecordDao;

    @Autowired
    YhglobalReceivedPrepaidRecordDao yhglobalReceivedPrepaidRecordDao;

    @Override
    public AccountAmount getYhglobalReceivedAccountAmount(RpcHeader rpcHeader, String currencyCode, long projectId) {
        logger.info("#traceId={}# [IN][getYhglobalReceivedAccountAmount] params: currencyCode={}, projectId={}",
                rpcHeader.traceId, currencyCode, projectId);
        try {
            double roundUpValue;
            AccountAmount accountAmount = new AccountAmount();
            YhglobalReceivedCouponAccount couponAccount = yhglobalReceivedCouponAccountDao.getAccount(projectId);
            accountAmount.setCouponAmount(couponAccount.getTotalAmount());
            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * couponAccount.getTotalAmount() / HUNDRED);
            accountAmount.setCouponAmountDouble(roundUpValue);
            YhglobalReceivedPrepaidAccount prepaidAccount = yhglobalReceivedPrepaidAccountDao.getAccount(projectId);
            accountAmount.setPrepaidAmount(prepaidAccount.getTotalAmount());
            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * prepaidAccount.getTotalAmount() / HUNDRED);
            accountAmount.setPrepaidAmountDouble(roundUpValue);
            logger.info("#traceId={}# [OUT]: get amount success, projectId={}", rpcHeader.traceId, projectId);
            return accountAmount;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public PageInfo<FrontPageFlow> selectCouponReceivedRecords(RpcHeader rpcHeader, String currencyCode, long projectId, Integer moneyFlow, Date beginDate, Date endDate, int pageNum, int pageSize) {
        logger.info("#traceId={}# [IN][selectCouponReceivedRecords] params:currencyCode{}, projectId{}, moneyFlow{}, beginDate{}, endDate{}, pageNum{}, pageSize={}",
                rpcHeader.traceId, currencyCode, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<YhglobalReceivedCouponRecord> list = yhglobalReceivedCouponRecordDao.selectCouponReceivedRecords(currencyCode, projectId, moneyFlow, beginDate, endDate);
            PageInfo<YhglobalReceivedCouponRecord> oldPageInfo = new PageInfo<>(list);
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (YhglobalReceivedCouponRecord record : list) {
                FrontPageFlow pageFlow = new FrontPageFlow();
                pageFlow.setFlowNo(record.getRecordId() + "");
                pageFlow.setType(record.getRecordType());
                pageFlow.setCurrencyCode(record.getCurrencyCode());
                pageFlow.setTransactionAmount(1.0 * record.getTransactionAmount() / HUNDRED);
                pageFlow.setAmountAfterTransaction(1.0 * record.getAmountAfterTransaction() / HUNDRED);
                pageFlow.setCreateTime(record.getCreateTime());
                pageFlow.setCreateByName(record.getCreatedByName());
                frontPageFlows.add(pageFlow);
            }
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlows);
            pageInfo.setTotal(oldPageInfo.getTotal());
            logger.info("#traceId={}# [OUT]: select list success", rpcHeader.traceId);
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public FrontPageFlowSubtotal getCouponSubtotal(RpcHeader rpcHeader,
                                                   String currencyCode,
                                                   long projectId, Integer moneyFlow,
                                                   Date beginDate, Date endDate) {
        logger.info("#traceId={}# [IN][getCouponSubtotal] params: currencyCode={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.traceId, currencyCode, projectId, moneyFlow, beginDate, endDate);
        try {
            List<FlowSubtotal> flowSubtotals = yhglobalReceivedCouponRecordDao.selectIncomeAndExpenditure(currencyCode, projectId, moneyFlow, beginDate, endDate);
            FrontPageFlowSubtotal frontPageFlowSubtotal = AccountSubtotalUtil.getSubtotal(flowSubtotals);
            logger.info("#traceId={}# [OUT]: get subtotal success", rpcHeader.traceId);
            return frontPageFlowSubtotal;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<FrontPageFlow> selectPrepaidReceivedRecords(RpcHeader rpcHeader, String currencyCode, long projectId, Integer moneyFlow, Date beginDate, Date endDate, int pageNum, int pageSize) {
        logger.info("#traceId={}# [IN][selectPrepaidReceivedRecords] params: currencyCode={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.traceId, currencyCode, projectId, moneyFlow, beginDate, endDate);
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<YhglobalReceivedPrepaidRecord> list = yhglobalReceivedPrepaidRecordDao.selectPrepaidReceivedRecords(currencyCode, projectId, moneyFlow, beginDate, endDate);
            PageInfo<YhglobalReceivedPrepaidRecord> oldPageInfo = new PageInfo<>(list);
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (YhglobalReceivedPrepaidRecord record : list) {
                FrontPageFlow pageFlow = new FrontPageFlow();
                pageFlow.setFlowNo(record.getRecordId() + "");
                pageFlow.setType(record.getRecordType());
                pageFlow.setCurrencyCode(record.getCurrencyCode());
                pageFlow.setTransactionAmount(1.0 * record.getTransactionAmount() / HUNDRED);
                pageFlow.setAmountAfterTransaction(1.0 * record.getAmountAfterTransaction() / HUNDRED);
                pageFlow.setCreateTime(record.getCreateTime());
                pageFlow.setCreateByName(record.getCreatedByName());
                frontPageFlows.add(pageFlow);
            }
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlows);
            pageInfo.setTotal(oldPageInfo.getTotal());
            logger.info("#traceId={}# [OUT]: select list success", rpcHeader.traceId);
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public FrontPageFlowSubtotal getPrepaidSubtotal(RpcHeader rpcHeader, String currencyCode, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        logger.info("#traceId={}# [IN][getPrepaidSubtotal] params: currencyCode={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.traceId, currencyCode, projectId, moneyFlow, beginDate, endDate);
        try {
            List<FlowSubtotal> flowSubtotals = yhglobalReceivedPrepaidRecordDao.selectIncomeAndExpenditure(currencyCode, projectId, moneyFlow, beginDate, endDate);
            FrontPageFlowSubtotal frontPageFlowSubtotal = AccountSubtotalUtil.getSubtotal(flowSubtotals);
            logger.info("#traceId={}# [OUT]: get subtotal success", rpcHeader.traceId);
            return frontPageFlowSubtotal;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult transferYhglobalReceivedCoupon(RpcHeader rpcHeader, long projectId, double amountDouble, String remark) {
        logger.info("#traceId={}# [IN][transferYhglobalReceivedCoupon] params: projectId={}, amountDouble={}",
                rpcHeader.traceId, projectId, amountDouble);
        try {
            long amount = Math.round(amountDouble * HUNDRED);
            YhglobalReceivedCouponAccount account = yhglobalReceivedCouponAccountDao.getAccount(projectId);
            if (amount > account.getTotalAmount()) {
                //账户余额不足
                return RpcResult.newFailureResult(YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
            }
            yhglobalReceivedCouponAccountService.updateYhglobalReceivedCouponAccount(rpcHeader, "CNY", projectId, -amount, new Date(), null, null, remark);
            logger.info("#traceId={}# [OUT]: transfer success. projectId={}, amount={}", rpcHeader.traceId, projectId, amountDouble);
            return RpcResult.newSuccessResult();
        } catch (DataIntegrityViolationException e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return RpcResult.newFailureResult(YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult transferYhglobalReceivedPrepaid(RpcHeader rpcHeader, long projectId, double amountDouble, String remark) {
        logger.info("#traceId={}# [IN][transferYhglobalReceivedPrepaid] params: projectId={}, amountDouble={}",
                rpcHeader.traceId, projectId, amountDouble);
        try {
            long amount = Math.round(amountDouble * HUNDRED);
            YhglobalReceivedPrepaidAccount account = yhglobalReceivedPrepaidAccountDao.getAccount(projectId);
            if (amount > account.getTotalAmount()) {
                //账户余额不足
                return RpcResult.newFailureResult(YHGLOBAL_RECEIVED_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), YHGLOBAL_RECEIVED_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
            }
            yhglobalReceivedPrepaidAccountService.updateYhglobalReceivedPrepaidAccount(rpcHeader, "CNY", projectId, -amount, new Date(), null, null,remark);
            logger.info("#traceId={}# [OUT]: transfer success. projectId={}, amount={}", rpcHeader.traceId, projectId, amountDouble);
            return RpcResult.newSuccessResult();
        } catch (DataIntegrityViolationException e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return RpcResult.newFailureResult(YHGLOBAL_RECEIVED_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), YHGLOBAL_RECEIVED_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
