package com.yhglobal.gongxiao.payment.jdservice;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCodeJd;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.payment.AccountManageDao.*;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.constant.AccountTypeConstant;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure;
import com.yhglobal.gongxiao.payment.model.*;
import com.yhglobal.gongxiao.payment.project.model.FrontPageFlow;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Service
public class SupplierFrozenAccountServiceImpl extends SupplierFrozenAccountServiceGrpc.SupplierFrozenAccountServiceImplBase{
    //extends SupplierFrozenAccountServiceGrpc.SupplierFrozenAccountServiceImplBase

    private static final Logger logger = LoggerFactory.getLogger(SupplierFrozenAccountServiceImpl.class);

    @Autowired
    SupplierPurchaseReservedFrozenAccountDao supplierPurchaseReservedFrozenAccountDao;

    @Autowired
    SupplierUnitPriceDiscountFrozenAccountDao supplierUnitPriceDiscountFrozenAccountDao;

    @Autowired
    SupplierUnitPriceDiscountFrozenAccountTransferFlowDao supplierUnitPriceDiscountFrozenAccountTransferFlowDao;

    @Autowired
    SupplierPurchaseReservedFrozenAccountTransferFlowDao supplierPurchaseReservedFrozenAccountTransferFlowDao;

    @Autowired
    UpdateAccountService updateAccountService;
    /**
     * 查询冻结账户余额
     */
    @Override
    public void getFrozenAccountAmount(SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountReq req,
                                       StreamObserver<SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountResp> responseObserver){
        try {
            Long projectId = req.getProjectId();
            GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
            logger.info("start get FrozenAccountAmount project :", projectId);
            BigDecimal zero = new BigDecimal("0");
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            SupplierPurchaseReservedFrozenAccount supplierPurchaseReservedFrozenAccount = supplierPurchaseReservedFrozenAccountDao.selectByProjectId(projectId, prefix);
            SupplierUnitPriceDiscountFrozenAccount supplierUnitPriceDiscountFrozenAccount = supplierUnitPriceDiscountFrozenAccountDao.selectByProjectId(projectId, prefix);
            BigDecimal amount1 = supplierPurchaseReservedFrozenAccount==null?zero:supplierPurchaseReservedFrozenAccount.getFrozenAmount();
            BigDecimal amount2 = supplierUnitPriceDiscountFrozenAccount==null?zero:supplierUnitPriceDiscountFrozenAccount.getFrozenAmount();
            SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountResp.Builder respBuilder = SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountResp.newBuilder();
            respBuilder.setDiscountAccount(amount2.doubleValue());
            respBuilder.setReservedAccount(amount1.doubleValue());
            respBuilder.setReturnCode(ErrorCodeJd.SUCCESS.getErrorCode());
            respBuilder.setMsg(ErrorCodeJd.SUCCESS.getMessage());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("getFrozenAccountAmount throw exception", e.getMessage());
            throw new RuntimeException("SupplierFrozenAccountServiceImpl throw exception"+e.getMessage());
        }
    }

    /**
     * 分页查询流水
     */
    @Override
    public void getFrozenAccountFlow(SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowReq req, StreamObserver<SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowResp> responseObserver){
        try {
            Integer moneyFlow = StringUtils.isBlank(req.getFlowType()) ? null : Integer.valueOf(req.getFlowType());
            Integer accountType = StringUtils.isBlank(req.getAccountType()) ? null : Integer.valueOf(req.getAccountType());
            Date dateS = req.getDateS() != -1 ? new Date(req.getDateS()) : null;
            Date dateE = req.getDateE() != -1 ? new Date(req.getDateE()) : null;
            Long projectId = req.getProjectId();
            Integer pageNum = req.getPageNum();
            Integer pageSize = req.getPageSize();
            logger.info("getFrozenAccountFlow params :moneyFlow, accountType, dateE, dateS, projectId", moneyFlow, accountType, dateE, dateS, projectId);
            GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowResp.Builder respBuilder = SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowResp.newBuilder();
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<FrontPageFlow> frontPageFlowPageInfo = null;
            // 获取账户类型常量
            AccountTypeConstant accountTypeConstant = AccountTypeConstant.getAccountTypeConstantByCode(accountType);
            switch (accountTypeConstant) {
                case SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT:
                    // 条件查询单价折扣冻结账户流水 从记录表中查询然后封装成前台对象
                    List<SupplierUnitPriceDiscountFrozenAccountTransferFlow> list = supplierUnitPriceDiscountFrozenAccountTransferFlowDao.selectByConditions(moneyFlow, dateS, dateE, prefix);
                    for (SupplierUnitPriceDiscountFrozenAccountTransferFlow flow : list) {
                        PaymentCommonGrpc.FrontPageFlow.Builder flowBuilder = PaymentCommonGrpc.FrontPageFlow.newBuilder();
                        flowBuilder.setFlowNo(flow.getPurchaseOrderNo()==null?"":flow.getPurchaseOrderNo())
                                .setTransactionAmount(flow.getTransactionAmount().doubleValue())
                                .setCurrencyCode(flow.getCurrencyCode()==null?"":flow.getCurrencyCode())
                                .setAmountAfterTransaction(flow.getAmountAfterTransaction().doubleValue())
                                .setRemark(flow.getRemark() == null ? "" : flow.getRemark())
                                .setCreateTime(flow.getTransferTime().getTime()).setType(flow.getFlowType()).setCreateByName(flow.getCreatedByName());
                        respBuilder.addFlowList(flowBuilder);
                    }
                    // 用分页包装
                    frontPageFlowPageInfo = new PageInfo(list);
                    break;
                case SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT:
                    // 条件查询采购预留冻结账户流水
                    List<SupplierPurchaseReservedFrozenAccountTransferFlow> list2 = supplierPurchaseReservedFrozenAccountTransferFlowDao.selectByConditions(moneyFlow, dateS, dateE, prefix);
                    for (SupplierPurchaseReservedFrozenAccountTransferFlow flow : list2) {
                        PaymentCommonGrpc.FrontPageFlow.Builder flowBuilder = PaymentCommonGrpc.FrontPageFlow.newBuilder();
                        flowBuilder.setFlowNo(flow.getPurchaseOrderNo()==null?"":flow.getPurchaseOrderNo())
                                .setTransactionAmount(flow.getTransactionAmount().doubleValue())
                                .setCurrencyCode(flow.getCurrencyCode()==null?"":flow.getCurrencyCode())
                                .setAmountAfterTransaction(flow.getAmountAfterTransaction().doubleValue())
                                .setRemark(flow.getRemark() == null ? "" : flow.getRemark())
                                .setCreateTime(flow.getTransferTime().getTime()).setType(flow.getFlowType()).setCreateByName(flow.getCreatedByName());
                        respBuilder.addFlowList(flowBuilder);
                    }
                    // 用分页包装
                    frontPageFlowPageInfo = new PageInfo(list2);
                    break;
                default:
                    throw new IllegalArgumentException("unknown AccountType: " + accountTypeConstant.getAccountName());
            }
            respBuilder.setTotal(frontPageFlowPageInfo.getTotal());
            respBuilder.setPages(frontPageFlowPageInfo.getPages());
            respBuilder.setPageNum(pageNum);
            respBuilder.setPageSize(pageSize);

            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("getFrozenAccountFlow throw exception",e.getMessage());
            throw new RuntimeException("getFrozenAccountFlow throw exception");
        }
    }
    /**
     * 账户额度转出
     */
    @Override
    public void accountTransferOut(SupplierFrozenAccountServiceStructure.AccountTransferOutReq req,
                                   StreamObserver<SupplierFrozenAccountServiceStructure.AccountTransferOutResp> responseObserver){
        try {
            Long projectId = req.getProjectId();
            Long supplierId = 1L;
            Integer accountType = StringUtils.isBlank(req.getAccountType()) ? null : Integer.valueOf(req.getAccountType());
            BigDecimal transferAmount = new BigDecimal(req.getTransferOutAmount() + "");
            GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            AccountTypeConstant accountTypeConstant = AccountTypeConstant.getAccountTypeConstantByCode(accountType);
            String remark = req.getRemark();
            SupplierFrozenAccountServiceStructure.AccountTransferOutResp.Builder respBuilder = SupplierFrozenAccountServiceStructure.AccountTransferOutResp.newBuilder();
            SupplierFrozenAccountServiceStructure.AccountTransferOutResp resp ;
            // 冻结账户转出逻辑  冻结账户转出 对应预留账户会增加对应额度
            switch (accountTypeConstant) {
                case SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT:
                    // 更新单价折扣冻结账户  扣减
                    RpcResult rpcResult = updateAccountService.updateSupplierUnitPriceDiscountFrozenAccount(supplierId, projectId, prefix, rpcHeader, transferAmount.multiply(new BigDecimal("-1")), remark);
                    if (rpcResult.getCode() != 0) {
                        // 账户额度不足
                        respBuilder.setReturnCode(rpcResult.getCode()).setMsg(rpcResult.getMessage());
                    }else {
                        // 更新单价折扣预留账户  增加
                        RpcResult rpcResult1 = updateAccountService.updateSupplierUnitPriceDiscountReservedAccount(supplierId, projectId, prefix, rpcHeader, transferAmount, remark);
                        respBuilder.setReturnCode(rpcResult1.getCode()).setMsg(rpcResult1.getMessage());
                    }
                    break;
                case SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT: // 采购预留冻结账户
                    // 更新采购预留冻结账户 扣减
                    RpcResult rpcResult1 = updateAccountService.updateSupplierPurchaseReservedFrozenAccount(supplierId, projectId, prefix, rpcHeader, transferAmount.multiply(new BigDecimal("-1")), remark);
                    if (rpcResult1.getCode() != 0) {
                        // 账户额度不足
                        respBuilder.setReturnCode(rpcResult1.getCode()).setMsg(rpcResult1.getMessage());
                    }else {
                         // 更新采购预留账户 增加
                        RpcResult rpcResult2 = updateAccountService.updateSupplierPurchaseReservedAccount(supplierId, projectId, prefix, rpcHeader, transferAmount, remark);
                        respBuilder.setReturnCode(rpcResult2.getCode()).setMsg(rpcResult2.getMessage());
                    }
                    break;
                default:
                    throw new IllegalArgumentException("unknown AccountType: " + accountTypeConstant.getAccountName());
            }
            resp = respBuilder.build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("FrozenAccountTransferOut throw exception",e.getMessage());
            throw new RuntimeException("FrozenAccountTransferOut throw exception");
        }

    }


    /**
     * 统计流水金额
     */
    @Override
    public void getFrozenAccountSubtotal(SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalReq req,
                            StreamObserver<SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp> responseObserver){

        try {
            Integer moneyFlow = req.getFlowType()!=""?Integer.parseInt(req.getFlowType()):null;
            Integer accountType = Integer.parseInt(req.getAccountType());
            Date dateS = req.getDateS() != -1 ? new Date(req.getDateS()) : null;
            Date dateE = req.getDateE() != -1 ? new Date(req.getDateE()) : null;
            long projectId = req.getProjectId();
            logger.info("getFrozenAccountSubtotal params: moneyFlow, accountType, dateE, dateS, projectId", moneyFlow, accountType, dateE, dateS, projectId);
            GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            AccountTypeConstant accountTypeConstant = AccountTypeConstant.getAccountTypeConstantByCode(accountType);
            SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp.Builder respBuilder = SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp.newBuilder();
            SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp resp;

            List<FlowSubtotal> list;
            // 冻结账户流水统计
            switch (accountTypeConstant) {
                case SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT:
                    list = supplierUnitPriceDiscountFrozenAccountTransferFlowDao.selectTotal(moneyFlow, dateS, dateE, tablePrefix);
                    break;
                case SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT: // 采购预留冻结账户
                    list = supplierPurchaseReservedFrozenAccountTransferFlowDao.selectTotal(moneyFlow, dateS, dateE, tablePrefix);
                    break;
                default:
                    throw new IllegalArgumentException("unknown AccountType: " + accountTypeConstant.getAccountName());
            }
            FrontPageFlowSubtotal subtotal = listToSubtotal(list);
            // 把统计结果塞到grpc的resp中
            PaymentCommonGrpc.FrontPageFlowSubtotal.Builder subtotal1 = PaymentCommonGrpc.FrontPageFlowSubtotal.newBuilder();
            subtotal1.setExpenditureAmount(subtotal.getExpenditureAmount().doubleValue());
            subtotal1.setExpenditureQuantity(subtotal.getExpenditureQuantity());
            subtotal1.setIncomeAmount(subtotal.getIncomeAmount().doubleValue());
            subtotal1.setIncomeQuantity(subtotal.getIncomeQuantity());
            respBuilder.setSubtotal(subtotal1);

            resp = respBuilder.build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("getFrozenAccountSubtotal throw exception");
            throw new RuntimeException("getFrozenAccountSubtotal throw exception"+e.getMessage(),e);
        }
    }


    // 把sql语句查出的数据封装
    public FrontPageFlowSubtotal listToSubtotal(List<FlowSubtotal> list){
        FrontPageFlowSubtotal subtotal = new FrontPageFlowSubtotal();
        for (FlowSubtotal flow:list){
            if (flow.getFlowType() == FlowTypeEnum.OUT.getType()){
                // 转出流水的数据
                subtotal.setExpenditureAmount(flow.getAmountCount());
                subtotal.setExpenditureQuantity(flow.getCount());
            }else if (flow.getFlowType() == FlowTypeEnum.IN.getType()){
                // 转入流水的数据
                subtotal.setIncomeQuantity(flow.getCount());
                subtotal.setIncomeAmount(flow.getAmountCount());
            }
        }
        return subtotal;
    }

}
