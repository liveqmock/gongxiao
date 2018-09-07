package com.yhglobal.gongxiao.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.CashCouponPrepaidFlows;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.constant.YhGlobalInoutFlowConstant;
import com.yhglobal.gongxiao.payment.dao.DistributorCashAccountDao;
import com.yhglobal.gongxiao.payment.dao.DistributorCashFlowDao;
import com.yhglobal.gongxiao.payment.dao.DistributorCouponAccountDao;
import com.yhglobal.gongxiao.payment.dao.DistributorPrepaidAccountDao;
import com.yhglobal.gongxiao.payment.dao.DistributorPrepaidFlowDao;
import com.yhglobal.gongxiao.payment.model.*;
import com.yhglobal.gongxiao.payment.service.DistributorAccountService;
import com.yhglobal.gongxiao.payment.service.DistributorCashAccountService;
import com.yhglobal.gongxiao.payment.service.DistributorCouponAccountService;
import com.yhglobal.gongxiao.payment.service.DistributorPrepaidAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.processor.DistributorAccountServiceTransactionProcessor;
import com.yhglobal.gongxiao.util.AccountSubtotalUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;

/**
 * @author: 葛灿
 */
@Service(timeout = 5000)
public class DistributorAccountServiceImpl implements DistributorAccountService {

    private static Logger logger = LoggerFactory.getLogger(DistributorAccountServiceImpl.class);

    @Autowired
    DistributorCashAccountService distributorCashAccountService;
    @Autowired
    DistributorCouponAccountService distributorCouponAccountService;
    @Autowired
    DistributorPrepaidAccountService distributorPrepaidAccountService;
    @Autowired
    SupplierCouponBufferToDistributorService supplierCouponBufferToDistributorService;
    @Autowired
    SupplierPrepaidBufferToDistributorService supplierPrepaidBufferToDistributorService;
    @Autowired
    DistributorCouponAccountDao distributorCouponAccountDao;
    @Autowired
    DistributorPrepaidAccountDao distributorPrepaidAccountDao;
    @Autowired
    DistributorCashAccountDao distributorCashAccountDao;
    @Autowired
    DistributorCashFlowDao distributorCashFlowDao;
    @Autowired
    DistributorPrepaidFlowDao distributorPrepaidFlowDao;
    @Autowired
    DistributorAccountServiceTransactionProcessor distributorAccountServiceTransactionProcessor;


    @Override
    public AccountAmount getDistributorAccountAmount(RpcHeader rpcHeader, String currencyCode, long projectId, long distributorId) {

        logger.info("#traceId={}# [IN][getDistributorAccountAmount] params: currencyCode={}, projectId={}, distributorId={}",
                rpcHeader.traceId, currencyCode, projectId, distributorId);
        try {
            AccountAmount accountAmount = new AccountAmount();
            //查询现金账户
            DistributorCashAccount account = distributorCashAccountDao.getAccount(projectId, distributorId);
            accountAmount.setCashAmount(account.getTotalAmount());
            accountAmount.setCashAmountDouble(Double.parseDouble(DoubleScale.keepTwoBits(account.getTotalAmount())));
            //查询返利账户
            DistributorCouponAccount couponAccount = distributorCouponAccountDao.getAccount(projectId, distributorId);
            accountAmount.setCouponAmount(couponAccount.getTotalAmount());
            accountAmount.setCouponAmountDouble(Double.parseDouble(DoubleScale.keepTwoBits(couponAccount.getTotalAmount())));
            //查询代垫账户
            DistributorPrepaidAccount prepaidAccount = distributorPrepaidAccountDao.getAccount(projectId, distributorId);
            accountAmount.setPrepaidAmount(prepaidAccount.getTotalAmount());
            accountAmount.setPrepaidAmountDouble(Double.parseDouble(DoubleScale.keepTwoBits(prepaidAccount.getTotalAmount())));
            logger.info("#traceId={}# [OUT]: get amount success.", rpcHeader.traceId);
            return accountAmount;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult<CashCouponPrepaidFlows> payForSalesOrder(RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, long couponAmount, long prepaidAmount, long cashAmount, String salesOrderNo, Date transactionTime) {
        logger.info("#traceId={}# [IN][payForSalesOrder] params: currencyCode={}, distributorId={}, projectId={}, couponAmount={}, prepaidAmount={}, cashAmount={}, salesOrderNo={}, transactionTime={}",
                rpcHeader.traceId, currencyCode, distributorId, projectId, couponAmount, prepaidAmount, cashAmount, salesOrderNo, transactionTime);
        try {
            RpcResult<CashCouponPrepaidFlows> rpcResult = distributorAccountServiceTransactionProcessor.payForSalesOrder(rpcHeader, currencyCode, distributorId, projectId, couponAmount, prepaidAmount, cashAmount, salesOrderNo, transactionTime);
            return rpcResult;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult<CashCouponPrepaidFlows> returnForSalesReturnOrder(RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, long couponAmount, long prepaidAmount, long cashAmount, String salesReturnOrderNo, Date transactionTime) {
        logger.info("#traceId={}# [IN][returnForSalesReturnOrder] params: currencyCode={}, distributorId={}, projectId={}, couponAmount={}, prepaidAmount={}, cashAmount={}, salesReturnOrderNo={}, transactionTime={}",
                rpcHeader.traceId, currencyCode, distributorId, projectId, couponAmount, prepaidAmount, cashAmount, salesReturnOrderNo, transactionTime);
        try {
            RpcResult<CashCouponPrepaidFlows> rpcResult = distributorAccountServiceTransactionProcessor.returnForSalesReturnOrder(rpcHeader, currencyCode, distributorId, projectId, couponAmount, prepaidAmount, cashAmount, salesReturnOrderNo, transactionTime);
            return rpcResult;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult<String> depositCouponReceived(RpcHeader rpcHeader, String currencyCode, long distributorId, String distributorName, long projectId, long couponAmount, Date transactionTime, String remark) {
        logger.info("#traceId={}# [IN][depositCouponReceived] params: currencyCode={}, distributorId={}, distributorName={}, projectId={}, couponAmount={}, transactionTime={}, remark={}",
                rpcHeader.traceId, currencyCode, distributorId, distributorName, projectId, couponAmount, transactionTime, remark);
        try {
            RpcResult<String> rpcResult = distributorAccountServiceTransactionProcessor.depositCouponReceived(rpcHeader, currencyCode, distributorId, distributorName, projectId, couponAmount, transactionTime, remark);
            return rpcResult;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult<String> depositPrepaidReceived(RpcHeader rpcHeader, String currencyCode, long distributorId, String distributorName, long projectId, long prepaidAmount, Date transactionTime, String remark) {
        logger.info("#traceId={}# [IN][depositPrepaidReceived] params: currencyCode={}, supplierId={}, supplierName={}, distributorId={}, distributorName={}, projectId={}, couponAmount={}, transactionTime={}, remark={}",
                rpcHeader.traceId, currencyCode, distributorId, distributorName, projectId, prepaidAmount, transactionTime, remark);
        try {
            RpcResult<String> rpcResult = distributorAccountServiceTransactionProcessor.depositPrepaidReceived(rpcHeader, currencyCode, distributorId, distributorName, projectId, prepaidAmount, transactionTime, remark);
            return rpcResult;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<DistributorCouponAccount> selectDistributorCouponAccountList(RpcHeader rpcHeader, String currencyCode, long projectId, int pageNum, int pageSize) {
        logger.info("#traceId={}# [IN][selectDistributorCouponAccountList] params: currencyCode={}, projectId={}, pageNum={}, pageSize={}",
                rpcHeader.traceId, currencyCode, projectId, pageNum, pageSize);
        try {
            //启动分页
            PageHelper.startPage(pageNum, pageSize);
            //查询列表
            List<DistributorCouponAccount> couponAccountList = distributorCouponAccountDao.getAccountList(projectId);
            PageInfo<DistributorCouponAccount> distributorPrepaidAccountPageInfo = new PageInfo<>(couponAccountList);
            logger.info("#traceId={}# [OUT]: select list success.", rpcHeader.traceId);
            return distributorPrepaidAccountPageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<DistributorPrepaidAccount> selectDistributorPrepaidAccountList(RpcHeader rpcHeader, String currencyCode, long projectId, int pageNum, int pageSize) {
        logger.info("#traceId={}# [IN][selectDistributorPrepaidAccountList] params: currencyCode={}, projectId={}, pageNum={}, pageSize={}",
                rpcHeader.traceId, currencyCode, projectId, pageNum, pageSize);
        try {
            //启动分页
            PageHelper.startPage(pageNum, pageSize);
            //查询列表
            List<DistributorPrepaidAccount> prepaidAccountList = distributorPrepaidAccountDao.getAccountList(projectId);
            PageInfo<DistributorPrepaidAccount> distributorPrepaidAccountPageInfo = new PageInfo<>(prepaidAccountList);
            logger.info("#traceId={}# [OUT]: select list success.", rpcHeader.traceId);
            return distributorPrepaidAccountPageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult depositCouponReceivedAccounts(RpcHeader rpcHeader, List<DistributorCouponAccount> distributorCouponAccounts) {
        logger.info("#traceId={}# [IN][depositCouponReceivedAccounts] params:  distributorCouponAccounts.size()={}",
                rpcHeader.traceId, distributorCouponAccounts.size());
        try {
            RpcResult rpcResult = distributorAccountServiceTransactionProcessor.depositCouponReceivedAccounts(rpcHeader, distributorCouponAccounts);
            return rpcResult;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override

    public RpcResult depositPrepaidReceivedAccounts(RpcHeader rpcHeader, List<DistributorPrepaidAccount> distributorPrepaidAccounts) {
        logger.info("#traceId={}# [IN][depositCouponReceivedAccounts] params:  distributorPrepaidAccounts.size()={}",
                rpcHeader.traceId, distributorPrepaidAccounts.size());
        try {
            RpcResult rpcResult = distributorAccountServiceTransactionProcessor.depositPrepaidReceivedAccounts(rpcHeader, distributorPrepaidAccounts);
            return rpcResult;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public PageInfo<FrontPageFlow> selectCashFlow(RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, Integer moneyFlow, Date beginDate, Date endDate, int pageNum, int pageSize) {
        logger.info("#traceId={}# [IN][cancelApproveLedger] params: currencyCode={}, distributorId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}, pageNum={}, pageSize={}",
                rpcHeader.traceId, currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
        try {
            PageHelper.startPage(pageNum, pageSize);
            //查询流水
            List<DistributorCashFlow> list = distributorCashFlowDao.selectAllBydistributorId(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
            //转换模型
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (DistributorCashFlow record : list) {
                FrontPageFlow pageFlow = new FrontPageFlow();
                pageFlow.setFlowNo(record.getFlowId() + "");
                pageFlow.setType(record.getFlowType());
                pageFlow.setCurrencyCode(record.getCurrencyCode());
                pageFlow.setTransactionAmount(record.getTransactionAmount() / FXConstant.HUNDRED_DOUBLE);
                pageFlow.setAmountAfterTransaction(record.getAmountAfterTransaction() / FXConstant.HUNDRED_DOUBLE);
                pageFlow.setCreateTime(record.getCreateTime());
                pageFlow.setCreateByName(record.getCreateByName());
                frontPageFlows.add(pageFlow);
            }
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlows);
            logger.info("#traceId={}# [OUT]: select list success.", rpcHeader.traceId);
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public FrontPageFlowSubtotal getCashSubtotal(RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        logger.info("#traceId={}# [IN][cancelApproveLedger] params: currencyCode={}, distributorId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.traceId, currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
        try {
            //查询小计
            List<FlowSubtotal> flowSubtotals = distributorCashFlowDao.selectIncomeAndExpenditure(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
            FrontPageFlowSubtotal subtotal = AccountSubtotalUtil.getSubtotal(flowSubtotals);
            logger.info("#traceId={}# [OUT]: get subtotal success.", rpcHeader.traceId);
            return subtotal;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<FrontPageFlow> selectPrepaidFlow(RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, int accountType, Integer moneyFlow, Date beginDate, Date endDate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DistributorPrepaidFlow> prepaidTransferRecordList = distributorPrepaidFlowDao.selectAllBydistributorId(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
        List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
        for (DistributorPrepaidFlow record : prepaidTransferRecordList) {
            FrontPageFlow frontPageFlow = generateFrontPageFlow(record);
            frontPageFlowList.add(frontPageFlow);
        }
        // 用分页包装
        PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlowList);
        return pageInfo;
    }

    /**
     * 生成流水包装类
     */
    FrontPageFlow generateFrontPageFlow(DistributorPrepaidFlow record) {
        FrontPageFlow frontPageFlow = new FrontPageFlow();
        // 前台显示订单号
        frontPageFlow.setFlowNo(record.getFlowNo());
        frontPageFlow.setAmountAfterTransaction(record.getAmountAfterTransaction() / FXConstant.HUNDRED_DOUBLE);
        frontPageFlow.setCreateTime(record.getCreateTime());
        frontPageFlow.setCurrencyCode(record.getCurrencyCode());
        frontPageFlow.setTransactionAmount(record.getTransactionAmount() / FXConstant.HUNDRED_DOUBLE);
        frontPageFlow.setType(record.getFlowType());
        frontPageFlow.setCreateByName(record.getCreateByName());
        return frontPageFlow;
    }

    @Override
    public FrontPageFlowSubtotal getPrepaidSubtotal(RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        FrontPageFlowSubtotal frontPageFlowSubtotal = new FrontPageFlowSubtotal();
        List<FlowSubtotal> flowSubtotals = distributorPrepaidFlowDao.selectIncomeAndExpenditure(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
        for (FlowSubtotal flowSubtotal : flowSubtotals) {
            if (YhGlobalInoutFlowConstant.FLOW_TYPE_IN.getNum() == flowSubtotal.getRecordType()) {
                frontPageFlowSubtotal.setIncomeQuantity(flowSubtotal.getCount());
                frontPageFlowSubtotal.setIncomeAmount(flowSubtotal.getAmountCount() / FXConstant.HUNDRED_DOUBLE);
            } else if (YhGlobalInoutFlowConstant.FLOW_TYPE_OUT.getNum() == flowSubtotal.getRecordType()) {
                frontPageFlowSubtotal.setExpenditureQuantity(flowSubtotal.getCount());
                frontPageFlowSubtotal.setExpenditureAmount(flowSubtotal.getAmountCount() / FXConstant.HUNDRED_DOUBLE);
            }
        }
        return frontPageFlowSubtotal;
    }
}
