package com.yhglobal.gongxiao.bootstrap;

import com.yhglobal.gongxiao.grpc.server.RpcServerConfig;
import com.yhglobal.gongxiao.purchase.microservice.NotifiInboundServiceGrpc;
import com.yhglobal.gongxiao.purchase.microservice.PurchaseFileServiceGrpc;
import com.yhglobal.gongxiao.purchase.microservice.PurchaseServiceGrpc;
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
public class JappPurchaseManagementBootstrapServer {

    private static final Logger logger = LoggerFactory.getLogger(JappPurchaseManagementBootstrapServer.class);

    @Autowired
    PurchaseFileServiceGrpc.PurchaseFileServiceImplBase purchaseFileServiceImplBase;

    @Autowired
    PurchaseServiceGrpc.PurchaseServiceImplBase purchaseServiceImplBase;

    @Autowired
    NotifiInboundServiceGrpc.NotifiInboundServiceImplBase notifiInboundServiceImplBase;

    @Autowired
    RpcServerConfig rpcServerConfig;

    private static ApplicationContext applicationContext = null;

    public static void main(String[] args) throws Exception {
        String serverMode = CommonUtil.getServerMode();
        logger.info("serverMode={}", serverMode);
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JappPurchaseManagementBootstrapServer bootstrapServer = applicationContext.getBean("jappPurchaseManagementBootstrapServer", JappPurchaseManagementBootstrapServer.class);
        bootstrapServer.start();
        bootstrapServer.blockUntilShutdown();
    }


    private Server server;

    private void start() throws IOException {
        int port = rpcServerConfig.getServerPort();
        server = ServerBuilder.forPort(port)
                .addService(purchaseFileServiceImplBase)
                .addService(purchaseServiceImplBase)
                .addService(notifiInboundServiceImplBase)
                .build()
                .start();
        logger.info("JappPurchaseManagementBootstrapServer started on port: {}", port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down JappPurchaseManagementBootstrapServer since JVM is shutting down");
                JappPurchaseManagementBootstrapServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            logger.info("JappPurchaseManagementBootstrapServer stopped");
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}
