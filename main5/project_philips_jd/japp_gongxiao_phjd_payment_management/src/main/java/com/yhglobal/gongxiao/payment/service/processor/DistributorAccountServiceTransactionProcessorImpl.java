package com.yhglobal.gongxiao.payment.service.processor;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.payment.bo.CashCouponPrepaidFlows;
import com.yhglobal.gongxiao.payment.dao.DistributorCashAccountDao;
import com.yhglobal.gongxiao.payment.dao.DistributorCouponAccountDao;
import com.yhglobal.gongxiao.payment.dao.DistributorPrepaidAccountDao;
import com.yhglobal.gongxiao.payment.model.DistributorCouponAccount;
import com.yhglobal.gongxiao.payment.model.DistributorPrepaidAccount;
import com.yhglobal.gongxiao.payment.service.*;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
@Service
public class DistributorAccountServiceTransactionProcessorImpl implements DistributorAccountServiceTransactionProcessor {

    private static Logger logger = LoggerFactory.getLogger(DistributorAccountServiceTransactionProcessorImpl.class);

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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpcResult<CashCouponPrepaidFlows> payForSalesOrder(GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, long couponAmount, long prepaidAmount, long cashAmount, String salesOrderNo, Date transactionTime) {
        logger.info("#traceId={}# [IN][payForSalesOrder] transaction start");
        try {
            CashCouponPrepaidFlows flows = new CashCouponPrepaidFlows();
            if (cashAmount > 0) {
                try {
                    //前往现金账账户扣款，返回流水号
                    String couponFlowNo = distributorCashAccountService.updateDistributorCashAccount(rpcHeader,
                            currencyCode, distributorId,
                            projectId, -cashAmount, salesOrderNo, transactionTime, null
                    );
                    flows.setCashFlowNo(couponFlowNo);
                } catch (DataIntegrityViolationException e) {
                    //ad现金余额不足
                    logger.error("#traceId={}# [OUT]: pay for order FAILED.", rpcHeader.getTraceId());
                    return RpcResult.newFailureResult(ErrorCode.DISTRIBUTOR_CASH_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.DISTRIBUTOR_CASH_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
                }
            }
            if (couponAmount > 0) {
                try {
                    //前往返利账户扣款，返回流水号
                    String couponFlowNo = distributorCouponAccountService.updateDistributorCouponAccount(rpcHeader,
                            currencyCode, distributorId,
                            projectId, -couponAmount, salesOrderNo, transactionTime, null, null
                    );
                    flows.setCouponFlowNo(couponFlowNo);
                } catch (DataIntegrityViolationException e) {
                    //ad返利余额不足
                    logger.error("#traceId={}# [OUT]: pay for order FAILED.", rpcHeader.getTraceId());
                    return RpcResult.newFailureResult(ErrorCode.DISTRIBUTOR_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.DISTRIBUTOR_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
                }
            }
            if (prepaidAmount > 0) {
                try {
                    //前往返利账户扣款，返回流水号
                    String prepaidFlow = distributorPrepaidAccountService.updateDistributorPrepaidAccount(rpcHeader,
                            currencyCode, distributorId,
                            projectId, -prepaidAmount, salesOrderNo, transactionTime, null, null
                    );
                    flows.setPrepaidFlowNo(prepaidFlow);
                } catch (DataIntegrityViolationException e) {
                    //ad代垫余额不足
                    logger.error("#traceId={}# [OUT]: pay for order FAILED.", rpcHeader.getTraceId());
                    return RpcResult.newFailureResult(ErrorCode.DISTRIBUTOR_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.DISTRIBUTOR_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
                }
            }
            logger.info("#traceId={}# [OUT]: transaction end.", rpcHeader.getTraceId());
            return RpcResult.newSuccessResult(flows);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpcResult<CashCouponPrepaidFlows> returnForSalesReturnOrder(GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, long couponAmount, long prepaidAmount, long cashAmount, String salesReturnOrderNo, Date transactionTime) {
        logger.info("#traceId={}# [IN][returnForSalesReturnOrder] params: currencyCode={}, distributorId={}, projectId={}, couponAmount={}, prepaidAmount={}, cashAmount={}, salesReturnOrderNo={}, transactionTime={}",
                rpcHeader.getTraceId(), currencyCode, distributorId, projectId, couponAmount, prepaidAmount, cashAmount, salesReturnOrderNo, transactionTime);
        try {
            CashCouponPrepaidFlows flows = new CashCouponPrepaidFlows();
            if (cashAmount > 0) {
                //现金转入，返回流水号
                String cashFlowNo = distributorCashAccountService.updateDistributorCashAccount(rpcHeader,
                        currencyCode, distributorId,
                        projectId, cashAmount, salesReturnOrderNo, transactionTime, null
                );
                flows.setCashFlowNo(cashFlowNo);
            }
            if (couponAmount > 0) {
                //返利转入，返回流水号
                String couponFlowNo = distributorCouponAccountService.updateDistributorCouponAccount(rpcHeader,
                        currencyCode, distributorId,
                        projectId, couponAmount, salesReturnOrderNo, transactionTime, null, null
                );
                flows.setCouponFlowNo(couponFlowNo);
            }
            if (prepaidAmount > 0) {
                //返利转入，返回流水号
                String prepaidFlow = distributorPrepaidAccountService.updateDistributorPrepaidAccount(rpcHeader,
                        currencyCode, distributorId,
                        projectId, prepaidAmount, salesReturnOrderNo, transactionTime, null, null
                );
                flows.setPrepaidFlowNo(prepaidFlow);
            }
            logger.info("#traceId={}# [OUT]: pay for return success.", rpcHeader.getTraceId());
            return RpcResult.newSuccessResult(flows);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpcResult<String> depositCouponReceived(GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long distributorId, String distributorName, long projectId, long couponAmount, Date transactionTime, String remark) {
        logger.info("#traceId={}# [IN][depositCouponReceived] params: currencyCode={}, distributorId={}, distributorName={}, projectId={}, couponAmount={}, transactionTime={}, remark={}",
                rpcHeader.getTraceId(), currencyCode, distributorId, distributorName, projectId, couponAmount, transactionTime, remark);
        try {
            //返利过账账户转出
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            String flowNo = supplierCouponBufferToDistributorService.updateCouponBufferToDistributorAccount(prefix, rpcHeader,
                    currencyCode, projectId, distributorId, distributorName, -couponAmount, null, transactionTime, remark, null);
            //返利账户转入
            String couponFlowNo = distributorCouponAccountService.updateDistributorCouponAccount(rpcHeader,
                    currencyCode, distributorId,
                    projectId, couponAmount, null, transactionTime, remark, flowNo
            );
            logger.info("#traceId={}# [OUT]: deposit success.", rpcHeader.getTraceId());
            return RpcResult.newSuccessResult(couponFlowNo);
        } catch (DataIntegrityViolationException e) {
            //返利过账账户余额不足
            logger.error("#traceId={}# [OUT]: pay for order FAILED.", rpcHeader.getTraceId());
            return RpcResult.newFailureResult(ErrorCode.COUPON_BUFFER_TO_DISTRIBUTOR_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.COUPON_BUFFER_TO_DISTRIBUTOR_BALANCE_NOT_ENOUGH.getMessage());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpcResult<String> depositPrepaidReceived(GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long distributorId, String distributorName, long projectId, long prepaidAmount, Date transactionTime, String remark) {
        logger.info("#traceId={}# [IN][depositPrepaidReceived] params: currencyCode={}, supplierId={}, supplierName={}, distributorId={}, distributorName={}, projectId={}, couponAmount={}, transactionTime={}, remark={}",
                rpcHeader.getTraceId(), currencyCode, distributorId, distributorName, projectId, prepaidAmount, transactionTime, remark);
        try {
            //返利过账账户转出
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            String flowNo = supplierPrepaidBufferToDistributorService.updatePrepaidBufferToDistributorAccount(prefix,rpcHeader,
                    currencyCode, projectId, distributorId, distributorName, -prepaidAmount, null,
                    transactionTime, remark, null);
            //返利账户转入
            String couponFlowNo = distributorPrepaidAccountService.updateDistributorPrepaidAccount(rpcHeader,
                    currencyCode, distributorId,
                    projectId, prepaidAmount, null, transactionTime, remark, flowNo);
            logger.info("#traceId={}# [OUT]: deposit success.", rpcHeader.getTraceId());
            return RpcResult.newSuccessResult(couponFlowNo);
        } catch (DataIntegrityViolationException e) {
            //代垫过账账户余额不足
            logger.error("#traceId={}# [OUT]: pay for order FAILED.", rpcHeader.getTraceId());
            return RpcResult.newFailureResult(ErrorCode.DISTRIBUTOR_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.DISTRIBUTOR_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpcResult depositCouponReceivedAccounts(GongxiaoRpc.RpcHeader rpcHeader, List<DistributorCouponAccount> distributorCouponAccounts) {
        logger.info("#traceId={}# [IN][depositCouponReceivedAccounts] params:  distributorCouponAccounts.size()={}",
                rpcHeader.getTraceId(), distributorCouponAccounts.size());
        try {
            for (DistributorCouponAccount pageAccount : distributorCouponAccounts) {
                DistributorCouponAccount databaseAccount = distributorCouponAccountDao.getAccount(pageAccount.getProjectId(), pageAccount.getDistributorId());
                //每个账户转入的金额
                double transferAmountDouble = pageAccount.getTransferAmountDouble();
                long transferAmount = (long) (transferAmountDouble * 1000);
                RpcResult<String> rpcResult = this.depositCouponReceived(rpcHeader, databaseAccount.getCurrencyCode(),
                        databaseAccount.getDistributorId(), databaseAccount.getDistributorName(),
                        databaseAccount.getProjectId(), transferAmount,
                        new Date(), null);
                if (!rpcResult.getSuccess()) {
                    //如果失败，回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return rpcResult;
                }
            }
            logger.info("#traceId={}# [OUT]: deposit list success.", rpcHeader.getTraceId());
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpcResult depositPrepaidReceivedAccounts(GongxiaoRpc.RpcHeader rpcHeader, List<DistributorPrepaidAccount> distributorPrepaidAccounts) {
        logger.info("#traceId={}# [IN][depositCouponReceivedAccounts] params:  distributorPrepaidAccounts.size()={}",
                rpcHeader.getTraceId(), distributorPrepaidAccounts.size());
        try {
            for (DistributorPrepaidAccount pageAccount : distributorPrepaidAccounts) {
                DistributorPrepaidAccount databaseAccount = distributorPrepaidAccountDao.getAccount(pageAccount.getProjectId(), pageAccount.getDistributorId());
                //每个账户转入的金额
                double transferAmountDouble = pageAccount.getTransferAmountDouble();
                long transferAmount = (long) (transferAmountDouble * 1000);
                RpcResult<String> rpcResult = this.depositPrepaidReceived(rpcHeader, databaseAccount.getCurrencyCode(),
                        databaseAccount.getDistributorId(), databaseAccount.getDistributorName(),
                        databaseAccount.getProjectId(), transferAmount,
                        new Date(), null);
                if (!rpcResult.getSuccess()) {
                    //如果失败，回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return rpcResult;
                }
            }
            logger.info("#traceId={}# [OUT]: deposit list success.", rpcHeader.getTraceId());
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
