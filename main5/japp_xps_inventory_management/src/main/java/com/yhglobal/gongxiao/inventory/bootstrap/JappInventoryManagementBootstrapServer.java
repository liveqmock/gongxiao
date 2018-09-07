package com.yhglobal.gongxiao.inventory.bootstrap;

import com.yhglobal.gongxiao.grpc.server.RpcServerConfig;
import com.yhglobal.gongxiao.inventory.account.microservice.InventoryLedgerServiceGrpc;
import com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventoryBatchServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventoryCheckServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryFlowServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmoundServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncServiceGrpc;
import com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeServiceGrpc;
import com.yhglobal.gongxiao.inventory.wholeinventoryage.microservice.WholeInventoryAgeServiceGrpc;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.wmsconfirm.WmsConfirmServiceGrpc;
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
public class JappInventoryManagementBootstrapServer {

    private static final Logger logger = LoggerFactory.getLogger(JappInventoryManagementBootstrapServer.class);

    /**
     * 库存台账服务类
     */
    @Autowired
    InventoryLedgerServiceGrpc.InventoryLedgerServiceImplBase inventoryLedgerServiceImplBase;
    /**
     * 库存批次查询服务类
     */
    @Autowired
    InventoryBatchServiceGrpc.InventoryBatchServiceImplBase inventoryBatchServiceImplBase;
    /**
     *  核对库存服务类
     */
    @Autowired
    InventoryCheckServiceGrpc.InventoryCheckServiceImplBase inventoryCheckServiceImplBase;
    /**
     *  出入库流水台账服务类
     */
    @Autowired
    InventoryFlowServiceGrpc.InventoryFlowServiceImplBase inventoryFlowServiceImplBase;
    /**
     *  库存查询服务类
     */
    @Autowired
    InventoryquerryServiceGrpc.InventoryquerryServiceImplBase inventoryquerryServiceImplBase;
    /**
     *  库存同步服务类
     */
    @Autowired
    InventorysyncServiceGrpc.InventorysyncServiceImplBase inventorysyncServiceImplBase;

    /**
     *  批次库存库龄服务类
     */
    @Autowired
    InventoryAgeServiceGrpc.InventoryAgeServiceImplBase inventoryAgeServiceImplBase;

    /**
     *  月度库存服务类
     */
    @Autowired
    InventoryMonthAmoundServiceGrpc.InventoryMonthAmoundServiceImplBase inventoryMonthAmoundServiceImplBase;

    /**
     *  整体库龄服务类
     */
    @Autowired
    WholeInventoryAgeServiceGrpc.WholeInventoryAgeServiceImplBase wholeInventoryAgeServiceImplBase;

    /**
     *  180天库存库龄服务类
     */
    @Autowired
    ProductInventory180AgeServiceGrpc.ProductInventory180AgeServiceImplBase productInventory180AgeServiceImplBase;

    /**
     *  插入即时库存服务类
     */
    @Autowired
    InsertInventoryServiceGrpc.InsertInventoryServiceImplBase insertInventoryServiceImplBase;


    @Autowired
    RpcServerConfig rpcServerConfig;
    
    private static ApplicationContext applicationContext = null;

    public static void main(String[] args) throws Exception {
        String serverMode = CommonUtil.getServerMode();
        logger.info("serverMode={}", serverMode);
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JappInventoryManagementBootstrapServer bootstrapServer = applicationContext.getBean("jappInventoryManagementBootstrapServer", JappInventoryManagementBootstrapServer.class);
        bootstrapServer.start();
        bootstrapServer.blockUntilShutdown();
    }


    private Server server;

    private void start() throws IOException {
        int port = rpcServerConfig.getServerPort();
        server = ServerBuilder.forPort(port)
                .addService(inventoryLedgerServiceImplBase)
                .addService(inventoryBatchServiceImplBase)
                .addService(inventoryCheckServiceImplBase)
                .addService(inventoryFlowServiceImplBase)
                .addService(inventoryquerryServiceImplBase)
                .addService(inventorysyncServiceImplBase)
                .addService(inventoryAgeServiceImplBase)
                .addService(inventoryMonthAmoundServiceImplBase)
                .addService(wholeInventoryAgeServiceImplBase)
                .addService(productInventory180AgeServiceImplBase)
                .addService(insertInventoryServiceImplBase)
                .build()
                .start();
        logger.info("JappInventoryManagementBootstrapServer started on port: {}", port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down JappInventoryManagementBootstrapServer since JVM is shutting down");
                JappInventoryManagementBootstrapServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            logger.info("JappInventoryManagementBootstrapServer stopped");
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


}
