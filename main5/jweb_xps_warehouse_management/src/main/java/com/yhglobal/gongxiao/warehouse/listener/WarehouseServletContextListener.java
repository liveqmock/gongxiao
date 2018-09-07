package com.yhglobal.gongxiao.warehouse.listener;

import com.yhglobal.gongxiao.wmsconfirm.WmsConfirmServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * 当jweb_wms_notify_gateway模块收到wms的回告之后，会调用本类注册的grpc服务，进行对应的出入库记录等操作
 */
@Service
public class WarehouseServletContextListener implements ApplicationListener<ContextRefreshedEvent> {


    private static final Logger logger = LoggerFactory.getLogger(WarehouseServletContextListener.class);

    @Autowired
    WmsConfirmServiceGrpc.WmsConfirmServiceImplBase wmsConfirmServiceImplBase;

    private Server server;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {

        if (evt.getApplicationContext().getParent() == null) {
            logger.info("*** WarehouseServletContextListener.onApplicationEvent got executed");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        startWarehouseGrpcService();
                        blockUntilShutdown();
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }).start();

            logger.info("*** WarehouseServletContextListener.onApplicationEvent completed");
        }


    }


    private void startWarehouseGrpcService() throws IOException {
        int port = 11220;
        server = ServerBuilder.forPort(port)
                .addService(wmsConfirmServiceImplBase)
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (server != null) { //关闭grpc服务
                    server.shutdown();
                    logger.info("*** xps warehouse GRPC server shut down");
                }
            }
        });

    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}
