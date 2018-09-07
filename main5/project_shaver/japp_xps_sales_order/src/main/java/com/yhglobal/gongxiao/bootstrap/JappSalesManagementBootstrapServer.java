package com.yhglobal.gongxiao.bootstrap;

import com.yhglobal.gongxiao.grpc.server.RpcServerConfig;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerServiceGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemServiceGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesScheduleDeliveryServiceGrpc;
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
public class JappSalesManagementBootstrapServer {

    private static final Logger logger = LoggerFactory.getLogger(JappSalesManagementBootstrapServer.class);

    @Autowired
    RpcServerConfig rpcServerConfig;

    @Autowired
    SalesOrderServiceGrpc.SalesOrderServiceImplBase salesOrderServiceImplBase;

    @Autowired
    SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceImplBase salesScheduleDeliveryServiceImplBase;

    @Autowired
    PlanSaleServiceGrpc.PlanSaleServiceImplBase planSaleServiceImplBase;

    @Autowired
    PlanSaleItemServiceGrpc.PlanSaleItemServiceImplBase planSaleItemServiceImplBase;

    @Autowired
    PlanSaleCustomerServiceGrpc.PlanSaleCustomerServiceImplBase planSaleCustomerServiceImplBase;

    @Autowired
    SalesOrderReturnServiceGrpc.SalesOrderReturnServiceImplBase salesOrderReturnServiceImplBase;

    private static ApplicationContext applicationContext = null;

    public static void main(String[] args) throws Exception {
        String serverMode = CommonUtil.getServerMode();
        logger.info("serverMode={}", serverMode);
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JappSalesManagementBootstrapServer bootstrapServer = applicationContext.getBean("jappSalesManagementBootstrapServer", JappSalesManagementBootstrapServer.class);
        bootstrapServer.start();
        bootstrapServer.blockUntilShutdown();
    }


    private Server server;

    private void start() throws IOException {
        int port = rpcServerConfig.getServerPort();
        server = ServerBuilder.forPort(port)
                .addService(salesOrderServiceImplBase)
                .addService(salesScheduleDeliveryServiceImplBase)
                .addService(planSaleCustomerServiceImplBase)
                .addService(planSaleItemServiceImplBase)
                .addService(planSaleServiceImplBase)
                .addService(salesOrderReturnServiceImplBase)
                .build()
                .start();
        logger.info("JappSalesManagementBootstrapServer started on port: {}", port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down JappSalesManagementBootstrapServer since JVM is shutting down");
                JappSalesManagementBootstrapServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            logger.info("JappSalesManagementBootstrapServer stopped");
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}
