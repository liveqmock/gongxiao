package com.yhglobal.gongxiao.payment.jdservice;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.ErrorCodeJd;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.AccountManageDao.*;
import com.yhglobal.gongxiao.payment.microservice.GrpcUpdateAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.GrpcUpdateAccountStructure;
import com.yhglobal.gongxiao.payment.model.*;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import io.grpc.stub.StreamObserver;
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
public class GrpcUpdateAccountServiceImpl extends GrpcUpdateAccountServiceGrpc.GrpcUpdateAccountServiceImplBase{


    private static final Logger logger = LoggerFactory.getLogger(GrpcUpdateAccountServiceImpl.class);

    @Autowired
    UpdateAccountService updateAccountService;

    /**
     * 单价折扣冻结账户更新
     * @return
     */
    @Override
    public void updateSupplierUnitPriceDiscountFrozenAccount(GrpcUpdateAccountStructure.UpdateRequest req,
                                                                  StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> streamObserver) {
        logger.info("start updateSupplierUnitPriceDiscountFrozenAccount");
        try{
            double amount = req.getAmount();
            String prefix = req.getPrefix();
            long projectId = req.getProjectId();
            String remark = req.getRemark();
            GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
            long supplierId = req.getSupplierId();
            // long transactionTime = req.getTransactionTime();
            RpcResult rpcResult = updateAccountService.updateSupplierUnitPriceDiscountFrozenAccount(supplierId, projectId, prefix, rpcHeader, new BigDecimal(amount), remark);
            if (rpcResult.getCode() == ErrorCodeJd.SUCCESS.getErrorCode()) {
                logger.info("updateSupplierUnitPriceDiscountFrozenAccount success");
            }
        }catch (Exception e){
            logger.error("updateSupplierUnitPriceDiscountFrozenAccount fail", e.getMessage());
            throw new RuntimeException();
        }

    }

    /**
     * 更新供应商采购预留冻结账户
     * @return
     */
    @Override
    public void updateSupplierPurchaseReservedFrozenAccount(GrpcUpdateAccountStructure.UpdateRequest req,
                                                             StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> streamObserver) {

        logger.info("start updateSupplierPurchaseReservedFrozenAccount");
        try{
            double amount = req.getAmount();
            String prefix = req.getPrefix();
            long projectId = req.getProjectId();
            String remark = req.getRemark();
            GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
            long supplierId = req.getSupplierId();
            long transactionTime = req.getTransactionTime();

            RpcResult rpcResult = updateAccountService.updateSupplierPurchaseReservedFrozenAccount(supplierId, projectId, prefix, rpcHeader, new BigDecimal(amount), remark);
            if (rpcResult.getCode() == ErrorCodeJd.SUCCESS.getErrorCode()) {
                logger.info("updateSupplierPurchaseReservedFrozenAccount success");
            }

        }catch (Exception e){
            logger.error("updateSupplierPurchaseReservedFrozenAccount fail", e.getMessage());
            throw new RuntimeException();
        }

    }

    /**
     * 更新销售差价账户
     * @return
     */
    @Override
    public void updateSalesDifferenceAccount(GrpcUpdateAccountStructure.UpdateRequest req,
                                                             StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> streamObserver) {


        logger.info("start updateSalesDifferenceAccount");
        try{
            double amount = req.getAmount();
            String prefix = req.getPrefix();
            long projectId = req.getProjectId();
            String remark = req.getRemark();
            GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
            long supplierId = req.getSupplierId();
            long transactionTime = req.getTransactionTime();
            RpcResult rpcResult = updateAccountService.updateSalesDifferenceAccount(supplierId, projectId, prefix, rpcHeader, new BigDecimal(amount + ""), remark);
            if (rpcResult.getCode() == ErrorCodeJd.SUCCESS.getErrorCode()) {
                logger.info("updateSalesDifferenceAccount success");
            }else {
                logger.info("updateSalesDifferenceAccount fail");
            }
        }catch (Exception e){
            logger.error("updateSalesDifferenceAccount fail", e.getMessage());
            throw new RuntimeException();
        }
    }

//    /**
//     * 更新越海返利实收账户
//     * @return
//     */
//    @Override
//    public void updateYhglobalReceivedCouponAccount(GrpcUpdateAccountStructure.UpdateRequest req,
//                                                             StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> streamObserver) {
//
//        logger.info("start updateYhglobalReceivedCouponAccount");
//        try{
//            double amount = req.getAmount();
//            String prefix = req.getPrefix();
//            long projectId = req.getProjectId();
//            String remark = req.getRemark();
//            GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
//            long supplierId = req.getSupplierId();
//            long transactionTime = req.getTransactionTime();
//
//            logger.info("updateYhglobalReceivedCouponAccount success");
//        }catch (Exception e){
//            logger.error("updateYhglobalReceivedCouponAccount fail", e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    /**
//     * 更新越海代垫实收账户
//     * @return
//     */
//    @Override
//    public void updateYhglobalReceivedPrepaidAccount(GrpcUpdateAccountStructure.UpdateRequest req,
//                                                             StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> streamObserver) {
//        logger.info("start updateYhglobalReceivedPrepaidAccount");
//        try{
//            double amount = req.getAmount();
//            String prefix = req.getPrefix();
//            long projectId = req.getProjectId();
//            String remark = req.getRemark();
//            GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
//            long supplierId = req.getSupplierId();
//            long transactionTime = req.getTransactionTime();
//
//            logger.info("updateYhglobalReceivedPrepaidAccount success");
//        }catch (Exception e){
//            logger.error("updateYhglobalReceivedPrepaidAccount fail", e.getMessage());
//            throw new RuntimeException();
//        }
//
//    }

}
