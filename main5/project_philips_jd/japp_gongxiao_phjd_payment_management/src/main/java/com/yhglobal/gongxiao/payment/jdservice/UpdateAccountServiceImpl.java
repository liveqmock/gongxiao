package com.yhglobal.gongxiao.payment.jdservice;

import com.yhglobal.gongxiao.constant.ErrorCodeJd;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.AccountManageDao.*;
import com.yhglobal.gongxiao.payment.model.*;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 王帅
 */
@Service
public class UpdateAccountServiceImpl implements UpdateAccountService{


    private static final Logger logger = LoggerFactory.getLogger(UpdateAccountServiceImpl.class);

    @Autowired
    SupplierPurchaseReservedFrozenAccountDao supplierPurchaseReservedFrozenAccountDao;

    @Autowired
    SupplierUnitPriceDiscountFrozenAccountDao supplierUnitPriceDiscountFrozenAccountDao;
    @Autowired
    SupplierPurchaseReservedAccountDao supplierPurchaseReservedAccountDao;
    @Autowired
    SupplierUnitPriceDiscountReservedAccountDao supplierUnitPriceDiscountReservedAccountDao;

    @Autowired
    SupplierUnitPriceDiscountFrozenAccountTransferFlowDao supplierUnitPriceDiscountFrozenAccountTransferFlowDao;

    @Autowired
    SupplierPurchaseReservedFrozenAccountTransferFlowDao supplierPurchaseReservedFrozenAccountTransferFlowDao;
    @Autowired
    SupplierUnitPriceDiscountReservedAccountFlowDao supplierUnitPriceDiscountReservedAccountFlowDao;

    @Autowired
    SupplierPurchaseReservedAccountFlowDao supplierPurchaseReservedAccountFlowDao;

    @Autowired
    SupplierSalesDifferenceReservedAccountDao supplierSalesDifferenceReservedAccountDao;
    @Autowired
    SupplierSalesDifferenceReservedAccountFlowDao supplierSalesDifferenceReservedAccountFlowDao;

    /**
     * 单价折扣冻结账户更新
     * @param projectId
     * @param prefix
     * @param rpcHeader
     * @param transferAmount
     * @param remark
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpcResult updateSupplierUnitPriceDiscountFrozenAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark ) {

        String flowNo = null;
        int maxRetryTimes = 6; // 账户额度变动基于乐观锁 最大重试次数6次
        try {
            while (maxRetryTimes-- > 0) {
                // 交易额度为负表示扣减 为正为增加
                SupplierUnitPriceDiscountFrozenAccount account = supplierUnitPriceDiscountFrozenAccountDao.selectByProjectId(projectId, prefix);
                // 计算交易后账户额度
                BigDecimal amountAfterTransfer = account.getFrozenAmount().add(transferAmount);
                if (amountAfterTransfer.compareTo(new BigDecimal("0")) == -1) {// 账户交易后额度小于0 说明扣减额度过多
                    // 返回账户额度不足
                    return new RpcResult(ErrorCodeJd.SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT_NOT_ENOUGH.getErrorCode(), ErrorCodeJd.SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT_NOT_ENOUGH.getMessage());
                }
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "单价折扣冻结账户更新");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                int i = supplierUnitPriceDiscountFrozenAccountDao.updateAccountAmount(supplierId, amountAfterTransfer, account.getDataVersion(), new Date(), appendTraceLog, prefix);
                if (i != 1) {
                    // 更新失败 抛出异常
                    throw new RuntimeException("update supplierUnitPriceDiscountFrozenAccount failed");
                }
                try {
                    // 插入冻结账户交易流水
                    flowNo = insertUnitPriceDiscountFrozenAccountFlow(account.getCurrencyCode(), account.getFrozenAmount(), transferAmount,
                            amountAfterTransfer, account.getSupplierId(), account.getSupplierName(), account.getProjectId(), account.getProjectName(), Long.valueOf(rpcHeader.getUid()),
                            rpcHeader.getUsername(), remark, prefix);
                } catch (Exception e) {
                    logger.error("insert unitPriceDiscountFrozenAccountFlow failed");
                }
                break;
            }
            //如果更新失败，抛出未知系统异常
            if (maxRetryTimes <= 0) {
                logger.error("fail to update UnitPriceDiscountFrozenAccount");
                throw new RuntimeException("FAILED to update UnitPriceDiscountFrozenAccount");
            }
            logger.info("#traceId={}# [OUT]: update UnitPriceDiscountFrozenAccount success. amount={} flowNo={}", rpcHeader.getTraceId(), transferAmount, flowNo);
            return new RpcResult(ErrorCodeJd.SUCCESS.getErrorCode(), ErrorCodeJd.SUCCESS.getMessage());
        }catch (Exception e){
            logger.error("update UnitPriceDiscountFrozenAccount throw exception", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 单价折扣预留账户更新
     * @param projectId
     * @param prefix
     * @param rpcHeader
     * @param transferAmount
     * @param remark
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpcResult updateSupplierUnitPriceDiscountReservedAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark ) {

        String flowNo = null;
        int maxRetryTimes = 6; // 账户额度变动基于乐观锁 最大重试次数6次
        try {
            while (maxRetryTimes-- > 0) {
                // 交易额度为负表示扣减 为正为增加
                SupplierUnitPriceDiscountReservedAccount account = supplierUnitPriceDiscountReservedAccountDao.selectByProjectId(projectId, prefix);
                // 计算交易后账户额度
                BigDecimal amountAfterTransfer = account.getAccountAmount().add(transferAmount);
                if (amountAfterTransfer.compareTo(new BigDecimal("0")) == -1) {// 账户交易后额度小于0 说明扣减额度过多
                    // 返回账户额度不足
                    return new RpcResult(ErrorCodeJd.ACCOUNT_AMOUNT_IS_NOT_ENOUGH.getErrorCode(), ErrorCodeJd.ACCOUNT_AMOUNT_IS_NOT_ENOUGH.getMessage());
                }
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "单价折扣预留账户更新");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                int i = supplierUnitPriceDiscountReservedAccountDao.updateAccountAmount(supplierId, amountAfterTransfer, account.getDataVersion(), new Date(), appendTraceLog, prefix);
                if (i != 1) {
                    // 更新失败 抛出异常
                    throw new RuntimeException("update supplierUnitPriceDiscountReservedAccount failed");
                }
                try {
                    // 插入账户交易流水
                    flowNo = insertUnitPriceDiscountReservedAccountFlow(account.getCurrencyCode(), account.getAccountAmount(), transferAmount,
                            amountAfterTransfer, account.getSupplierId(), account.getSupplierName(), account.getProjectId(), account.getProjectName(), Long.valueOf(rpcHeader.getUid()),
                            rpcHeader.getUsername(), remark, prefix);
                } catch (Exception e) {
                    logger.error("insert unitPriceDiscountReservedAccountFlow failed");
                }
                break;
            }
            //如果更新失败，抛出未知系统异常
            if (maxRetryTimes <= 0) {
                logger.error("fail to update UnitPriceDiscountReservedAccount");
                throw new RuntimeException("FAILED to update UnitPriceDiscountReservedAccount");
            }
            logger.info("#traceId={}# [OUT]: update UnitPriceDiscountReservedAccount success. amount={} flowNo={}", rpcHeader.getTraceId(), transferAmount, flowNo);
            return new RpcResult(ErrorCodeJd.SUCCESS.getErrorCode(), ErrorCodeJd.SUCCESS.getMessage());
        }catch (Exception e){
            logger.error("update UnitPriceDiscountReservedAccount throw exception", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 更新采购预留冻结账户
     * @param projectId
     * @param prefix
     * @param rpcHeader
     * @param transferAmount
     * @param remark
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpcResult updateSupplierPurchaseReservedFrozenAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark ) {

        String flowNo = null;
        int maxRetryTimes = 6; // 账户额度变动基于乐观锁 最大重试次数6次
        try {
            while (maxRetryTimes-- > 0) {
                // 交易额度为负表示扣减 为正为增加
                SupplierPurchaseReservedFrozenAccount account = supplierPurchaseReservedFrozenAccountDao.selectByProjectId(projectId, prefix);
                // 计算交易后账户额度
                BigDecimal amountAfterTransfer = account.getFrozenAmount().add(transferAmount);
                if (amountAfterTransfer.compareTo(new BigDecimal("0")) == -1) {// 账户交易后额度小于0 说明是扣减且扣减额度过多
                    // 返回账户额度不足
                    return new RpcResult(ErrorCodeJd.ACCOUNT_AMOUNT_IS_NOT_ENOUGH.getErrorCode(), ErrorCodeJd.ACCOUNT_AMOUNT_IS_NOT_ENOUGH.getMessage());
                }
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "采购预留冻结账户更新");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                int i = supplierPurchaseReservedFrozenAccountDao.updateAccountAmount(supplierId, amountAfterTransfer, account.getDataVersion(), new Date(), appendTraceLog, prefix);
                if (i != 1) {
                    // 更新失败 抛出异常
                    throw new RuntimeException("update SupplierPurchaseReservedFrozenAccount failed");
                }
                try {
                    // 插入冻结账户交易流水
                    flowNo = insertPurchaseReservedFrozenAccountFlow(account.getCurrencyCode(), account.getFrozenAmount(), transferAmount,
                            amountAfterTransfer, account.getSupplierId(), account.getSupplierName(), account.getProjectId(), account.getProjectName(), Long.valueOf(rpcHeader.getUid()),
                            rpcHeader.getUsername(), remark, prefix);
                } catch (Exception e) {
                    logger.error("insert SupplierPurchaseReservedFrozenAccountFlow failed");
                }
                break;
            }
            //如果更新失败，抛出未知系统异常
            if (maxRetryTimes <= 0) {
                logger.error("fail to update SupplierPurchaseReservedFrozenAccount");
                throw new RuntimeException("FAILED to update SupplierPurchaseReservedFrozenAccount");
            }
            logger.info("#traceId={}# [OUT]: update SupplierPurchaseReservedFrozenAccount success. amount={} flowNo={}", rpcHeader.getTraceId(), transferAmount, flowNo);
            return new RpcResult(ErrorCodeJd.SUCCESS.getErrorCode(), ErrorCodeJd.SUCCESS.getMessage());

        }catch (Exception e){
            logger.error("update SupplierPurchaseReservedFrozenAccount throw exception", e.getMessage());
            throw  new RuntimeException(e.getMessage());
        }
    }

    /**
     * 更新采购预留账户
     * @param projectId
     * @param prefix
     * @param rpcHeader
     * @param transferAmount
     * @param remark
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpcResult updateSupplierPurchaseReservedAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark ) {

        String flowNo = null;
        int maxRetryTimes = 6; // 账户额度变动基于乐观锁 最大重试次数6次
        try {
            while (maxRetryTimes-- > 0) {
                // 交易额度为负表示扣减 为正为增加
                SupplierPurchaseReservedAccount account = supplierPurchaseReservedAccountDao.selectByProjectId(projectId, prefix);
                // 计算交易后账户额度
                BigDecimal amountAfterTransfer = account.getAccountAmount().add(transferAmount);
                if (amountAfterTransfer.compareTo(new BigDecimal("0")) == -1) {// 账户交易后额度小于0 说明是扣减且扣减额度过多
                    // 返回账户额度不足
                    return new RpcResult(ErrorCodeJd.ACCOUNT_AMOUNT_IS_NOT_ENOUGH.getErrorCode(), ErrorCodeJd.ACCOUNT_AMOUNT_IS_NOT_ENOUGH.getMessage());
                }
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "采购预留账户更新");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                int i = supplierPurchaseReservedAccountDao.updateAccountAmount(supplierId, amountAfterTransfer, account.getDataVersion(), new Date(), appendTraceLog, prefix);
                if (i != 1) {
                    // 更新失败 抛出异常
                    throw new RuntimeException("update SupplierPurchaseReservedFrozenAccount failed");
                }
                try {
                    // 插入账户交易流水
                    flowNo = insertPurchaseReservedAccountFlow(account.getCurrencyCode(), account.getAccountAmount(), transferAmount,
                            amountAfterTransfer, account.getSupplierId(), account.getSupplierName(), account.getProjectId(), account.getProjectName(), Long.valueOf(rpcHeader.getUid()),
                            rpcHeader.getUsername(), remark, prefix);
                } catch (Exception e) {
                    logger.error("insert SupplierPurchaseReservedAccountFlow failed");
                }
                break;
            }
            //如果更新失败，抛出未知系统异常
            if (maxRetryTimes <= 0) {
                logger.error("fail to update SupplierPurchaseReservedAccount");
                throw new RuntimeException("FAILED to update SupplierPurchaseReservedAccount");
            }
            logger.info("#traceId={}# [OUT]: update SupplierPurchaseReservedAccount success. amount={} flowNo={}", rpcHeader.getTraceId(), transferAmount, flowNo);
            return new RpcResult(ErrorCodeJd.SUCCESS.getErrorCode(), ErrorCodeJd.SUCCESS.getMessage());

        }catch (Exception e){
            logger.error("update SupplierPurchaseReservedAccount throw exception", e.getMessage());
            throw  new RuntimeException(e.getMessage());
        }
    }


    /**
     * 更新销售差价账户
     * @param projectId
     * @param prefix
     * @param rpcHeader
     * @param transferAmount
     * @param remark
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpcResult updateSalesDifferenceAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark ) {

        String flowNo = null;
        int maxRetryTimes = 6; // 账户额度变动基于乐观锁 最大重试次数6次
        try {
            while (maxRetryTimes-- > 0) {
                // 交易额度为负表示扣减 为正为增加
                SupplierSalesDifferenceReservedAccount account = supplierSalesDifferenceReservedAccountDao.selectByProjectId(projectId, prefix);

                // 计算交易后账户额度
                BigDecimal amountAfterTransfer = account.getAccountAmount().add(transferAmount);
                if (amountAfterTransfer.compareTo(new BigDecimal("0")) == -1) {// 账户交易后额度小于0 说明是扣减且扣减额度过多
                    // 返回账户额度不足
                    return new RpcResult(ErrorCodeJd.ACCOUNT_AMOUNT_IS_NOT_ENOUGH.getErrorCode(), ErrorCodeJd.ACCOUNT_AMOUNT_IS_NOT_ENOUGH.getMessage());
                }
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "修改销售差价账户");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                // TODO
                //int i = supplierPurchaseReservedAccountDao.updateAccountAmount(supplierId, amountAfterTransfer, account.getDataVersion(), new Date(), appendTraceLog, prefix);
                int i = supplierSalesDifferenceReservedAccountDao.updateAccountAmount(supplierId, amountAfterTransfer, account.getDataVersion(), new Date(), appendTraceLog, prefix);

                if (i != 1) {
                    // 更新失败 抛出异常
                    throw new RuntimeException("update SupplierPurchaseReservedFrozenAccount failed");
                }
                try {
                    // 插入账户交易流水
                    flowNo = insertSalesDifferenceReservedAccountFlow(account.getCurrencyCode(), account.getAccountAmount(), transferAmount,
                            amountAfterTransfer, account.getSupplierId(), account.getSupplierName(), account.getProjectId(), account.getProjectName(), Long.valueOf(rpcHeader.getUid()),
                            rpcHeader.getUsername(), remark, prefix);
                } catch (Exception e) {
                    logger.error("insert SupplierPurchaseReservedAccountFlow failed");
                }
                break;
            }
            //如果更新失败，抛出未知系统异常
            if (maxRetryTimes <= 0) {
                logger.error("fail to update SupplierPurchaseReservedAccount");
                throw new RuntimeException("FAILED to update SupplierPurchaseReservedAccount");
            }
            logger.info("#traceId={}# [OUT]: update SupplierPurchaseReservedAccount success. amount={} flowNo={}", rpcHeader.getTraceId(), transferAmount, flowNo);
            return new RpcResult(ErrorCodeJd.SUCCESS.getErrorCode(), ErrorCodeJd.SUCCESS.getMessage());

        }catch (Exception e){
            logger.error("update SupplierPurchaseReservedAccount throw exception", e.getMessage());
            throw  new RuntimeException(e.getMessage());
        }
    }

//    /**
//     * 更新返利实收账户
//     * @param projectId
//     * @param prefix
//     * @param rpcHeader
//     * @param transferAmount
//     * @param remark
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public RpcResult updateYhglobalReceivedCouponAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark ) {
//
//        String flowNo = null;
//        int maxRetryTimes = 6; // 账户额度变动基于乐观锁 最大重试次数6次
//        try {
//            while (maxRetryTimes-- > 0) {
//                //TODO 交易额度为负表示扣减 为正为增加
//                SupplierPurchaseReservedAccount account = supplierPurchaseReservedAccountDao.selectByProjectId(projectId, prefix);
//
//                // 计算交易后账户额度
//                BigDecimal amountAfterTransfer = account.getAccountAmount().add(transferAmount);
//                if (amountAfterTransfer.compareTo(new BigDecimal("0")) == -1) {// 账户交易后额度小于0 说明是扣减且扣减额度过多
//                    // 返回账户额度不足
//                    return new RpcResult(ErrorCodeJd.ACCOUNT_AMOUNT_IS_NOT_ENOUGH.getErrorCode(), ErrorCodeJd.ACCOUNT_AMOUNT_IS_NOT_ENOUGH.getMessage());
//                }
//                //添加操作日志
//                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "返利实收账户更新");
//                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
//                //TODO 流水
//                int i = supplierPurchaseReservedAccountDao.updateAccountAmount(supplierId, amountAfterTransfer, account.getDataVersion(), new Date(), appendTraceLog, prefix);
//                if (i != 1) {
//                    // 更新失败 抛出异常
//                    throw new RuntimeException("update SupplierPurchaseReservedFrozenAccount failed");
//                }
//                try {
//                    // 插入账户交易流水
//                    flowNo = insertPurchaseReservedAccountFlow(account.getCurrencyCode(), account.getAccountAmount(), transferAmount,
//                            amountAfterTransfer, account.getSupplierId(), account.getSupplierName(), account.getProjectId(), account.getProjectName(), Long.valueOf(rpcHeader.getUid()),
//                            rpcHeader.getUsername(), remark, prefix);
//                } catch (Exception e) {
//                    logger.error("insert SupplierPurchaseReservedAccountFlow failed");
//                }
//                break;
//            }
//            //如果更新失败，抛出未知系统异常
//            if (maxRetryTimes <= 0) {
//                logger.error("fail to update SupplierPurchaseReservedAccount");
//                throw new RuntimeException("FAILED to update SupplierPurchaseReservedAccount");
//            }
//            logger.info("#traceId={}# [OUT]: update SupplierPurchaseReservedAccount success. amount={} flowNo={}", rpcHeader.getTraceId(), transferAmount, flowNo);
//            return new RpcResult(ErrorCodeJd.SUCCESS.getErrorCode(), ErrorCodeJd.SUCCESS.getMessage());
//
//        }catch (Exception e){
//            logger.error("update SupplierPurchaseReservedAccount throw exception", e.getMessage());
//            throw  new RuntimeException(e.getMessage());
//        }
//    }
//
//    /**
//     * 更新代垫实收账户
//     * @param projectId
//     * @param prefix
//     * @param rpcHeader
//     * @param transferAmount
//     * @param remark
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public RpcResult updateYhglobalReceivedPrepaidAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark ) {
//
//        String flowNo = null;
//        int maxRetryTimes = 6; // 账户额度变动基于乐观锁 最大重试次数6次
//        try {
//            while (maxRetryTimes-- > 0) {
//                //TODO 交易额度为负表示扣减 为正为增加
//                // SupplierPurchaseReservedAccount account = supplierPurchaseReservedAccountDao.selectByProjectId(projectId, prefix);
//                SupplierPurchaseReservedAccount account = null;
//                // 计算交易后账户额度
//                BigDecimal amountAfterTransfer = account.getAccountAmount().add(transferAmount);
//                if (amountAfterTransfer.compareTo(new BigDecimal("0")) == -1) {// 账户交易后额度小于0 说明是扣减且扣减额度过多
//                    // 返回账户额度不足
//                    return new RpcResult(ErrorCodeJd.ACCOUNT_AMOUNT_IS_NOT_ENOUGH.getErrorCode(), ErrorCodeJd.ACCOUNT_AMOUNT_IS_NOT_ENOUGH.getMessage());
//                }
//                //添加操作日志
//                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "代垫实收账户更新");
//                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
//                //TODO  流水
//                int i = supplierPurchaseReservedAccountDao.updateAccountAmount(supplierId, amountAfterTransfer, account.getDataVersion(), new Date(), appendTraceLog, prefix);
//                if (i != 1) {
//                    // 更新失败 抛出异常
//                    throw new RuntimeException("update SupplierPurchaseReservedFrozenAccount failed");
//                }
//                try {
//                    // 插入账户交易流水
//                    flowNo = insertPurchaseReservedAccountFlow(account.getCurrencyCode(), account.getAccountAmount(), transferAmount,
//                            amountAfterTransfer, account.getSupplierId(), account.getSupplierName(), account.getProjectId(), account.getProjectName(), Long.valueOf(rpcHeader.getUid()),
//                            rpcHeader.getUsername(), remark, prefix);
//                } catch (Exception e) {
//                    logger.error("insert SupplierPurchaseReservedAccountFlow failed");
//                }
//                break;
//            }
//            //如果更新失败，抛出未知系统异常
//            if (maxRetryTimes <= 0) {
//                logger.error("fail to update SupplierPurchaseReservedAccount");
//                throw new RuntimeException("FAILED to update SupplierPurchaseReservedAccount");
//            }
//            logger.info("#traceId={}# [OUT]: update SupplierPurchaseReservedAccount success. amount={} flowNo={}", rpcHeader.getTraceId(), transferAmount, flowNo);
//            return new RpcResult(ErrorCodeJd.SUCCESS.getErrorCode(), ErrorCodeJd.SUCCESS.getMessage());
//
//        }catch (Exception e){
//            logger.error("update SupplierPurchaseReservedAccount throw exception", e.getMessage());
//            throw  new RuntimeException(e.getMessage());
//        }
//    }

    private String insertUnitPriceDiscountFrozenAccountFlow(String currencyCode, BigDecimal amountBeforeTransaction, BigDecimal transactionAmount,
                                                          BigDecimal amountAfterTransaction,  Long supplierId, String supplierName,
                                                          Long projectId, String projectName,Long createdById, String createdByName, String remark, String tablePrefix){
        SupplierUnitPriceDiscountFrozenAccountTransferFlow flow = new SupplierUnitPriceDiscountFrozenAccountTransferFlow();
//  `flowType` int(5) unsigned DEFAULT '0' COMMENT '流水类型 305:支出 306:转入 ',
        String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
        String flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT_TRANSFER_FLOW);
        flow.setFlowNo(flowNo);
        flow.setRemark(remark);
        flow.setSupplierId(supplierId);
        flow.setSupplierName(supplierName);
        flow.setProjectId(projectId);
        flow.setProjectName(projectName);
        flow.setCreatedById(createdById);
        flow.setCreatedByName(createdByName);
        flow.setCurrencyCode(currencyCode);
        flow.setAmountBeforeTransaction(amountBeforeTransaction);
        flow.setTransactionAmount(transactionAmount);
        flow.setAmountAfterTransaction(amountAfterTransaction);
        flow.setTransferTime(new Date());
        flow.setTablePrefix(tablePrefix);
        if (transactionAmount.compareTo(new BigDecimal("0")) == -1){ // 转出
            flow.setFlowType(FlowTypeEnum.OUT.getType());
        }else {
            flow.setFlowType(FlowTypeEnum.IN.getType()); // 转入
        }
        int i = supplierUnitPriceDiscountFrozenAccountTransferFlowDao.insert(flow);
        if (i != 1){
            throw new RuntimeException("insert supplierUnitPriceDiscountFrozenAccountTransferFlow failed");
        }
        return flowNo;
    }

    private String insertUnitPriceDiscountReservedAccountFlow(String currencyCode, BigDecimal amountBeforeTransaction, BigDecimal transactionAmount,
                                                            BigDecimal amountAfterTransaction,  Long supplierId, String supplierName,
                                                            Long projectId, String projectName,Long createdById, String createdByName, String remark, String tablePrefix){
        SupplierUnitPriceDiscountReservedAccountTransferFlow flow = new SupplierUnitPriceDiscountReservedAccountTransferFlow();
//  `flowType` int(5) unsigned DEFAULT '0' COMMENT '流水类型 305:支出 306:转入 ',
        String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
        String flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT_TRANSFER_FLOW);
        flow.setFlowNo(flowNo);
        flow.setRemark(remark);
        flow.setSupplierId(supplierId);
        flow.setSupplierName(supplierName);
        flow.setProjectId(projectId);
        flow.setProjectName(projectName);
        flow.setCreatedById(createdById);
        flow.setCreatedByName(createdByName);
        flow.setCurrencyCode(currencyCode);
        flow.setAmountBeforeTransaction(amountBeforeTransaction);
        flow.setTransactionAmount(transactionAmount);
        flow.setAmountAfterTransaction(amountAfterTransaction);
        flow.setTransferTime(new Date());
        flow.setTablePrefix(tablePrefix);
        if (transactionAmount.compareTo(new BigDecimal("0")) == -1){ // 转出
            flow.setFlowType(FlowTypeEnum.OUT.getType());
        }else {
            flow.setFlowType(FlowTypeEnum.IN.getType()); // 转入
        }
        int i = supplierUnitPriceDiscountReservedAccountFlowDao.insert(flow);
        if (i != 1){
            throw new RuntimeException("insert supplierUnitPriceDiscountReservedAccountTransferFlow failed");
        }
        return flowNo;
    }
    private String insertPurchaseReservedFrozenAccountFlow(String currencyCode, BigDecimal amountBeforeTransaction, BigDecimal transactionAmount,
                                                         BigDecimal amountAfterTransaction,  Long supplierId, String supplierName,
                                                         Long projectId, String projectName,Long createdById, String createdByName, String remark, String tablePrefix){
        SupplierPurchaseReservedFrozenAccountTransferFlow flow = new SupplierPurchaseReservedFrozenAccountTransferFlow();
//  `flowType` int(5) unsigned DEFAULT '0' COMMENT '流水类型 305:支出 306:转入 ',
        String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
        String flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT_TRANSFER_FLOW);
        flow.setFlowNo(flowNo);
        flow.setRemark(remark);
        flow.setSupplierId(supplierId);
        flow.setSupplierName(supplierName);
        flow.setProjectId(projectId);
        flow.setProjectName(projectName);
        flow.setCreatedById(createdById);
        flow.setCreatedByName(createdByName);
        flow.setCurrencyCode(currencyCode);
        flow.setAmountBeforeTransaction(amountBeforeTransaction);
        flow.setTransactionAmount(transactionAmount);
        flow.setAmountAfterTransaction(amountAfterTransaction);
        flow.setTransferTime(new Date());
        flow.setTablePrefix(tablePrefix);
        if (transactionAmount.compareTo(new BigDecimal("0")) == -1){ // 转出
            flow.setFlowType(FlowTypeEnum.OUT.getType());
        }else {
            flow.setFlowType(FlowTypeEnum.IN.getType()); // 转入
        }
        int i = supplierPurchaseReservedFrozenAccountTransferFlowDao.insert(flow);
        if (i != 1){
            throw new RuntimeException("insert supplierPurchaseReservedFrozenAccountFlow failed");
        }
        return flowNo;
    }
    private String insertPurchaseReservedAccountFlow(String currencyCode, BigDecimal amountBeforeTransaction, BigDecimal transactionAmount,
                                                           BigDecimal amountAfterTransaction,  Long supplierId, String supplierName,
                                                           Long projectId, String projectName,Long createdById, String createdByName, String remark, String tablePrefix){
        SupplierPurchaseReservedAccountTransferFlow flow = new SupplierPurchaseReservedAccountTransferFlow();
//  `flowType` int(5) unsigned DEFAULT '0' COMMENT '流水类型 305:支出 306:转入 ',
        String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
        String flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT_TRANSFER_FLOW);
        flow.setFlowNo(flowNo);
        flow.setRemark(remark);
        flow.setSupplierId(supplierId);
        flow.setSupplierName(supplierName);
        flow.setProjectId(projectId);
        flow.setProjectName(projectName);
        flow.setCreatedById(createdById);
        flow.setCreatedByName(createdByName);
        flow.setCurrencyCode(currencyCode);
        flow.setAmountBeforeTransaction(amountBeforeTransaction);
        flow.setTransactionAmount(transactionAmount);
        flow.setAmountAfterTransaction(amountAfterTransaction);
        flow.setTransferTime(new Date());
        flow.setTablePrefix(tablePrefix);
        if (transactionAmount.compareTo(new BigDecimal("0")) == -1){ // 转出
            flow.setFlowType(FlowTypeEnum.OUT.getType());
        }else {
            flow.setFlowType(FlowTypeEnum.IN.getType()); // 转入
        }
        int i = supplierPurchaseReservedAccountFlowDao.insert(flow);
        if (i != 1){
            throw new RuntimeException("insert supplierPurchaseReservedFrozenAccountFlow failed");
        }
        return flowNo;
    }


    private String insertSalesDifferenceReservedAccountFlow(String currencyCode, BigDecimal amountBeforeTransaction, BigDecimal transactionAmount,
                                                     BigDecimal amountAfterTransaction,  Long supplierId, String supplierName,
                                                     Long projectId, String projectName,Long createdById, String createdByName, String remark, String tablePrefix){
        SupplierSalesDifferenceReservedAccountTransferFlow flow = new SupplierSalesDifferenceReservedAccountTransferFlow();
//  `flowType` int(5) unsigned DEFAULT '0' COMMENT '流水类型 305:支出 306:转入 ',
        String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
        String flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT_TRANSFER_FLOW);
        flow.setFlowNo(flowNo);
        flow.setRemark(remark);
        flow.setSupplierId(supplierId);
        flow.setSupplierName(supplierName);
        flow.setProjectId(projectId);
        flow.setProjectName(projectName);
        flow.setCreatedById(createdById);
        flow.setCreatedByName(createdByName);
        flow.setCurrencyCode(currencyCode);
        flow.setAmountBeforeTransaction(amountBeforeTransaction);
        flow.setTransactionAmount(transactionAmount);
        flow.setAmountAfterTransaction(amountAfterTransaction);
        flow.setTransferTime(new Date());
        flow.setTablePrefix(tablePrefix);
        if (transactionAmount.compareTo(new BigDecimal("0")) == -1){ // 转出
            flow.setFlowType(FlowTypeEnum.OUT.getType());
        }else {
            flow.setFlowType(FlowTypeEnum.IN.getType()); // 转入
        }
        int i = supplierSalesDifferenceReservedAccountFlowDao.insert(flow);
        if (i != 1){
            throw new RuntimeException("insert supplierPurchaseReservedFrozenAccountFlow failed");
        }
        return flowNo;
    }
}
