package com.yhglobal.gongxiao.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.constant.YhGlobalInoutFlowConstant;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponAccountDao;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponBufferToDistributorDao;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponTransferToDistributorFlowDao;
import com.yhglobal.gongxiao.payment.dao.SupplierPrepaidAccountDao;
import com.yhglobal.gongxiao.payment.dao.SupplierPrepaidBufferToDistributorDao;
import com.yhglobal.gongxiao.payment.dao.SupplierPrepaidTransferRecordDao;
import com.yhglobal.gongxiao.payment.dao.SupplierPrepaidTransferToDistributorFlowDao;
import com.yhglobal.gongxiao.payment.dao.SupplierSellHeightTransferAccountDao;
import com.yhglobal.gongxiao.payment.dao.SupplierSellHeightTransferRecordDao;
import com.yhglobal.gongxiao.payment.dto.PurchaseFlowCollections;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierCouponAccount;
import com.yhglobal.gongxiao.payment.model.SupplierCouponBufferToDistributor;
import com.yhglobal.gongxiao.payment.model.SupplierCouponTransferToDistributorFlow;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidAccount;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidBufferToDistributor;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidTransferRecord;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidTransferToDistributorFlow;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightAccount;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightTransferRecord;
import com.yhglobal.gongxiao.payment.service.SupplierAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.SupplierSellHeightTransferAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedPrepaidAccountService;
import com.yhglobal.gongxiao.payment.service.processor.SupplierAccountServiceTransactionProcessor;
import com.yhglobal.gongxiao.payment.task.SyncCouponPrepaidToYhglobalReceivedTask;
import com.yhglobal.gongxiao.util.AccountSubtotalUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;

/**
 * @author: 葛灿
 */
@Service(timeout = 5000)
public class SupplierAccountServiceImpl implements SupplierAccountService {

    private static Logger logger = LoggerFactory.getLogger(SupplierAccountServiceImpl.class);

    @Autowired
    SupplierCouponAccountService supplierCouponAccountService;

    @Autowired
    SupplierPrepaidAccountService supplierPrepaidAccountService;

    @Autowired
    SupplierCouponBufferToYhglobalService supplierCouponBufferToYhglobalService;

    @Autowired
    SupplierPrepaidBufferToYhglobalService supplierPrepaidBufferToYhglobalService;

    @Autowired
    SupplierCouponBufferToDistributorService supplierCouponBufferToDistributorService;

    @Autowired
    SupplierPrepaidBufferToDistributorService supplierPrepaidBufferToDistributorService;

    @Autowired
    SupplierSellHeightTransferAccountService supplierSellHeightTransferAccountService;

    @Autowired
    SupplierSellHeightTransferAccountDao supplierSellHeightTransferAccountDao;

    @Autowired
    SupplierSellHeightTransferRecordDao supplierSellHeightTransferRecordDao;

    @Autowired
    SupplierCouponAccountDao supplierCouponAccountDao;

    @Autowired
    SupplierPrepaidAccountDao supplierPrepaidAccountDao;

    @Autowired
    SupplierCouponBufferToDistributorDao supplierCouponBufferToDistributorDao;

    @Autowired
    SupplierPrepaidBufferToDistributorDao supplierPrepaidBufferToDistributorDao;

    @Autowired
    SupplierCouponTransferToDistributorFlowDao supplierCouponTransferToDistributorFlowDao;

    @Autowired
    SupplierPrepaidTransferToDistributorFlowDao supplierPrepaidTransferToDistributorFlowDao;

    @Autowired
    SupplierPrepaidTransferRecordDao supplierPrepaidTransferRecordDao;

    @Autowired
    private YhglobalReceivedCouponAccountService yhglobalReceivedCouponAccountService;
    @Autowired
    private YhglobalReceivedPrepaidAccountService yhglobalReceivedPrepaidAccountService;

    @Autowired
    private SupplierAccountServiceTransactionProcessor supplierAccountServiceTransactionProcessor;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolExecutor;


    @Override
    public AccountAmount getSupplierCouponBufferToDistributor(RpcHeader rpcHeader, String currencyCode, long projectId) {
        logger.info("#traceId={}# [IN][getSupplierCouponBufferToDistributor] params: currencyCode={}, projectId={}",
                rpcHeader.traceId, currencyCode, projectId);
        try {
            //新建返回模型
            double roundUpValue;
            AccountAmount accountAmount = new AccountAmount();
            //供应商返利过账到ad账户
            SupplierCouponBufferToDistributor couponBufferToDistributor = supplierCouponBufferToDistributorDao.getAccount(projectId);
            accountAmount.setCouponAmount(couponBufferToDistributor.getTotalAmount());
            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * couponBufferToDistributor.getTotalAmount() / HUNDRED);
            accountAmount.setCouponAmountDouble(roundUpValue);
            //供应商代垫过账到ad账户
            SupplierPrepaidBufferToDistributor prepaidBufferToDistributor = supplierPrepaidBufferToDistributorDao.getAccount(projectId);
            accountAmount.setPrepaidAmount(prepaidBufferToDistributor.getTotalAmount());
            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * prepaidBufferToDistributor.getTotalAmount() / HUNDRED);
            accountAmount.setPrepaidAmountDouble(roundUpValue);
            logger.info("#traceId={}# [OUT]: get amount success.", rpcHeader.traceId);
            return accountAmount;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public AccountAmount getSupplierAccountAmount(RpcHeader rpcHeader, String currencyCode, long projectId) {
        logger.info("#traceId={}# [IN][getSupplierAccountAmount] params: currencyCode={}, projectId={}",
                rpcHeader.traceId, currencyCode, projectId);
        try {
            double roundUpValue;
            AccountAmount accountAmount = new AccountAmount();
            SupplierCouponAccount couponAccount = supplierCouponAccountDao.getAccount(projectId);
            accountAmount.setCouponAmount(couponAccount.getTotalAmount());
            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * couponAccount.getTotalAmount() / HUNDRED);
            accountAmount.setCouponAmountDouble(roundUpValue);
            SupplierPrepaidAccount prepaidAccount = supplierPrepaidAccountDao.getAccount(projectId);
            accountAmount.setPrepaidAmount(prepaidAccount.getTotalAmount());
            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * prepaidAccount.getTotalAmount() / HUNDRED);
            accountAmount.setPrepaidAmountDouble(roundUpValue);
            logger.info("#traceId={}# [OUT]: get amount success.", rpcHeader.traceId);
            return accountAmount;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public String postSupplierCouponAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long couponAmount, String remark) {
        logger.info("#traceId={}# [IN][postSupplierCouponAccount] params: currencyCode={}, projectId={}, couponAmount={}, remark={}",
                rpcHeader.traceId, currencyCode, projectId, couponAmount, remark);
        try {
            //上账账户转入
            String flowNo = supplierCouponAccountService.updateSupplierCouponAccount(rpcHeader, currencyCode, projectId, couponAmount, new Date(), remark, null, null);
            logger.info("#traceId={}# [OUT]: post coupon success.", rpcHeader.traceId);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public String postSupplierPrepaidAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long prepaidAmount, String remark) {
        logger.info("#traceId={}# [IN][postSupplierCouponAccount] params: currencyCode={}, projectId={}, prepaidAmount={}, remark={}",
                rpcHeader.traceId, currencyCode, projectId, prepaidAmount, remark);
        try {
            //上账账户转入
            String flowNo = supplierPrepaidAccountService.updateSupplierPrepaidAccount(rpcHeader, currencyCode, projectId, prepaidAmount, new Date(), remark, null, null);
            logger.info("#traceId={}# [OUT]: approve success.", rpcHeader.traceId);
            return flowNo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult<PurchaseFlowCollections> payForPurchase(RpcHeader rpcHeader,
                                                             String currencyCode,
                                                             long projectId, long couponToYh,
                                                             long couponToAd, long prepaidToYh,
                                                             long prepaidToAd, String purchaseOrderNo,
                                                             Date transactionTime) {
        logger.info("#traceId={}# [IN][" +
                        "] params: currencyCode={}, projectId={}, couponToYh={}, couponToAd={}, prepaidToYh={}, prepaidToAd={}, purchaseOrderNo={}, transactionTime={}",
                rpcHeader.traceId, currencyCode, projectId, couponToYh, couponToAd, prepaidToYh, prepaidToAd, purchaseOrderNo, transactionTime);
        try {

            RpcResult<PurchaseFlowCollections> rpcResult = supplierAccountServiceTransactionProcessor.payForPurchase(rpcHeader, currencyCode, projectId, couponToYh, couponToAd, prepaidToYh, prepaidToAd, purchaseOrderNo, transactionTime);
            if (rpcResult.getSuccess()) {
                //新起线程，把越海的返利代垫，从缓冲账户同步到实收账户
                SyncCouponPrepaidToYhglobalReceivedTask task = new SyncCouponPrepaidToYhglobalReceivedTask(ApplicationContextProvider.getApplicationContext(), rpcHeader,
                        currencyCode, projectId, couponToYh, prepaidToYh, transactionTime, purchaseOrderNo, supplierCouponBufferToYhglobalService,
                        yhglobalReceivedCouponAccountService,
                        supplierPrepaidBufferToYhglobalService,
                        yhglobalReceivedPrepaidAccountService);
                threadPoolExecutor.submit(task);
            }
            logger.info("#traceId={}# [OUT]: pay for purchase success.", rpcHeader.traceId);
            return rpcResult;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public PageInfo<FrontPageFlow> selectBufferCouponFlowList(RpcHeader rpcHeader,
                                                              String currencyCode, Long supplierId, long projectId, Integer moneyFlow,
                                                              Date beginDate,
                                                              Date endDate, int pageNum, int pageSize) {

        logger.info("#traceId={}# [IN][selectBufferCouponFlowList] params: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}, pageNum={}, pageSize={}",
                rpcHeader.traceId, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<SupplierCouponTransferToDistributorFlow> list = supplierCouponTransferToDistributorFlowDao.selectBufferCouponFlowList(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            PageInfo<SupplierCouponTransferToDistributorFlow> oldPageInfo = new PageInfo<>(list);
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (SupplierCouponTransferToDistributorFlow record : list) {
                FrontPageFlow pageFlow = new FrontPageFlow();
                pageFlow.setFlowNo(record.getFlowId() + "");
                pageFlow.setType(record.getFlowType());
                pageFlow.setCurrencyCode(record.getCurrencyCode());
                pageFlow.setTransactionAmount(1.0 * record.getTransactionAmount() / HUNDRED);
                pageFlow.setAmountAfterTransaction(1.0 * record.getAmountAfterTransaction() / HUNDRED);
                pageFlow.setCreateTime(record.getCreateTime());
                pageFlow.setCreateByName(record.getCreatedByName());
                frontPageFlows.add(pageFlow);
            }
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlows);
            pageInfo.setTotal(oldPageInfo.getTotal());
            logger.info("#traceId={}# [OUT]: get buffer coupon flow list success.", rpcHeader.traceId);
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<FrontPageFlow> selectBufferPrepaidFlowList(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate, int pageNum, int pageSize) {
        logger.info("#traceId={}# [IN][selectBufferPrepaidFlowList] params: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}, pageNum={}, pageSize={}",
                rpcHeader.traceId, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<SupplierPrepaidTransferToDistributorFlow> list = supplierPrepaidTransferToDistributorFlowDao.selectBufferPrepaidFlowList(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            PageInfo<SupplierPrepaidTransferToDistributorFlow> oldPageInfo = new PageInfo<>(list);
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (SupplierPrepaidTransferToDistributorFlow record : list) {
                FrontPageFlow pageFlow = new FrontPageFlow();
                pageFlow.setFlowNo(record.getFlowId() + "");
                pageFlow.setType(record.getFlowType());
                pageFlow.setCurrencyCode(record.getCurrencyCode());
                pageFlow.setTransactionAmount(1.0 * record.getTransactionAmount() / HUNDRED);
                pageFlow.setAmountAfterTransaction(1.0 * record.getAmountAfterTransaction() / HUNDRED);
                pageFlow.setCreateTime(record.getCreateTime());
                pageFlow.setCreateByName(record.getCreatedByName());
                frontPageFlows.add(pageFlow);
            }
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlows);
            pageInfo.setTotal(oldPageInfo.getTotal());
            logger.info("#traceId={}# [OUT]: get buffer prepaid flow list success.", rpcHeader.traceId);
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public FrontPageFlowSubtotal getCouponBufferToDistributorSubtotal(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        logger.info("#traceId={}# [IN][getCouponBufferToDistributorSubtotal] params: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.traceId, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
        try {
            List<FlowSubtotal> flowSubtotals = supplierCouponTransferToDistributorFlowDao.selectIncomeAndExpenditure(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            FrontPageFlowSubtotal frontPageFlowSubtotal = AccountSubtotalUtil.getSubtotal(flowSubtotals);
            logger.info("#traceId={}# [OUT]: get subtotal success.", rpcHeader.traceId);
            return frontPageFlowSubtotal;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public FrontPageFlowSubtotal getPrepaidBufferToDistributorSubtotal(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        logger.info("#traceId={}# [IN][getCouponBufferToDistributorSubtotal] params: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.traceId, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
        try {
            List<FlowSubtotal> flowSubtotals = supplierPrepaidTransferToDistributorFlowDao.selectIncomeAndExpenditure(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            FrontPageFlowSubtotal frontPageFlowSubtotal = AccountSubtotalUtil.getSubtotal(flowSubtotals);
            logger.info("#traceId={}# [OUT]: get subtotal success.", rpcHeader.traceId);
            return frontPageFlowSubtotal;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public SupplierSellHeightAccount getSellHighAccount(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId) {
        logger.info("#traceId={}# [IN][getSellHighAccount] params: currencyCode={}, supplierId={}, projectId={}",
                rpcHeader.getTraceId(), currencyCode, supplierId, projectId);
        try {
            SupplierSellHeightAccount account = supplierSellHeightTransferAccountDao.getAccount(projectId);
            if (account != null) {
                account.setTotalAmountDouble(1.0 * account.getTotalAmount() / HUNDRED);
                logger.info("#traceId={}# [OUT] get account success.", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] get account fail.", rpcHeader.traceId);
            }
            return account;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult salesUpdateSellHighAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long amount, String salesOrderNo, Date transactionTime) {
        logger.info("#traceId={}# [IN][salesUpdateSellHighAccount] params: currencyCode={}, projectId={}, amount={}, salesOrderNo={}, transactionTime={}",
                rpcHeader.getTraceId(), currencyCode, projectId, amount, salesOrderNo, transactionTime);
        try {
            String flowNo = supplierSellHeightTransferAccountService.updateSupplierSellHeightTransferAccount(rpcHeader, currencyCode, projectId, amount, salesOrderNo, transactionTime);
            logger.info("#traceId={}# [OUT] update sell high account success. salesOrderNo={}, flowNo={}", rpcHeader.traceId, salesOrderNo, flowNo);
            return RpcResult.newSuccessResult(flowNo);
        } catch (DataIntegrityViolationException e) {
            //返利上账账户余额不足
            logger.error("#traceId={}# [OUT]: pay for order FAILED.", rpcHeader.traceId);
            return RpcResult.newFailureResult(ErrorCode.SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), ErrorCode.SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<FrontPageFlow> selectSupplierSellHighRecordList(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate, int pageNum, int pageSize) {
        logger.info("#traceId={}# [IN][selectSupplierSellHighRecordList] params: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}, pageNum={}, pageSize={}",
                rpcHeader.traceId, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<SupplierSellHeightTransferRecord> list = supplierSellHeightTransferRecordDao.selectSupplierSellHighRecordList(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            PageInfo<SupplierSellHeightTransferRecord> oldPageInfo = new PageInfo<>(list);
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (SupplierSellHeightTransferRecord record : list) {
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
            logger.info("#traceId={}# [OUT]: select record list success.", rpcHeader.traceId);
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public FrontPageFlowSubtotal getSellHighSubtotal(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        logger.info("#traceId={}# [IN][getSellHighSubtotal] params: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.traceId, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
        try {
            List<FlowSubtotal> flowSubtotals = supplierSellHeightTransferRecordDao.selectIncomeAndExpenditure(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            FrontPageFlowSubtotal frontPageFlowSubtotal = AccountSubtotalUtil.getSubtotal(flowSubtotals);
            logger.info("#traceId={}# [OUT]: select record subtotal success.", rpcHeader.traceId);
            return frontPageFlowSubtotal;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<FrontPageFlow> selectPrepaidBySupplierId(String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<SupplierPrepaidTransferRecord> supplierCouponTransferRecordList = supplierPrepaidTransferRecordDao.selectAllBySupplierId(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
        PageInfo<SupplierPrepaidTransferRecord> oldPageInfo = new PageInfo<>(supplierCouponTransferRecordList);
        List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
        for (SupplierPrepaidTransferRecord record : supplierCouponTransferRecordList) {
            FrontPageFlow frontPageFlow = generateFrontPageFlow(record);
            frontPageFlowList.add(frontPageFlow);
        }
        // 用分页包装
        PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlowList);
        pageInfo.setTotal(oldPageInfo.getTotal());
        return pageInfo;
    }

    /**
     * 生成流水包装类
     */
    FrontPageFlow generateFrontPageFlow(SupplierPrepaidTransferRecord record) {
        FrontPageFlow frontPageFlow = new FrontPageFlow();
        // 前台显示订单号
        frontPageFlow.setFlowNo(record.getRecordId() + "");
        frontPageFlow.setAmountAfterTransaction(1.0 * record.getAmountAfterTransaction() / HUNDRED);
        frontPageFlow.setCreateTime(record.getCreateTime());
        frontPageFlow.setCurrencyCode(record.getCurrencyCode());
        frontPageFlow.setTransactionAmount(1.0 * record.getTransactionAmount() / HUNDRED);
        frontPageFlow.setType(record.getRecordType());
        frontPageFlow.setCreateByName(record.getCreatedByName());
        return frontPageFlow;
    }

    @Override
    public FrontPageFlowSubtotal selectIncomeAndExpenditure(String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        FrontPageFlowSubtotal frontPageFlowSubtotal = new FrontPageFlowSubtotal();
        List<FlowSubtotal> flowSubtotals = supplierPrepaidTransferRecordDao.selectIncomeAndExpenditure(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
        for (FlowSubtotal flowSubtotal : flowSubtotals) {
            if (YhGlobalInoutFlowConstant.FLOW_TYPE_IN.getNum() == flowSubtotal.getRecordType()) {
                frontPageFlowSubtotal.setIncomeQuantity(flowSubtotal.getCount());
                frontPageFlowSubtotal.setIncomeAmount(1.0 * flowSubtotal.getAmountCount() / HUNDRED);
            } else if (YhGlobalInoutFlowConstant.FLOW_TYPE_OUT.getNum() == flowSubtotal.getRecordType()) {
                frontPageFlowSubtotal.setExpenditureQuantity(flowSubtotal.getCount());
                frontPageFlowSubtotal.setExpenditureAmount(1.0 * flowSubtotal.getAmountCount() / HUNDRED);
            }
        }
        return frontPageFlowSubtotal;

    }
}
