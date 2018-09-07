package com.yhglobal.gongxiao.warehouse.bootstrap;

import com.yhglobal.gongxiao.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JappWarehouseManagementBootstrapServer {
    private static final Logger logger = LoggerFactory.getLogger(JappWarehouseManagementBootstrapServer.class);

    private static ApplicationContext applicationContext = null;

    public static void main(String[] args) throws InterruptedException {
        String serverMode = CommonUtil.getServerMode();
        logger.info("serverMode={}", serverMode);
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        synchronized (JappWarehouseManagementBootstrapServer.class) {  // 避免spring容器退出
            JappWarehouseManagementBootstrapServer.class.wait();
        }
    }
}
