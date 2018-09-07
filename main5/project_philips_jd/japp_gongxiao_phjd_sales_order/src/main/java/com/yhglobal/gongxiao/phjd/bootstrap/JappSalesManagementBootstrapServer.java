package com.yhglobal.gongxiao.phjd.bootstrap;

import com.yhglobal.gongxiao.grpc.server.RpcServerConfig;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceGrpc;
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
 * @author weizecheng
 * @date 2018/8/20 10:48
 */
@Component
public class JappSalesManagementBootstrapServer {

    private static final Logger logger = LoggerFactory.getLogger(JappSalesManagementBootstrapServer.class);

    @Autowired
    RpcServerConfig rpcServerConfig;
    @Autowired
    SalesOrderServiceGrpc.SalesOrderServiceImplBase salesOrderServiceImplBase;
    @Autowired
    SalesOrderAddressServiceGrpc.SalesOrderAddressServiceImplBase salesOrderAddressServiceImplBase;
    @Autowired
    SalesOrderDeliveryServiceGrpc.SalesOrderDeliveryServiceImplBase salesOrderDeliveryServiceImplBase;


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
//        int port = 16220;
        server = ServerBuilder.forPort(port)
                .addService(salesOrderServiceImplBase)
                .addService(salesOrderAddressServiceImplBase)
                .addService(salesOrderDeliveryServiceImplBase)
                .build()
                .start();
        logger.info("JappSalesManagementBootstrapServer started on port: {}", port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down JappSalesManagementBootstrapServer phjd since JVM is shutting down");
                JappSalesManagementBootstrapServer.this.stop();
                System.err.println("*** phjd server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            logger.info("JappSalesManagementBootstrapServer phjd stopped");
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}
