package com.yhglobal.gongxiao.sales.bootstrap;

import com.yhglobal.gongxiao.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;


public class JappSalesOrderBootstrapServer {

    private static final Logger logger = LoggerFactory.getLogger(JappSalesOrderBootstrapServer.class);

    private static ApplicationContext applicationContext = null;

    public static void main(String[] args) throws InterruptedException {
        String serverMode = CommonUtil.getServerMode();
        logger.info("serverMode={}", serverMode);
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        synchronized (JappSalesOrderBootstrapServer.class) {  // 避免spring容器退出
            JappSalesOrderBootstrapServer.class.wait();
        }
    }

}
