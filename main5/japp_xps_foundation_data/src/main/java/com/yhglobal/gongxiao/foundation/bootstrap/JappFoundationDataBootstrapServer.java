package com.yhglobal.gongxiao.foundation.bootstrap;

import com.yhglobal.gongxiao.foundation.area.microservice.AreaServiceGrpc;
import com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserServiceGrpc;
import com.yhglobal.gongxiao.grpc.server.RpcServerConfig;
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


@Component
public class JappFoundationDataBootstrapServer {

    private static final Logger logger = LoggerFactory.getLogger(JappFoundationDataBootstrapServer.class);

    @Autowired
    AreaServiceGrpc.AreaServiceImplBase areaServiceImplBase;

//    @Autowired
//    FileAddressServiceGrpc.FileAddressServiceImplBase fileAddressServiceImplBase;

    @Autowired
    ShippingAddressServiceGrpc.ShippingAddressServiceImplBase shippingAddressServiceImplBase;

    @Autowired
    CurrencyServiceGrpc.CurrencyServiceImplBase currencyServiceImplBase;

    @Autowired
    ChannelServiceGrpc.ChannelServiceImplBase channelServiceImplBase;

    @Autowired
    DistributorServiceGrpc.DistributorServiceImplBase distributorServiceImplBase;

    @Autowired
    ProductServiceGrpc.ProductServiceImplBase productServiceImplBase;

    @Autowired
    ProjectServiceGrpc.ProjectServiceImplBase projectServiceImplBase;

    @Autowired
    SupplierServiceGrpc.SupplierServiceImplBase supplierServiceImplBase;

    @Autowired
    WarehouseServiceGrpc.WarehouseServiceImplBase warehouseServiceImplBase;

    @Autowired
    YhportalUserServiceGrpc.YhportalUserServiceImplBase yhportalUserServiceImplBase;

    @Autowired
    RpcServerConfig rpcServerConfig;

    private static ApplicationContext applicationContext = null;

    public static void main(String[] args) throws Exception {
        String serverMode = CommonUtil.getServerMode();
        logger.info("serverMode={}", serverMode);
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JappFoundationDataBootstrapServer bootstrapServer = applicationContext.getBean("jappFoundationDataBootstrapServer", JappFoundationDataBootstrapServer.class);
        bootstrapServer.start();
        bootstrapServer.blockUntilShutdown();
    }


    private Server server;

    private void start() throws IOException {
        int port = rpcServerConfig.getServerPort();
        server = ServerBuilder.forPort(port)
                .addService(areaServiceImplBase)
                .addService(channelServiceImplBase)
                .addService(shippingAddressServiceImplBase)
                .addService(currencyServiceImplBase)
                .addService(distributorServiceImplBase)
                .addService(productServiceImplBase)
                .addService(projectServiceImplBase)
                .addService(supplierServiceImplBase)
                .addService(warehouseServiceImplBase)
                .addService(yhportalUserServiceImplBase)
                .build()
                .start();
        logger.info("JappFoundationDataBootstrapServer started on port: {}", port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down JappFoundationDataBootstrapServer since JVM is shutting down");
                JappFoundationDataBootstrapServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            logger.info("JappFoundationDataBootstrapServer stopped");
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


}
