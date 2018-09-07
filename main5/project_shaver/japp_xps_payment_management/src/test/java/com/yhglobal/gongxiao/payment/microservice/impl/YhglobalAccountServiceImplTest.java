package com.yhglobal.gongxiao.payment.microservice.impl;

import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure;
import com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * 葛灿
 *
 * @author 葛灿
 */
public class YhglobalAccountServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void getYhglobalReceivedAccountAmount() {
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader("1", "1", "gecan");
        YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest rpcRequest = YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest.newBuilder()
                .setRpcHeader(rpcHeader)
                .setProjectId(146798161)
                .build();
        YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
        PaymentCommonGrpc.AccountAmountResponse rpcResponse = yhglobalAccountService.getYhglobalReceivedAccountAmount(rpcRequest);
        logger.info("rpcResponse={}", rpcResponse);
    }

    @Test
    public void getSellHighAccount() {
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader("1", "1", "gecan");
        SupplierAccountServiceStructure.GetSellHighAccountRequest rpcRequest = SupplierAccountServiceStructure.GetSellHighAccountRequest.newBuilder()
                .setRpcHeader(rpcHeader)
                .setProjectId(146798161)
                .build();
        SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
        SupplierAccountServiceStructure.GetSellHighAccountResponse rpcResponse = rpcStub.getSellHighAccount(rpcRequest);
        logger.info("rpcResponse={}", rpcResponse);
    }

    @Test
    public void updateYhglobalReceivedCouponAccount(){
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader("1", "1", "gecan");
        PaymentCommonGrpc.WriteOffRequest rpcRequest = PaymentCommonGrpc.WriteOffRequest.newBuilder()
                .setRpcHeader(rpcHeader)
                .setProjectId(146798161)
                .setAmount(-399999999999999L)
                .build();
        YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
        PaymentCommonGrpc.WriteOffResponse rpcResponse = yhglobalAccountService.updateYhglobalReceivedCouponAccount(rpcRequest);
        logger.info("rpcResponse={}", rpcResponse);
    }

    @Test
    public void updateYhglobalReceivedPrepaidAccount(){
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader("1", "1", "gecan");
        PaymentCommonGrpc.WriteOffRequest rpcRequest = PaymentCommonGrpc.WriteOffRequest.newBuilder()
                .setRpcHeader(rpcHeader)
                .setProjectId(146798161)
                .setAmount(5)
                .build();
        YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
        PaymentCommonGrpc.WriteOffResponse rpcResponse = yhglobalAccountService.updateYhglobalReceivedPrepaidAccount(rpcRequest);
        logger.info("rpcResponse={}", rpcResponse);
    }

    @Test
    public void updateSellHighAccount(){
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader("1", "1", "gecan");
        PaymentCommonGrpc.WriteOffRequest rpcRequest = PaymentCommonGrpc.WriteOffRequest.newBuilder()
                .setRpcHeader(rpcHeader)
                .setProjectId(146798161)
                .setAmount(5)
                .build();
        SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
        PaymentCommonGrpc.WriteOffResponse rpcResponse = rpcStub.writeOffUpdateSellHighAccount(rpcRequest);
        logger.info("rpcResponse={}", rpcResponse);
    }


}