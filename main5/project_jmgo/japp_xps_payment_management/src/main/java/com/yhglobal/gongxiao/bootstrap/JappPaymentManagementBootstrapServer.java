package com.yhglobal.gongxiao.bootstrap;

import com.yhglobal.gongxiao.accountmanage.microservice.DistributorCouponTransferServiceGrpc;
import com.yhglobal.gongxiao.accountmanage.microservice.SupplierCouponPostingServiceGrpc;
import com.yhglobal.gongxiao.accountmanage.microservice.YhglobalCouponServiceGrpc;
import com.yhglobal.gongxiao.accountmanage.microservice.YhglobalCouponWriteoffServiceGrpc;
import com.yhglobal.gongxiao.grpc.server.RpcServerConfig;
import com.yhglobal.gongxiao.payment.microservice.*;
import com.yhglobal.gongxiao.util.CommonUtil;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 */
@Component
public class JappPaymentManagementBootstrapServer {

    private static final Logger logger = LoggerFactory.getLogger(JappPaymentManagementBootstrapServer.class);

    @Autowired
    YhglobalAccountServiceGrpc.YhglobalAccountServiceImplBase yhglobalAccountService;

    @Autowired
    CashConfirmServiceGrpc.CashConfirmServiceImplBase cashConfirmService ;

    @Autowired
    CashLedgerServiceGrpc.CashLedgerServiceImplBase cashLedgerService ;

    @Autowired
    DistributorAccountServiceGrpc.DistributorAccountServiceImplBase distributorAccountService;

    @Autowired
    SupplierAccountServiceGrpc.SupplierAccountServiceImplBase supplierAccountService ;

    @Autowired
    SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceImplBase supplierCouponPostingService;

    @Autowired
    DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceImplBase distributorCouponTransferService;

    @Autowired
    YhglobalCouponWriteoffServiceGrpc.YhglobalCouponWriteoffServiceImplBase yhglobalCouponWriteoffService;

    @Autowired
    YhglobalCouponServiceGrpc.YhglobalCouponServiceImplBase yhglobalCouponService;

    @Autowired
    RpcServerConfig rpcServerConfig;

    private static ApplicationContext applicationContext = null;

    public static void main(String[] args) throws Exception {
        String serverMode = CommonUtil.getServerMode();
        logger.info("serverMode={}", serverMode);
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JappPaymentManagementBootstrapServer bootstrapServer = applicationContext.getBean("jappPaymentManagementBootstrapServer", JappPaymentManagementBootstrapServer.class);
        bootstrapServer.start();
        bootstrapServer.blockUntilShutdown();
    }


    private Server server;

    private void start() throws IOException {
        int port = rpcServerConfig.getServerPort();
        server = ServerBuilder.forPort(port)
                .addService(supplierCouponPostingService)
                .addService(yhglobalAccountService)
                .addService(distributorAccountService)
                .addService(supplierAccountService)
                .addService(cashConfirmService)
                .addService(cashLedgerService)
                .addService(distributorCouponTransferService)
                .addService(yhglobalCouponWriteoffService)
                .addService(yhglobalCouponService)
                .build()
                .start();
        logger.info("JappPaymentManagementBootstrapServer started on port: {}", port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down JappPaymentManagementBootstrapServer since JVM is shutting down");
                JappPaymentManagementBootstrapServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            logger.info("JappPaymentManagementBootstrapServer stopped");
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


}
