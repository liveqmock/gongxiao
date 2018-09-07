package com.yhglobal.gongxiao.payment.jdservice;

import com.yhglobal.gongxiao.constant.ErrorCodeJd;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.AccountManageDao.SupplierSalesDifferenceReservedAccountDao;
import com.yhglobal.gongxiao.payment.AccountManageDao.SupplierSalesDifferenceReservedAccountFlowDao;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.constant.AccountTypeConstant;
import com.yhglobal.gongxiao.payment.AccountManageDao.SupplierPurchaseReservedAccountDao;
import com.yhglobal.gongxiao.payment.AccountManageDao.SupplierPurchaseReservedAccountFlowDao;
import com.yhglobal.gongxiao.payment.AccountManageDao.SupplierUnitPriceDiscountReservedAccountDao;
import com.yhglobal.gongxiao.payment.AccountManageDao.SupplierUnitPriceDiscountReservedAccountFlowDao;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure;
import com.yhglobal.gongxiao.payment.model.*;
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
public class SupplierReservedAccountServiceImpl extends SupplierReservedAccountServiceGrpc.SupplierReservedAccountServiceImplBase{

    @Autowired
    SupplierUnitPriceDiscountReservedAccountDao supplierUnitPriceDiscountReservedAccountDao;

    @Autowired
    SupplierPurchaseReservedAccountDao supplierPurchaseReservedAccountDao;

    @Autowired
    SupplierSalesDifferenceReservedAccountDao supplierSalesDifferenceReservedAccountDao;

    @Autowired
    SupplierUnitPriceDiscountReservedAccountFlowDao supplierUnitPriceDiscountReservedAccountFlowDao;
    @Autowired
    SupplierPurchaseReservedAccountFlowDao supplierPurchaseReservedAccountFlowDao;
    @Autowired
    SupplierSalesDifferenceReservedAccountFlowDao supplierSalesDifferenceReservedAccountFlowDao;

    private static final Logger logger = LoggerFactory.getLogger(SupplierReservedAccountServiceImpl.class);
    // 查询预留账户的余额
    @Override
    public void getReservedAccountAmount (SupplierReservedAccountServiceStructure.GetReservedAccountAmountReq req,
                                          StreamObserver<SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp> responseObserver) {
        try {
            Long projectId = req.getProjectId();
            GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
            logger.info("start getReservedAccountAmount project :", projectId);
            BigDecimal zero = new BigDecimal("0");
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            SupplierUnitPriceDiscountReservedAccount supplierUnitPriceDiscountReservedAccount = supplierUnitPriceDiscountReservedAccountDao.selectByProjectId(projectId, prefix);
            SupplierPurchaseReservedAccount supplierPurchaseReservedAccount = supplierPurchaseReservedAccountDao.selectByProjectId(projectId, prefix);
            SupplierSalesDifferenceReservedAccount supplierSalesDifferenceReservedAccount = supplierSalesDifferenceReservedAccountDao.selectByProjectId(projectId, prefix);
            BigDecimal amount1 = supplierUnitPriceDiscountReservedAccount==null?zero:supplierUnitPriceDiscountReservedAccount.getAccountAmount();
            BigDecimal amount2 = supplierPurchaseReservedAccount==null?zero:supplierPurchaseReservedAccount.getAccountAmount();
            BigDecimal amount3 = supplierSalesDifferenceReservedAccount==null?zero:supplierSalesDifferenceReservedAccount.getAccountAmount();

            SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp.Builder respBuilder = SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp.newBuilder();
            respBuilder.setDiscountAccount(amount1.doubleValue());
            respBuilder.setReservedAccount(amount2.doubleValue());
            respBuilder.setSalesDifference(amount3.doubleValue());

            respBuilder.setReturnCode(ErrorCodeJd.SUCCESS.getErrorCode());
            respBuilder.setMsg(ErrorCodeJd.SUCCESS.getMessage());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("getReservedAccountAmount throw exception", e.getMessage());
            throw new RuntimeException("SupplierReservedAccountServiceImpl throw exception"+e.getMessage());
        }
    }
    // 分页条件查询流水
    @Override
    public void getReservedAccountFlow (SupplierReservedAccountServiceStructure.GetReservedAccountFlowReq req,
                                        StreamObserver<SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp> responseObserver)  {
        try {
            Integer moneyFlow = StringUtils.isBlank(req.getFlowType()) ? null : Integer.valueOf(req.getFlowType());
            Integer accountType = StringUtils.isBlank(req.getAccountType()) ? null : Integer.valueOf(req.getAccountType());
            Date dateS = req.getDateS() != -1 ? new Date(req.getDateS()) : null;
            Date dateE = req.getDateE() != -1 ? new Date(req.getDateE()) : null;
            Long projectId = req.getProjectId();
            logger.info("getFrozenAccountFlow params :moneyFlow, accountType, dateE, dateS, projectId", moneyFlow, accountType, dateE, dateS, projectId);
            GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp.Builder respBuilder = SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp.newBuilder();

            // 获取账户类型常量
            AccountTypeConstant accountTypeConstant = AccountTypeConstant.getAccountTypeConstantByCode(accountType);
            switch (accountTypeConstant) {
                case SUPPLIER_UNIT_PRICE_DISCOUNT_RESERVED_ACCOUNT:
                    // 条件查询单价折扣账户流水 从记录表中查询然后封装成前台对象
                    List<SupplierUnitPriceDiscountReservedAccountTransferFlow> list = supplierUnitPriceDiscountReservedAccountFlowDao.selectByConditions(moneyFlow, dateS, dateE, prefix);
                    for (SupplierUnitPriceDiscountReservedAccountTransferFlow flow : list) {
                        PaymentCommonGrpc.FrontPageFlow.Builder flowBuilder = PaymentCommonGrpc.FrontPageFlow.newBuilder();
                        flowBuilder.setFlowNo(flow.getPurchaseOrderNo()==null?"":flow.getPurchaseOrderNo())
                                .setTransactionAmount(flow.getTransactionAmount().doubleValue())
                                .setCurrencyCode(flow.getCurrencyCode()==null?"":flow.getCurrencyCode())
                                .setAmountAfterTransaction(flow.getAmountAfterTransaction().doubleValue())
                                .setRemark(flow.getRemark() == null ? "" : flow.getRemark())
                                .setCreateTime(flow.getTransferTime().getTime()).setType(flow.getFlowType()).setCreateByName(flow.getCreatedByName());
                        respBuilder.addFlowList(flowBuilder);
                    }
                    break;
                case SUPPLIER_PURCHASE_RESERVED_ACCOUNT:
                    // 条件查询采购预留账户流水
                    List<SupplierPurchaseReservedAccountTransferFlow> list2 = supplierPurchaseReservedAccountFlowDao.selectByConditions(moneyFlow, dateS, dateE, prefix);
                    for (SupplierPurchaseReservedAccountTransferFlow flow : list2) {
                        PaymentCommonGrpc.FrontPageFlow.Builder flowBuilder = PaymentCommonGrpc.FrontPageFlow.newBuilder();
                        flowBuilder.setFlowNo(flow.getPurchaseOrderNo()==null?"":flow.getPurchaseOrderNo())
                                .setTransactionAmount(flow.getTransactionAmount().doubleValue())
                                .setCurrencyCode(flow.getCurrencyCode()==null?"":flow.getCurrencyCode())
                                .setAmountAfterTransaction(flow.getAmountAfterTransaction().doubleValue())
                                .setRemark(flow.getRemark() == null ? "" : flow.getRemark())
                                .setCreateTime(flow.getTransferTime().getTime()).setType(flow.getFlowType()).setCreateByName(flow.getCreatedByName());
                        respBuilder.addFlowList(flowBuilder);
                    }
                    break;
                case SUPPLIER_SALES_DIFFERENCE_RESERVED_ACCOUNT:
                    // 条件查询销售差价账户流水
                    List<SupplierSalesDifferenceReservedAccountTransferFlow> list3 = supplierSalesDifferenceReservedAccountFlowDao.selectByConditions(moneyFlow, dateS, dateE, prefix);
                    for (SupplierSalesDifferenceReservedAccountTransferFlow flow : list3) {
                        PaymentCommonGrpc.FrontPageFlow.Builder flowBuilder = PaymentCommonGrpc.FrontPageFlow.newBuilder();
                        flowBuilder.setFlowNo(flow.getPurchaseOrderNo()==null?"":flow.getPurchaseOrderNo())
                                .setTransactionAmount(flow.getTransactionAmount().doubleValue())
                                .setCurrencyCode(flow.getCurrencyCode()==null?"":flow.getCurrencyCode())
                                .setAmountAfterTransaction(flow.getAmountAfterTransaction().doubleValue())
                                .setRemark(flow.getRemark() == null ? "" : flow.getRemark())
                                .setCreateTime(flow.getTransferTime().getTime()).setType(flow.getFlowType()).setCreateByName(flow.getCreatedByName());
                        respBuilder.addFlowList(flowBuilder);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("unknown AccountType: " + accountTypeConstant.getAccountName());
            }
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("getFrozenAccountFlow throw exception",e.getMessage());
            throw new RuntimeException("getFrozenAccountFlow throw exception");
        }
    }

    // 统计流水条数及金额
    @Override
    public void getReservedAccountSubtotal (SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalReq req,
                                            StreamObserver<SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp> responseObserver) {
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
            SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp.Builder respBuilder = SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp.newBuilder();
            SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp resp;

            List<FlowSubtotal> list;
            // 冻结账户流水统计
            switch (accountTypeConstant) {
                case SUPPLIER_UNIT_PRICE_DISCOUNT_RESERVED_ACCOUNT:
                    list = supplierUnitPriceDiscountReservedAccountFlowDao.selectTotal(moneyFlow, dateS, dateE, tablePrefix);
                    break;
                case SUPPLIER_PURCHASE_RESERVED_ACCOUNT: // 采购预留账户
                    list = supplierPurchaseReservedAccountFlowDao.selectTotal(moneyFlow, dateS, dateE, tablePrefix);
                    break;
                case SUPPLIER_SALES_DIFFERENCE_RESERVED_ACCOUNT:
                    list = supplierSalesDifferenceReservedAccountFlowDao.selectTotal(moneyFlow, dateS, dateE, tablePrefix);
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
